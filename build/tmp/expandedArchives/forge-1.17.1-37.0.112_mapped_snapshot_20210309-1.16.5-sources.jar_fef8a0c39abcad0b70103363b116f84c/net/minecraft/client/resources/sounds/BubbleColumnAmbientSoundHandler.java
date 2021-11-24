package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BubbleColumnAmbientSoundHandler implements AmbientSoundHandler {
   private final LocalPlayer f_119662_;
   private boolean f_119663_;
   private boolean f_119664_ = true;

   public BubbleColumnAmbientSoundHandler(LocalPlayer p_119666_) {
      this.f_119662_ = p_119666_;
   }

   public void m_7551_() {
      Level level = this.f_119662_.f_19853_;
      BlockState blockstate = level.m_46847_(this.f_119662_.m_142469_().m_82377_(0.0D, (double)-0.4F, 0.0D).m_82406_(1.0E-6D)).filter((p_119669_) -> {
         return p_119669_.m_60713_(Blocks.f_50628_);
      }).findFirst().orElse((BlockState)null);
      if (blockstate != null) {
         if (!this.f_119663_ && !this.f_119664_ && blockstate.m_60713_(Blocks.f_50628_) && !this.f_119662_.m_5833_()) {
            boolean flag = blockstate.m_61143_(BubbleColumnBlock.f_50956_);
            if (flag) {
               this.f_119662_.m_5496_(SoundEvents.f_11777_, 1.0F, 1.0F);
            } else {
               this.f_119662_.m_5496_(SoundEvents.f_11775_, 1.0F, 1.0F);
            }
         }

         this.f_119663_ = true;
      } else {
         this.f_119663_ = false;
      }

      this.f_119664_ = false;
   }
}