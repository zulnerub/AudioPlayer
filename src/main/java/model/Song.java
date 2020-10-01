package model;

import enumeration.Genre;

import static enumeration.Genre.DEFAULT;

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

    public String getTitle(){
        return title;
    }

    public Singer getSinger(){
        return singer;
    }

    public String getShortInfo(){
        return "Song title: " + title +
                "\n\t  Song Author: " + author.getName();
    }

    @Override
    public String toString() {
        return "Song details:" +
                "\n\tTitle: " + title +
                "\n\tGenre: " + genre +
                "\n\tSinger: " + singer +
                "\n\tAuthor: " + author.getName() +
                "\n\tDuration: " + getDuration() + "\n";
    }

    private String getDuration() {
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
