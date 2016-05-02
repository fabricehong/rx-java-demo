package rx.liip.ch.rxdemo.view;

import android.app.Activity;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import rx.liip.ch.rxdemo.R;
import rx.liip.ch.rxdemo.Updatable;

public class Item extends LinearLayout implements Updatable {

    public static final int PADDING = 10;
    private final Activity activity;
    private TextView labelView;

    public Item(Activity activity) {
        super(activity);
        this.activity = activity;
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.deactivated, null));
        //setPadding(PADDING, PADDING, PADDING, PADDING);
        LayoutParams params = new LayoutParams(100, 100);
        params.setMargins(PADDING, PADDING, PADDING, PADDING);
        setLayoutParams(params);

        labelView = new TextView(activity);
        labelView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(labelView);

        requestLayout();
    }

    public void setLabel(final String label) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                labelView.setText(label);
            }
        });

    }

    @Override
    public void complete() {
        System.out.println("Item : complete");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.complete, null));
            }
        });
    }

    @Override
    public void start() {
        System.out.println("Item : in progress");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.in_progress, null));
            }
        });
    }

    public String getLabel() {
        return labelView.getText().toString();
    }

    @Override
    public void error() {
        System.out.println("Item : error");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.error, null));
            }
        });
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
