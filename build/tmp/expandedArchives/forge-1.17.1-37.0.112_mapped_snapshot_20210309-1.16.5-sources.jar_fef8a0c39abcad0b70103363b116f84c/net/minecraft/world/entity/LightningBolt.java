package net.minecraft.world.entity;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningBolt extends Entity {
   private static final int f_147136_ = 2;
   private static final double f_147137_ = 3.0D;
   private static final double f_147138_ = 15.0D;
   private int f_20860_;
   public long f_20859_;
   private int f_20861_;
   private boolean f_20862_;
   @Nullable
   private ServerPlayer f_20863_;
   private final Set<Entity> f_147134_ = Sets.newHashSet();
   private int f_147135_;
   private float damage = 5.0F;

   public LightningBolt(EntityType<? extends LightningBolt> p_20865_, Level p_20866_) {
      super(p_20865_, p_20866_);
      this.f_19811_ = true;
      this.f_20860_ = 2;
      this.f_20859_ = this.f_19796_.nextLong();
      this.f_20861_ = this.f_19796_.nextInt(3) + 1;
   }

   public void m_20874_(boolean p_20875_) {
      this.f_20862_ = p_20875_;
   }

   public SoundSource m_5720_() {
      return SoundSource.WEATHER;
   }

   @Nullable
   public ServerPlayer m_147158_() {
      return this.f_20863_;
   }

   public void m_20879_(@Nullable ServerPlayer p_20880_) {
      this.f_20863_ = p_20880_;
   }

   private void m_147161_() {
      BlockPos blockpos = this.m_147162_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      if (blockstate.m_60713_(Blocks.f_152587_)) {
         ((LightningRodBlock)blockstate.m_60734_()).m_153760_(blockstate, this.f_19853_, blockpos);
      }

   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getDamage() {
      return this.damage;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_20860_ == 2) {
         if (this.f_19853_.m_5776_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12090_, SoundSource.WEATHER, 10000.0F, 0.8F + this.f_19796_.nextFloat() * 0.2F, false);
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12089_, SoundSource.WEATHER, 2.0F, 0.5F + this.f_19796_.nextFloat() * 0.2F, false);
         } else {
            Difficulty difficulty = this.f_19853_.m_46791_();
            if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
               this.m_20870_(4);
            }

            this.m_147161_();
            m_147150_(this.f_19853_, this.m_147162_());
            this.m_146850_(GameEvent.f_157772_);
         }
      }

      --this.f_20860_;
      if (this.f_20860_ < 0) {
         if (this.f_20861_ == 0) {
            if (this.f_19853_ instanceof ServerLevel) {
               List<Entity> list = this.f_19853_.m_6249_(this, new AABB(this.m_20185_() - 15.0D, this.m_20186_() - 15.0D, this.m_20189_() - 15.0D, this.m_20185_() + 15.0D, this.m_20186_() + 6.0D + 15.0D, this.m_20189_() + 15.0D), (p_147140_) -> {
                  return p_147140_.m_6084_() && !this.f_147134_.contains(p_147140_);
               });

               for(ServerPlayer serverplayer : ((ServerLevel)this.f_19853_).m_8795_((p_147157_) -> {
                  return p_147157_.m_20270_(this) < 256.0F;
               })) {
                  CriteriaTriggers.f_145089_.m_153391_(serverplayer, this, list);
               }
            }

            this.m_146870_();
         } else if (this.f_20860_ < -this.f_19796_.nextInt(10)) {
            --this.f_20861_;
            this.f_20860_ = 1;
            this.f_20859_ = this.f_19796_.nextLong();
            this.m_20870_(0);
         }
      }

      if (this.f_20860_ >= 0) {
         if (!(this.f_19853_ instanceof ServerLevel)) {
            this.f_19853_.m_6580_(2);
         } else if (!this.f_20862_) {
            List<Entity> list1 = this.f_19853_.m_6249_(this, new AABB(this.m_20185_() - 3.0D, this.m_20186_() - 3.0D, this.m_20189_() - 3.0D, this.m_20185_() + 3.0D, this.m_20186_() + 6.0D + 3.0D, this.m_20189_() + 3.0D), Entity::m_6084_);

            for(Entity entity : list1) {
               if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, this))
               entity.m_8038_((ServerLevel)this.f_19853_, this);
            }

            this.f_147134_.addAll(list1);
            if (this.f_20863_ != null) {
               CriteriaTriggers.f_10554_.m_21721_(this.f_20863_, list1);
            }
         }
      }

   }

   private BlockPos m_147162_() {
      Vec3 vec3 = this.m_20182_();
      return new BlockPos(vec3.f_82479_, vec3.f_82480_ - 1.0E-6D, vec3.f_82481_);
   }

   private void m_20870_(int p_20871_) {
      if (!this.f_20862_ && !this.f_19853_.f_46443_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46131_)) {
         BlockPos blockpos = this.m_142538_();
         BlockState blockstate = BaseFireBlock.m_49245_(this.f_19853_, blockpos);
         if (this.f_19853_.m_8055_(blockpos).m_60795_() && blockstate.m_60710_(this.f_19853_, blockpos)) {
            this.f_19853_.m_46597_(blockpos, blockstate);
            ++this.f_147135_;
         }

         for(int i = 0; i < p_20871_; ++i) {
            BlockPos blockpos1 = blockpos.m_142082_(this.f_19796_.nextInt(3) - 1, this.f_19796_.nextInt(3) - 1, this.f_19796_.nextInt(3) - 1);
            blockstate = BaseFireBlock.m_49245_(this.f_19853_, blockpos1);
            if (this.f_19853_.m_8055_(blockpos1).m_60795_() && blockstate.m_60710_(this.f_19853_, blockpos1)) {
               this.f_19853_.m_46597_(blockpos1, blockstate);
               ++this.f_147135_;
            }
         }

      }
   }

   private static void m_147150_(Level p_147151_, BlockPos p_147152_) {
      BlockState blockstate = p_147151_.m_8055_(p_147152_);
      BlockPos blockpos;
      BlockState blockstate1;
      if (blockstate.m_60713_(Blocks.f_152587_)) {
         blockpos = p_147152_.m_142300_(blockstate.m_61143_(LightningRodBlock.f_52588_).m_122424_());
         blockstate1 = p_147151_.m_8055_(blockpos);
      } else {
         blockpos = p_147152_;
         blockstate1 = blockstate;
      }

      if (blockstate1.m_60734_() instanceof WeatheringCopper) {
         p_147151_.m_46597_(blockpos, WeatheringCopper.m_154906_(p_147151_.m_8055_(blockpos)));
         BlockPos.MutableBlockPos blockpos$mutableblockpos = p_147152_.m_122032_();
         int i = p_147151_.f_46441_.nextInt(3) + 3;

         for(int j = 0; j < i; ++j) {
            int k = p_147151_.f_46441_.nextInt(8) + 1;
            m_147145_(p_147151_, blockpos, blockpos$mutableblockpos, k);
         }

      }
   }

   private static void m_147145_(Level p_147146_, BlockPos p_147147_, BlockPos.MutableBlockPos p_147148_, int p_147149_) {
      p_147148_.m_122190_(p_147147_);

      for(int i = 0; i < p_147149_; ++i) {
         Optional<BlockPos> optional = m_147153_(p_147146_, p_147148_);
         if (!optional.isPresent()) {
            break;
         }

         p_147148_.m_122190_(optional.get());
      }

   }

   private static Optional<BlockPos> m_147153_(Level p_147154_, BlockPos p_147155_) {
      for(BlockPos blockpos : BlockPos.m_175264_(p_147154_.f_46441_, 10, p_147155_, 1)) {
         BlockState blockstate = p_147154_.m_8055_(blockpos);
         if (blockstate.m_60734_() instanceof WeatheringCopper) {
            WeatheringCopper.m_154899_(blockstate).ifPresent((p_147144_) -> {
               p_147154_.m_46597_(blockpos, p_147144_);
            });
            p_147154_.m_46796_(3002, blockpos, -1);
            return Optional.of(blockpos);
         }
      }

      return Optional.empty();
   }

   public boolean m_6783_(double p_20869_) {
      double d0 = 64.0D * m_20150_();
      return p_20869_ < d0 * d0;
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag p_20873_) {
   }

   protected void m_7380_(CompoundTag p_20877_) {
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public int m_147159_() {
      return this.f_147135_;
   }

   public Stream<Entity> m_147160_() {
      return this.f_147134_.stream().filter(Entity::m_6084_);
   }
}
