package frc.robot.commands.drive;

import frc.lib.Utils;
import frc.lib.util.DriveSignal;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class Curvature2Drive extends DriveCommand {
    private final double QUICKSTOP_THRESHOLD = 0.2d;
    private final double QUICKSTOP_ALPHA = 0.1d;
    private double quickStopAccumulator;

    public Curvature2Drive(DriveTrain subsystem) {
        super(subsystem);
    }

    @Override
    public void execute() {
        // Get joystick inputs
        final double throttle = RobotContainer.getRightJoystick().getX();
        final double turn = RobotContainer.getRightJoystick().getY();

        // Get drive signal
        final DriveSignal signal = calculateDrive(throttle, turn,
                RobotContainer.getRightJoystick().getRawButton(Constants.QUICKTURN_CH));

        // For debugging, log signal output
        System.out.println(signal);

        // Set motor values
//        this.setDrive(signal);
    }

    private final DriveSignal calculateDrive(double xSpeed, double zRotation, boolean quickTurn) {
        xSpeed = Utils.clamp(xSpeed, -1.0, 1.0);
        xSpeed = Utils.deadband(xSpeed);

        zRotation = Utils.clamp(zRotation, -1.0, 1.0);
        zRotation = Utils.deadband(zRotation);

        double angularPower;
        boolean overPower;

        if (quickTurn) {
            if (Math.abs(xSpeed) < QUICKSTOP_THRESHOLD) {
                this.quickStopAccumulator = (1 - QUICKSTOP_ALPHA) * this.quickStopAccumulator + QUICKSTOP_ALPHA
                        * Utils.clamp(zRotation, -1.0, 1.0) * 2;
            }
            overPower = true;
            angularPower = zRotation;
        } else {
            overPower = false;
            angularPower = Math.abs(xSpeed) * zRotation - this.quickStopAccumulator;

            if (this.quickStopAccumulator > 1) {
                this.quickStopAccumulator -= 1;
            } else if (this.quickStopAccumulator < -1) {
                this.quickStopAccumulator += 1;
            } else {
                this.quickStopAccumulator = 0;
            }
        }

        double left = xSpeed + angularPower;
        double right = xSpeed - angularPower;

        if (overPower) {
            if (left > 1.0) {
                right -= left - 1.0;
                left = 1.0;
            } else if (right > 1.0) {
                left -= right - 1.0;
                right = 1.0;
            } else if (left < -1.0) {
                right -= left + 1.0;
            } else if (right < -1.0) {
                left -= right + 1.0;
                right = -1.0;
            }
         }

        double maxMagnitude = Math.max(Math.abs(left), Math.abs(right));

        if (maxMagnitude > 1.0) {
            left /= maxMagnitude;
            right /= maxMagnitude;
        }

        final DriveSignal signal = new DriveSignal(left, right);
        return signal;
    }
}
