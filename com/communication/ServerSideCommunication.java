package com.communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

import com.logic.GameInterpretation;

public class ServerSideCommunication {
    static int playerCount = 0;
    static GameInterpretation gameInterpretation = new GameInterpretation();
    static String interpretation = "Server interpretaion";

    
    public static void process(Set<SelectionKey> selectionKeySet) throws Exception { 
        
        Iterator<SelectionKey> iterator = selectionKeySet.iterator(); 

        while (iterator.hasNext()) { 
            // Get the next ready selection key object 
            SelectionKey selectionKey = iterator.next(); 

            // Remove the key from the ready key set 
            iterator.remove(); 

            // Process the key according to the operation it is ready for 
            if (selectionKey.isAcceptable()) { 
                processAccept(selectionKey); 
            } 

            if (selectionKey.isReadable()) { 
                String request = readRequest(selectionKey);
                String reponse = generateReponse(request);
                if (request.length() > 0) { 
                    response(selectionKey, reponse); 
                } 
            } 
        } 
    } 

    private static String generateReponse(String request) {
        String response;
        if(request.startsWith(Commands.GET_PLAYER_ID))
        {
            response = Commands.PLAYER_ID+playerCount;
            playerCount++;
        }else { //if(request.startsWith(Commands.UPDATE))
            response = playerCount < 2 ? Commands.HOLD : gameInterpretation.getString();
            // String userInput = request.substring(6);
            // interpretation = interpretation+userInput;
            // response = interpretation;
        }
    
        return response;
    }

    public static void processAccept(SelectionKey key) throws IOException { 
        System.out.println("Acepting connection");
        ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel(); 
        SocketChannel clientChannel = (SocketChannel) serverChannel.accept(); 
        clientChannel.configureBlocking(false); 
        clientChannel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE); 
    } 

    public static String readRequest(SelectionKey key) throws Exception { 
        SocketChannel sChannel = (SocketChannel) key.channel(); 
        ByteBuffer buffer = ByteBuffer.allocate(1024); 
        int bytesCount = sChannel.read(buffer); 
        String msg = ""; 

        if (bytesCount > 0) { 
            buffer.flip(); 
            Charset charset = Charset.forName("UTF-8"); 
            CharsetDecoder decoder = charset.newDecoder(); 
            CharBuffer charBuffer = decoder.decode(buffer); 
            msg = charBuffer.toString(); 
            System.out.println("Received request: " + msg); 
        } 

        return msg; 
    } 

    public static void response(SelectionKey key, String response) throws IOException { 
        SocketChannel sChannel = (SocketChannel) key.channel(); 
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes()); 
        System.out.println("sending reposnse :"+ response);
        sChannel.write(buffer); 
    } 
}
