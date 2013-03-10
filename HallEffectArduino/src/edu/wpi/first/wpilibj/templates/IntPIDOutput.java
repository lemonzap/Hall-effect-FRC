/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Lemonzap
 */
public class IntPIDOutput implements PIDOutput {
    
    double output;
    
    public void pidWrite(double d) {
        output = d;
        System.out.println(d);
    }
    
    public double getOutput(){
        return output;
    }
}
