package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

//Draws Enter PIN Code DialogBox with DeviceID, textBox, Connect and Cancel Buttons

class ResetDefaultsDialogBox
{
	
	DialogBox questionDialogbox;
	String questionResult = "null";
	TextBox enterPinTextBox;
	Button connectButton, cancelButton;
	
			 
	public ResetDefaultsDialogBox(String messageString)
	{
		VerticalPanel DialogBoxContents;
		ClickHandler listener, listener2;
		HTML message;
		
		HorizontalPanel holder;
		HorizontalPanel textBoxholder;
	    
	   
		
		questionDialogbox = new DialogBox(false);
	    questionDialogbox.setGlassEnabled(true);
	    questionDialogbox.setStyleName("gtvremote-DialogBox");
	    questionDialogbox.setText("Warning!");
	    questionDialogbox.setWidth("255px");
	    DialogBoxContents = new VerticalPanel();
	    
	    
	    
	    message = new HTML(messageString + "<br /><br />");
	    message.setWidth("160");
	    message.setStyleName("gtvremote-DialogBox-message");
	    
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    
	    
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    listener = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				questionResult = "ok";
				
			}
	    };
	    listener2 = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				questionResult = "cancel";
				
			}
	    };
	    
	    connectButton = new Button("Reset to Defaults", listener);
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
	    //DialogBoxContents.add(textBoxholder);
	    DialogBoxContents.add(holder);
	    questionDialogbox.setWidget(DialogBoxContents);
	    questionDialogbox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			questionDialogbox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		questionDialogbox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					questionDialogbox.removeStyleName("gtvRemote-PushButton-scaleIn");
				enterPinTextBox.setFocus(true);
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			questionDialogbox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				questionDialogbox.hide();
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					questionDialogbox.removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public String getResponse()
	{
		return questionResult;
	}
	public Boolean isVisible()
	{
		if(questionDialogbox.isVisible())
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
		final PanelAnimationSlide animation = new PanelAnimationSlide( questionDialogbox.getElement() );
		animation.shakeHorizontal();
	}
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
	
}
