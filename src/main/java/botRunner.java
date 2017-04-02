import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DataBaseWorker;
import util.ExcelWriter;
import util.ProductDescription;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class botRunner implements Loggable{

    private String userDataLoggerName;
    private String actionDetailLoggerName;
    private Logger actionDetailLogger;
    private Logger userDataLogger;

    private String productionType;
    private int testAmount;
    private Connection connect;
    boolean stillRunning = true;

    private int verifiedUserDataIndex = 0;



    public botRunner(String productType, int testAmountm, Connection connect) {
        this.productionType = productType;
        this.testAmount = testAmount;
        this.connect = connect;
    }

    @Override
    public void activateLoggers() {
        Loggable.initializeLoggers(actionDetailLoggerName, userDataLoggerName);
    }

    private String getProductName() {
        return ProductDescription.getLobName(productionType);
    }
    public int incrementVerifiedUserData(){
        return ++verifiedUserDataIndex;
    }

    public void seekValidData() throws SQLException {

        String productName = getProductName();
        userDataLoggerName = productName + "UserDataLogger";
        actionDetailLoggerName = productName + "ActionDetailLogger";
        activateLoggers();
        actionDetailLogger = LoggerFactory.getLogger(actionDetailLoggerName);
        userDataLogger = LoggerFactory.getLogger(userDataLoggerName);

        String filePath = "./VerifiedTestData_" + productName + ".xlsx";

        DataBaseWorker dbWorker = new DataBaseWorker(productionType);
        List<String[]> userDataContainerDiag = dbWorker.runQueryforResult("diagnostic",connect);

        List<String[]> verifiedUserData = new ArrayList<>();

        ExcelWriter excelWriter = new ExcelWriter(actionDetailLogger, filePath);

        // userDataContainerDiag.forEach(stringArr ->
        //actionDetailLogger.debug(Arrays.toString(stringArr)));

        List<Future<Integer>> futures = new ArrayList<>();
        for( String[] userData : userDataContainerDiag) {
            futures.add(BotHQ.minionRunners.submit(() -> {
                if(new BrowserTask( actionDetailLogger).runBrowserTask(userData)) {
                    excelWriter.writeToWorkBook(userData,verifiedUserDataIndex);
                    incrementVerifiedUserData();
                    verifiedUserData.add(userData);
                    userDataLogger.info("USER DATA VERIFIED" + Arrays.toString(userData));
                }
                return null;
            }));
        }

            while (stillRunning) {
            stillRunning = false;
            futures.forEach(future -> {
                if (!future.isDone())
                    stillRunning = true;
            });
            if(verifiedUserDataIndex >= testAmount) {
                for(Future<Integer> future : futures) {
                    future.cancel(false);
                }
                break;
                }
            }
                /* MORE CODE FOR SECOND QUERY IF NEEDED*/

    }


}
