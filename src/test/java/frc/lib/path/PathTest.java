package frc.lib.path;

import frc.lib.geometry.Translation2;
import org.junit.jupiter.api.Test;

public class PathTest {

    @Test
    void testBuilder() {
        final Path path = PathBuilder.withParameters(2.0, 1.0, 1.0)
                .addPoint(0, 0)
                .addPoint(100, 300)
                .addPoint(140, 400)
                .addPoint(100, 20)
                .buildPath(3, true);

        System.out.println(path.toCSV());

    }
}
