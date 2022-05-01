package wuyudaxia.CoreLevel;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wuyudaxia.CoreLevel.CommandEx.MainCommand;
import wuyudaxia.CoreLevel.IO.FileManager;
import wuyudaxia.CoreLevel.Listener.InventoryClickListener;
import wuyudaxia.CoreLevel.Listener.JoinQuitListener;
import wuyudaxia.CoreLevel.Manager.CoinManager;
import wuyudaxia.CoreLevel.Manager.LevelManager;
import wuyudaxia.CoreLevel.Papi.Papi;
import wuyudaxia.CoreLevel.SmartInventory.LevelInventory;
import wuyudaxia.CoreLevel.SmartInventory.PackageInventory;
import wuyudaxia.CoreLevel.Util.ConfigT;
import wuyudaxia.CoreLevel.sql.SQLMain;
import wuyudaxia.CoreLevel.Util.ConfigF;
import wuyudaxia.CoreLevel.Achievement.MainAchievement;
import wuyudaxia.CoreLevel.Listener.QuestHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CoreLevel extends JavaPlugin implements Listener{
    public Map<String, Integer> coinMap = new HashMap<>();
    public Map<String, ConfigF> infoMap = new HashMap<>();
    public ArrayList<ConfigF> filec = new ArrayList<ConfigF>();
    public HashMap<String, ConfigT> filec2 = new HashMap<>();
    public MainAchievement achieve = new MainAchievement();
    public FileManager fileManager = new FileManager(this);
    public LevelManager levelManager = new LevelManager(this);
    public CoinManager coinManager = new CoinManager(this);
    public PackageInventory packageInventory = new PackageInventory(this);
    public LevelInventory levelInventory = new LevelInventory(this);
    public JoinQuitListener joinQuitListener = new JoinQuitListener(this);
    public InventoryClickListener inventoryClickListener = new InventoryClickListener(this);
    public SQLMain mysql;
    public Boolean BAR;
    @Override
    public void onEnable(){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null);
        else{
            getLogger().warning("CoreLevel need PlaceholderAPI! Please add it at https://www.spigotmc.org/resources/placeholderapi.6245/");
            Bukkit.getPluginManager().disablePlugin(this);
            }
        new Papi(this).register();
        Bukkit.getPluginManager().registerEvents(new QuestHandler(this), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(joinQuitListener,this);
        Bukkit.getPluginManager().registerEvents(inventoryClickListener,this);
        this.getCommand("corelevel").setExecutor(new MainCommand(this));
        this.getCommand("cl").setExecutor(new MainCommand(this));
        try {
            fileManager.createFile();
            fileManager.createFile2();
            }
        catch (IOException e) {
            e.printStackTrace();
            }
        __init__();
        Bukkit.getLogger().info("CoreLevel have been plugged in!");
    }
    public void __init__(){
        fileManager.readConfig();
        fileManager.readConfig2();
        if(filec.get(0).R.equals("false"))
            BAR = false;
        else
            BAR = true;
        filec.remove(0);
        mysql = new SQLMain(filec);

    }
    @Override
    public void onDisable(){
        Bukkit.getLogger().info("CoreLevel have been disabled!");
    }


    public void chengjiu(Player p) throws SQLException {
        p.openInventory(achieve.getInv1(p));
    }





}
