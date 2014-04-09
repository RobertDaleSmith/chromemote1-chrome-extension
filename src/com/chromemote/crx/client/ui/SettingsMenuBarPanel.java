package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class SettingsMenuBarPanel extends AbsolutePanel{
	

	public PushButton deviceMenuButton;
	public static PushButton keyboardMenuButton;
	public static PushButton mouseMenuButton;
	public PushButton settingsMenuButton;
	public static PushButton statusMenuButton;
	public static PushButton pinMenuButton;
	
	
	
	public SettingsMenuBarPanel()
	{
		super();
		this.setStyleName("gtvRemote-menuBarPanel");
		
		
//		menuStatusLabel = new Label("Chromemote");
//		menuStatusLabel.setStyleName("gtvRemote-menuBarDeviceLabel");
//		menuStatusLabel.getElement().setId("title-bar-status");
		
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			this.setSize("320px","25px");
//			menuStatusLabel.setSize("200px","25px");
		}
		else
		{
			this.setSize("960px","25px");
//			menuStatusLabel.setSize("770px","25px");
		}
		
		
		
		
		deviceMenuButton = new PushButton("Devices");
		deviceMenuButton.getUpFace().setHTML( "<div class=\"gtvRemote-menuBarDeviceLabel\"><img src=\"images/menuBar/hardwareTV.png\"><div id=\"menuBarText\">Chromemote</div></div>");
		deviceMenuButton.getDownFace().setHTML( "<div class=\"gtvRemote-menuBarDeviceLabel-down-hovering\"><img src=\"images/menuBar/hardwareTV_touched.png\"><div id=\"menuBarText\">Chromemote</div></div>");
		deviceMenuButton.setStyleName("gtvRemote-menuBarButton");
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			
			deviceMenuButton.setSize("210px","25px");
		}
		else
		{
			
			deviceMenuButton.setSize("320px","25px");
		}
		
		
		keyboardMenuButton = new PushButton("Keyboard");
		keyboardMenuButton.getUpFace().setImage( new Image("images/menuBar/hardwareKeyboard.png") );
		keyboardMenuButton.getDownFace().setImage( new Image("images/menuBar/hardwareKeyboard.png") );
		keyboardMenuButton.setStyleName("gtvRemote-menuBarButton2");
		keyboardMenuButton.setSize("25px","25px");
		
		mouseMenuButton = new PushButton("Mouse");
		mouseMenuButton.getUpFace().setImage( new Image("images/menuBar/hardwareMouse.png") );
		mouseMenuButton.getDownFace().setImage( new Image("images/menuBar/hardwareMouse.png") );
		mouseMenuButton.setStyleName("gtvRemote-menuBarButton2");
		mouseMenuButton.setSize("25px","25px");
		hideMouseIcon();
		
		pinMenuButton = new PushButton("Pin");
		if(getTabOrPopUpType().compareTo("popup") != 0)
		{
			pinMenuButton.getUpFace().setImage( new Image("images/menuBar/pinToTab.png") );
			pinMenuButton.getDownFace().setImage( new Image("images/menuBar/pinToTab_touched.png") );
		}
		else
		{
			pinMenuButton.getUpFace().setImage( new Image("images/menuBar/fullTabMode.png") );
			pinMenuButton.getDownFace().setImage( new Image("images/menuBar/fullTabMode_touched.png") );
		}
		pinMenuButton.setStyleName("gtvRemote-menuBarButton");
		pinMenuButton.setSize("25px","25px");
		
		settingsMenuButton = new PushButton("Settings");
		settingsMenuButton.getUpFace().setImage( new Image("images/menuBar/actionSettings.png") );
		settingsMenuButton.getDownFace().setImage( new Image("images/menuBar/actionSettings_touched.png") );
		settingsMenuButton.setStyleName("gtvRemote-menuBarButton");
		settingsMenuButton.setSize("25px","25px");
		
		statusMenuButton = new PushButton("Status");
		statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessFound.png") );
		statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessFound_touched.png") );
		statusMenuButton.setStyleName("gtvRemote-menuBarButton");
		statusMenuButton.setSize("25px","25px");
		
		
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			this.add(deviceMenuButton,      0, 0);
			//this.add(menuStatusLabel,      35, 4);
			
			this.add(keyboardMenuButton,  215, 0);
			this.add(mouseMenuButton,     190, 0);
			
			this.add(pinMenuButton,       240, 0);
			this.add(settingsMenuButton,  265, 0);
			this.add(statusMenuButton,    290, 0);
		}
		else
		{
			this.add(deviceMenuButton,      0, 0);
			//this.add(menuStatusLabel,      35, 4);
			
			
			this.add(keyboardMenuButton,  855, 0);
			this.add(mouseMenuButton,     830, 0);
			this.add(pinMenuButton,       880, 0);
			this.add(settingsMenuButton,  905, 0);
			this.add(statusMenuButton,    930, 0);
		}
		
		
		exportStaticMethod();
		
	}
	
	public static native void exportStaticMethod() /*-{
		$wnd.indicatorFlash  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::indicatorFlash());
		$wnd.setIndicatorConnected  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::setIndicatorConnected());
		$wnd.setIndicatorDisconnected  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::setIndicatorDisconnected());
		
		$wnd.hideKeyboardIcon  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::hideKeyboardIcon());
		$wnd.showKeyboardIcon  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::showKeyboardIcon());
		
		$wnd.hideMouseIcon  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::hideMouseIcon());
		$wnd.showMouseIcon  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::showMouseIcon());
		
		$wnd.setStatusLabel  = $entry(@com.chromemote.crx.client.ui.SettingsMenuBarPanel::setStatusLabel(Ljava/lang/String;));
	}-*/;
	
	public static void hideKeyboardIcon()
	{
		keyboardMenuButton.setVisible(false);
	}
	public static void showKeyboardIcon()
	{
		keyboardMenuButton.setVisible(true);
	}
	public static void hideMouseIcon()
	{
		mouseMenuButton.setVisible(false);
	}
	public static void showMouseIcon()
	{
		mouseMenuButton.setVisible(true);
	}
	
	public static void setStatusLabel(String msg)
	{
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			//Ensure device name fits message box size.
		    String upToNCharacters = msg.substring(0, Math.min(msg.length(), 23));
			if (upToNCharacters != msg)
			{
				upToNCharacters = msg.substring(0, Math.min(msg.length(), 21));
				msg = upToNCharacters + "...";
			}
		}
		
		
		setStatus(msg);
		
		
