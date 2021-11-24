package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EntityPositionSource implements PositionSource {
   public static final Codec<EntityPositionSource> f_157725_ = RecordCodecBuilder.create((p_157735_) -> {
      return p_157735_.group(Codec.INT.fieldOf("source_entity_id").forGetter((p_157737_) -> {
         return p_157737_.f_157726_;
      })).apply(p_157735_, EntityPositionSource::new);
   });
   final int f_157726_;
   private Optional<Entity> f_157727_ = Optional.empty();

   public EntityPositionSource(int p_157730_) {
      this.f_157726_ = p_157730_;
   }

   public Optional<BlockPos> m_142502_(Level p_157733_) {
      if (!this.f_157727_.isPresent()) {
         this.f_157727_ = Optional.ofNullable(p_157733_.m_6815_(this.f_157726_));
      }

      return this.f_157727_.map(Entity::m_142538_);
   }

   public PositionSourceType<?> m_142510_() {
      return PositionSourceType.f_157872_;
   }

   public static class Type implements PositionSourceType<EntityPositionSource> {
      public EntityPositionSource m_142281_(FriendlyByteBuf p_157741_) {
         return new EntityPositionSource(p_157741_.m_130242_());
      }

      public void m_142235_(FriendlyByteBuf p_157743_, EntityPositionSource p_157744_) {
         p_157743_.m_130130_(p_157744_.f_157726_);
      }

      public Codec<EntityPositionSource> m_142341_() {
         return EntityPositionSource.f_157725_;
      }
   }
}