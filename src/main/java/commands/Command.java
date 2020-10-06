package commands;

import model.AudioPlayer;

public interface Command {

    void action(AudioPlayer audioPlayer);
}
