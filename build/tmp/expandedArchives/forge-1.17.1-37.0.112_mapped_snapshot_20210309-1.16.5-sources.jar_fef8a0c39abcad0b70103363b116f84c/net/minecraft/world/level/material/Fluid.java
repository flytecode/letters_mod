package net.minecraft.world.level.material;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class Fluid extends net.minecraftforge.registries.ForgeRegistryEntry<Fluid> implements net.minecraftforge.common.extensions.IForgeFluid {
   public static final IdMapper<FluidState> f_76104_ = new IdMapper<>();
   protected final StateDefinition<Fluid, FluidState> f_76105_;
   private FluidState f_76103_;

   protected Fluid() {
      StateDefinition.Builder<Fluid, FluidState> builder = new StateDefinition.Builder<>(this);
      this.m_7180_(builder);
      this.f_76105_ = builder.m_61101_(Fluid::m_76145_, FluidState::new);
      this.m_76142_(this.f_76105_.m_61090_());
   }

   protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76121_) {
   }

   public StateDefinition<Fluid, FluidState> m_76144_() {
      return this.f_76105_;
   }

   protected final void m_76142_(FluidState p_76143_) {
      this.f_76103_ = p_76143_;
   }

   public final FluidState m_76145_() {
      return this.f_76103_;
   }

   public abstract Item m_6859_();

   protected void m_7450_(Level p_76116_, BlockPos p_76117_, FluidState p_76118_, Random p_76119_) {
   }

   protected void m_6292_(Level p_76113_, BlockPos p_76114_, FluidState p_76115_) {
   }

   protected void m_7449_(Level p_76132_, BlockPos p_76133_, FluidState p_76134_, Random p_76135_) {
   }

   @Nullable
   protected ParticleOptions m_7792_() {
      return null;
   }

   protected abstract boolean m_5486_(FluidState p_76127_, BlockGetter p_76128_, BlockPos p_76129_, Fluid p_76130_, Direction p_76131_);

   protected abstract Vec3 m_7000_(BlockGetter p_76110_, BlockPos p_76111_, FluidState p_76112_);

   public abstract int m_6718_(LevelReader p_76120_);

   protected boolean m_6685_() {
      return false;
   }

   protected boolean m_6759_() {
      return false;
   }

   protected abstract float m_6752_();

   public abstract float m_6098_(FluidState p_76124_, BlockGetter p_76125_, BlockPos p_76126_);

   public abstract float m_7427_(FluidState p_76123_);

   protected abstract BlockState m_5804_(FluidState p_76136_);

   public abstract boolean m_7444_(FluidState p_76140_);

   public abstract int m_7430_(FluidState p_76141_);

   public boolean m_6212_(Fluid p_76122_) {
      return p_76122_ == this;
   }

   public boolean m_76108_(Tag<Fluid> p_76109_) {
      return p_76109_.m_8110_(this);
   }

   public abstract VoxelShape m_7999_(FluidState p_76137_, BlockGetter p_76138_, BlockPos p_76139_);

   private final net.minecraftforge.common.util.ReverseTagWrapper<Fluid> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, net.minecraft.tags.FluidTags::m_144299_);
   @Override
   public java.util.Set<net.minecraft.resources.ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   /**
    * Creates the fluid attributes object, which will contain all the extended values for the fluid that aren't part of the vanilla system.
    * Do not call this from outside. To retrieve the values use {@link Fluid#getAttributes()}
    */
   protected net.minecraftforge.fluids.FluidAttributes createAttributes()
   {
      return net.minecraftforge.common.ForgeHooks.createVanillaFluidAttributes(this);
   }

   private net.minecraftforge.fluids.FluidAttributes forgeFluidAttributes;
   public final net.minecraftforge.fluids.FluidAttributes getAttributes() {
      if (forgeFluidAttributes == null)
         forgeFluidAttributes = createAttributes();
      return forgeFluidAttributes;
   }

   public Optional<SoundEvent> m_142520_() {
      return Optional.empty();
   }
}
