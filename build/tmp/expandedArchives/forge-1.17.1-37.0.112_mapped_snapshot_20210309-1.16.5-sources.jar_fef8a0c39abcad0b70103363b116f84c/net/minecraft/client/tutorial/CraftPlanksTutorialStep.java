package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CraftPlanksTutorialStep implements TutorialStepInstance {
   private static final int f_175011_ = 1200;
   private static final Component f_120460_ = new TranslatableComponent("tutorial.craft_planks.title");
   private static final Component f_120461_ = new TranslatableComponent("tutorial.craft_planks.description");
   private final Tutorial f_120462_;
   private TutorialToast f_120463_;
   private int f_120464_;

   public CraftPlanksTutorialStep(Tutorial p_120467_) {
      this.f_120462_ = p_120467_;
   }

   public void m_7737_() {
      ++this.f_120464_;
      if (!this.f_120462_.m_175028_()) {
         this.f_120462_.m_120588_(TutorialSteps.NONE);
      } else {
         if (this.f_120464_ == 1) {
            LocalPlayer localplayer = this.f_120462_.m_120597_().f_91074_;
            if (localplayer != null) {
               if (localplayer.m_150109_().m_36001_(ItemTags.f_13168_)) {
                  this.f_120462_.m_120588_(TutorialSteps.NONE);
                  return;
               }

               if (m_120471_(localplayer, ItemTags.f_13168_)) {
                  this.f_120462_.m_120588_(TutorialSteps.NONE);
                  return;
               }
            }
         }

         if (this.f_120464_ >= 1200 && this.f_120463_ == null) {
            this.f_120463_ = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, f_120460_, f_120461_, false);
            this.f_120462_.m_120597_().m_91300_().m_94922_(this.f_120463_);
         }

      }
   }

   public void m_7736_() {
      if (this.f_120463_ != null) {
         this.f_120463_.m_94968_();
         this.f_120463_ = null;
      }

   }

   public void m_6967_(ItemStack p_120470_) {
      if (p_120470_.m_150922_(ItemTags.f_13168_)) {
         this.f_120462_.m_120588_(TutorialSteps.NONE);
      }

   }

   public static boolean m_120471_(LocalPlayer p_120472_, Tag<Item> p_120473_) {
      for(Item item : p_120473_.m_6497_()) {
         if (p_120472_.m_108630_().m_13015_(Stats.f_12981_.m_12902_(item)) > 0) {
            return true;
         }
      }

      return false;
   }
}