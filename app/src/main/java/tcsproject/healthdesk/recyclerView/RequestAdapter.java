package tcsproject.healthdesk.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.doctor.Request;

/**
 * Created by Srishti on 6/7/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    Context c;
    private ArrayList<Request> aList;

    public RequestAdapter(Context c, ArrayList<Request> list) {
        this.c = c;
        this.aList = list;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        Request data = aList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public void setFilter(List<Request> reqData){
        aList = new ArrayList<>();
        aList.addAll(reqData);
        notifyDataSetChanged();
    }

}