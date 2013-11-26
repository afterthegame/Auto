/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import auto.UserController;
import java.util.HashMap;
import javax.swing.JFrame;


public class GuiController {
    
    private UserController userController = null;
    private JFrame currentFrame = null;
    private HashMap<String, JFrame> map;
    
    public GuiController(UserController userController) {
        this.userController = userController;
        map = new HashMap<String, JFrame>();
    }
    
    public boolean isCurrentUserAdmin() {
        return userController.isCurrentUserAdmin();
    }
    
    public void logout() {
        userController.logout();
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
