import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.stream.Collectors;

//given a tiStage 1 formatted pixel file
//read contents of said file
//and re-write the Colorscript
//comingled within a GLemohn-compatible GL-commands file
//if the input tiStage 1 file is too short, round-robin
//overwrite the remained of the comingled Colorscript

//P.S. the original intention of this program was to make it
//easy for this project's founder to change copyrighted Colorscripts
//(originally Minecraft's cobblestone texture from circa 2016 was used)
//to royalty-free Colorscripts.

public class tiStage_overwriteLoop{
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
	public static void main(String[] argv) throws Exception{
		if(argv.length<1){
			System.err.println("Invalid number of parameters. Please supply 1 parameter containing the path to the desired file to edit the Colorscript of.");
			throw new Exception();
		}else if(argv.length>1){
			System.err.println("Invalid number of parameters. Please supply 1 parameter containing the path to the desired file to edit the Colorscript of.");
			throw new Exception();
		}
		//Colorscript configuration file
		FileReader csin2 = new FileReader("./pixel_values.txt");
		//file to change Colorscript of
		FileReader toparse2 = new FileReader(argv[0]);
		//temporary file, which will be moved to original file Colorscript was modified from
		FileWriter output = new FileWriter("./tisoltmp.txt");
		//reverse polymorphism
		BufferedReader csin = new BufferedReader(csin2);
		BufferedReader toparse = new BufferedReader(toparse2);
		//grab new Colorscript textlines:
		String[] cfgl=csin.lines().collect(Collectors.joining(System.lineSeparator())).split("\n");
		//grab present working configuration file
		String[] cfg=toparse.lines().collect(Collectors.joining(System.lineSeparator())).split("\n");
		System.out.println(cfgl.length+" "+cfg.length);
		//last piece of intermediary data preparation
		//craft new color lines to be used
		String[] clrcmds = new String[cfgl.length];
		for(int i=0;i<cfgl.length;i++){
			String[] args=cfgl[i].split(":");
			clrcmds[i]=
				"color "+(Float.parseFloat(args[2])/255.f)+
				" "+(Float.parseFloat(args[3])/255.f)+
				" "+(Float.parseFloat(args[4])/255.f)+"\n" ;
			//
		}
		//0==cmds[i].compareTo("quad3")
		//the actual dirtywork
		int i2=0; //i2 controls csin2-based color command iteration
		for(int i=0;i<cfg.length;i++){
			System.out.println(i+" "+cfg.length);
			//String[] tmpstr = str.split(":");
			//base
			if(0==cfg[i].split(" ")[0].compareTo("color")){
				output.append(clrcmds[i2]);
			}else{
				output.append(cfg[i]+"\n");
			}
			//output.flush();
			i2=i2+1;
			if(i2>(cfgl.length-1)){
				i2=0;
			}
		}
		output.flush();
		output.close();
	}
}
