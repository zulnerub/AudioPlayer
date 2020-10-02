import model.AudioPlayer;
import model.Author;
import model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static enumeration.Genre.*;

/**
 * Starting point of the application.
 * Initializing the data.
 * Starting the audio player.
 * Executing the listed methods after exiting the audio player.
 */
public class Application {
    private static final AudioPlayer audioPlayer = new AudioPlayer();
    private static final String SINGER_NOT_FOUND_MESSAGE = "Singer not found!";
    private static final Map<String, List<Song>> authorRepository = new HashMap<>();

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(audioPlayer.getListOfCommands());

        String initialInput = audioPlayer.getUserInput();

        audioPlayer.start(initialInput);

        System.out.println(info());

        System.out.println("Input song title:");
        System.out.println(getSingerPositionInPlaylist(audioPlayer.getUserInput()));

        System.out.println("Please input full Author name:");

        List<Song> result = getSongsOfAuthor(audioPlayer.getUserInput());

        if (result != null) {
            result.forEach(s -> System.out.println(s.getShortInfo()));
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
        Author johnDou = new Author("John  Dou");
        Author koleKolev = new Author("Kole Kolev");

        authorRepository.putIfAbsent(johnDou.getName(), new ArrayList<>());
        authorRepository.putIfAbsent(koleKolev.getName(), new ArrayList<>());


        Song getYourFreakOn = new Song(johnDou, RAP, "Get your freak on", 533);
        Song prituriSaPlaninata = new Song(koleKolev, COUNTRY, "Prituri sa planinata", 745);
        Song bojeChuvajJaOdZlo = new Song(koleKolev, POP, "Boje chuvaj ja od zlo", 451);
        Song unknown = new Song(null, null, "", 0);

        authorRepository.get(johnDou.getName()).add(getYourFreakOn);

        authorRepository.get(koleKolev.getName()).add(prituriSaPlaninata);
        authorRepository.get(koleKolev.getName()).add(bojeChuvajJaOdZlo);

        System.out.println(addSongToPlaylist(getYourFreakOn));
        System.out.println(addSongToPlaylist(prituriSaPlaninata));
        System.out.println(addSongToPlaylist(bojeChuvajJaOdZlo));
        System.out.println(addSongToPlaylist(unknown));
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
     */
    private static List<Song> getSongsOfAuthor(String authorName) {
        return authorRepository.get(authorName);
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
            audioPlayer.getPlayList().add(song);
            return "Song: " + song.getTitle() + " added to playlist.";
        } else {
            return "Song not added.";
        }
    }
}
