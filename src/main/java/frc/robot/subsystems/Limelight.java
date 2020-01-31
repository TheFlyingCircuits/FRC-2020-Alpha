package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.scheduling.Scheduler;
import frc.lib.subsystem.CommandSubsystem;
import lombok.Getter;

public class Limelight extends CommandSubsystem {

    private static Limelight instance;

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }

        return instance;
    }

    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    /* NETWORK TABLE ENTRIES */
    NetworkTableEntry tx = limelightTable.getEntry("tx");
    NetworkTableEntry ty = limelightTable.getEntry("ty");
    NetworkTableEntry ta = limelightTable.getEntry("ta");

    @Getter private final IO io = new IO();

    public Limelight() {
        super("Limelight 1");
    }

    @Override
    public void check() {

    }

    @Override
    public void readIO() {
        io.tx = tx.getDouble(0.0);
        io.ty = ty.getDouble(0.0);
        io.ta = ta.getDouble(0.0);
    }

    @Override
    public void writeIO() {

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("tx", io.tx);
        SmartDashboard.putNumber("ty", io.ty);
        SmartDashboard.putNumber("ta", io.ta);
    }

    @Override
    public void registerLoops(Scheduler scheduler) {

    }

    @Getter private final class IO {
        private double tx, ty, ta;
    }
}
