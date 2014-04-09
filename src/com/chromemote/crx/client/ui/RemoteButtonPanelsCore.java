package com.chromemote.crx.client.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.chromemote.crx.client.KeyCodeLabel;
import com.chromemote.crx.client.KeyCodeHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;

public class RemoteButtonPanelsCore extends FlexTable implements ClickHandler {

	private static final String JSON_PANEL_URL = GWT.getHostPageBaseURL() + "panels/";
	
	private KeyCodeLabel keyCodePressedLabel;
	
	private ArrayList<KeyCodeHandler> handlers = new ArrayList<KeyCodeHandler>();
	
	private int xPositionInt = -320;
	
	RemoteButtonPanel remotePanelPrimary, remotePanelSecondary;
	
	ScrollButtonPanel remoteScrollPanel;
	
	boolean keyboardIsEnabled = true;
	
	private static RemoteButton allRemoteButtons[][] = new RemoteButton[3][33];
		
	public RemoteButtonPanelsCore(KeyCodeLabel callBackCodelabel)
	{
		super();
		keyCodePressedLabel = callBackCodelabel;
		this.setStyleName("unselectable");
		
		//Build and draw remote panels.
		remotePanelPrimary   = new RemoteButtonPanel(JSON_PANEL_URL + "remoteButtonList.json" , "primary"  , keyCodePressedLabel, this, allRemoteButtons);
		remotePanelSecondary = new RemoteButtonPanel(JSON_PANEL_URL + "remoteButtonList2.json", "secondary", keyCodePressedLabel, this, allRemoteButtons);
		remoteScrollPanel    = new ScrollButtonPanel(JSON_PANEL_URL + "scrollButtonList.json" , keyCodePressedLabel, this);
		
		
		this.setCellSpacing(0); 
		this.setCellPadding(0);
		
		setUiType();
		
		if(getTabOrPopUpType().compareTo("popup") == 0)
		{
			this.setWidget(0, 0, remotePanelSecondary);
			this.setWidget(0, 1, remotePanelPrimary);
			this.setWidget(0, 2, remoteScrollPanel);
		}
		else
		{
			this.setWidget(0, 0, remotePanelSecondary);
			this.setWidget(0, 1, remotePanelPrimary);
			this.setWidget(0, 2, remoteScrollPanel);
		}
		
		this.addKeyCodeHandler( new KeyCodeHandler()
		{
			public void onKeyCodeChange(String keyCodeString)
			{
				if ( keyCodeString.compareTo("PANELS_MOVE_RIGHT") == 0 && getTabOrPopUpType().compareTo("popup") == 0)
					slideRightAnimation();

				if ( keyCodeString.compareTo("PANELS_MOVE_LEFT") == 0 && getTabOrPopUpType().compareTo("popup") == 0)
					slideLeftAnimation();
				
				if ( keyCodeString.compareTo("PANELS_MOVE_HOME") == 0 && getTabOrPopUpType().compareTo("popup") == 0)
					slideHomeAnimation();
				
				if ( keyCodeString.compareTo("UI_KEYBOARD") == 0 )
				{
					if (keyboardIsEnabled)
					{
						disableKeyBoardEvents();
					    keyboardIsEnabled = false;
					}
					else
					{
						enableKeyBoardEvents();
						keyboardIsEnabled = true;
					}
					
				}
				if (keyCodeString.compareTo("UI_VOICE") == 0 )
				{
				    showSearchBox();
				    disableKeyBoardEvents();
				    keyboardIsEnabled = false;
				}
				if (keyCodeString.compareTo("UI_TEXT") == 0 )
				{
				    showSearchBox();
				    disableKeyBoardEvents();
				    keyboardIsEnabled = false;
				}
				
				if ( keyCodeString.contains("SP_") && getTabOrPopUpType().compareTo("popup") == 0) {
					slideHomeAnimation();
					keyCodePressedLabel.setText( keyCodePressedLabel.getText().replaceAll("SP_", "") );
					
					Timer t = new Timer() {
					      public void run() {
					    	  remoteScrollPanel.scrollToTop();
					      }
					};
					t.schedule(500);
				}				
				
				
			}
		});
		exportStaticMethod();
	}
	
	public void slideLeftAnimation()
	{
		if(xPositionInt < 0)
		{
			xPositionInt = xPositionInt+320;
			PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
			animation.scrollTo( xPositionInt, 0, 500 );
		}
	}
	
	public void slideRightAnimation()
	{
		if(xPositionInt > -640)
		{
			xPositionInt = xPositionInt-320;
			PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
			animation.scrollTo( xPositionInt, 0, 500 );
		}
	}
	
	private void slideHomeAnimation()
	{
		xPositionInt = -320;
		PanelAnimationSlide animation = new PanelAnimationSlide( this.getElement() );
		animation.scrollTo( xPositionInt, 0, 500 );
	}

