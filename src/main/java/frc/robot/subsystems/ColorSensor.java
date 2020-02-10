package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.lib.scheduling.Scheduler;
import frc.lib.subsystem.Subsystem;
import lombok.Getter;

public class ColorSensor extends Subsystem {

    private static ColorSensor instance;
    public static ColorSensor getInstance() {
        if (instance == null) {
            instance = new ColorSensor();
        }

        return instance;
    }

    private final ColorSensorV3 colorSensorV3 = new ColorSensorV3(Port.kOnboard);
    @Getter private final IO io = new IO();

    public ColorSensor() {
        super("Color Sensor v3");
    }

    @Override
    public void check() {

    }

    @Override
    public void readIO() {
        io.red = colorSensorV3.getRed();
        io.green = colorSensorV3.getGreen();
        io.blue = colorSensorV3.getBlue();
        io.ir = colorSensorV3.getIR();
        io.proximity = colorSensorV3.getProximity();
    }

    @Override
    public void writeIO() {

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putString("color", String.format("rgb(%s,%s,%s)", io.red, io.green, io.blue));
        SmartDashboard.putNumber("sensorProximity", io.proximity);
        SmartDashboard.putNumber("sensorIR", io.ir);
    }

    @Override
    public void registerLoops(Scheduler scheduler) {

    }

    @Getter private final class IO {
        private int red, green, blue, ir, proximity;
    }
}
