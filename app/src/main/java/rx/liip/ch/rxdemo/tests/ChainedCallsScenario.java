package rx.liip.ch.rxdemo.tests;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.view.Scenario;

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



    }
}
