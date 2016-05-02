package rx.liip.ch.rxdemo.fragments;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.liip.ch.rxdemo.view.Item;
import rx.liip.ch.rxdemo.RxOperations;
import rx.liip.ch.rxdemo.view.Scenario;
import rx.liip.ch.rxdemo.ServerCalls;
import rx.liip.ch.rxdemo.Updatable;

public class ModelUpdateScenario extends Scenario {

    @Override
    public void run() {
        clear();

        final Item viewState = new Item(activity);
        final Item modelState = new Item(activity);
        setViewItems(viewState);
        setCurrentModelItemRow(modelState);

        //calls

        Observable<Updatable> observerForView = ServerCalls.createSimulatedServerCall(modelState)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        modelState.complete();
                    }
                });

        observerForView
                .compose(RxOperations.applySchedulers())
                .subscribe(new Subscriber<Object>() {
                       @Override
                       public void onCompleted() {
                           System.out.println("Activity : complete");
                           viewState.complete();
                       }

                       @Override
                       public void onError(Throwable e) {

                       }

                       @Override
                       public void onNext(Object o) {
                           System.out.println(String.format("Activity : on next : %s", o));
                       }
                   }
                );
    }
}
