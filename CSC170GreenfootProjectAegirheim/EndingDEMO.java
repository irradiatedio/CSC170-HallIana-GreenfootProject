import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ending here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndingDEMO extends World
{

    /**
     * Constructor for objects of class Ending.
     * 
     */
    public EndingDEMO()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        
        GreenfootImage bgImage = new GreenfootImage(1,1);
        bgImage.setColor(Color.BLACK);
        bgImage.drawRect(0,0,1,1);
        setBackground(bgImage);
        
        showText("This is the end of the prototype stage\nThis stage is meant for quick testing of implemented features for\nease of testing and grading.\nThank you.\n-Iana", 300, 100);
        Greenfoot.stop();
    }
}
