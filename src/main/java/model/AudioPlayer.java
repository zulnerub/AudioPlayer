package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static final String INVALID_PLAY_LIST = "There are no songs to play!";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public final List<Song> playList;
    public final int INDEX_OF_FIRST_SONG = 0;
    public int currentSongIndex = 0;
    public boolean isPaused = false;
    public String userInput;
    public boolean hasNewInput = false;
    public int timeTheSongWasPaused = 0;

    public AudioPlayer(List<Song> playList) {
        this.playList = playList;
    }

    /**
     * Delays the program until the song ends.
     *
     * @param timing Duration of the song in seconds.
     */
    public void songIsPlaying(int timing) {
        int startSongFromTime = 1;
        if (isPaused) {
            startSongFromTime = timeTheSongWasPaused;
            resetPlaylist();
            isPaused = false;
        }
        for (; startSongFromTime <= timing; startSongFromTime++) {
            System.out.println(startSongFromTime);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                if (bufferedReader.ready()) {
                    if (isInputValid()) {
                        timeTheSongWasPaused = startSongFromTime;
                        return;
                    }
                }
            } catch (IOException exception) {
                throw new CustomException("BufferedReader was not ready.");
            }

        }
    }

    /**
     * Handles provided input from the user.
     *
     * @return The input of the user.
     * @throws CustomException - In relation to BufferedReader.
     */
    public String getUserInput() {
        try {
            return bufferedReader.readLine().trim();
        } catch (IOException exception) {
            throw new CustomException("Invalid input.");
        }
    }

    /**
     * Validate the input.
     *
     * @return Weather the input is valid or not.
     */
    private boolean isInputValid() {
        String consoleInput = getUserInput();

        if (consoleInput != null && !consoleInput.isBlank()) {
            boolean isCommandValid = Arrays.stream(UserCommands.values())
                    .map(UserCommands::getSimpleName)
                    .collect(Collectors.toList())
                    .contains(consoleInput.toLowerCase());

            if (isCommandValid) {
                hasNewInput = true;
                userInput = "" + consoleInput;
                return true;
            }
        }
        return false;
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
     * Returns the values of the variables, that store conditions
     * about the state od the audio player, to their initial values.
     */
    public void resetPlaylist() {
        timeTheSongWasPaused = 1;
        currentSongIndex = 0;
    }

    /**
     * @return The currently enlisted songs to be played.
     */
    public List<Song> getPlayList() {
        return playList;
    }
}
