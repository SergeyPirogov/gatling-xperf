package io.github.itishniki.javacmd.commands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import picocli.CommandLine;

@CommandLine.Command(
        name = "google", mixinStandardHelpOptions = true,
        description = "Search in Google"
)
public class GoogleSearchCommand implements Runnable {

    private static final String GOOGLE_BASE_URL = "https://google.com";

    @CommandLine.Parameters(
            description = "Search request"
    )
    protected String[] request;

    @Override
    public void run() {
        final WebDriver driver = new ChromeDriver();
        driver.get(GOOGLE_BASE_URL);
        driver.findElement(By.name("q")).sendKeys(String.join(" ", request));
        driver.findElement(By.name("q")).submit();
    }

}
