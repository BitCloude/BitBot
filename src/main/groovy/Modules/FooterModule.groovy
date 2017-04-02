package Modules

import geb.Module

/**
 * Created by AyvAndr on 4/1/2017.
 */
class FooterModule extends Module{

    static content = {
        footer {$('#footer')}
    }

    boolean exists(){
        footer;
    }
}
