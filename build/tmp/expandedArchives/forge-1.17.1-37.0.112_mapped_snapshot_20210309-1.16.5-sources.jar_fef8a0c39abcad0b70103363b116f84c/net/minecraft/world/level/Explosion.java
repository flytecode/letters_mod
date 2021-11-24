package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Explosion {
   private static final ExplosionDamageCalculator f_46008_ = new ExplosionDamageCalculator();
   private static final int f_151469_ = 16;
   private final boolean f_46009_;
   private final Explosion.BlockInteraction f_46010_;
   private final Random f_46011_ = new Random();
   private final Level f_46012_;
   private final double f_46013_;
   private final double f_46014_;
   private final double f_46015_;
   @Nullable
   private final Entity f_46016_;
   private final float f_46017_;
   private final DamageSource f_46018_;
   private final ExplosionDamageCalculator f_46019_;
   private final List<BlockPos> f_46020_ = Lists.newArrayList();
   private final Map<Player, Vec3> f_46021_ = Maps.newHashMap();
   private final Vec3 position;

   public Explosion(Level p_151471_, @Nullable Entity p_151472_, double p_151473_, double p_151474_, double p_151475_, float p_151476_) {
      this(p_151471_, p_151472_, p_151473_, p_151474_, p_151475_, p_151476_, false, Explosion.BlockInteraction.DESTROY);
   }

   public Explosion(Level p_46024_, @Nullable Entity p_46025_, double p_46026_, double p_46027_, double p_46028_, float p_46029_, List<BlockPos> p_46030_) {
      this(p_46024_, p_46025_, p_46026_, p_46027_, p_46028_, p_46029_, false, Explosion.BlockInteraction.DESTROY, p_46030_);
   }

   public Explosion(Level p_46041_, @Nullable Entity p_46042_, double p_46043_, double p_46044_, double p_46045_, float p_46046_, boolean p_46047_, Explosion.BlockInteraction p_46048_, List<BlockPos> p_46049_) {
      this(p_46041_, p_46042_, p_46043_, p_46044_, p_46045_, p_46046_, p_46047_, p_46048_);
      this.f_46020_.addAll(p_46049_);
   }

   public Explosion(Level p_46032_, @Nullable Entity p_46033_, double p_46034_, double p_46035_, double p_46036_, float p_46037_, boolean p_46038_, Explosion.BlockInteraction p_46039_) {
      this(p_46032_, p_46033_, (DamageSource)null, (ExplosionDamageCalculator)null, p_46034_, p_46035_, p_46036_, p_46037_, p_46038_, p_46039_);
   }

   public Explosion(Level p_46051_, @Nullable Entity p_46052_, @Nullable DamageSource p_46053_, @Nullable ExplosionDamageCalculator p_46054_, double p_46055_, double p_46056_, double p_46057_, float p_46058_, boolean p_46059_, Explosion.BlockInteraction p_46060_) {
      this.f_46012_ = p_46051_;
      this.f_46016_ = p_46052_;
      this.f_46017_ = p_46058_;
      this.f_46013_ = p_46055_;
      this.f_46014_ = p_46056_;
      this.f_46015_ = p_46057_;
      this.f_46009_ = p_46059_;
      this.f_46010_ = p_46060_;
      this.f_46018_ = p_46053_ == null ? DamageSource.m_19358_(this) : p_46053_;
      this.f_46019_ = p_46054_ == null ? this.m_46062_(p_46052_) : p_46054_;
      this.position = new Vec3(this.f_46013_, this.f_46014_, this.f_46015_);
   }

   private ExplosionDamageCalculator m_46062_(@Nullable Entity p_46063_) {
      return (ExplosionDamageCalculator)(p_46063_ == null ? f_46008_ : new EntityBasedExplosionDamageCalculator(p_46063_));
   }

   public static float m_46064_(Vec3 p_46065_, Entity p_46066_) {
      AABB aabb = p_46066_.m_142469_();
      double d0 = 1.0D / ((aabb.f_82291_ - aabb.f_82288_) * 2.0D + 1.0D);
      double d1 = 1.0D / ((aabb.f_82292_ - aabb.f_82289_) * 2.0D + 1.0D);
      double d2 = 1.0D / ((aabb.f_82293_ - aabb.f_82290_) * 2.0D + 1.0D);
      double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
      double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
      if (!(d0 < 0.0D) && !(d1 < 0.0D) && !(d2 < 0.0D)) {
         int i = 0;
         int j = 0;

         for(float f = 0.0F; f <= 1.0F; f = (float)((double)f + d0)) {
            for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1)) {
               for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2)) {
                  double d5 = Mth.m_14139_((double)f, aabb.f_82288_, aabb.f_82291_);
                  double d6 = Mth.m_14139_((double)f1, aabb.f_82289_, aabb.f_82292_);
                  double d7 = Mth.m_14139_((double)f2, aabb.f_82290_, aabb.f_82293_);
                  Vec3 vec3 = new Vec3(d5 + d3, d6, d7 + d4);
                  if (p_46066_.f_19853_.m_45547_(new ClipContext(vec3, p_46065_, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, p_46066_)).m_6662_() == HitResult.Type.MISS) {
                     ++i;
                  }

                  ++j;
               }
            }
         }

         return (float)i / (float)j;
      } else {
         return 0.0F;
      }
   }

   public void m_46061_() {
      this.f_46012_.m_142346_(this.f_46016_, GameEvent.f_157812_, new BlockPos(this.f_46013_, this.f_46014_, this.f_46015_));
      Set<BlockPos> set = Sets.newHashSet();
      int i = 16;

      for(int j = 0; j < 16; ++j) {
         for(int k = 0; k < 16; ++k) {
            for(int l = 0; l < 16; ++l) {
               if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                  double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                  double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                  double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                  d0 = d0 / d3;
                  d1 = d1 / d3;
                  d2 = d2 / d3;
                  float f = this.f_46017_ * (0.7F + this.f_46012_.f_46441_.nextFloat() * 0.6F);
                  double d4 = this.f_46013_;
                  double d6 = this.f_46014_;
                  double d8 = this.f_46015_;

                  for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                     BlockPos blockpos = new BlockPos(d4, d6, d8);
                     BlockState blockstate = this.f_46012_.m_8055_(blockpos);
                     FluidState fluidstate = this.f_46012_.m_6425_(blockpos);
                     if (!this.f_46012_.m_46739_(blockpos)) {
                        break;
                     }

                     Optional<Float> optional = this.f_46019_.m_6617_(this, this.f_46012_, blockpos, blockstate, fluidstate);
                     if (optional.isPresent()) {
                        f -= (optional.get() + 0.3F) * 0.3F;
                     }

                     if (f > 0.0F && this.f_46019_.m_6714_(this, this.f_46012_, blockpos, blockstate, f)) {
                        set.add(blockpos);
                     }

                     d4 += d0 * (double)0.3F;
                     d6 += d1 * (double)0.3F;
                     d8 += d2 * (double)0.3F;
                  }
               }
            }
         }
      }

      this.f_46020_.addAll(set);
      float f2 = this.f_46017_ * 2.0F;
      int k1 = Mth.m_14107_(this.f_46013_ - (double)f2 - 1.0D);
      int l1 = Mth.m_14107_(this.f_46013_ + (double)f2 + 1.0D);
      int i2 = Mth.m_14107_(this.f_46014_ - (double)f2 - 1.0D);
      int i1 = Mth.m_14107_(this.f_46014_ + (double)f2 + 1.0D);
      int j2 = Mth.m_14107_(this.f_46015_ - (double)f2 - 1.0D);
      int j1 = Mth.m_14107_(this.f_46015_ + (double)f2 + 1.0D);
      List<Entity> list = this.f_46012_.m_45933_(this.f_46016_, new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
      net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.f_46012_, this, list, f2);
      Vec3 vec3 = new Vec3(this.f_46013_, this.f_46014_, this.f_46015_);

      for(int k2 = 0; k2 < list.size(); ++k2) {
         Entity entity = list.get(k2);
         if (!entity.m_6128_()) {
            double d12 = Math.sqrt(entity.m_20238_(vec3)) / (double)f2;
            if (d12 <= 1.0D) {
               double d5 = entity.m_20185_() - this.f_46013_;
               double d7 = (entity instanceof PrimedTnt ? entity.m_20186_() : entity.m_20188_()) - this.f_46014_;
               double d9 = entity.m_20189_() - this.f_46015_;
               double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
               if (d13 != 0.0D) {
                  d5 = d5 / d13;
                  d7 = d7 / d13;
                  d9 = d9 / d13;
                  double d14 = (double)m_46064_(vec3, entity);
                  double d10 = (1.0D - d12) * d14;
                  entity.m_6469_(this.m_46077_(), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f2 + 1.0D)));
                  double d11 = d10;
                  if (entity instanceof LivingEntity) {
                     d11 = ProtectionEnchantment.m_45135_((LivingEntity)entity, d10);
                  }

                  entity.m_20256_(entity.m_20184_().m_82520_(d5 * d11, d7 * d11, d9 * d11));
                  if (entity instanceof Player) {
                     Player player = (Player)entity;
                     if (!player.m_5833_() && (!player.m_7500_() || !player.m_150110_().f_35935_)) {
                        this.f_46021_.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                     }
                  }
               }
            }
         }
      }

   }

   public void m_46075_(boolean p_46076_) {
      if (this.f_46012_.f_46443_) {
         this.f_46012_.m_7785_(this.f_46013_, this.f_46014_, this.f_46015_, SoundEvents.f_11913_, SoundSource.BLOCKS, 4.0F, (1.0F + (this.f_46012_.f_46441_.nextFloat() - this.f_46012_.f_46441_.nextFloat()) * 0.2F) * 0.7F, false);
      }

      boolean flag = this.f_46010_ != Explosion.BlockInteraction.NONE;
      if (p_46076_) {
         if (!(this.f_46017_ < 2.0F) && flag) {
            this.f_46012_.m_7106_(ParticleTypes.f_123812_, this.f_46013_, this.f_46014_, this.f_46015_, 1.0D, 0.0D, 0.0D);
         } else {
            this.f_46012_.m_7106_(ParticleTypes.f_123813_, this.f_46013_, this.f_46014_, this.f_46015_, 1.0D, 0.0D, 0.0D);
         }
      }

      if (flag) {
         ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
         Collections.shuffle(this.f_46020_, this.f_46012_.f_46441_);

         for(BlockPos blockpos : this.f_46020_) {
            BlockState blockstate = this.f_46012_.m_8055_(blockpos);
            Block block = blockstate.m_60734_();
            if (!blockstate.m_60795_()) {
               BlockPos blockpos1 = blockpos.m_7949_();
               this.f_46012_.m_46473_().m_6180_("explosion_blocks");
               if (blockstate.canDropFromExplosion(this.f_46012_, blockpos, this) && this.f_46012_ instanceof ServerLevel) {
                  BlockEntity blockentity = blockstate.m_155947_() ? this.f_46012_.m_7702_(blockpos) : null;
                  LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.f_46012_)).m_78977_(this.f_46012_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(blockpos)).m_78972_(LootContextParams.f_81463_, ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, blockentity).m_78984_(LootContextParams.f_81455_, this.f_46016_);
                  if (this.f_46010_ == Explosion.BlockInteraction.DESTROY) {
                     lootcontext$builder.m_78972_(LootContextParams.f_81464_, this.f_46017_);
                  }

                  blockstate.m_60724_(lootcontext$builder).forEach((p_46074_) -> {
                     m_46067_(objectarraylist, p_46074_, blockpos1);
                  });
               }

               blockstate.onBlockExploded(this.f_46012_, blockpos, this);
               this.f_46012_.m_46473_().m_7238_();
            }
         }

         for(Pair<ItemStack, BlockPos> pair : objectarraylist) {
            Block.m_49840_(this.f_46012_, pair.getSecond(), pair.getFirst());
         }
      }

      if (this.f_46009_) {
         for(BlockPos blockpos2 : this.f_46020_) {
            if (this.f_46011_.nextInt(3) == 0 && this.f_46012_.m_8055_(blockpos2).m_60795_() && this.f_46012_.m_8055_(blockpos2.m_7495_()).m_60804_(this.f_46012_, blockpos2.m_7495_())) {
               this.f_46012_.m_46597_(blockpos2, BaseFireBlock.m_49245_(this.f_46012_, blockpos2));
            }
         }
      }

   }

   private static void m_46067_(ObjectArrayList<Pair<ItemStack, BlockPos>> p_46068_, ItemStack p_46069_, BlockPos p_46070_) {
      int i = p_46068_.size();

      for(int j = 0; j < i; ++j) {
         Pair<ItemStack, BlockPos> pair = p_46068_.get(j);
         ItemStack itemstack = pair.getFirst();
         if (ItemEntity.m_32026_(itemstack, p_46069_)) {
            ItemStack itemstack1 = ItemEntity.m_32029_(itemstack, p_46069_, 16);
            p_46068_.set(j, Pair.of(itemstack1, pair.getSecond()));
            if (p_46069_.m_41619_()) {
               return;
            }
         }
      }

      p_46068_.add(Pair.of(p_46069_, p_46070_));
   }

   public DamageSource m_46077_() {
      return this.f_46018_;
   }

   public Map<Player, Vec3> m_46078_() {
      return this.f_46021_;
   }

   @Nullable
   public LivingEntity m_46079_() {
      if (this.f_46016_ == null) {
         return null;
      } else if (this.f_46016_ instanceof PrimedTnt) {
         return ((PrimedTnt)this.f_46016_).m_32099_();
      } else if (this.f_46016_ instanceof LivingEntity) {
         return (LivingEntity)this.f_46016_;
      } else {
         if (this.f_46016_ instanceof Projectile) {
            Entity entity = ((Projectile)this.f_46016_).m_37282_();
            if (entity instanceof LivingEntity) {
               return (LivingEntity)entity;
            }
         }

         return null;
      }
   }

   public void m_46080_() {
      this.f_46020_.clear();
   }

   public List<BlockPos> m_46081_() {
      return this.f_46020_;
   }

   public Vec3 getPosition() {
      return this.position;
   }

   @Nullable
   public Entity getExploder() {
      return this.f_46016_;
   }

   public static enum BlockInteraction {
      NONE,
      BREAK,
      DESTROY;
   }
}
