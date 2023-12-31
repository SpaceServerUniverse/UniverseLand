package space.yurisi.universeland.command;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecore.database.DatabaseManager;
import space.yurisi.universecore.database.models.Land;
import space.yurisi.universecore.database.models.User;
import space.yurisi.universecore.exception.LandNotFoundException;
import space.yurisi.universecore.exception.MoneyNotFoundException;
import space.yurisi.universecore.exception.UserNotFoundException;
import space.yurisi.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universeeconomy.exception.ParameterException;
import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;
import space.yurisi.universeland.store.LandStore;
import space.yurisi.universeland.utils.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        } else if (args[0].equals("buy")) {
            BoundingBox land = landData.getLand();

            if(land == null){
                player.sendMessage(Component.text("土地を購入する場合は /land で土地を指定してください"));
                return false;
            }

            if (UniverseLand.getInstance().getPluginConfig().getDenyWorlds().contains(land.getWorldName())) {
                player.sendMessage(Component.text("このワールドでは土地を保護することはできません"));
                return false;
            }

            LandData overlapLandData = LandDataManager.getInstance().getLandData(land);

            if (overlapLandData != null) {
                OfflinePlayer p = UniverseLand.getInstance().getServer().getOfflinePlayer(overlapLandData.getOwnerUUID());
                player.sendMessage(Component.text("選択した範囲は、" + p.getName() + "によって保護されています"));
                return false;
            }

            DatabaseManager database = UniverseLand.getInstance().getDatabaseManager();
            Long price = landData.getPrice();
            try {
                Long money = UniverseEconomyAPI.getInstance().getMoney(player);
                if (price > money) {
                    player.sendMessage(Component.text("購入失敗: お金が足りません(不足金: " + (price - money) + "star"));
                } else {
                    UniverseEconomyAPI.getInstance().reduceMoney(player, price, "土地の購入");
                    database.getLandRepository().createLand(player, land.getMinX(), land.getMinZ(), land.getMaxX(), land.getMaxZ(), land.getWorldName(), price);

                    player.sendMessage(Component.text("指定した土地の購入に成功しました"));
                }
            } catch (UserNotFoundException | MoneyNotFoundException e) {
                player.sendMessage(Component.text("購入失敗: データが見つかりませんでした"));
            } catch (ParameterException e) {
                player.sendMessage(Component.text("購入失敗: 土地の値段が不正です"));
            } catch (CanNotReduceMoneyException e) {
                player.sendMessage(Component.text("購入失敗: 決済処理に失敗しました"));
            }
        }else if (args[0].equals("sell")) {
            LandData land = LandDataManager.getInstance().ultimateChickenHorseMaximumTheHormoneGetYutakaOzakiGreatGodUniverseWonderfulSpecialExpertPerfectHumanVerySuperGeri(player);

            if(land == null){
                player.sendMessage(Component.text("この土地の情報がみつかりませんでした"));
                return false;
            }

            UUID ownerUUID = land.getOwnerUUID();
            if(!ownerUUID.toString().equals(player.getUniqueId().toString())){
                player.sendMessage(Component.text("あなたはこの土地の所有者ではありません"));
                return false;
            }

            DatabaseManager database = UniverseLand.getInstance().getDatabaseManager();
            UniverseEconomyAPI economy = UniverseEconomyAPI.getInstance();

            try {
                database.getLandRepository().deleteLand(database.getLandRepository().getLand(land.getId()));
                economy.addMoney(player, land.getPrice(), "土地の売却");
            } catch (LandNotFoundException | UserNotFoundException | MoneyNotFoundException | CanNotAddMoneyException |
                     ParameterException ignored) {
            }

            player.sendMessage(Component.text("土地を [" + land.getPrice() + "star] で売却しました"));
        } else if (args[0].equals("invite")) {
            if (args.length == 1) {
                player.sendMessage(Component.text("招待するプレイヤー名を指定してください"));
                return false;
            }else if(args[1].equals(player.getName())){
                player.sendMessage(Component.text("自分を招待することはできません"));
                return false;
            }

            DatabaseManager database = UniverseLand.getInstance().getDatabaseManager();

            try {
                User user = database.getUserRepository().getUserFromPlayerName(args[1]);
                LandData land = LandDataManager.getInstance().ultimateChickenHorseMaximumTheHormoneGetYutakaOzakiGreatGodUniverseWonderfulSpecialExpertPerfectHumanVerySuperGeri(player);
                if(land == null){
                    player.sendMessage("現在いる場所は、あなたの土地ではないため招待できません");
                    return false;
                }
                Land dbland = database.getLandRepository().getLand(land.getId());
                database.getLandPermissionRepository().createLandPermission(user, dbland);

                player.sendMessage(Component.text(args[1] + "をこの土地に招待しました"));
            } catch (UserNotFoundException e) {
                player.sendMessage(Component.text("ユーザーが見つかりませんでした"));
            } catch (LandNotFoundException e) {
                player.sendMessage(Component.text("土地データが見つかりませんでした"));
            }
        } else if (args[0].equals("here")) {
            LandData land = LandDataManager.getInstance().ultimateChickenHorseMaximumTheHormoneGetYutakaOzakiGreatGodUniverseWonderfulSpecialExpertPerfectHumanVerySuperGeri(player);
            if(land == null){
                player.sendMessage(Component.text("この土地の情報がみつかりませんでした"));
                return false;
            }

            OfflinePlayer offlinePlayer = UniverseLand.getInstance().getServer().getOfflinePlayer(land.getOwnerUUID());
            player.sendMessage(Component.text("情報"));
            player.sendMessage(Component.text("土地ID: " + land.getId()));
            player.sendMessage(Component.text("所有者: " + offlinePlayer.getName()));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = List.of("buy", "invite", "here", "sell");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return ImmutableList.of();
    }
}
