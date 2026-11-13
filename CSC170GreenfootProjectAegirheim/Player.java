import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private int speed = 3;
    private boolean isMoving = false;
    private int hp = 0;
    private int currentHP = 0;
    private int attack = 0;
    private int defense = 0;
    private boolean isBrave = true;
    private boolean hasGold = true;
    
    //for collision
    int length = 0;
    
    public Player(){
        length = getImage().getWidth();
        
    }

    /**
     * Act - do whatever the player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        isMoving = false;
        movePlayer();
        checkAccessSwitch();
        checkEscapeDEMO();
        touchEnemyDEMO();
    }
    
    private void movePlayer(){
        int currentX = getX();
        int currentY = getY();
        
        int oldX = getX();
        int oldY = getY();
        
        int changeX = speed;
        int changeY = speed;
        
        //keyboard input, process movement and wall collision.

        if (Greenfoot.isKeyDown("w")){
            setLocation(currentX,currentY - changeY);
            isMoving = true;
            if (isTouching(Wall.class) || isTouching(AccessPoint.class)){
                setLocation(oldX, oldY);
            }
        }
        else if (Greenfoot.isKeyDown("s")){
            setLocation(currentX,currentY + changeY);
            isMoving = true;
            if (isTouching(Wall.class) || isTouching(AccessPoint.class)){
                setLocation(oldX, oldY);
            }
        }
        else if (Greenfoot.isKeyDown("a")){
            setLocation(currentX - changeX ,currentY);
            isMoving = true;
            if (isTouching(Wall.class) || isTouching(AccessPoint.class)){
                setLocation(oldX, oldY);
            }
        }
        else if (Greenfoot.isKeyDown("d")){
            setLocation(currentX + changeX ,currentY);
            isMoving = true;
            if (isTouching(Wall.class) || isTouching(AccessPoint.class)){
                setLocation(oldX, oldY);
            }
        }
    }
    
    public boolean playerMovement(){
        return isMoving;
    }
    
    private void checkAccessSwitch(){
        AccessSwitch intersectingSwitch = (AccessSwitch) getOneIntersectingObject(AccessSwitch.class);
        
        if (intersectingSwitch != null){
            intersectingSwitch.activate();
        }
    }
    
    private void checkEscapeDEMO(){
        /*this Escape method will stop the prototype when you pass over it. 
        *end of experience, congradulations, please hit reset. In the full project the Escape
        *method proper will progress the map when you pass over it, and if it is
        *level 3, process the ending.
        */
       if (isTouching(Escape.class)){
           Greenfoot.stop();
           return;
       }
    }
    
    private void touchEnemyDEMO(){
        /*this method is a prototype of a lose scenario in battle. The enemy touches you,
         * it disappears, you return to the respawn point.
        */
        if (isTouching(Enemy.class)){
            removeTouching(Enemy.class);
            
            SeaBed world = (SeaBed) getWorld();
            int[]respawnPoint = world.getRespawnPoint();
            
            setLocation(respawnPoint[0], respawnPoint[1]);
        }
    }
}
