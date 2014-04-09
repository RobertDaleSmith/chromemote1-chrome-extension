package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.ui.PushButton;

public class DeviceMenuScrollButton extends PushButton{
	
	String deviceName;
	String deviceIPAddress;
	String keyCode;
	Boolean newDevice;
	int rowIndex;
	int btnHeightInt = 49;		//Actually is 60px with 1px top border set in CSS.
	int btnWidthInt = 320;		//Offset from 320 for avoiding scroll bars.
	int maxNameLength = 30;
	
	public DeviceMenuScrollButton(String nameString, String ipAddressString, int rowIndexInt, Boolean isNewDevice)
	{
		//Store inputs to this class.
		super(ipAddressString);
		
		//keyCode  = bKeyCodeString;
		deviceName = nameString;
		deviceIPAddress = ipAddressString;
		rowIndex = rowIndexInt;
		newDevice = isNewDevice;
		
		//Limit the device name length from pushing over the UI standard button height.
		String upToNCharacters = nameString.substring(0, Math.min(nameString.length(), 32));
		
		if (upToNCharacters != nameString)
		{
			upToNCharacters = nameString.substring(0, Math.min(nameString.length(), 29));
			nameString = upToNCharacters + "...";
		}
		
		if(nameString.length() > maxNameLength)
			nameString = nameString.substring(0, maxNameLength-1) + "...";
		
		if(isNewDevice)
		{
			this.setHTML("<table cellspacing=0 cellpadding=0>" +
				       "<tr>" +
				     	"<td width=60 height=49 vertical-align=middle align=center>" +
				     		"<img src=images/settingsPanels/hardwareTV.png></td><td width=270>" + nameString + 
				     		"<br><span style=font-size:11px;>" + ipAddressString + "</span>" +
				     	"</td>" +
				       "</tr>" +
				     "</table>");
			this.getDownFace().setHTML("<table cellspacing=0 cellpadding=0>" +
			           "<tr>" +
			     	    "<td width=60 height=49 vertical-align=middle align=center>" +
			     		    "<img src=images/settingsPanels/hardwareTV_touched.png></td><td width=270>" + nameString + 
			     		    "<br><span style=font-size:11px;>" + ipAddressString + "</span>" +
				     	"</td>" +
				       "</tr>" +
				     "</table>");
		}
		else
		{
			this.setHTML("<table cellspacing=0 cellpadding=0>" +
				       "<tr>" +
				     	"<td width=60 height=49 vertical-align=middle align=center>" +
				     		"<img src=images/settingsPanels/hardwareTV.png></td><td width=270>" + nameString + 
				     		"<br><span style=font-size:11px;>" + ipAddressString + "</span>" +
				     	"</td>" +
				       "</tr>" +
				     "</table>");
			this.getDownFace().setHTML("<table cellspacing=0 cellpadding=0>" +
			           "<tr>" +
			     	    "<td width=60 height=49 vertical-align=middle align=center>" +
			     		    "<img src=images/settingsPanels/hardwareTV_touched.png></td><td width=270>" + nameString + 
			     		    "<br><span style=font-size:11px;>" + ipAddressString + "</span>" +
				     	"</td>" +
				       "</tr>" +
				     "</table>");
		}
		
		
		
				
		//Set default size and style.
		this.setSize(btnWidthInt + "px", btnHeightInt + "px");
		this.setStyleName("gtvRemote-scrollDeviceMenuPushButton");
//		if (rowIndexInt == 0)
//			this.addStyleName("gtvRemote-scrollDeviceMenuPushButton-border0");
//		else
			this.addStyleName("gtvRemote-scrollDeviceMenuPushButton-border1");
		
	}
	
	public String getKeyCode()
	{
		return keyCode;
	}
	
	public String getDeviceName()
	{
		return deviceName;
	}
	
	public String getDeviceIP()
	{
		return deviceIPAddress;
	}
		
	public int getRowIndex()
	{
		return rowIndex;
	}

	public Boolean isNewDevice()
	{
		return newDevice;
	}
	
	public void setKeyCode(String keyCode)
	{
		this.keyCode = keyCode;
	}
		
	public void setDeviceName(String nameString)
	{
		this.deviceName = nameString;
		
		if(nameString.length() > maxNameLength)
			nameString = nameString.substring(0, maxNameLength-1) + "...";
		
		this.setHTML("<table cellspacing=0 cellpadding=0>" +
			       "<tr>" +
			     	"<td width=60 height=49 vertical-align=middle align=center>" +
			     		"<img src=images/settingsPanels/hardwareTV.png></td><td width=270>" + nameString + 
			     		"<br><span style=font-size:11px;>" + deviceIPAddress + "</span>" +
			     	"</td>" +
			       "</tr>" +
			     "</table>");
		this.getDownFace().setHTML("<table cellspacing=0 cellpadding=0>" +
		           "<tr>" +
		     	    "<td width=60 height=49 vertical-align=middle align=center>" +
		     		    "<img src=images/settingsPanels/hardwareTV_touched.png></td><td width=270>" + nameString + 
		     		    "<br><span style=font-size:11px;>" + deviceIPAddress + "</span>" +
			     	"</td>" +
			       "</tr>" +
			     "</table>");
		
	}
	
	



}
