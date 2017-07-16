package tcsproject.healthdesk.healthSeeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.recyclerView.AppointmentAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;

/**
 * Created by Srishti on 3/07/2017.
 */

public class PastAppoints extends Fragment{
    FirebaseHelper helper;
    DatabaseReference db;
    public Bundle bundle = new Bundle();
    public String doctor, date,department,hospital,reason,id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointments, container, false);

        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        db = FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(HealthSeekerHomePage.Student).child("Appointments");
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        AppointmentAdapter adapter = new AppointmentAdapter(getActivity(), helper.pastAppointRetrieve());
        recyclerview.setAdapter(adapter);

        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(helper.paList.size()>0) {
                    Appointment data = helper.paList.get(position);
                    Log.d("position:", position + " ");
                    doctor = data.getDoctor();
                    date=data.getDate();
                    hospital= data.getHospital();
                    reason= data.getReason();
                    department=data.getDepartment();
                    id=data.getId();
                    //hospital = data.getKey();
                    Log.d("DocData",doctor);

                    Intent intent = new Intent(getActivity(),AppointDetails.class);
                    bundle.putString("Doctor", doctor);
                    bundle.putString("Date", date);
                    bundle.putString("Department", department);
                    bundle.putString("Hospital", hospital);
                    bundle.putString("Reason",reason);
                    bundle.putString("Id",id);
                    bundle.putString("From","past");
                    if(!data.getPrescription().contentEquals("")){
                        bundle.putString("Prescription",data.prescription);
                    }
                    else
                        bundle.putString("Prescription","");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
}
