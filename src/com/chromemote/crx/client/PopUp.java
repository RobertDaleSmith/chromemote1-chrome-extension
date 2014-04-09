
package com.chromemote.crx.client;

import com.chromemote.crx.client.ui.AppLauncherDrawerPanel;
import com.chromemote.crx.client.ui.CustomAdPanel;
import com.chromemote.crx.client.ui.RemoteButtonPanelsCore;
import com.chromemote.crx.client.ui.FooterUIMenuBarPanel;
import com.chromemote.crx.client.ui.PanelAnimationSlide;
import com.chromemote.crx.client.ui.SettingsDrawerPanel;
import com.chromemote.crx.client.ui.SettingsMenuBarPanel;
import com.chromemote.crx.client.ui.WelcomeDialogBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PopUp implements EntryPoint {
	
	private RootPanel rootPanel = RootPanel.get("remoteContainer");
	
	private RootPanel pinButtonRootPanel = RootPanel.get("pinButtonPanel");
	
	static SettingsDrawerPanel settingsDrawer;
	
	static AppLauncherDrawerPanel appLauncherDrawer;
	
	public void onModuleLoad() {

		//Disables Browser's Right-Click Context Menu
		disableRightClickMenu();
		
		
		//Debug Label to Display KEY_CODE Presses
		
		KeyCodeLabel debugLable = new KeyCodeLabel();
		final KeyCodeLabel debugLable2 = new KeyCodeLabel();
		
//		Label debugNameLable = new Label("From Label:  ");
//		Label debugNameLable2 = new Label("From Handler: ");
//		
//		rootPanel.add(debugNameLable,  10, 510);  rootPanel.add(debugLable,  125, 510);
//		rootPanel.add(debugNameLable2, 10, 525);  rootPanel.add(debugLable2, 125, 525);
		
		final RemoteButtonPanelsCore remotePanelsCore = new RemoteButtonPanelsCore(debugLable);
		remotePanelsCore.addKeyCodeHandler( new KeyCodeHandler(){
			public void onKeyCodeChange(String keyCodeString)
			{
				//keyCodeString = keyCodeString.replaceAll("SP_", "");
				debugLable2.setText(keyCodeString);
				
				//alert(keyCodeString);
				if(keyCodeString == "KEYCODE_ALL_POWER")
				{
					//not used, left from early prototyping
				}
			}
		});
		
		AbsolutePanel popUpUIPanel = new AbsolutePanel();
		
		AbsolutePanel buttonsPanel = new AbsolutePanel();
		
		
		setUiType();
		//toast(getTabOrPopUpType());
		
		
		
		CustomAdPanel customAdPanel = new CustomAdPanel();
		
		
		
		
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			buttonsPanel.add(remotePanelsCore, -320, 0);
			buttonsPanel.setSize("320px","480px");
			
			popUpUIPanel.add(buttonsPanel, 0, 25);			
			popUpUIPanel.setSize("320px","549px");
			
			rootPanel.add(popUpUIPanel);
			
			if(adsEnabled())
			{
				rootPanel.add(customAdPanel);
				rootPanel.setSize("320px","599px");
			}
			else
				rootPanel.setSize("320px","549px");
		}
		else
		{
			buttonsPanel.add(remotePanelsCore, 0, 0);
			buttonsPanel.setSize("960px","480px");
			
			popUpUIPanel.add(buttonsPanel, 0, 25);
			popUpUIPanel.setSize("960px","549px");
			
			rootPanel.add(popUpUIPanel);
			
			
			if(adsEnabled())
			{
				rootPanel.add(customAdPanel);
				rootPanel.setSize("960px","599px");
			}
			else
				rootPanel.setSize("960px","549px");
		}
		
		
		final FooterUIMenuBarPanel footerRemoteMenuBar = new FooterUIMenuBarPanel();
		
		footerRemoteMenuBar.moveUILeftButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(appLauncherDrawer.getAbsoluteTop() == 25)
				{

					closeAppLauncher();
					
				}
				closeConnectMan();
				remotePanelsCore.slideLeftAnimation();
				footerRemoteMenuBar.moveUILeftButton.setFocus(false);
				sendGAEvent("AltMenuBar", "Slide Panels Left");
				
				
			}
		});
		footerRemoteMenuBar.moveUIRightButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(appLauncherDrawer.getAbsoluteTop() == 25)
				{

					closeAppLauncher();
					
				}
				closeConnectMan();
				remotePanelsCore.slideRightAnimation();
				footerRemoteMenuBar.moveUIRightButton.setFocus(false);
				sendGAEvent("AltMenuBar", "Slide Panels Right");
				
			}
		});
		
		
		
		
		
		
		
		
		
				
		settingsDrawer = new SettingsDrawerPanel();
		popUpUIPanel.add(settingsDrawer,        0, -455);
		
		final SettingsMenuBarPanel remoteMenuBar = new SettingsMenuBarPanel();
		popUpUIPanel.add(remoteMenuBar,         0,  0);
		
		remoteMenuBar.deviceMenuButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(settingsDrawer.getAbsoluteTop() != 25)
				{
					PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
					animation.scrollTo( 0,   25, 300 );
					settingsDrawer.goToTab(0, 0);
					closeAppLauncher();
				}
				else
				{
					if (settingsDrawer.devicesTabButton.isEnabled() == false)
					{
						PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
						animation.scrollTo( 0, -455, 300 );
					}
					else
					{
						settingsDrawer.goToTab(0, 300);
					}
				}
				remoteMenuBar.deviceMenuButton.setFocus(false);
				sendGAEvent("MenuBar", "Device Manager");
			}
		});
		
		
		
		
		remoteMenuBar.settingsMenuButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(settingsDrawer.getAbsoluteTop() != 25)
				{
					PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
					animation.scrollTo( 0,   25, 300 );
					settingsDrawer.goToTab(1, 0);
					closeAppLauncher();
				}
				else
				{
					if (settingsDrawer.settingsTabButton.isEnabled() == false)
					{
						PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
						animation.scrollTo( 0, -455, 300 );
					}
					else
					{
						settingsDrawer.goToTab(1, 300);
					}
				}
				remoteMenuBar.settingsMenuButton.setFocus(false);
				sendGAEvent("MenuBar", "Settings");
			}
		});
						
		SettingsMenuBarPanel.statusMenuButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(settingsDrawer.getAbsoluteTop() != 25)
				{
					PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
					animation.scrollTo( 0,   25, 300 );
					settingsDrawer.goToTab(2, 0);
					closeAppLauncher();
				}
				else
				{
					if (settingsDrawer.aboutTabButton.isEnabled() == false)
					{
						PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
						animation.scrollTo( 0, -455, 300 );
					}
					else
					{
						settingsDrawer.goToTab(2, 300);
					}
				}
				SettingsMenuBarPanel.statusMenuButton.setFocus(false);
				sendGAEvent("MenuBar", "Status");
			}
		});
		
		
		
		appLauncherDrawer = new AppLauncherDrawerPanel();
		popUpUIPanel.add(appLauncherDrawer,        0, 505);
		
		footerRemoteMenuBar.appLauncherButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				if(appLauncherDrawer.getAbsoluteTop() != 25)
				{
					PanelAnimationSlide animation = new PanelAnimationSlide( appLauncherDrawer.getElement() );
					animation.scrollTo( 0,   25, 300 );
					closeConnectMan();
				}
				else
				{
					if (appLauncherDrawer.devicesTabButton.isEnabled() == false)
					{
						PanelAnimationSlide animation = new PanelAnimationSlide( appLauncherDrawer.getElement() );
						animation.scrollTo( 0, 505, 300 );
					}
					else
					{
						PanelAnimationSlide animation = new PanelAnimationSlide( appLauncherDrawer.getElement() );
						animation.scrollTo( 0, 505, 300 );
						appLauncherDrawer.goToTab(0, 0);
					}
				}
				footerRemoteMenuBar.appLauncherButton.setFocus(false);
				sendGAEvent("AltMenuBar", "App Launcher");
			}
		});
		
		popUpUIPanel.add(footerRemoteMenuBar,         0,  505);
		
		
		SettingsMenuBarPanel.pinMenuButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				openOptionsPage();
				
				SettingsMenuBarPanel.pinMenuButton.setFocus(false);
				
				sendGAEvent("MenuBar", "Pin to Tab");
			}
		});
		
		updatePairedDevicesList();
		
		exportStaticMethod();
		
		
		if( !firstInstallAck() ){
			WelcomeDialogBox welcomeDialogBox = new WelcomeDialogBox();
			welcomeDialogBox.Show();
		}
		
