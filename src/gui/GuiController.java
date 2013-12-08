/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auto.UserController;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;


public class GuiController {
    
    private UserController userController = null;
    private JFrame currentFrame = null;
    private HashMap<String, JFrame> map;
    private ArrayList<Clearable> clearables;
    
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
