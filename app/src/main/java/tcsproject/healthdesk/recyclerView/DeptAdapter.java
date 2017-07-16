package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tcsproject.healthdesk.R;

/**
 * Created by Srishti on 5/07/2017.
 */

public class DeptAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context c;
    private String[] dList;

    public DeptAdapter(Context c, String[] list) {
        this.c = c;
        this.dList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_single_line, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String data = dList[position];
        holder.title.setText(data);
    }
    @Override
    public int getItemCount() {
        return dList.length;
    }
}