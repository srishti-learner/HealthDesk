package tcsproject.healthdesk.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.healthSeeker.Appointment;

/**
 * Created by Srishti on 27/06/2017.
 */

class AppointViewHolder extends RecyclerView.ViewHolder {

    public TextView doctor, date,reason;  //hospital and time not shown in the list



    public AppointViewHolder(View appointView) {
        super(appointView);
        appointView.setClickable(true);
        doctor = (TextView) appointView.findViewById(R.id.doctor);
        date = (TextView) appointView.findViewById(R.id.date);
        //hospital = (TextView) appointView.findViewById(R.id.hospital);
       // time = (TextView) appointView.findViewById(R.id.time);
        reason = (TextView) appointView.findViewById(R.id.reason);
    }

    public void bind(Appointment appointData) {

        doctor.setText(appointData.getDoctor());
        date.setText(appointData.getDate());
        //time.setText(appointData.getTime());
        //hospital.setText(appointData.getHospital());
        reason.setText(appointData.getReason());
    }

    /* public void bind(User user) {
        //user data binding to display
    }*/
}
