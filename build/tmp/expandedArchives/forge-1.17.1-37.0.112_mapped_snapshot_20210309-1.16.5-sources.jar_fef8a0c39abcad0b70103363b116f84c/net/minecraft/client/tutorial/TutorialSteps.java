package net.minecraft.client.tutorial;

import java.util.function.Function;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum TutorialSteps {
   MOVEMENT("movement", MovementTutorialStepInstance::new),
   FIND_TREE("find_tree", FindTreeTutorialStepInstance::new),
   PUNCH_TREE("punch_tree", PunchTreeTutorialStepInstance::new),
   OPEN_INVENTORY("open_inventory", OpenInventoryTutorialStep::new),
   CRAFT_PLANKS("craft_planks", CraftPlanksTutorialStep::new),
   NONE("none", CompletedTutorialStepInstance::new);

   private final String f_120630_;
   private final Function<Tutorial, ? extends TutorialStepInstance> f_120631_;

   private <T extends TutorialStepInstance> TutorialSteps(String p_120637_, Function<Tutorial, T> p_120638_) {
      this.f_120630_ = p_120637_;
      this.f_120631_ = p_120638_;
   }

   public TutorialStepInstance m_120640_(Tutorial p_120641_) {
      return this.f_120631_.apply(p_120641_);
   }

   public String m_120639_() {
      return this.f_120630_;
   }

   public static TutorialSteps m_120642_(String p_120643_) {
      for(TutorialSteps tutorialsteps : values()) {
         if (tutorialsteps.f_120630_.equals(p_120643_)) {
            return tutorialsteps;
         }
      }

      return NONE;
   }
}