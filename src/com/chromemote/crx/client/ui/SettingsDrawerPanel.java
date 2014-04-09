package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SettingsDrawerPanel extends AbsolutePanel{

	public  PushButton 	  settingsTabButton;
	public  PushButton 	  devicesTabButton;
	private PushButton 	  closeMenuButton;
	public  PushButton 	  aboutTabButton;
	private AbsolutePanel tabButtonPanel;
	private AbsolutePanel seperatorLayer;
	private VerticalPanel settingsMenuPanel;
	private CustomScrollPanel   settingsScrollMenuPanel;
	private AbsolutePanel devicesMenuPanel;
	private AbsolutePanel aboutMenuPanel;
	private final Grid    menuPanelsGrid;
	private static Label discoveryStatusLabel;
	private static DeviceMenuScrollPanel deviceMenuScrollPanel;
	
	public SettingsDrawerPanel()
	{
		super();
		this.setSize("320px","480px");
		this.setStyleName("unselectable");
		
		//Tab Button Panel
		tabButtonPanel = new AbsolutePanel();
		tabButtonPanel.setSize("320px","45px");
		
		seperatorLayer = new AbsolutePanel();
		seperatorLayer.setStyleName("chromemote-TabBarSeperatorLayer");
		seperatorLayer.setSize("320px","23px");
		tabButtonPanel.add(seperatorLayer,       0,  0);
		
		devicesTabButton = new PushButton("DEVICES");
		devicesTabButton.setStyleName("chromemote-TabBarButton");
		devicesTabButton.setSize("106px","28px");
		tabButtonPanel.add(devicesTabButton,     0,  0);
		
		settingsTabButton = new PushButton("SETTINGS");
		settingsTabButton.setStyleName("chromemote-TabBarButton");
		settingsTabButton.setSize("106px","28px");
		tabButtonPanel.add(settingsTabButton,  107,  0);
		
		aboutTabButton = new PushButton("ABOUT");
		aboutTabButton.setStyleName("chromemote-TabBarButton");
		aboutTabButton.setSize("106px","28px");
		tabButtonPanel.add(aboutTabButton,     214,  0);

		this.add(tabButtonPanel, 0, 0);
		
		 	
		
		//Settings Menu Panels
		menuPanelsGrid = new Grid(1,3);
		menuPanelsGrid.setSize("960px","410px");
		menuPanelsGrid.setCellSpacing(0); 
		menuPanelsGrid.setCellPadding(0);
		
		devicesMenuPanel = new AbsolutePanel();
		devicesMenuPanel.setSize("320px","410px");
		devicesMenuPanel.setStyleName("gtvRemote-settingsMenuBarPanel");
			
		
		
		AbsolutePanel deviceDiscoverMenuPanel = new AbsolutePanel();
		deviceDiscoverMenuPanel.setSize("320px", "45px");
		deviceDiscoverMenuPanel.setStyleName("gtvRemote-deviceDiscoverMenuPanel");
		devicesMenuPanel.add(deviceDiscoverMenuPanel, 0, 0 );
		
		deviceMenuScrollPanel = new DeviceMenuScrollPanel();
		devicesMenuPanel.add(deviceMenuScrollPanel, 0, 49 );
		
		
		final PushButton discoveryStopButton = new PushButton(new Image("images/settingsPanels/device_discover.gif"), new Image("images/settingsPanels/device_discover_stop_touched.png") );
		discoveryStopButton.getUpHoveringFace().setImage(new Image("images/settingsPanels/device_discover_stop.png") );
		discoveryStopButton.setStyleName("gtvRemote-deviceMenuButtons");
		discoveryStopButton.getElement().setAttribute("alt", "Stop Discovery");
		discoveryStopButton.getElement().setId("stop-discovery");
		discoveryStopButton.setSize("45px","45px");
		discoveryStopButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				stopDiscoveryClient();
				discoveryStopButton.setFocus(false);
				setDiscoveryLabel("");
			}});
		deviceDiscoverMenuPanel.add(discoveryStopButton,  4, 0); 
		
		final PushButton discoveryButton = new PushButton(new Image("images/settingsPanels/device_discover.png"), new Image("images/settingsPanels/device_discover_touched.png") );
		discoveryButton.setStyleName("gtvRemote-deviceMenuButtons");
		discoveryStopButton.getElement().setAttribute("alt", "Start Discovery");
		discoveryButton.getElement().setId("start-discovery");
		discoveryButton.setSize("45px","45px");
		discoveryButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				startDiscoveryClient();
				discoveryButton.setFocus(false);
				setDiscoveryLabel("Discovering...");
			}});
		deviceDiscoverMenuPanel.add(discoveryButton,  4, 0);
		
		
		
		Image dividerImage = new Image("images/settingsPanels/divider.png");
		deviceDiscoverMenuPanel.add(dividerImage,  55, 12);
		
		discoveryStatusLabel = new Label("");
		discoveryStatusLabel.setStyleName("gtvremote-Discovery-status");
		discoveryStatusLabel.setSize("145px", "20px");
		deviceDiscoverMenuPanel.add(discoveryStatusLabel,  65, 16);
		
		
		final PushButton deviceDiscardButton = new PushButton(new Image("images/settingsPanels/device_discard.png"), new Image("images/settingsPanels/device_discard_touched.png") );
		deviceDiscardButton.setStyleName("gtvRemote-deviceMenuButtons");
		deviceDiscardButton.getElement().setAttribute("alt", "Clear All Devices");
		deviceDiscardButton.getElement().setId("clear-devices");
		deviceDiscardButton.setSize("45px","45px");
		deviceDiscardButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				deviceMenuScrollPanel.clearDeviceList();
				deviceDiscardButton.setFocus(false);
				
			}});
		deviceDiscoverMenuPanel.add(deviceDiscardButton,  210, 0); 
		
		final PushButton deviceAddButton = new PushButton(new Image("images/settingsPanels/device_add.png"), new Image("images/settingsPanels/device_add_touched.png") );
		deviceAddButton.setStyleName("gtvRemote-deviceMenuButtons");
		deviceDiscardButton.getElement().setAttribute("alt", "Manually Add Devices");
		deviceAddButton.getElement().setId("add-device");
		deviceAddButton.setSize("45px","45px");
		deviceDiscoverMenuPanel.add(deviceAddButton,  269, 0); 
		deviceAddButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				disableKeyBoardEvents();
				final AddDeviceDialogBox addDeviceDialogBox = new AddDeviceDialogBox();
				ClickHandler clickHandler = new ClickHandler()
			    {	public void onClick(ClickEvent event) 
					{
			    		if(addDeviceDialogBox.getIPResponse().compareTo("cancel") == 0)
			    		{
			    		    addDeviceDialogBox.Close();
			    		    enableKeyBoardEvents();
			    		}
			    		else if(addDeviceDialogBox.getIPResponse().compareTo("null") != 0)
			    		{
			    			//disableKeyBoardEvents();
			    			
			    			if(wasChallengeAccepted()){}
			    			DeviceMenuScrollPanel.startPinDialogBox(addDeviceDialogBox.getNameResponse(), addDeviceDialogBox.getIPResponse(), true);
			    			
			    			
			    		}	
			    			
					}
			    };
			    addDeviceDialogBox.addClickHandler(clickHandler);
			    addDeviceDialogBox.Show();
			    deviceAddButton.setFocus(false);
			    
			}
		});
		
		
		
		
		menuPanelsGrid.setWidget(0, 0, devicesMenuPanel);
		
		
		
		
		settingsMenuPanel = new VerticalPanel();
		settingsMenuPanel.setSize("320px","475px");
		//settingsMenuPanel.setStyleName("gtvRemote-settingsMenuBarPanel");
		
		settingsScrollMenuPanel = new CustomScrollPanel();
		settingsScrollMenuPanel.setSize("320px","410px");
		settingsScrollMenuPanel.setStyleName("gtvRemote-settingsMenuBarPanel");		
		
		
		
		HTML buttonConfigLabel = new HTML();
		buttonConfigLabel.setHTML("<table width=320 cellspacing=0 cellpadding=0>" +
			     "<tr>" +
			     	"<td width=10 height=40 vertical-align=middle align=center>" +
			     		"&nbsp;</td><td width=320>" + "Button Config" + 
			     	"</td>" +
			     "</tr>" +
			    "</table>");
		buttonConfigLabel.setStyleName("gtvRemote-scrollSettingsLabel");
		settingsMenuPanel.add(buttonConfigLabel);
		
		
		SettingsMenuScrollButton testButton = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_edit_buttons.png","Edit Remote Buttons", false);
		ClickHandler testButtonHandler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				
				launchRemoteButtonEditDialogBox();
				
				sendGAEvent("Settings", "Edit Remote Buttons");
			}
		};
		testButton.addClickHandler(testButtonHandler);
		settingsMenuPanel.add(testButton);
		
		SettingsMenuScrollButton editScrollButton = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_channel_list.png","Edit Scroll Button List", false);
		ClickHandler editScrollButtonHandler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				
				launchScrollButtonEditDialogBox();
				
				sendGAEvent("Settings", "Edit Scroll Button List");
			}
		};
		editScrollButton.addClickHandler(editScrollButtonHandler);
		settingsMenuPanel.add(editScrollButton);
		
		SettingsMenuScrollButton buttonDefaultsSettingsButton = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_default_buttons.png","Reset All To Default", false);
		ClickHandler buttonDefaultsSettingsButtonHandler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
								
				disableKeyBoardEvents();
				final ResetDefaultsDialogBox resetDefaultsDialogBox = new ResetDefaultsDialogBox("Are you sure you want to reset all custom remote button settings to the original default settings?");
				ClickHandler clickHandler = new ClickHandler()
			    {	public void onClick(ClickEvent event) 
					{
			    		if(resetDefaultsDialogBox.getResponse().compareTo("cancel") == 0)
			    		{
			    			resetDefaultsDialogBox.Close();
			    		    enableKeyBoardEvents();
			    		}
			    		else if(resetDefaultsDialogBox.getResponse().compareTo("null") != 0)
			    		{
			    			//disableKeyBoardEvents();
			    			if(resetDefaultsDialogBox.getResponse().compareTo("ok") == 0)
			    			{
			    				resetDefaultsDialogBox.Close();
			    				resetButtonSettingsToDefault();
			    			}
			    		}	
			    			
					}
			    };
			    resetDefaultsDialogBox.addClickHandler(clickHandler);
			    resetDefaultsDialogBox.Show();
			    deviceAddButton.setFocus(false);
				
				
				sendGAEvent("Settings", "Reset All To Default");
			}
		};
		buttonDefaultsSettingsButton.addClickHandler(buttonDefaultsSettingsButtonHandler);
		settingsMenuPanel.add(buttonDefaultsSettingsButton);
		
		SettingsMenuScrollButton testButton2 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_edit_macros.png","Edit Macros", false);
		ClickHandler testButton2Handler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				displayToast("Feature under development.<br>Coming soon!");
				sendGAEvent("Settings", "Edit Macros");
			}
		};
		testButton2.addClickHandler(testButton2Handler);
		settingsMenuPanel.add(testButton2);
		
		
		
		HTML appearanceLabel = new HTML();
		appearanceLabel.setHTML("<table width=320 cellspacing=0 cellpadding=0>" +
			     "<tr>" +
			     	"<td width=10 height=30 vertical-align=middle align=center>" +
			     		"&nbsp;</td><td>" + "Appearance" + 
			     	"</td>" +
			     "</tr>" +
			    "</table>");
		appearanceLabel.setStyleName("gtvRemote-scrollSettingsLabel");
		settingsMenuPanel.add(appearanceLabel);
		
		
		
		SettingsMenuScrollButton testButton3 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_select_theme.png","Select Theme", false);
		ClickHandler testButton3Handler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				displayToast("Feature under development.<br>Coming soon!");
				sendGAEvent("Settings", "Select Theme");
			}
		};
		testButton3.addClickHandler(testButton3Handler);
		settingsMenuPanel.add(testButton3);
		
		SettingsMenuScrollButton testButton6 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_full_mode.png","Open Full Tab Mode", false);
		if(getTabOrPopUpType().compareTo("tab") == 0)
			testButton6 = new SettingsMenuScrollButton("images/settingsPanels/pin_full_tab_mode.png","Pin Full Tab Mode", false);
		ClickHandler testButton6Handler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				
				openOptionsPage();
				
				sendGAEvent("Settings", "Open Full Tab Mode");
			}
		};
		testButton6.addClickHandler(testButton6Handler);
		settingsMenuPanel.add(testButton6);
		
		final SettingsMenuScrollButton testButton5 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_lights_out.png","Lights Out", true);
		ClickHandler testButton5Handler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				
				
				testButton5.toggleOnOff();
				toggleBGImage();
				
				if(lightsOutIsOn())
					sendGAEvent("Settings", "Lights Out");
				else
					sendGAEvent("Settings", "Lights On");
			}
		};
		//testButton5.toggleOnOff();
		testButton5.addClickHandler(testButton5Handler);
		settingsMenuPanel.add(testButton5);
		
		
		if(lightsOutIsOn())
			testButton5.toggleOnOff();
		
		
		
		HTML miscLabel = new HTML();
		miscLabel.setHTML("<table width=320 cellspacing=0 cellpadding=0>" +
			     "<tr>" +
			     	"<td width=10 height=30 vertical-align=middle align=center>" +
			     		"&nbsp;</td><td>" + "Other" + 
			     	"</td>" +
			     "</tr>" +
			    "</table>");
		miscLabel.setStyleName("gtvRemote-scrollSettingsLabel");
		settingsMenuPanel.add(miscLabel);
		
		SettingsMenuScrollButton testButton4 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_edit_changelog.png","Changelogs", false);
		ClickHandler testButton4Handler = new ClickHandler(){
			public void onClick(ClickEvent event) {				
				
				WelcomeDialogBox welcomeDialogBox = new WelcomeDialogBox();
				welcomeDialogBox.Show();
				closeConnectMan();
				
				sendGAEvent("Settings", "Changelogs");
				
			}
		};
		testButton4.addClickHandler(testButton4Handler);
		settingsMenuPanel.add(testButton4);
		
		
		
		
