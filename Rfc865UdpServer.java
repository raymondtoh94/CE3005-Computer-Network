package udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Rfc865UdpServer {
    public static final int PORT = 17; //Quote Of The Day PORT specify in rfc865.txt

    public static void main(String[] args) throws IOException {
        // 1. Open UDP socket at well-known port
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        byte[] tx_buf = new byte[1024];
        byte[] rx_buf = new byte[1024];

        while (true) {
                // 2. Listen for UDP request from client
                DatagramPacket rxPacket = new DatagramPacket(rx_buf, rx_buf.length);
                serverSocket.receive(rxPacket);
                String sentence = new String(rxPacket.getData(),0,rxPacket.getLength());
                System.out.println("Client says: "+sentence);
                if (sentence.equals("Test") == true ) {
                    System.out.println("Client has sent FIN."); //TCP
                    break;
                }
                else {
                    System.out.println("Length: "+sentence.length());
                }

                // 3. Send UDP reply to client
                InetAddress IPAddress = rxPacket.getAddress();
                int port = rxPacket.getPort();
                Scanner sc = new Scanner(System.in);
                System.out.print("Say something to the client: ");
                String msg = sc.nextLine();
                tx_buf = msg.getBytes();
                DatagramPacket txPacket = new DatagramPacket(tx_buf,tx_buf.length,IPAddress,port);
                serverSocket.send(txPacket);
        }
            serverSocket.close();
    }
}
