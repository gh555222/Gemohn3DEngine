import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.stream.Collectors;
public class tiStage2 {
	public static String[] precalcf(int mul){
		return precalcf((float)mul);
	}
	public static String[] precalcf(float mul){
		String[] rv = new String[5];
		//rv[0]="cvp "+(-1)+" "+(1/mul)+" 0\n";
		rv[0]="cvp "+(1/mul)+" "+(0)+" "+(-1)+"\n";
		float m = 1f/((float)mul);
		rv[1]="cvm "+m/2f+" "+m/2f+" "+m/2f+"\n";
		rv[2]="cvp 0 0 "+m+"\n";
		rv[3]="quad3\n";
		rv[4]="cvp "+(-0.5f-1f/mul/2f)+" "+(0.5f+(1f/mul)/2f)+" "+(0.5-1/mul/2)+"\n";
		return rv;
	}
   public static void main(String argv[])throws Exception {
      //FileWriter writer = new FileWriter("./pixel_commands.txt");
      FileWriter writer = new FileWriter("./x0y0z0_bottomQuadCobblestone.txt");
	  String pc[] = precalcf(16);
	  String crlf=pc[0];//"cvp -16 1 0\n"; //end-of-pixel-row
	  String mul=pc[1];//"cvm 1 1 1\n"; //base multiplier for texture size
	  String incr=pc[2];//"cvp 1 0 0\n"; //incrmental pixel position change (intra-row)
	  String quadt=pc[3];//"quad1\n"; //quad type string (which side of cube)
      FileReader fr= new FileReader("./pixel_values.txt");
	  BufferedReader br = new BufferedReader(fr);
	  String[] lines;// = br.lines().collect(Collectors.joining(System.lineSeparator()));
	  lines=br.lines().collect(Collectors.joining(System.lineSeparator())).split("\n");
	  //System.out.println(lines);
	  int lastLine=0;
	  writer.append("reset\n");
	  writer.append(mul);
	  //writer.append("cvp -0.5 0.53125 0.46875\n");
	  writer.append(pc[4]);
	  for(int i=0;i<lines.length;i++){
	  	String[] args = lines[i].split(":");
		//writer.append(quadt);
		//writer.append(incr);
		if(Integer.parseInt(args[0])<lastLine){
		//if(Integer.parseInt(lines[0])<lastLine){
			writer.append(crlf);
			writer.append(incr);
		}else{
			writer.append(incr);
		}
		lastLine=Integer.parseInt(args[0]);
		writer.append(
			"color "+(Float.parseFloat(args[2])/255.f)+
			" "+(Float.parseFloat(args[3])/255.f)+
			" "+(Float.parseFloat(args[4])/255.f)+"\n"
		);
		writer.append(quadt);
	  }
	  writer.flush();
	  writer.close();
			/*
			writer.append(x+":");
			writer.append(y+":");
            writer.append(red+":");
            writer.append(green+":");
            writer.append(blue+"");
			*/
      System.out.println("RGB values at each pixel are stored in the specified file");
   }
}
