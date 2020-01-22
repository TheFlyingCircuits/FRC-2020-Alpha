package frc.robot;

import frc.lib.geometry.Geometry;
import frc.lib.geometry.Pose2;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Twist2;
import frc.lib.util.DriveSignal;

public class Kinematics {


    public static Twist2 forwardKinematics(double dLeft, double dRight) {
        double dTheta = (dRight - dLeft) / RobotConstants.WHEEL_DISTANCE;
        return forwardKinematics(dLeft, dRight, dTheta);
    }

    public static Twist2 forwardKinematics(double dLeft, double dRight, double dTheta) {
        final double dx = (dLeft + dRight) / 2.0;
        return new Twist2(dx, 0.0, dTheta);
    }

    public static Twist2 forwardKinematics(Rotation2 previous, double dLeft, double dRight, Rotation2 current) {
        final double dx = (dLeft + dRight) / 2.0;
        final double dy = 0;
        return new Twist2(dx, dy, previous.inverse().rotate(current).getTheta());
    }

    public static DriveSignal inverseKinematics(Twist2 velocity) {
        if (Math.abs(velocity.deltaTheta()) < Geometry.EPSILON) {
            return new DriveSignal(velocity.deltaX(), velocity.deltaY());
        }

        System.out.println(velocity);

        double deltaV = RobotConstants.WHEEL_DISTANCE * velocity.deltaTheta() / (2 * 10); // TODO Factor
        return new DriveSignal(velocity.deltaX() - deltaV, velocity.deltaY() - deltaV);
    }

    public static Pose2 integrateForwardKinematics(Pose2 current, Twist2 kinematics) {
        return current.transform(Pose2.exp(kinematics));
    }
}
