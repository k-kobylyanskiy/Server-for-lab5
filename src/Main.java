import java.net.*;
import java.io.*;

class Server {

    public void run() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(10950);
        while (true) {
                byte[] receiveData = new byte[12];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                new Thread(new Responder(serverSocket, receivePacket, receiveData)).start();
        }
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.run();
    }
}
