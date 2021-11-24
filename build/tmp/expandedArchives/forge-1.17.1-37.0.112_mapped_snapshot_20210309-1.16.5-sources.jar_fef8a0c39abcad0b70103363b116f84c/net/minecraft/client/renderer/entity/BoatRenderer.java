package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoatRenderer extends EntityRenderer<Boat> {
   private final Map<Boat.Type, Pair<ResourceLocation, BoatModel>> f_173934_;

   public BoatRenderer(EntityRendererProvider.Context p_173936_) {
      super(p_173936_);
      this.f_114477_ = 0.8F;
      this.f_173934_ = Stream.of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
         return p_173938_;
      }, (p_173941_) -> {
         return Pair.of(new ResourceLocation("textures/entity/boat/" + p_173941_.m_38429_() + ".png"), new BoatModel(p_173936_.m_174023_(ModelLayers.m_171289_(p_173941_))));
      }));
   }

   public void m_7392_(Boat p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
      p_113932_.m_85836_();
      p_113932_.m_85837_(0.0D, 0.375D, 0.0D);
      p_113932_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_113930_));
      float f = (float)p_113929_.m_38385_() - p_113931_;
      float f1 = p_113929_.m_38384_() - p_113931_;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      if (f > 0.0F) {
         p_113932_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14031_(f) * f * f1 / 10.0F * (float)p_113929_.m_38386_()));
      }

      float f2 = p_113929_.m_38352_(p_113931_);
      if (!Mth.m_14033_(f2, 0.0F)) {
         p_113932_.m_85845_(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), p_113929_.m_38352_(p_113931_), true));
      }

      Pair<ResourceLocation, BoatModel> pair = getModelWithLocation(p_113929_);
      ResourceLocation resourcelocation = pair.getFirst();
      BoatModel boatmodel = pair.getSecond();
      p_113932_.m_85841_(-1.0F, -1.0F, 1.0F);
      p_113932_.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      boatmodel.m_6973_(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer vertexconsumer = p_113933_.m_6299_(boatmodel.m_103119_(resourcelocation));
      boatmodel.m_7695_(p_113932_, vertexconsumer, p_113934_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!p_113929_.m_5842_()) {
         VertexConsumer vertexconsumer1 = p_113933_.m_6299_(RenderType.m_110478_());
         boatmodel.m_102282_().m_104301_(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.f_118083_);
      }

      p_113932_.m_85849_();
      super.m_7392_(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
   }

   @Deprecated // forge: override getModelWithLocation to change the texture / model
   public ResourceLocation m_5478_(Boat p_113927_) {
      return getModelWithLocation(p_113927_).getFirst();
   }

   public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) { return this.f_173934_.get(boat.m_38387_()); }
}
