package reshamandi.knowledgeGraph;

import java.util.Dictionary;
import java.util.Hashtable;

public class dict{
    private Dictionary<String,Integer> product = new Hashtable<String,Integer>();
    private Dictionary<Integer,String> month = new Hashtable<Integer,String>();
    String[] types_list={"Accessories","Art-silk","Bagru","Banarasi","Bhagalpuri","Chanderi-cotton","Cotton-linen","Cotton-tant","Cotton-voile","Tussar-silk","Linnen","Patola"};
    String[] cat_list={"AC-Blanket(DOHAR)","Beads","Bedsheet","Bermuda","Shorts","Blouse","Chiffon","Crochet Lace","Fabric","Fusing","Cutting-Roll","Girls-Womens-suit","Saree","Shirt","Skirt","Dupatta"};
    String[] month_list={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] weave_list={"ROYAL-OXFORD","Plain","JACQUARD","Yarn Dyed","Satin"};
    private int typeNo;
    private int categoryNo;
    private int weaveNo;

    public dict(){
        this.typeNo=21;
        for(String type:types_list){
            product.put(type,typeNo);
            typeNo++;
        }
        this.categoryNo=41;
        for(String category:cat_list){
            product.put(category,categoryNo);
            categoryNo++;
        }
        weaveNo=61;
        for(String weave:weave_list){
            product.put(weave,weaveNo);
            weaveNo++;
        }
        int i=1;
        for(String m:month_list){
            month.put(i,m);
            i++;
        }
    }

    public String yearName(String dateStamp){
        String[] list = dateStamp.split("-",3);
        return list[0];
    }

    public String pdtid(String type,String Category,String Weave){
        String inputType,inputCategory,inputWeave;
        try{
            inputType = Integer.toString(product.get(type));
        }catch (NullPointerException e){
            product.put(type,typeNo);
            typeNo++;
            inputType = Integer.toString(product.get(type));
        }
        try{
            inputCategory = Integer.toString(product.get(Category));
        }catch (NullPointerException e){
            product.put(Category,categoryNo);
            categoryNo++;
            inputCategory = Integer.toString(product.get(Category));
        }
        try{
            inputWeave = Integer.toString(product.get(Weave));
        }catch (NullPointerException e){
            product.put(Weave,weaveNo);
            weaveNo++;
            inputWeave = Integer.toString(product.get(Weave));
        }
        return inputType+inputCategory+inputWeave;
    }

    public String monthName(String dateStamp){
        String[] list = dateStamp.split("-",3);
        int i = Integer.parseInt(list[1]);
        return month.get(i);
    }

}

// public class month {
//     public static void main(String[] args) {

//         dict d = new dict();
//         System.out.println(d.pdtid("Accessories","AC-Blanket(DOHAR)","Yarn Dyed"));
//         System.out.println(d.monthName("22-07-2018 22:57"));
//         System.out.println("Hello world!");
//     }
// }
