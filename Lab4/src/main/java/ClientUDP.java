import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientUDP implements Runnable {

    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;
    private final BlockingQueue<byte[]> queue;
    private final byte[] buffer;
    private final int[] timeouts;

    public ClientUDP(String ip, int port) throws IOException {
        this.address = InetAddress.getByName(ip);
        this.port = port;
        this.socket = new DatagramSocket();
        this.buffer = new byte[GlobalVariables.MAX_PACKET_SIZE];
        this.queue = new LinkedBlockingQueue<>();
        this.timeouts = new int[] {10, 50, 100, 500};
    }

    @Override
    public void run() {
        System.out.println("Client starts");
        while (true) {
            try {
                byte[] message = queue.take();
                DatagramPacket packetToServer = new DatagramPacket(message, message.length, address, port);

                for (int timeout : timeouts) {
                    try {
                        socket.setSoTimeout(timeout);
                        socket.send(packetToServer);
                        DatagramPacket packetFromServer = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packetFromServer);
                        System.out.println(new String(packetFromServer.getData()));
                        break;
                    } catch (IOException e) {
                        System.out.println("Fail: Too long waiting");
                    }
                }
            } catch (Exception ignored) {}
        }
    }

    public boolean push(byte[] message) {
        if (message.length < GlobalVariables.MAX_PACKET_SIZE) {
            queue.add(message);
            return true;
        }
        return false;
    }

    public void stop() {
        socket.close();
        Thread.currentThread().interrupt();
    }
}
