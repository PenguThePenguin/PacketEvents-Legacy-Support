package io.github.retrooper.packetevents.injector;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.UserLoginEvent;
import com.github.retrooper.packetevents.injector.ChannelInjector;
import com.github.retrooper.packetevents.protocol.player.User;

public abstract class SpigotChannelInjector implements ChannelInjector {

    public void updatePlayer(User user, Object player) {
        PacketEvents.getAPI().getEventManager().callEvent(new UserLoginEvent(user, player));
        Object channel = user.getChannel();
        if (channel == null) {
            channel = PacketEvents.getAPI().getPlayerManager().getChannel(player);
        }
        setPlayer(channel, player);
    }

}
