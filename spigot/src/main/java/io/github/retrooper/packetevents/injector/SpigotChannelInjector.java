package io.github.retrooper.packetevents.injector;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.UserLoginEvent;
import com.github.retrooper.packetevents.injector.ChannelInjector;
import com.github.retrooper.packetevents.protocol.player.User;
import io.github.retrooper.packetevents.injector.legacy.SpigotChannelInjectorLegacy;
import io.github.retrooper.packetevents.injector.modern.SpigotChannelInjectorModern;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;

public class SpigotChannelInjector implements ChannelInjector {
    private final ChannelInjector injector;

    public SpigotChannelInjector() {
        this.injector = SpigotReflectionUtil.USE_MODERN_NETTY_PACKAGE
                ? new SpigotChannelInjectorModern() //1.8+ injector
                : new SpigotChannelInjectorLegacy(); //1.7 injector
    }

    @Override
    public boolean isServerBound() {
        return injector.isServerBound();
    }

    @Override
    public void inject() {
        injector.inject();
    }

    @Override
    public void uninject() {
        injector.uninject();
    }

    @Override
    public void updateUser(Object channel, User user) {
        injector.updateUser(channel, user);
    }

    public void updatePlayer(User user, Object player) {
        PacketEvents.getAPI().getEventManager().callEvent(new UserLoginEvent(user, player));
        Object channel = user.getChannel();
        if (channel == null) {
            channel = PacketEvents.getAPI().getPlayerManager().getChannel(player);
        }
        setPlayer(channel, player);
    }

    @Override
    public void setPlayer(Object channel, Object player) {
        injector.setPlayer(channel, player);
    }

}
