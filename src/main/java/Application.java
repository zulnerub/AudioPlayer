import model.AudioPlayer;
import model.Author;
import model.Singer;
import model.Song;


import java.util.Scanner;

import static enumeration.Genre.*;

public class Application {
    private static AudioPlayer audioPlayer = new AudioPlayer();

    public static void main(String[] args) {
        init();

        Scanner scanner = new Scanner(System.in);

        System.out.println(getListOfCommands());

        String userInput = getUserInput(scanner);

        while (!userInput.equals("7")) {
            switch (userInput) {
                case "1":
                    System.out.println(audioPlayer.playSongsInOrderOfAppearance());
                    break;

                case "2":

                    break;

                case "3":

                    break;

                case "4":

                    break;

                case "5":

                    break;

                case "6":

                    break;

                default:
                    System.out.println("Please enter a valid command\n");
                    break;
            }
            System.out.println(getListOfCommands());
            userInput = getUserInput(scanner);
        }


    }

    private static String getListOfCommands() {
        return "Hello. Please chose a command from the following:\n" +
                "\t 1 \tPlay\n" +
                "\t 2 \tStop\n" +
                "\t 3 \tPause\n" +
                "\t 4 \tNext\n" +
                "\t 5 \tPrevious\n" +
                "\t 6 \tShuffle\n" +
                "\t 7 \tExit\n";
    }

    private static String getUserInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    private static void init() {
        Author johnDou = new Author("John  Dou");
        Author koleKolev = new Author("Kole Kolev");

        Singer missyEliot = new Singer("Missy Eliot");
        Singer valqBalkanska = new Singer("Valq Balkanska");
        Singer toseProevski = new Singer("Tose Proevski");

        Song getYourFreakOn = new Song(johnDou, RAP, missyEliot, "Get your freak on", 533);
        johnDou.addSong(getYourFreakOn);
        missyEliot.addSong(getYourFreakOn);

        Song prituriSaPlaninata = new Song(koleKolev, COUNTRY, valqBalkanska, "Prituri sa planinata", 745);
        koleKolev.addSong(prituriSaPlaninata);
        valqBalkanska.addSong(prituriSaPlaninata);

        Song bojeChuvajJaOdZlo = new Song(koleKolev, POP, toseProevski, "Boje chuvaj ja od zlo", 451);
        koleKolev.addSong(bojeChuvajJaOdZlo);
        toseProevski.addSong(bojeChuvajJaOdZlo);

        Song unknown = new Song(null, null, null, "", 0);

        audioPlayer.addSongToPlaylist(getYourFreakOn);
        audioPlayer.addSongToPlaylist(prituriSaPlaninata);
        audioPlayer.addSongToPlaylist(bojeChuvajJaOdZlo);
        audioPlayer.addSongToPlaylist(unknown);

    }
}
