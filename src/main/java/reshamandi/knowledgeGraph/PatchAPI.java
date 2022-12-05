package reshamandi.knowledgeGraph;

public class PatchAPI {
    static dict d = new dict();
    public static void main(String args[]){
        System.out.println(d.pdtid("Accessories","AC-Blanket(DOHAR)","Yarn Dyed"));
        System.out.println(d.monthName("22-07-2018 22:57"));
        System.out.println("Hello world!");
        return ;
    }

    public static void retailerTransaction(Retailer ret){

        


        System.out.println("retailerrrr");
        System.out.println(ret.getid());
        System.out.println(ret.getcreated_by());
        System.out.println(ret.getcreated_date());
        System.out.println(ret.getcategory());
        System.out.println(ret.getcost_price());
        System.out.println(ret.getquantity());
        System.out.println(ret.getselling_price());
        System.out.println(ret.getstatus());
        System.out.println(ret.gettype());
        System.out.println(ret.getwarehouseid());
        System.out.println(ret.getsold_quantity());
        System.out.println(ret.getsku_listing_status());
        System.out.println(ret.getlanding_price());
        System.out.println(ret.getuom());
        System.out.println(ret.getgst_amount());
        System.out.println(ret.getgst_percentage());
        System.out.println(ret.getlogistics_amount());
        System.out.println(ret.getweave());
        System.out.println(ret.getcst());
        System.out.println(ret.getigst());
        System.out.println(ret.gettotal_amount());
        System.out.println(ret.gettotal_pre_tax_price());
        System.out.println(ret.getsku_count());
        System.out.println(ret.getsku_total_quantity());
        System.out.println(ret.getstate());
        System.out.println(ret.getretailer_id());
        System.out.println(ret.getdiscount());
        System.out.println(ret.getgross_amount());
        System.out.println(ret.getdiscount_amount());
        System.out.println(ret.getbusiness_type());
        System.out.println(ret.gettransaction_id());
        return ;
    }

    public static void weaverTransaction(Weaver w){
        System.out.println("weaverrrrr");
        System.out.println(w.getid());
        return ;
    }

}


