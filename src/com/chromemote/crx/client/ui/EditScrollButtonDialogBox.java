package com.chromemote.crx.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

//Draws Enter PIN Code DialogBox with DeviceID, textBox, Connect and Cancel Buttons

class EditScrollButtonDialogBox
{
	
	DialogBox editScrollButtonDialogBox;
	String buttonTextResult = "";
	String buttonSubTextResult = "null";
		
	ListBox keycodeListBox = new ListBox();
//	ListBox iconListBox = new ListBox();
	
	TextBox buttonNameTextBox;
	TextBox buttonSubNameTextBox;
	
	int scrollButtonListCount;
	
	Button saveButton, closeButton, nextButton, prevButton;
	
	CheckBox defaultsCheckBox = new CheckBox("defaults");
//	iconEnabledCheckBox = new CheckBox("Icon");
//	CheckBox buttonTextCheckBox = new CheckBox("Text");
	
	ScrollButton scrollButton, demoScrollButton;
	
	String initKeycode, initName, initSubName;
//	String initIcon;
//	boolean initIconEnabled;
	boolean initDefaultsEnabled;
	
	ScrollButton scrollButtons[];
	
	int currentPanelIndex;

//	private scrollButton allRemoteButtons[][];
			 
//	public EditScrollButtonDialogBox(RemoteButton buttons[][], int panelIndex, int buttonToEditIndex) {
//		
//		this.allRemoteButtons = buttons; 
//		
//		currentPanelIndex = panelIndex;
//		
//		remoteButtons = buttons[currentPanelIndex];
//		
//		remoteButton = remoteButtons[buttonToEditIndex];
//		
//		initdemoScrollButton(scrollButton.getBoldName() , scrollButton.getKeyCode() , scrollButton.getRowIndex(), scrollButton.getNormName());
//		
//		editScrollButtonDialogBox(panelIndex, buttonToEditIndex);
//	}
	
	public EditScrollButtonDialogBox(ScrollButton[] buttons, int panelIndex, int rowIndex) {
		
		currentPanelIndex = panelIndex;
		
		scrollButtons = buttons;
		
		scrollButton = buttons[rowIndex];
		
		scrollButtonListCount = scrollButtons.length;
		
		initdemoScrollButton(scrollButton.getBoldName() , scrollButton.getKeyCode().replaceAll("SP_KEYCODE_", "KEYCODE_") , scrollButton.getRowIndex(), scrollButton.getNormName());
		
		editScrollButtonDialogBox(panelIndex, rowIndex);
	}
	
	public void editScrollButtonDialogBox(int panelIndex, int buttonToEditIndex)
	{
		VerticalPanel DialogBoxContents;
		ClickHandler listener;
		ClickHandler listener2;
				
		
		
//		if(scrollButton.hasIconImage())
//			demoScrollButton.setIconImage(scrollButton.getIconPath(), scrollButton.getIconPath().replaceFirst(".png", "_touched.png"));
//		if(scrollButton.iconImageEnabled())
//		{
//			demoScrollButton.enableIconImage();
//			
//			iconEnabledCheckBox.setValue(true);
//		}
//		else
//		{
//			demoScrollButton.disableIconImage();
//			
//			buttonTextCheckBox.setValue(true);
//		}

	    
		demoScrollButton.addMouseUpHandler(new MouseUpHandler(){
			@Override
			public void onMouseUp(MouseUpEvent event) {
				sendButtonKeyEvent( demoScrollButton.getKeyCode().replaceAll("SP_KEYCODE_", "").replaceAll("KEYCODE_", ""), false );
			}
		});
		
	    demoScrollButton.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				sendButtonKeyEvent( demoScrollButton.getKeyCode().replaceAll("SP_KEYCODE_", "").replaceAll("KEYCODE_", ""), true );
			}
		});

//		Label iconLabel;
		Label keycodeLabel;
		
		Label buttonTextLabel;
		Label subTextLabel;
		
		HTML deviceIPInfoLabel;
		
		HorizontalPanel holder;
		
		HorizontalPanel scrollButtonHolder;
		
//		HorizontalPanel iconListBoxholder;
		HorizontalPanel keycodeListBoxholder;
		
		HorizontalPanel buttonNameTextBoxholder;
		HorizontalPanel subTextTextBoxholder;
	    
		
	    buttonNameTextBox = new TextBox();
	    
	    buttonNameTextBox.addKeyUpHandler(new KeyUpHandler(){
	    	
			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				if ( event.getNativeKeyCode() == 13 )
	    		{
	    			saveButton.click();
	    			
	    		}
				
				
				demoScrollButton.setBoldName( buttonNameTextBox.getValue() );

//				if( iconEnabledCheckBox.getValue() )
//					 demoScrollButton.enableIconImage();
				
				if(matchInitSettings(true, true))
					 saveButton.setEnabled(false);
				 else
					 saveButton.setEnabled(true);
				
			}

		});	    
	    	    
	    
	    buttonSubNameTextBox = new TextBox();
	    
	    buttonSubNameTextBox.addKeyUpHandler(new KeyUpHandler(){
	    	
			@Override
			public void onKeyUp(KeyUpEvent event) {

				if ( event.getNativeKeyCode() == 13 )
	    		{
	    			saveButton.click();
	    			
	    		}
				
				
				demoScrollButton.setNormName(buttonSubNameTextBox.getValue());
				
//				if( iconEnabledCheckBox.getValue() )
//					 demoScrollButton.enableIconImage();
				
				if(matchInitSettings(true,true))
					 saveButton.setEnabled(false);
				 else
					 saveButton.setEnabled(true);
				
			}

		});	    
	    
		
	    editScrollButtonDialogBox = new DialogBox(false);
	    editScrollButtonDialogBox.setGlassEnabled(true);
	    editScrollButtonDialogBox.setStyleName("gtvremote-DialogBox");
	    editScrollButtonDialogBox.setText("Edit Scroll Button [ " + altIndexToDisplay() + " ]" );
	    DialogBoxContents = new VerticalPanel();
	    
	    //iconLabel = new Label("Icon");
	    //iconLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    keycodeLabel = new Label("Key Code");
	    //keycodeLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    buttonTextLabel = new Label("Text");
	    buttonTextLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    subTextLabel = new Label("Sub Text");
	    subTextLabel.setStyleName("gtvremote-DialogBox-message");
	    
	    deviceIPInfoLabel = new HTML(" ");
	    //deviceIPInfoLabel = new HTML("You can find IP address of your Google TV<br>device by going to:<br>Settings > Network > Status");
	    deviceIPInfoLabel.setStyleName("gtvremote-DialogBox-message3");
	    
	    
	    
	    
	    scrollButtonHolder = new HorizontalPanel();
	    scrollButtonHolder.setSpacing(5);
	    scrollButtonHolder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    scrollButtonHolder.add(demoScrollButton);
	    
	    
	    
	    
	    
	    
//	    iconListBoxholder = new HorizontalPanel();
//	    iconListBoxholder.setSpacing(5);
//	    iconListBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    
	    
	    
	    
	    
	    
