package com.chromemote.crx.client.ui;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class DeviceMenuScrollPanel extends CustomScrollPanel {

	private static int deviceCount = 0;
	
	private static DeviceMenuScrollButton deviceMenuButtons[];
	
	private static FlexTable flexTable;
	
	private ClickHandler keyCodeHandler;
	
	static Timer pairTimer, anymoteTimer;
	
	static int timeOutCounter  = 0;
	
	static int timeOutCounter2 = 0;
	
	static PinDialogBox pinDialogbox;
	
	public DeviceMenuScrollPanel()
	{
		super( new FlexTable() );
		flexTable = (FlexTable) this.getWidget();
		flexTable.setCellSpacing(0);
		flexTable.setCellPadding(0);
		
		this.setSize("320px", "365px");
		
		this.setAlwaysShowScrollBars(false);
		this.removeHorizontalScrollbar();
		//this.removeVerticalScrollbar();
	
		
		
		deviceMenuButtons = new DeviceMenuScrollButton[255];  //TODO Configure dynamic array for infinite device list.
		
		
		
		
		exportStaticMethod();
		
		if ( !wasPluginLoaded() )
			displayPluginErrorAlert();
		
		
	}
	
	public static void addDevice(final String deviceName, final String ipAddress, final boolean isNewDevice)
	{
		
		if( !alreadyInList(ipAddress) )
		{
			deviceCount++;
			
			saveDeviceCount(deviceCount);
			deviceMenuButtons[deviceCount] = new DeviceMenuScrollButton( stripHTML(deviceName), ipAddress, deviceCount, isNewDevice );
			final DeviceMenuScrollButton deviceMenuButton = deviceMenuButtons[deviceCount];
			
			if(deviceMenuButtons[deviceCount].isNewDevice())
			{
				
				deviceMenuButtons[deviceCount].addMouseDownHandler(new MouseDownHandler(){
					@Override
					public void onMouseDown(MouseDownEvent event) {
						
						if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
						{ 
							startPinDialogBox(deviceMenuButtons[getDevicePosition(ipAddress)].getDeviceName(), ipAddress, isNewDevice);
							deviceMenuButton.setFocus(false);
							
						}
						else
						{
							int x = event.getClientX();
							int y = event.getClientY();
							
							final DeviceContextMenu deviceContextMenu = new DeviceContextMenu(x,y);
							
							deviceContextMenu.addMenuCommand("Pair", new ClickHandler(){public void onClick(ClickEvent event) { 
								startPinDialogBox(deviceMenuButtons[getDevicePosition(ipAddress)].getDeviceName(), ipAddress, isNewDevice);
								deviceMenuButton.setFocus(false);
							}});
							deviceContextMenu.addMenuCommand("Rename"	, new ClickHandler(){public void onClick(ClickEvent event) { 
								renameDeviceDialogBox(deviceMenuButtons[getDevicePosition(ipAddress)].getDeviceName(), ipAddress);
							}});
							deviceContextMenu.addMenuCommand("Remove"	, new ClickHandler(){public void onClick(ClickEvent event) { 
								removeDevice(ipAddress);
								
							}});
							
							deviceContextMenu.Show();
							
						}
						
					}});
			}
			else {
			
				deviceMenuButtons[deviceCount].addMouseDownHandler(new MouseDownHandler(){
				
					public void onMouseDown(MouseDownEvent event) {
						
						if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
						{ 
							anymoteConnectToExistingDevice(ipAddress);
							deviceMenuButton.setFocus(false);
						}
						else
						{
							int x = event.getClientX();
							int y = event.getClientY();
							
							final DeviceContextMenu deviceContextMenu = new DeviceContextMenu(x,y);
							
							deviceContextMenu.addMenuCommand("Reconnect", new ClickHandler(){public void onClick(ClickEvent event) { 
								anymoteConnectToExistingDevice(ipAddress);
								deviceMenuButton.setFocus(false);
							}});
//							deviceContextMenu.addMenuCommand("Re-Pair", new ClickHandler(){public void onClick(ClickEvent event) { 
//							}});
							deviceContextMenu.addMenuCommand("Rename"	, new ClickHandler(){public void onClick(ClickEvent event) { 
								renameDeviceDialogBox(deviceMenuButtons[getDevicePosition(ipAddress)].getDeviceName(), ipAddress);
							}});
							deviceContextMenu.addMenuCommand("Remove"	, new ClickHandler(){public void onClick(ClickEvent event) { 
								removeDevice(ipAddress);
								
							}});
//							deviceContextMenu.addMenuCommand("Reboot"	, new ClickHandler(){public void onClick(ClickEvent event) { 
//								sendRebootCommand();
//							}});
							
							deviceContextMenu.Show();
							
						}
					}});
								
			}
			
			flexTable.setWidget(deviceCount, 0, deviceMenuButtons[deviceCount]);
			
			updateDeviceList();
		}
		else
		{
			//saveDeviceCount(deviceCount);
			//TODO Handle already in list.
			for(int i=1 ; i < deviceCount ; i++)
			{
				if (deviceMenuButtons[i].getDeviceIP() == ipAddress)
				{
					
					break;
				}
			}
		}
		//saveDeviceCount(deviceCount);
	}
	
	
	public static native void exportStaticMethod() /*-{
    	$wnd.addDevice =
    		$entry(@com.chromemote.crx.client.ui.DeviceMenuScrollPanel::addDevice(Ljava/lang/String;Ljava/lang/String;Z));
    		
    	$wnd.startPinDialogBox =
    		$entry(@com.chromemote.crx.client.ui.DeviceMenuScrollPanel::startPinDialogBox(Ljava/lang/String;Ljava/lang/String;Z));
    	
    	$wnd.closePinDialogBox =
    		$entry(@com.chromemote.crx.client.ui.DeviceMenuScrollPanel::closePinDialogBox());
    		
    	$wnd.displayPinDialogBox =
    		$entry(@com.chromemote.crx.client.ui.DeviceMenuScrollPanel::displayPinDialogBox(Ljava/lang/String;Ljava/lang/String;Z));
    		
    	$wnd.moveDeviceToFront =
    		$entry(@com.chromemote.crx.client.ui.DeviceMenuScrollPanel::moveDeviceToFront(Ljava/lang/String;));
 	}-*/;
	
	
	public static void closePinDialogBox()
	{
		pinDialogbox.Close();
	}

	public static native void pairNewDevice(String deviceName, String deviceIP) /*-{
	  
	  	$wnd.disableKeyBoardEvents();
		$wnd.pairingSessionPair(deviceName, deviceIP);
    	
	}-*/;
	
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();
	
	}-*/;
	
	public static native void enableKeyBoardEvents() /*-{
	
		$wnd.enableKeyBoardEvents();
	
	}-*/;
	
	public static native void anymoteStartSession(String deviceIP) /*-{
	  
		$wnd.anymoteStartSession(deviceIP,
	            function(e) {
	              if (e.type == googletvremote.anymote.EventType.CONNECTED) {
	                console.log('Successful Anymote session connection.');
	
	              } else {
	                console.log('Unsuccessful Anymote session connection.');
	              }
	    });

	}-*/;
	
	public static native void sendChallengeResponse(String challengResponse) /*-{
	  
		$wnd.sendChallengeResponse(challengResponse);
			  	
	}-*/;
	
	public static native boolean wasChallengeResponseAccepted() /*-{
		
		return $wnd.wasChallengeResponseAccepted();
		
	}-*/;
	
	public static native boolean wasChallengeAccepted() /*-{
	
		return $wnd.wasChallengeAccepted();
	
	}-*/;
	
	public static native void cancelChallengeResponse() /*-{
	  
		$wnd.cancelChallengeResponse();

	}-*/;
	
	public static native void anymoteConnectToExistingDevice(String ipAddress) /*-{
	  
		$wnd.anymoteConnectToExistingSingleDevice(ipAddress);
	
	}-*/;

	
	public static boolean alreadyInList(String ipAddress)
	{
		boolean deviceFound = false;
		for(int i=1 ; i <= deviceCount ; i++)
		{
			if (deviceMenuButtons[i].getDeviceIP().compareTo(ipAddress) == 0)
			{
				deviceFound = true;
			}			
		}
		return deviceFound;
	}
    
	public void clearDeviceList()
	{
        for (int i = deviceCount; i >= 0; i--) {
        	flexTable.removeRow(i);
        }
        deviceCount = 0;
        saveDeviceCount(deviceCount);
        
        clearLocalStorageDeviceList();
	}
	
	public static native void clearLocalStorageDeviceList() /*-{
	
		$wnd.clearDeviceList();

	}-*/;
	
	public static void removeDevice(String ipAddress)
	{
		
		for(int i=1 ; i <= deviceCount ; i++)
		{
			if (deviceMenuButtons[i].getDeviceIP().compareTo(ipAddress) == 0)
			{
				flexTable.removeRow(i);
				
				deviceMenuButtons[i].removeFromParent();
				
				for(int k=i ; k < deviceCount ; k++)
				{
					deviceMenuButtons[k] = deviceMenuButtons[k+1];
				}
				
				deviceCount--;
				saveDeviceCount(deviceCount);
				break;
			}			
		}
		
		removeDeviceFromList(ipAddress);
		
		if (isConnected() && getConnectedIP().compareTo(ipAddress) == 0)
		{
			stopAnymoteSession();
			
		}
		
		
	}
	
	
	public static void moveDeviceToFront(String ipAddress)
	{
				
		for(int i=1 ; i <= deviceCount ; i++)
		{
			if (deviceMenuButtons[i].getDeviceIP().compareTo(ipAddress) == 0)
			{
				DeviceMenuScrollButton tempDeviceMenuButton = deviceMenuButtons[1];
				flexTable.setWidget(1, 0, deviceMenuButtons[i]);
				flexTable.setWidget(i, 0, tempDeviceMenuButton);
				deviceMenuButtons[1] = deviceMenuButtons[i];
				deviceMenuButtons[i] = tempDeviceMenuButton;
								
				for(int k=i ; k > 2 ; k--)
				{
					tempDeviceMenuButton = deviceMenuButtons[k];
					flexTable.setWidget(k, 0, deviceMenuButtons[k-1]);
					flexTable.setWidget(k-1, 0, tempDeviceMenuButton);
					deviceMenuButtons[k] = deviceMenuButtons[k-1];
					deviceMenuButtons[k-1] = tempDeviceMenuButton;
				}
				
				break;
			}
		}
				
	}
	
	
	
	public static void renameDevice(String newDeviceName, String ipAddress)
	{
				
		for(int i=1 ; i <= deviceCount ; i++)
		{
			if (deviceMenuButtons[i].getDeviceIP().compareTo(ipAddress) == 0)
			{
				deviceMenuButtons[i].setDeviceName(newDeviceName);
				renameDeviceFromList(newDeviceName, ipAddress);
			}
		}
				
	}
	
	public static void renameDeviceDialogBox(final String currentName, final String ipAddress)
	{
		
		disableKeyBoardEvents();
		final RenameDeviceDialogBox renameDeviceDialogBox = new RenameDeviceDialogBox(currentName, ipAddress);
		ClickHandler clickHandler = new ClickHandler()
	    {	public void onClick(ClickEvent event) 
			{
	    		if(renameDeviceDialogBox.getIPResponse().compareTo("cancel") == 0)
	    		{
	    			renameDeviceDialogBox.Close();
	    		    enableKeyBoardEvents();
	    		}
	    		else if(renameDeviceDialogBox.getNameResponse().compareTo("null") != 0)
	    		{
	    		    enableKeyBoardEvents();
	    		    String newDeviceName = renameDeviceDialogBox.getNameResponse();
	    		    renameDevice(newDeviceName, ipAddress);
	    		}	
	    			
			}
	    };
	    renameDeviceDialogBox.addClickHandler(clickHandler);
	    renameDeviceDialogBox.Show();
		
	}
	
	public static int getDevicePosition(final String ipAddress)
	{
		int position = 0;
		for(int i=1 ; i <= deviceCount ; i++)
		{
			if (deviceMenuButtons[i].getDeviceIP().compareTo(ipAddress) == 0)
			{
				position = i;
			}
		}
		return position;
		
	}
	
	
	public static void startPinDialogBox(final String deviceName, final String ipAddress, final boolean isNewDevice)
	{
		
		stopDiscovery();
		cancelChallengeResponse();
		pairNewDevice(deviceName, ipAddress);
		
		timeOutCounter = 0;
		
		final WaitingDialogBox waitingDialogBox = new WaitingDialogBox("Waiting for pairing code");
		waitingDialogBox.Show();
		ClickHandler clickListener = new ClickHandler()
	    {	public void onClick(ClickEvent event) 
			{
	    		
	    		cancelChallengeResponse();
	    		waitingDialogBox.Close();
	    		timeOutCounter = 20;
	    		pairTimer.cancel();
	    
			}
	    };
	    waitingDialogBox.addClickListener(clickListener);
	    
		
		
		
		pairTimer = new Timer() {
			public void run() {
				timeOutCounter++;
		      	if( wasChallengeAccepted() )
					{
		      			pairTimer.cancel();
		      			waitingDialogBox.Close();
						displayPinDialogBox(deviceName, ipAddress, isNewDevice);
		
					}
		      		if(timeOutCounter == 20)
		      		{
		      			pairTimer.cancel();
		      			waitingDialogBox.Close();
		      			//enableKeyBoardEvents();
		      			displayToast(ipAddress + " did not respond.");
		      			
		      			disableKeyBoardEvents();
						final ForcePINDialogBox retryDialogBox = new ForcePINDialogBox(deviceName, ipAddress);
						ClickHandler clickListener = new ClickHandler()
					    {	public void onClick(ClickEvent event) 
							{
					    		if(retryDialogBox.getIPResponse().compareTo("cancel") == 0)
					    		{
					    			retryDialogBox.Close();
					    		    enableKeyBoardEvents();
					    		}
					    		else if(retryDialogBox.getIPResponse().compareTo("null") != 0)
					    		{
					    			//disableKeyBoardEvents();
					    			
					    			if(wasChallengeAccepted()){}
					    			DeviceMenuScrollPanel.startPinDialogBox(retryDialogBox.getNameResponse(), retryDialogBox.getIPResponse(), true);
					    			
					    			
					    		}	
					    			
							}
					    };
					    retryDialogBox.addClickListener(clickListener);
					    retryDialogBox.Show();
					    
					    
		      		}
		      	
		      	
		      	
		    	  
		    }
		};
		pairTimer.schedule(500);
		pairTimer.scheduleRepeating(500);
	}
	
	public static void displayPinDialogBox(final String deviceName, final String ipAddress, final boolean isNewDevice)
	{
		
		pinDialogbox = new PinDialogBox(deviceName);
		ClickHandler clickListener = new ClickHandler()
	    {	public void onClick(ClickEvent event) 
			{
	    		if(pinDialogbox.getResponse().compareTo("cancel") == 0)
	    		{
	    			cancelChallengeResponse();
	    		    pinDialogbox.Close();
	    		    enableKeyBoardEvents();
	    			//debugLabel.setText( "User Canceled PIN Dialogbox" );
	    		}
	    		else if(pinDialogbox.getResponse().compareTo("null") != 0)
	    		{
	    			//pinDialogbox.Close();
	    			sendChallengeResponse(pinDialogbox.getResponse());
	    			
	    			
	    			final WaitingDialogBox waitingDialogBox2 = new WaitingDialogBox("Waiting for challenge response.");
	    			waitingDialogBox2.Show();
	    			ClickHandler clickListener = new ClickHandler()
	    		    {	public void onClick(ClickEvent event) 
	    				{
	    		    		waitingDialogBox2.Close();
	    		    	}
	    		    };
	    		    waitingDialogBox2.addClickListener(clickListener);					    			
	    			
	    			timeOutCounter2 = 0;
	    			
	    			anymoteTimer = new Timer() {
	    			      public void run() {
	    			    	  timeOutCounter2++;
	    			    	  if ( wasChallengeResponseAccepted() )
		    	    			{
	    			    		  	anymoteTimer.cancel();
	    			      			waitingDialogBox2.Close();
	    			      			
	    			      			
	    			      			int indexFound = deviceCount;
	    			      			
    			      				for(int i=1 ; i <= deviceCount ; i++)
    			      				{
    			      					if (deviceMenuButtons[i].getDeviceIP() == ipAddress)
    			      					{
    			      						indexFound = i;
    			      						break;
    			      					}			
    			      				}
	    			      			
	    			      				
		    	    				deviceMenuButtons[indexFound] = new DeviceMenuScrollButton( deviceName, ipAddress, indexFound, isNewDevice);
		    						deviceMenuButtons[indexFound].addMouseDownHandler(new MouseDownHandler(){
		    							@Override
										public void onMouseDown(MouseDownEvent event) {
		    								
		    								if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
		    								{ 
		    									anymoteConnectToExistingDevice(ipAddress);
		    									
		    								}
		    								else
		    								{
		    									int x = event.getClientX();
		    									int y = event.getClientY();
		    									
		    									final DeviceContextMenu deviceContextMenu = new DeviceContextMenu(x,y);
		    									
		    									deviceContextMenu.addMenuCommand("Reconnect", new ClickHandler(){public void onClick(ClickEvent event) { 
		    										anymoteConnectToExistingDevice(ipAddress);
		    									}});
		    									deviceContextMenu.addMenuCommand("Rename"	, new ClickHandler(){public void onClick(ClickEvent event) { 
		    										renameDeviceDialogBox(deviceMenuButtons[getDevicePosition(ipAddress)].getDeviceName(), ipAddress);
		    									}});
		    									deviceContextMenu.addMenuCommand("Remove"	, new ClickHandler(){public void onClick(ClickEvent event) { 
		    										removeDevice(ipAddress);
		    										
		    									}});
		    									
		    									deviceContextMenu.Show();
		    									
		    								}
		    								
		    						}});
		    						flexTable.removeCell(indexFound, 0);
		    						flexTable.setWidget(indexFound, 0, deviceMenuButtons[indexFound]);
		    					
		    						moveDeviceToFront(ipAddress);
		    						
		    						pinDialogbox.Close();
		    						
		    	    			}
		    	    			else{
		    	    				anymoteTimer.cancel();
		    	    				waitingDialogBox2.Close();
		    	    				pinDialogbox.Shake();
		    	    				
		    	    			}
	    			    	    if(timeOutCounter2 == 20)
	    			      		{
	    			    	    	anymoteTimer.cancel();
	    			      			waitingDialogBox2.Close();
	    			      			enableKeyBoardEvents();
	    			      			displayToast(ipAddress + " did not respond.");
	    			      			
	    			      			
	    			      			
	    			      			
	    			      			
	    			      		}	
	    			    	  
	    			    	  
	    			      }
	    			    };

	    			    // Schedule the timer to run once in 5 seconds.
	    			    anymoteTimer.schedule(250);
	    			    anymoteTimer.scheduleRepeating(500);
	    			
	    			
	    			
	    			//debugLabel.setText( "'" + pinDialogbox.getResponse() + "' PIN Entered" );
	    		}	
	    			
			}
	    };
	    pinDialogbox.addClickListener(clickListener);
	    pinDialogbox.Show();
		
		
		
		
		
	}
	
	public int getDeviceCount()
	{
		return deviceCount;
	}
	
	public void	displayPluginErrorAlert()
	{
		AbsolutePanel incompatiblePanel = new AbsolutePanel();
		incompatiblePanel.setSize("320px","245px");
		incompatiblePanel.setStyleName("gtvRemote-incompatiblePanel");
		
		Image alertImage = new Image("images/menuBar/alertsWarning.png");
		incompatiblePanel.add(alertImage, 10, 10);
		
		Label alertMessageTitleLabel = new Label("");
		alertMessageTitleLabel.setWidth("250px");
		alertMessageTitleLabel.setStyleName("chromemote-alertInfoLabelTitle");
		incompatiblePanel.add(alertMessageTitleLabel, 40, 14);
		
		Label alertMessageLabel = new Label("");
		alertMessageLabel.setWidth("250px");
		alertMessageLabel.setStyleName("chromemote-alertInfoLabel");
		incompatiblePanel.add(alertMessageLabel, 40, 40);
		
		Label donateMessageLabel = new Label("");
		donateMessageLabel.setWidth("250px");
		donateMessageLabel.setStyleName("chromemote-alertInfoLabel");
		incompatiblePanel.add(donateMessageLabel, 40, 145);
		
		HTML payPalHTML = new HTML("<a href=\"http://www.chromemote.com/donate/\" target=\"_blank\"" +
				   				   "onClick=\"_gaq.push(['_trackEvent', 'Alert', 'Donate']);\"" +
				   				   "><img src=\"images/tipjar.png\" border=\'0\'></a>");
		payPalHTML.setStyleName("chromemote-settingsAppWwwHTML");
		
		
		String detectedOS = getDetectedOS();
		if ( getDetectedOS().compareTo("CrOS") == 0)
		{
			detectedOS = "Chrome OS";
			alertMessageTitleLabel.setText(detectedOS + " is Not Compatible!");
			alertMessageLabel.setText(detectedOS + " does not support the Chrome to GTVRemote Plugin. Chromemote needs this plugin for communication with Google TV devices, so currently Chromemote not compatible with Google ChromeOS.");
			donateMessageLabel.setText("Want Chrome OS support? Make a donation and request Chrome OS support to be added.");
			
			incompatiblePanel.add(payPalHTML, 100, 198 );
		}
		else if ( getDetectedOS().compareTo("Windows8") == 0)
		{
			detectedOS = "Windows 8";
			alertMessageTitleLabel.setText(detectedOS + " App Mode is Not Supported!");
			alertMessageLabel.setText(detectedOS + " App Mode does not support the GTVRemote Plugin used by Chromemote. Relaunch Chrome on the desktop for GTVRemote Plugin support.");
			donateMessageLabel.setText("Want Windows 8 App Mode support? Make a donation and request it.");
			
			incompatiblePanel.add(alertMessageLabel, 40, 60);
			incompatiblePanel.add(payPalHTML, 115, 198 );
		}
		else if ( getDetectedOS().compareTo("Linux") == 0)
		{
			detectedOS = "Linux";
			alertMessageTitleLabel.setText(detectedOS + " Partially Supported!");
			alertMessageLabel.setText("Currently Chromemote is only compatible with Ubuntu 12.");
			donateMessageLabel.setText("Want more Linux support? Make a donation and request it.");
			
			incompatiblePanel.add(donateMessageLabel, 40, 80);
			incompatiblePanel.add(payPalHTML, 115, 120 );
			incompatiblePanel.setSize("320px","167px");
		}
		else
		{
			alertMessageTitleLabel.setText("Unable to Start Plugin!");
			alertMessageLabel.setText("Oops.. There was an error trying to load the GTVRemote Plugin. Chromemote is compatible with Windows, Mac OS X, and Ubuntu 12. Please contact the developer and report this bug if Chromemote is compatible.");			
			donateMessageLabel.setText("Operating System: " + detectedOS);
			
			incompatiblePanel.add(donateMessageLabel, 40, 155);
			incompatiblePanel.setSize("320px","196px");
		}
		flexTable.setWidget(0, 0, incompatiblePanel);
	}
	
	private static String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	
	private static void updateDeviceList()
	{
		for (int i = deviceCount; i >= 0; i--) {
        	flexTable.removeRow(i);
        }
		for (int i = 1; i <= deviceCount; i++) {
			flexTable.setWidget(i, 0, deviceMenuButtons[i]);
        }
		
		
	}
	
	public static native void setDiscoveryLabel(String msg) /*-{
	
		$wnd.setDiscoveryLabel(msg);

	}-*/;
	
	
	
	public static native void stopDiscovery()  /*-{
	
		$wnd.stopDiscoveryClient();

	}-*/;
	
	public static native void displayToast(String message)  /*-{
		
		$wnd.displayToast(message);

	}-*/;
	
	public static native void addOneToBadge()  /*-{
		$wnd.backgroundPageWindow.addOneToBadge();
	}-*/;
	
	public static native void delOneFromBadge()  /*-{
		$wnd.backgroundPageWindow.delOneFromBadge();
	}-*/;

	public static native void saveDeviceCount(int count)  /*-{
		//$wnd.backgroundPageWindow.connectedDeviceCount = count;
		
//		var textString = "" + count;
//		chrome.browserAction.setBadgeText({text:textString});
//		chrome.browserAction.setBadgeBackgroundColor({color:[0, 200, 0, 100]});
//		
//		if(count == 0) {
//			chrome.browserAction.setBadgeBackgroundColor({color:[200, 0, 0, 100]});
//		}
		
	}-*/;
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;

	public static native boolean wasPluginLoaded()  /*-{
		return $wnd.backgroundPageWindow.gTvPluginLoaded;
	}-*/;
	
	public static native void sendRebootCommand()  /*-{
		$wnd.sendKeyEvent("CTRL_ALT_DEL");
	}-*/;
	
	public static native void removeDeviceFromList(String address)  /*-{
	
		$wnd.removeDeviceFromList(address);

	}-*/;
	
	public static native void renameDeviceFromList(String newName, String address)  /*-{
	
		$wnd.renameDeviceFromList(newName, address);
	
	}-*/;
	
	public static native boolean isConnected()  /*-{
	
		return $wnd.backgroundPageWindow.anymoteSessionActive;
	
	}-*/;
	
	public static native String getConnectedIP()  /*-{
	
		return $wnd.backgroundPageWindow.connectedDevice;
	
	}-*/;
	
	public static native void stopAnymoteSession()  /*-{
		
		$wnd.anymoteSession.stopSession();
		
		$wnd.sendGAEvent("Connection", "Clear Devices");
		$wnd.setStatusLabel('Chromemote');
		
		$wnd.disableKeyBoardEvents();
		$wnd.setIndicatorDisconnected();
		$wnd.backgroundPageWindow.connectedDevice = "";
		$wnd.backgroundPageWindow.anymoteSessionActive = false;
		$wnd.backgroundPageWindow.console.log('Connected device removed, so Anymote session has been disconnected.');
		
		
	}-*/;
}
