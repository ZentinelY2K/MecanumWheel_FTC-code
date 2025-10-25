package org.firstinspires.ftc.teamcode;//take ftc tools
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;//import linearopmode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;//import teleop
import com.qualcomm.robotcore.hardware.DcMotor;//import dcmotor
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="MecanumWheelSolo",group="RobotControl")//declare DS name
public class MecanumWheelsSolo extends LinearOpMode{
    DcMotor leftBack,leftFront, rightBack,rightFront;//declare motors


    @Override// override functions
    public void runOpMode() {//press INIT
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");// find hardware
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");//find hardware
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");//find hwardare
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");//find hardware

        //reverse motor direction so they don't counter attack each other.
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;//up and down
            double x = gamepad1.left_stick_x;//left or right
            double rx = gamepad1.right_stick_x;//rotation
            //px = x py = y pa = rx
            //mecanum wheel logic

            double leftBackPW = y - x + rx;//apply mecanum formula for left back
            double leftFrontPW = y + x + rx;//apply mecanum formula for left front
            double rightBackPW = y + x - rx;//apply mecanum formula for right back
            double rightFrontPW = y - x - rx;//apply mecanum formula for right front


            //safety protocol before wheels get the power MAX library setup
            double max_rpm = Math.max(Math.abs(leftBackPW),Math.max(Math.abs(leftFrontPW),Math.max(Math.abs(rightBackPW),Math.max(Math.abs(rightFrontPW),1.0))));



            if(max_rpm>1.0){ //We set the limit of power
                leftBackPW /= max_rpm; //divide its current power by its maximum value resulting in: 1
                leftFrontPW /= max_rpm;//same
                rightBackPW /= max_rpm;//same
                rightFrontPW /= max_rpm;//same
            }

            //wheels
            leftBack.setPower(leftBackPW);//set motor to have the now filtered and formulated power
            leftFront.setPower(leftFrontPW); //same
            rightBack.setPower(rightBackPW);//same
            rightFront.setPower(rightFrontPW);//same



            telemetry.addData("LeftBack Wheel: ",leftBack.getPower());//telemetry for power visual
            telemetry.addData("LeftFront Wheel: ",leftFront.getPower());//same
            telemetry.addData("rightBack Wheel: ",rightBack.getPower());//same
            telemetry.addData("rightFront Wheel: ",rightFront.getPower());//same;
            telemetry.update();//update telemetry for it to show in the DS
        }//close while loop


    }//close opmode
}//close teleop (program)


//Save for later
/*double leftBackPW = -x + y - rx;//apply mecanum formula for left back
double leftFrontPW = x + y + rx;//apply mecanum formula for left front
double rightBackPW = x + y + rx;//apply mecanum formula for right back
double rightFrontPW = -x + y + rx;//apply mecanum formula for right front*/