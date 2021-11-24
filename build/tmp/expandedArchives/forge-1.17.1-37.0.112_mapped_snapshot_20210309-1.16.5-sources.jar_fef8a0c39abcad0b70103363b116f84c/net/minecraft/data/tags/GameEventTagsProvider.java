package net.minecraft.data.tags;

import java.nio.file.Path;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.level.gameevent.GameEvent;

public class GameEventTagsProvider extends TagsProvider<GameEvent> {
   @Deprecated
   public GameEventTagsProvider(DataGenerator p_176826_) {
      super(p_176826_, Registry.f_175412_);
   }
   public GameEventTagsProvider(DataGenerator p_126517_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(p_126517_, Registry.f_175412_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_126548_(GameEventTags.f_144302_).m_126584_(GameEvent.f_157791_, GameEvent.f_157792_, GameEvent.f_157793_, GameEvent.f_157794_, GameEvent.f_157795_, GameEvent.f_157796_, GameEvent.f_157797_, GameEvent.f_157798_, GameEvent.f_157799_, GameEvent.f_157800_, GameEvent.f_157801_, GameEvent.f_157802_, GameEvent.f_157803_, GameEvent.f_157804_, GameEvent.f_157805_, GameEvent.f_157806_, GameEvent.f_157807_, GameEvent.f_157808_, GameEvent.f_157809_, GameEvent.f_157810_, GameEvent.f_157811_, GameEvent.f_157812_, GameEvent.f_157813_, GameEvent.f_157814_, GameEvent.f_157815_, GameEvent.f_157816_, GameEvent.f_157769_, GameEvent.f_157770_, GameEvent.f_157771_, GameEvent.f_157772_, GameEvent.f_157773_, GameEvent.f_157774_, GameEvent.f_157775_, GameEvent.f_157776_, GameEvent.f_157777_, GameEvent.f_157778_, GameEvent.f_157779_, GameEvent.f_157780_, GameEvent.f_157781_, GameEvent.f_157782_, GameEvent.f_157783_, GameEvent.f_157784_, GameEvent.f_157785_, GameEvent.f_157786_, GameEvent.f_157787_);
      this.m_126548_(GameEventTags.f_144303_).m_126584_(GameEvent.f_157770_, GameEvent.f_157778_, GameEvent.f_157785_, GameEvent.f_157786_);
   }

   protected Path m_6648_(ResourceLocation p_176829_) {
      return this.f_126539_.m_123916_().resolve("data/" + p_176829_.m_135827_() + "/tags/game_events/" + p_176829_.m_135815_() + ".json");
   }

   public String m_6055_() {
      return "Game Event Tags";
   }
}
