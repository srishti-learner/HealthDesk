package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tcsproject.healthdesk.R;

/**
 * Created by Srishti on 7/7/2017.
 */

public class AssistantPatientAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context c;
    private ArrayList<String> list;

    public AssistantPatientAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_single_line, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String assistant = list.get(position);
        holder.title.setText(assistant);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
