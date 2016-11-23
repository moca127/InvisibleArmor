package kr.mocha;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import kr.mocha.command.ArmorCommand;
import me.onebone.economyapi.EconomyAPI;

import java.util.LinkedHashMap;

/**
 * Created by mocha on 16. 11. 21.
 */
public class InvisibleArmor extends PluginBase implements Listener{
    public static EconomyAPI economyAPI = EconomyAPI.getInstance();
    public static InvisibleArmor instance;
    public static Config setting;

    @Override
    public void onEnable() {
        instance = this;
        this.getDataFolder().mkdirs();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("low-price", 5000);
        map.put("middle-price", 10000);
        map.put("high-price", 20000);
        map.put("low", 12);
        map.put("middle", 16);
        map.put("high", 20);
        setting = new Config(getDataFolder()+"/setting.yml", Config.YAML);
        this.getServer().getCommandMap().register("armor", new ArmorCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.save();
        super.onDisable();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void whenPlayerDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player)event.getEntity();

            if(getConfig().exists(player.getName())){
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)getConfig().get(player.getName());

                switch (map.get("kind").toString()){
                    case "low":
                        map.put("Durable", (int)map.get("Durable")-1);
                        event.setDamage((int)event.getDamage()*10/setting.getInt("low"));
                        this.getConfig().set(player.getName(), map);
                        save();
                        break;
                    case "middle":
                        map.put("Durable", (int)map.get("Durable")-1);
                        event.setDamage((int)event.getDamage()*10/setting.getInt("middle"));
                        this.getConfig().set(player.getName(), map);
                        save();
                        break;
                    case "high":
                        map.put("Durable", (int)map.get("Durable")-1);
                        event.setDamage((int)event.getDamage()*10/setting.getInt("high"));
                        this.getConfig().set(player.getName(), map);
                        save();
                        break;
                }
            }
        }
    }

    /*utils*/
    public void save(){
        this.getConfig().save();
    }

    public static InvisibleArmor getInstance(){
        return instance;
    }
}
