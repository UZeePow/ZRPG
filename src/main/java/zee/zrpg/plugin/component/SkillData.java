package zee.zrpg.plugin.component;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class SkillData implements Component<EntityStore> {
    private int skillLevel;

    public static final BuilderCodec<SkillData> CODEC =
            BuilderCodec.builder(SkillData.class, SkillData::new)
                    .append(new KeyedCodec<>("SkillLevel", Codec.INTEGER),
                            (data, value) -> data.skillLevel = value, // setter
                            data -> data.skillLevel) // getter
                    .addValidator(Validators.nonNull())
                    .add()
                    .build();

    public SkillData() {
        this.skillLevel = 0;
    }

    public SkillData(SkillData clone) {
        this.skillLevel = clone.skillLevel;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new SkillData(this);
    }

    public void setSkillLevel(int level) {
        this.skillLevel = level;
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }
}
