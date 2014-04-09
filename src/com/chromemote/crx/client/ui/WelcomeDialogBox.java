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
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WelcomeDialogBox extends DialogBox
{
	PushButton donateButton;
	Button		closeButton;
	
	public WelcomeDialogBox()
	{
		super(false);
		VerticalPanel dialogBoxContents;
		ClickHandler closeButtonHandler, donateButtonHandler;
		
		HTML updateDetailsMsg;
		
		ScrollPanel 	scrollPanel;
		
		HorizontalPanel buttonHolder;
		
		
	    this.setGlassEnabled(true);
	    this.setStyleName("gtvremote-DialogBox");
	    this.setText("New Install / Update");
	    dialogBoxContents = new VerticalPanel();
	    
	    String updateMsg = getNewInstallUpdateMsg();

	    
	    updateDetailsMsg = new HTML(updateMsg);
	    updateDetailsMsg.setStyleName("gtvremote-DialogBox-message3");
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    buttonHolder = new HorizontalPanel();
	    buttonHolder.setSpacing(5);
	    buttonHolder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    

	    donateButtonHandler = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				sendGAEvent("First_Launch","Donate");
				Window.open("http://chromemote.com/support-us/", "_blank", null);
				Close();
				
				acknowledgeFirstInstall();
			}
	    };
	    closeButtonHandler = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				sendGAEvent("First_Launch","Close");
				Close();
				
				acknowledgeFirstInstall();
			}
	    };
	    
	    donateButton = new PushButton("", donateButtonHandler);
	    donateButton.setSize("120px", "38px");
	    donateButton.setStyleName("chromemote-donate-PushButton");
	    buttonHolder.add(donateButton);
	    
	    closeButton = new Button("Close", closeButtonHandler);
	    closeButton.setSize("120px", "38px");
	    closeButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    buttonHolder.add(closeButton);

	    
	    
	    scrollPanel = new ScrollPanel();
	    scrollPanel.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    scrollPanel.setSize("255px","200px");
	    scrollPanel.add(updateDetailsMsg);
	    
	    buttonHolder.setStyleName("gtvremote-DialogBox-footer");
	    buttonHolder.setSize("255px", "35px");

	    dialogBoxContents.add(scrollPanel);
	    dialogBoxContents.add(buttonHolder);
	    this.setWidget(dialogBoxContents);
	    this.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 110);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			this.addStyleName("gtvRemote-PushButton-scaleIn");
		
		this.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					removeStyleName("gtvRemote-PushButton-scaleIn");
				
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			this.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				hide();
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public boolean isVisible()
	{
		if(this.isVisible())
			return true;
		else
			return false;
	}
	public void addClickHandler(ClickHandler clickHandler)
	{
		donateButton.addClickHandler(clickHandler);
		closeButton.addClickHandler(clickHandler);
	}
	public void Shake()
	{
		final PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
		animation.shakeHorizontal();
	}
	public static native void sendGAEvent(String category, String action) /*-{
	
		$wnd.sendGAEvent(category, action);

	}-*/;
	
	
	public static native void acknowledgeFirstInstall()  /*-{
	
		backgroundPageWindow.firstInstallAck = true;
		installAck = true;
		localStorage.setItem('install-ack', installAck);

	}-*/;
	
	public static native String getNewInstallUpdateMsg()  /*-{
	
		return backgroundPageWindow.newInstallUpdateMsg;
		
	}-*/;
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
}
