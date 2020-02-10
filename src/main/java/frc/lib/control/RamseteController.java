package frc.lib.control;

import frc.lib.geometry.Pose2;
import frc.lib.geometry.Twist2;

public class RamseteController {

    private final double zeta, b;


    public RamseteController() {
        this(0.7, 2.0);
    }

    public RamseteController(double zeta, double b) {
        this.zeta = zeta;
        this.b = b;
    }

    public Twist2 compute(Pose2 current, Pose2 target, Twist2 targetConstraints) {
        Pose2 poseError = target.relativeTo(current);
        final double eX = poseError.getTranslation().getX();
        final double eY = poseError.getTranslation().getY();
        final double eTheta = poseError.getRotation().getRadians();
        final double velocity = targetConstraints.deltaX();
        final double angularVelocity = targetConstraints.deltaTheta();

        final double k = 2.0 * zeta * Math.sqrt(Math.pow(angularVelocity, 2) + b * Math.pow(velocity, 2));
        return new Twist2(velocity * poseError.getRotation().cos() + k * eX,
                0.0,
                angularVelocity + k * eTheta + b * velocity * sinc(eTheta) * eY);
    }

    private double sinc(double x) {
        if (Math.abs(x) < 1e-9) {
            return 1.0 - 1.0 / 6.0 * x * x;
        } else {
            return Math.sin(x) / x;
        }
    }
}
