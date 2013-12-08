/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auto.UserController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;


public class GuiController {
    
    private UserController userController = null;
    private JFrame currentFrame = null;
    private HashMap<String, JFrame> map;
    private ArrayList<Clearable> clearables;
    
    public static KeyListener listener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            ((JButton)e.getSource()).doClick();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    };
    
    public GuiController(UserController userController) {
        this.userController = userController;
        map = new HashMap<String, JFrame>();
        clearables = new ArrayList<Clearable>();
    }
    
    public boolean isCurrentUserAdmin() {
        return userController.isCurrentUserAdmin();
    }
    
    public void logout() {
        userController.logout();
    }
    
    public void clearForms() {
        for(Clearable c : clearables) {
            c.clear();
        }
    }
    
    public void addFrame(String name, JFrame frame) {
        map.put(name, frame);
        if(frame instanceof Clearable) {
            clearables.add((Clearable)frame);
        }
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
