import model.Author;
import model.Song;
import org.junit.Assert;
import org.junit.Test;

import static enumeration.Genre.*;

public class SongClassUnitTests {
    private static final String INVALID_TITLE_NAME_MESSAGE = "The title cannot be empty.";
    private static final String INVALID_AUTHOR_MESSAGE = "Please provide a valid author for your song.";
    private static final String INVALID_GENRE_MESSAGE = "Such genre does not exist.";
    private static final String INVALID_TIMING_MESSAGE = "Please specify timing of the song greater than 0 seconds.";

    @Test
    public void shouldGetCorrectTitle() {
        Song testSong = new Song(new Author("Valid Author"), POP, "Title test song", 1);

        Assert.assertEquals("Title test song", testSong.getTitle());
    }

    @Test
    public void shouldGetCorrectTiming() {
        Song testSong = new Song(new Author("Valid Author"), POP, "Title test song", 1);

        Assert.assertEquals(1, testSong.getTiming());
    }

    @Test
    public void shouldGetCorrectAuthor() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, "Title test song", 1);

        Assert.assertEquals(testAuthor, testSong.getAuthor());
    }

    @Test
    public void shouldReturnSongDurationFormatted() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, "Title test song", 3666);

        Assert.assertEquals("01:01:06", testSong.getDuration());
    }

    @Test
    public void shouldReturnShortInfoOfSongFormatted() {
        // Given
        Author testAuthor = new Author("Valid Author");
        // When
        Song testSong = new Song(testAuthor, POP, "Title test song", 3666);
        // Then
        Assert.assertEquals("Song title: Title test song\n\t   Song Author: Valid Author", testSong.getShortInfo());
    }

    @Test
    public void shouldReturnToStringMethodAsOverridden() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, "Title test song", 3666);

        Assert.assertEquals("Song details:" +
                "\n\tTitle: Title test song" +
                "\n\tGenre: POP" +
                "\n\tAuthor: Valid Author" +
                "\n\tDuration: 01:01:06\n", testSong.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForTitleInput_NULL() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, null, 3666);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForTitleInput_EMPTY() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, "", 3666);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForTimingInput_EMPTY() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, POP, "Valid title", 0);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionForGenreInput_NULL() {
        Author testAuthor = new Author("Valid Author");
        Song testSong = new Song(testAuthor, null, "Valid title", 3666);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionForAuthorInput_NULL() {
        Song testSong = new Song(null, POP, "Valid title", 3666);
    }


}
