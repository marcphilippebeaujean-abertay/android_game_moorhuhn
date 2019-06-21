package de.awacademy.training.android.gametemplate.controllers;

import java.util.LinkedList;
import java.util.List;

import de.awacademy.training.android.gametemplate.IDrawable;
import de.awacademy.training.android.gametemplate.ILoopControllerInterface;
import de.awacademy.training.android.gametemplate.user_interface.UiElement;

public class MenuController implements ILoopControllerInterface {
    LinkedList<UiElement> uiElements;

    public MenuController() {
        uiElements = new LinkedList<>();
    }

    public void update(long dt) {}

    @Override
    public List<IDrawable> getDrawables(){
        return new LinkedList<IDrawable>(uiElements);
    }
}
