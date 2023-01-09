package com;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.communication.ClientSideCommunication;
import com.communication.PlayerID;
public class GameClient {
    GameWindow gameWindow = new GameWindow();
    PlayerID player;
    public static void main(String args[]) throws Exception {
        GameClient client = new GameClient();
        client.start();
    }

    private void start() throws Exception {
        // Get a selector 
        Selector selector = Selector.open(); 

        // Create and configure a client socket channel
        try (SocketChannel channel = SocketChannel.open()) { 
            channel.configureBlocking(false); 
            channel.connect(new InetSocketAddress(InetAddress.getByName("localhost"), 3000)); 

            // Register the channel for connect, read and write operations 
            int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE; 
            
            channel.register(selector, operations); 

            while (true) { 
                if (selector.select() > 0) { 
                    boolean doneStatus = ClientSideCommunication.process(selector.selectedKeys()); 
                    if (doneStatus) { 
                        
                        break; 
                    } 
                    gameWindow.repaint();
                    Thread.sleep(1000);
                } 
            } 
        } 
    }

   
}
