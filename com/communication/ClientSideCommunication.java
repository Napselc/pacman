package com.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.logic.GameInterpretation;

public class ClientSideCommunication {
    
    private static String playerID;
    public static boolean process(Set<SelectionKey> readySet) throws Exception { 
     
        Iterator<SelectionKey> iterator = readySet.iterator(); 
        while (iterator.hasNext()) { 
            // Get the next ready selection key object 
            SelectionKey key = (SelectionKey) iterator.next(); 

            // Remove the key from the ready key set 
            iterator.remove(); 

            if (key.isConnectable()) { 
                boolean connected = processConnect(key); 
                if (!connected) { 
                    return true; // Exit 
                } 
            } 

            if (key.isReadable()) { 
                String response = readResponse(key); 
                System.out.println("[Server]: " + response); 
                processResonse(response);
            } 

            if (key.isWritable()) {  
                String request = createRequest();
                sendRequest(key,request); 
            } 

        } 

        return false; // Not done yet 
    } 

    private static void processResonse(String response) {
        if(response.startsWith(Commands.PLAYER_ID)){
            playerID = response.substring(10);
            System.out.println("player client Id "+playerID);
        }else if(response.startsWith(Commands.HOLD)){
            System.out.println(response);
        }else{
            // System.out.print(response);
            GameInterpretation.setInterpretation(response);
        }
    }

    private static String createRequest() {
        return Objects.isNull(playerID) ? Commands.GET_PLAYER_ID : Commands.UPDATE+playerID;
    }

    public static boolean processConnect(SelectionKey key) { 
        SocketChannel channel = (SocketChannel) key.channel(); 

        try { 
            // Call the finishConnect() in a 
            // loop as it is non-blocking for your channel 
            while (channel.isConnectionPending()) { 
                channel.finishConnect(); 
            } 
        } catch (IOException e) { 
            // Cancel the channel's registration with the selector 
            key.cancel(); 
            e.printStackTrace(); 
            return false; 
        } 

        return true; 
    } 

    public static String readResponse(SelectionKey key) throws Exception { 
        SocketChannel sChannel = (SocketChannel) key.channel(); 
        ByteBuffer buffer = ByteBuffer.allocate(1024); 
        sChannel.read(buffer); 
        buffer.flip(); 
        Charset charset = Charset.forName("UTF-8"); 
        CharsetDecoder decoder = charset.newDecoder(); 
        CharBuffer charBuffer = decoder.decode(buffer); 
        String msg = charBuffer.toString(); 
        return msg; 
    } 
    
    public static void sendRequest(SelectionKey key, String msg) throws IOException { 
        SocketChannel sChannel = (SocketChannel) key.channel(); 
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes()); 
        sChannel.write(buffer); 
    } 

}
