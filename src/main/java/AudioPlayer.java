import model.Author;
import model.Singer;
import model.Song;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static enumeration.Genre.*;

public class AudioPlayer {
    private static List<Song> songs = new ArrayList<>();
    private static Song currentSong = null;
    private static BufferedReader bufferedReader;
    private static String userInput;
    private static boolean receivedInputWhilePlaying = false;
    private static boolean isPaused = false;

    public static void main(String[] args) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        init();

        System.out.println(getListOfCommands());

        userInput = getUserInput(bufferedReader);

        while (!userInput.equals("7")) {
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
                    System.out.println("Please enter a valid command\n");
                    break;
            }
            if (!receivedInputWhilePlaying) {
                System.out.println(getListOfCommands());
                userInput = getUserInput(bufferedReader);
            } else {
                receivedInputWhilePlaying = false;
            }

        }

        System.out.println(info());

        System.out.println("Input song title:");
        getSingerBySongTitle(bufferedReader.readLine().trim());

        System.out.println("Please input Singer name:");
        getSongsOfSinger(bufferedReader.readLine().trim());

        System.out.println("There are: " + getCountOfAllListedSongs() + " songs in the list.");

    }

    private static void shuffle() throws IOException {
        Collections.shuffle(songs);
        play(0);
    }

    public static void previous() throws IOException {
        int previousSongIndex = (songs.indexOf(currentSong) - 1) < 0 ?
                songs.size() - 1 : songs.indexOf(currentSong) - 1;
        play(previousSongIndex);
    }

    public static void next() throws IOException {
        int nextSongIndex = (songs.indexOf(currentSong) + 1) > songs.size() ?
                1 : songs.indexOf(currentSong) + 1;
        play(nextSongIndex);
    }

    public static void stop() {
        currentSong = null;
        isPaused = false;
    }

    public static void pause() {
        isPaused = true;
    }

    public static void play(int index) throws IOException {
        int startIndex = isPaused ? songs.indexOf(currentSong) : index;
        isPaused = false;

        for (; startIndex < songs.size(); startIndex++) {
            currentSong = songs.get(startIndex);

            System.out.print("Now playing: " + (songs.indexOf(currentSong) + 1) + "\n\t* ");
            System.out.println(currentSong.getShortInfo());

            try {
                Thread.sleep(currentSong.getTiming() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (bufferedReader.ready()) {
                int input = Integer.parseInt(getUserInput(bufferedReader));

                if (input >= 1 && input < 7) {
                    receivedInputWhilePlaying = true;
                    userInput = "" + input;
                    break;
                }
            }
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

    private static String getUserInput(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.readLine().trim();
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

    public static String getSingerBySongTitle(String songTitle) {
        String result = "Song not found!\n";
        String singerName;
        int songIndex;

        Song foundSong = songs.stream()
                .filter(s -> s.getTitle().contains(songTitle))
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


    public static List<Song> getSongsOfSinger(String singerName) {
        return songs.stream()
                .filter(s -> s.getSinger().getName().contains(singerName))
                .collect(Collectors.toList());
    }

    public static int getCountOfAllListedSongs() {
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

    public static String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
