package controllers;

import commands.*;
import model.AudioPlayer;
import model.CustomException;
import model.Song;
import model.UserCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static model.UserCommands.*;

/**
 * This class provides the connection between the main Application class and
 * the execution of commands. Using the AudioPlayer class to execute commands of the user.
 */
public class AudioPlayerController {
    private static final String SINGER_NOT_FOUND_MESSAGE = "Singer not found!";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private AudioPlayer audioPlayer;
    private Map<UserCommands, Command> availableCommands = new LinkedHashMap<>();
    public String userInput;
    public boolean isPaused = false;
    public boolean hasNewInput = false;
    public boolean isShufflePressed = false;
    public int currentSongIndex = 0;
    public int timeTheSongWasPaused = 0;

    public AudioPlayerController(List<Song> playlist) {
        this.audioPlayer = new AudioPlayer(playlist);
        availableCommands.put(PLAY, new PlayCommandImpl());
        availableCommands.put(STOP, new StopCommandImpl());
        availableCommands.put(PAUSE, new PauseCommandImpl());
        availableCommands.put(NEXT, new NextCommandImpl());
        availableCommands.put(PREVIOUS, new PreviousCommandImpl());
        availableCommands.put(SHUFFLE, new ShuffleCommandImpl());
    }

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

    public String getAudioPlayerInfo() {
        return audioPlayer.info();
    }

    /**
     * Returns the values of the variables, that store conditions
     * about the state od the audio player, to their initial values.
     */
    public void resetPlaylist() {
        isShufflePressed = false;
        timeTheSongWasPaused = 1;
        currentSongIndex = 0;
    }

    /**
     * Handles provided input from the user.
     *
     * @return The input of the user.
     * @throws CustomException - In relation to BufferedReader.
     */
    private String getUserInput() {
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
     * Gets the user input and calls the corresponding command on the audio player.
     *
     * @param command Enum representetion of the user command.
     */
    public void execute(UserCommands command) {
        availableCommands.get(command).action(this);

        if (command.equals(SHUFFLE) || command.equals(PREVIOUS) || command.equals(NEXT)) {
            availableCommands.get(PLAY).action(this);
        }
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public List<Song> getPlaylist(){
        return audioPlayer.getPlayList();
    }

    /**
     * @return Gets the current number of objects in the playlist.
     */
    public int getCountOfAllListedSongs() {
        return audioPlayer.getPlayList().size();
    }

    /**
     * Removes from the playlist a song by its index.
     *
     * @param index Index of the song to remove.
     * @return String statement if song was removed or not.
     */
    public String removeSongFromPlayList(int index) {
        if (index >= 0 && index < getPlaylist().size()) {
            getPlaylist().remove(index);
            return "Song removed.";
        }

        return "Provided index is not valid. Please add valid index.";
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
     * Provides string representation of the users options to the user.
     *
     * @return - String representation of the available commands.
     */
    private String getListOfCommands() {
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
        return audioPlayer.getPlayList()
                .stream()
                .filter(song -> song.getAuthor().getName().contains(authorName))
                .collect(Collectors.toList());
    }
}
