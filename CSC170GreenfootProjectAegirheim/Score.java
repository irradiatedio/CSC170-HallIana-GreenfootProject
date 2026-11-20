import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Score here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Score extends Actor
{

    private int score = 0; 
    
    public Score() {
        updateImage();
    }
    
    public void addScore(int points) {
        score += points;
        updateImage();
    }
    
    public int getScore(){
        return score;
    }
    
    private void updateImage(){
        setImage(new GreenfootImage("Score: " + score, 24, Color.WHITE, new Color(0,0,0,0))); 
    }
    /**
     * Act - do whatever the Scoreboard wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
