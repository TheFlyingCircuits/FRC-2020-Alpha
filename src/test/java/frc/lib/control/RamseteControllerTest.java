package frc.lib.control;

import frc.lib.control.Path.Waypoint;
import frc.lib.geometry.Pose2;
import frc.lib.geometry.Rotation2;
import frc.lib.geometry.Translation2;
import frc.lib.geometry.Twist2;
import frc.lib.util.Kinematics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RamseteControllerTest {

    RamseteController controller;

    @BeforeEach
    void setUp() {
        controller = new RamseteController();
    }

    @Test
    void testCompute() {
        final Pose2 robotPose = Pose2.of(2.7, 23.0, 0.0);

        final List<Waypoint> waypoints = new ArrayList<>();
//        waypoints.add(new Waypoint(Pose2.of()))

    }
}