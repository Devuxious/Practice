package com.hysteria.practice.visual.leaderboard.menu.button;

import com.google.common.collect.Lists;
import com.hysteria.practice.HyPractice;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.visual.leaderboard.entry.LeaderboardKitsEntry;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.visual.leaderboard.Leaderboard;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.meta.ProfileKitData;
import com.hysteria.practice.utilities.chat.CC;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class KitButton extends Button {

    private final Kit kit;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = Lists.newArrayList();
        List<LeaderboardKitsEntry> leaderboard = Leaderboard.getKitLeaderboards().get(kit.getName()).stream().limit(10).collect(Collectors.toList());

        int pos = 0;

        lore.add(CC.MENU_BAR);
        for (LeaderboardKitsEntry leaderboardKitsEntry : leaderboard) {
            pos++;
            Profile profile = leaderboardKitsEntry.getProfile();
            if (profile != null) {
                if (pos == 1) {
                    List<String> first = HyPractice.get().getLeaderboardConfig().getStringList("INVENTORY.KIT.POSITIONS.1");
                    for (String s : first) {
                        lore.add(s
                                .replace("{pos}", String.valueOf(pos))
                                .replace("{name}", profile.getName())
                                .replace("{color}", profile.getColor())
                                .replace("{data}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getElo()))
                                .replace("{winstreak}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getKillstreak()))
                                .replace("{bars}", CC.MENU_BAR));
                    }
                } else if (pos == 2) {
                    List<String> second = HyPractice.get().getLeaderboardConfig().getStringList("INVENTORY.KIT.POSITIONS.2");
                    for (String s : second) {
                        lore.add(s
                                .replace("{pos}", String.valueOf(pos))
                                .replace("{name}", profile.getName())
                                .replace("{color}", profile.getColor())
                                .replace("{data}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getElo()))
                                .replace("{winstreak}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getKillstreak()))
                                .replace("{bars}", CC.MENU_BAR));
                    }
                } else if (pos == 3) {
                    List<String> third = HyPractice.get().getLeaderboardConfig().getStringList("INVENTORY.KIT.POSITIONS.3");
                    for (String s : third) {
                        lore.add(s
                                .replace("{pos}", String.valueOf(pos))
                                .replace("{name}", profile.getName())
                                .replace("{color}", profile.getColor())
                                .replace("{data}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getElo()))
                                .replace("{winstreak}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getKillstreak()))
                                .replace("{bars}", CC.MENU_BAR));
                    }
                } else {
                    List<String> another = HyPractice.get().getLeaderboardConfig().getStringList("INVENTORY.KIT.POSITIONS.ANOTHER");
                    for (String s : another) {
                        lore.add(s
                                .replace("{pos}", String.valueOf(pos))
                                .replace("{name}", profile.getName())
                                .replace("{color}", profile.getColor())
                                .replace("{data}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getElo()))
                                .replace("{winstreak}", String.valueOf(profile.getKitData().getOrDefault(kit, new ProfileKitData()).getKillstreak()))
                                .replace("{bars}", CC.MENU_BAR));
                    }
                }
            }
        }
        lore.add(CC.MENU_BAR);

        return new ItemBuilder(kit.getDisplayIcon().getType())
                .name(HyPractice.get().getLeaderboardConfig().getString("INVENTORY.KIT.TITLE").replace("{kit}", kit.getDisplayName()))
                .durability(kit.getDisplayIcon().getDurability())
                .lore(lore)
                .build();
    }
}
