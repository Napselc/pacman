package com.images;


import javax.imageio.ImageIO;

import com.Constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProvider{
   
    public static BufferedImage getImage(String name){
        try {
             return ImageIO.read(new File(Constants.PATH, name));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}