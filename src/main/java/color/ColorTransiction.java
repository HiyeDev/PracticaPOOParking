/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package color;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author GuillermoGomezGascon
 */
public class ColorTransiction {
    
    private static Timer timer;
    
    public static void startColorTransition(JButton button,Color startColor, Color endColor) {
                if (timer != null) {
                    timer.stop();
                }
                
                int steps = 20;
                int delay = 20;

               timer = new Timer(delay, new ActionListener() {
                    private int step = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (step <= steps) {
                            float progress = (float) step / steps;
                            Color colorBackground = blendColors(startColor, endColor, progress);
                            Color colorForegroud = blendColors(endColor, startColor, progress);
                            button.setBackground(colorBackground);
                            button.setForeground(colorForegroud);
                            step++;
                        } else {
                            timer.stop();
                        }
                    }
                });
                timer.start();
            }


            private static Color blendColors(Color color1, Color color2, float ratio) {
                int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
                int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
                int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);
                return new Color(red, green, blue);
            }
    
}
