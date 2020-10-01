import model.Author;
import model.Singer;
import model.Song;


import static enumeration.Genre.*;

public class Application {

    public static void main(String[] args) {
        Author johnDou = new Author("John  Dou");
        Author koleKolev = new Author("Kole Kolev");

        Singer missyEliot = new Singer("Missy Eliot");
        Singer valqBalkanska = new Singer("Valq Balkanska");
        Singer toseProevski = new Singer("Tose Proevski");

        Song getYourFreakOn = new Song(johnDou, RAP, missyEliot, "Get your freak on", 533);

        Song prituriSaPlaninata = new Song(koleKolev, COUNTRY, valqBalkanska, "Prituri sa planinata", 745);

        Song bojeChuvajJaOdZlo = new Song(koleKolev, POP, toseProevski, "Boje chuvaj ja od zlo", 451);

        Song unknown = new Song(null, null, null, "", 0);


    }
}
