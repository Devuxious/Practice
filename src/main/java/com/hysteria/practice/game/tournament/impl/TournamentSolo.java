package com.hysteria.practice.game.tournament.impl;

import com.google.common.collect.Lists;
import com.hysteria.practice.game.tournament.events.TournamentEndEvent;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.TaskUtil;
import com.hysteria.practice.utilities.countdown.Countdown;
import com.hysteria.practice.utilities.file.type.BasicConfigurationFile;
import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.HyPractice;
import com.hysteria.practice.match.Match;
import com.hysteria.practice.match.impl.BasicTeamMatch;
import com.hysteria.practice.match.participant.MatchGamePlayer;
import com.hysteria.practice.player.profile.participant.alone.GameParticipant;
import com.hysteria.practice.game.tournament.Tournament;
import com.hysteria.practice.game.tournament.TournamentState;
import com.hysteria.practice.utilities.chat.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Hysteria Development
 * @project Practice
 * @date 2/12/2023
 */

@Getter @Setter
public class TournamentSolo extends Tournament<Player> {

    @Override
    public void join(Player player){
        HyPractice.get().getTournamentManager().handleJoin(player);

        if(getTeams().size() == getLimit()) {
            Countdown.of(1, TimeUnit.MINUTES)
                    .players(getOnlinePlayers())
                    .broadcastAt(1, TimeUnit.MINUTES)
                    .broadcastAt(30, TimeUnit.SECONDS)
                    .broadcastAt(10, TimeUnit.SECONDS)
                    .broadcastAt(5, TimeUnit.SECONDS)
                    .broadcastAt(4, TimeUnit.SECONDS)
                    .broadcastAt(3, TimeUnit.SECONDS)
                    .broadcastAt(2, TimeUnit.SECONDS)
                    .broadcastAt(1, TimeUnit.SECONDS)
                    .withMessage("&7Tournament start in&4 {time}")
                    .onFinish(this::start).start();

            setState(TournamentState.STARTING);
        }
    }

    public void start() {
        nextRound();
    }

    public void nextRound() {
        setState(TournamentState.SELECTING_DUELS);
        setRound(getRound() + 1);
        //Shuffle list to randomize
        Collections.shuffle(getTeams());
        //New team LinkedList to remove usedTeams
        LinkedList<GameParticipant<MatchGamePlayer>> teamsShuffle = new LinkedList<>(getTeams());
        //Count down
        String round = "&7Next round in&4 {time}";
        if(getRound() == 1){
            round = "&7Starting in &4{time}";
        }
        Countdown.of(10, TimeUnit.SECONDS)
            .players(getOnlinePlayers())
            .broadcastAt(10, TimeUnit.SECONDS)
            .broadcastAt(5, TimeUnit.SECONDS)
            .broadcastAt(4, TimeUnit.SECONDS)
            .broadcastAt(3, TimeUnit.SECONDS)
            .broadcastAt(2, TimeUnit.SECONDS)
            .broadcastAt(1, TimeUnit.SECONDS)
            .withMessage(round)
            .onFinish(() -> {
                setState(TournamentState.IN_FIGHT);
                //Logic to start match
                TaskUtil.runTimer(new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (teamsShuffle.isEmpty()) {
                            cancel();
                            return;
                        }
                        GameParticipant<MatchGamePlayer> teamA = teamsShuffle.poll();
                        if (teamsShuffle.isEmpty()) {
                            teamA.getPlayers().forEach(matchGamePlayer ->
                                matchGamePlayer.getPlayer().sendMessage(CC.translate("&7No other player found," + " you should wait in this round.")));
                            return;
                        }
                        GameParticipant<MatchGamePlayer> teamB = teamsShuffle.poll();

                        Arena arena = Arena.getRandomArena(getKit());

                        if (arena == null) {
                            teamA.getPlayers().forEach(matchGamePlayer -> matchGamePlayer.getPlayer().sendMessage(CC.translate("&7Tried to start a match but there are no available arenas.")));
                            teamB.getPlayers().forEach(matchGamePlayer -> matchGamePlayer.getPlayer().sendMessage(CC.translate("&7Tried to start a match but there are no available arenas.")));
                            return;
                        }
                        arena.setActive(true);
                        Match match = new BasicTeamMatch(null, getKit(), arena, false, teamA, teamB);
                        match.start();
                        getMatches().add(match);
                    }
                }, 1L, 1L);
            }).start();
    }

    public void eliminatedTeam(GameParticipant<MatchGamePlayer> teamEliminated) {
        getTeams().remove(teamEliminated);
        getTeams().forEach(team -> team.getPlayers().forEach(matchGamePlayer -> {
            Profile.get(matchGamePlayer.getPlayer().getUniqueId()).setInTournament(false);
            getPlayers().remove(matchGamePlayer.getUuid());
            Player player = matchGamePlayer.getPlayer();
            if(player != null) player.sendMessage(CC.translate("&4" + teamEliminated.getConjoinedNames() + "&7 has been &4eliminated."));
        }));
    }

    public void end(GameParticipant<MatchGamePlayer> winner){
        setState(TournamentState.ENDED);
        getTeams().forEach(team -> team.getPlayers().forEach(matchGamePlayer ->
            Profile.get(matchGamePlayer.getPlayer().getUniqueId()).setInTournament(false)
        ));
        getTeams().clear();
        setStarted(false);
        setWinner(winner);
        if(winner != null) {
            new TournamentEndEvent(winner, true, false).call();
            Bukkit.broadcastMessage(CC.CHAT_BAR);
            Bukkit.broadcastMessage(CC.translate("&7(*) &cTournament Ended &7(*)"));
            Bukkit.broadcastMessage(CC.translate("&7Winner: &c" + winner.getConjoinedNames()));
            Bukkit.broadcastMessage(CC.CHAT_BAR);
        } else {
            Bukkit.broadcastMessage(CC.CHAT_BAR);
            Bukkit.broadcastMessage(CC.translate("&cTournament has been cancelled."));
            Bukkit.broadcastMessage(CC.CHAT_BAR);
        }
        TaskUtil.runLater(() -> setTournament(null), 20 * 10L);
    }

    @Override
    public List<String> getTournamentScoreboard() {
        List<String> lines = Lists.newArrayList();
        BasicConfigurationFile config = HyPractice.get().getScoreboardConfig();
        String bars = config.getString("LINES.BARS");

        config.getStringList("TOURNAMENTS.SOLO.LINES").forEach(line -> lines.add(line.replace("{kit}", getKit().getName())
                .replace("{bars}", bars)
                .replace("{size}", String.valueOf(getPlayers().size()))
                .replace("{limit}", String.valueOf(getLimit()))
                .replace("{state}", getState().getName())));


        if (this.getState() == TournamentState.IN_FIGHT) {
            config.getStringList("TOURNAMENTS.SOLO.IN-FIGHT").forEach(line -> lines.add(line.replace("{round}", String.valueOf(getRound()))));
        }
        if (this.getState() == TournamentState.ENDED && getWinner() != null) {
            MatchGamePlayer leader = getWinner().getLeader();
            config.getStringList("TOURNAMENTS.SOLO.ON-END").forEach(line -> lines.add(line.replace("{color}", Profile.get(leader.getPlayer().getUniqueId()).getColor())
                    .replace("{player}", leader.getPlayer().getName())));
        }

        return lines;
    }
}