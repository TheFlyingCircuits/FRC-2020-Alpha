package frc.lib.util;

import frc.lib.Utils;
import frc.lib.geometry.Pose2;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Twist2;
import frc.robot.Constants;

public final class Kinematics {


    private Kinematics() {
    }

    public static Twist2 forwardKinematics(double dLeft, double dRight) {
        double dTheta = (dRight - dLeft) / Constants.TRACK_WIDTH_INCHES;
        return forwardKinematics(dLeft, dRight, dTheta);
    }

    public static Twist2 forwardKinematics(double dLeft, double dRight, double dTheta) {
        final double dx = (dLeft + dRight) / 2.0;
        return new Twist2(dx, 0.0, dTheta);
    }

    public static Twist2 forwardKinematics(Rotation2 previous, double dLeft, double dRight, Rotation2 current) {
        final double dx = (dLeft + dRight) / 2.0;
        final double dy = 0;
        return new Twist2(dx, dy, previous.inverse().rotate(current).getRadians());
    }

    public static DriveSignal inverseKinematics(Twist2 velocity) {
        return new DriveSignal(velocity.deltaX() - Constants.TRACK_WIDTH_INCHES / 2 * velocity.deltaTheta(),
                velocity.deltaX() + Constants.TRACK_WIDTH_INCHES / 2 * velocity.deltaTheta());
    }

    public static DriveSignal inverseKinematicsOld(Twist2 velocity) {
        if (Math.abs(velocity.deltaTheta()) < Utils.EPSILON) {
            return new DriveSignal(velocity.deltaX(), velocity.deltaY());
        }

        System.out.println(velocity);

        double deltaV = Constants.TRACK_WIDTH_INCHES * velocity.deltaTheta() / (2 * 10); // TODO Factor
        return new DriveSignal(velocity.deltaX() - deltaV, velocity.deltaY() - deltaV);
    }

    public static Pose2 integrateForwardKinematics(Pose2 current, Twist2 kinematics) {
        return current.transform(Pose2.exp(kinematics));
    }
}
