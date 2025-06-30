class Player {

    static enum PlayerState {
        STANDING,
        WALKING
    };

    private static PlayerState state;

    private double xPos;
    private double yPos;

    private double xVel;
    private double yVel;

    private int sanityLevel;

    public static PlayerState getState() {
        return state;
    }

    public static void setState(PlayerState newState) {
        state = newState;
    }
}
