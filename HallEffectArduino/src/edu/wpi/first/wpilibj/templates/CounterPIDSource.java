/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Lemonzap
 */
public class CounterPIDSource implements PIDSource {
    
    Counter counter;
    RingBuffer speeds;
    
    public CounterPIDSource(Counter counter){
        this.counter = counter;
        speeds = new RingBuffer(15);
    }
    
    public double pidGet() {
        speeds.add(this.getRPM());
        return speeds.getAverage();
    }
    
    public double getRPS(){
        return 1/counter.getPeriod();
    }
    
    public double getRPM(){
        return this.getRPS()*60;
    }
    
}
