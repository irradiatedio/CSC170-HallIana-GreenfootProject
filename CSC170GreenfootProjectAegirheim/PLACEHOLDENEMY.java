import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PLACEHOLDENEMY here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PLACEHOLDENEMY extends Actor
{
    private int speed = 1;
    /**
     * Act - do whatever the PLACEHOLDENEMY wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        checkPlayerMovement();
    }
    
    public void checkPlayerMovement(){
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        
        if (player!= null) {
            if (player.playerMovement()){
                pursuePlayer(player);
            } else {
                standStill();
            }
        }
    }
    
    public void pursuePlayer(Player player){
        int foeX = getX() - player.getX();
        int foeY = getY() - player.getY();

        if (Math.abs(foeX) > Math.abs(foeY)) {
            setLocation(getX() + (foeX > 0 ? -speed : speed), getY());
        } else {
            setLocation(getX(), getY() + (foeY > 0 ? -speed : speed));
        }
    }   
    
    public void standStill(){
        
    }
}
