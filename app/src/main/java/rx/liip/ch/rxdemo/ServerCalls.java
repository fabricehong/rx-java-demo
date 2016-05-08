package rx.liip.ch.rxdemo;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.liip.ch.rxdemo.utils.CollectionsUtils;
import rx.liip.ch.rxdemo.utils.Function;

public class ServerCalls {
    static Random rand = new Random();

    public static Observable<Updatable> createSimulatedServerCall(final Updatable state) {
        return createSimulatedServerCall(state, null);
    }

    public static Observable<Updatable> createSimulatedServerCall(final Updatable state, final String label) {
        state.start();
        return createSimulatedServerCall().flatMap(new Func1<Long, Observable<Updatable>>() {
            @Override
            public Observable<Updatable> call(Long aLong) {
                state.setLabel(label);
                return Observable.just(state);
            }
        });
    }

    @NonNull
    private static Observable<Long> createSimulatedServerCall() {
        return Observable.timer((rand.nextLong() % 2000)+1000, TimeUnit.MILLISECONDS);
    }

    public static List<Observable<Updatable>> createSimulatedServerCalls(Updatable... friends) {
        return CollectionsUtils.transform(Arrays.asList(friends), new Function<Updatable, Observable<Updatable>>() {
            @Override
            public Observable<Updatable> apply(final Updatable input) {
                input.start();
                return createSimulatedServerCall()
                        .flatMap(new Func1<Long, Observable<Updatable>>() {
                            @Override
                            public Observable<Updatable> call(Long aLong) {
                                return Observable.just(input);
                            }
                        }).doOnNext(new Action1<Updatable>() {
                            @Override
                            public void call(Updatable updatable) {
                                updatable.complete();
                            }
                        });
            }
        });
    }
}
