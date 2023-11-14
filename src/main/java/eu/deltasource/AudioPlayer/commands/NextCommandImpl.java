package eu.deltasource.AudioPlayer.commands;

import eu.deltasource.AudioPlayer.controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController with the functionality of switching to the next song in the audio player.
 */
public class NextCommandImpl implements Command {

    /**
     * Makes the player to go one index forward in the playlist and validates the index.
     * If the index is bigger than the playlist size, starts the playing of the songs from the beginning
     * of the playlist.
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
        return (audioPlayerController.currentSongIndex + 1) >= audioPlayerController.getCountOfAllListedSongs()
                ? 0
                : (audioPlayerController.currentSongIndex + 1);
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}
