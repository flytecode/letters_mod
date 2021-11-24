package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerBlockEntity extends BlockEntity {
   private final BaseSpawner f_59788_ = new BaseSpawner() {
      public void m_142523_(Level p_155767_, BlockPos p_155768_, int p_155769_) {
         p_155767_.m_7696_(p_155768_, Blocks.f_50085_, p_155769_, 0);
      }

      public void m_142667_(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
         super.m_142667_(p_155771_, p_155772_, p_155773_);
         if (p_155771_ != null) {
            BlockState blockstate = p_155771_.m_8055_(p_155772_);
            p_155771_.m_7260_(p_155772_, blockstate, blockstate, 4);
         }

      }

      @javax.annotation.Nullable
       public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return SpawnerBlockEntity.this; }
   };

   public SpawnerBlockEntity(BlockPos p_155752_, BlockState p_155753_) {
      super(BlockEntityType.f_58925_, p_155752_, p_155753_);
   }

   public void m_142466_(CompoundTag p_155760_) {
      super.m_142466_(p_155760_);
      this.f_59788_.m_151328_(this.f_58857_, this.f_58858_, p_155760_);
   }

   public CompoundTag m_6945_(CompoundTag p_59795_) {
      super.m_6945_(p_59795_);
      this.f_59788_.m_151339_(this.f_58857_, this.f_58858_, p_59795_);
      return p_59795_;
   }

   public static void m_155754_(Level p_155755_, BlockPos p_155756_, BlockState p_155757_, SpawnerBlockEntity p_155758_) {
      p_155758_.f_59788_.m_151319_(p_155755_, p_155756_);
   }

   public static void m_155761_(Level p_155762_, BlockPos p_155763_, BlockState p_155764_, SpawnerBlockEntity p_155765_) {
      p_155765_.f_59788_.m_151311_((ServerLevel)p_155762_, p_155763_);
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 1, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      CompoundTag compoundtag = this.m_6945_(new CompoundTag());
      compoundtag.m_128473_("SpawnPotentials");
      return compoundtag;
   }

   public boolean m_7531_(int p_59797_, int p_59798_) {
      return this.f_59788_.m_151316_(this.f_58857_, p_59797_) ? true : super.m_7531_(p_59797_, p_59798_);
   }

   public boolean m_6326_() {
      return true;
   }

   public BaseSpawner m_59801_() {
      return this.f_59788_;
   }
}
