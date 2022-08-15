package com.osreboot.ridhvl2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.osreboot.ridhvl2.HvlAction.A0r;
import com.osreboot.ridhvl2.menu.HvlType;

public interface HvlConfig {

	public <T> T load(String pathArg);
	
	public <T> T load(String pathArg, HvlAction.A0r<T> notFoundArg);

	public <T> boolean save(T objectArg, String pathArg);

	public boolean exists(String pathArg);
	
	public String getConfigName();

	public static final HvlConfig RAW = new HvlConfig(){
		@Override
		public <T> T load(String pathArg){
			return load(pathArg, () -> {throw new ConfigMissingException(this, pathArg);});
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> T load(String pathArg, A0r<T> notFoundArg){
			if(exists(pathArg)){
				try{
					FileInputStream fileInputStream = new FileInputStream(pathArg);
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					Object output = objectInputStream.readObject();
					objectInputStream.close();
					return (T)output;//TODO catch this potential ClassCastException
				}catch(Exception e){
					e.printStackTrace();//TODO allow this exception to pass so user can catch it
					return null;
				}
			}else return notFoundArg.run();
		}

		@Override
		public <T> boolean save(T objectArg, String pathArg){
			try{
				FileOutputStream fileOutputStream = new FileOutputStream(pathArg);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(objectArg);
				objectOutputStream.close();
				return true;
			}catch(Exception e){
				e.printStackTrace();//TODO allow this exception to pass so user can catch it
				return false;
			}
		}

		@Override
		public boolean exists(String pathArg){
			//TODO type checking
			return new File(pathArg).exists();
		}

		@Override
		public String getConfigName(){
			return "RAW";
		}
	};

	public static final HvlConfig PJSON = new HvlConfig(){
		public static final String
		MESSAGE_TRACE_START = "/*[CLASS TRACE, DO NOT MODIFY -> ",
		MESSAGE_TRACE_END = "]*/";

		@Override
		public <T> T load(String pathArg){
			return load(pathArg, () -> {throw new ConfigMissingException(this, pathArg);});
		}
		
		@Override
		public <T> T load(String pathArg, A0r<T> notFoundArg){
			if(exists(pathArg)){
				try{
					BufferedReader reader = new BufferedReader(new FileReader(pathArg));
					String typeName = reader.readLine();
					typeName = typeName.substring(MESSAGE_TRACE_START.length(), typeName.length() - MESSAGE_TRACE_END.length());
					String rawJson = "";
					String line = "";
					while((line = reader.readLine()) != null)
						rawJson += line;
					reader.close();

					Class<T> c = HvlType.getCachedForName(typeName);
					
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
					objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
					
					return objectMapper.<T>readValue(rawJson, c);//TODO catch this potential JsonSyntaxException
				}catch(Exception e){
					e.printStackTrace();//TODO allow this exception to pass so user can catch it
					return null;
				}
			}else return notFoundArg.run();
		}

		@Override
		public <T> boolean save(T objectArg, String pathArg){
			try{
				FileWriter writer = new FileWriter(pathArg);
				writer.write(MESSAGE_TRACE_START);
				writer.write(objectArg.getClass().getName());
				writer.write(MESSAGE_TRACE_END + "\n");
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
				objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
				objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				
				DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
				prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
				
				objectMapper.writer(prettyPrinter).writeValue(writer, objectArg);
				return true;
			}catch(Exception e){
				e.printStackTrace();//TODO allow this exception to pass so user can catch it
				return false;
			}
		}

		@Override
		public boolean exists(String pathArg){
			//TODO type checking
			return new File(pathArg).exists();
		}
		
		@Override
		public String getConfigName(){
			return "PJSON";
		}
	};
	
	public static class ConfigMissingException extends RuntimeException{
		private static final long serialVersionUID = 5061579317982909912L;
		
		public ConfigMissingException(HvlConfig configArg, String pathArg){
			super("File " + pathArg + " not found for HvlConfig type " + configArg.getConfigName() + "!");
		}
	}

}
