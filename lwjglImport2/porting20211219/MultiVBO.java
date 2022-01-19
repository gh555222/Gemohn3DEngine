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

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.*;
import org.lwjgl.util.glu.*;

import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.ARBVertexBufferObject;

public class MultiVBO{
static int vbo_left;
static int vao_left;
static int ibo_left;
static float left_vertices[] = { -0.5f, 0f, -0.25f, 0.5f, 0f, 0f };
static int left_indices[] = { 0, 1, 2, 3, 4, 5 };

static int vbo_right;
static int vao_right;
static int ibo_right;
static float right_vertices[] = { 0f, 0f, 0.25f, -0.5f, 0.5f, 0f };
static int right_indices[] = { 0, 1, 2, 3, 4, 5 };

public static void main(String[] argv){
	setupLoop();
}
private void setupLoop() {
        GL.createCapabilities();
        debugProc = GLUtil.setupDebugMessageCallback();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            Engine.makeAndDrawBuffersForTwoTriangles();

            renderLoop();
        }
    }

    private void renderLoop() {
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        float aspect = (float) width / height;
        glLoadIdentity();
        glOrtho(-aspect, aspect, -1, 1, -1, 1);

        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events.
        glfwPollEvents();
    }
public static void makeAndDrawBuffersForTwoTriangles() {
        vbo_left = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_left);
        glBufferData(GL_ARRAY_BUFFER, left_vertices, GL_STATIC_DRAW);

        vao_left = glGenVertexArrays();
        glBindVertexArray(vao_left);
        glBindBuffer(GL_ARRAY_BUFFER, vao_left);
        glVertexAttribPointer(vao_left, 2, GL_FLOAT, false, 2, 0);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vbo_left = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_left);
        glBufferData(GL_ARRAY_BUFFER, left_vertices, GL_STATIC_DRAW);

        vao_left = glGenVertexArrays();
        glBindVertexArray(vao_left);
        glBindBuffer(GL_ARRAY_BUFFER, vao_left);
        glVertexAttribPointer(vao_left, 2, GL_FLOAT, false, 2, 0);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(vao_left);
        glDrawArrays(GL_TRIANGLES, 0, left_vertices.length);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(vao_right);
        glDrawArrays(GL_TRIANGLES, 0, right_vertices.length);

    }
public static void makeAndDrawBuffersForTwoTriangles2() {
    // VBO
    vbo_left = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo_left);
    glBufferData(GL_ARRAY_BUFFER, left_vertices, GL_STATIC_DRAW);
    // IBO
    ibo_left = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_left);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,
            (IntBuffer) BufferUtils.createIntBuffer(left_indices.length).put(left_indices).flip(), GL_STATIC_DRAW);
    glVertexPointer(2, GL_FLOAT, 0, 0L);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    // VAO
    vao_left = glGenVertexArrays();
    glBindVertexArray(vao_left);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_left);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2, 0);
    glBindVertexArray(0);

    // VBO
    vbo_right = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vbo_right);
    glBufferData(GL_ARRAY_BUFFER, right_vertices, GL_STATIC_DRAW);
    // IBO
    ibo_right = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_right);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER,
            (IntBuffer) BufferUtils.createIntBuffer(right_indices.length).put(right_indices).flip(),
            GL_STATIC_DRAW);
    glVertexPointer(2, GL_FLOAT, 0, 0L);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    // VAO
    vao_right = glGenVertexArrays();
    glBindVertexArray(vao_right);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_right);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2, 0);
    glBindVertexArray(0);

    // Unbind all
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    // Draw elements
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_left);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_left);
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2, 0);
    glDrawElements(GL11.GL_TRIANGLES, left_indices.length, GL_UNSIGNED_INT, 0L);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_right);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo_right);
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2, 0);
    glDrawElements(GL_TRIANGLES, right_indices.length, GL_UNSIGNED_INT, 0L);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    GL11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
    /*
    // Draw Arrays
    glBindVertexArray(vao_left);
    glDrawArrays(GL_TRIANGLES, 0, left_indices.length);
    glBindVertexArray(vao_right);
    glDrawArrays(GL_TRIANGLES, 0, right_indices.length);
    glBindVertexArray(0);
    */
}
}
