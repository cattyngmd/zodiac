package cat.yoink.zodiac.command.commands;

import cat.yoink.zodiac.command.manager.Command;
import cat.yoink.zodiac.command.manager.CommandManager;
import cat.yoink.zodiac.util.CommandUtil;

public class Prefix extends Command
{
    public Prefix()
    {
        super("Prefix", "Set the command prefix", new String[]{"prefix"});
    }

    @Override
    public void onCommand(String arguments)
    {
        if (arguments.equals(""))
        {
            CommandUtil.sendChatMessage(String.format("&7Usage: %sprefix <Char>", CommandManager.getPrefix()));
        }

        CommandManager.setPrefix(arguments);
        CommandUtil.sendChatMessage(String.format("&7Set prefix to %s", arguments));
    }
}
