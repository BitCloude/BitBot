package Pages

import Modules.FooterModule
import geb.Browser
import geb.Page
import geb.navigator.Navigator
import geb.waiting.Wait
import geb.waiting.WaitTimeoutException
import jodd.util.ArraysUtil

/**
 * Created by AyvAndr on 4/1/2017.
 */
class ImgurUserCommentsPage extends Page {

        public static user = ""

        static url =  "user/"

        static at = {
            footer.exists()
            title.indexOf(user + " on Imgur") != -1
        }

        static content = {
            footer {module FooterModule}
            comment_links_element {$('.permalink-caption-link')}
            comment_links_value {$('.permalink-caption-link').@href}
            comment_load_sets( required: false){$(".captions-container > div >div")}
            //comment_load_urls( wait: true, required: false) {int index -> comment_load_sets.eq(index)}
        }

        void setUser(String value){
            user = value
            url += user + "/"
        }

        ArrayList<String> getCommentURLLoadSet(int index) {
            ArrayList<String> permLinkArr = new ArrayList<>()
            try{
            waitFor{comment_load_sets[index].isDisplayed()}}
            catch (WaitTimeoutException e){}

            if(comment_load_sets[index]){
            def permLinksNav = comment_load_sets[index].find('a.permalink-caption-link')
            permLinksNav.forEach{link -> permLinkArr.add(link.@href)}
            }
            return permLinkArr
    }

        boolean prepareNextSet(Browser browser) {
            browser.drive() {
                interact {
                    moveToElement(footer.footer)
                }
            }
        }




    }





