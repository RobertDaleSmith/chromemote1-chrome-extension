package com.chromemote.crx.client.ui;

import com.chromemote.crx.client.KeyCodeLabel;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;

public class RemoteButtonPanel extends AbsolutePanel {

	private Grid topButtonGrid, midButtonGrid, lowButtonGrid;
	
	private Image btnHighlightImage = new Image("images/button_highlight.png");
	
	private KeyCodeLabel keyCodePressedLabel;
	
	private RemoteButton remoteButtons[];
	
	private RemoteDPadButton dPadButton;
	
	private RemoteTPadButton touchPadPanel;
	
	private String panelType;
	
	private ClickHandler keyCodeHandler;
	
	private boolean touchPadIsOpen = false;
	
	private boolean initPointerLock = false;
	
	private boolean canPointerLock = false;
	
	private boolean tPadVisible = false;
	
	private RemoteButton allRemoteButtons[][];
	
	private int panelIndex;
	
	boolean isMouseDown = false;
	
	public RemoteButtonPanel(String jsonUrlPathString, final String panelTypeString, final KeyCodeLabel callBackCodelabel, ClickHandler clickHandler, RemoteButton buttons[][])
	{
		allRemoteButtons = buttons; 
		panelType = panelTypeString.toLowerCase();
		keyCodePressedLabel = callBackCodelabel;
		keyCodeHandler = clickHandler;
		
		//loadButtonPropertiesList(jsonUrlPathString); //deprecated
		
		//displayError(getJsonString(URL.encode(jsonUrlPathString)));
		
		String jsonString = "";
		if (panelTypeString.equals("primary"))
		{
			panelIndex = 1;
			
			jsonString = getJsonString(panelIndex);
			
			remoteButtons = allRemoteButtons[panelIndex];
		}
		else if (panelTypeString.equals("secondary"))
		{
			panelIndex = 2;
			
			jsonString = getJsonString(panelIndex);
			
			remoteButtons = allRemoteButtons[panelIndex];
		}
		
		parseJsonPanelButtons( jsonString );
		drawButtonGrid();
		
		exportStaticMethod();
	}
	
	private void showBtnHighlight(int xElementInt, int yElementInt, Boolean isWide)
	{
		final int btnHeightInt = 60;
		final int btnWidthInt;
		final int widthOffset;
		final int posXOffset;
		int posXInt = xElementInt * 80;
		int posYInt = yElementInt * 60;
		
		if (isWide)
		{	btnWidthInt = 160;	widthOffset = 60;	posXOffset  = 14;	}
		else
		{	btnWidthInt = 80;	widthOffset = 30;	posXOffset  = 0;	}
		
		this.setWidgetPosition(btnHighlightImage, posXInt-posXOffset-16, posYInt-11);
		btnHighlightImage.setSize(btnWidthInt+widthOffset + "px", btnHeightInt+22 + "px");
		btnHighlightImage.setVisible(true);
	}
	
	private void drawHighlightImage()
	{
		btnHighlightImage.setVisible(false);
		this.add(btnHighlightImage, 0, 0);
		this.setSize("320px", "480px");
	}
	
	private void buttonCallListener(String callCode)
	{
		if (callCode == "TOUCHPAD_SHOWHIDE")
		{
			if (getTabOrPopUpType().compareTo("popup") == 0) 
			{	
				if (!tPadVisible)
					showTouchPad( true );
				else
					showTouchPad( false );
			}
			
			
			//Check if Pointer Lock is supported.
			if ( canPointerLock() )
			{
				if (getTabOrPopUpType().compareTo("tab") == 0 )
				{	//toast("Pointer Lock Supported.");
					canPointerLock = true;
				}
			}
			else
			{
				toast("Your version of Chrome does not support Pointer Lock. Upgrade to 22+");
			}
			
			if (canPointerLock && isGtvConnected() )
			{
				if(!initPointerLock)
				{
					initPointerLock();
					initPointerLock = true;
				}
				activatePointerLock();
				
			}
			
		}
		


		keyCodePressedLabel.setText(callCode);
	}
	
