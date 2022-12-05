package reshamandi.knowledgeGraph;
import java.util.regex.*;

public class validate {
    public boolean validateWId(String id){
        Pattern pat = Pattern.compile("[10000-11999]");
        Matcher mat = pat.matcher(id);
        if(mat.matches()){
            return true;
        }else
            return false;
    }
    public boolean validateRId(String id){
        Pattern pat = Pattern.compile("[12000-14000]");
        Matcher mat = pat.matcher(id);
        if(mat.matches()){
            return true;
        }else
            return false;
    }
    
    public boolean phoneNumber(String number){
        Pattern pat = Pattern.compile("[0000000000-9999999999]");
        Matcher mat = pat.matcher(number);
        if(mat.matches()){
            return true;
        }else
            return false;
    }
}
