/*
 * Copyright (C) 2012 Google Inc.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



if(chrome.extension.getURL("/").indexOf("bhcjclaangpnjgfllaoodflclpdfcegb") >= 0)
{
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-34201638-1']);
	_gaq.push(['_trackPageview']);
	(function() {
		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		ga.src = 'https://ssl.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
	console.log("__CWS_MODE__");
}
else
	console.log("__DEV_MODE__");


/**
 * Connects listeners to the buttons in the popup window.
 * Most buttons call functions that are organized into separate files.
 *   googletvremote_controller.js - Creates the plugin.
 *   discovery_client_controll.js - Finds Google TVs on the local network.
 *   pairing_session_controller.js - Pairs to a Google TV.
 *   anymote_session_controller.js - Communicates with Google TV after paired.
 */


/** Key used by localStorage to store ip addresses of paired Google TVs. */
STORAGE_KEY_PAIRED_DEVICES = 'paired-devices';

var cheatCode = "";

var backgroundPageWindow = chrome.extension.getBackgroundPage();

var tabOrPopUp = setUiType();

var setStatusBarText = function(msg){
	document.getElementById('menuBarText').innerHTML = msg;
}

var toggleBGImage = function(){
	
	if(tabOrPopUp == 'tab')
	{
		if(document.getElementById('page_body').className == "bg1")
		{
			document.getElementById('page_body').className="bg2";
			document.getElementById('logo_container').className="logo_light_bg";
			backgroundPageWindow.localStorage.setItem('lights-out', false);
		}
		else
		{
			document.getElementById('page_body').className="bg1";
			document.getElementById('logo_container').className="logo_dark_bg";
			backgroundPageWindow.localStorage.setItem('lights-out', true);
		}
	}
	else
	{
		if(backgroundPageWindow.localStorage.getItem('lights-out') == 'true')
			backgroundPageWindow.localStorage.setItem('lights-out', false);
		else
			backgroundPageWindow.localStorage.setItem('lights-out', true);
		
	}
	
}

var openOptionsPage = function(){

	var isTab = "false";

	chrome.tabs.getAllInWindow(undefined, function(tabs) {
		for (var i = 0, tab; tab = tabs[i]; i++) {
			isTab = "false";
			
			if(tab.url == chrome.extension.getURL('tab.html') ){ isTab = "true"; }
			
				if (tab.url && isTab == "true") {
					if(tabOrPopUp == 'tab')
				{
					chrome.tabs.update(tab.id, {'pinned': !tab.pinned, selected: true});
					backgroundPageWindow.console.log("Full Tab Mode was detected. Toggle tabs pinned state.");
					isTab = "true";
					break;
					}
					else
					{
						chrome.tabs.update(tab.id, {selected: true});
					backgroundPageWindow.console.log("Full Tab Mode was detected. Selected Full Mode Tab.");
					isTab = "true";
					break;
					}
				}	
		}
		if(isTab == "false") {
			chrome.tabs.create( {
				index: 0,
				url: chrome.extension.getURL('tab.html'),
				pinned: true
			} );
			backgroundPageWindow.console.log("Full Tab Mode was not detected. Enabling Full Tab Mode.");
		}
	});

}

var setBGImage = function(){
	
	var lightsOutEnabled = backgroundPageWindow.localStorage.getItem('lights-out');
	
	if(tabOrPopUp == 'tab')
	{
		if(lightsOutEnabled == 'false')
		{
			document.getElementById('page_body').className="bg2";
			document.getElementById('logo_container').className="logo_light_bg";
		}
		else if(lightsOutEnabled == 'true')
		{
			document.getElementById('page_body').className="bg1";
			document.getElementById('logo_container').className="logo_dark_bg";
			
		}
		else
		{
			document.getElementById('page_body').className="bg1";
			document.getElementById('logo_container').className="logo_dark_bg";
			backgroundPageWindow.localStorage.setItem('lights-out', true);
		}
	}

}


backgroundPageWindow.getLatestAdsJson();

/** Register listeners for the button elements. */
window.onload = function() {
	
	//document.getElementById('toggle_lights').onclick=toggleBGImage;
	
	if(tabOrPopUp == 'tab')
		setBGImage();
	
	
	if(tabOrPopUp == 'tab')
		backgroundPageWindow.console.log('Chromemote Tab opened.');
	else
		backgroundPageWindow.console.log('Chromemote Pop-up opened.');
	
	//tabOrPopUp = setUiType();
	
	setTimeout(function()
	{
		
		if(!backgroundPageWindow.anymoteSessionActive)
		{
			openConnectMan();
			startDiscoveryClient();
			setIndicatorDisconnected();
			
			setDiscoveryLabel('Discovering...');
		}
		else
		{
			enableKeyBoardEvents();
			setIndicatorConnected();
		}
	}, 500);
  
	
	
	googletvremoteInitializePlugin();
	
	checkIfConnectionIsActive();
	
	
	
	
	
	if (backgroundPageWindow.gTvPluginLoaded == false)
		setDiscoveryLabel('Plug-in Not Detected');
	
	
};

window.onunload = function() {
	if(tabOrPopUp == 'tab')
		backgroundPageWindow.console.log('Chromemote Tab closed.');
	else
		backgroundPageWindow.console.log('Chromemote Pop-up closed.');
	stopDiscoveryClient();
	cancelChallengeResponse();
};

