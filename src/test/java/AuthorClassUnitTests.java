import model.Author;
import model.CustomException;
import org.junit.Assert;
import org.junit.Test;

public class AuthorClassUnitTests {
    private static final String INCORRECT_NAME_PARAMETER = "Provided name for the author cannot be empty or null.";

    @Test
    public void shouldCreateAuthorWithName() {
        Author testAuthor = new Author("Simo");

        Assert.assertEquals("Simo", testAuthor.getName());
    }

    @Test(expected = CustomException.class)
    public void shouldThrowIllegalArgumentException_Input_NULL() {
        Author testAuthor = new Author(null);
    }

    @Test(expected = CustomException.class)
    public void shouldThrowIllegalArgumentException_Input_EmptyString() {
        Author testAuthor = new Author("");
    }
}
