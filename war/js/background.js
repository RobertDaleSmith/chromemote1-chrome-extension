var appVersion = chrome.i18n.getMessage("version");

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
	
var previousTab;
var currentTab;
var cheatsEnabled = false;

chrome.tabs.getSelected(null, function(tab) {
	previousTab = tab.id;
	currentTab = null;
});

chrome.tabs.onSelectionChanged.addListener(function(tab) {
	if (previousTab == null) {
		previousTab = tab;
	}
	if (currentTab == null) {
		currentTab = tab;
	}
	else {
		previousTab = currentTab;
		currentTab = tab;
	}
});

var remotePanel1JsonString = 'empty';
var remotePanel2JsonString = 'empty';
var scrollPanelJsonString  = 'empty';
var sponsorListJsonString = '[{\"index\": 1,\"imgUrl\":\"/images/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/keep-chromemote-running/\",\"fling\":\"FALSE\",},]';

var osDetected="Unknown OS";
var gTvPluginLoaded = false;
var userAgent;
var firstInstallAck = false;
var connectedDevice = "";

var installAck = localStorage.getItem('install-ack');
if (installAck != null)
{
	if (installAck == 'false')
		firstInstallAck = false;
	else if (installAck == 'true')
		firstInstallAck = true;
	
	console.log('Restored Acknowledgement Status to ' + firstInstallAck);
}


var firstInstall = function() {
	
	firstInstallAck = false;
	localStorage.setItem('install-ack', firstInstallAck);
	console.log("New Install / Update detected ... Showing whats new dialog until acknowledgment received.");
	cheatsEnabled = false;
	var sponsorList = "";
	if(localStorage.getItem('sponsor-list'))
		sponsorList = localStorage.getItem('sponsor-list');
//	adsEnabled = true;
	sponsorListJsonString = '[{\"index\": 1,\"imgUrl\":\"/images/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/keep-chromemote-running/\",\"fling\":\"FALSE\",},]';
	localStorage.setItem('sponsor-list', sponsorListJsonString);
	
}

try
{
	chrome.runtime.onInstalled.addListener(firstInstall);
}
catch(err)
{
	console.log("Update Chrome to 23+ for best support.");
}


var newInstallUpdateMsg = "" +
		"Welcome to Chromemote Update "   +
		"for April 8, 2014.  (v. 0.1404.8)" +
		"<br><br>" +
		"What's New This Update:" +
		"<ul>" +
		"<li>Re-release of the original Chromemote. Although I worked my butt off to build a whole new Chromemote ver 2. Many users have requested to bring back the original. Well here it is. Always check out the latest version and share your feedback so I can keep making it better.</li>" +

		"</ul>" +
		"What's New Last Updates:" +
		"<ul>" +
		"<li>Fixed bug related to saving custom button settings.</li>" +
		"<li>Fixed OnLive app link.</li>" +
		"<li>Added Amazon Instant, M-GO, and VUDU apps to app launcher.</li>" +
		"<li>Set the YouTube fling to Google TV button to pause the video and send the current time.</li>" +
		"<li>Removed and replaced buggy UI animations for improved performance.</li>" +
		"<li>Corrected PIN dialog box staying open bug on Vizio Costar devices.</li>" +
		"<li>Edit Scroll Buttons List option now enabled.</li>" +
		"<li>Right-click menu added to scroll list buttons.</li>" +
		"<li>Add, remove, and rearrange scroll button list.</li>" +
		"<li>Fixed scroll button bug from previous update.</li>" +
		"<li>Individual scroll buttons can be edited.</li>" +
		"<li>Enlarged the device menu button in the top menu bar.</li>" +
		"<li>Corrected bug with d-pad and added guidelines.</li>" +
		"<li>Enabled fling right-click chrome context menu for HTML5 videos.</li>" +
		"<li>Enabled fling right-click chrome context menu for audio.</li>" +
		"<li>Added an option to check for updates in the about menu.</li>" +
		"<li>Added chromemote.com/update for installing updates.</li>" +
		"<li>Chromemote sponsor ad bar added.</li>" +
		"<li>Signup for one of our sponsors and support Chromemote at the same time.</li>" +
		"<li>Lights Out Setting added to toggle Full Tab Mode background from light to dark.</li>" +
		"<li>Added dark background for Full Tab Mode.</li>" +
		"<li>Added Open Full Tab Mode button to Menu Bar</li>" +
		"<li>Added Open Full Tab Mode option to Settings.</li>" +
		"<li>Added Pin/Unpin option to Settings.</li>" +
		"<li>Updated all support buttons to point to updated URL.</li>" +
		"<li>New logo and website.</li>" +
		"<li>Improved remote button hold repeat.</li>" +
		"<li>Implemented true remote button hold support to acces alternative button fuctions when held.</li>" +
		"<li>Locked cursor input now support click and drag/hold.</li>" +
		"<li>Touch pad also supports click and drag/hold.</li>" +
		"<li>Improved D-pad by adding button hold features.</li>" +
		"<li>Mapped keyboard delete button to send Back Button keycode.</li>" +
		
		
		"</ul>" +
		
		
		
		"Like what you see? Then please consider " +
		"making a donation. Be sure to include " +
		"feature requests with your donation." +
		
		"<br><br><a href=\"http://chromemote.com/changelog\" target=\"_blank\">Open Full Changelog >></a>";



