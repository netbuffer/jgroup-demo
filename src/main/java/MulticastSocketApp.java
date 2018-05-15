import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MulticastSocketApp {

    private static int port = 8000;
    private static String address = "228.0.0.4";

    public static void main(String[] args) throws Exception {

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket multicastSocket = null;
        multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(group);
        final MulticastSocket finalMulticastSocket = multicastSocket;
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true){
                        receive(finalMulticastSocket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (true) {
            System.out.println("input:");
            send(multicastSocket, group);
        }
    }

    public static void send(MulticastSocket multicastSocket, InetAddress group) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        byte[] buffer = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length, group, port);
        multicastSocket.send(dp);
    }

    public static void receive(MulticastSocket multicastSocket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        multicastSocket.receive(dp);
        String s = new String(dp.getData(), 0, dp.getLength());
        System.out.println("receive from "+dp.getAddress().getHostAddress()+":" + s);
    }
}