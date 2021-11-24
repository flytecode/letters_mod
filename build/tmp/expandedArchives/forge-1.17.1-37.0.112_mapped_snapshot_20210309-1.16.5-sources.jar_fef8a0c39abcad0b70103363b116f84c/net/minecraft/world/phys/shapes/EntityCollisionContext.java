package net.minecraft.world.phys.shapes;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class EntityCollisionContext implements CollisionContext {
   protected static final CollisionContext f_82865_ = new EntityCollisionContext(false, -Double.MAX_VALUE, ItemStack.f_41583_, ItemStack.f_41583_, (p_82891_) -> {
      return false;
   }, Optional.empty()) {
      public boolean m_6513_(VoxelShape p_82898_, BlockPos p_82899_, boolean p_82900_) {
         return p_82900_;
      }
   };
   private final boolean f_82866_;
   private final double f_82867_;
   private final ItemStack f_82868_;
   private final ItemStack f_166001_;
   private final Predicate<Fluid> f_82869_;
   private final Optional<Entity> f_166002_;

   protected EntityCollisionContext(boolean p_166004_, double p_166005_, ItemStack p_166006_, ItemStack p_166007_, Predicate<Fluid> p_166008_, Optional<Entity> p_166009_) {
      this.f_82866_ = p_166004_;
      this.f_82867_ = p_166005_;
      this.f_166001_ = p_166006_;
      this.f_82868_ = p_166007_;
      this.f_82869_ = p_166008_;
      this.f_166002_ = p_166009_;
   }

   @Deprecated
   protected EntityCollisionContext(Entity p_82872_) {
      this(p_82872_.m_20164_(), p_82872_.m_20186_(), p_82872_ instanceof LivingEntity ? ((LivingEntity)p_82872_).m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_, p_82872_ instanceof LivingEntity ? ((LivingEntity)p_82872_).m_21205_() : ItemStack.f_41583_, p_82872_ instanceof LivingEntity ? ((LivingEntity)p_82872_)::m_7479_ : (p_82881_) -> {
         return false;
      }, Optional.of(p_82872_));
   }

   public boolean m_142055_(Item p_166011_) {
      return this.f_166001_.m_150930_(p_166011_);
   }

   public boolean m_7142_(Item p_82879_) {
      return this.f_82868_.m_150930_(p_82879_);
   }

   public boolean m_7875_(FluidState p_82883_, FlowingFluid p_82884_) {
      return this.f_82869_.test(p_82884_) && !p_82883_.m_76152_().m_6212_(p_82884_);
   }

   public boolean m_6226_() {
      return this.f_82866_;
   }

   public boolean m_6513_(VoxelShape p_82886_, BlockPos p_82887_, boolean p_82888_) {
      return this.f_82867_ > (double)p_82887_.m_123342_() + p_82886_.m_83297_(Direction.Axis.Y) - (double)1.0E-5F;
   }

   public Optional<Entity> m_166012_() {
      return this.f_166002_;
   }
}