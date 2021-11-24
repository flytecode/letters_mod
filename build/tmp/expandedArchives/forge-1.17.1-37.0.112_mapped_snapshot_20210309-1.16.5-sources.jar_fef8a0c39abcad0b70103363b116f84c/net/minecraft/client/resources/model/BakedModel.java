package net.minecraft.client.resources.model;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface BakedModel extends net.minecraftforge.client.extensions.IForgeBakedModel {
   /**@deprecated Forge: Use {@link net.minecraftforge.client.extensions.IForgeBakedModel#getQuads(BlockState, Direction, Random, net.minecraftforge.client.model.data.IModelData)}*/
   @Deprecated
   List<BakedQuad> m_6840_(@Nullable BlockState p_119123_, @Nullable Direction p_119124_, Random p_119125_);

   boolean m_7541_();

   boolean m_7539_();

   boolean m_7547_();

   boolean m_7521_();

   /**@deprecated Forge: Use {@link net.minecraftforge.client.extensions.IForgeBakedModel#getParticleIcon(net.minecraftforge.client.model.data.IModelData)}*/
   @Deprecated
   TextureAtlasSprite m_6160_();

   /**@deprecated Forge: Use {@link net.minecraftforge.client.extensions.IForgeBakedModel#handlePerspective(ItemTransforms.TransformType, com.mojang.blaze3d.vertex.PoseStack)} instead */
   @Deprecated
   default ItemTransforms m_7442_() { return ItemTransforms.f_111786_; }

   ItemOverrides m_7343_();
}
