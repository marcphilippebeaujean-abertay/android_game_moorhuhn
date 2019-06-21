package de.awacademy.training.android.gametemplate;
import java.util.List;

public interface ILoopControllerInterface {

    void update(long dt);
    List<IDrawable> getDrawables();
}
