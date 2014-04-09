package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.ui.PushButton;

public class SettingsMenuScrollButton extends PushButton{
	
	String settingsName;
	String settingsIcon;
	boolean hasOnOffButton = false;
	boolean onOffBtnIsOn = false;
	
	int btnHeightInt = 49;		//Actually is 60px with 1px top border set in CSS.
	int btnWidthInt = 320;		//Offset from 320 for avoiding scroll bars.
	
	public SettingsMenuScrollButton(String iconPathString, String labelString, boolean hasOnOffBtn)
	{
		//Store inputs to this class.
		super(labelString);
		
		settingsIcon = iconPathString;
		settingsName = labelString;
		hasOnOffButton = hasOnOffBtn;
		
		
		if(!hasOnOffButton)
		{
			this.getUpFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
				    	"<tr>" +
				     		"<td width=50 height=49 vertical-align=middle align=center>" +
				     			"<img src="+ settingsIcon +">" +
				     		"</td>" +
				     		"<td >" + 
				     			settingsName + 
				     		"</td>" +
				     	"</tr>" +
				    "</table>");
			this.getDownFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
				     	"<tr>" +
			     	    	"<td width=50 height=49 vertical-align=middle align=center>" +
			     	    		"<img src="+ settingsIcon.replaceFirst(".png","_touched.png") +">" +
			     	    	"</td>" +
			     	    	"<td >" +
			     	    		settingsName + 
			     	    	"</td>" +
					    "</tr>" +
				    "</table>");
		}
		else {
			this.getUpFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
				     	"<tr>" +
				     	"<td width=50 height=49 vertical-align=middle align=center>" +
				     		"<img src="+ settingsIcon +">" +
				     	"</td>" +
				     	"<td width=150>" + 
				     		settingsName + 
				     	"</td>" +
				     	"<td >" + 
			     			"<img src=\"images/settingsPanels/settings_menu_off_button.png\">" + 
			     		"</td>" +
				     "</tr>" +
				    "</table>");
			this.getDownFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
				     	"<tr>" +
			     	    "<td width=50 height=49 vertical-align=middle align=center>" +
			     	    	"<img src="+ settingsIcon.replaceFirst(".png","_touched.png") +">" +
			     	    "</td>" +
			     	    "<td width=150>" + 
			     	    	settingsName + 
					    "</td>" +
					    "<td >" + 
				     		"<img src=\"images/settingsPanels/settings_menu_on_button.png\">" + 
				     	"</td>" +
					    "</tr>" +
				     "</table>");
		}
		
		
		
		
				
		//Set default size and style.
		this.setSize(btnWidthInt + "px", btnHeightInt + "px");
		this.setStyleName("gtvRemote-scrollSettingsMenuPushButton");
//		if (rowIndexInt == 0)
//			this.addStyleName("gtvRemote-scrollSettingsMenuPushButton-border0");
//		else
			this.addStyleName("gtvRemote-scrollSettingsMenuPushButton-border1");
		
	}
	
	public String getSettingsIcon()
	{
		return settingsIcon;
	}
	
	public String getSettingsName()
	{
		return settingsName;
	}
		
	public void setSettingsIcon(String iconPathString)
	{
		settingsIcon = iconPathString;
	}
	
	public void setSettingsName(String labelString)
	{
		settingsName = labelString;
	}
		
	public boolean isOnOffBtnOn()
	{
		return onOffBtnIsOn;
	}

	public void toggleOnOff()
	{
		if (onOffBtnIsOn) {onOffBtnIsOn = false;}
		else 			  {onOffBtnIsOn = true; 
		}
		if(onOffBtnIsOn)
		{
			this.getUpFace().setHTML("" +
				"<table cellspacing=0 cellpadding=0>" +
					"<tr>" +
				   		"<td width=50 height=49 vertical-align=middle align=center>" +
				   			"<img src="+ settingsIcon +">" +
				   			"</td>" +
				   		"<td width=150>" + 
				   			settingsName + 
				   		"</td>" +
				   		"<td >" + 
				   			"<img src=\"images/settingsPanels/settings_menu_on_button.png\">" + 
				   		"</td>" +
				   	"</tr>" +
				"</table>");
			this.getDownFace().setHTML("" +
				"<table cellspacing=0 cellpadding=0>" +
				   	"<tr>" +
			        	"<td width=50 height=49 vertical-align=middle align=center>" +
			        		"<img src="+ settingsIcon.replaceFirst(".png","_touched.png") +">" +
			        	"</td>" +
			        	"<td width=150>" + 
			        		settingsName + 
			        	"</td>" +
			        	"<td >" + 
				   			"<img src=\"images/settingsPanels/settings_menu_off_button.png\">" + 
				   		"</td>" +
				    "</tr>" +
				"</table>");
		}
		else
		{
			this.getUpFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
						"<tr>" +
					   		"<td width=50 height=49 vertical-align=middle align=center>" +
					   			"<img src="+ settingsIcon +">" +
					   			"</td>" +
					   		"<td width=150>" + 
					   			settingsName + 
					   		"</td>" +
					   		"<td >" + 
					   			"<img src=\"images/settingsPanels/settings_menu_off_button.png\">" + 
					   		"</td>" +
					   	"</tr>" +
					"</table>");
				this.getDownFace().setHTML("" +
					"<table cellspacing=0 cellpadding=0>" +
					   	"<tr>" +
				        	"<td width=50 height=49 vertical-align=middle align=center>" +
				        		"<img src="+ settingsIcon.replaceFirst(".png","_touched.png") +">" +
				        	"</td>" +
				        	"<td width=150>" + 
				        		settingsName + 
				        	"</td>" +
				        	"<td >" + 
					   			"<img src=\"images/settingsPanels/settings_menu_on_button.png\">" + 
					   		"</td>" +
					    "</tr>" +
					"</table>");
		}
		
	}
	



}
