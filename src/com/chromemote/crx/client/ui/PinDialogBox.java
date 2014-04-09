package com.chromemote.crx.client.ui;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

//Draws Enter PIN Code DialogBox with DeviceID, textBox, Connect and Cancel Buttons

@SuppressWarnings("deprecation")
class PinDialogBox
{
	
	static DialogBox pinDialogbox;
	String pairCodeResult = "null";
	TextBox enterPinTextBox;
	Button connectButton, cancelButton;
	
			 
	public PinDialogBox(String deviceIDStr)
	{
		VerticalPanel DialogBoxContents;
		ClickListener listener, listener2;
		HTML message;
		
		HorizontalPanel holder;
		HorizontalPanel textBoxholder;
	    
	    enterPinTextBox = new TextBox();
	    
	    enterPinTextBox.addKeyboardListener(new KeyboardListener(){
	    	@Override
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
	    		if (KeyboardListener.KEY_ENTER == keyCode)
	    			connectButton.click();
			}
	    	
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
		});
		
		pinDialogbox = new DialogBox(false);
	    pinDialogbox.setGlassEnabled(true);
	    pinDialogbox.setStyleName("gtvremote-DialogBox");
	    pinDialogbox.setText("Enter PIN code");
	    DialogBoxContents = new VerticalPanel();
	    
	    //Ensure device name fits message box size.
	    String upToNCharacters = deviceIDStr.substring(0, Math.min(deviceIDStr.length(), 20));
		if (upToNCharacters != deviceIDStr)
		{
			upToNCharacters = upToNCharacters + "...";
		}
	    
	    message = new HTML(upToNCharacters);
	    message.setStyleName("gtvremote-DialogBox-message");
	    
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    enterPinTextBox.setMaxLength(4);
	    enterPinTextBox.setSize("200px", "30px");
	    enterPinTextBox.setText("Enter PIN code");
	    enterPinTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    
	    enterPinTextBox.addFocusListener(new FocusListener()
	    {
			@Override
			public void onFocus(Widget sender) {
				if(enterPinTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					enterPinTextBox.setText("");
					enterPinTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
			public void onLostFocus(Widget sender) {
				if(enterPinTextBox.getText().compareTo("") == 0)
				{
					enterPinTextBox.setText("Enter PIN code");
					enterPinTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
				}
				return;
			}
	    });
	    
	    textBoxholder.add(enterPinTextBox);
	    
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    listener = new ClickListener()
	    {
			@Override
			public void onClick(Widget sender) {
				
				if(enterPinTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox") == 0)
				{
					if(enterPinTextBox.getText().length() == 4)
					{
						pairCodeResult = enterPinTextBox.getText();
						
					}
					else
					{
						
						enterPinTextBox.setFocus(true);
						Shake();
					}
				}
				else
				{
					enterPinTextBox.setFocus(true);
					Shake();
				}
				
			}
	    };
	    listener2 = new ClickListener()
	    {
	    	@Override
			public void onClick(Widget sender) {
								
				pairCodeResult = "cancel";
				
				
			}
	    };
	    
	    connectButton = new Button("Pair", listener);
	    connectButton.setSize("120px", "38px");
	    connectButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(connectButton);
	    
	    cancelButton = new Button("Cancel", listener2);
	    cancelButton.setSize("120px", "38px");
	    cancelButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(cancelButton);
	    
	    textBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder.setSize("255px", "58px");
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    DialogBoxContents.add(message);
	    DialogBoxContents.add(textBoxholder);
	    DialogBoxContents.add(holder);
	    pinDialogbox.setWidget(DialogBoxContents);
	    pinDialogbox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140);
	    exportStaticMethod();
	}
	public void Show()
	{

		pinDialogbox.show();
		Timer t = new Timer() {
			public void run() {
				enterPinTextBox.setFocus(true);
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
		Timer t = new Timer() {
			public void run() {
				pinDialogbox.hide();
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public String getResponse()
	{
		return pairCodeResult;
	}
	public Boolean isVisible()
	{
		if(pinDialogbox.isVisible())
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
		final PanelAnimationSlide animation = new PanelAnimationSlide( pinDialogbox.getElement() );
		animation.shakeHorizontal();
	}
	
	public static void closePinDialogBox()
	{
		pinDialogbox.hide();
		
	}
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
	
	public static native void exportStaticMethod() /*-{
	
		$wnd.closePinDialogBox = $entry(@com.chromemote.crx.client.ui.PinDialogBox::closePinDialogBox());
	
	}-*/;
}