//	    iconListBox.addItem("Select an icon...");
//	    iconListBox.addItem("KEYCODE_ALL_POWER");
//	    iconListBox.addItem("KEYCODE_AVR_POWER");
//	    iconListBox.addItem("KEYCODE_STB_POWER");
//	    iconListBox.addItem("KEYCODE_TV_POWER");
//
//	    iconListBox.addItem("KEYCODE_AVR_INPUT");
//	    iconListBox.addItem("KEYCODE_STB_INPUT");
//	    iconListBox.addItem("KEYCODE_TV_INPUT");
//	    
//	    iconListBox.addItem("KEYCODE_MEDIA_PLAY");
//	    iconListBox.addItem("KEYCODE_MEDIA_PLAY_PAUSE");
//	    iconListBox.addItem("KEYCODE_MEDIA_STOP");
//	    iconListBox.addItem("KEYCODE_PAUSE");
//	    iconListBox.addItem("KEYCODE_MEDIA_REWIND");
//	    iconListBox.addItem("KEYCODE_MEDIA_FAST_FORWARD");
//
//	    iconListBox.addItem("KEYCODE_PAGE_DOWN");
//	    iconListBox.addItem("KEYCODE_PAGE_UP");
//
//	    iconListBox.addItem("KEYCODE_CHANNEL_UP");
//	    iconListBox.addItem("KEYCODE_CHANNEL_DOWN");
//	    
//	    iconListBox.addItem("KEYCODE_VOLUME_DOWN");
//	    iconListBox.addItem("KEYCODE_VOLUME_UP");
//	    iconListBox.addItem("KEYCODE_MUTE");
//	    
//	    iconListBox.addItem("KEYCODE_ESCAPE");
//	    iconListBox.addItem("KEYCODE_RECALL");
//	    
//	    
//	    iconListBox.addItem("KEYCODE_MENU");
//	    iconListBox.addItem("KEYCODE_HOME");
//	    iconListBox.addItem("KEYCODE_BACK");
//	    iconListBox.addItem("KEYCODE_LIVE");
//	    iconListBox.addItem("KEYCODE_WINDOW");
//	    iconListBox.addItem("KEYCODE_BOOKMARK");
//	    iconListBox.addItem("KEYCODE_SEARCH");
//	    
//	    iconListBox.addItem("KEYCODE_DVR");
//	    iconListBox.addItem("KEYCODE_GUIDE");
//	    iconListBox.addItem("KEYCODE_INFO");
//	    
//	    iconListBox.addItem("KEYCODE_MEDIA_PREVIOUS");
//	    iconListBox.addItem("KEYCODE_MEDIA_NEXT");
//	    iconListBox.addItem("KEYCODE_MEDIA_RECORD");
//	    
//	    iconListBox.addItem("KEYCODE_PROG_YELLOW");
//	    iconListBox.addItem("KEYCODE_PROG_BLUE");
//	    iconListBox.addItem("KEYCODE_PROG_RED");
//	    iconListBox.addItem("KEYCODE_PROG_GREEN");
//	    
//	    iconListBox.addItem("KEYCODE_EJECT");
//	    iconListBox.addItem("KEYCODE_AUDIO");
//	    
//	    iconListBox.addItem("KEYCODE_ZOOM_IN");
//	    iconListBox.addItem("KEYCODE_ZOOM_OUT");
//	    iconListBox.addItem("KEYCODE_STB_MENU");
//	    
//	    iconListBox.addItem("KEYCODE_NUM1");
//	    iconListBox.addItem("KEYCODE_NUM2");
//	    iconListBox.addItem("KEYCODE_NUM3");
//	    iconListBox.addItem("KEYCODE_NUM4");
//	    iconListBox.addItem("KEYCODE_NUM5");
//	    iconListBox.addItem("KEYCODE_NUM6");
//	    iconListBox.addItem("KEYCODE_NUM7");
//	    iconListBox.addItem("KEYCODE_NUM8");
//	    iconListBox.addItem("KEYCODE_NUM9");
//	    iconListBox.addItem("KEYCODE_NUM0");
//	    
//	    iconListBox.addItem("KEYCODE_SETTINGS");
//
//	    iconListBox.addItem("KEYCODE_FLING_TAB");
//	    
//	    iconListBox.addItem("UI_KEYBOARD");
//	  
//	    iconListBox.addItem("UI_HIDE_TOUCHPAD");
//	    
//	    iconListBox.addItem("UI_LOCK_MOUSE");
//	    iconListBox.addItem("UI_MOVE_LEFT");
//	    iconListBox.addItem("UI_MOVE_RIGHT");
//	    iconListBox.addItem("UI_SHOW_TOUCHPAD");
//	    iconListBox.addItem("UI_SROLL_MENU");
//	    iconListBox.addItem("UI_VOICE");
//	    iconListBox.addItem("UI_TEXT");
//	    
//	    iconListBox.addItem("OTHER_AC_ON_OFF");
//	    iconListBox.addItem("OTHER_AC_DOWN");
//	    iconListBox.addItem("OTHER_AC_UP");
//
//	    iconListBox.addItem("OTHER_LIGHT_POWER");
//	    iconListBox.addItem("OTHER_LIGHT_DOWN");
//	    iconListBox.addItem("OTHER_LIGHT_UP");
//	    
//	    iconListBox.addItem("APP_CHROME");
//	    iconListBox.addItem("APP_CLOCK");
//	    iconListBox.addItem("APP_CNBC");
//	    iconListBox.addItem("APP_DOWNLOADS");
//	    
//	    iconListBox.addItem("APP_NBA");
//	    iconListBox.addItem("APP_NETFLIX");
//	    iconListBox.addItem("APP_ONLIVE");
//	    iconListBox.addItem("APP_PANDORA");
//	    
//	    iconListBox.addItem("APP_PICTURES");
//	    iconListBox.addItem("APP_PLAYMOVIES");
//	    iconListBox.addItem("APP_PLAYMUSIC");
//	    iconListBox.addItem("APP_PLAYSTORE");
//	    
//	    iconListBox.addItem("APP_PODCASTS");
//	    iconListBox.addItem("APP_PRIMETIME");
//	    iconListBox.addItem("APP_SEARCH");
//	    iconListBox.addItem("APP_SETTINGS");
//	    
//	    iconListBox.addItem("APP_SPOTLIGHT");
//	    iconListBox.addItem("APP_TV");
//	    iconListBox.addItem("APP_TWITTER");
//	    iconListBox.addItem("APP_VOICESEARCH");
//	    iconListBox.addItem("APP_YOUTUBE");
//	    
//	    iconListBox.setVisibleItemCount(1);
//	    
//	    iconListBox.setSize("225px", "30px");
//	    iconListBox.setStyleName("gtvremote-DialogBox-textBox");
	    