//		HTML miscLabel = new HTML();
//		miscLabel.setHTML("<table width=320 cellspacing=0 cellpadding=0>" +
//			     "<tr>" +
//			     	"<td width=10 height=30 vertical-align=middle align=center>" +
//			     		"&nbsp;</td><td>" + "TV Connector" + 
//			     	"</td>" +
//			     "</tr>" +
//			    "</table>");
//		miscLabel.setStyleName("gtvRemote-scrollSettingsLabel");
//		settingsMenuPanel.add(miscLabel, 0, 303 );
//		
//		
//		SettingsMenuScrollButton testButton5 = new SettingsMenuScrollButton("images/settingsPanels/settings_menu_channel_list.png","Channel List", true);
//		ClickHandler testButton5Handler = new ClickHandler(){
//			public void onClick(ClickEvent event) {				
//				displayToast("Feature under development.<br>Coming soon!");
//				sendGAEvent("Settings", "Channel List");
//			}
//		};
//		testButton5.addClickHandler(testButton5Handler);
//		settingsMenuPanel.add(testButton5, 0, 336 );
		
		settingsScrollMenuPanel.add(settingsMenuPanel);
		
		menuPanelsGrid.setWidget(0, 1, settingsScrollMenuPanel);
		
		
		ScrollPanel aboutMenuScrollPanel = new ScrollPanel();
		aboutMenuScrollPanel.setSize("320px","410px");
		aboutMenuScrollPanel.setStyleName("gtvRemote-settingsMenuBarPanel");
		
		aboutMenuPanel = new AbsolutePanel();
		aboutMenuPanel.setSize("320px","410px");
		
		Image appLogoImage = new Image("images/settingsPanels/settings_about_logo.png");
		aboutMenuPanel.add(appLogoImage, 20, 16);
			
		Label appVersionLabel = new Label("v."+getVersionNumber());
		appVersionLabel.setStyleName("chromemote-settingsAppVersionLabel");
		aboutMenuPanel.add(appVersionLabel, 70, 71 );
		
		HTML appWwwHTML = new HTML("<a href=\"http://www.chromemote.com/\"  target=\"_blank\">chromemote.com</> ");
		appWwwHTML.setStyleName("chromemote-settingsAppWwwHTML");
		aboutMenuPanel.add(appWwwHTML, 165, 70 );
		
