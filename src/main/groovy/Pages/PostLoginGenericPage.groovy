package Pages

import Modules.FooterModule
import geb.Page

/**
 * Created by AyvAndr on 4/1/2017.
 */
class PostLoginGenericPage extends Page{

    static at = {
        footer.exists()
        ((title.indexOf("Your Application Status ") > 0 || title.indexOf("yourLoanTracker")>=0) &&
                (progressHeading.text() == "Progress-at-a-Glance" ))
    }
    static content = {

        footer {module FooterModule}
        progressHeading(required: false) {$("h2.progressHeading")}
        stipulations(required: false) {$("#actionItems a.uploadReviewTrigger")}
    }

    boolean getUploadStips(){
        getStips("Upload")
    }

    boolean getStips(def type){
        if(stipulations.size() != 0){
            for(def stip: stipulations){
                if(stip.text().equals(type)){
                    return true
                }
            }
        }
    }

}
