package enumeration;

/**
 * stores values with private field name
 * to compliment the class Song
 */
public enum Genre {
    COUNTRY("country"),
    POP("pop"),
    RAP("rap"),
    DEFAULT("default");

    String name;

    Genre(String name){
        this.name = name;
    }
}
