package com.chromemote.crx.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;

public class PanelAnimationScale extends Animation 
{	
	private final Element element;
	private int startWidth;
    private int startHeight;
    private int finalWidth;
    private int finalHeight;
 
    public PanelAnimationScale(Element element)
    {
        this.element = element;
    }
 
    public void scaleTo(int width, int height, int milliseconds)
    {
        this.finalWidth  = width;
        this.finalHeight = height;
        
        startWidth = element.getClientWidth();
        startHeight = element.getClientHeight();
        
        
    
        run(milliseconds);
    }
 
    @Override
    protected void onUpdate(double progress)
    {
        double width  = startWidth + (progress * (this.finalWidth - startWidth));
        double height = startHeight + (progress * (this.finalHeight - startHeight));
        
        this.element.getStyle().setWidth(width, Style.Unit.PX);
        this.element.getStyle().setHeight(height, Style.Unit.PX);
        
    }
 
    @Override
    protected void onComplete()
    {
        super.onComplete();
        this.element.getStyle().setWidth(this.finalWidth, Style.Unit.PX);
        this.element.getStyle().setHeight(this.finalHeight, Style.Unit.PX);
        
    }
}