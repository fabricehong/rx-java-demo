package rx.liip.ch.rxdemo.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.liip.ch.rxdemo.R;

public abstract class Scenario extends Fragment{


    private static final int PADDING = 10;
    protected Activity activity;

    @Bind(R.id.view_items)
    LinearLayout viewItems;

    @Bind(R.id.model_categories)
    LinearLayout modelCategories;

    LinearLayout currentModelItemRow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this, rootView);
        addModelRow();
        return rootView;
    }

    protected void addModelRow() {
        currentModelItemRow = new LinearLayout(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(PADDING, PADDING, PADDING, PADDING);
        currentModelItemRow.setLayoutParams(params);
        currentModelItemRow.requestLayout();
        modelCategories.addView(currentModelItemRow);
    }

    protected void clear() {
        viewItems.removeAllViews();
        currentModelItemRow.removeAllViews();
        modelCategories.removeAllViews();
        addModelRow();
    }

    protected void setViewItems(Item... items) {
        for (Item item : items) {
            viewItems.addView(item);
        }
    }

    protected void setCurrentModelItemRow(Item... items) {
        for (Item item : items) {
            currentModelItemRow.addView(item);
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    abstract public void run();


}
