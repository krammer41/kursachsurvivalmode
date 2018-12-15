package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Base {
    public  static ObservableList<Notation> ATCBase = FXCollections.observableArrayList();


    public boolean toFile() throws Exception {

        ArrayList<Notation> temp=new ArrayList<>(ATCBase);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Base.dat"))) {
            objectOutputStream.writeObject(temp);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public void fromFile(){
        ArrayList<Notation> tmp=null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Base.dat")))
        {
            tmp=(ArrayList) ois.readObject();

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        ObservableList<Notation> temp2=FXCollections.observableArrayList(tmp);
        ATCBase=temp2;

    }

    public void add(Notation notation) {
        this.ATCBase.add(notation);
    }

    public void add(){
        System.out.println("create new notation.");
        Notation newNotation=new Notation();
        Scanner scn=new Scanner(System.in);
        String temp;
        String tempAddres[]=new String[3];

        boolean flag =false;
        boolean isFree=false;
        do {
            System.out.println("Input phone number:");
            temp = scn.next();
           flag= newNotation.setPhoneNumber(temp);
        }while (!flag);

        do{
            System.out.println("Input Name Of The Subscriber:(input [.] if number is free");
            temp=scn.next();
            if(temp.equals(".")){
                isFree=true;
                newNotation.setNameOfSubscriber("");
            }
            else
            flag=newNotation.setNameOfSubscriber(temp);
        }while (!flag);
        if(!isFree) {
            do {

                System.out.println("Input Subscriber City:");
                tempAddres[0] = scn.next();
                System.out.println("Input Subscriber Street:");
                tempAddres[1] = scn.next();
                System.out.println("Input Subscriber Address House:");
                tempAddres[2] = scn.next();

                flag = newNotation.subscriberAddress.setSubscriberAddress(tempAddres[0], tempAddres[1], tempAddres[2]);
            } while (!flag);
        }

        add(newNotation);
    }

    public String searchByNumber(String search) {
        String result="";
        int searchI = -1;
        boolean searchB = false;
        for (int i = 0; i < ATCBase.size(); i++) {
            if (ATCBase.get(i).getPhoneNumber().equals(search)) {
                searchB = true;
                searchI = i;
                break;
            }

        }
        if (searchB) {

            if (ATCBase.get(searchI).getNameOfTheSubscriber().equals(""))
                result += "Этот номер свободен!";
            else {
                result += "Имя абонента:" + ATCBase.get(searchI).getNameOfTheSubscriber();
                result += "\nАдрес абонента:" + ATCBase.get(searchI).subscriberAddress.getSubscriberAddress();
            }
        }else result="Совпадений не найдено";

        return result;

    }

    public ArrayList<String> lisOfFreeNumbers() {
        boolean flag = false;
        ArrayList<String> freeNumbers=new ArrayList<>();
        for (int i = 0; i < ATCBase.size(); i++) {
            if (ATCBase.get(i).getNameOfTheSubscriber().equals("phone number is free")) {
                flag=true;
               freeNumbers.add(ATCBase.get(i).phoneNumber);
            }
        }
        if (!flag)
            return null;
        return freeNumbers;
    }

    public String searchByName(String search) {
        boolean flag = false;
        String result="";
        for (int i = 0; i < ATCBase.size(); i++) {
            if (ATCBase.get(i).NameOfTheSubscriber.equals(search)) {
                result+="Абонент владеет номером " + ATCBase.get(i).phoneNumber;
                result+=" \nАдрес абонента:" + ATCBase.get(i).subscriberAddress.getSubscriberAddress();
                flag = true;

            }
        }
        if (!flag)
            result+="Поиск не дал результатов";

        return result;
    }

    public void changeOwner(String number) {
        boolean flag = false;
        for (int i = 0; i < ATCBase.size(); i++) {
            if (ATCBase.get(i).getPhoneNumber().equals(number)) {
                Scanner scn = new Scanner(System.in);
                String temp = "";
                if (ATCBase.get(i).getNameOfTheSubscriber().equals("phone number is free")) {
                    System.out.println("phone number is free. \nspecify new owner:");
                    temp = scn.next();
                    ATCBase.get(i).setNameOfSubscriber(temp);
                } else {
                    System.out.println("The owner is " + ATCBase.get(i).getNameOfTheSubscriber() + "\nspecify new owner:");
                    temp = scn.next();
                    ATCBase.get(i).setNameOfSubscriber(temp);
                }
            } else
                System.out.println("number not found!");

        }

    }
}
