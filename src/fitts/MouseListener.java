package fitts;

import java.awt.event.MouseEvent;
import java.awt.Point;

/**
 * handles mouse input in the workspace
 * @author tzeentch
 *
 */

public class MouseListener extends Object implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

	FittsMain root;
	
	
	/**
	 * creator
	 * @param m parent
	 */
	public MouseListener(FittsMain m)
	{	root = m;
		
	}
	
	/**
     * Define actions on mouse press according to current attempt etc...
     * @param e mouse event
     */
	public void mousePressed(MouseEvent e) 
    {  	AttemptData attempt = root.getAttempt();
		BlankArea blankArea = root.blankArea;
		
		//System.out.println("mouse");
		if(attempt==null || attempt.finished()) return;
		if(attempt.inType == AttemptData.KEY) return;
		
		//System.out.println("mouse2");
    	
    	Point m,n;
        int s = (int)blankArea.circle.width;
        n = new Point((int)blankArea.circle.x+s/2,(int)blankArea.circle.y+s/2);
        m = new Point((int)e.getX(),(int)e.getY());
        //System.out.println("mouse3");
        if( m.distance(n) <= s/2 ) 
        {// hit the target
        	//System.out.println("mouse4");
        	attempt.addHit(m,n,s);
        	//System.out.println("mouse5");
        	root.eventOutput(attempt.dumpInfo(attempt.target-1));
        	if(!attempt.finished()) 
        	{	
        		if(attempt.inType == AttemptData.MOUSE)
        			n = blankArea.createNewMouseTarget(attempt);
        		if(attempt.inType == AttemptData.LEARN)
        			n = blankArea.createNewLearnTarget(attempt);
        	
        	}
        	else root.finishAttempt();
        	
        }
        else 
        {	attempt.addMiss(m,n);
        	root.eventOutput("Miss !"+m.x+" "+m.y);
        }
        
    }
	
	/**
	 * Adds mouse movement to the current AttemptData
	 * @param e mouse event
	 */
    public void mouseMoved(MouseEvent e) {
    	AttemptData attempt = root.getAttempt();
			    	
    	if(attempt != null && attempt.target > -1 && attempt.target < attempt.maxTargets) 
    		attempt.addMove(new Point(e.getX(),e.getY()));
    	//eventOutput(count + "a",e);
    	    	
      }
    
    /* Currently unused mouse event handlers 
     * defined as abstract in parent - must be defined - do nothing*/
    
	public void mouseReleased(MouseEvent e) {
	      //  eventOutput("Mouse released (# of clicks: "
	      //         + e.getClickCount() + ")", e);
	}    
	       
    public void mouseDragged(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    
}
