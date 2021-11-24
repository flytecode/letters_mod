package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoatModel extends ListModel<Boat> {
   private static final String f_170451_ = "left_paddle";
   private static final String f_170452_ = "right_paddle";
   private static final String f_170453_ = "water_patch";
   private static final String f_170454_ = "bottom";
   private static final String f_170455_ = "back";
   private static final String f_170456_ = "front";
   private static final String f_170457_ = "right";
   private static final String f_170458_ = "left";
   private final ModelPart f_170459_;
   private final ModelPart f_170460_;
   private final ModelPart f_102257_;
   private final ImmutableList<ModelPart> f_102258_;

   public BoatModel(ModelPart p_170462_) {
      this.f_170459_ = p_170462_.m_171324_("left_paddle");
      this.f_170460_ = p_170462_.m_171324_("right_paddle");
      this.f_102257_ = p_170462_.m_171324_("water_patch");
      this.f_102258_ = ImmutableList.of(p_170462_.m_171324_("bottom"), p_170462_.m_171324_("back"), p_170462_.m_171324_("front"), p_170462_.m_171324_("right"), p_170462_.m_171324_("left"), this.f_170459_, this.f_170460_);
   }

   public static LayerDefinition m_170463_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 32;
      int j = 6;
      int k = 20;
      int l = 4;
      int i1 = 28;
      partdefinition.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), PartPose.m_171423_(0.0F, 3.0F, 1.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), PartPose.m_171423_(-15.0F, 4.0F, 4.0F, 0.0F, ((float)Math.PI * 1.5F), 0.0F));
      partdefinition.m_171599_("front", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171481_(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F), PartPose.m_171423_(15.0F, 4.0F, 0.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
      partdefinition.m_171599_("right", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171481_(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), PartPose.m_171423_(0.0F, 4.0F, -9.0F, 0.0F, (float)Math.PI, 0.0F));
      partdefinition.m_171599_("left", CubeListBuilder.m_171558_().m_171514_(0, 43).m_171481_(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), PartPose.m_171419_(0.0F, 4.0F, 9.0F));
      int j1 = 20;
      int k1 = 7;
      int l1 = 6;
      float f = -5.0F;
      partdefinition.m_171599_("left_paddle", CubeListBuilder.m_171558_().m_171514_(62, 0).m_171481_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).m_171481_(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.m_171423_(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
      partdefinition.m_171599_("right_paddle", CubeListBuilder.m_171558_().m_171514_(62, 20).m_171481_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).m_171481_(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.m_171423_(3.0F, -5.0F, -9.0F, 0.0F, (float)Math.PI, 0.19634955F));
      partdefinition.m_171599_("water_patch", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), PartPose.m_171423_(0.0F, -3.0F, 1.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void m_6973_(Boat p_102269_, float p_102270_, float p_102271_, float p_102272_, float p_102273_, float p_102274_) {
      m_170464_(p_102269_, 0, this.f_170459_, p_102270_);
      m_170464_(p_102269_, 1, this.f_170460_, p_102270_);
   }

   public ImmutableList<ModelPart> m_6195_() {
      return this.f_102258_;
   }

   public ModelPart m_102282_() {
      return this.f_102257_;
   }

   private static void m_170464_(Boat p_170465_, int p_170466_, ModelPart p_170467_, float p_170468_) {
      float f = p_170465_.m_38315_(p_170466_, p_170468_);
      p_170467_.f_104203_ = Mth.m_144920_((-(float)Math.PI / 3F), -0.2617994F, (Mth.m_14031_(-f) + 1.0F) / 2.0F);
      p_170467_.f_104204_ = Mth.m_144920_((-(float)Math.PI / 4F), ((float)Math.PI / 4F), (Mth.m_14031_(-f + 1.0F) + 1.0F) / 2.0F);
      if (p_170466_ == 1) {
         p_170467_.f_104204_ = (float)Math.PI - p_170467_.f_104204_;
      }

   }
}