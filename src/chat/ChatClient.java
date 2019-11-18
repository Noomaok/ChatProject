package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Thread {

    private Socket clientSocket;
    private PrintStream socOut;
    private BufferedReader socIn;

    public ChatClient(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.socOut = new PrintStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            String line;
            while (true) {
                line = socIn.readLine();
                if(line == null) {
                    System.out.println("Server disconnected !");
                    System.exit(1);
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            //System.err.println("Error in client run");
        }
    }

    public void send(String message) {
        socOut.println(message);
    }

    public void close() throws IOException {
        this.socOut.close();
        this.socIn.close();
        this.clientSocket.close();
    }

    // MAIN
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            ChatClient cc = new ChatClient(new Socket(args[0], Integer.parseInt(args[1])));
            cc.start();
            String line;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // do read input from client
                line = stdIn.readLine();
                if (line.equals("/quit")) break;
                cc.send(line);
            }
            cc.close();
            stdIn.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't get I/O for " + "the connection to:" + args[0]);
            System.exit(1);
        }
    }
}