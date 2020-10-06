package commands;

import controllers.AudioPlayerController;

public class NextCommandImpl implements Command {

    /**
     * Makes the player to go one index forward in the playlist and validates the index.
     * If the index is bigger than the playlist size, starts the playing of the songs from the beginning
     * of the playlist.
     * Simulating continuous execution of the playlist.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.resetPlaylist();
        audioPlayerController.isPaused = true;
        audioPlayerController.currentSongIndex =
                (audioPlayerController.currentSongIndex + 1) >= audioPlayerController.getCountOfAllListedSongs()
                        ? 0 : (audioPlayerController.currentSongIndex + 1);
    }

    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}
