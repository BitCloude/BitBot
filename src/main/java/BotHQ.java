import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.GetPropertyValues;
import util.ProductDescription;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class BotHQ implements Loggable {

private HashMap<String,String> propsEnv;
private HashMap<String,String> propsQuery;
static boolean stillRunning = true;
//static ExecutorService botMinions = Executors.newCachedThreadPool();
static ExecutorService minionRunners = Executors.newFixedThreadPool(6);
static ExecutorService commentCollector = Executors.newSingleThreadExecutor();

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
        //sysProps.setProperty("geb.build.baseUrl", propsEnv.get("baseUrl"));
        sysProps.setProperty("geb.build.baseUrl","http://imgur.com/");
    }

public int getTestAmount() {
        return Integer.parseInt(propsQuery.get("testDataAmount"));
}

public static void main(String[] args) {


    BotHQ botHQ = new BotHQ();
    Logger actionDetailLogger = LoggerFactory.getLogger(actionDetailLoggerName);
    botHQ.setSystemProperties();
    Boolean reachedEndOfComments = false;

    LinkedBlockingDeque<String> comment_links = new LinkedBlockingDeque<>();
    UserCommentCollector comCollector = new UserCommentCollector( actionDetailLogger, "TheRealDongLover");

    Future<ArrayList<String>>  commentCollector_future = null;
    List<Future<Integer>>  botRunner_futures = new ArrayList<>();


    //comCollector.collectUserCommentUrls().forEach(comment_links::addLast);

    while(reachedEndOfComments && comment_links.isEmpty()) {

        if (commentCollector_future == null && !reachedEndOfComments) {
            commentCollector_future = commentCollector.submit(() -> {
                actionDetailLogger.info("Running UserCommentCollector");
                return comCollector.collectUserCommentUrls();
            });
        }

        if (commentCollector_future.isDone()) {
            try {
                if (commentCollector_future.get() != null)
                    commentCollector_future.get().forEach(comment_links::addLast);
                else
                    reachedEndOfComments = true;
            } catch (InterruptedException | ExecutionException e) {
                actionDetailLogger.error("This thread was interrupted", e);
            } finally {
                commentCollector_future = null;
            }
        }


        if (comment_links.peekFirst() != null) {
            botRunner_futures.add(minionRunners.submit(() -> {
                actionDetailLogger.info("Warning! botRunner Activating");
                new BrowserTask(actionDetailLogger).runBrowserTask(comment_links.pollFirst());
                return null;
            }));
        }


    }

    while (stillRunning) {
        stillRunning = false;
        botRunner_futures.forEach(future -> {
            if (!future.isDone())
                stillRunning = true;
        });
    }


}



}

/*
   BotHQ botHQ = new BotHQ();
    botHQ.activateLoggers();
    Logger actionDetailLogger = LoggerFactory.getLogger(actionDetailLoggerName);
    Properties connectionProps = new Properties();

    try{
        botHQ.initProperties();
        botHQ.setSystemProperties();
    } catch (IOException e){
        actionDetailLogger.error("Error getting properties", e);
        System.exit(1);
    }
*//*
    int testDataAmount = botHQ.getTestAmount();

        List<Future<Integer>>  futures = new ArrayList<>();
        for (String ptCode : args) {
            futures.add(botMinions.submit(() -> {
                actionDetailLogger.info("Warning! botRunner Activating: prepare to recieve" + ProductDescription.getLobName(ptCode) + " data!");
                new botRunner().seekValidData();
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
            botMinions.shutdown();
            minionRunners.shutdown();
    }
*/