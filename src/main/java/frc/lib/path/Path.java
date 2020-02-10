package frc.lib.path;

import com.google.common.collect.ImmutableList;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Translation2;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final double spacing;
    private final boolean forward;
    private final List<Translation2> path = new ArrayList();
    private Translation2 endTranslation;

    private List<Waypoint> bakedPath;
    private Waypoint bakedEnd;

    Path(double spacing, boolean forward) {
        this.spacing = spacing;
        this.forward = forward;
    }

    void addSegment(Translation2 start, Translation2 end) {
        final List<Translation2> points = fillPoints(start, end, this.spacing);
        path.addAll(points);
        this.endTranslation = end;
    }

    void doneAdding() {
        path.add(endTranslation);
    }

    private List<Translation2> fillPoints(Translation2 start, Translation2 end, double spacing) {
        List<Translation2> filling = new ArrayList<>();

        final Translation2 translation = end.diff(start);
        // number of points
        final double numPoints = Math.ceil(translation.norm() / spacing);

        // get unit vector
        Translation2 otherTranslation = translation.unit().scale(translation.norm() / numPoints);

        //
        for (int i = 0; i < numPoints; i++) {
            filling.add(start.translate(otherTranslation.scale(i)));
        }

        return filling;
    }

    void bake(double maxAccel, double maxVel, double k) {
        this.bakedPath = new ArrayList<Waypoint>(path.size());

        // fill the baked path
        this.path.forEach(t -> {
            bakedPath.add(new Waypoint(t));
        });

        // set start and end curvature to 0
        bakedPath.get(0).setCurvature(0.0);
        bakedPath.get(bakedPath.size() - 1).setCurvature(0.0);

        // calculate curvature for remaining points
        for (int i = 1; i < bakedPath.size() - 1; i++) {
            bakedPath.get(i).setCurvature(calculateCurvatureForPoint(bakedPath, i));
        }

        // calculate distances
        double distance = 0;
        bakedPath.get(0).setDistance(0.0);
        for (int i = 1; i < bakedPath.size(); i++) {
            distance += bakedPath.get(i).getTranslation().distance(bakedPath.get(i - 1).getTranslation());
            bakedPath.get(i).setDistance(distance);
        }

        // update target velocities
        bakedPath.get(bakedPath.size() - 1).setVelocity(0);
        for (int i = bakedPath.size() - 2; i >= 0; i--) {
            double dist = bakedPath.get(i + 1).getTranslation().distance(bakedPath.get(i).getTranslation());
            double maxReachableVelocity = Math.sqrt(Math.pow(bakedPath.get(i + 1).getVelocity(), 2) + (2 * maxAccel * distance));
            bakedPath.get(i).setVelocity(Math.min(calculateMaxVelocityAtPoint(bakedPath, i, maxVel, k), maxReachableVelocity));
        }
    }

    private double calculateMaxVelocityAtPoint(List<Waypoint> points, int index, double maxPathVelocity, double k) {
        if (index > 0) {
            double curvature = calculateCurvatureForPoint(bakedPath, index);
            return Math.min(maxPathVelocity, k / curvature);
        }

        return maxPathVelocity;
    }

    private double calculateCurvatureForPoint(List<Waypoint> points, int index) {
        Waypoint cur = points.get(index);
        Waypoint prev = points.get(index - 1);
        Waypoint next = points.get(index + 1);

        double lA = cur.getTranslation().distance(prev.getTranslation());
        double lB = cur.getTranslation().distance(next.getTranslation());
        double lC = next.getTranslation().distance(prev.getTranslation());

        double sideProduct = lA * lB * lC;
        double semiPerimeter = (lA + lB + lC) / 2.0;
        double triangleArea = Math.sqrt(semiPerimeter * (semiPerimeter - lA) * (semiPerimeter - lB) * (semiPerimeter - lC));

        double radius = sideProduct / ( 4 * triangleArea );
        double curvature = 1 / radius;

        return curvature;
    }

    public List<Waypoint> getBakedPath() {
        return ImmutableList.copyOf(bakedPath);
    }

    public String toCSV() {
        StringBuilder builder = new StringBuilder();
        builder.append("x,y\n");

        for (Waypoint waypoint : getBakedPath()) {
            builder.append(String.format("%s,%s,%s,%s,%s\n",
                    waypoint.getTranslation().getX(),
                    waypoint.getTranslation().getY(),
                    waypoint.getCurvature(),
                    waypoint.getDistance(),
                    waypoint.getVelocity()));
        }

        return builder.toString();
    }

    public static final class Waypoint {
        @Getter @Setter private double curvature, distance, velocity;
        @Getter private final Translation2 translation;
        @Getter private final Rotation2 rotation;

        private Waypoint(Translation2 position) {
            this(position, 0.0, 0.0, 0.0);
        }

        private Waypoint(Translation2 position, double curvature, double distance, double velocity) {
            this.translation = position;
            this.curvature = curvature;
            this.distance = distance;
            this.velocity = velocity;
            this.rotation = Rotation2.IDENTITY;
        }

        public Waypoint withPosition(Translation2 position) {
            return new Waypoint(position, this.curvature, this.distance, this.velocity);
        }

        public Waypoint withPosition(double x, double y) {
            return withPosition(new Translation2(x, y));
        }

    }
}
