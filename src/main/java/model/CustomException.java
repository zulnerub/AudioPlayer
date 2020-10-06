package model;

/**
 * Custom unchecked xception to handle incorrect input from the code.
 */
public class CustomException extends  RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