window.onload = function() {
	
	getLatestAdsJson();
	
	userAgent = window.navigator.appVersion;
	
	//For simulating other OS userAgents.
	//userAgent = "5.0 (X11; Linux x86_64) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4";
	//userAgent = "5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";
	//userAgent = "5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.91 Safari/537.11";
	//userAgent = "5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.91 Safari/537.11";
	//userAgent = "5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";
	//userAgent = "5.0 (X11; CrOS i686 2465.227.0) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.92 Safari/537.1";
	
	
	console.log( userAgent ); //Display Version Information in the console.
	
	if (userAgent.indexOf("Win")		   !=-1)  osDetected="WindowsOS";
	if (userAgent.indexOf("Windows NT 6.2")!=-1)  osDetected="Windows8";
	if (userAgent.indexOf("Windows NT 6.1")!=-1)  osDetected="Windows7";
	if (userAgent.indexOf("Windows NT 6.0")!=-1)  osDetected="WindowsVi";
	if (userAgent.indexOf("Windows NT 5.1")!=-1)  osDetected="WindowsXP";
	if (userAgent.indexOf("Mac OS X")	   !=-1)  osDetected="MacOSX";
	if (userAgent.indexOf("Mac OS X 10_9") !=-1)  osDetected="MacOSX9";
	if (userAgent.indexOf("Mac OS X 10_8") !=-1)  osDetected="MacOSX8";
	if (userAgent.indexOf("Mac OS X 10_7") !=-1)  osDetected="MacOSX7";
	if (userAgent.indexOf("Mac OS X 10_6") !=-1)  osDetected="MacOSX6";
	if (userAgent.indexOf("CrOS")		   !=-1)  osDetected="CrOS";
	if (userAgent.indexOf("Linux")		   !=-1)  osDetected="Linux";
	
	console.log( "Operating System Detected::" + osDetected );
			
			
	chrome.browserAction.setIcon({path:"icons/icon19_grey.png"});
	
	
	
	
//	var remoteButtonsStorage1 = localStorage.getItem('remote-buttons-1');
//	if (remoteButtonsStorage1 != null)
//	{
//		localStorage.setItem('remote-buttons-1', firstInstallAck);
//		
//		//console.log('Restored Acknowledgement Status to ' + firstInstallAck);
//	}


	var remoteButtonsTemp1 = localStorage.getItem('remote-buttons-1');
	var remoteButtonsTemp2 = localStorage.getItem('remote-buttons-2');
	var scrollButtonsTemp = localStorage.getItem('scroll-buttons');
	if (remoteButtonsTemp1 == null || remoteButtonsTemp2 == null || scrollButtonsTemp == null)
	{
		setDefaultJsonStrings();
	}
	else
	{
		remotePanel1JsonString = remoteButtonsTemp1;
		remotePanel2JsonString = remoteButtonsTemp2;
		scrollPanelJsonString = scrollButtonsTemp;
	}
		
	
	googletvremoteInitializePlugin();
	
	loadGoogleTVConstants();
	
    window.anymoteConnectToExistingDevice();

	var notConnectedMessage = "You are not connected to any Google TV devices. Please click the Chromemote icon at the top right to connect to a Google TV device on your network.";
	
  	// A link onclick callback function.
	var linkOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		if(anymoteSessionActive)
		{
			sendAnymoteFling(info.linkUrl);
			flashBadge();
			
			sendGAEvent("Fling", "Link");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
			//TODO: Notify user no devices paired.
		}

	};
	var linkId = chrome.contextMenus.create({"title": "Fling Link to Google TV", "contexts":["link"], "onclick": linkOnClick});
	console.log("'" + "link" + "' item:" + linkId);

	
  	// A page onclick callback function.
	var pageOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		
		if(anymoteSessionActive)
		{
			sendAnymoteFling(tab.url);
			flashBadge();
			
			sendGAEvent("Fling", "Page");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
			//TODO: Notify user no devices paired.
		}
	};
	var pageId = chrome.contextMenus.create({"title": "Fling Page to Google TV", "contexts":["page"], "onclick": pageOnClick});
	console.log("'" + "page" + "' item:" + pageId);


	
	
  	// A selection on click callback function.
	var selectionOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		
		if(anymoteSessionActive)
		{
			sendAnymoteKeyEvent(googletvremote.anymote.KeyCode.SEARCH);
			sendAnymoteStringMessage(info.selectionText);
			flashBadge();
			
			sendGAEvent("Fling", "Selection");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
			//TODO: Notify user no devices paired.
		}
	};
	var selectId = chrome.contextMenus.create({"title": "Search Selected Text on Google TV", "contexts":["selection"], "onclick": selectionOnClick});
	console.log("'" + "selection" + "' item:" + selectId);
		
	
	
  	// A video onclick callback function.
	var videoOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		
		if(anymoteSessionActive)
		{
			sendAnymoteFling(info.srcUrl);
			flashBadge();
			
			sendGAEvent("Fling", "HTML5_VIDEO");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
			//TODO: Notify user no devices paired.
		}
	};
	var videoId = chrome.contextMenus.create({"title": "Fling video to Google TV", "contexts":["video"], "onclick": videoOnClick});
	console.log("'" + "video" + "' item:" + videoId);
	
  	// A selection onclick callback function.
	var audioOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		
		if(anymoteSessionActive)
		{
			sendAnymoteFling(info.srcUrl);
			flashBadge();
			
			sendGAEvent("Fling", "AUDIO");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
			//TODO: Notify user no devices paired.
		}
	};
	var audioId = chrome.contextMenus.create({"title": "Fling audio to Google TV", "contexts":["audio"], "onclick": audioOnClick});
	console.log("'" + "audio" + "' item:" + audioId);
	
  	// A image onclick callback function.
	var imageOnClick = function(info, tab) {
		console.log("item " + info.menuItemId + " was clicked");
		console.log("info: " + JSON.stringify(info));
		console.log("tab: " + JSON.stringify(tab));
		
		if(anymoteSessionActive)
		{
			sendAnymoteFling(info.srcUrl);
			flashBadge();
			
			sendGAEvent("Fling", "Image");
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
		}
	};
	var id = chrome.contextMenus.create({"title": "Fling Image to Google TV", "contexts":["image"], "onclick": imageOnClick});
	console.log("'" + "image" + "' item:" + id);	
	
	//Initialize badge count and color.
	//chrome.browserAction.setBadgeText({text:"0"});
	//chrome.browserAction.setBadgeBackgroundColor({color:[200, 0, 0, 100]});
	
	
	


};

