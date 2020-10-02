import model.Song;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static final String INVALID_COMMAND_MESSAGE = "Please enter a valid command.";
    private static final String EXIT_AUDIO_PLAYER_COMMAND = "7";
    private final List<Song> playList = new ArrayList<>();
    private Song currentSong = null;
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private String userInput;
    private boolean hasNewInput = false;
    private boolean isPaused = false;

    public AudioPlayer() {
    }

    public void start() throws IOException {
        while (!userInput.equals(EXIT_AUDIO_PLAYER_COMMAND)) {
            executeUserCommand();

            hasInput();
        }
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
                play(0);
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
     * Executes the main function of the player. Plays the songs and listens for
     * user input to interrupt the play accordingly to the user likings.
     * As well as providing validation to the input to some extend and interrupting
     * only if the input is relevant.
     * <p>
     * !!! Included Thread.sleep() which may cause problems ....!!!
     *
     * @param index - The place in the playlist from witch to start the player.
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    public void play(int index) throws IOException {
        int startIndex = isPaused ? playList.indexOf(currentSong) : index;
        isPaused = false;

        for (; startIndex < playList.size(); startIndex++) {
            currentSong = playList.get(startIndex);

            System.out.print("Now playing: " + (playList.indexOf(currentSong) + 1) + "\n\t* ");
            System.out.println(currentSong.getShortInfo());

            songIsPlaying();

            if (bufferedReader.ready()) {
                if (isInputValid()) break;
            }
        }

    }

    /**
     * Validate the input.
     *
     * @return Weather the input is valid or not.
     * @throws IOException Chaining exception from BufferedReader in userInput.
     */
    private boolean isInputValid() throws IOException {
        int input = Integer.parseInt(getUserInput());

        if (input >= 1 && input < 7) {
            hasNewInput = true;
            userInput = "" + input;
            return true;
        }
        return false;
    }

    /**
     * Delays the program until the song ends.
     */
    private void songIsPlaying() {
        try {
            Thread.sleep(currentSong.getTiming() * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nullifies the status of the player.
     */
    public void stop() {
        currentSong = null;
        isPaused = false;
    }

    /**
     * Creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning.
     */
    public void pause() {
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
    public void next() throws IOException {
        int nextSongIndex = (playList.indexOf(currentSong) + 1) > playList.size() ?
                1 : playList.indexOf(currentSong) + 1;
        play(nextSongIndex);
    }

    /**
     * This method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist.
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    public void previous() throws IOException {
        int previousSongIndex = (playList.indexOf(currentSong) - 1) < 0 ?
                playList.size() - 1 : playList.indexOf(currentSong) - 1;
        play(previousSongIndex);
    }

    /**
     * This method takes the current  collection of songs(playlist) and rearranges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled.
     *
     * @throws IOException - Cascading exception from lower method in relation to BufferedReader.
     */
    private void shuffle() throws IOException {
        Collections.shuffle(playList);
        play(0);
    }

    /**
     * Provides string representation of the users options to the user.
     *
     * @return - String representation of the available commands.
     */
    public String getListOfCommands() {
        return "Hello. Please chose a command from the following:\n" +
                "\t 1 \tPlay\n" +
                "\t 2 \tStop\n" +
                "\t 3 \tPause\n" +
                "\t 4 \tNext\n" +
                "\t 5 \tPrevious\n" +
                "\t 6 \tShuffle\n" +
                "\t 7 \tExit\n";
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
     * @return The currently enlisted songs to be played.
     */
    public List<Song> getPlayList() {
        return playList;
    }
}
