package com.osreboot.ridhvl2.menu;

import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuad;

import java.util.HashMap;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class HvlFont extends HvlTaggable{
	private static final long serialVersionUID = 7326153102489408036L;

	public static final String FILE_EXTENSION = "hvlft"; 

	public static final HvlTag<String> TAG_TEXTURE = 		new HvlTag<>(String.class, "texture");
	public static final HvlTag<String> TAG_REGEX_NEWLINE = 	new HvlTag<>(String.class, "regex_newline");
	public static final HvlTag<Float> TAG_SCALE = 			new HvlTag<>(Float.class, "scale");
	public static final HvlTag<Float> TAG_X_SPACING = 		new HvlTag<>(Float.class, "x_spacing");
	public static final HvlTag<Float> TAG_Y_SPACING = 		new HvlTag<>(Float.class, "y_spacing");

	private HashMap<Character, HvlCharacter> characters;

	private transient Texture loadedTexture;

	// Constructor for Jackson deserialization
	private HvlFont(){}
	
	public HvlFont(HashMap<Character, HvlCharacter> charactersArg, Texture loadedTextureArg, String textureArg){
		super(TAG_TEXTURE, TAG_REGEX_NEWLINE, TAG_SCALE, TAG_X_SPACING, TAG_Y_SPACING);
		characters = charactersArg;
		loadedTexture = loadedTextureArg;
		set(TAG_TEXTURE, textureArg);
		set(TAG_REGEX_NEWLINE, "\\\\n");
		set(TAG_SCALE, 1.0f);
		set(TAG_X_SPACING, 0f);
		set(TAG_Y_SPACING, 0f);
	}

	public void draw(String textArg, float xArg, float yArg){
		draw(textArg, xArg, yArg, Color.white, 1f);
	}

	public void draw(String textArg, float xArg, float yArg, Color colorArg){
		draw(textArg, xArg, yArg, colorArg, 1f);
	}

	public void draw(String textArg, float xArg, float yArg, float scaleArg){
		draw(textArg, xArg, yArg, Color.white, scaleArg);
	}

	public void draw(String textArg, float xArg, float yArg, Color colorArg, float scaleArg){
		String[] lines = textArg.split(get(TAG_REGEX_NEWLINE));

		float currentY = yArg;
		for(String line : lines){
			float currentX = xArg;
			float maxHeight = 0;
			for(char c : line.toCharArray()){
				if(characters.containsKey(c)){
					HvlCharacter character = characters.get(c);

					float charWidth = (character.getUV1().x - character.getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
					float charHeight = (character.getUV1().y - character.getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;

					hvlDraw(hvlQuad(currentX, currentY, charWidth, charHeight, 
							character.getUV0().x, character.getUV0().y, character.getUV1().x, character.getUV1().y),
							loadedTexture, colorArg);

					currentX += charWidth;
					currentX += get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;

					if(charHeight > maxHeight)
						maxHeight = charHeight;
				}//TODO else throw exception
			}

			currentY += maxHeight;
			currentY += get(TAG_Y_SPACING) * get(TAG_SCALE) * scaleArg;
		}
	}

	public float getWidth(String textArg, float scaleArg){
		String[] lines = textArg.split(get(TAG_REGEX_NEWLINE));

		float maxWidth = 0;
		for(String line : lines){
			float lineWidth = 0;
			for(char c : line.toCharArray()){
				lineWidth += (characters.get(c).getUV1().x - characters.get(c).getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
				lineWidth += get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;
			}
			lineWidth -= get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;

			if(lineWidth > maxWidth)
				maxWidth = lineWidth;
		}

		return maxWidth;
	}

	public float getHeight(String textArg, float scaleArg){
		String[] lines = textArg.split(get(TAG_REGEX_NEWLINE));

		float totalHeight = 0;
		for(String line : lines){
			float maxHeight = 0;
			for(char c : line.toCharArray()){
				float charHeight = (characters.get(c).getUV1().y - characters.get(c).getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;
				if(charHeight > maxHeight)
					maxHeight = charHeight;
			}

			totalHeight += maxHeight;
			totalHeight += get(TAG_Y_SPACING) * get(TAG_SCALE) * scaleArg;
		}
		totalHeight -= get(TAG_Y_SPACING) * get(TAG_SCALE) * scaleArg;

		return totalHeight;
	}

	public Set<Character> getCharacterSet(){
		return characters.keySet();
	}

	public void setLoadedTexture(Texture textureArg){
		loadedTexture = textureArg;
	}

	public boolean isTextureLoaded(){
		return loadedTexture != null;
	}

}
