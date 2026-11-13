import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class seaBed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SeaBed extends World
{

    /**
     * Constructor for objects of class seaBed.
     * 
     */
    //make tile size
    //a tile size of 65 is small enough to prevent slipping into the walls, and 
    //big enough to make rounding corners and corridors forgiving.
    private static final int TILE_SIZE = 65;
    // create the coord array for the Respawn Point Actor
    private int[] respawnCoords = new int[2];
    
    //the game is not randomly generated, so each map is hardcoded on a 2d array
    //w = wall
    //. = empty space
    //p = player
    //D = demo beast
    
    //A= Access Point 1
    //a= Access Switch 1
    //B= Access Point 2
    //b= Access Switch 2
    //C= Access Point 3
    //c= Access Swtich 3
    
    //e = Escape
    //r = Respawn Point
    
    private char[][] seaBedDEMO = {
        {'w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
        {'w','p','.','.','.','A','.','.','.','.','.','.','D','w'},
        {'w','r','.','.','.','w','w','w','.','w','w','w','w','w'},
        {'w','.','a','.','.','w','.','w','.','.','.','.','.','w'},
        {'w','.','.','.','.','w','.','w','w','w','w','w','.','w'},
        {'w','w','w','w','w','.','.','.','.','.','.','.','.','w'},
        {'w','.','.','.','.','.','w','w','w','w','.','w','w','w'},
        {'w','.','.','.','w','w','w','.','b','w','.','w','.','w'},
        {'w','.','.','.','.','.','w','.','w','w','.','w','.','w'},
        {'w','w','w','w','w','.','w','.','w','.','.','.','.','w'},
        {'w','.','.','.','w','.','.','.','.','.','.','w','w','w'},
        {'w','.','e','.','w','w','w','w','w','w','.','.','.','w'},
        {'w','.','.','.','B','.','.','.','.','.','.','w','.','w'},
        {'w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
    };
       
        
        
    
    //OH. I could make a custom level mode with this. To Do(if time), implement a Level Editor???
    
    public SeaBed()
    {    
        // Sets the world to a size equal to 14 x 14 tiles
        super(14 * TILE_SIZE, 14 * TILE_SIZE, 1);
        // Make tan
        Color tan = new Color(210, 180, 140);
        // Make one pixel of tan. Set as repeating background.
        GreenfootImage bgImage = new GreenfootImage(1,1);
        bgImage.setColor(tan);
        bgImage.drawRect(0,0,1,1);
        setBackground(bgImage);

        generateMap();

        //prepare();
        prepare();
    }
    
    private void generateMap(){
        int[][] accessPair1 = {{-1,-1},{-1,-1}};
        int[][] accessPair2 = {{-1,-1},{-1,-1}};
        int[][] accessPair3 = {{-1,-1},{-1,-1}};
     
        //accessPair_[0][] are for the coords for the Access Point, while accessPair[1][] are for the Access Switch
        //accessPair_[][0] is x, accessPair_[][1] is y
        //respawnPoint[0] is x, respawnPoint[1] is y
        
        for (int row = 0 ; row < seaBedDEMO.length; row++){
            for (int col = 0 ; col < seaBedDEMO[row].length; col++){
                //calculate placement of pixels, centering on the actor
                int x = (col * TILE_SIZE) + (TILE_SIZE / 2);
                int y = (row * TILE_SIZE) + (TILE_SIZE / 2);
                
                //setting up tiles
                if (seaBedDEMO[row][col] == 'w') {
                    addObject(new Wall(), x, y);
                } else if (seaBedDEMO[row][col] == 'p'){
                    addObject(new Player(), x, y);
                } else if (seaBedDEMO[row][col] == 'D'){
                    addObject(new Enemy("demoBeast"), x, y);
                } else if (seaBedDEMO[row][col] == 'A'){
                    //create the first access point
                    accessPair1[0][0] = col;
                    accessPair1[0][1] = row;
                    //addObject(new accessPoint(), x, y);
                } else if (seaBedDEMO[row][col] == 'a'){
                    //create the first access switch
                    accessPair1[1][0] = col;
                    accessPair1[1][1] = row;
                } else if (seaBedDEMO[row][col] == 'B'){
                    //create the second access point
                    accessPair2[0][0] = col;
                    accessPair2[0][1] = row;
                } else if (seaBedDEMO[row][col] == 'b'){
                    //create the second access switch
                    accessPair2[1][0] = col;
                    accessPair2[1][1] = row;
                } else if (seaBedDEMO[row][col] == 'C'){
                    //create the third access point
                    accessPair3[0][0] = col;
                    accessPair3[0][1] = row;
                } else if (seaBedDEMO[row][col] == 'c'){
                    //create the third access switch
                    accessPair3[1][0] = col;
                    accessPair3[1][1] = row;
                } else if (seaBedDEMO[row][col] == 'e'){
                    addObject(new Escape(), x, y);
                } else if (seaBedDEMO[row][col] == 'r'){
                    respawnCoords[0] = x;
                    respawnCoords[1] = y;
                }// entering a '.' in the array will generate an empty space
            }
        }
        int progressPair = 0;
        lockAndKey(accessPair1);
        //progressPair++;
        lockAndKey(accessPair2);
        //progressPair++
        lockAndKey(accessPair3);
    }
        
    private void lockAndKey(int[][] accessCoords){
        //check for if the pairs are active or one is missing
        if(accessCoords[0][0] == -1 || accessCoords[1][0] == -1){
            return;
            //Level Editor idea: add a warning for incomplete pairs?
        }
        //calculate placement for the Access Point, centering on the actor
        int pointX = (accessCoords[0][0] * TILE_SIZE) + (TILE_SIZE / 2);
        int pointY = (accessCoords[0][1] * TILE_SIZE) + (TILE_SIZE / 2);
        
        //calculate placement for the Access Switch, centering on the actor
        int switchX = (accessCoords[1][0] * TILE_SIZE) + (TILE_SIZE / 2);
        int switchY = (accessCoords[1][1] * TILE_SIZE) + (TILE_SIZE / 2);
        
        AccessPoint accessPoint = new AccessPoint();
        addObject(accessPoint, pointX, pointY);
        addObject(new AccessSwitch(accessPoint),switchX, switchY);
        
    }
    
    public int[] getRespawnPoint(){
        return respawnCoords;
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Player player = new Player();
    }
}