window.onerror = function() {
	backgroundPageWindow.console.log('JavaScript error found.');
	sendGAEvent("Error", "JavaScript");
	try {
		backgroundPageWindow.console.log('Checking if embed object has crashed.');
		anymoteSession.sendPing();
	}
	catch(e) {
		 //catch and just suppress error
		sendGAEvent("Error", "JavaScript", "Message", e.message);
		backgroundPageWindow.console.log('GTV plumbing embed plugin may have crashed. Error message was: ' + e.message);
		backgroundPageWindow.console.log('Restarting GTV plumbing.');
		backgroundPageWindow.googletvremoteInitializePlugin();
		googletvremoteInitializePlugin();
		backgroundPageWindow.anymoteSessionActive = false;
		anymoteConnectToExistingDevice();
	}
	
};

var sendKeyEvent = function(keyCode, keyDown) {
	
	if(backgroundPageWindow.anymoteSessionActive)
	{
		switch (keyCode)
		{
			case 'SOFT_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SOFT_LEFT, keyDown);
				break;
			case 'SOFT_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SOFT_RIGHT, keyDown);
				break;
			case 'HOME': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.HOME, keyDown);
				break;
			case 'BACK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BACK, keyDown);
				break;
			case 'CALL': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CALL, keyDown);
				break;
			case 'NUM0': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM0, keyDown);
				break;
			case 'NUM1': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM1, keyDown);
				break;
			case 'NUM2': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM2, keyDown);
				break;
			case 'NUM3': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM3, keyDown);
				break;
			case 'NUM4': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM4, keyDown);
				break;
			case 'NUM5': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM5, keyDown);
				break;
			case 'NUM6': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM6, keyDown);
				break;
			case 'NUM7': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM7, keyDown);
				break;
			case 'NUM8': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM8, keyDown);
				break;
			case 'NUM9': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM9, keyDown);
				break;
			case 'STAR': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.STAR, keyDown);
				break;
			case 'POUND': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.POUND, keyDown);
				break;
			case 'DPAD_UP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DPAD_UP, keyDown);
				break;
			case 'DPAD_DOWN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DPAD_DOWN, keyDown);
				break;
			case 'DPAD_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DPAD_LEFT, keyDown);
				break;
			case 'DPAD_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DPAD_RIGHT, keyDown);
				break;
			case 'DPAD_CENTER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DPAD_CENTER, keyDown);
				break;
			case 'VOLUME_UP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.VOLUME_UP, keyDown);
				break;
			case 'VOLUME_DOWN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.VOLUME_DOWN, keyDown);
				break;
			case 'POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.POWER, keyDown);
				break;
			case 'CAMERA': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CAMERA, keyDown);
				break;
			case 'A': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.A, keyDown);
				break;
			case 'B': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.B, keyDown);
				break;
			case 'C': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.C, keyDown);
				break;
			case 'D': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.D, keyDown);
				break;
			case 'E': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.E, keyDown);
				break;
			case 'F': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F, keyDown);
				break;
			case 'G': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.G, keyDown);
				break;
			case 'H': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.H, keyDown);
				break;
			case 'I': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.I, keyDown);
				break;
			case 'J': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.J, keyDown);
				break;
			case 'K': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.K, keyDown);
				break;
			case 'L': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.L, keyDown);
				break;
			case 'M': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.M, keyDown);
				break;
			case 'N': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.N, keyDown);
				break;
			case 'O': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.O, keyDown);
				break;
			case 'P': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.P, keyDown);
				break;
			case 'Q': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.Q, keyDown);
				break;
			case 'R': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.R, keyDown);
				break;
			case 'S': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.S, keyDown);
				break;
			case 'T': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.T, keyDown);
				break;
			case 'U': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.U, keyDown);
				break;
			case 'V': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.V, keyDown);
				break;
			case 'W': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.W, keyDown);
				break;
			case 'X': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.X, keyDown);
				break;
			case 'Y': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.Y, keyDown);
				break;
			case 'Z': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.Z, keyDown);
				break;
			case 'COMMA': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.COMMA, keyDown);
				break;
			case 'PERIOD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PERIOD, keyDown);
				break;
			case 'ALT_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ALT_LEFT, keyDown);
				break;
			case 'ALT_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ALT_RIGHT, keyDown);
				break;
			case 'SHIFT_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SHIFT_LEFT, keyDown);
				break;
			case 'SHIFT_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SHIFT_RIGHT, keyDown);
				break;
			case 'TAB': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.TAB, keyDown);
				break;
			case 'SPACE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SPACE, keyDown);
				break;
			case 'EXPLORER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.EXPLORER, keyDown);
				break;
			case 'ENTER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ENTER, keyDown);
				break;
			case 'DEL': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DEL, keyDown);
				break;
			case 'GRAVE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.GRAVE, keyDown);
				break;
			case 'MINUS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MINUS, keyDown);
				break;
			case 'EQUALS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.EQUALS, keyDown);
				break;
			case 'LEFT_BRACKET': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.LEFT_BRACKET, keyDown);
				break;
			case 'RIGHT_BRACKET': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.RIGHT_BRACKET, keyDown);
				break;
			case 'BACKSLASH': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BACKSLASH, keyDown);
				break;
			case 'SEMICOLON': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SEMICOLON, keyDown);
				break;
			case 'APOSTROPHE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.APOSTROPHE, keyDown);
				break;
			case 'SLASH': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SLASH, keyDown);
				break;
			case 'AT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.AT, keyDown);
				break;
			case 'FOCUS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.FOCUS, keyDown);
				break;
			case 'PLUS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PLUS, keyDown);
				break;
			case 'MENU': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MENU, keyDown);
				break;
			case 'SEARCH': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SEARCH, keyDown);
				break;
			case 'MEDIA_PLAY_PAUSE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_PLAY_PAUSE, keyDown);
				break;
			case 'MEDIA_STOP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_STOP, keyDown);
				break;
			case 'MEDIA_NEXT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_NEXT, keyDown);
				break;
			case 'MEDIA_PREVIOUS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_PREVIOUS, keyDown);
				break;
			case 'MEDIA_REWIND': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_REWIND, keyDown);
				break;
			case 'MEDIA_FAST_FORWARD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_FAST_FORWARD, keyDown);
				break;
			case 'MUTE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MUTE, keyDown);
				break;
			case 'CTRL_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CTRL_LEFT, keyDown);
				break;
			case 'CTRL_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CTRL_RIGHT, keyDown);
				break;
			case 'INSERT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.INSERT, keyDown);
				break;
			case 'PAUSE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PAUSE, keyDown);
				break;
			case 'PAGE_UP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PAGE_UP, keyDown);
				break;
			case 'PAGE_DOWN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PAGE_DOWN, keyDown);
				break;
			case 'PRINT_SCREEN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PRINT_SCREEN, keyDown);
				break;
			case 'INFO': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.INFO, keyDown);
				break;
			case 'WINDOW': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.WINDOW, keyDown);
				break;
			case 'BOOKMARK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BOOKMARK, keyDown);
				break;
			case 'CAPS_LOCK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CAPS_LOCK, keyDown);
				break;
			case 'ESCAPE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ESCAPE, keyDown);
				break;
			case 'META_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.META_LEFT, keyDown);
				break;
			case 'ZOOM_IN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ZOOM_IN, keyDown);
				break;
			case 'ZOOM_OUT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.ZOOM_OUT, keyDown);
				break;	
			case 'CHANNEL_UP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CHANNEL_UP, keyDown);
				break;
			case 'CHANNEL_DOWN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.CHANNEL_DOWN, keyDown);
				break;
			case 'LIVE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.LIVE, keyDown);
				break;
			case 'DVR': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.DVR, keyDown);
				break;
			case 'GUIDE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.GUIDE, keyDown);
				break;
			case 'MEDIA_SKIP_BACK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_SKIP_BACK, keyDown);
				break;
			case 'MEDIA_SKIP_FORWARD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_SKIP_FORWARD, keyDown);
				break;
			case 'MEDIA_RECORD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_RECORD, keyDown);
				break;
			case 'MEDIA_PLAY': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_PLAY, keyDown);
				break;
			case 'PROG_RED': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PROG_RED, keyDown);
				break;
			case 'PROG_GREEN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PROG_GREEN, keyDown);
				break;
			case 'PROG_YELLOW': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PROG_YELLOW, keyDown);
				break;
			case 'PROG_BLUE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PROG_BLUE, keyDown);
				break;
			case 'BD_POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BD_POWER, keyDown);
				break;
			case 'BD_INPUT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BD_INPUT, keyDown);
				break;
			case 'STB_POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.STB_POWER, keyDown);
				break;
			case 'STB_INPUT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.STB_INPUT, keyDown);
				break;
			case 'STB_MENU': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.STB_MENU, keyDown);
				break;
			case 'TV_POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.TV_POWER, keyDown);
				break;
			case 'TV_INPUT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.TV_INPUT, keyDown);
				break;
			case 'AVR_POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.AVR_POWER, keyDown);
				break;
			case 'AVR_INPUT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.AVR_INPUT, keyDown);
				break;
			case 'AUDIO': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.AUDIO, keyDown);
				break;
			case 'EJECT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.EJECT, keyDown);
				break;
			case 'BD_POPUP_MENU': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BD_POPUP_MENU, keyDown);
				break;
			case 'BD_TOP_MENU': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BD_TOP_MENU, keyDown);
				break;
			case 'SETTINGS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SETTINGS, keyDown);
				break;
			case 'SETUP': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SETUP, keyDown);
				break;
			case 'ALL_POWER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.TV_POWER, keyDown);
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.AVR_POWER, keyDown);
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.STB_POWER, keyDown);
				break;		

			case 'BTN_FORWARD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BTN_FORWARD, keyDown);
				break;
			case 'BTN_BACK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BTN_BACK, keyDown);
				break;
			case 'META_RIGHT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.META_RIGHT, keyDown);
				break;
			case 'META_LEFT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.META_LEFT, keyDown);
				break;
			
			case 'CTRL_ALT_DEL': 
				//backgroundPageWindow.sendCtrlAltDelAnymoteKeyEvent();
				//sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.POWER);
				sendAnymoteFling('intent:#Intent;' +
						  'action=android.intent.action/.MAIN;' +
						  'category=android.intent.category/.REBOOT;' + 
						  'end');
				break;		
				
			
				
				
			case 'PICTSYMBOLS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.PICTSYMBOLS, keyDown);
				break;	
			case 'SWITCH_CHARSET': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SWITCH_CHARSET, keyDown);
				break;	
			case 'FORWARD_DEL': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.FORWARD_DEL, keyDown);
				break;	
			case 'SCROLL_LOCK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SCROLL_LOCK, keyDown);
				break;	
			case 'FUNCTION': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.FUNCTION, keyDown);
				break;	
			case 'SYSRQ': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SYSRQ, keyDown);
				break;	
			case 'BREAK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BREAK, keyDown);
				break;	
			case 'MOVE_HOME': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MOVE_HOME, keyDown);
				break;	
			case 'MOVE_END': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MOVE_END, keyDown);
				break;	
			case 'FORWARD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.FORWARD, keyDown);
				break;	
			case 'MEDIA_CLOSE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.MEDIA_CLOSE, keyDown);
				break;	
			case 'F1': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F1, keyDown);
				break;	
			case 'F2': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F2, keyDown);
				break;	
			case 'F3': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F3, keyDown);
				break;	
			case 'F4': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F4, keyDown);
				break;	
			case 'F5': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F5, keyDown);
				break;	
			case 'F6': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F6, keyDown);
				break;	
			case 'F7': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F7, keyDown);
				break;	
			case 'F8': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F8, keyDown);
				break;	
			case 'F9': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F9, keyDown);
				break;	
			case 'F10': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F10, keyDown);
				break;	
			case 'F11': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F11, keyDown);
				break;	
			case 'F12': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.F12, keyDown);
				break;	
			case 'NUM_LOCK': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUM_LOCK, keyDown);
				break;	
			case 'NUMPAD_0': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_0, keyDown);
				break;	
			case 'NUMPAD_1': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_1, keyDown);
				break;	
			case 'NUMPAD_2': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_2, keyDown);
				break;	
			case 'NUMPAD_3': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_3, keyDown);
				break;	
			case 'NUMPAD_4': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_4, keyDown);
				break;	
			case 'NUMPAD_5': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_5, keyDown);
				break;	
			case 'NUMPAD_6': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_6, keyDown);
				break;	
			case 'NUMPAD_7': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_7, keyDown);
				break;	
			case 'NUMPAD_8': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_8, keyDown);
				break;	
			case 'NUMPAD_9': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_9, keyDown);
				break;	
			case 'NUMPAD_DIVIDE': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_DIVIDE, keyDown);
				break;	
			case 'NUMPAD_MULTIPLY': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_MULTIPLY, keyDown);
				break;	
			case 'NUMPAD_SUBTRACT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_SUBTRACT, keyDown);
				break;	
			case 'NUMPAD_ADD': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_ADD, keyDown);
				break;	
			case 'NUMPAD_DOT': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_DOT, keyDown);
				break;	
			case 'NUMPAD_COMMA': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_COMMA, keyDown);
				break;	
			case 'NUMPAD_ENTER': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_ENTER, keyDown);
				break;	
			case 'NUMPAD_EQUALS': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_EQUALS, keyDown);
				break;	
			case 'NUMPAD_LEFT_PAREN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_LEFT_PAREN, keyDown);
				break;	
			case 'NUMPAD_RIGHT_PAREN': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.NUMPAD_RIGHT_PAREN, keyDown);
				break;	
			case 'APP_SWITCH"': 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.APP_SWITCH, keyDown);
				break;	
			
		}
		
		if( keyDown )
		{
			switch ( keyCode )
			{			
				case 'FLING_TAB': 
					if(tabOrPopUp == 'tab')
						chrome.tabs.get(backgroundPageWindow.previousTab, function(tab) {sendAnymoteFling(tab.url);});
					else
						chrome.tabs.getSelected(null, function(tab) {sendAnymoteFling(tab.url);});
					break;
				case 'APP_CHROME':
					sendAnymoteFling('chrome://');
					break;	
				case 'APP_CLOCK': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.android.deskclock/.DeskClock;' + 'end');
					break;	
				case 'APP_CNBC': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.nbc.cnbc.android.googletv/.ui.Splash;' + 'end');
					break;	
				case 'APP_DOWNLOADS': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.android.providers.downloads.ui/.DownloadList;' + 'end');
					break;	
				case 'APP_NBA': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.nbadigital.gametimegtv/.ActivityManager;' + 'end');
					break;	
				case 'APP_NETFLIX': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.tv.netflix/.NetflixActivity;' + 'end');
					break;	
				case 'APP_ONLIVE': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.onlive.clientgtv/.OnLiveClientGTVActivity;' + 'end');
					break;	
				case 'APP_PANDORA': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.pandora.android.gtv/com.pandora.android.Main;' + 'end');
					break;	
				case 'APP_PICTURES': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.tv.mediabrowser/.newui.MainActivity;' + 'end');
					break;	
				case 'APP_PLAYMOVIES': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.android.videos/com.google.android.youtube.videos.EntryPoint;' + 'end');
					break;	
				case 'APP_PLAYMUSIC': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.android.music/.activitymanagement.TopLevelActivity;' + 'end');
					break;	
				case 'APP_PLAYSTORE': 
					sendAnymoteFling('market://search?id=' + '');
					break;					
				case 'APP_PODCASTS': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.android.apps.listen/.WelcomeActivity;' + 'end');
					break;	
				case 'APP_PRIMETIME': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.tv.alf/.ui.MainActivity;' + 'end');
					break;	
				case 'APP_SEARCH': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.android.quicksearchbox/.SearchActivity;' + 'end');
					break;				
				case 'APP_SETTINGS': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.tv.settings/.Settings;' + 'end');
					break;					
				case 'APP_SPOTLIGHT': 
					sendAnymoteFling('http://www.google.com/tv/spotlight-gallery.html');
					break;	
				case 'APP_TV': 
					sendAnymoteFling('tv://');
					break;	
				case 'APP_TWITTER': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.twitter.android.tv/com.twitter.android.LoginActivity;' + 'end');
					break;	
				case 'APP_YOUTUBE': 
					sendAnymoteFling('intent:#Intent;' + 'component=' + 'com.google.android.youtube.googletv/.MainActivity;' + 'end');
					break;	
	//			case 'APP_NETFLIX': 
	//				sendAnymoteFling('nflx://');
	//				break;	
	//			case 'APP_CHROME_BLANK': 	//loads chrome with blank page
	//				sendAnymoteFling('intent:#Intent;' + 'component=com.google.tv.chrome/.HubActivity;' + 'end');
	//				break;	
	//			case 'APP_TEST': 
	//				sendAnymoteFling('chrome://www.google.com/');
	//				break;				
			}
		}
		sendGAEvent("KeyCode", keyCode);
	}
	else
	{
		backgroundPageWindow.console.log('Remote Keycode not sent because no anymote session is active.');
		displayToast("No Google TV's are connected.");
	}
	indicatorFlash();
};


