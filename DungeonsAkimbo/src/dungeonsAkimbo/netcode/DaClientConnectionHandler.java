package dungeonsAkimbo.netcode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DaClientConnectionHandler extends Thread {
	
	private String name, address;
	private int port;
	
	private DatagramSocket socket;
	private InetAddress netAddress;
	
	private Thread send;
	
	private int ID = -1;
	
	public DaClientConnectionHandler(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}
	
	public String getClientName() {
		return name;
	}
	
	public String getCLientAddress() {
		return address;
	}
	
	public int getClientPort() {
		return port;
	}
	
	public boolean openConnnection(String address) {
		try {
			socket = new DatagramSocket();
			netAddress = InetAddress.getByName(address);	
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String message = new String(packet.getData());
		return message;
	}
	
	public void send(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, netAddress, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	public void close() {
		new Thread() {
			public void run() {
				synchronized(socket) {
					socket.close();
				}
			}
		}.start();
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}

}
