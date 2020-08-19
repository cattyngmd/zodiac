package cat.yoink.zodiac.util;

import cat.yoink.zodiac.Client;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class RPC
{
    public static final DiscordRichPresence presence = new DiscordRichPresence();
    private static final String ClientId = "743331193105416203";
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    private static String details;
    private static String state;

    public RPC()
    {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = "Version " + Client.VERSION;
        presence.state = "Main Menu";
        presence.largeImageKey = "zodiac";
        presence.largeImageText = "Zodiac";

        rpc.Discord_UpdatePresence(presence);
        new Thread(() ->
        {
            while (!Thread.currentThread().isInterrupted())
            {
                try
                {
                    rpc.Discord_RunCallbacks();
                    details = "Version " + Client.VERSION;
                    state = "come again, i cannot hear ya name";
                    if (!details.equals(presence.details) || !state.equals(presence.state))
                    {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                }
                catch (Exception ignored)
                {
                }
                try
                {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException ignored)
                {
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
}