//	    if(scrollButton.getIconPath() != null)
//	    	iconListBox.setSelectedIndex( getIconIndex(scrollButton.getIconPath().replaceFirst("images/icons_", "").replaceFirst(".png", "")) );
	    
	    
//		iconListBox.addChangeHandler(new ChangeHandler()
//		{
//			 public void onChange(ChangeEvent event)
//			 {
//				 int selectedIndex = iconListBox.getSelectedIndex();
//				 
//				 if( iconListBox.getValue(selectedIndex).compareTo("Select an icon...") != 0 )
//					 demoScrollButton.setIconImage( "images/icons_" + iconListBox.getValue(selectedIndex) + ".png", "images/icons_" + iconListBox.getValue(selectedIndex) + "_touched.png");
//				 
//				 if( buttonTextCheckBox.getValue() )
//					 demoScrollButton.disableIconImage();
//				 
//				 if(matchInitSettings(true,true))
//					 saveButton.setEnabled(false);
//				 else
//					 saveButton.setEnabled(true);
//			 }
//		});
	    
	    
	    
	    
//	    iconListBoxholder.add(iconListBox);
	    
	   
	   
	   
	   
	   
	   
	   
		keycodeListBoxholder = new HorizontalPanel();
		keycodeListBoxholder.setSpacing(5);
		keycodeListBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		
		
		
		keycodeListBox.addItem("Select a keycode...");
		keycodeListBox.addItem("KEYCODE_ALL_POWER");
		
		keycodeListBox.addItem("KEYCODE_TV_POWER");
		keycodeListBox.addItem("KEYCODE_TV_INPUT");
		
		keycodeListBox.addItem("KEYCODE_AVR_POWER");
		keycodeListBox.addItem("KEYCODE_AVR_INPUT");
		
		keycodeListBox.addItem("KEYCODE_STB_POWER");
		keycodeListBox.addItem("KEYCODE_STB_INPUT");
		keycodeListBox.addItem("KEYCODE_STB_MENU");
		
		keycodeListBox.addItem("KEYCODE_BD_POWER");
		keycodeListBox.addItem("KEYCODE_BD_INPUT");
		keycodeListBox.addItem("KEYCODE_BD_TOP_MENU");
		keycodeListBox.addItem("KEYCODE_BD_POPUP_MENU");
		
		keycodeListBox.addItem("KEYCODE_AUDIO");
		keycodeListBox.addItem("KEYCODE_EJECT");
		
		
	    keycodeListBox.addItem("KEYCODE_HOME");
	    keycodeListBox.addItem("KEYCODE_BACK");
		keycodeListBox.addItem("KEYCODE_MENU");
		keycodeListBox.addItem("KEYCODE_SEARCH");
	    
		keycodeListBox.addItem("KEYCODE_INFO");
		keycodeListBox.addItem("KEYCODE_WINDOW");
		keycodeListBox.addItem("KEYCODE_BOOKMARK");
		keycodeListBox.addItem("KEYCODE_ESCAPE");
		
		keycodeListBox.addItem("KEYCODE_DVR");
		keycodeListBox.addItem("KEYCODE_LIVE");
		keycodeListBox.addItem("KEYCODE_GUIDE");
		keycodeListBox.addItem("KEYCODE_PAUSE");
		
		keycodeListBox.addItem("KEYCODE_MEDIA_RECORD");
		keycodeListBox.addItem("KEYCODE_MEDIA_PLAY");
		keycodeListBox.addItem("KEYCODE_MEDIA_PLAY_PAUSE");
		keycodeListBox.addItem("KEYCODE_MEDIA_STOP");
		
		keycodeListBox.addItem("KEYCODE_MEDIA_PREVIOUS");
		keycodeListBox.addItem("KEYCODE_MEDIA_NEXT");				
		
		keycodeListBox.addItem("KEYCODE_MEDIA_REWIND");
		keycodeListBox.addItem("KEYCODE_MEDIA_FAST_FORWARD");
		
		keycodeListBox.addItem("KEYCODE_MEDIA_SKIP_BACK");
		keycodeListBox.addItem("KEYCODE_MEDIA_SKIP_FORWARD");

		keycodeListBox.addItem("KEYCODE_CHANNEL_UP");
		keycodeListBox.addItem("KEYCODE_CHANNEL_DOWN");
		
	    keycodeListBox.addItem("KEYCODE_VOLUME_UP");
	    keycodeListBox.addItem("KEYCODE_VOLUME_DOWN");
		
	    keycodeListBox.addItem("KEYCODE_MUTE");

		keycodeListBox.addItem("KEYCODE_PROG_RED");
		keycodeListBox.addItem("KEYCODE_PROG_GREEN");		
		keycodeListBox.addItem("KEYCODE_PROG_YELLOW");
		keycodeListBox.addItem("KEYCODE_PROG_BLUE");
		
		keycodeListBox.addItem("KEYCODE_PAGE_UP");
		keycodeListBox.addItem("KEYCODE_PAGE_DOWN");
	    
	    keycodeListBox.addItem("KEYCODE_ZOOM_IN");
		keycodeListBox.addItem("KEYCODE_ZOOM_OUT");
	    
	    keycodeListBox.addItem("KEYCODE_TAB");
	    keycodeListBox.addItem("KEYCODE_ENTER");
	    
	    keycodeListBox.addItem("KEYCODE_DPAD_UP");
	    keycodeListBox.addItem("KEYCODE_DPAD_DOWN");
	    keycodeListBox.addItem("KEYCODE_DPAD_LEFT");
	    keycodeListBox.addItem("KEYCODE_DPAD_RIGHT");
	    keycodeListBox.addItem("KEYCODE_DPAD_CENTER");
	    
	    keycodeListBox.addItem("KEYCODE_NUM0");
	    keycodeListBox.addItem("KEYCODE_NUM1");
	    keycodeListBox.addItem("KEYCODE_NUM2");
	    keycodeListBox.addItem("KEYCODE_NUM3");
	    keycodeListBox.addItem("KEYCODE_NUM4");
	    keycodeListBox.addItem("KEYCODE_NUM5");
	    keycodeListBox.addItem("KEYCODE_NUM6");
	    keycodeListBox.addItem("KEYCODE_NUM7");
	    keycodeListBox.addItem("KEYCODE_NUM8");
	    keycodeListBox.addItem("KEYCODE_NUM9");
	    
	    keycodeListBox.addItem("KEYCODE_STAR");
	    keycodeListBox.addItem("KEYCODE_POUND");
	    
	    keycodeListBox.addItem("KEYCODE_A");
	    keycodeListBox.addItem("KEYCODE_B");
	    keycodeListBox.addItem("KEYCODE_C");
	    keycodeListBox.addItem("KEYCODE_D");
	    keycodeListBox.addItem("KEYCODE_E");
	    keycodeListBox.addItem("KEYCODE_F");
	    keycodeListBox.addItem("KEYCODE_G");
	    keycodeListBox.addItem("KEYCODE_H");
	    keycodeListBox.addItem("KEYCODE_I");
	    keycodeListBox.addItem("KEYCODE_J");
	    keycodeListBox.addItem("KEYCODE_K");
	    keycodeListBox.addItem("KEYCODE_L");
	    keycodeListBox.addItem("KEYCODE_M");
	    keycodeListBox.addItem("KEYCODE_N");
	    keycodeListBox.addItem("KEYCODE_O");
	    keycodeListBox.addItem("KEYCODE_P");
	    keycodeListBox.addItem("KEYCODE_Q");
	    keycodeListBox.addItem("KEYCODE_R");
	    keycodeListBox.addItem("KEYCODE_S");
	    keycodeListBox.addItem("KEYCODE_T");
	    keycodeListBox.addItem("KEYCODE_U");
	    keycodeListBox.addItem("KEYCODE_V");
	    keycodeListBox.addItem("KEYCODE_W");
	    keycodeListBox.addItem("KEYCODE_X");
	    keycodeListBox.addItem("KEYCODE_Y");
	    keycodeListBox.addItem("KEYCODE_Z");
	    
	    keycodeListBox.addItem("KEYCODE_SPACE");	    
	    keycodeListBox.addItem("KEYCODE_COMMA");
	    keycodeListBox.addItem("KEYCODE_PERIOD");
	    
	    keycodeListBox.addItem("KEYCODE_SHIFT_LEFT");
	    keycodeListBox.addItem("KEYCODE_SHIFT_RIGHT");
	    
	    keycodeListBox.addItem("KEYCODE_CTRL_LEFT");
		keycodeListBox.addItem("KEYCODE_CTRL_RIGHT");
		
	    keycodeListBox.addItem("KEYCODE_ALT_LEFT");
	    keycodeListBox.addItem("KEYCODE_ALT_RIGHT");
	    
		keycodeListBox.addItem("KEYCODE_DEL");
		keycodeListBox.addItem("KEYCODE_GRAVE");
		keycodeListBox.addItem("KEYCODE_MINUS");
		keycodeListBox.addItem("KEYCODE_EQUALS");
		keycodeListBox.addItem("KEYCODE_LEFT_BRACKET");
		keycodeListBox.addItem("KEYCODE_RIGHT_BRACKET");
		keycodeListBox.addItem("KEYCODE_BACKSLASH");
		keycodeListBox.addItem("KEYCODE_SEMICOLON");
		keycodeListBox.addItem("KEYCODE_APOSTROPHE");
		keycodeListBox.addItem("KEYCODE_SLASH");
		keycodeListBox.addItem("KEYCODE_AT");
		keycodeListBox.addItem("KEYCODE_FOCUS");
		keycodeListBox.addItem("KEYCODE_PLUS");
		
		keycodeListBox.addItem("KEYCODE_INSERT");		
		keycodeListBox.addItem("KEYCODE_PRINT_SCREEN");
		keycodeListBox.addItem("KEYCODE_CAPS_LOCK");
				
		keycodeListBox.addItem("KEYCODE_POWER");
		keycodeListBox.addItem("KEYCODE_EXPLORER");
		keycodeListBox.addItem("KEYCODE_META_LEFT");
		keycodeListBox.addItem("KEYCODE_META_RIGHT");
		keycodeListBox.addItem("KEYCODE_SOFT_LEFT");
	    keycodeListBox.addItem("KEYCODE_SOFT_RIGHT");
	    keycodeListBox.addItem("KEYCODE_CAMERA");
	    keycodeListBox.addItem("KEYCODE_CALL");
	    
	    keycodeListBox.addItem("KEYCODE_SETTINGS");
		keycodeListBox.addItem("KEYCODE_FLING_TAB");
		
		keycodeListBox.addItem("KEYCODE_NULL");
		
		keycodeListBox.addItem("UI_KEYBOARD");
		keycodeListBox.addItem("UI_VOICE");
		keycodeListBox.addItem("UI_TEXT");
	  //keycodeListBox.addItem("TOUCHPAD_SHOWHIDE");
		
		keycodeListBox.addItem("APP_CHROME");
	    keycodeListBox.addItem("APP_CLOCK");
	    keycodeListBox.addItem("APP_CNBC");
	    keycodeListBox.addItem("APP_DOWNLOADS");
	    
	    keycodeListBox.addItem("APP_NBA");
	    keycodeListBox.addItem("APP_NETFLIX");
	    keycodeListBox.addItem("APP_ONLIVE");
	    keycodeListBox.addItem("APP_PANDORA");
	    
	    keycodeListBox.addItem("APP_PICTURES");
	    keycodeListBox.addItem("APP_PLAYMOVIES");
	    keycodeListBox.addItem("APP_PLAYMUSIC");
	    keycodeListBox.addItem("APP_PLAYSTORE");
	    
	    keycodeListBox.addItem("APP_PODCASTS");
	    keycodeListBox.addItem("APP_PRIMETIME");
	    keycodeListBox.addItem("APP_SEARCH");
	    keycodeListBox.addItem("APP_SETTINGS");
	    
	    keycodeListBox.addItem("APP_SPOTLIGHT");
	    keycodeListBox.addItem("APP_TV");
	    keycodeListBox.addItem("APP_TWITTER");
	    //keycodeListBox.addItem("APP_VOICESEARCH");
	    keycodeListBox.addItem("APP_YOUTUBE");
		
	    
		
		
		
		keycodeListBox.setVisibleItemCount(1); //Makes listBox into a dropdown list.
		
		keycodeListBox.setSize("225px", "30px");
		keycodeListBox.setStyleName("gtvremote-DialogBox-textBox");
	    
		for(int i = 0 ; i < keycodeListBox.getItemCount() ; i++)
		{
			if(keycodeListBox.getValue(i).compareTo( scrollButton.getKeyCode().replaceAll("SP_KEYCODE_", "KEYCODE_") ) == 0)
			{
				keycodeListBox.setSelectedIndex(i);
				break;
			}
		}
		
		
		keycodeListBox.addChangeHandler(new ChangeHandler()
		{
			 public void onChange(ChangeEvent event)
			 {
				 updateToNextOnKeycodeList(0);
				
			 }
		});
		
		keycodeListBoxholder.add(keycodeListBox);
	   
		Button prevKeycodeButton = new Button("^");
		prevKeycodeButton.setSize("20px", "20px");
		prevKeycodeButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				updateToNextOnKeycodeList(-1);
				
			}
			
		});
		prevKeycodeButton.setStyleName("gtvRemote-DialogBox-PushButton");
		//keycodeListBoxholder.add(prevKeycodeButton);
	   
		
		Button nextKeycodeButton = new Button("v");
		nextKeycodeButton.setSize("20px", "20px");
		nextKeycodeButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {

				updateToNextOnKeycodeList(1);
				
			}
			
		});
		nextKeycodeButton.setStyleName("gtvRemote-DialogBox-PushButton");
		//keycodeListBoxholder.add(nextKeycodeButton);
		
		
		
	    buttonNameTextBoxholder = new HorizontalPanel();
	    buttonNameTextBoxholder.setSpacing(5);
	    buttonNameTextBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    buttonNameTextBox.setMaxLength(32);
	    buttonNameTextBox.setSize("200px", "30px");
	    buttonNameTextBox.setText("required");
	    buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    
	    buttonNameTextBox.addFocusHandler(new FocusHandler()
	    {
			@Override
			public void onFocus(FocusEvent event) {
				if(buttonNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					buttonNameTextBox.setText("");
					buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
	    });
	    buttonNameTextBox.addBlurHandler(new BlurHandler()
	    {
			public void onBlur(BlurEvent event) {
				if(buttonNameTextBox.getText().compareTo("") == 0)
				{
					buttonNameTextBox.setText("required");
					buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
				}
				return;
			}
	    });
	    
	    
	    
	   buttonNameTextBoxholder.add(buttonNameTextBox);
	    
	    


	    
	    
	    
	    subTextTextBoxholder = new HorizontalPanel();
	    subTextTextBoxholder.setSpacing(5);
	    subTextTextBoxholder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    
	    buttonSubNameTextBox.setMaxLength(32);
	    buttonSubNameTextBox.setSize("200px", "30px");
	    buttonSubNameTextBox.setText("optional");
	    buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
	    
	    buttonSubNameTextBox.addFocusHandler(new FocusHandler()
	    {
			@Override
			public void onFocus(FocusEvent event) {
				if(buttonSubNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") == 0)
				{
					buttonSubNameTextBox.setText("");
					buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
					
				}
				return;
			}
	    });
	    buttonSubNameTextBox.addBlurHandler(new BlurHandler()
	    {
			public void onBlur(BlurEvent event) {
				if(buttonSubNameTextBox.getText().compareTo("") == 0)
				{
					buttonSubNameTextBox.setText("optional");
					buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
				}
				return;
			}
	    });
	    
	    subTextTextBoxholder.add(buttonSubNameTextBox);
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    holder = new HorizontalPanel();
	    holder.setSpacing(5);
	    holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    
	    listener = new ClickHandler()
	    {
			@Override
			public void onClick(ClickEvent event) {
				
				if(buttonNameTextBox.getStyleName().toString().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
				{
											
//					if( !( iconListBox.getSelectedIndex() == 0 && iconEnabledCheckBox.getValue() ) )
//					{
						if( keycodeListBox.getSelectedIndex() != 0 )
						{
							buttonTextResult = buttonNameTextBox.getText();
							buttonSubTextResult   = buttonSubNameTextBox.getText();
							Close();
						}
						else
						{
							keycodeListBox.setFocus(true);
							Shake();
						}
//					}
//					else
//					{
//						iconListBox.setFocus(true);
//						Shake();
//					}
					
					
				}
				else
				{
					buttonNameTextBox.setFocus(true);
					buttonNameTextBox.selectAll();
					Shake();
				}
				
			}
	    };
	    listener2 = new ClickHandler()
	    {
	    	@Override
	    	public void onClick(ClickEvent event) {
								
				buttonSubTextResult = "cancel";
				
			}
	    };
	    
	    prevButton = new Button("<", listener2);
	    prevButton.setSize("24px", "38px");
	    prevButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(prevButton);
	    prevButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
												
				loadNextButtonToEdit(false);
				
			}
		});
	    
	    
	    
	    saveButton = new Button("Save/Close", listener);
	    saveButton.setEnabled(false);
	    saveButton.setSize("90px", "38px");
	    saveButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(saveButton);
	    saveButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				saveSettings();
																
			}});
	    
	    closeButton = new Button("Close", listener2);
	    closeButton.setSize("90px", "38px");
	    closeButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(closeButton);
	    
	    nextButton = new Button(">", listener2);
	    nextButton.setSize("24px", "38px");
	    nextButton.setStyleName("gtvRemote-DialogBox-PushButton");
	    holder.add(nextButton);
	    nextButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				loadNextButtonToEdit(true);
				
			}
		});
	    
	    scrollButtonHolder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    scrollButtonHolder.setSize("255px", "50px");
	    
