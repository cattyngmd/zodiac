package cat.yoink.zodiac.gui;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL41.glClearDepthf;

import me.superblaubeere27.clickgui.IRenderer;
import cat.yoink.zodiac.util.GLUtil;
import cat.yoink.zodiac.util.GuiUtils;
import cat.yoink.zodiac.util.GlyphPageFontRenderer;

public class BHRendererImpl implements IRenderer {
	
    private GlyphPageFontRenderer renderer;

    public BHRendererImpl(GlyphPageFontRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void drawRect(double x, double y, double w, double h, Color c) {
        GuiUtils.getInstance().drawRect(x / 2.0, y / 2.0, x / 2.0 + w / 2.0, y / 2.0 + h / 2.0, GLUtil.toRGBA(c));
    }

    @Override
    public void drawOutline(double x, double y, double w, double h, float lineWidth, Color c) {
        glLineWidth(lineWidth);
        GuiUtils.getInstance().drawBorderedRect(x / 2.0, y / 2.0, x / 2.0 + w / 2.0, y / 2.0 + h / 2.0, GLUtil.toRGBA(c), 0x00000000);
    }

    @Override
    public void setColor(Color c) {
        GLUtil.setColor(c);
    }

    @Override
    public void drawString(int x, int y, String text, Color color) {
        renderer.drawString(text, x / 2f, y / 2f, GLUtil.toRGBA(color), false);
    }

    @Override
    public int getStringWidth(String str) {
        return (int) (renderer.getStringWidth(str) * 2);
    }

    @Override
    public int getStringHeight(String str) {
        return renderer.getFontHeight() * 2;
    }

    @Override
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {}

    @Override
    public void initMask() {
        glClearDepthf(1.0f);
        glClear(GL_DEPTH_BUFFER_BIT);
        glColorMask(false, false, false, false);
        glDepthFunc(GL_LESS);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
    }

    @Override
    public void useMask() {
        glColorMask(true, true, true, true);
        glDepthMask(true);
        glDepthFunc(GL_EQUAL);
    }

    @Override
    public void disableMask() {
        glClearDepthf(1.0f);
        glClear(GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        glDisable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glDepthMask(false);
    }
}