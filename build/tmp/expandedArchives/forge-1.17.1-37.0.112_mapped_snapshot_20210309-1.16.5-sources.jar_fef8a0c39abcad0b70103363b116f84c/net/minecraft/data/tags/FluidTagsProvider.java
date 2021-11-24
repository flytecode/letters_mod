package net.minecraft.data.tags;

import java.nio.file.Path;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidTagsProvider extends TagsProvider<Fluid> {
   @Deprecated
   public FluidTagsProvider(DataGenerator p_126523_) {
      super(p_126523_, Registry.f_122822_);
   }
   public FluidTagsProvider(DataGenerator p_126523_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      super(p_126523_, Registry.f_122822_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_126548_(FluidTags.f_13131_).m_126584_(Fluids.f_76193_, Fluids.f_76192_);
      this.m_126548_(FluidTags.f_13132_).m_126584_(Fluids.f_76195_, Fluids.f_76194_);
   }

   protected Path m_6648_(ResourceLocation p_126526_) {
      return this.f_126539_.m_123916_().resolve("data/" + p_126526_.m_135827_() + "/tags/fluids/" + p_126526_.m_135815_() + ".json");
   }

   public String m_6055_() {
      return "Fluid Tags";
   }
}
