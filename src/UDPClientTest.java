import org.junit.Test;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.mockito.Mockito.*;

public class UDPClientTest {
    static InetAddress address;
    @Test
    public void testClient() {
        try {
            try {
                address = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            // Mock DatagramSocket
            DatagramSocket socketMock = mock(DatagramSocket.class);

            // Create UDPClient with mock DatagramSocket
            UDPClient client = new UDPClient(address,8888);
            client.send("Hello, server!");

            // Verify that DatagramPacket was sent to the correct address and port
            verify(socketMock, times(1)).send(any(DatagramPacket.class));

            // Verify that the DatagramPacket was created with the correct data
            verify(socketMock).send(argThat(argument -> {
                String message = new String(argument.getData(), 0, argument.getLength());
                try {
                    return message.equals("Hello, server!") &&
                            argument.getAddress().equals(InetAddress.getByName("localhost")) &&
                            argument.getPort() == 8888;
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}