import java.io.*;

public class TestReadLineMVC {
	public static void main(String[] args) {
		BufferedReader in = new EditableBufferedReaderMVC(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			str = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nline is: " + str);
	}
}
