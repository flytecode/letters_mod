package net.minecraft.world.level;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.LevelData;

public interface LevelAccessor extends CommonLevelAccessor, LevelTimeAccess {
   default long m_8044_() {
      return this.m_6106_().m_6792_();
   }

   TickList<Block> m_6219_();

   TickList<Fluid> m_6217_();

   LevelData m_6106_();

   DifficultyInstance m_6436_(BlockPos p_46800_);

   @Nullable
   MinecraftServer m_142572_();

   default Difficulty m_46791_() {
      return this.m_6106_().m_5472_();
   }

   ChunkSource m_7726_();

   default boolean m_7232_(int p_46794_, int p_46795_) {
      return this.m_7726_().m_5563_(p_46794_, p_46795_);
   }

   Random m_5822_();

   default void m_6289_(BlockPos p_46781_, Block p_46782_) {
   }

   void m_5594_(@Nullable Player p_46775_, BlockPos p_46776_, SoundEvent p_46777_, SoundSource p_46778_, float p_46779_, float p_46780_);

   void m_7106_(ParticleOptions p_46783_, double p_46784_, double p_46785_, double p_46786_, double p_46787_, double p_46788_, double p_46789_);

   void m_5898_(@Nullable Player p_46771_, int p_46772_, BlockPos p_46773_, int p_46774_);

   default int m_142475_() {
      return this.m_6042_().m_63964_();
   }

   default void m_46796_(int p_46797_, BlockPos p_46798_, int p_46799_) {
      this.m_5898_((Player)null, p_46797_, p_46798_, p_46799_);
   }

   void m_142346_(@Nullable Entity p_151549_, GameEvent p_151550_, BlockPos p_151551_);

   default void m_151555_(GameEvent p_151556_, BlockPos p_151557_) {
      this.m_142346_((Entity)null, p_151556_, p_151557_);
   }

   default void m_151552_(GameEvent p_151553_, Entity p_151554_) {
      this.m_142346_((Entity)null, p_151553_, p_151554_.m_142538_());
   }

   default void m_151545_(@Nullable Entity p_151546_, GameEvent p_151547_, Entity p_151548_) {
      this.m_142346_(p_151546_, p_151547_, p_151548_.m_142538_());
   }
}