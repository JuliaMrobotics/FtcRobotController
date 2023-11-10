package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "BasicDrive2")
public class BasicDrive2 extends OpMode {

    //setting the motors to null to reset them
    public static DcMotor FrontLeft = null;
    public static DcMotor FrontRight = null;
    public static DcMotor BackLeft = null;
    public static DcMotor BackRight = null;
//    public static DcMotor Lift = null;
//    public static DcMotor Claw = null;
    public static DcMotor Launcher = null;
    public static Servo LeftHangArm = null;
    public static Servo RightHangArm = null;

    @Override
    public void init() {

        //this clears and starts telemetry for robot initilization status
        telemetry.clearAll();
        telemetry.addData("Status", "TeleOp Initialization In Progress");
        telemetry.update();

        /* this is setting the name of the motor used in the code
        equal to the name of that motor in the robot configuration*/
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");
//        Lift = hardwareMap.get(DcMotor.class, "Lift");
//        Claw = hardwareMap.get(DcMotor.class, "Claw");
        Launcher = hardwareMap.get(DcMotor.class, "PlaneLauncher");
        LeftHangArm = hardwareMap.get(Servo.class, "LArm");
        RightHangArm = hardwareMap.get(Servo.class, "RArm");

        //this sets the direction that the motors spin
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
//        Lift.setDirection(DcMotor.Direction.FORWARD);
//        Claw.setDirection(DcMotor.Direction.FORWARD);
        Launcher.setDirection(DcMotor.Direction.FORWARD);
        LeftHangArm.setDirection(Servo.Direction.FORWARD);
        RightHangArm.setDirection(Servo.Direction.FORWARD);


        //this resets the encoders to zero, so that the recordings are accurate
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /*this sets up the movement so that anytime the motors are used
        it will use encoders*/
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Claw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //this sets the motors to immediately brake when power is zero
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //this makes sure that the motor do not move before they are set to
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
//        Lift.setPower(0);
//        Claw.setPower(0);
        Launcher.setPower(0);
        LeftHangArm.setPosition(0.35);
        RightHangArm.setPosition(0);

        //this is the text visible on the driver hub that the robot is done initializing
        telemetry.addData("Status" , "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {

        //value each input from the controller
        double straightIn = (gamepad1.left_stick_y); //forward or backward
        double strafeIn = (gamepad1.left_stick_x); //straight left or right
        double turnIn = (gamepad1.right_stick_x); //turning

        /*using the input from controller to set the output for the motors
        all the positive inputs or added inputs are the motors that will spin forward with that direction input*/

            FrontLeft.setPower(straightIn - turnIn - strafeIn);
            FrontRight.setPower(straightIn + turnIn + strafeIn);
            BackLeft.setPower(straightIn - turnIn + strafeIn);
            BackRight.setPower(straightIn + turnIn - strafeIn);



//        Lift.setPower(gamepad2.left_stick_y);

        if (gamepad1.left_bumper && gamepad1.right_bumper) {
            //launches paper airplane
            Launcher.setPower(1);
        } else {
            Launcher.setPower(0);
        }

        if (gamepad2.y) {
            LeftHangArm.setPosition(0);
            RightHangArm.setPosition(0.35);
        } else if (gamepad2.a) {
            LeftHangArm.setPosition(0.35);
            RightHangArm.setPosition(0);
        }

//        if (gamepad1.b) {
//            FrontLeft.setPower(0.25);
//            FrontRight.setPower(0.25);
//            BackLeft.setPower(0.25);
//            BackRight.setPower(0.25);
//        } else {
//            FrontLeft.setPower(0);
//            FrontRight.setPower(0);
//            BackLeft.setPower(0);
//            BackRight.setPower(0);
//        }

//        else if (gamepad2.x) {
//            //claw opens
//        } else if (gamepad2.y) {
//            //claw closes
//        }


        //encoder data for each motor
        telemetry.addLine("FL:" + FrontLeft.getCurrentPosition());
        telemetry.addLine("FR:" + FrontRight.getCurrentPosition());
        telemetry.addLine("BL:" + BackLeft.getCurrentPosition());
        telemetry.addLine("BR:" + BackRight.getCurrentPosition());
        telemetry.addLine("ControllerInput:" + straightIn);
    }
}
