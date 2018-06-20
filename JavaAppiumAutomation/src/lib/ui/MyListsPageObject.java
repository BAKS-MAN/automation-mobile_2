package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            ARTICLE_BY_INDEX_TPL,
            ARTICLE_BY_CONTENT_TPL;

    /*TEMPLATES METHODS */
    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitile(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    private static String getSavedArticleXpathByIndex(String article_index){
        return ARTICLE_BY_INDEX_TPL.replace("{INDEX}", article_index);
    }
    private static String getSavedArticleXpathByContent(String article_to_delete_content){
        return ARTICLE_BY_CONTENT_TPL.replace("{CONTENT}", article_to_delete_content);
    }
    /*TEMPLATES METHODS */

    public MyListsPageObject (AppiumDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find created folder: '" + name_of_folder + "'",
                5
        );
    }
    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitile(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title '" + article_title + "'",10);
    }

    public void swipeArticleToDelete (String article_title){
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitile(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );
        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath,"Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitile(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title '" + article_title + "'",10);
    }
    public void openSavedArticle(String saved_article){
        String article_xpath = getSavedArticleXpathByTitile(saved_article);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot open saved: '" + saved_article + "'",
                5
        );
    }
    public String getArticleByIndex(String article_index){
        String saved_article_xpath = getSavedArticleXpathByIndex(article_index);
        return saved_article_xpath;
    }
    public void swipeSpecificArticleToDelete (String article_to_delete){
        WebElement article_to_delete_content = driver.findElementByXPath(article_to_delete);
        if (Platform.getInstance().isAndroid()){
            return article_to_delete_content.getAttribute("text");
        }else {
            return article_to_delete_content.getAttribute("name");
        }
        System.out.println(article_to_delete_content);
        this.swipeElementToLeft(
                article_to_delete,
                "Cannot find saved article"
        );
        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_to_delete,"Cannot find saved article");
        }
        this.waitForArticleToDisappearByContent(article_to_delete_content);
    }
    public void waitForArticleToDisappearByContent(String article_to_delete_content){
        String article_xpath = getSavedArticleXpathByContent(article_to_delete_content);
        this.waitForElementNotPresent(article_xpath, "Saved article still present",10);
    }
}
