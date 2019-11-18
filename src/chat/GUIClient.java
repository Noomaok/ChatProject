package chat;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUIClient extends JFrame implements ActionListener {

    private JButton connectButton;
    private JButton quitButton;

    public GUIClient() {
        this.setTitle("Chat client");
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(1, 6));
        connectionPanel.add(new JLabel("Server IP : "));
        connectionPanel.add(new JTextField("127.0.0.1"));
        connectionPanel.add(new JLabel("Server PORT : "));
        connectionPanel.add(new JTextField("1111"));
        connectionPanel.add(new JLabel("Username : "));
        connectionPanel.add(new JTextField());
        this.add(connectionPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        mainPanel.add(textArea);
        this.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(connectButton);
        buttonPanel.add(quitButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == connectButton) {
            //do connect
        }
        else if(e.getSource() == quitButton){
            //do disconnect
        }
    }

    public static void main(String[] args) {
        GUIClient guiClient = new GUIClient();
    }
}