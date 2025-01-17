
package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Hang {

    public final RobotHardware robot;
    private final LinearOpMode opMode;

    // Constructor that takes RobotHardware as a parameter
    public Hang(RobotHardware robot, LinearOpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;
    }

    // Hang Programming

    public void controlHang(boolean buttonA, boolean buttonY, boolean leftBumper, boolean rightBumper, int leftStick) {
        if (buttonA) {
            setClawPower(1, 100); // Hang Down
        } else if (buttonY) {
            setClawPower(-1, 100); // Hang Up
        }
        if (leftBumper) {
            setLeftClawServo(1);
            setRightClawServo(0);
        } else if (rightBumper) {
            setLeftClawServo(0.5);
            setRightClawServo(0.7);
        }
        if (leftStick > 0) {
            setHangPower(1,100);
        } else if (leftStick < 0) {
            setHangPower(-1, 100);
        }

    }

    public void setHangPower(double power, int time) {
        robot.leftHang.setPower(power);
        robot.rightHang.setPower(power);
        opMode.sleep(time);
        robot.leftHang.setPower(0);
        robot.rightHang.setPower(0);
    }
    public void setLeftClawServo ( double Pos){

        robot.leftClawServo.setPosition(Pos);
    }
    public void setRightClawServo (double Pos){
        robot.rightClawServo.setPosition(Pos);
    }
    public void setClawPower ( double power, int time) {
        robot.Claw.setPower(power);
        opMode.sleep(time);
        robot.Claw.setPower(0);
    }
}
