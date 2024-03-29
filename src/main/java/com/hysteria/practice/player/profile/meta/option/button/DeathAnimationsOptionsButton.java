package com.hysteria.practice.player.profile.meta.option.button;
/* 
   Made by hypractice Development Team
   Created on 05.11.2021
*/

import com.hysteria.practice.HyPractice;
import com.hysteria.practice.player.cosmetics.impl.killeffects.menu.KillEffectsMenu;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.player.profile.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DeathAnimationsOptionsButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {

        return new ItemBuilder(Material.REDSTONE)
                .name(HyPractice.get().getMenuConfig().getString("COSMETICS.KILL-EFFECTS.ITEM-NAME"))
                .lore(HyPractice.get().getMenuConfig().getStringList("COSMETICS.KILL-EFFECTS.LORE"))
                .build();

    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Profile profile = Profile.get(player.getUniqueId());
        new KillEffectsMenu().openMenu(player);
    }
}
