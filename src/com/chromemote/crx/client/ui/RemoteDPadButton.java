package com.chromemote.crx.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class RemoteDPadButton extends FocusPanel{
	
	private Label dPadStatus = new Label("0"); //1 = Mouse is down;   0 = Mouse is not down.
	
	private boolean mouseMovedBool = false;
	
	private String previousStyleName;
	
	private RemoteButton remoteButtons[];
	
	public RemoteDPadButton(RemoteButton buttons[])
	{
		remoteButtons = buttons;
		this.setSize("148px", "148px");
		this.setStyleName("gtvRemote-dPad");
		MouseListener dPadListener = new MouseListener()
	    {	
		@Override
		public void onMouseDown(Widget sender, int x, int y) {
			
			
			if(		(x-74)*(x-74)+(y-74)*(y-74) < (74*74)		)	//Checks if click is within circle.
			{
				//dPadLabel.setText("dPadClicked(x,y): (" + x + "," + y + ")");
			}
			
			detectDPadDirection(x, y);
			
			dPadStatus.setText("1");
			mouseMovedBool = false;
		}

		@Override
		public void onMouseEnter(Widget sender) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMouseLeave(Widget sender) {
			
			if(previousStyleName.compareTo("gtvRemote-dPad-PressDn") == 0)
				sendDpadKeyEvent("DPAD_CENTER", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideLeft") == 0)
				sendDpadKeyEvent("DPAD_LEFT", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideRight") == 0)
				sendDpadKeyEvent("DPAD_RIGHT", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideUp") == 0)
				sendDpadKeyEvent("DPAD_UP", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideDown") == 0)
				sendDpadKeyEvent("DPAD_DOWN", false);
			
			
			dPadStatus.setText("0");
			removeDPadStyles();
			mouseMovedBool = false;
			
			previousStyleName = "";
			
		}

		@Override
		public void onMouseMove(Widget sender, int x, int y) {
			
			if(dPadStatus.getText().compareTo("1") == 0)		//Only takes action while onMouseDown is triggered also.
			{
				mouseMovedBool = true;
				detectDPadDirection(x, y);
				
				
			}
		}

		@Override
		public void onMouseUp(Widget sender, int x, int y) {
			
			if(		(x-74)*(x-74)+(y-74)*(y-74) < (74*74)		)	//Checks if click is within circle.
			{
				//dPadLabel.setText("dPadClicked(x,y): (" + x + "," + y + ")");
				
				if(previousStyleName.compareTo("gtvRemote-dPad-PressDn") == 0)
					sendDpadKeyEvent("DPAD_CENTER", false);
				else if(previousStyleName.compareTo("gtvRemote-dPad-slideLeft") == 0)
					sendDpadKeyEvent("DPAD_LEFT", false);
				else if(previousStyleName.compareTo("gtvRemote-dPad-slideRight") == 0)
					sendDpadKeyEvent("DPAD_RIGHT", false);
				else if(previousStyleName.compareTo("gtvRemote-dPad-slideUp") == 0)
					sendDpadKeyEvent("DPAD_UP", false);
				else if(previousStyleName.compareTo("gtvRemote-dPad-slideDown") == 0)
					sendDpadKeyEvent("DPAD_DOWN", false);
				
				
				
				
				removeDPadStyles();
				
			}
				
			dPadStatus.setText("0");
			mouseMovedBool = false;
		}
	    
	    };
		this.addMouseListener(dPadListener);
		
	}
	
	private void removeDPadStyles()
	{
		PanelAnimationSlide slideAnimation = new PanelAnimationSlide( this.getElement() );
		slideAnimation.scrollTo( 86, 227, 50 );
		
		PanelAnimationScale scaleAnimation = new PanelAnimationScale( this.getElement() );
		scaleAnimation.scaleTo( 148, 148, 50 );
		
		String styleName = this.getStyleName();
		this.removeStyleName("gtvRemote-dPad-PressUp");
		this.removeStyleName("gtvRemote-dPad-PressDn");
		this.removeStyleName("gtvRemote-dPad-slideLeft");
		this.removeStyleName("gtvRemote-dPad-slideRight");
		this.removeStyleName("gtvRemote-dPad-slideUp");
		this.removeStyleName("gtvRemote-dPad-slideDown");
		if (styleName.compareTo("gtvRemote-dPad-PressUp") == 0)
		{
			this.addStyleName("gtvRemote-dPad-PressDn");
		}
		if (styleName.compareTo("gtvRemote-dPad-PressDn") == 0)
		{
			this.addStyleName("gtvRemote-dPad-PressUp");
		}
		if (styleName.compareTo("gtvRemote-dPad-slideLeft") == 0)
		{
			this.addStyleName("gtvRemote-dPad-slideLeftReturn");
		}
		if (styleName.compareTo("gtvRemote-dPad-slideRight") == 0)
		{
			this.addStyleName("gtvRemote-dPad-slideRightReturn");
		}
		if (styleName.compareTo("gtvRemote-dPad-slideUp") == 0)
		{
			this.addStyleName("gtvRemote-dPad-slideUpRetsurn");
		}
		if (styleName.compareTo("gtvRemote-dPad-slideDown") == 0)
		{
			this.addStyleName("gtvRemote-dPad-slideDownReturn");
		}
		
	}
	
	private void detectDPadDirection(int x, int y)
	{
		if(		(x-74)*(x-74)+(y-74)*(y-74) < (74*74)		)	//Checks if click is within circle.	(x-h)^2+(y-k)^2 < r^2s
		{
			
			
			if ( (x > 55 && y > 55) && (x < 93 && y < 93) )		//Checks if within the center square
			{
				if (!mouseMovedBool || previousStyleName.compareTo("gtvRemote-dPad-PressDn") != 0)
				{
					removeDPadStyles();
					PanelAnimationScale scaleAnimation = new PanelAnimationScale( this.getElement() );
					scaleAnimation.scaleTo( 143, 143, 50 );
					PanelAnimationSlide slideAnimation = new PanelAnimationSlide( this.getElement() );
					slideAnimation.scrollTo( 86+2, 227+2, 50 );
					//this.addStyleName("gtvRemote-dPad-PressDn");
					sendDpadKeyEvent("DPAD_CENTER", true);
					previousStyleName = "gtvRemote-dPad-PressDn";
				}
				
			}
			else												//Is inside Circle but not in the Center Square.
			{
				Boolean SouthWest = false;
				Boolean NorthWest = false;
				int element;
				
				if (x < y)	{	element = (x) * (y * (-1) );	}	//Checks if direction is South-West. (Down-Left)	
				else 		{	element = (x) * (y);			}
				if (   element < 0   )											
				{	
					SouthWest = true;	//toast("SOUTHWEST");
				}
				
				if (   148*x + 148*y - 148*148 < 0  )											
				{	
					NorthWest = true;
				}
				
				if 		(    SouthWest &&  NorthWest   )					//Direction Clicked is Left					
				{
					if (!mouseMovedBool || previousStyleName.compareTo("gtvRemote-dPad-slideLeft") != 0)
					{
						removeDPadStyles();
						PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
						animation.scrollTo( 86-4, 227, 50 );
						//this.addStyleName("gtvRemote-dPad-slideLeft");
						sendDpadKeyEvent("DPAD_LEFT", true);
						previousStyleName = "gtvRemote-dPad-slideLeft";
					}
				}
				else if (   !SouthWest && !NorthWest   )					//Direction Clicked is Right
				{
					if (!mouseMovedBool || previousStyleName.compareTo("gtvRemote-dPad-slideRight") != 0)
					{	
						removeDPadStyles();
						PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
						animation.scrollTo( 86+4, 227, 50 );
						//this.addStyleName("gtvRemote-dPad-slideRight");
						sendDpadKeyEvent("DPAD_RIGHT", true);
						previousStyleName = "gtvRemote-dPad-slideRight";
					}
				}
				else if (   !SouthWest &&  NorthWest   )					//Direction Clicked is Up					
				{
					if (!mouseMovedBool || previousStyleName.compareTo("gtvRemote-dPad-slideUp") != 0)
					{
						removeDPadStyles();
						PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
						animation.scrollTo( 86, 227-4, 50 );
						//this.addStyleName("gtvRemote-dPad-slideUp");
						sendDpadKeyEvent("DPAD_UP", true);
						previousStyleName = "gtvRemote-dPad-slideUp";
					}
				}
				else if (    SouthWest && !NorthWest   )					//Direction Clicked is Down					
				{
					if (!mouseMovedBool || previousStyleName.compareTo("gtvRemote-dPad-slideDown") != 0)
					{
						removeDPadStyles();
						PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
						animation.scrollTo( 86, 227+4, 50 );
						//this.addStyleName("gtvRemote-dPad-slideDown");
						sendDpadKeyEvent("DPAD_DOWN", true);
						previousStyleName = "gtvRemote-dPad-slideDown";
					}
					
				}
			}
			
		}
		else// if(!mouseMovedBool)//If outside the circle and mouse was not dragged in.
		{
			
			if(previousStyleName.compareTo("gtvRemote-dPad-PressDn") == 0)
				sendDpadKeyEvent("DPAD_CENTER", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideLeft") == 0)
				sendDpadKeyEvent("DPAD_LEFT", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideRight") == 0)
				sendDpadKeyEvent("DPAD_RIGHT", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideUp") == 0)
				sendDpadKeyEvent("DPAD_UP", false);
			else if(previousStyleName.compareTo("gtvRemote-dPad-slideDown") == 0)
				sendDpadKeyEvent("DPAD_DOWN", false);
			removeDPadStyles();
			if(x <= 73)			//On the Left side.
			{
				if (y <= 13)		//Over button #14
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[14]);
				}
				else if ( y <= 73)	//Over button #17
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[17]);
				}
				else if ( y <= 134)	//Over button #21
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[21]);
				}
				else if ( y <= 148)	//Over button #26
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[26]);
				}
			}
			else				//On the Right side.
			{
				if (y <= 13)		//Over button #15
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[15]);
				}
				else if ( y <= 73)	//Over button #19
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[19]);
				}
				else if ( y <= 134)	//Over button #23
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[23]);
				}
				else if ( y <= 148)	//Over button #27
				{
					DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false), remoteButtons[27]);
				}
			}
		}
	}
	
	
	public static native void sendDpadKeyEvent(String direction, boolean keyDown) /*-{
		$wnd.sendKeyEvent(direction, keyDown);
		$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + direction + ' \' sent to connected device.');
	}-*/;
	public static native void toast(String msg) /*-{
		$wnd.displayToast(msg);
	}-*/;
}
