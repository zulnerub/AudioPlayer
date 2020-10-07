package commands;

import controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController with the functionality to switch to the previous song in the playlist.
 */
public class PreviousCommandImpl implements Command {

    /**
     * This method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.timeTheSongWasPaused = 1;
        audioPlayerController.hasToSwitchSong = true;
        audioPlayerController.currentSongIndex = getCurrentSongIndex(audioPlayerController);
    }

    /**
     * Calculates the index of the current song that is played.
     *
     * @param audioPlayerController The controller that provides access to the current song.
     * @return The index of the current song based on the current state of the audio player.
     */
    private int getCurrentSongIndex(AudioPlayerController audioPlayerController) {
        return (audioPlayerController.currentSongIndex - 1) < 0
                ? audioPlayerController.getCountOfAllListedSongs() - 1
                : (audioPlayerController.currentSongIndex - 1);
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}