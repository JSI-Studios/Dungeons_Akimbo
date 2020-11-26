package dungeonsAkimbo.netcode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class DaServer extends Thread {
	
	private List<DaServerClientHandler> clients = new ArrayList<DaServerClientHandler>();
	private List<Integer> clientResponse = new ArrayList<Integer>();
	
	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	
	//debugging flags
	private boolean raw = true;
	private boolean verbose = true;
	
	private Thread run, manage, send, receive;
	
	private final int MAX_ATTEMPTS = 5;
	
	public DaServer(int port) {
		this.setName("Server");	
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this, "Server sub-Process");
		run.start();
	}
		
	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		manageClients();
		receive();
		
		
	}

	

	private void manageClients() {
		// TODO Auto-generated method stub
		manage = new Thread("DaServerManager" ) {
			public void run() {
				while(running) {
					sendToAll("/i/server");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < clients.size(); i++) {
						DaServerClientHandler c = clients.get(i);
						if (!clientResponse.contains(clients.get(i).getID())) {
							if(c.attempt >= MAX_ATTEMPTS) {
								disconnect(c.getID(), false);
							} else {
								c.attempt = 0;
							}
						}
					}
					//manage clients
				}
			}
		};
		manage.start();		
	}
	
	private void receive() {
		// TODO Auto-generated method stub
		receive = new Thread("DaServerReceiver") {
			public void run() {
				while(running) {
					//recieve data
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						process(packet);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		receive.start();
		
	}
	
	private void sendToAll(String message) {
		if(message.startsWith("/m/")) {
			String text = message.substring(3);
			text = text.split("/e/")[0];
			System.out.println(message);
		}
		for (int i = 0; i < clients.size(); i++) {
			DaServerClientHandler client = clients.get(i);
			send(message.getBytes(), client.address, client.port);
		}
	}
	
	private void send(byte[] data, InetAddress address, int port) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					socket.send(packet);;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	private void send(String message, InetAddress address, int port) {
		message += "/e/";
		send(message.getBytes(), address, port);
	}
	
	private void process(DatagramPacket packet) throws ClassNotFoundException, IOException {
		String string = new String(packet.getData());
		if (raw) System.out.println(string);
		if (string.startsWith("/c/")) {
			int id = UniqueIdentifier.getIdentifier();
			String name = string.split("/c/|/e/")[1];
			System.out.println(name +"(" + id + ") connected!");
			if(verbose) System.out.println("From address: " + packet.getAddress().toString().split("/")[1] +":"+ packet.getPort());
			clients.add(new DaServerClientHandler(name, packet.getAddress(), packet.getPort(), id));
			String ID = "/c/" + id;
			send(ID, packet.getAddress(), packet.getPort());
		} else if (string.startsWith("/m/")) {
			sendToAll(string);
		} else if (string.startsWith("/d/")) {
			String id = string.split("/d/|/e/")[1];
			disconnect(Integer.parseInt(id), true);
		} else if (string.startsWith("/i/")) {
			if(verbose) System.out.println("Keep Alive revieved from " + packet.getAddress().toString().split("/")[1] + ":" + packet.getPort());
			clientResponse.add(Integer.parseInt(string.split("/i/|/e/")[1]));
		} else if (string.startsWith("/u/")) {
			System.out.println("player update recieved");
			UpdatePacket update = UpdateHandler.recieveUpdate(string.split("/u/|/e/")[1].getBytes());
			System.out.println(update.getFrame());
			System.out.println(update.getPlayerID());
			System.out.println(update.getPlayerPos().getX()+ " , " + update.getPlayerPos().getY());
			System.out.println(update.getPlayerVel().getX() + " , " + update.getPlayerVel().getY());
			
		} else {
			System.out.println(string);
		}
	}
	
	private void disconnect(int id, boolean status) {
		DaServerClientHandler c = null;
		boolean existed = false;
		for (int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getID() == id) {
				c = clients.get(i);
				clients.remove(i);
				existed = true;
				break;
			}
		}
		if (!existed) return;
		String message = "";
		if(status) {
			message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port
					+ " disconnected.";
		} else {
			message = "Client " + c.name + " (" + c.getID() + ") @ " + c.address.toString() + ":" + c.port
					+ " timed out.";		
		}
		System.out.println(message);
	}
}
