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
	public StepBackVoxel myStepBack = new StepBackVoxel();
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
	public void quad3(int a,int b,int c,int d){
		myStepBack.myVbo.writeQuad(ver, color, new int[]{a, b, c, d} );
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
					//myStepBack.addQuadToVbo(myStepBack.myVbo,0,3,2,1,vert,color);
					int tmpi=0;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmds[i].compareTo("quad2")){
				if(ext_mode==MODE_EXEC_NORM){ quad(2,3,7,6);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					//myStepBack.addQuadToVbo(myStepBack.myVbo,2,3,7,6,vert,color);
					int tmpi=1;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmds[i].compareTo("quad3")){
				if(ext_mode==MODE_EXEC_NORM){ quad(0,4,7,3);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					//myStepBack.addQuadToVbo(myStepBack.myVbo,0,4,7,3,vert,color);
					int tmpi=2;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmds[i].compareTo("quad4")){
				if(ext_mode==MODE_EXEC_NORM){ quad(1,2,6,5);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					//myStepBack.addQuadToVbo(myStepBack.myVbo,1,2,6,5,vert,color);
					int tmpi=3;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmds[i].compareTo("quad5")){
				if(ext_mode==MODE_EXEC_NORM){ quad(4,5,6,7);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					//myStepBack.addQuadToVbo(myStepBack.myVbo,4,5,6,7,vert,color);
					int tmpi=4;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmds[i].compareTo("quad6")){
				if(ext_mode==MODE_EXEC_NORM){ quad(0,1,5,4);}
				else if(ext_mode==MODE_EXEC_CACHE_VBO){
					//myStepBack.addQuadToVbo(myStepBack.myVbo,0,1,5,4,vert,color);
					int tmpi=5;
					quad3(qquadvals[tmpi][0], qquadvals[tmpi][1], qquadvals[tmpi][2], qquadvals[tmpi][3]);
				}
			}else if(0==cmdargv[0].compareTo("cvp")){
				//System.out.println(Float.valueOf(cmdargv[1])+" "+Float.valueOf(cmdargv[2])+" "+Float.valueOf(cmdargv[3]));
				if(ext_mode==MODE_EXEC_NORM){
					changeVertPos(Float.valueOf(cmdargv[1]), Float.valueOf(cmdargv[2]), Float.valueOf(cmdargv[3]));
				}else if(ext_mode==MODE_EXEC_CACHE_VBO){
					changeVertPos2(Float.valueOf(cmdargv[1]), Float.valueOf(cmdargv[2]), Float.valueOf(cmdargv[3]), 1.0f);
				}
			}else if(0==cmdargv[0].compareTo("extf")){
				//System.out.println(cmdargv[1]);
				testGen(cmdargv[1]);
				drawMap(myStepBack.cmdline, MODE_EXEC_DEFAULT);
			}else if(0==cmdargv[0].compareTo("extfvbo")){
				testGen(cmdargv[1]);
				boolean needsBuilt=false;
				//iterate over cache of vbos
				for(StepBackVoxel sbvitem : extfcache){
					if(0==sbvitem.fname.compareTo(cmdargv[1])){
						//vbo cache record died, rebuild cache record from configuration file
						if(sbvitem.ttl<1){
							sbvitem.ttl=64;
							needsBuilt=true;
						}
						sbvitem.ttl-=1;
						System.out.println("ttl remaining on "+sbvitem.fname+": "+sbvitem.ttl);
					}
				}
				if(extfcache.toArray().length<1){
					needsBuilt=true;
				}
				if(needsBuilt==true){
					System.out.println("MODE_EXEC_CACHE_VBO: Construction Started!");
					myStepBack.fname=cmdargv[1];
					drawMap(myStepBack.cmdline, MODE_EXEC_CACHE_VBO);
				}else{
					myStepBack.myVbo.paintVbo();
				}
				//
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
					//System.out.println("MODE_EXEC_CACHE_VBO: alloc: "+cmdargv[1]);
					//myStepBack.myVbo = (new StepBackVoxel()).new Vbo(Integer.valueOf(cmdargv[1]));
				}else if(0==cmdargv[0].compareTo("close")){
					myStepBack.myVbo.genVbo();
					extfcache.add(myStepBack);
					myStepBack.myVbo.paintVbo();
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
	public void changeVertPos2(float x, float y, float z, float pol){
		for(int i=0;i<8;i++){
			int i2=0;
			ver[i][i2]=ver[i][i2]+x*pol; i2++;
			ver[i][i2]=ver[i][i2]+y*pol; i2++;
			ver[i][i2]=ver[i][i2]+z*pol; 
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
