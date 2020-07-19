package cat.yoink.zodiac.gui;

import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {

    int X = 100;
    int Y = 100;
    int W = 200;
    int H = 300;

    int xB = 0;
    int yB = 0;

    boolean d = false;

    @Override
    public void drawScreen(int mX, int mY, float partialTicks) {
        if (d) {
            X = mX - xB;
            Y = mY - yB;
        }

        drawRect(X, Y, X + W, Y + H, 0xff000000);

    }

    @Override
    protected void mouseClicked(int mX, int mY, int mouseButton) {

        if (isHover(X, Y, W, 20, mX, mY)) {
            d = true;

            xB = mX - X;
            yB = mY - Y;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        d = false;
    }

    public boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
}