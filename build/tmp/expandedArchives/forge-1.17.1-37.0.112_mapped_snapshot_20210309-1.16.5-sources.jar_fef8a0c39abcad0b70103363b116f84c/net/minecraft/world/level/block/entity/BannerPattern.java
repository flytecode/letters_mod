package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public enum BannerPattern implements net.minecraftforge.common.IExtensibleEnum {
   BASE("base", "b", false),
   SQUARE_BOTTOM_LEFT("square_bottom_left", "bl"),
   SQUARE_BOTTOM_RIGHT("square_bottom_right", "br"),
   SQUARE_TOP_LEFT("square_top_left", "tl"),
   SQUARE_TOP_RIGHT("square_top_right", "tr"),
   STRIPE_BOTTOM("stripe_bottom", "bs"),
   STRIPE_TOP("stripe_top", "ts"),
   STRIPE_LEFT("stripe_left", "ls"),
   STRIPE_RIGHT("stripe_right", "rs"),
   STRIPE_CENTER("stripe_center", "cs"),
   STRIPE_MIDDLE("stripe_middle", "ms"),
   STRIPE_DOWNRIGHT("stripe_downright", "drs"),
   STRIPE_DOWNLEFT("stripe_downleft", "dls"),
   STRIPE_SMALL("small_stripes", "ss"),
   CROSS("cross", "cr"),
   STRAIGHT_CROSS("straight_cross", "sc"),
   TRIANGLE_BOTTOM("triangle_bottom", "bt"),
   TRIANGLE_TOP("triangle_top", "tt"),
   TRIANGLES_BOTTOM("triangles_bottom", "bts"),
   TRIANGLES_TOP("triangles_top", "tts"),
   DIAGONAL_LEFT("diagonal_left", "ld"),
   DIAGONAL_RIGHT("diagonal_up_right", "rd"),
   DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud"),
   DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud"),
   CIRCLE_MIDDLE("circle", "mc"),
   RHOMBUS_MIDDLE("rhombus", "mr"),
   HALF_VERTICAL("half_vertical", "vh"),
   HALF_HORIZONTAL("half_horizontal", "hh"),
   HALF_VERTICAL_MIRROR("half_vertical_right", "vhr"),
   HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb"),
   BORDER("border", "bo"),
   CURLY_BORDER("curly_border", "cbo"),
   GRADIENT("gradient", "gra"),
   GRADIENT_UP("gradient_up", "gru"),
   BRICKS("bricks", "bri"),
   GLOBE("globe", "glb", true),
   CREEPER("creeper", "cre", true),
   SKULL("skull", "sku", true),
   FLOWER("flower", "flo", true),
   MOJANG("mojang", "moj", true),
   PIGLIN("piglin", "pig", true);

   private static final BannerPattern[] f_58529_ = values();
   public static final int f_58526_ = f_58529_.length;
   public static final int f_58527_ = (int)Arrays.stream(f_58529_).filter((p_58581_) -> {
      return p_58581_.f_58530_;
   }).count();
   public static final int f_58528_ = f_58526_ - f_58527_ - 1;
   private final boolean f_58530_;
   private final String f_58531_;
   final String f_58532_;

   private BannerPattern(String p_58564_, String p_58565_) {
      this(p_58564_, p_58565_, false);
   }

   private BannerPattern(String p_58569_, String p_58570_, boolean p_58571_) {
      this.f_58531_ = p_58569_;
      this.f_58532_ = p_58570_;
      this.f_58530_ = p_58571_;
   }

   public ResourceLocation m_58577_(boolean p_58578_) {
      String s = p_58578_ ? "banner" : "shield";
      return new ResourceLocation("entity/" + s + "/" + this.m_58572_());
   }

   public String m_58572_() {
      return this.f_58531_;
   }

   public String m_58579_() {
      return this.f_58532_;
   }

   @Nullable
   public static BannerPattern m_58575_(String p_58576_) {
      for(BannerPattern bannerpattern : values()) {
         if (bannerpattern.f_58532_.equals(p_58576_)) {
            return bannerpattern;
         }
      }

      return null;
   }

   @Nullable
   public static BannerPattern m_155045_(String p_155046_) {
      for(BannerPattern bannerpattern : values()) {
         if (bannerpattern.f_58531_.equals(p_155046_)) {
            return bannerpattern;
         }
      }

      return null;
   }

   public static BannerPattern create(String enumName, String fileNameIn, String hashNameIn) {
      throw new IllegalStateException("Enum not extended");
   }

   public static BannerPattern create(String enumName, String fileNameIn, String hashNameIn, boolean hasPatternItem) {
      throw new IllegalStateException("Enum not extended");
   }

   public static class Builder {
      private final List<Pair<BannerPattern, DyeColor>> f_58585_ = Lists.newArrayList();

      public BannerPattern.Builder m_58588_(BannerPattern p_58589_, DyeColor p_58590_) {
         return this.m_155048_(Pair.of(p_58589_, p_58590_));
      }

      public BannerPattern.Builder m_155048_(Pair<BannerPattern, DyeColor> p_155049_) {
         this.f_58585_.add(p_155049_);
         return this;
      }

      public ListTag m_58587_() {
         ListTag listtag = new ListTag();

         for(Pair<BannerPattern, DyeColor> pair : this.f_58585_) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128359_("Pattern", (pair.getFirst()).f_58532_);
            compoundtag.m_128405_("Color", pair.getSecond().m_41060_());
            listtag.add(compoundtag);
         }

         return listtag;
      }
   }
}
