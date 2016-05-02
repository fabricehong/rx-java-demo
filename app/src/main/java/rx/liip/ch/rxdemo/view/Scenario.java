package rx.liip.ch.rxdemo.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.liip.ch.rxdemo.R;

public abstract class Scenario extends Fragment{


    private static final int PADDING = 10;
    protected Activity activity;
    private LayoutInflater layoutInflater;

    @Bind(R.id.table)
    TableLayout table;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    protected void addRow(String label, Item... items) {
        if (table.getChildCount()!=0) {
            LinearLayout divider = new LinearLayout(activity);
            divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            divider.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.divider, null));
            table.addView(divider);
        }

        View row = layoutInflater.inflate(R.layout.row_content, table, false);
        TextView title = (TextView)row.findViewById(R.id.row_name);
        title.setText(label);
        LinearLayout rowItems = (LinearLayout)row.findViewById(R.id.view_items);

        for (Item item : items) {
            rowItems.addView(item);
        }

        table.addView(row);
    }

    protected void clear() {
        table.removeAllViews();
    }

    public void init(Activity activity, LayoutInflater layoutInflater) {
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    abstract protected void init();

    abstract public void run();


}
