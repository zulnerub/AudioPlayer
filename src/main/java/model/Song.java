package model;

import enumeration.Genre;
import static exceptions.CustomExceptions.*;

/**
 * Stores details about a song.
 * Validation of fields made in the constructor.
 * Allowed default implementation if wrong input is provided.
 */
public class Song {
    private static final String INVALID_TITLE_NAME_MESSAGE = "The title cannot be empty.";
    private static final String INVALID_AUTHOR_MESSAGE = "Please provide a valid author for your song.";
    private static final String INVALID_GENRE_MESSAGE = "Such genre does not exist.";
    private static final String INVALID_TIMING_MESSAGE = "Please specify timing of the song greater than 0 seconds.";
    private Author author;
    private Genre genre;
    private String title;
    private int timing;

    private static final int LENGTH_OF_HOUR_IN_SECONDS = 3600;
    private static final int LENGTH_OF_MINUTE_IN_SECONDS = 60;

    public Song(Author author, Genre genre, String title, int timing) {
        this.author = getValidAuthorName(author);
        this.genre = getValidGenre(genre);
        this.title = getValidTitle(title);
        this.timing = getValidTiming(timing);
    }

    /**
     * Validates the provided int input.
     *
     * @param timing Parameter of type int.
     * @return Valid int parameter.
     * @throws IllegalArgumentException if the parameter is less than 1.
     */
    private int getValidTiming(int timing) {
        if (timing < 1) {
            invalidArgumentException(INVALID_TIMING_MESSAGE);
        }

        return timing;
    }

    /**
     * Validates the provided String value.
     *
     * @param title Parameter of type String.
     * @return Valid String value.
     * @throws IllegalArgumentException if the title is empty or not present.
     */
    private String getValidTitle(String title) {
        if (title == null || title.isBlank()){
            invalidArgumentException(INVALID_TITLE_NAME_MESSAGE);
        }

        return title;
    }

    /**
     * Validates the provided Genre.
     *
     * @param genre An instance of enum Genre.
     * @return Valid instance of Genre.
     * @throws NullPointerException if provided genre is not present.
     */
    private Genre getValidGenre(Genre genre) {
        if (genre == null){
            nullArgumentException(INVALID_GENRE_MESSAGE);
        }
        return genre;
    }

    /**
     * Validates the provided Author.
     *
     * @param author An instance of class Author.
     * @return Valid instance of Author.
     * @throws NullPointerException if provided author is null.
     */
    private Author getValidAuthorName(Author author) {
        if (author == null){
            nullArgumentException(INVALID_AUTHOR_MESSAGE);
        }
        return author;
    }

    /**
     * @return The author field of Song.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * @return The timing field of Song.
     */
    public int getTiming() {
        return timing;
    }

    /**
     * @return The title field of Song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Gets the songs title and author name and formats them.
     */
    public String getShortInfo() {
        return "Song title: " + title +
                "\n\t   Song Author: " + author.getName();
    }

    /**
     * Method is overridden to fit individual needs of the application.
     *
     * @return String - representation of info of the song, formatted.
     */
    @Override
    public String toString() {
        return "Song details:" +
                "\n\tTitle: " + title +
                "\n\tGenre: " + genre +
                "\n\tAuthor: " + author.getName() +
                "\n\tDuration: " + getDuration() + "\n";
    }

    /**
     * Uses the timing of the song to format and provide
     * a string representation of the duration of the song.
     *
     * @return String - calculates and provides the duration of the song.
     */
    public String getDuration() {
        String result = "";

        result += getHoursOfSong(timing);
        int remainingSeconds = timing % LENGTH_OF_HOUR_IN_SECONDS;

        result += getMinutesOfSong(remainingSeconds);

        result += getSecondsOfSong(remainingSeconds);

        return result;
    }

    /**
     * Calculate and format the seconds of a song.
     *
     * @param remainingSeconds The remainder of the song's duration
     *                         after calculating the hours and the minutes of the song.
     * @return Formatted String representation of the input pointing to the number of seconds in a song.
     */
    private String getSecondsOfSong(int remainingSeconds) {
        int seconds = remainingSeconds % LENGTH_OF_MINUTE_IN_SECONDS;

        if (seconds == 0) {
            return "00";
        }

        return seconds < 10 ? ("0" + seconds) : String.valueOf(seconds);
    }

    /**
     * Calculate and format the minutes in a song.
     *
     * @param remainingSeconds The remainder of the song's duration
     *                         after calculating the hours of the song.
     * @return Formatted String representation of the input pointing to the number of minutes in a song.
     */
    private String getMinutesOfSong(int remainingSeconds) {
        int minute = (remainingSeconds >= LENGTH_OF_MINUTE_IN_SECONDS) ?
                (remainingSeconds / LENGTH_OF_MINUTE_IN_SECONDS) : 0;
        if (minute == 0) {
            return "00:";
        }

        return minute < 10 ? ("0" + minute + ":") : (minute + ":");
    }

    /**
     * Calculate and format the hours in a song.
     *
     * @param timing The duration of the song in seconds.
     * @return Formatted String representation of the input pointing to the number of hours in a song.
     */
    private String getHoursOfSong(int timing) {
        int hours = timing >= LENGTH_OF_HOUR_IN_SECONDS ? timing / LENGTH_OF_HOUR_IN_SECONDS : 0;

        if (hours == 0) {
            return "00:";
        }

        return hours < 10 ? ("0" + hours + ":") : (hours + ":");
    }
}
