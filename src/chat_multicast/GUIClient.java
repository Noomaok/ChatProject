package chat_multicast;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.event.*;

public class GUIClient extends JFrame implements ActionListener, KeyListener {

    private JButton connectButton, quitButton, sendButton;
    private JTextField ipField, portField, usernameField, chatField;
    private JTextArea textArea;
    private ChatClient chatClient = null;
    private String username;

    public GUIClient() {
        this.setTitle("Chat client");
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(1, 6));
        connectionPanel.add(new JLabel("Server IP : "));
        ipField = new JTextField("230.0.0.0");
        connectionPanel.add(ipField);
        connectionPanel.add(new JLabel("Server PORT : "));
        portField = new JTextField("1111");
        connectionPanel.add(portField);
        connectionPanel.add(new JLabel("Username : "));
        usernameField = new JTextField();
        usernameField.addKeyListener(this);
        connectionPanel.add(usernameField);
        this.add(connectionPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scroller = new JScrollPane(textArea);
        mainPanel.add(scroller, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(new JLabel("Chat :"), BorderLayout.WEST);
        chatField = new JTextField();
        chatField.addKeyListener(this);
        inputPanel.add(chatField, BorderLayout.CENTER);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

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

    public void displayMessage(String message) {
        textArea.append(message + System.lineSeparator());
    }

    public void connect() {
        if (chatClient != null) {
            chatClient.send(username + " has disconnected !");
            chatClient.close();
        }
        textArea.setText("");
        
        String ip = ipField.getText();
        username = usernameField.getText();
        if(ip.equals("") || username.equals("")) {
            JOptionPane.showMessageDialog(this, "IP and Username must be set", "Input error", JOptionPane.ERROR_MESSAGE);
            chatClient = null;
        }
        else {
            try {
                Integer port = Integer.parseInt(portField.getText());
                chatClient = new ChatClient(ip, port, this);
                chatClient.start();
                chatClient.send(username + " has joined !");
            } catch (NumberFormatException nex) {
                JOptionPane.showMessageDialog(this, "Port value must be a number", "Input error", JOptionPane.ERROR_MESSAGE);
                chatClient = null;
            } catch (Exception ex) {
            }
        }
    }

    public void send() {
        String message = chatField.getText();
            if (chatClient != null && !message.equals("")) {
                chatClient.send(username + " : " + message);
                chatField.setText("");
            }
            chatField.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            connect();
        } else if (e.getSource() == quitButton) {
            if (chatClient != null) {
                chatClient.send(username + " has disconnected !");
                chatClient.close();
            }
            System.exit(0);
        } else if (e.getSource() == sendButton) {
            send();
        }
    }

    public void keyPressed(KeyEvent evt){
        if(evt.getSource() == usernameField) {
            if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
                connect();
            }
        } 
        else if(evt.getSource() == chatField) {
            if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
                send();
            }
        }
    }

    public void keyReleased(KeyEvent evt){} 

    public void keyTyped(KeyEvent evt) {}

    public static void main(String[] args) {
        GUIClient guiClient = new GUIClient();
    }
}