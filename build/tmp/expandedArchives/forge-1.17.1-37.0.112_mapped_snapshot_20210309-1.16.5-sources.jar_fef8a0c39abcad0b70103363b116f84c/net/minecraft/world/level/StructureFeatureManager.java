package net.minecraft.world.level;

import com.mojang.datafixers.DataFixUtils;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.FeatureAccess;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureFeatureManager {
   private final LevelAccessor f_47266_;
   private final WorldGenSettings f_47267_;

   public StructureFeatureManager(LevelAccessor p_47269_, WorldGenSettings p_47270_) {
      this.f_47266_ = p_47269_;
      this.f_47267_ = p_47270_;
   }

   public StructureFeatureManager m_47272_(WorldGenRegion p_47273_) {
      if (p_47273_.m_6018_() != this.f_47266_) {
         throw new IllegalStateException("Using invalid feature manager (source level: " + p_47273_.m_6018_() + ", region: " + p_47273_);
      } else {
         return new StructureFeatureManager(p_47273_, this.f_47267_);
      }
   }

   public Stream<? extends StructureStart<?>> m_47289_(SectionPos p_47290_, StructureFeature<?> p_47291_) {
      return this.f_47266_.m_46819_(p_47290_.m_123170_(), p_47290_.m_123222_(), ChunkStatus.f_62316_).m_6705_(p_47291_).stream().map((p_47307_) -> {
         return SectionPos.m_123196_(new ChunkPos(p_47307_), this.f_47266_.m_151560_());
      }).map((p_47276_) -> {
         return this.m_47297_(p_47276_, p_47291_, this.f_47266_.m_46819_(p_47276_.m_123170_(), p_47276_.m_123222_(), ChunkStatus.f_62315_));
      }).filter((p_47278_) -> {
         return p_47278_ != null && p_47278_.m_73603_();
      });
   }

   @Nullable
   public StructureStart<?> m_47297_(SectionPos p_47298_, StructureFeature<?> p_47299_, FeatureAccess p_47300_) {
      return p_47300_.m_7253_(p_47299_);
   }

   public void m_47301_(SectionPos p_47302_, StructureFeature<?> p_47303_, StructureStart<?> p_47304_, FeatureAccess p_47305_) {
      p_47305_.m_8078_(p_47303_, p_47304_);
   }

   public void m_47292_(SectionPos p_47293_, StructureFeature<?> p_47294_, long p_47295_, FeatureAccess p_47296_) {
      p_47296_.m_6306_(p_47294_, p_47295_);
   }

   public boolean m_47271_() {
      return this.f_47267_.m_64657_();
   }

   public StructureStart<?> m_47285_(BlockPos p_47286_, boolean p_47287_, StructureFeature<?> p_47288_) {
      return DataFixUtils.orElse(this.m_47289_(SectionPos.m_123199_(p_47286_), p_47288_).filter((p_151637_) -> {
         return p_47287_ ? p_151637_.m_73602_().stream().anyMatch((p_151633_) -> {
            return p_151633_.m_73547_().m_71051_(p_47286_);
         }) : p_151637_.m_73601_().m_71051_(p_47286_);
      }).findFirst(), StructureStart.f_73561_);
   }
}