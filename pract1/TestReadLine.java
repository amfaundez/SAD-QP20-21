import java.io.*;

public class TestReadLine {
	public static void main(String[] args) {
		EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			str = in.readLine();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nLine is: " + str);
	}
}
