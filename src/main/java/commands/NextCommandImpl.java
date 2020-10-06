package commands;

import model.AudioPlayer;

public class NextCommandImpl implements Command {

    /**
     * Makes the player to go one index forward in the playlist and validates the index.
     * If the index is bigger than the playlist size, starts the playing of the songs from the beginning
     * of the playlist.
     * Simulating continuous execution of the playlist.
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        audioPlayer.resetPlaylist();
        audioPlayer.isPaused = true;
        audioPlayer.currentSongIndex =
                (audioPlayer.currentSongIndex + 1) >= audioPlayer.playList.size()
                        ? 0 : (audioPlayer.currentSongIndex + 1);
    }
}
