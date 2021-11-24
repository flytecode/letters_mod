package net.minecraft.world.item;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BoneMealItem extends Item {
   public static final int f_150701_ = 3;
   public static final int f_150702_ = 1;
   public static final int f_150703_ = 3;

   public BoneMealItem(Item.Properties p_40626_) {
      super(p_40626_);
   }

   public InteractionResult m_6225_(UseOnContext p_40637_) {
      Level level = p_40637_.m_43725_();
      BlockPos blockpos = p_40637_.m_8083_();
      BlockPos blockpos1 = blockpos.m_142300_(p_40637_.m_43719_());
      if (applyBonemeal(p_40637_.m_43722_(), level, blockpos, p_40637_.m_43723_())) {
         if (!level.f_46443_) {
            level.m_46796_(1505, blockpos, 0);
         }

         return InteractionResult.m_19078_(level.f_46443_);
      } else {
         BlockState blockstate = level.m_8055_(blockpos);
         boolean flag = blockstate.m_60783_(level, blockpos, p_40637_.m_43719_());
         if (flag && m_40631_(p_40637_.m_43722_(), level, blockpos1, p_40637_.m_43719_())) {
            if (!level.f_46443_) {
               level.m_46796_(1505, blockpos1, 0);
            }

            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   @Deprecated //Forge: Use Player/Hand version
   public static boolean m_40627_(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_) {
      if (p_40629_ instanceof net.minecraft.server.level.ServerLevel)
         return applyBonemeal(p_40628_, p_40629_, p_40630_, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.server.level.ServerLevel)p_40629_));
      return false;
   }

   public static boolean applyBonemeal(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_, net.minecraft.world.entity.player.Player player) {
      BlockState blockstate = p_40629_.m_8055_(p_40630_);
      int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, p_40629_, p_40630_, blockstate, p_40628_);
      if (hook != 0) return hook > 0;
      if (blockstate.m_60734_() instanceof BonemealableBlock) {
         BonemealableBlock bonemealableblock = (BonemealableBlock)blockstate.m_60734_();
         if (bonemealableblock.m_7370_(p_40629_, p_40630_, blockstate, p_40629_.f_46443_)) {
            if (p_40629_ instanceof ServerLevel) {
               if (bonemealableblock.m_5491_(p_40629_, p_40629_.f_46441_, p_40630_, blockstate)) {
                  bonemealableblock.m_7719_((ServerLevel)p_40629_, p_40629_.f_46441_, p_40630_, blockstate);
               }

               p_40628_.m_41774_(1);
            }

            return true;
         }
      }

      return false;
   }

   public static boolean m_40631_(ItemStack p_40632_, Level p_40633_, BlockPos p_40634_, @Nullable Direction p_40635_) {
      if (p_40633_.m_8055_(p_40634_).m_60713_(Blocks.f_49990_) && p_40633_.m_6425_(p_40634_).m_76186_() == 8) {
         if (!(p_40633_ instanceof ServerLevel)) {
            return true;
         } else {
            Random random = p_40633_.m_5822_();

            label80:
            for(int i = 0; i < 128; ++i) {
               BlockPos blockpos = p_40634_;
               BlockState blockstate = Blocks.f_50037_.m_49966_();

               for(int j = 0; j < i / 16; ++j) {
                  blockpos = blockpos.m_142082_(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                  if (p_40633_.m_8055_(blockpos).m_60838_(p_40633_, blockpos)) {
                     continue label80;
                  }
               }

               Optional<ResourceKey<Biome>> optional = p_40633_.m_45837_(blockpos);
               if (Objects.equals(optional, Optional.of(Biomes.f_48166_)) || Objects.equals(optional, Optional.of(Biomes.f_48169_))) {
                  if (i == 0 && p_40635_ != null && p_40635_.m_122434_().m_122479_()) {
                     blockstate = BlockTags.f_13052_.m_13288_(p_40633_.f_46441_).m_49966_().m_61124_(BaseCoralWallFanBlock.f_49192_, p_40635_);
                  } else if (random.nextInt(4) == 0) {
                     blockstate = BlockTags.f_13050_.m_13288_(random).m_49966_();
                  }
               }

               if (blockstate.m_60620_(BlockTags.f_13052_)) {
                  for(int k = 0; !blockstate.m_60710_(p_40633_, blockpos) && k < 4; ++k) {
                     blockstate = blockstate.m_61124_(BaseCoralWallFanBlock.f_49192_, Direction.Plane.HORIZONTAL.m_122560_(random));
                  }
               }

               if (blockstate.m_60710_(p_40633_, blockpos)) {
                  BlockState blockstate1 = p_40633_.m_8055_(blockpos);
                  if (blockstate1.m_60713_(Blocks.f_49990_) && p_40633_.m_6425_(blockpos).m_76186_() == 8) {
                     p_40633_.m_7731_(blockpos, blockstate, 3);
                  } else if (blockstate1.m_60713_(Blocks.f_50037_) && random.nextInt(10) == 0) {
                     ((BonemealableBlock)Blocks.f_50037_).m_7719_((ServerLevel)p_40633_, random, blockpos, blockstate1);
                  }
               }
            }

            p_40632_.m_41774_(1);
            return true;
         }
      } else {
         return false;
      }
   }

   public static void m_40638_(LevelAccessor p_40639_, BlockPos p_40640_, int p_40641_) {
      if (p_40641_ == 0) {
         p_40641_ = 15;
      }

      BlockState blockstate = p_40639_.m_8055_(p_40640_);
      if (!blockstate.m_60795_()) {
         double d0 = 0.5D;
         double d1;
         if (blockstate.m_60713_(Blocks.f_49990_)) {
            p_40641_ *= 3;
            d1 = 1.0D;
            d0 = 3.0D;
         } else if (blockstate.m_60804_(p_40639_, p_40640_)) {
            p_40640_ = p_40640_.m_7494_();
            p_40641_ *= 3;
            d0 = 3.0D;
            d1 = 1.0D;
         } else {
            d1 = blockstate.m_60808_(p_40639_, p_40640_).m_83297_(Direction.Axis.Y);
         }

         p_40639_.m_7106_(ParticleTypes.f_123748_, (double)p_40640_.m_123341_() + 0.5D, (double)p_40640_.m_123342_() + 0.5D, (double)p_40640_.m_123343_() + 0.5D, 0.0D, 0.0D, 0.0D);
         Random random = p_40639_.m_5822_();

         for(int i = 0; i < p_40641_; ++i) {
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = random.nextGaussian() * 0.02D;
            double d4 = random.nextGaussian() * 0.02D;
            double d5 = 0.5D - d0;
            double d6 = (double)p_40640_.m_123341_() + d5 + random.nextDouble() * d0 * 2.0D;
            double d7 = (double)p_40640_.m_123342_() + random.nextDouble() * d1;
            double d8 = (double)p_40640_.m_123343_() + d5 + random.nextDouble() * d0 * 2.0D;
            if (!p_40639_.m_8055_((new BlockPos(d6, d7, d8)).m_7495_()).m_60795_()) {
               p_40639_.m_7106_(ParticleTypes.f_123748_, d6, d7, d8, d2, d3, d4);
            }
         }

      }
   }
}
