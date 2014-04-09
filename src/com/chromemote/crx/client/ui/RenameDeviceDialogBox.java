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


class RenameDeviceDialogBox
{
	
	DialogBox renameDeviceDialogBox;
	String deviceNameResult = "Google TV Device";
	String deviceIPResult = "null";
	TextBox deviceNameTextBox;
	TextBox deviceIPTextBox;
	Button renameButton, cancelButton;
	
			 
	public RenameDeviceDialogBox(String deviceName, String deviceIPAddress)
	{
		VerticalPanel DialogBoxContents;
		ClickHandler listener;
		ClickHandler listener2;
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
					renameButton.click();
			}
		});
	    
	    deviceIPTextBox = new TextBox();
	    
	    deviceIPTextBox.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getCharCode())
					renameButton.click();
			}
		});
	    
	    deviceIPTextBox.setEnabled(false);
		
		renameDeviceDialogBox = new DialogBox(false);
	    renameDeviceDialogBox.setGlassEnabled(true);
	    renameDeviceDialogBox.setStyleName("gtvremote-DialogBox");
	    renameDeviceDialogBox.setText("Rename Google TV");
	    DialogBoxContents = new VerticalPanel();
	    deviceNameLabel = new Label("Custom Name");
	    deviceNameLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPLabel = new Label("IP Address");
	    deviceIPLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPInfoLabel = new HTML(" ");
	    //deviceIPInfoLabel = new HTML("You can find IP address of your Google TV<br>device by going to:<br>Settings > Network > Status");
	    deviceIPInfoLabel.setStyleName("gtvremote-DialogBox-message3");
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    deviceNameTextBox.setMaxLength(64);
	    deviceNameTextBox.setSize("200px", "30px");
	    deviceNameTextBox.setText("New device name here..");
	    deviceNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    
	    deviceNameTextBox.addFocusHandler(new FocusHandler()
	    {
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
					deviceNameTextBox.setText("New device name here..");
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
				
				if(deviceNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
				{
											
						
						deviceNameResult = stripHTML(deviceNameTextBox.getText());
						deviceIPResult   = deviceIPTextBox.getText();
						Close();
					
				}
				else
				{
					deviceNameTextBox.setFocus(true);
					deviceNameTextBox.selectAll();
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
	    
	    renameButton = new Button("Rename", listener);
	    renameButton.setSize("120px", "38px");
	    renameButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(renameButton);
	    
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
	    renameDeviceDialogBox.setWidget(DialogBoxContents);
	    renameDeviceDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140);
	    
	    
	    deviceNameTextBox.setText(deviceName);
		deviceIPTextBox.setText(deviceIPAddress);
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			renameDeviceDialogBox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		renameDeviceDialogBox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					renameDeviceDialogBox.removeStyleName("gtvRemote-PushButton-scaleIn");
				deviceNameTextBox.setFocus(true);
				deviceNameTextBox.selectAll();
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			renameDeviceDialogBox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				renameDeviceDialogBox.hide();
				
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					renameDeviceDialogBox.removeStyleName("gtvRemote-PushButton-scaleOut");
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
		if(renameDeviceDialogBox.isVisible())
			return true;
		else
			return false;
	}
	public void addClickHandler(ClickHandler clickHandler)
	{
		renameButton.addClickHandler(clickHandler);
		cancelButton.addClickHandler(clickHandler);
	}
	public void Shake()
	{
		final PanelAnimationSlide animation = new PanelAnimationSlide( renameDeviceDialogBox.getElement() );
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
