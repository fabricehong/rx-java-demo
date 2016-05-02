package rx.liip.ch.rxdemo.fragments;

import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.view.Scenario;

public class LiveScenario extends Scenario {

    private Item viewState;
    private Item modelState;

    protected void init() {
        clear();
        viewState = new Item(activity);
        modelState = new Item(activity);
        addRow("View", viewState);
        addRow("Model", modelState);
    }

    @Override
    public void run() {

        init();

        //calls

    }


}
