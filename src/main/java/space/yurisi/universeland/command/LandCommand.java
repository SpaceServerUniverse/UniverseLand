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
import space.yurisi.universecore.database.DatabaseManager;
import space.yurisi.universecore.database.models.User;
import space.yurisi.universecore.exception.MoneyNotFoundException;
import space.yurisi.universecore.exception.UserNotFoundException;
import space.yurisi.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universeeconomy.exception.ParameterException;
import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandStore;
import space.yurisi.universeland.utils.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class LandCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("このコマンドはゲーム内で実行してください"));
            return false;
        }

        if (LandDataManager.getInstance().getLandData(player.getUniqueId()) == null) {
            LandDataManager.getInstance().setLandData(player.getUniqueId());
        }

        LandStore landData = LandDataManager.getInstance().getLandData(player.getUniqueId());

        if (args.length == 0) {
            landData.setSelectLand(true);
            landData.resetLandData();
            player.sendMessage(Component.text("範囲を指定してください"));
            return false;
        } else if (args.length == 1 && args[0].equals("buy")) {
            BoundingBox land = landData.getLand();

            if (land == null) {
                player.sendMessage(Component.text("範囲が指定されていません"));
                return false;
            }

            DatabaseManager database = UniverseLand.getInstance().getDatabaseManager();
            Long price = landData.getPrice();
            try {
                User user = database.getUserRepository().getUserFromUUID(player.getUniqueId());
                Long money = UniverseEconomyAPI.getInstance().getMoney(player);
                if (price > money) {
                    player.sendMessage(Component.text("購入失敗: お金が足りません(不足金: " + (price - money) + "star"));
                } else {
                    UniverseEconomyAPI.getInstance().reduceMoney(player, price, "土地の購入");
                    database.getLandRepository().createLand(user, land.getMinX(), land.getMinZ(), land.getMaxX(), land.getMaxZ(), land.getWorldName());

                    player.sendMessage(Component.text("指定した土地の購入に成功しました"));
                }
            } catch (UserNotFoundException | MoneyNotFoundException e) {
                player.sendMessage(Component.text("購入失敗: データが見つかりませんでした"));
            } catch (ParameterException e) {
                player.sendMessage(Component.text("購入失敗: 土地の値段が不正です"));
            } catch (CanNotReduceMoneyException e) {
                player.sendMessage(Component.text("購入失敗: 決済処理に失敗しました"));
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = List.of("buy");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return ImmutableList.of();
    }
}
