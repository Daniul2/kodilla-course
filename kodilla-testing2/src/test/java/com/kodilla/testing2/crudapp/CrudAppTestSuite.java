package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrudAppTestSuite {

    private static final String BASE_URL = "https://daniul2.github.io/tasks-frontend/";
    private static final String TRELLO_API_KEY = System.getenv("TRELLO_API_KEY");
    private static final String TRELLO_API_TOKEN = System.getenv("TRELLO_API_TOKEN");
    private static final String TRELLO_BOARD_ID = "TbUTpPtq";

    private WebDriver driver;
    private final Random generator = new Random();

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
    }

    @AfterEach
    public void cleanUpAfterTest() {
        driver.close();
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);

        assertTrue(checkTaskExistsInTrello(taskName));

        deleteCrudAppTestTask(taskName);
    }

    private String createCrudAppTestTask() throws InterruptedException {
        final String XPATH_TASK_NAME = "//form[contains(@action,\"createTask\")]/fieldset[1]/input";
        final String XPATH_TASK_CONTENT = "//form[contains(@action,\"createTask\")]/fieldset[2]/textarea";
        final String XPATH_ADD_BUTTON = "//form[contains(@action,\"createTask\")]/fieldset[3]/button";

        String taskName = "Task number " + generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement addButton = driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(2000);

        return taskName;
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[1]")));

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });

        Thread.sleep(5000);

        createCardViaTrelloApi(taskName);
    }

    private void createCardViaTrelloApi(String taskName) {
        try {
            String listsUrl = "https://api.trello.com/1/boards/" + TRELLO_BOARD_ID + "/lists"
                    + "?key=" + TRELLO_API_KEY
                    + "&token=" + TRELLO_API_TOKEN;

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest listRequest = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(listsUrl))
                    .GET()
                    .build();

            java.net.http.HttpResponse<String> listResponse =
                    client.send(listRequest, java.net.http.HttpResponse.BodyHandlers.ofString());

            String body = listResponse.body();
            int idIndex = body.indexOf("\"id\":\"") + 6;
            String listId = body.substring(idIndex, body.indexOf("\"", idIndex));

            String cardUrl = "https://api.trello.com/1/cards"
                    + "?key=" + TRELLO_API_KEY
                    + "&token=" + TRELLO_API_TOKEN
                    + "&idList=" + listId
                    + "&name=" + java.net.URLEncoder.encode(taskName, "UTF-8");

            java.net.http.HttpRequest cardRequest = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(cardUrl))
                    .POST(java.net.http.HttpRequest.BodyPublishers.noBody())
                    .build();

            client.send(cardRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkTaskExistsInTrello(String taskName) {
        try {
            String url = "https://api.trello.com/1/boards/" + TRELLO_BOARD_ID + "/cards"
                    + "?key=" + TRELLO_API_KEY
                    + "&token=" + TRELLO_API_TOKEN;

            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .GET()
                    .build();

            java.net.http.HttpResponse<String> response =
                    client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            return response.body().contains(taskName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void deleteCrudAppTestTask(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[1]")));

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement deleteButton =
                            theForm.findElement(By.xpath(".//button[@data-task-delete-button]"));
                    deleteButton.click();
                });

        Thread.sleep(2000);
    }
}
