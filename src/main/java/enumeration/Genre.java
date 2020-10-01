package enumeration;

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
