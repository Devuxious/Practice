package com.hysteria.practice.game.kit.menu;

import com.google.common.collect.Maps;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.Menu;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.utilities.chat.CC;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@AllArgsConstructor
public class KitEditEffectsMenu extends Menu {

    private final Kit kit;

    {
        setUpdateAfterClick(true);
    }

    @Override
    public String getTitle(Player player) {
        return "&cEditing &f" + kit.getDisplayName() + " &ceffects";
    }

    @Override
    public int getSize() {
        return 9*3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

//        for (String effect : kit.getGameRules().getEffects()) {
//            buttons.put(buttons.size(), new EffectButton(effect));
//        }

        buttons.put(22, new RemoveEffect());
        buttons.put(24, new AddEffect());

        return buttons;
    }

    @AllArgsConstructor
    private static class EffectButton extends Button {

        private final String effect;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.GLASS_BOTTLE)
                    .name(CC.RED + effect)
                    .build();
        }
    }

    private static class AddEffect extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.CARPET)
                    .durability(5)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            super.clicked(player, clickType);
        }
    }

    private static class RemoveEffect extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.CARPET)
                    .durability(14)
                    .build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            super.clicked(player, clickType);
        }
    }
}
