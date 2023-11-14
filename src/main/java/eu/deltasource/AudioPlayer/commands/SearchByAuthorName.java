package eu.deltasource.AudioPlayer.commands;

import eu.deltasource.AudioPlayer.controllers.AudioPlayerController;
import eu.deltasource.AudioPlayer.model.Song;
import java.util.List;

/**
 * Provides functionality to search for author by authors name and prints the name and the songs of the author.
 */
public class SearchByAuthorName implements Command{

    /**
     * Gets the user input and searches for such author.
     * Prints the result of the operation.
     * @param audioPlayerController
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        System.out.println("Please input Author name:");
        List<Song> foundSongsOfAuthor = audioPlayerController.getSongsOfAuthor();

        if (foundSongsOfAuthor != null && !foundSongsOfAuthor.isEmpty()) {
            foundSongsOfAuthor.forEach(s -> System.out.println(s.getShortInfo() + "\n"));
        } else {
            System.out.println("Author not found.");
        }
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
