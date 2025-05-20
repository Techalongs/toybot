package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

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
        double armPower = -gamepad2.left_stick_y * limiter;
        if (bottomArmLimit.isPressed()) {
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armPower = Math.max(0, armPower);
        }

        arm.setPower(armPower);
    }

    public void displayData(Telemetry telemetry) {
        telemetry.addData("Back Right Power", backRight.getPower());
        telemetry.addData("Back Left Power", backLeft.getPower());
        telemetry.addData("Back Right Position", backRight.getCurrentPosition());
        telemetry.addData("Back Left Position", backLeft.getCurrentPosition());
        telemetry.addData("Arm Power", arm.getPower());
        telemetry.addData("Arm Position", arm.getCurrentPosition());
        telemetry.addData("Bottom Arm Limit", bottomArmLimit.isPressed());
        telemetry.update();
    }
}
