package eu.deltasource.commands;

import eu.deltasource.controllers.AudioPlayerController;

/**
 * Provides signature for the command classes.
 */
public interface Command {

    void action(AudioPlayerController audioPlayerController);

    boolean hasToStartAgain();
}
