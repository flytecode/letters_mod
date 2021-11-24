package net.minecraft.world.level.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

public class PressurePlateBlock extends BasePressurePlateBlock {
   public static final BooleanProperty f_55249_ = BlockStateProperties.f_61448_;
   private final PressurePlateBlock.Sensitivity f_55250_;

   public PressurePlateBlock(PressurePlateBlock.Sensitivity p_55253_, BlockBehaviour.Properties p_55254_) {
      super(p_55254_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55249_, Boolean.valueOf(false)));
      this.f_55250_ = p_55253_;
   }

   protected int m_6016_(BlockState p_55270_) {
      return p_55270_.m_61143_(f_55249_) ? 15 : 0;
   }

   protected BlockState m_7422_(BlockState p_55259_, int p_55260_) {
      return p_55259_.m_61124_(f_55249_, Boolean.valueOf(p_55260_ > 0));
   }

   protected void m_5494_(LevelAccessor p_55256_, BlockPos p_55257_) {
      if (this.f_60442_ != Material.f_76320_ && this.f_60442_ != Material.f_76321_) {
         p_55256_.m_5594_((Player)null, p_55257_, SoundEvents.f_12449_, SoundSource.BLOCKS, 0.3F, 0.6F);
      } else {
         p_55256_.m_5594_((Player)null, p_55257_, SoundEvents.f_12637_, SoundSource.BLOCKS, 0.3F, 0.8F);
      }

   }

   protected void m_5493_(LevelAccessor p_55267_, BlockPos p_55268_) {
      if (this.f_60442_ != Material.f_76320_ && this.f_60442_ != Material.f_76321_) {
         p_55267_.m_5594_((Player)null, p_55268_, SoundEvents.f_12448_, SoundSource.BLOCKS, 0.3F, 0.5F);
      } else {
         p_55267_.m_5594_((Player)null, p_55268_, SoundEvents.f_12636_, SoundSource.BLOCKS, 0.3F, 0.7F);
      }

   }

   protected int m_6693_(Level p_55264_, BlockPos p_55265_) {
      AABB aabb = f_49287_.m_82338_(p_55265_);
      List<? extends Entity> list;
      switch(this.f_55250_) {
      case EVERYTHING:
         list = p_55264_.m_45933_((Entity)null, aabb);
         break;
      case MOBS:
         list = p_55264_.m_45976_(LivingEntity.class, aabb);
         break;
      default:
         return 0;
      }

      if (!list.isEmpty()) {
         for(Entity entity : list) {
            if (!entity.m_6090_()) {
               return 15;
            }
         }
      }

      return 0;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55262_) {
      p_55262_.m_61104_(f_55249_);
   }

   public static enum Sensitivity {
      EVERYTHING,
      MOBS;
   }
}