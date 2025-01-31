package org.firstinspires.ftc.teamcode.autonomous;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeleOp.Hang;
import org.firstinspires.ftc.teamcode.TeleOp.RobotHardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "ClawTest", preselectTeleOp = "ControlsNEW")
public class ClawTest extends LinearOpMode {


    private final List<String> telemetryLog = new ArrayList<>();

    // Constants for distances and headings
    private static final double FORWARD_DIST1 = 80;

    private static final double STRAFE_RIGHT_DIST = 24;

    private static final double HANG_DIST = 12;
    private static final double OBSERVATION_FORWARD_DIST = 58;
    private static final double GRAB_FORWARD_DIST = 12;
    private static final int ANGDEG = 110;

    public Hang hangControl;

    private final RobotHardware robot = new RobotHardware();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        hangControl = new Hang(robot, this);
        waitForStart();
        CloseClaw(); // 0.5 0.7
        OpenClaw();  // 1.0 0.2
        double clawLeft = 0.5;
        double clawRight = 0.7;
        if (isStopRequested()) return;
        for (int i = 0; i < 5; i++) {
            clawLeft = 0.5 - 0.1 * i;
            clawRight = 0.7 + 0.1 * i;
            hangControl.setLeftClawServo(clawLeft);
            hangControl.setRightClawServo(clawRight);
            sleep(1000);
        }

        telemetry.addLine("Autonomous routine completed!");
        telemetry.update();

        while (opModeIsActive());
    }

    private void closeClaw(){
        hangControl.setLeftClawServo(1);
        hangControl.setRightClawServo(0);
    }

    private void openClaw(){
        hangControl.setLeftClawServo(0.5);
        hangControl.setRightClawServo(0.5);
    }

    /**
     * Logs the current position of the robot to telemetry.
     * @param drive The MecanumDrive instance.
     * @param message A custom message to display alongside the position.
     */

    private void logCurrentPosition(TeamMecanumDrive drive, String message) {
        Pose2d currentPose = drive.getPoseEstimate();

        // Format the log entry
        @SuppressLint("DefaultLocale")
        String logEntry = String.format(
                "%s | X: %.2f, Y: %.2f, Heading: %.2f",
                message, currentPose.getX(), currentPose.getY(), Math.toDegrees(currentPose.getHeading())
        );

        // Add the log entry to the list
        telemetryLog.add(logEntry);

        // Display all logs on telemetry
        telemetry.clear();
        for (String log : telemetryLog) {
            telemetry.addLine(log);
        }
        telemetry.update();
    }
    private void RightServo (double Pos){
        robot.rightClawServo.setPosition(Pos);
    }
    private void LeftServo (double Pos){
        robot.leftClawServo.setPosition(Pos);
    }
    private void CloseClaw (){
        LeftServo(0.5);
        RightServo(0.7);
    }
    private void OpenClaw (){
        LeftServo(0.8);
        RightServo(0.3);
    }
    public void setHangPower(double power, int time) {
        robot.leftHang.setPower(power);
        robot.rightHang.setPower(power);
        sleep(time);
        robot.leftHang.setPower(0);
        robot.rightHang.setPower(0);
    }
}