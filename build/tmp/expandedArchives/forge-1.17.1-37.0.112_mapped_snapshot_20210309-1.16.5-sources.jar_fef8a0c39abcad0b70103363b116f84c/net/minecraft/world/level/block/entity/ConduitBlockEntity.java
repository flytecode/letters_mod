package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ConduitBlockEntity extends BlockEntity {
   private static final int f_155390_ = 2;
   private static final int f_155391_ = 13;
   private static final float f_155392_ = -0.0375F;
   private static final int f_155393_ = 16;
   private static final int f_155394_ = 42;
   private static final int f_155395_ = 8;
   private static final Block[] f_59184_ = new Block[]{Blocks.f_50377_, Blocks.f_50378_, Blocks.f_50386_, Blocks.f_50379_};
   public int f_59183_;
   private float f_59185_;
   private boolean f_59186_;
   private boolean f_59187_;
   private final List<BlockPos> f_59188_ = Lists.newArrayList();
   @Nullable
   private LivingEntity f_59189_;
   @Nullable
   private UUID f_59190_;
   private long f_59191_;

   public ConduitBlockEntity(BlockPos p_155397_, BlockState p_155398_) {
      super(BlockEntityType.f_58941_, p_155397_, p_155398_);
   }

   public void m_142466_(CompoundTag p_155437_) {
      super.m_142466_(p_155437_);
      if (p_155437_.m_128403_("Target")) {
         this.f_59190_ = p_155437_.m_128342_("Target");
      } else {
         this.f_59190_ = null;
      }

   }

   public CompoundTag m_6945_(CompoundTag p_59207_) {
      super.m_6945_(p_59207_);
      if (this.f_59189_ != null) {
         p_59207_.m_128362_("Target", this.f_59189_.m_142081_());
      }

      return p_59207_;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 5, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public static void m_155403_(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, ConduitBlockEntity p_155407_) {
      ++p_155407_.f_59183_;
      long i = p_155404_.m_46467_();
      List<BlockPos> list = p_155407_.f_59188_;
      if (i % 40L == 0L) {
         p_155407_.f_59186_ = m_155414_(p_155404_, p_155405_, list);
         m_155428_(p_155407_, list);
      }

      m_155399_(p_155404_, p_155405_, p_155407_);
      m_155418_(p_155404_, p_155405_, list, p_155407_.f_59189_, p_155407_.f_59183_);
      if (p_155407_.m_59216_()) {
         ++p_155407_.f_59185_;
      }

   }

   public static void m_155438_(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, ConduitBlockEntity p_155442_) {
      ++p_155442_.f_59183_;
      long i = p_155439_.m_46467_();
      List<BlockPos> list = p_155442_.f_59188_;
      if (i % 40L == 0L) {
         boolean flag = m_155414_(p_155439_, p_155440_, list);
         if (flag != p_155442_.f_59186_) {
            SoundEvent soundevent = flag ? SoundEvents.f_11767_ : SoundEvents.f_11824_;
            p_155439_.m_5594_((Player)null, p_155440_, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         p_155442_.f_59186_ = flag;
         m_155428_(p_155442_, list);
         if (flag) {
            m_155443_(p_155439_, p_155440_, list);
            m_155408_(p_155439_, p_155440_, p_155441_, list, p_155442_);
         }
      }

      if (p_155442_.m_59216_()) {
         if (i % 80L == 0L) {
            p_155439_.m_5594_((Player)null, p_155440_, SoundEvents.f_11768_, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         if (i > p_155442_.f_59191_) {
            p_155442_.f_59191_ = i + 60L + (long)p_155439_.m_5822_().nextInt(40);
            p_155439_.m_5594_((Player)null, p_155440_, SoundEvents.f_11822_, SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }

   }

   private static void m_155428_(ConduitBlockEntity p_155429_, List<BlockPos> p_155430_) {
      p_155429_.m_59214_(p_155430_.size() >= 42);
   }

   private static boolean m_155414_(Level p_155415_, BlockPos p_155416_, List<BlockPos> p_155417_) {
      p_155417_.clear();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            for(int k = -1; k <= 1; ++k) {
               BlockPos blockpos = p_155416_.m_142082_(i, j, k);
               if (!p_155415_.m_46801_(blockpos)) {
                  return false;
               }
            }
         }
      }

      for(int j1 = -2; j1 <= 2; ++j1) {
         for(int k1 = -2; k1 <= 2; ++k1) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               int i2 = Math.abs(j1);
               int l = Math.abs(k1);
               int i1 = Math.abs(l1);
               if ((i2 > 1 || l > 1 || i1 > 1) && (j1 == 0 && (l == 2 || i1 == 2) || k1 == 0 && (i2 == 2 || i1 == 2) || l1 == 0 && (i2 == 2 || l == 2))) {
                  BlockPos blockpos1 = p_155416_.m_142082_(j1, k1, l1);
                  BlockState blockstate = p_155415_.m_8055_(blockpos1);

                  if (blockstate.isConduitFrame(p_155415_, blockpos1, p_155416_)) {
                     p_155417_.add(blockpos1);
                  }
               }
            }
         }
      }

      return p_155417_.size() >= 16;
   }

   private static void m_155443_(Level p_155444_, BlockPos p_155445_, List<BlockPos> p_155446_) {
      int i = p_155446_.size();
      int j = i / 7 * 16;
      int k = p_155445_.m_123341_();
      int l = p_155445_.m_123342_();
      int i1 = p_155445_.m_123343_();
      AABB aabb = (new AABB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).m_82400_((double)j).m_82363_(0.0D, (double)p_155444_.m_141928_(), 0.0D);
      List<Player> list = p_155444_.m_45976_(Player.class, aabb);
      if (!list.isEmpty()) {
         for(Player player : list) {
            if (p_155445_.m_123314_(player.m_142538_(), (double)j) && player.m_20070_()) {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19592_, 260, 0, true, true));
            }
         }

      }
   }

   private static void m_155408_(Level p_155409_, BlockPos p_155410_, BlockState p_155411_, List<BlockPos> p_155412_, ConduitBlockEntity p_155413_) {
      LivingEntity livingentity = p_155413_.f_59189_;
      int i = p_155412_.size();
      if (i < 42) {
         p_155413_.f_59189_ = null;
      } else if (p_155413_.f_59189_ == null && p_155413_.f_59190_ != null) {
         p_155413_.f_59189_ = m_155424_(p_155409_, p_155410_, p_155413_.f_59190_);
         p_155413_.f_59190_ = null;
      } else if (p_155413_.f_59189_ == null) {
         List<LivingEntity> list = p_155409_.m_6443_(LivingEntity.class, m_155431_(p_155410_), (p_59213_) -> {
            return p_59213_ instanceof Enemy && p_59213_.m_20070_();
         });
         if (!list.isEmpty()) {
            p_155413_.f_59189_ = list.get(p_155409_.f_46441_.nextInt(list.size()));
         }
      } else if (!p_155413_.f_59189_.m_6084_() || !p_155410_.m_123314_(p_155413_.f_59189_.m_142538_(), 8.0D)) {
         p_155413_.f_59189_ = null;
      }

      if (p_155413_.f_59189_ != null) {
         p_155409_.m_6263_((Player)null, p_155413_.f_59189_.m_20185_(), p_155413_.f_59189_.m_20186_(), p_155413_.f_59189_.m_20189_(), SoundEvents.f_11823_, SoundSource.BLOCKS, 1.0F, 1.0F);
         p_155413_.f_59189_.m_6469_(DamageSource.f_19319_, 4.0F);
      }

      if (livingentity != p_155413_.f_59189_) {
         p_155409_.m_7260_(p_155410_, p_155411_, p_155411_, 2);
      }

   }

   private static void m_155399_(Level p_155400_, BlockPos p_155401_, ConduitBlockEntity p_155402_) {
      if (p_155402_.f_59190_ == null) {
         p_155402_.f_59189_ = null;
      } else if (p_155402_.f_59189_ == null || !p_155402_.f_59189_.m_142081_().equals(p_155402_.f_59190_)) {
         p_155402_.f_59189_ = m_155424_(p_155400_, p_155401_, p_155402_.f_59190_);
         if (p_155402_.f_59189_ == null) {
            p_155402_.f_59190_ = null;
         }
      }

   }

   private static AABB m_155431_(BlockPos p_155432_) {
      int i = p_155432_.m_123341_();
      int j = p_155432_.m_123342_();
      int k = p_155432_.m_123343_();
      return (new AABB((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1))).m_82400_(8.0D);
   }

   @Nullable
   private static LivingEntity m_155424_(Level p_155425_, BlockPos p_155426_, UUID p_155427_) {
      List<LivingEntity> list = p_155425_.m_6443_(LivingEntity.class, m_155431_(p_155426_), (p_155435_) -> {
         return p_155435_.m_142081_().equals(p_155427_);
      });
      return list.size() == 1 ? list.get(0) : null;
   }

   private static void m_155418_(Level p_155419_, BlockPos p_155420_, List<BlockPos> p_155421_, @Nullable Entity p_155422_, int p_155423_) {
      Random random = p_155419_.f_46441_;
      double d0 = (double)(Mth.m_14031_((float)(p_155423_ + 35) * 0.1F) / 2.0F + 0.5F);
      d0 = (d0 * d0 + d0) * (double)0.3F;
      Vec3 vec3 = new Vec3((double)p_155420_.m_123341_() + 0.5D, (double)p_155420_.m_123342_() + 1.5D + d0, (double)p_155420_.m_123343_() + 0.5D);

      for(BlockPos blockpos : p_155421_) {
         if (random.nextInt(50) == 0) {
            BlockPos blockpos1 = blockpos.m_141950_(p_155420_);
            float f = -0.5F + random.nextFloat() + (float)blockpos1.m_123341_();
            float f1 = -2.0F + random.nextFloat() + (float)blockpos1.m_123342_();
            float f2 = -0.5F + random.nextFloat() + (float)blockpos1.m_123343_();
            p_155419_.m_7106_(ParticleTypes.f_123775_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, (double)f, (double)f1, (double)f2);
         }
      }

      if (p_155422_ != null) {
         Vec3 vec31 = new Vec3(p_155422_.m_20185_(), p_155422_.m_20188_(), p_155422_.m_20189_());
         float f3 = (-0.5F + random.nextFloat()) * (3.0F + p_155422_.m_20205_());
         float f4 = -1.0F + random.nextFloat() * p_155422_.m_20206_();
         float f5 = (-0.5F + random.nextFloat()) * (3.0F + p_155422_.m_20205_());
         Vec3 vec32 = new Vec3((double)f3, (double)f4, (double)f5);
         p_155419_.m_7106_(ParticleTypes.f_123775_, vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, vec32.f_82479_, vec32.f_82480_, vec32.f_82481_);
      }

   }

   public boolean m_59216_() {
      return this.f_59186_;
   }

   public boolean m_59217_() {
      return this.f_59187_;
   }

   private void m_59214_(boolean p_59215_) {
      this.f_59187_ = p_59215_;
   }

   public float m_59197_(float p_59198_) {
      return (this.f_59185_ + p_59198_) * -0.0375F;
   }
}
