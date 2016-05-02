package rx.liip.ch.rxdemo.utils;

/**
 * Created by fabrice on 16.03.16.
 */
public interface Function<A, B> {
    B apply(A input);
}