var sendTpadMouseMoveEvent = function(xDelta,yDelta) {
	
	if(backgroundPageWindow.anymoteSessionActive)
	{
		sendAnymoteMouseMove(xDelta,yDelta);
		
	}
	else
	{
		backgroundPageWindow.console.log('Mouse cursor movement not sent because no anymote session is active.');
		displayToast("No Google TV's are connected.");
	}
	indicatorFlash();
};

var sendTpadMouseWheelEvent = function(xDelta,yDelta) {
	if(backgroundPageWindow.anymoteSessionActive)
	{
		sendAnymoteMouseWheel(xDelta,yDelta);
		
	}
	else
	{
		backgroundPageWindow.console.log('Mouse scroll movement not sent because no anymote session is active.');
		displayToast("No Google TV's are connected.");
	}
	indicatorFlash();
};

var sendTpadKeyEvent = function(button, keyDown) {
	
	if(backgroundPageWindow.anymoteSessionActive)
	{
		switch (button)
		{
			case 1: 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BTN_LEFT, keyDown);
				break;
			case 2: 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BTN_RIGHT, keyDown);
				break;
			case 3: 
				sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.BTN_MIDDLE, keyDown);
				break;	
		}
		
	}
	else
	{
		backgroundPageWindow.console.log('Touchpad click not sent because no anymote session is active.');
		displayToast("No Google TV's are connected.");
	}
	sendGAEvent("KeyCode", "BTN_LEFT");
	indicatorFlash();
};

