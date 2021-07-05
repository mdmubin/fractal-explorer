package main.java.controller;

/**
 * Static references to the controllers for ease of controller manipulation.
 * <p>
 * If I don't do this, I have to jump through a thousand hoops and loops just to get a simple thing working.
 * So better to have this than not ¯\_(ツ)_/¯
 */
class ControllerInstances {
    public static CanvasPanel canvasController;
    public static SettingsPanel settingsController;
}
