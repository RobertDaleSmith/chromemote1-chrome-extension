package com.chromemote.crx.client.ui;

import com.chromemote.crx.client.KeyCodeLabel;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.FlexTable;

public class ScrollButtonPanel extends CustomScrollPanel {

	private KeyCodeLabel keyCodePressedLabel;
	
	private static ScrollButton scrollButtons[];
	
	private int scrollButtonListCount;
	
	private int rowCount = 0;
	
	private FlexTable flexTable;
	
	private ClickHandler keyCodeHandler;
	
	public ScrollButtonPanel(String jsonUrlPathString, KeyCodeLabel callBackCodelabel, ClickHandler clickHandler)
	{
		super( new FlexTable() );
		keyCodePressedLabel = callBackCodelabel;
		keyCodeHandler = clickHandler;
		flexTable = (FlexTable) this.getWidget();
		flexTable.setCellSpacing(0);
		flexTable.setCellPadding(0);
		this.setStyleName("gtvRemote-scrollPanelMenu");
		
		this.setSize("320px", "480px");
		
		this.removeHorizontalScrollbar();
		//this.removeVerticalScrollbar();
		
		this.setAlwaysShowScrollBars(false);
		
		//loadButtonPropertiesList(jsonUrlPathString);  //deprecated
		
		
		//displayError(getJsonString(URL.encode(jsonUrlPathString)));
		
		String jsonString = getJsonString();
				
		parseJsonPanelButtons( jsonString );
		drawScrollPanel();
		exportStaticMethod();
	}
	
	DeviceContextMenu deviceContextMenu;
	