var enableKeyBoardEvents = function() {
	
	document.body.addEventListener('keydown', keyBoardEvents,false);
	backgroundPageWindow.console.log('Enabled keyboard mode.');
	showKeyboardIcon();
	
};

var disableKeyBoardEvents = function() {
	
	document.body.removeEventListener('keydown', keyBoardEvents, false);
	backgroundPageWindow.console.log('Disabled keyboard mode.');
	hideKeyboardIcon();
};

var keyBoardEvents = function(e) {
	
	if(backgroundPageWindow.anymoteSessionActive)
	{	
		var keyCodeSent = false;
	    switch (e.keyCode) {
	    case 36:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.HOME);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 46:   //Delete key is made to act as Back commmand.
	  	  	//sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.BACK); //User  about this but I like it. :( Custom keys later. :)
	    	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DEL);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 48:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM0);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 49:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM1);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 50:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM2);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 51:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM3);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 52:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM4);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 53:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM5);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 54:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM6);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 55:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM7);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 56:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM8);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 57:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.NUM9);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 106:
	    	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.STAR);
	    	keyCodeSent = true;
	  	  	break;
	    case 37:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DPAD_LEFT);
	        keyCodeSent = true;
	  	  	break;
	    case 38:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DPAD_UP);
	        keyCodeSent = true;
	  	  	break;
	    case 39:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DPAD_RIGHT);
	        keyCodeSent = true;
	  	  	break;
	    case 40:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DPAD_DOWN);
	        keyCodeSent = true;
	  	  	break;
	    case 123:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.VOLUME_UP);
	        keyCodeSent = true;
	  	  	break;
	    case 122:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.VOLUME_DOWN);
	        keyCodeSent = true;
	  	  	break;
	    case 65:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.A);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 66:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.B);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 67:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.C);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 68:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.D);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 69:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.E);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 70:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.F);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 71:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.G);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 72:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.H);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 73:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.I);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 74:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.J);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 75:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.K);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 76:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.L);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 77:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.M);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 78:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.N);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 79:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.O);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 80:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.P);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 81:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.Q);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 82:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.R);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 83:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.S);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 84:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.T);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 85:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.U);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 86:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.V);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 87:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.W);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 88:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.X);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 89:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.Y);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 90:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.Z);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 188:
	    	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.COMMA);
	    	keyCodeSent = true;
	  	  	break;
	    case 190:
	  	  	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PERIOD);
	  	  	keyCodeSent = true;
	  	  	break;
	    case 9:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.TAB);
	        keyCodeSent = true;
	  	  	break;
	    case 32:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SPACE);
	        keyCodeSent = true;
	  	  	break;    
	    case 13:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.ENTER);
	        keyCodeSent = true;
	  	  	break; 
	    case 8:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.DEL);
	        keyCodeSent = true;
	  	  	break; 
	    case 192:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.GRAVE);
	        keyCodeSent = true;
	  	  	break; 
	    case 189:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MINUS);
	        keyCodeSent = true;
	  	  	break; 
	    case 186:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SEMICOLON);
	        keyCodeSent = true;
	  	  	break; 
	    case 187:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.EQUALS);
	        keyCodeSent = true;
	  	  	break; 
	    case 219:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.LEFT_BRACKET);
	        keyCodeSent = true;
	  	  	break;  
	    case 221:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.RIGHT_BRACKET);
	        keyCodeSent = true;
	  	  	break;  
	    case 220:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.BACKSLASH);
	        keyCodeSent = true;
	  	  	break;    
	    case 222:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.APOSTROPHE);
	        keyCodeSent = true;
	  	  	break;    
	    case 191:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SLASH);
	        keyCodeSent = true;
	  	  	break;    
	    case 107:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PLUS);
	        keyCodeSent = true;
	  	  	break;    
	    case 35:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SEARCH);
	        keyCodeSent = true;
	  	  	break;   
	    case 119:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MEDIA_PLAY);
	        keyCodeSent = true;
	  	  	break;    
	    case 120:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MEDIA_NEXT);
	        keyCodeSent = true;
	  	  	break;    
	    case 118:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MEDIA_PREVIOUS);
	        keyCodeSent = true;
	  	  	break; 
	    case 121:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MUTE);
	        keyCodeSent = true;
	  	  	break;
	    case 16:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SHIFT_LEFT);
	        keyCodeSent = true;
	  	  	break;
	    case 17:
	    	//sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.SEARCH);
	    	sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.CTRL_LEFT);
	        keyCodeSent = true;
	  	  	break;
	    case 18:
	        //sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MENU);
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.ALT_LEFT);
	        keyCodeSent = true;
	  	  	break;
	    case 45:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.INSERT);
	        keyCodeSent = true;
	  	  	break;
	    case 19:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PAUSE);
	        keyCodeSent = true;
	  	  	break;  
	    case 33:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PAGE_UP);
	        keyCodeSent = true;
	  	  	break;   
	    case 34:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PAGE_DOWN);
	        keyCodeSent = true;
	  	  	break;     
	    case 124:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.PRINT_SCREEN);
	        keyCodeSent = true;
	  	  	break;
	    case 20:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.CAPS_LOCK);
	        keyCodeSent = true;
	  	  	break;   
	    case 27:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.ESCAPE);
	        keyCodeSent = true;
	  	  	break;
	    case 113:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.BTN_FORWARD);
	        keyCodeSent = true;
	  	  	break;
	    case 112:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.BTN_BACK);
	        keyCodeSent = true;
	  	  	break;
	    case 114:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.INFO);
	        keyCodeSent = true;
	  	  	break;
	    case 115:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.WINDOW);
	        keyCodeSent = true;
	  	  	break;
	    case 116:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.MENU);
	        keyCodeSent = true;
	  	  	break;
	    case 117:
	        sendAnymoteKeyBoardEvent(googletvremote.anymote.KeyCode.BOOKMARK);
	        keyCodeSent = true;
	  	  	break;
	    }
	    switch (e.keyCode) {
	    case 37:
	        cheatCode = cheatCode + "LEFT";
	  	  	break;
	    case 38:
	    	cheatCode = cheatCode + "UP";
	  	  	break;
	    case 39:
	    	cheatCode = cheatCode + "RIGHT";
	  	  	break;
	    case 40:
	    	cheatCode = cheatCode + "DOWN";
	  	  	break;
	    case 65:
	    	cheatCode = cheatCode + "A";
	  	  	break;
	    case 66:
	    	cheatCode = cheatCode + "B";
	  	  	break;
	  	default:
	  		cheatCode = "";
	    }
	    if(cheatCode.indexOf("UPDOWNUPDOWNLEFTRIGHTLEFTRIGHTABAB") !== -1)
	    {
	    	if(backgroundPageWindow.cheatsEnabled == false)
	    	{
		    	displayToast("CHEATS UNLOCKED<br>Please contribute to the tip jar.");
		    	cheatCode = "";
		    	backgroundPageWindow.cheatsEnabled = true;
		    	var sponsorList = localStorage.getItem('sponsor-list');
		    	var adsEnabled = true;
	//	    	if(sponsorList[0] != "$")
		    	sponsorList = "$" + sponsorList;
		    	backgroundPageWindow.sponsorListJsonString = sponsorList;
		    	localStorage.setItem('sponsor-list', sponsorList);
	    	}
	    	else
	    	{
		    	displayToast("CHEATS DISABLED<br>Thanks for supporting us!");
		    	cheatCode = "";
		    	backgroundPageWindow.cheatsEnabled = false;
		    	var sponsorList = localStorage.getItem('sponsor-list');
		    	var adsEnabled = false;
	//	    	if(sponsorList[0] != "$")
		    	sponsorList = "*" + sponsorList;
		    	backgroundPageWindow.sponsorListJsonString = sponsorList;
		    	localStorage.setItem('sponsor-list', sponsorList);
	    	}
	    	
	    }
	       
	    if(keyCodeSent)
	    {
	    	//backgroundPageWindow.console.log('Keyboard code \'' + e.keyCode +'\' was sent to connected device.'); //For Debug use only. Disable for security reasons. 
	    	backgroundPageWindow.console.log('Keyboard code \'' + 'SECURED' +'\' was sent to connected device.');
	    	
	    }
	    
	}
	else
	{
		//backgroundPageWindow.console.log('Keyboard code \'' + e.keyCode +'\' was not sent because no anymote session is active.'); //For Debug use only. Disable for security reasons.
		backgroundPageWindow.console.log('Keyboard code \'' + 'SECURED' +'\' was not sent because no anymote session is active.');
	}
	indicatorFlash();
	
};


