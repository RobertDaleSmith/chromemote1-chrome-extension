package com.chromemote.crx.client.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class RemoteButtonProperties extends JavaScriptObject {									// (1)
	
	// Overlay types always have protected, zero argument constructors.
	protected RemoteButtonProperties() {}                                              			// (2)
	
	// JSNI methods to get remote button properties.
	public final native int    getIndex() 	 /*-{ return this.index; 	}-*/; 					// (3)
	public final native String getName() 	 /*-{ return this.name; 	}-*/;
	public final native String getSubName()  /*-{ return this.subName; 	}-*/;
	public final native String getKeyCode()  /*-{ return this.keyCode; 	}-*/;
	public final native String getIconUp() 	 /*-{ return this.iconUp; 	}-*/;
	public final native String getIconDown() /*-{ return this.iconDown; }-*/;
	
	// Non-JSNI method to return change percentage.                       						// (4)
	public final Boolean hasIcon() {
		if( getIconUp().compareTo("") == 0 )
			return false;
		else
			return true;
	}
	
	public final Boolean hasSubName() {
		if( getSubName().compareTo("") == 0 )
			return false;
		else
			return true;
	}

}
