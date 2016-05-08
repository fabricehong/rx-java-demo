package rx.liip.ch.rxdemo.tests;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.view.Scenario;
import rx.schedulers.Schedulers;

public class ModelUpdateScenario extends Scenario {

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
