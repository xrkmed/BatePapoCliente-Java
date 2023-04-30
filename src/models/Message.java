package models;

import java.util.Queue;

import controllers.Packet;

public class Message {
    private static Integer mIdCounter = 0;
    private final int mId;
    private final int mPacketId;
    private final Queue<Packet> mPackets;

    public Message(int mPacketId, Queue<Packet> mPackets){
        Message.mIdCounter++;
        this.mId = Message.mIdCounter;
        this.mPacketId = mPacketId;
        this.mPackets = mPackets;
    }

    public int getPacketId() {
        return mPacketId;
    }

    public Packet getPacket(){
        return mPackets.poll();
    }

    public int getPacketCount(){
        return mPackets.size();
    }
}
