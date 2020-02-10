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
public final class Constants {
    /* MOTOR CONTROLLERS */
    public static final int FR_CH = 3;
    public static final int BR_CH = 4;
    public static final int FL_CH = 1;
    public static final int BL_CH = 2;
    public static final int CLIMB_A = 6;
    public static final int CLIMB_B = 7;
    public static final int SHOOTERA_CH = 8;
    public static final int SHOOTERB_CH = 9;

    public static final int CURRENT_LIMIT = 40;

    /* JOYSTICKS */
    public static final int LJS_CH = 1;
    public static final int RJS_CH = 0;

    /* JOYSTICK BUTTONS */
    public static final int QUICKTURN_CH = 2; // TODO correct channel

    /* ROBOT */
    public static final double TRACK_WIDTH_INCHES = 21.0;
    public static final double encoderPerRotation = (5.35 + 5.38) / 2.0;
    public static final double wheelDiameterA = 4.0;
    public static final double wheelCircumferenceA = wheelDiameterA * Math.PI;
    public static final double positionFactorA = wheelCircumferenceA / encoderPerRotation;
    public static final double velocityFactorA = 10.0 / 240.0;

    /* ENCODERS */
    public static final double TPR = 4096.0;
    public static final double VTR = (5.0/7.11)/238.0;
    public static final double IPT = (4.0 * Math.PI)/5.45;

    /* PATH PURSUIT */
    public static final double DEFAULT_MAX_SPEED = 10;
    public static final double DEFAULT_MAX_ACCEL = 1;
    public static final double DEFAULT_WPT_RADIUS = 5;
    public static final int IR_SENSOR_ID = 0;

    private Constants() {
    }
}
