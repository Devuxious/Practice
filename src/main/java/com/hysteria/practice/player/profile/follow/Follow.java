package com.hysteria.practice.player.profile.follow;

import com.hysteria.practice.player.profile.hotbar.Hotbar;
import com.hysteria.practice.player.profile.hotbar.impl.HotbarItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.ProfileState;
import com.hysteria.practice.utilities.chat.CC;

import java.util.Map;
import java.util.UUID;

public class Follow {
    public static Map<UUID, Follow> follows;
    private final UUID follower;
    private final Player followedPlayer;

    public static Follow getByFollowed(UUID uUID) {
        return follows.values().stream().filter(follow -> follow.getFollowed().equals(uUID)).findFirst().orElse(null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.follower);
    }

    public void follow() {
        Player player = this.getPlayer();
        this.detect();
        player.getInventory().clear();
        player.getInventory().setItem(Hotbar.getSlot(HotbarItem.FOLLOW), Hotbar.getItem(HotbarItem.FOLLOW));
        player.updateInventory();
    }

    public void detect() {
        Player player = this.getPlayer();
        Profile profile = Profile.get(this.followedPlayer.getUniqueId());
        if (profile.getState() == ProfileState.LOBBY || profile.getState() == ProfileState.QUEUEING) {
            player.showPlayer(this.followedPlayer);
            player.teleport(this.followedPlayer.getLocation());
        } else if (profile.getState() == ProfileState.FIGHTING || profile.getState() == ProfileState.SPECTATING) {
            profile.getMatch().addSpectator(player, this.followedPlayer);
        } else {
            player.sendMessage(CC.translate("&cCan't follow."));
        }
    }

    public Player getFollowed() {
        return this.followedPlayer;
    }

    public Follow(UUID uUID, Player player) {
        this.follower = uUID;
        this.followedPlayer = player;
    }

    public static Map<UUID, Follow> getFollows() {
        return follows;
    }
}
