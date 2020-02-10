package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.scheduling.Scheduler;
import frc.lib.subsystem.Subsystem;
import frc.robot.Constants;
import lombok.Getter;

public class IRSensor extends Subsystem {

    private static IRSensor instance;

    public static IRSensor getInstance() {
        if (instance == null) {
            instance = new IRSensor();
        }

        return instance;
    }

    private final DigitalInput sensor = new DigitalInput(Constants.IR_SENSOR_ID);
    private final IO io = new IO();

    public IRSensor() {
        super("IR Sensor");
    }

    @Override
    public void check() {

    }

    @Override
    public void readIO() {
        io.activated = sensor.get();
    }

    @Override
    public void writeIO() {

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("irSensor", io.activated);
    }

    @Override
    public void registerLoops(Scheduler scheduler) {

    }

    @Getter public static class IO {
        private boolean activated;
        private IO() {}
    }
}
