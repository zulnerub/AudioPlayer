import model.*;
import model.AudioPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static model.Genre.*;
import static model.UserCommands.*;

/**
 * Starting point of the application.
 * Initializing the data.
 * Starting the audio player.
 * Executing the listed methods after exiting the audio player.
 */
public class Application {
    private static final String SINGER_NOT_FOUND_MESSAGE = "Singer not found!";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static AudioPlayerController audioPlayerController;

    private static final Map<String, List<Song>> authorRepository = new HashMap<>();

    private static final List<Song> songsToBePlayed = new ArrayList<>();

    private static String userInput;
    private static boolean hasNewInput = false;

    public static void main(String[] args) {
        init();

        System.out.println(getListOfCommands());

        userInput = getUserInput();

        while (!userInput.equalsIgnoreCase(EXIT.getSimpleName())) {
            if (isInputValidCommand()){
                audioPlayerController.execute(UserCommands.valueOf(userInput.toUpperCase()));
            }

            hasInput();
        }

        System.out.println(info());

        System.out.println("Input song title:");
        System.out.println(getAuthorPositionInPlaylist(getUserInput()));

        System.out.println("Please input full Author name:");

        List<Song> foundSongsOfAuthor = getSongsOfAuthor(getUserInput());

        if (foundSongsOfAuthor != null && !foundSongsOfAuthor.isEmpty()) {
            foundSongsOfAuthor.forEach(s -> System.out.println(s.getShortInfo()));
        } else {
            System.out.println("Author not found.");
        }

        System.out.println("There are: " + getCountOfAllListedSongs() + " songs in the list.");

        System.out.println(removeSongFromPlayList(2));
    }

    public static void setHasNewInput(Boolean isNewInputPresent){
        hasNewInput = isNewInputPresent;
    }

    public static void setUserInput(String newInput){
        userInput = newInput;
    }

    /**
     * Collects the names of the enum UserCommand in a list and
     * checks if that list contains the user input.
     */
    private static boolean isInputValidCommand() {
        return Arrays.stream(UserCommands.values())
                .map(UserCommands::getSimpleName)
                .collect(Collectors.toList())
                .contains(userInput.toLowerCase());
    }

    /**
     * This initializes the application:
     * - creates Objects - Authors, Singers, Songs;
     * - enlists songs in the playlist for the AudioPlayer to work with;
     */
    private static void init() {
        Author johnDou = null;
        Author koleKolev = null;

        try {
            johnDou = new Author("John  Dou");
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            koleKolev = new Author("Kole Kolev");
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        Author nullNameAuthor = null;

        try {
            nullNameAuthor = new Author(null);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        authorRepository.putIfAbsent(johnDou.getName(), new ArrayList<>());
        authorRepository.putIfAbsent(koleKolev.getName(), new ArrayList<>());

        Song getYourFreakOn = null;
        Song prituriSaPlaninata = null;
        Song bojeChuvajJaOdZlo = null;

        try {
            getYourFreakOn = new Song(johnDou, RAP, "Get your freak on", 20);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            prituriSaPlaninata = new Song(koleKolev, COUNTRY, "Prituri sa planinata", 20);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            bojeChuvajJaOdZlo = new Song(koleKolev, POP, "Boje chuvaj ja od zlo", 20);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        Song invalidAuthor = null;
        Song invalidGenre = null;
        Song invalidTitle = null;
        Song invalidTiming = null;

        try {
            invalidAuthor = new Song(null, POP, "Merry christmas", 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidGenre = new Song(koleKolev, null, "Merry christmas", 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidTitle = new Song(koleKolev, POP, null, 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidTiming = new Song(koleKolev, POP, "Merry christmas", 0);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        authorRepository.get(johnDou.getName()).add(getYourFreakOn);

        authorRepository.get(koleKolev.getName()).add(prituriSaPlaninata);
        authorRepository.get(koleKolev.getName()).add(bojeChuvajJaOdZlo);

        System.out.println(addSongToPlaylist(getYourFreakOn));
        System.out.println(addSongToPlaylist(prituriSaPlaninata));
        System.out.println(addSongToPlaylist(bojeChuvajJaOdZlo));

        AudioPlayer audioPlayer = new AudioPlayer(songsToBePlayed);
        audioPlayerController = new AudioPlayerController(audioPlayer);

    }

    /**
     * Check if there is input. if present validates the input.
     */
    private static void hasInput() {
        if (!hasNewInput) {
            System.out.println(getListOfCommands());
            userInput = getUserInput();
        } else {
            hasNewInput = false;
        }
    }

    /**
     * Handles provided input from the user.
     *
     * @return The input of the user.
     * @throws CustomException - In relation to BufferedReader.
     */
    private static String getUserInput() {
        try {
            return bufferedReader.readLine().trim();
        } catch (IOException exception) {
            throw new CustomException("Invalid input.");
        }
    }

    /**
     * Provides string representation of the users options to the user.
     *
     * @return - String representation of the available commands.
     */
    private static String getListOfCommands() {
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
     * Gets the singer by song if found and gives the according response.
     *
     * @param songTitle String that occurs somewhere in the song's title.
     * @return String representation of the singer and position of the song or
     * if not found string that singer was not found.
     */
    private static String getAuthorPositionInPlaylist(String songTitle) {
        String singerName;
        int songIndex;

        Optional<Song> foundSong = audioPlayerController.getPlaylist().stream()
                .filter(s -> s.getTitle().contains(songTitle))
                .findFirst();

        if (foundSong.isPresent()) {
            singerName = foundSong.get().getAuthor().getName();
            songIndex = audioPlayerController.getPlaylist().indexOf(foundSong.get()) + 1;

            return "Singer name: " + singerName +
                    "\nPosition in playlist: " + songIndex + "\n";
        }

        return SINGER_NOT_FOUND_MESSAGE + "\n";
    }

    /**
     * Gathering a list of songs that match the criteria.
     *
     * @param authorName String - string that is checked if it is contained in any singer name.
     * @return String representation of the found songs of the singer.
     * or empty list if no songs are found.
     */
    private static List<Song> getSongsOfAuthor(String authorName) {
        List<Song> foundSongs = authorRepository.get(authorName);
        return foundSongs == null || !foundSongs.isEmpty() ? foundSongs : new ArrayList<>();
    }

    /**
     * @return Gets the current number of objects in the playlist.
     */
    private static int getCountOfAllListedSongs() {
        return audioPlayerController.getPlaylist().size();
    }

    /**
     * Removes from the playlist a song by its index.
     *
     * @param index Index of the song to remove.
     * @return String statement if song was removed or not.
     */
    private static String removeSongFromPlayList(int index) {
        if (index >= 0 && index < audioPlayerController.getPlaylist().size()) {
            audioPlayerController.getPlaylist().remove(index);
            return "Song removed.";
        }

        return "Provided index is not valid. Please add valid index.";
    }

    /**
     * Validating the song and enlist it to playlist.
     *
     * @param song Object of type song to be added to the current instance of the playlist.
     */
    private static String addSongToPlaylist(Song song) {
        if (song != null) {
            songsToBePlayed.add(song);
            return "Song: " + song.getTitle() + " added to playlist.";
        } else {
            return "Song not added.";
        }
    }

    /**
     * @return String representation of the class fields as info.
     */
    private static String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