window.onerror = function() {
	console.log('JS Error found!');
};

var googletvremoteInitializePlugin = function() {
	var pluginEl = document.getElementById('pluginId');
    if (pluginEl) {
      pluginEl.parentNode.removeChild(pluginEl);
    }
    pluginEl = document.createElement('embed');
    pluginEl.type = 'application/x-gtvremote';
    pluginEl.id = 'pluginId';
    document.body.appendChild(pluginEl);
	
	
    
	try
	{
		window.googletvremote = pluginEl.gtvremote.plugin.object.GTVRemote();
		
		googletvremote.init('');
		
		gTvPluginLoaded = true;
	}
	catch(err)
	{
		gTvPluginLoaded = false;
		
		
		
		console.log("ERROR:: GTVRemote Plugin is not detected.");
		
		if (osDetected == 'CrOS')
		{
			console.log("Chrome OS does not support the GTVRemote Plugin. Chromemote is not compatible with Google ChromeOS.");
		}
		else if (osDetected == 'Windows8')
		{
			console.log("ALERT:: Windows 8 app mode does not support the GTVRemote Plugin used by Chromemote.");
			console.log("ALERT:: Chromemote is only compatible with the desktop mode.");
			console.log("ALERT:: Relaunch Chrome on the desktop for GTVRemote Plugin support.");
		}
		else if (osDetected == 'Linux')
		{
			console.log("ALERT:: The GTVRemote Plugin for Linux currently only supports Chrome extensions on Ubuntu 12. Contact developer for details.");
		}
		else 
		{
			console.log("ALERT:: Chromemote is compatible with WindowsXP/Vista/7/8, Mac OS X 10.6+, and Linux Ubuntu 12.");
			console.log("ALERT:: Chromemote is not currently compatible with ChromeOS, Windows 8 app mode, or other versions of Linux besides Ubuntu 12.");
			console.log("ALERT:: If you see this message and your operating system is compatible, then please contact the developer. Visit www.chromemote.com");
		}
	}
	
	
	var cert = localStorage.getItem('cert');
    if (!cert || !googletvremote.loadCert(cert, 'password')) {
      // Change the 'password' to a unique string for your app.
      console.log('First launch.  Made a new certificate.');
      var uid = (((1 + Math.random()) * 0x10000000) | 0).toString(16);
      cert = googletvremote.generateSelfSignedCert('chrome-gtv-' + uid, 'password');
      localStorage.setItem('cert', cert);
      localStorage.removeItem(STORAGE_KEY_PAIRED_DEVICES);
    }
    
    window.discoveryClient = googletvremote.createDiscoveryClient();
    window.pairingSession  = googletvremote.createPairingSession();
    window.anymoteSession  = googletvremote.createAnymoteSession();
};

