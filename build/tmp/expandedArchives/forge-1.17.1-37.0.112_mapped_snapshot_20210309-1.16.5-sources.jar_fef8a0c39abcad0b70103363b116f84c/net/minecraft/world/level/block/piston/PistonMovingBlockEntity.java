package net.minecraft.world.level.block.piston;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonMovingBlockEntity extends BlockEntity {
   private static final int f_155898_ = 2;
   private static final double f_155899_ = 0.01D;
   public static final double f_155897_ = 0.51D;
   private BlockState f_60334_;
   private Direction f_60335_;
   private boolean f_60336_;
   private boolean f_60337_;
   private static final ThreadLocal<Direction> f_60338_ = ThreadLocal.withInitial(() -> {
      return null;
   });
   private float f_60339_;
   private float f_60340_;
   private long f_60341_;
   private int f_60342_;

   public PistonMovingBlockEntity(BlockPos p_155901_, BlockState p_155902_) {
      super(BlockEntityType.f_58926_, p_155901_, p_155902_);
   }

   public PistonMovingBlockEntity(BlockPos p_155904_, BlockState p_155905_, BlockState p_155906_, Direction p_155907_, boolean p_155908_, boolean p_155909_) {
      this(p_155904_, p_155905_);
      this.f_60334_ = p_155906_;
      this.f_60335_ = p_155907_;
      this.f_60336_ = p_155908_;
      this.f_60337_ = p_155909_;
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public boolean m_60387_() {
      return this.f_60336_;
   }

   public Direction m_60392_() {
      return this.f_60335_;
   }

   public boolean m_60397_() {
      return this.f_60337_;
   }

   public float m_60350_(float p_60351_) {
      if (p_60351_ > 1.0F) {
         p_60351_ = 1.0F;
      }

      return Mth.m_14179_(p_60351_, this.f_60340_, this.f_60339_);
   }

   public float m_60380_(float p_60381_) {
      return (float)this.f_60335_.m_122429_() * this.m_60390_(this.m_60350_(p_60381_));
   }

   public float m_60385_(float p_60386_) {
      return (float)this.f_60335_.m_122430_() * this.m_60390_(this.m_60350_(p_60386_));
   }

   public float m_60388_(float p_60389_) {
      return (float)this.f_60335_.m_122431_() * this.m_60390_(this.m_60350_(p_60389_));
   }

   private float m_60390_(float p_60391_) {
      return this.f_60336_ ? p_60391_ - 1.0F : 1.0F - p_60391_;
   }

   private BlockState m_60403_() {
      return !this.m_60387_() && this.m_60397_() && this.f_60334_.m_60734_() instanceof PistonBaseBlock ? Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_60236_, Boolean.valueOf(this.f_60339_ > 0.25F)).m_61124_(PistonHeadBlock.f_60235_, this.f_60334_.m_60713_(Blocks.f_50032_) ? PistonType.STICKY : PistonType.DEFAULT).m_61124_(PistonHeadBlock.f_52588_, this.f_60334_.m_61143_(PistonBaseBlock.f_52588_)) : this.f_60334_;
   }

   private static void m_155910_(Level p_155911_, BlockPos p_155912_, float p_155913_, PistonMovingBlockEntity p_155914_) {
      Direction direction = p_155914_.m_60399_();
      double d0 = (double)(p_155913_ - p_155914_.f_60339_);
      VoxelShape voxelshape = p_155914_.m_60403_().m_60812_(p_155911_, p_155912_);
      if (!voxelshape.m_83281_()) {
         AABB aabb = m_155925_(p_155912_, voxelshape.m_83215_(), p_155914_);
         List<Entity> list = p_155911_.m_45933_((Entity)null, PistonMath.m_60328_(aabb, direction, d0).m_82367_(aabb));
         if (!list.isEmpty()) {
            List<AABB> list1 = voxelshape.m_83299_();
            boolean flag = p_155914_.f_60334_.isSlimeBlock(); //TODO: is this patch really needed the logic of the original seems sound revisit later
            Iterator iterator = list.iterator();

            while(true) {
               Entity entity;
               while(true) {
                  if (!iterator.hasNext()) {
                     return;
                  }

                  entity = (Entity)iterator.next();
                  if (entity.m_7752_() != PushReaction.IGNORE) {
                     if (!flag) {
                        break;
                     }

                     if (!(entity instanceof ServerPlayer)) {
                        Vec3 vec3 = entity.m_20184_();
                        double d1 = vec3.f_82479_;
                        double d2 = vec3.f_82480_;
                        double d3 = vec3.f_82481_;
                        switch(direction.m_122434_()) {
                        case X:
                           d1 = (double)direction.m_122429_();
                           break;
                        case Y:
                           d2 = (double)direction.m_122430_();
                           break;
                        case Z:
                           d3 = (double)direction.m_122431_();
                        }

                        entity.m_20334_(d1, d2, d3);
                        break;
                     }
                  }
               }

               double d4 = 0.0D;

               for(AABB aabb2 : list1) {
                  AABB aabb1 = PistonMath.m_60328_(m_155925_(p_155912_, aabb2, p_155914_), direction, d0);
                  AABB aabb3 = entity.m_142469_();
                  if (aabb1.m_82381_(aabb3)) {
                     d4 = Math.max(d4, m_60367_(aabb1, direction, aabb3));
                     if (d4 >= d0) {
                        break;
                     }
                  }
               }

               if (!(d4 <= 0.0D)) {
                  d4 = Math.min(d4, d0) + 0.01D;
                  m_60371_(direction, entity, d4, direction);
                  if (!p_155914_.f_60336_ && p_155914_.f_60337_) {
                     m_155920_(p_155912_, entity, direction, d0);
                  }
               }
            }
         }
      }
   }

   private static void m_60371_(Direction p_60372_, Entity p_60373_, double p_60374_, Direction p_60375_) {
      f_60338_.set(p_60372_);
      p_60373_.m_6478_(MoverType.PISTON, new Vec3(p_60374_ * (double)p_60375_.m_122429_(), p_60374_ * (double)p_60375_.m_122430_(), p_60374_ * (double)p_60375_.m_122431_()));
      f_60338_.set((Direction)null);
   }

   private static void m_155931_(Level p_155932_, BlockPos p_155933_, float p_155934_, PistonMovingBlockEntity p_155935_) {
      if (p_155935_.m_60404_()) {
         Direction direction = p_155935_.m_60399_();
         if (direction.m_122434_().m_122479_()) {
            double d0 = p_155935_.f_60334_.m_60812_(p_155932_, p_155933_).m_83297_(Direction.Axis.Y);
            AABB aabb = m_155925_(p_155933_, new AABB(0.0D, d0, 0.0D, 1.0D, 1.5000000999999998D, 1.0D), p_155935_);
            double d1 = (double)(p_155934_ - p_155935_.f_60339_);

            for(Entity entity : p_155932_.m_6249_((Entity)null, aabb, (p_60384_) -> {
               return m_60364_(aabb, p_60384_);
            })) {
               m_60371_(direction, entity, d1, direction);
            }

         }
      }
   }

   private static boolean m_60364_(AABB p_60365_, Entity p_60366_) {
      return p_60366_.m_7752_() == PushReaction.NORMAL && p_60366_.m_20096_() && p_60366_.m_20185_() >= p_60365_.f_82288_ && p_60366_.m_20185_() <= p_60365_.f_82291_ && p_60366_.m_20189_() >= p_60365_.f_82290_ && p_60366_.m_20189_() <= p_60365_.f_82293_;
   }

   private boolean m_60404_() {
      return this.f_60334_.m_60713_(Blocks.f_50719_);
   }

   public Direction m_60399_() {
      return this.f_60336_ ? this.f_60335_ : this.f_60335_.m_122424_();
   }

   private static double m_60367_(AABB p_60368_, Direction p_60369_, AABB p_60370_) {
      switch(p_60369_) {
      case EAST:
         return p_60368_.f_82291_ - p_60370_.f_82288_;
      case WEST:
         return p_60370_.f_82291_ - p_60368_.f_82288_;
      case UP:
      default:
         return p_60368_.f_82292_ - p_60370_.f_82289_;
      case DOWN:
         return p_60370_.f_82292_ - p_60368_.f_82289_;
      case SOUTH:
         return p_60368_.f_82293_ - p_60370_.f_82290_;
      case NORTH:
         return p_60370_.f_82293_ - p_60368_.f_82290_;
      }
   }

   private static AABB m_155925_(BlockPos p_155926_, AABB p_155927_, PistonMovingBlockEntity p_155928_) {
      double d0 = (double)p_155928_.m_60390_(p_155928_.f_60339_);
      return p_155927_.m_82386_((double)p_155926_.m_123341_() + d0 * (double)p_155928_.f_60335_.m_122429_(), (double)p_155926_.m_123342_() + d0 * (double)p_155928_.f_60335_.m_122430_(), (double)p_155926_.m_123343_() + d0 * (double)p_155928_.f_60335_.m_122431_());
   }

   private static void m_155920_(BlockPos p_155921_, Entity p_155922_, Direction p_155923_, double p_155924_) {
      AABB aabb = p_155922_.m_142469_();
      AABB aabb1 = Shapes.m_83144_().m_83215_().m_82338_(p_155921_);
      if (aabb.m_82381_(aabb1)) {
         Direction direction = p_155923_.m_122424_();
         double d0 = m_60367_(aabb1, direction, aabb) + 0.01D;
         double d1 = m_60367_(aabb1, direction, aabb.m_82323_(aabb1)) + 0.01D;
         if (Math.abs(d0 - d1) < 0.01D) {
            d0 = Math.min(d0, p_155924_) + 0.01D;
            m_60371_(p_155923_, p_155922_, d0, direction);
         }
      }

   }

   public BlockState m_60400_() {
      return this.f_60334_;
   }

   public void m_60401_() {
      if (this.f_58857_ != null && (this.f_60340_ < 1.0F || this.f_58857_.f_46443_)) {
         this.f_60339_ = 1.0F;
         this.f_60340_ = this.f_60339_;
         this.f_58857_.m_46747_(this.f_58858_);
         this.m_7651_();
         if (this.f_58857_.m_8055_(this.f_58858_).m_60713_(Blocks.f_50110_)) {
            BlockState blockstate;
            if (this.f_60337_) {
               blockstate = Blocks.f_50016_.m_49966_();
            } else {
               blockstate = Block.m_49931_(this.f_60334_, this.f_58857_, this.f_58858_);
            }

            this.f_58857_.m_7731_(this.f_58858_, blockstate, 3);
            this.f_58857_.m_46586_(this.f_58858_, blockstate.m_60734_(), this.f_58858_);
         }
      }

   }

   public static void m_155915_(Level p_155916_, BlockPos p_155917_, BlockState p_155918_, PistonMovingBlockEntity p_155919_) {
      p_155919_.f_60341_ = p_155916_.m_46467_();
      p_155919_.f_60340_ = p_155919_.f_60339_;
      if (p_155919_.f_60340_ >= 1.0F) {
         if (p_155916_.f_46443_ && p_155919_.f_60342_ < 5) {
            ++p_155919_.f_60342_;
         } else {
            p_155916_.m_46747_(p_155917_);
            p_155919_.m_7651_();
            if (p_155919_.f_60334_ != null && p_155916_.m_8055_(p_155917_).m_60713_(Blocks.f_50110_)) {
               BlockState blockstate = Block.m_49931_(p_155919_.f_60334_, p_155916_, p_155917_);
               if (blockstate.m_60795_()) {
                  p_155916_.m_7731_(p_155917_, p_155919_.f_60334_, 84);
                  Block.m_49902_(p_155919_.f_60334_, blockstate, p_155916_, p_155917_, 3);
               } else {
                  if (blockstate.m_61138_(BlockStateProperties.f_61362_) && blockstate.m_61143_(BlockStateProperties.f_61362_)) {
                     blockstate = blockstate.m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(false));
                  }

                  p_155916_.m_7731_(p_155917_, blockstate, 67);
                  p_155916_.m_46586_(p_155917_, blockstate.m_60734_(), p_155917_);
               }
            }

         }
      } else {
         float f = p_155919_.f_60339_ + 0.5F;
         m_155910_(p_155916_, p_155917_, f, p_155919_);
         m_155931_(p_155916_, p_155917_, f, p_155919_);
         p_155919_.f_60339_ = f;
         if (p_155919_.f_60339_ >= 1.0F) {
            p_155919_.f_60339_ = 1.0F;
         }

      }
   }

   public void m_142466_(CompoundTag p_155930_) {
      super.m_142466_(p_155930_);
      this.f_60334_ = NbtUtils.m_129241_(p_155930_.m_128469_("blockState"));
      this.f_60335_ = Direction.m_122376_(p_155930_.m_128451_("facing"));
      this.f_60339_ = p_155930_.m_128457_("progress");
      this.f_60340_ = this.f_60339_;
      this.f_60336_ = p_155930_.m_128471_("extending");
      this.f_60337_ = p_155930_.m_128471_("source");
   }

   public CompoundTag m_6945_(CompoundTag p_60377_) {
      super.m_6945_(p_60377_);
      p_60377_.m_128365_("blockState", NbtUtils.m_129202_(this.f_60334_));
      p_60377_.m_128405_("facing", this.f_60335_.m_122411_());
      p_60377_.m_128350_("progress", this.f_60340_);
      p_60377_.m_128379_("extending", this.f_60336_);
      p_60377_.m_128379_("source", this.f_60337_);
      return p_60377_;
   }

   public VoxelShape m_60356_(BlockGetter p_60357_, BlockPos p_60358_) {
      VoxelShape voxelshape;
      if (!this.f_60336_ && this.f_60337_) {
         voxelshape = this.f_60334_.m_61124_(PistonBaseBlock.f_60153_, Boolean.valueOf(true)).m_60812_(p_60357_, p_60358_);
      } else {
         voxelshape = Shapes.m_83040_();
      }

      Direction direction = f_60338_.get();
      if ((double)this.f_60339_ < 1.0D && direction == this.m_60399_()) {
         return voxelshape;
      } else {
         BlockState blockstate;
         if (this.m_60397_()) {
            blockstate = Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_52588_, this.f_60335_).m_61124_(PistonHeadBlock.f_60236_, Boolean.valueOf(this.f_60336_ != 1.0F - this.f_60339_ < 0.25F));
         } else {
            blockstate = this.f_60334_;
         }

         float f = this.m_60390_(this.f_60339_);
         double d0 = (double)((float)this.f_60335_.m_122429_() * f);
         double d1 = (double)((float)this.f_60335_.m_122430_() * f);
         double d2 = (double)((float)this.f_60335_.m_122431_() * f);
         return Shapes.m_83110_(voxelshape, blockstate.m_60812_(p_60357_, p_60358_).m_83216_(d0, d1, d2));
      }
   }

   public long m_60402_() {
      return this.f_60341_;
   }
}
