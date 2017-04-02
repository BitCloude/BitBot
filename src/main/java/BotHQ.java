import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.GetPropertyValues;
import util.ProductDescription;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class BotHQ implements Loggable {

private HashMap<String,String> propsEnv;
private HashMap<String,String> propsQuery;
static boolean stillRunning = true;
static ExecutorService botMinions = Executors.newCachedThreadPool();
static ExecutorService minionRunners = Executors.newFixedThreadPool(6);

private BotHQ(){
    /* This is a Main Class */
}
public void initProperties() throws IOException {
    GetPropertyValues propClass = new GetPropertyValues();
    propsEnv = (HashMap<String,String>) propClass.getEnvironmentalPropValues();
    propsQuery = (HashMap<String,String>) propClass.getQueryPropValues();
}
public void setSystemProperties(){
    Properties sysProps = System.getProperties();
    sysProps.setProperty("geb.build.baseUrl", propsEnv.get("baseUrl"));
}

public void initOracleDriver() throws ClassNotFoundException{
    Class.forName("oracle.jdbc.driver.OracleDriver");
}

public String initDbUrlAndProps(Properties connection){
    connection.put("user", propsEnv.get("User"));
    connection.put("password", propsEnv.get("Password"));

    return "jdbc:oracle:thin:@(DESCIPTION =(ADDRESS = (PROTOCOL=TCP)(HOST=" + propsEnv.get("Host") + ")(PORT="
            + propsEnv.get("Port") + "))(CONNECT_DATA=(SERVICE_NAME=" + propsEnv.get("Name") + ")))";
}

public int getTestAmount() {
    return Integer.parseInt(propsQuery.get("testDataAmount"));
}

public static void main(String[] args) {

BotHQ botHQ = new BotHQ();
    botHQ.activateLoggers();
    Logger actionDetailLogger = LoggerFactory.getLogger(actionDetailLoggerName);
    Properties connectionProps = new Properties();

    try{
        botHQ.initProperties();
        botHQ.initOracleDriver();
        botHQ.setSystemProperties();
    } catch (IOException | ClassNotFoundException e){
        actionDetailLogger.error("Error Registering Database Driver or getting properties", e);
        System.exit(1);
    }

    int testDataAmount = botHQ.getTestAmount();
    try (Connection connect = DriverManager.getConnection(botHQ.initDbUrlAndProps(connectionProps),connectionProps);){
        List<Future<Integer>>  futures = new ArrayList<>();
        for (String ptCode : args) {
            futures.add(botMinions.submit(() -> {
                actionDetailLogger.info("Warning! botRunner Activating: prepare to recieve" + ProductDescription.getLobName(ptCode) + " data!");
                new botRunner(ptCode, testDataAmount, connect).seekValidData();
                return null;
            }));
        }
            while (stillRunning) {
                stillRunning = false;
                futures.forEach(future -> {
                    if (!future.isDone())
                        stillRunning = true;
                });
            }
        } catch (SQLException e){
            actionDetailLogger.error("Error connecting or querying", e);
        } finally {
            botMinions.shutdown();
            minionRunners.shutdown();
    }



}



}
