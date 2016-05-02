package rx.liip.ch.rxdemo;

import java.util.List;

import rx.Observable;

public interface Server {
    Observable<Updatable> getUser();
    List<Observable<Updatable>> getFriends();
}
