package server;

import java.util.Vector;

public class Sender {

    private static Sender instance = null;
    private static Vector<ClientThread> allClient = null;

    private Sender() {
    }

    public static Sender getInstance() {
        if (instance == null) {
            instance = new Sender();
            allClient = new Vector<>();
        }
        return instance;
    }

    public void addClient(ClientThread ct) {
        allClient.add(ct);
    }

    public void removeClient(ClientThread ct) {
        allClient.remove(ct);
    }

    public void sendAll(String message) {
        for (ClientThread ct : allClient) {
            ct.send(message);
        }
    }
}