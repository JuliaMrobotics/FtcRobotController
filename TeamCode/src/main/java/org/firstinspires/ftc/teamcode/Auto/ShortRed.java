package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.WebcamExample;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "ShortRed")
public class ShortRed extends LinearOpMode {

    OpenCvWebcam webcam;
    public static DcMotor FrontLeft = null;
    public static DcMotor FrontRight = null;
    public static DcMotor BackLeft = null;
    public static DcMotor BackRight = null;
    public static Servo Claw = null;
    public static Servo TiltClaw = null;
    public static DcMotor Lift = null;
//    public static DcMotor Launcher = null;
    public static int position = -1;

    @Override
    public void runOpMode() throws InterruptedException{

        telemetry.clearAll();

        /* this is setting the name of the motor used in the code
        equal to the name of that motor in the robot configuration*/
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");
        Lift = hardwareMap.get(DcMotor.class, "Lift");
        TiltClaw = hardwareMap.get(Servo.class, "Tilt");
        Claw = hardwareMap.get(Servo.class, "Claw");
//        Launcher = hardwareMap.get(DcMotor.class, "PlaneLauncher");

        //this sets the direction that the motors spin
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        Lift.setDirection(DcMotor.Direction.FORWARD);
        TiltClaw.setDirection(Servo.Direction.FORWARD);
        Claw.setDirection(Servo.Direction.FORWARD);
//        Launcher.setDirection(DcMotor.Direction.FORWARD);


        //this resets the encoders to zero, so that the recordings are accurate
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /*this sets up the movement so that anytime the motors are used
        it will use encoders*/
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        Lift.setPower(0);
        TiltClaw.setPosition(0.25);
        Claw.setPosition(0);
//        Launcher.setPower(0);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(new ShortRed.SamplePipeline());
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                //This will be called if the camera could not be opened
            }
        });

        waitForStart();

