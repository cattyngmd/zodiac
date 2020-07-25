package cat.yoink.zodiac.util;

import cat.yoink.zodiac.Client;
import club.minnced.discord.rpc.*;

public class RPC {
    private static final String ClientId = "728619119091515423";
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static final DiscordRichPresence presence = new DiscordRichPresence();
    private static String details;
    private static String state;

    public RPC(){
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = "Version " + Client.VERSION;
        presence.state = "Main Menu";
        presence.largeImageKey = "zodiac";
        presence.largeImageText = "Zodiac";

        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.Discord_RunCallbacks();
                    details = "Version " + Client.VERSION;
                    state = "youre nos alaong gon haveing tyoure teeth until youre goaing to tlaking toyoure ugly ass youre cop and the polciey piggy gonna crying youre crying cause yorue onlty have teeth youre only gums!!!!!!!!";
                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                } catch(Exception ignored) {};
                try {
                    Thread.sleep(5000L);
                } catch(InterruptedException ignored){}
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
}
