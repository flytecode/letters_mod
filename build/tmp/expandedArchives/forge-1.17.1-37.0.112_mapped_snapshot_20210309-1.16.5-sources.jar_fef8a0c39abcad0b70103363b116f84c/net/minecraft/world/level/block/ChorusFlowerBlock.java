package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChorusFlowerBlock extends Block {
   public static final int f_153067_ = 5;
   public static final IntegerProperty f_51647_ = BlockStateProperties.f_61408_;
   private final ChorusPlantBlock f_51648_;

   public ChorusFlowerBlock(ChorusPlantBlock p_51651_, BlockBehaviour.Properties p_51652_) {
      super(p_51652_);
      this.f_51648_ = p_51651_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51647_, Integer.valueOf(0)));
   }

   public void m_7458_(BlockState p_51678_, ServerLevel p_51679_, BlockPos p_51680_, Random p_51681_) {
      if (!p_51678_.m_60710_(p_51679_, p_51680_)) {
         p_51679_.m_46961_(p_51680_, true);
      }

   }

   public boolean m_6724_(BlockState p_51696_) {
      return p_51696_.m_61143_(f_51647_) < 5;
   }

   public void m_7455_(BlockState p_51702_, ServerLevel p_51703_, BlockPos p_51704_, Random p_51705_) {
      BlockPos blockpos = p_51704_.m_7494_();
      if (p_51703_.m_46859_(blockpos) && blockpos.m_123342_() < p_51703_.m_151558_()) {
         int i = p_51702_.m_61143_(f_51647_);
         if (i < 5 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_51703_, blockpos, p_51702_, true)) {
            boolean flag = false;
            boolean flag1 = false;
            BlockState blockstate = p_51703_.m_8055_(p_51704_.m_7495_());
            if (blockstate.m_60713_(Blocks.f_50259_)) {
               flag = true;
            } else if (blockstate.m_60713_(this.f_51648_)) {
               int j = 1;

               for(int k = 0; k < 4; ++k) {
                  BlockState blockstate1 = p_51703_.m_8055_(p_51704_.m_6625_(j + 1));
                  if (!blockstate1.m_60713_(this.f_51648_)) {
                     if (blockstate1.m_60713_(Blocks.f_50259_)) {
                        flag1 = true;
                     }
                     break;
                  }

                  ++j;
               }

               if (j < 2 || j <= p_51705_.nextInt(flag1 ? 5 : 4)) {
                  flag = true;
               }
            } else if (blockstate.m_60795_()) {
               flag = true;
            }

            if (flag && m_51697_(p_51703_, blockpos, (Direction)null) && p_51703_.m_46859_(p_51704_.m_6630_(2))) {
               p_51703_.m_7731_(p_51704_, this.f_51648_.m_51710_(p_51703_, p_51704_), 2);
               this.m_51661_(p_51703_, blockpos, i);
            } else if (i < 4) {
               int l = p_51705_.nextInt(4);
               if (flag1) {
                  ++l;
               }

               boolean flag2 = false;

               for(int i1 = 0; i1 < l; ++i1) {
                  Direction direction = Direction.Plane.HORIZONTAL.m_122560_(p_51705_);
                  BlockPos blockpos1 = p_51704_.m_142300_(direction);
                  if (p_51703_.m_46859_(blockpos1) && p_51703_.m_46859_(blockpos1.m_7495_()) && m_51697_(p_51703_, blockpos1, direction.m_122424_())) {
                     this.m_51661_(p_51703_, blockpos1, i + 1);
                     flag2 = true;
                  }
               }

               if (flag2) {
                  p_51703_.m_7731_(p_51704_, this.f_51648_.m_51710_(p_51703_, p_51704_), 2);
               } else {
                  this.m_51658_(p_51703_, p_51704_);
               }
            } else {
               this.m_51658_(p_51703_, p_51704_);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_51703_, p_51704_, p_51702_);
         }
      }
   }

   private void m_51661_(Level p_51662_, BlockPos p_51663_, int p_51664_) {
      p_51662_.m_7731_(p_51663_, this.m_49966_().m_61124_(f_51647_, Integer.valueOf(p_51664_)), 2);
      p_51662_.m_46796_(1033, p_51663_, 0);
   }

   private void m_51658_(Level p_51659_, BlockPos p_51660_) {
      p_51659_.m_7731_(p_51660_, this.m_49966_().m_61124_(f_51647_, Integer.valueOf(5)), 2);
      p_51659_.m_46796_(1034, p_51660_, 0);
   }

   private static boolean m_51697_(LevelReader p_51698_, BlockPos p_51699_, @Nullable Direction p_51700_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (direction != p_51700_ && !p_51698_.m_46859_(p_51699_.m_142300_(direction))) {
            return false;
         }
      }

      return true;
   }

   public BlockState m_7417_(BlockState p_51687_, Direction p_51688_, BlockState p_51689_, LevelAccessor p_51690_, BlockPos p_51691_, BlockPos p_51692_) {
      if (p_51688_ != Direction.UP && !p_51687_.m_60710_(p_51690_, p_51691_)) {
         p_51690_.m_6219_().m_5945_(p_51691_, this, 1);
      }

      return super.m_7417_(p_51687_, p_51688_, p_51689_, p_51690_, p_51691_, p_51692_);
   }

   public boolean m_7898_(BlockState p_51683_, LevelReader p_51684_, BlockPos p_51685_) {
      BlockState blockstate = p_51684_.m_8055_(p_51685_.m_7495_());
      if (!blockstate.m_60713_(this.f_51648_) && !blockstate.m_60713_(Blocks.f_50259_)) {
         if (!blockstate.m_60795_()) {
            return false;
         } else {
            boolean flag = false;

            for(Direction direction : Direction.Plane.HORIZONTAL) {
               BlockState blockstate1 = p_51684_.m_8055_(p_51685_.m_142300_(direction));
               if (blockstate1.m_60713_(this.f_51648_)) {
                  if (flag) {
                     return false;
                  }

                  flag = true;
               } else if (!blockstate1.m_60795_()) {
                  return false;
               }
            }

            return flag;
         }
      } else {
         return true;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51694_) {
      p_51694_.m_61104_(f_51647_);
   }

   public static void m_51665_(LevelAccessor p_51666_, BlockPos p_51667_, Random p_51668_, int p_51669_) {
      p_51666_.m_7731_(p_51667_, ((ChorusPlantBlock)Blocks.f_50490_).m_51710_(p_51666_, p_51667_), 2);
      m_51670_(p_51666_, p_51667_, p_51668_, p_51667_, p_51669_, 0);
   }

   private static void m_51670_(LevelAccessor p_51671_, BlockPos p_51672_, Random p_51673_, BlockPos p_51674_, int p_51675_, int p_51676_) {
      ChorusPlantBlock chorusplantblock = (ChorusPlantBlock)Blocks.f_50490_;
      int i = p_51673_.nextInt(4) + 1;
      if (p_51676_ == 0) {
         ++i;
      }

      for(int j = 0; j < i; ++j) {
         BlockPos blockpos = p_51672_.m_6630_(j + 1);
         if (!m_51697_(p_51671_, blockpos, (Direction)null)) {
            return;
         }

         p_51671_.m_7731_(blockpos, chorusplantblock.m_51710_(p_51671_, blockpos), 2);
         p_51671_.m_7731_(blockpos.m_7495_(), chorusplantblock.m_51710_(p_51671_, blockpos.m_7495_()), 2);
      }

      boolean flag = false;
      if (p_51676_ < 4) {
         int l = p_51673_.nextInt(4);
         if (p_51676_ == 0) {
            ++l;
         }

         for(int k = 0; k < l; ++k) {
            Direction direction = Direction.Plane.HORIZONTAL.m_122560_(p_51673_);
            BlockPos blockpos1 = p_51672_.m_6630_(i).m_142300_(direction);
            if (Math.abs(blockpos1.m_123341_() - p_51674_.m_123341_()) < p_51675_ && Math.abs(blockpos1.m_123343_() - p_51674_.m_123343_()) < p_51675_ && p_51671_.m_46859_(blockpos1) && p_51671_.m_46859_(blockpos1.m_7495_()) && m_51697_(p_51671_, blockpos1, direction.m_122424_())) {
               flag = true;
               p_51671_.m_7731_(blockpos1, chorusplantblock.m_51710_(p_51671_, blockpos1), 2);
               p_51671_.m_7731_(blockpos1.m_142300_(direction.m_122424_()), chorusplantblock.m_51710_(p_51671_, blockpos1.m_142300_(direction.m_122424_())), 2);
               m_51670_(p_51671_, blockpos1, p_51673_, p_51674_, p_51675_, p_51676_ + 1);
            }
         }
      }

      if (!flag) {
         p_51671_.m_7731_(p_51672_.m_6630_(i), Blocks.f_50491_.m_49966_().m_61124_(f_51647_, Integer.valueOf(5)), 2);
      }

   }

   public void m_5581_(Level p_51654_, BlockState p_51655_, BlockHitResult p_51656_, Projectile p_51657_) {
      BlockPos blockpos = p_51656_.m_82425_();
      if (!p_51654_.f_46443_ && p_51657_.m_142265_(p_51654_, blockpos) && p_51657_.m_6095_().m_20609_(EntityTypeTags.f_13124_)) {
         p_51654_.m_46953_(blockpos, true, p_51657_);
      }

   }
}
