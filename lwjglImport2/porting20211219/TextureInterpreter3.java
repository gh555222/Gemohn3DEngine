import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Arrays;

public class TextureInterpreter3{
	public static void main(String[] argv){
		try{
			BufferedImage image = javax.imageio.ImageIO.read(new File("./input.jpg"));
			byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			System.out.println(Arrays.toString(pixels));
		}catch(Exception e){
		}
	}
}
