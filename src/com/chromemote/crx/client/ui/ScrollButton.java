package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.ui.PushButton;

public class ScrollButton extends PushButton{
	
	String buttonName;
	String boldName;
	String normName;
	String keyCode;
	int rowIndex;
	int btnHeightInt = 59;		//Actually is 60px with 1px top border set in CSS.
	int btnWidthInt = 300;		//Offset from 320 for avoiding scroll bars.
	
	public ScrollButton(String boldNameString, String normNameString, String bKeyCodeString, int rowIndexInt)
	{
		//Store inputs to this class.
		super(boldNameString + " " + normNameString);
		setName(boldNameString, normNameString);
		keyCode  = bKeyCodeString;
		rowIndex = rowIndexInt;
		
		//bKeyCodeString = bKeyCodeString.replaceAll("SP_", "");
		this.getElement().setId(bKeyCodeString);
		
		//Set default size and style.
		this.setSize(btnWidthInt + "px", btnHeightInt + "px");
		this.setStyleName("gtvRemote-scrollMenuPushButton");
		if (rowIndexInt == 1)
			this.addStyleName("gtvRemote-scrollMenuPushButton-border0");
		else
			this.addStyleName("gtvRemote-scrollMenuPushButton-border1");
		
	}
	
	public String getKeyCode()
	{
		return keyCode;
	}
	
	public String getName()
	{
		return buttonName;
	}
	
	public String getBoldName()
	{
		return boldName;
	}
	
	public String getNormName()
	{
		return normName;
	}
	
	public int getRowIndex()
	{
		return rowIndex;
	}
	
	public void setRowIndex(int rowIndexInt)
	{
		rowIndex = rowIndexInt;
		if (rowIndexInt == 1)
			this.addStyleName("gtvRemote-scrollMenuPushButton-border0");
		else
			this.addStyleName("gtvRemote-scrollMenuPushButton-border1");
	}
	
	public void setKeyCode(String keyCode)
	{
		this.keyCode = keyCode;
	}
	
	public void setName(String boldNameString, String normNameString)
	{
		buttonName = boldNameString + " " + normNameString;
		boldName = boldNameString;
		normName = normNameString;
		
		setName();
	}
	
	public void setBoldName(String boldNameString)
	{
		buttonName = boldNameString + " " + normName;
		boldName = boldNameString;
		
		setName();
	}
	
	public void setNormName(String normNameString)
	{
		buttonName = boldName + " " + normNameString;
		normName = normNameString;
		
		setName();
	}
	
	public void setName(String nameString)
	{
		buttonName = nameString;
		boldName = "";
		normName = nameString;
		
		setName();
	}
	
	private void setName()
	{
		//Set button HTML text.
		if (boldName.compareTo("") == 0)
			this.setHTML(normName);
		else
			this.setHTML("<b>" + boldName + "</b> " + normName);
		
		//Add color to text if colored button.
		if ( boldName.toLowerCase().compareTo("green")  == 0 )
			this.setHTML("<font color=\"#00f400\"><b>" + boldName + "</b></font> " + normName);
		if ( boldName.toLowerCase().compareTo("red")    == 0 )
			this.setHTML("<font color=\"#fe0000\"><b>" + boldName + "</b></font> " + normName);
		if ( boldName.toLowerCase().compareTo("yellow") == 0 )
			this.setHTML("<font color=\"#ffff00\"><b>" + boldName + "</b></font> " + normName);
		if ( boldName.toLowerCase().compareTo("blue")   == 0 )
			this.setHTML("<font color=\"#0600ff\"><b>" + boldName + "</b></font> " + normName);
	}
	



}
