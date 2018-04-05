package sample;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;

final class MainPage {

    // our page elements
    private final Element click;
    private final Element alert;
    private final Element carList;
    public final Element checkbox;

    public MainPage(App app) {
        click = app.newElement(Locator.CLASSNAME, "click");
        alert = app.newElement(Locator.CSS, "input#alert_button");
        carList = app.newElement(Locator.ID, "car_list");
        checkbox = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
    }

    public void selectCar(String car) {
        carList.selectOption(car);
    }

    public void assertCar(String car) {
        carList.assertEquals().selectedOption(car);
    }

    public void generateAlert() {
        click.click();
        alert.click();
    }
}