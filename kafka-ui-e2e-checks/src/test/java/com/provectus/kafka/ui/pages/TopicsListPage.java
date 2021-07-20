package com.provectus.kafka.ui.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.provectus.kafka.ui.helpers.Utils.refreshUntil;

public class TopicsListPage {
    public static final String path = "ui/clusters/secondLocal/topics";

    @Step
    public TopicsListPage shouldBeOnPage() {
        $(By.xpath("//*[contains(text(),'Loading')]")).shouldBe(Condition.disappear);
        $(By.xpath("//span[text()='All Topics']")).shouldBe(Condition.visible);
        return this;
    }

    @SneakyThrows
    public TopicsListPage openTopic(String topicName) {
        refreshUntil(By.xpath("//div[contains(@class,'section')]//table//a[text()='%s']".formatted(topicName)));
        $(By.xpath("//div[contains(@class,'section')]//table//a[text()='%s']".formatted(topicName)))
                .click();
        return this;
    }

}
