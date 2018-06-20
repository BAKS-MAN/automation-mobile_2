package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeWorkTests extends CoreTestCase {

    private static final String name_of_folder = "Nu-metal bands";

    @Test
    public void testEx2Search(){
        MainPageObject MainPageObject = new MainPageObject(driver);
        MainPageObject.waitForElementAndClick(
                "xpath://*[contains(@text,'Search Wikipedia')]",
                "Cannot find Search Wikipedia input",
                5
        );
        WebElement search_element = MainPageObject.waitForElementPresent(
                "id:org.wikipedia:id/search_src_text",
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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Korn");
        SearchPageObject.clickByArticleWithSubstring("American nu-metal band");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Linkin park");
        SearchPageObject.clickByArticleWithSubstring("American rock band");

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addAnotherArticleToMyExistList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            try {Thread.sleep(5000);} catch (Exception e) {}
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        String article_to_delete = MyListsPageObject.getArticleByIndex("1");
        MyListsPageObject.swipeSpecificArticleToDelete(article_to_delete);
    }

    @Test
    public void testEx6AssertTitle(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Limp Bizkit");
        SearchPageObject.clickByArticleWithSubstring("American nu-metal band");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertTitlePresent();
    }
    @Test
    public void testEx9SearchWithTwoValues(){
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Linkin park");
        SearchPageObject.getAmountOfFoundArticles();
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("We found too few results!", amount_of_search_results >3);
        SearchPageObject.waitForElementByTitleAndDescription("Linkin Park","American rock band");

    }
}
