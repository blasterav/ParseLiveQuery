package tgio.parselivequery;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

// this is the middleman object
public class RxBus {
    private static RxBus instance = null;
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());
    private Subscription mSubscription;

    private static RxBus getInstance() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    public static void broadCast(LiveQueryEvent event) {
        getInstance().send(event);
    }
    public static void subscribe(final Action1 onNext){
        getInstance().mSubscription = getInstance().toObserverable().subscribe(onNext);
    }

    public static void unsubscribe(){
        if (getInstance().mSubscription!=null){
            getInstance().mSubscription.unsubscribe();
        }
    }

    private void send(LiveQueryEvent event) {
        _bus.onNext(event);
    }

    private Observable<Object> toObserverable() {
        return _bus;
    }
}