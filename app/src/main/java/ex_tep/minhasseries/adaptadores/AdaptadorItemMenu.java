package ex_tep.minhasseries.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.ItemMenu;

/**
 * Created by Anailson on 08/07/2015.
 */
public class AdaptadorItemMenu extends BaseAdapter {

    private Context context;
    private ArrayList<ItemMenu> navDrawerItems;

    public AdaptadorItemMenu(Context context, ArrayList<ItemMenu> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_menu, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        if (navDrawerItems.get(position).getCounterVisible()) {
            txtCount.setText(navDrawerItems.get(position).getCount());
        } else {
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }
}