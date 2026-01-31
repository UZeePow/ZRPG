package zee.zrpg.plugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import zee.zrpg.plugin.component.SkillData;

public class SkillTreePage extends InteractiveCustomUIPage<SkillData> {
    public SkillTreePage(PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, SkillData.CODEC);
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder uiCommandBuilder, @NonNullDecl UIEventBuilder uiEventBuilder, @NonNullDecl Store<EntityStore> store) {
        uiCommandBuilder.append("Pages/SkillTree.ui");
        if(store.getComponent(ref, ZRPGMain.instance().getSkillDataComponent())) {
            SkillData data = store.getComponent(ref, ZRPGMain.instance().getSkillDataComponent());
            uiCommandBuilder.set("skillNumberInput", Integer.toString(data.getSkillLevel()));
        } else {
            uiCommandBuilder.set("skillNumberInput", "0");
        }

        uiEventBuilder.addEventBinding(
            CustomUIEventBindingType.ValueChanged,
            "#NumberField",
            new EventData().append("@SkillNumber", "#NumberField.Value")
        ).addEventBinding(
            CustomUIEventBindingType.ValueChanged,
            "#Toggle",
            new EventData().append("@PlayerClass", "#Toggle.Value")
        );
    }

    @Override
    public void handleDataEvent(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl Store<EntityStore> store, @NonNullDecl SkillData data) {
        // Get the player component
        Player player = store.getComponent(ref, Player.getComponentType());

        // Since we're using putComponent, it could be useful to check if it already exists on the player in case we want to update it
        if(store.getComponent(ref, ZRPGMain.instance().getSkillDataComponent()) != null) {
            var comp = store.getComponent(ref, ZRPGMain.instance().getSkillDataComponent());
            comp.setSkillLevel(data.getSkillLevel());
        } else {
            var comp = new SkillData();
            comp.setSkillLevel(data.getSkillLevel());

            // putComponent allows you to insert declared objects
            store.putComponent(ref, ZRPGMain.instance().getSkillDataComponent(), comp);
        }

        // Close the UI
        // player.getPageManager().setPage(ref, store, Page.None);
    }
}
