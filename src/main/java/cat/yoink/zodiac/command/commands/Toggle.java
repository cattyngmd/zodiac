package cat.yoink.zodiac.command.commands;

import cat.yoink.zodiac.command.manager.Command;
import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.util.CommandUtil;

public class Toggle extends Command
{
    public Toggle()
    {
        super("Toggle", "Toggle a module", new String[]{"t", "toggle"});
    }

    @Override
    public void onCommand(String arguments)
    {
        if (arguments.equals(""))
        {
            CommandUtil.sendChatMessage(String.format("&7Usage: %st <Module>", CommandManager.getPrefix()));
            return;
        }

        boolean foundModule = false;
        Module module = ModuleManager.getModuleByName(arguments);

        if (module != null)
        {
            module.toggle();
            foundModule = true;
        }

        if (!foundModule)
        {
            CommandUtil.sendChatMessage("&cUnable to find module");
        }
    }
}
