package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.geometry.Geometry;
import frc.lib.geometry.Twist2;
import frc.lib.util.DriveSignal;
import frc.robot.Kinematics;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class CurvatureDrive extends DriveCommand {
    public CurvatureDrive(DriveTrain subsystem) {
        super(subsystem);
    }

    @Override
    public void execute() {
//        final double
        final double throttle = RobotContainer.getRightJoystick().getX();
        final double wheel = RobotContainer.getRightJoystick().getY();

        final DriveSignal signal = getSignal(throttle, wheel, false);

        this.driveTrain.getFrontRight().set(signal.getRight());
        this.driveTrain.getBackRight().set(signal.getRight());
        this.driveTrain.getFrontLeft().set(signal.getLeft());
        this.driveTrain.getBackLeft().set(signal.getLeft());
    }

    public synchronized DriveSignal getSignal(double throttle, double wheel, boolean quickTurn) {
        if (Geometry.epsilonEquals(throttle, 0.0, 0.04)) {
            throttle = 0.0;
        }

        if (Geometry.epsilonEquals(wheel, 0.0, 0.035)) {
            wheel = 0.0;
        }

        /*final double WHEEL_GAIN = 0.05;
        final double WHEEL_NON_LINEARITY = 0.05;
        final double denom = Math.sin(Math.PI / 2.0 * WHEEL_NON_LINEARITY);

        if (!quickTurn) {
            wheel = Math.sin(Math.PI / 2.0 * WHEEL_NON_LINEARITY * wheel);
            wheel = wheel / (denom * denom) * Math.abs(throttle);
        }*/

//        wheel *= WHEEL_GAIN;

        DriveSignal signal = Kinematics.inverseKinematics(new Twist2(throttle, 0.0, wheel));
//        double scalingFactor = Math.max(1.0, Math.max(Math.abs(signal.getLeft()), Math.abs(signal.getRight())));
        return new DriveSignal(signal.getLeft(), signal.getRight());
    }

    @Override
    public void end(boolean interrupted) {
        this.driveTrain.getFrontLeft().set(0);
        this.driveTrain.getBackLeft().set(0);
        this.driveTrain.getFrontRight().set(0);
        this.driveTrain.getBackRight().set(0);
    }

    final double clamp(double d, double max, double min) {
        return Math.max(min, Math.min(max, d));
    }
}
