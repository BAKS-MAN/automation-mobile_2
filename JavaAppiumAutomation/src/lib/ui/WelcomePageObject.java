package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
        STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
        STEP_ADD_OR_EDIT_PREFFERED_LANG_LINK = "Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "Learn more about data collected",
        NEXT_LINK = "Next",
        GET_STARTED_BUTTON = "Get started",
        SKIP = "id:Skip";

    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }

    public void waitForLearnMoreLink(){
        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link",10);
    }

    public void waitForNewWayToExploreText(){
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link",10);
    }

    public void waitForAddorEditPreferredLangText(){
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFFERED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link",10);
    }

    public void waitForLearnMoreAboutDataCollectedText(){
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link",10);
    }


    public void clickNextButton(){
        this.waitForElementAndClick(NEXT_LINK, "Cannot find and click 'Next' link",10);
    }

    public void clicGetStartedButton(){
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Get started' button",10);
    }

    public void clickSkip(){
        this.waitForElementAndClick(SKIP, "Cannot find and click skip button",5);
    }
}
