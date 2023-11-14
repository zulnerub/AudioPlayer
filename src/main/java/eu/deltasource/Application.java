package eu.deltasource;

import eu.deltasource.controllers.AudioPlayerController;
import eu.deltasource.model.*;

import static eu.deltasource.model.Genre.*;

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

        Author johnDou;
        Author koleKolev;

        johnDou = new Author("John Dou");

        koleKolev = new Author("Kole Kolev");

        Song getYourFreakOn;
        Song prituriSaPlaninata;
        Song bojeChuvajJaOdZlo;

        getYourFreakOn = new Song(johnDou, RAP, "Get your freak on", 20);

        prituriSaPlaninata = new Song(koleKolev, COUNTRY, "Prituri sa planinata", 20);

        bojeChuvajJaOdZlo = new Song(koleKolev, POP, "Boje chuvaj ja od zlo", 20);

        System.out.println("Sample wrong input for validation demonstration purposes:\n");

        try {
            Author nullNameAuthor = new Author(null);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Song invalidAuthor = new Song(null, POP, "Merry christmas", 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Song invalidGenre = new Song(koleKolev, null, "Merry christmas", 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Song invalidTitle = new Song(koleKolev, POP, null, 50);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            Song invalidTiming = new Song(koleKolev, POP, "Merry christmas", 0);
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println();
        System.out.println("Adding songs to playlist for demonstration purposes:\n");

        System.out.println(audioPlayerController.addSongToPlaylist(getYourFreakOn));
        System.out.println(audioPlayerController.addSongToPlaylist(prituriSaPlaninata));
        System.out.println(audioPlayerController.addSongToPlaylist(bojeChuvajJaOdZlo));
        System.out.println();
    }
}


