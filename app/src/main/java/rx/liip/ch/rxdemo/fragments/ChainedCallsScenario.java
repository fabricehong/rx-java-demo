package rx.liip.ch.rxdemo.fragments;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.view.Scenario;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;

public class ChainedCallsScenario extends Scenario {

    private Item call1State;
    private Item call2State;
    private Item viewState;

    protected void init() {
        clear();

        viewState = new Item(activity);
        call1State = new Item(activity);
        call2State = new Item(activity);
        addRow("View", viewState);
        addRow("Model : call 1", call1State);
        addRow("Model : call 2", call2State);
    }

    @Override
    public void run() {

        init();

        // calls

        final Observable<Updatable> call1Observable = ServerCalls.createSimulatedServerCall(call1State, "R1");

        Observable<Updatable> observableForView = call1Observable.flatMap(new Func1<Updatable, Observable<Updatable>>() {
            @Override
            public Observable<Updatable> call(final Updatable firstResult) {
                call1State.complete();
                return ServerCalls.createSimulatedServerCall(call2State, "R2").flatMap(new Func1<Updatable, Observable<Updatable>>() {
                    @Override
                    public Observable<Updatable> call(Updatable call2State) {
                        call2State.complete();
                        return Observable.just(firstResult);
                    }
                });
            }
        });

        observableForView
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
