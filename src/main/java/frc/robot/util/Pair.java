package frc.robot.util;

import lombok.Getter;

public class Pair<A, B> {

    @Getter private final A a;
    @Getter private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
