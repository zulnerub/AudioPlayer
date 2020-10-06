package commands;

import model.AudioPlayer;

import java.util.Collections;

public class ShuffleCommandImpl implements Command {

    /**
     * This method takes the current  collection of songs(playlist) and rearranges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled.
     */
    @Override
    public void action(AudioPlayer audioPlayer) {
        audioPlayer.resetPlaylist();
        Collections.shuffle(audioPlayer.playList);
    }
}