//		menuStatusLabel.setText(msg);
//		deviceMenuButton.getUpFace().setHTML( "<div class=\"gtvRemote-menuBarDeviceLabel\"><img src=\"images/menuBar/hardwareTV.png\"><div id=\"menuBarText\">Chromemote</div></div>");
//		deviceMenuButton.getDownFace().setHTML( "<div class=\"gtvRemote-menuBarDeviceLabel-down-hovering\"><img src=\"images/menuBar/hardwareTV_touched.png\"><div id=\"menuBarText\">Chromemote</div></div>");
	}
	
	
	
	public static void indicatorFlash()
	{
		if( isAnymoteSessionActive() )
		{
			statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessSearching.png") );
			statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessSearching_touched.png") );
			Timer t1 = new Timer() {
				public void run() {
					statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessFound.png") );
					statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessFound_touched.png") );
				}
		    };
		    t1.schedule(150);
		}
		else
		{
			statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessLost.png") );
			statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessLost_touched.png") );
			Timer t1 = new Timer() {
				public void run() {
					statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessSearching.png") );
					statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessSearching_touched.png") );
				}
		    };
		    t1.schedule(150);
		}

	}
	
	public static native boolean isAnymoteSessionActive()  /*-{
		return $wnd.backgroundPageWindow.anymoteSessionActive;
	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;	
	
	public static void setIndicatorConnected()
	{
		statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessFound.png") );
		statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessFound_touched.png") );
	}
	
	public static void setIndicatorDisconnected()
	{
		statusMenuButton.getUpFace().setImage( new Image("images/menuBar/deviceAccessSearching.png") );
		statusMenuButton.getDownFace().setImage( new Image("images/menuBar/deviceAccessSearching_touched.png") );
	}
	
	public static native String setStatus(String msg) /*-{
		
		$wnd.setStatusBarText(msg);
	}-*/;	
	
}
