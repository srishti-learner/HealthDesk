package tcsproject.healthdesk.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import tcsproject.healthdesk.R;

/**
 * Created by uset on 7/7/2017.
 */

public class AssistantHolder extends RecyclerView.ViewHolder{
    public TextView name;
    CheckBox checkbox;
    View itemView;

    public AssistantHolder(View view) {
        super(view);
        this.itemView=view;
        name = (TextView) view.findViewById(R.id.name);
        checkbox= (CheckBox) view.findViewById(R.id.checkBox);
        checkbox.setClickable(false);
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }
}