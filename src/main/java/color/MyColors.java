/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package color;

import java.awt.Color;

/**
 *
 * @author GuillermoGomezGascon
 */
public class MyColors extends Color {
    
    public static final MyColors TURQUOISE = new MyColors(51, 204, 204);

    public static final MyColors AQUAMARINE_BLUE = new MyColors(122, 221, 221);

    public MyColors(int r, int g, int b) {
        super(r, g, b);
    }
    
}