//	    iconListBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
//	    iconListBoxholder.setSize("255px", "50px");
	    
	    keycodeListBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    keycodeListBoxholder.setSize("255px", "50px");
	    
	    
	    
	    
	    buttonNameTextBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    buttonNameTextBoxholder.setSize("255px", "50px");
	    
	    subTextTextBoxholder.setStyleName("gtvremote-DialogBox-textBoxPanel");
	    subTextTextBoxholder.setSize("255px", "50px");
	    
	    
	    holder.setStyleName("gtvremote-DialogBox-footer");
	    holder.setSize("255px", "35px");
	    
	    
	    
	    
	    defaultsCheckBox.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				if(defaultsCheckBox.getValue())
				{
					int selectedIndex = keycodeListBox.getSelectedIndex();
					if(keycodeListBox.getValue(selectedIndex).compareTo("Select a keycode...") != 0)
					{
						setDefaults();
					}
					
				}
				else
				{
					
				}
				
				if(matchInitSettings(false,true))
					 saveButton.setEnabled(false);
				 else
					 saveButton.setEnabled(true);
				
				
			}});
	    defaultsCheckBox.setStyleName("chromemote-checkboxdefaults");
	    HorizontalPanel defaultsLabelHolder = new HorizontalPanel();
	    defaultsLabelHolder.setStyleName("gtvremote-DialogBox-message");
	    defaultsLabelHolder.setWidth("255px");
	    defaultsLabelHolder.add(keycodeLabel);
	    defaultsLabelHolder.add(defaultsCheckBox);
	    defaultsLabelHolder.add(prevKeycodeButton);
	    defaultsLabelHolder.add(nextKeycodeButton);
	    
	    HTML spacerHTML = new HTML("&nbsp;");
	    spacerHTML.setWidth("15px");
	    defaultsLabelHolder.add(spacerHTML);
	    
	    
	    
	    
	    
//	    iconEnabledCheckBox.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				if(iconEnabledCheckBox.getValue())
//				{
////					demoScrollButton.enableIconImage();
//					buttonTextCheckBox.setValue(false);
//					int selectedIndex = iconListBox.getSelectedIndex();
//					
////					if(iconListBox.getValue(selectedIndex).compareTo("Select an icon...") != 0)
////						demoScrollButton.setIconImage( "images/icons_" + iconListBox.getValue(selectedIndex) + ".png", "images/icons_" + iconListBox.getValue(selectedIndex) + "_touched.png");
//				}
//				else
//				{
////					demoScrollButton.disableIconImage();
//					buttonTextCheckBox.setValue(true);
//										
//					if(buttonNameTextBox.getStyleName().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
//						demoScrollButton.setBoldName( buttonNameTextBox.getValue() );
//					if(buttonSubNameTextBox.getStyleName().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
//						demoScrollButton.setNormName( buttonSubNameTextBox.getValue() );
//				}
//				
//				if(matchInitSettings(true,true))
//					 saveButton.setEnabled(false);
//				 else
//					 saveButton.setEnabled(true);
//				
//			}});
	    
