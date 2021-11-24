package net.minecraft.world.entity.vehicle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractMinecart extends Entity implements net.minecraftforge.common.extensions.IForgeAbstractMinecart {
   private static final EntityDataAccessor<Integer> f_38079_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_38080_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> f_38081_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> f_38082_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_38083_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_38084_ = SynchedEntityData.m_135353_(AbstractMinecart.class, EntityDataSerializers.f_135035_);
   private static final ImmutableMap<Pose, ImmutableList<Integer>> f_38067_ = ImmutableMap.of(Pose.STANDING, ImmutableList.of(0, 1, -1), Pose.CROUCHING, ImmutableList.of(0, 1, -1), Pose.SWIMMING, ImmutableList.of(0, 1));
   protected static final float f_150249_ = 0.95F;
   private boolean f_38068_;
   private static final Map<RailShape, Pair<Vec3i, Vec3i>> f_38069_ = Util.m_137469_(Maps.newEnumMap(RailShape.class), (p_38135_) -> {
      Vec3i vec3i = Direction.WEST.m_122436_();
      Vec3i vec3i1 = Direction.EAST.m_122436_();
      Vec3i vec3i2 = Direction.NORTH.m_122436_();
      Vec3i vec3i3 = Direction.SOUTH.m_122436_();
      Vec3i vec3i4 = vec3i.m_7495_();
      Vec3i vec3i5 = vec3i1.m_7495_();
      Vec3i vec3i6 = vec3i2.m_7495_();
      Vec3i vec3i7 = vec3i3.m_7495_();
      p_38135_.put(RailShape.NORTH_SOUTH, Pair.of(vec3i2, vec3i3));
      p_38135_.put(RailShape.EAST_WEST, Pair.of(vec3i, vec3i1));
      p_38135_.put(RailShape.ASCENDING_EAST, Pair.of(vec3i4, vec3i1));
      p_38135_.put(RailShape.ASCENDING_WEST, Pair.of(vec3i, vec3i5));
      p_38135_.put(RailShape.ASCENDING_NORTH, Pair.of(vec3i2, vec3i7));
      p_38135_.put(RailShape.ASCENDING_SOUTH, Pair.of(vec3i6, vec3i3));
      p_38135_.put(RailShape.SOUTH_EAST, Pair.of(vec3i3, vec3i1));
      p_38135_.put(RailShape.SOUTH_WEST, Pair.of(vec3i3, vec3i));
      p_38135_.put(RailShape.NORTH_WEST, Pair.of(vec3i2, vec3i));
      p_38135_.put(RailShape.NORTH_EAST, Pair.of(vec3i2, vec3i1));
   });
   private static net.minecraftforge.common.IMinecartCollisionHandler COLLISIONS = null;
   private int f_38070_;
   private double f_38071_;
   private double f_38072_;
   private double f_38073_;
   private double f_38074_;
   private double f_38075_;
   private double f_38076_;
   private double f_38077_;
   private double f_38078_;
   private boolean canBePushed = true;

   protected AbstractMinecart(EntityType<?> p_38087_, Level p_38088_) {
      super(p_38087_, p_38088_);
      this.f_19850_ = true;
   }

   protected AbstractMinecart(EntityType<?> p_38090_, Level p_38091_, double p_38092_, double p_38093_, double p_38094_) {
      this(p_38090_, p_38091_);
      this.m_6034_(p_38092_, p_38093_, p_38094_);
      this.f_19854_ = p_38092_;
      this.f_19855_ = p_38093_;
      this.f_19856_ = p_38094_;
   }
   
   public net.minecraftforge.common.IMinecartCollisionHandler getCollisionHandler() {
      return COLLISIONS;
   }

   public static void registerCollisionHandler(@Nullable net.minecraftforge.common.IMinecartCollisionHandler handler) {
      COLLISIONS = handler;
   }

   public static AbstractMinecart m_38119_(Level p_38120_, double p_38121_, double p_38122_, double p_38123_, AbstractMinecart.Type p_38124_) {
      if (p_38124_ == AbstractMinecart.Type.CHEST) {
         return new MinecartChest(p_38120_, p_38121_, p_38122_, p_38123_);
      } else if (p_38124_ == AbstractMinecart.Type.FURNACE) {
         return new MinecartFurnace(p_38120_, p_38121_, p_38122_, p_38123_);
      } else if (p_38124_ == AbstractMinecart.Type.TNT) {
         return new MinecartTNT(p_38120_, p_38121_, p_38122_, p_38123_);
      } else if (p_38124_ == AbstractMinecart.Type.SPAWNER) {
         return new MinecartSpawner(p_38120_, p_38121_, p_38122_, p_38123_);
      } else if (p_38124_ == AbstractMinecart.Type.HOPPER) {
         return new MinecartHopper(p_38120_, p_38121_, p_38122_, p_38123_);
      } else {
         return (AbstractMinecart)(p_38124_ == AbstractMinecart.Type.COMMAND_BLOCK ? new MinecartCommandBlock(p_38120_, p_38121_, p_38122_, p_38123_) : new Minecart(p_38120_, p_38121_, p_38122_, p_38123_));
      }
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_38079_, 0);
      this.f_19804_.m_135372_(f_38080_, 1);
      this.f_19804_.m_135372_(f_38081_, 0.0F);
      this.f_19804_.m_135372_(f_38082_, Block.m_49956_(Blocks.f_50016_.m_49966_()));
      this.f_19804_.m_135372_(f_38083_, 6);
      this.f_19804_.m_135372_(f_38084_, false);
   }

   public boolean m_7337_(Entity p_38168_) {
      return Boat.m_38323_(this, p_38168_);
   }

   public boolean m_6094_() {
      return canBePushed;
   }

   protected Vec3 m_7643_(Direction.Axis p_38132_, BlockUtil.FoundRectangle p_38133_) {
      return LivingEntity.m_21289_(super.m_7643_(p_38132_, p_38133_));
   }

   public double m_6048_() {
      return 0.0D;
   }

   public Vec3 m_7688_(LivingEntity p_38145_) {
      Direction direction = this.m_6374_();
      if (direction.m_122434_() == Direction.Axis.Y) {
         return super.m_7688_(p_38145_);
      } else {
         int[][] aint = DismountHelper.m_38467_(direction);
         BlockPos blockpos = this.m_142538_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         ImmutableList<Pose> immutablelist = p_38145_.m_7431_();

         for(Pose pose : immutablelist) {
            EntityDimensions entitydimensions = p_38145_.m_6972_(pose);
            float f = Math.min(entitydimensions.f_20377_, 1.0F) / 2.0F;

            for(int i : f_38067_.get(pose)) {
               for(int[] aint1 : aint) {
                  blockpos$mutableblockpos.m_122178_(blockpos.m_123341_() + aint1[0], blockpos.m_123342_() + i, blockpos.m_123343_() + aint1[1]);
                  double d0 = this.f_19853_.m_45564_(DismountHelper.m_38446_(this.f_19853_, blockpos$mutableblockpos), () -> {
                     return DismountHelper.m_38446_(this.f_19853_, blockpos$mutableblockpos.m_7495_());
                  });
                  if (DismountHelper.m_38439_(d0)) {
                     AABB aabb = new AABB((double)(-f), 0.0D, (double)(-f), (double)f, (double)entitydimensions.f_20378_, (double)f);
                     Vec3 vec3 = Vec3.m_82514_(blockpos$mutableblockpos, d0);
                     if (DismountHelper.m_38456_(this.f_19853_, p_38145_, aabb.m_82383_(vec3))) {
                        p_38145_.m_20124_(pose);
                        return vec3;
                     }
                  }
               }
            }
         }

         double d1 = this.m_142469_().f_82292_;
         blockpos$mutableblockpos.m_122169_((double)blockpos.m_123341_(), d1, (double)blockpos.m_123343_());

         for(Pose pose1 : immutablelist) {
            double d2 = (double)p_38145_.m_6972_(pose1).f_20378_;
            int j = Mth.m_14165_(d1 - (double)blockpos$mutableblockpos.m_123342_() + d2);
            double d3 = DismountHelper.m_38463_(blockpos$mutableblockpos, j, (p_38149_) -> {
               return this.f_19853_.m_8055_(p_38149_).m_60812_(this.f_19853_, p_38149_);
            });
            if (d1 + d2 <= d3) {
               p_38145_.m_20124_(pose1);
               break;
            }
         }

         return super.m_7688_(p_38145_);
      }
   }

   public boolean m_6469_(DamageSource p_38117_, float p_38118_) {
      if (!this.f_19853_.f_46443_ && !this.m_146910_()) {
         if (this.m_6673_(p_38117_)) {
            return false;
         } else {
            this.m_38160_(-this.m_38177_());
            this.m_38154_(10);
            this.m_5834_();
            this.m_38109_(this.m_38169_() + p_38118_ * 10.0F);
            this.m_146852_(GameEvent.f_157808_, p_38117_.m_7639_());
            boolean flag = p_38117_.m_7639_() instanceof Player && ((Player)p_38117_.m_7639_()).m_150110_().f_35937_;
            if (flag || this.m_38169_() > 40.0F) {
               this.m_20153_();
               if (flag && !this.m_8077_()) {
                  this.m_146870_();
               } else {
                  this.m_7617_(p_38117_);
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   protected float m_6041_() {
      BlockState blockstate = this.f_19853_.m_8055_(this.m_142538_());
      return blockstate.m_60620_(BlockTags.f_13034_) ? 1.0F : super.m_6041_();
   }

   public void m_7617_(DamageSource p_38115_) {
      this.m_142687_(Entity.RemovalReason.KILLED);
      if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
         ItemStack itemstack = new ItemStack(Items.f_42449_);
         if (this.m_8077_()) {
            itemstack.m_41714_(this.m_7770_());
         }

         this.m_19983_(itemstack);
      }

   }

   public void m_6053_() {
      this.m_38160_(-this.m_38177_());
      this.m_38154_(10);
      this.m_38109_(this.m_38169_() + this.m_38169_() * 10.0F);
   }

   public boolean m_6087_() {
      return !this.m_146910_();
   }

   private static Pair<Vec3i, Vec3i> m_38125_(RailShape p_38126_) {
      return f_38069_.get(p_38126_);
   }

   public Direction m_6374_() {
      return this.f_38068_ ? this.m_6350_().m_122424_().m_122427_() : this.m_6350_().m_122427_();
   }

   public void m_8119_() {
      if (this.m_38176_() > 0) {
         this.m_38154_(this.m_38176_() - 1);
      }

      if (this.m_38169_() > 0.0F) {
         this.m_38109_(this.m_38169_() - 1.0F);
      }

      this.m_146871_();
      this.m_20157_();
      if (this.f_19853_.f_46443_) {
         if (this.f_38070_ > 0) {
            double d5 = this.m_20185_() + (this.f_38071_ - this.m_20185_()) / (double)this.f_38070_;
            double d6 = this.m_20186_() + (this.f_38072_ - this.m_20186_()) / (double)this.f_38070_;
            double d7 = this.m_20189_() + (this.f_38073_ - this.m_20189_()) / (double)this.f_38070_;
            double d2 = Mth.m_14175_(this.f_38074_ - (double)this.m_146908_());
            this.m_146922_(this.m_146908_() + (float)d2 / (float)this.f_38070_);
            this.m_146926_(this.m_146909_() + (float)(this.f_38075_ - (double)this.m_146909_()) / (float)this.f_38070_);
            --this.f_38070_;
            this.m_6034_(d5, d6, d7);
            this.m_19915_(this.m_146908_(), this.m_146909_());
         } else {
            this.m_20090_();
            this.m_19915_(this.m_146908_(), this.m_146909_());
         }

      } else {
         if (!this.m_20068_()) {
            double d0 = this.m_20069_() ? -0.005D : -0.04D;
            this.m_20256_(this.m_20184_().m_82520_(0.0D, d0, 0.0D));
         }

         int k = Mth.m_14107_(this.m_20185_());
         int i = Mth.m_14107_(this.m_20186_());
         int j = Mth.m_14107_(this.m_20189_());
         if (this.f_19853_.m_8055_(new BlockPos(k, i - 1, j)).m_60620_(BlockTags.f_13034_)) {
            --i;
         }

         BlockPos blockpos = new BlockPos(k, i, j);
         BlockState blockstate = this.f_19853_.m_8055_(blockpos);
         if (canUseRail() && BaseRailBlock.m_49416_(blockstate)) {
            this.m_6401_(blockpos, blockstate);
            if (blockstate.m_60734_() instanceof PoweredRailBlock && ((PoweredRailBlock) blockstate.m_60734_()).isActivatorRail()) {
               this.m_6025_(k, i, j, blockstate.m_61143_(PoweredRailBlock.f_55215_));
            }
         } else {
            this.m_38163_();
         }

         this.m_20101_();
         this.m_146926_(0.0F);
         double d1 = this.f_19854_ - this.m_20185_();
         double d3 = this.f_19856_ - this.m_20189_();
         if (d1 * d1 + d3 * d3 > 0.001D) {
            this.m_146922_((float)(Mth.m_14136_(d3, d1) * 180.0D / Math.PI));
            if (this.f_38068_) {
               this.m_146922_(this.m_146908_() + 180.0F);
            }
         }

         double d4 = (double)Mth.m_14177_(this.m_146908_() - this.f_19859_);
         if (d4 < -170.0D || d4 >= 170.0D) {
            this.m_146922_(this.m_146908_() + 180.0F);
            this.f_38068_ = !this.f_38068_;
         }

         this.m_19915_(this.m_146908_(), this.m_146909_());
         AABB box;
         if (getCollisionHandler() != null) box = getCollisionHandler().getMinecartCollisionBox(this);
         else                               box = this.m_142469_().m_82377_(0.2F, 0.0D, 0.2F);
         if (canBeRidden() && this.m_20184_().m_165925_() > 0.01D) {
            List<Entity> list = this.f_19853_.m_6249_(this, box, EntitySelector.m_20421_(this));
            if (!list.isEmpty()) {
               for(int l = 0; l < list.size(); ++l) {
                  Entity entity1 = list.get(l);
                  if (!(entity1 instanceof Player) && !(entity1 instanceof IronGolem) && !(entity1 instanceof AbstractMinecart) && !this.m_20160_() && !entity1.m_20159_()) {
                     entity1.m_20329_(this);
                  } else {
                     entity1.m_7334_(this);
                  }
               }
            }
         } else {
            for(Entity entity : this.f_19853_.m_45933_(this, box)) {
               if (!this.m_20363_(entity) && entity.m_6094_() && entity instanceof AbstractMinecart) {
                  entity.m_7334_(this);
               }
            }
         }

         this.m_20073_();
         if (this.m_20077_()) {
            this.m_20093_();
            this.f_19789_ *= 0.5F;
         }

         this.f_19803_ = false;
      }
   }

   protected double m_7097_() {
      return (this.m_20069_() ? 4.0D : 8.0D) / 20.0D;
   }

   public void m_6025_(int p_38111_, int p_38112_, int p_38113_, boolean p_38114_) {
   }

   protected void m_38163_() {
      double d0 = f_19861_ ? this.m_7097_() : getMaxSpeedAirLateral();
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(Mth.m_14008_(vec3.f_82479_, -d0, d0), vec3.f_82480_, Mth.m_14008_(vec3.f_82481_, -d0, d0));
      if (this.f_19861_) {
         this.m_20256_(this.m_20184_().m_82490_(0.5D));
      }

      if (getMaxSpeedAirVertical() > 0 && m_20184_().f_82480_ > getMaxSpeedAirVertical()) {
          if(Math.abs(m_20184_().f_82479_) < 0.3f && Math.abs(m_20184_().f_82481_) < 0.3f)
              m_20256_(new Vec3(m_20184_().f_82479_, 0.15f, m_20184_().f_82481_));
          else
              m_20256_(new Vec3(m_20184_().f_82479_, getMaxSpeedAirVertical(), m_20184_().f_82481_));
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      if (!this.f_19861_) {
         this.m_20256_(this.m_20184_().m_82490_(getDragAir()));
      }

   }

   protected void m_6401_(BlockPos p_38156_, BlockState p_38157_) {
      this.f_19789_ = 0.0F;
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      Vec3 vec3 = this.m_38179_(d0, d1, d2);
      d1 = (double)p_38156_.m_123342_();
      boolean flag = false;
      boolean flag1 = false;
      BaseRailBlock baserailblock = (BaseRailBlock) p_38157_.m_60734_();
      if (baserailblock instanceof PoweredRailBlock && !((PoweredRailBlock) baserailblock).isActivatorRail()) {
         flag = p_38157_.m_61143_(PoweredRailBlock.f_55215_);
         flag1 = !flag;
      }

      double d3 = 0.0078125D;
      if (this.m_20069_()) {
         d3 *= 0.2D;
      }

      Vec3 vec31 = this.m_20184_();
      RailShape railshape = ((BaseRailBlock)p_38157_.m_60734_()).getRailDirection(p_38157_, this.f_19853_, p_38156_, this);
      switch(railshape) {
      case ASCENDING_EAST:
         this.m_20256_(vec31.m_82520_(-1 * getSlopeAdjustment(), 0.0D, 0.0D));
         ++d1;
         break;
      case ASCENDING_WEST:
         this.m_20256_(vec31.m_82520_(getSlopeAdjustment(), 0.0D, 0.0D));
         ++d1;
         break;
      case ASCENDING_NORTH:
         this.m_20256_(vec31.m_82520_(0.0D, 0.0D, getSlopeAdjustment()));
         ++d1;
         break;
      case ASCENDING_SOUTH:
         this.m_20256_(vec31.m_82520_(0.0D, 0.0D, -1 * getSlopeAdjustment()));
         ++d1;
      }

      vec31 = this.m_20184_();
      Pair<Vec3i, Vec3i> pair = m_38125_(railshape);
      Vec3i vec3i = pair.getFirst();
      Vec3i vec3i1 = pair.getSecond();
      double d4 = (double)(vec3i1.m_123341_() - vec3i.m_123341_());
      double d5 = (double)(vec3i1.m_123343_() - vec3i.m_123343_());
      double d6 = Math.sqrt(d4 * d4 + d5 * d5);
      double d7 = vec31.f_82479_ * d4 + vec31.f_82481_ * d5;
      if (d7 < 0.0D) {
         d4 = -d4;
         d5 = -d5;
      }

      double d8 = Math.min(2.0D, vec31.m_165924_());
      vec31 = new Vec3(d8 * d4 / d6, vec31.f_82480_, d8 * d5 / d6);
      this.m_20256_(vec31);
      Entity entity = this.m_146895_();
      if (entity instanceof Player) {
         Vec3 vec32 = entity.m_20184_();
         double d9 = vec32.m_165925_();
         double d11 = this.m_20184_().m_165925_();
         if (d9 > 1.0E-4D && d11 < 0.01D) {
            this.m_20256_(this.m_20184_().m_82520_(vec32.f_82479_ * 0.1D, 0.0D, vec32.f_82481_ * 0.1D));
            flag1 = false;
         }
      }

      if (flag1 && shouldDoRailFunctions()) {
         double d22 = this.m_20184_().m_165924_();
         if (d22 < 0.03D) {
            this.m_20256_(Vec3.f_82478_);
         } else {
            this.m_20256_(this.m_20184_().m_82542_(0.5D, 0.0D, 0.5D));
         }
      }

      double d23 = (double)p_38156_.m_123341_() + 0.5D + (double)vec3i.m_123341_() * 0.5D;
      double d10 = (double)p_38156_.m_123343_() + 0.5D + (double)vec3i.m_123343_() * 0.5D;
      double d12 = (double)p_38156_.m_123341_() + 0.5D + (double)vec3i1.m_123341_() * 0.5D;
      double d13 = (double)p_38156_.m_123343_() + 0.5D + (double)vec3i1.m_123343_() * 0.5D;
      d4 = d12 - d23;
      d5 = d13 - d10;
      double d14;
      if (d4 == 0.0D) {
         d14 = d2 - (double)p_38156_.m_123343_();
      } else if (d5 == 0.0D) {
         d14 = d0 - (double)p_38156_.m_123341_();
      } else {
         double d15 = d0 - d23;
         double d16 = d2 - d10;
         d14 = (d15 * d4 + d16 * d5) * 2.0D;
      }

      d0 = d23 + d4 * d14;
      d2 = d10 + d5 * d14;
      this.m_6034_(d0, d1, d2);
      this.moveMinecartOnRail(p_38156_);
      if (vec3i.m_123342_() != 0 && Mth.m_14107_(this.m_20185_()) - p_38156_.m_123341_() == vec3i.m_123341_() && Mth.m_14107_(this.m_20189_()) - p_38156_.m_123343_() == vec3i.m_123343_()) {
         this.m_6034_(this.m_20185_(), this.m_20186_() + (double)vec3i.m_123342_(), this.m_20189_());
      } else if (vec3i1.m_123342_() != 0 && Mth.m_14107_(this.m_20185_()) - p_38156_.m_123341_() == vec3i1.m_123341_() && Mth.m_14107_(this.m_20189_()) - p_38156_.m_123343_() == vec3i1.m_123343_()) {
         this.m_6034_(this.m_20185_(), this.m_20186_() + (double)vec3i1.m_123342_(), this.m_20189_());
      }

      this.m_7114_();
      Vec3 vec33 = this.m_38179_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      if (vec33 != null && vec3 != null) {
         double d17 = (vec3.f_82480_ - vec33.f_82480_) * 0.05D;
         Vec3 vec34 = this.m_20184_();
         double d18 = vec34.m_165924_();
         if (d18 > 0.0D) {
            this.m_20256_(vec34.m_82542_((d18 + d17) / d18, 1.0D, (d18 + d17) / d18));
         }

         this.m_6034_(this.m_20185_(), vec33.f_82480_, this.m_20189_());
      }

      int j = Mth.m_14107_(this.m_20185_());
      int i = Mth.m_14107_(this.m_20189_());
      if (j != p_38156_.m_123341_() || i != p_38156_.m_123343_()) {
         Vec3 vec35 = this.m_20184_();
         double d26 = vec35.m_165924_();
         this.m_20334_(d26 * (double)(j - p_38156_.m_123341_()), vec35.f_82480_, d26 * (double)(i - p_38156_.m_123343_()));
      }

      if (shouldDoRailFunctions())
          baserailblock.onMinecartPass(p_38157_, f_19853_, p_38156_, this);

      if (flag && shouldDoRailFunctions()) {
         Vec3 vec36 = this.m_20184_();
         double d27 = vec36.m_165924_();
         if (d27 > 0.01D) {
            double d19 = 0.06D;
            this.m_20256_(vec36.m_82520_(vec36.f_82479_ / d27 * 0.06D, 0.0D, vec36.f_82481_ / d27 * 0.06D));
         } else {
            Vec3 vec37 = this.m_20184_();
            double d20 = vec37.f_82479_;
            double d21 = vec37.f_82481_;
            if (railshape == RailShape.EAST_WEST) {
               if (this.m_38129_(p_38156_.m_142125_())) {
                  d20 = 0.02D;
               } else if (this.m_38129_(p_38156_.m_142126_())) {
                  d20 = -0.02D;
               }
            } else {
               if (railshape != RailShape.NORTH_SOUTH) {
                  return;
               }

               if (this.m_38129_(p_38156_.m_142127_())) {
                  d21 = 0.02D;
               } else if (this.m_38129_(p_38156_.m_142128_())) {
                  d21 = -0.02D;
               }
            }

            this.m_20334_(d20, vec37.f_82480_, d21);
         }
      }

   }

   private boolean m_38129_(BlockPos p_38130_) {
      return this.f_19853_.m_8055_(p_38130_).m_60796_(this.f_19853_, p_38130_);
   }

   protected void m_7114_() {
      double d0 = this.m_20160_() ? 0.997D : 0.96D;
      Vec3 vec3 = this.m_20184_();
      vec3 = vec3.m_82542_(d0, 0.0D, d0);
      if (this.m_20069_()) {
         vec3 = vec3.m_82490_((double)0.95F);
      }

      this.m_20256_(vec3);
   }

   @Nullable
   public Vec3 m_38096_(double p_38097_, double p_38098_, double p_38099_, double p_38100_) {
      int i = Mth.m_14107_(p_38097_);
      int j = Mth.m_14107_(p_38098_);
      int k = Mth.m_14107_(p_38099_);
      if (this.f_19853_.m_8055_(new BlockPos(i, j - 1, k)).m_60620_(BlockTags.f_13034_)) {
         --j;
      }

      BlockState blockstate = this.f_19853_.m_8055_(new BlockPos(i, j, k));
      if (BaseRailBlock.m_49416_(blockstate)) {
         RailShape railshape = ((BaseRailBlock)blockstate.m_60734_()).getRailDirection(blockstate, this.f_19853_, new BlockPos(i, j, k), this);
         p_38098_ = (double)j;
         if (railshape.m_61745_()) {
            p_38098_ = (double)(j + 1);
         }

         Pair<Vec3i, Vec3i> pair = m_38125_(railshape);
         Vec3i vec3i = pair.getFirst();
         Vec3i vec3i1 = pair.getSecond();
         double d0 = (double)(vec3i1.m_123341_() - vec3i.m_123341_());
         double d1 = (double)(vec3i1.m_123343_() - vec3i.m_123343_());
         double d2 = Math.sqrt(d0 * d0 + d1 * d1);
         d0 = d0 / d2;
         d1 = d1 / d2;
         p_38097_ = p_38097_ + d0 * p_38100_;
         p_38099_ = p_38099_ + d1 * p_38100_;
         if (vec3i.m_123342_() != 0 && Mth.m_14107_(p_38097_) - i == vec3i.m_123341_() && Mth.m_14107_(p_38099_) - k == vec3i.m_123343_()) {
            p_38098_ += (double)vec3i.m_123342_();
         } else if (vec3i1.m_123342_() != 0 && Mth.m_14107_(p_38097_) - i == vec3i1.m_123341_() && Mth.m_14107_(p_38099_) - k == vec3i1.m_123343_()) {
            p_38098_ += (double)vec3i1.m_123342_();
         }

         return this.m_38179_(p_38097_, p_38098_, p_38099_);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3 m_38179_(double p_38180_, double p_38181_, double p_38182_) {
      int i = Mth.m_14107_(p_38180_);
      int j = Mth.m_14107_(p_38181_);
      int k = Mth.m_14107_(p_38182_);
      if (this.f_19853_.m_8055_(new BlockPos(i, j - 1, k)).m_60620_(BlockTags.f_13034_)) {
         --j;
      }

      BlockState blockstate = this.f_19853_.m_8055_(new BlockPos(i, j, k));
      if (BaseRailBlock.m_49416_(blockstate)) {
         RailShape railshape = ((BaseRailBlock)blockstate.m_60734_()).getRailDirection(blockstate, this.f_19853_, new BlockPos(i, j, k), this);
         Pair<Vec3i, Vec3i> pair = m_38125_(railshape);
         Vec3i vec3i = pair.getFirst();
         Vec3i vec3i1 = pair.getSecond();
         double d0 = (double)i + 0.5D + (double)vec3i.m_123341_() * 0.5D;
         double d1 = (double)j + 0.0625D + (double)vec3i.m_123342_() * 0.5D;
         double d2 = (double)k + 0.5D + (double)vec3i.m_123343_() * 0.5D;
         double d3 = (double)i + 0.5D + (double)vec3i1.m_123341_() * 0.5D;
         double d4 = (double)j + 0.0625D + (double)vec3i1.m_123342_() * 0.5D;
         double d5 = (double)k + 0.5D + (double)vec3i1.m_123343_() * 0.5D;
         double d6 = d3 - d0;
         double d7 = (d4 - d1) * 2.0D;
         double d8 = d5 - d2;
         double d9;
         if (d6 == 0.0D) {
            d9 = p_38182_ - (double)k;
         } else if (d8 == 0.0D) {
            d9 = p_38180_ - (double)i;
         } else {
            double d10 = p_38180_ - d0;
            double d11 = p_38182_ - d2;
            d9 = (d10 * d6 + d11 * d8) * 2.0D;
         }

         p_38180_ = d0 + d6 * d9;
         p_38181_ = d1 + d7 * d9;
         p_38182_ = d2 + d8 * d9;
         if (d7 < 0.0D) {
            ++p_38181_;
         } else if (d7 > 0.0D) {
            p_38181_ += 0.5D;
         }

         return new Vec3(p_38180_, p_38181_, p_38182_);
      } else {
         return null;
      }
   }

   public AABB m_6921_() {
      AABB aabb = this.m_142469_();
      return this.m_38184_() ? aabb.m_82400_((double)Math.abs(this.m_38183_()) / 16.0D) : aabb;
   }

   protected void m_7378_(CompoundTag p_38137_) {
      if (p_38137_.m_128471_("CustomDisplayTile")) {
         this.m_38146_(NbtUtils.m_129241_(p_38137_.m_128469_("DisplayState")));
         this.m_38174_(p_38137_.m_128451_("DisplayOffset"));
      }

   }

   protected void m_7380_(CompoundTag p_38151_) {
      if (this.m_38184_()) {
         p_38151_.m_128379_("CustomDisplayTile", true);
         p_38151_.m_128365_("DisplayState", NbtUtils.m_129202_(this.m_38178_()));
         p_38151_.m_128405_("DisplayOffset", this.m_38183_());
      }

   }

   public void m_7334_(Entity p_38165_) {
      if (getCollisionHandler() != null) {
         getCollisionHandler().onEntityCollision(this, p_38165_);
         return;
      }
      if (!this.f_19853_.f_46443_) {
         if (!p_38165_.f_19794_ && !this.f_19794_) {
            if (!this.m_20363_(p_38165_)) {
               double d0 = p_38165_.m_20185_() - this.m_20185_();
               double d1 = p_38165_.m_20189_() - this.m_20189_();
               double d2 = d0 * d0 + d1 * d1;
               if (d2 >= (double)1.0E-4F) {
                  d2 = Math.sqrt(d2);
                  d0 = d0 / d2;
                  d1 = d1 / d2;
                  double d3 = 1.0D / d2;
                  if (d3 > 1.0D) {
                     d3 = 1.0D;
                  }

                  d0 = d0 * d3;
                  d1 = d1 * d3;
                  d0 = d0 * (double)0.1F;
                  d1 = d1 * (double)0.1F;
                  d0 = d0 * 0.5D;
                  d1 = d1 * 0.5D;
                  if (p_38165_ instanceof AbstractMinecart) {
                     double d4 = p_38165_.m_20185_() - this.m_20185_();
                     double d5 = p_38165_.m_20189_() - this.m_20189_();
                     Vec3 vec3 = (new Vec3(d4, 0.0D, d5)).m_82541_();
                     Vec3 vec31 = (new Vec3((double)Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)), 0.0D, (double)Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)))).m_82541_();
                     double d6 = Math.abs(vec3.m_82526_(vec31));
                     if (d6 < (double)0.8F) {
                        return;
                     }

                     Vec3 vec32 = this.m_20184_();
                     Vec3 vec33 = p_38165_.m_20184_();
                     if (((AbstractMinecart)p_38165_).isPoweredCart() && !this.isPoweredCart()) {
                        this.m_20256_(vec32.m_82542_(0.2D, 1.0D, 0.2D));
                        this.m_5997_(vec33.f_82479_ - d0, 0.0D, vec33.f_82481_ - d1);
                        p_38165_.m_20256_(vec33.m_82542_(0.95D, 1.0D, 0.95D));
                     } else if (!((AbstractMinecart)p_38165_).isPoweredCart() && this.isPoweredCart()) {
                        p_38165_.m_20256_(vec33.m_82542_(0.2D, 1.0D, 0.2D));
                        p_38165_.m_5997_(vec32.f_82479_ + d0, 0.0D, vec32.f_82481_ + d1);
                        this.m_20256_(vec32.m_82542_(0.95D, 1.0D, 0.95D));
                     } else {
                        double d7 = (vec33.f_82479_ + vec32.f_82479_) / 2.0D;
                        double d8 = (vec33.f_82481_ + vec32.f_82481_) / 2.0D;
                        this.m_20256_(vec32.m_82542_(0.2D, 1.0D, 0.2D));
                        this.m_5997_(d7 - d0, 0.0D, d8 - d1);
                        p_38165_.m_20256_(vec33.m_82542_(0.2D, 1.0D, 0.2D));
                        p_38165_.m_5997_(d7 + d0, 0.0D, d8 + d1);
                     }
                  } else {
                     this.m_5997_(-d0, 0.0D, -d1);
                     p_38165_.m_5997_(d0 / 4.0D, 0.0D, d1 / 4.0D);
                  }
               }

            }
         }
      }
   }

   public void m_6453_(double p_38102_, double p_38103_, double p_38104_, float p_38105_, float p_38106_, int p_38107_, boolean p_38108_) {
      this.f_38071_ = p_38102_;
      this.f_38072_ = p_38103_;
      this.f_38073_ = p_38104_;
      this.f_38074_ = (double)p_38105_;
      this.f_38075_ = (double)p_38106_;
      this.f_38070_ = p_38107_ + 2;
      this.m_20334_(this.f_38076_, this.f_38077_, this.f_38078_);
   }

   public void m_6001_(double p_38171_, double p_38172_, double p_38173_) {
      this.f_38076_ = p_38171_;
      this.f_38077_ = p_38172_;
      this.f_38078_ = p_38173_;
      this.m_20334_(this.f_38076_, this.f_38077_, this.f_38078_);
   }

   public void m_38109_(float p_38110_) {
      this.f_19804_.m_135381_(f_38081_, p_38110_);
   }

   public float m_38169_() {
      return this.f_19804_.m_135370_(f_38081_);
   }

   public void m_38154_(int p_38155_) {
      this.f_19804_.m_135381_(f_38079_, p_38155_);
   }

   public int m_38176_() {
      return this.f_19804_.m_135370_(f_38079_);
   }

   public void m_38160_(int p_38161_) {
      this.f_19804_.m_135381_(f_38080_, p_38161_);
   }

   public int m_38177_() {
      return this.f_19804_.m_135370_(f_38080_);
   }

   public abstract AbstractMinecart.Type m_6064_();

   public BlockState m_38178_() {
      return !this.m_38184_() ? this.m_6390_() : Block.m_49803_(this.m_20088_().m_135370_(f_38082_));
   }

   public BlockState m_6390_() {
      return Blocks.f_50016_.m_49966_();
   }

   public int m_38183_() {
      return !this.m_38184_() ? this.m_7144_() : this.m_20088_().m_135370_(f_38083_);
   }

   public int m_7144_() {
      return 6;
   }

   public void m_38146_(BlockState p_38147_) {
      this.m_20088_().m_135381_(f_38082_, Block.m_49956_(p_38147_));
      this.m_38138_(true);
   }

   public void m_38174_(int p_38175_) {
      this.m_20088_().m_135381_(f_38083_, p_38175_);
      this.m_38138_(true);
   }

   public boolean m_38184_() {
      return this.m_20088_().m_135370_(f_38084_);
   }

   public void m_38138_(boolean p_38139_) {
      this.m_20088_().m_135381_(f_38084_, p_38139_);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   // Forge Start
   private boolean canUseRail = true;
   @Override public boolean canUseRail() { return canUseRail; }
   @Override public void setCanUseRail(boolean value) { this.canUseRail = value; }
   private float currentSpeedCapOnRail = getMaxCartSpeedOnRail();
   @Override public float getCurrentCartSpeedCapOnRail() { return currentSpeedCapOnRail; }
   @Override public void setCurrentCartSpeedCapOnRail(float value) { currentSpeedCapOnRail = Math.min(value, getMaxCartSpeedOnRail()); }
   private float maxSpeedAirLateral = DEFAULT_MAX_SPEED_AIR_LATERAL;
   @Override public float getMaxSpeedAirLateral() { return maxSpeedAirLateral; }
   @Override public void setMaxSpeedAirLateral(float value) { maxSpeedAirLateral = value; }
   private float maxSpeedAirVertical = DEFAULT_MAX_SPEED_AIR_VERTICAL;
   @Override public float getMaxSpeedAirVertical() { return maxSpeedAirVertical; }
   @Override public void setMaxSpeedAirVertical(float value) { maxSpeedAirVertical = value; }
   private double dragAir = DEFAULT_AIR_DRAG;
   @Override public double getDragAir() { return dragAir; }
   @Override public void setDragAir(double value) { dragAir = value; }
   @Override
   public double getMaxSpeedWithRail() { //Non-default because getMaximumSpeed is protected
      if (!canUseRail()) return m_7097_();
      BlockPos pos = this.getCurrentRailPosition();
      BlockState state = this.f_19853_.m_8055_(pos);
      if (!state.m_60620_(BlockTags.f_13034_)) return m_7097_();

      float railMaxSpeed = ((BaseRailBlock)state.m_60734_()).getRailMaxSpeed(state, this.f_19853_, pos, this);
      return Math.min(railMaxSpeed, getCurrentCartSpeedCapOnRail());
   }
   @Override
   public void moveMinecartOnRail(BlockPos pos) { //Non-default because getMaximumSpeed is protected
      AbstractMinecart mc = this;
      double d24 = mc.m_20160_() ? 0.75D : 1.0D;
      double d25 = mc.getMaxSpeedWithRail();
      Vec3 vec3d1 = mc.m_20184_();
      mc.m_6478_(MoverType.SELF, new Vec3(Mth.m_14008_(d24 * vec3d1.f_82479_, -d25, d25), 0.0D, Mth.m_14008_(d24 * vec3d1.f_82481_, -d25, d25)));
   }
   // Forge end

   public ItemStack m_142340_() {
      Item item;
      switch(this.m_6064_()) {
      case FURNACE:
         item = Items.f_42520_;
         break;
      case CHEST:
         item = Items.f_42519_;
         break;
      case TNT:
         item = Items.f_42693_;
         break;
      case HOPPER:
         item = Items.f_42694_;
         break;
      case COMMAND_BLOCK:
         item = Items.f_42657_;
         break;
      default:
         item = Items.f_42449_;
      }
      // TODO, should this be above?
      if (item == null) return getCartItem();
      return new ItemStack(item);
   }

   public static enum Type {
      RIDEABLE,
      CHEST,
      FURNACE,
      TNT,
      SPAWNER,
      HOPPER,
      COMMAND_BLOCK;
   }
}