    public void addKeyCodeHandler(KeyCodeHandler listener)
    {
    	handlers.add(listener);
    }

    public void removeKeyCodeHandler(KeyCodeHandler listener)
    {
    	handlers.remove(listener);
    }

    public void onKeyCodePress(String keyCodeString)
    {
        for(Iterator<KeyCodeHandler> it = handlers.iterator(); it.hasNext();)
        {
        	KeyCodeHandler listener = (KeyCodeHandler) it.next();
            listener.onKeyCodeChange(keyCodeString);
        }
    }
	
	public void onClick(ClickEvent event) 
	{
		onKeyCodePress( keyCodePressedLabel.getText() );
	}
	
    public void showSearchBox()
    {
    	final SearchDialogBox searchDialogbox = new SearchDialogBox();
    	ClickHandler clickListener = new ClickHandler()
	    {
    		@Override
    		public void onClick(ClickEvent event) {
    			if(searchDialogbox.getResponse().compareTo("cancel") == 0) {
    				enableKeyBoardEvents();
    				keyboardIsEnabled = true;
    			}	
    			else if(searchDialogbox.getResponse().compareTo("null") != 0) 
    			{
    				enableKeyBoardEvents();
    				keyboardIsEnabled = true;
    				sendAnymoteSearchStringMessage( searchDialogbox.getResponse() );
    			}
    		}
	    };
	    searchDialogbox.addClickHandler(clickListener);
	    searchDialogbox.Show();
    	
    }
    
    public static void launchButtonEditDialogBox()
    {
		disableKeyBoardEvents();
		final EditRemoteButtonDialogBox editRemoteButtonDialogBox = new EditRemoteButtonDialogBox(allRemoteButtons, 1, 1);
		ClickHandler clickListener = new ClickHandler()
	    {	public void onClick(ClickEvent event) 
			{
	    		if(editRemoteButtonDialogBox.getIPResponse().compareTo("cancel") == 0)
	    		{
	    			editRemoteButtonDialogBox.Close();
	    		    enableKeyBoardEvents();
	    		}
	    		else if(editRemoteButtonDialogBox.getNameResponse().compareTo("null") != 0 && editRemoteButtonDialogBox.getNameResponse().compareTo("") != 0)
	    		{
	    		    enableKeyBoardEvents();
	    		    
	    		}	
	    			
			}
	    };
	    editRemoteButtonDialogBox.addClickListener(clickListener);
	    editRemoteButtonDialogBox.Show();
    }
    
//    public static void launchScrollButtonEditDialogBox()
//    {
//		disableKeyBoardEvents();
//		final EditScrollButtonDialogBox editScrollButtonDialogBox = new EditScrollButtonDialogBox(scrollButtons, 3, 1);
//		ClickHandler clickListener = new ClickHandler()
//	    {	public void onClick(ClickEvent event) 
//			{
//	    		if(editScrollButtonDialogBox.getIPResponse().compareTo("cancel") == 0)
//	    		{
//	    			editScrollButtonDialogBox.Close();
//	    		    enableKeyBoardEvents();
//	    		}
//	    		else if(editScrollButtonDialogBox.getNameResponse().compareTo("null") != 0 && editScrollButtonDialogBox.getNameResponse().compareTo("") != 0)
//	    		{
//	    		    enableKeyBoardEvents();
//	    		    
//	    		}	
//	    			
//			}
//	    };
//	    editScrollButtonDialogBox.addClickListener(clickListener);
//	    editScrollButtonDialogBox.Show();
//    }
	
    
    
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();
	
	}-*/;
	
	public static native void enableKeyBoardEvents() /*-{
	
		$wnd.enableKeyBoardEvents();
	
	}-*/;
	
	public static native void sendAnymoteSearchStringMessage(String message) /*-{
		if($wnd.backgroundPageWindow.anymoteSessionActive)
		{
			$wnd.sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.android.quicksearchbox/.SearchActivity' + ';end');
			$wnd.sendAnymoteStringMessage(message);
		}
		else
		{
			$wnd.backgroundPageWindow.console.log('Text / Voice search was not sent because no anymote session is active.');
			$wnd.displayToast("No Google TV's are connected.");
		}
	
	}-*/;
	
	public static native boolean isGtvConnected() /*-{
		return backgroundPageWindow.anymoteSessionActive;
	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;
	
	public static native void setUiType() /*-{
		$wnd.tabOrPopUp = $wnd.setUiType();
	}-*/;
	
	public static native void exportStaticMethod() /*-{
	
		$wnd.launchButtonEditDialogBox  = $entry( @com.chromemote.crx.client.ui.RemoteButtonPanelsCore::launchButtonEditDialogBox() );
	}-*/;
	
}
