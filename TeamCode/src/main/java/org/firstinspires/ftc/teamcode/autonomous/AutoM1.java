package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commons.RobotHardware;
import org.firstinspires.ftc.teamcode.teleOp.Claw;
import org.firstinspires.ftc.teamcode.teleOp.Hang;

@Autonomous (name = "AutoM1", preselectTeleOp = "ControlsNEW")
public class AutoM1 extends LinearOpMode {

    public final AutonomousRobot robot = new AutonomousRobot(hardwareMap,telemetry);
    // Odometers (Encoders for localization)
    private Odometer odometer;


    @Override
    public void runOpMode() {
        waitForStart();
        telemetry.addLine("Started");
        telemetry.update();

        // Move to a target (example)
        robot.moveToTarget(10.0, 10.0);  // Move to coordinates (50, 50)

        // Perform other autonomous tasks (servo control, object interaction, etc.)
        //robot.setServoPosition(0, 0.5); // Set servo1 to half position
        sleep(1000); // Wait for 1 second
    }
}
