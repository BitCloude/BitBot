import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public interface Loggable {

      String actionDetailLoggerName = "ActionLogger";
      String userDataLoggerName = "UserDataLogger";



     default void activateLoggers(){
        initializeLoggers(actionDetailLoggerName,userDataLoggerName);

    }

     static void initializeLoggers(String actionDetailLoggerName, String userDataLoggerName){
       Logger actionDetailLogger = LoggerFactory.getLogger(actionDetailLoggerName);
        Logger userDetailLogger = LoggerFactory.getLogger(userDataLoggerName);
    }


}
