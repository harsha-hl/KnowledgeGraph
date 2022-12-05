package reshamandi.knowledgeGraph;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.engine.AttributeName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

@Controller
public class WebController {
    static final Dotenv dotenv = Dotenv.load();
    static final Neo4j neo = new Neo4j(dotenv.get("NEO4J_URI"), dotenv.get("NEO4j_AUTH_USER"), dotenv.get("NEO4j_AUTH_PASSWORD"));
    static String[] states = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "National Capital Territory of Delhi", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    static String[] years = {"2016", "2017", "2018", "2019", "2020"};
    static String[] seasons = {"Monsoon", "Spring", "Summer", "Winter"};
    static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    type = 12
//    category=16
//    weave=5
    
    //Retailer declarations
    static String[] status2= {"New","Sold"};
    static String[] skuListingStatus= {"Sampled","Purchased"};
    static String[] uom2 = {"Kgs","Meters","Pieces"};
    static String[] businessType= {"ECD","D2R"};

    //Transactions.java implementation
    // static String[] types_list={"Accessories","Art-silk","Bagru","Banarasi","Bhagalpuri","Chanderi-cotton","Cotton-linen","Cotton-tant","Cotton-voile","Tussar-silk","Linnen","Patola"};
    // static String[] cat_list={"AC-Blanket(DOHAR)","Beads","Bedsheet","Bermuda","Shorts","Blouse","Chiffon","Crochet Lace","Fabric","Fusing","Cutting-Roll","Girls-Womens-suit","Saree","Shirt","Skirt","Dupatta"};
    // static String[] weave_list={"ROYAL-OXFORD","Plain","JACQUARD","Yarn Dyed","Satin"};
    static String[] trans_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    // static String[] trans_states = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh ", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "National Capital Territory of Delhi", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    static String[] role_list={"Weaver","Retailer"};
    //This var is used to return all weaver/ retailer id collected in transactions
    String[] ID_list[]={};

    //Products.java implementation
    // static String[] prod_cat_list={"AC-Blanket(DOHAR)","Beads","Bedsheet","Bermuda","Shorts","Blouse","Chiffon","Crochet Lace","Fabric","Fusing","Cutting-Roll","Girls-Womens-suit","Saree","Shirt","Skirt","Dupatta"};
    // static String[] prod_type_list={"Accessories","Art-silk","Bagru","Banarasi","Bhagalpuri","Chanderi-cotton","Cotton-linen","Cotton-tant","Cotton-voile","Tussar-silk","Linnen","Patola"};
    // static String[] prod_weave_list={"ROYAL-OXFORD","Plain","JACQUARD","Yarn Dyed","Satin"};
    static String[] filter_list={"Split","Total"};


    @GetMapping("/patch")
    public String retailerForm(Model model, Weaver w, Retailer ret){
        model.addAttribute("w", new Weaver());
        model.addAttribute("ret", new Retailer());
        model.addAttribute("status",status2);
        model.addAttribute("skuListingStatus",skuListingStatus);
        model.addAttribute("uom",uom2);
        model.addAttribute("formdisp", 1);
        model.addAttribute("business_type",businessType);
         model.addAttribute("w", new Weaver());
        model.addAttribute("l1",status2);
        model.addAttribute("l2",skuListingStatus);
        model.addAttribute("l3",uom2);
        model.addAttribute("l4",businessType);
        neo.getAttributes();
        model.addAttribute("attributes", neo.attributeList);
        return "form";
    }

    // @GetMapping("/patch")
    // public String retailerForm(Model model){
    //     model.addAttribute("ret", new Retailer());
    //     model.addAttribute("status",status2);
    //     model.addAttribute("skuListingStatus",skuListingStatus);
    //     model.addAttribute("uom",uom2);
    //     model.addAttribute("formdisp", 1);
    //     model.addAttribute("business_type",businessType);
    //      model.addAttribute("w", new Weaver());
    //     model.addAttribute("l1",status2);
    //     model.addAttribute("l2",skuListingStatus);
    //     model.addAttribute("l3",uom2);
    //     model.addAttribute("l4",businessType);
    //     String attributes[][] = {{"COLOR", "Red"}, {"Border", "Zigzag", "Floral", "Straight"}};
    //     String filters[] = {"filter[0]","filter[1]"};
    //     model.addAttribute("attributes", attributes);
    //     model.addAttribute("filters", filters);

