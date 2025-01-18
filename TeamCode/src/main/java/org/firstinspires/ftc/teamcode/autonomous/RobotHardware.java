package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {

    // Declare motors and servos
    public DcMotor Claw, leftHang, rightHang;
    public Servo leftRotation, rightRotation, rightClawServo, leftClawServo;

    // Initialization method to set up all hardware
    public void init(HardwareMap hardwareMap) {

        Claw = hardwareMap.get(DcMotor.class, "Claw");

        leftHang = hardwareMap.get(DcMotor.class, "leftHang");
        rightHang = hardwareMap.get(DcMotor.class, "rightHang");

        leftRotation = hardwareMap.servo.get("leftRotation");
        rightRotation = hardwareMap.servo.get("rightRotation");
        rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
        leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");

        leftHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}