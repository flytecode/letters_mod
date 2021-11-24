package net.minecraft.world.entity.item;

import java.util.function.Predicate;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FallingBlockEntity extends Entity {
   private BlockState f_31946_ = Blocks.f_49992_.m_49966_();
   public int f_31942_;
   public boolean f_31943_ = true;
   private boolean f_31947_;
   private boolean f_31939_;
   private int f_31940_ = 40;
   private float f_149641_;
   public CompoundTag f_31944_;
   protected static final EntityDataAccessor<BlockPos> f_31945_ = SynchedEntityData.m_135353_(FallingBlockEntity.class, EntityDataSerializers.f_135038_);

   public FallingBlockEntity(EntityType<? extends FallingBlockEntity> p_31950_, Level p_31951_) {
      super(p_31950_, p_31951_);
   }

   public FallingBlockEntity(Level p_31953_, double p_31954_, double p_31955_, double p_31956_, BlockState p_31957_) {
      this(EntityType.f_20450_, p_31953_);
      this.f_31946_ = p_31957_;
      this.f_19850_ = true;
      this.m_6034_(p_31954_, p_31955_ + (double)((1.0F - this.m_20206_()) / 2.0F), p_31956_);
      this.m_20256_(Vec3.f_82478_);
      this.f_19854_ = p_31954_;
      this.f_19855_ = p_31955_;
      this.f_19856_ = p_31956_;
      this.m_31959_(this.m_142538_());
   }

   public boolean m_6097_() {
      return false;
   }

   public void m_31959_(BlockPos p_31960_) {
      this.f_19804_.m_135381_(f_31945_, p_31960_);
   }

   public BlockPos m_31978_() {
      return this.f_19804_.m_135370_(f_31945_);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_31945_, BlockPos.f_121853_);
   }

   public boolean m_6087_() {
      return !this.m_146910_();
   }

   public void m_8119_() {
      if (this.f_31946_.m_60795_()) {
         this.m_146870_();
      } else {
         Block block = this.f_31946_.m_60734_();
         if (this.f_31942_++ == 0) {
            BlockPos blockpos = this.m_142538_();
            if (this.f_19853_.m_8055_(blockpos).m_60713_(block)) {
               this.f_19853_.m_7471_(blockpos, false);
            } else if (!this.f_19853_.f_46443_) {
               this.m_146870_();
               return;
            }
         }

         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.04D, 0.0D));
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
         if (!this.f_19853_.f_46443_) {
            BlockPos blockpos1 = this.m_142538_();
            boolean flag = this.f_31946_.m_60734_() instanceof ConcretePowderBlock;
            boolean flag1 = flag && this.f_19853_.m_6425_(blockpos1).m_76153_(FluidTags.f_13131_);
            double d0 = this.m_20184_().m_82556_();
            if (flag && d0 > 1.0D) {
               BlockHitResult blockhitresult = this.f_19853_.m_45547_(new ClipContext(new Vec3(this.f_19854_, this.f_19855_, this.f_19856_), this.m_20182_(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
               if (blockhitresult.m_6662_() != HitResult.Type.MISS && this.f_19853_.m_6425_(blockhitresult.m_82425_()).m_76153_(FluidTags.f_13131_)) {
                  blockpos1 = blockhitresult.m_82425_();
                  flag1 = true;
               }
            }

            if (!this.f_19861_ && !flag1) {
               if (!this.f_19853_.f_46443_ && (this.f_31942_ > 100 && (blockpos1.m_123342_() <= this.f_19853_.m_141937_() || blockpos1.m_123342_() > this.f_19853_.m_151558_()) || this.f_31942_ > 600)) {
                  if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                     this.m_19998_(block);
                  }

                  this.m_146870_();
               }
            } else {
               BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
               this.m_20256_(this.m_20184_().m_82542_(0.7D, -0.5D, 0.7D));
               if (!blockstate.m_60713_(Blocks.f_50110_)) {
                  if (!this.f_31947_) {
                     boolean flag2 = blockstate.m_60629_(new DirectionalPlaceContext(this.f_19853_, blockpos1, Direction.DOWN, ItemStack.f_41583_, Direction.UP));
                     boolean flag3 = FallingBlock.m_53241_(this.f_19853_.m_8055_(blockpos1.m_7495_())) && (!flag || !flag1);
                     boolean flag4 = this.f_31946_.m_60710_(this.f_19853_, blockpos1) && !flag3;
                     if (flag2 && flag4) {
                        if (this.f_31946_.m_61138_(BlockStateProperties.f_61362_) && this.f_19853_.m_6425_(blockpos1).m_76152_() == Fluids.f_76193_) {
                           this.f_31946_ = this.f_31946_.m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(true));
                        }

                        if (this.f_19853_.m_7731_(blockpos1, this.f_31946_, 3)) {
                           ((ServerLevel)this.f_19853_).m_7726_().f_8325_.m_140201_(this, new ClientboundBlockUpdatePacket(blockpos1, this.f_19853_.m_8055_(blockpos1)));
                           this.m_146870_();
                           if (block instanceof Fallable) {
                              ((Fallable)block).m_142216_(this.f_19853_, blockpos1, this.f_31946_, blockstate, this);
                           }

                           if (this.f_31944_ != null && this.f_31946_.m_155947_()) {
                              BlockEntity blockentity = this.f_19853_.m_7702_(blockpos1);
                              if (blockentity != null) {
                                 CompoundTag compoundtag = blockentity.m_6945_(new CompoundTag());

                                 for(String s : this.f_31944_.m_128431_()) {
                                    Tag tag = this.f_31944_.m_128423_(s);
                                    if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                       compoundtag.m_128365_(s, tag.m_6426_());
                                    }
                                 }

                                 try {
                                    blockentity.m_142466_(compoundtag);
                                 } catch (Exception exception) {
                                    f_19849_.error("Failed to load block entity from falling block", (Throwable)exception);
                                 }

                                 blockentity.m_6596_();
                              }
                           }
                        } else if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                           this.m_146870_();
                           this.m_149650_(block, blockpos1);
                           this.m_19998_(block);
                        }
                     } else {
                        this.m_146870_();
                        if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                           this.m_149650_(block, blockpos1);
                           this.m_19998_(block);
                        }
                     }
                  } else {
                     this.m_146870_();
                     this.m_149650_(block, blockpos1);
                  }
               }
            }
         }

         this.m_20256_(this.m_20184_().m_82490_(0.98D));
      }
   }

   public void m_149650_(Block p_149651_, BlockPos p_149652_) {
      if (p_149651_ instanceof Fallable) {
         ((Fallable)p_149651_).m_142525_(this.f_19853_, p_149652_, this);
      }

   }

   public boolean m_142535_(float p_149643_, float p_149644_, DamageSource p_149645_) {
      if (!this.f_31939_) {
         return false;
      } else {
         int i = Mth.m_14167_(p_149643_ - 1.0F);
         if (i < 0) {
            return false;
         } else {
            Predicate<Entity> predicate;
            DamageSource damagesource;
            if (this.f_31946_.m_60734_() instanceof Fallable) {
               Fallable fallable = (Fallable)this.f_31946_.m_60734_();
               predicate = fallable.m_142398_();
               damagesource = fallable.m_142088_();
            } else {
               predicate = EntitySelector.f_20408_;
               damagesource = DamageSource.f_19322_;
            }

            float f = (float)Math.min(Mth.m_14143_((float)i * this.f_149641_), this.f_31940_);
            this.f_19853_.m_6249_(this, this.m_142469_(), predicate).forEach((p_149649_) -> {
               p_149649_.m_6469_(damagesource, f);
            });
            boolean flag = this.f_31946_.m_60620_(BlockTags.f_13033_);
            if (flag && f > 0.0F && this.f_19796_.nextFloat() < 0.05F + (float)i * 0.05F) {
               BlockState blockstate = AnvilBlock.m_48824_(this.f_31946_);
               if (blockstate == null) {
                  this.f_31947_ = true;
               } else {
                  this.f_31946_ = blockstate;
               }
            }

            return false;
         }
      }
   }

   protected void m_7380_(CompoundTag p_31973_) {
      p_31973_.m_128365_("BlockState", NbtUtils.m_129202_(this.f_31946_));
      p_31973_.m_128405_("Time", this.f_31942_);
      p_31973_.m_128379_("DropItem", this.f_31943_);
      p_31973_.m_128379_("HurtEntities", this.f_31939_);
      p_31973_.m_128350_("FallHurtAmount", this.f_149641_);
      p_31973_.m_128405_("FallHurtMax", this.f_31940_);
      if (this.f_31944_ != null) {
         p_31973_.m_128365_("TileEntityData", this.f_31944_);
      }

   }

   protected void m_7378_(CompoundTag p_31964_) {
      this.f_31946_ = NbtUtils.m_129241_(p_31964_.m_128469_("BlockState"));
      this.f_31942_ = p_31964_.m_128451_("Time");
      if (p_31964_.m_128425_("HurtEntities", 99)) {
         this.f_31939_ = p_31964_.m_128471_("HurtEntities");
         this.f_149641_ = p_31964_.m_128457_("FallHurtAmount");
         this.f_31940_ = p_31964_.m_128451_("FallHurtMax");
      } else if (this.f_31946_.m_60620_(BlockTags.f_13033_)) {
         this.f_31939_ = true;
      }

      if (p_31964_.m_128425_("DropItem", 99)) {
         this.f_31943_ = p_31964_.m_128471_("DropItem");
      }

      if (p_31964_.m_128425_("TileEntityData", 10)) {
         this.f_31944_ = p_31964_.m_128469_("TileEntityData");
      }

      if (this.f_31946_.m_60795_()) {
         this.f_31946_ = Blocks.f_49992_.m_49966_();
      }

   }

   public Level m_31979_() {
      return this.f_19853_;
   }

   public void m_149656_(float p_149657_, int p_149658_) {
      this.f_31939_ = true;
      this.f_149641_ = p_149657_;
      this.f_31940_ = p_149658_;
   }

   public boolean m_6051_() {
      return false;
   }

   public void m_7976_(CrashReportCategory p_31962_) {
      super.m_7976_(p_31962_);
      p_31962_.m_128159_("Immitating BlockState", this.f_31946_.toString());
   }

   public BlockState m_31980_() {
      return this.f_31946_;
   }

   public boolean m_6127_() {
      return true;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this, Block.m_49956_(this.m_31980_()));
   }

   public void m_141965_(ClientboundAddEntityPacket p_149654_) {
      super.m_141965_(p_149654_);
      this.f_31946_ = Block.m_49803_(p_149654_.m_131509_());
      this.f_19850_ = true;
      double d0 = p_149654_.m_131500_();
      double d1 = p_149654_.m_131501_();
      double d2 = p_149654_.m_131502_();
      this.m_6034_(d0, d1 + (double)((1.0F - this.m_20206_()) / 2.0F), d2);
      this.m_31959_(this.m_142538_());
   }
}