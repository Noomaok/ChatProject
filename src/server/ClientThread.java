package server;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

	private Socket clientSocket;
	private PrintStream socOut;
	private String username = null;

	ClientThread(Socket s) {
		this.clientSocket = s;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket the client socket
	 **/
	public void run() {
		try {
			BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socOut = new PrintStream(clientSocket.getOutputStream());
			username = socIn.readLine();
			Sender.getInstance().sendHistory(this);
			Sender.getInstance().addClient(this);
			Sender.getInstance().sendToAll(username + " has joined");
			while (true) {
				String line = socIn.readLine();
				if (line == null) {
					Sender.getInstance().removeClient(this);
					Sender.getInstance().sendToAll(username + " has disconnected");
					break;
				}
				Sender.getInstance().sendToAll(username + " : " + line);
			}
			clientSocket.close();
			socIn.close();
			socOut.close();
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}

	public void send(String message) {
		socOut.println(message);
	}

}
