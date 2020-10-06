package commands;

import model.AudioPlayer;

public class PauseCommandImpl implements Command {

    /**
     * Creates indication that the player has been paused so if play is chosen after this
     * it starts from the corresponding song and not from the beginning.
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        audioPlayer.isPaused = true;
    }
}
