package eu.deltasource.AudioPlayer.model;

/**
 * Used to instantiate an object that is used in the Song class.
 */
public class Author {
    private static final String INCORRECT_NAME_PARAMETER = "Provided name for the author cannot be empty or null.";
    private String name;

    public Author(String name) {
        this.name = getValidAuthorName(name);
    }

    /**
     * Validates the provided value for name.
     *
     * @param name String input value for field name.
     * @return Valid value of type String.
     * @throws IllegalArgumentException if parameter is not present or blank.
     */
    private String getValidAuthorName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(INCORRECT_NAME_PARAMETER);
        }

        return name;
    }

    /**
     * @return String value of field name.
     */
    public String getName() {
        return name;
    }
}
