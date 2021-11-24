package net.minecraft.world.level.material;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class FluidState extends StateHolder<Fluid, FluidState> implements net.minecraftforge.common.extensions.IForgeFluidState {
   public static final Codec<FluidState> f_76146_ = m_61127_(Registry.f_122822_, Fluid::m_76145_).stable();
   public static final int f_164510_ = 9;
   public static final int f_164511_ = 8;

   public FluidState(Fluid p_76149_, ImmutableMap<Property<?>, Comparable<?>> p_76150_, MapCodec<FluidState> p_76151_) {
      super(p_76149_, p_76150_, p_76151_);
   }

   public Fluid m_76152_() {
      return this.f_61112_;
   }

   public boolean m_76170_() {
      return this.m_76152_().m_7444_(this);
   }

   public boolean m_164512_(Fluid p_164513_) {
      return this.f_61112_ == p_164513_ && this.f_61112_.m_7444_(this);
   }

   public boolean m_76178_() {
      return this.m_76152_().m_6759_();
   }

   public float m_76155_(BlockGetter p_76156_, BlockPos p_76157_) {
      return this.m_76152_().m_6098_(this, p_76156_, p_76157_);
   }

   public float m_76182_() {
      return this.m_76152_().m_7427_(this);
   }

   public int m_76186_() {
      return this.m_76152_().m_7430_(this);
   }

   public boolean m_76171_(BlockGetter p_76172_, BlockPos p_76173_) {
      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            BlockPos blockpos = p_76173_.m_142082_(i, 0, j);
            FluidState fluidstate = p_76172_.m_6425_(blockpos);
            if (!fluidstate.m_76152_().m_6212_(this.m_76152_()) && !p_76172_.m_8055_(blockpos).m_60804_(p_76172_, blockpos)) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_76163_(Level p_76164_, BlockPos p_76165_) {
      this.m_76152_().m_6292_(p_76164_, p_76165_, this);
   }

   public void m_76166_(Level p_76167_, BlockPos p_76168_, Random p_76169_) {
      this.m_76152_().m_7450_(p_76167_, p_76168_, this, p_76169_);
   }

   public boolean m_76187_() {
      return this.m_76152_().m_6685_();
   }

   public void m_76174_(Level p_76175_, BlockPos p_76176_, Random p_76177_) {
      this.m_76152_().m_7449_(p_76175_, p_76176_, this, p_76177_);
   }

   public Vec3 m_76179_(BlockGetter p_76180_, BlockPos p_76181_) {
      return this.m_76152_().m_7000_(p_76180_, p_76181_, this);
   }

   public BlockState m_76188_() {
      return this.m_76152_().m_5804_(this);
   }

   @Nullable
   public ParticleOptions m_76189_() {
      return this.m_76152_().m_7792_();
   }

   public boolean m_76153_(Tag<Fluid> p_76154_) {
      return this.m_76152_().m_76108_(p_76154_);
   }

   @Deprecated //Forge: Use more sensitive version
   public float m_76190_() {
      return this.m_76152_().m_6752_();
   }

   public boolean m_76158_(BlockGetter p_76159_, BlockPos p_76160_, Fluid p_76161_, Direction p_76162_) {
      return this.m_76152_().m_5486_(this, p_76159_, p_76160_, p_76161_, p_76162_);
   }

   public VoxelShape m_76183_(BlockGetter p_76184_, BlockPos p_76185_) {
      return this.m_76152_().m_7999_(this, p_76184_, p_76185_);
   }
}
