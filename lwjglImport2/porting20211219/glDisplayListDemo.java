package porting20211219;
//https://stackoverflow.com/questions/8860382/basic-lwjgl-triangle-w-opengl

//import porting20211219.*;
//import default1.SimplexNoise_octave;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Scanner;
import java.io.Console;

//import com.fasterxml.jackson.core.*;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Collectors;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import java.io.*;

import java.lang.Math;
import java.util.ArrayList;

import java.nio.IntBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.input.*;
import org.lwjgl.util.glu.*;

import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.ARBVertexBufferObject;

/*
Vim-friendly table of contents

to navigate to the section titled above
stick cursor on line below desired section
and type `yy:@"'

default key bindings
/Rt2JqbCUHa8nn

map drawing function
/Rt2JqbCUHa82

default map initialization function
/Rt2JqbCUHa2

*/
public class glDisplayListDemo {
	void WFglColor3fv(float[] a){
		GL11.glColor3f(a[0],a[1],a[2]);
	}
	void WFglVertex3fv(float[] a){
		GL11.glVertex3f(a[0],a[1],a[2]);
	}
	public void pullCmd(){
		System.out.println("pullCmd");
		Console c = System.console();
		String[] argv = c.readLine().split(" ");
		int aintv[] = new int[argv.length];
		for(int i=0;i<argv.length;i++){
			System.out.println("pullCmd: trigger: "+argv[0]);
			//cmd format:
			// "cmdedit" <vox num> <cmd num> <cmd name> <ext data> <> <> <> ...
			//
			if(0==argv[0].compareTo("cmdedit")){
				try{
					aintv[i] = Integer.parseInt(argv[i]);
				}catch(NumberFormatException e){
					aintv[i]=-1;
				}
				//System.out.println(aintv[i]);
			}else if(0==argv[0].compareTo("load")){
				System.out.println("pullCmd: load");
				try{
					FileReader fr = new FileReader("tmp");
					BufferedReader br = new BufferedReader(fr);
					//myStepBack.cmdline=br.lines().collect(Collectors.joining(System.lineSeparator()));
					br.close();
				}catch(Exception e){
					System.out.println("pullCmd: load: Warning, exception raised.");
					e.printStackTrace();
				}
			}else if(0==argv[0].compareTo("save")){
				System.out.println("pullCmd: save");
				try{
					FileWriter fw = new FileWriter("tmp", true);
					BufferedWriter bw = new BufferedWriter(fw);
					//bw.write(myStepBack.cmdline);
					bw.close();
				}catch(Exception e){
					System.out.println("pullCmd: save: Warning, exception raised.");
					e.printStackTrace();
				}
			}else if(0==argv[0].compareTo("load")){
				
			}
		}
		if(0==argv[0].compareTo("cmdedit")){
		}
		try{
			Thread.sleep(5);
		}catch(InterruptedException e){
		}
	}
	public void offer(double deg, double mul){
		double offx=Math.cos(deg*3.141/180.0);
		double offy=Math.sin(deg*3.141/180.0);
		transx-=offx*mul;
		transz-=offy*mul;
	}
	public static float rotate_x=20, rotate_y=0;
	public static float transx=0,transy=0,transz=0;
	public static int w=800,h=600;
	//Rt2JqbCUHa8nn
	public double moverate=1.0;
	public void keydown_defaults(String key){
		if(new String(key).equals("A")){
			System.out.println("Onward!");
			//transz+=1;
			offer(rotate_y,moverate);
		}
		if(new String(key).equals("D")){
			//transz-=1;
			offer(rotate_y,-1*moverate);
		}
		if(new String(key).equals("W")){
			//transx+=1;
			offer(rotate_y+90,moverate);
		}
		if(new String(key).equals("S")){
			//transx-=1;
			offer(rotate_y+90,-1*moverate);
		}
		if(new String(key).equals("F")){
			rotate_y-=moverate;
		}
		if(new String(key).equals("H")){
			rotate_y+=moverate;
		}
		if(new String(key).equals("T")){
			rotate_x+=moverate;
		}
		if(new String(key).equals("G")){
			rotate_x-=moverate;
		}
		if(new String(key).equals("M")){
			//note bottom-left is 0,0
			System.out.println("mouse:"+Mouse.getX()+" "+Mouse.getY());
			System.out.println("pos:"+transx+" "+transy+" "+transz);
		}
		if(new String(key).equals("Q")){
			transy+=moverate;
		}
		if(new String(key).equals("E")){
			transy-=moverate;
		}
		if(new String(key).equals("Z")){
			moverate=moverate*2.0;
		}
		if(new String(key).equals("X")){
			moverate=moverate/2.0;
		}
		if(new String(key).equals("P")){
			pullCmd();
		}
	}
	//Rt2JqbCUHa82
	int drawMap_glBigDispList;
	boolean drawMap_pregen=false;
	public boolean overlay2d_dpComplete = false;
	int vertices = 4;
	int vbo_vertex_handle;
	int vbo_color_handle;

	int vertex_size = 3; // X, Y, Z,
	int color_size = 3; // R, G, B,

	FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
	public void overlay2d(){
		//glList_start(0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// Reset the coordinate system to center of screen
		GL11.glLoadIdentity();
		Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		//System.out.println(cal1.getTimeInMillis());
		float timeoff = ((float)(cal1.getTimeInMillis()/100%20))/1000.0f;
		// Place the viewpoint
		GLU.gluLookAt(
			0f, 0f, 1.25f,   // eye position 
			0f, 0f, 0f,    // target to look at (the origin)
			0f, 1f, 0f);   // which way is up (Y axis)
		// draw a triangle centered around 0,0,0
		if(false==overlay2d_dpComplete){
			vertex_data.put(new float[] { -0.1f, -0.1f, 0f, });
			vertex_data.put(new float[] { 0.1f, -0.1f, 0f, });
			vertex_data.put(new float[] { 0.1f, 0.1f, 0f, });
			vertex_data.put(new float[] { -0.1f, 0.1f, 0f, });
			vertex_data.flip();

			FloatBuffer color_data = BufferUtils.createFloatBuffer(vertices * color_size);
			color_data.put(new float[] { 1f, 0f, 0f, });
			color_data.put(new float[] { 0f, 1f, 0f, });
			color_data.put(new float[] { 0f, 0f, 1f, });
			color_data.put(new float[] { 1f, 1f, 1f, });
			color_data.flip();

			vbo_vertex_handle = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertex_data, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			vbo_color_handle = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color_data, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			//render
			/*
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL11.glVertexPointer(vertex_size, GL11.GL_FLOAT, 0, 0l);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
			GL11.glColorPointer(color_size, GL11.GL_FLOAT, 0, 0l);

			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices);

			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
			*/
			//glList_start(0);
			//glList_start(0, 0);
			/*
			int size = 9, i=0;

			IntBuffer lists = BufferUtils.createIntBuffer(size);
			int compiledList;
			compiledList = GL11.glGenLists(size);
			GL11.glNewList(compiledList+i, GL11.GL_COMPILE);

			GL11.glBegin(GL11.GL_TRIANGLES);           // draw triangles
				GL11.glVertex3f( 0.0f, .010f+timeoff, 0.0f);         // Top
				GL11.glVertex3f(-.010f,-.010f, 0.0f);         // Bottom Left
				GL11.glVertex3f( .010f,-.010f, 0.0f);         // Bottom Right
			GL11.glEnd(); // done
			//glList_end(0);
			
			
			GL11.glEndList();
			lists.flip();
			GL11.glListBase(compiledList);
			GL11.glCallLists(lists);
			
			*/
			overlay2d_dpComplete=true;
			//GL11.glCallLists(DisplayLists.lists[0]);
		}else{
			System.out.println("GL11.glCallLists(DisplayLists.lists[%entry%]);");
			//GL11.glCallLists(DisplayLists.compiledList[0]);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
			GL11.glVertexPointer(vertex_size, GL11.GL_FLOAT, 0, 0l);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
			GL11.glColorPointer(color_size, GL11.GL_FLOAT, 0, 0l);

			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

			GL11.glDrawArrays(GL11.GL_QUADS, 0, vertices);

			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		}
	}
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(w, h));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(1==1){

	GL11.glClearColor( 0.25f, 0, 0, 1 );
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	//overlay2d();
    GL11.glEnable( GL11.GL_DEPTH_TEST );

    GL11.glMatrixMode( GL11.GL_PROJECTION );
    GL11.glLoadIdentity();
    //int w = 800;//glutGet( GLUT_WINDOW_WIDTH );
    //int h = 600;//glutGet( GLUT_WINDOW_HEIGHT );
    GLU.gluPerspective( 60, w / h, 0.5f, 10000 );

    GL11.glMatrixMode( GL11.GL_MODELVIEW );
    GL11.glLoadIdentity();
    GLU.gluLookAt
        (
                        0,0,0.5f,
                        0,0,1,
                        0,1,0
        );

    GL11.glRotatef( rotate_x, 1.0f, 0.0f, 0.0f );
    GL11.glRotatef( rotate_y, 0.0f, 1.0f, 0.0f );
	//fastBox(1.0f, 1.0f, 1.0f, 4.0f);
	//overlay2d();
	//fastBox(1.0f, 1.0f, 1.0f, 4.0f);
    GL11.glTranslatef( transx,transy,transz );
	//fastBox(0.2f, 0.2f, 0.2f, 8.0f);
	//drawMap();
	int[][][] texarray = new int[2][2][4];//{
	
	overlay2d();
	//transx=transx+0.001f;
	//overlay2d();
		 Display.update();
			while(Keyboard.next()){
				Keyboard.enableRepeatEvents(true);
				String keystr = Keyboard.getKeyName(Keyboard.getEventKey());
				if(Keyboard.getEventKeyState()){
					//key down
					System.out.println(keystr);
					keydown_defaults(keystr);
				}else{
					//key up
				}
			}
		 }

	}
	/*
	//Rt2JqbCUHa2
	*/
    public static void main(String args[]) {
        glDisplayListDemo ct = new glDisplayListDemo();
		//ct.initStartingMap();
        ct.start();
    }

}
