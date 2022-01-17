import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP implements Runnable {

    private final DatagramSocket socket;
    private final byte[] buffer;

    public ServerUDP(int port) throws IOException {
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[GlobalVariables.MAX_PACKET_SIZE];
    }

    @Override
    public void run() {
        System.out.println("Server starts");
        while (true) {
            try {
                DatagramPacket packetFromClient = new DatagramPacket(buffer, buffer.length);
                socket.receive(packetFromClient);

                byte[] response = "Got message".getBytes();
                DatagramPacket packetToClient = new DatagramPacket(
                    response, response.length,
                    packetFromClient.getAddress(), packetFromClient.getPort()
                );
                socket.send(packetToClient);

                System.out.println("Message from client: " + new String(packetFromClient.getData()));
            } catch (IOException ignored) {}
        }
    }

    public void shutdown() {
        this.socket.close();
        Thread.currentThread().interrupt();
    }
}
