package eu.deltasource.AudioPlayer.commands;

import eu.deltasource.AudioPlayer.controllers.AudioPlayerController;

/**
 * Provides signature for the command classes.
 */
public interface Command {

    void action(AudioPlayerController audioPlayerController);

    boolean hasToStartAgain();
}
