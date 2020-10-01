package model;

import java.util.ArrayList;
import java.util.List;

/**
 * creates an object that has collection of songs and name
 * used in the main application class
 */
public class Singer {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Singer(String name) {
        this.name = (name.isBlank() || name == null) ? "no name" : name;
    }

    /**
     * adds a song to the singers collection of songs
     * @param song - provided object of type Song
     */
    public void addSong(Song song){
        songs.add(song);
    }

    /**
     *
     * @return String - gets the name of the singer
     */
    public String getName(){
        return name;
    }
}
