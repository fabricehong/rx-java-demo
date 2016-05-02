package rx.liip.ch.rxdemo.fragments;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.FuncN;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.view.Scenario;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;

public class AllScenarios extends Scenario {

    @Override
    public void run() {
        clear();

        final Item viewState = new Item(activity);
        final Item modelState = new Item(activity);
        final Item userState = new Item(activity);
        final Item[] friends = new ArrayList<Item>() {{
            for (int i = 0; i < 10; i++) {
                add(new Item(activity));
            }
        }}.toArray(new Item[10]);
        final Item[] addresses = new ArrayList<Item>() {{
            for (int i = 0; i < 4; i++) {
                add(new Item(activity));
            }
        }}.toArray(new Item[4]);

        setViewItems(viewState);
        setCurrentModelItemRow(modelState);
        addModelRow();
        setCurrentModelItemRow(userState);
        addModelRow();
        setCurrentModelItemRow(friends);
        addModelRow();
        setCurrentModelItemRow(addresses);

        // calls

        final Observable<Updatable> userInitialization = ServerCalls.createSimulatedServerCall(userState, "User")
                .flatMap(new Func1<Updatable, Observable<Updatable>>() {
                    @Override
                    public Observable<Updatable> call(final Updatable user) {
                        user.complete();
                        Observable<List<Updatable>> friendCalls = Observable.combineLatest(ServerCalls.createSimulatedServerCalls(friends), new FuncN<List<Updatable>>() {
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

                        Observable<List<Updatable>> addressesCalls = Observable.combineLatest(ServerCalls.createSimulatedServerCalls(addresses), new FuncN<List<Updatable>>() {
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

                        Observable<Updatable> friendsAndAddressesParallel = Observable.combineLatest(friendCalls, addressesCalls, new Func2<List<Updatable>, List<Updatable>, Updatable>() {
                            @Override
                            public Updatable call(List<Updatable> updatables, List<Updatable> updatables2) {
                                return user;
                            }
                        });
                        return friendsAndAddressesParallel;
                    }
                });

        Observable<Updatable> observerForView = userInitialization.doOnNext(new Action1<Updatable>() {
            @Override
            public void call(Updatable updatable) {
                modelState.setLabel(updatable.getLabel());
                modelState.complete();
            }
        });

        observerForView
                .compose(RxOperations.<Updatable>applySchedulers())
                .subscribe(new Action1<Updatable>() {
                    @Override
                    public void call(Updatable updatable) {
                        viewState.setLabel(updatable.getLabel());
                        viewState.complete();
                    }
                });

    }


}
