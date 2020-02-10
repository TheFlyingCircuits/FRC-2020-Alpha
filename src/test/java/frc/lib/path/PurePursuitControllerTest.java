package frc.lib.path;

import frc.lib.geometry.Translation2;
import org.checkerframework.dataflow.qual.Pure;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PurePursuitControllerTest {

    private final Path path = PathBuilder.withParameters(5.0, 1.0, 1.0)
            .addPoint(0, 0)
            .addPoint(10, 3)
            .addPoint(30, 20)
            .addPoint(3, 30)
            .buildPath(5, true);


    @Test
    void calculateIntersectionTValue() {
        final PurePursuitController controller = new PurePursuitController(path);

        Optional<Double> opt = controller.calculateIntersectionTValue(
                Translation2.of(0, 0),
                Translation2.of(3, 4),
                Translation2.of(1, 1),
                3);

        System.out.println(opt.orElse(Double.MAX_VALUE));

    }
}