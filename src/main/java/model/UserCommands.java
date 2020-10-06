package model;

/**
 * Stores values for user commands in class model.strategyPatternImpl.AudioPlayer.
 */
public enum UserCommands {
    PLAY("play"),
    STOP("stop"),
    PAUSE("pause"),
    NEXT("next"),
    PREVIOUS("previous"),
    SHUFFLE("shuffle"),
    EXIT("exit");

    private final String name;

    UserCommands(String name) {
        this.name = name;
    }

    public String getSimpleName(){
        return name;
    }

}
