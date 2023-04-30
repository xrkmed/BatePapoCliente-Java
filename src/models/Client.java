package models;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

import visual.ChatSample;

public class Client {

    private String username;
    private final ChatSample chat;
    private final Queue<Thread> queueMessages = new LinkedList<Thread>();
    private Boolean stopShowingNewMessages = false;


    public Client(){
        chat = new ChatSample();
        chat.setVisible(true);
        getChat().getChatText().requestFocus();
        stopShowingNewMessages = false;
    }

    public Client(String username){
        this();
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public ChatSample getChat(){
        return chat;
    }

    public void setStopShowingNewMessages(Boolean status){
        stopShowingNewMessages = status;

        if(!stopShowingNewMessages){
            while(!queueMessages.isEmpty()){
                queueMessages.poll().start();
            }
        }
    }

    public void parsePacket(Message message) throws IllegalArgumentException{
        int packetId = message.getPacketId();
       
        switch(packetId){
            case 1: {
                if(stopShowingNewMessages){
                    queueMessages.add(new Thread(new Runnable(){
                        @Override
                        public void run(){
                            getChat().receiveMessage(message);
                        }
                    }));
                }else{
                    getChat().receiveMessage(message);
                }
                

                break;
            }
            case 2: {
                stopShowingNewMessages = true;
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        getChat().receiveHistorico(message);
                    }
                }).start();
                break;
            }
            case 3: {
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        getChat().receiveUsers(message);
                    }
                }).start();
                break;
            }
            default: {
                throw new IllegalArgumentException("> Protocolo recebido pelo servidor com o ID inválido: " + packetId + "\n\nAtualize o seu cliente e tente novamente.");
            }
        }
    }

    
    
}
