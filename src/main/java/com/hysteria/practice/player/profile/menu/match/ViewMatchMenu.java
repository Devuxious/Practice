package com.hysteria.practice.player.profile.menu.match;

import com.google.common.collect.Maps;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.pagination.PaginatedMenu;
import com.hysteria.practice.match.mongo.MatchInfo;
import com.hysteria.practice.player.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class ViewMatchMenu extends PaginatedMenu {

    private final Profile profile;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&c&lRecent Matches&7: &7" + profile.getName();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        profile.getMatches().forEach(matchInfo -> buttons.put(buttons.size(), new MatchButton(matchInfo)));

        return buttons;
    }

    @RequiredArgsConstructor
    private static class MatchButton extends Button {

        private final MatchInfo matchInfo;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.PAPER)
                .name("&7" + matchInfo.getWinningParticipant() + " &4vs&7 " + matchInfo.getLosingParticipant())
                .lore("&7" + matchInfo.getDate())
                .lore("")
                .lore("&4Duration&7:&7 " + matchInfo.getDuration())
                .lore("&4Kit&7:&7 " + matchInfo.getKit().getName())
                .lore(" &7✦ &aWinner&7:&7 " + matchInfo.getWinningParticipant() + " &7(&a+" + matchInfo.getNewWinnerElo() + "&7)")
                .lore(" &7✦ &4Loser&7:&7 " + matchInfo.getLosingParticipant() + " &7(&c-" + matchInfo.getNewLoserElo() + "&7)")
                .build();
        }
    }

}