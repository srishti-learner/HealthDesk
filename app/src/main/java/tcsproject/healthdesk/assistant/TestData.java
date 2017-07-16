package tcsproject.healthdesk.assistant;

/**
 * Created by Srishti on 8/7/2017.
 */

public class TestData {
    public String patient,doctor,dept;
    public TestData(){

    }

    public TestData(String doctor, String patient,String dept) {
        this.doctor=doctor;
        this.patient=patient;
        this.dept=dept;
    }

    public String getPatient() {
        return patient;
    }

    public String getDoctor() {
        return doctor;
    }
    public String getDept() {
        return dept;
    }

}