    //     return "form";
    // }

    @RequestMapping(value= "/retailer",
    method= RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String weaverSubmit(@ModelAttribute Retailer ret, @RequestParam Map<String, String> formData, Model model) {
        List<String> valueList = new ArrayList<String>();
        List<String> keyList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            valueList.add(entry.getValue());
            keyList.add(entry.getKey());
        }
        System.out.println(keyList);
        System.out.println(valueList);
        System.out.println(ret.getretailer_id());
        model.addAttribute("ret", new Retailer());
        model.addAttribute("formdisp", 1);
        model.addAttribute("l1",status2);
        model.addAttribute("l2",skuListingStatus);
        model.addAttribute("l3",uom2);
        model.addAttribute("l4",businessType);
        model.addAttribute("attributes", neo.attributeList);
        neo.patchRetailerTransaction(ret, valueList, keyList);

        return "form";
    
    }
    @GetMapping("/register1")
    public String registernewperson(Model model){
        model.addAttribute("reta", new RetailerReg());
        model.addAttribute("wr", new WeaverReg());
        return "register";
    }
    @GetMapping("/registerret")
    public String registernewperson2(Model model){
        model.addAttribute("reta", new RetailerReg());
        model.addAttribute("wr", new WeaverReg());
        return "register";
    }

    @PostMapping("/registerret")
    public String registrationsubmit1(@ModelAttribute RetailerReg reta, Model model) {
        neo.patchRetailerRegistration(reta);
        model.addAttribute("reta", new RetailerReg());
        System.out.println(reta.getId()+ " " + reta.getName()+ " " + reta.getPhone()+ " " 
                    + reta.getState()+ " " + reta.getMajority_sourced_textile()+ " " + reta.getRetailer_of());
        
        return "register";
    }
    // @PostMapping("/retailer")
    // public String retailerSubmit(@ModelAttribute Retailer ret, Model model){
    //     //neo.patchRetailerTransaction(ret);
        
    //     model.addAttribute("ret", new Retailer());
    //     model.addAttribute("formdisp", 1);
    //     model.addAttribute("l1",status2);
    //     model.addAttribute("l2",skuListingStatus);
    //     model.addAttribute("l3",uom2);
    //     model.addAttribute("l4",businessType);
        
    //     System.out.println(ret.getid());
    //     System.out.println(ret.getcreated_by());
    //     System.out.println(ret.getcreated_date());
    //     System.out.println(ret.getcategory());
    //     System.out.println(ret.getcost_price());
    //     System.out.println(ret.getquantity());
    //     System.out.println(ret.getselling_price());
    //     System.out.println(ret.getstatus());
    //     System.out.println(ret.gettype());
    //     System.out.println(ret.getwarehouseid());
    //     System.out.println(ret.getsold_quantity());
    //     System.out.println(ret.getretailer_id());
    //     System.out.println(ret.getsku_listing_status());
        
    //     System.out.println(ret.getlanding_price());
    //     System.out.println(ret.getgross_amount());
    //     System.out.println(ret.getuom());
    //     System.out.println(ret.getgst_amount());
    //     System.out.println(ret.getgst_percentage());
    //     System.out.println(ret.getdiscount_amount());  
    //     System.out.println(ret.getlogistics_amount());
        
    //     System.out.println(ret.getweave());
        
