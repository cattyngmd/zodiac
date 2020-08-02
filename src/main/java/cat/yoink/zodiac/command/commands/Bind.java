package cat.yoink.zodiac.command.commands;

import cat.yoink.zodiac.command.manager.Command;
import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.module.ModuleManager;
import cat.yoink.zodiac.util.CommandUtil;
import org.lwjgl.input.Keyboard;

public class Bind extends Command
{
    public Bind()
    {
        super("Bind", "Bind a module to a key", new String[]{"b", "bind"});
    }

    @Override
    public void onCommand(String arguments)
    {
        if (arguments.equals(""))
        {
            CommandUtil.sendChatMessage(String.format("&7Usage: %sbind <Module> <Key>", CommandManager.getPrefix()));
            return;
        }


        boolean moduleFound = false;

        String[] arg = arguments.split(" ");
        int key = Keyboard.getKeyIndex(arg[1].toUpperCase());

        for (Module module : ModuleManager.getModules())
        {
            if (module.getName().equalsIgnoreCase(arg[0]))
            {
                try
                {
                    if (Keyboard.getKeyName(key).equals("NONE"))
                    {
                        module.setBind(Keyboard.KEYBOARD_SIZE);
                    }
                    else
                    {
                        module.setBind(key);

                    }
                    CommandUtil.sendChatMessage(String.format("bound %s to %s", module.getName(), Keyboard.getKeyName(key)));
                    moduleFound = true;
                }
                catch (Exception ignored)
                {
                }
            }
        }

        if (!moduleFound)
        {
            CommandUtil.sendChatMessage("&7Module not found");
        }


    }
}
