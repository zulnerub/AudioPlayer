package enumeration;

public enum Genre {
    ROCK("rock"),
    POP("pop"),
    RAP("rap"),
    DEFAULT("default");

    String name;

    Genre(String name){
        this.name = name;
    }
}
