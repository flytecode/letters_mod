package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.apache.commons.lang3.Validate;

public class BannerItem extends StandingAndWallBlockItem {
   private static final String f_150695_ = "block.minecraft.banner.";

   public BannerItem(Block p_40534_, Block p_40535_, Item.Properties p_40536_) {
      super(p_40534_, p_40535_, p_40536_);
      Validate.isInstanceOf(AbstractBannerBlock.class, p_40534_);
      Validate.isInstanceOf(AbstractBannerBlock.class, p_40535_);
   }

   public static void m_40542_(ItemStack p_40543_, List<Component> p_40544_) {
      CompoundTag compoundtag = p_40543_.m_41737_("BlockEntityTag");
      if (compoundtag != null && compoundtag.m_128441_("Patterns")) {
         ListTag listtag = compoundtag.m_128437_("Patterns", 10);

         for(int i = 0; i < listtag.size() && i < 6; ++i) {
            CompoundTag compoundtag1 = listtag.m_128728_(i);
            DyeColor dyecolor = DyeColor.m_41053_(compoundtag1.m_128451_("Color"));
            BannerPattern bannerpattern = BannerPattern.m_58575_(compoundtag1.m_128461_("Pattern"));
            if (bannerpattern != null) {
               p_40544_.add((new TranslatableComponent("block.minecraft.banner." + bannerpattern.m_58572_() + "." + dyecolor.m_41065_())).m_130940_(ChatFormatting.GRAY));
            }
         }

      }
   }

   public DyeColor m_40545_() {
      return ((AbstractBannerBlock)this.m_40614_()).m_48674_();
   }

   public void m_7373_(ItemStack p_40538_, @Nullable Level p_40539_, List<Component> p_40540_, TooltipFlag p_40541_) {
      m_40542_(p_40538_, p_40540_);
   }
}