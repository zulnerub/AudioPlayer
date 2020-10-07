package commands;

import controllers.AudioPlayerController;

/**
 * Provides the AudioPlayerController the functionality to remove a current song from the play list.
 */
public class RemoveSongCommand implements Command {

    /**
     * @param audioPlayerController The controller which method to remove a song is called.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        System.out.println(audioPlayerController.removeSongFromPlaylist());
    }

    /**
     * @return Whether the method has to execute the play command.
     */
    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
