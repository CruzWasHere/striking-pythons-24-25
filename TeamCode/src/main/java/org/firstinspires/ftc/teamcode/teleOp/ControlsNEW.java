package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ControlsNEW extends LinearOpMode {
    public final RobotHardware robot = new RobotHardware();
    public Base driveControl;

    public Hang hangControl;

    
    @Override

    public void runOpMode() {
        robot.init(hardwareMap); // Initialize robot hardware
        driveControl = new Base(robot); // Initialize Control1 with RobotHardware
        hangControl = new Hang(robot, this); // Initialize Hang with RobotHardware

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Pass the joystick values to Control1's drive method
            double y = -gamepad1.left_stick_y; // Forward/backward
            double x = gamepad1.left_stick_x * 1.1; // Strafing
            double rx = gamepad1.right_stick_x; // Rotation
            driveControl.drive(y, x, rx);

//            clawControl.controlClaw(
//                    gamepad2.a,
//                    gamepad2.y,
//                    gamepad2.x,
//                    gamepad2.b,
//                    gamepad2.left_bumper,
//                    gamepad2.right_bumper
//            );

            hangControl.controlHang(
                    gamepad2.a,
                    gamepad2.y,
                    gamepad2.left_bumper,
                    gamepad2.right_bumper,
                    (int) Math.signum (gamepad2.left_stick_y)
                    );
        }
    }
}