/**
 * Sends an Anymote key event to Google TV.  See the googletvremote_constants
 * for a complete list of keycode values.
 */
var sendAnymoteKeyEvent = function(keycode) {
	anymoteSession.sendKeyEvent(keycode, googletvremote.anymote.Action.DOWN);
	anymoteSession.sendKeyEvent(keycode, googletvremote.anymote.Action.UP);
};

var sendCtrlAltDelAnymoteKeyEvent = function() {
	
	setInterval(function(){
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.CTRL_RIGHT, googletvremote.anymote.Action.DOWN);
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.CTRL_RIGHT, googletvremote.anymote.Action.UP);
		
	},2000);
	setInterval(function(){
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.ALT_RIGHT,  googletvremote.anymote.Action.DOWN);
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.ALT_RIGHT,  googletvremote.anymote.Action.UP);
	
	},2000);
	setInterval(function(){
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.DEL, 	  googletvremote.anymote.Action.DOWN);
		anymoteSession.sendKeyEvent(googletvremote.anymote.KeyCode.DEL, 	  googletvremote.anymote.Action.UP);
		
	},2000);
};

/**
 * Sends a data message of type STRING.
 */
var sendAnymoteStringMessage = function(message) {
	anymoteSession.sendData(googletvremote.anymote.DataType.STRING, message);
};

