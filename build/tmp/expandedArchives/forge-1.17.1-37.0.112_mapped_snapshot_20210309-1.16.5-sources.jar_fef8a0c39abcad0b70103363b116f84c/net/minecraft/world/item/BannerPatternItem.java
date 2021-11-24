package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;

public class BannerPatternItem extends Item {
   private final BannerPattern f_40546_;

   public BannerPatternItem(BannerPattern p_40548_, Item.Properties p_40549_) {
      super(p_40549_);
      this.f_40546_ = p_40548_;
   }

   public BannerPattern m_40555_() {
      return this.f_40546_;
   }

   public void m_7373_(ItemStack p_40551_, @Nullable Level p_40552_, List<Component> p_40553_, TooltipFlag p_40554_) {
      p_40553_.add(this.m_40556_().m_130940_(ChatFormatting.GRAY));
   }

   public MutableComponent m_40556_() {
      return new TranslatableComponent(this.m_5524_() + ".desc");
   }
}