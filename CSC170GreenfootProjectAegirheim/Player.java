import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private Score score;
    
    private int speed = 3;
    private boolean isMoving = false;
    private int hp = 20;
    private int currentHP = 20;
    private int attack = 5;
    private int defense = 4;
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
        touchEnemy();
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
        
        if ((intersectingSwitch != null) && (!intersectingSwitch.isActivated())) {
            changeScore(3);
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
           Greenfoot.setWorld(new EndingDEMO());
       }
    }
    
    private void touchEnemy(){
        if (isTouching(Enemy.class)){
            Enemy touchedEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
            
            if (touchedEnemy != null){
                SeaBed currentWorld = (SeaBed) getWorld();
                
                currentWorld.removeObject(touchedEnemy);
                Greenfoot.setWorld(new FieldOfBattle(this, touchedEnemy));
            }   
        }
    }
    
    public void respawn(){
        currentHP = hp;
        
        SeaBed world = (SeaBed) getWorld();
        int[]respawnPoint = world.getRespawnPoint();
        setLocation(respawnPoint[0], respawnPoint[1]);
    }
    
    public void takeDamage(int amount){
        currentHP = Math.max(0, currentHP- amount);
    }
    
    public void changeScore(int points) {
    if (getWorld() != null && !getWorld().getObjects(Score.class).isEmpty()) {
        Score scoreActor = (Score) getWorld().getObjects(Score.class).get(0);
        scoreActor.addScore(points);
    }
}

    
    //battle getters / setter
    public int getHP() {
        return hp;
    }
    
    public int getCurrentHP(){
        return currentHP;
    }
    
    public int getAttack (){
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public boolean getGold() {
        return hasGold;
    }
    
    public void setGold(boolean hasGold){
        this.hasGold = hasGold;
    }
    
    public boolean getBrave(){
        return isBrave;
    }
    
    public void setBrave(boolean isBrave){
        this.isBrave = isBrave;
    }
}
