package com;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import com.communication.ServerSideCommunication; 

public class GameServer { 
    static boolean isPlayer1Connected = false;
    static String interpretation = "";
    public static void main(String[] args) throws Exception { 
       
        // Get a selector 
        Selector selector = Selector.open(); 

        // Get a server socket channel 
        ServerSocketChannel channel = ServerSocketChannel.open(); 
        channel.configureBlocking(false); 
        channel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), 3000)); 
        channel.register(selector, SelectionKey.OP_ACCEPT ); 
        System.out.println("Server started.");

        while (true) { 
            if (selector.select() <= 0) { 
                continue; 
            } 
            ServerSideCommunication.process(selector.selectedKeys()); 
        } 
    } 

   
} 