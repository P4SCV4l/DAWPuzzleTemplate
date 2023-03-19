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

//    public BubbleGrid(Bubble bubblegrid[][], int startx, int starty) {
//        bubblegrid = new Bubble[startx][starty];
//    }

    //Este método se dedica a mostrar el contenido de todos lo indices del grid que no sean nulos.
    public void paint(GraphicsContext gc) {
        for (int i = 0; i < this.bubblegrid.length; i++) {
            for (int j = 0; j < this.bubblegrid[i].length; j++) {
                if (this.bubblegrid[i][j] != null) {
                    this.bubblegrid[i][j].paint(gc);
                }
            }
        }
    }

    //Este método existe para poder saber cuando un bola choca contra el techo y donde ponerla,
    //detectar las colisiones con otras bolas y donde ubicarlas,
    //también a diferenciar el posicionamiento de las bolas dependiendo de si la fila es par
    //o impar y por último poder llamar al método análisis y poder explotar las bolas.
    public boolean collision(Bubble b) {
        boolean colission = false;
        //boolean par = false;
//        int f = 0;
        if (b.getPosicion().getY() - Bubble.HEIGHT / 2 <= this.starty) {
            colission = true;
            //par = true;
        } else {
            for (int i = bubblegrid.length-1; i >= 0 && !colission; i--) {
                for (int j = this.bubblegrid[i].length-1; j >=0  && !colission; j--) {
                    if (this.bubblegrid[i][j] != null) {
                        double distancia = this.bubblegrid[i][j].getPosicion().distance(b.getPosicion());
                        if (distancia <= 16) {
                            //System.out.println(distancia);
                            //par=true;
                            colission = true;
                        }
                    }
                }
            }
        }
        if (colission /*&& par*/) {
            int f, c;
            //for(int i=0;i<this.bubblegrid.length && colission==true;i++){
            //    for(int j=0;j<this.bubblegrid[i].length;j++){
            b.stop();
            System.out.println(b.getPosicion().getY());
            System.out.println(this.starty);
            f = (int) ((b.getPosicion().getY() - this.starty) / Bubble.HEIGHT);
            //Revisar f porque se sale del límite del tamaño del grid.
            if(f<0){
                    f=(Math.abs(39 - this.starty) / Bubble.HEIGHT);
                }
            if (f % 2 == 0) {

                c = (int) ((b.getPosicion().getX() - this.startx) / Bubble.WIDTH);
                
                b.setPosicion(new Point2D(
                        this.startx + c * Bubble.WIDTH + Bubble.WIDTH / 2,
                        this.starty + f * Bubble.HEIGHT + Bubble.HEIGHT / 2)
                );
            } else {
                c = (int) ((b.getPosicion().getX() - this.startx - Bubble.WIDTH / 2) / Bubble.WIDTH);
                b.setPosicion(new Point2D(
                        this.startx + (c * Bubble.WIDTH) + Bubble.WIDTH,
                        this.starty + f * Bubble.HEIGHT + Bubble.HEIGHT / 2)
                );
            }
            this.bubblegrid[f][c] = b;
            //}
            Point2D iguales[]= new Point2D[80];
            analisis(c, f, b.getBalltype(), iguales, 0);
            int contador=conteo(iguales);
            if(contador>=3){
                for(int i=0;i<contador;i++){
                    int x=(int)(iguales[i].getX());
                    int y=(int)(iguales[i].getY());
                    bubblegrid[y][x]=null;
                }
            }
        }
        return colission;
    }
    //Este método nos permite saber si en algun índice del vector ya existe o está el punto de la bola que queremos comparar.
    public boolean existe (Point2D iguales[], Point2D punto){
        boolean existe=false;
        for(int i=0;i<iguales.length;i++){
            if(iguales[i]!=null){
                int x =(int)punto.getX();
                int y =(int)punto.getY();
                if(iguales[i].getX()==x && iguales[i].getY()== y){
                    existe=true;
                }
            }
        }
        
        return existe;
    }
    //Este método nos indicará si existe un Point2D en el vector y si no es así que introduzca uno.
    public void insertar (Point2D iguales[], Point2D punto){
        for(int i=0;i<iguales.length;i++){
                if(iguales[i]==null){
                    iguales[i]=punto;
                    break;
                }
        }
    }
    //Este método nos ayuda a saber cuál es el numero de índices que acaba poseyendo el vector
    //que agrupara las bolas.
    public int conteo(Point2D iguales[]){
        int contador=0;
        for(int i=0;i<iguales.length;i++){
            if(iguales[i]==null){
                break;
            }
            contador++;
        }
        return contador;
    }
    //Este método lo que va ha hacer es comprobar en el vector de iguales el punto en cuestión,
    //después lo que hacemos es evaluar la fila y columna donde se encuentra para poder determinar
    //hacia donde es que podemos analizar sin salirnos de los límites.
    public void analisis (int c, int f, BubbleType color, Point2D iguales[], int numerollama){
        if (numerollama>=50){
            return;
        }
        Point2D punto= new Point2D(c, f);
        boolean estaen =existe(iguales, punto);
        if(bubblegrid[f][c]!=null && bubblegrid[f][c].getBalltype()==color && !estaen){
            insertar(iguales, punto);
            //Filas pares.
            //Salir por la izquierda.
            if(c>0){
                analisis(c-1, f, color, iguales, numerollama+1);
            }
            if(f%2==0){
                //Salir por la derecha.
                if(c+1<bubblegrid[0].length){
                    analisis(c+1, f, color, iguales, numerollama+1);
                }
                //Salir por arriba.
                if(f>0 && c<bubblegrid[0].length-2){
                    analisis(c, f-1, color, iguales, numerollama+1);
                }
                //Salir por arriba a la izquierda.
                if(c>0 && f>0){
                    analisis(c-1, f-1, color, iguales, numerollama+1);
                }
                //Salir por abajo.
                if(f<bubblegrid.length-1 && c<bubblegrid[1].length){
                    analisis(c, f+1, color, iguales, numerollama+1);
                }
                //Salir por abajo a la izquierda.
                if(c>0 && f>bubblegrid.length-1){
                    analisis(c-1, f+1, color, iguales, numerollama+1);
                }
                //Filas impares.
            }else{
                //Salir por la derecha.
                if(c+1<bubblegrid[1].length){
                    analisis(c+1, f, color, iguales, numerollama+1);
                }
                //Salir por arriba.
                if(f>0 && c<bubblegrid[1].length-1){
                    analisis(c, f-1, color, iguales, numerollama+1);
                }
                //Salir por abajo.
                if(f<bubblegrid.length-1 && c<bubblegrid[1].length){
                    analisis(c, f+1, color, iguales, numerollama+1);
                }
                //Salir por abajo a la derecha.
                if(c<bubblegrid[1].length-1 && f<bubblegrid.length-1){
                    analisis(c+1, f+1, color, iguales, numerollama+1);
                }
                //Salir por arriba a la derecha.
                if(c<bubblegrid[1].length-1 && f>0){
                    analisis(c+1, f-1, color, iguales, numerollama+1);
                }
            }
        }
    }
    //Este método sirve para vaciar el grid.
    public void resetGrid(){
        for(int i=0;i<bubblegrid.length;i++){
            for(int j=0;j<bubblegrid[i].length;j++){
                bubblegrid[i][j]=null;
            }
        }
    }
    //Este método rellena las tres primeras filas del grid con bolas de color aleatorio.
    public void rellenarGrid(){
        double variaciónx=8;      
        for (int j=0; j<8 ;j++){   
            bubblegrid[0][j]= new Bubble(this.startx+variaciónx, this.starty+Bubble.HEIGHT/2, BubbleType.values()[(int) (Math.random() * BubbleType.values().length)]);
            variaciónx+=16;
        }
        double variaciónimparesx=16;
        for (int j=0; j<7 ;j++){   
            bubblegrid[1][j]= new Bubble(this.startx+variaciónimparesx, this.starty+Bubble.HEIGHT+Bubble.HEIGHT/2, BubbleType.values()[(int) (Math.random() * BubbleType.values().length)]);
            variaciónimparesx+=16;
        }
        variaciónx=8;
        for (int j=0; j<8 ;j++){   
            bubblegrid[2][j]= new Bubble(this.startx+variaciónx, this.starty+Bubble.HEIGHT*2+Bubble.HEIGHT/2, BubbleType.values()[(int) (Math.random() * BubbleType.values().length)]);
            variaciónx+=16;
        }
    }
    // public void initGrid(Level nivel){
        
    // }
}
