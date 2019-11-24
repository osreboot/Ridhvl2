package com.osreboot.ridhvl2.menu;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class HvlTaggable implements Serializable{
	private static final long serialVersionUID = -7986790903295832118L;

	protected static HvlTagTransient<?>[] accumulate(HvlTagTransient<?>[] tags0Arg, HvlTagTransient<?>... tags1Arg){
		HvlTagTransient<?>[] output = Arrays.copyOf(tags0Arg, tags0Arg.length + tags1Arg.length);
		System.arraycopy(tags1Arg, 0, output, tags0Arg.length, tags1Arg.length);
		return output;
	}
	
	@JsonSerialize(using = TagMapSerializer.class)
	@JsonDeserialize(using = TagMapDeserializer.class)
	private HashMap<HvlTag<?>, Object> properties;
	private transient HashMap<HvlTagTransient<?>, Object> propertiesTransient;

	public HvlTaggable(HvlTagTransient<?>... tags){
		properties = new HashMap<>();
		propertiesTransient = new HashMap<>();

		for(HvlTagTransient<?> tag : tags){
			if(tag instanceof HvlTag<?>)
				properties.put((HvlTag<?>)tag, null);
			else propertiesTransient.put(tag, null);
		}
	}

	public <T> HvlTaggable set(HvlTagTransient<T> tagArg, T valueArg){
		if(tagArg instanceof HvlTag<?>){
			if(!properties.containsKey(tagArg))
				throw new TagMismatchException();
			else properties.put((HvlTag<?>)tagArg, valueArg);
		}else{
			if(!propertiesTransient.containsKey(tagArg))
				throw new TagMismatchException();
			else propertiesTransient.put(tagArg, valueArg);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(HvlTagTransient<T> tagArg){
		if(tagArg instanceof HvlTag<?>){
			if(!properties.containsKey(tagArg))
				throw new TagMismatchException();
			else return (T)properties.get(tagArg);
		}else{
			if(!propertiesTransient.containsKey(tagArg))
				throw new TagMismatchException();
			else return (T)propertiesTransient.get(tagArg);
		}
	}

	public Set<HvlTagTransient<?>> validTags(){
		HashSet<HvlTagTransient<?>> output = new HashSet<>(properties.keySet());
		output.addAll(propertiesTransient.keySet());
		return output;
	}

	public static class TagMismatchException extends RuntimeException{
		private static final long serialVersionUID = -8599901093855663636L;
	}
	
	@SuppressWarnings("serial")
	public static class TagMapSerializer extends StdSerializer<HashMap<HvlTag<?>, Object>>{

		protected TagMapSerializer(){
			this(null);
		}
		
		protected TagMapSerializer(Class<HashMap<HvlTag<?>, Object>> typeArg){
			super(typeArg);
		}

		@Override
		public void serialize(HashMap<HvlTag<?>, Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException{
			gen.writeStartArray();
			for(HvlTag<?> tag : value.keySet()){
				gen.writeStartArray();
				gen.writeObject(tag);
				gen.writeObject(value.get(tag));
				gen.writeEndArray();
			}
			gen.writeEndArray();
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class TagMapDeserializer extends StdDeserializer<HashMap<HvlTag<?>, Object>>{

		protected TagMapDeserializer(){
			this(null);
		}
		
		protected TagMapDeserializer(Class<HashMap<HvlTag<?>, Object>> typeArg){
			super(typeArg);
		}

		@Override
		public HashMap<HvlTag<?>, Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException{
			JsonNode node = p.getCodec().readTree(p);
			
			HashMap<HvlTag<?>, Object> output = new HashMap<>();
			for(int i = 0; i < node.size(); i++){
				JsonParser parser = node.get(i).traverse();
				parser.nextToken();
				parser.nextToken();
				HvlTag<?> key = ctxt.readValue(parser, HvlTag.class);
				parser.nextToken();
				Object value = ctxt.readValue(parser, HvlType.getCachedForName(key.getTypeName()));
				output.put(key, value);
			}
			return output;
		}
		
	}

}
