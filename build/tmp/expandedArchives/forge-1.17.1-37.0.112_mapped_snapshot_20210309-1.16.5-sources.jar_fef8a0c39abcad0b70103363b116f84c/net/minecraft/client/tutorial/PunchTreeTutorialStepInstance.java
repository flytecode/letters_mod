package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PunchTreeTutorialStepInstance implements TutorialStepInstance {
   private static final int f_175019_ = 600;
   private static final Component f_120541_ = new TranslatableComponent("tutorial.punch_tree.title");
   private static final Component f_120542_ = new TranslatableComponent("tutorial.punch_tree.description", Tutorial.m_120592_("attack"));
   private final Tutorial f_120543_;
   private TutorialToast f_120544_;
   private int f_120545_;
   private int f_120546_;

   public PunchTreeTutorialStepInstance(Tutorial p_120549_) {
      this.f_120543_ = p_120549_;
   }

   public void m_7737_() {
      ++this.f_120545_;
      if (!this.f_120543_.m_175028_()) {
         this.f_120543_.m_120588_(TutorialSteps.NONE);
      } else {
         if (this.f_120545_ == 1) {
            LocalPlayer localplayer = this.f_120543_.m_120597_().f_91074_;
            if (localplayer != null) {
               if (localplayer.m_150109_().m_36001_(ItemTags.f_13182_)) {
                  this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                  return;
               }

               if (FindTreeTutorialStepInstance.m_120503_(localplayer)) {
                  this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if ((this.f_120545_ >= 600 || this.f_120546_ > 3) && this.f_120544_ == null) {
            this.f_120544_ = new TutorialToast(TutorialToast.Icons.TREE, f_120541_, f_120542_, true);
            this.f_120543_.m_120597_().m_91300_().m_94922_(this.f_120544_);
         }

      }
   }

   public void m_7736_() {
      if (this.f_120544_ != null) {
         this.f_120544_.m_94968_();
         this.f_120544_ = null;
      }

   }

   public void m_7464_(ClientLevel p_120554_, BlockPos p_120555_, BlockState p_120556_, float p_120557_) {
      boolean flag = p_120556_.m_60620_(BlockTags.f_13106_);
      if (flag && p_120557_ > 0.0F) {
         if (this.f_120544_ != null) {
            this.f_120544_.m_94962_(p_120557_);
         }

         if (p_120557_ >= 1.0F) {
            this.f_120543_.m_120588_(TutorialSteps.OPEN_INVENTORY);
         }
      } else if (this.f_120544_ != null) {
         this.f_120544_.m_94962_(0.0F);
      } else if (flag) {
         ++this.f_120546_;
      }

   }

   public void m_6967_(ItemStack p_120552_) {
      if (p_120552_.m_150922_(ItemTags.f_13182_)) {
         this.f_120543_.m_120588_(TutorialSteps.CRAFT_PLANKS);
      }
   }
}