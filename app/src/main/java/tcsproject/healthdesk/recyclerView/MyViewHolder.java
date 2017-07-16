package tcsproject.healthdesk.recyclerView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tcsproject.healthdesk.R;
/**
 * Created by Srishti on 04/07/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView title,desc;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.titleText);
        desc = (TextView) view.findViewById(R.id.descText);
    }
}
