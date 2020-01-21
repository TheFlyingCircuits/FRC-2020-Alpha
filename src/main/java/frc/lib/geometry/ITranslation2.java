package frc.lib.geometry;

public interface ITranslation2<S> extends State<S>, Interpolable<S> {
    Translation2 getTranslation();
}
