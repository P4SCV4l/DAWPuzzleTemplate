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
    //Este método shoot lo que hace es ir asignando el valor de a bola actual a la tempo(que será la que dispararemos),
    //luego la actual pasará a ser la que anteriormente fue la siguiente y la siguiente pasa a crear una nueva bola.
    public Bubble shoot() {
        Bubble tempo = this.actual;
        tempo.setAngulo(angulo);
        this.actual = this.siguiente;
        this.siguiente = new Bubble();
        return tempo;
    }
    //Este método getter se dedica a ir eligiendo la flecha que debemos elegir por cada angulo.
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
    //Este método se dedica a imprimir por pantalla todo lo relacionado al shotter.
    public void paint(GraphicsContext gc) {
        //Aquí se muestra la imagen del disparador.
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
        //Aquí lo que mostramos es la flecha correspondiente al ángulo gracias al método que hemos
        //definido anteriormente "getArrowPoint2D".
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
        //Aquí conseguimos mostrar la bola actual por pantalla.
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
        //Y aquí mostramos la siguiente.
        if (this.siguiente!=null){
            gc.drawImage(r.getImage("balls"),
                //inicio de la posicion
                this.siguiente.getBalltype().getX(),
                this.siguiente.getBalltype().getY(),
                Bubble.WIDTH,
                Bubble.HEIGHT,
                //dibujar en el lienzo
                ((this.posicion.getX() - Bubble.WIDTH / 2) * Game.SCALE)-100,
                ((this.posicion.getY() - Bubble.HEIGHT / 2) * Game.SCALE)+35,
                Bubble.WIDTH * Game.SCALE,
                Bubble.HEIGHT * Game.SCALE);
            }
    }
    //Este método será llamado por la tecla LEFT para poder mover la flecha hacia la izquierda.
    public void Moveleft() {
        this.angulo += this.incremento;
        if (this.angulo > MAX_ANGLE) {
            this.angulo = MAX_ANGLE;
        }
    }
    //Este otro método será llamado por la tecla RIGHT para poder mover la flecha hacia la derecha.
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
