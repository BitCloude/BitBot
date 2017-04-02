import org.openqa.selenium.chrome.ChromeDriver

waiting {

    timeout = 10
    retryInterval = 0.5

    presets {
        oneMinute {
            timeout = 60
            retryInterval = 1
        }
    }
}


driver = {System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe")
     new ChromeDriver()}

environments{
    chrome{
        driver = {
            System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe")
            driver = new ChromeDriver()
        }
    }
}

quitCachedDriverOnShutdown = false
cacheDriverPerThread = true
//cacheDriver = true
baseNavigatorWaiting = true
atCheckWaiting = true
autoClearCookies = true

def envPath = System.getProperty("ccsspTestPath");

if(envPath){
    baseUrl = new ConfigSlurper().parse( new File("release_conf/" + envPath + "/baseurl/OASBaseUrl.groovy").toURI())
    ///BLA BLA
}


