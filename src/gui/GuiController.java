/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.HashMap;
import javax.swing.JFrame;


public class GuiController {
    
    private JFrame currentFrame = null;
    private HashMap<String, JFrame> map;
    
    public GuiController() {
        map = new HashMap<String, JFrame>();
    }
    
    public void addFrame(String name, JFrame frame) {
        map.put(name, frame);
    }
    
    public void changeFrame(String name) {
        if(currentFrame != null) {
            currentFrame.setVisible(false);
        }
        currentFrame = map.get(name);
        if(currentFrame != null) {
            currentFrame.setVisible(true);
        }
    }
}
