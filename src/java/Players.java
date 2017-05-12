
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
   
    ArrayList<Details> list = new ArrayList<>();
    public int dots=0;
    public boolean canMove=false;
    public boolean playersReady=false;
    
    public void initPlayers(){
        
        players[0]="P1";
        players[1]="P2";
        players[2]="P3";
        players[3]="P4";
        
        if(playersReady==false){
        int[] temp_pos = new int[2];
        temp_pos[0]=45;
        temp_pos[1]=45;
           
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
        if(playersReady){
            
            
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
    
    public int winner(){
    
        int winner;
        ArrayList<Integer> ar = new ArrayList<>();
        for(int i=0;i<4;i++){
        ar.add(list.get(i).score);
        }
        int max = Collections.max(ar);
        winner = ar.indexOf(max);
    return winner;
    }
    
    public void move(int player, int movement, Board board){
        
        if(canMove){
        
        if(movement == 37){
          if(list.get(player-1).pos[0]==0)    
            list.get(player-1).pos[0]+=44;
          else
            list.get(player-1).pos[0]--;
        }
        if(movement == 38){
            if(list.get(player-1).pos[1]==0)    
            list.get(player-1).pos[1]+=44;
            else
        list.get(player-1).pos[1]--;
        }
        if(movement == 39){
            if(list.get(player-1).pos[0]==44)    
            list.get(player-1).pos[0]=0;
            else
        list.get(player-1).pos[0]++;
        }
        if(movement == 40){
            if(list.get(player-1).pos[1]==44)    
            list.get(player-1).pos[1]=0;
            else
        list.get(player-1).pos[1]++;
        }
        //gotPoints(board, player);
        }
    }
    
    public void gotPoints(Board board, int player){
        int index;
        char col;
        if(canMove){
        for(int i=0;i<12;i++){
            if(board.places[i][0]==list.get(player-1).pos[0] &&board.places[i][1]==list.get(player-1).pos[1] ){
               //index = board.list.indexOf(list.get(player-1));
               index = i;
               col = board.dots[index];
               switch(col){
                    case 'B' :
                        list.get(player-1).score+=4;                       
                        break;
                    case 'G' :
                        list.get(player-1).score+=2;
                        break;
                    case 'R' :
                        list.get(player-1).score+=1;
                        break;      
               }
               board.places[i][0]=-1;
               board.places[i][1]=-1;
               dots++;
           } else {
           }
        }
        for(int i=0;i<4;i++){     //if colide
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
    
    public void reset(){
        list.clear();
        this.playersReady=false;  
        this.initPlayers();
        this.playersReady=true;
        this.initPlayers();
    
    }
    
    public String printPlayers(){
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

class Details{
    
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