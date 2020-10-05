package exceptions;

public class CustomExceptions {

    public static Exception invalidArgumentException(String message){
        throw new IllegalArgumentException(message);
    }

    public static Exception nullArgumentException(String message){
        throw new NullPointerException(message);
    }
}
