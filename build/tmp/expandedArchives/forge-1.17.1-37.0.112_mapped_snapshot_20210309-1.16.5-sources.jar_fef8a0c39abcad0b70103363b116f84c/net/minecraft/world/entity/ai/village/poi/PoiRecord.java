package net.minecraft.world.entity.ai.village.poi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.VisibleForDebug;

public class PoiRecord {
   private final BlockPos f_27227_;
   private final PoiType f_27228_;
   private int f_27229_;
   private final Runnable f_27230_;

   public static Codec<PoiRecord> m_27242_(Runnable p_27243_) {
      return RecordCodecBuilder.create((p_27246_) -> {
         return p_27246_.group(BlockPos.f_121852_.fieldOf("pos").forGetter((p_148673_) -> {
            return p_148673_.f_27227_;
         }), Registry.f_122870_.fieldOf("type").forGetter((p_148671_) -> {
            return p_148671_.f_27228_;
         }), Codec.INT.fieldOf("free_tickets").orElse(0).forGetter((p_148669_) -> {
            return p_148669_.f_27229_;
         }), RecordCodecBuilder.point(p_27243_)).apply(p_27246_, PoiRecord::new);
      });
   }

   private PoiRecord(BlockPos p_27232_, PoiType p_27233_, int p_27234_, Runnable p_27235_) {
      this.f_27227_ = p_27232_.m_7949_();
      this.f_27228_ = p_27233_;
      this.f_27229_ = p_27234_;
      this.f_27230_ = p_27235_;
   }

   public PoiRecord(BlockPos p_27237_, PoiType p_27238_, Runnable p_27239_) {
      this(p_27237_, p_27238_, p_27238_.m_27385_(), p_27239_);
   }

   @Deprecated
   @VisibleForDebug
   public int m_148667_() {
      return this.f_27229_;
   }

   protected boolean m_27247_() {
      if (this.f_27229_ <= 0) {
         return false;
      } else {
         --this.f_27229_;
         this.f_27230_.run();
         return true;
      }
   }

   protected boolean m_27250_() {
      if (this.f_27229_ >= this.f_27228_.m_27385_()) {
         return false;
      } else {
         ++this.f_27229_;
         this.f_27230_.run();
         return true;
      }
   }

   public boolean m_27253_() {
      return this.f_27229_ > 0;
   }

   public boolean m_27254_() {
      return this.f_27229_ != this.f_27228_.m_27385_();
   }

   public BlockPos m_27257_() {
      return this.f_27227_;
   }

   public PoiType m_27258_() {
      return this.f_27228_;
   }

   public boolean equals(Object p_27256_) {
      if (this == p_27256_) {
         return true;
      } else {
         return p_27256_ != null && this.getClass() == p_27256_.getClass() ? Objects.equals(this.f_27227_, ((PoiRecord)p_27256_).f_27227_) : false;
      }
   }

   public int hashCode() {
      return this.f_27227_.hashCode();
   }
}