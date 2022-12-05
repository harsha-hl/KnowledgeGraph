package reshamandi.knowledgeGraph;

public class Transactions {
    private String[] ID;
    private String role;
    private String[] month;
    private String[] state;
    private String[] type;
    private String[] weave;
    private String[] category;

    //1.Weaver or Retailer ID
    public String[] getID(){
        return ID;
    }
    public void setID(String[] ID){
        this.ID=ID;
    }

    //2.Weaver or Retailer role
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }

    //3. Month
    public String[] getMonth(){
        return month;
    }
    public void setMonth(String[] month){
        this.month=month;
    }

    //4. State
    public String[] getState(){
        return state;
    }
    public void setState(String[] state){
        this.state=state;
    }

    //5. Type
    public String[] getType(){
        return type;
    }
    public void setType(String[] type){
        this.type=type;
    }

    //6. Weave
    public String[] getWeave(){
        return weave;
    }
    public void setWeave(String[] weave){
        this.weave=weave;
    }

    //7. Category
    public String[] getCategory(){
        return category;
    }
    public void setCategory(String[] category){
        this.category=category;
    }
    private String[][] attr;
    public String[][] getattr() {
        return attr;
    }
    public void setattr(String a[][]) {
        attr = a;
    }
}
