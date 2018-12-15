package sample;

import java.io.Serializable;

public class Notation extends  Operator implements Serializable  {

    String phoneNumber;
    String NameOfTheSubscriber;
    SubscriberAddress subscriberAddress = new SubscriberAddress();
    String fullAdress="1";



    public String getPhoneNumber()  {
        return this.phoneNumber;
    }


    public void fullAddresCreate(){
        if(!subscriberAddress.house.equals(""))
        fullAdress=subscriberAddress.city + "," + subscriberAddress.street + "," + subscriberAddress.house;
        else
            fullAdress="";
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public boolean normNumFormat(String phoneNumber){
        if (phoneNumber.equals("")) return false;
        if (phoneNumber.charAt(0) == '+' && phoneNumber.length() == 13) {

                this.phoneNumber = phoneNumber;
                return true;

        } else return false;

    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber.equals("")) return false;
        if (phoneNumber.charAt(0) == '+' && phoneNumber.length() == 13) {
            boolean flag=true;
            for (int i = 0; i <Controller.base.ATCBase.size() ; i++) {
                if(Controller.base.ATCBase.get(i).getPhoneNumber().equals(phoneNumber)){
                    flag=false;
                    break;
                }
            }


            if(flag){
            this.phoneNumber = phoneNumber;
            return true;
            }
            else return false;
        } else return false;

    }

    public String getNameOfTheSubscriber() {
        return this.NameOfTheSubscriber;
    }

    public boolean setNameOfSubscriber(String NameOfTheSubscriber) {
        if (NameOfTheSubscriber.equals(""))//номер свободен
        {
            this.NameOfTheSubscriber = "phone number is free";
            return true;
        }
        boolean itsTrue = true;
        for (int i = 0; i < NameOfTheSubscriber.length(); i++) {
            if ((Character.isLetter(NameOfTheSubscriber.charAt(i)) == false)
                    && ((NameOfTheSubscriber.charAt(i) != ' '))) {
                itsTrue = false;
                return false;
            }
        }
        if (itsTrue == true)
            this.NameOfTheSubscriber = NameOfTheSubscriber;
        return true;
    }


}
