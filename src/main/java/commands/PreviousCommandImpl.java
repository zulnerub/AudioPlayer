package commands;

import controllers.AudioPlayerController;

public class PreviousCommandImpl implements Command {

    /**
     * This method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.resetPlaylist();
        audioPlayerController.isPaused = true;
        audioPlayerController.currentSongIndex = (audioPlayerController.currentSongIndex - 1) < 0 ?
                audioPlayerController.getCountOfAllListedSongs() - 1 : (audioPlayerController.currentSongIndex - 1);
    }

    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}
