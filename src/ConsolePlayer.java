import builders.MediaBuilder;
import builders.StdMediaBuilder;
import exceptions.MediaTimerException;
import file.MediaLoader;
import media.*;
import player.PlayerModel;
import player.StdPlayerModel;
import timer.MediaTimer;
import timer.StdMediaTimer;
import timer.TimerState;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsolePlayer {

    private PlayerModel model;
    private MediaTimer timer;

    private boolean debugMod;
    private BufferedReader consoleReader;

    public ConsolePlayer(PlayerModel model, boolean debugMod){
        this.debugMod = debugMod;
        this.timer = new StdMediaTimer();
        this.model = model;

        ChangeListener modelListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                try {
                    if (!model.isFinished()){
                        if (!debugMod){
                            Media media = MediaLoader.loadCompleteMediaFromMediaFile(model.getCurrentMedia().getName());
                            model.setCurrentMediaInfo(media);

                            System.out.print("\n" + media.toString());
                        } else {
                            System.out.println(model.getCurrentMediaInfo().toString());
                        }

                        if (timer.getState() != TimerState.NOT_STARTED) timer.restart();
                    } else {
                        timer.stop();
                        System.out.println("\nThe playlist has ended");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        ChangeListener timerListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                try {
                    System.out.print('\r' + model.getCurrentMediaInfo().toString() + " " +
                            timer.getTime() + "/" +
                            model.getCurrentMediaInfo().getDuration() + " sec");

                    if (!debugMod && timer.getTime() == model.getCurrentMediaInfo().getDuration()){
                        model.goToNextMedia(model.getCurrentMedia());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        this.model.addChangeListener(modelListener);
        timer.addChangeListener(timerListener);

        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException, MediaTimerException {
        String command = "";

        try {
            model.startPlaylist();
            timer.start();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        while (!command.equals("stop") && !model.isFinished()){
            command = consoleReader.readLine();

            switch (command) {
                case "pause":
                    timer.pause();
                    break;
                case "resume":
                    timer.resume();
                    break;
                case "next":
                    model.goToNextMedia(model.getCurrentMedia());
                    break;
                case "previous":
                    model.goToPreviousMedia(model.getCurrentMedia());
                    break;
                default:
                    break;
            }
        }

        /*Thread thread = new Thread() {
            public void run(){
                try {
                    String command = "";

                    while (!command.equals("stop")){
                        command = consoleReader.readLine();

                        switch (command) {
                            case "pause":
                                timer.pause();
                                break;
                            case "resume":
                                timer.resume();
                                break;
                            case "next":
                                model.goToNextMedia(model.getCurrentMedia());
                                break;
                            case "previous":
                                model.goToPreviousMedia(model.getCurrentMedia());
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        thread.start();*/
    }

    public PlayerModel getModel(){
        return model;
    }

    public MediaTimer getTimer(){
        return timer;
    }

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Error : you have to give a xpl list file");
            return;
        }

        try {
            MediaBuilder builder = new StdMediaBuilder();
            ListMedia list = MediaLoader.loadListFromXPL(args[0], builder);

            PlayerModel model = new StdPlayerModel(list);
            ConsolePlayer player = new ConsolePlayer(model, false);
            player.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
