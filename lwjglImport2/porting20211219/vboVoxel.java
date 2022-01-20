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

public class vboVoxel {
	public static int w=800,h=600;
	//Rt2JqbCUHa8nn
	public double moverate=1.0;
	//Rt2JqbCUHa82
	int drawMap_glBigDispList;
	boolean drawMap_pregen=false;
	public boolean overlay2d_dpComplete = false;
	//int vertices = 4;
	int vbo_vertex_handle;
	int vbo_color_handle;
	ArrayList<float[]> clrDat = new ArrayList<float[]>();
	ArrayList<float[]> vertDat = new ArrayList<float[]>();
	//int vertex_size = 3; // X, Y, Z,
	//int color_size = 3; // R, G, B,
	public void writeQuad(float[][] ver, float[][] clr, int[] abcd){
		float[][] tmpver = new float[8][3];
		float[][] tmpclr = new float[8][3];
		for(int i=0;i<8;i++){
			for(int i2=0;i2<3;i2++){
				tmpver[i][i2]=ver[i][i2];
				tmpclr[i][i2]=clr[i][i2];
			}
		}

		/*for(int i=0;i<8;i++){
			tmpver[i]=new float[3];
			tmpclr[i]=new float[3];
			System.arraycopy(ver, 0, tmpver[i], 0, 3);
			System.arraycopy(clr, 0, tmpclr[i], 0, 3);
		}*/
		for(int i=0;i<4;i++){
			//for(int i2=0;i2<3;i2++){
				vertDat.add(tmpver[abcd[i]]);
				clrDat.add(tmpclr[abcd[i]]);
			//}
		}
	}
	public void genVbo(){
		//float[][] arg1 = vertDat.toArray();
		float[][] vert = new float[vertDat.toArray().length][3];
		int i=0;
		for (float item[] : vertDat) {
			vert[i][0]=item[0];
			vert[i][1]=item[1];
			vert[i][2]=item[2];
			i=i+1;
		}
		i=0;
		float[][] clr = new float[clrDat.toArray().length][3];
		for (float item[] : clrDat) {
			clr[i][0]=item[0];
			clr[i][1]=item[1];
			clr[i][2]=item[2];
			i=i+1;
		}
		genVbo(3, vertDat.toArray().length, vert, clr);
	}
	public void genVbo(int size, int vertices, float[][] vertPutDat, float[][] clrPutDat){
		FloatBuffer vertex_data = BufferUtils.createFloatBuffer(vertices * size);
		FloatBuffer color_data = BufferUtils.createFloatBuffer(vertices * size);
		int vertex_size=size, color_size=size;
		//vertex_data.put(new float[] { -0.1f, -0.1f, 0f, });
		//vertex_data.put(new float[] { 0.1f, -0.1f, 0f, });
		//vertex_data.put(new float[] { 0.1f, 0.1f, 0f, });
		//vertex_data.put(new float[] { -0.1f, 0.1f, 0f, });
		for(float[] buf : vertPutDat){
			vertex_data.put(buf);
		}
		vertex_data.flip();

		//color_data.put(new float[] { 1f, 0f, 0f, });
		//color_data.put(new float[] { 0f, 1f, 0f, });
		//color_data.put(new float[] { 0f, 0f, 1f, });
		//color_data.put(new float[] { 1f, 1f, 1f, });
		for(float[] buf : clrPutDat){
			color_data.put(buf);
		}
		color_data.flip();

		vbo_vertex_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertex_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		vbo_color_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	public void paintVbo(){
		paintVbo(vertDat.toArray().length, 3, 3);
	}
	public void paintVbo(int vertn, int clrl, int vertl) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
		GL11.glVertexPointer(vertl, GL11.GL_FLOAT, 0, 0l);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
		GL11.glColorPointer(clrl, GL11.GL_FLOAT, 0, 0l);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, vertn);

		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

	}
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
		int vertex_size=3;
		int color_size=3;
		if(false==overlay2d_dpComplete){
			genVbo(3, 4, new float[][] {{ -0.1f, -0.1f, 0f, },{ 0.1f, -0.1f, 0f, },{ 0.1f, 0.1f, 0f, },{ -0.1f, 0.1f, 0f, }}, new float[][]{{ 1f, 0f, 0f, },{ 0f, 1f, 0f, },{ 0f, 0f, 1f, },{ 1f, 1f, 1f, }});
			overlay2d_dpComplete=true;
			//GL11.glCallLists(DisplayLists.lists[0]);
		}else{
			System.out.println("GL11.glCallLists(DisplayLists.lists[%entry%]);");
			paintVbo(4, color_size, vertex_size);
			//GL11.glCallLists(DisplayLists.compiledList[0]);
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

    //GL11.glRotatef( rotate_x, 1.0f, 0.0f, 0.0f );
    //GL11.glRotatef( rotate_y, 0.0f, 1.0f, 0.0f );
	//fastBox(1.0f, 1.0f, 1.0f, 4.0f);
	//overlay2d();
	//fastBox(1.0f, 1.0f, 1.0f, 4.0f);
    //GL11.glTranslatef( transx,transy,transz );
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
					//keydown_defaults(keystr);
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
        vboVoxel ct = new vboVoxel();
		//ct.initStartingMap();
        ct.start();
    }

}
