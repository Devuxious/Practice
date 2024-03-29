package com.hysteria.practice.game.event.game.command;

import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import com.hysteria.practice.game.event.Event;
import com.hysteria.practice.game.event.game.EventGame;
import com.hysteria.practice.game.event.game.map.EventGameMap;
import com.hysteria.practice.game.event.game.map.vote.EventGameMapVoteData;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.game.event.game.menu.EventHostMenu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventHostCommand extends BaseCommand {

	@Command(name ="host", permission = "hypractice.event.host")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String[] args = commandArgs.getArgs();

		if (args.length == 0) {
			new EventHostMenu().openMenu(player);
		}
		else if (args.length == 1) {
			Event event = Event.getByName(args[0]);
			if (event == null) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.DARK_RED + "This event doesn't exist.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (EventGame.getActiveGame() != null) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.DARK_RED + "There is already an active event.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (!EventGame.getCooldown().hasExpired()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.DARK_RED + "The event cooldown is active.");
				player.sendMessage(CC.translate("&bCooldown: &7" + EventGame.getCooldown().getTimeLeft()));
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (EventGameMap.getMaps().isEmpty()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.DARK_RED + "There are no available event maps.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			List<EventGameMap> validMaps = new ArrayList<>();

			for (EventGameMap gameMap : EventGameMap.getMaps()) {
				if (event.getAllowedMaps().contains(gameMap.getMapName())) {
					validMaps.add(gameMap);
				}
			}

			if (validMaps.isEmpty()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "There are no available event maps.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			try {
				EventGame game = new EventGame(event, player, Profile.getHostSlots(player.getUniqueId()));

				for (EventGameMap gameMap : validMaps) {
					game.getVotesData().put(gameMap, new EventGameMapVoteData());
				}

				game.sendAnnouncement();
				game.start();
				game.getGameLogic().onJoin(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Event event = Event.getByName(args[0]);
			if (event == null) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "This event doesn't exist.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			int slots;
			if (!StringUtils.isNumeric(args[1])) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "Please insert a valid Integer.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}
			slots = Integer.getInteger(args[1]);

			if (!player.hasPermission("hypractice.event.admin")) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "You don't have permissions to host with Slots.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (EventGame.getActiveGame() != null) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "There is already an active event.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (!EventGame.getCooldown().hasExpired()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "The event cooldown is active.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (EventGameMap.getMaps().isEmpty()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "There are no available event maps.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			List<EventGameMap> validMaps = new ArrayList<>();

			for (EventGameMap gameMap : EventGameMap.getMaps()) {
				if (event.getAllowedMaps().contains(gameMap.getMapName())) {
					validMaps.add(gameMap);
				}
			}

			if (validMaps.isEmpty()) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(CC.RED + "There are no available event maps.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			try {
				EventGame game = new EventGame(event, player, slots);

				for (EventGameMap gameMap : validMaps) {
					game.getVotesData().put(gameMap, new EventGameMapVoteData());
				}

				game.sendAnnouncement();
				game.start();
				game.getGameLogic().onJoin(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
