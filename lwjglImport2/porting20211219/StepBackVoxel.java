package porting20211219;
import porting20211219.StartVoxel;
import java.util.ArrayList;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
//import java.util.ArrayList;
/*
enum ComplexVoxCmd{
	quad1, quad2, quad3, quad4, quad5, quad6,
	color1, color2, color3, color4, color5, color6,
	cvp, cvm, 
	no_op,
}
*/
public class StepBackVoxel{
	String cmdline;
	public class Vbo{
		public int vertices; //number of vertices
		int vertex_handle;
		int color_handle;

		int vertex_size = 3; // X, Y, Z,
		int color_size = 3; // R, G, B,
		FloatBuffer vertex_data;
		FloatBuffer color_data;
		int ttl;
		String filename;
		public Vbo(int vertc){
			this(vertc, "");
		}
		public Vbo(int vertc, String fname){
			vertices=vertc;
			ttl=64;
			filename = fname;
			vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
			color_data = BufferUtils.createFloatBuffer(vertices * color_size);
		}
		public Vbo(){
			this(4);
		}
	}
	Vbo myVbo;
	//static final int quad1=1,quad2=2,quad3=3,quad4=4,quad5=5,quad6=6,cvm=7,cvp=8,no_op=9;
	int size;
	int nextdat=0;
	String dump;
	public StepBackVoxel(){
		this(10);
	}
	public StepBackVoxel(int size1){
		myVbo = new Vbo(size1);
	}
	public StepBackVoxel(int size1, String fname){
		myVbo = new Vbo(size1, fname);
	}
}
