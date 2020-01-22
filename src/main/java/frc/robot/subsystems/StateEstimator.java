package frc.robot.subsystems;

public class StateEstimator {

    private double lastTimestamp;


    public void tick(final double timestamp) {
        final double dt = timestamp - lastTimestamp;
        final double leftDistance = 

        // update last values
        lastTimestamp = timestamp;
    }


}
