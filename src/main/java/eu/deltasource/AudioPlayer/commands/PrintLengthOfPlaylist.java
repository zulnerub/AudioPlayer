package eu.deltasource.AudioPlayer.commands;

import eu.deltasource.AudioPlayer.controllers.AudioPlayerController;

/**
 * Provides functionality to print the length of the playlist on the console.
 */
public class PrintLengthOfPlaylist implements Command{

    /**
     * Gets the number of songs in the playlist and prints them on the console.
     * @param audioPlayerController - Needed to get user input.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        System.out.println("There are: " + audioPlayerController.getCountOfAllListedSongs() + " songs in the list.");
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
