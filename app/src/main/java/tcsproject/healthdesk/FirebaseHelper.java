package tcsproject.healthdesk;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import tcsproject.healthdesk.assistant.Assistant;
import tcsproject.healthdesk.assistant.AssistantHomePage;
import tcsproject.healthdesk.assistant.TestData;
import tcsproject.healthdesk.doctor.Doctor;
import tcsproject.healthdesk.doctor.Request;
import tcsproject.healthdesk.healthSeeker.Appointment;

/**
 * Created by Srishti on 25/6/2017.
 */

public class FirebaseHelper {
    private DatabaseReference db;
    private DatabaseReference mDatabase,hDatabase;
    public String id;
    public ArrayList<Appointment> paList = new ArrayList<>();
    public ArrayList<Appointment> faList = new ArrayList<>();
    public ArrayList<Doctor> docList = new ArrayList<>();
    public ArrayList<Request> reqList = new ArrayList<>();
    public ArrayList<Request> approvedReqList = new ArrayList<>();
    public ArrayList<Assistant> assistantList = new ArrayList<>();
    public ArrayList<String> testList = new ArrayList<>();
    public ArrayList<TestData> testDataList=new ArrayList<>();
    public ArrayList<String> hospitalList = new ArrayList<String>() {
        {
            add("AIIMS");
            add("Safdarjung");
        }
    };
    public static String[] depList = new String[]{"Allergist", "Cardiologist", "Dermatologist", "Gynaecologist", "Psychiatrist", "Radiologist", "Surgeon"};

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public FirebaseHelper(DatabaseReference db, DatabaseReference mDatabase) {
        this.db = db;
        this.mDatabase = mDatabase;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public ArrayList<Appointment> pastAppointRetrieve() {
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("PastAppList", "InDataChange: " + dataSnapshot.toString());
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.e("PastApp", "In childData: " + childDataSnapshot.toString());
                    final Appointment appointData = new Appointment();
                    appointData.id = childDataSnapshot.getKey();
                    Log.e("pastAppId:", appointData.id);
                    if (appointData.id != null) {
                        Log.e("InIdNull", "Buraahhhh");
                        appointData.doctor = childDataSnapshot.child("doctor").getValue().toString();
                        appointData.date = childDataSnapshot.child("date").getValue().toString();
                        appointData.hospital = childDataSnapshot.child("hospital").getValue().toString();
                        appointData.reason = childDataSnapshot.child("reason").getValue().toString();
                        appointData.department = childDataSnapshot.child("department").getValue().toString();
                        if (childDataSnapshot.child("prescription").getValue() != null) {
                            appointData.prescription = childDataSnapshot.child("prescription").getValue().toString();
                        }
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                        String curDate = df.format(calendar.getTime());

                        String dateVal[] = new String[3];
                        String appDate[] = new String[3];
                        int k = 0;
                        for (String s : curDate.split("/")) {
                            Log.e("DateVal", s);
                            dateVal[k] = s;
                            k++;
                        }
                        k = 0;
                        for (String s : appointData.date.split("/")) {
                            Log.e("eveDate", s);
                            appDate[k] = s;
                            k++;
                        }
                        k = 2;
                        Log.e("value", dateVal[k]);
                        Log.e("appDate", appDate[k]);
                        int x = Integer.parseInt(dateVal[k]);
                        int y = Integer.parseInt(appDate[k]);
                        Log.e("val", x + " x");
                        Log.e("eve", y + " y");
                        while ((x == y) && (k > 0)) {
                            k--;
                            x = Integer.parseInt(dateVal[k]);
                            y = Integer.parseInt(appDate[k]);
                        }
                        if (x > y) {
                            paList.add(appointData);
                        }
                    }

                    Log.d("Past", "List");
                    if (paList.size() > 0)
                        Log.d("PastData", paList.get(paList.size() - 1).doctor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("appointmentList", paList.toString());
        return paList;
    }

    public ArrayList<Appointment> futureAppointRetrieve() {
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapShot : dataSnapshot.getChildren()) {
                    final Appointment appointData = new Appointment();
                    appointData.id = childDataSnapShot.getKey();
                    if (appointData.id != null) {
                        appointData.doctor = childDataSnapShot.child("doctor").getValue().toString();
                        appointData.date = childDataSnapShot.child("date").getValue().toString();
                        appointData.hospital = childDataSnapShot.child("hospital").getValue().toString();
                        appointData.reason = childDataSnapShot.child("reason").getValue().toString();
                        appointData.department = childDataSnapShot.child("department").getValue().toString();
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                        String curDate = df.format(calendar.getTime());

                        String dateVal[] = new String[3];
                        String appDate[] = new String[3];
                        int k = 0;
                        for (String s : curDate.split("/")) {
                            Log.e("DateVal", s);
                            dateVal[k] = s;
                            k++;
                        }
                        k = 0;
                        for (String s : appointData.date.split("/")) {
                            Log.e("appDate", s);
                            appDate[k] = s;
                            k++;
                        }
                        k = 2;
                        Log.e("value", dateVal[k]);
                        Log.e("appDate", appDate[k]);
                        int x = Integer.parseInt(dateVal[k]);
                        int y = Integer.parseInt(appDate[k]);
                        Log.e("val", x + " x");
                        Log.e("Fappoint", y + " y");
                        while ((x == y) && (k > 0)) {
                            k--;
                            x = Integer.parseInt(dateVal[k]);
                            y = Integer.parseInt(appDate[k]);
                        }
                        if (x <= y) {
                            faList.add(appointData);
                        }
                    }
                    Log.d("Future", "List");
                    if (faList.size() > 0)
                        Log.d("futureData", faList.get(faList.size() - 1).doctor);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("FutureAppointmentList", faList.toString());
        return faList;
    }

    public ArrayList<Doctor> docRetrieve() {
        for (int i = 0; i < depList.length; i++) {
            mDatabase = db.child(depList[i]);
            final int finalI = i;
            if (mDatabase.getDatabase() != null) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            Doctor doctor = new Doctor();
                            Log.d("child", "" + childSnapShot);
                            doctor.name = childSnapShot.child("name").getValue().toString();
                            doctor.department = depList[finalI];
                            doctor.id = childSnapShot.getKey();
                            docList.add(doctor);
                            if (docList.size() > 0)
                                Log.d("Doctor Data: ", docList.get(docList.size() - 1).name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        Log.e("DoctorList", docList.toString());
        return docList;
    }

  /*  public ArrayList<Doctor> deptDocRetrieve(final String dept) {
        for(int i=0;i<hospitalList.size();i++){
            mDatabase=db.child(hospitalList.get(i)).child(dept);
            final int finalI = i;
            if(mDatabase.getDatabase()!=null) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            Doctor doctor = new Doctor();
                            doctor.name=childSnapShot.child("name").getValue().toString();
                            doctor.department = dept;
                            deptDocList.add(doctor);
                            if (deptDocList.size() > 0)
                                Log.d("Doctor Data: ", docList.get(deptDocList.size() - 1).name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        Log.e("DeptDoctorList", deptDocList.toString());
        return deptDocList;
    }*/

    public ArrayList<String> hospitalRetrieve() {
        Log.e("hospitalList: ", hospitalList.toString());
        return hospitalList;
    }

    public ArrayList<Request> requestsRetrieve() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("req", "InData");
                Log.d("notworkingdata", dataSnapshot.toString());
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Request request = new Request();
                    Log.d("qwertyReq", "called" + childSnapShot.toString());
                    request.reqId = childSnapShot.getKey();
                    Log.d("newR", childSnapShot.toString());
                    request.id = childSnapShot.child("id").getValue().toString();
                    request.date = childSnapShot.child("date").getValue().toString();
                    request.reason = childSnapShot.child("reason").getValue().toString();
                    if (childSnapShot.child("grant").getValue() == null)
                        reqList.add(request);
                    Log.d("req", "added");
                    if (reqList.size() > 0)
                        Log.d("Request Data: ", reqList.get(reqList.size() - 1).id);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("RequestList: ", reqList.toString());
        return reqList;
    }

    public ArrayList<Request> getApprovedReqList() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("appReq", "InData");
                Log.d("notWorkingData", dataSnapshot.toString());
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Request request = new Request();
                    Log.d("qwertyReq", "called" + childSnapShot.toString());
                    request.reqId = childSnapShot.getKey();
                    Log.d("newR", childSnapShot.toString());
                    request.id = childSnapShot.child("id").getValue().toString();
                    request.date = childSnapShot.child("date").getValue().toString();
                    request.reason = childSnapShot.child("reason").getValue().toString();
                    if (childSnapShot.child("grant").getValue() != null) {
                        if (childSnapShot.child("grant").getValue().toString().contentEquals("true")) {
                            approvedReqList.add(request);
                        }
                    }
                    Log.d("req", "added");
                    if (approvedReqList.size() > 0)
                        Log.d("Request Data: ", approvedReqList.get(approvedReqList.size() - 1).id);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("RequestList: ", approvedReqList.toString());
        return approvedReqList;
    }

    public ArrayList<Assistant> assistantRetrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Assistant assistant=new Assistant();
                    Log.d("childA", "" + childSnapShot);
                    assistant.name = childSnapShot.child("name").getValue().toString();
                    assistant.id = childSnapShot.getKey();
                    assistantList.add(assistant);
                    if (assistantList.size() > 0)
                        Log.d("Assistant Data: ", assistantList.get(assistantList.size() - 1).name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("DoctorList", assistantList.toString());
        return assistantList;
    }

    public ArrayList<String> testRetrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("assdata",dataSnapshot.toString());
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    testList.add(childSnapshot.getValue().toString());
                    if (testList.size() > 0)
                        Log.d("Assistant Data: ", testList.get(testList.size() - 1));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("TestList", testList.toString());
        return testList;
    }

    public ArrayList<TestData> testDataRetrieve() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("testDataData",dataSnapshot.toString());
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    final String[] doctor = new String[1];
                    final String[] patient = new String[1];
                    String docId=childSnapshot.child("docId").getValue().toString();
                    final String hId=childSnapshot.child("hId").getValue().toString();
                    final String dept=childSnapshot.child("department").getValue().toString();
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Hospitals").child(AssistantHomePage.hospital).child(dept)
                    .child(docId).child("name");
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            doctor[0] =dataSnapshot.getValue().toString();
                            hDatabase=FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(hId).child("name");
                            hDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    patient[0] =dataSnapshot.getValue().toString();
                                    TestData data=new TestData(doctor[0], patient[0],dept);
                                    testDataList.add(data);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if (testDataList.size() > 0)
                        Log.d("Doctor TestData: ", testDataList.get(testDataList.size() - 1).doctor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("TestDataList", testDataList.toString());
        return testDataList;
    }
}