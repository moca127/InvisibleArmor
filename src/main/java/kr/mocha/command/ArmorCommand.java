package kr.mocha.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import kr.mocha.InvisibleArmor;

import java.util.LinkedHashMap;

/**
 * Created by mocha on 16. 11. 21.
 */
public class ArmorCommand extends Command{
    public Config config = InvisibleArmor.getInstance().getConfig();

    public ArmorCommand(){
        super("armor", "Invisible Armor", "/armor <buy|status>", new String[] {"갑옷"});
        this.setPermission("armor.cmd");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        try{
            switch (args[0]){
                case "buy":
                case "b":
                case "구매":
                    if(config.exists(sender.getName()))
                        sender.sendMessage(TextFormat.GREEN+"이미 구매하셨습니다.");
                    else{
                        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

                        switch (args[1]) {
                            case "low":
                            case "하급":

                                if(!(InvisibleArmor.economyAPI.reduceMoney(
                                        sender.getName(), InvisibleArmor.setting.getInt("low-price"))==1)){
                                    sender.sendMessage(TextFormat.RED + "No Have Money");
                                    return false;
                                }

                                map.put("kind", "low");
                                map.put("Durable", 100);
                                config.set(sender.getName(), map);
                                config.save();
                                break;
                            case "middle":
                            case "중급":
                                if(!(InvisibleArmor.economyAPI.reduceMoney(
                                        sender.getName(), InvisibleArmor.setting.getInt("middle-price"))==1)) {
                                    sender.sendMessage(TextFormat.RED + "No Have Money");
                                    return false;
                                }
                                map.put("kind", "middle");
                                map.put("Durable", 200);
                                config.set(sender.getName(), map);
                                config.save();

                                break;
                            case "high":
                            case "상급":
                                if(!(InvisibleArmor.economyAPI.reduceMoney(
                                        sender.getName(), InvisibleArmor.setting.getInt("high-price"))==1)) {
                                    sender.sendMessage(TextFormat.RED + "No Have Money");
                                    return false;
                                }

                                map.put("kind", "high");
                                map.put("Durable", 300);
                                config.set(sender.getName(), map);
                                config.save();

                                break;

                            default:
                                sender.sendMessage(TextFormat.GREEN+"Armor kind : low, middle, high");
                                return false;
                        }

                        config.set(sender.getName(), map);
                        sender.sendMessage(TextFormat.GREEN + "구매되었습니다.");
                    }
                    break;
                case "status":
                case "상태":
                    if(!config.exists(sender.getName()))
                        sender.sendMessage(TextFormat.GREEN+"갑옷을 소지하고 있지 않습니다.");
                    else{
                        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)config.get(sender.getName());

                        sender.sendMessage(TextFormat.AQUA+"=== Armor Status ===");
                        sender.sendMessage(TextFormat.GOLD + "Kind : "+map.get("kind"));
                        sender.sendMessage(TextFormat.GOLD + "DEF : " +
                                InvisibleArmor.setting.getInt(map.get("kind").toString()));
                        sender.sendMessage(TextFormat.GOLD + "Durablity : "+map.get("Durable"));
                    }
                    break;

            }
        }catch (ArrayIndexOutOfBoundsException e){
            sender.sendMessage(TextFormat.RED+this.getUsage());
            sender.sendMessage(TextFormat.GREEN+"Armor kind : low, middle, high");
        }
        return false;
    }
}
