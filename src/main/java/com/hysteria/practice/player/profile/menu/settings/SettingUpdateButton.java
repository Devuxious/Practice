package com.hysteria.practice.player.profile.menu.settings;

import com.hysteria.practice.HyPractice;
import com.hysteria.practice.player.cosmetics.impl.killeffects.menu.KillEffectsMenu;
import com.hysteria.practice.player.profile.menu.matchmaking.MatchMakingMenu;
import com.hysteria.practice.player.profile.meta.option.ProfileOptions;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.file.type.BasicConfigurationFile;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.visual.tablist.TabType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.chat.CC;

import java.util.ArrayList;
import java.util.List;

public class SettingUpdateButton extends Button {
    private final String name;
    private final Material material;
    private final int durability;
    private final List<String> lore;
    private final String command;
    private final String type;
    private final HyPractice plugin = HyPractice.get();

    @Override
    public ItemStack getButtonItem(Player player) {
        ArrayList<String> arrayList = new ArrayList<>(this.lore);
        Profile practicePlayerData = Profile.get(player.getUniqueId());
        ProfileOptions playerSettings = practicePlayerData.getOptions();
        BasicConfigurationFile configCursor = HyPractice.get().getMenuConfig();
        String string = CC.translate(configCursor.getString("SETTINGS-INVENTORY.ENABLED"));
        String string2 = CC.translate(configCursor.getString("SETTINGS-INVENTORY.DISABLED"));
        String string3 = CC.translate(configCursor.getString("SETTINGS-INVENTORY.UNSELECTED"));
        if (this.type.equalsIgnoreCase("duelRequest")) {
            arrayList.add((playerSettings.receiveDuelRequests() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.DUEL_REQUEST.O-ENABLED"));
            arrayList.add((!playerSettings.receiveDuelRequests() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.DUEL_REQUEST.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("scoreboardToggled")) {
            arrayList.add((playerSettings.showScoreboard() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.TOGGLE_SCOREBOARD.O-ENABLED"));
            arrayList.add((!playerSettings.showScoreboard() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.TOGGLE_SCOREBOARD.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("spectatorsAllowed")) {
            arrayList.add((playerSettings.allowSpectators() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.ALLOW_SPECTATORS.O-ENABLED"));
            arrayList.add((!playerSettings.allowSpectators() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.ALLOW_SPECTATORS.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("vanillaTab")) {
            arrayList.add((practicePlayerData.getTabType() == TabType.WEIGHT ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.VANILLA_TAB.O-ENABLED"));
            arrayList.add((practicePlayerData.getTabType() == TabType.DEFAULT ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.VANILLA_TAB.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("publicChat")) {
            arrayList.add((playerSettings.publicChatEnabled() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.PUBLIC-CHAT.O-ENABLED"));
            arrayList.add((!playerSettings.publicChatEnabled() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.PUBLIC-CHAT.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("receivingnewconversations")) {
            arrayList.add((playerSettings.receivingNewConversations() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.NEW-CONVERSATIONS.O-ENABLED"));
            arrayList.add((!playerSettings.receivingNewConversations() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.NEW-CONVERSATIONS.O-DISABLED"));
        } else if (this.type.equalsIgnoreCase("playingmessagesounds")) {
            arrayList.add((playerSettings.playingMessageSounds() ? string : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.MESSAGE-SOUNDS.O-ENABLED"));
            arrayList.add((!playerSettings.playingMessageSounds() ? string2 : string3) + configCursor.getString("SETTINGS-INVENTORY.SETTINGS.MESSAGE-SOUNDS.O-DISABLED"));
        }
        arrayList.add(CC.translate(this.plugin.getMenuConfig().getString("SETTINGS-INVENTORY.BOTTOM-SPLITTER")));
        return new ItemBuilder(this.material).name(CC.translate(configCursor.getString("SETTINGS-INVENTORY.NAME").replace("<setting_name>", this.name))).amount(1).lore(arrayList).durability(this.durability).build();
    }

    @Override
    public void clicked(Player player, int n, ClickType clickType, int n2) {
        switch (this.type) {
            case "deathEffectsSettings": {
                new KillEffectsMenu().openMenu(player);
                SettingUpdateButton.playSuccess(player);
                break;
            } case "matchmakingSettings": {
                if (!player.hasPermission("hypractice.matchmaking_settings")) {
                    player.sendMessage(CC.translate(this.plugin.getMenuConfig().getString("SETTINGS-INVENTORY.NO-PERMISSION")));
                    SettingUpdateButton.playFail(player);
                    break;
                }
                new MatchMakingMenu().openMenu(player);
                SettingUpdateButton.playSuccess(player);
                break;
            } case "publicChat": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().publicChatEnabled(!profile.getOptions().publicChatEnabled());
                if(profile.getOptions().publicChatEnabled()) {
                    player.sendMessage(CC.translate("&aYou have enabled public chat!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled public chat!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "scoreboardToggled": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().showScoreboard(!profile.getOptions().showScoreboard());
                if(profile.getOptions().showScoreboard()) {
                    player.sendMessage(CC.translate("&aYou have enabled scoreboard!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled scoreboard!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "spectatorsAllowed": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().allowSpectators(!profile.getOptions().allowSpectators());
                if(profile.getOptions().allowSpectators()) {
                    player.sendMessage(CC.translate("&aYou have enabled spectators!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled spectators!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "receivingnewconversations": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().receivingNewConversations(!profile.getOptions().receivingNewConversations());
                if(profile.getOptions().receivingNewConversations()) {
                    player.sendMessage(CC.translate("&aYou have enabled conversations!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled conversations!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "duelrequest": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().receiveDuelRequests(!profile.getOptions().receiveDuelRequests());
                if(profile.getOptions().receiveDuelRequests()) {
                    player.sendMessage(CC.translate("&aYou have enabled duel requests!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled duel requests!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "playingmessagesounds": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().playingMessageSounds(!profile.getOptions().playingMessageSounds());
                if(profile.getOptions().playingMessageSounds()) {
                    player.sendMessage(CC.translate("&aYou have enabled message sounds!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled message sounds!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "vanillaTab": {
                Profile profile = Profile.get(player.getUniqueId());
                if (profile.getTabType() == TabType.DEFAULT) profile.setTabType(TabType.WEIGHT);
                else profile.setTabType(TabType.DEFAULT);
                profile.getOptions().vanillaTab(!profile.getOptions().vanillaTab());

                if(profile.getOptions().vanillaTab()) {
                    player.sendMessage(CC.translate("&aYou have enabled vanilla tab!"));
                } else {
                    player.sendMessage(CC.translate("&cYou have disabled vanilla tab!"));
                }
                SettingUpdateButton.playSuccess(player);
                break;
            } case "resetSettings": {
                Profile profile = Profile.get(player.getUniqueId());
                profile.getOptions().publicChatEnabled(true);
                profile.getOptions().showScoreboard(true);
                profile.getOptions().allowSpectators(true);
                profile.getOptions().receivingNewConversations(true);
                profile.getOptions().receiveDuelRequests(true);
                profile.getOptions().playingMessageSounds(true);
                profile.getOptions().vanillaTab(false);
                break;
            }
            default: {
                player.performCommand(this.command);
                SettingUpdateButton.playSuccess(player);
            }
        }
    }

    public SettingUpdateButton(String string, Material material, int n, List<String> list, String string2, String string3) {
        this.name = string;
        this.material = material;
        this.durability = n;
        this.lore = list;
        this.command = string2;
        this.type = string3;
    }

    @Override
    public boolean shouldUpdate(Player player, ClickType clickType) {
        return true;
    }
}
