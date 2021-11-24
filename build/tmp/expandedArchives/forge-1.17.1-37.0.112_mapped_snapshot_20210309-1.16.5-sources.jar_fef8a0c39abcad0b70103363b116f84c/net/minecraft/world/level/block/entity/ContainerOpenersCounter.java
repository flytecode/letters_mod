package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public abstract class ContainerOpenersCounter {
   private static final int f_155447_ = 5;
   private int f_155448_;

   protected abstract void m_142292_(Level p_155460_, BlockPos p_155461_, BlockState p_155462_);

   protected abstract void m_142289_(Level p_155473_, BlockPos p_155474_, BlockState p_155475_);

   protected abstract void m_142148_(Level p_155463_, BlockPos p_155464_, BlockState p_155465_, int p_155466_, int p_155467_);

   protected abstract boolean m_142718_(Player p_155451_);

   public void m_155452_(Player p_155453_, Level p_155454_, BlockPos p_155455_, BlockState p_155456_) {
      int i = this.f_155448_++;
      if (i == 0) {
         this.m_142292_(p_155454_, p_155455_, p_155456_);
         p_155454_.m_142346_(p_155453_, GameEvent.f_157803_, p_155455_);
         m_155480_(p_155454_, p_155455_, p_155456_);
      }

      this.m_142148_(p_155454_, p_155455_, p_155456_, i, this.f_155448_);
   }

   public void m_155468_(Player p_155469_, Level p_155470_, BlockPos p_155471_, BlockState p_155472_) {
      int i = this.f_155448_--;
      if (this.f_155448_ == 0) {
         this.m_142289_(p_155470_, p_155471_, p_155472_);
         p_155470_.m_142346_(p_155469_, GameEvent.f_157802_, p_155471_);
      }

      this.m_142148_(p_155470_, p_155471_, p_155472_, i, this.f_155448_);
   }

   private int m_155457_(Level p_155458_, BlockPos p_155459_) {
      int i = p_155459_.m_123341_();
      int j = p_155459_.m_123342_();
      int k = p_155459_.m_123343_();
      float f = 5.0F;
      AABB aabb = new AABB((double)((float)i - 5.0F), (double)((float)j - 5.0F), (double)((float)k - 5.0F), (double)((float)(i + 1) + 5.0F), (double)((float)(j + 1) + 5.0F), (double)((float)(k + 1) + 5.0F));
      return p_155458_.m_142425_(EntityTypeTest.m_156916_(Player.class), aabb, this::m_142718_).size();
   }

   public void m_155476_(Level p_155477_, BlockPos p_155478_, BlockState p_155479_) {
      int i = this.m_155457_(p_155477_, p_155478_);
      int j = this.f_155448_;
      if (j != i) {
         boolean flag = i != 0;
         boolean flag1 = j != 0;
         if (flag && !flag1) {
            this.m_142292_(p_155477_, p_155478_, p_155479_);
            p_155477_.m_142346_((Entity)null, GameEvent.f_157803_, p_155478_);
         } else if (!flag) {
            this.m_142289_(p_155477_, p_155478_, p_155479_);
            p_155477_.m_142346_((Entity)null, GameEvent.f_157802_, p_155478_);
         }

         this.f_155448_ = i;
      }

      this.m_142148_(p_155477_, p_155478_, p_155479_, j, i);
      if (i > 0) {
         m_155480_(p_155477_, p_155478_, p_155479_);
      }

   }

   public int m_155450_() {
      return this.f_155448_;
   }

   private static void m_155480_(Level p_155481_, BlockPos p_155482_, BlockState p_155483_) {
      p_155481_.m_6219_().m_5945_(p_155482_, p_155483_.m_60734_(), 5);
   }
}