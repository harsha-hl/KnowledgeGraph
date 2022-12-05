package reshamandi.knowledgeGraph;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import io.github.cdimascio.dotenv.Dotenv;

interface transact{
    public float calculateGST();
    public float calculateAmount();
    public float calculateGross();
}

public class Neo4j implements AutoCloseable {
    dict d = new dict();

    String stateList[];
    String attributeList[][];
    String attrNames[] = new String[100];
    String typeList[];
    String categoryList[];
    String weaveList[];
    String yearList[];
    String othersList[];
    String fin_set_prodid;
    String Prod_Id;
    int count1=0;

    private final Driver driver;

    public Neo4j(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void getStates(){
        String query = "MATCH (n:State)\n"+"RETURN n.states\n"+"ORDER BY n.states";
        // System.out.println(query);
        try (Session session = driver.session()) {
            stateList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[] states = new String[list.size()];
                // System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        states[i++] = s.asString();
                    }
                }
                System.out.println("States Hit");
                return states;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(stateList));
    }

    public void getYear(){
        String query = "MATCH (n:Year)\n"+"RETURN n.year\n"+"ORDER BY n.year";
        // System.out.println(query);
        try (Session session = driver.session()) {
            yearList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[] years = new String[list.size()];
                // System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        if(s!=null)
                            years[i++] = s.asString();
                    }
                }
                System.out.println("Year Hit");
                return years;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(yearList));
    }

    public void getCategory(){
        String query = "MATCH (n:Category)\n"+"RETURN n.category\n"+"ORDER BY n.category";
        // System.out.println(query);
        try (Session session = driver.session()) {
            categoryList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[] categories = new String[list.size()];
                // System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        if(s!=null)
                            categories[i++] = s.asString();
                    }
                }
                System.out.println("Category Hit");
                return categories;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(categoryList));
    }

    public void getWeave(){
        String query = "MATCH (n:Weave)\n"+"RETURN n.weave\n"+"ORDER BY n.weave";
        // System.out.println(query);
        try (Session session = driver.session()) {
            weaveList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[] weaves = new String[list.size()];
                // System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        if(s!=null)
                            weaves[i++] = s.asString();
                    }
                }
                System.out.println("Weave Hit");
                return weaves;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(weaveList));
    }

    public void getTypes(){
        String query = "MATCH (n:Type)\n"+"RETURN n.type\n"+"ORDER BY n.type";
        // System.out.println(query);
        try (Session session = driver.session()) {
            typeList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[] types = new String[list.size()];
                // System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        if(s!=null)
                        types[i++] = s.asString();
                    }
                }
                System.out.println("Types Hit");
                return types;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(typeList));
    }

    public String monthFromSeason(int season) {
        switch (season) {
            case 0:
                return ("r.month='April' or r.month='May' or r.month='June' or ");
            case 1:
                return ("r.month='February' or r.month='March' or ");
            case 2:
                return ("r.month='July' or r.month='August' or r.month='September' or ");
            default:
                return ("r.month='October' or r.month='November' or r.month='December' or r.month='January' or ");
        }
    }

    public String monthFromSeason(String season) {
        switch (season) {
            case "Summer":
                return ("r.month='April' or r.month='May' or r.month='June' or ");
            case "Spring":
                return ("r.month='February' or r.month='March' or ");
            case "Monsoon":
                return ("r.month='July' or r.month='August' or r.month='September' or ");
            default:
                return ("r.month='October' or r.month='November' or r.month='December' or r.month='January' or ");
        }
    }

    public String[] seasonToMonth(String[] seasons) {
        ArrayList<String> months = new ArrayList<String>();
        for (int i = 0; i < seasons.length; i++) {
            switch (seasons[i]) {
                case "Summer":
                    months.add("April");
                    months.add("May");
                    months.add("June");
                    break;
                case "Spring":
                    months.add("February");
                    months.add("March");
                    break;
                case "Monsoon":
                    months.add("July");
                    months.add("August");
                    months.add("September");
                    break;
                default:
                    months.add("October");
                    months.add("November");
                    months.add("December");
                    months.add("January");
                    break;
            }
        }
        String[] m = new String[0];
        m = months.toArray(m);
        return m;
    }

    public int columnsFromSeasons(String[] seasons) {
        int sum = 0;
        for (int i = 0; i < seasons.length; i++) {
            switch (seasons[i]) {
                case "Summer":
                    sum += 3;
                    break;
                case "Spring":
                    sum += 2;
                    break;
                case "Monsoon":
                    sum += 3;
                    break;
                default:
                    sum += 4;
                    break;
            }
        }
        return sum;
    }

    public static int findIndex(String arr[], String t) {
        int len = arr.length;
        int i = 0;
        while (i < len) {
            if (arr[i].equals(t)) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    public <T> List<T> twoDArrayToList(T[][] twoDArray) {
        List<T> list = new ArrayList<T>();
        for (T[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }


    public String[][] statistics(String role, String productSpec, String filter, String[] states, String[] seasons,
            String[] years) {
        if (productSpec == null)
            productSpec = "pdt.type";
        if (filter == null)
            filter = "r.month";
        System.out.println(states);
        System.out.println("Statessssssś");
        String[][] tableData1;
        try (Session session = driver.session()) {
            String qrole = "MATCH (m:Month) WITH collect(m) as monthList UNWIND monthList as months MATCH (months)-[r:"
                    +
                    role + "]->(pdt:Product) ";
            boolean flag = false;
            int i;
            StringBuilder qstates = new StringBuilder("");
            StringBuilder qseasons = new StringBuilder("");
            StringBuilder qyears = new StringBuilder("");

            if (states != null) {
                flag = true;
                for (i = 0; i < (states.length) - 1; i++)
                    qstates.append("r.state='").append(states[i]).append("' or ");
                qstates.append("r.state='").append(states[i]).append("') ");
                qstates = new StringBuilder("(" + qstates);
            }

            if (seasons != null) {
                if (flag)
                    qseasons = new StringBuilder("and (");
                else
                    qseasons = new StringBuilder("(");
                flag = true;
                for (i = 0; i < (seasons.length) - 1; i++) {
                    qseasons.append(monthFromSeason(seasons[i]));
                }
                qseasons.append(monthFromSeason(seasons[i]));
                qseasons = new StringBuilder(qseasons.substring(0, qseasons.length() - 4));
                qseasons.append(") ");
            }

            if (years != null) {
                if (flag)
                    qyears = new StringBuilder("and (");
                else
                    qyears = new StringBuilder("(");
                flag = true;
                for (i = 0; i < (years.length) - 1; i++)
                    qyears.append("left(r.created_date,4)='").append(years[i]).append("' or ");
                qyears.append("left(r.created_date,4)='").append(years[i]).append("') ");
            }
            String wquery = qrole;
            if (flag)
                wquery += "WHERE " + qstates + qseasons + qyears;
            wquery += "WITH pdt,collect(";
            if (filter.equals("r.created_date"))
                wquery += "left(r.created_date,4)) ";
            else
                wquery += (filter + ") ");
            wquery += "AS outputList UNWIND outputList AS filter2  WITH filter2,collect(";
            String query = wquery + productSpec
                    + ") AS filterList UNWIND filterList AS filter1 RETURN filter1,filter2,count(filter1) ORDER by filter1, filter2";
            System.out.println(query);

            String finalProductSpec = productSpec;
            String finalFilter = filter;
            tableData1 = session.writeTransaction(tx -> {
                Result result = tx.run(query);
                List<Record> list = new ArrayList<Record>(result.list());
                int rows, columns, f = 1;
                rows = finalProductSpec.equals("pdt.type") ? 12 : (finalProductSpec.equals("pdt.category") ? 16 : 5);
                switch (finalFilter) {
                    case "r.state":
                        columns = (states != null) ? states.length : WebController.states.length;
                        f = 0;
                        break;
                    case "r.month":
                        columns = (seasons != null) ? columnsFromSeasons(seasons) : WebController.months.length;
                        f = 1;
                        break;
                    default:
                        columns = (years != null) ? years.length : 5;
                        f = 2;
                        break;
                }
                String[][] tableData = new String[rows + 1][columns + 1];
                String months[] = {};
                if (seasons != null)
                    months = seasonToMonth(seasons);
                switch (f) {
                    case 0:
                        for (int k = 1; k <= columns; k++)
                            tableData[0][k] = (states != null) ? states[k - 1] : WebController.states[k - 1];
                        break;
                    case 1:
                        for (int k = 1; k <= columns; k++)
                            tableData[0][k] = (seasons != null) ? months[k - 1] : WebController.months[k - 1];
                        break;
                    default:
                        for (int k = 1; k <= columns; k++)
                            tableData[0][k] = (years != null) ? years[k - 1] : WebController.years[k - 1];
                }

                tableData[0][0] = finalProductSpec.substring(4);
                int rowIndex = 1;
                for (int j = 0; j < list.size() && rowIndex <= rows; j++, rowIndex++) {
                    // System.out.println("lol");
                    tableData[rowIndex][0] = list.get(j).get("filter1").asString();
                    while (j < list.size() - 1 && list.get(j).get("filter1").asString().equals(list.get(j + 1).get("filter1").asString())) {
                        if(findIndex(tableData[0], list.get(j).get("filter2").asString()) == -1)System.out.println("x");
                        else tableData[rowIndex][findIndex(tableData[0], list.get(j).get("filter2").asString())] = String
                            .valueOf(list.get(j).get("count(filter1)"));
                        j++;
                    }
                    tableData[rowIndex][findIndex(tableData[0], list.get(j).get("filter2").asString())] = String
                            .valueOf(list.get(j).get("count(filter1)"));
                    for (int l = 1; l <= columns; l++) {
                        if (tableData[rowIndex][l] == null)
                            tableData[rowIndex][l] = "0";
                    }
                }

                List<String> x = twoDArrayToList(tableData);
                x.removeAll(Collections.singleton(null));
                for(int j=0;j<x.size();j++)
                    if(x.get(j) == null)x.remove(j);
                String ansTable[][] = new String[x.size()/(columns+1)][columns+1];
                int j = 0;
                for(int k=0;k<ansTable.length;k++)
                    for(int l=0;l<ansTable[0].length;l++)
                        ansTable[k][l] = x.get(j++);

                return ansTable;
            });
        }
        return tableData1;
    }

    public static void main(String... args) {
    Transactions t = new Transactions();
    // String ids[] = {"12598"};
    t.setRole("Retailer");
    // t.setID(ids);
    String months[] = { "January", "March" };
    String state[] = { "Karnataka", "Goa" };
    String type[] = null;
    String cat[] = { "Beads", "Saree" };
    String weave[] = { "Satin", "Plain", "JACQUARD" };
    t.setMonth(months);
    t.setState(state);
    t.setCategory(cat);
    t.setType(type);
    t.setWeave(weave);
    Dotenv dotenv = Dotenv.load();
    Neo4j neo = new Neo4j(dotenv.get("NEO4J_URI"), dotenv.get("NEO4j_AUTH_USER"),
    dotenv.get("NEO4j_AUTH_PASSWORD"));
    //System.out.println(Arrays.deepToString(neo.transactionQuery(t)[1]));
    // System.out.println(neo.transactionQuery(t)[0]);
    //System.out.println(neo.transactionQuery(t)[1][2]);

    System.out.println("hello");
    return;
    }

    public String[][] transactionQuery(Transactions t,String[][] res, String[] soln) {
        String query = "";
        int i;
        boolean flag = false;

        if (t.getID().length != 0) {
            String ids[] = t.getID();
            StringBuilder qids = new StringBuilder("WHERE (");
            for (i = 0; i < (ids.length) - 1; i++)
                qids.append("n.id='").append(ids[i]).append("' or ");
            qids.append("n.id='").append(ids[i]).append("') WITH r\n");
            query += qids;
        }
        StringBuilder qstates = new StringBuilder("");
        StringBuilder qmonths = new StringBuilder("");
        StringBuilder qtypes = new StringBuilder("");
        StringBuilder qweaves = new StringBuilder("");
        StringBuilder qcategories = new StringBuilder("");
        if (t.getState() != null) {
            flag = true;
            qstates = new StringBuilder("(");
            String states[] = t.getState();
            for (i = 0; i < (states.length) - 1; i++)
                qstates.append("r.state='").append(states[i]).append("' or ");
            qstates.append("r.state='").append(states[i]).append("') ");
        }
        if (t.getMonth() != null) {
            String months[] = t.getMonth();
            if (flag)
                qmonths = new StringBuilder("and (");
            else
                qmonths = new StringBuilder("(");
            flag = true;
            for (i = 0; i < (months.length) - 1; i++)
                qmonths.append("r.month='").append(months[i]).append("' or ");
            qmonths.append("r.month='").append(months[i]).append("') ");
        }
        if (t.getType() != null) {
            String types[] = t.getType();
            if (flag)
                qtypes = new StringBuilder("and (");
            else
                qtypes = new StringBuilder("(");
            flag = true;
            for (i = 0; i < (types.length) - 1; i++)
                qtypes.append("r.type='").append(types[i]).append("' or ");
            qtypes.append("r.type='").append(types[i]).append("') ");
        }
        if (t.getWeave() != null) {
            String weaves[] = t.getWeave();
            if (flag)
                qweaves = new StringBuilder("and (");
            else
                qweaves = new StringBuilder("(");
            flag = true;
            for (i = 0; i < (weaves.length) - 1; i++)
                qweaves.append("r.weave='").append(weaves[i]).append("' or ");
            qweaves.append("r.weave='").append(weaves[i]).append("') ");
        }
        if (t.getCategory() != null) {
            String categories[] = t.getCategory();
            if (flag)
                qcategories = new StringBuilder("and (");
            else
                qcategories = new StringBuilder("(");
            flag = true;
            for (i = 0; i < (categories.length) - 1; i++)
                qcategories.append("r.category='").append(categories[i]).append("' or ");
            qcategories.append("r.category='").append(categories[i]).append("') ");
        }

        if (flag)
            query += "WHERE " + qstates + qmonths + qtypes + qweaves + qcategories + "\n";
            int k=0;
            if(res!=null){
                for(String st:soln){
                    if(res[k]!=null && res[k].length!=0){
                        if (flag)
                        query +="and (";
            else
                        query +="WHERE (";
                    flag = true;
    
                        for (i = 0; i < res[k].length-1; i++)
                        query +="r."+soln[k].toLowerCase()+"='"+res[k][i]+"' or ";
                        query +="r."+soln[k].toLowerCase()+"='"+res[k][i]+"' ) ";
                }k++;
                    }    
                } 

        if (t.getRole().equals("Weaver"))
            query = "MATCH (n:Weaver)-[r:soldBy]->(w:Product)\n" + query
                    + "RETURN r.transaction_id AS Transaction_ID,r.weaver_id AS Weaver_ID,r.quantity AS Quantity,r.state AS State,r.month AS Month,left(r.created_date,4) AS Year,r.cost_price AS Cost_Price,r.pdt_id AS Product_ID,r.type AS Type,r.category AS Category,r.weave AS Weave";

        else
            query = "MATCH (w:Product)-[r:boughtBy]->(n:Retailer)\n" + query
                    + "RETURN r.transaction_id AS Transaction_ID,r.retailer_id AS Retailer_ID,r.quantity AS Quantity,r.state AS State,r.month AS Month,left(r.created_date,4) AS Year,r.cost_price AS Cost_Price,r.pdt_id AS Product_ID,r.type AS Type,r.category AS Category,r.weave AS Weave";

        System.out.println(query);
        String finalQuery = query;
        String[][] answer = {};
        try (Session session = driver.session()) {
            answer = session.writeTransaction(tx -> {
                Result result = tx.run(finalQuery);
                List<Record> list = new ArrayList<Record>(result.list());
                String queryAnswer[][] = new String[list.size() + 1][11];
                queryAnswer[0] = list.get(0).keys().toArray(new String[0]);
                for (int j = 0; j < 11; j++)
                    queryAnswer[0][j] = queryAnswer[0][j].replace('_', ' ');
               for (int kt = 0; kt < list.size(); kt++)
                    for (int j = 0; j < 11; j++)
                    queryAnswer[kt + 1][j] = String.valueOf(list.get(kt).get(j)).replaceAll("^\"|\"$", ""); 
                return queryAnswer;
            });
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error");
        }
        // System.out.println(Arrays.deepToString(answer));
        return answer;
    }

    ////////////////////////// PATCHING
    ////////////////////////// /////////////////////////////////////////////

    public void patchWeaverRegistration(WeaverReg w) {
        String weaverNode = "MERGE (w:Weaver{phone:'" + w.getphone() + "',name:'" + w.getname() + "',yarn_cocoon_type:'"
                + w.getyarn_cocoon_type() + "',state:'" + w.getstate() + "',id:'" + w.getid() + "',yarn_capacity:'"
                + w.getyarn_capacity() + "',type:'" + w.gettype() + "',twisted_type:'" + w.gettwisted_type()
                + "',denier:'" + w.getdenier() + "'})";
        System.out.println(weaverNode);
        
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(weaverNode);
                System.out.println("Hit");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void patchRetailerRegistration(RetailerReg r) {
        String retailerNode = "MERGE (r:Retailer{phone:'" + r.getPhone() + "',name:'" + r.getName()
                + "',majority_sourced_textile:'" + r.getMajority_sourced_textile() + "',state:'" + r.getState()
                + "',id:'" + r.getId() + "',retailer_of:'" + r.getRetailer_of() + "'})";
        System.out.println(retailerNode);
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(retailerNode);
                System.out.println("New retailer registered successfully");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    public void patchWeaverTransaction (Weaver w, List<String> adressList, List<String> keyList) {
        
        String str_attr="";
        String get_prodid="Match (p:ProductNumber) Return p.currentId;";
        String incr_prodid="Match (p:ProductNumber) set p.currentId = p.currentId + 1;";
        getAttributes();
        ArrayList<String> attr_names= new ArrayList<String>();
        for (String i : attrNames) {
            if(i!=null)
                attr_names.add(i);
        }
        //creting product node (optional)
        //match (p:Product) where p.type="aaa" and p.category="bbb" and p.weave="ccc" and p.color="blue" and p.border="zigzag" return p;
        //merge (p:Product {type:"aaa", category:"bbb", weave:"ccc", color:"blue", border:"zigzag", pdtId:15000}) return p;
        String productExists="Match (p:Product) where p.type='"+w.gettype()+"' and p.weave='"+w.getweave()+"' and p.category='"+
                w.getcategory()+"'";
        String set_prod_id1="Merge (p:Product {type:'"+w.gettype()+"', category:'"+w.getcategory()+"', weave:'"+w.getweave()+"'";

        int index=-1;
        String attr_entry, key_entry;
        for (String j : attr_names) {
            index=keyList.indexOf(j);
            if(index>=0){
                attr_entry=adressList.get(index);
                if(!attr_entry.isEmpty()){
                    key_entry=keyList.get(index).toLowerCase();
                    str_attr+=" ,"+key_entry+": '" +attr_entry+"'";
                    productExists+=" and p."+key_entry+"='"+attr_entry+"'";
                    set_prod_id1+=" , "+key_entry+":'"+attr_entry+"'";
                    //System.out.println(str_attr);
                }
            }
        }
        

        //creating attr nodes
        //merge (c:Color {color:"White"});
        //Match (n:Attributes) where NOT any(x IN n.color WHERE x IN ['White']) set n.color = n.color + 'White' return n
        //Match (b:Border) where b.border = "zigzag" MATCH (p:Product) where p.type=...,p.category=...,p.weave=...., p.color=... Merge (b) - [r:borderName]->(p)
        
        ArrayList<String> create_attr_node= new ArrayList<String>();
        ArrayList<String> update_attr_tab= new ArrayList<String>();
        ArrayList<String> create_attr_rel= new ArrayList<String>();

        for (String j : attr_names) {
            index=keyList.indexOf(j);
            if(index>=0){
                attr_entry=adressList.get(index);
                if(!attr_entry.isEmpty()){
                    key_entry=keyList.get(index).toLowerCase();
                    create_attr_node.add("Merge (a:"+key_entry.substring(0,1).toUpperCase() + key_entry.substring(1).toLowerCase()
                    +" {"+key_entry+":'"+attr_entry+"'});");
                    update_attr_tab.add("Match (a:Attributes) where NOT any(x IN a."+key_entry+" WHERE x IN ['"
                        +attr_entry+"']) SET a."+key_entry+"= a."+key_entry+" + '"+attr_entry+"' Return a;");
                    create_attr_rel.add("Match (a:"+key_entry.substring(0,1).toUpperCase() + key_entry.substring(1).toLowerCase()+") where a."
                    +key_entry+"= '"+attr_entry+"' "+productExists
                         +" Merge (a) - [r:"+key_entry+"Name {"+key_entry+":'"+attr_entry+"'}] -> (p);");
                }
            }
        }

        // System.out.println(create_attr_node);
        // System.out.println(create_attr_rel);
        // System.out.println(update_attr_tab);
        for (String i : create_attr_node) {
            System.out.println(i);
        }
        for (String i : create_attr_rel) {
            System.out.println(i);
        }
        for (String i : update_attr_tab) {
            System.out.println(i);
        }
       
        
        final String set_prod_id=set_prod_id1;
        final String fin_prod_exst=productExists+" Return p;";
        final String fin_get_prod_id=productExists+" Return p.pdtId;";
        final String fin_str_attr=str_attr;


        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                
                final int fin_prod_id=0;
                
                //System.out.println(fin_get_prod_id);
                Result result = tx.run(fin_get_prod_id);
                List<Record> id_list = new ArrayList<Record>(result.list());
                if(id_list.isEmpty())
                {
                    //System.out.println(get_prodid);
                    //System.out.println(incr_prodid);
                    Result res_prod_id=tx.run(get_prodid);
                    List<Record> prod_id_lis = new ArrayList<Record>(res_prod_id.list());
                    for(Record r: prod_id_lis){
                        //System.out.println(r.values().get(0));
                        fin_set_prodid=set_prod_id+" , pdtId:"+r.values().get(0)+"}) Return p;";
                        Prod_Id=String.valueOf(r.values().get(0));
                        System.out.println(Prod_Id);
                        System.out.println("================================================================");
                        System.out.println(fin_set_prodid);
                    }
                    tx.run(incr_prodid);
                }
                else{
                    for(Record r: id_list){
                        fin_set_prodid=set_prod_id+" , pdtId:"+r.values().get(0)+"}) Return p;";
                        Prod_Id=String.valueOf(r.values().get(0));
                        System.out.println(Prod_Id);
                        System.out.println("================================================================");
                        System.out.println(fin_set_prodid);
                    }
                }
                
                //System.out.println(fin_prod_exst);
                result = tx.run(fin_prod_exst);
                List<Record> list = new ArrayList<Record>(result.list());
                if(list.isEmpty())
                {
                    Result res_prod =tx.run(fin_set_prodid);
                    List<Record> res_prod_lis = new ArrayList<Record>(res_prod.list());
                    //System.out.println(res_prod_lis);

                }
                else{
                    System.out.println("product exists!");
                    
                }

                //creating attributes
                for (String i : create_attr_node) {
                    int index1=-1;
                    index1=create_attr_node.indexOf(i);
                    tx.run(i);
                    tx.run(create_attr_rel.get(index1));
                    tx.run(update_attr_tab.get(index1));
                }

                // creating new nodes
                String nodes = "MERGE (p1:Product{pdtId:" + Prod_Id + ",type:'"
                        + w.gettype() + "' , weave: '" + w.getweave() + "' , category:'" + w.getcategory() + "'})\n" +
                        "MERGE (t:Type{type:'" + w.gettype() + "'})\n" +
                        "MERGE (t1:Category{category:'" + w.getcategory() + "'})\n" +
                        "MERGE (t2:Weave{weave:'" + w.getweave() + "'})\n" +
                        "MERGE (m:Month{month : '" + d.monthName(w.getcreated_date()) + "'})\n" +
                        "MERGE (s:State{state : '" + w.getstate() + "'})\n"+
                        "MERGE (y:Year{year : '" + d.yearName(w.getcreated_date()) + "'})";
                System.out.println(nodes);

                 // soldbyRelation
                String soldByRelation = "Match (w:Weaver) WHERE w.id = '" + w.getweaver_id() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId ="+Prod_Id+"\nMERGE (w)-[rl:soldBy{selling_price:'" + w.getselling_price() + "',return_quantity:'"
                        + w.getreturn_quantity() + "',cst:'" + w.getcst() + "',discount_amount:'" + w.getdiscount_amount()
                        + "',discount:'" + w.getdiscount() + "',weaver_id:'" + w.getweaver_id() + "',gross_amount:'"
                        + w.getgross_amount() + "',type:'" + w.gettype() + "',sold_quantity:'" + w.getsold_quantity()
                        + "',igst:'" + w.getigst() + "',uom:'" + w.getuom() + "',gst_amount:'" + w.getgst_amount()
                        + "',sku_listing_status:'" + w.getsku_listing_status() + "',warehouseid:'" + w.getwarehouseid()
                        + "',sku_total_quantity:'" + w.getsku_total_quantity() + "',business_type:'" + w.getbusiness_type()
                        + "',returned_defective_quantity:'" + w.getreturned_defective_quantity() + "',state:'" + w.getstate()
                        + "',id:'" + w.getid() + "',landing_price:'" + w.getlanding_price() + "',cost_price:'"
                        + w.getcost_price() + "',transaction_id:'" + w.gettransaction_id() + "',quantity:'" + w.getquantity()
                        + "',total_pre_tax_price:'" + w.gettotal_pre_tax_price() + "',pdt_id:"+Prod_Id+",created_by:'" + w.getcreated_by()
                        + "',gst_percentage:'" + w.getgst_percentage() + "',month:'" + d.monthName(w.getcreated_date())
                        + "',total_amount:'" + w.gettotal_amount() + "',sku_count:'" + w.getsku_count() + "',logistics_amount:'"
                        + w.getlogistics_amount() + "',defective_count:'" + w.getdefective_count() + "',created_date:'"
                        + w.getcreated_date() + "',category:'" + w.getcategory() + "',weave:'" + w.getweave() + "',status:'"
                        + w.getstatus() + "'"+ fin_str_attr+"}]->(p)";

                // linking type to product
                String linkTypeToProduct = "MATCH (t:Type) WHERE t.type ='" + w.gettype() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:typeName{type : '" + w.gettype() + "'}]->(p)";
                System.out.println(linkTypeToProduct);

                // linking category to product
                String linkCategoryToProduct = "MATCH (t:Category) WHERE t.category ='" + w.getcategory() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:categoryName{category : '" + w.getcategory() + "'}]->(p)";
                System.out.println(linkCategoryToProduct);

                // linking weave to product
                String linkWeaveToProduct = "MATCH (t:Weave) WHERE t.weave ='" + w.getweave() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:weaveName{weave : '" + w.getweave() + "'}]->(p)";
                System.out.println(linkWeaveToProduct);

                // linking - soldMonth
                String soldMonth = "MATCH (m:Month) WHERE m.month = '" + d.monthName(w.getcreated_date()) + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (m)-[r:soldMonth{transaction_id:'" + w.gettransaction_id() + "',month:'"
                        + d.monthName(w.getcreated_date()) + "',weaver_id:'" + w.getweaver_id() + "',state:'" + w.getstate()
                        + "',created_date:'" + w.getcreated_date() + "',pdt_id:"
                        + Prod_Id + "}]->(p)";
                System.out.println(soldMonth);

                // linking - soldState
                String soldState = "MATCH (m:State) WHERE m.state = '" + w.getstate() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (m)-[r:soldState{transaction_id:'" + w.gettransaction_id() + "',month:'"
                        + d.monthName(w.getcreated_date()) + "',weaver_id:'" + w.getweaver_id() + "',state:'" + w.getstate()
                        + "',created_date:'" + w.getcreated_date() + "',pdt_id:"
                        + Prod_Id + "}]->(p)";
                System.out.println(soldState);

                tx.run(nodes);
                tx.run(soldByRelation);
                tx.run(linkTypeToProduct);
                tx.run(linkCategoryToProduct);
                tx.run(linkWeaveToProduct);
                tx.run(soldMonth);
                tx.run(soldState);
                System.out.println(soldByRelation);
                System.out.println("Hit");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        
    }

    public void patchRetailerTransaction(Retailer r, List<String> adressList, List<String> keyList) {
        String str_attr="";
        String get_prodid="Match (p:ProductNumber) Return p.currentId;";
        String incr_prodid="Match (p:ProductNumber) set p.currentId = p.currentId + 1;";
        getAttributes();
        ArrayList<String> attr_names= new ArrayList<String>();
        for (String i : attrNames) {
            if(i!=null)
                attr_names.add(i);
        }
        //creting product node (optional)
        //match (p:Product) where p.type="aaa" and p.category="bbb" and p.weave="ccc" and p.color="blue" and p.border="zigzag" return p;
        //merge (p:Product {type:"aaa", category:"bbb", weave:"ccc", color:"blue", border:"zigzag", pdtId:15000}) return p;
        String productExists="Match (p:Product) where p.type='"+r.gettype()+"' and p.weave='"+r.getweave()+"' and p.category='"+
                r.getcategory()+"'";
        String set_prod_id1="Merge (p:Product {type:'"+r.gettype()+"', category:'"+r.getcategory()+"', weave:'"+r.getweave()+"'";

        int index=-1;
        String attr_entry, key_entry;
        for (String j : attr_names) {
            index=keyList.indexOf(j);
            if(index>=0){
                attr_entry=adressList.get(index);
                if(!attr_entry.isEmpty()){
                    key_entry=keyList.get(index).toLowerCase();
                    str_attr+=" ,"+key_entry+": '" +attr_entry+"'";
                    productExists+=" and p."+key_entry+"='"+attr_entry+"'";
                    set_prod_id1+=" , "+key_entry+":'"+attr_entry+"'";
                    //System.out.println(str_attr);
                }
            }
        }
        

        //creating attr nodes
        //merge (c:Color {color:"White"});
        //Match (n:Attributes) where NOT any(x IN n.color WHERE x IN ['White']) set n.color = n.color + 'White' return n
        //Match (b:Border) where b.border = "zigzag" MATCH (p:Product) where p.type=...,p.category=...,p.weave=...., p.color=... Merge (b) - [r:borderName]->(p)
        
        ArrayList<String> create_attr_node= new ArrayList<String>();
        ArrayList<String> update_attr_tab= new ArrayList<String>();
        ArrayList<String> create_attr_rel= new ArrayList<String>();

        for (String j : attr_names) {
            index=keyList.indexOf(j);
            if(index>=0){
                attr_entry=adressList.get(index);
                if(!attr_entry.isEmpty()){
                    key_entry=keyList.get(index).toLowerCase();
                    create_attr_node.add("Merge (a:"+key_entry.substring(0,1).toUpperCase() + key_entry.substring(1).toLowerCase()
                    +" {"+key_entry+":'"+attr_entry+"'});");
                    update_attr_tab.add("Match (a:Attributes) where NOT any(x IN a."+key_entry+" WHERE x IN ['"
                        +attr_entry+"']) SET a."+key_entry+"= a."+key_entry+" + '"+attr_entry+"' Return a;");
                    create_attr_rel.add("Match (a:"+key_entry.substring(0,1).toUpperCase() + key_entry.substring(1).toLowerCase()+") where a."
                        +key_entry+"= '"+attr_entry+"' "+productExists
                             +" Merge (a) - [r:"+key_entry+"Name {"+key_entry+":'"+attr_entry+"'}] -> (p);");
                }
            }
        }

        // System.out.println(create_attr_node);
        // System.out.println(create_attr_rel);
        // System.out.println(update_attr_tab);
        for (String i : create_attr_node) {
            System.out.println(i);
        }
        for (String i : create_attr_rel) {
            System.out.println(i);
        }
        for (String i : update_attr_tab) {
            System.out.println(i);
        }
       
        
        final String set_prod_id=set_prod_id1;
        final String fin_prod_exst=productExists+" Return p;";
        final String fin_get_prod_id=productExists+" Return p.pdtId;";
        final String fin_str_attr=str_attr;



        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {

                final int fin_prod_id=0;
                
                //System.out.println(fin_get_prod_id);
                Result result = tx.run(fin_get_prod_id);
                List<Record> id_list = new ArrayList<Record>(result.list());
                if(id_list.isEmpty())
                {
                    //System.out.println(get_prodid);
                    //System.out.println(incr_prodid);
                    Result res_prod_id=tx.run(get_prodid);
                    List<Record> prod_id_lis = new ArrayList<Record>(res_prod_id.list());
                    for(Record r1: prod_id_lis){
                        //System.out.println(r.values().get(0));
                        fin_set_prodid=set_prod_id+" , pdtId:"+r1.values().get(0)+"}) Return p;";
                        Prod_Id=String.valueOf(r1.values().get(0));
                        System.out.println(Prod_Id);
                        System.out.println("================================================================");
                        System.out.println(fin_set_prodid);
                    }
                    tx.run(incr_prodid);
                }
                else{
                    for(Record r1: id_list){
                        fin_set_prodid=set_prod_id+" , pdtId:"+r1.values().get(0)+"}) Return p;";
                        Prod_Id=String.valueOf(r1.values().get(0));
                        System.out.println(Prod_Id);
                        System.out.println("================================================================");
                        System.out.println(fin_set_prodid);
                    }
                }
                
                //System.out.println(fin_prod_exst);
                result = tx.run(fin_prod_exst);
                List<Record> list = new ArrayList<Record>(result.list());
                if(list.isEmpty())
                {
                    Result res_prod =tx.run(fin_set_prodid);
                    List<Record> res_prod_lis = new ArrayList<Record>(res_prod.list());
                    //System.out.println(res_prod_lis);

                }
                else{
                    System.out.println("product exists!");
                    
                }

                //creating attributes
                for (String i : create_attr_node) {
                    int index1=-1;
                    index1=create_attr_node.indexOf(i);
                    tx.run(i);
                    tx.run(create_attr_rel.get(index1));
                    tx.run(update_attr_tab.get(index1));
                }

                        
                // creating new nodes
                String nodes = "MERGE (p1:Product{pdtId:" + Prod_Id + ",type:'"
                        + r.gettype() + "' , weave: '" + r.getweave() + "' , category:'" + r.getcategory() + "'})\n" +
                        "MERGE (t:Type{type:'" + r.gettype() + "'})\n" +
                        "MERGE (t1:Category{category:'" + r.getcategory() + "'})\n" +
                        "MERGE (t2:Weave{weave:'" + r.getweave() + "'})\n" +
                        "MERGE (m:Month{month : '" + d.monthName(r.getcreated_date()) + "'})\n" +
                        "MERGE (s:State{state : '" + r.getstate() + "'})\n" +
                        "MERGE (y:Year{year : '" + d.yearName(r.getcreated_date()) + "'})";
                System.out.println(nodes);
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println(r.getretailer_id());
                System.out.println("--------------------------------------------------------------------------------");


                // BoughtbyRelation
                String BoughtbyRelation = "Match (r:Retailer) WHERE r.id = '" + r.getretailer_id() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (p)-[rl:boughtBy{selling_price:'" + r.getselling_price() + "',cst:'" + r.getcst()
                        + "',discount_amount:'" + r.getdiscount_amount() + "',discount:'" + r.getdiscount() + "',gross_amount:'"
                        + r.getgross_amount() + "',type:'" + r.gettype() + "',sold_quantity:'" + r.getsold_quantity()
                        + "',igst:'" + r.getigst() + "',gst_amount:'" + r.getgst_amount() + "',uom:'" + r.getuom()
                        + "',sku_listing_status:'" + r.getsku_listing_status() + "',warehouseid:'" + r.getwarehouseid()
                        + "',sku_total_quantity:'" + r.getsku_total_quantity() + "',business_type:'" + r.getbusiness_type()
                        + "',state:'" + r.getstate() + "',id:'" + r.getid() + "',landing_price:'" + r.getlanding_price()
                        + "',cost_price:'" + r.getcost_price() + "',transaction_id:'" + r.gettransaction_id() + "',quantity:'"
                        + r.getquantity() + "',total_pre_tax_price:'" + r.gettotal_pre_tax_price() + "',retailer_id:'"
                        + r.getretailer_id() + "',pdt_id:" +Prod_Id
                        + ",created_by:'" + r.getcreated_by() + "',gst_percentage:'" + r.getgst_percentage() + "',month:'"
                        + d.monthName(r.getcreated_date()) + "',total_amount:'" + r.gettotal_amount() + "',sku_count:'"
                        + r.getsku_count() + "',logistics_amount:'" + r.getlogistics_amount() + "',created_date:'"
                        + r.getcreated_date() + "',category:'" + r.getcategory() + "',weave:'" + r.getweave() + "',status:'"
                        + r.getstatus() + "'"+ fin_str_attr+"}]->(r)";
                System.out.println(BoughtbyRelation);

                // linking type to product
                String linkTypeToProduct = "MATCH (t:Type) WHERE t.type ='" + r.gettype() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:typeName{type : '" + r.gettype() + "'}]->(p)";
                System.out.println(linkTypeToProduct);

                // linking category to product
                String linkCategoryToProduct = "MATCH (t:Category) WHERE t.category ='" + r.getcategory() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:categoryName{category : '" + r.getcategory() + "'}]->(p)";
                System.out.println(linkCategoryToProduct);

                // linking weave to product
                String linkWeaveToProduct = "MATCH (t:Weave) WHERE t.weave ='" + r.getweave() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (t)-[r:weaveName{weave : '" + r.getweave() + "'}]->(p)";
                System.out.println(linkWeaveToProduct);

                // linking - boughtMonth
                String boughtMonth = "MATCH (m:Month) WHERE m.month = '" + d.monthName(r.getcreated_date()) + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id + "\n" +
                        "MERGE (m)-[r:boughtMonth{transaction_id:'" + r.gettransaction_id() + "',month:'"
                        + d.monthName(r.getcreated_date()) + "',retailer_id:'" + r.getretailer_id() + "',state:'" + r.getstate()
                        + "',created_date:'" + r.getcreated_date() + "',pdt_id:'"
                        + Prod_Id + "'}]->(p)";
                System.out.println(boughtMonth);

                // linking - boughtState
                String boughtState = "MATCH (m:State) WHERE m.state = '" + r.getstate() + "'\n" +
                        "MATCH (p:Product) WHERE p.pdtId = " + Prod_Id+ "\n" +
                        "MERGE (m)-[r:boughtState{transaction_id:'" + r.gettransaction_id() + "',month:'"
                        + d.monthName(r.getcreated_date()) + "',retailer_id:'" + r.getretailer_id() + "',state:'" + r.getstate()
                        + "',created_date:'" + r.getcreated_date() + "',pdt_id:'"
                        + Prod_Id + "'}]->(p)";
                System.out.println(boughtState);

                tx.run(nodes);
                tx.run(BoughtbyRelation);
                tx.run(linkTypeToProduct);
                tx.run(linkCategoryToProduct);
                tx.run(linkWeaveToProduct);
                tx.run(boughtMonth);
                tx.run(boughtState);
                System.out.println("Updated database with new retailer transaction");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
    }



    // public void patchWeaverTransaction(Weaver w) {

    //     // creating new nodes
    //     String nodes = "MERGE (p1:Product{pdtid:'" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "',type:'"
    //             + w.gettype() + "' , weave: '" + w.getweave() + "' , category:'" + w.getcategory() + "'})\n" +
    //             "MERGE (t:Type{type:'" + w.gettype() + "'})\n" +
    //             "MERGE (t1:Category{category:'" + w.getcategory() + "'})\n" +
    //             "MERGE (t2:Weave{weave:'" + w.getweave() + "'})\n" +
    //             "MERGE (m:Month{month : '" + d.monthName(w.getcreated_date()) + "'})\n" +
    //             "MERGE (s:State{state : '" + w.getstate() + "'})\n"+
    //             "MERGE (y:Year{year : '" + d.yearName(w.getcreated_date()) + "'})";
    //     System.out.println(nodes);

    //     // soldbyRelation
    //     String soldByRelation = "Match (w:Weaver) WHERE w.id = '" + w.getweaver_id() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (w)-[rl:soldBy{selling_price:'" + w.getselling_price() + "',return_quantity:'"
    //             + w.getreturn_quantity() + "',cst:'" + w.getcst() + "',discount_amount:'" + w.getdiscount_amount()
    //             + "',discount:'" + w.getdiscount() + "',weaver_id:'" + w.getweaver_id() + "',gross_amount:'"
    //             + w.getgross_amount() + "',type:'" + w.gettype() + "',sold_quantity:'" + w.getsold_quantity()
    //             + "',igst:'" + w.getigst() + "',uom:'" + w.getuom() + "',gst_amount:'" + w.getgst_amount()
    //             + "',sku_listing_status:'" + w.getsku_listing_status() + "',warehouseid:'" + w.getwarehouseid()
    //             + "',sku_total_quantity:'" + w.getsku_total_quantity() + "',business_type:'" + w.getbusiness_type()
    //             + "',returned_defective_quantity:'" + w.getreturned_defective_quantity() + "',state:'" + w.getstate()
    //             + "',id:'" + w.getid() + "',landing_price:'" + w.getlanding_price() + "',cost_price:'"
    //             + w.getcost_price() + "',transaction_id:'" + w.gettransaction_id() + "',quantity:'" + w.getquantity()
    //             + "',total_pre_tax_price:'" + w.gettotal_pre_tax_price() + "',pdt_id:'"
    //             + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "',created_by:'" + w.getcreated_by()
    //             + "',gst_percentage:'" + w.getgst_percentage() + "',month:'" + d.monthName(w.getcreated_date())
    //             + "',total_amount:'" + w.gettotal_amount() + "',sku_count:'" + w.getsku_count() + "',logistics_amount:'"
    //             + w.getlogistics_amount() + "',defective_count:'" + w.getdefective_count() + "',created_date:'"
    //             + w.getcreated_date() + "',category:'" + w.getcategory() + "',weave:'" + w.getweave() + "',status:'"
    //             + w.getstatus() + "'}]->(p)";
    //     System.out.println(soldByRelation);

    //     // linking type to product
    //     String linkTypeToProduct = "MATCH (t:Type) WHERE t.type ='" + w.gettype() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (t)-[r:typeName{type : '" + w.gettype() + "'}]->(p)";
    //     System.out.println(linkTypeToProduct);

    //     // linking category to product
    //     String linkCategoryToProduct = "MATCH (t:Category) WHERE t.category ='" + w.getcategory() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (t)-[r:categoryName{category : '" + w.getcategory() + "'}]->(p)";
    //     System.out.println(linkCategoryToProduct);

    //     // linking weave to product
    //     String linkWeaveToProduct = "MATCH (t:Weave) WHERE t.weave ='" + w.getweave() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (t)-[r:weaveName{weave : '" + w.getweave() + "'}]->(p)";
    //     System.out.println(linkWeaveToProduct);

    //     // linking - soldMonth
    //     String soldMonth = "MATCH (m:Month) WHERE m.month = '" + d.monthName(w.getcreated_date()) + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (m)-[r:soldMonth{transaction_id:'" + w.gettransaction_id() + "',month:'"
    //             + d.monthName(w.getcreated_date()) + "',weaver_id:'" + w.getweaver_id() + "',state:'" + w.getstate()
    //             + "',created_date:'" + w.getcreated_date() + "',pdt_id:'"
    //             + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'}]->(p)";
    //     System.out.println(soldMonth);

    //     // linking - soldState
    //     String soldState = "MATCH (m:State) WHERE m.state = '" + w.getstate() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'\n" +
    //             "MERGE (m)-[r:soldState{transaction_id:'" + w.gettransaction_id() + "',month:'"
    //             + d.monthName(w.getcreated_date()) + "',weaver_id:'" + w.getweaver_id() + "',state:'" + w.getstate()
    //             + "',created_date:'" + w.getcreated_date() + "',pdt_id:'"
    //             + d.pdtid(w.gettype(), w.getcategory(), w.getweave()) + "'}]->(p)";
    //     System.out.println(soldState);

    //     try (Session session = driver.session()) {
    //         session.writeTransaction(tx -> {
    //             tx.run(nodes);
    //             tx.run(soldByRelation);
    //             tx.run(linkTypeToProduct);
    //             tx.run(linkCategoryToProduct);
    //             tx.run(linkWeaveToProduct);
    //             tx.run(soldMonth);
    //             tx.run(soldState);
    //             System.out.println("Hit");
    //             return 0;
    //         });
    //     } catch (Exception e) {
    //         System.out.println("Error");
    //     }
    // }

    // public void patchRetailerTransaction(Retailer r) {

    //     // creating new nodes
    //     String nodes = "MERGE (p1:Product{pdtid:'" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "',type:'"
    //             + r.gettype() + "' , weave: '" + r.getweave() + "' , category:'" + r.getcategory() + "'})\n" +
    //             "MERGE (t:Type{type:'" + r.gettype() + "'})\n" +
    //             "MERGE (t1:Category{category:'" + r.getcategory() + "'})\n" +
    //             "MERGE (t2:Weave{weave:'" + r.getweave() + "'})\n" +
    //             "MERGE (m:Month{month : '" + d.monthName(r.getcreated_date()) + "'})\n" +
    //             "MERGE (s:State{state : '" + r.getstate() + "'})\n" +
    //             "MERGE (y:Year{year : '" + d.yearName(r.getcreated_date()) + "'})";
    //     System.out.println(nodes);

    //     // BoughtbyRelation
    //     String BoughtbyRelation = "Match (r:Retailer) WHERE r.id = '" + r.getretailer_id() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (p)-[rl:boughtBy{selling_price:'" + r.getselling_price() + "',cst:'" + r.getcst()
    //             + "',discount_amount:'" + r.getdiscount_amount() + "',discount:'" + r.getdiscount() + "',gross_amount:'"
    //             + r.getgross_amount() + "',type:'" + r.gettype() + "',sold_quantity:'" + r.getsold_quantity()
    //             + "',igst:'" + r.getigst() + "',gst_amount:'" + r.getgst_amount() + "',uom:'" + r.getuom()
    //             + "',sku_listing_status:'" + r.getsku_listing_status() + "',warehouseid:'" + r.getwarehouseid()
    //             + "',sku_total_quantity:'" + r.getsku_total_quantity() + "',business_type:'" + r.getbusiness_type()
    //             + "',state:'" + r.getstate() + "',id:'" + r.getid() + "',landing_price:'" + r.getlanding_price()
    //             + "',cost_price:'" + r.getcost_price() + "',transaction_id:'" + r.gettransaction_id() + "',quantity:'"
    //             + r.getquantity() + "',total_pre_tax_price:'" + r.gettotal_pre_tax_price() + "',retailer_id:'"
    //             + r.getretailer_id() + "',pdt_id:'" + d.pdtid(r.gettype(), r.getcategory(), r.getweave())
    //             + "',created_by:'" + r.getcreated_by() + "',gst_percentage:'" + r.getgst_percentage() + "',month:'"
    //             + d.monthName(r.getcreated_date()) + "',total_amount:'" + r.gettotal_amount() + "',sku_count:'"
    //             + r.getsku_count() + "',logistics_amount:'" + r.getlogistics_amount() + "',created_date:'"
    //             + r.getcreated_date() + "',category:'" + r.getcategory() + "',weave:'" + r.getweave() + "',status:'"
    //             + r.getstatus() + "'}]->(r)";
    //     System.out.println(BoughtbyRelation);

    //     // linking type to product
    //     String linkTypeToProduct = "MATCH (t:Type) WHERE t.type ='" + r.gettype() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (t)-[r:typeName{type : '" + r.gettype() + "'}]->(p)";
    //     System.out.println(linkTypeToProduct);

    //     // linking category to product
    //     String linkCategoryToProduct = "MATCH (t:Category) WHERE t.category ='" + r.getcategory() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (t)-[r:categoryName{category : '" + r.getcategory() + "'}]->(p)";
    //     System.out.println(linkCategoryToProduct);

    //     // linking weave to product
    //     String linkWeaveToProduct = "MATCH (t:Weave) WHERE t.weave ='" + r.getweave() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (t)-[r:weaveName{weave : '" + r.getweave() + "'}]->(p)";
    //     System.out.println(linkWeaveToProduct);

    //     // linking - boughtMonth
    //     String boughtMonth = "MATCH (m:Month) WHERE m.month = '" + d.monthName(r.getcreated_date()) + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (m)-[r:boughtMonth{transaction_id:'" + r.gettransaction_id() + "',month:'"
    //             + d.monthName(r.getcreated_date()) + "',retailer_id:'" + r.getretailer_id() + "',state:'" + r.getstate()
    //             + "',created_date:'" + r.getcreated_date() + "',pdt_id:'"
    //             + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'}]->(p)";
    //     System.out.println(boughtMonth);

    //     // linking - boughtState
    //     String boughtState = "MATCH (m:State) WHERE m.state = '" + r.getstate() + "'\n" +
    //             "MATCH (p:Product) WHERE p.pdtid = '" + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'\n" +
    //             "MERGE (m)-[r:boughtState{transaction_id:'" + r.gettransaction_id() + "',month:'"
    //             + d.monthName(r.getcreated_date()) + "',retailer_id:'" + r.getretailer_id() + "',state:'" + r.getstate()
    //             + "',created_date:'" + r.getcreated_date() + "',pdt_id:'"
    //             + d.pdtid(r.gettype(), r.getcategory(), r.getweave()) + "'}]->(p)";
    //     System.out.println(boughtState);

    //     try (Session session = driver.session()) {
    //         session.writeTransaction(tx -> {
    //             tx.run(nodes);
    //             tx.run(BoughtbyRelation);
    //             tx.run(linkTypeToProduct);
    //             tx.run(linkCategoryToProduct);
    //             tx.run(linkWeaveToProduct);
    //             tx.run(boughtMonth);
    //             tx.run(boughtState);
    //             System.out.println("Updated database with new retailer transaction");
    //             return 0;
    //         });
    //     } catch (Exception e) {
    //         System.out.println("Error");
    //     }
    // }

    ////////////////////////// PATCHING
    ////////////////////////// /////////////////////////////////////////////

    /////////////////////// topTenProducts////////////////////////
    public String[][] topTenProduct(Transactions t,String[][] res, String[] soln) {
        dict d = new dict();
        StringBuilder mainQuery = new StringBuilder();
        StringBuilder catList = new StringBuilder();
        StringBuilder typeList = new StringBuilder();
        StringBuilder weaveList = new StringBuilder();
        StringBuilder stateList = new StringBuilder();
        StringBuilder monthList = new StringBuilder();
        if (t.getRole() == "Weaver") {
            mainQuery.append("MATCH (n:Weaver)-[r:soldBy]->(w:Product)");
        } else {
            mainQuery.append("MATCH (w:Product)-[r:boughtBy]->(n:Retailer)");
        }
        if (t.getID().length != 0) {
            mainQuery.append("\nWHERE (");
            mainQuery.append("n.id=").append('"' + t.getID()[0] + '"');
            for (int i = 1; i < t.getID().length; i++) {
                mainQuery.append(" or n.id=");
                mainQuery.append('"' + t.getID()[i] + '"');
            }
            mainQuery.append(")");
            mainQuery.append("\nWITH r");
        }

        if (t.getCategory() != null) {
            catList.append("(").append("r.category = ").append('"' + t.getCategory()[0] + '"');
            for (int i = 1; i < t.getCategory().length; i++) {
                catList.append(" or r.category = ");
                catList.append('"' + t.getCategory()[i] + '"');
            }
            catList.append(")");
        } else
            catList.append("(true)");

        if (t.getType() != null) {
            typeList.append("(").append("r.type = ").append('"' + t.getType()[0] + '"');
            for (int i = 1; i < t.getType().length; i++) {
                typeList.append(" or r.type = ");
                typeList.append('"' + t.getType()[i] + '"');
            }
            typeList.append(")");
        } else
            typeList.append("(true)");

        if (t.getWeave() != null) {
            weaveList.append("(r.weave = ").append('"' + t.getWeave()[0] + '"');
            for (int i = 1; i < t.getWeave().length; i++) {
                weaveList.append(" or r.weave = ");
                weaveList.append('"' + t.getWeave()[i] + '"');
            }
            weaveList.append(")");
        } else
            weaveList.append("(true)");

        if (t.getMonth() != null) {
            monthList.append("(r.month = ").append('"' + t.getMonth()[0] + '"');
            for (int i = 1; i < t.getMonth().length; i++) {
                monthList.append(" or r.month = ");
                monthList.append('"' + t.getMonth()[i] + '"');
            }
            monthList.append(")");
        } else
            monthList.append("(true)");

        if (t.getState() != null) {
            stateList.append("(r.state = ").append('"' + t.getState()[0] + '"');
            for (int i = 1; i < t.getState().length; i++) {
                stateList.append(" or r.state = ");
                stateList.append('"' + t.getState()[i] + '"');
            }
            stateList.append(")");
        } else
            stateList.append("(true)");

        mainQuery.append(" ").append("\nWHERE ").append(monthList).append(" and ").append(stateList).append(" and ")
                .append(catList).append(" and ");
        mainQuery.append(typeList).append(" and ").append(weaveList);
        if(soln!=null){
            int k=0;
            for(String st:soln){
            if(res[k]!=null && res[k].length!=0){
               mainQuery.append(" and (r."+soln[k].toLowerCase()+" = ").append('"' + res[k][0] + '"');
                      for (int i = 1; i < res[k].length; i++) {
                       mainQuery.append(" or r."+soln[k].toLowerCase()+" = ");
                       mainQuery.append('"' + t.getState()[i] + '"');
                      }
                      mainQuery.append(")");
            } k++;
          } }
                mainQuery.append("\nRETURN r.pdt_id,sum(toFloat(r.quantity)) AS Quantity,r.type,r.category,r.weave\n" +
                        "ORDER BY Quantity DESC LIMIT 10");
       System.out.println("TOP TEN PRODUCTS: " + mainQuery);
        String[][] tableData1 = new String[11][5];
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(mainQuery));
                List<Record> list = new ArrayList<Record>(result.list());
                tableData1[0][0] = "Product ID";
                tableData1[0][1] = "Quantity";
                tableData1[0][2] = "Type";
                tableData1[0][3] = "Category";
                tableData1[0][4] = "Weave";
                int i = 1;
                for (Record r : list) {
                    int j = 0;
                    for (var s1 : r.values()) {
                        tableData1[i][j] = new String(s1.toString());
                        if(j!=1) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                        j++;
                    }
                    i++;
                }
                return 0;
            });
        } catch (Exception e) {
            System.out.println(e);
        }
        return tableData1;
    }




    /////////////////////// topTenWeavers////////////////////////
    int weaver = 0;
    public String[][] topTenWeavers(Transactions t,String[][] res, String[] soln) {
       dict d = new dict();
       StringBuilder mainQuery = new StringBuilder();
       StringBuilder catList = new StringBuilder();
       StringBuilder typeList = new StringBuilder();
       StringBuilder weaveList = new StringBuilder();
       StringBuilder stateList = new StringBuilder();
       StringBuilder monthList = new StringBuilder();
       if (t.getRole().equals("Weaver")) {
           mainQuery.append("MATCH (n:Weaver)-[r:soldBy]->(w:Product)"); weaver = 1;
       } else {
           mainQuery.append("MATCH (w:Product)-[r:boughtBy]->(n:Retailer)");
       }
       if (t.getID().length != 0) {
           mainQuery.append("\nWHERE (");
           mainQuery.append("n.id=").append('"' + t.getID()[0] + '"');
           for (int i = 1; i < t.getID().length; i++) {
               mainQuery.append(" or n.id=");
               mainQuery.append('"' + t.getID()[i] + '"');
           }
           mainQuery.append(")");
           mainQuery.append("\nWITH r");
       }
   
       if (t.getCategory() != null) {
           catList.append("(").append("r.category = ").append('"' + t.getCategory()[0] + '"');
           for (int i = 1; i < t.getCategory().length; i++) {
               catList.append(" or r.category = ");
               catList.append('"' + t.getCategory()[i] + '"');
           }
           catList.append(")");
       } else
           catList.append("(true)");
   
       if (t.getType() != null) {
           typeList.append("(").append("r.type = ").append('"' + t.getType()[0] + '"');
           for (int i = 1; i < t.getType().length; i++) {
               typeList.append(" or r.type = ");
               typeList.append('"' + t.getType()[i] + '"');
           }
           typeList.append(")");
       } else
           typeList.append("(true)");
   
       if (t.getWeave() != null) {
           weaveList.append("(r.weave = ").append('"' + t.getWeave()[0] + '"');
           for (int i = 1; i < t.getWeave().length; i++) {
               weaveList.append(" or r.weave = ");
               weaveList.append('"' + t.getWeave()[i] + '"');
           }
           weaveList.append(")");
       } else
           weaveList.append("(true)");
   
       if (t.getMonth() != null) {
           monthList.append("(r.month = ").append('"' + t.getMonth()[0] + '"');
           for (int i = 1; i < t.getMonth().length; i++) {
               monthList.append(" or r.month = ");
               monthList.append('"' + t.getMonth()[i] + '"');
           }
           monthList.append(")");
       } else
           monthList.append("(true)");
   
       if (t.getState() != null) {
           stateList.append("(r.state = ").append('"' + t.getState()[0] + '"');
           for (int i = 1; i < t.getState().length; i++) {
               stateList.append(" or r.state = ");
               stateList.append('"' + t.getState()[i] + '"');
           }
           stateList.append(")");
       } else
           stateList.append("(true)");
   
       mainQuery.append(" ").append("\nWHERE ").append(monthList).append(" and ").append(stateList).append(" and ")
               .append(catList).append(" and ");
       mainQuery.append(typeList).append(" and ").append(weaveList);
       if(soln!=null){
        int k=0;
        for(String st:soln){
        if(res[k]!=null && res[k].length!=0){
           mainQuery.append(" and (r."+soln[k].toLowerCase()+" = ").append('"' + res[k][0] + '"');
                  for (int i = 1; i < res[k].length; i++) {
                   mainQuery.append(" or r."+soln[k].toLowerCase()+" = ");
                   mainQuery.append('"' + t.getState()[i] + '"');
                  }
                  mainQuery.append(")");
        } k++;
      } }
       if (t.getRole().equals("Weaver"))
               mainQuery.append("\nRETURN r.weaver_id,sum(toFloat(r.quantity)) AS Quantity\n" +
                       "ORDER BY Quantity DESC LIMIT 10");
               else 
               mainQuery.append("\nRETURN r.retailer_id,sum(toFloat(r.quantity)) AS Quantity\n" +
               "ORDER BY Quantity DESC LIMIT 10");
   
   
       String[][] tableData1 = new String[11][2];
       try (Session session = driver.session()) {
           session.writeTransaction(tx -> {
               Result result = tx.run(String.valueOf(mainQuery));
               List<Record> list = new ArrayList<Record>(result.list());
               if (t.getRole().equals("Weaver")){
               tableData1[0][0] = "Weaver ID"; }
               else {tableData1[0][0] = "Retailer ID"; }
               tableData1[0][1] = "Quantity";
   
               int i = 1;
               for (Record r : list) {
                   int j = 0;
                   for (var s1 : r.values()) {
                       tableData1[i][j] = new String(s1.toString());
                       if(j!=1) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                       j++;
                   }
                   i++;
               }
               return 0;
           });
       } catch (Exception e) {
           System.out.println(e);
       }
       return tableData1;
   }
   
   
   ///////////////end of top 10 weavers /////////
   ///////////
   
   
    ///////////////////////// Product Stock
    ///////////////////////// ///////////////////////////////////////////////
    public String[][] productStock(Products t, String soln[], String res[][]) {
        StringBuilder catList = new StringBuilder();
        StringBuilder typeList = new StringBuilder();
        StringBuilder weaveList = new StringBuilder();
        StringBuilder mainQuery1 = new StringBuilder();
        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        StringBuilder pquery = new StringBuilder();
        StringBuilder squery = new StringBuilder();
        String[][] pdtData = new String[0][];
        String[][] inQData = new String[0][];
        String[][] outQData = new String[0][];
        String[][] pdtData3 = new String[0][];
        String[][] pdtData4 = new String[0][];
        String[] slist = new String[0];
        int noOfPdt, noOfInq, noOfout2, noOfStates;
        if (t.getCategory() != null) {
            catList.append("(").append("r1.category = ").append('"' + t.getCategory()[0] + '"');
            for (int i = 1; i < t.getCategory().length; i++) {
                catList.append(" or r1.category = ");
                catList.append('"' + t.getCategory()[i] + '"');
            }
            catList.append(")");
        } else
            catList.append("(true)");

        if (t.getType() != null) {
            typeList.append("(").append("r2.type = ").append('"' + t.getType()[0] + '"');
            for (int i = 1; i < t.getType().length; i++) {
                typeList.append(" or r2.type = ");
                typeList.append('"' + t.getType()[i] + '"');
            }
            typeList.append(")");
        } else
            typeList.append("(true)");

        if (t.getWeave() != null) {
            weaveList.append("(r3.weave = ").append('"' + t.getWeave()[0] + '"');
            for (int i = 1; i < t.getWeave().length; i++) {
                weaveList.append(" or r3.weave = ");
                weaveList.append('"' + t.getWeave()[i] + '"');
            }
            weaveList.append(")");
        } else
            weaveList.append("(true)");

            if (t.getFilter().equals("Total")) {
                int k=0; StringBuilder a= new StringBuilder("a0");
                StringBuilder A=new StringBuilder("A0");
                if(res!=null){
                    for(String st:soln){
                        if(res[k]!=null && res[k].length!=0){
                            mainQuery1.append("MATCH ("+a+":"+st.substring(0,1)+st.substring(1,st.length()).toLowerCase()+")-["+A+":"+st.toLowerCase()+"Name]->(p1:Product)\n");
                            mainQuery1.append("WHERE ("+A+"."+st.toLowerCase()+" = ");
                            mainQuery1.append('"' + res[k][0] + '"');
                            for (int i = 1; i < res[k].length; i++) {
                                mainQuery1.append(" or "+A+"."+st.toLowerCase()+" = ");
                                mainQuery1.append('"' + res[k][i] + '"');
                    }
                        mainQuery1.append(")"); mainQuery1.append("\n WITH p1\n");
                        } 
                        k++;
                        a.append(k);
                        A.append(k);
                    } 
                    System.out.println("main " + mainQuery1);
                } 
            mainQuery1.append("MATCH (c:Category)-[r1:categoryName]->(p1:Product)\n");
            mainQuery1.append("WHERE ").append(catList).append("\nWITH p1 \n");
            mainQuery1.append("MATCH (t:Type)-[r2:typeName]->(p1)\n");
            mainQuery1.append("WHERE ").append(typeList).append("\nWITH p1\n");
            mainQuery1.append("MATCH (w:Weave)-[r3:weaveName]->(p1:Product)\n");
            mainQuery1.append("WHERE ").append(weaveList);
            query1.append(mainQuery1).append("\nWITH p1\n").append("MATCH (weav:Weaver)-[r4:soldBy]->(p1)\n" +
                    "WITH p1,sum(toFloat(r4.quantity)) AS inQuantity\n" +
                    "RETURN p1.pdtId,ceil(inQuantity) AS Stock,p1.type,p1.category,p1.weave\n" +
                    "ORDER BY p1.pdtId\n");
            query2.append(mainQuery1).append("\nWITH p1\n").append("MATCH (p1)-[r5:boughtBy]->(ret:Retailer)\n" +
                    "WITH p1,sum(toFloat(r5.quantity)) AS outQuantity\n" +
                    "RETURN p1.pdtId,ceil(outQuantity) AS Stock,p1.type,p1.category,p1.weave\n" +
                    "ORDER BY p1.pdtId\n");
                    pquery.append(mainQuery1).append("RETURN p1.pdtId,p1.type,p1.category,p1.weave,p1.id");
                    if(res!=null){
                        int l=0;
                        for(String st:soln){
                            System.out.println(l+" "+res[l]);
                            if(res[l]!=null && res[l].length!=0) pquery.append(",p1."+st.toLowerCase()); 
                            l++;
                        }}
                        pquery.append( "\n ORDER BY p1.pdtId\n");
            System.out.println(pquery);
            System.out.println(query1);
            System.out.println(query2);

            try (Session session = driver.session()) {


                // listing all products with given combination
                pdtData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(pquery));
                    List<Record> list = new ArrayList<Record>(result.list());
                    System.out.println(list.size());
                    String[][] tableData1;
                    int al=0,count=0; 
                    if(res!=null){
                    for(al=0;al<soln.length;al++){
                        if(res[al]!=null && res[al].length!=0) count++;
                    }}
                    count1=count;
                    if(res!=null) tableData1=new String[list.size() + 1][5+count];
                    else tableData1=new String[list.size() + 1][5];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][1] = "Type";
                    tableData1[0][2] = "Category";
                    tableData1[0][3] = "Weave";
                    tableData1[0][4] = Integer.toString(list.size());
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if(j==0) 
                           { tableData1[i][j] = s1.toString(); 
                            tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                           }
                           else tableData1[i][j] = s1.asString(); 
                            j++;
                        }
                        i++;
                    }
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    return tableData1;
                });
                noOfPdt = Integer.parseInt(pdtData[0][4]);
                pdtData[0][4] = "Stock";
                System.out.println("Returned list of products ");
                // listing all products sold by weavers to company along with stock
                inQData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(query1));
                    List<Record> list = new ArrayList<Record>(result.list());
                    String[][] tableData1 = new String[list.size() + 1][6];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][2] = "Type";
                    tableData1[0][3] = "Category";
                    tableData1[0][4] = "Weave";
                    tableData1[0][1] = "Stock";
                    tableData1[0][5] = Integer.toString(list.size());
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if (j == 1 || j==0) {
                                tableData1[i][j] = s1.toString();
                                if(j==0) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                                j++;
                            } else {
                                tableData1[i][j] = s1.asString();
                                j++;
                            }
                        }
                        i++;
                    }
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    return tableData1;
                });
                noOfInq = Integer.parseInt(inQData[0][5]);

                // listing all products bought by retailer from company along with stock
                outQData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(query2));
                    List<Record> list = new ArrayList<Record>(result.list());
                    System.out.println(list.size());
                    String[][] tableData1 = new String[list.size() + 1][6];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][2] = "Type";
                    tableData1[0][3] = "Category";
                    tableData1[0][4] = "Weave";
                    tableData1[0][1] = "Stock";
                    tableData1[0][5] = Integer.toString(list.size());
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if (j == 1 || j==0) {
                                tableData1[i][j] = s1.toString();
                                if(j==0) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                                j++;
                            } else {
                                tableData1[i][j] = s1.asString();
                                j++;
                            }
                        }
                        i++;
                    }
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    return tableData1;
                });
                noOfout2 = Integer.parseInt(outQData[0][5]);
                int m = 1, n = 1;
                for (int i = 1; i < noOfPdt; i++) {
                    if (m < noOfInq && pdtData[i][0].equals(inQData[m][0])) {
                        pdtData[i][4] = inQData[m][1];
                        m++;
                    } else {
                        pdtData[i][4] = "0.0";
                    }
                    if (n < noOfout2 && pdtData[i][0].equals(outQData[n][0])) {
                        pdtData[i][4] = Float
                                .toString(Float.parseFloat(pdtData[i][4]) - Float.parseFloat(outQData[n][1]));
                        n++;
                    }
                }

                // for (String[] s1 : pdtData) {
                //     for (String s2 : s1) {
                //         System.out.print(s2 + "   ");
                //     }
                //     System.out.println("");
                // }

            } catch (Exception e) {
                System.out.println(e);
            }
            int index=5,index1=0;
            if(res!=null){
            while(index<5+count1){
                if(res[index1]!=null && res[index1].length!=0)
            {pdtData[0][index] = soln[index1];
                index=index+1;}
                index1++;
            }}
            System.out.println();
            return pdtData;

        } else {
            int k=0; StringBuilder a= new StringBuilder("a0");
            StringBuilder A=new StringBuilder("A0");
            if(res!=null){
            for(String st:soln){
                if(res[k]!=null && res[k].length!=0){
                    mainQuery1.append("MATCH ("+a+":"+st.substring(0,1)+st.substring(1,st.length()).toLowerCase()+")-["+A+":"+st.toLowerCase()+"Name]->(p1:Product)\n");
                    mainQuery1.append("WHERE ("+A+"."+st.toLowerCase()+" = ");
                    mainQuery1.append('"' + res[k][0] + '"');
                    for (int i = 1; i < res[k].length; i++) {
                        mainQuery1.append(" or "+A+"."+st.toLowerCase()+" = ");
                        mainQuery1.append('"' + res[k][i] + '"');
            }
                mainQuery1.append(")"); mainQuery1.append("\n WITH p1\n");
                } 
                k++;
                a.append(k);
                A.append(k);
            } 
            System.out.println("main " + mainQuery1);
        }
            mainQuery1.append("MATCH (c:Category)-[r1:categoryName]->(p1:Product)\n");
            mainQuery1.append("WHERE ").append(catList).append("\nWITH p1 \n");
            mainQuery1.append("MATCH (t:Type)-[r2:typeName]->(p1)\n");
            mainQuery1.append("WHERE ").append(typeList).append("\nWITH p1\n");
            mainQuery1.append("MATCH (w:Weave)-[r3:weaveName]->(p1:Product)\n");
            mainQuery1.append("WHERE ").append(weaveList);
            query1.append(mainQuery1).append("\nWITH p1\n").append("MATCH (weav:Weaver)-[r4:soldBy]->(p1)\n" +
                    "WITH p1,r4.state AS state,sum(toFloat(r4.quantity)) AS inQuantity\n" +
                    "RETURN p1.pdtId,state,ceil(inQuantity) AS Stock,p1.type,p1.category,p1.weave\n" +
                    "ORDER BY p1.pdtId,state\n");
            query2.append(mainQuery1).append("\nWITH p1\n").append("MATCH (p1)-[r5:boughtBy]->(ret:Retailer)\n" +
                    "WITH p1,r5.state AS state,sum(toFloat(r5.quantity)) AS outQuantity\n" +
                    "RETURN p1.pdtId,state,ceil(outQuantity) AS Stock,p1.type,p1.category,p1.weave\n" +
                    "ORDER BY p1.pdtId,state\n");
            pquery.append(mainQuery1).append("RETURN p1.pdtId,p1.type,p1.category,p1.weave\n" +
                    "ORDER BY p1.pdtId\n");
            squery.append("MATCH (s:State)\n").append("RETURN s.states\n").append("ORDER BY s.states\n");
            System.out.println(pquery);
            System.out.println(squery);
            System.out.println(query1);
            System.out.println(query2);
            try (Session session = driver.session()) {

                // listing all products with given combination
                pdtData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(pquery));
                    List<Record> list = new ArrayList<Record>(result.list());
                    System.out.println(list.size());
                    String[][] tableData1 = new String[list.size() + 1][5];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][1] = "Type";
                    tableData1[0][2] = "Category";
                    tableData1[0][3] = "Weave";
                    tableData1[0][4] = Integer.toString(list.size());
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if(j==0) {
                                tableData1[i][j] = s1.toString();
                                tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                            }
                            else tableData1[i][j] = s1.asString();
                            j++;
                        }
                        i++;
                    }
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    return tableData1;
                });
                noOfPdt = Integer.parseInt(pdtData[0][4]);
                System.out.println("Returned list of products ");

                // listing all states
                slist = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(squery));
                    List<Record> list = new ArrayList<Record>(result.list());
                    System.out.println(list.size());
                    String[] tableData1 = new String[list.size() + 2];
                    tableData1[0] = "State";
                    tableData1[1] = Integer.toString(list.size());
                    int i = 2;
                    for (Record r : list) {
                        for (var s1 : r.values()) {
                            tableData1[i] = s1.asString();
                        }
                        i++;
                    }
                    // for (String s1 : tableData1) {

                    //     System.out.println(s1);
                    // }
                    return tableData1;
                });
                noOfStates = Integer.parseInt(slist[1]);

                // Weaver products sold quantity with state
                inQData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(query1));
                    List<Record> list = new ArrayList<Record>(result.list());
                    String[][] tableData1 = new String[list.size() + 1][7];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][3] = "Type";
                    tableData1[0][4] = "Category";
                    tableData1[0][5] = "Weave";
                    tableData1[0][2] = "Stock";
                    tableData1[0][6] = Integer.toString(list.size());
                    tableData1[0][1] = "State";
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if (j == 2 || j==0) {
                                tableData1[i][j] = s1.toString();
                                if(j==0) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                                j++;
                            } else {
                                tableData1[i][j] = s1.asString();
                                j++;
                            }
                        }
                        i++;
                    }
                    System.out.println("INQ DATA");
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    return tableData1;
                });
                noOfInq = Integer.parseInt(inQData[0][6]);

                // listing all products bought by retailer from company along with stock
                outQData = session.writeTransaction(tx -> {
                    Result result = tx.run(String.valueOf(query2));
                    List<Record> list = new ArrayList<Record>(result.list());
                    System.out.println(list.size());
                    String[][] tableData1 = new String[list.size() + 1][7];
                    tableData1[0][0] = new String("Product ID");
                    tableData1[0][3] = "Type";
                    tableData1[0][4] = "Category";
                    tableData1[0][5] = "Weave";
                    tableData1[0][2] = "Stock";
                    tableData1[0][6] = Integer.toString(list.size());
                    tableData1[0][1] = "State";
                    int i = 1;
                    for (Record r : list) {
                        int j = 0;
                        for (var s1 : r.values()) {
                            if (j == 2 || j==0) {
                                tableData1[i][j] = s1.toString();
                                if(j==0) tableData1[i][j] = tableData1[i][j].substring(1,tableData1[i][j].length()-1);
                                j++;
                            } else {
                                tableData1[i][j] = s1.asString();
                                j++;
                            }
                        }
                        i++;
                    }
                    System.out.println("OUTQ DATA");
                    // for (String[] s1 : tableData1) {
                    // for (String s2 : s1) {
                    // System.out.print(s2 + " ");
                    // }
                    // System.out.println("");
                    // }
                    System.out.println(tableData1[0][2]);
                    return tableData1;
                });
                noOfout2 = Integer.parseInt(outQData[0][6]);

                String[][] pdtData2 = new String[noOfPdt+1][noOfStates+5];
                
                for (int i = 0; i < noOfPdt; i++) {
                    for (int j = 0; j < 4; j++) {
                        pdtData2[i][j] = pdtData[i][j];
                    }
                }

                for (int i = 0; i < noOfStates; i++) {
                    pdtData2[0][i + 4] = slist[i + 2];
                }

                for (int i = 0; i < noOfPdt; i++) {
                    for (int j = 0; j < noOfStates; j++) {
                        pdtData2[i + 1][j + 4] = "0.0";
                    }
                }

                int m = 1, n = 1;
                for (int i = 1; i < noOfPdt; i++) {
                    // System.out.println(pdtData2[i][0]);
                    // System.out.println(inQData[m][0]);
                    // System.out.println(m);
                    if (pdtData2[i][0].equals(inQData[m][0])) {
                        System.out.println("1");
                        for (int j = 0; j < noOfStates; j++) {
                            if (m < noOfInq && pdtData2[i][0].equals(inQData[m][0])
                                    && (inQData[m][1]==null ||pdtData2[0][j+4].equals(inQData[m][1]))) {
                                        System.out.println("2");
                                pdtData2[i][j+4] = Float.toString(
                                    Float.parseFloat(pdtData2[i][j+4]) + Float.parseFloat(inQData[m][2]));
                                m++;
                            }
                        }
                    }
                    System.out.println("look here"+ Float.parseFloat(pdtData2[2][10]));
                    if (pdtData2[i][0].equals(outQData[n][0])) {
                        for (int j = 0; j < noOfStates; j++) {
                            if (n < noOfout2 && pdtData2[i][0].equals(outQData[n][0])
                            && (inQData[m][1]==null || pdtData2[0][j+4].equals(outQData[n][1]))) {
                                pdtData2[i][j+4] = Float.toString(
                                        Float.parseFloat(pdtData2[i][j+4]) - Float.parseFloat(outQData[n][2]));
                                n++;
                            }
                        }
                    }

                }
                pdtData3 = pdtData2;

            } catch (Exception e) {
                System.out.println(e);
            }
            // System.out.println();
            // for (String[] s1 : pdtData3) {
            //     for (String s2 : s1) {
            //     System.out.print(s2 + " ");
            //     }
            //     System.out.println("");
            //     }
            return pdtData3;
        }
    }

    // public static void main(String[] args) {
    //     Transactions t1 = new Transactions();
    //     Products p1 = new Products();
    //     p1.setFilter("Split");
    //     // t.setRole("Retailer");
    //     try (Neo4j neo = new Neo4j("neo4j+s://ba6b34f2.databases.neo4j.io", "neo4j",
    //             "yjPkJyIuUi65j4p5yNUK4Tua1ZzgK3z4VPGc0iU_7rU")) {
    //         neo.productStock(p1);
    //     } catch (Exception e) {
    //         System.out.println("Exception");
    //     }
    // }

    public void pushNewAttribute(String newAttribute){
        String atr = newAttribute.toLowerCase();
        String query = "MATCH (n:Attributes)\n"+"SET n."+atr+" = []";
        System.out.println(query);
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(query);
                System.out.println("Hit");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void removeAttribute(String oldAttribute){
        String atr = oldAttribute.toLowerCase();
        String query = "MATCH (n:Attributes)\n"+"REMOVE n."+atr;
        System.out.println(query);
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(query);
                System.out.println("Hit");
                return 0;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void getAttributes(){
        attrNames = new String[100];
        String query = "MATCH (n:Attributes)\n"+"RETURN properties(n)";
        // System.out.println(query);
        try (Session session = driver.session()) {
            attributeList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                String[][] atrList = new String[100][100];
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        for(var s2 : s.keys()){
                            int k=0;
                            String[] lists = s.get(s2).toString().split("\"");
                            atrList[i][0] = s2.toUpperCase();
                            // atrList[i][1] = s2.toUpperCase();
                            int j=1;
                            for(String s3 : lists){
                                if(k%2==1){
                                        atrList[i][j++] = s3;
                                    }
                                    k++;
                                }
                            i++;
                            }
                        }
                        }
                
                System.out.println("Attribute Hit");
                return atrList;
            });
            int j=0;String str;
                for(;j<100;j++){
                    if(attributeList[j][0] != null){
                        attrNames[j] = attributeList[j][0];
                    }
                }
        } catch (Exception e) {
            System.out.println(e);
        }
        // System.out.println(Arrays.deepToString(attributeList));
    }

    public String[][] newStatistics(Statistics stats,String[][] attributes,String[] keys,String sDate,String eDate){
        String[][] stt = new String[0][];
        StringBuilder squery = new StringBuilder();
        StringBuilder stateQ = new StringBuilder();
        StringBuilder yearQ = new StringBuilder();
        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        StringBuilder mainQuery = new StringBuilder();
        String[] mon = new String[12];
        for(int g=0;g<12;g++){
            mon[g] = "zz";
        }

        int sflag = 0;
        int monthNo = 0;
        if(stats.getSeasons()!=null){
            int i=0;
            squery.append("(");
            for(String seasonName : stats.getSeasons()){
                if(i==0){
                    if(seasonName.compareToIgnoreCase("Monsoon") == 0){
                        squery.append("r.month = "+'"'+"June"+'"'+" or "+"r.month = "+'"'+"July"+'"'+" or "+"r.month = "+'"'+"August"+'"');
                        mon[0] = "June";
                        mon[1] = "July";
                        mon[2] = "August";
                    }
                    if(seasonName.compareToIgnoreCase("Summer") == 0){
                        squery.append("r.month = "+'"'+"March"+'"'+" or "+"r.month = "+'"'+"April"+'"'+" or "+"r.month = "+'"'+"May"+'"');
                        mon[3] = "March";
                        mon[4] = "April";
                        mon[5] = "May";
                    }
                    if(seasonName.compareToIgnoreCase("Winter") == 0){
                        squery.append("r.month = "+'"'+"October"+'"'+" or "+"r.month = "+'"'+"September"+'"'+" or "+"r.month = "+'"'+"November"+'"');
                        mon[6] = "October";
                        mon[7] = "November";
                        mon[8] = "September";
                    }
                    if(seasonName.compareToIgnoreCase("Spring") == 0){
                        squery.append("r.month = "+'"'+"December"+'"'+" or "+"r.month = "+'"'+"January"+'"'+" or "+"r.month = "+'"'+"February"+'"');
                        mon[9] = "December";
                        mon[10] = "January";
                        mon[11] = "February";
                    }
                }else{
                    if(seasonName.compareToIgnoreCase("Monsoon") == 0){
                        squery.append(" or "+"r.month = "+'"'+"June"+'"'+" or "+"r.month = "+'"'+"July"+'"'+" or "+"r.month = "+'"'+"August"+'"');
                        mon[0] = "June";
                        mon[1] = "July";
                        mon[2] = "August";
                    }
                    if(seasonName.compareToIgnoreCase("Summer") == 0){
                        squery.append(" or "+"r.month = "+'"'+"March"+'"'+" or "+"r.month = "+'"'+"April"+'"'+" or "+"r.month = "+'"'+"May"+'"');
                        mon[3] = "March";
                        mon[4] = "April";
                        mon[5] = "May";
                    }
                    if(seasonName.compareToIgnoreCase("Winter") == 0){
                        squery.append(" or "+"r.month = "+'"'+"October"+'"'+" or "+"r.month = "+'"'+"September"+'"'+" or "+"r.month = "+'"'+"November"+'"');
                        mon[6] = "October";
                        mon[7] = "November";
                        mon[8] = "September";
                    }
                    if(seasonName.compareToIgnoreCase("Spring") == 0){
                        squery.append(" or "+"r.month = "+'"'+"December"+'"'+" or "+"r.month = "+'"'+"January"+'"'+" or "+"r.month = "+'"'+"February"+'"');
                        mon[9] = "December";
                        mon[10] = "January";
                        mon[11] = "February";
                    }
                }
                i++;
            }

            squery.append(")");
            sflag = 1;
            monthNo = 3*i;
        }

        

        if(sflag == 0 && stats.getMonth()!=null && stats.getMonth().length!=0){
            int i=0;
            for(String monthName : stats.getMonth()){
                monthNo++;
                if(i==0){
                    squery.append("("+"r.month = "+'"'+monthName+'"');
                }else{
                    squery.append(" or"+" r.month = "+'"'+monthName+'"');
                }
                i++;
            }
            squery.append(")");
        }else if(sflag == 0 && stats.getMonth()==null){
            squery.append("(true)");
            monthNo = 12;
        }

        int stateNo = 0;
        System.out.println("Hello");
        if(stats.getStates()!=null && stats.getStates().length!=0){
            int i=0;
            for(String stateName : stats.getStates()){
                stateNo++;
                if(i==0){
                    stateQ.append("("+"r.states = "+'"'+stateName+'"');
                }else{
                    stateQ.append(" or"+" r.states = "+'"'+stateName+'"');
                }
                i++;
            }
            stateQ.append(")");
        }else{
            stateQ.append("(true)");
            getStates();
            stateNo = stateList.length;
        }

        StringBuilder stateQ2 = new StringBuilder();
        if(stats.getStates()!=null && stats.getStates().length!=0){
            int i=0;
            for(String stateName : stats.getStates()){
                if(i==0){
                    stateQ2.append("("+"r.state = "+'"'+stateName+'"');
                }else{
                    stateQ2.append(" or"+" r.state = "+'"'+stateName+'"');
                }
                i++;
            }
            stateQ2.append(")");
        }else{
            stateQ2.append("(true)");
        }

        int yearNo = 0;
        if(stats.getYears()!=null){
            int i=0;
            yearNo = stats.getYears().length;
            for(String yearName : stats.getYears()){
                // yearNo++;
                if(i==0){
                    yearQ.append("("+"r.year = "+'"'+yearName+'"');
                }else{
                    yearQ.append(" or"+" r.year = "+'"'+yearName+'"');
                }
                i++;
            }
            yearQ.append(")");
        }else{
            yearQ.append("(true)");
            getYear();
            yearNo = yearList.length;
        }

        StringBuilder yearQ2 = new StringBuilder();
        if(stats.getYears()!=null){
            int i=0;
            yearNo = stats.getYears().length;
            for(String yearName : stats.getYears()){
                // yearNo++;
                if(i==0){
                    yearQ2.append("("+"split(r.created_date,'-' )[0] = "+'"'+yearName+'"');
                }else{
                    yearQ2.append(" or"+" split(r.created_date,'-' )[0] = "+'"'+yearName+'"');
                }
                i++;
            }
            yearQ2.append(")");
        }else{
            yearQ2.append("(true)");
            getYear();
            yearNo = yearList.length;
        }


        StringBuilder filters = new StringBuilder();
        int fflag = 0;
        int arr2 = 0;
        if(stats.getFilter()!=null){
            if(stats.getFilter().compareToIgnoreCase("Month") == 0){
                query2.append("MATCH (r:Month)\n"+
                "WHERE "+squery+"\n"+
                "WITH collect(r.month)as monthList\n"+
                "UNWIND monthList as stateName\n");
                fflag = 1;
                filters.append("month : sname,");
                arr2 = monthNo;
            }else if(stats.getFilter().compareToIgnoreCase("State") == 0){
                query2.append("MATCH (r:State)\n"+
                "WHERE "+stateQ+"\n"+
                "WITH collect(r.states)as stateList\n"+
                "UNWIND stateList as stateName\n"); 
                fflag = 2;
                filters.append("state : sname,");
                arr2 = stateNo;
            }else if(stats.getFilter().compareToIgnoreCase("Year") == 0){
                query2.append("MATCH (r:Year)\n"+
                "WHERE "+yearQ+"\n"+
                "WITH collect(r.year)as stateList\n"+
                "UNWIND stateList as stateName\n"); 
                filters.append(" ");
                fflag = 3;
                arr2 = yearNo;
            }
        }
        StringBuilder query3 = new StringBuilder();
        int fflag2 = 0;
        StringBuilder filter2 = new StringBuilder();
        // 
        // mon[0] = "January";
        if(stats.getProductSpec()!=null){
            if(stats.getProductSpec().compareToIgnoreCase("Type") == 0){
                query3.append("MATCH (t:Type)\n"+
                "WHERE (true)\n"+
                "WITH stateName as sname,Collect(t.type) as typeList\n"+
                "UNWIND typeList as types\n");
                filter2.append("type : ");
                fflag2 = 1;
            }else if(stats.getProductSpec().compareToIgnoreCase("Category") == 0){
                query3.append("MATCH (t:Category)\n"+
                "WHERE (true)\n"+
                "WITH stateName as sname,Collect(t.category) as typeList\n"+
                "UNWIND typeList as types\n"); 
                filter2.append("category : ");
                fflag2 = 2;
            }else if(stats.getProductSpec().compareToIgnoreCase("Weave") == 0){
                query3.append("MATCH (t:Weave)\n"+
                "WHERE (true)\n"+
                "WITH stateName as sname,Collect(t.weave) as typeList\n"+
                "UNWIND typeList as types\n"); 
                filter2.append("weave : ");
                fflag2 = 3;
            }else{
                query3.append("MATCH (t:"+stats.getProductSpec()+")\n"+
                "WHERE (true)\n"+
                "WITH stateName as sname,Collect(t."+stats.getProductSpec().toLowerCase()+") as typeList\n"+
                "UNWIND typeList as types\n"); 
                filter2.append(" "+stats.getProductSpec().toLowerCase()+" : ");
                fflag2 = 4;
            }
        }


        if(stats.getRole().compareToIgnoreCase("Weaver") == 0){
            query1.append("MATCH (w1:Weaver)-[r:soldBy{"+ filters +filter2+"types}]->(p2:Product)\n");
        }else{
            query1.append("MATCH (p2:Product)-[r:boughtBy{"+ filters +filter2+"types}]->(w1:Retailer)\n"); 
        }

        StringBuilder attrQ = new StringBuilder();
        int attributeCount = 0;
        int count2=0;
        attrQ.append("(true) ");
        if(attributes!=null){
        for(String[] part1 : attributes){
            attrQ.append(" and (");
            if(part1!=null){
                String s2 = new String();
                s2 = "r."+keys[attributeCount].toLowerCase();
                attrQ.append("(");
                for(String part2 : part1){
                    count2++;
                    if(count2!=1){
                        attrQ.append(" or " +s2 +" = "+'"'+ part2 +'"');
                    }else{
                        attrQ.append(s2 +" = "+'"'+part2+'"');
                    }
                }
                count2 = 0;
                attrQ.append(")");
            }else{
                attrQ.append("(true)");
            }
            attrQ.append(")");
            attributeCount++;
        }}

        int  index = 0;
        for(index = 0;index!=keys.length;index++){
            if(fflag2 == 4 && keys[index].compareToIgnoreCase(stats.getProductSpec()) == 0 ){
                break;
            }
        }

        StringBuilder attrs = new StringBuilder(" ");
        attrs.append(index);

        StringBuilder dateQ = new StringBuilder();

        int startMonth = Integer.parseInt(sDate.split("-")[1]);
        int startYear = Integer.parseInt(sDate.split("-")[0]);
        int startDay = Integer.parseInt(sDate.split("-")[2]);
        int endMonth = Integer.parseInt(eDate.split("-")[1]);
        int endYear = Integer.parseInt(eDate.split("-")[0]);
        int endDay = Integer.parseInt(eDate.split("-")[2]);
        dateQ.append(" and "+"(toInteger(split(r.created_date,'-')[0]) >"+startYear+" or (toInteger(split(r.created_date,'-')[0]) = "+startYear+" and toInteger(split(r.created_date,'-')[1]) > "+startMonth+") or (toInteger(split(r.created_date,'-')[0]) = "+startYear+" and toInteger(split(r.created_date,'-')[1]) ="+startMonth+" and toInteger(split(r.created_date,'-')[2]) >="+startDay+")) and (toInteger(split(r.created_date,'-')[0]) <"+endYear+" or (toInteger(split(r.created_date,'-')[0]) = "+endYear+" and toInteger(split(r.created_date,'-')[1]) < "+endMonth+") or (toInteger(split(r.created_date,'-')[0]) = "+endYear+" and toInteger(split(r.created_date,'-')[1]) = "+endMonth+" and toInteger(split(r.created_date,'-')[2]) <="+endDay+"))");



        mainQuery.append(query2).append(query3);
        mainQuery.append(query1);
        if(fflag == 3){
            mainQuery.append("WHERE "+"split(r.created_date,'-' )[0]=sname and "+attrQ+" and "+stateQ2+" and "+yearQ2+" and "+squery+dateQ);
        }else{
            mainQuery.append("WHERE "+attrQ+" and "+stateQ2+" and "+yearQ2+" and "+squery+dateQ);
        }

        // if(fflag == 3){
        //     mainQuery.append("WHERE "+"split(r.created_date,'-' )[0]=sname and "+stateQ2+" and "+yearQ+" and "+squery+dateQ);
        // }else{
        //     mainQuery.append("WHERE " +stateQ2+" and "+yearQ+" and "+squery+dateQ);
        // }

        mainQuery.append("\nWITH sname,types,sum(toFloat(r.quantity)) AS inQuantity\n"+
        "RETURN sname,types,ceil(inQuantity) AS Stock\n"+
        "ORDER BY sname,types\n");
        System.out.println(mainQuery);

        StringBuilder ff = new StringBuilder(" ");
        ff.append(fflag2);
        StringBuilder ff2 = new StringBuilder(" ");
        ff2.append(fflag);
        StringBuilder col = new StringBuilder(" ");
        col.append(arr2);
        System.out.println("HEEEEEEE "+arr2+"\n");
        try (Session session = driver.session()) {
            stt = session.writeTransaction( tx -> {
                Result result = tx.run(String.valueOf(mainQuery));
                List<Record> list = new ArrayList<Record>(result.list());
                // System.out.println(list.size());
                // System.out.println(list);
                int arr = 0;
                // System.out.println(ff);
                // System.out.println(Integer.valueOf(ff.toString().trim()));
                switch(Integer.parseInt(ff.toString().trim())){
                    case 1 :getTypes();
                            arr = typeList.length;
                            break;
                    case 2 :getCategory();
                            arr = categoryList.length;
                            break;
                    case 3 :getWeave();
                            arr = weaveList.length;
                            break;
                    default : if(attributes!= null && attributes[Integer.valueOf(attrs.toString().trim())]!=null){
                        arr = attributes[Integer.valueOf(attrs.toString().trim())].length;
                    }else{
                        getOthers(stats.getProductSpec()); 
                        arr = othersList.length;
                    }
                                break;
                }

                // System.out.println("Index is "+Integer.parseInt(col.toString().trim())+2);
                String[][] statList = new String[arr+1][Integer.parseInt(col.toString().trim())+1];
                // System.out.println(Integer.valueOf(ff.toString().trim()));
                switch(Integer.parseInt(ff.toString().trim())){
                    case 1 :for(int m=0;m!=typeList.length;m++){
                                statList[m+1][0] = typeList[m];
                            }
                            break;
                    case 2 :for(int m=0;m!=categoryList.length;m++){
                                statList[m+1][0] = categoryList[m];
                               
                            }
                            break;

                    case 3 :for(int m=0;m!=weaveList.length;m++){
                                statList[m+1][0] = weaveList[m];
                            }
                            break;

                    default :if(attributes==null){
                                for(int m=0;m!=othersList.length;m++){
                                    statList[m+1][0] = othersList[m];
                                }
                            }else if(attributes!=null && attributes[Integer.valueOf(attrs.toString().trim())]==null){
                                for(int m=0;m!=othersList.length;m++){
                                    statList[m+1][0] = othersList[m];
                                }
                            }else{
                                if(attributes!=null){
                                for(int m=0;m!=attributes[Integer.valueOf(attrs.toString().trim())].length;m++){
                                    statList[m+1][0] = attributes[Integer.valueOf(attrs.toString().trim())][m];
                                }}
                            }
                            break;
                }

                // System.out.println(mon[0]);
                // System.out.print("HELLO");
                // System.out.println(d.month_list[4]);
               switch(Integer.parseInt(ff2.toString().trim())){
                    case 1 :if(stats.getMonth()!=null){
                        Collections.sort(Arrays.asList(d.month_list));
                            if(Integer.parseInt(col.toString().trim()) !=12){ 
                            for(int m=0;m!=stats.getMonth().length;m++){
                                statList[0][m+1] = stats.getMonth()[m];
                                
                            }}else{
                                for(int m=0;m!=d.month_list.length;m++){
                                    statList[0][m+1] = d.month_list[m];
                                }
                            }
                    }else if(stats.getSeasons()!=null){
                        Collections.sort(Arrays.asList(mon));
                            if(Integer.parseInt(col.toString().trim()) !=12){ 
                            for(int m=0;m!=mon.length;m++){
                                if(!mon[m].equals("zz")){
                                statList[0][m+1] = mon[m];
                                }else{
                                    break;
                                }
                            }}else{
                                Collections.sort(Arrays.asList(d.month_list));
                                for(int m=0;m!=d.month_list.length;m++){
                                    statList[0][m+1] = d.month_list[m];
                                }
                            }
                    }else{
                        Collections.sort(Arrays.asList(d.month_list));
                        for(int m=0;m!=d.month_list.length;m++){
                            statList[0][m+1] = d.month_list[m];
                        }
                    }
                            break;
                    case 2 : if(stats.getStates()!=null){
                        Collections.sort(Arrays.asList(stats.getStates()));
                        for(int m=0;m!=stats.getStates().length;m++){
                            statList[0][m+1] = stats.getStates()[m];
                        }
                    }else{
                        getStates();
                        for(int m=0;m!=stateList.length;m++){
                            statList[0][m+1] = stateList[m];
                        }
                    }
                            break;
                    case 3 : if(stats.getYears()!=null){
                        Collections.sort(Arrays.asList(stats.getYears()));
                        for(int m=0;m!=stats.getYears().length;m++){
                            statList[0][m+1] = stats.getYears()[m];
                        }
                    }else{
                        for(int m=0;m!=yearList.length;m++){
                            statList[0][m+1] = yearList[m];
                        }
                    }
                            break;
                }

                String[][] mainList = new String[list.size()][4];
                int p=0;
                for(Record r : list){
                    int q = 0;
                    for(var a: r.values()){
                        if(q == 2){
                            mainList[p][q] = a.toString();
                        }else{
                            mainList[p][q] = a.asString();
                        }
                        q++;
                    }
                    p++;
                }

                
                

                int count = 0;
                for(int m=0;m<Integer.parseInt(col.toString().trim());m++){
                    if(count<list.size() && mainList[count][0].equals(statList[0][m+1])){
                        for(int n=0;n<arr;n++){
                            if(count<list.size()  && mainList[count][0].equals(statList[0][m+1]) && mainList[count][1].equals(statList[n+1][0])){
                                statList[n+1][m+1] = mainList[count][2];
                                count++;
                            }else{
                                statList[n+1][m+1] = "0";
                            }
                        }
                    }else{
                        for(int n=0;n<arr;n++){
                            statList[n+1][m+1] = "0";
                        }
                    }  
                }



                System.out.println("Hit");
                for(var a: statList){
                    for(var b :a){
                        System.out.print(b +" ");
                    }
                    System.out.println(" ");
                }
                return statList;
            });
        } catch (Exception e) {
            System.out.println(e);
        }
        return stt;    
    }

 // public String[][] newStatistics(Statistics stats,String[][] attributes,String[] keys,String sDate,String eDate){
    //     String[][] stt = new String[0][];
    //     StringBuilder squery = new StringBuilder();
    //     StringBuilder stateQ = new StringBuilder();
    //     StringBuilder yearQ = new StringBuilder();
    //     StringBuilder query1 = new StringBuilder();
    //     StringBuilder query2 = new StringBuilder();
    //     StringBuilder mainQuery = new StringBuilder();
    //     int arr2 = 0;
    //     int monthNo = 0;
    //     int sflag = 0;
    //     if(stats.getSeasons()!=null){
    //         int i=0;
    //         for(String seasonName : stats.getSeasons()){
    //             if(i==0){
    //                 if(seasonName.compareToIgnoreCase("Monsoon") == 0){
    //                     squery.append("("+"r.month = "+'"'+"June"+'"'+" or "+"r.month = "+'"'+"July"+'"'+" or "+"r.month = "+'"'+"August"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Summer") == 0){
    //                     squery.append("("+"r.month = "+'"'+"March"+'"'+" or "+"r.month = "+'"'+"April"+'"'+" or "+"r.month = "+'"'+"May"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Winter") == 0){
    //                     squery.append("("+"r.month = "+'"'+"October"+'"'+" or "+"r.month = "+'"'+"September"+'"'+" or "+"r.month = "+'"'+"November"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Spring") == 0){
    //                     squery.append("("+"r.month = "+'"'+"December"+'"'+" or "+"r.month = "+'"'+"January"+'"'+" or "+"r.month = "+'"'+"February"+'"'+")");
    //                 }
    //             }else{
    //                 if(seasonName.compareToIgnoreCase("Monsoon") == 0){
    //                     squery.append(" or "+"("+"r.month = "+'"'+"June"+'"'+" or "+"r.month = "+'"'+"July"+'"'+" or "+"r.month = "+'"'+"August"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Summer") == 0){
    //                     squery.append(" or "+"("+"r.month = "+'"'+"March"+'"'+" or "+"r.month = "+'"'+"April"+'"'+" or "+"r.month = "+'"'+"May"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Winter") == 0){
    //                     squery.append(" or "+"("+"r.month = "+'"'+"October"+'"'+" or "+"r.month = "+'"'+"September"+'"'+" or "+"r.month = "+'"'+"November"+'"'+")");
    //                 }
    //                 if(seasonName.compareToIgnoreCase("Spring") == 0){
    //                     squery.append(" or "+"("+"r.month = "+'"'+"December"+'"'+" or "+"r.month = "+'"'+"January"+'"'+" or "+"r.month = "+'"'+"February"+'"'+")");
    //                 }
    //             }
    //             i++;
    //         }
    //         sflag = 1;
    //         monthNo = 3*i;
    //     }


    //     if(sflag == 0 && stats.getMonth()!=null && stats.getMonth().length!=0){
    //         int i=0;
    //         for(String monthName : stats.getMonth()){
    //             monthNo++;
    //             if(i==0){
    //                 squery.append("("+"r.month = "+'"'+monthName+'"');
    //             }else{
    //                 squery.append(" or"+" r.month = "+'"'+monthName+'"');
    //             }
    //             i++;
    //         }
    //         squery.append(")");
    //     }else if(sflag == 0 && stats.getMonth()==null){
    //         squery.append("(true)");
    //         monthNo = 12;
    //     }

    //     int stateNo = 0;
    //     if(stats.getStates()!=null && stats.getStates().length!=0){
    //         int i=0;
    //         for(String stateName : stats.getStates()){
    //             stateNo++;
    //             if(i==0){
    //                 stateQ.append("("+"r.states = "+'"'+stateName+'"');
    //             }else{
    //                 stateQ.append(" or"+" r.states = "+'"'+stateName+'"');
    //             }
    //             i++;
    //         }
    //         stateQ.append(")");
    //     }else{
    //         stateQ.append("(true)");
    //         getStates();
    //         stateNo = stateList.length;
    //     }

    //     StringBuilder stateQ2 = new StringBuilder();
    //     if(stats.getStates()!=null && stats.getStates().length!=0){
    //         int i=0;
    //         for(String stateName : stats.getStates()){
    //             if(i==0){
    //                 stateQ2.append("("+"r.state = "+'"'+stateName+'"');
    //             }else{
    //                 stateQ2.append(" or"+" r.state = "+'"'+stateName+'"');
    //             }
    //             i++;
    //         }
    //         stateQ2.append(")");
    //     }else{
    //         stateQ2.append("(true)");
    //     }

    //     int yearNo = 0;
    //     if(stats.getYears()!=null){
    //         int i=0;
    //         for(String yearName : stats.getYears()){
    //             yearNo++;
    //             if(i==0){
    //                 yearQ.append("("+"r.year = "+'"'+yearName+'"');
    //             }else{
    //                 yearQ.append(" or"+" r.year = "+'"'+yearName+'"');
    //             }
    //             i++;
    //         }
    //         yearQ.append(")");
    //     }else{
    //         yearQ.append("(true)");
    //         getYear();
    //         yearNo = yearList.length;
    //     }


    //     StringBuilder filters = new StringBuilder();
    //     int fflag = 0;
    //     if(stats.getFilter()!=null){
    //         if(stats.getFilter().compareToIgnoreCase("Month") == 0){
    //             query2.append("MATCH (r:Month)\n"+
    //             "WHERE "+squery+"\n"+
    //             "WITH collect(r.month)as monthList\n"+
    //             "UNWIND monthList as stateName\n");
    //             fflag = 1;
    //             filters.append("month : sname,");
    //             arr2 = monthNo;
    //         }else if(stats.getFilter().compareToIgnoreCase("State") == 0){
    //             query2.append("MATCH (r:State)\n"+
    //             "WHERE "+stateQ+"\n"+
    //             "WITH collect(r.states)as stateList\n"+
    //             "UNWIND stateList as stateName\n"); 
    //             fflag = 2;
    //             filters.append("state : sname,");
    //             arr2 = stateNo;
    //         }else if(stats.getFilter().compareToIgnoreCase("Year") == 0){
    //             query2.append("MATCH (r:Year)\n"+
    //             "WHERE "+yearQ+"\n"+
    //             "WITH collect(r.year)as stateList\n"+
    //             "UNWIND stateList as stateName\n"); 
    //             filters.append(" ");
    //             fflag = 3;
    //             arr2 = yearNo;
    //         }
    //     }
    //     StringBuilder query3 = new StringBuilder();
    //     int fflag2 = 0;
    //     StringBuilder filter2 = new StringBuilder();
    //     if(stats.getProductSpec()!=null){
    //         if(stats.getProductSpec().compareToIgnoreCase("Type") == 0){
    //             query3.append("MATCH (t:Type)\n"+
    //             "WHERE (true)\n"+
    //             "WITH stateName as sname,Collect(t.type) as typeList\n"+
    //             "UNWIND typeList as types\n");
    //             filter2.append("type : ");
    //             fflag2 = 1;
    //         }else if(stats.getProductSpec().compareToIgnoreCase("Category") == 0){
    //             query3.append("MATCH (t:Category)\n"+
    //             "WHERE (true)\n"+
    //             "WITH stateName as sname,Collect(t.category) as typeList\n"+
    //             "UNWIND typeList as types\n"); 
    //             filter2.append("category : ");
    //             fflag2 = 2;
    //         }else if(stats.getProductSpec().compareToIgnoreCase("Weave") == 0){
    //             query3.append("MATCH (t:Weave)\n"+
    //             "WHERE (true)\n"+
    //             "WITH stateName as sname,Collect(t.weave) as typeList\n"+
    //             "UNWIND typeList as types\n"); 
    //             filter2.append("weave : ");
    //             fflag2 = 3;
    //         }else{
    //             query3.append("MATCH (t:"+stats.getProductSpec()+")\n"+
    //             "WHERE (true)\n"+
    //             "WITH stateName as sname,Collect(t."+stats.getProductSpec().toLowerCase()+") as typeList\n"+
    //             "UNWIND typeList as types\n"); 
    //             filter2.append(" "+stats.getProductSpec().toLowerCase()+" : ");
    //             fflag2 = 4;
    //         }
    //     }


    //     if(stats.getRole().compareToIgnoreCase("Weaver") == 0){
    //         query1.append("MATCH (w1:Weaver)-[r:soldBy{"+ filters +filter2+"types}]->(p2:Product)\n");
    //     }else{
    //         query1.append("MATCH (p2:Product)-[r:boughtBy{"+ filters +filter2+"types}]->(w1:Retailer)\n"); 
    //     }

    //     StringBuilder attrQ = new StringBuilder();
    //     int attributeCount = 0;
    //     int count2=0;
    //     attrQ.append("(true) ");
    //     if(attributes!=null){
    //     for(String[] part1 : attributes){
    //         attrQ.append(" and (");
    //         if(part1!=null){
    //             String s2 = new String();
    //             s2 = "r."+keys[attributeCount].toLowerCase();
    //             attrQ.append("(");
    //             for(String part2 : part1){
    //                 count2++;
    //                 if(count2!=1){
    //                     attrQ.append(" or " +s2 +" = "+'"'+ part2 +'"');
    //                 }else{
    //                     attrQ.append(s2 +" = "+'"'+part2+'"');
    //                 }
    //             }
    //             count2 = 0;
    //             attrQ.append(")");
    //         }else{
    //             attrQ.append("(true)");
    //         }
    //         attrQ.append(")");
    //         attributeCount++;
    //     }
    // }
    //     int  index = 0;
    //     for(index = 0;index!=keys.length;index++){
    //         if(fflag2 == 4 && keys[index].compareToIgnoreCase(stats.getProductSpec()) == 0 ){
    //             break;
    //         }
    //     }

    //     StringBuilder attrs = new StringBuilder(" ");
    //     attrs.append(index);

    //     StringBuilder dateQ = new StringBuilder();

    //     int startMonth = Integer.parseInt(sDate.split("-")[1]);
    //     int startYear = Integer.parseInt(sDate.split("-")[0]);
    //     int startDay = Integer.parseInt(sDate.split("-")[2]);
    //     int endMonth = Integer.parseInt(eDate.split("-")[1]);
    //     int endYear = Integer.parseInt(eDate.split("-")[0]);
    //     int endDay = Integer.parseInt(eDate.split("-")[2]);
    //     dateQ.append(" and "+"(toInteger(split(r.created_date,'-')[0]) >"+startYear+" or (toInteger(split(r.created_date,'-')[0]) = "+startYear+" and toInteger(split(r.created_date,'-')[1]) > "+startMonth+") or (toInteger(split(r.created_date,'-')[0]) = "+startYear+" and toInteger(split(r.created_date,'-')[1]) ="+startMonth+" and toInteger(split(r.created_date,'-')[2]) >="+startDay+")) and (toInteger(split(r.created_date,'-')[0]) <"+endYear+" or (toInteger(split(r.created_date,'-')[0]) = "+endYear+" and toInteger(split(r.created_date,'-')[1]) < "+endMonth+") or (toInteger(split(r.created_date,'-')[0]) = "+endYear+" and toInteger(split(r.created_date,'-')[1]) = "+endMonth+" and toInteger(split(r.created_date,'-')[2]) <="+endDay+"))");



    //     mainQuery.append(query2).append(query3);
    //     mainQuery.append(query1);
    //     if(fflag == 3){
    //         mainQuery.append("WHERE "+"split(r.created_date,'-' )[0]=sname and "+attrQ+" and "+stateQ2+" and "+yearQ+" and "+squery+dateQ);
    //     }else{
    //         mainQuery.append("WHERE "+attrQ+" and "+stateQ2+" and "+yearQ+" and "+squery+dateQ);
    //     }


    //     mainQuery.append("\nWITH sname,types,sum(toFloat(r.quantity)) AS inQuantity\n"+
    //     "RETURN sname,types,ceil(inQuantity) AS Stock\n"+
    //     "ORDER BY sname,types\n");
    //     System.out.println(mainQuery);

    //     StringBuilder ff = new StringBuilder(" ");
    //     ff.append(fflag2);
    //     StringBuilder ff2 = new StringBuilder(" ");
    //     ff2.append(fflag);
    //     StringBuilder col = new StringBuilder(" ");
    //     col.append(arr2);
    //     System.out.println("HEEEEEEE "+arr2+"\n");
    //     try (Session session = driver.session()) {
    //         stt = session.writeTransaction( tx -> {
    //             Result result = tx.run(String.valueOf(mainQuery));
    //             List<Record> list = new ArrayList<Record>(result.list());
    //             // System.out.println(list.size());
    //             // System.out.println(list);
    //             int arr = 0;
    //             // System.out.println(ff);
    //             // System.out.println(Integer.valueOf(ff.toString().trim()));
    //             switch(Integer.parseInt(ff.toString().trim())){
    //                 case 1 :getTypes();
    //                         arr = typeList.length;
    //                         break;
    //                 case 2 :getCategory();
    //                         arr = categoryList.length;
    //                         break;
    //                 case 3 :getWeave();
    //                         arr = weaveList.length;
    //                         break;
    //                 default : if(attributes[Integer.valueOf(attrs.toString().trim())]!=null){
    //                     arr = attributes[Integer.valueOf(attrs.toString().trim())].length;
    //                 }else{
    //                     getOthers(stats.getProductSpec()); 
    //                     arr = othersList.length;
    //                 }
    //                             break;
    //             }

    //             // System.out.println("Index is "+Integer.parseInt(col.toString().trim())+2);
    //             String[][] statList = new String[arr+1][Integer.parseInt(col.toString().trim())+1];
    //             // System.out.println(Integer.valueOf(ff.toString().trim()));
    //             switch(Integer.parseInt(ff.toString().trim())){
    //                 case 1 :for(int m=0;m!=typeList.length;m++){
    //                             statList[m+1][0] = typeList[m];
    //                         }
    //                         break;
    //                 case 2 :for(int m=0;m!=categoryList.length;m++){
    //                             statList[m+1][0] = categoryList[m];
                               
    //                         }
    //                         break;
    //                 case 3 :for(int m=0;m!=weaveList.length;m++){
    //                             statList[m+1][0] = weaveList[m];
    //                         }
    //                         break;
    //                 default : if(attributes[Integer.valueOf(attrs.toString().trim())]==null){
    //                     for(int m=0;m!=othersList.length;m++){
    //                         statList[m+1][0] = othersList[m];
    //                     }
    //                 }else{
    //                     for(int m=0;m!=attributes[Integer.valueOf(attrs.toString().trim())].length;m++){
    //                         statList[m+1][0] = attributes[Integer.valueOf(attrs.toString().trim())][m];
    //                     }
    //                 }
    //                 break;
    //             }
    //             // System.out.print("HELLO");
    //             // System.out.println(d.month_list[4]);
    //            switch(Integer.parseInt(ff2.toString().trim())){
    //                 case 1 :Collections.sort(Arrays.asList(d.month_list));
    //                         if(Integer.parseInt(col.toString().trim()) !=12){ 
    //                         for(int m=0;m!=stats.getMonth().length;m++){
    //                             statList[0][m+1] = stats.getMonth()[m];
                                
    //                         }}else{
    //                             for(int m=0;m!=d.month_list.length;m++){
    //                                 statList[0][m+1] = d.month_list[m];
                                    
    //                             }
    //                         }
    //                         break;
    //                 case 2 : if(stats.getStates()!=null){
    //                     Collections.sort(Arrays.asList(stats.getStates()));
    //                     for(int m=0;m!=stats.getStates().length;m++){
    //                         statList[0][m+1] = stats.getStates()[m];
    //                     }
    //                 }else{
    //                     getStates();
    //                     for(int m=0;m!=stateList.length;m++){
    //                         statList[0][m+1] = stateList[m];
    //                     }
    //                 }
    //                         break;
    //                 case 3 : if(stats.getYears()!=null){
    //                     Collections.sort(Arrays.asList(stats.getYears()));
    //                     for(int m=0;m!=stats.getYears().length;m++){
    //                         statList[0][m+1] = stats.getYears()[m];
    //                     }
    //                 }else{
    //                     for(int m=0;m!=yearList.length;m++){
    //                         statList[0][m+1] = yearList[m];
    //                     }
    //                 }
    //                         break;
    //             }

    //             String[][] mainList = new String[list.size()][4];
    //             int p=0;
    //             for(Record r : list){
    //                 int q = 0;
    //                 for(var a: r.values()){
    //                     if(q == 2){
    //                         mainList[p][q] = a.toString();
    //                     }else{
    //                         mainList[p][q] = a.asString();
    //                     }
    //                     q++;
    //                 }
    //                 p++;
    //             }

                
                

    //             int count = 0;
    //             for(int m=0;m<Integer.parseInt(col.toString().trim());m++){
    //                 if(count<list.size() && mainList[count][0].equals(statList[0][m+1])){
    //                     for(int n=0;n<arr;n++){
    //                         if(count<list.size()  && mainList[count][0].equals(statList[0][m+1]) && mainList[count][1].equals(statList[n+1][0])){
    //                             statList[n+1][m+1] = mainList[count][2];
    //                             count++;
    //                         }else{
    //                             statList[n+1][m+1] = "0";
    //                         }
    //                     }
    //                 }else{
    //                     for(int n=0;n<arr;n++){
    //                         statList[n+1][m+1] = "0";
    //                     }
    //                 }  
    //             }



    //             System.out.println("Hit");
    //             for(var a: statList){
    //                 for(var b :a){
    //                     System.out.print(b +" ");
    //                 }
    //                 System.out.println(" ");
    //             }

    //             return statList;
    //         });
    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }
    //     return stt;    
    // }

    public void getOthers(String name){
        String query = "MATCH (n:"+name+")\n"+"RETURN n."+name.toLowerCase()+"\n"+"ORDER BY n."+name.toLowerCase();
        System.out.println(query);
        try (Session session = driver.session()) {
            othersList = session.writeTransaction(tx -> {
                Result result = tx.run(String.valueOf(query));
                List<Record> list = new ArrayList<Record>(result.list());
                System.out.println(list.size());
                String[] categories = new String[list.size()];
                System.out.println(list);
                int i=0;
                for(Record r : list){
                    for(var s : r.values()){
                        if(s!=null)
                            categories[i++] = s.asString();
                    }
                }
                System.out.println("Hit");
                return categories;
            });
        } catch (Exception e) {
            System.out.println("Error");
        }
        System.out.println(Arrays.deepToString(othersList));
    }

}

