package space.yurisi.universeland.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;

public class LandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("このコマンドはゲーム内で実行してください"));
            return false;
        }

        if(LandDataManager.getInstance().getLandData(player.getUniqueId()) == null){
            LandDataManager.getInstance().setLandData(player.getUniqueId());
        }

        LandData landData = LandDataManager.getInstance().getLandData(player.getUniqueId());

        if(args.length == 0){
            landData.setSelectLand(true);
            landData.resetLandData();
            sender.sendMessage(Component.text("範囲を指定してください"));
            return false;
        }

        return true;
    }
}
