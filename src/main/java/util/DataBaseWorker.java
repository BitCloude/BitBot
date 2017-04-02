package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class DataBaseWorker {
    private String productType;

    public DataBaseWorker(String productType){
        this.productType = productType;
    }
    public List<String[]> runQueryforResult(String queryType, Connection connect) throws SQLException {
        String queryToUse;
        if("diagnostic".equals(queryType))
            queryToUse = createSQLDiagnosticQuery(productType);
        else
            queryToUse = createSQLFullDataQuery(productType);

        try(Statement statement = connect.createStatement(); ResultSet result = statement.executeQuery(queryToUse)) {
            return getResultData(result);
        }
    }

    private List<String[]> getResultData(ResultSet result) throws SQLException {
        List<String[]> data = new ArrayList<>();
        while (result.next()) {
            String[] authData = new String[7];
            for (int i = 1; i<=6; i++){
                authData[i-1] = result.getString(i);
            }
            data.add(authData);
        }
        return data;
    }

    public String createSQLDiagnosticQuery(String productType) {
        return "select distinct" + "blabla";
    }

    public String createSQLFullDataQuery(String productType) {
        return "select " + "blabla";
    }
}
