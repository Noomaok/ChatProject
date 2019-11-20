package chat_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatClient extends Thread {

    private MulticastSocket socket;
    private InetAddress address;
    private Integer port;
    private GUIClient parent;

    public ChatClient(String ipAddress, Integer port, GUIClient parent) throws IOException {
        this.socket = new MulticastSocket(port);
        this.address = InetAddress.getByName(ipAddress);
        this.port = port;
        this.parent = parent;
    }

    public void run() {
        try {
            socket.joinGroup(address);
            byte[] buf = new byte[256];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                parent.displayMessage(received);
            }
        } catch (IOException e) {
            // System.err.println("Error in client run");
        }
    }

    public void send(String message) {
        try {
            byte[] buf = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);
        } catch(Exception e) {}
    }

    public void close(){
        try {
            this.socket.leaveGroup(address);
            this.socket.close();
        } catch (Exception e) {
        }
    }
}