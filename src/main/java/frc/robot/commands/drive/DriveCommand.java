package frc.robot.commands.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.util.DriveSignal;
import frc.robot.subsystems.DriveTrain;

public abstract class DriveCommand extends CommandBase {
    protected final DriveTrain driveTrain;

    public DriveCommand(DriveTrain subsystem) {
        addRequirements(subsystem);
        this.driveTrain = subsystem;
    }

    @Override
    public void initialize() {
        // Zero all the encoders
        this.driveTrain.getFREncoder().setPosition(0);
        this.driveTrain.getBREncoder().setPosition(0);
        this.driveTrain.getFLEncoder().setPosition(0);
        this.driveTrain.getBLEncoder().setPosition(0);

        this.driveTrain.getFREncoder().setVelocityConversionFactor(1);
        this.driveTrain.getBREncoder().setVelocityConversionFactor(1);
        this.driveTrain.getFLEncoder().setVelocityConversionFactor(1);
        this.driveTrain.getBLEncoder().setVelocityConversionFactor(1);
        this.driveTrain.getFREncoder().setPositionConversionFactor(1);
        this.driveTrain.getBREncoder().setPositionConversionFactor(1);
        this.driveTrain.getFLEncoder().setPositionConversionFactor(1);
        this.driveTrain.getBLEncoder().setPositionConversionFactor(1);

        // set brake mode
        setBrakeMode(IdleMode.kCoast);
    }

    protected final void setBrakeMode(IdleMode mode) {
        this.driveTrain.getFrontRight().setIdleMode(mode);
        this.driveTrain.getBackRight().setIdleMode(mode);
        this.driveTrain.getFrontLeft().setIdleMode(mode);
        this.driveTrain.getBackLeft().setIdleMode(mode);
    }

    protected final void setLeft(double value) {
        this.driveTrain.getFrontLeft().set(value);
        this.driveTrain.getBackLeft().set(value);
    }

    protected final void setRight(double value) {
        this.driveTrain.getFrontRight().set(value);
        this.driveTrain.getBackRight().set(value);
    }

    protected final void setDrive(DriveSignal signal) {
        setLeft(signal.getLeft());
        setRight(signal.getRight());
    }
}
