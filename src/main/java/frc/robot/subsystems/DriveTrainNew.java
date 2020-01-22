package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.DriveSignal;
import lombok.Getter;

public class DriveTrainNew extends SubsystemBase {

    private DriveSignal signal;


    public synchronized void setSignal(DriveSignal signal) {
        this.signal = signal;
    }
}
