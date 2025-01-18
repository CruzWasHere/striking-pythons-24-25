package org.firstinspires.ftc.teamcode.autonomous;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.autonomous.Hang;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "AutoLeftSide", preselectTeleOp = "ControlsNEW")
public class AutoLeftSideOptimized extends LinearOpMode {


    private final List<String> telemetryLog = new ArrayList<>();

    // Constants for distances and headings
    private static final double FORWARD_DIST1 = 24;
    private static final double STRAFE_RIGHT_DIST = 24;
    private static final double HANG_DIST = 8;
    private static final double OBSERVATION_FORWARD_DIST = 64;
    private static final double GRAB_FORWARD_DIST = 18;
    private static final int ANGDEG = 82;

    private Hang hangControl;

    private final RobotHardware robot = new RobotHardware();


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPosition = new Pose2d(0, 0, 0); // Starting position (customize if needed)

        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        hangControl = new Hang(robot, this);

        // Build the trajectory sequence
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPosition)
                .addDisplacementMarker(() -> this.closeClaw())
                .forward(FORWARD_DIST1)
                //Check to move up arm before hanging
                .addDisplacementMarker(() -> {
                    logCurrentPosition(drive, "Hanging first specimen...");
                    hangControl.setHangPower(-1,200);
                    this.openClaw();
                })


                .back(HANG_DIST)
                .turn(Math.toRadians(ANGDEG))
                .forward(OBSERVATION_FORWARD_DIST)
                .turn(Math.toRadians(ANGDEG))
                .waitSeconds(1)
                .forward(GRAB_FORWARD_DIST)
                .addDisplacementMarker(() -> {
                    logCurrentPosition(drive, "Grabbing second specimen...");
                    hangControl.setHangPower(1,200);
                    this.closeClaw();
                    this.sleep(100);
                    hangControl.setHangPower(-1,200);
                })

                .back(GRAB_FORWARD_DIST)
                .turn(Math.toRadians(ANGDEG))
                .forward(OBSERVATION_FORWARD_DIST)
                .turn(Math.toRadians(ANGDEG))
                .forward(HANG_DIST)
                .addDisplacementMarker(() -> {
                    logCurrentPosition(drive, "Hanging second specimen...");
                    hangControl.setHangPower(1,200);
                    this.openClaw();
                })

                .back(HANG_DIST)
                .turn(Math.toRadians(ANGDEG))
                .forward(OBSERVATION_FORWARD_DIST-2)
                .turn(Math.toRadians(ANGDEG))
                .forward(GRAB_FORWARD_DIST)
                .addTemporalMarker(() -> logCurrentPosition(drive, "Parking the robot..."))


                .build();

        waitForStart();

        if (isStopRequested()) return;

        // Follow the complete trajectory sequence
        drive.followTrajectorySequence(trajectorySequence);

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
            hangControl.setRightClawServo(0.7);
    }

    /**
     * Logs the current position of the robot to telemetry.
     * @param drive The MecanumDrive instance.
     * @param message A custom message to display alongside the position.
     */

    private void logCurrentPosition(SampleMecanumDrive drive, String message) {
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
}