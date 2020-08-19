package cat.yoink.zodiac.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.gui.Gui;

public class GuiUtils {
	
	public void drawCheck(int x, int y, Color c)
    {
//		GL11.glScalef(1.2F, 1.2F, 1.2F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x+1, y+4);
        GL11.glVertex2f(x+3, (float)y+6.5f);
        GL11.glVertex2f(x+7, y+2);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glScalef(1, 1, 1);
    }
	
	public void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
    	x *= 2; y *= 2; x1 *= 2; y1 *= 2;
    	GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 1, y1 -2, borderC);
        drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawHLine(x + 2, x1 - 3, y, borderC);
        drawHLine(x + 2, x1 - 3, y1 -1, borderC);
        drawHLine(x + 1, x + 1, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawHLine(x + 1, x + 1, y1 - 2, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawBorderedRect(float x, float y, float x1, float y1, int borderC, int insideC)
	{
		x *= 2; x1 *= 2; y *= 2; y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawVLine(x, y, y1 - 1, borderC);
		drawVLine(x1 - 1, y , y1, borderC);
		drawHLine(x, x1 - 1, y, borderC);
		drawHLine(x, x1 - 2, y1 -1, borderC);
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	
	public void drawBorderedRect(double d, double e, double f, double g, int borderC, int insideC)
	{
		d *= 2; f *= 2; e *= 2; g *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawVLine((float)d, (float)e, (float)g - 1, borderC);
		drawVLine((float)f - 1, (float)e , (float)g, borderC);
		drawHLine((float)d, (float)f - 1, (float)e, borderC);
		drawHLine((float)d, (float)f - 2, (float)g -1, borderC);
		drawRect((float)d + 1, (float)e + 1, (float)f - 1, (float)g - 1, insideC);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	
	public void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        drawRect((float)x, (float)y, (float)x2, (float)y2, col2);

        float f = (float)(col1 >> 24 & 0xFF) / 255F;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
        float f3 = (float)(col1 & 0xFF) / 255F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public void drawHLine(float par1, float par2, float par3, int par4)
	{
		if (par2 < par1)
		{
			float var5 = par1;
			par1 = par2;
			par2 = var5;
		}

		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}

	public void drawVLine(float par1, float par2, float par3, int par4)
	{
		if (par3 < par2)
		{
			float var5 = par2;
			par2 = par3;
			par3 = var5;
		}

		drawRect(par1, par2 + 1, par1 + 1, par3, par4);
	}

	public void drawRect(double paramXStart, double paramYStart, double paramXEnd, double paramYEnd, int paramColor)
	{
		Gui.drawRect((int)paramXStart, (int)paramYStart, (int)paramXEnd, (int)paramYEnd, paramColor);
		
//		float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
//		float red = (float)(paramColor >> 16 & 0xFF) / 255F;
//		float green = (float)(paramColor >> 8 & 0xFF) / 255F;
//		float blue = (float)(paramColor & 0xFF) / 255F;
//
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glEnable(GL11.GL_LINE_SMOOTH);
//
//		GL11.glPushMatrix();
//		GL11.glColor4f(red, green, blue, alpha);
//		GL11.glBegin(GL11.GL_QUADS);
//		GL11.glVertex2d(paramXEnd, paramYStart);
//		GL11.glVertex2d(paramXStart, paramYStart);
//		GL11.glVertex2d(paramXStart, paramYEnd);
//		GL11.glVertex2d(paramXEnd, paramYEnd);
//		GL11.glEnd();
//		GL11.glPopMatrix();
//
//		GL11.glEnable(GL11.GL_TEXTURE_2D);
//		GL11.glDisable(GL11.GL_BLEND);
//		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) 
	{
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		float f7 = (float)(col2 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	public void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
	{
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		drawGradientRect(x, y, x2, y2, col2, col3);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public void drawStrip(int x, int y, float width, double angle, float points, float radius, int color)
	{
		GL11.glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(f2, f3, f4, f1);
		GL11.glLineWidth(width);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL13.GL_MULTISAMPLE);

		if (angle > 0)
		{
			GL11.glBegin(GL11.GL_LINE_STRIP);

			for (int i = 0; i < angle; i++)
			{
				float a = (float)(i * (angle * Math.PI / points));
				float xc = (float)(Math.cos(a) * radius);
				float yc = (float)(Math.sin(a) * radius);
				GL11.glVertex2f(xc, yc);
			}

			GL11.glEnd();
		}

		if (angle < 0)
		{
			GL11.glBegin(GL11.GL_LINE_STRIP);

			for (int i = 0; i > angle; i--)
			{
				float a = (float)(i * (angle * Math.PI / points));
				float xc = (float)(Math.cos(a) * -radius);
				float yc = (float)(Math.sin(a) * -radius);
				GL11.glVertex2f(xc, yc);
			}

			GL11.glEnd();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL13.GL_MULTISAMPLE);
		GL11.glDisable(GL11.GL_MAP1_VERTEX_3);
		GL11.glPopMatrix();
	}


	public void drawCircle(float cx, float cy, float r, int num_segments, int c)
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		r *= 2;
		cx *= 2;
		cy *= 2;
		float f = (float) (c >> 24 & 0xff) / 255F;
		float f1 = (float) (c >> 16 & 0xff) / 255F;
		float f2 = (float) (c >> 8 & 0xff) / 255F;
		float f3 = (float) (c & 0xff) / 255F;
		float theta = (float) (2 * 3.1415926 / (num_segments));
		float p = (float) Math.cos(theta);//calculate the sine and cosine
		float s = (float) Math.sin(theta);
		float t;
		GL11.glColor4f(f1, f2, f3, f);
		float x = r;
		float y = 0;//start at angle = 0
				GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int ii = 0; ii < num_segments; ii++)
		{
			GL11.glVertex2f(x + cx, y + cy);//final vertex vertex

			//rotate the stuff
			t = x;
			x = p * x - s * y;
			y = s * t + p * y;
		}
		GL11.glEnd();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glScalef(2F, 2F, 2F);
	}

	public void drawFullCircle(int cx, int cy, double r, int c) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		r *= 2;
		cx *= 2;
		cy *= 2;
		float f = (float) (c >> 24 & 0xff) / 255F;
		float f1 = (float) (c >> 16 & 0xff) / 255F;
		float f2 = (float) (c >> 8 & 0xff) / 255F;
		float f3 = (float) (c & 0xff) / 255F;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		for (int i = 0; i <= 360; i++) {
			double x = Math.sin((i * Math.PI / 180)) * r;
			double y = Math.cos((i * Math.PI / 180)) * r;
			GL11.glVertex2d(cx + x, cy + y);
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glScalef(2F, 2F, 2F);
	}
	
	private static GuiUtils derp = new GuiUtils();
	
	public static GuiUtils getInstance(){
		return derp;
	}
}
