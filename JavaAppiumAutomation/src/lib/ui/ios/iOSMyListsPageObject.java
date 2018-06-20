package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        ARTICLE_BY_INDEX_TPL = "xpath://XCUIElementTypeCell[{INDEX}]";
        ARTICLE_BY_CONTENT_TPL = "xpath://XCUIElementTypeCell[{CONTENT}]";
    }
    public iOSMyListsPageObject(AppiumDriver driver){
        super(driver);
    }
}
