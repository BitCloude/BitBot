package util;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class ProductDescription {

    public static String getLobName(String ptCode){

        switch(ptCode){

            case "PT06150000":
                return "BizDirect";
            case "PT02150010":
                return "HomeMortgage";
            case "PT02150005":
                return "PLL";
            case "PT02150007":
                return "AutoLoan";
            case "PT00206027":
                return "StudentLoan";
            case "PT00206008":
                return "HomeEquity";
            default:
                return "LOB not found!";
        }
    }

}
