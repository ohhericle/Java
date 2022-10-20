package Final;

import java.awt.*;
import java.util.*;

/**
 *  Program 10
 *  FallingSand Particles
 *  CS108-1
 *  7/7/19
 *  @author  Eric Le
  */

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1; //does not move
  public static final int SAND = 2; //moves down
  public static final int WATER = 3; //moves down,left,right
  public static final int FART = 4; //moves in all directions
  public static final int FIRE = 5; //moves up
  public static final int ICE = 6; //does not move
  public static final int EARTH = 7; //
  public static final Color BROWN = new Color(102,51,0);

  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[8];  //initializes 8 string 
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "SAND";
    names[WATER] = "WATER";
    names[FART] = "FART";
    names[FIRE] = "FIRE";
    names[ICE] = "ICE";
    names[EARTH] = "EARTH";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int [numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
      grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[0].length; col++) {
              if (grid[row][col] == EMPTY) {
                    display.setColor(row, col, Color.black);
              }
                    else if (grid[row][col] == METAL){  //set metal 
                        display.setColor(row, col, Color.darkGray);
                    }
                    else if (grid[row][col] == SAND){  //set sand
                        display.setColor(row, col, Color.yellow);
                    }
                    else if (grid[row][col] == WATER){ //set water 
                        display.setColor(row, col, Color.blue);
                    }
                    else if (grid[row][col] == FART){ //set fart
                        display.setColor(row, col, Color.green);
                    }
                    else if (grid[row][col] == FIRE){ //set fire
                        display.setColor(row, col, Color.red);
                    }
                    else if (grid[row][col] == ICE){ //set ice
                        display.setColor(row, col, Color.cyan);
                    }
                    else if (grid[row][col] == EARTH){ //set rock
                        display.setColor(row, col, BROWN);
                    
              }
          }
      }
  }
      

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
      Random rand = new Random(); //gets random row and column point on grid
      int randRow = rand.nextInt(grid.length);
      int randCol = rand.nextInt(grid[0].length);
      int curr = grid[randRow][randCol]; //selected point
      boolean change = false;
      int changeRow = randRow; //row of position you're moving to
      int changeCol = randCol; //column of position you're moving to
      int changeCurr = EMPTY; //what element the current point will change to
      int changeType = EMPTY; //what element the point we're moving to will be
      
      if (curr == SAND && randRow+1 < grid.length && (grid[randRow+1][randCol] == EMPTY || grid[randRow+1][randCol] == WATER)) {//if sand
          change = true;
          changeType = SAND;
          if (grid[randRow+1][randCol] == WATER){ //if space below is water, then switch
              changeCurr = WATER;
          }
          changeRow = randRow+1;
          
      }else if (curr == WATER) { //if water
          changeType = WATER;
          int direction = rand.nextInt(3);
          if (direction == 0 && randCol-1 >= 0 && grid[randRow][randCol-1] == EMPTY) { //left
              change = true;
              changeCol = randCol-1;
          }else if (direction == 1 && randCol+1 < grid[0].length && grid[randRow][randCol+1] == EMPTY) { //right
              change = true;
              changeCol = randCol+1;
          }else if (direction == 2 && randRow+1 < grid.length && grid[randRow+1][randCol] == EMPTY) { //down
              change = true;
              changeRow = randRow+1;
          }
      }else if (curr == FART){ //if fart
          changeType = FART; 
          int direction = rand.nextInt(8);
          if (direction == 0 && randCol-1 >= 0 && grid[randRow][randCol-1] == EMPTY) { //left
              change = true;
              changeCol = randCol-1;
          }else if (direction == 1 && randCol+1 < grid[0].length && grid[randRow][randCol+1] == EMPTY) { //right
              change = true;
              changeCol = randCol+1;
          }else if (direction == 2 && randRow+1 < grid.length && grid[randRow+1][randCol] == EMPTY) { //down
              change = true;
              changeRow = randRow+1;
          }else if (direction == 3 && randRow-1 >= 0 && grid[randRow-1][randCol] == EMPTY) { //up 
              change = true;                   
              changeRow = randRow-1;
          } else if (direction == 4 && randRow-1>=0 && randCol-1 >=0 && grid[randRow-1][randCol-1] == EMPTY) { //up one, left one
              change = true;
              changeRow = randRow-1;                 
              changeCol = randCol-1;
          } else if (direction == 5 && randRow-1>=0 && randCol+1<grid[0].length && grid[randRow-1][randCol+1] == EMPTY) { //up one, right one
              change = true;
              changeRow = randRow-1;                 
              changeCol = randCol+1;
          } else if (direction == 6 && randRow+1<grid.length && randCol+1<grid[0].length && grid[randRow+1][randCol+1] == EMPTY) { // down one, right one
              change = true;
              changeRow = randRow+1;                 
              changeCol = randCol+1;
          } else if (direction == 7 && randRow+1<grid.length && randCol-1>=0 && grid[randRow+1][randCol-1] == EMPTY) { //down one, left one 
              change = true;
              changeRow = randRow+1;                 
              changeCol = randCol-1;
          }
      } else if (curr == EARTH && randRow+1 < grid.length && (grid[randRow+1][randCol] == EMPTY || grid[randRow+1][randCol] == WATER)) {//if earth
          change = true;
          changeType = EARTH;
          if (grid[randRow+1][randCol] == WATER){ //if space below is water, then soak up
              changeCurr = EARTH;
          }
          changeRow = randRow+1;
          
      }else if (curr == FIRE){
          change = true;
          if(randRow-1 >= 0){
              changeRow = randRow-1;
              if (grid[randRow-1][randCol] == METAL){
                  change = false;
              }else if (grid[randRow-1][randCol] == WATER || grid[randRow-1][randCol] == EARTH){
                  changeType = grid[randRow-1][randCol];
              }else if (grid[randRow-1][randCol] == ICE){
                  changeCurr = WATER;
              }else if (grid[randRow-1][randCol] == EMPTY || grid[randRow-1][randCol] == SAND){
                  changeType = FIRE;
              }else if (grid[randRow-1][randCol] == FART){
                  changeCurr = FIRE;
                  changeType = FIRE;
                  for (int i=1; i<9; i++){
                      if (randCol-i>=0){
                          if (grid[randRow][randCol-i] == EMPTY || grid[randRow][randCol-i] == SAND || grid[randRow][randCol-i] == FART){
                              grid[randRow][randCol-i] = FIRE; 
                          }
                          if (grid[randRow-1][randCol-i] == EMPTY || grid[randRow-1][randCol-i] == SAND || grid[randRow-1][randCol-i] == FART){
                              grid[randRow-1][randCol-i] = FIRE; 
                          }
                      }
                      if (randCol+i<grid[0].length){
                          if (grid[randRow][randCol+i] == EMPTY || grid[randRow][randCol+i] == SAND || grid[randRow][randCol+i] == FART){
                              grid[randRow][randCol+i] = FIRE; 
                          }
                          if (grid[randRow-1][randCol+i] == EMPTY || grid[randRow-1][randCol+i] == SAND || grid[randRow-1][randCol+i] == FART){
                              grid[randRow-1][randCol+i] = FIRE; 
                          }
                      }
                  }
              }
          }
      }
      
      if (change){
          grid[randRow][randCol] = changeCurr;
          grid[changeRow][changeCol] = changeType; 
      }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}