//		//Code for tabbed page pin button.
//		AbsolutePanel pinButtonPanel = new AbsolutePanel();
//		
//		final PushButton pinButton = new PushButton(new Image("pin_tab.png"));
//		pinButton.addClickHandler(new ClickHandler(){
//			public void onClick(ClickEvent event) {
//				pinCurrentTab();
//				pinButton.setFocus(false);
//			}	
//		});
//
//		pinButtonPanel.add(pinButton);
//		pinButtonPanel.setSize("32px","32px");
//		
//		pinButtonRootPanel.add(pinButtonPanel);
//		pinButtonRootPanel.setSize("32px","32px");
		
	}
	
	
	public static native void googletvremoteInitializePlugin() /*-{
			  
		$wnd.googletvremoteInitializePlugin();
            
	}-*/;
	
	public static native void anymoteConnectToExistingDevice() /*-{
	  
    	$wnd.anymoteConnectToExistingDevice();
    	    
	}-*/;
	
	public static native void updatePairedDevicesList() /*-{
	
		$wnd.updatePairedDevicesList();
		
	   
	}-*/;

	public void disableRightClickMenu()
	{
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.addDomHandler(new ContextMenuHandler() {

			@Override public void onContextMenu(ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		}, ContextMenuEvent.getType());
		
	}
	
	public static void openConnectMan()
	{
		
		PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
		animation.scrollTo( 0,   25, 300 );
		settingsDrawer.goToTab(0, 0);
		
	}
	
	public static void closeConnectMan()
	{
		
		PanelAnimationSlide animation = new PanelAnimationSlide( settingsDrawer.getElement() );
		animation.scrollTo( 0, -455, 300 );
		
	}
	
	public static void closeAppLauncher()
	{
		
		appLauncherDrawer.closeAppLauncher();
		
		
	}

	public static native void exportStaticMethod() /*-{
		
		$wnd.openConnectMan  = $entry(@com.chromemote.crx.client.PopUp::openConnectMan());
		$wnd.closeConnectMan = $entry(@com.chromemote.crx.client.PopUp::closeConnectMan());
		
	}-*/;
	
	public static native void startDiscovery()  /*-{
		
		$wnd.stopDiscoveryClient();
		$wnd.startDiscoveryClient();
		backgroundPageWindow.console.log('Discovery session started.');
		
	}-*/;
	
	public static native void pinCurrentTab()  /*-{
	
		$wnd.chrome.tabs.getSelected(null, function(tab) {
        	// Toggle the pinned status
        	$wnd.chrome.tabs.update(tab.id, {'pinned': !tab.pinned});
    	});
	
	}-*/;
	
	public static native void openOptionsPage()  /*-{
		
		$wnd.openOptionsPage();

	}-*/;
	
	public static native void sendGAEvent(String category, String action) /*-{
	
		$wnd.sendGAEvent(category, action);

	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;	
	
	public static native void toast(String msg) /*-{
		$wnd.displayToast(msg);
	}-*/;
	
	public static native void setUiType() /*-{
		$wnd.tabOrPopUp = $wnd.setUiType();
	}-*/;
	
	public static native boolean firstInstallAck()  /*-{
	
		return backgroundPageWindow.firstInstallAck;
	
	}-*/;
	
	public static native String getAdJsonString() /*-{
		return backgroundPageWindow.sponsorListJsonString;
	}-*/;
	
	public boolean adsEnabled()
	{	
		boolean isEnabled = true;
				
		if(!getAdJsonString().startsWith("*") && !getAdJsonString().startsWith("$"))
			isEnabled = true;
		else
			isEnabled = false;
		
		return isEnabled;
	}
}