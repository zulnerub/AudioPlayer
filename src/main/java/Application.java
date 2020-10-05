import model.AudioPlayer;
import model.Author;
import model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Genre.*;

/**
 * Starting point of the application.
 * Initializing the data.
 * Starting the audio player.
 * Executing the listed methods after exiting the audio player.
 */
public class Application {
    private static AudioPlayer audioPlayer;
    private static final String SINGER_NOT_FOUND_MESSAGE = "Singer not found!";
    private static final Map<String, List<Song>> authorRepository = new HashMap<>();
    private static final List<Song> songsToBePlayed = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(audioPlayer.getListOfCommands());

        String initialInput = audioPlayer.getUserInput();

        audioPlayer.start(initialInput);

        System.out.println(info());

        System.out.println("Input song title:");
        System.out.println(getSingerPositionInPlaylist(audioPlayer.getUserInput()));

        System.out.println("Please input full Author name:");

        List<Song> foundSongsOfAuthor = getSongsOfAuthor(audioPlayer.getUserInput());

        if (foundSongsOfAuthor != null && !foundSongsOfAuthor.isEmpty()) {
            foundSongsOfAuthor.forEach(s -> System.out.println(s.getShortInfo()));
        } else {
            System.out.println("Author not found.");
        }

        System.out.println("There are: " + getCountOfAllListedSongs() + " songs in the list.");

        System.out.println(removeSongFromPlayList(2));
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
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            koleKolev = new Author("Kole Kolev");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        Author nullNameAuthor = null;

        try {
            nullNameAuthor = new Author(null);
        } catch (NullPointerException nullPointerException) {
            System.out.println(nullPointerException.getMessage());
        }

        authorRepository.putIfAbsent(johnDou.getName(), new ArrayList<>());
        authorRepository.putIfAbsent(koleKolev.getName(), new ArrayList<>());

        Song getYourFreakOn = null;
        Song prituriSaPlaninata = null;
        Song bojeChuvajJaOdZlo = null;

        try {
            getYourFreakOn = new Song(johnDou, RAP, "Get your freak on", 20);
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            prituriSaPlaninata = new Song(koleKolev, COUNTRY, "Prituri sa planinata", 20);
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            bojeChuvajJaOdZlo = new Song(koleKolev, POP, "Boje chuvaj ja od zlo", 20);
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        Song invalidAuthor = null;
        Song invalidGenre = null;
        Song invalidTitle = null;
        Song invalidTiming = null;

        try {
            invalidAuthor = new Song(null, POP, "Merry christmas", 50);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidGenre = new Song(koleKolev, null, "Merry christmas", 50);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidTitle = new Song(koleKolev, POP, null, 50);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            invalidTiming = new Song(koleKolev, POP, "Merry christmas", 0);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        authorRepository.get(johnDou.getName()).add(getYourFreakOn);

        authorRepository.get(koleKolev.getName()).add(prituriSaPlaninata);
        authorRepository.get(koleKolev.getName()).add(bojeChuvajJaOdZlo);

        System.out.println(addSongToPlaylist(getYourFreakOn));
        System.out.println(addSongToPlaylist(prituriSaPlaninata));
        System.out.println(addSongToPlaylist(bojeChuvajJaOdZlo));

        audioPlayer = new AudioPlayer(songsToBePlayed);

    }

    /**
     * Gets the singer by song if found and gives the according response.
     *
     * @param songTitle String that occurs somewhere in the song's title.
     * @return String representation of the singer and position of the song or
     * if not found string that singer was not found.
     */
    private static String getSingerPositionInPlaylist(String songTitle) {
        String singerName;
        int songIndex;

        Song foundSong = audioPlayer.getPlayList().stream()
                .filter(s -> s.getTitle().contains(songTitle))
                .findFirst()
                .orElse(null);

        if (foundSong != null) {
            singerName = foundSong.getAuthor().getName();
            songIndex = audioPlayer.getPlayList().indexOf(foundSong);

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
        return audioPlayer.getPlayList().size();
    }

    /**
     * Removes from the playlist a song by its index.
     *
     * @param index Index of the song to remove.
     * @return String statement if song was removed or not.
     */
    private static String removeSongFromPlayList(int index) {
        if (index >= 0 && index < audioPlayer.getPlayList().size()) {
            audioPlayer.getPlayList().remove(index);
            return "Song removed.";
        }

        return "Provided index is not valid. Please add valid index.";
    }

    /**
     * @return String representation of the class fields as info.
     */
    private static String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
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
}
