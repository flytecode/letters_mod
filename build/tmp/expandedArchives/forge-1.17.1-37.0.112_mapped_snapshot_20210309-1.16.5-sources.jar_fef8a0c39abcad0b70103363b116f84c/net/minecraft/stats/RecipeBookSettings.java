package net.minecraft.stats;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.RecipeBookType;

public final class RecipeBookSettings {
   private static final Map<RecipeBookType, Pair<String, String>> f_12725_ = ImmutableMap.of(RecipeBookType.CRAFTING, Pair.of("isGuiOpen", "isFilteringCraftable"), RecipeBookType.FURNACE, Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"), RecipeBookType.BLAST_FURNACE, Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"), RecipeBookType.SMOKER, Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable"));
   private final Map<RecipeBookType, RecipeBookSettings.TypeSettings> f_12726_;

   private RecipeBookSettings(Map<RecipeBookType, RecipeBookSettings.TypeSettings> p_12730_) {
      this.f_12726_ = p_12730_;
   }

   public RecipeBookSettings() {
      this(Util.m_137469_(Maps.newEnumMap(RecipeBookType.class), (p_12740_) -> {
         for(RecipeBookType recipebooktype : RecipeBookType.values()) {
            p_12740_.put(recipebooktype, new RecipeBookSettings.TypeSettings(false, false));
         }

      }));
   }

   public boolean m_12734_(RecipeBookType p_12735_) {
      return (this.f_12726_.get(p_12735_)).f_12766_;
   }

   public void m_12736_(RecipeBookType p_12737_, boolean p_12738_) {
      (this.f_12726_.get(p_12737_)).f_12766_ = p_12738_;
   }

   public boolean m_12754_(RecipeBookType p_12755_) {
      return (this.f_12726_.get(p_12755_)).f_12767_;
   }

   public void m_12756_(RecipeBookType p_12757_, boolean p_12758_) {
      (this.f_12726_.get(p_12757_)).f_12767_ = p_12758_;
   }

   public static RecipeBookSettings m_12752_(FriendlyByteBuf p_12753_) {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> map = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType recipebooktype : RecipeBookType.values()) {
         boolean flag = p_12753_.readBoolean();
         boolean flag1 = p_12753_.readBoolean();
         map.put(recipebooktype, new RecipeBookSettings.TypeSettings(flag, flag1));
      }

      return new RecipeBookSettings(map);
   }

   public void m_12761_(FriendlyByteBuf p_12762_) {
      for(RecipeBookType recipebooktype : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings recipebooksettings$typesettings = this.f_12726_.get(recipebooktype);
         if (recipebooksettings$typesettings == null) {
            p_12762_.writeBoolean(false);
            p_12762_.writeBoolean(false);
         } else {
            p_12762_.writeBoolean(recipebooksettings$typesettings.f_12766_);
            p_12762_.writeBoolean(recipebooksettings$typesettings.f_12767_);
         }
      }

   }

   public static RecipeBookSettings m_12741_(CompoundTag p_12742_) {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> map = Maps.newEnumMap(RecipeBookType.class);
      f_12725_.forEach((p_12750_, p_12751_) -> {
         boolean flag = p_12742_.m_128471_(p_12751_.getFirst());
         boolean flag1 = p_12742_.m_128471_(p_12751_.getSecond());
         map.put(p_12750_, new RecipeBookSettings.TypeSettings(flag, flag1));
      });
      return new RecipeBookSettings(map);
   }

   public void m_12759_(CompoundTag p_12760_) {
      f_12725_.forEach((p_12745_, p_12746_) -> {
         RecipeBookSettings.TypeSettings recipebooksettings$typesettings = this.f_12726_.get(p_12745_);
         p_12760_.m_128379_(p_12746_.getFirst(), recipebooksettings$typesettings.f_12766_);
         p_12760_.m_128379_(p_12746_.getSecond(), recipebooksettings$typesettings.f_12767_);
      });
   }

   public RecipeBookSettings m_12731_() {
      Map<RecipeBookType, RecipeBookSettings.TypeSettings> map = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType recipebooktype : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings recipebooksettings$typesettings = this.f_12726_.get(recipebooktype);
         map.put(recipebooktype, recipebooksettings$typesettings.m_12771_());
      }

      return new RecipeBookSettings(map);
   }

   public void m_12732_(RecipeBookSettings p_12733_) {
      this.f_12726_.clear();

      for(RecipeBookType recipebooktype : RecipeBookType.values()) {
         RecipeBookSettings.TypeSettings recipebooksettings$typesettings = p_12733_.f_12726_.get(recipebooktype);
         this.f_12726_.put(recipebooktype, recipebooksettings$typesettings.m_12771_());
      }

   }

   public boolean equals(Object p_12764_) {
      return this == p_12764_ || p_12764_ instanceof RecipeBookSettings && this.f_12726_.equals(((RecipeBookSettings)p_12764_).f_12726_);
   }

   public int hashCode() {
      return this.f_12726_.hashCode();
   }

   static final class TypeSettings {
      boolean f_12766_;
      boolean f_12767_;

      public TypeSettings(boolean p_12769_, boolean p_12770_) {
         this.f_12766_ = p_12769_;
         this.f_12767_ = p_12770_;
      }

      public RecipeBookSettings.TypeSettings m_12771_() {
         return new RecipeBookSettings.TypeSettings(this.f_12766_, this.f_12767_);
      }

      public boolean equals(Object p_12783_) {
         if (this == p_12783_) {
            return true;
         } else if (!(p_12783_ instanceof RecipeBookSettings.TypeSettings)) {
            return false;
         } else {
            RecipeBookSettings.TypeSettings recipebooksettings$typesettings = (RecipeBookSettings.TypeSettings)p_12783_;
            return this.f_12766_ == recipebooksettings$typesettings.f_12766_ && this.f_12767_ == recipebooksettings$typesettings.f_12767_;
         }
      }

      public int hashCode() {
         int i = this.f_12766_ ? 1 : 0;
         return 31 * i + (this.f_12767_ ? 1 : 0);
      }

      public String toString() {
         return "[open=" + this.f_12766_ + ", filtering=" + this.f_12767_ + "]";
      }
   }
}