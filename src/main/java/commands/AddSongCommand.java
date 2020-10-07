package commands;

import controllers.AudioPlayerController;
import model.Author;
import model.CustomException;
import model.Genre;
import model.Song;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the AudioPlayerController with the functionality to add songs entered from the console to the playlist.
 */
public class AddSongCommand implements Command {
    private final List<Genre> genresToChoseFrom;
    private static final String INVALID_TIMING_MESSAGE = "Please enter a valid integer number.";
    private int songDuration;
    private String songTitle;
    private String songGenre;
    private String authorName;


    public AddSongCommand() {
        genresToChoseFrom = Arrays.stream(Genre.values())
                .collect(Collectors.toList());
    }

    /**
     * Gathers input from the user - song title, song author, song genre, song duration.
     * Creates the song and adds it to the playlist.
     */
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

        if (author == null) {
            author = new Author(authorName);
        }

        Song song = new Song(author,
                Genre.valueOf(songGenre.toUpperCase()),
                songTitle,
                songDuration);

        System.out.println(audioPlayerController.addSongToPlaylist(song));
    }

    /**
     * Gets input from the console validates it
     * and waits for input again if it is not valid.
     *
     * @param audioPlayerController Instance of the controller to get the user input.
     */
    private void getValidSongTiming(AudioPlayerController audioPlayerController) {
        boolean isSongDurationValid;
        do {
            System.out.println("Please enter song duration:");

            try {
                songDuration = Integer.parseInt(audioPlayerController.getUserInput());
            } catch (NumberFormatException exception) {
                throw new CustomException(INVALID_TIMING_MESSAGE);
            }

            isSongDurationValid = songDuration > 0;
        } while (!isSongDurationValid);
    }

    /**
     * Gets input from the console validates it
     * and waits for input again if it is not valid.
     *
     * @param audioPlayerController Instance of the controller to get the user input.
     */
    private void getValidSongGenre(AudioPlayerController audioPlayerController) {
        boolean isSongGenreValid;
        do {
            System.out.println("Please choose one of the following song genres:");
            genresToChoseFrom.forEach(genre -> System.out.println(genre.name()));

            songGenre = audioPlayerController.getUserInput();

            isSongGenreValid =
                    songGenre != null && !songGenre.isBlank() && genreExists();

        } while (!isSongGenreValid);
    }

    /**
     * @return Checks if the user input corresponds to any of the existing genres.
     */
    private boolean genreExists() {
        return genresToChoseFrom.stream()
                .map(Enum::name)
                .anyMatch(genreName -> genreName.equals(songGenre.toUpperCase()));
    }

    /**
     * Gets input from the console validates it
     * and waits for input again if it is not valid.
     *
     * @param audioPlayerController Instance of the controller to get the user input.
     */
    private void getValidAuthorName(AudioPlayerController audioPlayerController) {
        boolean isAuthorNameValid;
        do {
            System.out.println("Please enter Author name:");
            authorName = audioPlayerController.getUserInput();

            isAuthorNameValid = authorName != null && !authorName.isBlank();

        } while (!isAuthorNameValid);
    }

    /**
     * Gets input from the console validates it
     * and waits for input again if it is not valid.
     *
     * @param audioPlayerController Instance of the controller to get the user input.
     */
    private void getValidSongTitle(AudioPlayerController audioPlayerController) {
        boolean isSongTitleValid;
        do {
            System.out.println("Please enter song title:");
            songTitle = audioPlayerController.getUserInput();

            isSongTitleValid = songTitle != null && !songTitle.isBlank();

        } while (!isSongTitleValid);
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
