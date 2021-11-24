package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public abstract class ScatteredFeaturePiece extends StructurePiece {
   protected final int f_72787_;
   protected final int f_72788_;
   protected final int f_72789_;
   protected int f_72790_ = -1;

   protected ScatteredFeaturePiece(StructurePieceType p_163188_, int p_163189_, int p_163190_, int p_163191_, int p_163192_, int p_163193_, int p_163194_, Direction p_163195_) {
      super(p_163188_, 0, StructurePiece.m_163541_(p_163189_, p_163190_, p_163191_, p_163195_, p_163192_, p_163193_, p_163194_));
      this.f_72787_ = p_163192_;
      this.f_72788_ = p_163193_;
      this.f_72789_ = p_163194_;
      this.m_73519_(p_163195_);
   }

   protected ScatteredFeaturePiece(StructurePieceType p_72801_, CompoundTag p_72802_) {
      super(p_72801_, p_72802_);
      this.f_72787_ = p_72802_.m_128451_("Width");
      this.f_72788_ = p_72802_.m_128451_("Height");
      this.f_72789_ = p_72802_.m_128451_("Depth");
      this.f_72790_ = p_72802_.m_128451_("HPos");
   }

   protected void m_142347_(ServerLevel p_163197_, CompoundTag p_163198_) {
      p_163198_.m_128405_("Width", this.f_72787_);
      p_163198_.m_128405_("Height", this.f_72788_);
      p_163198_.m_128405_("Depth", this.f_72789_);
      p_163198_.m_128405_("HPos", this.f_72790_);
   }

   protected boolean m_72803_(LevelAccessor p_72804_, BoundingBox p_72805_, int p_72806_) {
      if (this.f_72790_ >= 0) {
         return true;
      } else {
         int i = 0;
         int j = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k = this.f_73383_.m_162398_(); k <= this.f_73383_.m_162401_(); ++k) {
            for(int l = this.f_73383_.m_162395_(); l <= this.f_73383_.m_162399_(); ++l) {
               blockpos$mutableblockpos.m_122178_(l, 64, k);
               if (p_72805_.m_71051_(blockpos$mutableblockpos)) {
                  i += p_72804_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos).m_123342_();
                  ++j;
               }
            }
         }

         if (j == 0) {
            return false;
         } else {
            this.f_72790_ = i / j;
            this.f_73383_.m_162367_(0, this.f_72790_ - this.f_73383_.m_162396_() + p_72806_, 0);
            return true;
         }
      }
   }
}