import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String name;
    private int HP;
    private int currentHP;
    private int speed;
    private boolean huntsYou = false;
    private boolean ghost = false;
    //private boolean isDefeated = false;
    private int agression;
    private int attack;
    private int defense;
    private String[] passMyTurn = new String[3];
     
    public Enemy(String name){
        if ("demoBeast".equals(name)){
            setImage("ppl1.png");
            this.name = name;
            this.HP = 20;
            this.currentHP = 20;
            this.speed = 1;
            this.huntsYou = true;
            this.ghost = false;
            //this.isDefeated = false;
            this.agression = 3;
            this.attack = 5;
            this.defense = 3;
            this.passMyTurn[0] = "\nThe eldrich creature shudders. \nParts of its alien body appear and disappear rapidly.";
            this.passMyTurn[1] = "\nPart of you is thankful this... \nthing won't follow you on your real adventure. \nWait, what?";
            this.passMyTurn[2] = "\nYou can't make heads or tails of this thing's anatomy...";
        
        } else if ("demoVigilant".equals(name)){
            setImage("ppl2.png");
            this.name = name;
            this.HP = 30;
            this.currentHP = 30;
            this.speed = 0;
            this.huntsYou = false;
            this.ghost = false;
            //this.isDefeated = false;
            this.agression = 3;
            this.attack = 10;
            this.defense = 4;
            this.passMyTurn[0] = "\nThis armorclad wight stands perfectly still\n...rattling with impatience.";
            this.passMyTurn[1] = "\nThis flavor text is a test.\nIf you are reading this, enemy.getRandomFlavor() is working.\nHm? No, I didn't say anything.";
            this.passMyTurn[2] = "\nThe watchman wedges its axe into a wall\nand looks down at the chain shackled to its leg.\nIt looks at your ring and howls!";
        
        }
    }
    
    public void act()
    {
        checkPlayerMovement();
    }
    
    public void checkPlayerMovement(){
        if (getWorld().getObjects(Player.class).isEmpty()){
            return;
        }
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
        
        int[] difference = {foeX, foeY};
        int[] steps = new int[2];
        
        for (int i = 0; i < difference.length; i++){
            if (difference[i] > 0){
                steps[i] = -speed;
            } else if (difference[i] < 0) {
                steps[i] = speed;
            }else {
                steps[i] = 0;
            }
        }
        
        int stepX = steps[0];
        int stepY = steps[1];
        
        //position before moving
        int oldX = getX();
        int oldY = getY();
        

        if (Math.abs(foeX) > Math.abs(foeY)) {
            //try horizontal
            setLocation(oldX + stepX, oldY);
            if (!ghost && (isTouching(Wall.class) || isTouching(AccessPoint.class))){
                setLocation(oldX, oldY + stepY);
                if (!ghost && (isTouching(Wall.class) || isTouching(AccessPoint.class))){
                    setLocation(oldX, oldY);
                    
                }
            }
        } else {
            //try vertical
            setLocation(oldX, oldY + stepY);
            if (!ghost && (isTouching(Wall.class) || isTouching(AccessPoint.class))){
                setLocation(oldX+ stepX, oldY);
                if (!ghost && (isTouching(Wall.class) || isTouching(AccessPoint.class))){
                setLocation(oldX, oldY);
                }
            }
        }
    }  
    public void standStill(){
        
    }
    
    //battle getters
    public int getHP() {
        return HP;
    }
    
    public int getCurrentHP(){
        return currentHP;
    }
    
    public void takeDamage(int amount){
        currentHP = Math.max(0, currentHP- amount);
    }
    
    public int getFoeAttack (){
        return attack;
    }
    
    public int getFoeDefense() {
        return defense;
    }
    
    public int getAggression(){
        return agression;
    }
    
    public String getFoeName () {
        return name;
    }
    
    public String getRandomFlavor (){
        int index = Greenfoot.getRandomNumber(passMyTurn.length);
        return passMyTurn[index];
    }
}
