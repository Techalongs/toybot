package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Robot {
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor arm;
    private TouchSensor bottomArmLimit;

    public Robot(HardwareMap hardwareMap) {
        this.backRight = hardwareMap.get(DcMotor.class, "backRight");
        this.backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        this.arm = hardwareMap.get(DcMotor.class, "armMotor");
        this.bottomArmLimit = hardwareMap.get(TouchSensor.class, "bottomArmLimit");

        init();
    }

    private void init() {
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(Gamepad gamepad1, double limiter) {
        double brPower = -gamepad1.left_stick_y - gamepad1.right_stick_x;
        double blPower = -gamepad1.left_stick_y + gamepad1.right_stick_x;

        backRight.setPower(brPower * limiter);
        backLeft.setPower(blPower * limiter);
    }

    public void controlArm(Gamepad gamepad2, double limiter) {
        if (bottomArmLimit.isPressed()) limiter = 0;
        arm.setPower(-gamepad2.left_stick_y * limiter);
    }
}