	private void setDefaultHandlers(final RemoteButton remoteButton, int y, int x)
	{
		
		//final String keyCodeString = remoteButton.getKeyCode();
		final int xInt = x;
		final int yInt = y;
		final Boolean isWide = remoteButton.isWide();
		
		remoteButton.addClickHandler(new ClickHandler() 
		{ 
			public void onClick(ClickEvent event) 
			{ 
				String keyCodeString = remoteButton.getKeyCode();
				buttonCallListener(keyCodeString);
				//sendButtonKeyEvent( keyCodeString.replaceAll("KEYCODE_", "") );
				//toast(keyCodeString.replaceAll("KEYCODE_", ""));
				
			} 
		});
		
		
		
		
		remoteButton.addMouseDownHandler(new MouseDownHandler() 
		{ 
			public void onMouseDown(MouseDownEvent event) 
			{
				isMouseDown = true;
				if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
				{ 
					showBtnHighlight(xInt, yInt , isWide); 
					
					sendButtonKeyEvent( remoteButton.getKeyCode().replaceAll("KEYCODE_", ""), true );
					
//					Timer t = new Timer() {
//						public void run() {
//							Timer timer = new Timer() {
//								public void run() {
//									if(isMouseDown)
//										sendButtonKeyEvent( remoteButton.getKeyCode().replaceAll("KEYCODE_", "") );
//									else
//										this.cancel();
//									
//							    }
//							};
//							timer.scheduleRepeating(200);    // Schedule the timer to run once in 0.4 seconds.
//							if(!isMouseDown)
//								timer.cancel();
//					    }
//					};
//					t.schedule(600);    // Schedule the timer to run once in 0.4 seconds.
//					if(!isMouseDown)
//						t.cancel();
				}
				else
				{
					
					if (remoteButton.getKeyCode() == "TOUCHPAD_SHOWHIDE")
					{
						
							if (!tPadVisible)
								showTouchPad( true );
							else
								showTouchPad( false );
						
					}
					else
					{
						int x = event.getClientX();
						int y = event.getClientY();
						
						final DeviceContextMenu deviceContextMenu = new DeviceContextMenu(x,y);
						
						deviceContextMenu.addMenuCommand("Edit", new ClickHandler(){public void onClick(ClickEvent event) { 
							
							disableKeyBoardEvents();
							final EditRemoteButtonDialogBox editRemoteButtonDialogBox = new EditRemoteButtonDialogBox(allRemoteButtons, panelIndex, remoteButton.getCellIndex());
							ClickHandler clickListener = new ClickHandler()
						    {	public void onClick(ClickEvent event) 
								{
						    		if(editRemoteButtonDialogBox.getIPResponse().compareTo("cancel") == 0)
						    		{
						    			editRemoteButtonDialogBox.Close();
						    		    enableKeyBoardEvents();
						    		}
						    		else if(editRemoteButtonDialogBox.getNameResponse().compareTo("null") != 0 && editRemoteButtonDialogBox.getNameResponse().compareTo("") != 0)
						    		{
						    		    enableKeyBoardEvents();
						    		    
						    		}	
						    			
								}
						    };
						    editRemoteButtonDialogBox.addClickListener(clickListener);
						    editRemoteButtonDialogBox.Show();
							
							
						}});
						
						deviceContextMenu.addMenuCommand("Move Up"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(panelIndex, remoteButton.getCellIndex(), "UP");
						}});
						deviceContextMenu.addMenuCommand("Move Down"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(panelIndex, remoteButton.getCellIndex(), "DOWN");
						}});
						deviceContextMenu.addMenuCommand("Move Left"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(panelIndex, remoteButton.getCellIndex(), "LEFT");				
						}});
						deviceContextMenu.addMenuCommand("Move Right"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(panelIndex, remoteButton.getCellIndex(), "RIGHT");
						}});
						
						deviceContextMenu.Show();
					}
					
					//Handle Right Click of Remote Buttons. Will be used for button customization.
					//toast(remoteButton.getKeyCode());
				}
			}
		});
		
		remoteButton.addMouseUpHandler(new MouseUpHandler() 
		{ 
			public void onMouseUp(MouseUpEvent event) 
			{ 
				sendButtonKeyEvent( remoteButton.getKeyCode().replaceAll("KEYCODE_", ""), false );
				
				
				isMouseDown = false;
				
				btnHighlightImage.setVisible(false); 
				remoteButton.setFocus(false);
			}
		});
		
		remoteButton.addFocusHandler(new FocusHandler()  
		{	//Resolves bug when pop up opens the first button is auto focused. Which 
			//causes problems when space bar/enter is used for keyboard input.
			public void onFocus(FocusEvent event) {
				remoteButton.setFocus(false);
			}
		});

		
	}
	
	private void addClickHandler(RemoteButton remoteButton, ClickHandler clickHandler)
	{
		remoteButton.addClickHandler( clickHandler );
	}
		
	private void drawButtonGrid() {
		if ( panelType == "primary" )
		{
			drawPrimaryGrid(remoteButtons);
			drawTPadButton();
			drawHighlightImage();
			drawDPadButton();
		}
		if ( panelType == "secondary" )
		{
			drawSecondaryGrid(remoteButtons);
			drawHighlightImage();
		}
	}
	
	private void drawPrimaryGrid(RemoteButton remoteButtons[])
	{
		
		remoteButtons[4].setAltStyle( true );
		if (remoteButtons[4].hasIconImage())
		{
			if (getTabOrPopUpType().compareTo("popup") == 0 )
			{
				remoteButtons[4].setIconImage("images/icons_ui_SHOW_TOUCHPAD.png", "images/icons_ui_SHOW_TOUCHPAD_touched.png");
			}
			else
			{
				remoteButtons[4].setIconImage("images/icons_ui_LOCK_MOUSE.png", "images/icons_ui_LOCK_MOUSE_touched.png");
			}
		}
		else
		{
			remoteButtons[4].getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Cursor" );
			remoteButtons[4].getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Cursor" );
		}
		
		remoteButtons[4].setKeyCode( "TOUCHPAD_SHOWHIDE" );
		remoteButtons[4].getElement().setId("TOUCHPAD_SHOWHIDE");
				
		remoteButtons[0] = new RemoteButton(4);
		remoteButtons[0].setName("");
		
		
		topButtonGrid = new Grid(4, 4);
		topButtonGrid.setCellPadding(0);
		topButtonGrid.setCellSpacing(0);
		topButtonGrid.setSize("320px", "240px");
		
		for (int i = 1, y = 0 ; i <= 16 ; y++)
		{
			for (int x = 0 ; i <= 16 && x < 4 ; i++, x++)
			{	
				setDefaultHandlers(remoteButtons[i], y,  x);
				addClickHandler(remoteButtons[i], keyCodeHandler);
				
				
				if(i == 4)		//  Fills grid position with blank button with background matching index. 
				{				//  This will be hidden with show touch-pad button.
					
					topButtonGrid.setWidget(y, x, remoteButtons[0]);
				}
				else
				{
					topButtonGrid.setWidget(y, x, remoteButtons[i]);
				}
				
			}
			
		}
		
		midButtonGrid = new Grid(2, 2);
		midButtonGrid.setCellPadding(0);
		midButtonGrid.setCellSpacing(0);
		midButtonGrid.setSize("320px", "120px");
		
		for (int i = 17, y = 0 ; i <= 24 ; y++)
		{
			for (int x = 0 ; i <= 24 && x < 2 ; i=i+2, x++)
			{
				int xOffset = 0;
				
				if (i == 19 || i == 23)
					xOffset = 1;
				
				remoteButtons[i].makeWide();
				setDefaultHandlers(remoteButtons[i], y+4,  x+xOffset);
				addClickHandler(remoteButtons[i], keyCodeHandler);
				midButtonGrid.setWidget(y, x, remoteButtons[i]);
			}
		}
		
		lowButtonGrid = new Grid(4, 4);
		lowButtonGrid.setCellPadding(0);
		lowButtonGrid.setCellSpacing(0);
		lowButtonGrid.setSize("320px", "120px");
		
		for (int i = 25, y = 0 ; i <= 32 ; y++)
		{
			for (int x = 0 ; i <= 32 && x < 4 ; i++, x++)
			{
				setDefaultHandlers(remoteButtons[i], y+6,  x);
				addClickHandler(remoteButtons[i], keyCodeHandler);
				lowButtonGrid.setWidget(y, x, remoteButtons[i]);
			}
		}
		
		
		
		
		this.add(topButtonGrid,       0,     0);
		this.add(remoteButtons[4],  240,     0);
		this.add(midButtonGrid,       0,   240);
		this.add(lowButtonGrid,       0,   360);
	}
	
	private void drawSecondaryGrid(RemoteButton remoteButtons[])
	{
		topButtonGrid = new Grid(8, 4);
		topButtonGrid.setSize("320px", "480px");
		
		topButtonGrid.setCellPadding(0);
		topButtonGrid.setCellSpacing(0);
		
		for (int i = 1, y = 0 ; i <= 32 ; y++)
		{
			for (int x = 0 ; i <= 32 && x < 4 ; i++, x++)
			{
				setDefaultHandlers(remoteButtons[i], y,  x);
				addClickHandler(remoteButtons[i], keyCodeHandler);
				topButtonGrid.setWidget(y, x, remoteButtons[i]);
			}
		}
		this.add(topButtonGrid,   0,   0);
		//remoteButtons[29].setAltStyle(true);
	}
	
	private void drawDPadButton()
	{
		dPadButton = new RemoteDPadButton(remoteButtons);
		this.add(dPadButton, 86, 227);
		
	}
	
	private void drawTPadButton()
	{
		touchPadPanel = new RemoteTPadButton(keyCodePressedLabel);
		//touchPadPanel.setVisible( false );
		tPadVisible = false;
		this.insert(touchPadPanel,320,   0, 0);
	}
	
	public void setKeyCodeLabel(KeyCodeLabel label)
	{
		keyCodePressedLabel = label;
	}
	
	public void showTouchPad(boolean showTouchPad)
	{
		if (showTouchPad)
		{
			//touchPadPanel.setVisible(true);
			tPadVisible = true;
			touchPadIsOpen = true;
			
			if (remoteButtons[4].hasIconImage())
				remoteButtons[4].setIconImage("images/icons_ui_HIDE_TOUCHPAD.png", "images/icons_ui_HIDE_TOUCHPAD_touched.png");
			else
			{
				remoteButtons[4].getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Buttons" );
				remoteButtons[4].getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Buttons" );
				
			}
		
			PanelAnimationSlide slideAnimation = new PanelAnimationSlide( topButtonGrid.getElement() );
			slideAnimation.scrollTo( -320, 0, 420 );
			
			PanelAnimationSlide slideAnimation2 = new PanelAnimationSlide( touchPadPanel.getElement() );
			slideAnimation2.scrollTo( 0, 0, 400 );
			
			
			//topButtonGrid.addStyleName("gtvRemote-PushButton-slideOut");
			//topButtonGrid.removeStyleName("gtvRemote-PushButton-slideIn");
			
			
			//touchPadPanel.addStyleName("gtvRemote-PushButton-slideOut");
			//touchPadPanel.removeStyleName("gtvRemote-PushButton-slideIn");
			
		}
		else
		{
			touchPadIsOpen = false;
			
			if (remoteButtons[4].hasIconImage())
			{
				if (getTabOrPopUpType().compareTo("popup") == 0 )
				{
					remoteButtons[4].setIconImage("images/icons_ui_SHOW_TOUCHPAD.png", "images/icons_ui_SHOW_TOUCHPAD_touched.png");
				}
				else
				{
					remoteButtons[4].setIconImage("images/icons_ui_LOCK_MOUSE.png", "images/icons_ui_LOCK_MOUSE_touched.png");
				}
			}
			else
			{
				remoteButtons[4].getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Cursor" );
				remoteButtons[4].getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + "" + "</p><br>" + "Cursor" );
			}
			
			
			
			PanelAnimationSlide slideAnimation = new PanelAnimationSlide( topButtonGrid.getElement() );
			slideAnimation.scrollTo( 0, 0, 400 );
			
			PanelAnimationSlide slideAnimation2 = new PanelAnimationSlide( touchPadPanel.getElement() );
			slideAnimation2.scrollTo( 320, 0, 420 );
			
			//topButtonGrid.removeStyleName("gtvRemote-PushButton-slideOut");
			//topButtonGrid.addStyleName("gtvRemote-PushButton-slideIn");


			//touchPadPanel.removeStyleName("gtvRemote-PushButton-slideOut");
			//touchPadPanel.addStyleName("gtvRemote-PushButton-slideIn");

			
			Timer t = new Timer() {
				public void run() {
					//touchPadPanel.setVisible(false);
					tPadVisible = false;
			    }
			};
			t.schedule(450);    // Schedule the timer to run once in 0.4 seconds.
			
		}
		
		
	}
	


	
