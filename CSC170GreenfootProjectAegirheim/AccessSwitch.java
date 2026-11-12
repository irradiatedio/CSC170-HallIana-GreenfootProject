import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class accessSwitch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AccessSwitch extends Actor
{
    /**
     * Act - do whatever the accessSwitch wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private AccessPoint connectedAccessPoint;
    private boolean isActivated = false;
    
    public AccessSwitch(AccessPoint DEBUGACCESS){
        this.connectedAccessPoint = DEBUGACCESS;
    }
    
    public void act()
    {
        // Add your action code here.
    }
    
     public void activate(){
        if (!isActivated){
            isActivated = true;
            
            if (connectedAccessPoint != null && connectedAccessPoint.getWorld() != null) {
                getWorld().removeObject(connectedAccessPoint);
            }
        }
    }
}
