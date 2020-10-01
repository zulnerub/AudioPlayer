package model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Author(String name) {
        this.name = (name.isBlank() || name == null) ? "no name" : name;
    }

    public void addSong(Song song){
        if (song != null) {
            songs.add(song);
        } else {
            System.out.println("Please choose a song.");
        }
    }

    public String getName(){
        return name;
    }

    public List<Song> getSongs(){
        return songs;
    }
}