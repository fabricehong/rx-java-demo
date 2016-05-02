package rx.liip.ch.rxdemo.fragments;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.FuncN;
import rx.liip.ch.rxdemo.Item;
import rx.liip.ch.rxdemo.Model;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.Scenario;
import rx.liip.ch.rxdemo.Server;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;

import static org.mockito.Mockito.when;

public class ParallelCallsScenario extends Scenario {

    private Random rand = new Random();

    @Override
    public void run() {
        clear();

        Item[] friends = new ArrayList<Item>() {{
            for (int i = 0; i < 10; i++) {
                add(new Item(activity));
            }
        }}.toArray(new Item[10]);

        final Item loaded = new Item(activity);
        setViewItems(loaded);
        setCurrentModelItemRow(friends);

        // calls

        List<Observable<Updatable>> serverCalls = ServerCalls.createSimulatedServerCalls(friends);

        Observable<List<Updatable>> observerForView = Observable.combineLatest(serverCalls, new FuncN<List<Updatable>>() {
            @Override
            public List<Updatable> call(final Object... args) {
                return new ArrayList<Updatable>() {{
                    for (Object o : args) {
                        Updatable o1 = (Updatable) o;
                        o1.complete();
                        add(o1);
                    }
                }};
            }
        });

        observerForView
                .compose(RxOperations.<List<Updatable>>applySchedulers())
                .subscribe(new Subscriber<List<Updatable>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Activity : complete");
                        loaded.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loaded.error();
                    }

                    @Override
                    public void onNext(List<Updatable> o) {
                        loaded.complete();
                        System.out.println(String.format("Activity : on next : %s", o));
                    }
                });
    }

}
