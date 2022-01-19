package porting20211219;
//https://stackoverflow.com/questions/8860382/basic-lwjgl-triangle-w-opengl

import porting20211219.*;
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
public class ColoredTriangle {
	FastVoxel map = new FastVoxel();
	 int[][] qquadvals = new int[][]{
		{0,3,2,1}, //z+
		{2,3,7,6}, //x+
		{0,4,7,3}, //y-
		{1,2,6,5}, //y+
		{4,5,6,7}, //z-
		{0,1,5,4}  //x-
	};

	 float[][] ver2 = new float[][]{
		{-1.0f,-1.0f,1.0f},
		{-1.0f,1.0f,1.0f},
		{1.0f,1.0f,1.0f},
		{1.0f,-1.0f,1.0f},
		{-1.0f,-1.0f,-1.0f},
		{-1.0f,1.0f,-1.0f},
		{1.0f,1.0f,-1.0f},
		{1.0f,-1.0f,-1.0f}
	};
	float[][]ver = new float[8][3];
	float[][] color2 = new float[][]{
		{0.0f,0.0f,0.0f},
		{1.0f,0.0f,0.0f},
		{1.0f,1.0f,0.0f},
		{1.0f,1.0f,1.0f},
		{0.0f,0.0f,1.0f},
		{1.0f,0.0f,1.0f},
		{1.0f,1.0f,1.0f},
		{0.0f,1.0f,1.0f}
	};
	float[][] color = new float[][]{
		{0.0f,0.0f,0.0f},
		{1.0f,0.0f,0.0f},
		{1.0f,1.0f,0.0f},
		{1.0f,1.0f,1.0f},
		{0.0f,0.0f,1.0f},
		{1.0f,0.0f,1.0f},
		{1.0f,1.0f,1.0f},
		{0.0f,1.0f,1.0f}
	};
	void WFglColor3fv(float[] a){
		GL11.glColor3f(a[0],a[1],a[2]);
	}
	void WFglVertex3fv(float[] a){
		GL11.glVertex3f(a[0],a[1],a[2]);
	}
	//previously quad_oflib for original WF (wieldfield.com) C standard name
	public void paintVbo(StepBackVoxel.Vbo vbo){
	}
	/*
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.vertex_handle);
		GL11.glVertexPointer(vbo.vertex_size, GL11.GL_FLOAT, 0, 0l);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.color_handle);
		GL11.glColorPointer(vbo.color_size, GL11.GL_FLOAT, 0, 0l);

		GL11.glEnableClientState(GL15.GL_ARRAY_BUFFER);
		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		System.out.println("paintVbo: "+vbo.vertices+" "+vbo.vertex_size+" "+vbo.color_size);
		GL11.glDrawArrays(GL11.GL_QUADS, 0, vbo.vertices/2);

		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		//GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL15.GL_ARRAY_BUFFER);
	}
	*/
	public void addQuadToVbo(StepBackVoxel.Vbo vbo,int a,int b,int c, int d){
	}
	/*
		try{
			addQuadToVbo(vbo,a,b,c,d,true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	public void addQuadToVbo(StepBackVoxel.Vbo vbo,int a,int b,int c, int d, boolean next){
	}/*
		//System.out.println("addQuadToVbo()");
		vbo.vertices=vbo.vertices+4; //add a quad's worth of vertices
		vbo.vertex_data.put(new float[] {ver[a][0], ver[a][1], ver[a][2]} );	
		vbo.vertex_data.put(new float[] {ver[b][0], ver[b][1], ver[b][2]} );	
		vbo.vertex_data.put(new float[] {ver[c][0], ver[c][1], ver[c][2]} );	
		vbo.vertex_data.put(new float[] {ver[d][0], ver[d][1], ver[d][2]} );	
		
		vbo.color_data.put(new float[] {color[a][0], color[a][1], color[a][2]} );	
		vbo.color_data.put(new float[] {color[b][0], color[b][1], color[b][2]} );	
		vbo.color_data.put(new float[] {color[c][0], color[c][1], color[c][2]} );	
		vbo.color_data.put(new float[] {color[d][0], color[d][1], color[d][2]} );	
		
		vbo.color_data.flip();
		//
		vbo.vertex_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.vertex_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vbo.vertex_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		vbo.color_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.color_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vbo.color_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

	}
	*/
	public void quad(int a,int b,int c,int d)
	{
		//System.out.println(GL11.GL_TRIANGLES);
		GL11.glBegin(GL11.GL_QUADS);
		WFglColor3fv(color[a]);
		WFglVertex3fv(ver[a]);

		WFglColor3fv(color[b]);
		WFglVertex3fv(ver[b]);

		WFglColor3fv(color[c]);
		WFglVertex3fv(ver[c]);

		WFglColor3fv(color[d]);
		WFglVertex3fv(ver[d]);
		GL11.glEnd();
	}
	public void quad2(int a,int b,int c,int d, float[][] color1, float[][]ver1)
	{
		GL11.glBegin(GL11.GL_QUADS);
		WFglColor3fv(color1[a]);
		WFglVertex3fv(ver1[a]);

		WFglColor3fv(color1[b]);
		WFglVertex3fv(ver1[b]);

		WFglColor3fv(color1[c]);
		WFglVertex3fv(ver1[c]);

		WFglColor3fv(color1[d]);
		WFglVertex3fv(ver1[d]);
		GL11.glEnd();
	}
	public StepBackVoxel myStepBack = new StepBackVoxel();
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
					myStepBack.cmdline=br.lines().collect(Collectors.joining(System.lineSeparator()));
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
					bw.write(myStepBack.cmdline);
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
	public void drawMap(){
		//testGen();
		//drawMap(myStepBack.cmdline, MODE_EXEC_DEFAULT);
		drawMap(myStepBack.cmdline);
	}
	public void drawMap(String cmdline){
		testGen();
		drawMap(cmdline, MODE_EXEC_DEFAULT);
	}
	final int MODE_EXEC_DEFAULT=0;
	final int MODE_EXEC_NORM=1;
	final int MODE_EXEC_CACHE_VBO=2;
	ArrayList<StepBackVoxel> extfcache = new ArrayList<StepBackVoxel>();
	public void drawMap(String cmdline, int mode_arg)
	{
		for(int i=0;i<8;i++){
			for(int i2=0;i2<3;i2++){
				ver[i][i2]=ver2[i][i2];
			}
		}
		myStepBack.nextdat=0;
		//String[] cmds = cmdline.split("\n");
		String[] cmds = myStepBack.cmdline.split("\n");
		int ext_mode;
		if(mode_arg==MODE_EXEC_DEFAULT){
			ext_mode = MODE_EXEC_NORM;
		}else{
			ext_mode = mode_arg;
		}
		for(int i=0;i<cmds.length;i++){
			String[] cmdargv = cmds[i].split(" ");
			//System.out.println(cmds[i]);
			if(0==cmds[i].compareTo("quad1")){
				if(ext_mode==MODE_EXEC_NORM){ quad(0,3,2,1); }
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,0,3,2,1);
				}
			}else if(0==cmds[i].compareTo("quad2")){
				if(ext_mode==MODE_EXEC_NORM){ quad(2,3,7,6);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,2,3,7,6);
				}
			}else if(0==cmds[i].compareTo("quad3")){
				if(ext_mode==MODE_EXEC_NORM){ quad(0,4,7,3);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,0,4,7,3);
				}
			}else if(0==cmds[i].compareTo("quad4")){
				if(ext_mode==MODE_EXEC_NORM){ quad(1,2,6,5);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,1,2,6,5);
				}
			}else if(0==cmds[i].compareTo("quad5")){
				if(ext_mode==MODE_EXEC_NORM){ quad(4,5,6,7);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,4,5,6,7);
				}
			}else if(0==cmds[i].compareTo("quad6")){
				if(ext_mode==MODE_EXEC_NORM){ quad(0,1,5,4);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					addQuadToVbo(myStepBack.myVbo,0,1,5,4);
				}
			}else if(0==cmdargv[0].compareTo("cvp")){
				//System.out.println(Float.valueOf(cmdargv[1])+" "+Float.valueOf(cmdargv[2])+" "+Float.valueOf(cmdargv[3]));
				changeVertPos(Float.valueOf(cmdargv[1]), Float.valueOf(cmdargv[2]), Float.valueOf(cmdargv[3]));
			}else if(0==cmdargv[0].compareTo("extf")){
				//System.out.println(cmdargv[1]);
				testGen(cmdargv[1]);
				drawMap(myStepBack.cmdline, MODE_EXEC_DEFAULT);
			}else if(0==cmdargv[0].compareTo("extfvbo")){
				//iterate through cache before evaluating if we must 
				//read the file *for real*, or call a pre-existing VBO
				//from cache
				int efvi=0;
				if(extfcache.size()==0){
					testGen(cmdargv[1]);
					drawMap(myStepBack.cmdline, MODE_EXEC_CACHE_VBO);
					myStepBack.myVbo.filename=cmdargv[0];
					extfcache.add(myStepBack);
					//paintVbo(extfcache.get(efvi).myVbo);
					paintVbo(myStepBack.myVbo);
				}
				for(;1==1;efvi=efvi+1){
					//read a cached record and evaluate if the record is for our
					//file (and if its not, just goto the next one using the for-loop above, again)
					if(efvi<extfcache.size()){
						
					}else{
						extfcache.add(myStepBack);
						paintVbo(myStepBack.myVbo);
						break;
					}
					if(0==extfcache.get(efvi).myVbo.filename.compareTo(cmdargv[1])){
						//record inquired about is in fact for our present extfvbo command
						System.out.println("extfvbo: ttl: "+extfcache.get(efvi).myVbo.ttl);
						if(extfcache.get(efvi).myVbo.ttl==0){
							//load file from disk, and invoke new instance of present function
							//to process commands within said file
							extfcache.remove(efvi);
							testGen(cmdargv[1]);
							drawMap(myStepBack.cmdline, MODE_EXEC_CACHE_VBO);
							paintVbo(extfcache.get(efvi).myVbo);
							break;
						}else if(extfcache.get(efvi).myVbo.ttl>0){
							//cache unexpired, reduce ttl and paint as needed
							paintVbo(extfcache.get(efvi).myVbo);
							extfcache.get(efvi).myVbo.ttl-=1;
						}else{
							System.out.println("extfvbo: TTL_NOT_GREATER_THAN_OR_EQUAL_TO_0");
						}
					}
				}
				System.out.println("extfvbo: "+efvi);
				//backup present StepBackVoxel
				//StepBackVoxel tmp = myStepBack;
				//create new record in extfcache
				//if(efvi==extfcache.size()){
				//	extfcache.add(myStepBack);
				//refresh/re-cache vbo record from on-disk commands list file
				//}else if(efvi<extfcache.size()){
				//	extfcache.remove(efvi);
				//	extfcache.add(myStepBack);
				//sanity check failed, fallacious error condition (program shall continue running >:O )
				//}else{
				//	System.out.println("extfvbo: EFVI_NOT_LESS_THAN_OR_EQUAL_TO_0");
				//}
				//restore StepBackVoxel from backup
				//myStepBack = tmp;
			}else if(0==cmdargv[0].compareTo("cvm")){
				changeVertMul(Float.valueOf(cmdargv[1]));
			}else if(0==cmdargv[0].compareTo("color")){
				for(int ii=0;ii<8;ii++){
					color[ii][0]=Float.valueOf(cmdargv[1]);
					color[ii][1]=Float.valueOf(cmdargv[2]);
					color[ii][2]=Float.valueOf(cmdargv[3]);
				}
			}else if(0==cmdargv[0].compareTo("cvpr")){
				for(int i3=0;i3<8;i3++){
					for(int i2=0;i2<3;i2++){
						ver[i3][i2]=ver2[i3][i2];
					}
				}
			}else if(0==cmdargv[0].compareTo("reset")){
				for(int i3=0;i3<8;i3++){
					for(int i2=0;i2<3;i2++){
						color[i3][i2]=color2[i3][i2];
					}
				}
				for(int i3=0;i3<8;i3++){
					for(int i2=0;i2<3;i2++){
						ver[i3][i2]=ver2[i3][i2];
					}
				}
			//vbo cache exec mode only commands:
			}else if(ext_mode==MODE_EXEC_CACHE_VBO){
				if(0==cmdargv[0].compareTo("alloc")){
					System.out.println("MODE_EXEC_CACHE_VBO: alloc: "+cmdargv[1]);
					myStepBack.myVbo = (new StepBackVoxel()).new Vbo(Integer.valueOf(cmdargv[1]));
				}else if(0==cmdargv[0].compareTo("close")){
					extfcache.add(myStepBack);
				}
			}
		}
	}
	public void changeVertMul(float size){
		for(int i=0;i<8;i++){
			for(int i2=0;i2<3;i2++){
				ver[i][i2]=ver[i][i2]*size;
			}
		}
	}
	public void changeVertPos(float x, float y, float z){
		for(int i=0;i<8;i++){
			int i2=0;
			ver[i][i2]=ver[i][i2]+x; i2++;
			ver[i][i2]=ver[i][i2]+y; i2++;
			ver[i][i2]=ver[i][i2]+z; 
		}

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
	public void overlay2d(){
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
		
		GL11.glBegin(GL11.GL_TRIANGLES);           // draw triangles
			GL11.glVertex3f( 0.0f, .010f+timeoff, 0.0f);         // Top
			GL11.glVertex3f(-.010f,-.010f, 0.0f);         // Bottom Left
			GL11.glVertex3f( .010f,-.010f, 0.0f);         // Bottom Right
		GL11.glEnd();                              // done

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
	drawMap();
	int[][][] texarray = new int[2][2][4];//{
	
	overlay2d();
	//transx=transx+0.001f;
	//overlay2d();

	/*
		 GL11.glMatrixMode(GL11.GL_PROJECTION);
		 GL11.glLoadIdentity();
		 GL11.glOrtho(-1.f, 1.f, -1.f, 1.f, 1.f, 20.f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		 GL11.glLoadIdentity();
		 GLU.gluLookAt(0.f, 0.f, 10.f, 0.f, 0.f, 0.f, 0.f, 1.f, 0.f);

		 GL11.glBegin(GL11.GL_QUADS);
		  GL11.glColor3f(1.f, 0.f, 0.f); GL11.glVertex3f(-.75f, -.75f, 0.f);
		  GL11.glColor3f(0.f, 1.f, 0.f); GL11.glVertex3f( .75f, -.75f, 0.f);
		  GL11.glColor3f(0.f, 0.f, 1.f); GL11.glVertex3f( .75f,  .75f, 0.f);
		  GL11.glColor3f(1.f, 1.f, 0.f); GL11.glVertex3f(-.75f,  .75f, 0.f);
		 GL11.glEnd();
	*/
		 Display.update();
		 	/*
			if(Keyboard.isKeyDown(Keyboard.KEY_F)){
				rotate_x++;
			}
		 	if(Keyboard.isKeyDown(Keyboard.KEY_H)){
				rotate_x--;
			}
		 	if(Keyboard.isKeyDown(Keyboard.KEY_T)){
				rotate_y++;
			}
		 	if(Keyboard.isKeyDown(Keyboard.KEY_G)){
				rotate_y--;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_I)){
				System.out.println(String.valueOf(rotate_x)+" ");//+rotate_y.toString());
			}
			*/
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
	public void cached_extf(String fname){
		try{
			FileReader fr = new FileReader(fname);
			BufferedReader br = new BufferedReader(fr);
			myStepBack.cmdline=br.lines().collect(Collectors.joining(System.lineSeparator()));
			br.close();
		}catch(Exception e){
			System.out.println("pullCmd: save: Warning, exception raised.");
			e.printStackTrace();
		}
	}
	public void testGen(String fname){
		try{
			FileReader fr = new FileReader(fname);
			BufferedReader br = new BufferedReader(fr);
			myStepBack.cmdline=br.lines().collect(Collectors.joining(System.lineSeparator()));
			br.close();
		}catch(Exception e){
			System.out.println("pullCmd: save: Warning, exception raised.");
			e.printStackTrace();
		}
	}
	public void testGen(float x, float y, float z, float mul){
		/*
		try{
			FileReader fr = new FileReader("tmp");
			BufferedReader br = new BufferedReader(fr);
			myStepBack.cmdline=br.lines().collect(Collectors.joining(System.lineSeparator()));
			br.close();
		}catch(Exception e){
			System.out.println("pullCmd: save: Warning, exception raised.");
			e.printStackTrace();
		}*/
		testGen("tmp");
		/*
		myStepBack.cmdline="";
		myStepBack.cmdline=myStepBack.cmdline+"cvp 2 2 2\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad1\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad2\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad3\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad4\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad5\n";
		myStepBack.cmdline=myStepBack.cmdline+"quad6\n";
		*/
	}
	public void testGen(){
		testGen(2f,2f,2f,1.0f);
	}
	//Rt2JqbCUHa2
	public void initStartingMap(){
		float[][] demo = new float[][]{
			{0.0f,-2.0f,0.0f,1.0f},
			{0.0f,-2.0f,2.0f,1.0f},
			{2.0f,-2.0f,0.0f,1.0f},
			{0.0f,0.0f,10.0f,1.0f}
		};
		int numof=4;
		for(int i = 0;i<numof;i++){
			StartVoxel voxel = new StartVoxel();
			voxel.x=demo[i][0];
			voxel.y=demo[i][1];
			voxel.z=demo[i][2];
			voxel.gmul=demo[i][3];
			map.voxels.add(voxel);
		}
		//map.voxels.get(0).better.demo();
		//map.voxels.get(0).cool = true;
		//map.voxels.get(3).better.demo();
		//map.voxels.get(3).cool = true;
	}
    public static void main(String args[]) {
        ColoredTriangle ct = new ColoredTriangle();
		ct.initStartingMap();
        ct.start();
    }

}
