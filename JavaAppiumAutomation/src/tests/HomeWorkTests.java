package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeWorkTests extends CoreTestCase {
    @Test
    public void testEx2Search(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );
        WebElement search_element = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input",
                5
        );
        String search_text = search_element.getAttribute("text");
        assertEquals(
                "Search input supposed to have placeholder text 'Search...'. But it has text: " + search_text,
                "Search…",
                search_text
        );
    }
    @Test
    public void testEx3CancelSearch() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia' input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Android",
                "Cannot find search input",
                5
        );

        List<WebElement> search_results = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        Integer results_quantity = search_results.size();
        assertTrue("Not enough articles in search results", results_quantity > 1);

//        waitForElementPresent(
//                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Android Oreo']"),
//                "One of articles was not found in search results",
//                5
//        );
        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/search_close_btn"),"Close button wasn't initialized");

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );
        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search results are still present on the page",
                5
        );
    }

    @Test
    public void testEx4CheckSearch(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia' input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Android",
                "Cannot find search input",
                5
        );
        List <WebElement> search_results = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        String search_keyword = driver.findElement(By.id("org.wikipedia:id/search_src_text")).getText().toLowerCase();
        for (WebElement results_titles : search_results) {
            String item_title = results_titles.getText().toLowerCase();
            assertTrue("Result title: '"+ item_title + "' doesn't contains search keyword: '"+ search_keyword + "'" , item_title.contains(search_keyword));
        }
        // Basic loop с отображением всех заголовков результатов переданных в List
//        for (int i = 0; i < search_results.size(); i++) {
//        WebElement results_titles = search_results.get(i);
//        String item_title = results_titles.getText();
//        System.out.println (item_title);
//        }
    }
    @Test
    public void testEx5saveTwoArticlesToMyList(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Korn",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'American nu-metal band']"),
                "Cannot find 'American nu-metal band' topic searching by 'Korn'",
                5
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                7
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );
//        MainPageObject.waitForMenuInit();
        MainPageObject.waitForElementAndClick(
//                By.xpath("//android.widget.LinearLayout[@index='2']"),
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );
        String name_of_folder = "Nu-metal bands";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Linkin park",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'American rock band']"),
                "Cannot find 'American rock band' topic searching by 'Linkin park'",
                5
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                7
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );
//        MainPageObject.waitForMenuInit();
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[@index='2']"),
                "Cannot find option to add article to reading list",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/item_container']//*[@text='" + name_of_folder + "']"),
                "Cannot find '"+ name_of_folder +"' saved list",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' navigation button",
                5
        );
        try {Thread.sleep(10000);} catch (Exception e) {}

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder: '"+ name_of_folder + "'",
                8
        );
        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Korn']"),
                "Cannot find saved article"
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Korn']"),
                "Cannot delete saved article",
                5
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Linkin Park']"),
                "Cannot find saved article",
                7
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Linkin Park']"),
                "Cannot open saved article",
                5
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                7
        );
    }
    @Test
    public void testEx6AssertTitle(){
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Korn",
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'American nu-metal band']"),
                "Cannot find 'American nu-metal band' topic searching by 'Korn'",
                5
        );
        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title not found"
        );
    }
}
