package udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Rfc865UdpClient {
    public static final String HOSTNAME = "172.21.150.53";
    public static final int PORT = 17; //Quote Of The Day PORT
    //public static final String MSG_SUBMIT = "Raymond, TE1, 192.168.56.1";

    public static void main(String[] argv) throws IOException {
        Scanner sc = new Scanner(System.in);

        // 1. Open UDP socket
        DatagramSocket clientSocket = new DatagramSocket(); //Create an empty UDP socket

        InetAddress ipAddr = InetAddress.getByName(HOSTNAME); //get IP and do some processing if needed
        if (HOSTNAME == null || HOSTNAME.length() == 0)
            ipAddr = InetAddress.getLocalHost();

        //prepare the buffer
        byte[] tx_buf = new byte[1024];
        byte[] rx_buf = new byte[1024];

        int opt; //Option
        while (true) {
            // 2. Send UDP request to server
            System.out.print("Say something to the server: ");
            String sentence = sc.nextLine();
            tx_buf = sentence.getBytes(); //get the string to bytes to send over socket
            DatagramPacket txPacket = new DatagramPacket(tx_buf,tx_buf.length,ipAddr,PORT); //Set IP and Port number
            clientSocket.send(txPacket);

            if (sentence.contentEquals("Test"))
            {
                break;
            }

            // 3. Receive UDP reply from server
            DatagramPacket rxPacket = new DatagramPacket(rx_buf,rx_buf.length);
            clientSocket.receive(rxPacket);
            //important to work with dirty buffer
            String reply = new String(rxPacket.getData(),0,rxPacket.getLength());
            System.out.println("Server says: " + reply);
            // Server Say: Wise men talk because they have something to say; fools, because they have to say something - Plato.

            //some optional stuff
            System.out.println("Continue? ");
            opt = sc.nextInt();
            sc.nextLine();
            if (opt == 0)
                break;
        }
        System.out.println("Closing Connection.");
        clientSocket.close();
    }
}
