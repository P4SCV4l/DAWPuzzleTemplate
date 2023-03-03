/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawpuzzletemplate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author DAWTarde
 */
public class BubbleGrid {

    private Bubble bubblegrid[][];
    private int startx, starty;
    private static final int ROWS = 12;
    private static final int COLS = 8;

    public BubbleGrid(int startx, int starty) {
        this.startx = startx;
        this.starty = starty;
        this.bubblegrid = new Bubble[ROWS][];
        for (int i = 0; i < this.bubblegrid.length; i++) {
            if (i % 2 == 0) {
                this.bubblegrid[i] = new Bubble[COLS];
            } else {
                this.bubblegrid[i] = new Bubble[COLS - 1];
            }
        }

    }

    public BubbleGrid(Bubble bubblegrid[][], int startx, int starty) {
        bubblegrid = new Bubble[8][9];
    }

    public void paint(GraphicsContext gc) {
        for (int i = 0; i < this.bubblegrid.length; i++) {
            for (int j = 0; j < this.bubblegrid[i].length; j++) {
                if (this.bubblegrid[i][j] != null) {
                    this.bubblegrid[i][j].paint(gc);
                }
            }
        }
    }

    public boolean collision(Bubble b) {
        boolean colission = false;

        if (b.getPosicion().getY() - Bubble.HEIGHT / 2 <= this.starty) {
            colission = true;
        } else {
            for (int i = 0; i < this.bubblegrid.length && !colission; i++) {
                for (int j = 0; j < this.bubblegrid[i].length && !colission; j++) {

                    if (this.bubblegrid[i][j] != null) {
                        double distancia = this.bubblegrid[i][j].getPosicion().distance(b.getPosicion());
                        if (distancia <= 16) {
                            System.out.println(distancia);

                            colission = true;
                        }
                    }
                }
            }
        }

        if (colission) {
            int f, c;
            //for(int i=0;i<this.bubblegrid.length && colission==true;i++){
            //    for(int j=0;j<this.bubblegrid[i].length;j++){
            b.stop();
            f = (int) ((b.getPosicion().getY() - this.starty) / Bubble.HEIGHT);
            c = (int) ((b.getPosicion().getX() - this.startx) / Bubble.WIDTH);
            this.bubblegrid[f][c] = b;
            b.setPosicion(new Point2D(
                    this.startx + c * Bubble.WIDTH + Bubble.WIDTH / 2,
                    this.starty + f * Bubble.HEIGHT + Bubble.HEIGHT / 2)
            );
            //    }
            //}
        }
        return colission;
    }

}
