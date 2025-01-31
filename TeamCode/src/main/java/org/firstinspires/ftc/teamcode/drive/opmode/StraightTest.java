package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeleOp.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.AutoRobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.TeamMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is a simple routine to test translational drive capabilities.
 */
@Config
//@Autonomous(group = "drive")
public class StraightTest extends LinearOpMode {
    public static double DISTANCE = 15; // in

    private final RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        robot.init(hardwareMap);
        TeamMecanumDrive drive = new TeamMecanumDrive(hardwareMap,robot);
        //SampleMecanumDrive drive = new TeamMecanumDrive(hardwareMap);

        Trajectory trajectory = drive.trajectoryBuilder(new Pose2d())
                //.lineToSplineHeading(new Pose2d(11.8, 61.7, Math.toRadians(90)))
                .forward(DISTANCE)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(trajectory);

        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.addData("wheel positions", poseEstimate.getHeading());drive.getWheelPositions();
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;
    }
}
