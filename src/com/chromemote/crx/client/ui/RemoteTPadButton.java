package com.chromemote.crx.client.ui;

import com.chromemote.crx.client.KeyCodeLabel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.MouseWheelVelocity;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class RemoteTPadButton extends FocusPanel{

	private KeyCodeLabel tPadLabel;
	private String mouseCoord;
	private String mouseCoordDown;
	private String mouseCoordUp;
	
	private int deltaXint, deltaYint, lastXint, lastYint;
	
	private Label tPadStatus = new Label("0"); //1 = Mouse is down;   0 = Mouse is not down.
	
	public RemoteTPadButton(KeyCodeLabel label)
	{
		tPadLabel = label;
	
		mouseCoord     = "";
		mouseCoordDown = "";
		mouseCoordUp   = "";
		
		this.setSize("320px", "240px");
		this.setStyleName("gtvRemote-touchPad");
		this.getElement().setId("Touch-Mouse-Pad");
		
		MouseListener touchPadListener = new MouseListener()
	    {	
		@Override
		public void onMouseDown(Widget sender, int x, int y) {
			mouseCoord = (x + "," + y);
			mouseCoordDown = (x + "," + y);		//Mouse Down x,y Coordinates.
			tPadStatus.setText("1");
			mouseCallListener();
			
			lastXint = x;
			lastYint = y;
		}

		@Override
		public void onMouseEnter(Widget sender) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMouseLeave(Widget sender) {
			
			tPadStatus.setText("0");
		}

		@Override
		public void onMouseMove(Widget sender, int x, int y) {
			
			if(tPadStatus.getText().compareTo("1") == 0)
			{
				mouseCoord = (x + "," + y);
				mouseCallListener();
				
				deltaXint = x - lastXint;
				deltaYint = y - lastYint;
				
				sendTpadMouseMoveEvent(deltaXint * 2, deltaYint * 2);
				
				lastXint = x;
				lastYint = y;
			}
		}

		@Override
		public void onMouseUp(Widget sender, int x, int y) {
			
			mouseCoord = (x + "," + y);
			mouseCoordUp = (x + "," + y);		//Mouse Up x,y Coordinates.
			tPadStatus.setText("0");
			mouseCallListener();
		}
	    
	    };
		this.addMouseListener(touchPadListener);
		
				
		MouseWheelListener touchPadWheelListener = new MouseWheelListener()
	    {

			@Override
			public void onMouseWheel(Widget sender, MouseWheelVelocity velocity) {
				// TODO Auto-generated method stub
				sendTpadMouseWheelEvent( 0, velocity.getDeltaY() );
			}	
			
	    };
	    this.addMouseWheelListener(touchPadWheelListener);
		
		
		
	}
	
	private void mouseCallListener()
	{
		if(tPadStatus.getText().compareTo("1") == 0)
		{
			if (mouseCoord.compareTo(mouseCoordUp) == 0 && mouseCoord.compareTo(mouseCoordDown) == 0)
			{
				tPadLabel.setText("MOUSE_MOVE_" + mouseCoord);
				sendLeftButtonTpadKeyEvent(true);
			}	
		}
		else
		{
			tPadLabel.setText("MOUSE_BTN_LEFT");
			sendLeftButtonTpadKeyEvent(false);
			
		}
		
		
		
	}
	
	public static native void sendTpadMouseMoveEvent(int xDelta, int yDelta) /*-{
		
		$wnd.sendTpadMouseMoveEvent(xDelta,yDelta);
		$wnd.backgroundPageWindow.console.log('Chromemote keycode to move cursor by \' (' + xDelta + ',' + yDelta + ') \' sent to connected device.');
		
	}-*/;
	
	public static native void sendTpadMouseWheelEvent(int xScroll, int yScroll) /*-{
	
		$wnd.sendTpadMouseWheelEvent(xScroll,yScroll);
		$wnd.backgroundPageWindow.console.log('Chromemote keycode to scroll \' (' + xScroll + ',' + yScroll + ') \' sent to connected device.');
		
	}-*/;
	
	public static native void sendLeftButtonTpadKeyEvent(boolean keyDown) /*-{
		
		$wnd.sendTpadKeyEvent(1, keyDown);
		$wnd.backgroundPageWindow.console.log('Chromemote keycode for \' LEFT_MOUSE \' click sent to connected device.');
		
	}-*/;
	
	
}