/** Update the UI to display the IP address of connected devices. */
var updatePairedDevicesList = function() {
  // Start by clearing the list and removing the button, then populate.

  document.getElementById('clear-devices').style.display = 'none';
  var devicesInStorage = localStorage.getItem(STORAGE_KEY_PAIRED_DEVICES);
  if (devicesInStorage) {
    var pairedDevices = JSON.parse(devicesInStorage);
    for (var i = 0; i < pairedDevices.length; i++) {
    	
    	var fields        = pairedDevices[i].split('~');
    	var deviceName    = fields[0];
    	var deviceAddress = fields[1];
    	
    	window.addDevice(deviceName, deviceAddress, false);
    	if (i == 0 && backgroundPageWindow.anymoteSessionActive && backgroundPageWindow.connectedDevice == deviceAddress)
    		setStatusLabel(deviceName);
    }
    if (pairedDevices.length > 0) {
      document.getElementById('clear-devices').style.display = 'inline-block';
    }
  }
};


var clearDeviceList = function() {
	
	localStorage.removeItem(STORAGE_KEY_PAIRED_DEVICES);
	updatePairedDevicesList();
	anymoteSession.stopSession();
	disableKeyBoardEvents();
	setIndicatorDisconnected();
	backgroundPageWindow.anymoteSessionActive = false;
	backgroundPageWindow.console.log('Device manager list cleared and anymote session disconnected.');
	
	sendGAEvent("Connection", "Clear Devices");
	setStatusLabel('Chromemote');
	
	chrome.browserAction.setIcon({path:"icons/icon19_grey.png"});
};

