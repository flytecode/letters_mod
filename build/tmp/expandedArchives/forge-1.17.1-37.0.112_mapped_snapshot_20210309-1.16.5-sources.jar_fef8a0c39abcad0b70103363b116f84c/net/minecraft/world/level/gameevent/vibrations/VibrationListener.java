package net.minecraft.world.level.gameevent.vibrations;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VibrationListener implements GameEventListener {
   protected final PositionSource f_157887_;
   protected final int f_157888_;
   protected final VibrationListener.VibrationListenerConfig f_157889_;
   protected Optional<GameEvent> f_157890_ = Optional.empty();
   protected int f_157891_;
   protected int f_157892_ = 0;

   public VibrationListener(PositionSource p_157894_, int p_157895_, VibrationListener.VibrationListenerConfig p_157896_) {
      this.f_157887_ = p_157894_;
      this.f_157888_ = p_157895_;
      this.f_157889_ = p_157896_;
   }

   public void m_157898_(Level p_157899_) {
      if (this.f_157890_.isPresent()) {
         --this.f_157892_;
         if (this.f_157892_ <= 0) {
            this.f_157892_ = 0;
            this.f_157889_.m_142190_(p_157899_, this, this.f_157890_.get(), this.f_157891_);
            this.f_157890_ = Optional.empty();
         }
      }

   }

   public PositionSource m_142460_() {
      return this.f_157887_;
   }

   public int m_142078_() {
      return this.f_157888_;
   }

   public boolean m_142721_(Level p_157901_, GameEvent p_157902_, @Nullable Entity p_157903_, BlockPos p_157904_) {
      if (!this.m_157916_(p_157902_, p_157903_)) {
         return false;
      } else {
         Optional<BlockPos> optional = this.f_157887_.m_142502_(p_157901_);
         if (!optional.isPresent()) {
            return false;
         } else {
            BlockPos blockpos = optional.get();
            if (!this.f_157889_.m_142008_(p_157901_, this, p_157904_, p_157902_, p_157903_)) {
               return false;
            } else if (this.m_157910_(p_157901_, p_157904_, blockpos)) {
               return false;
            } else {
               this.m_157905_(p_157901_, p_157902_, p_157904_, blockpos);
               return true;
            }
         }
      }
   }

   private boolean m_157916_(GameEvent p_157917_, @Nullable Entity p_157918_) {
      if (this.f_157890_.isPresent()) {
         return false;
      } else if (!GameEventTags.f_144302_.m_8110_(p_157917_)) {
         return false;
      } else {
         if (p_157918_ != null) {
            if (GameEventTags.f_144303_.m_8110_(p_157917_) && p_157918_.m_20161_()) {
               return false;
            }

            if (p_157918_.m_142050_()) {
               return false;
            }
         }

         return p_157918_ == null || !p_157918_.m_5833_();
      }
   }

   private void m_157905_(Level p_157906_, GameEvent p_157907_, BlockPos p_157908_, BlockPos p_157909_) {
      this.f_157890_ = Optional.of(p_157907_);
      if (p_157906_ instanceof ServerLevel) {
         this.f_157891_ = Mth.m_14107_(Math.sqrt(p_157908_.m_175582_(p_157909_, false)));
         this.f_157892_ = this.f_157891_;
         ((ServerLevel)p_157906_).m_143283_(new VibrationPath(p_157908_, this.f_157887_, this.f_157892_));
      }

   }

   private boolean m_157910_(Level p_157911_, BlockPos p_157912_, BlockPos p_157913_) {
      return p_157911_.m_151353_(new ClipBlockStateContext(Vec3.m_82512_(p_157912_), Vec3.m_82512_(p_157913_), (p_157915_) -> {
         return p_157915_.m_60620_(BlockTags.f_144272_);
      })).m_6662_() == HitResult.Type.BLOCK;
   }

   public interface VibrationListenerConfig {
      boolean m_142008_(Level p_157924_, GameEventListener p_157925_, BlockPos p_157926_, GameEvent p_157927_, @Nullable Entity p_157928_);

      void m_142190_(Level p_157920_, GameEventListener p_157921_, GameEvent p_157922_, int p_157923_);
   }
}