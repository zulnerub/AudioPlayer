import commands.*;
import model.AudioPlayer;
import model.Song;
import model.UserCommands;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static model.UserCommands.*;

public class AudioPlayerController {
    private AudioPlayer audioPlayer;
    private Map<UserCommands, Command> availableCommands = new LinkedHashMap<>();

    public AudioPlayerController(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        availableCommands.put(PLAY, new PlayCommandImpl());
        availableCommands.put(STOP, new StopCommandImpl());
        availableCommands.put(PAUSE, new PauseCommandImpl());
        availableCommands.put(NEXT, new NextCommandImpl());
        availableCommands.put(PREVIOUS, new PreviousCommandImpl());
        availableCommands.put(SHUFFLE, new ShuffleCommandImpl());
    }

    public void execute(UserCommands command){
        availableCommands.get(command).action(audioPlayer);

        if (command.equals(SHUFFLE) || command.equals(PREVIOUS) || command.equals(NEXT)){
            availableCommands.get(PLAY).action(audioPlayer);
        }

        if (audioPlayer.hasNewInput) {
            Application.setHasNewInput(true);
            Application.setUserInput(audioPlayer.userInput);
        }

    }

    public List<Song> getPlaylist(){
        return audioPlayer.getPlayList();
    }


}
