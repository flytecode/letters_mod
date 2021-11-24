package net.minecraft.world.level.saveddata.maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class MapIndex extends SavedData {
   public static final String f_164761_ = "idcounts";
   private final Object2IntMap<String> f_77878_ = new Object2IntOpenHashMap<>();

   public MapIndex() {
      this.f_77878_.defaultReturnValue(-1);
   }

   public static MapIndex m_164762_(CompoundTag p_164763_) {
      MapIndex mapindex = new MapIndex();

      for(String s : p_164763_.m_128431_()) {
         if (p_164763_.m_128425_(s, 99)) {
            mapindex.f_77878_.put(s, p_164763_.m_128451_(s));
         }
      }

      return mapindex;
   }

   public CompoundTag m_7176_(CompoundTag p_77884_) {
      for(Entry<String> entry : this.f_77878_.object2IntEntrySet()) {
         p_77884_.m_128405_(entry.getKey(), entry.getIntValue());
      }

      return p_77884_;
   }

   public int m_77880_() {
      int i = this.f_77878_.getInt("map") + 1;
      this.f_77878_.put("map", i);
      this.m_77762_();
      return i;
   }
}