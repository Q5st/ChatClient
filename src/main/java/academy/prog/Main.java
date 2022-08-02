package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				if (aCheck(text)){
					Message m = new Message(login,findTo(text), text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) { // 200 OK
						System.out.println("HTTP error ocurred: " + res);
						return;
					}
				}else {
					Message m = new Message(login, text);
					int res = m.send(Utils.getURL() + "/add");

					if (res != 200) { // 200 OK
						System.out.println("HTTP error ocurred: " + res);
						return;
					}
				}


			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}

	}

	public static String findTo(String text){
		String[] arrayText = text.split("[ ]");
		char[] textChar = arrayText[0].toCharArray();
		String textF = "";
		for (int i= 1; i< textChar.length; i++){
			textF = textF + textChar[i];
		}
		return textF;
	}

	public static boolean aCheck(String text){
		char[] textChar = text.toCharArray();
		if (textChar[0] == '@'){
			return true;
		}else {
			return false;
		}
	}
}
