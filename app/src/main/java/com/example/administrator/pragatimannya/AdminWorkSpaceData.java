package com.example.administrator.pragatimannya;

/**
 * Created by Administrator on 7/13/2017.
 */

public class AdminWorkSpaceData {
        public String empname;
        public String empid;
        public Long contact;
        public String email;
        public String type;
        public String empuid;

        public AdminWorkSpaceData(){
        //Empty Constructor
        }

        public AdminWorkSpaceData(String empname,String empid,String type,Long contact,String email,String empuid){
            this.empname=empname;
            this.empid=empid;
            this.type=type;
            this.contact=contact;
            this.email=email;
            this.empuid=empuid;
        }
        public String getEmpname()
        {
            return this.empname;
        }
        public String getEmpid()
        {
            return this.empid;
        }
        public String getType(){return this.type;}
        public String getContact() {return this.contact.toString();}
        public String getEmail() {return this.email;}
        public String getEmpuid(){return  this.empuid;}
}
/*ref2.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                Message message = messageSnapshot.getValue(Message.class);
            }
        }
   //..
}*/
