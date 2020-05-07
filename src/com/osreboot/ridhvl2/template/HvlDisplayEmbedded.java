package com.osreboot.ridhvl2.template;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.menu.HvlEnvironment;

public class HvlDisplayEmbedded extends HvlDisplay{

	// TODO override methods to indicate inflexibility

	private final int width, height;
	
	private final String rootElement;
	
	private Canvas canvas;
	private WebGLRenderingContext glContext;

	public HvlDisplayEmbedded(int refreshRateArg, int widthArg, int heightArg, String rootElementArg){
		super(refreshRateArg, false, false);
		width = widthArg;
		height = heightArg;
		rootElement = rootElementArg;
	}

	@Override
	protected void apply(){
		canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		glContext = (WebGLRenderingContext)canvas.getContext("webgl");
		if(glContext == null){
			Window.alert("Fatal Error: WebGL not supported!");
		}

		try{
			RootPanel.get(rootElement).add(canvas);
		}catch(Exception e){
			Window.alert("Fatal Error: " + e.getMessage());
		}
	}

	@Override
	protected void unapply(){
		// TODO HvlDisplayEmbedded can't handle close requests (the application is terminated anyways)
	}

	@Override
	protected void preUpdate(float delta){}

	@Override
	protected void postUpdate(float delta){
		// TODO HvlDisplayEmbedded can't handle close requests (the application is terminated anyways)
	}
	
	@Override
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		super.setVsyncEnabled(false);
		HvlLogger.println(HvlDisplay.class, "VSync cannot be enabled with an embedded display!");
	}

	@Override
	public void setResizable(boolean resizableArg){
		super.setResizable(false);
		HvlLogger.println(HvlDisplay.class, "Resizability cannot be enabled with an embedded display!");
	}

	@Override
	public HvlEnvironment getEnvironment(){
		return new HvlEnvironment(0, 0, width, height, false);
	}

	@Override
	public WebGLRenderingContext getGLContext(){
		return glContext;
	}

}
