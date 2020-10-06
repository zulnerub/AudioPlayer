package commands;

import model.AudioPlayer;

public class PreviousCommandImpl implements Command {

    /**
     * This method returns the player one index backwards and calls the play method again to start from
     * said index.
     * Simulating continuous execution of the playlist.
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        audioPlayer.resetPlaylist();
        audioPlayer.isPaused = true;
        audioPlayer.currentSongIndex = (audioPlayer.currentSongIndex - 1) < 0 ?
                audioPlayer.playList.size() - 1 : (audioPlayer.currentSongIndex - 1);
    }
}