var removeDeviceFromList = function(address) {
	//Finds and Removes single device from the local storage device list.
	var pairedDevices = [];
	var devicesInStorage = localStorage.getItem(STORAGE_KEY_PAIRED_DEVICES);
	if (devicesInStorage) {
	    pairedDevices = JSON.parse(devicesInStorage);
	}
	
	for (var i=0; i < pairedDevices.length; i++)
	{
	  var fields        	  = pairedDevices[i].split('~');
	  var deviceName    	  = fields[0];
	  var deviceAddress 	  = fields[1];
	  if(deviceAddress == address)
	  {
		  backgroundPageWindow.console.log("Device named " + deviceName + " at " + deviceAddress + " was removed from device list.");
		  delete pairedDevices[i];
		  var deviceList = JSON.stringify(pairedDevices);
		  deviceList = deviceList.replace("null,","");
		  deviceList = deviceList.replace(",null","");
		  deviceList = deviceList.replace("null","");
		  if(deviceList == "[]")
			  localStorage.removeItem(STORAGE_KEY_PAIRED_DEVICES);
		  else
		  	  localStorage.setItem(STORAGE_KEY_PAIRED_DEVICES, deviceList);
		  
	      updatePairedDevicesList();  
	  }    	  
	}
	  
};


var renameDeviceFromList = function(newName, address) {
	//Finds and Removes single device from the local storage device list.
	var pairedDevices = [];
	var devicesInStorage = localStorage.getItem(STORAGE_KEY_PAIRED_DEVICES);
	if (devicesInStorage) {
	    pairedDevices = JSON.parse(devicesInStorage);
	}
	
	for (var i=0; i < pairedDevices.length; i++)
	{
	  var fields        	  = pairedDevices[i].split('~');
	  var deviceName    	  = fields[0];
	  var deviceAddress 	  = fields[1];
	  if(deviceAddress == address)
	  {
		  backgroundPageWindow.console.log("Device named " + deviceName + " at " + deviceAddress + " was removed from device list.");
		  
		  var deviceList = JSON.stringify(pairedDevices);
		  deviceList = deviceList.replace(deviceName+"~"+deviceAddress, newName+"~"+deviceAddress);
		  if(deviceList == "[]")
			  localStorage.removeItem(STORAGE_KEY_PAIRED_DEVICES);
		  else
		  	  localStorage.setItem(STORAGE_KEY_PAIRED_DEVICES, deviceList);
		  
	      updatePairedDevicesList();  
	  }    	  
	}
	  
};


