package sample;

import com.coveros.selenified.selenium.App;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.selenium.element.Element;

public final class MainPage {

    // our page elements
    private Element click;
    private Element alert;
    private Element carList;
    public Element checkbox;

    public MainPage(App app) {
        click = app.newElement(Locator.CLASSNAME, "click");
        alert = app.newElement(Locator.CSS, "input#alert_button");
        carList = app.newElement(Locator.ID, "car_list");
        checkbox = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
    }

    public void selectCar(String car) {
        carList.select(car);
    }

    public void assertCar(String car) {
        carList.assertEquals().selectedOption(car);
    }

    public void generateAlert() {
        click.click();
        alert.click();
    }
}