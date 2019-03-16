package org.rnq.bindingoffenrir;

@SuppressWarnings("SpellCheckingInspection")
public final class Constants {
    // These come from the art and level design.
    // Other values will be calculated based off these.
    // - Tiles are 16x16 pixels
    // - Player should see 16 tiles between the left and right sides of the game window
    // - Player should see 15 tiles between the top and bottom sides of the game window
    private static final int TILE_SIZE_IN_PIXELS = 16;
    public static final int GAME_WIDTH_IN_TILES = 16;
    public static final int GAME_HEIGHT_IN_TILES = 15;

    // Game window width and height in pixels, unscaled.
    // These are based on our starting values, but the final values
    // used for the actual width/height will be scaled up further.
    private static final int GAME_WIDTH_IN_PIXELS = GAME_WIDTH_IN_TILES * TILE_SIZE_IN_PIXELS; // 256
    private static final int GAME_HEIGHT_IN_PIXELS = GAME_HEIGHT_IN_TILES * TILE_SIZE_IN_PIXELS; // 240

    // Rendering
    private static final float PPM = TILE_SIZE_IN_PIXELS;
    public static final float PIXELS_TO_METERS = 1f / PPM; // This is our "unit scale"

    // Window
    public static final String TITLE = "G L E I P N I R :: Binding Of Fenrir";
    private static final int SCALE_FACTOR = 3;
    public static final int WINDOW_WIDTH = GAME_WIDTH_IN_PIXELS * SCALE_FACTOR; // 768
    public static final int WINDOW_HEIGHT = GAME_HEIGHT_IN_PIXELS * SCALE_FACTOR; // 720

    // Physics
    public static final float GRAVITY = -9.8f;
    public static final float MAX_STEP_TIME = 1/45f;
    public static final float MAX_VELOCITY = 6f;

    // System Processing Order -- lower first
    public static final int PRIORITY_PLAYERCONTROL = 0;
    public static final int PRIORITY_PLAYERINPUT = 1;
    public static final int PRIORITY_MOVEMENT = 2;
    public static final int PRIORITY_PHYSICS = 3;
    public static final int PRIORITY_COLLISION = 4;
    public static final int PRIORITY_ANIMATION = 4;
    public static final int PRIORITY_RENDERING = 5;
    public static final int PRIORITY_PHYSICSDEBUG = 6;
}
