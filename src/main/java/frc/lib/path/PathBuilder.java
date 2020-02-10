package frc.lib.path;

import frc.lib.geometry.Translation2;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathBuilder {

    private final double maxVelocity, maxAcceleration, maxVelocityK;
    private final List<Translation2> waypoints = new ArrayList<>();

    private PathBuilder(double maxVelocity, double maxAcceleration, double maxVelocityK) {
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
        this.maxVelocityK = maxVelocityK;
    }

    private void addWaypoint(Translation2 waypoint) {
        this.waypoints.add(waypoint);
    }

    private void addWaypoints(Translation2... waypoints) {
        this.waypoints.addAll(Arrays.asList(waypoints));
    }

    public static PathBuilder withParameters(double maxVelocity, double maxAcceleration, double maxVelocityK) {
        return new PathBuilder(maxVelocity, maxAcceleration, maxVelocityK);
    }

    public PathBuilder addPoint(double x, double y) {
        addWaypoint(new Translation2(x, y));
        return this;
    }

    public PathBuilder addPoint(Translation2 waypoint) {
        addWaypoint(waypoint);
        return this;
    }

    public PathBuilder addPoints(Translation2... waypoints) {
        addWaypoints(waypoints);
        return this;
    }

    public Path buildPath(final double spacing, final boolean forward) {
        Path path = new Path(spacing, forward);

        // add segments to path
        for (int i = 0; i < waypoints.size() - 1; ++i) {
            path.addSegment(waypoints.get(i), waypoints.get(i + 1));
        }

        // add the last points
        path.doneAdding();

        // smooth the path?

        // initialize the path
        path.bake(maxAcceleration, maxVelocity, maxVelocityK);

        return path;
    }

}
