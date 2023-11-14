package eu.deltasource.commands;

import eu.deltasource.controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController with the functionality to pause the audio player at current time of current song.
 */
public class PauseCommandImpl implements Command {

    /**
     * Creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.isPaused = true;
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
