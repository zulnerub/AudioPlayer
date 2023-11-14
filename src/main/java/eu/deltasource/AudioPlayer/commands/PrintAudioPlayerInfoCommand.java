package eu.deltasource.AudioPlayer.commands;

import eu.deltasource.AudioPlayer.controllers.AudioPlayerController;

/**
 * Provides functionality to print audio player info.
 */
public class PrintAudioPlayerInfoCommand implements Command{

    /**
     * Gets the short info about the audioplayer class and prints it in the console.
     * @param audioPlayerController - Instance of the AudioPlayerController to take input.
     */
    @Override
    public void action(AudioPlayerController audioPlayerController) {
        System.out.println(audioPlayerController.getAudioPlayerInfo());
    }

    @Override
    public boolean hasToStartAgain() {
        return false;
    }
}
