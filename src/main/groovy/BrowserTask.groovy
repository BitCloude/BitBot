import Pages.CommentThreadPage
import geb.Browser
import org.slf4j.Logger

/**
 * Created by AyvAndr on 4/1/2017.
 */
class BrowserTask {

    private Logger slf4jLogger;

     BrowserTask(Logger actionDetailLogger){
        slf4jLogger = actionDetailLogger;
    }

    void runBrowserTask(String url, boolean isUpVote, String user) {

        Browser.drive() {
            try{
                page(CommentThreadPage)
                setUrl(url)
                setUser(user)
                to CommentThreadPage

                if(!isCorrectUser(user)){
                    return
                }
                if(isUpVote) {
                    upvote()
                    System.out.println("UPVOTING " + url)
                }
                else{
                    downvote()
                    System.out.println("DOWNVOTING " + url)
                }

            }catch (Exception e){
                slf4jLogger.error("Exception was thrown", e);
            }


        }


    }


}
