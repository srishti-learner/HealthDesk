package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.assistant.TestData;
import tcsproject.healthdesk.doctor.Doctor;

/**
 * Created by Srishti on 7/7/2017.
 */

public class TestDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context c;
    private ArrayList<TestData> tdList;

    public TestDataAdapter(Context c, ArrayList<TestData> list) {
        this.c = c;
        this.tdList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TestData data = tdList.get(position);
        String doc="By: "+data.getDoctor()+"("+data.getDept()+")";
        holder.title.setText(data.getPatient());
        holder.desc.setText(doc);
    }

    @Override
    public int getItemCount() {
        return tdList.size();
    }
}
