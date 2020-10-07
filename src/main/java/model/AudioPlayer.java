package model;

import java.util.List;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static final String INVALID_PLAY_LIST = "There are no songs to play!";
    private static final String SONG_REMOVED_MESSAGE = "Song removed.";
    private static final String NO_SONG_WITH_SUCH_NAME_EXIST_MESSAGE = "There is no song with such name";
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
    public List<Song> getPlaylist() {
        return playList;
    }

    /**
     * Validating the song and enlist it to playlist.
     *
     * @param song Object of type song to be added to the current instance of the playlist.
     */
    public String addSongToPlaylist(Song song) {
        if (song != null) {
            getPlaylist().add(song);
            return "Song: " + song.getTitle() + " added to playlist.";
        } else {
            return "Song not added.";
        }
    }

    /**
     * Removes from the playlist a song by its index.
     *
     * @param title Title or part of the title of a song.
     * @return String statement if song was removed or not.
     */
    public String removeSongFromPlayList(String title) {
        Song songToRemove = playList.stream()
                .filter(song -> song.getTitle().contains(title))
                .findFirst()
                .orElse(null);

        if (songToRemove != null) {
            getPlaylist().remove(songToRemove);
            return SONG_REMOVED_MESSAGE;
        }

        return NO_SONG_WITH_SUCH_NAME_EXIST_MESSAGE;
    }

    /**
     * @return String representation of the class fields as info.
     */
    public String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
