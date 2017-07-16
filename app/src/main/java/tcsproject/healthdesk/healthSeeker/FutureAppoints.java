package tcsproject.healthdesk.healthSeeker;


/**
 * Created by srishti on 26/06/2017.
 */

import tcsproject.healthdesk.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.recyclerView.AppointmentAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FutureAppoints extends Fragment  {
    FirebaseHelper helper;
    DatabaseReference db,rootRef;
    public Bundle bundle = new Bundle();
    public String doctor, date,department,hospital,reason,id;
    FloatingActionButton fab;
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
        fab =(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(),HospitalList.class);
                Bundle bundle=new Bundle();
                bundle.putString("User","HealthSeeker");
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db = FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(HealthSeekerHomePage.Student).child("Appointments");
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        AppointmentAdapter adapter = new AppointmentAdapter(getActivity(), helper.futureAppointRetrieve());
        recyclerview.setAdapter(adapter);

        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(helper.faList.size()>0) {
                    Appointment data = helper.faList.get(position);
                    Log.d("position:", position + " ");
                    doctor = data.getDoctor();
                    date=data.getDate();
                    hospital= data.getHospital();
                    reason= data.getReason();
                    department = data.getDepartment();
                    id=data.getId();
                    Log.d("DocData",doctor);

                    Intent intent = new Intent(getActivity(),AppointDetails.class);
                    bundle.putString("Doctor", doctor);
                    bundle.putString("Date", date);
                    bundle.putString("Department", department);
                    bundle.putString("Hospital", hospital);
                    bundle.putString("Reason", reason);
                    bundle.putString("Id", id);
                    bundle.putString("From","future");
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
