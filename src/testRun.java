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
        Random numGen = new Random();
        Color water = new Color(0, 0, 255);
        Color grass = new Color(0, 255, 0);
        Color scary = new Color(255, 0, 0);

        try{
            BufferedImage testNoise = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
            for(int y = 0; y < 100; y++){
                for(int x = 0; x < 100; x++){
                    double noise = (SimplexNoise.noise2D(x, y) + 1) / 2;
                    double randomNoise = (SimplexNoise.noise2D(numGen.nextInt(1000), numGen.nextInt(1000)) + 1) / numGen.nextInt(6);

                    noise -= randomNoise;
                    //System.out.println(noise);
//                    if(noise < .333){
//                        testNoise.setRGB(x, y, water.getRGB());
//                    }else if(noise > .333 && noise < .666){
//                        testNoise.setRGB(x, y, grass.getRGB());
//                    }else{
//                        testNoise.setRGB(x, y, scary.getRGB());
//                    }
                    testNoise.setRGB(x, y,(int)(noise * 1000000000));
                }
            }
            ImageIO.write(testNoise, "png", new File("C:/Users/Eric/Desktop/AdvanceWarsClone/heightmapnoise.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        //BasicGUI gui = new BasicGUI();
    }
}
