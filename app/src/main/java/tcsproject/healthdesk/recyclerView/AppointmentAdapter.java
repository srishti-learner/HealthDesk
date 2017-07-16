package tcsproject.healthdesk.recyclerView;

/**
 * Created by Srishti on 27/06/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.healthSeeker.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srishti on 27/06/2017.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointViewHolder> {
    Context c;
    private ArrayList<Appointment> aList;

    public AppointmentAdapter(Context c, ArrayList<Appointment> list) {
        this.c = c;
        this.aList = list;
    }

    @Override
    public AppointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View appointView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new AppointViewHolder(appointView);
    }

    @Override
    public void onBindViewHolder(AppointViewHolder holder, int position) {
        Appointment data = aList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public void setFilter(List<Appointment> appointData){
        aList = new ArrayList<>();
        aList.addAll(appointData);
        notifyDataSetChanged();
    }

}
