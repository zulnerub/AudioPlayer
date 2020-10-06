package commands;

import controllers.AudioPlayerController;

import java.util.Collections;

public class ShuffleCommandImpl implements Command {

    /**
     * This method takes the current  collection of songs(playlist) and rearranges it randomly.
     * Then with the newly ordered list calls the play method to play songs shuffled.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        audioPlayerController.resetPlaylist();
        Collections.shuffle(audioPlayerController.getPlaylist());
    }

    @Override
    public boolean hasToStartAgain() {
        return true;
    }
}
