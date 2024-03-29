package com.hysteria.practice.game.tournament.listeners;

import com.hysteria.practice.HyPractice;
import org.bukkit.entity.Player;
import com.hysteria.practice.game.tournament.Tournament;
import com.hysteria.practice.match.Match;
import com.hysteria.practice.match.events.MatchEndEvent;
import com.hysteria.practice.player.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Hysteria Development
 * @project Practice
 * @date 2/12/2023
 */

public class TournamentListener implements Listener {

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        if(Tournament.getTournament() != null) {
            Player player = playerQuitEvent.getPlayer();
            Profile profile = Profile.get(player.getUniqueId());

            if(profile.isInTournament()) {
                HyPractice.get().getTournamentManager().forceLeave(player);
            }
        }
    }

    @EventHandler
    public void onMatchEnd(MatchEndEvent event) {
        Match match = event.getMatch();

        Tournament<?> tournament = Tournament.getTournament();
        if(tournament == null) return;
        match.getParticipants().forEach(gameParticipant -> {
            if (gameParticipant.isAllDead()) {
                tournament.eliminatedTeam(gameParticipant);
            }
        });

        if (tournament.getMatches().contains(match)) {
            tournament.getMatches().remove(match);
            if (tournament.getMatches().isEmpty()) {
                if (tournament.getTeams().size() == 1) {
                    tournament.end(tournament.getTeams().get(0));
                } else if (tournament.getTeams().isEmpty()) {
                    tournament.end(null);
                } else {
                    tournament.nextRound();
                }
            }
        }
    }
}
