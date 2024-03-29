package com.hysteria.practice.game.tournament.commands.subcommands;

import com.hysteria.practice.game.tournament.Tournament;
import com.hysteria.practice.game.tournament.TournamentState;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Hysteria Development
 * @project Practice
 * @date 2/12/2023
 */

public class TournamentStatusCommand extends BaseCommand {

    @Command(name = "tournament.status")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Tournament<?> tournament = Tournament.getTournament();

        if (tournament == null || tournament.getState() == TournamentState.ENDED) {
            player.sendMessage(CC.CHAT_BAR);
            player.sendMessage(ChatColor.RED + "No tournament found.");
            player.sendMessage(CC.CHAT_BAR);
            return;
        }

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate( "&c&lTournament &7&m-&r &c&lStatus"));
        player.sendMessage(CC.translate(" &7▢ &4Players: &7" + tournament.getPlayers().size() + "/" + tournament.getLimit()));
        player.sendMessage(CC.translate(" &7▢ &4Matches: &7" + tournament.getMatches().size()));
        player.sendMessage(CC.translate(" &7▢ &4Stage: &7" + tournament.getState().getName()));
        player.sendMessage(CC.translate(" &7▢ &4Kit: &7" + tournament.getKit().getName()));
        player.sendMessage(CC.CHAT_BAR);
    }
}
