package rx.liip.ch.rxdemo.utils;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by fabrice on 16.03.16.
 */
public class CollectionsUtils {
    public static <A, B> List<B> transform(Collection<A> collection, Function<A, B> function) {
        ArrayList<B> result = new ArrayList<>();
        for (A b : collection) {
            result.add(function.apply(b));
        }
        return result;
    }


    public static <T> List<T> filter(List<T> filteredOfferVariations, Predicate<T> predicate) {
        ArrayList<T> result = new ArrayList<>();
        for (T obj : filteredOfferVariations) {
            if (predicate.apply(obj)) {
                result.add(obj);
            }
        }
        return result;
    }
}
