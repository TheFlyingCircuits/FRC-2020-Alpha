package frc.lib.path;

import frc.lib.util.DriveSignal;
import frc.robot.RobotState;

public abstract class PursuitController {

    public abstract DriveSignal update(RobotState state);

}
