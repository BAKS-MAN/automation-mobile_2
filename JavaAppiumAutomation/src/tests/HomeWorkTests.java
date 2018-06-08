package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeWorkTests extends CoreTestCase {
    @Test
    public void testEx2Search(){
        MainPageObject MainPageObject = new MainPageObject(driver);
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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Linkin park");
        SearchPageObject.getAmountOfFoundArticles();
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("Not enough articles in search results", amount_of_search_results >1);
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForSearchResultToDisappear();
    }

    @Test
    public void testEx4CheckSearch(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Android");

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
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForSearchInputInit();
        SearchPageObject.typeSearchLine("Korn");
        SearchPageObject.clickByArticleWithSubstring("American nu-metal band");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Nu-metal bands";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();
        try {Thread.sleep(5000);} catch (Exception e) {}
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForSearchInputInit();
        SearchPageObject.typeSearchLine("Linkin park");
        SearchPageObject.clickByArticleWithSubstring("American rock band");
        ArticlePageObject.waitForTitleElement();
        String second_article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addAnotherArticleToMyExistList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject (driver);
        try {Thread.sleep(5000);} catch (Exception e) {}
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeArticleToDelete(first_article_title);
        MyListsPageObject.waitForArticleToApearByTtile(second_article_title);
        MyListsPageObject.openArticleByName(second_article_title);
        ArticlePageObject.waitForTitleElement();
        String actual_article_title = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected article title!", second_article_title, actual_article_title);

    }
    @Test
    public void testEx6AssertTitle(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForSearchInputInit();
        SearchPageObject.typeSearchLine("Limp Bizkit");
        SearchPageObject.clickByArticleWithSubstring("American nu-metal band");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertTitlePresent();
    }
}
