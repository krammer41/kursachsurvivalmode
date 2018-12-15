package sample;

public class Operator {
    String id;
    String nameOperator;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNameOperator(String nameOperator) {
        this.nameOperator = nameOperator;
    }

    public String getNameOperator() {
        return nameOperator;
    }

    public void determinantID(String number){
        id="";
        String id=number.charAt(3)+"";
         id+=number.charAt(4)+"";
         id+=number.charAt(5)+"";
      //  System.out.println(id);
        switch (id){
            case "099":
                this.setNameOperator("Vodafone");
                break;
            case "066":
                this.setNameOperator("Vodafone");
                break;
            case "097":
                this.setNameOperator("Киевстар");
                break;
            case "067":
                this.setNameOperator("Киевстар");
                break;
                default:
                    this.setNameOperator("Не определён");

        }

    }
}

