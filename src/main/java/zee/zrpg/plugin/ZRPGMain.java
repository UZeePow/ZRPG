package zee.zrpg.plugin;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import zee.zrpg.plugin.component.SkillData;

import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class ZRPGMain extends JavaPlugin {
    private static ZRPGMain instance;
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private ComponentType<EntityStore, SkillData> skillDataComponent;

    public ZRPGMain(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());
        this.getCommandRegistry().registerCommand(new OpenSkillTreeCommand());
        this.skillDataComponent = this.getEntityStoreRegistry().registerComponent(
            SkillData.class,
            "SkillData",
            SkillData.CODEC
        );
    }

    public static ZRPGMain instance() {
        return instance;
    }

    public ComponentType<EntityStore, SkillData> getSkillDataComponent() {
        return skillDataComponent;
    }
}