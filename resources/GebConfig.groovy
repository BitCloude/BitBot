import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.chrome.ChromeOptions

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


driver = {
    System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe")

    path_to_extension = "D:\\Users\\AyvAndr\\IdeaProjects\\BitBotGod\\resources\\extensions\\2.8.6_0"
    ChromeOptions options = new ChromeOptions();
   // options.addArguments('load-extension=' + path_to_extension)

    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    capabilities.setCapability("pageLoadStrategy", "none");

    driver = new ChromeDriver(capabilities)
}

environments{
    chrome{
        driver = {
            System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe")

            path_to_extension = "D:\\Users\\AyvAndr\\IdeaProjects\\BitBotGod\\resources\\extensions\\2.8.6_0"
            ChromeOptions options = new ChromeOptions();
            options.addArguments('load-extension=' + path_to_extension)

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability("pageLoadStrategy", "none");

            driver = new ChromeDriver(capabilities)
        }
    }
}

quitCachedDriverOnShutdown = true
cacheDriverPerThread = true
//cacheDriver = true
baseNavigatorWaiting = true
atCheckWaiting = true
autoClearCookies = false

def envPath = System.getProperty("ccsspTestPath");

if(envPath){
    baseUrl = new ConfigSlurper().parse( new File("release_conf/" + envPath + "/baseurl/OASBaseUrl.groovy").toURI())
    ///BLA BLA
}


