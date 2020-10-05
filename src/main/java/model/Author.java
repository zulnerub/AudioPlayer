package model;

/**
 * Used to instantiate an object that is used in the Song class.
 */
public class Author {
    private static final String DEFAULT_AUTHOR_NAME = "no name";
    private String name;

    public Author(String name) {
        this.name = getValidAuthorName(name);
    }

    /**
     * Validates the provided value for name.
     *
     * @param name String input value for field name.
     * @return Valid value of type String.
     */
    private String getValidAuthorName(String name) {
       return (name == null || name.isBlank()) ? DEFAULT_AUTHOR_NAME : name;
    }

    /**
     * @return String value of field name.
     */
    public String getName() {
        return name;
    }
}
