import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class Mailing {
    private static final int TIME_TO_WORK = 60000;
    private static final long TIME_TO_SEND_MSG = 3000;
    private static final long TIME_TO_BE_ALIVE = 4000;
    private static final byte TIME_TO_LINE = 120;
    private static final int SOCKET_TIMEOUT = 100;
    private static final int UUID_LENGTH = 36;

    private final String ownUuid;
    private InetAddress groupAddress;
    private final int groupPort;
    private final HashMap<String, Long> connectionsMap;
    private int connectionsCount;

    private long startTime;
    private long lastSendTime;

    public Mailing(String address, int port) {
        ownUuid = UUID.randomUUID().toString();
        try {
            groupAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        groupPort = port;
        connectionsMap = new HashMap<>();
        connectionsCount = 0;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        lastSendTime = System.currentTimeMillis();
        MulticastSocket socket = createSocket();
        waitForReceive(socket);
    }

    public MulticastSocket createSocket() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(groupPort);
            socket.joinGroup(groupAddress);
            socket.setSoTimeout(SOCKET_TIMEOUT);
        } catch (IOException ignored) {}
        return socket;
    }

    public void waitForReceive(MulticastSocket socket) {
        long workingTime = System.currentTimeMillis() - startTime;
        while (workingTime < TIME_TO_WORK) {
            String additionalInfo = "Test";
            byte[] sendData = (ownUuid + additionalInfo).getBytes(StandardCharsets.UTF_8);
            String receivedData = null;
            String receivedUuid = null;

            long passedAfterLastSendTime = System.currentTimeMillis() - lastSendTime;
            if (passedAfterLastSendTime > TIME_TO_SEND_MSG) {
                DatagramPacket sendDatagram = new DatagramPacket(
                    sendData, sendData.length, groupAddress, groupPort
                );
                try {
                    socket.send(sendDatagram, TIME_TO_LINE);
                } catch (IOException ignored) {}
                lastSendTime = System.currentTimeMillis();
            }

            DatagramPacket receiveDatagram = new DatagramPacket(
                new byte[sendData.length], sendData.length
            );
            try {
                socket.receive(receiveDatagram);
                receivedData = new String(
                    receiveDatagram.getData(), StandardCharsets.UTF_8
                );
                receivedUuid = receivedData.substring(0, UUID_LENGTH);
            } catch (IOException ignored) {}

            cleanConnections();
            if (receivedUuid != null && !receivedUuid.equals(ownUuid)) {
                connectionsMap.put(receivedData, System.currentTimeMillis());
            }
            if (connectionsMap.size() != connectionsCount) {
                connectionsCount = connectionsMap.size();
                printConnections();
            }

            workingTime = System.currentTimeMillis() - startTime;
        }
    }

    private void cleanConnections() {
        connectionsMap.entrySet().removeIf(next -> (
            System.currentTimeMillis() - next.getValue() > TIME_TO_BE_ALIVE
        ));
    }

    private void printConnections() {
        System.out.println("Connections:");
        for (var entry : connectionsMap.entrySet()) {
            String uuid = entry.getKey().substring(0, UUID_LENGTH);
            String additionalInfo = entry.getKey().substring(UUID_LENGTH);
            String connectionTime = Utils.getTimeFromTimestamp(entry.getValue());
            System.out.println(uuid + " | " + additionalInfo + " | " + connectionTime);
        }
        System.out.println();
    }
}
