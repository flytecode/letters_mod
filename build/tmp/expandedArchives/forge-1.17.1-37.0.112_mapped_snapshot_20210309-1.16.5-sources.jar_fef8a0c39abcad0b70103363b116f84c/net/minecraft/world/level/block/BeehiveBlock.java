package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BeehiveBlock extends BaseEntityBlock {
   private static final Direction[] f_49565_ = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH};
   public static final DirectionProperty f_49563_ = HorizontalDirectionalBlock.f_54117_;
   public static final IntegerProperty f_49564_ = BlockStateProperties.f_61421_;
   public static final int f_152177_ = 5;
   private static final int f_152178_ = 3;

   public BeehiveBlock(BlockBehaviour.Properties p_49568_) {
      super(p_49568_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49564_, Integer.valueOf(0)).m_61124_(f_49563_, Direction.NORTH));
   }

   public boolean m_7278_(BlockState p_49618_) {
      return true;
   }

   public int m_6782_(BlockState p_49620_, Level p_49621_, BlockPos p_49622_) {
      return p_49620_.m_61143_(f_49564_);
   }
   // Forge: Fixed MC-227255 Beehives and bee nests do not rotate/mirror correctly in structure blocks
   @Override public BlockState m_6843_(BlockState blockState, net.minecraft.world.level.block.Rotation rotation) { return blockState.m_61124_(f_49563_, rotation.m_55954_(blockState.m_61143_(f_49563_))); }
   @Override public BlockState m_6943_(BlockState blockState, net.minecraft.world.level.block.Mirror mirror) { return blockState.m_60717_(mirror.m_54846_(blockState.m_61143_(f_49563_))); }

   public void m_6240_(Level p_49584_, Player p_49585_, BlockPos p_49586_, BlockState p_49587_, @Nullable BlockEntity p_49588_, ItemStack p_49589_) {
      super.m_6240_(p_49584_, p_49585_, p_49586_, p_49587_, p_49588_, p_49589_);
      if (!p_49584_.f_46443_ && p_49588_ instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)p_49588_;
         if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_49589_) == 0) {
            beehiveblockentity.m_58748_(p_49585_, p_49587_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            p_49584_.m_46717_(p_49586_, this);
            this.m_49649_(p_49584_, p_49586_);
         }

         CriteriaTriggers.f_10560_.m_146651_((ServerPlayer)p_49585_, p_49587_, p_49589_, beehiveblockentity.m_58776_());
      }

   }

   private void m_49649_(Level p_49650_, BlockPos p_49651_) {
      List<Bee> list = p_49650_.m_45976_(Bee.class, (new AABB(p_49651_)).m_82377_(8.0D, 6.0D, 8.0D));
      if (!list.isEmpty()) {
         List<Player> list1 = p_49650_.m_45976_(Player.class, (new AABB(p_49651_)).m_82377_(8.0D, 6.0D, 8.0D));
         if (list1.isEmpty()) return; //Forge: Prevent Error when no players are around.
         int i = list1.size();

         for(Bee bee : list) {
            if (bee.m_5448_() == null) {
               bee.m_6710_(list1.get(p_49650_.f_46441_.nextInt(i)));
            }
         }
      }

   }

   public static void m_49600_(Level p_49601_, BlockPos p_49602_) {
      m_49840_(p_49601_, p_49602_, new ItemStack(Items.f_42784_, 3));
   }

   public InteractionResult m_6227_(BlockState p_49624_, Level p_49625_, BlockPos p_49626_, Player p_49627_, InteractionHand p_49628_, BlockHitResult p_49629_) {
      ItemStack itemstack = p_49627_.m_21120_(p_49628_);
      int i = p_49624_.m_61143_(f_49564_);
      boolean flag = false;
      if (i >= 5) {
         Item item = itemstack.m_41720_();
         if (itemstack.canPerformAction(net.minecraftforge.common.ToolActions.SHEARS_HARVEST)) {
            p_49625_.m_6263_(p_49627_, p_49627_.m_20185_(), p_49627_.m_20186_(), p_49627_.m_20189_(), SoundEvents.f_11697_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            m_49600_(p_49625_, p_49626_);
            itemstack.m_41622_(1, p_49627_, (p_49571_) -> {
               p_49571_.m_21190_(p_49628_);
            });
            flag = true;
            p_49625_.m_142346_(p_49627_, GameEvent.f_157781_, p_49626_);
         } else if (itemstack.m_150930_(Items.f_42590_)) {
            itemstack.m_41774_(1);
            p_49625_.m_6263_(p_49627_, p_49627_.m_20185_(), p_49627_.m_20186_(), p_49627_.m_20189_(), SoundEvents.f_11770_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (itemstack.m_41619_()) {
               p_49627_.m_21008_(p_49628_, new ItemStack(Items.f_42787_));
            } else if (!p_49627_.m_150109_().m_36054_(new ItemStack(Items.f_42787_))) {
               p_49627_.m_36176_(new ItemStack(Items.f_42787_), false);
            }

            flag = true;
            p_49625_.m_142346_(p_49627_, GameEvent.f_157816_, p_49626_);
         }

         if (!p_49625_.m_5776_() && flag) {
            p_49627_.m_36246_(Stats.f_12982_.m_12902_(item));
         }
      }

      if (flag) {
         if (!CampfireBlock.m_51248_(p_49625_, p_49626_)) {
            if (this.m_49654_(p_49625_, p_49626_)) {
               this.m_49649_(p_49625_, p_49626_);
            }

            this.m_49594_(p_49625_, p_49624_, p_49626_, p_49627_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         } else {
            this.m_49590_(p_49625_, p_49624_, p_49626_);
         }

         return InteractionResult.m_19078_(p_49625_.f_46443_);
      } else {
         return super.m_6227_(p_49624_, p_49625_, p_49626_, p_49627_, p_49628_, p_49629_);
      }
   }

   private boolean m_49654_(Level p_49655_, BlockPos p_49656_) {
      BlockEntity blockentity = p_49655_.m_7702_(p_49656_);
      if (blockentity instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
         return !beehiveblockentity.m_58774_();
      } else {
         return false;
      }
   }

   public void m_49594_(Level p_49595_, BlockState p_49596_, BlockPos p_49597_, @Nullable Player p_49598_, BeehiveBlockEntity.BeeReleaseStatus p_49599_) {
      this.m_49590_(p_49595_, p_49596_, p_49597_);
      BlockEntity blockentity = p_49595_.m_7702_(p_49597_);
      if (blockentity instanceof BeehiveBlockEntity) {
         BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
         beehiveblockentity.m_58748_(p_49598_, p_49596_, p_49599_);
      }

   }

   public void m_49590_(Level p_49591_, BlockState p_49592_, BlockPos p_49593_) {
      p_49591_.m_7731_(p_49593_, p_49592_.m_61124_(f_49564_, Integer.valueOf(0)), 3);
   }

   public void m_7100_(BlockState p_49631_, Level p_49632_, BlockPos p_49633_, Random p_49634_) {
      if (p_49631_.m_61143_(f_49564_) >= 5) {
         for(int i = 0; i < p_49634_.nextInt(1) + 1; ++i) {
            this.m_49603_(p_49632_, p_49633_, p_49631_);
         }
      }

   }

   private void m_49603_(Level p_49604_, BlockPos p_49605_, BlockState p_49606_) {
      if (p_49606_.m_60819_().m_76178_() && !(p_49604_.f_46441_.nextFloat() < 0.3F)) {
         VoxelShape voxelshape = p_49606_.m_60812_(p_49604_, p_49605_);
         double d0 = voxelshape.m_83297_(Direction.Axis.Y);
         if (d0 >= 1.0D && !p_49606_.m_60620_(BlockTags.f_13049_)) {
            double d1 = voxelshape.m_83288_(Direction.Axis.Y);
            if (d1 > 0.0D) {
               this.m_49612_(p_49604_, p_49605_, voxelshape, (double)p_49605_.m_123342_() + d1 - 0.05D);
            } else {
               BlockPos blockpos = p_49605_.m_7495_();
               BlockState blockstate = p_49604_.m_8055_(blockpos);
               VoxelShape voxelshape1 = blockstate.m_60812_(p_49604_, blockpos);
               double d2 = voxelshape1.m_83297_(Direction.Axis.Y);
               if ((d2 < 1.0D || !blockstate.m_60838_(p_49604_, blockpos)) && blockstate.m_60819_().m_76178_()) {
                  this.m_49612_(p_49604_, p_49605_, voxelshape, (double)p_49605_.m_123342_() - 0.05D);
               }
            }
         }

      }
   }

   private void m_49612_(Level p_49613_, BlockPos p_49614_, VoxelShape p_49615_, double p_49616_) {
      this.m_49576_(p_49613_, (double)p_49614_.m_123341_() + p_49615_.m_83288_(Direction.Axis.X), (double)p_49614_.m_123341_() + p_49615_.m_83297_(Direction.Axis.X), (double)p_49614_.m_123343_() + p_49615_.m_83288_(Direction.Axis.Z), (double)p_49614_.m_123343_() + p_49615_.m_83297_(Direction.Axis.Z), p_49616_);
   }

   private void m_49576_(Level p_49577_, double p_49578_, double p_49579_, double p_49580_, double p_49581_, double p_49582_) {
      p_49577_.m_7106_(ParticleTypes.f_123779_, Mth.m_14139_(p_49577_.f_46441_.nextDouble(), p_49578_, p_49579_), p_49582_, Mth.m_14139_(p_49577_.f_46441_.nextDouble(), p_49580_, p_49581_), 0.0D, 0.0D, 0.0D);
   }

   public BlockState m_5573_(BlockPlaceContext p_49573_) {
      return this.m_49966_().m_61124_(f_49563_, p_49573_.m_8125_().m_122424_());
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49646_) {
      p_49646_.m_61104_(f_49564_, f_49563_);
   }

   public RenderShape m_7514_(BlockState p_49653_) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos p_152184_, BlockState p_152185_) {
      return new BeehiveBlockEntity(p_152184_, p_152185_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return p_152180_.f_46443_ ? null : m_152132_(p_152182_, BlockEntityType.f_58912_, BeehiveBlockEntity::m_155144_);
   }

   public void m_5707_(Level p_49608_, BlockPos p_49609_, BlockState p_49610_, Player p_49611_) {
      if (!p_49608_.f_46443_ && p_49611_.m_7500_() && p_49608_.m_46469_().m_46207_(GameRules.f_46136_)) {
         BlockEntity blockentity = p_49608_.m_7702_(p_49609_);
         if (blockentity instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
            ItemStack itemstack = new ItemStack(this);
            int i = p_49610_.m_61143_(f_49564_);
            boolean flag = !beehiveblockentity.m_58774_();
            if (flag || i > 0) {
               if (flag) {
                  CompoundTag compoundtag = new CompoundTag();
                  compoundtag.m_128365_("Bees", beehiveblockentity.m_58779_());
                  itemstack.m_41700_("BlockEntityTag", compoundtag);
               }

               CompoundTag compoundtag1 = new CompoundTag();
               compoundtag1.m_128405_("honey_level", i);
               itemstack.m_41700_("BlockStateTag", compoundtag1);
               ItemEntity itementity = new ItemEntity(p_49608_, (double)p_49609_.m_123341_(), (double)p_49609_.m_123342_(), (double)p_49609_.m_123343_(), itemstack);
               itementity.m_32060_();
               p_49608_.m_7967_(itementity);
            }
         }
      }

      super.m_5707_(p_49608_, p_49609_, p_49610_, p_49611_);
   }

   public List<ItemStack> m_7381_(BlockState p_49636_, LootContext.Builder p_49637_) {
      Entity entity = p_49637_.m_78982_(LootContextParams.f_81455_);
      if (entity instanceof PrimedTnt || entity instanceof Creeper || entity instanceof WitherSkull || entity instanceof WitherBoss || entity instanceof MinecartTNT) {
         BlockEntity blockentity = p_49637_.m_78982_(LootContextParams.f_81462_);
         if (blockentity instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
            beehiveblockentity.m_58748_((Player)null, p_49636_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         }
      }

      return super.m_7381_(p_49636_, p_49637_);
   }

   public BlockState m_7417_(BlockState p_49639_, Direction p_49640_, BlockState p_49641_, LevelAccessor p_49642_, BlockPos p_49643_, BlockPos p_49644_) {
      if (p_49642_.m_8055_(p_49644_).m_60734_() instanceof FireBlock) {
         BlockEntity blockentity = p_49642_.m_7702_(p_49643_);
         if (blockentity instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity beehiveblockentity = (BeehiveBlockEntity)blockentity;
            beehiveblockentity.m_58748_((Player)null, p_49639_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
         }
      }

      return super.m_7417_(p_49639_, p_49640_, p_49641_, p_49642_, p_49643_, p_49644_);
   }

   public static Direction m_49647_(Random p_49648_) {
      return Util.m_137545_(f_49565_, p_49648_);
   }
}
