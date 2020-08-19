package cat.yoink.zodiac.gui;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.superblaubeere27.clickgui.IRenderer;
import me.superblaubeere27.clickgui.Window;
import me.superblaubeere27.clickgui.components.ActionEventListener;
import me.superblaubeere27.clickgui.components.Button;
import me.superblaubeere27.clickgui.components.CheckBox;
import me.superblaubeere27.clickgui.components.ComboBox;
import me.superblaubeere27.clickgui.components.KeybindButton;
import me.superblaubeere27.clickgui.components.Label;
import me.superblaubeere27.clickgui.components.Pane;
import me.superblaubeere27.clickgui.components.Slider;
import me.superblaubeere27.clickgui.components.Spoiler;
import me.superblaubeere27.clickgui.components.ValueChangeListener;
import me.superblaubeere27.clickgui.layout.GridLayout;
import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.setting.Setting;
import cat.yoink.zodiac.util.Consts;
import cat.yoink.zodiac.util.MouseUtil;
import cat.yoink.zodiac.util.GlyphPageFontRenderer;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {
    private final GlyphPageFontRenderer consolas;
    private final Pane spoilerPane;
    private final HashMap<Category, Pane> categoryPaneMap;
    private Window window;
    private IRenderer renderer;
    private List<ActionEventListener> onRenderListeners = new ArrayList<>();

    public ClickGUI() {
        consolas = GlyphPageFontRenderer.create("Consolas", 15, false, false, false);
        renderer = new BHRendererImpl(consolas);

        window = new Window(Consts.NAME + " BETA GUI", 50, 50, 900, 400);

        Pane conentPane = new me.superblaubeere27.clickgui.components.ScrollPane(renderer, new me.superblaubeere27.clickgui.layout.GridLayout(1));

        Pane buttonPane = new Pane(renderer, new me.superblaubeere27.clickgui.layout.FlowLayout());

        HashMap<Category, List<Module>> moduleCategoryMap = new HashMap<>();
        categoryPaneMap = new HashMap<>();

        for (Module module : Client.getInstance().getMasterManager().getModuleManager().getModules()) {
            if (!moduleCategoryMap.containsKey(module.getCategory())) 
                if(module.getCategory().isVisible())
                	moduleCategoryMap.put(module.getCategory(), new ArrayList<>());
            
            if(moduleCategoryMap.containsKey(module.getCategory()))
            	moduleCategoryMap.get(module.getCategory()).add(module);
        }

        HashMap<Category, Pane> paneMap = new HashMap<>();

        List<Spoiler> spoilers = new ArrayList<>();
        List<Pane> paneList = new ArrayList<>();

        for (Map.Entry<Category, List<Module>> moduleCategoryListEntry : moduleCategoryMap.entrySet()) {
            Pane spoilerPane = new Pane(renderer, new GridLayout(1));


            for (Module module : moduleCategoryListEntry.getValue()) {
                Pane settingPane = new Pane(renderer, new me.superblaubeere27.clickgui.layout.GridLayout(4));

                {
                    settingPane.addComponent(new Label(renderer, "State"));
                    CheckBox cb;
                    settingPane.addComponent(cb = new CheckBox(renderer, "Enabled"));
                    onRenderListeners.add(() -> cb.setSelected(module.getState()));
                    cb.setListener(val -> {
                        module.setState(val);
                        return true;
                    });
                }
                {
                    settingPane.addComponent(new Label(renderer, "Keybind"));
                    KeybindButton kb;
                    settingPane.addComponent(kb = new KeybindButton(renderer, Keyboard::getKeyName));
                    onRenderListeners.add(() -> kb.setValue(module.getKey()));

                    kb.setListener(val -> {
                        module.setKey(val);
                        System.out.println();
                        return true;
                    });
                }

                List<Setting> settings = Client.getInstance().getMasterManager().getSettingManager().getSettingsByMod(module);
                if (settings != null) {
                	for(Setting s : settings) {
                		if(s.isCheck()) {
                			 settingPane.addComponent(new Label(renderer, s.getName()));
                			 
                			 CheckBox cb;
                			 settingPane.addComponent(cb = new CheckBox(renderer, "Enabled"));
                			 cb.setListener(new ValueChangeListener<Boolean>() {
								@Override
								public boolean onValueChange(Boolean newValue) {
									s.setValBoolean(newValue);
									return true;
								}
							 });
                			 onRenderListeners.add(() -> cb.setSelected(s.bool()));
                		}else if(s.isCombo()) {
                			settingPane.addComponent(new Label(renderer, s.getName()));
                			
                			ComboBox cb;
                			settingPane.addComponent(cb = new ComboBox(renderer, s.getOptions(), s.getOptionIndex()));
                			cb.setListener(new ValueChangeListener<Integer>() {
								@Override
								public boolean onValueChange(Integer newValue) {
									String option = s.getOptions().get(newValue);
									s.setValString(option);
									return true;
								}
							});
                			onRenderListeners.add(() -> cb.setSelectedIndex(s.getOptionIndex()));
                		}else if(s.isSlider()) {
                			settingPane.addComponent(new Label(renderer, s.getName()));

                            Slider cb;
                            Slider.NumberType type = s.onlyInt() ? Slider.NumberType.INTEGER : Slider.NumberType.DECIMAL;
                            
                            settingPane.addComponent(cb = new Slider(renderer, s.doubl(), s.getMin(), s.getMax(), type));
                            cb.setListener(new ValueChangeListener<Number>() {
								@Override
								public boolean onValueChange(Number newValue) {
									s.setValDouble((double) newValue);
									return false;
								}
							});

                            onRenderListeners.add(() -> cb.setValue(s.doubl()));
                		}
                	}
                }

                Spoiler spoiler = new Spoiler(renderer, module.getName(), width, settingPane);

                paneList.add(settingPane);
                spoilers.add(spoiler);

                spoilerPane.addComponent(spoiler);

                paneMap.put(moduleCategoryListEntry.getKey(), spoilerPane);
            }

            categoryPaneMap.put(moduleCategoryListEntry.getKey(), spoilerPane);


        }


        spoilerPane = new Pane(renderer, new GridLayout(1));


        for (Category moduleCategory : categoryPaneMap.keySet()) {
            Button button;
            buttonPane.addComponent(button = new me.superblaubeere27.clickgui.components.Button(renderer, moduleCategory.toString()));
            button.setOnClickListener(() -> setCurrentCategory(moduleCategory));
        }

        conentPane.addComponent(buttonPane);

        int maxWidth = Integer.MIN_VALUE;

        for (Pane pane : paneList) {
            maxWidth = Math.max(maxWidth, pane.getWidth());
        }

        window.setWidth(28 + maxWidth);

        for (Spoiler spoiler : spoilers) {
            spoiler.preferredWidth = maxWidth;
            spoiler.setWidth(maxWidth);
        }

        spoilerPane.setWidth(maxWidth);
        buttonPane.setWidth(maxWidth);

        conentPane.addComponent(spoilerPane);

        conentPane.updateLayout();

        window.setContentPane(conentPane);


        if (categoryPaneMap.keySet().size() > 0)
            setCurrentCategory(categoryPaneMap.keySet().iterator().next());
    }

    private void setCurrentCategory(Category category) {
        spoilerPane.clearComponents();
        spoilerPane.addComponent(categoryPaneMap.get(category));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (ActionEventListener onRenderListener : onRenderListeners) {
            onRenderListener.onActionEvent();
        }

        Point point = MouseUtil.calculateMouseLocation();
        window.mouseMoved(point.x * 2, point.y * 2);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glLineWidth(1.0f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        window.render(renderer);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        window.mouseMoved(mouseX * 2, mouseY * 2);
        window.mousePressed(mouseButton, mouseX * 2, mouseY * 2);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        window.mouseMoved(mouseX * 2, mouseY * 2);
        window.mouseReleased(state, mouseX * 2, mouseY * 2);

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        window.mouseMoved(mouseX * 2, mouseY * 2);
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int eventDWheel = Mouse.getEventDWheel();

        window.mouseWheel(eventDWheel);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        window.keyPressed(keyCode, typedChar);
        super.keyTyped(typedChar, keyCode);
    }
}