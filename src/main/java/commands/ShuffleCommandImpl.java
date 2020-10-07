package commands;

import controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController with the functionality to start playing the songs in the playlist in random order.
 */
public class ShuffleCommandImpl implements Command {

    /**
     * This method takes the current  collection of songs(playlist) and rearranges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.resetPlaylist();
        audioPlayerController.isShufflePressed = !audioPlayerController.isShufflePressed;
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}
