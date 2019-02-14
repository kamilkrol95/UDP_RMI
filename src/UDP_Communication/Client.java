package UDP_Communication;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private InetAddress Addr;
    private int Port;

    public Client(InetAddress addr, int port) {
        Addr = addr;
        Port = port;
    }

    public InetAddress getAddr() {
        return Addr;
    }

    public int getPort() {
        return Port;
    }

    public static void main(String[] args){
        try{
            if(args.length < 2){
                System.out.println("Usage: UDPClient <msg> <server host name>");
                System.exit(-1);
            }
            DatagramSocket aSocket = new DatagramSocket();
            byte[] msg = args[0].getBytes();
            System.out.println("Sent: "+ args[0]);
            InetAddress aHost = InetAddress.getByName(args[1]);
            int serverPort = 9876;
            DatagramPacket request = new DatagramPacket(msg, args[0].length(), aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[1024];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()));
            aSocket.close();


        }
        catch (SocketException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE , null , ex);
        }
        catch (UnknownHostException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE , null , ex);
        }
        catch (IOException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}
