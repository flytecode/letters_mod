package net.minecraft.world.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;

public class SpawnData extends WeightedEntry.IntrusiveBase {
   public static final int f_151629_ = 1;
   public static final String f_151630_ = "minecraft:pig";
   private final CompoundTag f_47257_;

   public SpawnData() {
      super(1);
      this.f_47257_ = new CompoundTag();
      this.f_47257_.m_128359_("id", "minecraft:pig");
   }

   public SpawnData(CompoundTag p_47263_) {
      this(p_47263_.m_128425_("Weight", 99) ? p_47263_.m_128451_("Weight") : 1, p_47263_.m_128469_("Entity"));
   }

   public SpawnData(int p_47260_, CompoundTag p_47261_) {
      super(p_47260_);
      this.f_47257_ = p_47261_;
      ResourceLocation resourcelocation = ResourceLocation.m_135820_(p_47261_.m_128461_("id"));
      if (resourcelocation != null) {
         p_47261_.m_128359_("id", resourcelocation.toString());
      } else {
         p_47261_.m_128359_("id", "minecraft:pig");
      }

   }

   public CompoundTag m_47264_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128365_("Entity", this.f_47257_);
      compoundtag.m_128405_("Weight", this.m_142631_().m_146281_());
      return compoundtag;
   }

   public CompoundTag m_47265_() {
      return this.f_47257_;
   }
}