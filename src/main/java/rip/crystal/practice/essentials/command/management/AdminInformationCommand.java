package rip.crystal.practice.essentials.command.management;

import rip.crystal.practice.cPractice;
import rip.crystal.practice.utilities.chat.CC;
import rip.crystal.practice.api.command.BaseCommand;
import rip.crystal.practice.api.command.Command;
import rip.crystal.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

public class AdminInformationCommand extends BaseCommand {

    @Command(name = "admin", aliases = {"admininformation"}, permission = "cpractice.owner")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate(" &7▢ &4Plugin: &r" + "cPractice"));
        player.sendMessage(CC.translate(" &7▢ &4Version: &r" + cPractice.get().getDescription().getVersion()));
        player.sendMessage(CC.translate(" &7▢ &4License: &r" + cPractice.get().getMainConfig().getString("LICENSE")));
        player.sendMessage(CC.translate(" &7▢ &4Developer: &r" + "ziue"));
        player.sendMessage(CC.CHAT_BAR);
    }
}