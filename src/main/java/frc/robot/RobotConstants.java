/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotConstants {
    /* MOTOR CONTROLLERS */
    public static final int FR_CH = 3;
    public static final int BR_CH = 4;
    public static final int FL_CH = 1;
    public static final int BL_CH = 2;

    public static final int CURRENT_LIMIT = 40;

    /* JOYSTICKS */
    public static final int LJS_CH = 1;
    public static final int RJS_CH = 0;

    /* JOYSTICK BUTTONS */
    public static final int QUICKTURN_CH = 2; // TODO correct channel

    /* ROBOT */
    public static final double WHEEL_DISTANCE = 21.0;
    public static final double encoderPerRotation = (5.35 + 5.38) / 2.0;
    public static final double wheelDiameterA = 4.0;
    public static final double wheelCircumferenceA = wheelDiameterA * Math.PI;
    public static final double positionFactorA = wheelCircumferenceA / encoderPerRotation;
    public static final double velocityFactorA = 10.0 / 240.0;

    private RobotConstants() {
    }
}
