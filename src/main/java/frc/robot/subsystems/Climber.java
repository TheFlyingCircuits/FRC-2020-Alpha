package frc.robot.subsystems;

import com.revrobotics.ControlType;
import frc.lib.drive.BetterSpark;
import frc.lib.scheduling.Scheduler;
import frc.lib.subsystem.CommandSubsystem;
import frc.robot.Constants;

public class Climber extends CommandSubsystem {

    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;
    }

    private final BetterSpark A = new BetterSpark(Constants.CLIMB_A), B = new BetterSpark(Constants.CLIMB_B);
    private final IO io = new IO();

    public Climber() {
        super("Climb");
    }

    @Override
    public void check() {

    }

    @Override
    public void readIO() {

    }

    @Override
    public void writeIO() {
        A.set(ControlType.kDutyCycle, io.output);
        B.set(ControlType.kDutyCycle, io.output);
    }

    public void setClimb(double speed) {
        io.output = speed;
        io.output = speed;
    }

    @Override
    public void updateDashboard() {

    }

    @Override
    public void registerLoops(Scheduler scheduler) {

    }

    private static class IO {
        private double output;
    }
}
