import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Simple server and client implementation");
        frame.setSize(GlobalVariables.WINDOW_SIZE[0], GlobalVariables.WINDOW_SIZE[1]);

        JPanel panel = new JPanel();
        JButton runButton = new JButton("Run Server & Client");
        JTextField messageField = new JTextField(30);
        JButton sendMessageButton = new JButton("Send message");

        ClientUDP clientUDP;
        ServerUDP serverUDP;
        try {
            clientUDP = new ClientUDP(GlobalVariables.ADDRESS, GlobalVariables.PORT);
            serverUDP = new ServerUDP(GlobalVariables.PORT);
        } catch (IOException e) {
            System.out.println("Some error occurred on initialising Server & Client. Exiting...");
            return;
        }

        runButton.addActionListener(event -> {
            new Thread(serverUDP).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(clientUDP).start();
            panel.remove(runButton);
            panel.repaint();
        });

        sendMessageButton.addActionListener(event -> {
            String message = messageField.getText();
            messageField.setText("");
            clientUDP.push(message.getBytes());
        });

        panel.add(runButton);
        panel.add(messageField);
        panel.add(sendMessageButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}
