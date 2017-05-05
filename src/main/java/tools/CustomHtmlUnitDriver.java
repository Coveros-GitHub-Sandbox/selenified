package tools;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CustomHtmlUnitDriver extends HtmlUnitDriver {
    public CustomHtmlUnitDriver(BrowserVersion version) {
        super(version);
    }

    public CustomHtmlUnitDriver() {
        super();
    }

    public CustomHtmlUnitDriver(boolean enableJavascript) {
        super(enableJavascript);
    }

    public CustomHtmlUnitDriver(Capabilities capabilities) {
        super(capabilities);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected WebClient modifyWebClient(WebClient client) {
        //currently does nothing, but may be changed in future versions
        WebClient modifiedClient = super.modifyWebClient(client);

        modifiedClient.getOptions().setThrowExceptionOnScriptError(false);
        return modifiedClient;
    }
}