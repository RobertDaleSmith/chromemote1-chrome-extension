package com.chromemote.crx.client.ui;

import java.util.ArrayList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppLauncherDrawerPanel extends AbsolutePanel{

	public  PushButton 	  settingsTabButton;
	public  PushButton 	  devicesTabButton;
	public  PushButton 	  aboutTabButton;
	private AbsolutePanel tabButtonPanel;
	private AbsolutePanel seperatorLayer;
	private AbsolutePanel settingsMenuPanel;
	private AbsolutePanel devicesMenuPanel;
	private AbsolutePanel aboutMenuPanel;
	private final Grid    menuPanelsGrid;
	private int appCount = 0;
	private ArrayList<AppButton> appButtons;
	private int appColumns;
	private int appRows;
	private Grid flowpanel;
	
	private CustomScrollPanel appLaunchScrollPanel;
	
	private String intentHeader = "intent:#Intent;" + "component=";
	private String intentFooter = ";end";
	
	public AppLauncherDrawerPanel()
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
		
		devicesTabButton = new PushButton("APPS");
		devicesTabButton.setStyleName("chromemote-TabBarButton");
		devicesTabButton.setSize("106px","28px");
		tabButtonPanel.add(devicesTabButton,     0,  0);
		
		settingsTabButton = new PushButton("CHANNELS");
		settingsTabButton.setStyleName("chromemote-TabBarButton");
		settingsTabButton.setSize("106px","28px");
		tabButtonPanel.add(settingsTabButton,  107,  0);
		
		aboutTabButton = new PushButton("MACROS");
		aboutTabButton.setStyleName("chromemote-TabBarButton");
		aboutTabButton.setSize("106px","28px");
		tabButtonPanel.add(aboutTabButton,     214,  0);

		this.add(tabButtonPanel, 0, 0);
		
		//Settings Menu Panels
		menuPanelsGrid = new Grid(1,3);
		menuPanelsGrid.setSize("960px","435px");
		menuPanelsGrid.setCellSpacing(0); 
		menuPanelsGrid.setCellPadding(0);
			
		devicesMenuPanel = new AbsolutePanel();

		appButtons = new ArrayList<AppButton>();
		
		addAppToList( new AppButton("Amazon"	 , "images/icons_APP_AMAZON.png"    	, intentHeader + "com.amazon.avod/com.amazon.avod.client.activity.HomeScreenActivity" + intentFooter) );
		
		addAppToList( new AppButton("Chrome" 	 , "images/icons_APP_CHROME.png"    	, intentHeader + "com.google.tv.chrome/.HubActivity" + intentFooter) );
		addAppToList( new AppButton("Clock"      , "images/icons_APP_CLOCK.png"     	, intentHeader + "com.android.deskclock/.DeskClock"  + intentFooter) );
		addAppToList( new AppButton("CNBC"       , "images/icons_APP_CNBC.png"      	, intentHeader + "com.nbc.cnbc.android.googletv/.ui.Splash" + intentFooter) );
		addAppToList( new AppButton("Downloads"  , "images/icons_APP_DOWNLOADS.png" 	, intentHeader + "com.android.providers.downloads.ui/.DownloadList" + intentFooter) );
		
		addAppToList( new AppButton("M-GO"		 , "images/icons_APP_MGO.png"    		, intentHeader + "com.technicolor.navi.mgo.gtv/.MainActivity" + intentFooter) );
		
		addAppToList( new AppButton("NBA"        , "images/icons_APP_NBA.png"       	, intentHeader + "com.nbadigital.gametimegtv/.ActivityManager" + intentFooter) );
		addAppToList( new AppButton("Netflix"    , "images/icons_APP_NETFLIX.png"   	, intentHeader + "com.google.tv.netflix/.NetflixActivity" + intentFooter) );
		addAppToList( new AppButton("OnLive"     , "images/icons_APP_ONLIVE.png"    	, intentHeader + "com.onlive.clientgtv/.OnLiveClientGTVActivity" + intentFooter) );
		addAppToList( new AppButton("Pandora"    , "images/icons_APP_PANDORA.png"   	, intentHeader + "com.pandora.android.gtv/com.pandora.android.Main" + intentFooter) );
		
		addAppToList( new AppButton("Pictures"   , "images/icons_APP_PICTURES.png"  	, intentHeader + "com.google.tv.mediabrowser/.newui.MainActivity" + intentFooter) );
		addAppToList( new AppButton("Play Movies", "images/icons_APP_PLAYMOVIES.png"	, intentHeader + "com.google.android.videos/com.google.android.youtube.videos.EntryPoint" + intentFooter) );
		addAppToList( new AppButton("Play Music" , "images/icons_APP_PLAYMUSIC.png" 	, intentHeader + "com.google.android.music/.activitymanagement.TopLevelActivity" + intentFooter) );
		addAppToList( new AppButton("Play Store" , "images/icons_APP_PLAYSTORE.png" 	, "market://search?id=" + "") );
		
		addAppToList( new AppButton("Podcasts"	 , "images/icons_APP_PODCASTS.png"  	, intentHeader + "com.google.android.apps.listen/.WelcomeActivity" + intentFooter) );
		addAppToList( new AppButton("Primetime"	 , "images/icons_APP_PRIMETIME.png" 	, intentHeader + "com.google.tv.alf/.ui.MainActivity" + intentFooter) );
		addAppToList( new AppButton("Search"	 , "images/icons_APP_SEARCH.png"	   	, intentHeader + "com.android.quicksearchbox/.SearchActivity" + intentFooter) );
		addAppToList( new AppButton("Settings"	 , "images/icons_APP_SETTINGS.png"  	, intentHeader + "com.google.tv.settings/.Settings" + intentFooter) );
		
		addAppToList( new AppButton("Spotlight"	 , "images/icons_APP_SPOTLIGHT.png" 	, "http://www.google.com/tv/spotlight-gallery.html") );
		addAppToList( new AppButton("TV"		 , "images/icons_APP_TV.png"		   	, "tv://") );
		addAppToList( new AppButton("Twitter"	 , "images/icons_APP_TWITTER.png"   	, intentHeader + "com.twitter.android.tv/com.twitter.android.LoginActivity" + intentFooter) );
		addAppToList( new AppButton("Voice"	 	 , "images/icons_APP_VOICESEARCH.png"	, intentHeader + "com.google.tv.voicesearch/.RecognitionActivity" + intentFooter) );
		
		addAppToList( new AppButton("VUDU"	  	 , "images/icons_APP_VUDU.png"    		, intentHeader + "com.vudu.google.tv.vuduapp/.VuduActivity" + intentFooter) );
		
		addAppToList( new AppButton("YouTube"	 , "images/icons_APP_YOUTUBE.png"		, intentHeader + "com.google.android.youtube.googletv/.MainActivity" + intentFooter) );
		
		

		
		
		appLaunchScrollPanel = new CustomScrollPanel();
		appLaunchScrollPanel.setSize("320px", "435px");
		appLaunchScrollPanel.setStyleName("gtvRemote-settingsMenuBarPanel");
		
		
		drawAppListToGrid();
		
		menuPanelsGrid.setWidget(0, 0, appLaunchScrollPanel);
		
		
		
		
		settingsMenuPanel = new AbsolutePanel();
		settingsMenuPanel.setSize("320px","435px");
		settingsMenuPanel.setStyleName("gtvRemote-settingsMenuBarPanel");

		
		
		
		
		
		
		//
		
		
		
		
		
		
		menuPanelsGrid.setWidget(0, 1, settingsMenuPanel);
		
		
		ScrollPanel aboutMenuScrollPanel = new ScrollPanel();
		aboutMenuScrollPanel.setSize("320px","435px");
		aboutMenuScrollPanel.setStyleName("gtvRemote-settingsMenuBarPanel");
		
		aboutMenuPanel = new AbsolutePanel();
		aboutMenuPanel.setSize("320px","435px");
		
		

		
		
		//
		
		
		
		
		
		
		
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
				
				//goToTab(1, 500);
				displayToast("Feature under development.<br>Coming soon!");
				settingsTabButton.setFocus(false);
			}
		});
		aboutTabButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				
				//goToTab(2, 500);
				displayToast("Feature under development.<br>Coming soon!");
				aboutTabButton.setFocus(false);
			}
		});
		
		
		
		
		
		HorizontalPanel menuBarLineSpace = new HorizontalPanel();
		menuBarLineSpace.setSize("320px", "2px");
		menuBarLineSpace.setStyleName("gtvRemote-settingsMenuBarLine");
		this.add(menuBarLineSpace, 0, 45);
		
		
		
		//Close Button
