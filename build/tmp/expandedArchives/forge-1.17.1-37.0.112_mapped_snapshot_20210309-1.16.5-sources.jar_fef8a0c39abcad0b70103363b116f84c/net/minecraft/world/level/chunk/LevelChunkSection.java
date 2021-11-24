package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class LevelChunkSection {
   public static final int f_156455_ = 16;
   public static final int f_156456_ = 16;
   public static final int f_156457_ = 4096;
   private static final Palette<BlockState> f_62967_ = new GlobalPalette<>(Block.f_49791_, Blocks.f_50016_.m_49966_());
   private final int f_62968_;
   private short f_62969_;
   private short f_62970_;
   private short f_62971_;
   private final PalettedContainer<BlockState> f_62972_;

   public LevelChunkSection(int p_62975_) {
      this(p_62975_, (short)0, (short)0, (short)0);
   }

   public LevelChunkSection(int p_62977_, short p_62978_, short p_62979_, short p_62980_) {
      this.f_62968_ = m_156458_(p_62977_);
      this.f_62969_ = p_62978_;
      this.f_62970_ = p_62979_;
      this.f_62971_ = p_62980_;
      this.f_62972_ = new PalettedContainer<>(f_62967_, Block.f_49791_, NbtUtils::m_129241_, NbtUtils::m_129202_, Blocks.f_50016_.m_49966_());
   }

   public static int m_156458_(int p_156459_) {
      return p_156459_ << 4;
   }

   public BlockState m_62982_(int p_62983_, int p_62984_, int p_62985_) {
      return this.f_62972_.m_63087_(p_62983_, p_62984_, p_62985_);
   }

   public FluidState m_63007_(int p_63008_, int p_63009_, int p_63010_) {
      return this.f_62972_.m_63087_(p_63008_, p_63009_, p_63010_).m_60819_();
   }

   public void m_62981_() {
      this.f_62972_.m_63084_();
   }

   public void m_63006_() {
      this.f_62972_.m_63120_();
   }

   public BlockState m_62986_(int p_62987_, int p_62988_, int p_62989_, BlockState p_62990_) {
      return this.m_62991_(p_62987_, p_62988_, p_62989_, p_62990_, true);
   }

   public BlockState m_62991_(int p_62992_, int p_62993_, int p_62994_, BlockState p_62995_, boolean p_62996_) {
      BlockState blockstate;
      if (p_62996_) {
         blockstate = this.f_62972_.m_63091_(p_62992_, p_62993_, p_62994_, p_62995_);
      } else {
         blockstate = this.f_62972_.m_63127_(p_62992_, p_62993_, p_62994_, p_62995_);
      }

      FluidState fluidstate = blockstate.m_60819_();
      FluidState fluidstate1 = p_62995_.m_60819_();
      if (!blockstate.m_60795_()) {
         --this.f_62969_;
         if (blockstate.m_60823_()) {
            --this.f_62970_;
         }
      }

      if (!fluidstate.m_76178_()) {
         --this.f_62971_;
      }

      if (!p_62995_.m_60795_()) {
         ++this.f_62969_;
         if (p_62995_.m_60823_()) {
            ++this.f_62970_;
         }
      }

      if (!fluidstate1.m_76178_()) {
         ++this.f_62971_;
      }

      return blockstate;
   }

   public boolean m_63013_() {
      return this.f_62969_ == 0;
   }

   public static boolean m_63000_(@Nullable LevelChunkSection p_63001_) {
      return p_63001_ == LevelChunk.f_62770_ || p_63001_.m_63013_();
   }

   public boolean m_63014_() {
      return this.m_63015_() || this.m_63016_();
   }

   public boolean m_63015_() {
      return this.f_62970_ > 0;
   }

   public boolean m_63016_() {
      return this.f_62971_ > 0;
   }

   public int m_63017_() {
      return this.f_62968_;
   }

   public void m_63018_() {
      this.f_62969_ = 0;
      this.f_62970_ = 0;
      this.f_62971_ = 0;
      this.f_62972_.m_63099_((p_62998_, p_62999_) -> {
         FluidState fluidstate = p_62998_.m_60819_();
         if (!p_62998_.m_60795_()) {
            this.f_62969_ = (short)(this.f_62969_ + p_62999_);
            if (p_62998_.m_60823_()) {
               this.f_62970_ = (short)(this.f_62970_ + p_62999_);
            }
         }

         if (!fluidstate.m_76178_()) {
            this.f_62969_ = (short)(this.f_62969_ + p_62999_);
            if (fluidstate.m_76187_()) {
               this.f_62971_ = (short)(this.f_62971_ + p_62999_);
            }
         }

      });
   }

   public PalettedContainer<BlockState> m_63019_() {
      return this.f_62972_;
   }

   public void m_63004_(FriendlyByteBuf p_63005_) {
      this.f_62969_ = p_63005_.readShort();
      this.f_62972_.m_63118_(p_63005_);
   }

   public void m_63011_(FriendlyByteBuf p_63012_) {
      p_63012_.writeShort(this.f_62969_);
      this.f_62972_.m_63135_(p_63012_);
   }

   public int m_63020_() {
      return 2 + this.f_62972_.m_63137_();
   }

   public boolean m_63002_(Predicate<BlockState> p_63003_) {
      return this.f_62972_.m_63109_(p_63003_);
   }
}