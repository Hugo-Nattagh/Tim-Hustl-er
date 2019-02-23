package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

public class Controller {

    public String pathSpec = "resources/Default Tone.mp3";
    public Integer state = 0;

    @FXML
    public TextField timing;
    public Label timeLabel;
    public Label audioName;
    public Button btn_play_main;

    public Timer timer = new Timer();
    public Media sound = new Media(new File(pathSpec).toURI().toString());
    public MediaPlayer mediaPlayer = new MediaPlayer(sound);


    public void bigButton (ActionEvent event) {
        if (mediaPlayer.getStatus().toString() == "PLAYING") {
            mediaPlayer.stop();
        }
        if (state == 0) {
            String timeContent = timing.getText();
            String[] parts = timeContent.split(":");
            Integer minutesS = Integer.parseInt(parts[0]) * 60;
            Integer secondes = Integer.parseInt(parts[1]) + minutesS +1;
            String toDisplay = timeToString(secondes);
            timeLabel.setText(toDisplay);
            launchTime();
        } else if (state == 1) {
            launchTime();
        } else if (state == 2) {
            pause();
        }
    }

    public void pause () {
        timer.cancel();
        timer.purge();
        timer = null;
        timer = new Timer();
        state = 1;
        btn_play_main.getStyleClass().clear();
        btn_play_main.getStyleClass().add("btn_main_play_style");
    }

    public void launchTime () {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        state = 2;
                        btn_play_main.getStyleClass().clear();
                        btn_play_main.getStyleClass().add("btn_main_pause_style");
                        String timeContent = timeLabel.getText();
                        String[] parts = timeContent.split(":");
                        Integer minutesS = Integer.parseInt(parts[0]) * 60;
                        Integer secondes = Integer.parseInt(parts[1]) + minutesS -1;
                        String toDisplay = timeToString(secondes);
                        timeLabel.setText(toDisplay);
                        if (secondes == 0) {
                            mediaPlayer.play();
                            timer.cancel();
                            timer.purge();
                            timer = null;
                            timer = new Timer();
                            state = 0;
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public String timeToString(Integer sec) {
        Integer min = Math.floorDiv(sec, 60);
        Integer secs = Math.floorMod(sec, 60);
        String minSt = new String();
        String secSt = new String();
        if (min <= 9) {
            minSt = "0".concat(min.toString());
        } else {
            minSt = min.toString();
        }
        if (secs <= 9) {
            secSt = "0".concat(secs.toString());
        } else {
            secSt = secs.toString();
        }
        String toDisplay = minSt.concat(":").concat(secSt);
        return toDisplay;
    }

    public void importAudio (ActionEvent event) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Mp3 File", "*.mp3");
        chooser.getExtensionFilters().add(filter);
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            Path content = Paths.get(selectedFile.getAbsolutePath());
            pathSpec = content.toString();
            String newTitleTemp[] = pathSpec.split("\\\\");
            String newTitle = newTitleTemp[newTitleTemp.length-1];
            audioName.setText(newTitle);
            sound = new Media(new File(pathSpec).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
        }
    }

    public void stop (ActionEvent event) {
        if (mediaPlayer.getStatus().toString() == "PLAYING") {
            mediaPlayer.stop();
        }
        pause();
        state = 0;
        String tempStr = timing.getText();
        timeLabel.setText(tempStr);
    }

}
