package frameutil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class ImageSizer {

    public ImageIcon overaallResizer(String src, int w, int h) {
        javax.swing.ImageIcon ico1 = new javax.swing.ImageIcon(getClass().getResource(src));
        java.awt.Image imagesrc1 = ico1.getImage();
        javax.swing.ImageIcon newResizedIcon1 = ImageSizer.getScaledImage(imagesrc1, w, h);
        return newResizedIcon1;
    }

    public static ImageIcon getScaledImage(Image imagesrc, int w, int h) {

        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(imagesrc, 0, 0, w, h, null);
        g2.dispose();
        ImageIcon iIconResized = new ImageIcon(resizedImg);
        return iIconResized;
    }
}
