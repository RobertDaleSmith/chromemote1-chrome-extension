package com.chromemote.crx.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class PanelAnimationSlide extends Animation 
{	
	private final Element element;
    private int startX;
    private int startY;
    private int finalX;
    private int finalY;
 
    public PanelAnimationSlide(Element element)
    {
        this.element = element;
    }
 
    public void scrollTo(int x, int y, int milliseconds)
    {
        this.finalX = x;
        this.finalY = y;
 
        startX = element.getOffsetLeft();
        startY = element.getOffsetTop();
 
        run(milliseconds);
    }
 
    @Override
    protected void onUpdate(double progress)
    {
        double positionX = startX + (progress * (this.finalX - startX));
        double positionY = startY + (progress * (this.finalY - startY));
 
        this.element.getStyle().setLeft(positionX, Style.Unit.PX);
        this.element.getStyle().setTop(positionY, Style.Unit.PX);
    }
 
    @Override
    protected void onComplete()
    {
        super.onComplete();
        this.element.getStyle().setLeft(this.finalX, Style.Unit.PX);
        this.element.getStyle().setTop(this.finalY, Style.Unit.PX);
    }
    
    public void shakeHorizontal()
    {
    	scrollTo( Window.getClientWidth() - (Window.getClientWidth()/2) - 120, 140, 500 );
		Timer t = new Timer() { public void run() {
				scrollTo( Window.getClientWidth() - (Window.getClientWidth()/2) - 140, 140, 100 );
				Timer t = new Timer() { public void run() {
					scrollTo( Window.getClientWidth() - (Window.getClientWidth()/2) - 125, 140, 100 );
					Timer t = new Timer() { public void run() {
						scrollTo( Window.getClientWidth() - (Window.getClientWidth()/2) - 135, 140, 100 );
						Timer t = new Timer() { public void run() {
							scrollTo( Window.getClientWidth() - (Window.getClientWidth()/2) - 130, 140, 100 );
						} }; t.schedule(80);  
					} }; t.schedule(80);  
				} }; t.schedule(80);  
		} }; t.schedule(80);  
    }
    
}