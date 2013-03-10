/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
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
    Counter hall1;
    RingBuffer speeds;
    IntPIDOutput output;
    
    public void robotInit() {
        //hall1 = new DigitalInput(1);
        hall1 = new Counter(1);
        hall1.setMaxPeriod(1);
        hall1.start();
        hallSource = new CounterPIDSource(hall1);
        speeds = new RingBuffer(15);
        output = new IntPIDOutput();
        pid = new PIDController(1, 0, 0, 0, hallSource, output);
        pid.enable();
        pid.setSetpoint(500);
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
        Timer.getFPGATimestamp();
        SmartDashboard.putNumber("hall1 count", hall1.get());
        SmartDashboard.putNumber("Hall Timer", hall1.getPeriod());
        SmartDashboard.putNumber("hall RPM", hallSource.getRPM());
        speeds.add(hallSource.getRPM());
        SmartDashboard.putNumber("buffered speed", speeds.getAverage());
        SmartDashboard.putNumber("PID output", output.getOutput());
        SmartDashboard.putNumber("pid output 2", pid.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
