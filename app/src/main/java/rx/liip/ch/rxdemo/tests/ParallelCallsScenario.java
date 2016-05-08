package rx.liip.ch.rxdemo.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.FuncN;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.view.Scenario;

public class ParallelCallsScenario extends Scenario {

    private Item[] friendsCall;
    private Item viewState;

    protected void init() {
        clear();
        viewState = new Item(activity);
        friendsCall = new ArrayList<Item>() {{
            for (int i = 0; i < 7; i++) {
                add(new Item(activity));
            }
        }}.toArray(new Item[7]);

        addRow("View", viewState);
        addRow("Model", friendsCall);
    }

    @Override
    public void run() {
        init();

        // calls


    }

}