	private void setDefaultHandlers(final ScrollButton scrollButton)
	{		
		final String keyCodeString = scrollButton.getKeyCode();
		
		scrollButton.addClickHandler(new ClickHandler() 
		{ 
			public void onClick(ClickEvent event) 
			{ 
				if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
				{ 
					buttonCallListener(keyCodeString);
					
					//String keyEventCode; 
					//keyEventCode = keyCodeString.replaceAll("SP_", "");
					//keyEventCode = keyEventCode.replaceAll("KEYCODE_", "");
					//sendButtonKeyEvent( keyEventCode ); //Old method
				}
				
			} 
		});
		
		
		
		scrollButton.addMouseDownHandler(new MouseDownHandler(){

			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
					sendButtonKeyEvent( scrollButton.getKeyCode().replaceAll("KEYCODE_", "").replaceAll("SP_", ""), true );
				else
				{
					int x = event.getClientX();
					int y = event.getClientY();
					
					deviceContextMenu = new DeviceContextMenu(x,y);
					
					
					deviceContextMenu.addMenuCommand("Edit", new ClickHandler(){public void onClick(ClickEvent event) { 
						
						
						disableKeyBoardEvents();
						
						launchScrollButtonEditDialogBox(scrollButton.getRowIndex());
						
						
						
					}});
					deviceContextMenu.addMenuCommand("Remove"	, new ClickHandler(){public void onClick(ClickEvent event) { 
						removeButton(scrollButton.getRowIndex());
					}});
					scrollButtonListCount = scrollButtons.length;
					
					if(scrollButton.getRowIndex() != 1)
						deviceContextMenu.addMenuCommand("Move Up"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(scrollButton.getRowIndex(), "UP");
						}});
					if(scrollButton.getRowIndex() != scrollButtonListCount-1)
						deviceContextMenu.addMenuCommand("Move Down"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(scrollButton.getRowIndex(), "DOWN");
						}});
					if(scrollButton.getRowIndex() != 1)
						deviceContextMenu.addMenuCommand("Move to Top"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(scrollButton.getRowIndex(), "TOP");
						}});
					if(scrollButton.getRowIndex() != scrollButtonListCount-1)
						deviceContextMenu.addMenuCommand("Move to End"	, new ClickHandler(){public void onClick(ClickEvent event) { 
							swapButtons(scrollButton.getRowIndex(), "BOTTOM");
						}});
					
					deviceContextMenu.addMenuCommand("Add New"	, new ClickHandler(){public void onClick(ClickEvent event) { 
						addNewButton(scrollButton.getRowIndex());
					}});
					
					deviceContextMenu.Show();
				}	
			}
		});
		
		scrollButton.addMouseUpHandler(new MouseUpHandler(){

			@Override
			public void onMouseUp(MouseUpEvent event) {
				
				if (event.getNativeButton() != NativeEvent.BUTTON_RIGHT) 
					sendButtonKeyEvent( scrollButton.getKeyCode().replaceAll("KEYCODE_", "").replaceAll("SP_", ""), false );
				
			}
		});

	}
	
	private void addClickHandler(ScrollButton scrollButton, ClickHandler clickHandler)
	{
		scrollButton.addClickHandler( clickHandler );
	}
	
	private void buttonCallListener(String callCode)
	{
		keyCodePressedLabel.setText(callCode);
	}
	
	private void drawScrollPanel()
	{
		for( int i=1 ; i < scrollButtons.length ; i++ )
		{
			setDefaultHandlers(scrollButtons[i]);
			addClickHandler(scrollButtons[i], keyCodeHandler);
			flexTable.setWidget(i, 0, scrollButtons[i]);
			rowCount++;
		}
	}
	
	private void reDrawScrollPanel()
	{
		flexTable.removeAllRows();
		for( int i=1 ; i < scrollButtons.length ; i++ )
		{
			
			flexTable.setWidget(i, 0, scrollButtons[i]);
			
		}
	}
	
	private void loadButtonPropertiesList(final String JSON_URL) 
	{
	    String url = JSON_URL;

	    url = URL.encode(url);

	    // Send request to server and catch any errors.
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

	    try 
	    {
	    	@SuppressWarnings("unused")
	    	Request request = builder.sendRequest(null, new RequestCallback() {
	    		public void onError(Request request, Throwable exception) {
	    			displayError("Couldn't retrieve JSON");
	    		}

	    		public void onResponseReceived(Request request, Response response) {
	    			if (200 == response.getStatusCode()) {
	    				
	    				//loadPanelButtons( asArrayOfRButtonProperties(response.getText()) );
	    				parseJsonPanelButtons( response.getText() );
	    				drawScrollPanel();
	    				
	    			} 
	    			else {
	    				displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")" + JSON_URL);
	    			}
	    		}
	    	});
	    	
	    } catch (RequestException e) {
	      displayError("Couldn't retrieve JSON");
	    }
	    
	}
	
