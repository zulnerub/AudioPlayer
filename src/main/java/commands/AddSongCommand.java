package commands;

import controllers.AudioPlayerController;
import model.Author;
import model.CustomException;
import model.Genre;
import model.Song;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddSongCommand implements Command {
    private final List<Genre> genresToChoseFrom;
    private static final String INVALID_TIMING_MESSAGE = "Please enter a valid integer number.";
    private boolean isSongTitleValid = true;
    private boolean isAuthorNameValid = true;
    private boolean isSongGenreValid = true;
    private boolean isSongDurationValid = true;
    private int songDuration;
    private String songTitle;
    private String songGenre;
    private String authorName;


    public AddSongCommand() {
        genresToChoseFrom = Arrays.stream(Genre.values())
                .collect(Collectors.toList());
    }

    @Override
    public void action(AudioPlayerController audioPlayerController) {

        getValidSongTitle(audioPlayerController);

        getValidAuthorName(audioPlayerController);


        getValidSongGenre(audioPlayerController);

        getValidSongTiming(audioPlayerController);

        Author author = audioPlayerController.getPlaylist()
                .stream()
                .map(Song::getAuthor)
                .filter(songAuthor -> songAuthor.getName().equals(authorName))
                .findFirst().orElse(null);

        if (author == null){
            author = new Author(authorName);
        }

        Song song = new Song(author,
                genresToChoseFrom.get(genresToChoseFrom.lastIndexOf(songGenre)),
                songTitle,
                songDuration);

        audioPlayerController.addSongToPlaylist(song);

    }

    private void getValidSongTiming(AudioPlayerController audioPlayerController) {
        do {
            System.out.println("Please enter song duration:");

            try {
                songDuration = Integer.parseInt(audioPlayerController.getUserInput());
            } catch (NumberFormatException exception) {
                throw new CustomException(INVALID_TIMING_MESSAGE);
            } finally {
                isSongDurationValid = false;
            }

            if (songDuration <= 0) {
                isSongDurationValid = false;
                System.out.println("Please enter a positive integer number.");
            }
        } while (isSongDurationValid);
    }

    private void getValidSongGenre(AudioPlayerController audioPlayerController) {
        do {
            System.out.println("Please choose one of the following song genres:");
            genresToChoseFrom.forEach(genre -> System.out.println(genre.name()));

            songGenre = audioPlayerController.getUserInput();

            if (songGenre == null ||
                    songGenre.isBlank() ||
                    !genresToChoseFrom.contains(Genre.valueOf(songGenre))) {
                isSongGenreValid = false;
            }
        } while (!isSongGenreValid);
    }

    private void getValidAuthorName(AudioPlayerController audioPlayerController) {
        do {
            System.out.println("Please enter Author name:");
            authorName = audioPlayerController.getUserInput();

            if (authorName == null || authorName.isBlank()) {
                isAuthorNameValid = false;
            }
        } while (!isAuthorNameValid);
    }

    private void getValidSongTitle(AudioPlayerController audioPlayerController) {
        do {
            System.out.println("Please enter song title:");
            songTitle = audioPlayerController.getUserInput();

            if (songTitle == null || songTitle.isBlank()) {
                isSongTitleValid = false;
            }
        } while (!isSongTitleValid);
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
