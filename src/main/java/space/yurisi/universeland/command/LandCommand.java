package space.yurisi.universeland.command;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandCommand implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("このコマンドはゲーム内で実行してください"));
            return false;
        }

        if (LandDataManager.getInstance().getLandData(player.getUniqueId()) == null) {
            LandDataManager.getInstance().setLandData(player.getUniqueId());
        }

        LandData landData = LandDataManager.getInstance().getLandData(player.getUniqueId());

        if (args.length == 0) {
            landData.setSelectLand(true);
            landData.resetLandData();
            sender.sendMessage(Component.text("範囲を指定してください"));
            return false;
        } else if (args.length == 1 && args[0].equals("buy")) {

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(args.length == 1) {
            List<String> completions = List.of("buy");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return ImmutableList.of();
    }
}
