package net.minecraft.commands.arguments.blocks;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockInput implements Predicate<BlockInWorld> {
   private final BlockState f_114662_;
   private final Set<Property<?>> f_114663_;
   @Nullable
   private final CompoundTag f_114664_;

   public BlockInput(BlockState p_114666_, Set<Property<?>> p_114667_, @Nullable CompoundTag p_114668_) {
      this.f_114662_ = p_114666_;
      this.f_114663_ = p_114667_;
      this.f_114664_ = p_114668_;
   }

   public BlockState m_114669_() {
      return this.f_114662_;
   }

   public Set<Property<?>> m_173526_() {
      return this.f_114663_;
   }

   public boolean test(BlockInWorld p_114675_) {
      BlockState blockstate = p_114675_.m_61168_();
      if (!blockstate.m_60713_(this.f_114662_.m_60734_())) {
         return false;
      } else {
         for(Property<?> property : this.f_114663_) {
            if (blockstate.m_61143_(property) != this.f_114662_.m_61143_(property)) {
               return false;
            }
         }

         if (this.f_114664_ == null) {
            return true;
         } else {
            BlockEntity blockentity = p_114675_.m_61174_();
            return blockentity != null && NbtUtils.m_129235_(this.f_114664_, blockentity.m_6945_(new CompoundTag()), true);
         }
      }
   }

   public boolean m_173523_(ServerLevel p_173524_, BlockPos p_173525_) {
      return this.test(new BlockInWorld(p_173524_, p_173525_, false));
   }

   public boolean m_114670_(ServerLevel p_114671_, BlockPos p_114672_, int p_114673_) {
      BlockState blockstate = Block.m_49931_(this.f_114662_, p_114671_, p_114672_);
      if (blockstate.m_60795_()) {
         blockstate = this.f_114662_;
      }

      if (!p_114671_.m_7731_(p_114672_, blockstate, p_114673_)) {
         return false;
      } else {
         if (this.f_114664_ != null) {
            BlockEntity blockentity = p_114671_.m_7702_(p_114672_);
            if (blockentity != null) {
               CompoundTag compoundtag = this.f_114664_.m_6426_();
               compoundtag.m_128405_("x", p_114672_.m_123341_());
               compoundtag.m_128405_("y", p_114672_.m_123342_());
               compoundtag.m_128405_("z", p_114672_.m_123343_());
               blockentity.m_142466_(compoundtag);
            }
         }

         return true;
      }
   }
}