package net.minecraft.world.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface NeutralMob {
   String f_147283_ = "AngerTime";
   String f_147284_ = "AngryAt";

   int m_6784_();

   void m_7870_(int p_21673_);

   @Nullable
   UUID m_6120_();

   void m_6925_(@Nullable UUID p_21672_);

   void m_6825_();

   default void m_21678_(CompoundTag p_21679_) {
      p_21679_.m_128405_("AngerTime", this.m_6784_());
      if (this.m_6120_() != null) {
         p_21679_.m_128362_("AngryAt", this.m_6120_());
      }

   }

   default void m_147285_(Level p_147286_, CompoundTag p_147287_) {
      this.m_7870_(p_147287_.m_128451_("AngerTime"));
      if (p_147286_ instanceof ServerLevel) {
         if (!p_147287_.m_128403_("AngryAt")) {
            this.m_6925_((UUID)null);
         } else {
            UUID uuid = p_147287_.m_128342_("AngryAt");
            this.m_6925_(uuid);
            Entity entity = ((ServerLevel)p_147286_).m_8791_(uuid);
            if (entity != null) {
               if (entity instanceof Mob) {
                  this.m_6703_((Mob)entity);
               }

               if (entity.m_6095_() == EntityType.f_20532_) {
                  this.m_6598_((Player)entity);
               }

            }
         }
      }
   }

   default void m_21666_(ServerLevel p_21667_, boolean p_21668_) {
      LivingEntity livingentity = this.m_5448_();
      UUID uuid = this.m_6120_();
      if ((livingentity == null || livingentity.m_21224_()) && uuid != null && p_21667_.m_8791_(uuid) instanceof Mob) {
         this.m_21662_();
      } else {
         if (livingentity != null && !Objects.equals(uuid, livingentity.m_142081_())) {
            this.m_6925_(livingentity.m_142081_());
            this.m_6825_();
         }

         if (this.m_6784_() > 0 && (livingentity == null || livingentity.m_6095_() != EntityType.f_20532_ || !p_21668_)) {
            this.m_7870_(this.m_6784_() - 1);
            if (this.m_6784_() == 0) {
               this.m_21662_();
            }
         }

      }
   }

   default boolean m_21674_(LivingEntity p_21675_) {
      if (!this.m_6779_(p_21675_)) {
         return false;
      } else {
         return p_21675_.m_6095_() == EntityType.f_20532_ && this.m_21670_(p_21675_.f_19853_) ? true : p_21675_.m_142081_().equals(this.m_6120_());
      }
   }

   default boolean m_21670_(Level p_21671_) {
      return p_21671_.m_46469_().m_46207_(GameRules.f_46127_) && this.m_21660_() && this.m_6120_() == null;
   }

   default boolean m_21660_() {
      return this.m_6784_() > 0;
   }

   default void m_21676_(Player p_21677_) {
      if (p_21677_.f_19853_.m_46469_().m_46207_(GameRules.f_46126_)) {
         if (p_21677_.m_142081_().equals(this.m_6120_())) {
            this.m_21662_();
         }
      }
   }

   default void m_21661_() {
      this.m_21662_();
      this.m_6825_();
   }

   default void m_21662_() {
      this.m_6703_((LivingEntity)null);
      this.m_6925_((UUID)null);
      this.m_6710_((LivingEntity)null);
      this.m_7870_(0);
   }

   @Nullable
   LivingEntity m_142581_();

   void m_6703_(@Nullable LivingEntity p_21669_);

   void m_6598_(@Nullable Player p_21680_);

   void m_6710_(@Nullable LivingEntity p_21681_);

   boolean m_6779_(LivingEntity p_181126_);

   @Nullable
   LivingEntity m_5448_();
}