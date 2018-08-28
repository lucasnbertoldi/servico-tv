package br.com.lucasnbertoldi.service.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationDTO {

    private List<String> up;
    private List<String> down;
    private List<String> left;
    private List<String> right;
    private List<String> ok;
    private List<String> back;
    private List<String> volumeUp;
    private List<String> volumeDown;
    private List<String> subtitleScreen;
    private List<String> playPause;
    private List<String> stop;
    private List<String> settings;
    private List<String> disable;

    public String user;
    public String password;
    public String urlKODI;
    public String sistema;
    public boolean showScreen;
    public boolean showControllerLOG;
    public int delayMouse;
    public int delayNumber;
    public int delayOpenKodi;

    public ConfigurationDTO() {
        this.up = new ArrayList<>();
        this.down = new ArrayList<>();
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.ok = new ArrayList<>();
        this.back = new ArrayList<>();
        this.volumeDown = new ArrayList<>();
        this.volumeUp = new ArrayList<>();
        this.subtitleScreen = new ArrayList<>();
        this.playPause = new ArrayList<>();
        this.stop = new ArrayList<>();
        this.settings = new ArrayList<>();
        this.disable = new ArrayList<>();
        this.urlKODI = "";
        this.user = "";
        this.password = "";
        this.sistema = "Windows";
        this.showScreen = true;
        this.showControllerLOG = true;
        this.delayMouse = 400;
        this.delayNumber = 1000;
        this.delayOpenKodi = 2000;
    }

    private List<String> convertStringToArray(String text) {
        return Arrays.asList(text.split(","));
    }

    private String convertArrayToString(List<String> array) {
        StringBuilder string = new StringBuilder("");
        for (String s : array) {
            string.append(s).append(",");
        }
        try {
            string = new StringBuilder(string.substring(0, string.length() - 1));
        } catch (Exception e) {
        }
        return string.toString();
    }

    public void setUp(String up) {
        this.up = convertStringToArray(up);
    }

    public void setDown(String down) {
        this.down = convertStringToArray(down);
    }

    public void setLeft(String left) {
        this.left = convertStringToArray(left);
    }

    public void setRight(String right) {
        this.right = convertStringToArray(right);
    }

    public void setOk(String ok) {
        this.ok = convertStringToArray(ok);
    }

    public void setBack(String back) {
        this.back = convertStringToArray(back);
    }

    public void setVolumeUp(String volumeUp) {
        this.volumeUp = convertStringToArray(volumeUp);
    }

    public void setVolumeDown(String volumeDown) {
        this.volumeDown = convertStringToArray(volumeDown);
    }

    public void setSubtitleScreen(String subtitleScreen) {
        this.subtitleScreen = convertStringToArray(subtitleScreen);
    }

    public void setPlayPause(String playPause) {
        this.playPause = convertStringToArray(playPause);
    }

    public void setStop(String stop) {
        this.stop = convertStringToArray(stop);
    }

    public void setSettings(String settings) {
        this.settings = convertStringToArray(settings);
    }

    public void setDisable(String disable) {
        this.disable = convertStringToArray(disable);
    }

    public String getTextUp() {
        return convertArrayToString(up);
    }

    public String getTextDown() {
        return convertArrayToString(down);
    }

    public String getTextLeft() {
        return convertArrayToString(left);
    }

    public String getTextRight() {
        return convertArrayToString(right);
    }

    public String getTextOk() {
        return convertArrayToString(ok);
    }

    public String getTextBack() {
        return convertArrayToString(back);
    }

    public String getTextVolumeUp() {
        return convertArrayToString(volumeUp);
    }

    public String getTextVolumeDown() {
        return convertArrayToString(volumeDown);
    }

    public String getTextSubtitleScreen() {
        return convertArrayToString(subtitleScreen);
    }

    public String getTextPlayPause() {
        return convertArrayToString(playPause);
    }

    public String getTextStop() {
        return convertArrayToString(stop);
    }

    public String getTextSettings() {
        return convertArrayToString(settings);
    }

    public String getTextDisable() {
        return convertArrayToString(disable);
    }

    public List<String> getUp() {
        return up;
    }

    public List<String> getDown() {
        return down;
    }

    public List<String> getLeft() {
        return left;
    }

    public List<String> getRight() {
        return right;
    }

    public List<String> getOk() {
        return ok;
    }

    public List<String> getVolumeUp() {
        return volumeUp;
    }

    public List<String> getVolumeDown() {
        return volumeDown;
    }

    public List<String> getBack() {
        return back;
    }

    public List<String> getSubtitleScreen() {
        return subtitleScreen;
    }

    public List<String> getPlayPause() {
        return playPause;
    }

    public List<String> getStop() {
        return stop;
    }

    public List<String> getSettings() {
        return settings;
    }

    public List<String> getDisable() {
        return disable;
    }

}
