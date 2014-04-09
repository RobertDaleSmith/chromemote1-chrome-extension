var host = document.location.host,
urlpath = document.location.pathname,
url = document.location.origin + '/watch' + document.location.search;

console.log(document.location);

var currentTime = document.createElement('div');
currentTime.setAttribute('id', 'currentTimeHolder');
currentTime.setAttribute('time', '0');
document.body.appendChild(currentTime);


//console.log(document.getElementById('currentTimeHolder').getAttribute('time'));

var flingIconPath = document.createElement('div');
flingIconPath.setAttribute('id', 'flingIconPathHolder');
flingIconPath.setAttribute('flingIconPath', chrome.extension.getURL('images/flinger/yt_fling_icon.png'));
document.body.appendChild(flingIconPath);



function inject(func) {
	var script = document.createElement('script');
	script.setAttribute('type', 'text/javascript');
	script.appendChild(document.createTextNode("var chromePage = \""+chrome.extension.getURL('')+"\";\n var inIncognito = "+chrome.extension.inIncognitoContext+";\n(" + func + ")();"));
	document.body.appendChild(script);
}
			
var mainInject = function() {
//---
	
cmf = {

	vars: function() {
		
		cmf.space = document.createTextNode(' ');
		cmf.player = null;
		cmf.button = null;
		cmf.buttonicon = null;
		cmf.buttonContainer = null;
	
		cmf.sharebutton = null;
		cmf.likebutton = null;
		cmf.design = '2011';
		
		cmf.playAction = null;
		cmf.rangeAction = null;
		cmf.setEventloaded = false;
		cmf.urlChecked = false;
	
		
		cmf.script = null;
	
	},
	setOnload: function () {
	if(document.getElementById('watch7-container')) {
		cmf.design = '2012';
		console.log('[Chromemote Flinger] YouTube in new style');
	}
	try {	
	
//		cmf.player = cmf.get('player');
		cmf.buttonContainer = document.getElementById('watch-actions')||document.getElementById('watch7-sentiment-actions');
	
		cmf.sharebutton = document.getElementById('watch-share')||document.getElementById('watch-dislike');
		cmf.likebutton = document.getElementById('watch-like');
	} catch (e) {
	
	} finally {
		cmf.getReady();

		console.log('getReady()');
		window.addEventListener ('message', function(e) {
			cmf.iconDisplay(cmf.check('flingicon'));
		}, false);
	}
	},
	
	
	
	check: function (c) {
	switch (c) {
		
		
		case 'flingicon': return (true);
		case 'btnDisable': return (cmf.likebutton.getAttribute('disabled') && cmf.likebutton.getAttribute('disabled').toLowerCase() == 'true' && cmf.likebutton != 'null');
		case 'playlistExist': return ( !isNaN(Number(yt.config_.PLAYLIST_BAR_PLAYING_INDEX)) && Number(yt.config_.PLAYLIST_BAR_PLAYING_INDEX) != -1 );
		case 'playlistAutoPlay': return (yt.config_.LIST_AUTO_PLAY_ON == true);
		
		
	}
	},
	get: function (c) {
		switch (c) {
			case 'player': return cmf.player = window.yt.config_.PLAYER_REFERENCE;
			case 'currenttime': return  (cmf.player.getCurrentTime != undefined) ? cmf.player.getCurrentTime() : false;
			case 'duration': return (cmf.player.getDuration != undefined) ? cmf.player.getDuration() : false;
			case 'playerstate': return (cmf.player.getPlayerState != undefined) ? cmf.player.getPlayerState() : false;
		}
	},
	
	
	
	setBtn: function() {
		if(cmf.btn) return;
		if(cmf.buttonContainer==null) return;
		var b = document.createElement('button'), i = document.createElement('span'), t = document.createElement('span'), 
			p = ( cmf.design=='2012' ? cmf.buttonContainer.childNodes[2] : cmf.buttonContainer.childNodes[5] ),
			c = cmf.sharebutton.getAttribute('class'), d = cmf.check('btnDisable'),
			s = cmf.likebutton.getElementsByTagName('span')[2].getAttribute('class');
			//s = ( cmf.design=='2012' ? cmf.likebutton.getElementsByTagName('span')[2].getAttribute('class') : cmf.likebutton.getElementsByTagName('span')[0].getAttribute('class') );
		b.setAttribute('class', c);
		//b.setAttribute('data-button-toggle', 'true');  //Disables Toggle of button
		if(d) b.setAttribute('disabled', d);
		b.setAttribute('id', 'fling');
		b.setAttribute('type','button');
		if(!d) b.setAttribute('title', 'Fling this video to Google TV');
		if(cmf.design == '2012') { 
			b.style.opacity = '1';
			b.addEventListener ('mouseover', function(){ 
				b.style.opacity = '1';
			});
			b.addEventListener ('mouseout', function(){ 
				//blank
			});
		}
		i.setAttribute('id', 'flingicon');
		
		var iconUrl = document.getElementById('flingIconPathHolder').getAttribute('flingIconPath');
		
		//console.log("TEST!!!!!!" + iconUrl);
		//iconUrl = 'chrome-extension://mndpcnaocgogkbobccpbgokejfejkfdp/images/flinger/yt_fling_icon.png';
		
		i.style.cssText = 'display: none; margin-right: 0; font-size: 12px; background: no-repeat url(' + iconUrl + ') 0px 0px; width: 20px; height: 16px;';
		b.appendChild(i);
		b.appendChild(cmf.space);
		t.setAttribute('class', s);
		t.innerText = 'Fling';
		b.appendChild(t);
		b.addEventListener ('click', cmf.btnAction);
		p.parentNode.insertBefore(b, p);
		cmf.btn = document.getElementById('fling');
		cmf.btnicon = document.getElementById('flingicon');
	},
	iconDisplay: function (c) {
		if(!cmf.btnicon) return false;
		cmf.btnicon.style.display = 'inline-block';
		cmf.btnicon.setAttribute('class', (c ? 'yt-uix-button-icon-wrapper yt-uix-button-icon' : ''));
	},
	btnClick: function (s) { console.log('[flinger] Button Click - Done'); return cmf.btn.click(); },
	
	
	
	
	
	btnDisplay: function () {
		if(cmf.design == '2012') {
			cmf.btn.getElementsByTagName('span')[1].innerText = 'Fling';
		}
		return cmf.btn.className = (true) ? ( cmf.btn.className.match('yt-uix-button-toggled') ? cmf.btn.className : cmf.btn.className.replace('yt-uix-button cm','yt-uix-button yt-uix-button-toggled cm')) : cmf.btn.className.replace(/( )?yt-uix-button-toggled/g,'');
	},
	btnAction: function () {
		
		playerState = cmf.get('playerstate');
		currentTime = cmf.get('currenttime');
		console.log(playerState);
		if ( playerState == 1 )
		{	//Pause Video if it is playing.
			cmf.player.pauseVideo();
		}
		if ( playerState == 0 )
		{	//Reset the current time if Video has ended.
			currentTime = 0;
		}

				
		document.getElementById('currentTimeHolder').setAttribute( 'time', currentTime );
		
		return;
		
	},
	
	onStateChange: function () {
		cmf.player = cmf.get('player');
	},
	setEvent: function () {
		cmf.player = cmf.get('player');
		try {
			cmf.setEventloaded = true;
			
			
				cmf.player.addEventListener('onStateChange', cmf.onStateChange);
				
			
		} catch (e) {
			setTimeout(cmf.setEvent, 500);
		}
	},
	
	getReady: function () {
	try {
//		if (cmf.player==null) cmf.player = cmf.get('player');
		//&&
		if (cmf.btn==null) cmf.setBtn();
		console.log('getReady() inside');
	} catch (e) {
		console.debug('[Flinger] getReady - Error: '+e.message);
	} finally {
		if(cmf.setEventloaded == false) cmf.setEvent();
		
	}
	return;
	
	},

};
cmf.vars(); 
cmf.setOnload();



//---
};

if (host.substr(host.length - 11) == 'youtube.com' && host != 'm.youtube.com') 
	inject(mainInject);

document.getElementById("fling").addEventListener('click',function(){
	sendMessage();
})

console.log('Chrome is in Incognito mode: '+chrome.extension.inIncognitoContext);



//connect to the background script
var port = chrome.extension.connect();

var sendMessage = function() {
	
	var currentTimeSeconds = document.getElementById('currentTimeHolder').getAttribute('time');
	
	currentTimeSeconds = currentTimeSeconds - (currentTimeSeconds % 1);
	
	var notification = { flingurl: url + '#t=' + currentTimeSeconds + 's' };
	
	//console.log(cmf.get('currenttime'));
	
	chrome.extension.sendMessage(notification, function(responseMessage) {
		// message coming back from content script
		console.log(responseMessage);
	});
	
};