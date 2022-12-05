package reshamandi.knowledgeGraph;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Statistics {
    private String role; //Weaver/Retailer
    private String productSpec; //Type,Category,Weave,Color
    private String filter; //State/Month/Year
    private String[] states; //Andaman and Nicobar Islands, Andhra Pradesh, Arunachal Pradesh , Assam, Bihar, Chandigarh, Chhattisgarh,
                          // Dadra and Nagar Haveli, Daman and Diu, Goa, Gujarat, Haryana, Himachal Pradesh, Jammu and Kashmir,
    //                      Jharkhand, Karnataka, Kerala, Lakshadweep, Madhya Pradesh, Maharashtra, Manipur, Meghalaya, Mizoram,
    //                      Nagaland, National Capital Territory of Delhi, Odisha, Puducherry, Punjab, Rajasthan, Sikkim, Tamil Nadu,
    //                      Telangana, Tripura, Uttar Pradesh, Uttarakhand, West Bengal,

    private String[] seasons; //Summer,Spring,Monsoon,Winter
    private String[] years; //2016,2017,2018,2019,2020

    private String[] month;

    public String[] getMonth(){
        return month;
    }

    public void setMonth(String[] month){
        this.month = month;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    public String[] getSeasons() {
        return seasons;
    }
    public void setSeasons(String[] seasons){
        this.seasons = seasons;
    }

    public String getProductSpec(){ return productSpec; }
    public void setProductSpec(String productSpec){
        this.productSpec = productSpec;
    }

    public String getFilter(){ return filter; }
    public void setFilter(String filter){
        this.filter = filter;
    }

    public String[] getStates(){ return states; }
    public void setStates(String[] states){
        this.states = states;
    }
    public String[] getYears(){ return years; }
    public void setYears(String[] years){
        this.years = years;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }


    private String[][] attr;
    public String[][] getattr() {
        return attr;
    }
    public void setattr(String a[][]) {
        attr = a;
    }

}

