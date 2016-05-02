package rx.liip.ch.rxdemo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RxOperations {

    private static final Observable.Transformer schedulerTransformer = new Observable.Transformer<Object, Object>() {
        @Override
        public Observable<Object> call(Observable<Object> observable) {
            return observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    };

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>)schedulerTransformer;
    }

    public static void run(Observable observable) {
        ((ConnectableObservable)observable).connect();
    }
}
