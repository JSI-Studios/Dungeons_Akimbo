package dungeonsAkimbo.netcode;

import java.util.ArrayList;
import java.util.List;

public class DaClient extends Thread {

	private Thread run, listen;
	private DaClientConnectionHandler DaHandler;
	private List<String> messageLog = new ArrayList<String>();

	private boolean running;

	public DaClient(String name, String address, int port) {
		DaHandler = new DaClientConnectionHandler(name, address, port);
		boolean connect = DaHandler.openConnnection(address);
		if (!connect) {
			System.err.println("Connection failed!");
			console("Connection Failed");
		}
		String connection = "/c/" + name + "/e/";
		DaHandler.send(connection.getBytes());
		running = true;
		run = new Thread(this, "DaClient");
		run.start();
	}

	public void run() {
		listen();
	}

	private void send(String message, boolean text) {
		if (message.equals(""))
			return;
		if (text) {
			message = DaHandler.getClientName() +"(" + DaHandler.getID() + ")" + ": " + message;
			message = "/m/" + message;

		}
		DaHandler.send(message.getBytes());
	}
	
	public void sendMessage(String message) {
		send(message, true);
	}

	public void listen() {
		listen = new Thread("Listen") {
			public void run() {
				while (running) {
					String message = DaHandler.receive();
					if (message.startsWith("/c/")) {
						DaHandler.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
						console("Connection Successful!");
					} else if (message.startsWith("/m/")) {
						String text = message.substring(3);
						text = text.split("/e/")[0];
						
						console(text);
						messageLog.add(text);
					} else if (message.startsWith("/i/")) {
						String text = "/i/" + DaHandler.getID() + "/e/";
						send(text, false);
					}
				}
			}
		};
		listen.start();
	}
	
	public void console(String message) {
		message = DaHandler.getClientName() + "( " + DaHandler.getID() + " ): " + message;
		System.out.println("Client: " + message);
						
	}
	
	public List<String> getMessageLog(){
		return messageLog;
	}
	
	public void clearMessageLog() {
		messageLog.clear();
	}

}
