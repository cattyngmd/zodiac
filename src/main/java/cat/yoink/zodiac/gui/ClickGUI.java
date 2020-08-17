package cat.yoink.zodiac.gui;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.*;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{

    boolean D = false;
    Category draggingCategory = null;

    int xB = 0;
    int yB = 0;


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        for (Category category : Category.values())
        {

            int X = category.getX();
            int Y = category.getY();

            if (D && draggingCategory == category)
            {

                category.setX(mouseX - xB);
                category.setY(mouseY - yB);

            }


            drawRect(X, Y, X + 100, Y + 15, 0xffcc0c0c);
            drawCenteredString(mc.fontRenderer, category.getName(), X + 50, Y + 4, 0xffffffff);

            for (Module module : Client.getInstance().moduleManager.getModules())
            {

                if (!module.getCategory().equals(category)) continue;

                Y += 15;


                drawRect(X, Y, X + 100, Y + 15, 0xff990808);

                int c;
                if (module.isEnabled()) c = 0xffffffff;
                else c = 0xff999999;

                drawCenteredString(mc.fontRenderer, module.getName(), X + 50, Y + 4, c);

            }

        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton != 0) return;

        for (Category category : Category.values())
        {

            int X = category.getX();
            int Y = category.getY();

            if (isHover(X, Y, 100, 15, mouseX, mouseY))
            {

                draggingCategory = category;
                D = true;

                xB = mouseX - category.getX();
                yB = mouseY - category.getY();

            }

            for (Module module : Client.getInstance().moduleManager.getModules())
            {

                Y += 15;

                if (isHover(X, Y, 100, 15, mouseX, mouseY))
                {

                    module.toggle();

                }

            }

        }

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {

        D = false;
        draggingCategory = null;

    }

    private boolean isHover(int X, int Y, int W, int H, int mX, int mY)
    {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }

}
