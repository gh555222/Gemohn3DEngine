import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.stream.Collectors;

public class GenCobblestoneChunk {
    public static void main(String args[]) throws Exception{
		FileWriter writer = new FileWriter("../topQuadCobblestoneFlatChunk.txt");
		//writer.append("test");
		//String[] cmd = new String[]{
		//	"reset\n",
		//	"cvm 0.03125 0.03125 0.03125\n",
		//	"cvp "
		//};
		FileReader fr= new FileReader("../x0y0z0_topQuadCobblestone_copy.txt");
		BufferedReader br = new BufferedReader(fr);
		String[] livefile = br.lines().collect(Collectors.joining(System.lineSeparator())).split("\n");
		for(int x=0;x<16;x++){
			for(int y=0;y<16;y++){
				//writer.append(cmd[0]+cmd[1]+cmd[2]+x+" 0 "+y+"\n");
				//for each line in source file:
				for(int i=0;i<livefile.length;i++){
					//if(i<10){ System.out.println(livefile[i]); }
					if(0==livefile[i].compareTo("%INTRACHUNKPOSITION%")){
						//System.out.println(i+" %INTRACHUNKPOSITION%");
						writer.append("cvp "+x+" 0 "+y+"\n");
					}else{
						writer.append(livefile[i]+"\n");
					}
				}
				//writer.append(livefile);
				//writer.append("cvp 0 0 1");
			}
			//writer.append("cvp 1 0 -16");
		}
		writer.flush();
		writer.close();
    }
}
