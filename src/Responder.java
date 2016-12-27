import java.net.*;
import java.io.*;
import java.nio.*;

public class Responder implements Runnable {

    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    byte[] sendData;
    byte[] receiveData;

    public Responder(DatagramSocket socket, DatagramPacket packet, byte[] receiveData) {
        this.serverSocket = socket;
        this.receivePacket = packet;
        this.receiveData = receiveData;
    }

    public void run() {

        float[] point = bytesToFloats(receiveData);
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();

        int sum = (Forma.isInside(point[0], point[1], point[2])) ? 1 : 0;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(output);
        try {
            dataOutput.writeInt(sum);
            dataOutput.close();
            sendData = output.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);

        } catch (Exception e) {

        }
    }

    public static float[] bytesToFloats(byte[] bytes) {
        float[] point = new float[bytes.length / 4];
        ByteBuffer.wrap(bytes).asFloatBuffer().get(point);
        return point;
    }

}