package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{
    public static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OKAY_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        MY_LIST_FOLDER_BY_NAME_TPL = "//*[@text='{MY_LIST_FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /*TEMPLATES METHODS */
    private static String getMyListFolderXpathByName(String name_of_folder){
        return MY_LIST_FOLDER_BY_NAME_TPL.replace("{MY_LIST_FOLDER_NAME}", name_of_folder);
    }
    /*TEMPLATES METHODS */

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE),"Cannot find article title on page",5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void assertTitlePresent(){
        this.assertElementPresent(By.id(TITLE),"Title not found");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of the article",
                10
        );
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForMenuInit();

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                5
        );
        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                5
        );
        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OKAY_BUTTON),
                "Cannot press 'OK' button",
                5
        );
    }
    public void closeArticle(){
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                10
        );
    }
    public void waitForMenuInit(){
        waitForElementPresent(By.xpath("//*[@text='Change language']"),"Menu wasn't initialized");
        waitForElementPresent(By.xpath("//*[@text='Share link']"),"Menu wasn't initialized");
        waitForElementPresent(By.xpath("//*[@text='Add to reading list']"),"Menu wasn't initialized");
        waitForElementPresent(By.xpath("//*[@text='Find in page']"),"Menu wasn't initialized");
        waitForElementPresent(By.xpath("//*[@text='Similar pages']"),"Menu wasn't initialized");
        waitForElementPresent(By.xpath("//*[@text='Font and theme']"),"Menu wasn't initialized");
    }
    public void addAnotherArticleToMyExistList(String name_of_folder){
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );
        this.waitForMenuInit();
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );
        String my_list_folder_name_xpath = getMyListFolderXpathByName (name_of_folder);
        this.waitForElementAndClick(
                By.xpath(my_list_folder_name_xpath),
                "Cannot find created list: '" + name_of_folder + "'",
                5
        );
    }
}
