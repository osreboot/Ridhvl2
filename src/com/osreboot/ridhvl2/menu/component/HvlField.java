package com.osreboot.ridhvl2.menu.component;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;
import com.osreboot.ridhvl2.template.HvlTemplate;

public class HvlField extends HvlButtonLabeled{
	private static final long serialVersionUID = 3524532940445964359L;

	private static boolean isMouseOverEnvironment(HvlEnvironment environment){
		return Mouse.getX() > environment.getX() && Mouse.getX() < environment.getX() + environment.getWidth() &&
				Display.getHeight() - Mouse.getY() > environment.getY() &&
				Display.getHeight() - Mouse.getY() < environment.getY() + environment.getHeight();
	}

	public static HvlField active;

	public static HvlField fromDefault(){
		return (HvlField)HvlDefault.applyIfExists(HvlField.class, new HvlField());
	}

	public static final String
	CHARACTERS_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
	CHARACTERS_NUMBERS = "0123456789";

	public static final HvlTag<String> TAG_ALLOWED_CHARACTERS = new HvlTag<>(String.class, "allowed_characters");
	public static final HvlTag<Integer> TAG_MAXIMUM_CHARACTERS = new HvlTag<>(Integer.class, "maximum_characters");
	public static final HvlTag<String> TAG_TEXT_HINT = new HvlTag<>(String.class, "text_hint");
	public static final HvlTag<Color> TAG_TEXT_HINT_COLOR = new HvlTag<>(Color.class, "text_hint_color");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {
		if(!environment.isBlocked()){
			if(active == component){
				if((!isMouseOverEnvironment(environment) && Mouse.isButtonDown(0)) || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
					active = null;
					component.set(TAG_STATE, HvlButtonState.OFF);
				}else{
					if(isMouseOverEnvironment(environment) && Mouse.isButtonDown(1))
						component.set(TAG_TEXT, "");
					
					Keyboard.poll();
					while(Keyboard.next()){
						if(Keyboard.getEventKeyState()){
							String text = ((HvlField)component).getText();
							if(Keyboard.getEventKey() == Keyboard.KEY_BACK){
								if(text.length() > 0)
									((HvlField)component).text(text.substring(0, Math.max(text.length() - 1, 0)));
							}else{
								String input = Keyboard.getEventCharacter() + "";
								if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) input = input.toUpperCase();
								if(component.get(TAG_ALLOWED_CHARACTERS).contains(input) &&
										(text + input).length() <= component.get(TAG_MAXIMUM_CHARACTERS)){
									((HvlField)component).text(text + input);
								}
							}
						}
					}
				}
			}else{
				//TODO implement HvlCursor here
				if(isMouseOverEnvironment(environment)){
					if(!Mouse.isButtonDown(0)){
						if(component.get(TAG_STATE) == HvlButtonState.ON){
							component.get(TAG_CLICKED).run((HvlButton)component);
							active = (HvlField)component;
						}
						component.set(TAG_STATE, HvlButtonState.HOVER);
					}else component.set(TAG_STATE, HvlButtonState.ON);
				}else component.set(TAG_STATE, HvlButtonState.OFF);
			}
		}else component.set(TAG_STATE, HvlButtonState.OFF);
	};

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_TEXT_DRAW = (delta, environment, component) -> {
		float x = HvlMath.map(component.get(TAG_TEXT_ALIGN_X), 0f, 1f, environment.getX(),
				environment.getX() + environment.getWidth() - component.get(TAG_FONT).getWidth(component.get(TAG_TEXT), component.get(TAG_TEXT_SCALE)));
		float y = HvlMath.map(component.get(TAG_TEXT_ALIGN_Y), 0f, 1f, environment.getY(),
				environment.getY() + environment.getHeight() - component.get(TAG_FONT).getHeight("A", component.get(TAG_TEXT_SCALE)));

		if(component.get(TAG_TEXT).isEmpty() && active != component){
			component.get(TAG_FONT).draw(component.get(TAG_TEXT_HINT), x + component.get(TAG_TEXT_OFFSET_X), y + component.get(TAG_TEXT_OFFSET_Y),
					component.get(TAG_TEXT_HINT_COLOR), component.get(TAG_TEXT_SCALE));
		}else{
			boolean drawCursor = active == component && HvlTemplate.newest().getTimer().getTotalTime() % 1f > 0.5f;

			component.get(TAG_FONT).draw(component.get(TAG_TEXT) + (drawCursor ? "_" : ""), x + component.get(TAG_TEXT_OFFSET_X), y + component.get(TAG_TEXT_OFFSET_Y),
					component.get(TAG_TEXT_COLOR), component.get(TAG_TEXT_SCALE));
		}
	},
	DEFAULT_DRAW = (delta, environment, component) -> {
		HvlButton.DEFAULT_DRAW.run(delta, environment, component);
		DEFAULT_TEXT_DRAW.run(delta, environment, component);
	};

	public static final HvlAction.A1<HvlButton>
	DEFAULT_CLICKED = (button) -> {};

	protected HvlField(HvlTagTransient<?>... tags){
		super(accumulate(tags,
				TAG_ALLOWED_CHARACTERS,
				TAG_MAXIMUM_CHARACTERS,
				TAG_TEXT_HINT,
				TAG_TEXT_HINT_COLOR));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_CLICKED, DEFAULT_CLICKED);
	}

	public HvlField(HvlFont fontArg, String textArg, Color colorArg, float scaleArg,
			HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState> drawStateArg){
		this();
		HvlDefault.applyIfExists(HvlField.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
		set(TAG_FONT, fontArg);
		set(TAG_TEXT, textArg);
		set(TAG_TEXT_COLOR, colorArg);
		set(TAG_TEXT_SCALE, scaleArg);
		set(TAG_TEXT_ALIGN_X, 0f);
		set(TAG_TEXT_ALIGN_Y, 0f);
		set(TAG_TEXT_OFFSET_X, 0f);
		set(TAG_TEXT_OFFSET_Y, 0f);
		set(TAG_ALLOWED_CHARACTERS, CHARACTERS_ALPHABET + CHARACTERS_NUMBERS);
		set(TAG_MAXIMUM_CHARACTERS, Integer.MAX_VALUE);
		set(TAG_TEXT_HINT, "");
		set(TAG_TEXT_HINT_COLOR, new Color(colorArg.r, colorArg.g, colorArg.b, colorArg.a / 2f));
	}

	public HvlField allowedCharacters(String allowedCharactersArg){
		return (HvlField)set(TAG_ALLOWED_CHARACTERS, allowedCharactersArg);
	}

	public HvlField maximumCharacters(int maximumCharactersArg){
		return (HvlField)set(TAG_MAXIMUM_CHARACTERS, maximumCharactersArg);
	}
	
	public HvlField textHint(String textHintArg){
		return (HvlField)set(TAG_TEXT_HINT, textHintArg);
	}

}
