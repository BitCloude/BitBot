import geb.Browser
import org.slf4j.Logger

import java.time.LocalDateTime

/**
 * Created by AyvAndr on 4/1/2017.
 */
class BrowserTask {

    private Logger slf4jLogger;

     BrowserTask(Logger actionDetailLogger){
        slf4jLogger = actionDetailLogger;
    }

    boolean runBrowserTask(userData) {
        boolean isSuccessful = false
        userData[6] = ""
        Browser.drive() {
            try{
                via AuthenticationPage
                for(int i = 0; i < 5; i++){
                    if(isAt(UnavailablePage))
                        via AuthenticationPage
                    else break
                }
                 isAt AuthenticationPage
                login(userData)

                isAt PostLoginGenericPage
                //slf4jLogger.debug("Login Successful")

                if(getCurrentUrl().endsWith(((String) StatusSummaryPage.url))) {
                        isAt StatusSummaryPage
                    clickOnApplication(userData[5])
                }
                if(isAt(PostLoginGenericPage)){
                    if((title.indexOf("We're Sorry") == -1 && title.indexOf("Timed Out")== -1 && title.indexOf("Temporarily Unavailable")==-1 && !(getCurrentUrl().endsWith(((String) StatusSummaryPage.url))))){
                        isSuccessful = true;

                        if(getUploadStips()){
                            userData[6] += "| Upload |";
                        }
                        if(getReviewStips()){
                            userData[6] += "| Review |";
                        }
                        if(getReviewAndSignStips()){
                            userData[6] += "| Review And Sign |";
                        }

                    }
                }
            }catch (Exception e){
                slf4jLogger.error("Exception was thrown", e);
            }
            finally {
                return isSuccessful;
            }


        }

        return isSuccessful;

    }


}
