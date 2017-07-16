package tcsproject.healthdesk.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.recyclerView.AppointmentAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;
import tcsproject.healthdesk.recyclerView.RequestAdapter;

/**
 * Created by Srishti on 6/7/2017.
 */

public class RequestsList extends Fragment {
    FirebaseHelper helper;
    DatabaseReference db,rootRef,mDatabase;
    public Bundle bundle = new Bundle();
    public String  name,dob,date,reason,id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointments, container, false);

        final RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                db = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(DoctorHomePage.hospital).child(DoctorHomePage.department).child(DoctorHomePage.docId).child("Requests");
                db.keepSynced(true);
                mDatabase=FirebaseDatabase.getInstance().getReference().child("HealthSeeker");
                if(db!=null){
                    Log.d("reqe","notNull "+db.toString());
                }
                else
                    Log.d("reqe","null");
                helper = new FirebaseHelper(db);
                RequestAdapter adapter = new RequestAdapter(getActivity(), helper.requestsRetrieve());
                recyclerview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(helper.reqList.size()>0) {
                    Request data = helper.reqList.get(position);
                    Log.d("position:", position + " ");
                    bundle.putString("Date", data.getDate());
                    bundle.putString("Reason",data.getReason());
                    bundle.putString("Id",data.getId());
                    bundle.putString("ReqId",data.getReqId());
                    bundle.putString("type","request");
                    Log.d("PatientId: ",data.getId());
                    mDatabase.child(data.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            bundle.putString("Name",dataSnapshot.child("name").getValue().toString());
                            bundle.putString("Dob",dataSnapshot.child("dob").getValue().toString());
                            Intent intent = new Intent(getActivity(),RequestPage.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
}