//	    HorizontalPanel iconLabelHolder = new HorizontalPanel();
//	    iconLabelHolder.setStyleName("gtvremote-DialogBox-message");
//	    iconLabelHolder.setWidth("255px");
//	    iconLabelHolder.add(iconEnabledCheckBox);
	    
	    
	    
	    
//	    buttonTextCheckBox.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				if(!buttonTextCheckBox.getValue())
//				{
////					demoScrollButton.enableIconImage();
////					iconEnabledCheckBox.setValue(true);
////					int selectedIndex = iconListBox.getSelectedIndex();
//					
////					if(iconListBox.getValue(selectedIndex).compareTo("Select an icon...") != 0)
////						demoScrollButton.setIconImage( "images/icons_" + iconListBox.getValue(selectedIndex) + ".png", "images/icons_" + iconListBox.getValue(selectedIndex) + "_touched.png");
//					
//				}
//				else
//				{
////					iconEnabledCheckBox.setValue(false);
////					demoScrollButton.disableIconImage();
//					
//					if(buttonNameTextBox.getStyleName().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
//						demoScrollButton.setBoldName( buttonNameTextBox.getValue() );
//					if(buttonSubNameTextBox.getStyleName().compareTo("gtvremote-DialogBox-textBox-empty") != 0)
//						demoScrollButton.setNormName( buttonSubNameTextBox.getValue() );
//				}
//				
//				if(matchInitSettings(true,true))
//					 saveButton.setEnabled(false);
//				 else
//					 saveButton.setEnabled(true);
//				
//			}});
	    
//	    HorizontalPanel buttonTextCheckBoxHolder = new HorizontalPanel();
//	    buttonTextCheckBoxHolder.setStyleName("gtvremote-DialogBox-message");
//	    buttonTextCheckBoxHolder.setWidth("255px");
//	    buttonTextCheckBoxHolder.add(buttonTextLabel);
	    
	    
	    
	    DialogBoxContents.add(scrollButtonHolder);
	    
	    
	    //DialogBoxContents.add(keycodeLabel);
	    
	    DialogBoxContents.add(defaultsLabelHolder);
	    DialogBoxContents.add(keycodeListBoxholder);
	    
