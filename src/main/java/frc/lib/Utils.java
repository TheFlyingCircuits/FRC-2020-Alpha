package frc.lib;

import frc.lib.geometry.Geometry;

public final class Utils {
    private Utils() {}

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public static double deadband(double value) {
        if (Math.abs(value) < Geometry.EPSILON)
            return 0;
        else return value;
    }
}
