package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static enumeration.UserCommands.*;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static final String INVALID_COMMAND_MESSAGE = "Please enter a valid command.";
    private static final String EXIT_AUDIO_PLAYER_COMMAND = "7";
    private static final String INVALID_PLAY_LIST = "There are no songs to play!";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final int INDEX_OF_FIRST_SONG = 0;
    private final List<Song> playList = new ArrayList<>();
    private int currentSongIndex = 0;
    private String userInput;
    private boolean hasNewInput = false;
    private boolean isPaused = false;
    private int timeTheSongWasPaused = 0;

    public AudioPlayer() {
    }

    public void start(String userInput) throws IOException {
        this.userInput = userInput;

        while (!this.userInput.equals(EXIT_AUDIO_PLAYER_COMMAND)) {
            executeUserCommand();

            hasInput();
        }
    }

    /**
     * Handles provided input from the user.
     *
     * @return The input of the user.
     * @throws IOException - In relation to BufferedReader.
     */
    public String getUserInput() throws IOException {
        return bufferedReader.readLine().trim();
    }

    /**
     * Check if there is input. if present validates the input.
     *
     * @throws IOException Chained exception from BufferedReader - userInput field.
     */
    private void hasInput() throws IOException {
        if (!hasNewInput) {
            System.out.println(getListOfCommands());
            userInput = getUserInput();
        } else {
            hasNewInput = false;
        }
    }

    /**
     * Evaluate the given command a execute the corresponding method.
     *
     * @throws IOException Chained exception from BufferedReader - userInput field.
     */
    private void executeUserCommand() throws IOException {
        switch (userInput) {
            case "1":
                play();
                break;

            case "2":
                stop();
                break;

            case "3":
                pause();
                break;

            case "4":
                next();
                break;

            case "5":
                previous();
                break;

            case "6":
                shuffle();
                break;

            default:
                invalidCommand();
                break;
        }
    }

    /**
     * Signals the user that his input is incorrect.
     */
    private void invalidCommand() {
        System.out.println(INVALID_COMMAND_MESSAGE + "\n");
    }

    /**
     * Calculates from which index to start playing the player.
     * Start playing the songs in the order they are in the playlist by calling the overloaded method with the current index.
     * !!! Included Thread.sleep() which may cause problems ....!!!
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void play() throws IOException {
        int startIndex = isPaused ? currentSongIndex : INDEX_OF_FIRST_SONG;

        play(startIndex);
    }

    /**
     * Plays the songs in the list and prints the current song played.
     *
     * @param startIndex - The place in the playlist from witch to start the player.
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void play(int startIndex) throws IOException {
        if (validatePlaylist()){
            return;
        }

        for (; startIndex < playList.size(); startIndex++) {
            currentSongIndex = startIndex;

            System.out.print("Now playing: " + (currentSongIndex + 1) + "\n\t* ");
            System.out.println(playList.get(currentSongIndex).getShortInfo());

            songIsPlaying(playList.get(currentSongIndex).getTiming());

            if (hasNewInput) {
                break;
            }
        }

    }

    /**
     * Validates the playlist.
     *
     * @return True if valid.
     * False if invalid. Print message to indicate invalid playlist.
     */
    private boolean validatePlaylist() {
        if (playList.isEmpty()) {
            System.out.println(INVALID_PLAY_LIST);
            return false;
        }
        return true;
    }

    /**
     * Validate the input.
     *
     * @return Weather the input is valid or not.
     * @throws IOException Chaining exception from BufferedReader in userInput.
     */
    private boolean isInputValid() throws IOException {
        String consoleInput = getUserInput();
        int input = 0;

        if (consoleInput != null && !consoleInput.isBlank()) {
            input = Integer.parseInt(consoleInput);
        }

        if (input >= 1 && input < 7) {
            hasNewInput = true;
            userInput = "" + input;
            return true;
        }
        return false;
    }

    /**
     * Delays the program until the song ends.
     *
     * @param timing Duration of the song in seconds.
     */
    private void songIsPlaying(int timing) throws IOException {
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

            if (bufferedReader.ready()) {
                if (isInputValid()) {
                    timeTheSongWasPaused = startSongFromTime;
                    return;
                }
            }
        }
    }

    /**
     * Nullifies the status of the player.
     */
    private void stop() {
        resetPlaylist();
        isPaused = false;
    }

    /**
     * Creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning.
     */
    private void pause() {
        isPaused = true;
    }

    /**
     * Makes the player to go one index forward in the playlist and validates the index.
     * If the index is bigger than the playlist size, starts the playing of the songs from the beginning
     * of the playlist.
     * Simulating continuous execution of the playlist.
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void next() throws IOException {
        resetPlaylist();
        int nextSongIndex = (currentSongIndex + 1) > playList.size() ?
                1 : (currentSongIndex + 1);
        play(nextSongIndex);
    }

    /**
     * This method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist.
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void previous() throws IOException {
        resetPlaylist();
        int previousSongIndex = (currentSongIndex - 1) < 0 ?
                playList.size() - 1 : (currentSongIndex - 1);
        play(previousSongIndex);
    }

    /**
     * This method takes the current  collection of songs(playlist) and rearranges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled.
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void shuffle() throws IOException {
        resetPlaylist();
        Collections.shuffle(playList);
        play(0);
    }

    private void resetPlaylist() {
        timeTheSongWasPaused = 1;
        currentSongIndex = 0;
    }

    /**
     * Provides string representation of the users options to the user.
     *
     * @return - String representation of the available commands.
     */
    public String getListOfCommands() {
        return "Hello. Please chose a command from the following:\n" +
                "\t 1 \t" + PLAY + "\n" +
                "\t 2 \t" + STOP + "\n" +
                "\t 3 \t" + PAUSE + "\n" +
                "\t 4 \t" + NEXT + "\n" +
                "\t 5 \t" + PREVIOUS + "\n" +
                "\t 6 \t" + SHUFFLE + "\n" +
                "\t 7 \t" + EXIT + "\n";
    }

    /**
     * @return The currently enlisted songs to be played.
     */
    public List<Song> getPlayList() {
        return playList;
    }
}
