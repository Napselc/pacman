package com;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        GameWindow gameWindow = new GameWindow();
        while(true){
            gameWindow.repaint();
            Thread.sleep(1000);
        }
    }
}
