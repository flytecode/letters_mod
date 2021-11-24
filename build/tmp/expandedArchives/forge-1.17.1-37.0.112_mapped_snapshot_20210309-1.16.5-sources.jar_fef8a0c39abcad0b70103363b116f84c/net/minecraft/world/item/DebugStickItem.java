package net.minecraft.world.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class DebugStickItem extends Item {
   public DebugStickItem(Item.Properties p_40948_) {
      super(p_40948_);
   }

   public boolean m_5812_(ItemStack p_40978_) {
      return true;
   }

   public boolean m_6777_(BlockState p_40962_, Level p_40963_, BlockPos p_40964_, Player p_40965_) {
      if (!p_40963_.f_46443_) {
         this.m_150802_(p_40965_, p_40962_, p_40963_, p_40964_, false, p_40965_.m_21120_(InteractionHand.MAIN_HAND));
      }

      return false;
   }

   public InteractionResult m_6225_(UseOnContext p_40960_) {
      Player player = p_40960_.m_43723_();
      Level level = p_40960_.m_43725_();
      if (!level.f_46443_ && player != null) {
         BlockPos blockpos = p_40960_.m_8083_();
         if (!this.m_150802_(player, level.m_8055_(blockpos), level, blockpos, true, p_40960_.m_43722_())) {
            return InteractionResult.FAIL;
         }
      }

      return InteractionResult.m_19078_(level.f_46443_);
   }

   private boolean m_150802_(Player p_150803_, BlockState p_150804_, LevelAccessor p_150805_, BlockPos p_150806_, boolean p_150807_, ItemStack p_150808_) {
      if (!p_150803_.m_36337_()) {
         return false;
      } else {
         Block block = p_150804_.m_60734_();
         StateDefinition<Block, BlockState> statedefinition = block.m_49965_();
         Collection<Property<?>> collection = statedefinition.m_61092_();
         String s = Registry.f_122824_.m_7981_(block).toString();
         if (collection.isEmpty()) {
            m_40956_(p_150803_, new TranslatableComponent(this.m_5524_() + ".empty", s));
            return false;
         } else {
            CompoundTag compoundtag = p_150808_.m_41698_("DebugProperty");
            String s1 = compoundtag.m_128461_(s);
            Property<?> property = statedefinition.m_61081_(s1);
            if (p_150807_) {
               if (property == null) {
                  property = collection.iterator().next();
               }

               BlockState blockstate = m_40969_(p_150804_, property, p_150803_.m_36341_());
               p_150805_.m_7731_(p_150806_, blockstate, 18);
               m_40956_(p_150803_, new TranslatableComponent(this.m_5524_() + ".update", property.m_61708_(), m_40966_(blockstate, property)));
            } else {
               property = m_40973_(collection, property, p_150803_.m_36341_());
               String s2 = property.m_61708_();
               compoundtag.m_128359_(s, s2);
               m_40956_(p_150803_, new TranslatableComponent(this.m_5524_() + ".select", s2, m_40966_(p_150804_, property)));
            }

            return true;
         }
      }
   }

   private static <T extends Comparable<T>> BlockState m_40969_(BlockState p_40970_, Property<T> p_40971_, boolean p_40972_) {
      return p_40970_.m_61124_(p_40971_, m_40973_(p_40971_.m_6908_(), p_40970_.m_61143_(p_40971_), p_40972_));
   }

   private static <T> T m_40973_(Iterable<T> p_40974_, @Nullable T p_40975_, boolean p_40976_) {
      return (T)(p_40976_ ? Util.m_137554_(p_40974_, p_40975_) : Util.m_137466_(p_40974_, p_40975_));
   }

   private static void m_40956_(Player p_40957_, Component p_40958_) {
      ((ServerPlayer)p_40957_).m_9146_(p_40958_, ChatType.GAME_INFO, Util.f_137441_);
   }

   private static <T extends Comparable<T>> String m_40966_(BlockState p_40967_, Property<T> p_40968_) {
      return p_40968_.m_6940_(p_40967_.m_61143_(p_40968_));
   }
}