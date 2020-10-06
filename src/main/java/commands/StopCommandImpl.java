package commands;

import model.AudioPlayer;

public class StopCommandImpl implements Command {

    /**
     * Nullifies the status of the player.
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        audioPlayer.resetPlaylist();
        audioPlayer.isPaused = false;
        audioPlayer.hasNewInput = false;
    }
}
