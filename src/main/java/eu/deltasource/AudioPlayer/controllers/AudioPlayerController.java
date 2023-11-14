package eu.deltasource.AudioPlayer.controllers;

import static eu.deltasource.AudioPlayer.model.UserCommands.ADD;
import static eu.deltasource.AudioPlayer.model.UserCommands.EXIT;
import static eu.deltasource.AudioPlayer.model.UserCommands.INFO;
import static eu.deltasource.AudioPlayer.model.UserCommands.NEXT;
import static eu.deltasource.AudioPlayer.model.UserCommands.PAUSE;
import static eu.deltasource.AudioPlayer.model.UserCommands.PLAY;
import static eu.deltasource.AudioPlayer.model.UserCommands.PLAYLIST_LENGTH;
import static eu.deltasource.AudioPlayer.model.UserCommands.PREVIOUS;
import static eu.deltasource.AudioPlayer.model.UserCommands.REMOVE;
import static eu.deltasource.AudioPlayer.model.UserCommands.SEARCH_BY_AUTHOR_NAME;
import static eu.deltasource.AudioPlayer.model.UserCommands.SEARCH_BY_SONG_TITLE;
import static eu.deltasource.AudioPlayer.model.UserCommands.SHUFFLE;
import static eu.deltasource.AudioPlayer.model.UserCommands.STOP;

import eu.deltasource.AudioPlayer.commands.AddSongCommand;
import eu.deltasource.AudioPlayer.commands.Command;
import eu.deltasource.AudioPlayer.commands.NextCommandImpl;
import eu.deltasource.AudioPlayer.commands.PauseCommandImpl;
import eu.deltasource.AudioPlayer.commands.PlayCommandImpl;
import eu.deltasource.AudioPlayer.commands.PreviousCommandImpl;
import eu.deltasource.AudioPlayer.commands.PrintAudioPlayerInfoCommand;
import eu.deltasource.AudioPlayer.commands.PrintLengthOfPlaylist;
import eu.deltasource.AudioPlayer.commands.RemoveSongCommand;
import eu.deltasource.AudioPlayer.commands.SearchByAuthorName;
import eu.deltasource.AudioPlayer.commands.SearchBySongTitle;
import eu.deltasource.AudioPlayer.commands.ShuffleCommandImpl;
import eu.deltasource.AudioPlayer.commands.StopCommandImpl;
import eu.deltasource.AudioPlayer.model.AudioPlayer;
import eu.deltasource.AudioPlayer.model.CustomException;
import eu.deltasource.AudioPlayer.model.Song;
import eu.deltasource.AudioPlayer.model.UserCommands;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class provides the connection between the main eu.deltasource.Application class and
 * the execution of commands. Using the AudioPlayer class to execute commands of the user.
 */
public class AudioPlayerController {
    private static final String SINGER_NOT_FOUND_MESSAGE = "Singer not found!";
    private static final String INTERRUPTED_WHILE_THREAD_WAS_SLEEPING_MESSAGE = "Interrupted while playing song at Thread.sleep().";
    private static final String BUFFERED_READER_NOT_READY_MESSAGE = "BufferedReader was not ready.";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final int DEFAULT_STARTING_POSITION_FOR_SONG = 1;
    private static final int DEFAULT_STARTING_INDEX_OF_PLAYLIST = 0;
    private static final String INVALID_USER_INPUT_MESSAGE = "Invalid input.";
    private static final String WRONG_TITLE_INPUT_MESSAGE = "The searched title must not be empty or blank.";
    private static final String ENTER_SONGS_TITLE_TO_BE_REMOVED_MESSAGE = "Please enter a title of the song to be removed:";
    private AudioPlayer audioPlayer = new AudioPlayer(new ArrayList<>());
    private final Map<UserCommands, Command> availableCommands = new LinkedHashMap<>();
    private String userInput;
    public int timeTheSongWasPaused;
    public int currentSongIndex;
    public boolean isPaused = false;
    public boolean isShufflePressed = false;
    public boolean hasToSwitchSong = false;
    public boolean hasNewInput = false;

