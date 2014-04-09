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

/**
 * Helper function to display a message on the screen for the user.
 * Similar to an Android Toast (hence the name).
 */


/**
 * Display a short message to the user.
 * @param {string} message Short text to display to the user.
 * @param {number=} duration_ms Time in milliseconds to display the message.
 *     Defaults to 2 seconds.
 */
var displayToast = function(message, duration_ms) {
  duration_ms = duration_ms || 1000;  // Default duration_ms
  var toastEl = document.createElement('div');
  
  tabOrPopUp = setUiType();
  if(tabOrPopUp == 'popup')
  {
	  toastEl.className = 'toast';  
  }
  else
  {
	  toastEl.className = 'toast_tab';
  }
  toastEl.innerHTML = message;
  toastEl.style.opacity = 1;
  document.body.appendChild(toastEl);

  // Fade out.
  window.setTimeout(function() {
    toastEl.style.opacity = 0;
  }, duration_ms);

  // Remove the element.
  window.setTimeout(function() {
    toastEl.parentNode.removeChild(toastEl);
  }, duration_ms + 1000);
};

var toastEl2 = document.createElement('div');

var displayPermToast = function(message) {
	  
	  
	  toastEl2.className = 'toast';
	  toastEl2.innerHTML = message;
	  toastEl2.style.opacity = 1;
	  document.body.appendChild(toastEl2);

};

var removePermToast = function() {
	  var duration_ms = 250;  // Default duration_ms
	  	  
	  // Fade out.
	  window.setTimeout(function() {
	    toastEl2.style.opacity = 0;
	  }, duration_ms);

	  // Remove the element.
	  window.setTimeout(function() {
	    toastEl2.parentNode.removeChild(toastEl2);
	  }, duration_ms + 1000);
	};

var displaySmallToast = function(message, duration_ms) {
	  duration_ms = duration_ms || 1000;  // Default duration_ms
	  var toastEl = document.createElement('div');
	  
	  tabOrPopUp = setUiType();
	  if(tabOrPopUp == 'popup')
	  {
		  toastEl.className = 'toast_small';  
	  }
	  else
	  {
		  toastEl.className = 'toast_tab_small';
	  }
	  toastEl.innerHTML = message;
	  toastEl.style.opacity = 1;
	  document.body.appendChild(toastEl);

	  // Fade out.
	  window.setTimeout(function() {
	    toastEl.style.opacity = 0;
	  }, duration_ms);

	  // Remove the element.
	  window.setTimeout(function() {
	    toastEl.parentNode.removeChild(toastEl);
	  }, duration_ms + 1000);
};