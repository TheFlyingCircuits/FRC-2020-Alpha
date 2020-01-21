package frc.robot.commands.drive;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDrive extends CommandBase {
    private final DriveTrain driveTrain;
    private double angles = 0;

    public ArcadeDrive(DriveTrain subsystem) {
        addRequirements(subsystem);
        this.driveTrain = subsystem;
    }

    @Override
    public void initialize() {
        // zero the angle
        angles = 0;

        // Zero all the encoders
        this.driveTrain.getFREncoder().setPosition(0);
        this.driveTrain.getBREncoder().setPosition(0);
        this.driveTrain.getFLEncoder().setPosition(0);
        this.driveTrain.getBLEncoder().setPosition(0);

        this.driveTrain.getFREncoder().setVelocityConversionFactor(1);
        this.driveTrain.getBREncoder().setVelocityConversionFactor(1);
        this.driveTrain.getFLEncoder().setVelocityConversionFactor(1);
        this.driveTrain.getBLEncoder().setVelocityConversionFactor(1);
        this.driveTrain.getFREncoder().setPositionConversionFactor(1);
        this.driveTrain.getBREncoder().setPositionConversionFactor(1);
        this.driveTrain.getFLEncoder().setPositionConversionFactor(1);
        this.driveTrain.getBLEncoder().setPositionConversionFactor(1);

        // set brake mode
        this.driveTrain.getFrontRight().setIdleMode(IdleMode.kCoast);
        this.driveTrain.getBackRight().setIdleMode(IdleMode.kCoast);
        this.driveTrain.getFrontLeft().setIdleMode(IdleMode.kCoast);
        this.driveTrain.getBackLeft().setIdleMode(IdleMode.kCoast);
    }

    @Override
    public void execute() {
        // get raw joystick inputs.
        final double xInput = RobotContainer.getRightJoystick().getX();
        final double yInput = RobotContainer.getRightJoystick().getY();

        // fix deadzone


        final double xAxis = xInput;
        final double yAxis = yInput;

        // calculate motor voltages.
        final double leftDrivePotential = yAxis + xAxis;
        final double rightDrivePotential = yAxis - xAxis;

        // set the voltages.
        this.driveTrain.getFrontRight().set(rightDrivePotential);
        this.driveTrain.getBackRight().set(rightDrivePotential);
        this.driveTrain.getFrontLeft().set(leftDrivePotential);
        this.driveTrain.getBackLeft().set(leftDrivePotential);

        final double avgPosition = ( this.driveTrain.getFREncoder().getPosition() + this.driveTrain.getFLEncoder().getPosition()) / 2;
        final double avgVelocity = ( this.driveTrain.getFREncoder().getVelocity() + this.driveTrain.getFLEncoder().getVelocity()) / 2;

        final double rightVelocity = this.driveTrain.getFREncoder().getVelocity() * Constants.velocityFactorA;
        final double leftVelocity = this.driveTrain.getFLEncoder().getVelocity() * Constants.velocityFactorA;

        final double turns = avgPosition / Constants.encoderPerRotation;

        final double radius = (Constants.WHEEL_DISTANCE / 2) * (leftVelocity + rightVelocity) / (rightVelocity + leftVelocity);
        final double curvature = 1 / radius;

        final double angle = 1 / Constants.WHEEL_DISTANCE * (rightVelocity - leftVelocity);
        this.angles += angle;

//        Robot.log("Distance: %sin, Velocity: %sin/s", turns * Constants.wheelCircumferenceA, avgVelocity * Constants.velocityFactorA);
        Robot.log("R: %sin/s, L: %sin/s, P: %sin", Math.round(rightVelocity), Math.round(leftVelocity), avgPosition * Constants.positionFactorA);
//        Robot.log("Angle: %s", angles * 180 / Math.PI);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
