package tcsproject.healthdesk.healthSeeker;


/**
 * Created by Srishti on 26/6/2017.
 */

public class Appointment {
    public String id,date,doctor,hospital,department,reason;  //reason to visit the doctor
    public String prescription="";
    public Appointment(){

    }


    public Appointment( String hospital, String department, String doctor, String date, String reason) {
        this.doctor=doctor;
        this.hospital=hospital;
        this.department=department;
        this.date=date;
        this.reason=reason;
    }

    public String getDoctor() {
        return doctor;
    }
    public String getId() { return id; }
    public String getDate() {
        return date;
    }
    public String getDepartment() {
        return department;
    }
    public String getHospital() {
        return hospital;
    }
    public String getReason() {
        return reason;
    }
    public  String getPrescription(){return  prescription;}
}
