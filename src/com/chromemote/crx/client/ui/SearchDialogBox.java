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
class SearchDialogBox
{
	
	DialogBox searchDialogBox;
	String pairCodeResult = "null";
	TextBox searchStringTextBox;
	Button searchButton, cancelButton;
	HTML pasteInfoLabel;
			 
	public SearchDialogBox()
	{
		VerticalPanel DialogBoxContents;
		ClickListener listener, listener2;
		
		HorizontalPanel holder;
		HorizontalPanel textBoxholder;
	    
	    searchStringTextBox = new TextBox();
		
	    searchStringTextBox.addKeyboardListener(new KeyboardListener(){
	    	@Override
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
	    		if (KeyboardListener.KEY_ENTER == keyCode)
	    			searchButton.click();
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
	    
	    
		searchDialogBox = new DialogBox(false);
	    searchDialogBox.setGlassEnabled(true);
	    searchDialogBox.setStyleName("gtvremote-DialogBox");
		if(getTabOrPopUpType().compareTo("popup") == 0)
			searchDialogBox.setText("Text Search");
		else
			searchDialogBox.setText("Text / Voice Search");
	    
	    DialogBoxContents = new VerticalPanel();
	    
	    
	    textBoxholder = new HorizontalPanel();
	    textBoxholder.setSpacing(5);
	    textBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    
	    searchStringTextBox.setSize("200px", "30px");
	    searchStringTextBox.setText("Search Google TV");

			searchStringTextBox.setStyleName("gtvremote-DialogBox-speechBox-empty");
		
//		if(getTabOrPopUpType().compareTo("popup") != 0)
	    	searchStringTextBox.getElement().setAttribute("x-webkit-speech", "x-webkit-speech");
	    	
	    searchStringTextBox.addFocusListener(new FocusListener()
	    {
			@Override
			public void onFocus(Widget sender) {
				if(searchStringTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-speechBox-empty") == 0)
				{
					
					if( searchStringTextBox.getText().toString().compareTo("Search Google TV") == 0)
					{
						searchStringTextBox.setText("");
					}

						searchStringTextBox.setStyleName("gtvremote-DialogBox-speechBox");

					
				}
				return;
			}
			public void onLostFocus(Widget sender) {
				if(searchStringTextBox.getText().compareTo("") == 0)
				{
					searchStringTextBox.setText("");

						searchStringTextBox.setStyleName("gtvremote-DialogBox-speechBox-empty");
				}
				return;
			}
	    });
	    searchStringTextBox.setSelectionRange(0, 30);
	    textBoxholder.add(searchStringTextBox);
	    
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    listener = new ClickListener()
	    {
			@Override
			public void onClick(Widget sender) {
				
				if(searchStringTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-speechBox") == 0)
				{
					pairCodeResult = searchStringTextBox.getText();
					Close();
					
				}
				else
				{
					searchStringTextBox.setFocus(true);
					searchStringTextBox.setText("");
					Shake();
				}
				
			}
	    };
	    listener2 = new ClickListener()
	    {
	    	@Override
			public void onClick(Widget sender) {
								
				pairCodeResult = "cancel";
				Close();
				
			}
	    };
	    
	    if(getDetectedOS().contains("Mac"))
	    	pasteInfoLabel = new HTML("Press Command + V to paste text.");
	    else
	    	pasteInfoLabel = new HTML("Press CTRL + V to paste text.");
	    pasteInfoLabel.setStyleName("gtvremote-DialogBox-message3");
	    
	    
	    searchButton = new Button("Search", listener);
	    searchButton.setSize("120px", "38px");
	    searchButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(searchButton);
	    
	    cancelButton = new Button("Cancel", listener2);
	    cancelButton.setSize("120px", "38px");
	    cancelButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(cancelButton);
	    
	    textBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    textBoxholder.setSize("255px", "58px");
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    DialogBoxContents.add(textBoxholder);
	    DialogBoxContents.add(pasteInfoLabel);
	    DialogBoxContents.add(holder);
	    searchDialogBox.setWidget(DialogBoxContents);
	    
	    searchDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 180);
	    
	}
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			searchDialogBox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		searchDialogBox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					searchDialogBox.removeStyleName("gtvRemote-PushButton-scaleIn");
				searchStringTextBox.setFocus(true);
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			searchDialogBox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				searchDialogBox.hide();
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					searchDialogBox.removeStyleName("gtvRemote-PushButton-scaleOut");
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
		if(searchDialogBox.isVisible())
			return true;
		else
			return false;
	}

	public void addClickHandler(ClickHandler listener)
	{
		searchButton.addClickHandler(listener);
		cancelButton.addClickHandler(listener);
	}

	public void Shake()
	{
		final PanelAnimationSlide animation = new PanelAnimationSlide( searchDialogBox.getElement() );
		animation.shakeHorizontal();
	}
	public static native String getTabOrPopUpType() /*-{
		return tabOrPopUp;
	}-*/;
	
	public static native String getDetectedOS() /*-{
		return backgroundPageWindow.osDetected;
	}-*/;
}
