package net.minecraft.world.entity;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.StringRepresentable;

public enum MobCategory implements StringRepresentable, net.minecraftforge.common.IExtensibleEnum {
   MONSTER("monster", 70, false, false, 128),
   CREATURE("creature", 10, true, true, 128),
   AMBIENT("ambient", 15, true, false, 128),
   UNDERGROUND_WATER_CREATURE("underground_water_creature", 5, true, false, 128),
   WATER_CREATURE("water_creature", 5, true, false, 128),
   WATER_AMBIENT("water_ambient", 20, true, false, 64),
   MISC("misc", -1, true, true, 128);

   public static final Codec<MobCategory> f_21584_ = net.minecraftforge.common.IExtensibleEnum.createCodecForExtensibleEnum(MobCategory::values, MobCategory::m_21605_);
   private static final Map<String, MobCategory> f_21585_ = Arrays.stream(values()).collect(Collectors.toMap(MobCategory::m_21607_, (p_21604_) -> {
      return p_21604_;
   }));
   private final int f_21586_;
   private final boolean f_21587_;
   private final boolean f_21588_;
   private final String f_21589_;
   private final int f_21590_ = 32;
   private final int f_21591_;

   private MobCategory(String p_21597_, int p_21598_, boolean p_21599_, boolean p_21600_, int p_21601_) {
      this.f_21589_ = p_21597_;
      this.f_21586_ = p_21598_;
      this.f_21587_ = p_21599_;
      this.f_21588_ = p_21600_;
      this.f_21591_ = p_21601_;
   }

   public String m_21607_() {
      return this.f_21589_;
   }

   public String m_7912_() {
      return this.f_21589_;
   }

   public static MobCategory m_21605_(String p_21606_) {
      return f_21585_.get(p_21606_);
   }

   public int m_21608_() {
      return this.f_21586_;
   }

   public boolean m_21609_() {
      return this.f_21587_;
   }

   public boolean m_21610_() {
      return this.f_21588_;
   }

   public static MobCategory create(String name, String id, int maxNumberOfCreatureIn, boolean isPeacefulCreatureIn, boolean isAnimalIn, int despawnDistance) {
      throw new IllegalStateException("Enum not extended");
   }

   @Override
   @Deprecated
   public void init() {
      f_21585_.put(this.m_21607_(), this);
   }

   public int m_21611_() {
      return this.f_21591_;
   }

   public int m_21612_() {
      return 32;
   }
}
