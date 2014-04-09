package com.chromemote.crx.client.ui;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

public class RemoteButton extends PushButton {
	
	private int    	widthInt    = 80;		//Default remote button width.
	private int    	heightInt   = 60;		//Default remote button height.
	private int		cellIndex;
	private String 	buttonName,				//Primary button text. (middle/center)
				   	subText     = "",		//Secondary button text. (top/left)
				   	keyCode		= "";
				   	
	private String  upFaceImg   = null,		//Icon image for remote button.
				   	downFaceImg = null;		//Mouse down icon image.
	
	private Boolean hasIconImage, 			//Marker for checking if icon is set.
					hasSubText,				//Marker for checking if sub text is set.
					iconIsEnabled,			//Tracks whether icon is enabled or not.
					buttonIsWide = false;
	
	public RemoteButton(String nameString, String btnKeyCode, int cellIndexInt)
	{
		super(nameString);
		buttonName = nameString;
		keyCode    = btnKeyCode;
		cellIndex  = cellIndexInt;
		
		
		this.getElement().setId(btnKeyCode);
		
		this.setHTML("<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + nameString);
		setStyleIndex(cellIndex);
		
		this.setSize(widthInt + "px", heightInt + "px");
		
		hasIconImage = false;	iconIsEnabled= false;
		hasSubText   = false;
	}
	
	public RemoteButton(String nameString, String btnKeyCode, int cellIndexInt, String subTextStr)
	{
		super(nameString);
		buttonName = nameString;
		keyCode = btnKeyCode;
		subText    = subTextStr;
		cellIndex  = cellIndexInt;
		
		
		this.getElement().setId(btnKeyCode);

		this.setHTML("<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + nameString);
		setStyleIndex(cellIndex);
		
		this.setSize(widthInt + "px", heightInt + "px");

		hasIconImage = false;	iconIsEnabled= false;
		hasSubText   = true;
	}
	
	public RemoteButton(String nameString, String btnKeyCode, int cellIndexInt, String iconImage, String iconImageTouched)
	{
		super(nameString);
		buttonName = nameString;
		keyCode = btnKeyCode;
		cellIndex  = cellIndexInt;
		
		
		this.getElement().setId(btnKeyCode);
		
		setStyleIndex(cellIndex);
		
		this.setSize(widthInt + "px", heightInt + "px");
		
		upFaceImg   = iconImage;
		downFaceImg = iconImageTouched;
		
		this.getUpFace().setImage( new Image(upFaceImg) );
		this.getDownFace().setImage( new Image(downFaceImg) );
		hasIconImage = true;	iconIsEnabled= true;
		hasSubText   = false;
	}
	
	public RemoteButton(int cellIndexInt)		//Used for blank dummy button.
	{
		super();
		cellIndex  = cellIndexInt;
		
		setStyleIndex(cellIndex);
		
		this.setSize(widthInt + "px", heightInt + "px");
		
		hasIconImage = false;	iconIsEnabled= false;
		hasSubText   = false;
	}
	
	public void setStyleIndex(int cellIndexInt)
	{
		cellIndex  = cellIndexInt;
		this.setStyleName("chromemote-PushButton" + cellIndex);
		this.addStyleName("chromemote-PushButton");
	}
	
	public void setAltStyle(Boolean enabled)
	{
		if (enabled)
		{
			this.setStyleName("chromemote-PushButton" + cellIndex + "-alt");
			this.addStyleName("chromemote-PushButton");
		}
		else
		{	
			this.setStyleName("chromemote-PushButton" + cellIndex);
			this.addStyleName("chromemote-PushButton");
		}
		
		
	}
	
	public void setName(String nameString)
	{
		buttonName = nameString;
		String tempHTML;
		if ( !isWide() ) {
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName;
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName;
		}
		else {
			if (cellIndex == 17 || cellIndex == 21) {
				tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + "\f \t \f \t \f \t" + buttonName;
			}
			else {
				tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName + "\f \t \f \t \f \t \f \t";
			}
		}
		
		this.getDownFace().setHTML( tempHTML );
		this.getUpFace().setHTML( tempHTML );
	}
	
	public String getName()
	{
		return buttonName;
	}
	
	public String getSubName()
	{
		return subText;
	}
	
	public String getKeyCode()
	{
		return keyCode;
	}
	
	public String getIconPath()
	{
		return upFaceImg;
	}
	
	public int getCellIndex()
	{
		return cellIndex;
	}
	
	public void setCellIndex(int index)
	{
		this.cellIndex = index;
	}
	
	public void setKeyCode(String btnKeyCode)
	{
		this.keyCode = btnKeyCode;
	}
			
	public void setSubText(String subTextStr)
	{
		subText = subTextStr;
		
		String tempHTML;
		if ( !isWide() ) {
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName;
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName;
		}
		else {
			if (cellIndex == 17 || cellIndex == 21) {
				tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + "\f \t \f \t \f \t" + buttonName;
			}
			else {
				tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName + "\f \t \f \t \f \t \f \t";
			}
		}
		
		this.getDownFace().setHTML( tempHTML );
		this.getUpFace().setHTML( tempHTML );
		hasSubText   = true;
	}
	
	public void removeSubText()
	{
		subText = "";
		if( !hasIconImage)
		{
			this.getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
			this.getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
		}
		hasSubText   = false;
	}
	
	public Boolean hasSubText()
	{
		return hasSubText;
	}
	
	public void setIconImage(String iconImage, String iconImageTouched)
	{
		upFaceImg   = iconImage;
		downFaceImg = iconImageTouched;
		
		this.getUpFace().setImage( new Image(upFaceImg) );
		this.getDownFace().setImage( new Image(downFaceImg) );
		hasIconImage = true;
		iconIsEnabled= true;
	}
	
	public void removeIconImage()
	{
		upFaceImg   = null;
		downFaceImg = null;
		
		this.getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
		this.getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
		hasIconImage = false;
		iconIsEnabled= false;
	}
	
	public Boolean hasIconImage()
	{
		return hasIconImage;
	}
	
	public Boolean iconImageEnabled()
	{
		return iconIsEnabled;
	}
	
	public void disableIconImage()
	{
		if (hasIconImage)
		{
			this.getDownFace().setHTML("<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
			this.getUpFace().setHTML(  "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName);
			
		}
		iconIsEnabled = false;
	}
	
	public void enableIconImage()
	{
		if (hasIconImage)
		{
			this.getUpFace().setImage( new Image(upFaceImg) );
			this.getDownFace().setImage( new Image(downFaceImg) );
			
		}
		iconIsEnabled = true;
	}
	
	public Boolean isWide()
	{
		return buttonIsWide;
	}
	
	public void makeWide()
	{
		buttonIsWide = true;
		
		widthInt = 160;
		this.setSize(widthInt + "px", heightInt + "px");
		
		this.setStyleName("chromemote-PushButton" + cellIndex + "-wide");
		this.addStyleName("chromemote-PushButton");
		
		String tempHTML;
		if (cellIndex == 17 || cellIndex == 21)
		{
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + "\f \t \f \t \f \t" + buttonName;
		}
		else
		{
			tempHTML = "<p class=\"chromemote-PushButton-alt\">" + subText + "</p><br>" + buttonName + "\f \t \f \t \f \t \f \t";
		}
		
		if ( !this.iconImageEnabled() )
		{
			this.getDownFace().setHTML( tempHTML );
			this.getUpFace().setHTML( tempHTML );
		}
		
	}


	
	
}
