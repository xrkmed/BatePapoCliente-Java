package controllers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Message;

public class SignalEntity {

    public static Object convertObject(String objectClass){
        switch(objectClass.toLowerCase().replaceAll("java.lang.", "")){
            case "string":
                return new String();
            case "integer":
                return (int)0;
            case "float":
                return (float)1F;
            case "double":
                return (double)1D;
            case "boolean":
                return false;
            case "long":
                return 1L;
            case "short":
                return (short)1;
            case "byte":
                return (byte)0;
            case "character":
                return 'C';
            default:
                return new String();
        }
    }

    public static String encodeSignal(Message message){
        String packetId = "{packetId@;@ " + message.getPacketId() + "}";
        String packets = "packets: {";
        while(message.getPacketCount() > 0){
            Packet packet = message.getPacket();
            String packetType = packet.getPacketType().getClass().getSimpleName();
            String packetMessage = packet.getString();
            packets += "{" + packetType + "@;@ " + packetMessage + "}";
            if(message.getPacketCount() > 0){
                packets += ", ";
            }
        }
        packets += "}";

        return "{" + packetId + ", " + packets + "}";
    }

    public static Message decodeSignal(String input){
        Integer packetIdValue = null;
        System.out.println(input);

        Pattern pattern = Pattern.compile("\\{([\\w!?\\]\\[-_.+=\\\\@#$%^&*)(]+)@;@([\\w\\s!?\\]\\[-_.+:=\\\\@#$%^&*)(]+)\\}|([\\w!?\\]\\[-_.+=\\\\@#$%^&*)(]+)@;@([\\w\\s!?\\]\\[-_.+=\\\\@#$%^&*)(]+)(?:,\\s*)?"); // {{packetId@;@ 1}, packets: {{String@;@ message}, {Integer@;@ 12}}
        Matcher matcher = pattern.matcher(input);


        Queue<Packet> packets = new LinkedList<>();
        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(1).equals("packetId")) {
                packetIdValue = Integer.parseInt(matcher.group(2).trim());
            } else {
                for(int i = 1; i <= matcher.groupCount(); i++){
                    if(i%2 == 0 && matcher.group(i) != null){
                        Object ob = convertObject(matcher.group(i-1).trim());

                        System.out.println("Key: " + ob.getClass() + " Value: " + matcher.group(i).trim());
                        Packet packet = new Packet(ob, matcher.group(i));
                        packets.add(packet);
                    }
                }
            }
        }

        return new Message(packetIdValue, packets);
    }
    
}
