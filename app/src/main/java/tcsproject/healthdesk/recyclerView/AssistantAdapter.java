package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.assistant.Assistant;

/**
 * Created by Srishti on 6/7/2017.
 */

public class AssistantAdapter extends RecyclerView.Adapter<AssistantHolder> {
    Context c;
    private ArrayList<Assistant> list;
    public interface OnItemCheckListener {
        void onItemCheck(Assistant assistant);
        void onItemUncheck(Assistant assistant);
    }


    @NonNull
    private OnItemCheckListener onItemCheckListener;
    public AssistantAdapter(Context c, ArrayList<Assistant> list, @NonNull OnItemCheckListener onItemCheckListener) {
        this.c=c;
        this.list = list;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public AssistantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_assistant_row, parent, false);

        return new AssistantHolder(view);
    }

    @Override
    public void onBindViewHolder(final AssistantHolder holder, int position) {
        final Assistant assistant = list.get(position);
        holder.name.setText(assistant.getName());


        (holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (holder).checkbox.setChecked(!((holder).checkbox.isChecked()));

                if ((holder).checkbox.isChecked()) {
                    onItemCheckListener.onItemCheck(assistant);
                } else {
                    onItemCheckListener.onItemUncheck(assistant);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}