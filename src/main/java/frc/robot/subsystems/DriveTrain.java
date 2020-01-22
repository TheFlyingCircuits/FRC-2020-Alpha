package frc.robot.subsystems;

import com.google.common.collect.ImmutableList;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import lombok.Getter;

import java.util.List;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.RobotConstants.*;

public class DriveTrain extends SubsystemBase {

    private static DriveTrain instance;
    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }

        return instance;
    }

    @Getter private final CANSparkMax frontRight, backRight, frontLeft, backLeft; // TODO slave/master motors?
    @Getter private final CANEncoder FREncoder, BREncoder, FLEncoder, BLEncoder;

    // TODO use ImmutableList from Guava (or another Collection) to get iterable of motor controllers

    public DriveTrain() {
        Robot.log("initializing motor controllers...");
        this.frontRight = new CANSparkMax(FR_CH, MotorType.kBrushless);
        this.backRight = new CANSparkMax(BR_CH, MotorType.kBrushless);
        this.frontLeft = new CANSparkMax(FL_CH, MotorType.kBrushless);
        this.backLeft = new CANSparkMax(BL_CH, MotorType.kBrushless);

        this.FREncoder = this.frontRight.getEncoder();
        this.BREncoder = this.backRight.getEncoder();
        this.FLEncoder = this.frontLeft.getEncoder();
        this.BLEncoder = this.backLeft.getEncoder();

        Robot.log("applying current limits...");
        // current limiting
        this.backLeft.setSmartCurrentLimit(CURRENT_LIMIT);
        this.frontLeft.setSmartCurrentLimit(CURRENT_LIMIT);
        this.backRight.setSmartCurrentLimit(CURRENT_LIMIT);
        this.frontRight.setSmartCurrentLimit(CURRENT_LIMIT);

        // inversion
        this.frontLeft.setInverted(false);
        this.backLeft.setInverted(false);
        this.frontRight.setInverted(true);
        this.backRight.setInverted(true);
    }

    public synchronized void setBrakes(boolean brakes) {
        IdleMode mode = brakes ? IdleMode.kBrake : IdleMode.kCoast;

        frontRight.setIdleMode(mode);
        backRight.setIdleMode(mode);

        frontLeft.setIdleMode(mode);
        backLeft.setIdleMode(mode);
    }

}