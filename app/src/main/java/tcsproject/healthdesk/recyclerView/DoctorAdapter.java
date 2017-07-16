package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.doctor.Doctor;
import java.util.ArrayList;

/**
 * Created by Srishti on 05/07/2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context c;
    private ArrayList<Doctor> dList;

    public DoctorAdapter(Context c, ArrayList<Doctor> list) {
        this.c = c;
        this.dList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Doctor data = dList.get(position);
        holder.title.setText(data.getDoctor());
        holder.desc.setText(data.getDepartment());
    }

    @Override
    public int getItemCount() {
        return dList.size();
    }
}
