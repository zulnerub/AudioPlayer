package model;

import java.util.ArrayList;
import java.util.List;

public class Singer {
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Singer(String name) {
        this.name = (name.isBlank() || name == null) ? "no name" : name;
    }

    public void addSong(Song song){
        songs.add(song);
    }

    public String getName(){
        return name;
    }
}
