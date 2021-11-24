package net.minecraft.data;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;

public class BlockFamily {
   private final Block f_175943_;
   final Map<BlockFamily.Variant, Block> f_175944_ = Maps.newHashMap();
   boolean f_175945_ = true;
   boolean f_175946_ = true;
   @Nullable
   String f_175947_;
   @Nullable
   String f_175948_;

   BlockFamily(Block p_175950_) {
      this.f_175943_ = p_175950_;
   }

   public Block m_175951_() {
      return this.f_175943_;
   }

   public Map<BlockFamily.Variant, Block> m_175954_() {
      return this.f_175944_;
   }

   public Block m_175952_(BlockFamily.Variant p_175953_) {
      return this.f_175944_.get(p_175953_);
   }

   public boolean m_175955_() {
      return this.f_175945_;
   }

   public boolean m_175956_() {
      return this.f_175946_;
   }

   public Optional<String> m_175957_() {
      return StringUtils.isBlank(this.f_175947_) ? Optional.empty() : Optional.of(this.f_175947_);
   }

   public Optional<String> m_175958_() {
      return StringUtils.isBlank(this.f_175948_) ? Optional.empty() : Optional.of(this.f_175948_);
   }

   public static class Builder {
      private final BlockFamily f_175959_;

      public Builder(Block p_175961_) {
         this.f_175959_ = new BlockFamily(p_175961_);
      }

      public BlockFamily m_175962_() {
         return this.f_175959_;
      }

      public BlockFamily.Builder m_175963_(Block p_175964_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.BUTTON, p_175964_);
         return this;
      }

      public BlockFamily.Builder m_175971_(Block p_175972_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.CHISELED, p_175972_);
         return this;
      }

      public BlockFamily.Builder m_175976_(Block p_175977_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.CRACKED, p_175977_);
         return this;
      }

      public BlockFamily.Builder m_175978_(Block p_175979_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.CUT, p_175979_);
         return this;
      }

      public BlockFamily.Builder m_175980_(Block p_175981_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.DOOR, p_175981_);
         return this;
      }

      public BlockFamily.Builder m_175982_(Block p_175983_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.FENCE, p_175983_);
         return this;
      }

      public BlockFamily.Builder m_175984_(Block p_175985_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.FENCE_GATE, p_175985_);
         return this;
      }

      public BlockFamily.Builder m_175965_(Block p_175966_, Block p_175967_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.SIGN, p_175966_);
         this.f_175959_.f_175944_.put(BlockFamily.Variant.WALL_SIGN, p_175967_);
         return this;
      }

      public BlockFamily.Builder m_175986_(Block p_175987_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.SLAB, p_175987_);
         return this;
      }

      public BlockFamily.Builder m_175988_(Block p_175989_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.STAIRS, p_175989_);
         return this;
      }

      public BlockFamily.Builder m_175990_(Block p_175991_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.PRESSURE_PLATE, p_175991_);
         return this;
      }

      public BlockFamily.Builder m_175992_(Block p_175993_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.POLISHED, p_175993_);
         return this;
      }

      public BlockFamily.Builder m_175994_(Block p_175995_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.TRAPDOOR, p_175995_);
         return this;
      }

      public BlockFamily.Builder m_175996_(Block p_175997_) {
         this.f_175959_.f_175944_.put(BlockFamily.Variant.WALL, p_175997_);
         return this;
      }

      public BlockFamily.Builder m_175970_() {
         this.f_175959_.f_175945_ = false;
         return this;
      }

      public BlockFamily.Builder m_175975_() {
         this.f_175959_.f_175946_ = false;
         return this;
      }

      public BlockFamily.Builder m_175968_(String p_175969_) {
         this.f_175959_.f_175947_ = p_175969_;
         return this;
      }

      public BlockFamily.Builder m_175973_(String p_175974_) {
         this.f_175959_.f_175948_ = p_175974_;
         return this;
      }
   }

   public static enum Variant {
      BUTTON("button"),
      CHISELED("chiseled"),
      CRACKED("cracked"),
      CUT("cut"),
      DOOR("door"),
      FENCE("fence"),
      FENCE_GATE("fence_gate"),
      SIGN("sign"),
      SLAB("slab"),
      STAIRS("stairs"),
      PRESSURE_PLATE("pressure_plate"),
      POLISHED("polished"),
      TRAPDOOR("trapdoor"),
      WALL("wall"),
      WALL_SIGN("wall_sign");

      private final String f_176013_;

      private Variant(String p_176019_) {
         this.f_176013_ = p_176019_;
      }

      public String m_176020_() {
         return this.f_176013_;
      }
   }
}