//		closeMenuButton = new PushButton("Collapse Settings");
//		closeMenuButton.getUpFace().setImage( new Image("images/menuBar/navigationCollapse.png") );
//		closeMenuButton.getDownFace().setImage( new Image("images/menuBar/navigationCollapse_touched.png") );
//		closeMenuButton.setStyleName("gtvRemote-menuBarButton");
//		closeMenuButton.setSize("320px","25px");
//		
//		this.add(closeMenuButton, 0, 455);
//		
//		final Element thisPointer = this.getElement();
//		closeMenuButton.addClickHandler(new ClickHandler(){
//			public void onClick(ClickEvent event) {
//				
//				PanelSlideAnimation animation = new PanelSlideAnimation( thisPointer );
//				animation.scrollTo( 0, -455, 500 );
//				closeMenuButton.setFocus(false);
//			}
//		});
		
		exportStaticMethod();
	}
	
	public void closeAppLauncher()
	{
		
		PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
		animation.scrollTo( 0, 505, 300 );
		this.goToTab(0, 0);
		
		
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
		

	
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();
   
	}-*/;
	
	public static native void enableKeyBoardEvents() /*-{
	
		$wnd.enableKeyBoardEvents();

	}-*/;
	

	
	public static native void displayToast(String msgString) /*-{
	
		$wnd.displayToast(msgString)
	
	}-*/;
	
	public static native void sendGAEvent(String category, String action) /*-{
	
		$wnd.sendGAEvent(category, action);

	}-*/;
	
	
	public static native void exportStaticMethod() /*-{
		
		//$wnd.setDiscoveryLabel  = $entry(@com.chromemote.crx.client.ui.AppLauncherDrawerPanel::setDiscoveryLabel(Ljava/lang/String;));
		
	}-*/;
	

	public void addAppToList(AppButton appButton)
	{
		appButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				closeAppLauncher();
			}
			
		});
		
		appButtons.add(appButton);

		appCount++;
	}
	
	public void drawAppListToGrid()
	{
		sortAppList();
		
		appColumns = 4;
		appRows    = appCount / appColumns;
		if (appCount % appColumns != 0)
			appRows++;
		
		int heightAdjust = 20 + ( (5+ 74) * appRows );
		devicesMenuPanel.setSize("320px", heightAdjust + "px");
			

		
		flowpanel = new Grid(appRows, appColumns);
		flowpanel.setWidth("280px");
		flowpanel.setCellSpacing(5); 
		flowpanel.setCellPadding(0);
		
		
		devicesMenuPanel.add(flowpanel, 20, 15);
		
		appLaunchScrollPanel.add(devicesMenuPanel);
		
		for(int appIndex = 0; appIndex < appCount; appIndex++)
		{
			int row = appIndex / appColumns;
			int col = appIndex % appColumns;
		
			flowpanel.setWidget(row, col, (Widget) appButtons.get(appIndex));
		}
		
	}
	
	public void sortAppList()
	{
		for (int index = 0; index < appCount-1; index++)
		{
			if(appButtons.get(index).getName().toLowerCase().compareTo( appButtons.get(index+1).getName().toLowerCase() ) > 0 )
			{
				AppButton tempAppButtonHolder = appButtons.get(index+1);
				
				appButtons.set( index+1,   appButtons.get(index) );
				
				appButtons.set( index, tempAppButtonHolder );
				
				index = -1;
				
			}
		}
		
		
		
	}
	
	
	
}
