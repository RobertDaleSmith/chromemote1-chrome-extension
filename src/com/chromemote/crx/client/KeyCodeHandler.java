package com.chromemote.crx.client;

public interface KeyCodeHandler extends java.util.EventListener
{
	void onKeyCodeChange(String keyCodeString);
}