package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

//Draws Enter PIN Code DialogBox with DeviceID, textBox, Connect and Cancel Buttons

@SuppressWarnings("deprecation")
class ForcePINDialogBox
{
	
	DialogBox forcePinDialogBox;
	String deviceNameResult = "Google TV Device";
	String deviceIPResult = "null";
	TextBox deviceNameTextBox;
	TextBox deviceIPTextBox;
	Button connectButton, forcePinButton, cancelButton;
	
			 
	public ForcePINDialogBox(final String deviceName, final String deviceIP)
	{
		VerticalPanel DialogBoxContents;
		ClickListener listener, listener2;
		Label deviceNameLabel;
		Label deviceIPLabel;
		HTML deviceIPInfoLabel;
		
		HorizontalPanel holder;
		HorizontalPanel textBoxholder;
		HorizontalPanel textBoxholder2;
		HorizontalPanel forcePinButtonHolder;
	    
	    deviceNameTextBox = new TextBox();
	    
	    deviceNameTextBox.addKeyboardListener(new KeyboardListener(){
	    	@Override
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				
	    		if (KeyboardListener.KEY_ENTER == keyCode)
	    			deviceIPTextBox.setFocus(true);
			}
	    	
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				
				
			}

			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				
				
			}
		});	    
	    
	    deviceIPTextBox = new TextBox();
	    
	    deviceIPTextBox.addKeyboardListener(new KeyboardListener(){
	    	@Override
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				
	    		if (KeyboardListener.KEY_ENTER == keyCode)
	    			connectButton.click();
			}
	    	
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				
				
			}

			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				
				
			}
		});
		
		forcePinDialogBox = new DialogBox(false);
	    forcePinDialogBox.setGlassEnabled(true);
	    forcePinDialogBox.setStyleName("gtvremote-DialogBox");
	    forcePinDialogBox.setText("No Response");
	    DialogBoxContents = new VerticalPanel();
	    deviceNameLabel = new Label("Device Name");
	    deviceNameLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPLabel = new Label("IP Address");
	    deviceIPLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPInfoLabel = new HTML("<br>Do you see a pair code on your TV screen?<br>If so, then press Force PIN.");
	    deviceIPInfoLabel.setStyleName("gtvremote-DialogBox-message4");
	    
	    
	    
	    
	    forcePinButtonHolder = new HorizontalPanel();
	    forcePinButtonHolder.setSpacing(5);
	    forcePinButtonHolder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    


	    
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    deviceNameTextBox.setMaxLength(32);
	    deviceNameTextBox.setSize("200px", "30px");
	    deviceNameTextBox.setText(deviceName);
	    //deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    
	    deviceNameTextBox.addFocusListener(new FocusListener()
	    {
			@Override
			public void onFocus(Widget sender) {
				if(deviceNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					deviceNameTextBox.setText("");
					deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
			public void onLostFocus(Widget sender) {
				if(deviceNameTextBox.getText().compareTo("") == 0)
				{
					deviceNameTextBox.setText("Optional");
					deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
				}
				return;
			}
	    });
	    
	    textBoxholder.add(deviceNameTextBox);
	    
	    


	    
	    
	    
	    textBoxholder2 = new HorizontalPanel();
	    textBoxholder2.setSpacing(5);
	    textBoxholder2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    deviceIPTextBox.setMaxLength(15);
	    deviceIPTextBox.setSize("200px", "30px");
	    deviceIPTextBox.setText(deviceIP);
	    //deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    
	    deviceIPTextBox.addFocusListener(new FocusListener()
	    {
			@Override
			public void onFocus(Widget sender) {
				if(deviceIPTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					deviceIPTextBox.setText("");
					deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
			public void onLostFocus(Widget sender) {
				if(deviceIPTextBox.getText().compareTo("") == 0)
				{
					deviceIPTextBox.setText("xxx.xxx.xxx.xxx");
					deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
				}
				return;
			}
	    });
	    
	    
	    textBoxholder2.add(deviceIPTextBox);
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    listener = new ClickListener()
	    {
			@Override
			public void onClick(Widget sender) {
				
				if(deviceIPTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox") == 0)
				{
					if( validIPAddress( deviceIPTextBox.getText() ) )
					{						
						if( deviceNameTextBox.getText().compareTo("Optional") != 0 )
						{
							deviceNameResult = deviceNameTextBox.getText();
						}
						deviceIPResult = deviceIPTextBox.getText();
						Close();
						
						
					}
					else
					{
						
						deviceIPTextBox.setFocus(true);
						Shake();
					}
				}
				else
				{
					deviceIPTextBox.setFocus(true);
					Shake();
				}
				
			}
	    };
	    listener2 = new ClickListener()
	    {
	    	@Override
			public void onClick(Widget sender) {
								
				deviceIPResult = "cancel";
				
				
			}
	    };
	    
	    connectButton = new Button("Retry", listener);
	    connectButton.setSize("80px", "38px");
	    connectButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(connectButton);
	    
	    Button forcePinButton = new Button("Force PIN");
	    forcePinButton.setSize("60px", "38px");
	    forcePinButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    forcePinButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Close();
				displayPinDialogBox(deviceName, deviceIP, true);		
			}
		});
	    holder.add(forcePinButton);
	    
	    cancelButton = new Button("Cancel", listener2);
	    cancelButton.setSize("80px", "38px");
	    cancelButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(cancelButton);
	    
	    textBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder.setSize("255px", "50px");
	    
	    textBoxholder2.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder2.setSize("255px", "50px");
	    
	    forcePinButtonHolder.setStyleName("gtvremote-DialogBox-forcePinButtonPanel");
	    forcePinButtonHolder.setSize("255px", "20px");
	    
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    DialogBoxContents.add(deviceNameLabel);
	    DialogBoxContents.add(textBoxholder);
	    DialogBoxContents.add(deviceIPLabel);
	    DialogBoxContents.add(textBoxholder2);
	    DialogBoxContents.add(deviceIPInfoLabel);
	    DialogBoxContents.add(forcePinButtonHolder);
	    DialogBoxContents.add(holder);
	    forcePinDialogBox.setWidget(DialogBoxContents);
	    forcePinDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			forcePinDialogBox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		forcePinDialogBox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					forcePinDialogBox.removeStyleName("gtvRemote-PushButton-scaleIn");
				deviceIPTextBox.setFocus(true);
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			forcePinDialogBox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				forcePinDialogBox.hide();
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					forcePinDialogBox.removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public String getNameResponse()
	{
		return deviceNameResult.replace('~', '-');  //Replace invalid separator char with valid one.
	}
	public String getIPResponse()
	{
		return deviceIPResult;
	}
	public Boolean isVisible()
	{
		if(forcePinDialogBox.isVisible())
			return true;
		else
			return false;
	}
	public void addClickListener(ClickHandler clickListener)
	{
		connectButton.addClickHandler(clickListener);
		cancelButton.addClickHandler(clickListener);
	}
	public void Shake()
	{
		final PanelAnimationSlide animation = new PanelAnimationSlide( forcePinDialogBox.getElement() );
		animation.shakeHorizontal();
	}
	public Boolean validIPAddress(String ipToCheckString)
	{
		int dotCount = 0;
		int dotTotal = 0;
		int numCount = 0;
		Boolean leadingZero = false;
		
		for (int i = 0; i < ipToCheckString.length(); i++){
		    char c = ipToCheckString.charAt(i);        
		    //Process char
		    if( (i == 0 || i == ipToCheckString.length()-1) && c == 46)	
		    {	//Checks if dot at start or end of string.
		    	return false;
		    }
		    if( c == 48 && numCount == 0 )
		    {
		    	leadingZero = true;
		    }
		    if( (c >= 48 && c <=57) || c == 46 )
		    {	//Checks if string contains only numbers 0-9 and dots .
		    	if( c >= 48 && c <=57 )
			    {
		    		numCount++;
		    		dotCount = 0;
			    }
		    	if( c == 46 )
			    {
		    		dotCount++;
		    		dotTotal++;
		    		numCount = 0;
		    		leadingZero = false;
		    		
		    		if(dotCount >= 2)
		    		{
		    			return false;
		    		}
			    }
		    	if( leadingZero && numCount >= 2 )
			    {
			    	return false;
			    }
		    }
		    else
		    {
		    	return false;
		    }
		    if(numCount >= 4)
    		{
    			return false;
    		}
		}
		if(dotTotal != 3)
		{
			return false;
		}
		
		return true;
	}
	
	public static native void displayPinDialogBox(String deviceName, String ipAddress, boolean isNewDevice) /*-{
	  
  		$wnd.displayPinDialogBox(deviceName, ipAddress, isNewDevice);
			
	}-*/;
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
}
