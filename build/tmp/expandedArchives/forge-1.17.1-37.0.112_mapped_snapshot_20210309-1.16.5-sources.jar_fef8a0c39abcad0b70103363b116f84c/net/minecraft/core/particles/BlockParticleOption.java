package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockParticleOption implements ParticleOptions {
   public static final ParticleOptions.Deserializer<BlockParticleOption> f_123624_ = new ParticleOptions.Deserializer<BlockParticleOption>() {
      public BlockParticleOption m_5739_(ParticleType<BlockParticleOption> p_123645_, StringReader p_123646_) throws CommandSyntaxException {
         p_123646_.expect(' ');
         return new BlockParticleOption(p_123645_, (new BlockStateParser(p_123646_, false)).m_116806_(false).m_116808_());
      }

      public BlockParticleOption m_6507_(ParticleType<BlockParticleOption> p_123648_, FriendlyByteBuf p_123649_) {
         return new BlockParticleOption(p_123648_, Block.f_49791_.m_7942_(p_123649_.m_130242_()));
      }
   };
   private final ParticleType<BlockParticleOption> f_123625_;
   private final BlockState f_123626_;

   public static Codec<BlockParticleOption> m_123634_(ParticleType<BlockParticleOption> p_123635_) {
      return BlockState.f_61039_.xmap((p_123638_) -> {
         return new BlockParticleOption(p_123635_, p_123638_);
      }, (p_123633_) -> {
         return p_123633_.f_123626_;
      });
   }

   public BlockParticleOption(ParticleType<BlockParticleOption> p_123629_, BlockState p_123630_) {
      this.f_123625_ = p_123629_;
      this.f_123626_ = p_123630_;
   }

   public void m_7711_(FriendlyByteBuf p_123640_) {
      p_123640_.m_130130_(Block.f_49791_.m_7447_(this.f_123626_));
   }

   public String m_5942_() {
      return Registry.f_122829_.m_7981_(this.m_6012_()) + " " + BlockStateParser.m_116769_(this.f_123626_);
   }

   public ParticleType<BlockParticleOption> m_6012_() {
      return this.f_123625_;
   }

   public BlockState m_123642_() {
      return this.f_123626_;
   }

   //FORGE: Add a source pos property, so we can provide models with additional model data
   private net.minecraft.core.BlockPos pos;
   public BlockParticleOption setPos(net.minecraft.core.BlockPos pos) {
      this.pos = pos;
      return this;
   }

   public net.minecraft.core.BlockPos getPos() {
      return pos;
   }
}
