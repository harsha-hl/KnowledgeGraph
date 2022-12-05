package reshamandi.knowledgeGraph;

public class RetailerReg {
    private int id;
    private long phone;
    private String name; 
    private String state; 
    private String majority_sourced_textile;
    private String retailer_of;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
        this.phone = phone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getMajority_sourced_textile() {
        return majority_sourced_textile;
    }
    public void setMajority_sourced_textile(String majority_sourced_textile) {
        this.majority_sourced_textile = majority_sourced_textile;
    }
    public String getRetailer_of() {
        return retailer_of;
    }
    public void setRetailer_of(String retailer_of) {
        this.retailer_of = retailer_of;
    }
}