//	    DialogBoxContents.add(iconLabelHolder);
//	    DialogBoxContents.add(iconListBoxholder);
	    
	    DialogBoxContents.add(buttonTextLabel);
	    DialogBoxContents.add(buttonNameTextBoxholder);
	    
	    DialogBoxContents.add(subTextLabel);
	    DialogBoxContents.add(subTextTextBoxholder);
	    
	    DialogBoxContents.add(deviceIPInfoLabel);
	    
	    DialogBoxContents.add(holder);
	    editScrollButtonDialogBox.setWidget(DialogBoxContents);
	    
	    if(getTabOrPopUpType().compareTo("popup") == 0)		
	    	editScrollButtonDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 45);
	    else
	    	editScrollButtonDialogBox.setPopupPosition(Window.getClientWidth() - (Window.getClientWidth()/2) - 130 + 0, 45);
	    
	    //iconListBox.setText(scrollButton.getIconPath());
		//keycodeListBox.setText(scrollButton.getKeyCode());
	    
	    if(scrollButton.getBoldName().compareTo("") != 0)
	    {
	    	buttonNameTextBox.setText(scrollButton.getBoldName());
	    	buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    }
	    
	    if(scrollButton.getNormName().compareTo("") != 0)
	    {
	    	buttonSubNameTextBox.setText(scrollButton.getNormName());
	    	buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    }
	    
	    
	    if ( areAllValuesDefault() )
	    {
	    	defaultsCheckBox.setValue(true);
	    }
	    
	    saveInitSettings( keycodeListBox.getValue(	keycodeListBox.getSelectedIndex()),  
	    				  							buttonNameTextBox.getValue(), 
	    				  							buttonSubNameTextBox.getValue(),
	    				  							defaultsCheckBox.getValue() );
	    
	}
	
	public void Show()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			editScrollButtonDialogBox.addStyleName("gtvRemote-PushButton-scaleIn");
		
		editScrollButtonDialogBox.show();
		Timer t = new Timer() {
			public void run() {
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					editScrollButtonDialogBox.removeStyleName("gtvRemote-PushButton-scaleIn");
				//iconListBox.setFocus(true);
				//buttonNameTextBox.selectAll();
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public void Close()
	{
//		if ( getDetectedOS().toLowerCase().contains("win") == false )
//			editScrollButtonDialogBox.addStyleName("gtvRemote-PushButton-scaleOut");
		
		Timer t = new Timer() {
			public void run() {
				editScrollButtonDialogBox.hide();
				
//				if ( getDetectedOS().toLowerCase().contains("win") == false )
//					editScrollButtonDialogBox.removeStyleName("gtvRemote-PushButton-scaleOut");
		    }
		};
		t.schedule(160);    // Schedule the timer to run once in 0.16 seconds.
	}
	public String getNameResponse()
	{
		return buttonTextResult; 
	}
	public String getIPResponse()
	{
		return buttonSubTextResult;
	}
	public Boolean isVisible()
	{
		if(editScrollButtonDialogBox.isVisible())
			return true;
		else
			return false;
	}
	public void addClickListener(ClickHandler clickListener)
	{
		saveButton.addClickHandler(clickListener);
		closeButton.addClickHandler(clickListener);
	}
	public void Shake()
	{
		final PanelAnimationSlide animation = new PanelAnimationSlide( editScrollButtonDialogBox.getElement() );
		animation.shakeHorizontal();
	}
		
	public String getDefaultName( String keyCode, boolean isPrimaryText )
	{	
		String primaryText = keyCode;
		
		String secondaryText = "";
		
		if( keyCode == "KEYCODE_ALL_POWER" )          {	primaryText = "POWER";  secondaryText = "All"; }
		if( keyCode == "KEYCODE_TV_POWER" )  		  {	primaryText = "TV";  	secondaryText = "ON/OFF"; }
		if( keyCode == "KEYCODE_TV_INPUT" )  		  {	primaryText = "TV";  secondaryText = "Switch input"; }
		if( keyCode == "KEYCODE_AVR_POWER" )  		  {	primaryText = "AV Receiver";  secondaryText = "ON/OFF"; }
		if( keyCode == "KEYCODE_AVR_INPUT" )  		  {	primaryText = "INPUT";  secondaryText = "AVR"; }
		if( keyCode == "KEYCODE_STB_POWER" )  		  {	primaryText = "POWER";  secondaryText = "STB"; }
		if( keyCode == "KEYCODE_STB_INPUT" )  		  {	primaryText = "INPUT";  secondaryText = "STB"; }
		if( keyCode == "KEYCODE_STB_MENU" )  		  {	primaryText = "MENU";   secondaryText = "STB"; }
		if( keyCode == "KEYCODE_BD_POWER" )  		  {	primaryText = "POWER";  secondaryText = "BD"; }
		if( keyCode == "KEYCODE_BD_INPUT" )  		  {	primaryText = "INPUT";  secondaryText = "BD"; }
		if( keyCode == "KEYCODE_BD_TOP_MENU" )        {	primaryText = "BD";   secondaryText = "Top Menu"; }
		if( keyCode == "KEYCODE_EJECT" )        	  {	primaryText = "BD";   secondaryText = "Eject"; }
		if( keyCode == "KEYCODE_BD_POPUP_MENU" )      {	primaryText = "BD";   secondaryText = "Pop Up/Menu"; }
		if( keyCode == "KEYCODE_MEDIA_RECORD" )       {	primaryText = "RECORD"; secondaryText = ""; }
		if( keyCode == "KEYCODE_MEDIA_PLAY" )         {	primaryText = "PLAY";   secondaryText = ""; }
		if( keyCode == "KEYCODE_MEDIA_PLAY_PAUSE" )   {	primaryText = "PAUSE";  secondaryText = "Play"; }
		if( keyCode == "KEYCODE_MEDIA_STOP" )         {	primaryText = "STOP";	secondaryText = ""; }
		if( keyCode == "KEYCODE_MEDIA_PREVIOUS" )     {	primaryText = "<"; 		secondaryText = "Previous"; }
		if( keyCode == "KEYCODE_MEDIA_NEXT" )         {	primaryText = ">"; 		secondaryText = "Next"; }
		if( keyCode == "KEYCODE_MEDIA_REWIND" )       {	primaryText = "<<";  	secondaryText = "Rewind"; }
		if( keyCode == "KEYCODE_MEDIA_FAST_FORWARD" ) {	primaryText = ">>";  	secondaryText = "Fast Forward"; }
		if( keyCode == "KEYCODE_MEDIA_SKIP_BACK" )    {	primaryText = "BACK";  	secondaryText = "Media Skip"; }
		if( keyCode == "KEYCODE_MEDIA_SKIP_FORWARD" ) {	primaryText = "FORWARD";secondaryText = "Media Skip"; }
		if( keyCode == "KEYCODE_CHANNEL_UP" )  		  	primaryText = "CH +";
		if( keyCode == "KEYCODE_CHANNEL_DOWN" )  	  	primaryText = "CH -";
		if( keyCode == "KEYCODE_VOLUME_UP" )  		  	primaryText = "VOL +";
		if( keyCode == "KEYCODE_VOLUME_DOWN" )  	  	primaryText = "VOL -";
		
		if( keyCode == "KEYCODE_PROG_RED" )  		  {	primaryText = "RED";  	secondaryText = "button"; }
		if( keyCode == "KEYCODE_PROG_GREEN" )  		  {	primaryText = "GREEN";  secondaryText = "button"; }
		
		if( keyCode == "KEYCODE_PROG_YELLOW" )  	  {	primaryText = "YELLOW"; secondaryText = "button"; }
		if( keyCode == "KEYCODE_PROG_BLUE" )  		  {	primaryText = "BLUE";  	secondaryText = "button"; }
		
		if( keyCode == "KEYCODE_PAGE_UP" )  		  {	primaryText = "PG +";  	secondaryText = ""; }
		if( keyCode == "KEYCODE_PAGE_DOWN" )  		  {	primaryText = "PG -";  	secondaryText = ""; }
		
		if( keyCode == "KEYCODE_ZOOM_IN" )  		  {	primaryText = "ZOOM";  	secondaryText = "In"; }
		if( keyCode == "KEYCODE_ZOOM_OUT" )  		  {	primaryText = "ZOOM";  	secondaryText = "Out"; }
		
		if( keyCode.contains("KEYCODE_DPAD") ) 		  {	primaryText = keyCode.replaceFirst("KEYCODE_DPAD_", "");  secondaryText = "D-Pad"; }
		
		if( keyCode == "KEYCODE_TAB" )  		    	primaryText = "RECALL";
		
		if( keyCode == "KEYCODE_BOOKMARK" )  		  {	primaryText = "FAV";  secondaryText = "Bookmark"; }
		if( keyCode == "KEYCODE_WINDOW" )  		  	    primaryText = "PIP";
		if( keyCode == "KEYCODE_LIVE" )  		  	    primaryText = "LIVE TV";
		if( keyCode == "KEYCODE_ESCAPE" )  		  	    primaryText = "ESC";
		
		if( keyCode == "KEYCODE_SHIFT_LEFT" )  		  {	primaryText = "SHIFT";  secondaryText = "Left"; }
		if( keyCode == "KEYCODE_SHIFT_RIGHT" )  	  {	primaryText = "SHIFT";  secondaryText = "Right"; }
		if( keyCode == "KEYCODE_CTRL_LEFT" )  		  {	primaryText = "CTRL";   secondaryText = "Left"; }
		if( keyCode == "KEYCODE_CTRL_RIGHT" )  		  {	primaryText = "CTRL";   secondaryText = "Right"; }
		if( keyCode == "KEYCODE_ALT_LEFT" )  		  {	primaryText = "ALT";    secondaryText = "Left"; }
		if( keyCode == "KEYCODE_ALT_RIGHT" )  		  {	primaryText = "ALT";    secondaryText = "Right"; }
		if( keyCode == "KEYCODE_DEL" ) 			  		primaryText = "DELETE";
		if( keyCode == "KEYCODE_GRAVE" )		  		primaryText = "`";
		
		if( keyCode == "KEYCODE_STAR" ) 		  		primaryText = "*";
		if( keyCode == "KEYCODE_POUND" ) 		  		primaryText = "#";
		if( keyCode == "KEYCODE_COMMA" ) 		  		primaryText = ",";
		if( keyCode == "KEYCODE_PERIOD" ) 		  		primaryText = ".";
		
		if( keyCode == "KEYCODE_MINUS" )				primaryText = "-";
		if( keyCode == "KEYCODE_EQUALS" )				primaryText = "=";
		if( keyCode == "KEYCODE_LEFT_BRACKET" ) 		primaryText = "{";
		if( keyCode == "KEYCODE_RIGHT_BRACKET") 		primaryText = "}";
		if( keyCode == "KEYCODE_BACKSLASH" )			primaryText = "\\";
		if( keyCode == "KEYCODE_SEMICOLON" )			primaryText = "`";
		if( keyCode == "KEYCODE_APOSTROPHE" )			primaryText = "'";
		if( keyCode == "KEYCODE_SLASH" )				primaryText = "/";
		
		if( keyCode == "KEYCODE_AT" )					primaryText = "@";
		if( keyCode == "KEYCODE_PLUS" )					primaryText = "+";
		if( keyCode == "KEYCODE_PRINT_SCREEN")		  { primaryText = "SCREEN"; secondaryText = "Print"; }
		if( keyCode == "KEYCODE_CAPS_LOCK" )		  { primaryText = "LOCK";   secondaryText = "Caps"; }
		if( keyCode == "KEYCODE_POWER" )      		  { primaryText = "POWER";  secondaryText = "Google TV"; }
		
		if( keyCode == "KEYCODE_META_LEFT" )  		  { primaryText = "LEFT"; 	secondaryText = "Meta"; }
		if( keyCode == "KEYCODE_META_RIGHT" ) 		  { primaryText = "RIGHT"; 	secondaryText = "Meta"; }
		if( keyCode == "KEYCODE_SOFT_LEFT" )  		  { primaryText = "LEFT"; 	secondaryText = "Soft"; }
		if( keyCode == "KEYCODE_SOFT_RIGHT" ) 		  { primaryText = "RIGHT"; 	secondaryText = "Soft"; }
		if( keyCode == "KEYCODE_FLING_TAB" )  		  { primaryText = "FLING"; 	secondaryText = "Tab"; }
		if( keyCode == "KEYCODE_NULL" )  		  	    primaryText = " ";
		if( keyCode == "KEYCODE_SETTINGS" )           {	primaryText = "Google TV";   secondaryText = "Settings"; }
		
		if( keyCode == "UI_KEYBOARD" )        		  { primaryText = "MODE"; 	secondaryText = "Keyboard"; }
		if( keyCode == "UI_VOICE" )        	  		  { primaryText = "VOICE"; 	secondaryText = "Search"; }
		if( keyCode == "UI_TEXT" )        	  		  { primaryText = "TEXT"; 	secondaryText = "Search"; }
				
		primaryText = primaryText.replaceFirst("KEYCODE_NUM", "");
		primaryText = primaryText.replaceFirst("KEYCODE_", "");
		primaryText = primaryText.replaceFirst("APP_", "");
		primaryText = primaryText.replaceFirst("UI_", "");
		
		if( primaryText.contains("TV") || primaryText.contains("BD") || primaryText.contains("RED") || primaryText.contains("BLUE") || primaryText.contains("GREEN") || primaryText.contains("YELLOW") ) 
			primaryText = primaryText.toUpperCase();
		else if( !primaryText.contains("AV Receiver"))
			primaryText = primaryText.substring(0,1).toUpperCase() + primaryText.substring(1).toLowerCase();
		
		
		
		if( primaryText.compareTo("Dvr") == 0 || primaryText.compareTo("Pip") == 0 ) primaryText = primaryText.toUpperCase();
		
		if( primaryText.compareTo("Live tv") == 0) primaryText = "Live TV";
		
		if( primaryText.compareTo("GOOGLE TV") == 0 )	primaryText = "Google TV";
		
		if( primaryText.contains("+") || primaryText.contains("-") ) primaryText = primaryText.toUpperCase();
		
		
		
		if (isPrimaryText)	return primaryText;
		else				return secondaryText;
	}
	
//	public int getIconIndex(String keyCode)
//	{				
//		int index;
//		for(index = 0 ; index < iconListBox.getItemCount() ; index++)
//		{	    	
//			if(iconListBox.getValue(index) == keyCode )
//			{
//				
//				break;
//			}
//		}
//					
//		if( index == iconListBox.getItemCount() )
//			index = 0;
//		
//		return index;
//	}
	
	public int getKeyCodeIndex(String keyCode)
	{				
		int index;
		for(index = 0 ; index < keycodeListBox.getItemCount() ; index++)
		{	    	
			if(keycodeListBox.getValue(index) == keyCode )
			{
				
				break;
			}
		}
					
		if( index == keycodeListBox.getItemCount() )
			index = 0;
		
		return index;
	}
	
	public boolean areAllValuesDefault()
	{
		boolean allDefault = false;
		
		//find if icon is default       //find if name is default	//find if subname is default
		allDefault = isNameDefault() && isSubNameDefault(); //&& isIconDefault();
		
		
		return allDefault;
	}
	
//	public boolean isIconDefault()
//	{
//		boolean isDefault = false;
//		
//		//find if icon is default
////		if(   keycodeListBox.getValue(  keycodeListBox.getSelectedIndex()).compareTo( iconListBox.getValue( iconListBox.getSelectedIndex() )  ) == 0   )
////			isDefault = true;
//		
//		//fix one exception to the rule TAB == RECALL
////		if( keycodeListBox.getValue(  keycodeListBox.getSelectedIndex()).compareTo("KEYCODE_TAB") == 0 
////		    && iconListBox.getValue(  iconListBox.getSelectedIndex()).compareTo("KEYCODE_RECALL") == 0 )
////			isDefault = true;
//		
//		if( keycodeListBox.getSelectedIndex() == 0 )//&& iconListBox.getSelectedIndex() == 0 )
//			isDefault = true;
//		
//		return isDefault;
//	}
	
	public boolean isNameDefault()
	{
		boolean isDefault = false;
		
		if(  getDefaultName( keycodeListBox.getValue(keycodeListBox.getSelectedIndex()),true ).compareTo(  buttonNameTextBox.getValue()  ) == 0 )
			isDefault = true;
		else
			isDefault = false;
		
		if( keycodeListBox.getSelectedIndex() == 0 && buttonNameTextBox.getValue().compareTo("required") == 0 )
			isDefault = true;
		
		return isDefault;
	}
	
	public boolean isSubNameDefault()
	{
		boolean isDefault = false;
		
		if(  getDefaultName( keycodeListBox.getValue(keycodeListBox.getSelectedIndex()),false ).compareTo(  buttonSubNameTextBox.getValue().replace("optional", "")  ) == 0 )
			isDefault = true;
		else
			isDefault = false;
		
		if( keycodeListBox.getSelectedIndex() == 0 && buttonSubNameTextBox.getValue().compareTo("optional") == 0 )
			isDefault = true;
				
		return isDefault;
	}
	
	public void setDefaults()
	{
		int selectedIndex = keycodeListBox.getSelectedIndex();
//		int i;
//	  	for( i = 0 ; i < iconListBox.getItemCount() ; i++)
//		{
//	    	//if(demoScrollButton.hasIconImage())
//
//				if(iconListBox.getValue(i).compareTo( keycodeListBox.getValue(selectedIndex) ) == 0)
//				{
//					iconEnabledCheckBox.setValue(true);
//			  		buttonTextCheckBox.setValue(false);
//																							
//					break;
//				}
//   	
//		}
//	  	if(i == iconListBox.getItemCount())
//	  	{
//	  		i = 0;
//	  		
////	  		demoScrollButton.disableIconImage();
//
//	  		iconEnabledCheckBox.setValue(false);
//	  		buttonTextCheckBox.setValue(true);
//	  	}
//	  	
//		iconListBox.setSelectedIndex(i);
		
		String defaultName = getDefaultName( keycodeListBox.getValue(selectedIndex), true);
		if (defaultName.compareTo("") != 0)
		{
			buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
			buttonNameTextBox.setValue( defaultName );
		}
		else
		{
			buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
			buttonNameTextBox.setValue( "required" );
		}
  		buttonNameTextBox.setValue( defaultName );
		demoScrollButton.setBoldName( defaultName );
  		
		String defaultSubName = getDefaultName( keycodeListBox.getValue(selectedIndex), false);
		if (defaultSubName.compareTo("") != 0)
		{
			buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
			buttonSubNameTextBox.setValue( defaultSubName );
		}
		else
		{
			buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
			buttonSubNameTextBox.setValue( "optional" );
		}
		demoScrollButton.setNormName( defaultSubName );
		
		
	  	
	  	
	  	
	  	int keyCodeSelectedIndex = keycodeListBox.getSelectedIndex();
	  	
	  	String buttonKeyCode = keycodeListBox.getValue(keyCodeSelectedIndex);
		if(buttonKeyCode.compareTo("KEYCODE_TAB") == 0)
		{
			buttonKeyCode = "KEYCODE_"+ getDefaultName("KEYCODE_TAB", true).toUpperCase();
//			iconListBox.setSelectedIndex( getIconIndex(buttonKeyCode) );
		}
		
//		int iconSelectedIndex = iconListBox.getSelectedIndex();
		
//		if( iconListBox.getValue(iconSelectedIndex).compareTo("Select an icon...") != 0 )
//			demoScrollButton.setIconImage( "images/icons_" + buttonKeyCode + ".png", "images/icons_" + buttonKeyCode + "_touched.png");
		
		
	}
	
	public void initdemoScrollButton(String name , String keycode , int index, String subName)
	{
		this.demoScrollButton = null;
		this.demoScrollButton = new ScrollButton(name , subName, keycode , index);
		this.demoScrollButton.setSize("200" + "px", "59" + "px");
		this.demoScrollButton.addStyleName("gtvRemote-scrollMenuPushButton-border-demo");
	}
	
	public void saveInitSettings(String keycode, String name, String subName, boolean defaultsEnabled)
    {
    	this.initKeycode = keycode;
//    	this.initIcon = icon;
    	this.initName = name.replaceAll("required","");
    	this.initSubName = subName.replaceAll("optional","");
//    	this.initIconEnabled = iconEnabled;
    	this.initDefaultsEnabled = defaultsEnabled;
    }
	
	public boolean matchInitSettings(boolean notDefaultsCheckBox, boolean notKeyCodeTextBox)
	{
		boolean matchingBool = false;
		
		
		if (keycodeListBox.getValue(keycodeListBox.getSelectedIndex()).compareTo(initKeycode)    == 0
//			&& iconListBox.getValue(iconListBox.getSelectedIndex()).compareTo(initIcon) 	     == 0
			&& buttonNameTextBox.getValue().replaceAll("required","").compareTo(initName) 	     == 0
			&& buttonSubNameTextBox.getValue().replaceAll("optional","").compareTo(initSubName)  == 0
//			&& iconEnabledCheckBox.getValue() == initIconEnabled									
			)
				matchingBool = true;
		
		
		if(buttonNameTextBox.getValue().compareTo("") != 0 && buttonNameTextBox.getValue().compareTo("required") != 0)
		{
									
//			if( !( iconListBox.getSelectedIndex() == 0 && iconEnabledCheckBox.getValue() ) )
//			{
//				if( keycodeListBox.getSelectedIndex() == 0 )
//				{
//					matchingBool = true;
//				}
//			}
//			else
//			{
//				matchingBool = true;
//			}
		}
		else
		{
			matchingBool = true;
		}
		
		if( matchingBool && notDefaultsCheckBox )
		{
			if(initDefaultsEnabled)
				defaultsCheckBox.setValue(true);
			else
				defaultsCheckBox.setValue(false);
		}
		
		if ( !matchingBool && notDefaultsCheckBox && notKeyCodeTextBox )
			defaultsCheckBox.setValue(false);
		
		if( !matchingBool )
			closeButton.setText("Cancel");
		else
			closeButton.setText("Close");
		
		
		return matchingBool;
	}
	
	public void saveSettings()
	{
		
		scrollButton.setBoldName(	stripHTML( demoScrollButton.getBoldName()   )   );
		scrollButton.setNormName(stripHTML( demoScrollButton.getNormName())   );
		scrollButton.setKeyCode(demoScrollButton.getKeyCode().replaceAll("KEYCODE_", "SP_KEYCODE_"));
//		if( !iconEnabledCheckBox.getValue() )
//			demoScrollButton.removeIconImage();
//		if(demoScrollButton.hasIconImage())
//			scrollButton.setIconImage(demoScrollButton.getIconPath(), demoScrollButton.getIconPath().replaceFirst(".png", "_touched.png"));
//		else
//			scrollButton.removeIconImage();
//		
//		if(demoScrollButton.iconImageEnabled())
//			scrollButton.enableIconImage();
//		else 
//			scrollButton.disableIconImage();
//		
//		
//		if ( scrollButton.isWide() ) {
//			scrollButton.makeWide();
//		}
			
		
		saveInitSettings( keycodeListBox.getValue(	keycodeListBox.getSelectedIndex()), 
 				  									buttonNameTextBox.getValue(), 
 				  									buttonSubNameTextBox.getValue(),  
 				  									defaultsCheckBox.getValue() );
		
		matchInitSettings(true,true);
		
		saveButton.setEnabled(false);
		
		buildAndSaveJsonString(currentPanelIndex);
	}
	
	private void loadNextButtonToEdit(boolean moveForward)
	{
		
		if( !matchInitSettings(true,true) )	//If changes made, then save.
			saveSettings();
		
		int nextButtonIndex = demoScrollButton.getRowIndex();
		if (moveForward)
			nextButtonIndex++;
		else
			nextButtonIndex--;
		
//		if( currentPanelIndex == 1 && ( nextButtonIndex == 4 || nextButtonIndex == 18 || nextButtonIndex == 20 || nextButtonIndex == 22 || nextButtonIndex == 24 ) )
//		{
//			if (moveForward)
//				nextButtonIndex++;
//			else
//				nextButtonIndex--;			
//		}
		
		if (nextButtonIndex == 0 || nextButtonIndex == 33)
		{
//			if(currentPanelIndex == 1)
//				currentPanelIndex = 2;
//			else if(currentPanelIndex == 2)
//				currentPanelIndex = 1;

//			remoteButtons = allRemoteButtons[currentPanelIndex];
		}
		
		scrollButtonListCount = scrollButtons.length;
		
		if (nextButtonIndex == 0)
			nextButtonIndex = scrollButtonListCount-1;
		else if (nextButtonIndex == scrollButtonListCount)
			nextButtonIndex = 1;
		
		
		
		
		
		
		
		scrollButton = scrollButtons[nextButtonIndex];
		editScrollButtonDialogBox.setText("Edit Scroll Button [ " + altIndexToDisplay() + " ]" );
		demoScrollButton.setBoldName(scrollButton.getBoldName());
		demoScrollButton.setNormName(scrollButton.getNormName());
		demoScrollButton.setKeyCode(scrollButton.getKeyCode().replaceAll("SP_KEYCODE_", "KEYCODE_"));
		demoScrollButton.setRowIndex(scrollButton.getRowIndex());
//		demoScrollButton.setStyleIndex(scrollButton.getRowIndex());
		
		keycodeListBox.setSelectedIndex( getKeyCodeIndex(demoScrollButton.getKeyCode()) );
		
//		if(scrollButton.hasIconImage())
//		{
//			
//			demoScrollButton.setIconImage(scrollButton.getIconPath(), scrollButton.getIconPath().replaceFirst(".png", "_touched.png"));
//			if(scrollButton.getIconPath() != null)
//		    	iconListBox.setSelectedIndex( getIconIndex(scrollButton.getIconPath().replaceFirst("images/icons_", "").replaceFirst(".png", "")) );
//						
//		}
//		if(scrollButton.iconImageEnabled())
//		{
//			demoScrollButton.enableIconImage();
//			buttonTextCheckBox.setValue(false);
//			iconEnabledCheckBox.setValue(true);
//		}
//		else
//		{
//			demoScrollButton.disableIconImage();
//			iconListBox.setSelectedIndex( 0 );
//			iconEnabledCheckBox.setValue(false);
//			buttonTextCheckBox.setValue(true);
//		}
		
		if(scrollButton.getBoldName().compareTo("") != 0)
	    {
	    	buttonNameTextBox.setText(scrollButton.getBoldName());
	    	buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    }
		else
		{
			buttonNameTextBox.setText("required");
	    	buttonNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
		}
	    
	    if(scrollButton.getNormName().compareTo("") != 0)
	    {
	    	buttonSubNameTextBox.setText(scrollButton.getNormName());
	    	buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox");
	    }
	    else
	    {
	    	buttonSubNameTextBox.setText("optional");
	    	buttonSubNameTextBox.setStyleName("gtvremote-DialogBox-textBox-empty");
		}
	    
	    
	    if ( areAllValuesDefault() )
	    	defaultsCheckBox.setValue(true);
	    else
	    	defaultsCheckBox.setValue(false);
	    
	    saveInitSettings( keycodeListBox.getValue(	keycodeListBox.getSelectedIndex()), 
	    				  							buttonNameTextBox.getValue(), 
	    				  							buttonSubNameTextBox.getValue(), 
	    				  							defaultsCheckBox.getValue() );
	    
		
	}
	
	public int altIndexToDisplay()
	{
		int index = scrollButton.getRowIndex();
		
		if(currentPanelIndex == 1)
		{
			if(index == 19)
				index = 18;
			else if (index == 21)
				index = 19;
			else if (index == 23)
				index = 20;
			else if (index >= 25 && index <= 32)
				index = index - 4;
			
		}
		
		return index;
	}
	
	private void updateToNextOnKeycodeList(int offSet)
	{
		int nextIndex = keycodeListBox.getSelectedIndex() + offSet;
	
		if( nextIndex < keycodeListBox.getItemCount() && nextIndex > 0 )
		{
			keycodeListBox.setSelectedIndex(nextIndex);
			
			int selectedIndex = keycodeListBox.getSelectedIndex();
			 
			if(keycodeListBox.getValue(selectedIndex).compareTo("Select a keycode...") != 0)
			{
				 demoScrollButton.setKeyCode( keycodeListBox.getValue(selectedIndex) );
				
				 if( defaultsCheckBox.getValue() )
					 setDefaults();
			}
			
			if(matchInitSettings(true,false))
				 saveButton.setEnabled(false);
			else
				 saveButton.setEnabled(true);
		}
	}
	
	public void buildAndSaveJsonString(int currentIndex)
	{
		ScrollButton buttons[] = scrollButtons;
		String jsonString = "[";
		
		for(int i=1; i < buttons.length ;i++)
		{
			jsonString += "{";
			jsonString += "   \"index\"   	:	"   + buttons[i].getRowIndex() 					+ ",";
			jsonString += "   \"name\"    	:	\"" + buttons[i].getNormName().replace("\\", "\\\\").replace("\"", "\\\"")    + "\",";
			jsonString += "   \"subName\" 	:	\"" + buttons[i].getBoldName().replace("\\", "\\\\").replace("\"", "\\\"") + "\",";
			jsonString += "   \"keyCode\" 	:	\"" + buttons[i].getKeyCode() 						+ "\",";
//			if(buttons[i].getIconPath() != null)
//			{
//				jsonString += "   \"iconUp\"  	:	\"" + buttons[i].getIconPath() 	+ "\",";
//				jsonString += "   \"iconDown\"	:	\"" + buttons[i].getIconPath().replaceFirst(".png", "_touched.png") + "\",";
//			}
//			else
//			{
				jsonString += "   \"iconUp\"  	:	\"" + "" 	+ "\",";
				jsonString += "   \"iconDown\"	:	\"" + "" 	+ "\",";
//			}
			jsonString += "},";
		}
		jsonString += "]";
		
		saveJsonString(currentIndex, jsonString);
	}
	
	private String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	
	public static native void saveJsonString(int jsonStringChoice, String jsonString) /*-{
	
		if ( jsonStringChoice == 1) {
			$wnd.backgroundPageWindow.remotePanel1JsonString = jsonString;
		}
		else if ( jsonStringChoice == 2) {
			$wnd.backgroundPageWindow.remotePanel2JsonString = jsonString;
		}
		else if ( jsonStringChoice == 3) {
			$wnd.backgroundPageWindow.scrollPanelJsonString = jsonString;
		}
		$wnd.backgroundPageWindow.saveJsonToLocalStorage();
	}-*/;
	
	public static native String getDetectedOS()  /*-{
		return $wnd.backgroundPageWindow.osDetected;
	}-*/;
	
	public static native void sendButtonKeyEvent(String keyCode, boolean keyDown) /*-{
		$wnd.sendKeyEvent(keyCode, keyDown);
		if(keyDown)
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' button \'UP\' sent to connected device.');
		else
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' button \'DOWN\' sent to connected device.');	
	}-*/;
	
	public static native String getTabOrPopUpType() /*-{
		return $wnd.tabOrPopUp;
	}-*/;
	
	public static native void toast(String keyCode) /*-{
		$wnd.displayToast(keyCode);
	}-*/;
}