var sendGAEvent = function(category, action) {
	if(chrome.extension.getURL("/").indexOf("bhcjclaangpnjgfllaoodflclpdfcegb") >= 0)
		_gaq.push(['_trackEvent', category, action]);
	
};

var sendGAEvent = function(category, action, optLabel, optValue) {
	if(chrome.extension.getURL("/").indexOf("bhcjclaangpnjgfllaoodflclpdfcegb") >= 0)
		_gaq.push(['_trackEvent', category, action, optLabel, optValue]);
			
};

function onMouseMove (e) {
	
	var movementX = e.movementX       || e.mozMovementX    || e.webkitMovementX || 0,
	movementY = e.movementY       || e.mozMovementY    || e.webkitMovementY || 0;
		 
	// Print the mouse movement delta values
	//backgroundPageWindow.console.log("movementX=" + movementX, "movementY=" + movementY);
	
	sendTpadMouseMoveEvent(movementX,movementY);
	backgroundPageWindow.console.log('Chromemote keycode to move cursor by \' (' + movementX + ',' + movementY + ') \' sent to connected device.');
	
};

function onMouseWheel (e) {
	
	var movementY = (e.wheelDelta / -120) || 0;
	if (movementY > 0 && movementY < 1)
		movementY = 1;
	if (movementY < 0 && movementY > -1)
		movementY = -1;
	
	// Print the mouse movement delta values
	//backgroundPageWindow.console.log("movementX=" + movementX, "movementY=" + movementY);
	
	function toInt(n){ return Math.round(Number(n)); };
	
	sendTpadMouseWheelEvent(0,toInt(movementY));
	backgroundPageWindow.console.log('Chromemote keycode to scroll \' (0,' + toInt(movementY) + ') \' sent to connected device.');
	
};

