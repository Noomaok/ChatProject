package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Vector;

public class Sender {
    private final String HISTORY_FILE = "history.txt";

    private static Sender instance = null;
    private Vector<ClientThread> allClient;
    private LinkedList<String> history;

    private Sender() {
        this.allClient = new Vector<>();
        this.history = new LinkedList<>();
        try {
            BufferedReader historyReader = new BufferedReader(new FileReader(HISTORY_FILE));
            String line;
            while((line = historyReader.readLine()) != null) {
                history.add(line);
            }
            historyReader.close();
        } catch(Exception e) {
        }
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
        save(message);
        for (ClientThread ct : allClient) {
            ct.send(message);
        }
    }

    public void save(String message) {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(HISTORY_FILE, true));
            outFile.write(message);
            outFile.newLine();
            outFile.close();
        } catch(Exception e) {}
    }

    public void sendHistory(ClientThread ct) {
        for(String message: history) {
            ct.send(message);
        }
    }
}