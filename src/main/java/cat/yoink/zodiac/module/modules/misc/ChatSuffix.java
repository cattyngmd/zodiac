package cat.yoink.zodiac.module.modules.misc;

import cat.yoink.zodiac.Client;
import cat.yoink.zodiac.module.manager.module.Category;
import cat.yoink.zodiac.module.manager.module.Module;
import cat.yoink.zodiac.module.manager.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;

import java.util.ArrayList;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", "Adds a suffix to your chat messages.", Category.MISC, true);
    }

    public ArrayList<String> prefixes;
    Setting blue;

    @Override
    public void init() {
        blue = new Setting("Blue", this, false);
        addSetting(blue);

        prefixes = new ArrayList<>();
        prefixes.add(".");
        prefixes.add("!");
        prefixes.add("#");
        prefixes.add("$");
        prefixes.add("-");
        prefixes.add(";");
        prefixes.add(":");
        prefixes.add(",");
        prefixes.add("\\");
        prefixes.add("@");
    }

    @Override
    public void onChatSend(ClientChatEvent event) {
        for (String prefix : prefixes) {
            if (event.getMessage().startsWith(prefix)) return;
        }

        StringBuilder message = new StringBuilder(event.getMessage());

        if (blue.getBoolValue()) message.append("`");
        message.append(" \u1d22\u1d0f\u1d05\u026a\u1d00\u1d04");

        event.setMessage(message.toString());
    }
}
