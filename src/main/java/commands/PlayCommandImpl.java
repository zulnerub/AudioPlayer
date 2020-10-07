package commands;

import controllers.AudioPlayerController;
import model.Song;

import java.util.List;
import java.util.Random;

/**
 * Provides the AudioPlayerController with the functionality to play the songs in the playlist of the audio player.
 */
public class PlayCommandImpl implements Command {
    private static final int INDEX_OF_FIRST_SONG = 0;

    /**
     * Calculates from which index to start playing the player.
     * Start playing the songs in the order they are in the playlist by calling the overloaded method with the current index.
     * Plays the songs in the list and prints the current song played.
     *
     * @param audioPlayerController - Current instance of the controller.
     *                              * !!! Included Thread.sleep() which may cause problems ....!!!
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        int startIndex = getStartIndex(audioPlayerController);

        boolean validPlaylist = audioPlayerController.validatePlaylist();

        if (!validPlaylist) {
            return;
        }

        if (audioPlayerController.isShufflePressed) {
            startIndex = getRandomSongIndex(audioPlayerController.getPlaylist());
        }

        for (; startIndex < audioPlayerController.getCountOfAllListedSongs(); startIndex++) {

            audioPlayerController.currentSongIndex = startIndex;

            Song currentSong = audioPlayerController.getCurrentSong();

            System.out.print("Now playing: " + (audioPlayerController.currentSongIndex + 1) + "\n\t* ");

            System.out.println(currentSong.getShortInfo());

            audioPlayerController.playSong(currentSong.getTiming());

            if (audioPlayerController.hasNewInput) {
                break;
            }

            if (startIndex == (audioPlayerController.getCountOfAllListedSongs() - 1)){
                startIndex = -1;
            }
        }
    }

    /**
     * Provides a check if the audio player has been paused and on that returns the index of the element
     * at which the pause was called
     * or the index of the first element of the playlist.
     *
     * @param audioPlayerController Instance of the AudioPlayerController
     * @return An integer which is the index from which to start playing the songs.
     */
    private int getStartIndex(AudioPlayerController audioPlayerController) {
        return audioPlayerController.isPaused || audioPlayerController.hasToSwitchSong
                ? audioPlayerController.currentSongIndex
                : INDEX_OF_FIRST_SONG;
    }

    /**
     * Generate a random int value between 0 and the length of the playlist (exclusively).
     *
     * @param playlist Used to get the upper bond for the random generator (the size of the collection).
     * @return A randomly generated int index in the range of the playlist collection.
     */
    private int getRandomSongIndex(List<Song> playlist) {
        return new Random().nextInt(playlist.size());
    }

    /**
     * @return Whether the controller has to execute the play command in the current iteration or not.
     */
    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
