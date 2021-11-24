package net.minecraft.world.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FishingHook extends Projectile {
   private final Random f_37098_ = new Random();
   private boolean f_37099_;
   private int f_37100_;
   private static final int f_150136_ = 10;
   private static final EntityDataAccessor<Integer> f_37101_ = SynchedEntityData.m_135353_(FishingHook.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_37102_ = SynchedEntityData.m_135353_(FishingHook.class, EntityDataSerializers.f_135035_);
   private int f_37103_;
   private int f_37089_;
   private int f_37090_;
   private int f_37091_;
   private float f_37092_;
   private boolean f_37093_ = true;
   @Nullable
   private Entity f_37094_;
   private FishingHook.FishHookState f_37095_ = FishingHook.FishHookState.FLYING;
   private final int f_37096_;
   private final int f_37097_;

   private FishingHook(EntityType<? extends FishingHook> p_150141_, Level p_150142_, int p_150143_, int p_150144_) {
      super(p_150141_, p_150142_);
      this.f_19811_ = true;
      this.f_37096_ = Math.max(0, p_150143_);
      this.f_37097_ = Math.max(0, p_150144_);
   }

   public FishingHook(EntityType<? extends FishingHook> p_150138_, Level p_150139_) {
      this(p_150138_, p_150139_, 0, 0);
   }

   public FishingHook(Player p_37106_, Level p_37107_, int p_37108_, int p_37109_) {
      this(EntityType.f_20533_, p_37107_, p_37108_, p_37109_);
      this.m_5602_(p_37106_);
      float f = p_37106_.m_146909_();
      float f1 = p_37106_.m_146908_();
      float f2 = Mth.m_14089_(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = Mth.m_14031_(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -Mth.m_14089_(-f * ((float)Math.PI / 180F));
      float f5 = Mth.m_14031_(-f * ((float)Math.PI / 180F));
      double d0 = p_37106_.m_20185_() - (double)f3 * 0.3D;
      double d1 = p_37106_.m_20188_();
      double d2 = p_37106_.m_20189_() - (double)f2 * 0.3D;
      this.m_7678_(d0, d1, d2, f1, f);
      Vec3 vec3 = new Vec3((double)(-f3), (double)Mth.m_14036_(-(f5 / f4), -5.0F, 5.0F), (double)(-f2));
      double d3 = vec3.m_82553_();
      vec3 = vec3.m_82542_(0.6D / d3 + 0.5D + this.f_19796_.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.f_19796_.nextGaussian() * 0.0045D, 0.6D / d3 + 0.5D + this.f_19796_.nextGaussian() * 0.0045D);
      this.m_20256_(vec3);
      this.m_146922_((float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * (double)(180F / (float)Math.PI)));
      this.m_146926_((float)(Mth.m_14136_(vec3.f_82480_, vec3.m_165924_()) * (double)(180F / (float)Math.PI)));
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(f_37101_, 0);
      this.m_20088_().m_135372_(f_37102_, false);
   }

   public void m_7350_(EntityDataAccessor<?> p_37153_) {
      if (f_37101_.equals(p_37153_)) {
         int i = this.m_20088_().m_135370_(f_37101_);
         this.f_37094_ = i > 0 ? this.f_19853_.m_6815_(i - 1) : null;
      }

      if (f_37102_.equals(p_37153_)) {
         this.f_37099_ = this.m_20088_().m_135370_(f_37102_);
         if (this.f_37099_) {
            this.m_20334_(this.m_20184_().f_82479_, (double)(-0.4F * Mth.m_14068_(this.f_37098_, 0.6F, 1.0F)), this.m_20184_().f_82481_);
         }
      }

      super.m_7350_(p_37153_);
   }

   public boolean m_6783_(double p_37125_) {
      double d0 = 64.0D;
      return p_37125_ < 4096.0D;
   }

   public void m_6453_(double p_37127_, double p_37128_, double p_37129_, float p_37130_, float p_37131_, int p_37132_, boolean p_37133_) {
   }

   public void m_8119_() {
      this.f_37098_.setSeed(this.m_142081_().getLeastSignificantBits() ^ this.f_19853_.m_46467_());
      super.m_8119_();
      Player player = this.m_37168_();
      if (player == null) {
         this.m_146870_();
      } else if (this.f_19853_.f_46443_ || !this.m_37136_(player)) {
         if (this.f_19861_) {
            ++this.f_37103_;
            if (this.f_37103_ >= 1200) {
               this.m_146870_();
               return;
            }
         } else {
            this.f_37103_ = 0;
         }

         float f = 0.0F;
         BlockPos blockpos = this.m_142538_();
         FluidState fluidstate = this.f_19853_.m_6425_(blockpos);
         if (fluidstate.m_76153_(FluidTags.f_13131_)) {
            f = fluidstate.m_76155_(this.f_19853_, blockpos);
         }

         boolean flag = f > 0.0F;
         if (this.f_37095_ == FishingHook.FishHookState.FLYING) {
            if (this.f_37094_ != null) {
               this.m_20256_(Vec3.f_82478_);
               this.f_37095_ = FishingHook.FishHookState.HOOKED_IN_ENTITY;
               return;
            }

            if (flag) {
               this.m_20256_(this.m_20184_().m_82542_(0.3D, 0.2D, 0.3D));
               this.f_37095_ = FishingHook.FishHookState.BOBBING;
               return;
            }

            this.m_37171_();
         } else {
            if (this.f_37095_ == FishingHook.FishHookState.HOOKED_IN_ENTITY) {
               if (this.f_37094_ != null) {
                  if (!this.f_37094_.m_146910_() && this.f_37094_.f_19853_.m_46472_() == this.f_19853_.m_46472_()) {
                     this.m_6034_(this.f_37094_.m_20185_(), this.f_37094_.m_20227_(0.8D), this.f_37094_.m_20189_());
                  } else {
                     this.m_150157_((Entity)null);
                     this.f_37095_ = FishingHook.FishHookState.FLYING;
                  }
               }

               return;
            }

            if (this.f_37095_ == FishingHook.FishHookState.BOBBING) {
               Vec3 vec3 = this.m_20184_();
               double d0 = this.m_20186_() + vec3.f_82480_ - (double)blockpos.m_123342_() - (double)f;
               if (Math.abs(d0) < 0.01D) {
                  d0 += Math.signum(d0) * 0.1D;
               }

               this.m_20334_(vec3.f_82479_ * 0.9D, vec3.f_82480_ - d0 * (double)this.f_19796_.nextFloat() * 0.2D, vec3.f_82481_ * 0.9D);
               if (this.f_37089_ <= 0 && this.f_37091_ <= 0) {
                  this.f_37093_ = true;
               } else {
                  this.f_37093_ = this.f_37093_ && this.f_37100_ < 10 && this.m_37158_(blockpos);
               }

               if (flag) {
                  this.f_37100_ = Math.max(0, this.f_37100_ - 1);
                  if (this.f_37099_) {
                     this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.1D * (double)this.f_37098_.nextFloat() * (double)this.f_37098_.nextFloat(), 0.0D));
                  }

                  if (!this.f_19853_.f_46443_) {
                     this.m_37145_(blockpos);
                  }
               } else {
                  this.f_37100_ = Math.min(10, this.f_37100_ + 1);
               }
            }
         }

         if (!fluidstate.m_76153_(FluidTags.f_13131_)) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.03D, 0.0D));
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_37283_();
         if (this.f_37095_ == FishingHook.FishHookState.FLYING && (this.f_19861_ || this.f_19862_)) {
            this.m_20256_(Vec3.f_82478_);
         }

         double d1 = 0.92D;
         this.m_20256_(this.m_20184_().m_82490_(0.92D));
         this.m_20090_();
      }
   }

   private boolean m_37136_(Player p_37137_) {
      ItemStack itemstack = p_37137_.m_21205_();
      ItemStack itemstack1 = p_37137_.m_21206_();
      boolean flag = itemstack.m_150930_(Items.f_42523_);
      boolean flag1 = itemstack1.m_150930_(Items.f_42523_);
      if (!p_37137_.m_146910_() && p_37137_.m_6084_() && (flag || flag1) && !(this.m_20280_(p_37137_) > 1024.0D)) {
         return false;
      } else {
         this.m_146870_();
         return true;
      }
   }

   private void m_37171_() {
      HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
      if (hitresult.m_6662_() == HitResult.Type.MISS || !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) this.m_6532_(hitresult);
   }

   protected boolean m_5603_(Entity p_37135_) {
      return super.m_5603_(p_37135_) || p_37135_.m_6084_() && p_37135_ instanceof ItemEntity;
   }

   protected void m_5790_(EntityHitResult p_37144_) {
      super.m_5790_(p_37144_);
      if (!this.f_19853_.f_46443_) {
         this.m_150157_(p_37144_.m_82443_());
      }

   }

   protected void m_8060_(BlockHitResult p_37142_) {
      super.m_8060_(p_37142_);
      this.m_20256_(this.m_20184_().m_82541_().m_82490_(p_37142_.m_82448_(this)));
   }

   private void m_150157_(@Nullable Entity p_150158_) {
      this.f_37094_ = p_150158_;
      this.m_20088_().m_135381_(f_37101_, p_150158_ == null ? 0 : p_150158_.m_142049_() + 1);
   }

   private void m_37145_(BlockPos p_37146_) {
      ServerLevel serverlevel = (ServerLevel)this.f_19853_;
      int i = 1;
      BlockPos blockpos = p_37146_.m_7494_();
      if (this.f_19796_.nextFloat() < 0.25F && this.f_19853_.m_46758_(blockpos)) {
         ++i;
      }

      if (this.f_19796_.nextFloat() < 0.5F && !this.f_19853_.m_45527_(blockpos)) {
         --i;
      }

      if (this.f_37089_ > 0) {
         --this.f_37089_;
         if (this.f_37089_ <= 0) {
            this.f_37090_ = 0;
            this.f_37091_ = 0;
            this.m_20088_().m_135381_(f_37102_, false);
         }
      } else if (this.f_37091_ > 0) {
         this.f_37091_ -= i;
         if (this.f_37091_ > 0) {
            this.f_37092_ = (float)((double)this.f_37092_ + this.f_19796_.nextGaussian() * 4.0D);
            float f = this.f_37092_ * ((float)Math.PI / 180F);
            float f1 = Mth.m_14031_(f);
            float f2 = Mth.m_14089_(f);
            double d0 = this.m_20185_() + (double)(f1 * (float)this.f_37091_ * 0.1F);
            double d1 = (double)((float)Mth.m_14107_(this.m_20186_()) + 1.0F);
            double d2 = this.m_20189_() + (double)(f2 * (float)this.f_37091_ * 0.1F);
            BlockState blockstate = serverlevel.m_8055_(new BlockPos(d0, d1 - 1.0D, d2));
            if (serverlevel.m_8055_(new BlockPos((int)d0, (int)d1 - 1, (int)d2)).m_60767_() == net.minecraft.world.level.material.Material.f_76305_) {
               if (this.f_19796_.nextFloat() < 0.15F) {
                  serverlevel.m_8767_(ParticleTypes.f_123795_, d0, d1 - (double)0.1F, d2, 1, (double)f1, 0.1D, (double)f2, 0.0D);
               }

               float f3 = f1 * 0.04F;
               float f4 = f2 * 0.04F;
               serverlevel.m_8767_(ParticleTypes.f_123816_, d0, d1, d2, 0, (double)f4, 0.01D, (double)(-f3), 1.0D);
               serverlevel.m_8767_(ParticleTypes.f_123816_, d0, d1, d2, 0, (double)(-f4), 0.01D, (double)f3, 1.0D);
            }
         } else {
            this.m_5496_(SoundEvents.f_11940_, 0.25F, 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.4F);
            double d3 = this.m_20186_() + 0.5D;
            serverlevel.m_8767_(ParticleTypes.f_123795_, this.m_20185_(), d3, this.m_20189_(), (int)(1.0F + this.m_20205_() * 20.0F), (double)this.m_20205_(), 0.0D, (double)this.m_20205_(), (double)0.2F);
            serverlevel.m_8767_(ParticleTypes.f_123816_, this.m_20185_(), d3, this.m_20189_(), (int)(1.0F + this.m_20205_() * 20.0F), (double)this.m_20205_(), 0.0D, (double)this.m_20205_(), (double)0.2F);
            this.f_37089_ = Mth.m_14072_(this.f_19796_, 20, 40);
            this.m_20088_().m_135381_(f_37102_, true);
         }
      } else if (this.f_37090_ > 0) {
         this.f_37090_ -= i;
         float f5 = 0.15F;
         if (this.f_37090_ < 20) {
            f5 = (float)((double)f5 + (double)(20 - this.f_37090_) * 0.05D);
         } else if (this.f_37090_ < 40) {
            f5 = (float)((double)f5 + (double)(40 - this.f_37090_) * 0.02D);
         } else if (this.f_37090_ < 60) {
            f5 = (float)((double)f5 + (double)(60 - this.f_37090_) * 0.01D);
         }

         if (this.f_19796_.nextFloat() < f5) {
            float f6 = Mth.m_14068_(this.f_19796_, 0.0F, 360.0F) * ((float)Math.PI / 180F);
            float f7 = Mth.m_14068_(this.f_19796_, 25.0F, 60.0F);
            double d4 = this.m_20185_() + (double)(Mth.m_14031_(f6) * f7 * 0.1F);
            double d5 = (double)((float)Mth.m_14107_(this.m_20186_()) + 1.0F);
            double d6 = this.m_20189_() + (double)(Mth.m_14089_(f6) * f7 * 0.1F);
            BlockState blockstate1 = serverlevel.m_8055_(new BlockPos(d4, d5 - 1.0D, d6));
            if (serverlevel.m_8055_(new BlockPos(d4, d5 - 1.0D, d6)).m_60767_() == net.minecraft.world.level.material.Material.f_76305_) {
               serverlevel.m_8767_(ParticleTypes.f_123769_, d4, d5, d6, 2 + this.f_19796_.nextInt(2), (double)0.1F, 0.0D, (double)0.1F, 0.0D);
            }
         }

         if (this.f_37090_ <= 0) {
            this.f_37092_ = Mth.m_14068_(this.f_19796_, 0.0F, 360.0F);
            this.f_37091_ = Mth.m_14072_(this.f_19796_, 20, 80);
         }
      } else {
         this.f_37090_ = Mth.m_14072_(this.f_19796_, 100, 600);
         this.f_37090_ -= this.f_37097_ * 20 * 5;
      }

   }

   private boolean m_37158_(BlockPos p_37159_) {
      FishingHook.OpenWaterType fishinghook$openwatertype = FishingHook.OpenWaterType.INVALID;

      for(int i = -1; i <= 2; ++i) {
         FishingHook.OpenWaterType fishinghook$openwatertype1 = this.m_37147_(p_37159_.m_142082_(-2, i, -2), p_37159_.m_142082_(2, i, 2));
         switch(fishinghook$openwatertype1) {
         case INVALID:
            return false;
         case ABOVE_WATER:
            if (fishinghook$openwatertype == FishingHook.OpenWaterType.INVALID) {
               return false;
            }
            break;
         case INSIDE_WATER:
            if (fishinghook$openwatertype == FishingHook.OpenWaterType.ABOVE_WATER) {
               return false;
            }
         }

         fishinghook$openwatertype = fishinghook$openwatertype1;
      }

      return true;
   }

   private FishingHook.OpenWaterType m_37147_(BlockPos p_37148_, BlockPos p_37149_) {
      return BlockPos.m_121990_(p_37148_, p_37149_).map(this::m_37163_).reduce((p_37139_, p_37140_) -> {
         return p_37139_ == p_37140_ ? p_37139_ : FishingHook.OpenWaterType.INVALID;
      }).orElse(FishingHook.OpenWaterType.INVALID);
   }

   private FishingHook.OpenWaterType m_37163_(BlockPos p_37164_) {
      BlockState blockstate = this.f_19853_.m_8055_(p_37164_);
      if (!blockstate.m_60795_() && !blockstate.m_60713_(Blocks.f_50196_)) {
         FluidState fluidstate = blockstate.m_60819_();
         return fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76170_() && blockstate.m_60812_(this.f_19853_, p_37164_).m_83281_() ? FishingHook.OpenWaterType.INSIDE_WATER : FishingHook.OpenWaterType.INVALID;
      } else {
         return FishingHook.OpenWaterType.ABOVE_WATER;
      }
   }

   public boolean m_37166_() {
      return this.f_37093_;
   }

   public void m_7380_(CompoundTag p_37161_) {
   }

   public void m_7378_(CompoundTag p_37151_) {
   }

   public int m_37156_(ItemStack p_37157_) {
      Player player = this.m_37168_();
      if (!this.f_19853_.f_46443_ && player != null && !this.m_37136_(player)) {
         int i = 0;
         net.minecraftforge.event.entity.player.ItemFishedEvent event = null;
         if (this.f_37094_ != null) {
            this.m_150155_(this.f_37094_);
            CriteriaTriggers.f_10553_.m_40416_((ServerPlayer)player, p_37157_, this, Collections.emptyList());
            this.f_19853_.m_7605_(this, (byte)31);
            i = this.f_37094_ instanceof ItemEntity ? 3 : 5;
         } else if (this.f_37089_ > 0) {
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.f_19853_)).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78972_(LootContextParams.f_81463_, p_37157_).m_78972_(LootContextParams.f_81455_, this).m_78977_(this.f_19796_).m_78963_((float)this.f_37096_ + player.m_36336_());
            lootcontext$builder.m_78972_(LootContextParams.f_81458_, this.m_37282_()).m_78972_(LootContextParams.f_81455_, this);
            LootTable loottable = this.f_19853_.m_142572_().m_129898_().m_79217_(BuiltInLootTables.f_78720_);
            List<ItemStack> list = loottable.m_79129_(lootcontext$builder.m_78975_(LootContextParamSets.f_81414_));
            event = new net.minecraftforge.event.entity.player.ItemFishedEvent(list, this.f_19861_ ? 2 : 1, this);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()) {
               this.m_146870_();
               return event.getRodDamage();
            }
            CriteriaTriggers.f_10553_.m_40416_((ServerPlayer)player, p_37157_, this, list);

            for(ItemStack itemstack : list) {
               ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), itemstack);
               double d0 = player.m_20185_() - this.m_20185_();
               double d1 = player.m_20186_() - this.m_20186_();
               double d2 = player.m_20189_() - this.m_20189_();
               double d3 = 0.1D;
               itementity.m_20334_(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
               this.f_19853_.m_7967_(itementity);
               player.f_19853_.m_7967_(new ExperienceOrb(player.f_19853_, player.m_20185_(), player.m_20186_() + 0.5D, player.m_20189_() + 0.5D, this.f_19796_.nextInt(6) + 1));
               if (itemstack.m_150922_(ItemTags.f_13156_)) {
                  player.m_36222_(Stats.f_12939_, 1);
               }
            }

            i = 1;
         }

         if (this.f_19861_) {
            i = 2;
         }

         this.m_146870_();
         return event == null ? i : event.getRodDamage();
      } else {
         return 0;
      }
   }

   public void m_7822_(byte p_37123_) {
      if (p_37123_ == 31 && this.f_19853_.f_46443_ && this.f_37094_ instanceof Player && ((Player)this.f_37094_).m_7578_()) {
         this.m_150155_(this.f_37094_);
      }

      super.m_7822_(p_37123_);
   }

   protected void m_150155_(Entity p_150156_) {
      Entity entity = this.m_37282_();
      if (entity != null) {
         Vec3 vec3 = (new Vec3(entity.m_20185_() - this.m_20185_(), entity.m_20186_() - this.m_20186_(), entity.m_20189_() - this.m_20189_())).m_82490_(0.1D);
         p_150156_.m_20256_(p_150156_.m_20184_().m_82549_(vec3));
      }
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   public void m_142687_(Entity.RemovalReason p_150146_) {
      this.m_150147_((FishingHook)null);
      super.m_142687_(p_150146_);
   }

   public void m_142036_() {
      this.m_150147_((FishingHook)null);
   }

   public void m_5602_(@Nullable Entity p_150154_) {
      super.m_5602_(p_150154_);
      this.m_150147_(this);
   }

   private void m_150147_(@Nullable FishingHook p_150148_) {
      Player player = this.m_37168_();
      if (player != null) {
         player.f_36083_ = p_150148_;
      }

   }

   @Nullable
   public Player m_37168_() {
      Entity entity = this.m_37282_();
      return entity instanceof Player ? (Player)entity : null;
   }

   @Nullable
   public Entity m_37170_() {
      return this.f_37094_;
   }

   public boolean m_6072_() {
      return false;
   }

   public Packet<?> m_5654_() {
      Entity entity = this.m_37282_();
      return new ClientboundAddEntityPacket(this, entity == null ? this.m_142049_() : entity.m_142049_());
   }

   public void m_141965_(ClientboundAddEntityPacket p_150150_) {
      super.m_141965_(p_150150_);
      if (this.m_37168_() == null) {
         int i = p_150150_.m_131509_();
         f_19849_.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.f_19853_.m_6815_(i), i);
         this.m_6074_();
      }

   }

   static enum FishHookState {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING;
   }

   static enum OpenWaterType {
      ABOVE_WATER,
      INSIDE_WATER,
      INVALID;
   }
}
