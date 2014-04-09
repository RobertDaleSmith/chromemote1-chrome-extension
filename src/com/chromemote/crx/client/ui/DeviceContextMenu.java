package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;


class DeviceContextMenu extends PopupPanel
{
	
	private VerticalPanel DialogBoxContents = new VerticalPanel();
	
	private int menuWidth = 110;
	private int commandCount = 0;
	private int menuHeight = 4 + (26 * commandCount);
	private int initialX = 0;
	private int initialY = 0;
	
	public DeviceContextMenu(int x, int y)
	{
		super(true);
	    this.setGlassEnabled(false);
	    this.setStyleName("chromemote-deviceContextMenu");
	    this.setWidth(menuWidth + "px");
	    
	    initialX = x;
		initialY = y;
	    
	    setPosition(x, y);
	    
	    
	    
	    
	    
	    this.setWidget(DialogBoxContents);
	    
	    
		HorizontalPanel holder;
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    
	    holder.setStyleName("gtvremote-DialogBox-message");
	    holder.setSize("100px", "90px");

	    //DialogBoxContents.add(holder);	    
	    
	    
	    
	    
	    
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			this.addStyleName("gtvRemote-PushButton-scaleIn");
		
		this.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					this.removeStyleName("gtvRemote-PushButton-scaleIn");
				
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
		hide();
	}
	
	public boolean isVisible()
	{
		if(this.isVisible())
			return true;
		else
			return false;
	}
	
	public void addClickListener(ClickHandler clickListener)
	{
		//connectButton.addClickHandler(clickListener);
		//cancelButton.addClickHandler(clickListener);
	}
	
	public void Shake()
	{
		this.addStyleName("gtvremote-DialogBox-shake");
		Timer t = new Timer() {
			public void run() {
				removeStyleName("gtvremote-DialogBox-shake");
			}
	    };
	    t.schedule(400);    // Schedule the timer to run once in 0.4 seconds.
	}
	
	public void addMenuCommand(String commmandName, ClickHandler handler)
	{
		PushButton menuCommand = new PushButton(commmandName);
		menuCommand.setStyleName("chromemote-deviceContextMenuButton");
		menuCommand.addClickHandler(handler);
		
		menuCommand.addClickHandler(new ClickHandler(){
		
			@Override
			public void onClick(ClickEvent event) {
				
				hide();
				
			}});
	    DialogBoxContents.add(menuCommand);
		
	    
	    commandCount++;
		menuHeight = 2 + (26 * commandCount);
	    
	    setPosition(initialX, initialY);
	}
	
	public void setPosition(int x, int y)
	{
		//Keep x,y within UI
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
		    if(x > 320 - menuWidth)
		    	x = 320 - menuWidth;
		    if(y > 549 - menuHeight)
		    	y = 549 - menuHeight;
		    
		}
		
		this.setPopupPosition(x, y);
	}
	
	public static native String getDetectedOS()  /*-{
		
		return $wnd.backgroundPageWindow.osDetected;
		
	}-*/;
	public static native void displayToast(String message)  /*-{
		$wnd.displayToast(message);
	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;
	
}