    //     System.out.println(ret.getcst());
    //     System.out.println(ret.getigst());
    //     System.out.println(ret.gettotal_amount());
    //     System.out.println(ret.gettotal_pre_tax_price());
    //     System.out.println(ret.getsku_count());
    //     System.out.println(ret.getsku_total_quantity());
    //     System.out.println(ret.getstate());
    //     System.out.println(ret.getdiscount());
    //     System.out.println(ret.getbusiness_type());
    //     System.out.println(ret.gettransaction_id());     
        
    //     return "form";
    // }

    @RequestMapping(value = "/weaver",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String weaverSubmit(@ModelAttribute Weaver w, @RequestParam Map<String, String> formData, Model model) {
        List<String> valueList = new ArrayList<String>();
        List<String> keyList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            valueList.add(entry.getValue());
            keyList.add(entry.getKey());
        }
        System.out.println(keyList);
        System.out.println(valueList);
        //System.out.println(neo.attrNames);
        //System.out.println(formData);
        //System.out.println(w.getlanding_price());
        //System.out.println(Arrays.deepToString(w.getfilter()));
        model.addAttribute("w", new Weaver());
        model.addAttribute("formdisp", 1);
        model.addAttribute("l1",status2);
        model.addAttribute("l2",skuListingStatus);
        model.addAttribute("l3",uom2);
        model.addAttribute("l4",businessType);
        model.addAttribute("attributes", neo.attributeList);
        neo.patchWeaverTransaction(w, valueList, keyList);
        
        return "form";
    }
    
    // @RequestMapping(value = "/weaver",
    // method = RequestMethod.POST,
    // consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
    // produces = MediaType.APPLICATION_JSON_VALUE)
    // public String retailerSubmit(@ModelAttribute Weaver w, @RequestParam Map<String, String> formData, Model model) {
    //     List<String> adressList = new ArrayList<String>();
    //     for (Map.Entry<String, String> entry : formData.entrySet()) {
    //         adressList.add(entry.getValue());
    //     }
    //     System.out.println(adressList);
    //     System.out.println(formData);
    //     System.out.println(w.getlanding_price());
    //     System.out.println(Arrays.deepToString(w.getfilter()));
    //     model.addAttribute("w", new Weaver());
    //     model.addAttribute("formdisp", 1);
    //     model.addAttribute("l1",status2);
    //     model.addAttribute("l2",skuListingStatus);
    //     model.addAttribute("l3",uom2);
    //     model.addAttribute("l4",businessType);

    //     return "form";
    // }

    // @PostMapping("/weaver")
    // public String retailerSubmit(@ModelAttribute Weaver w, Model model){
    //     // neo.patchWeaverTransaction(w);
        
    //     model.addAttribute("w", new Weaver());
    //     model.addAttribute("formdisp", 1);
    //     model.addAttribute("l1",status2);
    //     model.addAttribute("l2",skuListingStatus);
    //     model.addAttribute("l3",uom2);
    //     model.addAttribute("l4",businessType);
    //     // System.out.println(w.getid());
    //     // System.out.println(w.getcreated_by());
    //     // System.out.println(w.getcreated_date());
    //     // System.out.println(w.getcategory());
    //     // System.out.println(w.getcost_price());
    //     // System.out.println(w.getquantity());
    //     // System.out.println(w.getselling_price());
    //     // System.out.println(w.getstatus());
    //     // System.out.println(w.gettype());
    //     // System.out.println(w.getwarehouseid());
    //     // System.out.println(w.getsold_quantity());
    //     // System.out.println(w.getweaver_id());
    //     // System.out.println(w.getsku_listing_status());
    //     // System.out.println(w.getdefective_count());
    //     System.out.println(w.getlanding_price());
    //     // System.out.println(w.getgross_amount());
    //     // System.out.println(w.getuom());
    //     // System.out.println(w.getgst_amount());
    //     // System.out.println(w.getgst_percentage());
    //     // System.out.println(w.getdiscount_amount());  
    //     // System.out.println(w.getlogistics_amount());
    //     // System.out.println(w.getreturn_quantity());
    //     // System.out.println(w.getweave());
    //     // System.out.println(w.getreturned_defective_quantity());
    //     // System.out.println(w.getcst());
    //     // System.out.println(w.getigst());
    //     // System.out.println(w.gettotal_amount());
    //     // System.out.println(w.gettotal_pre_tax_price());
    //     // System.out.println(w.getsku_count());
    //     // System.out.println(w.getsku_total_quantity());
    //     // System.out.println(w.getstate());
    //     // System.out.println(w.getdiscount());
    //     // System.out.println(w.getbusiness_type());
    //     // System.out.println(w.gettransaction_id());     
    //     return "form";
    // }

