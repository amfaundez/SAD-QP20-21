import javax.swing.*;

public class Server {

	public static void main(String[] args) {
		ServerFrame window = new ServerFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("server");
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}
}
