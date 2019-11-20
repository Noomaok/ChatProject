package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient extends Thread {

    private Socket clientSocket;
    private PrintStream socOut;
    private BufferedReader socIn;
    private GUIClient parent;

    public ChatClient(Socket clientSocket, GUIClient parent) throws IOException {
        this.clientSocket = clientSocket;
        this.parent = parent;
        this.socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.socOut = new PrintStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            String line;
            while (true) {
                line = socIn.readLine();
                if (line == null) {
                    parent.displayMessage("Server disconnected !");
                    break;
                }
                parent.displayMessage(line);
            }
        } catch (IOException e) {
            // System.err.println("Error in client run");
        }
    }

    public void send(String message) {
        socOut.println(message);
    }

    public void close(){
        try {
            this.socOut.close();
            this.socIn.close();
            this.clientSocket.close();
        } catch (Exception e) {
        }
    }
}