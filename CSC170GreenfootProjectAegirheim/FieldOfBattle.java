import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FieldOfBattle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FieldOfBattle extends World
{
    //turn states
    private static final int PLAYER_TURN = 0;
    private static final int ENEMY_TURN = 1;
    private static final int BATTLE_WIN = 2;
    private static final int BATTLE_LOSS = 3;
    private static final int BATTLE_COWARD = 4;
    private static final int LOSE_STATE = 5;
    
    private int currentState;
    private int nextState;
    private boolean waitForEnter = false;
    
    private Player player;
    private Enemy enemy;
    private Score score;
    private World previousWorld;
    private String battleLog = "";
    
    /**
     * Constructor for objects of class FieldOfBattle.
     * 
     */
    public FieldOfBattle(Player player, Enemy enemy)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        
        GreenfootImage bgImage = new GreenfootImage(1,1);
        bgImage.setColor(Color.BLACK);
        bgImage.drawRect(0,0,1,1);
        setBackground(bgImage);
        
        this.player = player;
        this.enemy = enemy;
        this.previousWorld = player.getWorld();
        
        if (!previousWorld.getObjects(Score.class).isEmpty()){
            this.score = (Score)previousWorld.getObjects(Score.class).get(0);
        }
        
        this.currentState = PLAYER_TURN;
        this.battleLog = "A " + enemy.getFoeName() + " approaches... \n[1] Attack  [2] Heal  [3] Flee";
        updateDisplay();
        
    }
    
    public void act(){
        if (waitForEnter){
            //freeze the battle until player presses [Enter]
            if("enter".equals(Greenfoot.getKey())){
                waitForEnter= false;
                currentState= nextState;
                Greenfoot.getKey();
            
                if (currentState == PLAYER_TURN){
                    battleLog = "Now's your chance: [1] Attack  [2] Heal  [3] Flee\n";
                    updateDisplay();
                //}else if (currentState == ENEMY_TURN){
                    //handleEnemyTurn();
                }else if (currentState == BATTLE_WIN){
                    //handlePlayerVictory();
                    Greenfoot.setWorld(previousWorld);
                }else if (currentState == BATTLE_LOSS) {
                    //handlePlayerDefeat();
                    player.respawn();
                    Greenfoot.setWorld(previousWorld);
                }else if (currentState == BATTLE_COWARD){
                    //handleCowardice();
                    Greenfoot.setWorld(previousWorld);
                }else if (currentState == LOSE_STATE){
                    Greenfoot.stop();
                }
            }
            return;
        }
            //Execute turn
        if (currentState == PLAYER_TURN){
            handlePlayerInput();
        }else if (currentState == ENEMY_TURN){
            handleEnemyTurn();
        }
    }
    
    //display turn block, wait for player to proceed
    private void pauseWithBlock(String turnBlock, int stateAfterEnter){
        this.battleLog = turnBlock + "\n\n[Press Enter to continue]";
        this.nextState = stateAfterEnter;
        this.waitForEnter = true;
        Greenfoot.getKey();
        updateDisplay();
    }
    
    //random event generator; 1d10
    private int roll1d10(){
        return Greenfoot.getRandomNumber(10) + 1;
    }
    
    private void handlePlayerInput(){
        String key = Greenfoot.getKey();
        if (key == null) {
            return;
        }
        
        String turnBlock ="";
        
        if (key.equals("1")){
            //you smack them with your oar
            int roll = roll1d10();
            int totalAttack = (roll/2)+ player.getAttack();
            
            if (roll <= enemy.getFoeDefense()){
                turnBlock += enemy.getFoeName() + " evaded your strike with your oar!";
            } else {
                enemy.takeDamage(totalAttack);
                turnBlock += "You attack for " + totalAttack;
            }
            checkEndOfPlayerTurn(turnBlock);
        } else if (key.equals("2")){
            //heal option
            int healRoll = roll1d10();
            int healAmount = healRoll/2;
            if (healAmount == 5){
                turnBlock += "You pull a bottle from the sand and drink...\nYou don't know what you just drank but //wow//.\n+ " + healAmount;
            }else if (healAmount >= 2 && healAmount <= 4){
                turnBlock += "You pull a bottle from the sand and drink... \nSalty, but you feel better.\n+ " + healAmount;
            }else if (healAmount == 1){
                turnBlock += "You pull a bottle from the sand and drink...\nOh... why is it thick?\n+ " + healAmount;
            }else if (healAmount == 0) {
                turnBlock += "You pull a bottle from the sand and drick...\nIt was empty!";
            }
            player.takeDamage(-healAmount);
            checkEndOfPlayerTurn(turnBlock);
        }else if (key.equals("3")){
            //flee attempt
            int fleeRoll = roll1d10();
            if (fleeRoll >= 6){
                turnBlock += "You are fleeing the field of battle... \nFled.";
                handleCowardice(turnBlock);
            } else {
                turnBlock += "You are fleeing the field of battle... The enemy chases you down!";
                checkEndOfPlayerTurn(turnBlock); //enemy gets their turn
            } 
        }
    }
    
    private void checkEndOfPlayerTurn(String turnBlock){
        if (enemy.getCurrentHP() <= 0 ){
            handlePlayerVictory(turnBlock);
        } else {
            pauseWithBlock(turnBlock,ENEMY_TURN);
        }
    }
    
    private void handleEnemyTurn(){
        String turnBlock = "";
        int agressionRoll = roll1d10();
        //it's about this time I realize I mispelled 'aggression'. That'll be a problem for thanksgiving recess Iana
        
        /*
         * [][][][]TO DO - FIX AGRESSION TYPO[][][][]
         */
        
        if (agressionRoll < enemy.getAggression()){
            int index = Greenfoot.getRandomNumber(3);
            turnBlock += enemy.getRandomFlavor();
        } else {
            int attackRoll = roll1d10();
            
            if (attackRoll > player.getDefense()){
                int damage = enemy.getFoeAttack();
                player.takeDamage(damage);
                turnBlock += enemy.getFoeName() + " attacks for " + damage + "!";
                
                
            } else {
                turnBlock += "Enemy missed!";
            }
        }
        
        if (player.getCurrentHP() <= 0){
            handlePlayerDefeat(turnBlock);
        } else {
            pauseWithBlock(turnBlock, PLAYER_TURN);
        }
    }
    
    //UI Display, text based
    
    private void updateDisplay() {
        showText("The Oarsman HP: " + player.getCurrentHP() + "/" + player.getHP(), 120, 50);
        showText(enemy.getFoeName() + " HP: "+ enemy.getCurrentHP() + "/" + enemy.getHP(), 480, 50);
        showText(battleLog,300,220);
    }
    
    //outcomes
    private void handlePlayerVictory(String turnBlock){
        score.addScore(10);
        turnBlock += "\n" + enemy.getFoeName() + " falls to the sand defeated.";
        pauseWithBlock(turnBlock,BATTLE_WIN);
        //if (previousWorld != null && enemy.getWorld() == previousWorld){
            //previousWorld.removeObject(enemy);
        //}
        //updateDisplay();
    }
    
    private void handlePlayerDefeat(String turnBlock){
        boolean checkGold = player.getGold();
        score.addScore(-4);
        turnBlock += "\nYou have fallen in battle...";
        
        if (checkGold == false){
            turnBlock += "\nBut you have no offering for Aegir...\nYou never stand back up.\nI'm sorry.";
            pauseWithBlock(turnBlock,LOSE_STATE);
        } else {
            turnBlock += "\nIn your dream, you see Aegir, \ngod of the sea's bounty, takes your gold ring.\nYou come to somewhere in the dungeon.";
            checkGold = false;
            player.setGold(checkGold);
            pauseWithBlock(turnBlock,BATTLE_LOSS);
            //updateDisplay();
        }
    }
    
    private void handleCowardice(String turnBlock){
        player.setBrave(false);
        score.addScore(-1);
        
        if (previousWorld != null && enemy.getWorld() == previousWorld){
            previousWorld.removeObject(enemy);
        }
        pauseWithBlock(turnBlock,BATTLE_COWARD);
    }
}
