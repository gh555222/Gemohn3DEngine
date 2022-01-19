package porting20211219;
import porting20211219.StartVoxel;
import java.util.ArrayList;

enum ComplexVoxCmd{
	quad1, quad2, quad3, quad4, quad5, quad6,
	color1, color2, color3, color4, color5, color6,
	cvp, cvm, 
	no_op,
}
public class ComplexVoxel{
	ComplexVoxCmd[] cmds;
	float[][] ext;
	int size;
	int nextdat=0;
	String dump;
	public ComplexVoxel(){
	}
	public ComplexVoxel(int size1){
		//pts7=new float[size1][7];
		cmds=new ComplexVoxCmd[size1];
		ext=new float[size1][];
		size=size1;
	}
	public String dump(){
		int i=0;
		dump="";
		for(;i<size;i++){
			dump=dump+cmds[i].name()+" ";
			System.out.println("i:"+i);
			if(ext.length>i){
				System.out.println(ext[i].length);
				for(int i2=0;i2<ext[i].length;i2++){
					System.out.println("i2:"+i2);
					dump=dump+ext[i][i2]+" ";
				}
			}
			dump=dump+"\n";
		}
		return dump;
	}
}
