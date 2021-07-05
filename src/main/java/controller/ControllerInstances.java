package main.java.controller;

/**
 * Static references to the controllers for ease of controller manipulation.
 * <p>
 * I know this is isn't the recommended practice, but if I don't do this, I have to jump through a thousand hoops and
 * loops just to get a simple thing working. So better to have this than not ¯\_(ツ)_/¯.
 * A tradeoff between best practices and hours of headache...
 */
class ControllerInstances {
    public static CanvasController canvasController;
    public static SettingsController settingsController;
}
