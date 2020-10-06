package model;

import java.util.List;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static final String INVALID_PLAY_LIST = "There are no songs to play!";
    private final List<Song> playList;

    public AudioPlayer(List<Song> playList) {
        this.playList = playList;
    }


    /**
     * Validates the playlist.
     *
     * @return True if valid.
     * False if invalid. Print message to indicate invalid playlist.
     */
    public boolean isPlaylistValid() {
        if (playList.isEmpty()) {
            System.out.println(INVALID_PLAY_LIST);
            return false;
        }
        return true;
    }

    /**
     * @return The currently enlisted songs to be played.
     */
    public List<Song> getPlayList() {
        return playList;
    }

    /**
     * @return String representation of the class fields as info.
     */
    public String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
