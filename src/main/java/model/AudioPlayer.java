package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AudioPlayer {
    List<Song> songs = new ArrayList<>();

    public AudioPlayer() {
    }

    public String playSongsInOrderOfAppearance(){
        StringBuilder stringBuilder = new StringBuilder();

        songs.forEach(s -> {
            stringBuilder.append("Now playing:\n\t* ");
            stringBuilder.append(s.getShortInfo());
        });

        stringBuilder.append("\n");

        return stringBuilder.toString();
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

    public void removeSongFromPlayList(Song song) {
        songs.remove(song);
    }

    public void addSongToPlaylist(Song song) {
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
