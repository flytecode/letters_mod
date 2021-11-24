package net.minecraft.world.damagesource;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CombatTracker {
   public static final int f_146694_ = 100;
   public static final int f_146695_ = 300;
   private final List<CombatEntry> f_19276_ = Lists.newArrayList();
   private final LivingEntity f_19277_;
   private int f_19278_;
   private int f_19279_;
   private int f_19280_;
   private boolean f_19281_;
   private boolean f_19282_;
   @Nullable
   private String f_19283_;

   public CombatTracker(LivingEntity p_19285_) {
      this.f_19277_ = p_19285_;
   }

   public void m_19286_() {
      this.m_19299_();
      Optional<BlockPos> optional = this.f_19277_.m_21227_();
      if (optional.isPresent()) {
         BlockState blockstate = this.f_19277_.f_19853_.m_8055_(optional.get());
         if (!blockstate.m_60713_(Blocks.f_50155_) && !blockstate.m_60620_(BlockTags.f_13036_)) {
            if (blockstate.m_60713_(Blocks.f_50191_)) {
               this.f_19283_ = "vines";
            } else if (!blockstate.m_60713_(Blocks.f_50702_) && !blockstate.m_60713_(Blocks.f_50703_)) {
               if (!blockstate.m_60713_(Blocks.f_50704_) && !blockstate.m_60713_(Blocks.f_50653_)) {
                  if (blockstate.m_60713_(Blocks.f_50616_)) {
                     this.f_19283_ = "scaffolding";
                  } else {
                     this.f_19283_ = "other_climbable";
                  }
               } else {
                  this.f_19283_ = "twisting_vines";
               }
            } else {
               this.f_19283_ = "weeping_vines";
            }
         } else {
            this.f_19283_ = "ladder";
         }
      } else if (this.f_19277_.m_20069_()) {
         this.f_19283_ = "water";
      }

   }

   public void m_19289_(DamageSource p_19290_, float p_19291_, float p_19292_) {
      this.m_19296_();
      this.m_19286_();
      CombatEntry combatentry = new CombatEntry(p_19290_, this.f_19277_.f_19797_, p_19291_, p_19292_, this.f_19283_, this.f_19277_.f_19789_);
      this.f_19276_.add(combatentry);
      this.f_19278_ = this.f_19277_.f_19797_;
      this.f_19282_ = true;
      if (combatentry.m_19265_() && !this.f_19281_ && this.f_19277_.m_6084_()) {
         this.f_19281_ = true;
         this.f_19279_ = this.f_19277_.f_19797_;
         this.f_19280_ = this.f_19279_;
         this.f_19277_.m_8108_();
      }

   }

   public Component m_19293_() {
      if (this.f_19276_.isEmpty()) {
         return new TranslatableComponent("death.attack.generic", this.f_19277_.m_5446_());
      } else {
         CombatEntry combatentry = this.m_19298_();
         CombatEntry combatentry1 = this.f_19276_.get(this.f_19276_.size() - 1);
         Component component1 = combatentry1.m_19267_();
         Entity entity = combatentry1.m_19263_().m_7639_();
         Component component;
         if (combatentry != null && combatentry1.m_19263_() == DamageSource.f_19315_) {
            Component component2 = combatentry.m_19267_();
            if (combatentry.m_19263_() != DamageSource.f_19315_ && combatentry.m_19263_() != DamageSource.f_19317_) {
               if (component2 != null && !component2.equals(component1)) {
                  Entity entity1 = combatentry.m_19263_().m_7639_();
                  ItemStack itemstack1 = entity1 instanceof LivingEntity ? ((LivingEntity)entity1).m_21205_() : ItemStack.f_41583_;
                  if (!itemstack1.m_41619_() && itemstack1.m_41788_()) {
                     component = new TranslatableComponent("death.fell.assist.item", this.f_19277_.m_5446_(), component2, itemstack1.m_41611_());
                  } else {
                     component = new TranslatableComponent("death.fell.assist", this.f_19277_.m_5446_(), component2);
                  }
               } else if (component1 != null) {
                  ItemStack itemstack = entity instanceof LivingEntity ? ((LivingEntity)entity).m_21205_() : ItemStack.f_41583_;
                  if (!itemstack.m_41619_() && itemstack.m_41788_()) {
                     component = new TranslatableComponent("death.fell.finish.item", this.f_19277_.m_5446_(), component1, itemstack.m_41611_());
                  } else {
                     component = new TranslatableComponent("death.fell.finish", this.f_19277_.m_5446_(), component1);
                  }
               } else {
                  component = new TranslatableComponent("death.fell.killer", this.f_19277_.m_5446_());
               }
            } else {
               component = new TranslatableComponent("death.fell.accident." + this.m_19287_(combatentry), this.f_19277_.m_5446_());
            }
         } else {
            component = combatentry1.m_19263_().m_6157_(this.f_19277_);
         }

         return component;
      }
   }

   @Nullable
   public LivingEntity m_19294_() {
      LivingEntity livingentity = null;
      Player player = null;
      float f = 0.0F;
      float f1 = 0.0F;

      for(CombatEntry combatentry : this.f_19276_) {
         if (combatentry.m_19263_().m_7639_() instanceof Player && (player == null || combatentry.m_19264_() > f1)) {
            f1 = combatentry.m_19264_();
            player = (Player)combatentry.m_19263_().m_7639_();
         }

         if (combatentry.m_19263_().m_7639_() instanceof LivingEntity && (livingentity == null || combatentry.m_19264_() > f)) {
            f = combatentry.m_19264_();
            livingentity = (LivingEntity)combatentry.m_19263_().m_7639_();
         }
      }

      return (LivingEntity)(player != null && f1 >= f / 3.0F ? player : livingentity);
   }

   @Nullable
   private CombatEntry m_19298_() {
      CombatEntry combatentry = null;
      CombatEntry combatentry1 = null;
      float f = 0.0F;
      float f1 = 0.0F;

      for(int i = 0; i < this.f_19276_.size(); ++i) {
         CombatEntry combatentry2 = this.f_19276_.get(i);
         CombatEntry combatentry3 = i > 0 ? this.f_19276_.get(i - 1) : null;
         if ((combatentry2.m_19263_() == DamageSource.f_19315_ || combatentry2.m_19263_() == DamageSource.f_19317_) && combatentry2.m_19268_() > 0.0F && (combatentry == null || combatentry2.m_19268_() > f1)) {
            if (i > 0) {
               combatentry = combatentry3;
            } else {
               combatentry = combatentry2;
            }

            f1 = combatentry2.m_19268_();
         }

         if (combatentry2.m_19266_() != null && (combatentry1 == null || combatentry2.m_19264_() > f)) {
            combatentry1 = combatentry2;
            f = combatentry2.m_19264_();
         }
      }

      if (f1 > 5.0F && combatentry != null) {
         return combatentry;
      } else {
         return f > 5.0F && combatentry1 != null ? combatentry1 : null;
      }
   }

   private String m_19287_(CombatEntry p_19288_) {
      return p_19288_.m_19266_() == null ? "generic" : p_19288_.m_19266_();
   }

   public boolean m_146696_() {
      this.m_19296_();
      return this.f_19282_;
   }

   public boolean m_146697_() {
      this.m_19296_();
      return this.f_19281_;
   }

   public int m_19295_() {
      return this.f_19281_ ? this.f_19277_.f_19797_ - this.f_19279_ : this.f_19280_ - this.f_19279_;
   }

   private void m_19299_() {
      this.f_19283_ = null;
   }

   public void m_19296_() {
      int i = this.f_19281_ ? 300 : 100;
      if (this.f_19282_ && (!this.f_19277_.m_6084_() || this.f_19277_.f_19797_ - this.f_19278_ > i)) {
         boolean flag = this.f_19281_;
         this.f_19282_ = false;
         this.f_19281_ = false;
         this.f_19280_ = this.f_19277_.f_19797_;
         if (flag) {
            this.f_19277_.m_8098_();
         }

         this.f_19276_.clear();
      }

   }

   public LivingEntity m_19297_() {
      return this.f_19277_;
   }

   @Nullable
   public CombatEntry m_146698_() {
      return this.f_19276_.isEmpty() ? null : this.f_19276_.get(this.f_19276_.size() - 1);
   }

   public int m_146699_() {
      LivingEntity livingentity = this.m_19294_();
      return livingentity == null ? -1 : livingentity.m_142049_();
   }
}