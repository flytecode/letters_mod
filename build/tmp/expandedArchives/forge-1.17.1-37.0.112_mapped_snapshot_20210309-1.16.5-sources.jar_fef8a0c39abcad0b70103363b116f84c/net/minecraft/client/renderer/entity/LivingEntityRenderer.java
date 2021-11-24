package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.scores.Team;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
   private static final Logger f_115289_ = LogManager.getLogger();
   private static final float f_174287_ = 0.1F;
   protected M f_115290_;
   protected final List<RenderLayer<T, M>> f_115291_ = Lists.newArrayList();

   public LivingEntityRenderer(EntityRendererProvider.Context p_174289_, M p_174290_, float p_174291_) {
      super(p_174289_);
      this.f_115290_ = p_174290_;
      this.f_114477_ = p_174291_;
   }

   public final boolean m_115326_(RenderLayer<T, M> p_115327_) {
      return this.f_115291_.add(p_115327_);
   }

   public M m_7200_() {
      return this.f_115290_;
   }

   public void m_7392_(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T, M>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_))) return;
      p_115311_.m_85836_();
      this.f_115290_.f_102608_ = this.m_115342_(p_115308_, p_115310_);

      boolean shouldSit = p_115308_.m_20159_() && (p_115308_.m_20202_() != null && p_115308_.m_20202_().shouldRiderSit());
      this.f_115290_.f_102609_ = shouldSit;
      this.f_115290_.f_102610_ = p_115308_.m_6162_();
      float f = Mth.m_14189_(p_115310_, p_115308_.f_20884_, p_115308_.f_20883_);
      float f1 = Mth.m_14189_(p_115310_, p_115308_.f_20886_, p_115308_.f_20885_);
      float f2 = f1 - f;
      if (shouldSit && p_115308_.m_20202_() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)p_115308_.m_20202_();
         f = Mth.m_14189_(p_115310_, livingentity.f_20884_, livingentity.f_20883_);
         f2 = f1 - f;
         float f3 = Mth.m_14177_(f2);
         if (f3 < -85.0F) {
            f3 = -85.0F;
         }

         if (f3 >= 85.0F) {
            f3 = 85.0F;
         }

         f = f1 - f3;
         if (f3 * f3 > 2500.0F) {
            f += f3 * 0.2F;
         }

         f2 = f1 - f;
      }

      float f6 = Mth.m_14179_(p_115310_, p_115308_.f_19860_, p_115308_.m_146909_());
      if (p_115308_.m_20089_() == Pose.SLEEPING) {
         Direction direction = p_115308_.m_21259_();
         if (direction != null) {
            float f4 = p_115308_.m_20236_(Pose.STANDING) - 0.1F;
            p_115311_.m_85837_((double)((float)(-direction.m_122429_()) * f4), 0.0D, (double)((float)(-direction.m_122431_()) * f4));
         }
      }

      float f7 = this.m_6930_(p_115308_, p_115310_);
      this.m_7523_(p_115308_, p_115311_, f7, f, p_115310_);
      p_115311_.m_85841_(-1.0F, -1.0F, 1.0F);
      this.m_7546_(p_115308_, p_115311_, p_115310_);
      p_115311_.m_85837_(0.0D, (double)-1.501F, 0.0D);
      float f8 = 0.0F;
      float f5 = 0.0F;
      if (!shouldSit && p_115308_.m_6084_()) {
         f8 = Mth.m_14179_(p_115310_, p_115308_.f_20923_, p_115308_.f_20924_);
         f5 = p_115308_.f_20925_ - p_115308_.f_20924_ * (1.0F - p_115310_);
         if (p_115308_.m_6162_()) {
            f5 *= 3.0F;
         }

         if (f8 > 1.0F) {
            f8 = 1.0F;
         }
      }

      this.f_115290_.m_6839_(p_115308_, f5, f8, p_115310_);
      this.f_115290_.m_6973_(p_115308_, f5, f8, f7, f2, f6);
      Minecraft minecraft = Minecraft.m_91087_();
      boolean flag = this.m_5933_(p_115308_);
      boolean flag1 = !flag && !p_115308_.m_20177_(minecraft.f_91074_);
      boolean flag2 = minecraft.m_91314_(p_115308_);
      RenderType rendertype = this.m_7225_(p_115308_, flag, flag1, flag2);
      if (rendertype != null) {
         VertexConsumer vertexconsumer = p_115312_.m_6299_(rendertype);
         int i = m_115338_(p_115308_, this.m_6931_(p_115308_, p_115310_));
         this.f_115290_.m_7695_(p_115311_, vertexconsumer, p_115313_, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
      }

      if (!p_115308_.m_5833_()) {
         for(RenderLayer<T, M> renderlayer : this.f_115291_) {
            renderlayer.m_6494_(p_115311_, p_115312_, p_115313_, p_115308_, f5, f8, p_115310_, f7, f2, f6);
         }
      }

      p_115311_.m_85849_();
      super.m_7392_(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T, M>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_));
   }

   @Nullable
   protected RenderType m_7225_(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
      ResourceLocation resourcelocation = this.m_5478_(p_115322_);
      if (p_115324_) {
         return RenderType.m_110467_(resourcelocation);
      } else if (p_115323_) {
         return this.f_115290_.m_103119_(resourcelocation);
      } else {
         return p_115325_ ? RenderType.m_110491_(resourcelocation) : null;
      }
   }

   public static int m_115338_(LivingEntity p_115339_, float p_115340_) {
      return OverlayTexture.m_118093_(OverlayTexture.m_118088_(p_115340_), OverlayTexture.m_118096_(p_115339_.f_20916_ > 0 || p_115339_.f_20919_ > 0));
   }

   protected boolean m_5933_(T p_115341_) {
      return !p_115341_.m_20145_();
   }

   private static float m_115328_(Direction p_115329_) {
      switch(p_115329_) {
      case SOUTH:
         return 90.0F;
      case WEST:
         return 0.0F;
      case NORTH:
         return 270.0F;
      case EAST:
         return 180.0F;
      default:
         return 0.0F;
      }
   }

   protected boolean m_5936_(T p_115304_) {
      return p_115304_.m_146890_();
   }

   protected void m_7523_(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
      if (this.m_5936_(p_115317_)) {
         p_115320_ += (float)(Math.cos((double)p_115317_.f_19797_ * 3.25D) * Math.PI * (double)0.4F);
      }

      Pose pose = p_115317_.m_20089_();
      if (pose != Pose.SLEEPING) {
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_115320_));
      }

      if (p_115317_.f_20919_ > 0) {
         float f = ((float)p_115317_.f_20919_ + p_115321_ - 1.0F) / 20.0F * 1.6F;
         f = Mth.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(f * this.m_6441_(p_115317_)));
      } else if (p_115317_.m_21209_()) {
         p_115318_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F - p_115317_.m_146909_()));
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(((float)p_115317_.f_19797_ + p_115321_) * -75.0F));
      } else if (pose == Pose.SLEEPING) {
         Direction direction = p_115317_.m_21259_();
         float f1 = direction != null ? m_115328_(direction) : p_115320_;
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
         p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(this.m_6441_(p_115317_)));
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
      } else if (p_115317_.m_8077_() || p_115317_ instanceof Player) {
         String s = ChatFormatting.m_126649_(p_115317_.m_7755_().getString());
         if (("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(p_115317_ instanceof Player) || ((Player)p_115317_).m_36170_(PlayerModelPart.CAPE))) {
            p_115318_.m_85837_(0.0D, (double)(p_115317_.m_20206_() + 0.1F), 0.0D);
            p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         }
      }

   }

   protected float m_115342_(T p_115343_, float p_115344_) {
      return p_115343_.m_21324_(p_115344_);
   }

   protected float m_6930_(T p_115305_, float p_115306_) {
      return (float)p_115305_.f_19797_ + p_115306_;
   }

   protected float m_6441_(T p_115337_) {
      return 90.0F;
   }

   protected float m_6931_(T p_115334_, float p_115335_) {
      return 0.0F;
   }

   protected void m_7546_(T p_115314_, PoseStack p_115315_, float p_115316_) {
   }

   protected boolean m_6512_(T p_115333_) {
      double d0 = this.f_114476_.m_114471_(p_115333_);
      float f = p_115333_.m_20163_() ? 32.0F : 64.0F;
      if (d0 >= (double)(f * f)) {
         return false;
      } else {
         Minecraft minecraft = Minecraft.m_91087_();
         LocalPlayer localplayer = minecraft.f_91074_;
         boolean flag = !p_115333_.m_20177_(localplayer);
         if (p_115333_ != localplayer) {
            Team team = p_115333_.m_5647_();
            Team team1 = localplayer.m_5647_();
            if (team != null) {
               Team.Visibility team$visibility = team.m_7470_();
               switch(team$visibility) {
               case ALWAYS:
                  return flag;
               case NEVER:
                  return false;
               case HIDE_FOR_OTHER_TEAMS:
                  return team1 == null ? flag : team.m_83536_(team1) && (team.m_6259_() || flag);
               case HIDE_FOR_OWN_TEAM:
                  return team1 == null ? flag : !team.m_83536_(team1) && flag;
               default:
                  return true;
               }
            }
         }

         return Minecraft.m_91404_() && p_115333_ != minecraft.m_91288_() && flag && !p_115333_.m_20160_();
      }
   }
}
