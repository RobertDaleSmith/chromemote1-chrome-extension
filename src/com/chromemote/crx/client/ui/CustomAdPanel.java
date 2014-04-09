package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;

public class CustomAdPanel extends AbsolutePanel {
	String adListJsonString = "";
	int adCountInt = 0;
	int index = 1;
	
	HTML[] ads;
	HTML adSpaceHTML = new HTML();
	
	public CustomAdPanel(){
		super();
		this.setSize("320px","50px");

		adListJsonString = getAdJsonString();

		parseJsonPanelButtons(adListJsonString);

		if(adCountInt == 0)
		{
			adCountInt = 1;
			adListJsonString = "[{\"index\": 1,\"imgUrl\":\"http://chromemote.com/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/keep-chromemote-running/\",\"fling\":\"FALSE\",},]";
			parseJsonPanelButtons(adListJsonString);
		}	
						
		index = Random.nextInt(adCountInt) + 1;
		
		//toast(index+"");
		adSpaceHTML = ads[index];
		index++;
		
		add( adSpaceHTML );
		Timer t = new Timer(){
			public void run() {
				if(index > adCountInt)
					index = Random.nextInt(adCountInt) + 1;
				adSpaceHTML.removeFromParent();
				adSpaceHTML = ads[index];
				add( adSpaceHTML );
//				index++;
				index = Random.nextInt(adCountInt) + 1;
				
			}
		};
		t.run();
		t.scheduleRepeating(60000);
	}
	
	public static native void toast(String msg) /*-{
		$wnd.displayToast(msg);
	}-*/;
	
	public static native String getAdJsonString() /*-{
		return backgroundPageWindow.sponsorListJsonString;
	}-*/;
	
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
		boolean lastWasBackSlash  = false;
		int		objectsFoundCount = 0;		
		String 	refUrl = "", 
				imgUrl = "";
		
		for (int n=0; n < jsonString.length(); n++) 
		{
			char singleChar = jsonString.charAt(n);
			
			
			if ( singleChar == 44 && objectOpened && objectValueFound)  //		,
			{
				//displayError(objectNameString + " : " + objectValueString);
				if(objectNameString.equals("index"))
				{
					objectValueString = objectValueString.replaceAll("\\s+", "");
					//indexTemp = Integer.parseInt( objectValueString );
				}
				else if(objectNameString.equals("refUrl"))
				{
					refUrl = stripHTML(objectValueString);
				}
				else if(objectNameString.equals("imgUrl"))
				{
					imgUrl = stripHTML(objectValueString);
				}
				
				objectNameFound   = false;
				objectNameString  = "";
				objectValueFound  = false;
				objectValueString = "";
				readyForValue	  = false;

				objectValueOpened = false;
			}

			if ( singleChar == 91 && !objectOpened)  //		[
			{
				arrayOpened = true;
			}
			if ( singleChar == 93 && !objectOpened)  //		]
			{
				arrayOpened = false;
			}
			if ( singleChar == 123 && arrayOpened && !objectValueOpened && !objectNameOpened)  //		{
			{
				objectOpened = true;
			}
			if ( singleChar == 125 && arrayOpened && !objectValueOpened && !objectNameOpened)  //		}
			{
				objectsFoundCount++;
				
				objectOpened = false;
				addSponsoredAd(objectsFoundCount, refUrl, imgUrl);
				
				objectValueFound  = false;
				refUrl = ""; imgUrl = ""; //keyCodeTemp = ""; iconUpTemp = ""; iconDownTemp = "";
			}
			
			if ( singleChar == 34 && objectNameOpened == false && objectNameFound == false)  //		"
			{
				objectNameOpened = true;
			}
			else if ( singleChar == 34 && objectNameOpened == true && objectNameFound == false && !lastWasBackSlash)  //		"
			{
				objectNameOpened = false;
				objectNameFound  = true;
			}
			else if ( singleChar == 34 && objectValueOpened == false && objectNameFound == true)  //		"
			{
				objectValueOpened = true;
			}
			else if ( singleChar == 34 && objectValueOpened == true && objectNameFound == true && !lastWasBackSlash)  //		"
			{
				objectValueFound  = true;
			}

			if ( singleChar == 58 && !objectNameOpened && !objectValueOpened && objectNameFound)  //		:
			{
				readyForValue = true;
			}

			if ( ( (singleChar >= 32 && singleChar <= 254)  ) && singleChar != 58 && singleChar != 34 && objectNameOpened)
			{
				objectNameString = objectNameString + singleChar;
			}
			
			if ( ( (singleChar >= 32 && singleChar <= 254)  ) && singleChar != 34 && singleChar != 92 && readyForValue && objectValueOpened && !objectNameOpened)
			{
				objectValueString = objectValueString + singleChar;
			}
			else if((singleChar >= 48 && singleChar <= 57) && readyForValue)
			{
				objectValueString = objectValueString + singleChar;
				objectValueFound = true;
			}
			else if( singleChar == 34 && lastWasBackSlash && objectValueOpened)
			{
				objectValueString = objectValueString + "\"";
			}
			else if( singleChar == 92 && lastWasBackSlash && objectValueOpened)
			{
				objectValueString = objectValueString + "\\";
			}
			
			if ( singleChar == 92  && !lastWasBackSlash)  		//		\
				lastWasBackSlash = true;
			else if ( lastWasBackSlash )
				lastWasBackSlash = false;
		}
		
	}
		
	private void addSponsoredAd(int objectsFoundCount, String refUrl, String imgUrl)
	{
		HTML[] tempList = ads;
		ads = new HTML[objectsFoundCount+1];
		
		for(int n=1; n < objectsFoundCount ; n++)
		{
			ads[n] = tempList[n];
		}
		String name = imgUrl.replace("http://chromemote.com/ads/", "").replace(".png", "");
//		toast(name);
		ads[objectsFoundCount] = new HTML("<a href=\"" + refUrl + "\" target=\"_blank\" onClick=\"_gaq.push(['_trackEvent', 'Ads', '" + name + "']);\"><img src=\"" + imgUrl + "\"></a>");
		adCountInt++;
		


	}

	private String stripHTML(String valueString)
	{
		return valueString.replaceAll("\\<.*?>","");
	}
	public static native void sendGAEvent(String category, String action) /*-{
	
		$wnd.sendGAEvent(category, action);

	}-*/;
	
	
}
