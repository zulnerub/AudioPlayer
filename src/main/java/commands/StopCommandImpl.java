package commands;

import controllers.AudioPlayerController;

/**
 * Command that operates
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

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
