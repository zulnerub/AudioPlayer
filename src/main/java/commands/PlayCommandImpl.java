package commands;

import model.AudioPlayer;

public class PlayCommandImpl implements Command {

    /**
     * Calculates from which index to start playing the player.
     * * Start playing the songs in the order they are in the playlist by calling the overloaded method with the current index.
     * * !!! Included Thread.sleep() which may cause problems ....!!!
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        int startIndex = audioPlayer.isPaused ?
                audioPlayer.currentSongIndex : audioPlayer.INDEX_OF_FIRST_SONG;

        play(startIndex, audioPlayer);
    }

    /**
     * Plays the songs in the list and prints the current song played.
     *
     * @param startIndex - The place in the playlist from witch to start the player.
     */
    private void play(int startIndex, AudioPlayer audioPlayer) {
        boolean validPlaylist = audioPlayer.isPlaylistValid();
        if (!validPlaylist) {
            return;
        }

        for (; startIndex < audioPlayer.playList.size(); startIndex++) {
            audioPlayer.currentSongIndex = startIndex;

            System.out.print("Now playing: " + (audioPlayer.currentSongIndex + 1) + "\n\t* ");
            System.out.println(audioPlayer.playList.get(audioPlayer.currentSongIndex).getShortInfo());

            audioPlayer.songIsPlaying(audioPlayer.playList.get(audioPlayer.currentSongIndex).getTiming());

            if (audioPlayer.hasNewInput) {
                break;
            }
        }
    }
}
