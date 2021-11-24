package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final EnumProperty<BedPart> f_49440_ = BlockStateProperties.f_61391_;
   public static final BooleanProperty f_49441_ = BlockStateProperties.f_61445_;
   protected static final int f_152166_ = 9;
   protected static final VoxelShape f_49442_ = Block.m_49796_(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
   private static final int f_152167_ = 3;
   protected static final VoxelShape f_49443_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
   protected static final VoxelShape f_49444_ = Block.m_49796_(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
   protected static final VoxelShape f_49445_ = Block.m_49796_(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
   protected static final VoxelShape f_49446_ = Block.m_49796_(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
   protected static final VoxelShape f_49447_ = Shapes.m_83124_(f_49442_, f_49443_, f_49445_);
   protected static final VoxelShape f_49448_ = Shapes.m_83124_(f_49442_, f_49444_, f_49446_);
   protected static final VoxelShape f_49449_ = Shapes.m_83124_(f_49442_, f_49443_, f_49444_);
   protected static final VoxelShape f_49450_ = Shapes.m_83124_(f_49442_, f_49445_, f_49446_);
   private final DyeColor f_49451_;

   public BedBlock(DyeColor p_49454_, BlockBehaviour.Properties p_49455_) {
      super(p_49455_);
      this.f_49451_ = p_49454_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49440_, BedPart.FOOT).m_61124_(f_49441_, Boolean.valueOf(false)));
   }

   @Nullable
   public static Direction m_49485_(BlockGetter p_49486_, BlockPos p_49487_) {
      BlockState blockstate = p_49486_.m_8055_(p_49487_);
      return blockstate.m_60734_() instanceof BedBlock ? blockstate.m_61143_(f_54117_) : null;
   }

   public InteractionResult m_6227_(BlockState p_49515_, Level p_49516_, BlockPos p_49517_, Player p_49518_, InteractionHand p_49519_, BlockHitResult p_49520_) {
      if (p_49516_.f_46443_) {
         return InteractionResult.CONSUME;
      } else {
         if (p_49515_.m_61143_(f_49440_) != BedPart.HEAD) {
            p_49517_ = p_49517_.m_142300_(p_49515_.m_61143_(f_54117_));
            p_49515_ = p_49516_.m_8055_(p_49517_);
            if (!p_49515_.m_60713_(this)) {
               return InteractionResult.CONSUME;
            }
         }

         if (!m_49488_(p_49516_)) {
            p_49516_.m_7471_(p_49517_, false);
            BlockPos blockpos = p_49517_.m_142300_(p_49515_.m_61143_(f_54117_).m_122424_());
            if (p_49516_.m_8055_(blockpos).m_60713_(this)) {
               p_49516_.m_7471_(blockpos, false);
            }

            p_49516_.m_7703_((Entity)null, DamageSource.m_19334_(), (ExplosionDamageCalculator)null, (double)p_49517_.m_123341_() + 0.5D, (double)p_49517_.m_123342_() + 0.5D, (double)p_49517_.m_123343_() + 0.5D, 5.0F, true, Explosion.BlockInteraction.DESTROY);
            return InteractionResult.SUCCESS;
         } else if (p_49515_.m_61143_(f_49441_)) {
            if (!this.m_49490_(p_49516_, p_49517_)) {
               p_49518_.m_5661_(new TranslatableComponent("block.minecraft.bed.occupied"), true);
            }

            return InteractionResult.SUCCESS;
         } else {
            p_49518_.m_7720_(p_49517_).ifLeft((p_49477_) -> {
               if (p_49477_ != null) {
                  p_49518_.m_5661_(p_49477_.m_36423_(), true);
               }

            });
            return InteractionResult.SUCCESS;
         }
      }
   }

   public static boolean m_49488_(Level p_49489_) {
      return p_49489_.m_6042_().m_63961_();
   }

   private boolean m_49490_(Level p_49491_, BlockPos p_49492_) {
      List<Villager> list = p_49491_.m_6443_(Villager.class, new AABB(p_49492_), LivingEntity::m_5803_);
      if (list.isEmpty()) {
         return false;
      } else {
         list.get(0).m_5796_();
         return true;
      }
   }

   public void m_142072_(Level p_152169_, BlockState p_152170_, BlockPos p_152171_, Entity p_152172_, float p_152173_) {
      super.m_142072_(p_152169_, p_152170_, p_152171_, p_152172_, p_152173_ * 0.5F);
   }

   public void m_5548_(BlockGetter p_49483_, Entity p_49484_) {
      if (p_49484_.m_20162_()) {
         super.m_5548_(p_49483_, p_49484_);
      } else {
         this.m_49456_(p_49484_);
      }

   }

   private void m_49456_(Entity p_49457_) {
      Vec3 vec3 = p_49457_.m_20184_();
      if (vec3.f_82480_ < 0.0D) {
         double d0 = p_49457_ instanceof LivingEntity ? 1.0D : 0.8D;
         p_49457_.m_20334_(vec3.f_82479_, -vec3.f_82480_ * (double)0.66F * d0, vec3.f_82481_);
      }

   }

   public BlockState m_7417_(BlockState p_49525_, Direction p_49526_, BlockState p_49527_, LevelAccessor p_49528_, BlockPos p_49529_, BlockPos p_49530_) {
      if (p_49526_ == m_49533_(p_49525_.m_61143_(f_49440_), p_49525_.m_61143_(f_54117_))) {
         return p_49527_.m_60713_(this) && p_49527_.m_61143_(f_49440_) != p_49525_.m_61143_(f_49440_) ? p_49525_.m_61124_(f_49441_, p_49527_.m_61143_(f_49441_)) : Blocks.f_50016_.m_49966_();
      } else {
         return super.m_7417_(p_49525_, p_49526_, p_49527_, p_49528_, p_49529_, p_49530_);
      }
   }

   private static Direction m_49533_(BedPart p_49534_, Direction p_49535_) {
      return p_49534_ == BedPart.FOOT ? p_49535_ : p_49535_.m_122424_();
   }

   public void m_5707_(Level p_49505_, BlockPos p_49506_, BlockState p_49507_, Player p_49508_) {
      if (!p_49505_.f_46443_ && p_49508_.m_7500_()) {
         BedPart bedpart = p_49507_.m_61143_(f_49440_);
         if (bedpart == BedPart.FOOT) {
            BlockPos blockpos = p_49506_.m_142300_(m_49533_(bedpart, p_49507_.m_61143_(f_54117_)));
            BlockState blockstate = p_49505_.m_8055_(blockpos);
            if (blockstate.m_60713_(this) && blockstate.m_61143_(f_49440_) == BedPart.HEAD) {
               p_49505_.m_7731_(blockpos, Blocks.f_50016_.m_49966_(), 35);
               p_49505_.m_5898_(p_49508_, 2001, blockpos, Block.m_49956_(blockstate));
            }
         }
      }

      super.m_5707_(p_49505_, p_49506_, p_49507_, p_49508_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_49479_) {
      Direction direction = p_49479_.m_8125_();
      BlockPos blockpos = p_49479_.m_8083_();
      BlockPos blockpos1 = blockpos.m_142300_(direction);
      return p_49479_.m_43725_().m_8055_(blockpos1).m_60629_(p_49479_) ? this.m_49966_().m_61124_(f_54117_, direction) : null;
   }

   public VoxelShape m_5940_(BlockState p_49547_, BlockGetter p_49548_, BlockPos p_49549_, CollisionContext p_49550_) {
      Direction direction = m_49557_(p_49547_).m_122424_();
      switch(direction) {
      case NORTH:
         return f_49447_;
      case SOUTH:
         return f_49448_;
      case WEST:
         return f_49449_;
      default:
         return f_49450_;
      }
   }

   public static Direction m_49557_(BlockState p_49558_) {
      Direction direction = p_49558_.m_61143_(f_54117_);
      return p_49558_.m_61143_(f_49440_) == BedPart.HEAD ? direction.m_122424_() : direction;
   }

   public static DoubleBlockCombiner.BlockType m_49559_(BlockState p_49560_) {
      BedPart bedpart = p_49560_.m_61143_(f_49440_);
      return bedpart == BedPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
   }

   private static boolean m_49541_(BlockGetter p_49542_, BlockPos p_49543_) {
      return p_49542_.m_8055_(p_49543_.m_7495_()).m_60734_() instanceof BedBlock;
   }

   public static Optional<Vec3> m_49458_(EntityType<?> p_49459_, CollisionGetter p_49460_, BlockPos p_49461_, float p_49462_) {
      Direction direction = p_49460_.m_8055_(p_49461_).m_61143_(f_54117_);
      Direction direction1 = direction.m_122427_();
      Direction direction2 = direction1.m_122370_(p_49462_) ? direction1.m_122424_() : direction1;
      if (m_49541_(p_49460_, p_49461_)) {
         return m_49463_(p_49459_, p_49460_, p_49461_, direction, direction2);
      } else {
         int[][] aint = m_49538_(direction, direction2);
         Optional<Vec3> optional = m_49469_(p_49459_, p_49460_, p_49461_, aint, true);
         return optional.isPresent() ? optional : m_49469_(p_49459_, p_49460_, p_49461_, aint, false);
      }
   }

   private static Optional<Vec3> m_49463_(EntityType<?> p_49464_, CollisionGetter p_49465_, BlockPos p_49466_, Direction p_49467_, Direction p_49468_) {
      int[][] aint = m_49551_(p_49467_, p_49468_);
      Optional<Vec3> optional = m_49469_(p_49464_, p_49465_, p_49466_, aint, true);
      if (optional.isPresent()) {
         return optional;
      } else {
         BlockPos blockpos = p_49466_.m_7495_();
         Optional<Vec3> optional1 = m_49469_(p_49464_, p_49465_, blockpos, aint, true);
         if (optional1.isPresent()) {
            return optional1;
         } else {
            int[][] aint1 = m_49536_(p_49467_);
            Optional<Vec3> optional2 = m_49469_(p_49464_, p_49465_, p_49466_, aint1, true);
            if (optional2.isPresent()) {
               return optional2;
            } else {
               Optional<Vec3> optional3 = m_49469_(p_49464_, p_49465_, p_49466_, aint, false);
               if (optional3.isPresent()) {
                  return optional3;
               } else {
                  Optional<Vec3> optional4 = m_49469_(p_49464_, p_49465_, blockpos, aint, false);
                  return optional4.isPresent() ? optional4 : m_49469_(p_49464_, p_49465_, p_49466_, aint1, false);
               }
            }
         }
      }
   }

   private static Optional<Vec3> m_49469_(EntityType<?> p_49470_, CollisionGetter p_49471_, BlockPos p_49472_, int[][] p_49473_, boolean p_49474_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int[] aint : p_49473_) {
         blockpos$mutableblockpos.m_122178_(p_49472_.m_123341_() + aint[0], p_49472_.m_123342_(), p_49472_.m_123343_() + aint[1]);
         Vec3 vec3 = DismountHelper.m_38441_(p_49470_, p_49471_, blockpos$mutableblockpos, p_49474_);
         if (vec3 != null) {
            return Optional.of(vec3);
         }
      }

      return Optional.empty();
   }

   public PushReaction m_5537_(BlockState p_49556_) {
      return PushReaction.DESTROY;
   }

   public RenderShape m_7514_(BlockState p_49545_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49532_) {
      p_49532_.m_61104_(f_54117_, f_49440_, f_49441_);
   }

   public BlockEntity m_142194_(BlockPos p_152175_, BlockState p_152176_) {
      return new BedBlockEntity(p_152175_, p_152176_, this.f_49451_);
   }

   public void m_6402_(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) {
      super.m_6402_(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
      if (!p_49499_.f_46443_) {
         BlockPos blockpos = p_49500_.m_142300_(p_49501_.m_61143_(f_54117_));
         p_49499_.m_7731_(blockpos, p_49501_.m_61124_(f_49440_, BedPart.HEAD), 3);
         p_49499_.m_6289_(p_49500_, Blocks.f_50016_);
         p_49501_.m_60701_(p_49499_, p_49500_, 3);
      }

   }

   public DyeColor m_49554_() {
      return this.f_49451_;
   }

   public long m_7799_(BlockState p_49522_, BlockPos p_49523_) {
      BlockPos blockpos = p_49523_.m_5484_(p_49522_.m_61143_(f_54117_), p_49522_.m_61143_(f_49440_) == BedPart.HEAD ? 0 : 1);
      return Mth.m_14130_(blockpos.m_123341_(), p_49523_.m_123342_(), blockpos.m_123343_());
   }

   public boolean m_7357_(BlockState p_49510_, BlockGetter p_49511_, BlockPos p_49512_, PathComputationType p_49513_) {
      return false;
   }

   private static int[][] m_49538_(Direction p_49539_, Direction p_49540_) {
      return ArrayUtils.addAll((int[][])m_49551_(p_49539_, p_49540_), (int[][])m_49536_(p_49539_));
   }

   private static int[][] m_49551_(Direction p_49552_, Direction p_49553_) {
      return new int[][]{{p_49553_.m_122429_(), p_49553_.m_122431_()}, {p_49553_.m_122429_() - p_49552_.m_122429_(), p_49553_.m_122431_() - p_49552_.m_122431_()}, {p_49553_.m_122429_() - p_49552_.m_122429_() * 2, p_49553_.m_122431_() - p_49552_.m_122431_() * 2}, {-p_49552_.m_122429_() * 2, -p_49552_.m_122431_() * 2}, {-p_49553_.m_122429_() - p_49552_.m_122429_() * 2, -p_49553_.m_122431_() - p_49552_.m_122431_() * 2}, {-p_49553_.m_122429_() - p_49552_.m_122429_(), -p_49553_.m_122431_() - p_49552_.m_122431_()}, {-p_49553_.m_122429_(), -p_49553_.m_122431_()}, {-p_49553_.m_122429_() + p_49552_.m_122429_(), -p_49553_.m_122431_() + p_49552_.m_122431_()}, {p_49552_.m_122429_(), p_49552_.m_122431_()}, {p_49553_.m_122429_() + p_49552_.m_122429_(), p_49553_.m_122431_() + p_49552_.m_122431_()}};
   }

   private static int[][] m_49536_(Direction p_49537_) {
      return new int[][]{{0, 0}, {-p_49537_.m_122429_(), -p_49537_.m_122431_()}};
   }
}