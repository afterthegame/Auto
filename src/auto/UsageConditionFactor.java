/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

/**
 *
 * @author gorz
 */
public class UsageConditionFactor {
    
    private String name;
    private float value;
    
    public UsageConditionFactor(String name, float value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public float getValue() {
        return value;
    }
}
