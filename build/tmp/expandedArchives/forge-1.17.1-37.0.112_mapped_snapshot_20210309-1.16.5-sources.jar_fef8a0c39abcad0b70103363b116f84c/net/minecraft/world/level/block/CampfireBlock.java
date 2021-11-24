package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CampfireBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   protected static final VoxelShape f_51226_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
   public static final BooleanProperty f_51227_ = BlockStateProperties.f_61443_;
   public static final BooleanProperty f_51228_ = BlockStateProperties.f_61450_;
   public static final BooleanProperty f_51229_ = BlockStateProperties.f_61362_;
   public static final DirectionProperty f_51230_ = BlockStateProperties.f_61374_;
   private static final VoxelShape f_51231_ = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
   private static final int f_152748_ = 5;
   private final boolean f_51232_;
   private final int f_51233_;

   public CampfireBlock(boolean p_51236_, int p_51237_, BlockBehaviour.Properties p_51238_) {
      super(p_51238_);
      this.f_51232_ = p_51236_;
      this.f_51233_ = p_51237_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51227_, Boolean.valueOf(true)).m_61124_(f_51228_, Boolean.valueOf(false)).m_61124_(f_51229_, Boolean.valueOf(false)).m_61124_(f_51230_, Direction.NORTH));
   }

   public InteractionResult m_6227_(BlockState p_51274_, Level p_51275_, BlockPos p_51276_, Player p_51277_, InteractionHand p_51278_, BlockHitResult p_51279_) {
      BlockEntity blockentity = p_51275_.m_7702_(p_51276_);
      if (blockentity instanceof CampfireBlockEntity) {
         CampfireBlockEntity campfireblockentity = (CampfireBlockEntity)blockentity;
         ItemStack itemstack = p_51277_.m_21120_(p_51278_);
         Optional<CampfireCookingRecipe> optional = campfireblockentity.m_59051_(itemstack);
         if (optional.isPresent()) {
            if (!p_51275_.f_46443_ && campfireblockentity.m_59053_(p_51277_.m_150110_().f_35937_ ? itemstack.m_41777_() : itemstack, optional.get().m_43753_())) {
               p_51277_.m_36220_(Stats.f_12975_);
               return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
         }
      }

      return InteractionResult.PASS;
   }

   public void m_7892_(BlockState p_51269_, Level p_51270_, BlockPos p_51271_, Entity p_51272_) {
      if (!p_51272_.m_5825_() && p_51269_.m_61143_(f_51227_) && p_51272_ instanceof LivingEntity && !EnchantmentHelper.m_44938_((LivingEntity)p_51272_)) {
         p_51272_.m_6469_(DamageSource.f_19305_, (float)this.f_51233_);
      }

      super.m_7892_(p_51269_, p_51270_, p_51271_, p_51272_);
   }

   public void m_6810_(BlockState p_51281_, Level p_51282_, BlockPos p_51283_, BlockState p_51284_, boolean p_51285_) {
      if (!p_51281_.m_60713_(p_51284_.m_60734_())) {
         BlockEntity blockentity = p_51282_.m_7702_(p_51283_);
         if (blockentity instanceof CampfireBlockEntity) {
            Containers.m_19010_(p_51282_, p_51283_, ((CampfireBlockEntity)blockentity).m_59065_());
         }

         super.m_6810_(p_51281_, p_51282_, p_51283_, p_51284_, p_51285_);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_51240_) {
      LevelAccessor levelaccessor = p_51240_.m_43725_();
      BlockPos blockpos = p_51240_.m_8083_();
      boolean flag = levelaccessor.m_6425_(blockpos).m_76152_() == Fluids.f_76193_;
      return this.m_49966_().m_61124_(f_51229_, Boolean.valueOf(flag)).m_61124_(f_51228_, Boolean.valueOf(this.m_51323_(levelaccessor.m_8055_(blockpos.m_7495_())))).m_61124_(f_51227_, Boolean.valueOf(!flag)).m_61124_(f_51230_, p_51240_.m_8125_());
   }

   public BlockState m_7417_(BlockState p_51298_, Direction p_51299_, BlockState p_51300_, LevelAccessor p_51301_, BlockPos p_51302_, BlockPos p_51303_) {
      if (p_51298_.m_61143_(f_51229_)) {
         p_51301_.m_6217_().m_5945_(p_51302_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51301_));
      }

      return p_51299_ == Direction.DOWN ? p_51298_.m_61124_(f_51228_, Boolean.valueOf(this.m_51323_(p_51300_))) : super.m_7417_(p_51298_, p_51299_, p_51300_, p_51301_, p_51302_, p_51303_);
   }

   private boolean m_51323_(BlockState p_51324_) {
      return p_51324_.m_60713_(Blocks.f_50335_);
   }

   public VoxelShape m_5940_(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
      return f_51226_;
   }

   public RenderShape m_7514_(BlockState p_51307_) {
      return RenderShape.MODEL;
   }

   public void m_7100_(BlockState p_51287_, Level p_51288_, BlockPos p_51289_, Random p_51290_) {
      if (p_51287_.m_61143_(f_51227_)) {
         if (p_51290_.nextInt(10) == 0) {
            p_51288_.m_7785_((double)p_51289_.m_123341_() + 0.5D, (double)p_51289_.m_123342_() + 0.5D, (double)p_51289_.m_123343_() + 0.5D, SoundEvents.f_11784_, SoundSource.BLOCKS, 0.5F + p_51290_.nextFloat(), p_51290_.nextFloat() * 0.7F + 0.6F, false);
         }

         if (this.f_51232_ && p_51290_.nextInt(5) == 0) {
            for(int i = 0; i < p_51290_.nextInt(1) + 1; ++i) {
               p_51288_.m_7106_(ParticleTypes.f_123756_, (double)p_51289_.m_123341_() + 0.5D, (double)p_51289_.m_123342_() + 0.5D, (double)p_51289_.m_123343_() + 0.5D, (double)(p_51290_.nextFloat() / 2.0F), 5.0E-5D, (double)(p_51290_.nextFloat() / 2.0F));
            }
         }

      }
   }

   public static void m_152749_(@Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
      if (p_152751_.m_5776_()) {
         for(int i = 0; i < 20; ++i) {
            m_51251_((Level)p_152751_, p_152752_, p_152753_.m_61143_(f_51228_), true);
         }
      }

      BlockEntity blockentity = p_152751_.m_7702_(p_152752_);
      if (blockentity instanceof CampfireBlockEntity) {
         ((CampfireBlockEntity)blockentity).m_59066_();
      }

      p_152751_.m_142346_(p_152750_, GameEvent.f_157792_, p_152752_);
   }

   public boolean m_7361_(LevelAccessor p_51257_, BlockPos p_51258_, BlockState p_51259_, FluidState p_51260_) {
      if (!p_51259_.m_61143_(BlockStateProperties.f_61362_) && p_51260_.m_76152_() == Fluids.f_76193_) {
         boolean flag = p_51259_.m_61143_(f_51227_);
         if (flag) {
            if (!p_51257_.m_5776_()) {
               p_51257_.m_5594_((Player)null, p_51258_, SoundEvents.f_11914_, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            m_152749_((Entity)null, p_51257_, p_51258_, p_51259_);
         }

         p_51257_.m_7731_(p_51258_, p_51259_.m_61124_(f_51229_, Boolean.valueOf(true)).m_61124_(f_51227_, Boolean.valueOf(false)), 3);
         p_51257_.m_6217_().m_5945_(p_51258_, p_51260_.m_76152_(), p_51260_.m_76152_().m_6718_(p_51257_));
         return true;
      } else {
         return false;
      }
   }

   public void m_5581_(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
      BlockPos blockpos = p_51246_.m_82425_();
      if (!p_51244_.f_46443_ && p_51247_.m_6060_() && p_51247_.m_142265_(p_51244_, blockpos) && !p_51245_.m_61143_(f_51227_) && !p_51245_.m_61143_(f_51229_)) {
         p_51244_.m_7731_(blockpos, p_51245_.m_61124_(BlockStateProperties.f_61443_, Boolean.valueOf(true)), 11);
      }

   }

   public static void m_51251_(Level p_51252_, BlockPos p_51253_, boolean p_51254_, boolean p_51255_) {
      Random random = p_51252_.m_5822_();
      SimpleParticleType simpleparticletype = p_51254_ ? ParticleTypes.f_123778_ : ParticleTypes.f_123777_;
      p_51252_.m_6485_(simpleparticletype, true, (double)p_51253_.m_123341_() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)p_51253_.m_123342_() + random.nextDouble() + random.nextDouble(), (double)p_51253_.m_123343_() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
      if (p_51255_) {
         p_51252_.m_7106_(ParticleTypes.f_123762_, (double)p_51253_.m_123341_() + 0.5D + random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1), (double)p_51253_.m_123342_() + 0.4D, (double)p_51253_.m_123343_() + 0.5D + random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
      }

   }

   public static boolean m_51248_(Level p_51249_, BlockPos p_51250_) {
      for(int i = 1; i <= 5; ++i) {
         BlockPos blockpos = p_51250_.m_6625_(i);
         BlockState blockstate = p_51249_.m_8055_(blockpos);
         if (m_51319_(blockstate)) {
            return true;
         }

         boolean flag = Shapes.m_83157_(f_51231_, blockstate.m_60742_(p_51249_, blockpos, CollisionContext.m_82749_()), BooleanOp.f_82689_);//Forge fix: MC-201374
         if (flag) {
            BlockState blockstate1 = p_51249_.m_8055_(blockpos.m_7495_());
            return m_51319_(blockstate1);
         }
      }

      return false;
   }

   public static boolean m_51319_(BlockState p_51320_) {
      return p_51320_.m_61138_(f_51227_) && p_51320_.m_60620_(BlockTags.f_13087_) && p_51320_.m_61143_(f_51227_);
   }

   public FluidState m_5888_(BlockState p_51318_) {
      return p_51318_.m_61143_(f_51229_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_51318_);
   }

   public BlockState m_6843_(BlockState p_51295_, Rotation p_51296_) {
      return p_51295_.m_61124_(f_51230_, p_51296_.m_55954_(p_51295_.m_61143_(f_51230_)));
   }

   public BlockState m_6943_(BlockState p_51292_, Mirror p_51293_) {
      return p_51292_.m_60717_(p_51293_.m_54846_(p_51292_.m_61143_(f_51230_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51305_) {
      p_51305_.m_61104_(f_51227_, f_51228_, f_51229_, f_51230_);
   }

   public BlockEntity m_142194_(BlockPos p_152759_, BlockState p_152760_) {
      return new CampfireBlockEntity(p_152759_, p_152760_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
      if (p_152755_.f_46443_) {
         return p_152756_.m_61143_(f_51227_) ? m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155318_) : null;
      } else {
         return p_152756_.m_61143_(f_51227_) ? m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155306_) : m_152132_(p_152757_, BlockEntityType.f_58911_, CampfireBlockEntity::m_155313_);
      }
   }

   public boolean m_7357_(BlockState p_51264_, BlockGetter p_51265_, BlockPos p_51266_, PathComputationType p_51267_) {
      return false;
   }

   public static boolean m_51321_(BlockState p_51322_) {
      return p_51322_.m_60622_(BlockTags.f_13087_, (p_51262_) -> {
         return p_51262_.m_61138_(f_51229_) && p_51262_.m_61138_(f_51227_);
      }) && !p_51322_.m_61143_(f_51229_) && !p_51322_.m_61143_(f_51227_);
   }
}
