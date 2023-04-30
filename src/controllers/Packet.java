package controllers;

public class Packet {
    private final Object mPacketType;
    private final Object mPacketMessage;

    public Packet(Object mPacketType, Object mPacketMessage){
        this.mPacketType = mPacketType;
        this.mPacketMessage = mPacketMessage;
    }

    public String getString(){
        return mPacketMessage.toString();
    }

    public int getInt(){
        return Integer.parseInt(mPacketMessage.toString());
    }

    public double getDouble(){
        return Double.parseDouble(mPacketMessage.toString());
    }

    public float getFloat(){
        return Float.parseFloat(mPacketMessage.toString());
    }

    public long getLong(){
        return Long.parseLong(mPacketMessage.toString());
    }

    public boolean getBoolean(){
        return Boolean.parseBoolean(mPacketMessage.toString());
    }

    public char getChar(){
        return mPacketMessage.toString().charAt(0);
    }

    public byte getByte(){
        return Byte.parseByte(mPacketMessage.toString());
    }

    public short getShort(){
        return Short.parseShort(mPacketMessage.toString());
    }

    public Object getPacketType() {
        return mPacketType;
    }
}
