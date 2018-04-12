package Pages

import geb.Page

/**
 * Created by AyvAndr on 4/2/2017.
 */
class CommentThreadPage extends Page {

    public static user = ""

    static url =  ""

    static at = {
        footerExists()
        usernameExists()
        CommentsLoaded()
        title.indexOf("Album on Imgur") != -1

    }
    static content = {
        footer {$('#fixed-side-footer-links')}
        upvote_button {$('.icon-upvote-fill').eq(0)}
        downvote_button {$('.icon-downvote-fill').eq(0)}
        //username {$('a.comment-username').eq(0)}
        username {$('a.comment-username', title: user).eq(0)}
        commentsLoaded {$('div.usertext')}
    }

    void setUrl(String value){
        url = value
    }

    void setUser(String username){
        user = username
    }

     boolean footerExists(){
        footer
    }
    boolean usernameExists(){
        username
    }
    boolean CommentsLoaded(){
        commentsLoaded
    }

    void upvote(){
        //upvote_button.click();
    }
    void downvote(){
        downvote_button.click();
    }
    Boolean isCorrectUser(String user){
        return username.text() == user
    }

}
