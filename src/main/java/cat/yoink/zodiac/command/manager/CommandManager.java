package cat.yoink.zodiac.command.manager;

import cat.yoink.zodiac.command.commands.Bind;
import cat.yoink.zodiac.command.commands.Prefix;
import cat.yoink.zodiac.command.commands.Toggle;
import cat.yoink.zodiac.util.CommandUtil;

import java.util.ArrayList;

public class CommandManager
{
    public static ArrayList<Command> commands = new ArrayList<>();
    public static String prefix = ".";

    public static void initialize()
    {
        commands.add(new Toggle());
        // commands.add(new Set());
        commands.add(new Prefix());
        commands.add(new Bind());
    }

    public static void onCommand(String input)
    {
        boolean commandFound = false;
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String startCommand = split[0];
        String args = input.substring(startCommand.length()).trim();

        for (Command command : commands)
        {
            for (String alias : command.getAlias())
            {
                if (startCommand.equals(getPrefix() + alias))
                {
                    commandFound = true;
                    command.onCommand(args);
                }
            }
        }
        if (!commandFound)
        {
            CommandUtil.sendChatMessage("&cCommand not found");
        }

    }

    public static ArrayList<Command> getCommands()
    {
        return commands;
    }

    public static String getPrefix()
    {
        return prefix;
    }

    public static void setPrefix(String prefix)
    {
        CommandManager.prefix = prefix;
    }
}