    @RequestMapping(value = "/attribute",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    public String addAttribute(@RequestParam Map<String, String> formData, Model model) {
        List<String> formValues = new ArrayList<String>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            formValues.add(entry.getValue());
        }
        if(formValues.get(1).equals("add")) neo.pushNewAttribute(formValues.get(0));
        else neo.removeAttribute(formValues.get(0));        

        model.addAttribute("w", new Weaver());
        model.addAttribute("formdisp", 1);
        model.addAttribute("l1",status2);
        model.addAttribute("l2",skuListingStatus);
        model.addAttribute("l3",uom2);
        model.addAttribute("l4",businessType);

        return "form";
    }

   @GetMapping("/register")
    public String registrationform(Model model) {
        model.addAttribute("wr", new WeaverReg());
        return "weavreg";
    }

    @PostMapping("/register")
    public String registrationsubmit(@ModelAttribute WeaverReg wr, Model model) {
        neo.patchWeaverRegistration(wr);
        // System.out.println(wr.getname()+ " " + wr.getid());
        model.addAttribute("wr", new WeaverReg());
        return "weavreg";
    }

    @GetMapping("/query")
    public String landing(Model model) {

        neo.getStates();
        neo.getAttributes();
        neo.getTypes();
        neo.getCategory();
        neo.getWeave();
        neo.getYear();

        String productSpecs[] = new String[103],str;
        productSpecs[0] = "Type";
        productSpecs[1] = "Category";
        productSpecs[2] = "Weave";
        int j=3;
        for(int i=0;i<100;i++){
            if(neo.attrNames[i] != null){
                str = neo.attrNames[i].toLowerCase();
                str = str.substring(0, 1).toUpperCase() + str.substring(1);
                productSpecs[j++] = str;}
        }

        // System.out.println("sfgsdfsd------------------");
        // System.out.println(Arrays.deepToString(productSpecs));
        // System.out.println(Arrays.deepToString(neo.attrNames));

        //Statistics part
        model.addAttribute("q12", new Statistics());
        model.addAttribute("states", neo.stateList);
        model.addAttribute("years", neo.yearList);
        model.addAttribute("seasons", seasons);
        model.addAttribute("productSpecs", productSpecs);

        

        //Transactions part
        model.addAttribute("trans", new Transactions());
        model.addAttribute("trans_states", neo.stateList);
        model.addAttribute("trans_months", trans_months);
        model.addAttribute("categories", neo.categoryList);
        model.addAttribute("types", neo.typeList);
        model.addAttribute("weaves", neo.weaveList);
        model.addAttribute("role", role_list);
        model.addAttribute("ID", ID_list);

        //Products part
        model.addAttribute("prods", new Products());
        model.addAttribute("prod_cat", neo.categoryList);
        model.addAttribute("prod_type", neo.typeList);
        model.addAttribute("prod_weave", neo.weaveList);
        model.addAttribute("filter", filter_list);

        String attrs[] = new String[neo.attributeList.length];
        for(int i=0;i<attrs.length;i++)
            attrs[i] = "attr[" + i + "]";
        // System.out.println(Arrays.deepToString(attrs));

        model.addAttribute("attributes", neo.attributeList);
        model.addAttribute("attrs", attrs);

        return "index";
    }

