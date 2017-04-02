package Pages

import geb.Page

/**
 * Created by AyvAndr on 4/2/2017.
 */
class CommentThreadPage extends Page {

    //public static user = ""

    static url =  ""

    static at = {
        footerExists()
        title.indexOf("Album on Imgur") != -1
    }
    static content = {
        footer {$('#fixed-side-footer-links')}
        upvote_button {$('.icon-upvote-fill').eq(0)}
        downvote_button {$('.icon-downvote-fill').eq(0)}
        username {$('a.comment-username').eq(0)}
    }

    void setUrl(String value){
        //user = value
        url == value
    }

     boolean footerExists(){
        footer
    }

    void upvote(){
        upvote_button.click();
    }
    void downvote(){
        downvote_button.click();
    }
    Boolean isCorrectUser(String user){
        return username.text().equals(user)
    }

}