    public AudioPlayerController() {
        initializeAvailableCommands();
    }

    /**
     * Brings the audioPlayerController to active state,
     * ready to accept user input.
     */
    public void start() {
        System.out.println(getListOfCommands());

        userInput = getUserInput();

        while (!userInput.equalsIgnoreCase(EXIT.getSimpleName())) {
            UserCommands command;

            if (isInputValidCommand()) {
                command = UserCommands.valueOf(userInput.toUpperCase());

                execute(command);

                if (availableCommands.get(command).hasToStartAgain()) {
                    execute(PLAY);
                }
            }

            hasInput();
        }
    }

    /**
     * Delays the program until the song ends.
     *
     * @param timing Duration of the song in seconds.
     */
    public void playSong(int timing) {
        int startSongFromTime = 1;

        if (isPaused) {
            startSongFromTime = timeTheSongWasPaused;
            resetPlaylist();
            isPaused = false;
        }

        if (hasToSwitchSong) {
            hasToSwitchSong = false;
        }

        for (; startSongFromTime <= timing; startSongFromTime++) {
            System.out.println(startSongFromTime);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new CustomException(INTERRUPTED_WHILE_THREAD_WAS_SLEEPING_MESSAGE);
            }

            try {
                if (bufferedReader.ready()) {
                    if (isInputValid()) {
                        timeTheSongWasPaused = startSongFromTime;
                        return;
                    }
                }
            } catch (IOException exception) {
                throw new CustomException(BUFFERED_READER_NOT_READY_MESSAGE);
            }
        }
    }

    /**
     * Returns the values of the variables, that store conditions
     * about the state od the audio player, to their initial values.
     */
    public void resetPlaylist() {
        isPaused = false;
        isShufflePressed = false;
        hasToSwitchSong = false;
        timeTheSongWasPaused = DEFAULT_STARTING_POSITION_FOR_SONG;
        currentSongIndex = DEFAULT_STARTING_INDEX_OF_PLAYLIST;
    }

    /**
     * @return String representation of basic info for AudioPlayer class.
     */
    public String getAudioPlayerInfo() {
        return audioPlayer.info();
    }

    /**
     * Gets the user input and calls the corresponding command on the audio player.
     *
     * @param command Enum representation of the user command.
     */
    public void execute(UserCommands command) {
        availableCommands.get(command)
                .action(this);
    }

    /**
     * @return Gets the song currently being played.
     */
    public Song getCurrentSong() {
        return audioPlayer.getPlaylist().get(currentSongIndex);
    }

    /**
     * @return Fetches the audio player's playlist.
     */
    public List<Song> getPlaylist() {
        return audioPlayer.getPlaylist();
    }

    /**
     * @return Gets the current number of objects in the playlist.
     */
    public int getCountOfAllListedSongs() {
        return audioPlayer.getPlaylist().size();
    }

    /**
     * Calls the method of the audio player to remove a song from the playlist.
     *
     * @return Message whether the song was removed ot not.
     */
    public String removeSongFromPlaylist() {
        System.out.println(ENTER_SONGS_TITLE_TO_BE_REMOVED_MESSAGE);

        String title = getUserInput();

        if (title.isBlank()) {
            return WRONG_TITLE_INPUT_MESSAGE;
        }
        return audioPlayer.removeSongFromPlayList(title);
    }

    /**
     * Calls the method of the audio player to add a song to the playlist.
     *
     * @param song Object of class Song to be added to the playlist of the audio player.
     * @return String representation whether the command was executed or not.
     */
    public String addSongToPlaylist(Song song) {
        return audioPlayer.addSongToPlaylist(song);
    }

    /**
     * Validate the input.
     *
     * @return Weather the input is valid or not.
     */
    private boolean isInputValid() {
        String consoleInput = getUserInput();

        if (!consoleInput.isBlank()) {
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
     * Handles provided input from the user.
     *
     * @return The input of the user.
     * @throws CustomException - In relation to BufferedReader.
     */
    public String getUserInput() {
        try {
            return bufferedReader.readLine().trim();
        } catch (IOException exception) {
            throw new CustomException(INVALID_USER_INPUT_MESSAGE);
        }
    }

    /**
     * Check if there is input. if present validates the input.
     */
    private void hasInput() {
        if (!hasNewInput) {
            System.out.println(getListOfCommands());
            userInput = getUserInput();
        } else {
            hasNewInput = false;
        }
    }

    /**
     * Collects the names of the enum UserCommand in a list and
     * checks if that list contains the user input.
     */
    private boolean isInputValidCommand() {
        return Arrays.stream(UserCommands.values())
                .map(UserCommands::getSimpleName)
                .collect(Collectors.toList())
                .contains(userInput.toLowerCase());
    }


    /**
     * Gets the singer by song if found and gives the according response.
     *
     * @return String representation of the singer and position of the song or
     * if not found string that singer was not found.
     */
    public String getAuthorPositionInPlaylist() {
        String songTitle = getUserInput();

        String singerName;

        int songIndex;

        Optional<Song> foundSong = getPlaylist().stream()
                .filter(s -> s.getTitle().contains(songTitle))
                .findFirst();

        if (foundSong.isPresent()) {
            singerName = foundSong.get().getAuthor().getName();
            songIndex = getPlaylist().indexOf(foundSong.get()) + 1;

            return "Singer name: " + singerName +
                    "\nPosition in playlist: " + songIndex + "\n";
        }

        return SINGER_NOT_FOUND_MESSAGE + "\n";
    }

    /**
     * Gathering a list of songs that match the criteria.
     *
     * @return String representation of the found songs of the singer.
     * or empty list if no songs are found.
     */
    public List<Song> getSongsOfAuthor() {
        String authorName = getUserInput();

        return audioPlayer.getPlaylist()
                .stream()
                .filter(song ->
                        song.getAuthor()
                                .getName().toLowerCase()
                                .contains(authorName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * @return Gets the result of the validation method from AudioPlayer class
     */
    public boolean validatePlaylist() {
        return audioPlayer.isPlaylistValid(audioPlayer.getPlaylist());
    }

    /**
     * Provides string representation of the users options to the user.
     *
     * @return - String representation of the available commands.
     */
    private String getListOfCommands() {
        return "Hello. Please chose a command from the following:\n" +
                "\t  1 \t" + PLAY + "\n" +
                "\t  2 \t" + STOP + "\n" +
                "\t  3 \t" + PAUSE + "\n" +
                "\t  4 \t" + NEXT + "\n" +
                "\t  5 \t" + PREVIOUS + "\n" +
                "\t  6 \t" + SHUFFLE + "\n" +
                "\t  7 \t" + ADD + "\n" +
                "\t  8 \t" + REMOVE + "\n" +
                "\t  9 \t" + INFO + "\n" +
                "\t 10 \t" + SEARCH_BY_SONG_TITLE + "\n" +
                "\t 11 \t" + SEARCH_BY_AUTHOR_NAME + "\n" +
                "\t 12 \t" + PLAYLIST_LENGTH + "\n" +
                "\t 13 \t" + EXIT + "\n";
    }

    /**
     * Use to populate the map with the audio player's commands.
     */
    private void initializeAvailableCommands() {
        availableCommands.put(PLAY, new PlayCommandImpl());
        availableCommands.put(STOP, new StopCommandImpl());
        availableCommands.put(PAUSE, new PauseCommandImpl());
        availableCommands.put(NEXT, new NextCommandImpl());
        availableCommands.put(PREVIOUS, new PreviousCommandImpl());
        availableCommands.put(SHUFFLE, new ShuffleCommandImpl());
        availableCommands.put(REMOVE, new RemoveSongCommand());
        availableCommands.put(ADD, new AddSongCommand());
        availableCommands.put(INFO, new PrintAudioPlayerInfoCommand());
        availableCommands.put(SEARCH_BY_SONG_TITLE, new SearchBySongTitle());
        availableCommands.put(SEARCH_BY_AUTHOR_NAME, new SearchByAuthorName());
        availableCommands.put(PLAYLIST_LENGTH, new PrintLengthOfPlaylist());
    }
}
