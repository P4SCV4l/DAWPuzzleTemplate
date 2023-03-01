/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawpuzzletemplate;
import pedro.ieslaencanta.com.dawpuzzletemplate.Board;

/**
 *
 * @author DAWTarde
 */
public class BubbleGrid {
    private Bubble bubblegrid[][];
    private int startx, starty;
    private static final int ROWS=12;
    private static final int COLS=8;
    
    public BubbleGrid (int startx, int starty){
        this.startx = startx;
        this.starty = starty;
        this.bubblegrid= new Bubble[ROWS][];
        for(int i=0;i<this.bubblegrid.length;i++){
            if(i %2 ==0)
                this.bubblegrid[i]=new Bubble[COLS];
            else
                this.bubblegrid[i]=new Bubble[COLS-1];
        }
        
    }
    public BubbleGrid (Bubble bubblegrid[][], int startx, int starty){
        bubblegrid = new Bubble [8][9];
       // for (i=)
    }
    public boolean collision(Bubble b){
        boolean colission=false;
        if (this.bubblegrid.posicion==this.Board.game_zone){
        return colission;
        }
    }
}
