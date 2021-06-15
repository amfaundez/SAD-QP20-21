import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;


@SuppressWarnings("serial")
class ClientPanel extends JPanel implements Runnable{

	private String ip_a_enviar;
	private HashMap <String,String> mapa;
	private JTextField textfield;
	private JComboBox<String> ip;
	private JLabel nick ;
	private JTextArea textarea;
	private JButton button;
	private JScrollPane js;
	
	public ClientPanel() {
		String nick_user = JOptionPane.showInputDialog("Nick: "); //para cuando abrimos el programa
		JLabel n_nick = new JLabel("Nick:"); //delante del nick
		add(n_nick);
		nick = new JLabel();
		nick.setText(nick_user);
		add(nick);
		JLabel text = new JLabel("Online: ");
		add(text);
		ip = new JComboBox<>();
		add(ip);
		textarea = new JTextArea(12,20);
		js = new JScrollPane(textarea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(js);
		textarea.setEditable(false);
		add(new JTextField(20));
		button = new JButton("Send");
		SendText event = new SendText();
		button.addActionListener(event);
		add(button);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public String getNick() {
		String name = nick.getText();
		return name;
	}

	private class SendText implements ActionListener {
		public void actionPerformed(ActionEvent e) { //cuando apretamos el botón de enviar
			textarea.append("Yo: " + textfield.getText() + "\n" );
			try {
				Socket s = new Socket("192.168.1.33", 9999); //construccion del socket
				//Como enviamos tres Strings(nick,ip,mensaje) enviaremos un objeto
				PacketSend data = new PacketSend();
				data.setName(nick.getText());
				for(String x:mapa.keySet()) {
					 if(ip.getSelectedItem().toString() == x){
						  ip_a_enviar = mapa.get(x);
					 }
				}
				data.setIp(ip_a_enviar);
				data.setMessage(textfield.getText());
				//Creamos flujo para enviar el objeto
				ObjectOutputStream packet = new ObjectOutputStream(s.getOutputStream());
				packet.writeObject(data);
				s.close();
				textfield.setText("");
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
        }
	}
	
	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(9090);
			Socket s;
			PacketSend precieved;
			mapa = new HashMap<String,String>();
			while(true) {
				s = ss.accept();
				ObjectInputStream data = new ObjectInputStream(s.getInputStream());
				precieved = (PacketSend) data.readObject();
				if(!precieved.getMessage().equals("online")) {
					textarea.append(precieved.getName() + ": " + precieved.getMessage() + "\n");
				} else {
					HashMap <String,String> NombresMenu = new HashMap<String,String>();
					NombresMenu = precieved.getIpname();
					mapa.clear();
					mapa.putAll(NombresMenu);
					ip.removeAllItems();
					for(String z:NombresMenu.keySet()) {
						ip.addItem(z);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