//	private void loadButtonPropertiesList(final String JSON_URL) 
//	{
//	    String url = JSON_URL;
//
//	    url = URL.encode(url);
//
//	    // Send request to server and catch any errors.
//	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
//	    displayError(JSON_URL);
//	    try 
//	    {
//	    	@SuppressWarnings("unused")
//	    	Request request = builder.sendRequest(null, new RequestCallback() {
//	    		public void onError(Request request, Throwable exception) {
//	    			displayError("Couldn't retrieve JSON");
//	    		}
//
//	    		public void onResponseReceived(Request request, Response response) {
//	    			if (200 == response.getStatusCode()) {
//	    				
//	    				//loadPanelButtons( asArrayOfRButtonProperties(response.getText()) );
//	    				parseJsonPanelButtons( response.getText() );
//	    				drawButtonGrid();
//	    				
//	    			} 
//	    			else {
//	    				displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")" + JSON_URL);
//	    			}
//	    		}
//	    	});
//	    	
//	    } catch (RequestException e) {
//	      displayError("Couldn't retrieve JSON");
//	    }
//	    
//	}
	
	private void loadPanelButtons(JsArray<RemoteButtonProperties> buttons) {
		for (int i = 0; i < buttons.length(); i++) {
			RemoteButtonProperties p = buttons.get(i);
			this.addRemoteButton(p.getIndex(), p.getName(), p.getSubName(), p.getKeyCode(), p.getIconUp(), p.getIconDown());
		}
		
	}
	
	private void parseJsonPanelButtons(String jsonString) {
		
		boolean arrayOpened 	  = false;
		boolean objectOpened	  = false;
		boolean objectNameOpened  = false;
		boolean objectNameFound   = false;
		String  objectNameString  = "";
		boolean objectValueOpened = false;
		boolean objectValueFound   = false;
		String	objectValueString = "";
		boolean readyForValue 	  = false;
		boolean lastWasBackSlash  = false;
		
		int		objectsFoundCount = 0;		
		
		//int indexTemp = 0;
		String nameTemp = "", subNameTemp = "", keyCodeTemp = "", iconUpTemp = "", iconDownTemp = "";
		
		for (int n=0; n < jsonString.length(); n++) {
			char singleChar = jsonString.charAt(n);
			
			
			if ( singleChar == 44 && objectOpened && objectValueFound)  //		,
			{
				//displayError(objectNameString + " : " + objectValueString);
				if(objectNameString.equals("index"))
				{
					objectValueString = objectValueString.replaceAll("\\s+", "");
					//indexTemp = Integer.parseInt( objectValueString );
				}
				else if(objectNameString.equals("name"))
				{
					nameTemp = stripHTML(objectValueString);
				}
				else if(objectNameString.equals("subName"))
				{
					subNameTemp = stripHTML(objectValueString);
				}
				else if(objectNameString.equals( "keyCode"))
				{
					keyCodeTemp = stripHTML(objectValueString);
				}
				else if(objectNameString.equals("iconUp"))
				{
					iconUpTemp = stripHTML(objectValueString);
				}
				else if(objectNameString.equals("iconDown"))
				{
					iconDownTemp = stripHTML(objectValueString);
				}
				
				objectNameFound   = false;
				objectNameString  = "";
				objectValueFound  = false;
				objectValueString = "";
				readyForValue	  = false;

				objectValueOpened = false;
			}
			
			

			
			
			if ( singleChar == 91 && !objectOpened)  //		[
			{
				arrayOpened = true;
			}
			if ( singleChar == 93 && !objectOpened)  //		]
			{
				arrayOpened = false;
			}
			if ( singleChar == 123 && arrayOpened && !objectValueOpened && !objectNameOpened)  //		{
			{
				objectOpened = true;
			}
			if ( singleChar == 125 && arrayOpened && !objectValueOpened && !objectNameOpened)  //		}
			{
				objectsFoundCount++;
				
				objectOpened = false;
				addRemoteButton(objectsFoundCount, nameTemp, subNameTemp, keyCodeTemp, iconUpTemp, iconDownTemp);
				
				objectValueFound  = false;
				nameTemp = ""; subNameTemp = ""; keyCodeTemp = ""; iconUpTemp = ""; iconDownTemp = "";
			}
			
			if ( singleChar == 34 && objectNameOpened == false && objectNameFound == false)  //		"
			{
				objectNameOpened = true;
			}
			else if ( singleChar == 34 && objectNameOpened == true && objectNameFound == false && !lastWasBackSlash)  //		"
			{
				objectNameOpened = false;
				objectNameFound  = true;
			}
			else if ( singleChar == 34 && objectValueOpened == false && objectNameFound == true)  //		"
			{
				objectValueOpened = true;
			}
			else if ( singleChar == 34 && objectValueOpened == true && objectNameFound == true && !lastWasBackSlash)  //		"
			{
				objectValueFound  = true;
			}
			
			
			if ( singleChar == 58 && !objectNameOpened && !objectValueOpened && objectNameFound)  //		:
			{
				readyForValue = true;
			}
			
			
			if ( ( (singleChar >= 32 && singleChar <= 254)  ) && singleChar != 58 && singleChar != 34 && objectNameOpened)
			{
				objectNameString = objectNameString + singleChar;
			}
			
			if ( ( (singleChar >= 32 && singleChar <= 254)  ) && singleChar != 34 && singleChar != 92 && readyForValue && objectValueOpened && !objectNameOpened)
			{
				objectValueString = objectValueString + singleChar;
			}
			else if((singleChar >= 48 && singleChar <= 57) && readyForValue)
			{
				objectValueString = objectValueString + singleChar;
				objectValueFound = true;
			}
			else if( singleChar == 34 && lastWasBackSlash && objectValueOpened)
			{
				objectValueString = objectValueString + "\"";
			}
			else if( singleChar == 92 && lastWasBackSlash && objectValueOpened)
			{
				objectValueString = objectValueString + "\\";
			}
			
			if ( singleChar == 92  && !lastWasBackSlash)  		//		\
				lastWasBackSlash = true;
			else if ( lastWasBackSlash )
				lastWasBackSlash = false;
		}
		
		while(objectsFoundCount < 32)
		{
			objectsFoundCount++;
			addRemoteButton(objectsFoundCount, "", "", "", "", "");
		}
		
		
	}
	
	private void updatePanel(JsArray<RemoteButtonProperties> buttons) {
//		RButtonProperties p;
//		
//		for (int i = 0; i < buttons.length(); i++) {
//		  
//			p = buttons.get(i);
//		   
//			remoteButtons[p.getIndex()].setName(p.getName());
//			remoteButtons[p.getIndex()].setKeyCode(p.getKeyCode());
//		  
//			if (p.getSubName().compareTo("") != 0) {
//				remoteButtons[p.getIndex()].setSubText(p.getSubName());
//			}
//		  
//			if (p.getIconUp().compareTo("") != 0) {
//				remoteButtons[p.getIndex()].setIconImage(p.getIconUp(), p.getIconDown());
//			}
//		}
		
	}

	private void addRemoteButton(int index, String name, String subName, String code, String iconUp, String iconDown)
	{
		
		if( code == "UI_VOICE" && getTabOrPopUpType().compareTo("popup") == 0 )
		{
			iconUp   = iconUp.replaceAll("VOICE","TEXT");
			iconDown = iconDown.replaceAll("VOICE","TEXT");
		}
		
		if      (subName.compareTo("") != 0 && iconUp.compareTo("") == 0) {					//Has Sub-name and does not have Icon.
			remoteButtons[index] = new RemoteButton(name , code , index, subName);		
		}
		else if (subName.compareTo("") != 0 && iconUp.compareTo("") != 0) {					//Has Sub-name but does have Icon.
			remoteButtons[index] = new RemoteButton(name , code , index, iconUp, iconDown);
			remoteButtons[index].setSubText(subName);
			remoteButtons[index].enableIconImage();
		}
		else if (subName.compareTo("") == 0 && iconUp.compareTo("") == 0) {					//Has no Sub-name and does not have Icon.
			remoteButtons[index] = new RemoteButton(name , code , index);
		}
		else {																				//Has no Sub-name and does have Icon.
			remoteButtons[index] = new RemoteButton(name , code , index, iconUp, iconDown);
		}
		
		
	}

	private String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	
	private void swapButtons(int thisPanelIndex, int thisIndex, String direction)
	{
		int nextPanelIndex = thisPanelIndex;
		int nextIndex = thisIndex;
		
		if( thisPanelIndex == 2 && thisIndex % 4 == 0 && direction.compareTo("RIGHT") == 0) {
			nextPanelIndex = 1;
			nextIndex = thisIndex - 3;
		}
		else if( thisPanelIndex == 1 && (thisIndex == 1 || (thisIndex-1) % 4 == 0) && direction.compareTo("LEFT") == 0) {
			nextPanelIndex = 2;
			nextIndex = thisIndex + 3;
		}
		else if (direction.compareTo("RIGHT") == 0)
			nextIndex++;
		else if (direction.compareTo("LEFT") == 0)
			nextIndex--;
		else if (direction.compareTo("DOWN") == 0)
			nextIndex = nextIndex + 4;
		else if (direction.compareTo("UP") == 0)
			nextIndex = nextIndex - 4;
		
		if(thisPanelIndex == 1 && nextPanelIndex == 1)
		{
			if(direction.compareTo("RIGHT") == 0 && (thisIndex == 17 || thisIndex == 21 ))
				nextIndex = thisIndex + 2;
			if(direction.compareTo("LEFT")  == 0 && (thisIndex == 19 || thisIndex == 23 ))
				nextIndex = thisIndex - 2;
			if(direction.compareTo("DOWN")  == 0 && (nextIndex == 18 || nextIndex == 20) )
				nextIndex = nextIndex - 1;
			if(direction.compareTo("UP")    == 0 && (nextIndex == 22 || nextIndex == 24) )
				nextIndex = nextIndex - 1;
			
			if(direction.compareTo("UP")    == 0 && thisIndex == 19)
				nextIndex = nextIndex + 1;
			if(direction.compareTo("DOWN")  == 0 && thisIndex == 23)
				nextIndex = nextIndex + 1;
			
			if(direction.compareTo("RIGHT") == 0 && ((nextIndex == 4 || thisIndex == 19 || thisIndex == 23 ) || (thisIndex % 4 == 0)))
				nextIndex = thisIndex;
			if(direction.compareTo("UP")    == 0 && nextIndex == 4)
				nextIndex = thisIndex;
		}
		if(thisPanelIndex == 2 && nextPanelIndex == 2)
		{
			if(direction.compareTo("LEFT") == 0 && (thisIndex == 1 || (thisIndex-1) % 4 == 0))
				nextIndex = thisIndex;	
		}
		if(direction.compareTo("UP")       == 0 && thisIndex >= 1 && thisIndex <= 4)
			nextIndex = thisIndex;	
		if(direction.compareTo("DOWN")     == 0 && thisIndex >= 29 && thisIndex <= 32)
			nextIndex = thisIndex;	
		
		RemoteButton thisRemoteButton = allRemoteButtons[thisPanelIndex][thisIndex];
		RemoteButton nextRemoteButton = allRemoteButtons[nextPanelIndex][nextIndex];
		RemoteButton tempRemoteButton = new RemoteButton(thisRemoteButton.getName(),thisRemoteButton.getKeyCode(),thisRemoteButton.getCellIndex());
		if(thisRemoteButton.hasSubText())
			tempRemoteButton.setSubText(thisRemoteButton.getSubName());
		if(thisRemoteButton.iconImageEnabled())
			tempRemoteButton.setIconImage(thisRemoteButton.getIconPath(), thisRemoteButton.getIconPath().replaceFirst(".png", "_touched.png"));
			
		thisRemoteButton.setName(nextRemoteButton.getName());
		thisRemoteButton.setKeyCode(nextRemoteButton.getKeyCode());
		if(nextRemoteButton.hasSubText())
			thisRemoteButton.setSubText(nextRemoteButton.getSubName());
		else
			thisRemoteButton.removeSubText();
		if(nextRemoteButton.iconImageEnabled())
			thisRemoteButton.setIconImage(nextRemoteButton.getIconPath(), nextRemoteButton.getIconPath().replaceFirst(".png", "_touched.png"));
		else
			thisRemoteButton.disableIconImage();
		
		nextRemoteButton.setName(tempRemoteButton.getName());
		nextRemoteButton.setKeyCode(tempRemoteButton.getKeyCode());
		if(tempRemoteButton.hasSubText())
			nextRemoteButton.setSubText(tempRemoteButton.getSubName());
		else
			nextRemoteButton.removeSubText();
		if(tempRemoteButton.iconImageEnabled())
			nextRemoteButton.setIconImage(tempRemoteButton.getIconPath(), tempRemoteButton.getIconPath().replaceFirst(".png", "_touched.png"));
		else
			nextRemoteButton.disableIconImage();

		for(int i = 17; i <= 24; i = i+2)
			allRemoteButtons[1][i].makeWide();
		
		buildAndSaveJsonString(1);
		buildAndSaveJsonString(2);
	}
	
	public void buildAndSaveJsonString(int currentIndex)
	{
		RemoteButton buttons[] = allRemoteButtons[currentIndex];
		String jsonString = "[";
		
		for(int i=1; i < buttons.length ;i++)
		{
			jsonString += "{";
			jsonString += "   \"index\"   	:	"   + buttons[i].getCellIndex() 					+ ",";
			jsonString += "   \"name\"    	:	\"" + buttons[i].getName().replace("\\", "\\\\").replace("\"", "\\\"")    + "\",";
			jsonString += "   \"subName\" 	:	\"" + buttons[i].getSubName().replace("\\", "\\\\").replace("\"", "\\\"") + "\",";
			jsonString += "   \"keyCode\" 	:	\"" + buttons[i].getKeyCode() 						+ "\",";
			if(buttons[i].getIconPath() != null)
			{
				jsonString += "   \"iconUp\"  	:	\"" + buttons[i].getIconPath() 	+ "\",";
				jsonString += "   \"iconDown\"	:	\"" + buttons[i].getIconPath().replaceFirst(".png", "_touched.png") + "\",";
			}
			else
			{
				jsonString += "   \"iconUp\"  	:	\"" + "" 	+ "\",";
				jsonString += "   \"iconDown\"	:	\"" + "" 	+ "\",";
			}
			jsonString += "},";
		}
		jsonString += "]";
		
		saveJsonString(currentIndex, jsonString);
	}
		
	public static native void saveJsonString(int jsonStringChoice, String jsonString) /*-{
	
		if ( jsonStringChoice == 1) {
			$wnd.backgroundPageWindow.remotePanel1JsonString = jsonString;
		}
		else if ( jsonStringChoice == 2) {
			$wnd.backgroundPageWindow.remotePanel2JsonString = jsonString;
		}
		$wnd.backgroundPageWindow.saveJsonToLocalStorage();
	}-*/;
	
