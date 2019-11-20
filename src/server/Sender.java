package server;

import java.util.LinkedList;
import java.util.Vector;

public class Sender {

    private static Sender instance = null;
    private Vector<ClientThread> allClient;
    private LinkedList<String> history;

    private Sender() {
        this.allClient = new Vector<>();
        this.history = new LinkedList<>();
    }

    public static Sender getInstance() {
        if (instance == null) {
            instance = new Sender();
        }
        return instance;
    }

    public void addClient(ClientThread ct) {
        allClient.add(ct);
    }

    public void removeClient(ClientThread ct) {
        allClient.remove(ct);
    }

    public void sendToAll(String message) {
        history.add(message);
        for (ClientThread ct : allClient) {
            ct.send(message);
        }
    }

    public void sendHistory(ClientThread ct) {
        for(String message: history) {
            ct.send(message);
        }
    }
}