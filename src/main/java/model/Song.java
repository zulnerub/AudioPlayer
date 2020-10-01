package model;

import enumeration.Genre;

import static enumeration.Genre.DEFAULT;

/**
 * used to create objects of type song to use in the AudioPlayer
 * contains overridden method toString and other supplementary methods for accessing fields
 *
 *
 * validation of fields made in the constructor
 * allowed for default implementation if wrong input is provided
 */
public class Song {
    private Author author;
    private Genre genre;
    private Singer singer;
    private String title;
    private int timing;

    public Song(Author author, Genre genre, Singer singer, String title, int timing) {
        this.author = author != null ? author : new Author("no author");
        this.genre = genre == null ? DEFAULT : genre;
        this.singer = singer != null ? singer : new Singer("no singer");
        this.title = (title.isBlank() || title == null) ? "no name" : title;
        this.timing = timing < 1 ? 1 : timing;
    }

    /**
     *
     * @return int - gets the timing of the song
     */
    public int getTiming(){
        return timing;
    }

    /**
     *
     * @return String - gets the title of the song
     */
    public String getTitle(){
        return title;
    }

    /**
     *
     * @return Singer - gets the singer of the song
     */
    public Singer getSinger(){
        return singer;
    }

    /**
     *
     * @return String - gets the songs title and author name and formats them
     */
    public String getShortInfo(){
        return "Song title: " + title +
                "\n\t   Song Author: " + author.getName();
    }

    /**
     * method is overridden to fit individual needs of the application
     *
     * @return String - representation of info of the song, formatted
     */
    @Override
    public String toString() {
        return "Song details:" +
                "\n\tTitle: " + title +
                "\n\tGenre: " + genre +
                "\n\tSinger: " + singer +
                "\n\tAuthor: " + author.getName() +
                "\n\tDuration: " + getDuration() + "\n";
    }

    /**
     * uses the timing of the song to format and provide a string representation of the duration of the song
     *
     * @return String - calculates and provides the duration of the song
     */
    public String getDuration() {
        String result = "";

        if (timing >= 3600){
            result += timing / 3600;
            result += ":";
        }

        if (timing >= 60 && timing < 3600){
            result += "00:";
            result += (timing % 3600) / 60;
            result += ":";
        }

        if (timing < 60){
            result += "00:";
        }

        result += timing % 60;

        return result;
    }
}
