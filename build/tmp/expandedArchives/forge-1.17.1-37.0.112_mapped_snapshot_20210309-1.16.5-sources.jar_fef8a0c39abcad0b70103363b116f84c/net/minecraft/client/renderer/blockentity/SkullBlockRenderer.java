package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkullBlockRenderer implements BlockEntityRenderer<SkullBlockEntity> {
   private final Map<SkullBlock.Type, SkullModelBase> f_173658_;
   private static final Map<SkullBlock.Type, ResourceLocation> f_112519_ = Util.m_137469_(Maps.newHashMap(), (p_112552_) -> {
      p_112552_.put(SkullBlock.Types.SKELETON, new ResourceLocation("textures/entity/skeleton/skeleton.png"));
      p_112552_.put(SkullBlock.Types.WITHER_SKELETON, new ResourceLocation("textures/entity/skeleton/wither_skeleton.png"));
      p_112552_.put(SkullBlock.Types.ZOMBIE, new ResourceLocation("textures/entity/zombie/zombie.png"));
      p_112552_.put(SkullBlock.Types.CREEPER, new ResourceLocation("textures/entity/creeper/creeper.png"));
      p_112552_.put(SkullBlock.Types.DRAGON, new ResourceLocation("textures/entity/enderdragon/dragon.png"));
      p_112552_.put(SkullBlock.Types.PLAYER, DefaultPlayerSkin.m_118626_());
   });

   public static Map<SkullBlock.Type, SkullModelBase> m_173661_(EntityModelSet p_173662_) {
      Builder<SkullBlock.Type, SkullModelBase> builder = ImmutableMap.builder();
      builder.put(SkullBlock.Types.SKELETON, new SkullModel(p_173662_.m_171103_(ModelLayers.f_171240_)));
      builder.put(SkullBlock.Types.WITHER_SKELETON, new SkullModel(p_173662_.m_171103_(ModelLayers.f_171219_)));
      builder.put(SkullBlock.Types.PLAYER, new SkullModel(p_173662_.m_171103_(ModelLayers.f_171163_)));
      builder.put(SkullBlock.Types.ZOMBIE, new SkullModel(p_173662_.m_171103_(ModelLayers.f_171224_)));
      builder.put(SkullBlock.Types.CREEPER, new SkullModel(p_173662_.m_171103_(ModelLayers.f_171130_)));
      builder.put(SkullBlock.Types.DRAGON, new DragonHeadModel(p_173662_.m_171103_(ModelLayers.f_171135_)));
      return builder.build();
   }

   public SkullBlockRenderer(BlockEntityRendererProvider.Context p_173660_) {
      this.f_173658_ = m_173661_(p_173660_.m_173585_());
   }

   public void m_6922_(SkullBlockEntity p_112534_, float p_112535_, PoseStack p_112536_, MultiBufferSource p_112537_, int p_112538_, int p_112539_) {
      float f = p_112534_.m_59762_(p_112535_);
      BlockState blockstate = p_112534_.m_58900_();
      boolean flag = blockstate.m_60734_() instanceof WallSkullBlock;
      Direction direction = flag ? blockstate.m_61143_(WallSkullBlock.f_58097_) : null;
      float f1 = 22.5F * (float)(flag ? (2 + direction.m_122416_()) * 4 : blockstate.m_61143_(SkullBlock.f_56314_));
      SkullBlock.Type skullblock$type = ((AbstractSkullBlock)blockstate.m_60734_()).m_48754_();
      SkullModelBase skullmodelbase = this.f_173658_.get(skullblock$type);
      RenderType rendertype = m_112523_(skullblock$type, p_112534_.m_59779_());
      m_173663_(direction, f1, f, p_112536_, p_112537_, p_112538_, skullmodelbase, rendertype);
   }

   public static void m_173663_(@Nullable Direction p_173664_, float p_173665_, float p_173666_, PoseStack p_173667_, MultiBufferSource p_173668_, int p_173669_, SkullModelBase p_173670_, RenderType p_173671_) {
      p_173667_.m_85836_();
      if (p_173664_ == null) {
         p_173667_.m_85837_(0.5D, 0.0D, 0.5D);
      } else {
         float f = 0.25F;
         p_173667_.m_85837_((double)(0.5F - (float)p_173664_.m_122429_() * 0.25F), 0.25D, (double)(0.5F - (float)p_173664_.m_122431_() * 0.25F));
      }

      p_173667_.m_85841_(-1.0F, -1.0F, 1.0F);
      VertexConsumer vertexconsumer = p_173668_.m_6299_(p_173671_);
      p_173670_.m_142698_(p_173666_, p_173665_, 0.0F);
      p_173670_.m_7695_(p_173667_, vertexconsumer, p_173669_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_173667_.m_85849_();
   }

   public static RenderType m_112523_(SkullBlock.Type p_112524_, @Nullable GameProfile p_112525_) {
      ResourceLocation resourcelocation = f_112519_.get(p_112524_);
      if (p_112524_ == SkullBlock.Types.PLAYER && p_112525_ != null) {
         Minecraft minecraft = Minecraft.m_91087_();
         Map<Type, MinecraftProfileTexture> map = minecraft.m_91109_().m_118815_(p_112525_);
         return map.containsKey(Type.SKIN) ? RenderType.m_110473_(minecraft.m_91109_().m_118825_(map.get(Type.SKIN), Type.SKIN)) : RenderType.m_110458_(DefaultPlayerSkin.m_118627_(Player.m_36198_(p_112525_)));
      } else {
         return RenderType.m_110464_(resourcelocation);
      }
   }
}