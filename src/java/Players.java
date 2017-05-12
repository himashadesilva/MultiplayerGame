
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Himasha
 */
public class Players {
    
    private String[] players = new String[4];
    //private int[] scores = new int[4];
    //private int[][] positions = new int[4][2];
   
    ArrayList<Details> list = new ArrayList<>();  //create array list to put player details object (score and position)
    public int dots=0;          
    public boolean canMove=false;  //sate of a player can move or not, if all players are connected can move
    public boolean playersReady=false; //if all players ready , players will be placed
    
    public void initPlayers(){
        
        players[0]="P1";
        players[1]="P2";
        players[2]="P3";
        players[3]="P4";
        
        if(playersReady==false){
        int[] temp_pos = new int[2];
        temp_pos[0]=45;
        temp_pos[1]=45;               //init players with position of outside board, because all 4 players not connected
           
        Details detail1 = new Details(0,0,temp_pos);
        list.add(detail1);
        
        temp_pos[0]=45;
        temp_pos[1]=45;
           
        Details detail2 = new Details(1,0,temp_pos);
        list.add(detail2);
        
        temp_pos[0]=45;
        temp_pos[1]=45;
           
        Details detail3 = new Details(2,0,temp_pos);
        list.add(detail3);
        
        temp_pos[0]=45;
        temp_pos[1]=45;
           
        Details detail4 = new Details(3,0,temp_pos);
        list.add(detail4);
        }
        if(playersReady){              //all players are connected, so place 4 players at 4 corners
            
            
                list.get(0).pos[0]=0;
                list.get(0).pos[1]=0;
                
                list.get(1).pos[0]=44;
                list.get(1).pos[1]=0;
                
                 list.get(2).pos[0]=0;
                list.get(2).pos[1]=44;
                
                 list.get(3).pos[0]=44;
                list.get(3).pos[1]=44;
                
            
        
        }
    
    }
    
    
    public void move(int player, int movement, Board board){   //movement of a player
        
        if(canMove){      //only move player if can move, (if all players are connected)
        
        if(movement == 37){
          if(list.get(player-1).pos[0]==0)       //move ledt, if player at the left boarder , player will be appear in right side of boader
            list.get(player-1).pos[0]+=44;
          else
            list.get(player-1).pos[0]--;     //else move left
        }
        if(movement == 38){               //move up
            if(list.get(player-1).pos[1]==0)    
            list.get(player-1).pos[1]+=44;
            else
        list.get(player-1).pos[1]--;
        }
        if(movement == 39){             //move right
            if(list.get(player-1).pos[0]==44)    
            list.get(player-1).pos[0]=0;
            else
        list.get(player-1).pos[0]++;
        }
        if(movement == 40){              //move down
            if(list.get(player-1).pos[1]==44)    
            list.get(player-1).pos[1]=0;
            else
        list.get(player-1).pos[1]++;
        }
        //gotPoints(board, player);
        }
    }
    
    public void gotPoints(Board board, int player){ //check if players got any point from a move
        int index;
        char col;
        if(canMove){
        for(int i=0;i<12;i++){
            if(board.places[i][0]==list.get(player-1).pos[0] &&board.places[i][1]==list.get(player-1).pos[1] ){   //checkfor all dots
               //index = board.list.indexOf(list.get(player-1));
               index = i;
               col = board.dots[index];
               switch(col){
                    case 'B' :
                        list.get(player-1).score+=4;         //if hits blue dot , increase score by 4                
                        break;
                    case 'G' :
                        list.get(player-1).score+=2;        //if hit green dot, increase score by 2
                        break;
                    case 'R' :
                        list.get(player-1).score+=1;    //if hit red dot, increase score by 1
                        break;      
               }
               board.places[i][0]=-1;    //move dot that hit to -1,-1 position (out of the board)
               board.places[i][1]=-1;
               dots++;
           } else {
           }
        }
        for(int i=0;i<4;i++){     //check if collide two players
            if(list.get(player-1).pos[0]==list.get(i).pos[0] && list.get(player-1).pos[1]==list.get(i).pos[1] && (player-1!=i)){
                list.get(player-1).score-=3;
                list.get(i).score-=3;
                switch(i){
                    case 0:
                        list.get(i).pos[0]=0;
                        list.get(i).pos[1]=0;
                        break;
                    case 1:
                        list.get(i).pos[0] = 44;
                        list.get(i).pos[1] = 0;
                        break;
                    case 2:
                        list.get(i).pos[0] = 0;
                        list.get(i).pos[1] = 44;
                        break;
                    case 3:
                        list.get(i).pos[0] = 44;
                        list.get(i).pos[1] = 44;
                        break;    
                }
                
                
                switch(player-1){
                    case 0:
                        list.get(player-1).pos[0]=0;
                        list.get(player-1).pos[1]=0;
                        break;
                    case 1:
                        list.get(player-1).pos[0] = 44;
                        list.get(player-1).pos[1] = 0;
                        break;
                    case 2:
                        list.get(player-1).pos[0] = 0;
                        list.get(player-1).pos[1] = 44;
                        break;
                    case 3:
                        list.get(player-1).pos[0] = 44;
                        list.get(player-1).pos[1] = 44;
                        break;    
                }
            }
        }
        }
    }
    
    public void reset(){   //reset players
        list.clear();   //clear player details objects
        this.playersReady=false;   
        this.initPlayers();   //init players with score 0 ,
        this.playersReady=true;
        this.initPlayers();  //init players with position of 4 corners
    
    }
    
    public String printPlayers(){   //send player score and position as a part of a json
         StringBuffer sb = new StringBuffer("\"PLAYERS\":   [");
        //sb.append("");

        for(int i=0;i<4;i++){
            sb.append("[\""+players[i]+"\", "+list.get(i).score+", "+list.get(i).pos[0]+", "+list.get(i).pos[1]+"]");
            if(i!=3)
                sb.append(",");


       }
       sb.append("]");
        
        return sb.toString();
    }
    
    
    
}

class Details{   //detail object
    
    protected int player;
    protected int score;
    protected int[] pos= new int[2];
    
    public Details(int player, int score, int[] pos){
        this.player = player;
        this.score = score;
        this.pos[0] = pos[0];
        this.pos[1] = pos[1];
    } 
  

}