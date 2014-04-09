package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

//Draws Waiting DialogBox with Spinner, Message, and Cancel Button
@SuppressWarnings("deprecation")
class WaitingDialogBox
{
	DialogBox waitingDialogbox;
	Button button;
	
	public WaitingDialogBox(String messageStr)
	{
		VerticalPanel DialogBoxContents;
		ClickListener listener;
		HTML message;
		
		HorizontalPanel holder;
		
	    waitingDialogbox = new DialogBox(false);
	    HorizontalPanel messagePanel = new HorizontalPanel();
	    messagePanel.setStyleName("gtvremote-DialogBox-messagePanel");
	    messagePanel.setSize("255px","75px");
	    Image waitingImage = new Image("images/waiting.gif");
	    //waitingImage.setStyleName("gtvremote-DialogBox-waitingImg");
	    messagePanel.add(waitingImage);
	    
	    waitingDialogbox.setGlassEnabled(true);
	    waitingDialogbox.setStyleName("gtvremote-DialogBox2");
	    DialogBoxContents = new VerticalPanel();
	    
	    message = new HTML(messageStr);
	    message.setStyleName("gtvremote-DialogBox-message2");
	    messagePanel.add(message);
	    
	    listener = new ClickListener()
	    {
			@Override
			public void onClick(Widget sender) {
				Close();
			}
	    };
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    	    	    
	    button = new Button("Cancel", listener);
	    button.setSize("120px", "38px");
	    button.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(button);
	    
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    DialogBoxContents.add(messagePanel);
	    DialogBoxContents.add(holder);
	    waitingDialogbox.setWidget(DialogBoxContents);
	    
	    waitingDialogbox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 180);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			waitingDialogbox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		waitingDialogbox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					waitingDialogbox.removeStyleName("gtvRemote-PushButton-scaleIn");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			waitingDialogbox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				waitingDialogbox.hide();
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					waitingDialogbox.removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void addClickListener(ClickHandler clickListener)
	{
		button.addClickHandler(clickListener);
		
	}
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
}