/**
 * Flings a uri to the Google TV.
 */
var sendAnymoteFling = function(uri, sequenceNumber) {
	sequenceNumber = sequenceNumber || 42; // Sequence number default if not given
	anymoteSession.sendFling(uri, sequenceNumber);
};

var connectedDeviceCount = 0;

var addOneToBadge = function() {
	
	connectedDeviceCount++;
	var textString = "" + connectedDeviceCount;
	//chrome.browserAction.setBadgeText({text:textString});
	//chrome.browserAction.setBadgeBackgroundColor({color:[0, 200, 0, 100]});
	sendGAEvent("Connection", "Add Device", "Count", connectedDeviceCount);
	
};

var delOneFromBadge = function() {
	
	//connectedDeviceCount--;
	var textString = "" + connectedDeviceCount;
	//chrome.browserAction.setBadgeText({text:textString});
	
	if(connectedDeviceCount == 0) {
		//chrome.browserAction.setBadgeBackgroundColor({color:[200, 0, 0, 100]});
	}

};

var flashBadge = function() {
	
	var textString = " ";
	chrome.browserAction.setBadgeText({text:textString});
	chrome.browserAction.setBadgeBackgroundColor({color:[0, 200, 0, 100]});
	
	chrome.browserAction.setBadgeBackgroundColor({color:[255, 255, 0, 100]});
	setTimeout(function()
	{
		chrome.browserAction.setBadgeBackgroundColor({color:[0, 200, 0, 100]});
	}, 140);
	setTimeout(function()
	{
		chrome.browserAction.setBadgeBackgroundColor({color:[255, 255, 0, 100]});
	}, 280);
	setTimeout(function()
	{
		chrome.browserAction.setBadgeBackgroundColor({color:[0, 200, 0, 100]});
		textString = "";
		chrome.browserAction.setBadgeText({text:textString});
	}, 420);
	
	
};

var sendGAEvent = function(category, action) {
	
	if(chrome.extension.getURL("/").indexOf("bhcjclaangpnjgfllaoodflclpdfcegb") >= 0)
		_gaq.push(['_trackEvent', category, action]);
	
};

var sendGAEvent = function(category, action, optLabel, optValue) {
	
	if(chrome.extension.getURL("/").indexOf("bhcjclaangpnjgfllaoodflclpdfcegb") >= 0)
		_gaq.push(['_trackEvent', category, action, optLabel, optValue]);
			
};

var saveJsonToLocalStorage = function() {
	localStorage.setItem('remote-buttons-1', remotePanel1JsonString);
	localStorage.setItem('remote-buttons-2', remotePanel2JsonString);
	localStorage.setItem('scroll-buttons'  , scrollPanelJsonString);
	
	
};

var setDefaultJsonStrings = function() {
		
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'panels/remoteButtonList.json', true);

	xhr.onload = function(e) {
	  if (this.status == 200) {
	    // Note: .response instead of .responseText
		remotePanel1JsonString = this.response;
		
		localStorage.setItem('remote-buttons-1', remotePanel1JsonString);
	    //console.log(remotePanel1JsonString);
	    
	  }
	};
	xhr.send();
	
	var xhr2 = new XMLHttpRequest();
	xhr2.open('GET', 'panels/remoteButtonList2.json', true);

	xhr2.onload = function(e) {
	  if (this.status == 200) {
	    // Note: .response instead of .responseText
		remotePanel2JsonString = this.response;
		
		localStorage.setItem('remote-buttons-2', remotePanel2JsonString);
	    //console.log(remotePanel2JsonString);
	    
	  }
	};
	xhr2.send();
	
	var xhr3 = new XMLHttpRequest();
	xhr3.open('GET', 'panels/scrollButtonList.json', true);

	xhr3.onload = function(e) {
	  if (this.status == 200) {
	    // Note: .response instead of .responseText
		scrollPanelJsonString = this.response;
	    //console.log(remotePanel2JsonString);
		
		localStorage.setItem('scroll-buttons', scrollPanelJsonString);
	    
	  }
	};
	xhr3.send();
	
};

