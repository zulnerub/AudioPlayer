import model.Author;
import model.Singer;
import model.Song;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static enumeration.Genre.*;

/**
 * This class simulates the behavior/functionality of a real player.
 */
public class AudioPlayer {
    private static List<Song> songs = new ArrayList<>();
    private static Song currentSong = null;
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static String userInput;
    private static boolean receivedInputWhilePlaying = false;
    private static boolean isPaused = false;


    public static void main(String[] args) throws IOException {
        /*
            This method initializes the program - creates Songs, Authors, Singers and adds the Songs to the playlist
            of the AudioPlayer class.
         */
        init();

        /*
            Prints a list of commands available for the user
         */
        System.out.println(getListOfCommands());

        /*
            variable to store the user input using a method
         */
        userInput = getUserInput(bufferedReader);

        /*
            This loop is the place where the user chooses commands recursively while decides to exit the player
         */
        while (!userInput.equals("7")) {

            /*
                executes the method accordingly to the user input - compares a string of length one
                handles inputs not captured by the cases with a default
             */
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
            /*
                A check to see if user interrupted the AudioPlayer while playing
             */
            if (!receivedInputWhilePlaying) {
                System.out.println(getListOfCommands());
                userInput = getUserInput(bufferedReader);
            } else {
                receivedInputWhilePlaying = false;
            }

        }

        /*
            Printing the information for the audio player with short info
         */
        System.out.println(info());

        /*
            Instructing the user to input a string to search by that string for a song
            and prints information about singer by found song
         */
        System.out.println("Input song title:");
        System.out.println(getSingerBySongTitle(bufferedReader.readLine().trim()));

        /*
            Requests input from the user to search for singer
            to l ist all songs of said singer
         */
        System.out.println("Please input Singer name:");
        System.out.println(getSongsOfSinger(bufferedReader.readLine().trim()));

        /*
            Gets the number of songs currently in the AudioPlayer playlist.
         */
        System.out.println("There are: " + getCountOfAllListedSongs() + " songs in the list.");

        /*
            Removes an example song by id from the playlist
         */
        System.out.println(removeSongFromPlayList(2));

    }

    /**
     * This method takes the current  collection of songs(playlist) and rearanges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled
     *
     * @throws IOException - cascading exception from lower method in relation to BufferedReader
     */
    private static void shuffle() throws IOException {
        Collections.shuffle(songs);
        play(0);
    }

    /**
     * this method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist
     *
     * @throws IOException - cascading exception from lower method in relation to BufferedReader
     */
    public static void previous() throws IOException {
        int previousSongIndex = (songs.indexOf(currentSong) - 1) < 0 ?
                songs.size() - 1 : songs.indexOf(currentSong) - 1;
        play(previousSongIndex);
    }

    /**
     * makes the player to go one index forward in the playlist and validates the index.
     * If the index is bigger than the playlist size, starts the playing of the songs from the beginning
     * of the playlist
     * Simulating continuous execution of the playlist
     *
     * @throws IOException - cascading exception from lower method in relation to BufferedReader
     */
    public static void next() throws IOException {
        int nextSongIndex = (songs.indexOf(currentSong) + 1) > songs.size() ?
                1 : songs.indexOf(currentSong) + 1;
        play(nextSongIndex);
    }

    /**
     * nullifies the status of the player
     */
    public static void stop() {
        currentSong = null;
        isPaused = false;
    }

    /**
     * creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning
     */
    public static void pause() {
        isPaused = true;
    }

    /**
     * executes the main function of the player. Playss the songs and listnes for
     * user input to interrupt the play accordingly to the user likings.
     * As well as providing validation to the input to some extend and interrupting
     * only if the input is relevant
     *
     * !!! Included Thread.sleep() which may couse problems ....!!!
     *
     * @param index - the place in the playlist from witch to start the player
     * @throws IOException - cascading exception from lower method in relation to BufferedReader
     */
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

    /**
     * Provides string representation of the users options to the user
     *
     * @return - String
     */
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

    /**
     * handles provided input from the user
     *
     * @param bufferedReader - Console input
     * @return  String - the input of the user
     * @throws IOException - in relation to BufferedReader
     */
    private static String getUserInput(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.readLine().trim();
    }

    /**
     * This initializes the application
     *  - creates Objects - Authors, Singers, Songs
     *  - enlists songs in the playlist for the AudioPlayer to work with
     */
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

    /**
     * gets the singer by song if found
     * and gives the according response
     *
     * @param songTitle String - a string that occurs somewhere in the song's title
     * @return String - representation of the singer and position of the song or
     * if not found string that singer was not found
     */
    public static String getSingerBySongTitle(String songTitle) {
        String result = "Singer not found!\n";
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


    /**
     * gathering a list of songs that match the criteria
     *
     * @param singerName String - stirng that is checked if it is contained in any singer name
     * @return String - representation of the found songs of the singer
     */
    public static List<Song> getSongsOfSinger(String singerName) {
        return songs.stream()
                .filter(s -> s.getSinger().getName().contains(singerName))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return int - gets the current number of objects in the playlist
     */
    public static int getCountOfAllListedSongs() {
        return songs.size();
    }

    /**
     * removes from the playlist a song by its index
     *
     * @param index int - index of the song to remove
     * @return String - returns statement if song was removed or not
     */
    public static String removeSongFromPlayList(int index) {
        if (index >= 0 && index < songs.size()) {
            songs.remove(index);
            return "Song removed.";
        }

        return "Provided index is not valid. Please add valid index.";
    }

    /**
     * enlisting song to playlist
     * @param song Song - object of type song to be added to the current instance of the playlist
     */
    public static void addSongToPlaylist(Song song) {
        if (song != null) {
            songs.add(song);
        } else {
            System.out.println("Please add a valid song.");
        }
    }

    /**
     *
     * @return String - string representation of the class fields as info
     */
    public static String info() {
        return "AudioPlayer class characteristics:" +
                "\n\t*\tSongs - list of songs\n";
    }
}