//		HTML appGooglePlusHTML = new HTML("<g:plusone size=\"medium\" href=\"https://plus.google.com/107890677565458476428\"></g:plusone>");
//		appGooglePlusHTML.setStyleName("chromemote-settingsAppWwwHTML");
//		aboutMenuPanel.add(appGooglePlusHTML, 81, 67 );
		
//		HTML appFacebookLikeHTML = new HTML("<div class=\"fb-like\" data-href=\"http://www.facebook.com/chromemote\" data-send=\"false\" data-layout=\"button_count\" data-width=\"150\" data-show-faces=\"false\" data-colorscheme=\"dark\" data-font=\"tahoma\"></div>");
//		appFacebookLikeHTML.setStyleName("chromemote-settingsAppWwwHTML");
//		aboutMenuPanel.add(appFacebookLikeHTML, 175, 125 );
//		
//		HTML appTwitterHTML = new HTML("<a href=\"https://twitter.com/share\" class=\"twitter-share-button\" data-via=\"RobertDaleSmith\">Tweet</a>");
//		appTwitterHTML.setStyleName("chromemote-settingsAppWwwHTML");
//		aboutMenuPanel.add(appTwitterHTML, 225, 125 );
		
//		Image appDevImage = new Image("images/settingsPanels/settings_about_dev.png");
//		aboutMenuPanel.add(appDevImage, 25, 105);
		
		HTML betaDevices3Label = new HTML("<img src=\"images/settingsPanels/settings_about_dev2.png\" align=\"left\">" +
				"&nbsp;Hi, I'm Robert Dale Smith, the developer of chrome<em><b>mote</b></em>!<br>" +
				"chrome<em><b>mote</b></em> is \"honor-ware\". This means that you <b>pay what you can afford</b>" +
				", and there's no enforcement except the <b>honor system</b>. I'm trusting you. " +
				"In fact, I <b>quit my job</b> to work on chrome<em><b>mote</b></em>, so I'm trusting you a lot." +
				"<br><br>" +
				"&nbsp;Please pay as much as you can. Am I naive for trusting you to pay? I guess I'll find out :) ");
		betaDevices3Label.setWidth("268px");
		betaDevices3Label.setStyleName("chromemote-settingsAppInfoLabel");
		aboutMenuPanel.add(betaDevices3Label, 24, 105 );
		
		
