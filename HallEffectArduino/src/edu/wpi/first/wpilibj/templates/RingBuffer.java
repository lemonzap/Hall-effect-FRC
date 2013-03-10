/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
/**
 *
 * @author Lemonzap
 */

public class RingBuffer {
    
    int length;
    int idx = 0;
    double[] buffer; 
    
    public RingBuffer(int length){
        this.length = length;
        buffer = new double[length];
    }
    
    
    
    public void add(double newVal){
        buffer[idx] = newVal;
        idx++; 
        if( idx >= buffer.length){
            idx = 0;
        }        
    }
    
    public double getAverage(){
        double total = 0;
        for(int i = 0; i < buffer.length; i++){
            total += buffer[i];
        }
        return total/buffer.length;
    }
}
