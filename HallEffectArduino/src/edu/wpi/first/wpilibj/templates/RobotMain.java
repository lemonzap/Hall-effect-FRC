/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    PIDController pid;
    CounterPIDSource hallSource;
    //DigitalInput hall1;
    double p;
    double i;
    double d;
    double f;
    double setpoint;
    
    Counter hall1;
    RingBuffer speeds;
    
    SpeedControllerMultiplexer shooter;
    Talon shooter1;
    Talon shooter2;
    Talon shooter3;
    Talon shooter4;
    
    Joystick joy1;
    Joystick joy2;
    Joystick joy3;
    
    ToggleButton PIDToggle;
    ToggleButton motorToggle;
    
    public void robotInit() {
        //hall1 = new DigitalInput(1);
        joy2 = new Joystick(2);
        joy1 = new Joystick(1);
        joy3 = new Joystick(3);
        motorToggle = new ToggleButton(new JoystickButton(joy1, 1));
        PIDToggle = new ToggleButton(new JoystickButton(joy2, 1));
        
        hall1 = new Counter(1);
        hall1.setMaxPeriod(1);
        hall1.start();
        
        shooter1 = new Talon(1);
        shooter2 = new Talon(2);
        shooter3 = new Talon(3);
        shooter4 = new Talon(4);
        SpeedController[] controllers = {shooter1, shooter2, shooter3, shooter4};
        shooter = new SpeedControllerMultiplexer(controllers); 
        
        hallSource = new CounterPIDSource(hall1);
        
        pid = new PIDController(0, 0, 0, 0, hallSource, shooter);
        pid.setContinuous();
        pid.setInputRange(0, 10000);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        p = (joy1.getThrottle() + 1)/2;
        i = (joy2.getZ() + 1)/2;
        d = (joy3.getZ() + 1)/2;
        f = 0;
        SmartDashboard.putNumber("p", p);
        SmartDashboard.putNumber("i", i);
        SmartDashboard.putNumber("d", d);
        pid.setPID(p, i, d);
        
        setpoint = SmartDashboard.getNumber("setpoint");
        pid.setSetpoint(setpoint);
        
        SmartDashboard.putNumber("hall RPM", hallSource.getRPM());
        SmartDashboard.putNumber("buffered speed", hallSource.pidGet());
        
        if(PIDToggle.isToggled()){
            if(!pid.isEnable()){
                pid.enable();
                SmartDashboard.putString("PID State", "enabled");
            }     
        }else{
            if(pid.isEnable()){
                pid.disable();
                SmartDashboard.putString("PID State", "disabled");
            }
            shooter.set(joy1.getY());
        }

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
