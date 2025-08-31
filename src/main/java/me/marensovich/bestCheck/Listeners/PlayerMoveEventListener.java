package me.marensovich.bestCheck.Listeners;

import me.marensovich.bestCheck.BestCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEventListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (BestCheck.getInstance().getCheckManager().isFrozen(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
