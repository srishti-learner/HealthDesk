package tcsproject.healthdesk.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.doctor.Request;
import tcsproject.healthdesk.healthSeeker.Appointment;

/**
 * Created by Sristhi on 7/7/2017.
 */

public class RequestViewHolder extends RecyclerView.ViewHolder {

    public TextView date,reason;  //hospital and time not shown in the list



    public RequestViewHolder(View RequestView) {
        super(RequestView);
        RequestView.setClickable(true);
        reason = (TextView) RequestView.findViewById(R.id.titleText);
        date = (TextView) RequestView.findViewById(R.id.descText);
    }

    public void bind(Request reqData) {

        reason.setText(reqData.getReason());
        date.setText(reqData.getDate());
    }

}