//        while (opModeIsActive()) {
//
//            telemetry.addData("Frame Count", webcam.getFrameCount());
//            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
//            telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
//            telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
//            telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
//            telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
//            telemetry.update();
//
//            if(gamepad1.a) {
//                webcam.stopStreaming();
//                //webcam.closeCameraDevice();
//            }
//            sleep(100);
//        }

        if (position == 2) {
            webcam.stopStreaming();
            strafeDrive(10, 0.5);
            sleep(1000);
            straightDrive(27, 0.5);
            sleep(3000);
            Claw.setPosition(0.25);
            straightDrive(-25, 0.5);
            sleep(3000);
            strafeDrive(26, 0.5);
            sleep(5000);
        } else if (position == 1) {
            webcam.stopStreaming();
            straightDrive(30, 0.5);
            sleep(3000);
            Claw.setPosition(0.25);
            straightDrive(-28, 0.5);
            sleep(3000);
            strafeDrive(38, 0.5);
            sleep(5000);
        } else {
            webcam.stopStreaming();
            straightDrive(23, 0.5);
            sleep(3000);
            Turn(-14, 0.5);
            sleep(2000);
            straightDrive(7, 0.5);
            sleep(1000);
            Claw.setPosition(0.25);
            straightDrive(-10, 0.5);
            sleep(1000);
            Turn(14, 0.5);
            sleep(2000);
            straightDrive(-22, 0.5);
            sleep(3000);
            strafeDrive(36, 0.5);
            sleep(5000);
        }
    }

    class SamplePipeline extends OpenCvPipeline {
        boolean viewportPaused;

        @Override
        public Mat processFrame(Mat input) {
            Point LeftTop = new Point(0,120);
            Point LeftBottom = new Point(60, 180);
            Point MiddleTop = new Point(120, 120);
            Point MiddleBottom = new Point(200, 180);
            Point RightTop = new Point(320, 120);
            Point RightBottom = new Point(260, 180);
            int red = 0;
            int green = 0;
            int blue = 0;
            int total = 0;
            int totalRedLeft = 0;
            int totalRedMiddle = 0;
            int totalRedRight = 0;

            for (int x = (int)LeftTop.x; x < LeftBottom.x; x++) {
                for (int y = (int) LeftTop.y; y < LeftBottom.y; y++) {
                    red += input.get(y,x)[0];
                    green += input.get(y,x)[1];
                    blue += input.get(y, x)[2];
                    total++;
                }
            }

            blue /= total;
            green /= total;
            totalRedLeft = (red / total) -blue -green;
            blue =0;
            total = 0;
            red = 0;
            green = 0;

            //Middle
            for (int x = (int)MiddleTop.x; x < MiddleBottom.x; x++) {
                for (int y = (int) MiddleTop.y; y < MiddleBottom.y; y++) {
                    red += input.get(y,x)[0];
                    green += input.get(y,x)[1];
                    blue += input.get(y, x)[2];
                    total++;
                }
            }

            blue /= total;
            green /= total;
            totalRedMiddle = (red / total) -blue -green;
            blue =0;
            total = 0;
            red = 0;
            green = 0;

            //Right
            for (int x = (int)RightBottom.x; x < RightTop.x; x++) {
                for (int y = (int) RightTop.y; y < RightBottom.y; y++) {
                    red += input.get(y,x)[0];
                    green += input.get(y,x)[1];
                    blue += input.get(y, x)[2];
                    total++;
                }
            }

            blue /= total;
            green /= total;
            totalRedRight = (red / total) -blue -green;

            if(totalRedLeft > totalRedMiddle && totalRedLeft > totalRedRight) {
                Imgproc.rectangle(
                        input,
                        LeftTop,
                        LeftBottom,
                        new Scalar(0, 0, 255), 4);
                position = 0;
                telemetry.addLine("Middle" + totalRedMiddle);
                telemetry.addLine("Left" + totalRedLeft);
                telemetry.addLine("Right" + totalRedRight);
            }

            if(totalRedMiddle > totalRedLeft && totalRedMiddle > totalRedRight) {
                Imgproc.rectangle(
                        input,
                        MiddleTop,
                        MiddleBottom,
                        new Scalar(255, 0, 255), 4);
                position = 1;
                telemetry.addLine("Middle" + totalRedMiddle);
                telemetry.addLine("Left" + totalRedLeft);
                telemetry.addLine("Right" + totalRedRight);
            }

            if(totalRedRight > totalRedLeft && totalRedRight > totalRedMiddle) {
                Imgproc.rectangle(
                        input,
                        RightTop,
                        RightBottom,
                        new Scalar(255, 0, 0), 4);
                position = 2;
                telemetry.addLine("Middle" + totalRedMiddle);
                telemetry.addLine("Left" + totalRedLeft);
                telemetry.addLine("Right" + totalRedRight);
            }
            return input;
        }

        @Override
        public void onViewportTapped() {

            viewportPaused = !viewportPaused;

            if(viewportPaused) {
                webcam.pauseViewport();
            }
            else {
                webcam.resumeViewport();
            }
        }
    }

    public static void strafeDrive (float distance, double speed) {

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setTargetPosition(FrontLeft.getCurrentPosition() + (int) (43.47343 * -distance));
        FrontRight.setTargetPosition(FrontRight.getCurrentPosition() + (int) (43.47343 * distance));
        BackLeft.setTargetPosition(BackLeft.getCurrentPosition() + (int) (43.47343 * distance));
        BackRight.setTargetPosition(BackRight.getCurrentPosition() + (int) (43.47343 * -distance));

        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeft.setPower(speed);
        FrontRight.setPower(speed);
        BackLeft.setPower(speed);
        BackRight.setPower(speed);
    }

    public static void straightDrive (float distance, double speed) {

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setTargetPosition(FrontLeft.getCurrentPosition() + (int) (43.47343 * -distance));
        FrontRight.setTargetPosition(FrontRight.getCurrentPosition() + (int) (43.47343 * -distance));
        BackLeft.setTargetPosition(BackLeft.getCurrentPosition() + (int) (43.47343 * -distance));
        BackRight.setTargetPosition(BackRight.getCurrentPosition() + (int) (43.47343 * -distance));

        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeft.setPower(speed);
        FrontRight.setPower(speed);
        BackLeft.setPower(speed);
        BackRight.setPower(speed);
    }

    public static void Turn (float distance, double speed) {

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setTargetPosition(FrontLeft.getCurrentPosition() + (int) (43.47343 * -distance));
        FrontRight.setTargetPosition(FrontRight.getCurrentPosition() + (int) (43.47343 * distance));
        BackLeft.setTargetPosition(BackLeft.getCurrentPosition() + (int) (43.47343 * -distance));
        BackRight.setTargetPosition(BackRight.getCurrentPosition() + (int) (43.47343 * distance));

        FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeft.setPower(speed);
        FrontRight.setPower(speed);
        BackLeft.setPower(speed);
        BackRight.setPower(speed);
    }
}
