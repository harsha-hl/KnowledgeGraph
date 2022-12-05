package reshamandi.knowledgeGraph;

public class Products {
    private String filter;
    private String[] category; 
    private String[] type;
    private String[] weave;

    //1.filter
    public String getFilter(){
        return filter;
    }
    public void setFilter(String filter){
        this.filter=filter;
    }

    //2.Category
    public String[] getCategory(){
        return category;
    }
    public void setCategory(String[] category){
        this.category=category;
    }

    //3.Type
    public String[] getType(){
        return type;
    }
    public void setType(String[] type){
        this.type=type;
    }

    //4. Weave
    public String[] getWeave(){
        return weave;
    }
    public void setWeave(String[] weave){
        this.weave=weave;
    }
    private String[][] attr;
    public String[][] getattr() {
        return attr;
    }
    public void setattr(String a[][]) {
        attr = a;
    }
    
}
