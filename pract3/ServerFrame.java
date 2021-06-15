import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;

//@SuppressWarnings("serial")
class ServerFrame extends JFrame implements Runnable {

	private JTextArea textarea;

	public ServerFrame() {
		setBounds(1200, 300, 280, 350);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		textarea = new JTextArea();
		panel.add(textarea, BorderLayout.CENTER);
		add(panel);
		setVisible(true);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() { 
		try {
			ServerSocket ss = new ServerSocket(9999); //Escucha en el mismo puerto que hemos puesto en cliente
			String name,ip,message;
			HashMap <String,String> ipname = new HashMap<String,String>();
			PacketSend precieved;
			while(true) {
				Socket s = ss.accept(); 
				ObjectInputStream data_in = new ObjectInputStream(s.getInputStream());
				precieved = (PacketSend) data_in.readObject();
				name = precieved.getName();
				ip = precieved.getIp();
				message = precieved.getMessage();
				if(!message.equals("online")){
					textarea.append("\n" + name + ": " + message + " for " + ip);
					//Reenvio del paquete del server a los clientes
					Socket destination = new Socket(ip,9090);
					ObjectOutputStream pforwarded = new ObjectOutputStream(destination.getOutputStream());
					pforwarded.writeObject(precieved);
					pforwarded.close();
					destination.close();
					s.close();
				} else {
					InetAddress location = s.getInetAddress();
					String IpRemota = location.getHostAddress();
					ipname.put(name, IpRemota);
					precieved.setIpname(ipname);
					for(String ip_aux:ipname.values()) {
						Socket destination = new Socket(ip_aux,9090);
						ObjectOutputStream pforwarded = new ObjectOutputStream(destination.getOutputStream());
						pforwarded.writeObject(precieved);
						pforwarded.close(); 
						destination.close();
						s.close();
					}
				} 
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
