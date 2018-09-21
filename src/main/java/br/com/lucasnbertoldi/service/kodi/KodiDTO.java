package br.com.lucasnbertoldi.service.kodi;

public class KodiDTO {

    protected int volume;
    protected boolean muted;
    protected Integer playerID;
    protected String playerType;
    protected int windowID;
    protected int speed;

    @Override
    public String toString() {
        return "KodiDTO{" + "muted=" + muted + ", playerID=" + playerID + ", playerType=" + playerType + ", windowID=" + windowID + ", speed=" + speed + '}';
    }
    
}
