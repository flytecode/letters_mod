package net.minecraft.world.item;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem extends Item implements Vanishable {
   private static final Logger f_40715_ = LogManager.getLogger();
   public static final String f_150786_ = "LodestonePos";
   public static final String f_150787_ = "LodestoneDimension";
   public static final String f_150788_ = "LodestoneTracked";

   public CompassItem(Item.Properties p_40718_) {
      super(p_40718_);
   }

   public static boolean m_40736_(ItemStack p_40737_) {
      CompoundTag compoundtag = p_40737_.m_41783_();
      return compoundtag != null && (compoundtag.m_128441_("LodestoneDimension") || compoundtag.m_128441_("LodestonePos"));
   }

   public boolean m_5812_(ItemStack p_40739_) {
      return m_40736_(p_40739_) || super.m_5812_(p_40739_);
   }

   public static Optional<ResourceKey<Level>> m_40727_(CompoundTag p_40728_) {
      return Level.f_46427_.parse(NbtOps.f_128958_, p_40728_.m_128423_("LodestoneDimension")).result();
   }

   public void m_6883_(ItemStack p_40720_, Level p_40721_, Entity p_40722_, int p_40723_, boolean p_40724_) {
      if (!p_40721_.f_46443_) {
         if (m_40736_(p_40720_)) {
            CompoundTag compoundtag = p_40720_.m_41784_();
            if (compoundtag.m_128441_("LodestoneTracked") && !compoundtag.m_128471_("LodestoneTracked")) {
               return;
            }

            Optional<ResourceKey<Level>> optional = m_40727_(compoundtag);
            if (optional.isPresent() && optional.get() == p_40721_.m_46472_() && compoundtag.m_128441_("LodestonePos")) {
               BlockPos blockpos = NbtUtils.m_129239_(compoundtag.m_128469_("LodestonePos"));
               if (!p_40721_.m_46739_(blockpos) || !((ServerLevel)p_40721_).m_8904_().m_27044_(PoiType.f_27351_, blockpos)) {
                  compoundtag.m_128473_("LodestonePos");
               }
            }
         }

      }
   }

   public InteractionResult m_6225_(UseOnContext p_40726_) {
      BlockPos blockpos = p_40726_.m_8083_();
      Level level = p_40726_.m_43725_();
      if (!level.m_8055_(blockpos).m_60713_(Blocks.f_50729_)) {
         return super.m_6225_(p_40726_);
      } else {
         level.m_5594_((Player)null, blockpos, SoundEvents.f_12107_, SoundSource.PLAYERS, 1.0F, 1.0F);
         Player player = p_40726_.m_43723_();
         ItemStack itemstack = p_40726_.m_43722_();
         boolean flag = !player.m_150110_().f_35937_ && itemstack.m_41613_() == 1;
         if (flag) {
            this.m_40732_(level.m_46472_(), blockpos, itemstack.m_41784_());
         } else {
            ItemStack itemstack1 = new ItemStack(Items.f_42522_, 1);
            CompoundTag compoundtag = itemstack.m_41782_() ? itemstack.m_41783_().m_6426_() : new CompoundTag();
            itemstack1.m_41751_(compoundtag);
            if (!player.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            this.m_40732_(level.m_46472_(), blockpos, compoundtag);
            if (!player.m_150109_().m_36054_(itemstack1)) {
               player.m_36176_(itemstack1, false);
            }
         }

         return InteractionResult.m_19078_(level.f_46443_);
      }
   }

   private void m_40732_(ResourceKey<Level> p_40733_, BlockPos p_40734_, CompoundTag p_40735_) {
      p_40735_.m_128365_("LodestonePos", NbtUtils.m_129224_(p_40734_));
      Level.f_46427_.encodeStart(NbtOps.f_128958_, p_40733_).resultOrPartial(f_40715_::error).ifPresent((p_40731_) -> {
         p_40735_.m_128365_("LodestoneDimension", p_40731_);
      });
      p_40735_.m_128379_("LodestoneTracked", true);
   }

   public String m_5671_(ItemStack p_40741_) {
      return m_40736_(p_40741_) ? "item.minecraft.lodestone_compass" : super.m_5671_(p_40741_);
   }
}