//		HTML payPalHTML = new HTML("<a href=\"http://www.chromemote.com/tipjar/\" target=\"_blank\"" +
//								   "onClick=\"_gaq.push(['_trackEvent', 'About', 'Donate']);\"" +
//								   "><img src=\"images/donateb.png\" border=\'0\'></a>");
//		payPalHTML.setStyleName("chromemote-settingsAppWwwHTML");
//		aboutMenuPanel.add(payPalHTML, 115, 320 );
		
		PushButton donateButton;
		ClickHandler donateButtonHandler = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				sendGAEvent("About","Donate");
				Window.open("http://chromemote.com/support-us/", "_blank", null);
			}
	    };
	    
	    donateButton = new PushButton("", donateButtonHandler);
	    donateButton.setSize("120px", "38px");
	    donateButton.setStyleName("chromemote-donate-PushButton");
	    aboutMenuPanel.add(donateButton, 100, 322);
		
		
		HTML reportBugsHTML = new HTML("<a href=\"https://chrome.google.com/webstore/support/bhcjclaangpnjgfllaoodflclpdfcegb?hl=en&gl=US#bug\" onClick=\"_gaq.push(['_trackEvent', 'About', 'Report Bugs']);\" target=\"_blank\">Report Bugs</a>" +
				"&nbsp;|&nbsp;" +
				"<a href=\"https://chrome.google.com/webstore/support/bhcjclaangpnjgfllaoodflclpdfcegb?hl=en&gl=US#feature\" onClick=\"_gaq.push(['_trackEvent', 'About', 'Suggest Features']);\" target=\"_blank\">Suggest Features</a>" +
				"&nbsp;|&nbsp;" +
				"<a href=\"https://chrome.google.com/webstore/support/bhcjclaangpnjgfllaoodflclpdfcegb?hl=en&gl=US#question\" onClick=\"_gaq.push(['_trackEvent', 'About', 'Ask Questions']);\" target=\"_blank\">Ask Questions</a>");
		reportBugsHTML.setWidth("270px");
		reportBugsHTML.setStyleName("chromemote-settingsAppBugsLabel");
		aboutMenuPanel.add(reportBugsHTML, 25, 370 );
		
		HTML checkForUpdatesHTML = new HTML("<a href=\"http://chromemote.com/update/\" onClick=\"_gaq.push(['_trackEvent', 'About', 'Update']);\" target=\"_blank\">Check for Updates</a>");
		checkForUpdatesHTML.setWidth("260px");
		checkForUpdatesHTML.setStyleName("chromemote-settingsAppBugsLabel");
		aboutMenuPanel.add(checkForUpdatesHTML, 25, 390 );
		
		aboutMenuScrollPanel.add(aboutMenuPanel);
		menuPanelsGrid.setWidget(0, 2, aboutMenuScrollPanel);
		
		this.add(menuPanelsGrid, 0, 45);
		devicesTabButton.setEnabled(false);
		
		
		
		//Tab Button Panel's ClickHandler for moving between Settings Menu Panels.
		devicesTabButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				goToTab(0, 500);
				devicesTabButton.setFocus(false);
			}
		});
		settingsTabButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				goToTab(1, 500);
				settingsTabButton.setFocus(false);
			}
		});
		aboutTabButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				goToTab(2, 500);
				aboutTabButton.setFocus(false);
			}
		});
		
		
		
		
		
		HorizontalPanel menuBarLineSpace = new HorizontalPanel();
		menuBarLineSpace.setSize("320px", "2px");
		menuBarLineSpace.setStyleName("gtvRemote-settingsMenuBarLine");
		this.add(menuBarLineSpace, 0, 45);
		
		
		
		//Close Button
		closeMenuButton = new PushButton("Collapse Settings");
		closeMenuButton.getUpFace().setImage( new Image("images/menuBar/navigationCollapse.png") );
		closeMenuButton.getDownFace().setImage( new Image("images/menuBar/navigationCollapse_touched.png") );
		closeMenuButton.setStyleName("gtvRemote-menuBarButton");
		closeMenuButton.setSize("320px","25px");
		
		this.add(closeMenuButton, 0, 455);
		
		final Element thisPointer = this.getElement();
		closeMenuButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				PanelAnimationSlide animation = new PanelAnimationSlide( thisPointer );
				animation.scrollTo( 0, -455, 500 );
				closeMenuButton.setFocus(false);
			}
		});
		
		exportStaticMethod();
	}
	
	public void goToTab(int tabIndex, int animationSpeed)
	{
		if (tabIndex == 0)
		{
			PanelAnimationSlide animation = new PanelAnimationSlide( menuPanelsGrid.getElement() );
			animation.scrollTo( 0,    45, animationSpeed );
			devicesTabButton.setEnabled(false);
			settingsTabButton.setEnabled(true);
			aboutTabButton.setEnabled(true);
			
		}
		else if (tabIndex == 1)
		{	
			PanelAnimationSlide animation = new PanelAnimationSlide( menuPanelsGrid.getElement() );
			animation.scrollTo( -320, 45, animationSpeed );
			devicesTabButton.setEnabled(true);
			settingsTabButton.setEnabled(false);
			aboutTabButton.setEnabled(true);
		}
		else if (tabIndex == 2)
		{
			PanelAnimationSlide animation = new PanelAnimationSlide( menuPanelsGrid.getElement() );
			animation.scrollTo( -640, 45, animationSpeed );
			devicesTabButton.setEnabled(true);
			settingsTabButton.setEnabled(true);
			aboutTabButton.setEnabled(false);
		}
		
	}
		
	public static void setDiscoveryLabel(String msg)
	{

		if (msg.isEmpty())
		{
			int deviceCount = deviceMenuScrollPanel.getDeviceCount();
			
			if(deviceCount != 1)
				setDiscoveryLabel("" + deviceCount + " Google TVs Found");
			else
				setDiscoveryLabel("" + deviceCount + " Google TV Found");
		}
		else
		{
			discoveryStatusLabel.setText(msg);
		}
		
		
	}
	
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();
   
	}-*/;
	
	public static native void enableKeyBoardEvents() /*-{
	
		$wnd.enableKeyBoardEvents();

	}-*/;
	
	public static native void pairNewDevice(String deviceName, String deviceIP) /*-{
	  
		$wnd.pairingSessionPair(deviceName, deviceIP);
		$wnd.disableKeyBoardEvents();

	}-*/;
	
	public static native void addDevice(String googleTvName, String googleTvIpAddress) /*-{
		
		window.addDevice(googleTvName, googleTvIpAddress, false);
		
	}-*/;
	
	public static native boolean wasChallengeAccepted() /*-{
	
		return $wnd.wasChallengeAccepted();
	
	}-*/;
	
	public static native void startDiscoveryClient() /*-{
	
		$wnd.startDiscoveryClient();

	}-*/;
	
	public static native void stopDiscoveryClient() /*-{
	
		$wnd.stopDiscoveryClient();

	}-*/;
	

	
	public static native void initializePlugin() /*-{
		
		$wnd.backgroundPageWindow.console.log('Reinitializing Google TV Remote Plumbing Plugin.');
		$wnd.backgroundPageWindow.googletvremoteInitializePlugin();
		$wnd.googletvremoteInitializePlugin();
		$wnd.backgroundPageWindow.anymoteSessionActive = false;
		$wnd.anymoteConnectToExistingDevice();
		
	}-*/;
	
	public static native void displayToast(String msgString) /*-{
	
		$wnd.displayToast(msgString);
	
	}-*/;
	
	public static native void sendGAEvent(String category, String action) /*-{
	
		$wnd.sendGAEvent(category, action);

	}-*/;
	
	public static native void closeConnectMan() /*-{
	
		$wnd.closeConnectMan();
	
	}-*/;
	
	public static native void launchRemoteButtonEditDialogBox() /*-{
	
		$wnd.closeConnectMan();
		$wnd.launchButtonEditDialogBox();
	
	}-*/;
	
	public static native void launchScrollButtonEditDialogBox() /*-{
	
		$wnd.closeConnectMan();
		$wnd.launchScrollButtonEditDialogBox(1);
	
	}-*/;
	
	public static native void resetButtonSettingsToDefault() /*-{
	
		$wnd.backgroundPageWindow.setDefaultJsonStrings();
		$wnd.document.location.reload(true);
		
	}-*/;
	
	public static native void exportStaticMethod() /*-{
		
		$wnd.setDiscoveryLabel  = $entry(@com.chromemote.crx.client.ui.SettingsDrawerPanel::setDiscoveryLabel(Ljava/lang/String;));
		
	}-*/;
	
	public static native void toggleBGImage() /*-{
	
		$wnd.toggleBGImage();
		
	}-*/;
	
	
	public static native boolean lightsOutIsOn() /*-{
		
		var lightsOutEnabled = backgroundPageWindow.localStorage.getItem('lights-out');
		
		if(lightsOutEnabled == 'true')
			return true;
		else
			return false;
	}-*/;
	
	public static native void openOptionsPage()  /*-{
	
		$wnd.openOptionsPage();

	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;	
	
	public static native String getVersionNumber() /*-{
		return $wnd.backgroundPageWindow.appVersion;
		
	}-*/;	
}
