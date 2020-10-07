import controllers.AudioPlayerController;
import model.*;

import static model.Genre.*;

/**
 * Starting point of the application.
 * Initializing the data.
 * Starting the audio player.
 * Executing the listed methods after exiting the audio player.
 */
public class Application {
    private static AudioPlayerController audioPlayerController;

    public static void main(String[] args) {
        init();

        audioPlayerController.start();
    }

    /**
     * This initializes the application:
     * - creates Objects - Authors, Singers, Songs;
     * - enlists songs in the playlist for the AudioPlayer to work with;
     */
    private static void init() {
        audioPlayerController = new AudioPlayerController();
        Author johnDou = null;
        Author koleKolev = null;

        try {
            johnDou = new Author("John Dou");
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

        System.out.println("Sample wrong input for validation demonstration purposes:\n");

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

        System.out.println("Adding songs to playlist for demonstration purposes:\n");

        System.out.println(audioPlayerController.addSongToPlaylist(getYourFreakOn));
        System.out.println(audioPlayerController.addSongToPlaylist(prituriSaPlaninata));
        System.out.println(audioPlayerController.addSongToPlaylist(bojeChuvajJaOdZlo));
        System.out.println();
    }
}


