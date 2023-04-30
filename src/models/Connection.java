package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

import controllers.Packet;
import controllers.SignalEntity;

public class Connection implements Runnable {

    private final Client client;
    private final String ip;
    private final int port;
    private Socket mSocket;
    private BufferedReader in;
    private PrintWriter out;
    private long lastPacketSend;

    public Connection(Client client, String ip, int port){
        this.client = client;
        this.ip = ip;
        this.port = port;
        this.lastPacketSend = 0;
    }

    public Client getClient(){
        return this.client;
    }

    public Socket getSocket(){
        return mSocket;
    }

    @Override
    public void run() {
        this.connect();
    }

    public boolean connect()
    {
        try{
            mSocket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            out = new PrintWriter(mSocket.getOutputStream(), true);

            sendFirstPacket();
            return true;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "> Erro ao se conectar: " + e.getMessage());
        }

        return false;
    }

    public void sendFirstPacket(){
        Queue<Packet> firstPacket = new LinkedList<Packet>();
        firstPacket.add(new Packet("String", client.getUsername()));
        out.println(SignalEntity.encodeSignal(new Message(0, firstPacket)));


        //Comecar o dialogo Cliente-Servidor
        try (BufferedReader input = new BufferedReader(new InputStreamReader(getSocket().getInputStream()))) {
            String message;
            while ((message = input.readLine()) != null) {
                this.lastPacketSend = System.currentTimeMillis();

                Message packet = SignalEntity.decodeSignal(message);
				client.parsePacket(packet);
            }
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "> Uma excecao foi gerada: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        Queue<Packet> PacketQueue = new LinkedList<>();
        PacketQueue.add(new Packet("String", getClient().getUsername()));
        PacketQueue.add(new Packet("String", message));
        out.println(SignalEntity.encodeSignal(new Message(1, PacketQueue)));
    }


}
