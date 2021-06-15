import javax.swing.*;

public class Client {
	public static void main(String[] args) {
		ClientFrame window = new ClientFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("client");
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}
}
