package tcsproject.healthdesk.doctor;


/**
 * Created by Srishti on 6/7/2017.
 */

public class Request {
    public String id,dob,reason,date,name;
    public String grant,reqId;

    public Request(){

    }
    public void Request(String id,String reason, String date){
        this.id=id;
        this.reason=reason;
        this.date=date;
    }
    public Request(String id,  String name, String dob, String reason, String date) {
        this.id=id;
        this.name=name;
        this.dob=dob;
        this.reason=reason;
        this.date=date;
    }
    public String getId(){return id; }
    public String getDob(){return dob; }
    public String getName(){return name; }
    public String getReason(){return reason; }
    public String getDate(){return date; }
    public String getReqId(){return reqId; }
}
