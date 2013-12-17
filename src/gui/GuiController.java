/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auto.InputDataController;
import auto.UserController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GuiController {
    
    private UserController userController = null;
    private InputDataController inputController = null;
    private JFrame currentFrame = null;
    private HashMap<String, JFrame> map;
    private ArrayList<Clearable> clearables;
    
    public static KeyListener listener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 10)
                ((JButton)e.getSource()).doClick();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    };
    
    public GuiController(UserController userController, InputDataController input) {
        this.userController = userController;
        this.inputController = input;
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
    
    public void updateTables(String path) {
        inputController.updateTables(path);
    }
    
    public static String query(String query) {
        return JOptionPane.showInputDialog(null, query);
    }
    
    
}
