package model;

import java.util.ArrayList;
import java.util.List;

/**
 * used to create object of type Author with methods to provide
 * the name of the author and to add a song to his collection of songs
 */
public class Author {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Author(String name) {
        this.name = (name.isBlank() || name == null) ? "no name" : name;
    }

    /**
     * takes an object of type Song and adds it to the author's list of songs
     * @param song Song - object of type Song
     */
    public void addSong(Song song){
        if (song != null) {
            songs.add(song);
        } else {
            System.out.println("Please choose a song.");
        }
    }

    /**
     *
     * @return String - gets the name of the author
     */
    public String getName(){
        return name;
    }
}
