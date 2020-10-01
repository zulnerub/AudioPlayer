package model;

import enumeration.Genre;

import static enumeration.Genre.DEFAULT;

public class Song {
    private String title;
    private Author author;
    private Genre genre;
    private int timing;

    public Song(String title, Author author, Genre genre, int timing) {
        this.title = (title.isBlank() || title == null) ? "Default name" : title;
        this.author = author != null ? author : new Author();
        this.genre = genre == null ? DEFAULT : genre;
        this.timing = timing < 1 ? 1 : timing;
    }


}
