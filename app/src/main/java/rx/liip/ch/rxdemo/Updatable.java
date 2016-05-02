package rx.liip.ch.rxdemo;

public interface Updatable {
    void complete();
    void start();
    void error();
    void setLabel(String label);
    String getLabel();
}
