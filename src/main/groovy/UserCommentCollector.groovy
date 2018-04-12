import Pages.ImgurUserCommentsPage
import geb.Browser
import org.slf4j.Logger

/**
 * Created by AyvAndr on 4/1/2017.
 */
class UserCommentCollector {

    private Logger slf4jLogger;
    private String user;
    private static index = 0;

    UserCommentCollector(Logger actionDetailLogger, String _user) {
        slf4jLogger = actionDetailLogger
        user = _user
    }

    ArrayList<String> collectUserCommentUrls() {

        ArrayList<String> commentUrls = new ArrayList<>()
        Browser.drive() {
            try {
                page(ImgurUserCommentsPage)
                if (index == 0) {
                    setUser(user)
                    to ImgurUserCommentsPage
                }
                commentUrls = getCommentURLLoadSet(index++)
                prepareNextSet(getBrowser())
                commentUrls.forEach { url -> println(url) }
            } catch (Exception e) {
                slf4jLogger.error("Exception was thrown", e);
            }
        }
        if (commentUrls.isEmpty()) {
            return null
        }
        return commentUrls

    }
}
