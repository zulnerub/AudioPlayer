package eu.deltasource.commands;

import eu.deltasource.controllers.AudioPlayerController;

/**
 * Provides functionality to search by song title and prints the result.
 */
public class SearchBySongTitle implements Command{

    /**
     * Gets the user input and prints the result of the search by that input.
     * @param audioPlayerController - Needed to get the user input.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        System.out.println("Input song title:");
        System.out.println(audioPlayerController.getAuthorPositionInPlaylist());
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
