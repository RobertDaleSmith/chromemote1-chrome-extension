package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class FooterUIMenuBarPanel extends AbsolutePanel{
	

	public  PushButton moveUILeftButton;
	public  PushButton moveUIRightButton;
	public  PushButton appLauncherButton;

	
	public FooterUIMenuBarPanel()
	{
		super();
		this.setStyleName("gtvRemote-footerUIBarPanel");
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			this.setSize("320px","43px");
		}
		else
		{
			this.setSize("960px","43px");
		}
		
		
		
		appLauncherButton = new PushButton("AppLauncher");
		appLauncherButton.getUpFace().setImage( new Image("images/footerUIBar/appLaunch.png") );
		appLauncherButton.getDownFace().setImage( new Image("images/footerUIBar/appLaunch_touched.png") );
		appLauncherButton.setStyleName("gtvRemote-footerUIButton");
		appLauncherButton.setSize("120px","43px");
		
		moveUILeftButton = new PushButton("MoveUILeft");
		moveUILeftButton.getUpFace().setImage( new Image("images/footerUIBar/navigationPrev.png") );
		moveUILeftButton.getDownFace().setImage( new Image("images/footerUIBar/navigationPrev_touched.png") );
		moveUILeftButton.setStyleName("gtvRemote-footerUIButton");
		moveUILeftButton.setSize("120px","43px");
		
		moveUIRightButton = new PushButton("MoveUIRight");
		moveUIRightButton.getUpFace().setImage( new Image("images/footerUIBar/navigationNext.png") );
		moveUIRightButton.getDownFace().setImage( new Image("images/footerUIBar/navigationNext_touched.png") );
		moveUIRightButton.setStyleName("gtvRemote-footerUIButton");
		moveUIRightButton.setSize("120px","43px");
		

		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			this.add(appLauncherButton,  	98, 0);
			this.add(moveUILeftButton,       0, 0);
			this.add(moveUIRightButton,    200, 0);
		}
		else
		{
			this.add(appLauncherButton,      0, 0);
		}
		
		
	}
	
	
	
	public static native boolean isAnymoteSessionActive()  /*-{
		return $wnd.backgroundPageWindow.anymoteSessionActive;
	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;	
	
	
	
}
