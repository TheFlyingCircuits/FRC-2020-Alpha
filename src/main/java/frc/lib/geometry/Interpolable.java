package frc.lib.geometry;

public interface Interpolable<T> {
    T interpolate(T other, double p);
}
