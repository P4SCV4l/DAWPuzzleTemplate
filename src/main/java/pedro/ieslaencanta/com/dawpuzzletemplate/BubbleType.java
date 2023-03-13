/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pedro.ieslaencanta.com.dawpuzzletemplate;

/**
 *
 * @author Administrador
 */
public enum BubbleType {

    BLUE(1, 0),
    RED(1, 33),
    YELLOW(171,0),
    GREEN(171, 33),
    PURPLE(1, 66),
    ORANGE(171, 66),
    GRAY(1, 99),
    WRITE(171, 99);
    private final int x;
    private final int y;

    BubbleType(int x,  int y ) {
        this.x = x;
        this.y = y;
    }
   
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
