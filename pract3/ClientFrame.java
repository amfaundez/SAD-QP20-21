import javax.swing.*;

@SuppressWarnings("serial")
class ClientFrame extends JFrame {

	public ClientFrame() {
		setBounds(600, 300, 280, 350);
		ClientPanel panel = new ClientPanel();
		add(panel);
		setVisible(true);
		String name = panel.getNick();
		addWindowListener(new EnvioOnline(name)); //al abrir se ejecuta el método windowOpened
	}
}
