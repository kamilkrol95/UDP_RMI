package UDP_Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static DatagramSocket aSocket;
    private static HashMap<String,Client> uList = new HashMap<>();

    public Server(int port) {
        try {
            aSocket = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE , null , ex);
        }
    }

    private static void send(String message, Client client) {
        DatagramPacket reply = new DatagramPacket(message.getBytes(), message.length(), client.getAddr(), client.getPort());
        try {
            aSocket.send(reply);
        } catch (IOException ex) {
            String msg = " Send: " + message + " NOT COMPLITED! ";
            Logger.getLogger(Server.class.getName()).log(Level.INFO, msg, ex);
        }
    }

    public static void register(String name, InetAddress addr, int port) { //String msg){
        Client cl = new Client(addr,port);
        String ret;
        if(!uList.containsKey(name)) {
            uList.put(name,cl);
            ret = "+|"+name+"|OK|";
        }
        else
            ret = "FAILED:\n+|"+name+"|ALREADY EXIST|";
        Logger.getLogger(Server.class.getName()).log(Level.INFO, ret);
        send(ret,cl);
    }

    public static void unregister(String name){
        Client cl = uList.remove(name);
        String ret;
        if(cl != null) {
            ret = "+|"+name+"|OK|";
            send(ret,cl);
        }
        else
            ret = "FAILED:\n+|"+name+"|DON'T EXIST|";
        send(ret,cl);
    }

    public static void communicate(String name, String text) {
        Client cl = uList.get(name);
        String ret;
        if(cl != null) {
            ret = "!|"+name+"|"+text;
            send(ret,cl);
        }
    }

    public static void inform(){
        Iterator it = uList.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry mp = (Map.Entry)it.next();
            System.out.println("User: " + mp.getKey() + ", Message: " + mp.getValue());
            it.remove();
        }
    }

    public static void decode(DatagramPacket p) {
        String message = new String(p.getData(), 0, p.getLength());
        String[] parts = message.split("[|]");
        switch(parts[0]) {
            case "+":
                System.out.println("Otrzymalem: "+ message);
                register(parts[1],p.getAddress(),p.getPort());
                break;

            case "-":
                System.out.println("Otrzymalem: "+ message);
                unregister(parts[1]);
                break;

            case "?":
                System.out.println("Otrzymalem: "+ message);
                inform();
                break;

            case "!":
                System.out.println("Otrzymalem: "+ message);
                communicate(parts[1],parts[2]);
                break;

            default:
                System.out.println("B��dne ��danie klienta!");
                break;
        }
    }

    private static void loop() throws IOException{

        aSocket = new DatagramSocket(9876);
        byte[] buffer = new byte[1024];
        while(true){
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            decode(request);
        }
    }

    public static void main(String[] args){
        try{
            loop();
        } catch (SocketException ex){
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE , null , ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}
