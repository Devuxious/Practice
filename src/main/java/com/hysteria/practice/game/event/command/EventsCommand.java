package com.hysteria.practice.game.event.command;

import com.hysteria.practice.game.event.Event;
import com.hysteria.practice.utilities.chat.ChatComponentBuilder;
import com.hysteria.practice.utilities.chat.ChatHelper;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class EventsCommand extends BaseCommand {

	@Command(name = "events", permission = "hypractice.event.host")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();

		player.sendMessage(CC.RED + "Events:");
		for (Event events : Event.events) {
			ChatComponentBuilder builder = new ChatComponentBuilder("")
					.parse("&7- " + "&a" + events.getName());

			ChatComponentBuilder status = new ChatComponentBuilder("").parse("&7[&bSTATUS&7]");
			status.attachToEachPart(ChatHelper.hover("&bClick to view this event's status."));
			status.attachToEachPart(ChatHelper.click("/event info"));

			builder.append(" ");

			for (BaseComponent component : status.create()) {
				builder.append((TextComponent) component);
			}

			player.spigot().sendMessage(builder.create());
		}
	}
}
