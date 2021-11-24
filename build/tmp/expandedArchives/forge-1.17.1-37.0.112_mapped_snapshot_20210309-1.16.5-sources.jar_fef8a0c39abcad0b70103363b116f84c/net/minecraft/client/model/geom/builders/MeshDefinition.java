package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.PartPose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MeshDefinition {
   private PartDefinition f_171574_ = new PartDefinition(ImmutableList.of(), PartPose.f_171404_);

   public PartDefinition m_171576_() {
      return this.f_171574_;
   }
}