package commands;

import controllers.AudioPlayerController;

public class PauseCommandImpl implements Command {

    /**
     * Creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.isPaused = true;
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
