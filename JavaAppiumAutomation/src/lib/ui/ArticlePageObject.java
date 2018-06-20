package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import lib.Platform;

abstract public class ArticlePageObject extends MainPageObject{
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OKAY_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        MY_LIST_FOLDER_BY_NAME_TPL;

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /*TEMPLATES METHODS */
    private static String getMyListFolderXpathByName(String name_of_folder){
        return MY_LIST_FOLDER_BY_NAME_TPL.replace("{MY_LIST_FOLDER_NAME}", name_of_folder);
    }

    private static String getArticleTitleXpath(String article_title){
        return TITLE.replace("{TITLE}", article_title);
    }

    /*TEMPLATES METHODS */

    public WebElement waitForTitleElement(){
            return this.waitForElementPresent(TITLE, "Cannot find article title on page", 5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else {
            return title_element.getAttribute("name");
        }

    }

    public void assertTitlePresent(){
        this.assertElementPresent(TITLE,"Title not found");
    }

    public void swipeToFooter(){
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    20
            );
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
            "Cannot find the end of article",
            40);
        }
    }

    public void addArticleToMyList(String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForMenuInit();

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OKAY_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void addArticlesToMySaved(){
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to ad article to reading list",10);
    }

    public void closeArticle(){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                10
        );
    }
    public void waitForMenuInit(){
        waitForElementPresent("xpath://*[@text='Change language']","Menu wasn't initialized");
        waitForElementPresent("xpath://*[@text='Share link']","Menu wasn't initialized");
        waitForElementPresent("xpath://*[@text='Add to reading list']","Menu wasn't initialized");
        waitForElementPresent("xpath://*[@text='Find in page']","Menu wasn't initialized");
        waitForElementPresent("xpath://*[@text='Similar pages']","Menu wasn't initialized");
        waitForElementPresent("xpath://*[@text='Font and theme']","Menu wasn't initialized");
    }
    public void addAnotherArticleToMyExistList(String name_of_folder){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );
        this.waitForMenuInit();
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        String my_list_folder_name_xpath = getMyListFolderXpathByName (name_of_folder);
        this.waitForElementAndClick(
                my_list_folder_name_xpath,
                "Cannot find created list: '" + name_of_folder + "'",
                5
        );
    }
}
