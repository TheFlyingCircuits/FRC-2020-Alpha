package frc.lib.geometry;

import java.math.BigDecimal;

public final class Geometry {

    public static final double EPSILON = 1E-12;
    public static boolean epsilonEquals(double a, double b, double epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }

    public static boolean epsilonEquals(double a, double b) {
        return epsilonEquals(a, b, Geometry.EPSILON);
    }

    public static double limit(double d, double magnitude) {
        return limit(d, -magnitude, magnitude);
    }

    public static double limit(double d, double min, double max) {
        return Math.min(max, Math.max(min, d));
    }

    private Geometry() {}
}