//	private void loadPanelButtons(JsArray<RemoteButtonProperties> buttons) {
//		rowCount = buttons.length();
//		scrollButtons = new ScrollButton[rowCount+1];
//		
//		for (int i = 0; i < buttons.length(); i++) {
//			RemoteButtonProperties p = buttons.get(i);
//			displayError(p.getSubName() + ", " + p.getName() + ", " +  p.getKeyCode() + ", " + i);
//			addScrollButton( p.getSubName(), p.getName(), p.getKeyCode(), i );
//		}
//		
//	}
	
	
private void parseJsonPanelButtons(String jsonString) {
		
		boolean arrayOpened 	  = false;
		boolean objectOpened	  = false;
		boolean objectNameOpened  = false;
		boolean objectNameFound   = false;
		String  objectNameString  = "";
		boolean objectValueOpened = false;
		boolean objectValueFound   = false;
		String	objectValueString = "";
		boolean readyForValue 	  = false;
		
		int indexTemp = 0;
		String nameTemp = "", subNameTemp = "", keyCodeTemp = ""; //iconUpTemp = "", iconDownTemp = "";
		
		scrollButtons = new ScrollButton[rowCount+1];
		
		for (int n=0; n < jsonString.length(); n++) {
			char singleChar = jsonString.charAt(n);
			
			
			if ( singleChar == 44 && objectOpened && objectValueFound)  //		,
			{
				//displayError(objectNameString + " : " + objectValueString);
				if(objectNameString.equals("index"))
				{
					objectValueString = objectValueString.replaceAll("\\s+", "");
					indexTemp = Integer.parseInt( objectValueString );
				}
				else if(objectNameString.equals("name"))
				{
					nameTemp = objectValueString;
				}
				else if(objectNameString.equals("subName"))
				{
					subNameTemp = objectValueString;
				}
				else if(objectNameString.equals( "keyCode"))
				{
					keyCodeTemp = objectValueString;
				}
//				else if(objectNameString.equals("iconUp"))
//				{
//					iconUpTemp = objectValueString;
//				}
//				else if(objectNameString.equals("iconDown"))
//				{
//					iconDownTemp = objectValueString;
//				}
				
				objectNameFound   = false;
				objectNameString  = "";
				objectValueFound  = false;
				objectValueString = "";
				readyForValue	  = false;
			}
			
			
			if ( singleChar == 91)  //		[
			{
				arrayOpened = true;
			}
			if ( singleChar == 93)  //		]
			{
				arrayOpened = false;
			}
			if ( singleChar == 123 && arrayOpened)  //		{
			{
				objectOpened = true;
			}
			if ( singleChar == 125 && arrayOpened)  //		}
			{
				objectOpened = false;
				//displayError(subNameTemp + ", " + nameTemp + ", " + keyCodeTemp + ", " + indexTemp);
				addScrollButton(subNameTemp, nameTemp, keyCodeTemp, indexTemp);
				
				nameTemp = ""; subNameTemp = ""; keyCodeTemp = ""; //iconUpTemp = ""; iconDownTemp = "";
			}
			
			if ( singleChar == 34 && objectNameOpened == false && objectNameFound == false)  //		"
			{
				objectNameOpened = true;
			}
			else if ( singleChar == 34 && objectNameOpened == true && objectNameFound == false)  //		"
			{
				objectNameOpened = false;
				objectNameFound  = true;
			}
			else if ( singleChar == 34 && objectValueOpened == false && objectNameFound == true)  //		"
			{
				objectValueOpened = true;
			}
			else if ( singleChar == 34 && objectValueOpened == true && objectNameFound == true)  //		"
			{
				objectValueOpened = false;
				objectValueFound  = true;
			}
			
			if ( singleChar == 58)  //		:
			{
				readyForValue = true;
			}
			
			
			if ( ( (singleChar >= 32 && singleChar <= 90) || (singleChar >= 94 && singleChar <= 122)  ) && singleChar != 58 && singleChar != 34 && objectNameOpened == true)
			{
				objectNameString = objectNameString + singleChar;
			}
			
			if ( ( (singleChar >= 32 && singleChar <= 90) || (singleChar >= 94 && singleChar <= 122)  ) && singleChar != 58 && singleChar != 34 && readyForValue == true && objectValueOpened == true)
			{
				objectValueString = objectValueString + singleChar;
			}
			else if((singleChar >= 48 && singleChar <= 57) && readyForValue)
			{
				objectValueString = objectValueString + singleChar;
				objectValueFound = true;
			}
			
			
			
		}
		
		

		
	}
	
	
	
	
	private void addScrollButton(String subName, String name, String code, int index)
	{
		scrollButtons[index] = new ScrollButton(subName, name, code, index);
	}
	
	private void addNewButton(int rowIndex)
	{
		ScrollButton tempScrollButtons[] = scrollButtons;
		rowCount++;
		scrollButtons = new ScrollButton[rowCount];
		
		for(int index=1; index < tempScrollButtons.length ;index++)
		{
			scrollButtons[index] = tempScrollButtons[index];
		}
		addScrollButton("","","NULL",tempScrollButtons.length);
		setDefaultHandlers(scrollButtons[tempScrollButtons.length]);
		addClickHandler(scrollButtons[tempScrollButtons.length], keyCodeHandler);
		for(int index = tempScrollButtons.length; index > rowIndex ; index--)
		{
			ScrollButton thisScrollButton = scrollButtons[index];
			ScrollButton nextScrollButton = scrollButtons[index-1];
			ScrollButton tempScrollButton = new ScrollButton(thisScrollButton.getBoldName(),thisScrollButton.getNormName(),thisScrollButton.getKeyCode(),thisScrollButton.getRowIndex());
			tempScrollButton.setBoldName(thisScrollButton.getBoldName());
				
			thisScrollButton.setNormName(nextScrollButton.getNormName());
			thisScrollButton.setKeyCode(nextScrollButton.getKeyCode());
	
			thisScrollButton.setBoldName(nextScrollButton.getBoldName());
	
			nextScrollButton.setNormName(tempScrollButton.getNormName());
			nextScrollButton.setKeyCode(tempScrollButton.getKeyCode());
	
			nextScrollButton.setBoldName(tempScrollButton.getBoldName());
		}
		
		reDrawScrollPanel();
		
		launchScrollButtonEditDialogBox(rowIndex);
	}
	
	private void removeButton(int rowIndex)
	{
		
		swapButtons(rowIndex, "BOTTOM");
		
		flexTable.removeRow(rowCount);
		
		rowCount--;
		
		
		ScrollButton tempScrollButtons[] = scrollButtons;
				
		for(int index = 1 ; index <= rowCount ; index++)
		{
			tempScrollButtons[index] = scrollButtons[index];
		}
		
		scrollButtons = new ScrollButton[rowCount];
		
		for(int index = 1 ; index <= rowCount ; index++)
		{
			scrollButtons[index] = tempScrollButtons[index];
		}
		
		buildAndSaveJsonString(3);
		
	}
	
	
	private void swapButtons(int thisIndex, String direction)
	{
		
		int nextIndex = thisIndex;
		
		if (direction.compareTo("DOWN") == 0)
			nextIndex++;
		else if (direction.compareTo("UP") == 0)
			nextIndex--;
		else if (direction.compareTo("TOP") == 0)
			nextIndex = scrollButtonListCount;
		else if (direction.compareTo("BOTTOM") == 0)
			nextIndex = 0;
		
		scrollButtonListCount = scrollButtons.length;
		
		if (nextIndex >= scrollButtonListCount)
		{	//Move to top
			
			for(int index = thisIndex; index > 1 ; index--)
			{
				ScrollButton thisScrollButton = scrollButtons[index];
				ScrollButton nextScrollButton = scrollButtons[index-1];
				ScrollButton tempScrollButton = new ScrollButton(thisScrollButton.getBoldName(),thisScrollButton.getNormName(),thisScrollButton.getKeyCode(),thisScrollButton.getRowIndex());
				tempScrollButton.setBoldName(thisScrollButton.getBoldName());
					
				thisScrollButton.setNormName(nextScrollButton.getNormName());
				thisScrollButton.setKeyCode(nextScrollButton.getKeyCode());
		
				thisScrollButton.setBoldName(nextScrollButton.getBoldName());
		
				nextScrollButton.setNormName(tempScrollButton.getNormName());
				nextScrollButton.setKeyCode(tempScrollButton.getKeyCode());
		
				nextScrollButton.setBoldName(tempScrollButton.getBoldName());
			}
		}
		else if (nextIndex <= 0)
		{	//Move to bottom
			
			for(int index = thisIndex; index < scrollButtonListCount-1 ; index++)
			{
				ScrollButton thisScrollButton = scrollButtons[index];
				ScrollButton nextScrollButton = scrollButtons[index+1];
				ScrollButton tempScrollButton = new ScrollButton(thisScrollButton.getBoldName(),thisScrollButton.getNormName(),thisScrollButton.getKeyCode(),thisScrollButton.getRowIndex());
				tempScrollButton.setBoldName(thisScrollButton.getBoldName());
					
				thisScrollButton.setNormName(nextScrollButton.getNormName());
				thisScrollButton.setKeyCode(nextScrollButton.getKeyCode());
		
				thisScrollButton.setBoldName(nextScrollButton.getBoldName());
		
				nextScrollButton.setNormName(tempScrollButton.getNormName());
				nextScrollButton.setKeyCode(tempScrollButton.getKeyCode());
		
				nextScrollButton.setBoldName(tempScrollButton.getBoldName());
			}
		}
		else
		{	//Normal Swap
			ScrollButton thisScrollButton = scrollButtons[thisIndex];
			ScrollButton nextScrollButton = scrollButtons[nextIndex];
			ScrollButton tempScrollButton = new ScrollButton(thisScrollButton.getBoldName(),thisScrollButton.getNormName(),thisScrollButton.getKeyCode(),thisScrollButton.getRowIndex());
			tempScrollButton.setBoldName(thisScrollButton.getBoldName());
				
			thisScrollButton.setNormName(nextScrollButton.getNormName());
			thisScrollButton.setKeyCode(nextScrollButton.getKeyCode());
	
			thisScrollButton.setBoldName(nextScrollButton.getBoldName());
	
			nextScrollButton.setNormName(tempScrollButton.getNormName());
			nextScrollButton.setKeyCode(tempScrollButton.getKeyCode());
	
			nextScrollButton.setBoldName(tempScrollButton.getBoldName());
		}
				
		buildAndSaveJsonString(3);
		
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
			jsonString += "   \"iconUp\"  	:	\"" + "" 	+ "\",";
			jsonString += "},";
		}
		jsonString += "]";
		
		saveJsonString(currentIndex, jsonString);
	}
	
	private String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	
	private static void launchScrollButtonEditDialogBox(int rowIndex)
	{
		int panelIndex = 3; 
		final EditScrollButtonDialogBox editScrollButtonDialogBox = new EditScrollButtonDialogBox(scrollButtons, panelIndex, rowIndex);
		ClickHandler clickListener = new ClickHandler()
	    {	public void onClick(ClickEvent event) 
			{
	    		if(editScrollButtonDialogBox.getIPResponse().compareTo("cancel") == 0)
	    		{
	    			editScrollButtonDialogBox.Close();
	    		    enableKeyBoardEvents();
	    		}
	    		else if(editScrollButtonDialogBox.getNameResponse().compareTo("null") != 0 && editScrollButtonDialogBox.getNameResponse().compareTo("") != 0)
	    		{
	    		    enableKeyBoardEvents();
	    		    
	    		}	
	    			
			}
	    };
	    editScrollButtonDialogBox.addClickListener(clickListener);
	    editScrollButtonDialogBox.Show();
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
	
	
//	private final native JsArray<RemoteButtonProperties> asArrayOfRButtonProperties(String json) /*-{
//		return eval(json);
//	}-*/;

	public static native void displayError(String error) /*-{
		$wnd.backgroundPageWindow.console.log(error);
	}-*/;
		
	public static native void sendButtonKeyEvent(String keyCode, boolean keyDown) /*-{
		$wnd.sendKeyEvent(keyCode, keyDown);
	
		if(keyDown)
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' scroll button down sent to connected device.');
		else
			$wnd.backgroundPageWindow.console.log('Chromemote keycode \' ' + keyCode + ' \' scroll button up sent to connected device.');	
	}-*/;
	
	public static native void toast(String keyCode) /*-{
		$wnd.displayToast(keyCode);
	}-*/;
	
	public static native String getJsonString() /*-{
	
		var tempString;
		//$wnd.backgroundPageWindow.console.log($wnd.backgroundPageWindow.remotePanel1JsonString);
		
		tempString = $wnd.backgroundPageWindow.scrollPanelJsonString;
			
		return tempString;
	
	}-*/;
	
	public static native void disableKeyBoardEvents() /*-{
	
		$wnd.disableKeyBoardEvents();

	}-*/;

	public static native void enableKeyBoardEvents() /*-{

		$wnd.enableKeyBoardEvents();

	}-*/;
	

	
	public static native void exportStaticMethod() /*-{
		
		$wnd.launchScrollButtonEditDialogBox  = $entry( @com.chromemote.crx.client.ui.ScrollButtonPanel::launchScrollButtonEditDialogBox(I) );
		
	}-*/;
}
