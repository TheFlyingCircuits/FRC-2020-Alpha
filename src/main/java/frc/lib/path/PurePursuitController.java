package frc.lib.path;

import frc.lib.geometry.Translation2;
import frc.lib.path.Path.Waypoint;
import frc.lib.util.DriveSignal;
import frc.robot.RobotState;
import lombok.Getter;

import java.util.Optional;

public class PurePursuitController extends PursuitController {


    @Getter private Path path;

    public PurePursuitController(Path path) {
        this.path = path;
    }

    @Override
    public DriveSignal update(RobotState state) {
        boolean onLastSegment = false;
//        int closestIndex = getCl


        return DriveSignal.BRAKE;
    }

    Optional<Translation2> calculateLookahead(Translation2 start, Translation2 end, Translation2 currentPos, double lookaheadDistance, boolean onLastSegment) {
        Optional<Double> tIntersect = calculateIntersectionTValue(start, end, currentPos, lookaheadDistance);

        if (!tIntersect.isPresent()) {
            if (onLastSegment) {
                return Optional.of(path.getBakedPath().get(path.getBakedPath().size() - 1).getTranslation());
            } else {
                return Optional.empty();
            }
        } else {
            Translation2 intersect = end.diff(start);
            Translation2 segment = intersect.scale(tIntersect.get());
            Translation2 point = start.translate(segment);
            return Optional.of(point);
        }
    }

    Optional<Double> calculateIntersectionTValue(Translation2 start, Translation2 end, Translation2 current, double lookaheadDistance) {
        final Translation2 d = end.diff(start);
        final Translation2 f = start.diff(end);

        final double a = Translation2.dot(d, d);
        final double b = 2 * Translation2.dot(f, d);
        final double c = Translation2.dot(f, f) - Math.pow(lookaheadDistance, 2);
        double discriminant = Math.pow(b, 2) - (4 * a * c);

        if (discriminant < 0) {
            return Optional.empty();
        } else {
            discriminant = Math.sqrt(discriminant);
            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);

            if (t1 >= 0 && t1 <= 1) {
                return Optional.of(t1);
            }
            if (t2 >= 0 && t2 <= 1) {
                return Optional.of(t2);
            }

        }

        return Optional.empty();
    }

    int getClosestPointIndex(Translation2 current) {
        double shortestDistance = Double.MAX_VALUE;
        int closest = 0;
//        for (int i = lastC)
        return 0;
    }


}
