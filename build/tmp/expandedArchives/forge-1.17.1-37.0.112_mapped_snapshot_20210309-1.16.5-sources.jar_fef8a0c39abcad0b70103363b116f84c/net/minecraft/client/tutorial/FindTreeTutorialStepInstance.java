package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FindTreeTutorialStepInstance implements TutorialStepInstance {
   private static final int f_175012_ = 6000;
   private static final Set<Block> f_120488_ = Sets.newHashSet(Blocks.f_49999_, Blocks.f_50000_, Blocks.f_50001_, Blocks.f_50002_, Blocks.f_50003_, Blocks.f_50004_, Blocks.f_50686_, Blocks.f_50695_, Blocks.f_50011_, Blocks.f_50012_, Blocks.f_50013_, Blocks.f_50014_, Blocks.f_50015_, Blocks.f_50043_, Blocks.f_50688_, Blocks.f_50697_, Blocks.f_50050_, Blocks.f_50051_, Blocks.f_50052_, Blocks.f_50053_, Blocks.f_50054_, Blocks.f_50055_, Blocks.f_50451_, Blocks.f_50692_, Blocks.f_152470_, Blocks.f_152471_);
   private static final Component f_120489_ = new TranslatableComponent("tutorial.find_tree.title");
   private static final Component f_120490_ = new TranslatableComponent("tutorial.find_tree.description");
   private final Tutorial f_120491_;
   private TutorialToast f_120492_;
   private int f_120493_;

   public FindTreeTutorialStepInstance(Tutorial p_120496_) {
      this.f_120491_ = p_120496_;
   }

   public void m_7737_() {
      ++this.f_120493_;
      if (!this.f_120491_.m_175028_()) {
         this.f_120491_.m_120588_(TutorialSteps.NONE);
      } else {
         if (this.f_120493_ == 1) {
            LocalPlayer localplayer = this.f_120491_.m_120597_().f_91074_;
            if (localplayer != null) {
               for(Block block : f_120488_) {
                  if (localplayer.m_150109_().m_36063_(new ItemStack(block))) {
                     this.f_120491_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                     return;
                  }
               }

               if (m_120503_(localplayer)) {
                  this.f_120491_.m_120588_(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if (this.f_120493_ >= 6000 && this.f_120492_ == null) {
            this.f_120492_ = new TutorialToast(TutorialToast.Icons.TREE, f_120489_, f_120490_, false);
            this.f_120491_.m_120597_().m_91300_().m_94922_(this.f_120492_);
         }

      }
   }

   public void m_7736_() {
      if (this.f_120492_ != null) {
         this.f_120492_.m_94968_();
         this.f_120492_ = null;
      }

   }

   public void m_7554_(ClientLevel p_120501_, HitResult p_120502_) {
      if (p_120502_.m_6662_() == HitResult.Type.BLOCK) {
         BlockState blockstate = p_120501_.m_8055_(((BlockHitResult)p_120502_).m_82425_());
         if (f_120488_.contains(blockstate.m_60734_())) {
            this.f_120491_.m_120588_(TutorialSteps.PUNCH_TREE);
         }
      }

   }

   public void m_6967_(ItemStack p_120499_) {
      for(Block block : f_120488_) {
         if (p_120499_.m_150930_(block.m_5456_())) {
            this.f_120491_.m_120588_(TutorialSteps.CRAFT_PLANKS);
            return;
         }
      }

   }

   public static boolean m_120503_(LocalPlayer p_120504_) {
      for(Block block : f_120488_) {
         if (p_120504_.m_108630_().m_13015_(Stats.f_12949_.m_12902_(block)) > 0) {
            return true;
         }
      }

      return false;
   }
}