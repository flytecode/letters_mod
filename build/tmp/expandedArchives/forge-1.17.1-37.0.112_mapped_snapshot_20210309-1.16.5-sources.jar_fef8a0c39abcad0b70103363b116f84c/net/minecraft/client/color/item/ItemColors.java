package net.minecraft.client.color.item;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemColors {
   private static final int f_168642_ = -1;
   // FORGE: Use RegistryDelegates as non-Vanilla item ids are not constant
   private final java.util.Map<net.minecraftforge.registries.IRegistryDelegate<Item>, ItemColor> f_92674_ = new java.util.HashMap<>();

   public static ItemColors m_92683_(BlockColors p_92684_) {
      ItemColors itemcolors = new ItemColors();
      itemcolors.m_92689_((p_92708_, p_92709_) -> {
         return p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.m_41720_()).m_41121_(p_92708_);
      }, Items.f_42407_, Items.f_42408_, Items.f_42462_, Items.f_42463_, Items.f_42654_);
      itemcolors.m_92689_((p_92705_, p_92706_) -> {
         return GrassColor.m_46415_(0.5D, 1.0D);
      }, Blocks.f_50359_, Blocks.f_50360_);
      itemcolors.m_92689_((p_92702_, p_92703_) -> {
         if (p_92703_ != 1) {
            return -1;
         } else {
            CompoundTag compoundtag = p_92702_.m_41737_("Explosion");
            int[] aint = compoundtag != null && compoundtag.m_128425_("Colors", 11) ? compoundtag.m_128465_("Colors") : null;
            if (aint != null && aint.length != 0) {
               if (aint.length == 1) {
                  return aint[0];
               } else {
                  int i = 0;
                  int j = 0;
                  int k = 0;

                  for(int l : aint) {
                     i += (l & 16711680) >> 16;
                     j += (l & '\uff00') >> 8;
                     k += (l & 255) >> 0;
                  }

                  i = i / aint.length;
                  j = j / aint.length;
                  k = k / aint.length;
                  return i << 16 | j << 8 | k;
               }
            } else {
               return 9079434;
            }
         }
      }, Items.f_42689_);
      itemcolors.m_92689_((p_92699_, p_92700_) -> {
         return p_92700_ > 0 ? -1 : PotionUtils.m_43575_(p_92699_);
      }, Items.f_42589_, Items.f_42736_, Items.f_42739_);

      for(SpawnEggItem spawneggitem : SpawnEggItem.m_43233_()) {
         itemcolors.m_92689_((p_92681_, p_92682_) -> {
            return spawneggitem.m_43211_(p_92682_);
         }, spawneggitem);
      }

      itemcolors.m_92689_((p_92687_, p_92688_) -> {
         BlockState blockstate = ((BlockItem)p_92687_.m_41720_()).m_40614_().m_49966_();
         return p_92684_.m_92577_(blockstate, (BlockAndTintGetter)null, (BlockPos)null, p_92688_);
      }, Blocks.f_50440_, Blocks.f_50034_, Blocks.f_50035_, Blocks.f_50191_, Blocks.f_50050_, Blocks.f_50051_, Blocks.f_50052_, Blocks.f_50053_, Blocks.f_50054_, Blocks.f_50055_, Blocks.f_50196_);
      itemcolors.m_92689_((p_92696_, p_92697_) -> {
         return p_92697_ == 0 ? PotionUtils.m_43575_(p_92696_) : -1;
      }, Items.f_42738_);
      itemcolors.m_92689_((p_92693_, p_92694_) -> {
         return p_92694_ == 0 ? -1 : MapItem.m_42918_(p_92693_);
      }, Items.f_42573_);
      net.minecraftforge.client.ForgeHooksClient.onItemColorsInit(itemcolors, p_92684_);
      return itemcolors;
   }

   public int m_92676_(ItemStack p_92677_, int p_92678_) {
      ItemColor itemcolor = this.f_92674_.get(p_92677_.m_41720_().delegate);
      return itemcolor == null ? -1 : itemcolor.m_92671_(p_92677_, p_92678_);
   }

   public void m_92689_(ItemColor p_92690_, ItemLike... p_92691_) {
      for(ItemLike itemlike : p_92691_) {
         this.f_92674_.put(itemlike.m_5456_().delegate, p_92690_);
      }

   }
}
