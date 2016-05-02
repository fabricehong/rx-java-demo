package rx.liip.ch.rxdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.FuncN;

public class Model {
    private final Server server;

    public Model(Server server) {
        this.server = server;
    }

    public Observable<?> getUser() {
        return server.getUser()
                .doOnNext(new Action1<Updatable>() {
                    @Override
                    public void call(Updatable updatable) {
                        updatable.complete();
                    }
                });
    }

    public Observable<List<Updatable>> getFriendsProfiles() {
        List<Observable<Updatable>> addressesCalls = server.getFriends();

        return Observable.combineLatest(addressesCalls, new FuncN<List<Updatable>>() {
            @Override
            public List<Updatable> call(final Object... args) {
                return new ArrayList<Updatable>(){{
                    for (Object o : args) {
                        Updatable o1 = (Updatable) o;
                        o1.complete();
                        add(o1);
                    }
                }};
            }
        });
    }

}
