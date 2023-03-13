/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawpuzzletemplate;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import static pedro.ieslaencanta.com.dawpuzzletemplate.Bubble.HEIGHT;
import static pedro.ieslaencanta.com.dawpuzzletemplate.Bubble.WIDTH;

/**
 *
 * @author DAWTarde
 */
public class Shotter {

    private Point2D posicion;
    private Bubble actual, siguiente;
    private float angulo;
    private float incremento = 180f / 128f;
    private boolean debug;
    private static final int WIDTH = 64, HEIGHT = 64;
    private static final float MIN_ANGLE = 0f, MAX_ANGLE = 180f;

    public Shotter(Point2D posicion) {
        this.posicion = posicion;
        this.actual = CreateBubble();
        this.siguiente = CreateBubble();
        this.angulo = 90.0f;
    }

    public Shotter(float x, float y) {
        this.posicion = new Point2D(x, y);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Bubble CreateBubble() {
        return new Bubble();
    }

    public Bubble shoot() {
        Bubble tempo = this.actual;
        tempo.setAngulo(angulo);
        this.actual = this.siguiente;
        this.siguiente = new Bubble();
        return tempo;
    }
    public Point2D getArrowPoint2D() {
        int c;
        int f;
        int fotograma;
        if (this.angulo <= 90) {
            float anguloinver = 90 - this.angulo;
            float relativo = anguloinver / 90.0f;
            fotograma = (int) (relativo * 64);
            c = fotograma % 16;
            f = fotograma / 16;
            if(this.angulo==0){
                c=15;
                f=3;
            }
        } else {
            fotograma = (int) ((180 - this.angulo) / this.incremento);
            c = 15 - fotograma % 16;
            f = 3 - fotograma / 16;
        }
        return new Point2D(c, f);
    }

    public void paint(GraphicsContext gc) {
        Resources r = Resources.getInstance();
          gc.drawImage(r.getImage("spriters"),
            //inicio de la posicion
            1,
            1805,
            WIDTH,
            40,
            //dibujar en el lienzo
            (this.posicion.getX() - WIDTH / 2) * Game.SCALE,
            (this.posicion.getY() - 40 / 2) * Game.SCALE,
            WIDTH * Game.SCALE,
            40 * Game.SCALE);

        Point2D p = getArrowPoint2D();
        if (this.angulo <= 90) {
            gc.drawImage(r.getImage("spriters"),
                    1 + (p.getX() * 65),
                    1545 + (p.getY() * 65),
                    WIDTH,
                    HEIGHT,
                    (this.posicion.getX() - WIDTH / 2) * Game.SCALE,
                    (this.posicion.getY() - HEIGHT / 2) * Game.SCALE,
                    WIDTH * Game.SCALE,
                    HEIGHT * Game.SCALE);
        }

        if (this.angulo > 90) {
            gc.drawImage(r.getImage("spriters"),
                    1 + (p.getX() * 65),
                    1545 + (p.getY() * 65),
                    WIDTH,
                    HEIGHT,
                    (this.posicion.getX() - WIDTH / 2+ WIDTH ) * Game.SCALE,
                    (this.posicion.getY() - HEIGHT / 2) * Game.SCALE,
                  - WIDTH * Game.SCALE,
                    HEIGHT * Game.SCALE);
        }
        //si se esta depurando
        if (this.isDebug()) {
            gc.setStroke(Color.RED);
            gc.fillOval(this.getPosicion().getX() * Game.SCALE - 5,
                    (this.getPosicion().getY()) * Game.SCALE - 5, 10, 10);
            gc.setStroke(Color.GREEN);
            gc.strokeText(this.angulo + "º ",
                    this.posicion.getX() * Game.SCALE,
                    this.posicion.getY() * Game.SCALE);

//                    this.getPosicion().getY(), (this.getPosicion().getX() - WIDTH / 2) *Game.SCALE, 
//                    (this.getPosicion().getY() - HEIGHT / 2) * Game.SCALE);
        }
        if (this.actual!=null){
        gc.drawImage(r.getImage("balls"),
            //inicio de la posicion
            this.actual.getBalltype().getX(),
            this.actual.getBalltype().getY(),
            Bubble.WIDTH,
            Bubble.HEIGHT,
            //dibujar en el lienzo
            (this.posicion.getX() - Bubble.WIDTH / 2) * Game.SCALE,
            (this.posicion.getY() - Bubble.HEIGHT / 2) * Game.SCALE,
            Bubble.WIDTH * Game.SCALE,
            Bubble.HEIGHT * Game.SCALE);
        }
    }

    public void Moveleft() {
        this.angulo += this.incremento;
        if (this.angulo > MAX_ANGLE) {
            this.angulo = MAX_ANGLE;
        }
    }

    public void Moverigth() {
        this.angulo -= this.incremento;
        if (this.angulo < MIN_ANGLE) {
            this.angulo = MIN_ANGLE;
        }
    }

    /**
     * @return the posicion
     */
    public Point2D getPosicion() {
        return posicion;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }
}
