import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        MulticastSocket server = new MulticastSocket(1234);
        InetAddress group = InetAddress.getByName("234.5.6.7");
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        
        // Bergabung dengan grup multicast menggunakan signature metode baru
        server.joinGroup(new InetSocketAddress(group, 1234), networkInterface);

        boolean infinite = true;

        // Server terus-menerus menerima data dan mencetaknya
        while (infinite) {
            byte[] buf = new byte[1024];
            DatagramPacket data = new DatagramPacket(buf, buf.length);
            server.receive(data);
            String msg = new String(data.getData(), 0, data.getLength()).trim();
            System.out.println(msg);
        }
        
        // Meninggalkan grup multicast sebelum menutup socket
        server.leaveGroup(new InetSocketAddress(group, 1234), networkInterface);
        server.close();
    }
}
