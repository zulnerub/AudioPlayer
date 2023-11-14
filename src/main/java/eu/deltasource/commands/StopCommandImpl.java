package eu.deltasource.commands;

import eu.deltasource.controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController with the functionality
 * to stop the playing of the songs in the playlist of the audio player.
 */
public class StopCommandImpl implements Command {

    /**
     * Nullifies the status of the player.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.resetPlaylist();
        audioPlayerController.isPaused = false;
        audioPlayerController.hasNewInput = false;
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
