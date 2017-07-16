package tcsproject.healthdesk.healthSeeker;

/**
 * Created by Srishti on 6/26/2017.
 */

public class User {
   public String name,mailId,phone,pass,dob;
    public User(){

    }
    public User(String name, String mailId, String phone, String pass,String dob) {
        this.name=name;
        this.mailId=mailId;
        this.phone=phone;
        this.pass=pass;
        this.dob=dob;
    }


}
