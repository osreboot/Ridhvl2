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

	public static final HvlTag<String> TAG_TEXTURE = 					new HvlTag<>(String.class, "texture");
	public static final HvlTag<String> TAG_REGEX_NEWLINE =			 	new HvlTag<>(String.class, "regex_newline");
	public static final HvlTag<Float> TAG_SCALE = 						new HvlTag<>(Float.class, "scale");
	public static final HvlTag<Float> TAG_X_SPACING = 					new HvlTag<>(Float.class, "x_spacing");
	public static final HvlTag<Float> TAG_Y_SPACING = 					new HvlTag<>(Float.class, "y_spacing");
	public static final HvlTag<Float> TAG_X_OFFSET = 					new HvlTag<>(Float.class, "x_offset");
	public static final HvlTag<Float> TAG_Y_OFFSET = 					new HvlTag<>(Float.class, "y_offset");
	public static final HvlTag<HvlCharacter> TAG_CHARACTER_MISSING = 	new HvlTag<>(HvlCharacter.class, "character_missing");

	private HashMap<Character, HvlCharacter> characters;

	private transient Texture loadedTexture;
	
	// TODO remove this after verifying that it doesn't break font rendering
	private transient boolean texelNudge;

	// Constructor for Jackson deserialization
	private HvlFont(){}

	public HvlFont(HashMap<Character, HvlCharacter> charactersArg, Texture loadedTextureArg, String textureArg){
		super(TAG_TEXTURE,
				TAG_REGEX_NEWLINE,
				TAG_SCALE,
				TAG_X_SPACING,
				TAG_Y_SPACING,
				TAG_X_OFFSET,
				TAG_Y_OFFSET,
				TAG_CHARACTER_MISSING);
		characters = charactersArg;
		loadedTexture = loadedTextureArg;
		set(TAG_TEXTURE, textureArg);
		set(TAG_REGEX_NEWLINE, "\\\\n");
		set(TAG_SCALE, 1.0f);
		set(TAG_X_SPACING, 0f);
		set(TAG_Y_SPACING, 0f);
		set(TAG_X_OFFSET, 0f);
		set(TAG_Y_OFFSET, 0f);
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
		
		float currentY = yArg + get(TAG_Y_OFFSET);
		for(String line : lines){
			float currentX = xArg + get(TAG_X_OFFSET);
			float maxHeight = 0;
			for(char c : line.toCharArray()){
				if(characters.containsKey(c)){
					HvlCharacter character = characters.get(c);

					float charWidth = (character.getUV1().x - character.getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
					float charHeight = (character.getUV1().y - character.getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;

					hvlDraw(hvlQuad(texelNudge ? (int)currentX : currentX, texelNudge ? (int)currentY : currentY, charWidth, charHeight, 
							character.getUV0().x, character.getUV0().y, character.getUV1().x, character.getUV1().y),
							loadedTexture, colorArg);

					currentX += charWidth;
					currentX += get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;

					if(charHeight > maxHeight)
						maxHeight = charHeight;
				}else if(get(TAG_CHARACTER_MISSING) != null){
					float charWidth = (get(TAG_CHARACTER_MISSING).getUV1().x - get(TAG_CHARACTER_MISSING).getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
					float charHeight = (get(TAG_CHARACTER_MISSING).getUV1().y - get(TAG_CHARACTER_MISSING).getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;

					hvlDraw(hvlQuad(texelNudge ? (int)currentX : currentX, texelNudge ? (int)currentY : currentY, charWidth, charHeight, 
							get(TAG_CHARACTER_MISSING).getUV0().x, get(TAG_CHARACTER_MISSING).getUV0().y,
							get(TAG_CHARACTER_MISSING).getUV1().x, get(TAG_CHARACTER_MISSING).getUV1().y),
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

	public void drawc(String textArg, float xArg, float yArg){
		draw(textArg, xArg - (getWidth(textArg, 1f) / 2f), yArg - (getHeight(textArg, 1f) / 2f), Color.white, 1f);
	}

	public void drawc(String textArg, float xArg, float yArg, Color colorArg){
		draw(textArg, xArg - (getWidth(textArg, 1f) / 2f), yArg - (getHeight(textArg, 1f) / 2f), colorArg, 1f);
	}

	public void drawc(String textArg, float xArg, float yArg, float scaleArg){
		draw(textArg, xArg - (getWidth(textArg, scaleArg) / 2f), yArg - (getHeight(textArg, scaleArg) / 2f), Color.white, scaleArg);
	}

	public void drawc(String textArg, float xArg, float yArg, Color colorArg, float scaleArg){
		draw(textArg, xArg - (getWidth(textArg, scaleArg) / 2f), yArg - (getHeight(textArg, scaleArg) / 2f), colorArg, scaleArg);
	}

	public float getWidth(String textArg, float scaleArg){
		String[] lines = textArg.split(get(TAG_REGEX_NEWLINE));

		float maxWidth = 0;
		for(String line : lines){
			float lineWidth = 0;
			for(char c : line.toCharArray()){
				if(characters.containsKey(c)){
					lineWidth += (characters.get(c).getUV1().x - characters.get(c).getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
					lineWidth += get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;
				}else if(get(TAG_CHARACTER_MISSING) != null){
					lineWidth += (get(TAG_CHARACTER_MISSING).getUV1().x - get(TAG_CHARACTER_MISSING).getUV0().x) * loadedTexture.getImageWidth() * get(TAG_SCALE) * scaleArg;
					lineWidth += get(TAG_X_SPACING) * get(TAG_SCALE) * scaleArg;
				}//TODO else throw exception
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
				if(characters.containsKey(c)){
					float charHeight = (characters.get(c).getUV1().y - characters.get(c).getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;
					if(charHeight > maxHeight)
						maxHeight = charHeight;
				}else if(get(TAG_CHARACTER_MISSING) != null){
					float charHeight = (get(TAG_CHARACTER_MISSING).getUV1().y - get(TAG_CHARACTER_MISSING).getUV0().y) * loadedTexture.getImageHeight() * get(TAG_SCALE) * scaleArg;
					if(charHeight > maxHeight)
						maxHeight = charHeight;
				}//TODO else throw exception
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
	
	public void setTexelNudge(boolean texelNudgeArg){
		texelNudge = texelNudgeArg;
	}
	
	public boolean getTexelNudge(){
		return texelNudge;
	}

}
