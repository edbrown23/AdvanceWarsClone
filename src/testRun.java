import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class testRun {
    public static void main(String[] args){
        Color water = new Color(0, 0, 255);
        Color grass = new Color(0, 255, 0);
        Color mountains = new Color(130, 100, 25);
        
            float[][] finalNoiseArray = PerlinNoise.GeneratePerlinNoise(100, 100, 8);
            BufferedImage perlinNoiseImage = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
            for(int y = 0; y < 100; y++){
                for(int x = 0; x < 100; x++){
                    int grey = (int)(255 * finalNoiseArray[x][y]);
                    Color greyColor = new Color(255, grey, grey, grey);
                    if(grey < 100){
                        perlinNoiseImage.setRGB(x, y, water.getRGB());
                    }else if(grey > 100 && grey < 180){
                        perlinNoiseImage.setRGB(x, y, grass.getRGB());
                    }else{
                        perlinNoiseImage.setRGB(x, y, mountains.getRGB());
                    }
                    //perlinNoiseImage.setRGB(x, y, greyColor.getRGB());
                }
            }
            try{
                ImageIO.write(perlinNoiseImage, "png", new File("C:/Users/Eric/Desktop/AdvanceWarsClone/perlinNoise.png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        
        //BasicGUI gui = new BasicGUI();
    }
}
