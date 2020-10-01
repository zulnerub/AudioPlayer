import model.Author;
import model.Singer;
import model.Song;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static enumeration.Genre.*;

public class AudioPlayer {
    private static List<Song> songs = new ArrayList<>();

    public static void main(String[] args) {
        init();

        Scanner scanner = new Scanner(System.in);

        System.out.println(getListOfCommands());

        String userInput = getUserInput(scanner);

        while (!userInput.equals("7")) {
            switch (userInput) {
                case "1":
                    playSongsInOrderOfAppearance();
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

        addSongToPlaylist(getYourFreakOn);
        addSongToPlaylist(prituriSaPlaninata);
        addSongToPlaylist(bojeChuvajJaOdZlo);
        addSongToPlaylist(unknown);

    }

    public static void playSongsInOrderOfAppearance(){


        songs.forEach(s -> {
            System.out.println("Now playing:\n\t* ");
            System.out.println(s.getShortInfo());

        });

    }

    public String getSingerBySongTitle(String songTitle) {
        String result = "Song not found!\n";
        String singerName;
        int songIndex;

        Song foundSong = songs.stream()
                .filter(s -> s.getTitle().equals(songTitle))
                .collect(Collectors.toList())
                .get(0);

        if (foundSong != null) {
            singerName = foundSong.getSinger().getName();
            songIndex = songs.indexOf(foundSong);
            result = "Singer name: " + singerName +
                    "\nPosition in playlist: " + songIndex + "\n";
        }
        return result;
    }


    public List<Song> getSongsOfSinger(Singer singer) {
        return songs.stream()
                .filter(s -> s.getSinger().equals(singer))
                .collect(Collectors.toList());
    }

    public int getCountOfAllListedSongs() {
        return songs.size();
    }

    public static void removeSongFromPlayList(Song song) {
        songs.remove(song);
    }

    public static void addSongToPlaylist(Song song) {
        if (song != null) {
            songs.add(song);
        } else {
            System.out.println("Please add a valid song.");
        }
    }

    public String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
