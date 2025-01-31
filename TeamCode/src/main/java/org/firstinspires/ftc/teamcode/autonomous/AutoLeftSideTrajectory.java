package org.firstinspires.ftc.teamcode.autonomous;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeleOp.Hang;
import org.firstinspires.ftc.teamcode.TeleOp.RobotHardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "AutoLeftSideTrajectory", preselectTeleOp = "ControlsNEW")
public class AutoLeftSideTrajectory extends LinearOpMode {


    private final List<String> telemetryLog = new ArrayList<>();

    // Constants for distances and headings
    private static final double FORWARD_DIST1 = 31;

    private static final double STRAFE_RIGHT_DIST = 24;

    private static final double HANG_DIST = 12;
    private static final double OBSERVATION_FORWARD_DIST = 58;
    private static final double GRAB_FORWARD_DIST = 12;
    private static final int ANGDEG = 95;

    public Hang hangControl;

    private final RobotHardware robot = new RobotHardware();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        TeamMecanumDrive drive = new TeamMecanumDrive(hardwareMap,robot);
        Pose2d startPosition = new Pose2d(0, 0, 0); // Starting position (customize if needed)
        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        closeClaw();
        waitForStart();
        // Build the trajectory sequence
        Trajectory initial = drive.trajectoryBuilder(startPosition).forward(FORWARD_DIST1).build();

        waitForStart();

        if (isStopRequested()) return;
        setHangPower(1,800);
        drive.followTrajectory(initial);
        startPosition = drive.getPoseEstimate();
        setHangPower(-1,500);
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).back(HANG_DIST).build());
        drive.turn(Math.toRadians(ANGDEG));
        startPosition = drive.getPoseEstimate();
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).forward(OBSERVATION_FORWARD_DIST).build());
        drive.turn(Math.toRadians(ANGDEG));
        startPosition = drive.getPoseEstimate();
        sleep(1500); // human player
        openClaw();
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).forward(GRAB_FORWARD_DIST).build());
        closeClaw();
        sleep(500);
        setHangPower(1,500);
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).back(GRAB_FORWARD_DIST).build());
        drive.turn(Math.toRadians(ANGDEG));
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).forward(OBSERVATION_FORWARD_DIST-2).build());
        drive.turn(Math.toRadians(ANGDEG));
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).forward(GRAB_FORWARD_DIST).build());
        setHangPower(-1,500);
        openClaw();
        drive.turn(Math.toRadians(ANGDEG));
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).forward(OBSERVATION_FORWARD_DIST).build());
        drive.followTrajectory(drive.trajectoryBuilder(startPosition).strafeRight(30).build());

        // Follow the complete trajectory sequence
        //drive.followTrajectorySequence(trajectorySequence);

        telemetry.addLine("Autonomous routine completed!");
        telemetry.update();

        while (opModeIsActive());
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
    private void closeClaw (){
        LeftServo(0.5);
        RightServo(0.7);
    }
    private void openClaw (){
        LeftServo(0.8);
        RightServo(0.2);
    }
    public void setHangPower(double power, int time) {
        robot.leftHang.setPower(power);
        robot.rightHang.setPower(power);
        sleep(time);
        robot.leftHang.setPower(0);
        robot.rightHang.setPower(0);
    }
}