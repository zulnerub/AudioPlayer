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
    REMOVE("remove"),
    ADD("add"),
    INFO("info"),
    SEARCH_BY_SONG_TITLE("search_by_song_title"),
    SEARCH_BY_AUTHOR_NAME("search_by_author_name"),
    PLAYLIST_LENGTH("playlist_length"),
    EXIT("exit");

    private final String name;

    UserCommands(String name) {
        this.name = name;
    }

    /**
     * @return The class member name of the enumeration.
     */
    public String getSimpleName() {
        return name;
    }
}
