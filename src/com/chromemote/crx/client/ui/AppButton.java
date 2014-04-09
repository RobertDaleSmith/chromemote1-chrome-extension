package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;

public class AppButton extends PushButton {
	
	private int width = 64, height = 74;
	private String name;
	private String icon;
	private String intentFlingString;
	
	public AppButton(String appName, String appIcon, final String appIntent)
	{
		super(appName);
		name = appName;
		icon = appIcon;
		intentFlingString = appIntent;
		
		this.getUpFace().setHTML("<img src=\"" + icon + "\"><br><br>" + name);
		this.getDownFace().setHTML("<img src=\"" + icon + "\"><br><br>" + name);
		this.setStyleName("gtvRemote-appLauncherButton");
		this.setSize(width + "px", height + "px");
		
		this.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				sendAnymoteFling(intentFlingString);
			}
			
		});
	}
	
	public String getName()
	{
		return name;
	}
	
	public static native void sendAnymoteFling(String msgString) /*-{
		
		if($wnd.backgroundPageWindow.anymoteSessionActive)
		{
			$wnd.sendAnymoteFling(msgString)
			$wnd.indicatorFlash();
			
			$wnd.backgroundPageWindow.sendGAEvent("Fling", "Link");
		}
		else
		{
			$wnd.displayToast("No Google TV's are connected.");
			$wnd.backgroundPageWindow.console.log("Fling not sent because no anymote session is active.");
		}
		
		
	}-*/;
	
}
