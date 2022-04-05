package com.osreboot.ridhvl2.painter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlCoord;

public class HvlShader {

	public static final String SHADER_VERTEX_DEFAULT = "void main(){ gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex; gl_TexCoord[0] = gl_MultiTexCoord0; }";
	
	private static void setCurrentShader(HvlShader shader){
		if(shader != null){
			ARBShaderObjects.glUseProgramObjectARB(shader.getID());
			int loc = GL20.glGetUniformLocation(shader.getID(), "texture1");
			GL20.glUniform1i(loc, 0);
		}else ARBShaderObjects.glUseProgramObjectARB(0);
	}

	private int shaderID;
	
	private String vertLog, fragLog;

	public HvlShader(String fragmentArg){
		initialize(SHADER_VERTEX_DEFAULT, readFile(fragmentArg));
	}
	
	public HvlShader(String vertexArg, String fragmentArg){
		initialize(readFile(vertexArg), readFile(fragmentArg));
	}
	
	private void initialize(String vertexShaderArg, String fragmentShaderArg){
		int vertexShader = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
		ARBShaderObjects.glShaderSourceARB(vertexShader, vertexShaderArg);
		ARBShaderObjects.glCompileShaderARB(vertexShader);

		int fragmentShader = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		ARBShaderObjects.glShaderSourceARB(fragmentShader, fragmentShaderArg);
		ARBShaderObjects.glCompileShaderARB(fragmentShader);
		vertLog = ARBShaderObjects.glGetInfoLogARB(vertexShader, ARBShaderObjects.glGetObjectParameteriARB(vertexShader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
		fragLog = ARBShaderObjects.glGetInfoLogARB(fragmentShader, ARBShaderObjects.glGetObjectParameteriARB(fragmentShader, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
		
		shaderID = ARBShaderObjects.glCreateProgramObjectARB();
		ARBShaderObjects.glAttachObjectARB(shaderID, vertexShader);
		ARBShaderObjects.glAttachObjectARB(shaderID, fragmentShader);
		ARBShaderObjects.glLinkProgramARB(shaderID);
		ARBShaderObjects.glValidateProgramARB(shaderID);
	}
	
	public String getVertLog(){
		return vertLog;
	}

	public String getFragLog(){
		return fragLog;
	}
	
	public int getID(){
		return shaderID;
	}
	
	public void doShade(HvlAction.A0 actionArg){
		setCurrentShader(this);
		actionArg.run();
		setCurrentShader(null);
	}

	public void send(String key, int value){
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform1i(loc, value);
	}
	
	public void send(String key, float value){
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform1f(loc, value);
	}
	
	public void send(String key, int[] value){
		IntBuffer buffer = BufferUtils.createIntBuffer(value.length);
		buffer.put(value);
		buffer.rewind();
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform1(loc, buffer);
	}
	
	public void send(String key, float[] value){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(value.length);
		buffer.put(value);
		buffer.rewind();
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform1(loc, buffer);
	}
	
	public void send(String key, int id, HvlRenderFrame renderFrame){
		send(key, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + id);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderFrame.getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
	
	public void send(String key, int id, Texture texture){
		send(key, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + id);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
	
	public void send(String key, HvlCoord value){
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform2f(loc, value.x, value.y);
	}
	
	public void send(String key, Color value){
		int loc = GL20.glGetUniformLocation(shaderID, key);
		GL20.glUniform4f(loc, value.r, value.g, value.b, value.a);
	}
	
	public void send(String key, HvlAction.A1<Integer> sendCommand){
		sendCommand.run(GL20.glGetUniformLocation(shaderID, key));
	}
	
	// TODO send array
	
	private String readFile(String file){
		StringBuilder builder = new StringBuilder();
		try{
			FileInputStream input = new FileInputStream(file);
			BufferedReader reader = null;
			try{
				reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				String line;
				while((line = reader.readLine()) != null) builder.append(line).append('\n');
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(reader != null) reader.close();
				input.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return builder.toString();
	}

}