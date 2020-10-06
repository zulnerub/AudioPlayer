package commands;

import controllers.AudioPlayerController;

public class PlayCommandImpl implements Command {
    private static final int INDEX_OF_FIRST_SONG = 0;

    /**
     * Calculates from which index to start playing the player.
     * Start playing the songs in the order they are in the playlist by calling the overloaded method with the current index.
     * Plays the songs in the list and prints the current song played.
     *
     * @param audioPlayerController - Current instance of the controller.
     * * !!! Included Thread.sleep() which may cause problems ....!!!
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        int startIndex = audioPlayerController.isPaused ?
                audioPlayerController.currentSongIndex : INDEX_OF_FIRST_SONG;


        boolean validPlaylist = audioPlayerController.getAudioPlayer().isPlaylistValid();
        if (!validPlaylist) {
            return;
        }

        for (; startIndex < audioPlayerController.getCountOfAllListedSongs(); startIndex++) {
            audioPlayerController.currentSongIndex = startIndex;

            System.out.print("Now playing: " + (audioPlayerController.currentSongIndex + 1) + "\n\t* ");
            System.out.println(
                    audioPlayerController
                            .getAudioPlayer()
                            .getPlayList()
                            .get(
                                    audioPlayerController.currentSongIndex
                            ).getShortInfo());

            audioPlayerController.songIsPlaying(
                    audioPlayerController
                            .getAudioPlayer()
                            .getPlayList()
                            .get(
                                    audioPlayerController.currentSongIndex
                            ).getTiming());

            if (audioPlayerController.hasNewInput) {
                break;
            }
        }
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
