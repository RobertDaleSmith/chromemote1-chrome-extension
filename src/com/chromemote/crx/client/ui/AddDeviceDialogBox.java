package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

//Draws Enter PIN Code DialogBox with DeviceID, textBox, Connect and Cancel Buttons

class AddDeviceDialogBox
{
	
	DialogBox addDeviceDialogBox;
	String deviceNameResult = "Google TV Device";
	String deviceIPResult = "null";
	TextBox deviceNameTextBox;
	TextBox deviceIPTextBox;
	Button connectButton, cancelButton;
	
			 
	public AddDeviceDialogBox()
	{
		VerticalPanel DialogBoxContents;
		ClickHandler listener, listener2;
		Label deviceNameLabel;
		Label deviceIPLabel;
		HTML deviceIPInfoLabel;
		
		HorizontalPanel holder;
		HorizontalPanel textBoxholder;
		HorizontalPanel textBoxholder2;
	    
	    deviceNameTextBox = new TextBox();
	    
	    deviceNameTextBox.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getCharCode())
	    			deviceIPTextBox.setFocus(true);
			}
		});
	    
	    deviceIPTextBox = new TextBox();
	    
	    deviceIPTextBox.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getCharCode())
	    			connectButton.click();
			}
		});
		
		addDeviceDialogBox = new DialogBox(false);
	    addDeviceDialogBox.setGlassEnabled(true);
	    addDeviceDialogBox.setStyleName("gtvremote-DialogBox");
	    addDeviceDialogBox.setText("Add Google TV");
	    DialogBoxContents = new VerticalPanel();
	    deviceNameLabel = new Label("Custom Name");
	    deviceNameLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPLabel = new Label("IP Address");
	    deviceIPLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPInfoLabel = new HTML("You can find IP address of your Google TV<br>device by going to:<br>Settings > Network > Status");
	    deviceIPInfoLabel.setStyleName("gtvremote-DialogBox-message3");
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    deviceNameTextBox.setMaxLength(32);
	    deviceNameTextBox.setSize("200px", "30px");
	    deviceNameTextBox.setText("Optional");
	    deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    	    
	    deviceNameTextBox.addFocusHandler(new FocusHandler()
	    {
			@Override
			public void onFocus(FocusEvent event) {
				if(deviceNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					deviceNameTextBox.setText("");
					deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
	    });
	    deviceNameTextBox.addBlurHandler(new BlurHandler()
	    {
			public void onBlur(BlurEvent event) {
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
	    deviceIPTextBox.setText("xxx.xxx.xxx.xxx");
	    deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    
	    
	    deviceIPTextBox.addFocusHandler(new FocusHandler()
	    {
			@Override
			public void onFocus(FocusEvent event) {
				if(deviceIPTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					deviceIPTextBox.setText("");
					deviceIPTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
	    });
	    deviceIPTextBox.addBlurHandler(new BlurHandler()
	    {
			public void onBlur(BlurEvent event) {
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
	    
	    listener = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				
				if(deviceIPTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox") == 0)
				{
					if( validIPAddress( deviceIPTextBox.getText() ) )
					{						
						if( deviceNameTextBox.getText().compareTo("Optional") != 0 )
						{
							deviceNameResult = stripHTML(deviceNameTextBox.getText());
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
	    listener2 = new ClickHandler()
	    {
	    	@Override
	    	public void onClick(ClickEvent event) {
				deviceIPResult = "cancel";
			}
	    };
	    
	    connectButton = new Button("Connect", listener);
	    connectButton.setSize("120px", "38px");
	    connectButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(connectButton);
	    
	    cancelButton = new Button("Cancel", listener2);
	    cancelButton.setSize("120px", "38px");
	    cancelButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(cancelButton);
	    
	    textBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder.setSize("255px", "50px");
	    
	    textBoxholder2.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder2.setSize("255px", "50px");
	    
	    
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    DialogBoxContents.add(deviceNameLabel);
	    DialogBoxContents.add(textBoxholder);
	    DialogBoxContents.add(deviceIPLabel);
	    DialogBoxContents.add(textBoxholder2);
	    DialogBoxContents.add(deviceIPInfoLabel);
	    DialogBoxContents.add(holder);
	    addDeviceDialogBox.setWidget(DialogBoxContents);
	    addDeviceDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			addDeviceDialogBox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		addDeviceDialogBox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					addDeviceDialogBox.removeStyleName("gtvRemote-PushButton-scaleIn");
				deviceIPTextBox.setFocus(true);
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			addDeviceDialogBox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				addDeviceDialogBox.hide();
				
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					addDeviceDialogBox.removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public String getNameResponse()
	{
		return deviceNameResult.replace('~', '-').replace('"', '\'').replace('\\', '/');  //Replace invalid separator char with valid one.
	}
	public String getIPResponse()
	{
		return deviceIPResult;
	}
	public Boolean isVisible()
	{
		if(addDeviceDialogBox.isVisible())
			return true;
		else
			return false;
	}
	public void addClickHandler(ClickHandler clickHandler)
	{
		connectButton.addClickHandler(clickHandler);
		cancelButton.addClickHandler(clickHandler);
	}
	public void Shake()
	{
//Replaced because of buggy CSS animation behavior.
//		addDeviceDialogBox.addStyleName("gtvremote-DialogBox-shake");
//		Timer t = new Timer() {
//			public void run() {
//				addDeviceDialogBox.removeStyleName("gtvremote-DialogBox-shake");
//			}
//	    };
//	    t.schedule(400);    // Schedule the timer to run once in 0.4 seconds.
//Replaced with a custom GWT animation class and method:		
		final PanelAnimationSlide animation = new PanelAnimationSlide( addDeviceDialogBox.getElement() );
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
	
	private String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
	
}