    @PostMapping("/statistics")
    public String statisticsQuery(@ModelAttribute Statistics s, Model model) {

        if (s.getDateTime() == null) {
            s.setDateTime(LocalDateTime.of(2015, 7, 29, 19, 30, 40));
        }

        if (s.getEndDateTime() == null) {
            s.setEndDateTime(LocalDateTime.now());
        }
        
        String res[][] = s.getattr();

        String startTime = String.valueOf(s.getDateTime()).substring(0,10);
        String endTime = String.valueOf(s.getEndDateTime()).substring(0,10);
        // System.out.println(startTime);
        // System.out.println(endTime);
        System.out.println(s.getProductSpec());
        // neo.getAttributes();
        System.out.println(Arrays.deepToString(res));
        System.out.println(Arrays.deepToString(neo.attrNames));
        String data[][];
        try{
            data = neo.newStatistics(s, res, neo.attrNames, startTime, endTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String headData[] = data[0];
        String data1[][] = new String[data.length-1][data[0].length];
        for(int i=1;i<data.length;i++){
            for(int j1=0;j1<data[0].length;j1++)
                data1[i-1][j1] = data[i][j1];
        }
        String param2[] = new String[data[0].length - 1];
        for(int i=1;i<data[0].length;i++) param2[i-1]=data[0][i];
        System.out.println(Arrays.deepToString(data));
        System.out.println(Arrays.deepToString(data1));
        System.out.println(Arrays.deepToString(param2));
        model.addAttribute("data", data);
        model.addAttribute("data1", data1);
        model.addAttribute("headData", headData);
        model.addAttribute("tableDisplay", 1);
        model.addAttribute("chart", 1);
        model.addAttribute("data_row", data.length);
        model.addAttribute("data_col", data[0].length);
        model.addAttribute("param2", param2);

        String productSpecs[] = new String[103],str;
        productSpecs[0] = "Type";
        productSpecs[1] = "Category";
        productSpecs[2] = "Weave";
        int j=3;
        for(int i=0;i<100;i++){
            if(neo.attrNames[i] != null){
                str = neo.attrNames[i].toLowerCase();
                str = str.substring(0, 1).toUpperCase() + str.substring(1);
                productSpecs[j++] = str;}
        }

        //Statistics part
        model.addAttribute("q12", new Statistics());
        model.addAttribute("states", neo.stateList);
        model.addAttribute("years", neo.yearList);
        model.addAttribute("seasons", seasons);
        model.addAttribute("productSpecs", productSpecs);


        //Transactions part
        model.addAttribute("trans", new Transactions());
        model.addAttribute("trans_states", neo.stateList);
        model.addAttribute("trans_months", trans_months);
        model.addAttribute("categories", neo.categoryList);
        model.addAttribute("types", neo.typeList);
        model.addAttribute("weaves", neo.weaveList);
        model.addAttribute("role", role_list);
        model.addAttribute("ID", ID_list);

        //Products part
        model.addAttribute("prods", new Products());
        model.addAttribute("prod_cat", neo.categoryList);
        model.addAttribute("prod_type", neo.typeList);
        model.addAttribute("prod_weave", neo.weaveList);
        model.addAttribute("filter", filter_list);

        String attrs[] = new String[neo.attributeList.length];
        for(int i=0;i<attrs.length;i++)
            attrs[i] = "attr[" + i + "]";
        model.addAttribute("attrs", attrs);
        model.addAttribute("attributes", neo.attributeList);

        return "index";
    }

    // // @PostMapping("/statistics")
    // // public String greetingSubmit(@ModelAttribute Statistics q12, Model model) {

    // //    String data[][] = {{"Type","Blore","Chennai","Hyderabad"},{"Accessore","23","433","134"},{"Saree","334","34","22"}};
    //     String data[][];
    //     try{
    //         data = neo.statistics(q12.getRole(), q12.getProductSpec(), q12.getFilter(), q12.getStates(), q12.getSeasons(), q12.getYears());
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }
    //     String headData[] = data[0];
    //     String data1[][] = new String[data.length-1][data[0].length];
    //     for(int i=1;i<data.length;i++){
    //         for(int j=0;j<data[0].length;j++)
    //             data1[i-1][j] = data[i][j];
    //     }
    //     String param2[] = new String[data[0].length - 1];
    //     for(int i=1;i<data[0].length;i++) param2[i-1]=data[0][i];
    //     System.out.println(Arrays.deepToString(data));
    //     System.out.println(Arrays.deepToString(data1));
    //     System.out.println(Arrays.deepToString(param2));
    //     model.addAttribute("q12", q12);
    //     model.addAttribute("data", data);
    //     model.addAttribute("data1", data1);
    //     model.addAttribute("headData", headData);
    //     model.addAttribute("tableDisplay", 1);
    //     model.addAttribute("chart", 1);
    //     model.addAttribute("data_row", data.length);
    //     model.addAttribute("data_col", data[0].length);
    //     model.addAttribute("param2", param2);
        
    //     //Transactions
    //     model.addAttribute("states", states);
    //     model.addAttribute("years", years);
    //     model.addAttribute("seasons", seasons);

    //     model.addAttribute("trans_states", trans_states);
    //     model.addAttribute("trans_months", trans_months);
    //     model.addAttribute("categories", cat_list);
    //     model.addAttribute("types", types_list);
    //     model.addAttribute("weaves", weave_list);
    //     model.addAttribute("role", role_list);
    //     model.addAttribute("ID", ID_list);

    //     model.addAttribute("prod_cat", prod_cat_list);
    //     model.addAttribute("prod_type", prod_type_list);
    //     model.addAttribute("prod_weave", prod_weave_list);
    //     model.addAttribute("filter", filter_list);
    //     return "index";
    // }

    @PostMapping("/transactions")
    public String greetingTransactionSubmit(@ModelAttribute Transactions trans, Model model){

        String res[][] = trans.getattr();
        System.out.println("neo.attrnames = "+Arrays.deepToString(neo.attrNames));
        String soln[]=null;
        if(res!=null){
         soln = new String[res.length];
        int j=0;
        while(j<res.length){
            soln[j]=neo.attrNames[j];
            j++;
        }
     System.out.println("soln = "+ Arrays.deepToString(soln));
     System.out.println("res = "+ Arrays.deepToString(res));
    }
         
    //Statistics part
    model.addAttribute("q12", new Statistics());
    model.addAttribute("states", neo.stateList);
    model.addAttribute("years", neo.yearList);
    model.addAttribute("seasons", seasons);

    //Transactions part
    model.addAttribute("trans", new Transactions());
    model.addAttribute("trans_states", neo.stateList);
    model.addAttribute("trans_months", trans_months);
    model.addAttribute("categories", neo.categoryList);
    model.addAttribute("types", neo.typeList);
    model.addAttribute("weaves", neo.weaveList);
    model.addAttribute("role", role_list);
    model.addAttribute("ID", ID_list);

    //Products part
    model.addAttribute("prods", new Products());
    model.addAttribute("prod_cat", neo.categoryList);
    model.addAttribute("prod_type", neo.typeList);
    model.addAttribute("prod_weave", neo.weaveList);
    model.addAttribute("filter", filter_list);

       
         model.addAttribute("m", months);
        
        String attrs[] = new String[neo.attributeList.length];
         for(int i=0;i<attrs.length;i++)
             attrs[i] = "attr[" + i + "]";
         model.addAttribute("attrs", attrs);
         model.addAttribute("attributes", neo.attributeList);

        String topTenData[][], topTenData1[][],data[][];
        try{
            data = neo.transactionQuery(trans,res,soln);
            String headData[] = data[0];
            String data1[][] = new String[data.length-1][data[0].length];
            for(int i=1;i<data.length;i++){
                for(int j=0;j<data[0].length;j++)
                    data1[i-1][j] = data[i][j];
            }
            System.out.println(Arrays.deepToString(data));
            model.addAttribute("data1", data1);
            model.addAttribute("headData", headData);
            model.addAttribute("tableDisplay", 1);

            if(trans.getID().length ==0 ){
                model.addAttribute("chart2", 1);
                model.addAttribute("chart3", 1);
                model.addAttribute("chart1", 0);
                }
                else{
                    model.addAttribute("chart2", 0);
                    model.addAttribute("chart3", 0);
                    model.addAttribute("chart1", 1);
                }
            // topTenData = neo.topTenProduct(trans);
        } catch (Exception e) {
            System.out.println(e);
            // throw new RuntimeException(e);
        }

        try{
            if(trans.getID().length ==0 ){
                topTenData=neo.topTenProduct(trans,res,soln);
                topTenData1=neo.topTenWeavers(trans,res,soln);
                //Printing on terminal top10 table
                for (String[] s1 : topTenData) {
                    for (String s2 : s1) {
                        System.out.print(s2 + "   ");
                    }
                    System.out.println("");
                }
                System.out.println("Top 10 Weavers no \n");
                for (String[] s1 : topTenData1) {
                    for (String s2 : s1) {
                        System.out.print(s2 + "   ");
                    }
                    System.out.println("");
                }
                String headData_top10[] = topTenData[0];
                String data10[][] = new String[topTenData.length-1][topTenData[0].length];
                String data10_wr[][] = new String[topTenData1.length-1][topTenData1[0].length];

                for(int i=1;i<topTenData.length;i++){
                    for(int j=0;j<topTenData[0].length;j++){
                        data10[i-1][j] = topTenData[i][j];
                    }
                }
                for(int i=1;i<topTenData1.length;i++){
                    for(int j=0;j<topTenData1[0].length;j++){
                        data10_wr[i-1][j] = topTenData1[i][j];
                    }
                }
                String headData2_top10[] = new String[2];
              
                String lab = new String();
                if(trans.getRole().equals("Weaver")) { lab = "Top Weavers"; headData2_top10[0] = "Weaver ID"; }
                else { headData2_top10[0] = "Retailer ID"; lab= "Top Retailers"; }
                headData2_top10[1] = "Quantity";
                
                System.out.println(Arrays.deepToString(topTenData));
                System.out.println(headData_top10);
                model.addAttribute("data10", data10);
                model.addAttribute("data10_wr", data10_wr);
                model.addAttribute("headData_top10", headData_top10);
                model.addAttribute("headData2_top10", headData2_top10);
                model.addAttribute("lab", lab);  
                model.addAttribute("tableDisplay_top10", 1);   

                for(int i=1;i<topTenData.length;i++){
                        for(int j=0;j<topTenData[0].length;j++){
                            data10[i-1][j] = topTenData[i][j];
                        }
                    }
                
                System.out.println(Arrays.deepToString(topTenData));
                System.out.println(headData_top10);
                model.addAttribute("data10", data10);
                model.addAttribute("headData_top10", headData_top10);
                model.addAttribute("tableDisplay_top10", 1);    
            
             
                
            }else
            model.addAttribute("tableDisplay_top10", 0);
        }
        catch (Exception e){
            System.out.println(e);
        }

        System.out.println(trans.getRole());
        System.out.println(Arrays.deepToString(trans.getID()));
        System.out.println(Arrays.deepToString(trans.getMonth()));
        System.out.println(Arrays.deepToString(trans.getState()));
        System.out.println(Arrays.deepToString(trans.getCategory()));
        System.out.println(Arrays.deepToString(trans.getWeave()));
        System.out.println(Arrays.deepToString(trans.getType()));

        return "index";
        
    }
    // @RequestMapping(value = "/products",
    // method = RequestMethod.POST,
    // consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
    // produces = MediaType.APPLICATION_JSON_VALUE)
    // public String pdt(@ModelAttribute Products prods, @RequestParam Map<String, String> formData, Model model) {
    //     List<String> formValues = new ArrayList<String>();
    //     for (Map.Entry<String, String> entry : formData.entrySet()) {
    //         formValues.add(entry.getKey() + "\t" + entry.getValue());
    //     }
    //     for(String a: formValues)
    //     System.out.println(a);

    //     //Statistics part
    //     model.addAttribute("q12", new Statistics());
    //     model.addAttribute("states", neo.stateList);
    //     model.addAttribute("years", neo.yearList);
    //     model.addAttribute("seasons", seasons);

    //     //Transactions part
    //     model.addAttribute("trans", new Transactions());
    //     model.addAttribute("trans_states", neo.stateList);
    //     model.addAttribute("trans_months", trans_months);
    //     model.addAttribute("categories", neo.categoryList);
    //     model.addAttribute("types", neo.typeList);
    //     model.addAttribute("weaves", neo.weaveList);
    //     model.addAttribute("role", role_list);
    //     model.addAttribute("ID", ID_list);

    //     //Products part
    //     model.addAttribute("prods", new Products());
    //     model.addAttribute("prod_cat", neo.categoryList);
    //     model.addAttribute("prod_type", neo.typeList);
    //     model.addAttribute("prod_weave", neo.weaveList);
    //     model.addAttribute("filter", filter_list);

    //     String attrs[] = new String[neo.attributeList.length];
    //     for(int i=0;i<attrs.length;i++)
    //         attrs[i] = "attr[" + i + "]";
    //     model.addAttribute("attrs", attrs);
    //     model.addAttribute("attributes", neo.attributeList);
    //     return "index";
    // }

    @PostMapping("/products")
    public String productsQuery(@ModelAttribute Products prods, Model model) {
        
        String res[][] = prods.getattr();
        System.out.println("neo.attrnames = "+Arrays.deepToString(neo.attrNames));
        String soln[]=null;
        if(res!=null){
         soln = new String[res.length];
        int j=0;
        while(j<res.length){
            soln[j]=neo.attrNames[j];
            j++;
        }
     System.out.println("soln = "+ Arrays.deepToString(soln));
     System.out.println("res = "+ Arrays.deepToString(res));
    }
    //     //Statistics part
        model.addAttribute("q12", new Statistics());
        model.addAttribute("states", neo.stateList);
        model.addAttribute("years", neo.yearList);
        model.addAttribute("seasons", seasons);

    //     //Transactions part
        model.addAttribute("trans", new Transactions());
        model.addAttribute("trans_states", neo.stateList);
        model.addAttribute("trans_months", trans_months);
        model.addAttribute("categories", neo.categoryList);
        model.addAttribute("types", neo.typeList);
        model.addAttribute("weaves", neo.weaveList);
        model.addAttribute("role", role_list);
        model.addAttribute("ID", ID_list);

    //     //Products part
         model.addAttribute("prods", new Products());
         model.addAttribute("prod_cat", neo.categoryList);
         model.addAttribute("prod_type", neo.typeList);
         model.addAttribute("prod_weave", neo.weaveList);
         model.addAttribute("filter", filter_list);

         String attrs[] = new String[neo.attributeList.length];
         for(int i=0;i<attrs.length;i++)
             attrs[i] = "attr[" + i + "]";
         model.addAttribute("attrs", attrs);
         model.addAttribute("attributes", neo.attributeList);

            String productdata[][];
        try{
            productdata = neo.productStock(prods,soln,res);
            
            String headData_prod[] = productdata[0];
            String datap[][] = new String[productdata.length - 1][productdata[0].length];

            for (int i = 1; i < productdata.length; i++) {
                for (int k = 0; k < productdata[0].length; k++) {
                    datap[i - 1][k] = productdata[i][k];
                }
            }

            model.addAttribute("data1", datap);
            model.addAttribute("headData", headData_prod);
            model.addAttribute("tableDisplay", 1);
            model.addAttribute("chart4", 1);
            
        } catch (Exception e) {
            System.out.println(e);
            
        }
         return "index";
     }


    @RequestMapping(value="/register")
    public String doStuffMethod() {
        System.out.println("Success");
        return "weavereg";
    }

}