//	private final native JsArray<RemoteButtonProperties> asArrayOfRButtonProperties(String json) /*-{
//    	return eval(json);
//  }-*/;

	public static native void displayError(String error) /*-{
		$wnd.backgroundPageWindow.console.log(error);
	}-*/;
	
	public static native void sendButtonKeyEvent(String keyCode, boolean keyDown) /*-{
		$wnd.sendKeyEvent(keyCode, keyDown);
		
		if(keyDown)
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' button down sent to connected device.');
		else
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' button up sent to connected device.');
			
	}-*/;
	
	public static native void toast(String msg) /*-{
		$wnd.displayToast(msg);
	}-*/;
	
	public static native void displayPermToast(String msg) /*-{
		$wnd.displayPermToast(msg);
	}-*/;
	public static native void removePermToast() /*-{
		$wnd.removePermToast();
	}-*/;
	
	public static native String getJsonString(int jsonStringChoice) /*-{
		
		var tempString;
		//$wnd.backgroundPageWindow.console.log($wnd.backgroundPageWindow.remotePanel1JsonString);
		if ( jsonStringChoice == 1) {
			tempString = $wnd.backgroundPageWindow.remotePanel1JsonString;
		}
		else if ( jsonStringChoice == 2) {
			tempString = $wnd.backgroundPageWindow.remotePanel2JsonString;
		}
		
		return tempString;
		
	}-*/;
	
	
	
	public static native boolean canPointerLock() /*-{
		
		var havePointerLock = 'pointerLockElement' in document || 'mozPointerLockElement' in document || 'webkitPointerLockElement' in document;
		
		return havePointerLock;
		
	}-*/;
	
	public static native void initPointerLock() /*-{
	
		$wnd.initPointerLock();
	
	}-*/;
	
	public static native void activatePointerLock() /*-{
	
		$wnd.actPointLock();
		$wnd.showMouseIcon();

	}-*/;	
	
	public static native void deactivatePointerLock() /*-{
	
		$wnd.deactPointLock();
		$wnd.hideMouseIcon();
		 
		$wnd.document.getElementById('TOUCHPAD_SHOWHIDE').click();

	}-*/;	
	
	
	public static native void exportStaticMethod() /*-{
		
//		$wnd.showTouchPad =
//    		$entry(@com.chromemote.crx.client.ui.RemoteButtonPanel::showTouchPad(Z));
		
	}-*/;
	
	
	public static native String getTabOrPopUpType() /*-{
		return $wnd.tabOrPopUp;
	}-*/;	
	
	public static native boolean isGtvConnected() /*-{
		return backgroundPageWindow.anymoteSessionActive;
	}-*/;
	
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();
	
	}-*/;

	public static native void enableKeyBoardEvents() /*-{
	
		$wnd.enableKeyBoardEvents();
	
	}-*/;
	
}