var initPointerLock = function() {
	
	
	// The element we'll make fullscreen and pointer locked.
	var elem;
	 
	function fullscreenChange() {
	  if (document.webkitFullscreenElement === elem ||
	      document.mozFullscreenElement === elem ||
	      document.mozFullScreenElement === elem) { // Older API upper case 'S'.
	    // Element is fullscreen, now we can request pointer lock
	    elem.requestPointerLock = elem.requestPointerLock    ||
	                              elem.mozRequestPointerLock ||
	                              elem.webkitRequestPointerLock;
	    elem.requestPointerLock();
	  }
	}
	 
	document.addEventListener('fullscreenchange', fullscreenChange, false);
	document.addEventListener('mozfullscreenchange', fullscreenChange, false);
	document.addEventListener('webkitfullscreenchange', fullscreenChange, false);
	 
	function pointerLockChange() {
		elem = document.getElementById("Touch-Mouse-Pad");
	  if (document.mozPointerLockElement === elem || document.webkitPointerLockElement === elem) {
		  backgroundPageWindow.console.log("Pointer Lock was successful.");
		  document.addEventListener("mousemove", onMouseMove, false);
		  document.addEventListener("mousewheel", onMouseWheel, false);
		  displayPermToast('Mouse Cursor Locked to GTV.<br>Press ESC to return.');
	  } else {
		  backgroundPageWindow.console.log("Pointer Lock was lost.");
		  document.removeEventListener("mousemove", onMouseMove);
		  document.removeEventListener("mousewheel", onMouseWheel);
		  removePermToast();
		  hideMouseIcon();
		  
		  var myTextField = document.get().getElementById('TOUCHPAD_SHOWHIDE');
		  myTextField.click();
	  }
	}
	 
	document.addEventListener('pointerlockchange', pointerLockChange, false);
	document.addEventListener('mozpointerlockchange', pointerLockChange, false);
	document.addEventListener('webkitpointerlockchange', pointerLockChange, false);
	 
	function pointerLockError() {
		backgroundPageWindow.console.log("Error while locking pointer.");
		document.removeEventListener("mousemove", onMouseMove);
		document.removeEventListener("mousewheel", onMouseWheel);
	}
	 
	document.addEventListener('pointerlockerror', pointerLockError, false);
	document.addEventListener('mozpointerlockerror', pointerLockError, false);
	document.addEventListener('webkitpointerlockerror', pointerLockError, false);
	 
	function fullScreenAndLockPointer() {
	  elem = document.getElementById("Touch-Mouse-Pad");
	  // Start by going fullscreen with the element.  Current implementations
	  // require the element to be in fullscreen before requesting pointer
	  // lock--something that will likely change in the future.
	  elem.requestFullscreen = elem.requestFullscreen    ||
	                           elem.mozRequestFullscreen ||
	                           elem.mozRequestFullScreen || // Older API upper case 'S'.
	                           elem.webkitRequestFullscreen;
	  elem.requestFullscreen();
	}
	
	function lockPointer() {
		
		elem = document.getElementById("Touch-Mouse-Pad");
		elem.requestPointerLock = elem.requestPointerLock || elem.mozRequestPointerLock || elem.webkitRequestPointerLock;
		elem.requestPointerLock();

	}
	
};

var actPointLock = function() {
	
	  var elem;
	  
	  elem = document.getElementById("Touch-Mouse-Pad");
	  elem.requestPointerLock = elem.requestPointerLock || elem.mozRequestPointerLock || elem.webkitRequestPointerLock;
	  elem.requestPointerLock();
};

var deactPointLock = function() {
	
	  var elem;
	  
	  elem = document.getElementById("Touch-Mouse-Pad");
	  elem.exitPointerLock = elem.exitPointerLock || elem.mozExitPointerLock || elem.webkitExitPointerLock;
	  elem.exitPointerLock();
	  
	  document.removeEventListener("mousemove", onMouseMove);
	  document.removeEventListener("mousewheel", onMouseWheel);
};

var checkIfConnectionIsActive = function() {
	backgroundPageWindow.console.log('Checking if Anymote session is still connected.');
	if(backgroundPageWindow.anymoteSessionActive)
	{
		backgroundPageWindow.pingAck = false;
		anymoteSession.sendPing();
		
		var timer = window.setTimeout(function() {
	        
			if(!backgroundPageWindow.pingAck)
				anymoteConnectToExistingDevice();
	        
	    }, 1000);  // Auto stop discovery after 5-10 seconds.
		
	}
};








