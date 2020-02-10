package frc.lib.control;

import frc.lib.geometry.ITranslation2;
import frc.lib.geometry.Pose2;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Translation2;
import frc.robot.Constants;
import lombok.Getter;

import java.util.List;

public final class Path {

    private final List<Waypoint> waypoints;

    private Path(Waypoint... waypoints) {
        this.waypoints = List.of(waypoints);
    }

//    public static Path buildFrom(Waypoint... waypoints) {
//        if (waypoints.length < 2) {
//            throw new IllegalArgumentException("Must provide 2 or more waypoints");
//        }
//
//        if (waypoints.length > 2) {
//            do {
//
//            } while (true)
//        }
//    }

//    public final BakedPath bake() {
//
//    }

    public static final class Waypoint implements ITranslation2<Waypoint> {
        @Deprecated @Getter private double radius;
        @Deprecated @Getter private double maxSpeed, maxAccel;
        @Getter private final Pose2 pose;

        public Waypoint(Waypoint other) {
            this(other.pose);
        }

        public Waypoint(Pose2 pose, double maxSpeed, double maxAccel, double radius) {
            this.pose = pose;
            this.maxSpeed = maxSpeed;
            this.maxAccel = maxAccel;
            this.radius = radius;
        }

        public Waypoint(Pose2 pose) {
            this(pose, Constants.DEFAULT_MAX_SPEED, Constants.DEFAULT_MAX_ACCEL, Constants.DEFAULT_WPT_RADIUS);
        }

        @Override
        public double distance(Waypoint other) {
            return pose.distance(other.pose);
        }

        @Override
        public Waypoint interpolate(Waypoint other, double x) {
            if (x <= 0) {
                return new Waypoint(this);
            } else if (x >= 1) {
                return new Waypoint(other);
            } else {
                return new Waypoint(pose.interpolate(other.pose, x),
                        x * (other.maxSpeed - this.maxSpeed) + this.maxSpeed,
                        x * (other.maxAccel - this.maxAccel) + this.maxAccel,
                        x * (other.radius - this.radius) + this.radius);
            }
        }

        @Override
        public Translation2 getTranslation() {
            return pose.getTranslation();
        }
    }

    public static final class Line {
        private final Waypoint a, b;
        private final Translation2 slope, start, end;
        private final double speed;

        public Line(Waypoint a, Waypoint b) {
            this.a = a;
            this.b = b;
            this.speed = b.maxSpeed;

            // get "slope" vector
            this.slope = new Translation2(a.getTranslation(), b.getTranslation());

            // calculate start and end translations
            this.start = a.getTranslation().translate(slope.scale(a.radius / slope.norm()));
            this.end = b.getTranslation().translate(slope.scale(-b.radius / slope.norm()));
        }
    }

    public static final class Arc {
        private final Line a, b;
        private final Translation2 center;
        private final double speed, radius;

        public Arc(Line a, Line b) {
            this.a = a;
            this.b = b;
            this.speed = (a.speed + b.speed) / 2.0;
            this.center = intersect(a, b);
            this.radius = new Translation2(center, a.end).norm();
        }

        private static Translation2 intersect(Line i1, Line i2) {
            final Pose2 lineA = new Pose2(i1.end, new Rotation2(i1.slope, true).normal());
            final Pose2 lineB = new Pose2(i2.start, new Rotation2(i2.slope, true).normal());
            return lineA.intersection(lineB);
        }
    }
}
