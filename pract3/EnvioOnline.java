import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.net.*;


class EnvioOnline extends WindowAdapter { //con WindowListener tendriamos que sobreescribir todos sus metodos
	
	public String name;

	public EnvioOnline(String name) {
		this.name=name;
	}

	public void windowOpened(WindowEvent e) {
		try{
			Socket s = new Socket("192.168.1.33",9999); //puerto 9999 el del server que escucha
			PacketSend data = new PacketSend();
			data.setMessage("online");
			data.setName(name);
			ObjectOutputStream packet = new ObjectOutputStream(s.getOutputStream());
			packet.writeObject(data);
			s.close();
		}catch(Exception error) {
            error.printStackTrace();
        }
	}
}