var getLatestAdsJson = function() {
	
	var req = new XMLHttpRequest();
	var url = 'http://chromemote.com/ads/list.json';
	
	var sponsorList = "";
	if(localStorage.getItem('sponsor-list'))
		sponsorList = localStorage.getItem('sponsor-list');
	
//	var adsEnabled = true;
	if(sponsorList[0] == '$')
	{
//		adsEnabled = false;
		sponsorListJsonString = '$[{\"index\": 1,\"imgUrl\":\"/images/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/keep-chromemote-running/\",\"fling\":\"FALSE\",},]';
	}
	else
	{
		// try{
			
		// 	req.open("GET", url + ((/\?/).test(url) ? "&" : "?") + (new Date()).getTime(), true);
	
		// 	req.onload = function(e) {
		// 		if(req.readyState==4)
		// 		{
		// 			if (this.status == 200) {
		// 				sponsorListJsonString = JSON.stringify(this.response).replaceAll("img","imgUrl").replaceAll("url","refUrl").replaceAll("order","index").replaceAll("enabled_status","fling");
						
		// 				sponsorListJsonString = JSON.parse(sponsorListJsonString);
		// 				sponsorListJsonString[0].fling = "FALSE";
		// 				console.log(sponsorListJsonString);
		// 				localStorage.setItem('sponsor-list', sponsorListJsonString);
		// 			} else {
		// 				sponsorListJsonString = '[{\"index\": 1,\"imgUrl\":\"/images/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/support-us/\",\"fling\":\"FALSE\",},]';
		// 				localStorage.setItem('sponsor-list', sponsorListJsonString);
		// 			}
		// 		}
		// 	};
		// 	req.send(null);
		// }
		// catch(e)
		// {
			sponsorListJsonString = '[{\"index\": 1,\"imgUrl\":\"/images/ads/ad_chromemote.png\",\"refUrl\":\"http://chromemote.com/support-us/\",\"fling\":\"FALSE\",},]';
			localStorage.setItem('sponsor-list', sponsorListJsonString);
		// }
	}
		
}



//a tab requests connection to the background script
chrome.extension.onConnect.addListener(function(port) {
  var tabId = port.sender.tab.id;
  console.log('Received request from content script of a YouTube page.', port);
  
  // remove when closed/directed to another url
  port.onDisconnect.addListener(function() {
	  console.log('Lost connection with YouTube page.', port);
  });
});

chrome.extension.onMessage.addListener(function(request, sender, sendResponse) {
	
	var notConnectedMessage = "You are not connected to any Google TV devices. Please click the Chromemote icon at the top right to connect to a Google TV device on your network.";
	
	if(request.flingurl)
		if(anymoteSessionActive)
		{  
			//console.log('Fling...');
			console.log('Fling Success: ' + request.flingurl);
			chrome.tabs.getSelected(null, function(tab) {sendAnymoteFling(request.flingurl);});
			flashBadge();
			// if a callback is given:
			sendResponse && sendResponse('Fling Success: ' + request.flingurl);
		}
		else
		{
			console.log("Fling not sent because no anymote session is active.");
			window.alert(notConnectedMessage);
		}
	if(request.getVersion)
		sendResponse && sendResponse(appVersion);
	
	
});



String.prototype.replaceAll = function( token, newToken, ignoreCase ) {
    var _token;
    var str = this + "";
    var i = -1;

    if ( typeof token === "string" ) {

        if ( ignoreCase ) {

            _token = token.toLowerCase();

            while( (
                i = str.toLowerCase().indexOf(
                    token, i >= 0 ? i + newToken.length : 0
                ) ) !== -1
            ) {
                str = str.substring( 0, i ) +
                    newToken +
                    str.substring( i + token.length );
            }

        } else {
            return this.split( token ).join( newToken );
        }

    }
return str;
};