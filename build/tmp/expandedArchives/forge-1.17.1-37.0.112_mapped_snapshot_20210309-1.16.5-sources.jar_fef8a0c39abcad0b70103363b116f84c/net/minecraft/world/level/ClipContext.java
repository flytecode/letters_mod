package net.minecraft.world.level;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClipContext {
   private final Vec3 f_45682_;
   private final Vec3 f_45683_;
   private final ClipContext.Block f_45684_;
   private final ClipContext.Fluid f_45685_;
   private final CollisionContext f_45686_;

   public ClipContext(Vec3 p_45688_, Vec3 p_45689_, ClipContext.Block p_45690_, ClipContext.Fluid p_45691_, @javax.annotation.Nullable Entity p_45692_) {
      this.f_45682_ = p_45688_;
      this.f_45683_ = p_45689_;
      this.f_45684_ = p_45690_;
      this.f_45685_ = p_45691_;
      this.f_45686_ = p_45692_ == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(p_45692_);
   }

   public Vec3 m_45693_() {
      return this.f_45683_;
   }

   public Vec3 m_45702_() {
      return this.f_45682_;
   }

   public VoxelShape m_45694_(BlockState p_45695_, BlockGetter p_45696_, BlockPos p_45697_) {
      return this.f_45684_.m_7544_(p_45695_, p_45696_, p_45697_, this.f_45686_);
   }

   public VoxelShape m_45698_(FluidState p_45699_, BlockGetter p_45700_, BlockPos p_45701_) {
      return this.f_45685_.m_45731_(p_45699_) ? p_45699_.m_76183_(p_45700_, p_45701_) : Shapes.m_83040_();
   }

   public static enum Block implements ClipContext.ShapeGetter {
      COLLIDER(BlockBehaviour.BlockStateBase::m_60742_),
      OUTLINE(BlockBehaviour.BlockStateBase::m_60651_),
      VISUAL(BlockBehaviour.BlockStateBase::m_60771_);

      private final ClipContext.ShapeGetter f_45706_;

      private Block(ClipContext.ShapeGetter p_45712_) {
         this.f_45706_ = p_45712_;
      }

      public VoxelShape m_7544_(BlockState p_45714_, BlockGetter p_45715_, BlockPos p_45716_, CollisionContext p_45717_) {
         return this.f_45706_.m_7544_(p_45714_, p_45715_, p_45716_, p_45717_);
      }
   }

   public static enum Fluid {
      NONE((p_45736_) -> {
         return false;
      }),
      SOURCE_ONLY(FluidState::m_76170_),
      ANY((p_45734_) -> {
         return !p_45734_.m_76178_();
      });

      private final Predicate<FluidState> f_45724_;

      private Fluid(Predicate<FluidState> p_45730_) {
         this.f_45724_ = p_45730_;
      }

      public boolean m_45731_(FluidState p_45732_) {
         return this.f_45724_.test(p_45732_);
      }
   }

   public interface ShapeGetter {
      VoxelShape m_7544_(BlockState p_45740_, BlockGetter p_45741_, BlockPos p_45742_, CollisionContext p_45743_);
   }
}
