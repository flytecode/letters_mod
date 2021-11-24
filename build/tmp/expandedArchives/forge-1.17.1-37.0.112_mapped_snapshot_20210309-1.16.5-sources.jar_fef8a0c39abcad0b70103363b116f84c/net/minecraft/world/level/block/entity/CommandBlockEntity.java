package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandBlockEntity extends BlockEntity {
   private boolean f_59123_;
   private boolean f_59124_;
   private boolean f_59125_;
   private boolean f_59126_;
   private final BaseCommandBlock f_59127_ = new BaseCommandBlock() {
      public void m_6590_(String p_59157_) {
         super.m_6590_(p_59157_);
         CommandBlockEntity.this.m_6596_();
      }

      public ServerLevel m_5991_() {
         return (ServerLevel)CommandBlockEntity.this.f_58857_;
      }

      public void m_7368_() {
         BlockState blockstate = CommandBlockEntity.this.f_58857_.m_8055_(CommandBlockEntity.this.f_58858_);
         this.m_5991_().m_7260_(CommandBlockEntity.this.f_58858_, blockstate, blockstate, 3);
      }

      public Vec3 m_6607_() {
         return Vec3.m_82512_(CommandBlockEntity.this.f_58858_);
      }

      public CommandSourceStack m_6712_() {
         return new CommandSourceStack(this, Vec3.m_82512_(CommandBlockEntity.this.f_58858_), Vec2.f_82462_, this.m_5991_(), 2, this.m_45439_().getString(), this.m_45439_(), this.m_5991_().m_142572_(), (Entity)null);
      }
   };

   public CommandBlockEntity(BlockPos p_155380_, BlockState p_155381_) {
      super(BlockEntityType.f_58938_, p_155380_, p_155381_);
   }

   public CompoundTag m_6945_(CompoundTag p_59134_) {
      super.m_6945_(p_59134_);
      this.f_59127_.m_45421_(p_59134_);
      p_59134_.m_128379_("powered", this.m_59142_());
      p_59134_.m_128379_("conditionMet", this.m_59145_());
      p_59134_.m_128379_("auto", this.m_59143_());
      return p_59134_;
   }

   public void m_142466_(CompoundTag p_155383_) {
      super.m_142466_(p_155383_);
      this.f_59127_.m_45431_(p_155383_);
      this.f_59123_ = p_155383_.m_128471_("powered");
      this.f_59125_ = p_155383_.m_128471_("conditionMet");
      this.m_59137_(p_155383_.m_128471_("auto"));
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      if (this.m_59147_()) {
         this.m_59139_(false);
         CompoundTag compoundtag = this.m_6945_(new CompoundTag());
         return new ClientboundBlockEntityDataPacket(this.f_58858_, 2, compoundtag);
      } else {
         return null;
      }
   }

   public boolean m_6326_() {
      return true;
   }

   public BaseCommandBlock m_59141_() {
      return this.f_59127_;
   }

   public void m_59135_(boolean p_59136_) {
      this.f_59123_ = p_59136_;
   }

   public boolean m_59142_() {
      return this.f_59123_;
   }

   public boolean m_59143_() {
      return this.f_59124_;
   }

   public void m_59137_(boolean p_59138_) {
      boolean flag = this.f_59124_;
      this.f_59124_ = p_59138_;
      if (!flag && p_59138_ && !this.f_59123_ && this.f_58857_ != null && this.m_59148_() != CommandBlockEntity.Mode.SEQUENCE) {
         this.m_59152_();
      }

   }

   public void m_59144_() {
      CommandBlockEntity.Mode commandblockentity$mode = this.m_59148_();
      if (commandblockentity$mode == CommandBlockEntity.Mode.AUTO && (this.f_59123_ || this.f_59124_) && this.f_58857_ != null) {
         this.m_59152_();
      }

   }

   private void m_59152_() {
      Block block = this.m_58900_().m_60734_();
      if (block instanceof CommandBlock) {
         this.m_59146_();
         this.f_58857_.m_6219_().m_5945_(this.f_58858_, block, 1);
      }

   }

   public boolean m_59145_() {
      return this.f_59125_;
   }

   public boolean m_59146_() {
      this.f_59125_ = true;
      if (this.m_59151_()) {
         BlockPos blockpos = this.f_58858_.m_142300_(this.f_58857_.m_8055_(this.f_58858_).m_61143_(CommandBlock.f_51793_).m_122424_());
         if (this.f_58857_.m_8055_(blockpos).m_60734_() instanceof CommandBlock) {
            BlockEntity blockentity = this.f_58857_.m_7702_(blockpos);
            this.f_59125_ = blockentity instanceof CommandBlockEntity && ((CommandBlockEntity)blockentity).m_59141_().m_45436_() > 0;
         } else {
            this.f_59125_ = false;
         }
      }

      return this.f_59125_;
   }

   public boolean m_59147_() {
      return this.f_59126_;
   }

   public void m_59139_(boolean p_59140_) {
      this.f_59126_ = p_59140_;
   }

   public CommandBlockEntity.Mode m_59148_() {
      BlockState blockstate = this.m_58900_();
      if (blockstate.m_60713_(Blocks.f_50272_)) {
         return CommandBlockEntity.Mode.REDSTONE;
      } else if (blockstate.m_60713_(Blocks.f_50447_)) {
         return CommandBlockEntity.Mode.AUTO;
      } else {
         return blockstate.m_60713_(Blocks.f_50448_) ? CommandBlockEntity.Mode.SEQUENCE : CommandBlockEntity.Mode.REDSTONE;
      }
   }

   public boolean m_59151_() {
      BlockState blockstate = this.f_58857_.m_8055_(this.m_58899_());
      return blockstate.m_60734_() instanceof CommandBlock ? blockstate.m_61143_(CommandBlock.f_51794_) : false;
   }

   public static enum Mode {
      SEQUENCE,
      AUTO,
      REDSTONE;
   }
}