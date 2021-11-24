package net.minecraft.world.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.material.MaterialColor;

public enum DyeColor implements StringRepresentable {
   WHITE(0, "white", 16383998, MaterialColor.f_76406_, 15790320, 16777215),
   ORANGE(1, "orange", 16351261, MaterialColor.f_76413_, 15435844, 16738335),
   MAGENTA(2, "magenta", 13061821, MaterialColor.f_76414_, 12801229, 16711935),
   LIGHT_BLUE(3, "light_blue", 3847130, MaterialColor.f_76415_, 6719955, 10141901),
   YELLOW(4, "yellow", 16701501, MaterialColor.f_76416_, 14602026, 16776960),
   LIME(5, "lime", 8439583, MaterialColor.f_76417_, 4312372, 12582656),
   PINK(6, "pink", 15961002, MaterialColor.f_76418_, 14188952, 16738740),
   GRAY(7, "gray", 4673362, MaterialColor.f_76419_, 4408131, 8421504),
   LIGHT_GRAY(8, "light_gray", 10329495, MaterialColor.f_76420_, 11250603, 13882323),
   CYAN(9, "cyan", 1481884, MaterialColor.f_76421_, 2651799, 65535),
   PURPLE(10, "purple", 8991416, MaterialColor.f_76422_, 8073150, 10494192),
   BLUE(11, "blue", 3949738, MaterialColor.f_76361_, 2437522, 255),
   BROWN(12, "brown", 8606770, MaterialColor.f_76362_, 5320730, 9127187),
   GREEN(13, "green", 6192150, MaterialColor.f_76363_, 3887386, 65280),
   RED(14, "red", 11546150, MaterialColor.f_76364_, 11743532, 16711680),
   BLACK(15, "black", 1908001, MaterialColor.f_76365_, 1973019, 0);

   private static final DyeColor[] f_41032_ = Arrays.stream(values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).toArray((p_41067_) -> {
      return new DyeColor[p_41067_];
   });
   private static final Int2ObjectOpenHashMap<DyeColor> f_41033_ = new Int2ObjectOpenHashMap<>(Arrays.stream(values()).collect(Collectors.toMap((p_41064_) -> {
      return p_41064_.f_41040_;
   }, (p_41056_) -> {
      return p_41056_;
   })));
   private final int f_41034_;
   private final String f_41035_;
   private final MaterialColor f_41036_;
   private final float[] f_41039_;
   private final int f_41040_;
   private final net.minecraftforge.common.Tags.IOptionalNamedTag<Item> tag;
   private final int f_41041_;

   private DyeColor(int p_41046_, String p_41047_, int p_41048_, MaterialColor p_41049_, int p_41050_, int p_41051_) {
      this.f_41034_ = p_41046_;
      this.f_41035_ = p_41047_;
      this.f_41036_ = p_41049_;
      this.f_41041_ = p_41051_;
      int i = (p_41048_ & 16711680) >> 16;
      int j = (p_41048_ & '\uff00') >> 8;
      int k = (p_41048_ & 255) >> 0;
      this.tag = net.minecraft.tags.ItemTags.createOptional(new net.minecraft.resources.ResourceLocation("forge", "dyes/" + p_41047_));
      this.f_41039_ = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
      this.f_41040_ = p_41050_;
   }

   public int m_41060_() {
      return this.f_41034_;
   }

   public String m_41065_() {
      return this.f_41035_;
   }

   public float[] m_41068_() {
      return this.f_41039_;
   }

   public MaterialColor m_41069_() {
      return this.f_41036_;
   }

   public int m_41070_() {
      return this.f_41040_;
   }

   public int m_41071_() {
      return this.f_41041_;
   }

   public static DyeColor m_41053_(int p_41054_) {
      if (p_41054_ < 0 || p_41054_ >= f_41032_.length) {
         p_41054_ = 0;
      }

      return f_41032_[p_41054_];
   }

   public static DyeColor m_41057_(String p_41058_, DyeColor p_41059_) {
      for(DyeColor dyecolor : values()) {
         if (dyecolor.f_41035_.equals(p_41058_)) {
            return dyecolor;
         }
      }

      return p_41059_;
   }

   @Nullable
   public static DyeColor m_41061_(int p_41062_) {
      return f_41033_.get(p_41062_);
   }

   public String toString() {
      return this.f_41035_;
   }

   public String m_7912_() {
      return this.f_41035_;
   }

   public net.minecraftforge.common.Tags.IOptionalNamedTag<Item> getTag() {
      return tag;
   }

   @Nullable
   public static DyeColor getColor(ItemStack stack) {
      if (stack.m_41720_() instanceof DyeItem)
         return ((DyeItem)stack.m_41720_()).m_41089_();

      for (DyeColor color : f_41032_) {
         if (stack.m_150922_(color.getTag()))
             return color;
      }

      return null;
   }
}
