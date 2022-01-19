import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
//import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.lang.Math;

public class TextureInterpreter3{
	//public static int outsource1(BufferedImage img, int a, int b){
	public static Color outsource1(BufferedImage img, int a, int b){
		//return new Color(img.getRGB(a, b), true).getRed();
		//new Color(img.getRGB(a, b), true).getRed();
		Color tmp = new Color(img.getRGB(a, b), true);
		//img.getRGB(a, b);
		//tmp.getRed();
		//return 0;
		return tmp;
	}
	public static void main(String[] argv){
		try{
			BufferedImage image = javax.imageio.ImageIO.read(new File("./input.png"));
			byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			//System.out.println(Arrays.toString(pixels));
			float width = (float)Math.sqrt(pixels.length/3);
			int a[][][] = new int[(int)width][(int)width][3];
			final int length = a.length-2;
			System.out.println("cvpr");
			//System.out.println("cvm "+(1.f/(width*2))+" 0 0");
			//System.out.println("cvp -"+(1.f/(width*2))+" -"+(1.f/(width*2))+" "+(1.f/(width*2)));
			System.out.println("cvm "+(1.f/(length*2))+" 0 0");
			//System.out.println("cvp -"+(1.f/(width*2))+" -"+(1.f/(width*2))+" "+(1.f/(width*2)));
			System.out.println("//cvp 1 1 2");
			//final int length = (int)width+2;
			//System.out.println("length:"+length);
			for(int x=0;x<length;x++){
				//for(int y=0;y<a[x].length;y++){
				for(int y=0;y<length;y++){
					System.out.println(x+" "+y);
					
					//a[x][y][0]=new Color(image.getRGB(x, y), true).getRed();
					a[x][y][0]=outsource1(image,x,y).getRed();
					//outsource1(image,x,y);
					a[x][y][1]=outsource1(image,x,y).getGreen();
					a[x][y][2]=outsource1(image,x,y).getBlue();
					//a[x][y][1]=new Color(image.getRGB(x, y), true).getGreen();
					//a[x][y][2]=new Color(image.getRGB(x, y), true).getBlue();
					
					
					float r,g,b;
					r=((float)a[x][y][0])/256.0f;
					g=((float)a[x][y][1])/256.0f;
					b=((float)a[x][y][2])/256.0f;
					
					//r=128.f/((float)a[x][y][0]);
					//g=128.f/((float)a[x][y][1]);
					//b=128.f/((float)a[x][y][2]);
					
					if(r==Float.POSITIVE_INFINITY){
						r=0;
					}
					if(g==Float.POSITIVE_INFINITY){
						g=0;
					}
					if(b==Float.POSITIVE_INFINITY){
						b=0;
					}
					
					
					//System.out.println("cvp "+(1/width*0.5)+" 0 0");
					System.out.println("cvp "+(1/length)+" 0 0");
					System.out.println("color "+r+" "+g+" "+b);
					System.out.println("quad1");
					
					//System.out.println("end:"+x+" "+y);
				}
				//System.out.println("cvp -1 "+(1/length)+" 0 0");
				//System.out.println("cvp -1 "+(1/length)+" 0 0");
				//System.out.println("cvp -1 "+(1/width)+" 0 0");
				//System.out.println("cvp "+(-1+(1f/width*2.5f))+" "+(1f/width)+" 0 0");
				System.out.println("cvp "+(-1+(1f/length*2.5f))+" "+(1f/width)+" 0 0");
				//System.out.println(x);
			}
			/*
			for(int x=0;x<width;x++){
				for(int y=0;y<width;y++){
					float r,g,b;
					r=128.f/(pixels[x*3+0]+256);
					g=128.f/(pixels[x*3+1]+256);
					b=128.f/(pixels[x*3+2]+256);
					if(r==Float.POSITIVE_INFINITY){
						r=0;
					}
					if(g==Float.POSITIVE_INFINITY){
						g=0;
					}
					if(b==Float.POSITIVE_INFINITY){
						b=0;
					}
					//System.out.println("cvp "+x*(1/width)+" "+y*(1/width)+" 0");
					System.out.println("cvp "+(1/width)+" 0 0");
					System.out.println("color "+r+" "+g+" "+b);
					System.out.println("quad1");
				}
				System.out.println("cvp -1 "+(1/width)+" 0 0");
			}
			for(int i=0;i<pixels.length;i=i+3){
				System.out.println(pixels[i+0]+" "+pixels[i+1]+" "+pixels[i+2]);
			}
			*/
		}catch(Exception e){
		}
	}
}
