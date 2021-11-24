package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelBlockRenderer {
   private static final int f_173403_ = 0;
   private static final int f_173404_ = 1;
   static final Direction[] f_173405_ = Direction.values();
   private final BlockColors f_110995_;
   private static final int f_173406_ = 100;
   static final ThreadLocal<ModelBlockRenderer.Cache> f_110996_ = ThreadLocal.withInitial(ModelBlockRenderer.Cache::new);

   public ModelBlockRenderer(BlockColors p_110999_) {
      this.f_110995_ = p_110999_;
   }

   @Deprecated //Forge: Model data argument
   public boolean m_111047_(BlockAndTintGetter p_111048_, BakedModel p_111049_, BlockState p_111050_, BlockPos p_111051_, PoseStack p_111052_, VertexConsumer p_111053_, boolean p_111054_, Random p_111055_, long p_111056_, int p_111057_) {
       return tesselateBlock(p_111048_, p_111049_, p_111050_, p_111051_, p_111052_, p_111053_, p_111054_, p_111055_, p_111056_, p_111057_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean tesselateBlock(BlockAndTintGetter p_111048_, BakedModel p_111049_, BlockState p_111050_, BlockPos p_111051_, PoseStack p_111052_, VertexConsumer p_111053_, boolean p_111054_, Random p_111055_, long p_111056_, int p_111057_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = Minecraft.m_91086_() && p_111050_.getLightEmission(p_111048_, p_111051_) == 0 && p_111049_.m_7541_();
      Vec3 vec3 = p_111050_.m_60824_(p_111048_, p_111051_);
      p_111052_.m_85837_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
      modelData = p_111049_.getModelData(p_111048_, p_111051_, p_111050_, modelData);

      try {
         return flag ? this.tesselateWithAO(p_111048_, p_111049_, p_111050_, p_111051_, p_111052_, p_111053_, p_111054_, p_111055_, p_111056_, p_111057_, modelData) : this.tesselateWithoutAO(p_111048_, p_111049_, p_111050_, p_111051_, p_111052_, p_111053_, p_111054_, p_111055_, p_111056_, p_111057_, modelData);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Tesselating block model");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Block model being tesselated");
         CrashReportCategory.m_178950_(crashreportcategory, p_111048_, p_111051_, p_111050_);
         crashreportcategory.m_128159_("Using AO", flag);
         throw new ReportedException(crashreport);
      }
   }

   @Deprecated //Forge: Model data argument
   public boolean m_111078_(BlockAndTintGetter p_111079_, BakedModel p_111080_, BlockState p_111081_, BlockPos p_111082_, PoseStack p_111083_, VertexConsumer p_111084_, boolean p_111085_, Random p_111086_, long p_111087_, int p_111088_) {
       return tesselateWithAO(p_111079_, p_111080_, p_111081_, p_111082_, p_111083_, p_111084_, p_111085_, p_111086_, p_111087_, p_111088_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean tesselateWithAO(BlockAndTintGetter p_111079_, BakedModel p_111080_, BlockState p_111081_, BlockPos p_111082_, PoseStack p_111083_, VertexConsumer p_111084_, boolean p_111085_, Random p_111086_, long p_111087_, int p_111088_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = false;
      float[] afloat = new float[f_173405_.length * 2];
      BitSet bitset = new BitSet(3);
      ModelBlockRenderer.AmbientOcclusionFace modelblockrenderer$ambientocclusionface = new ModelBlockRenderer.AmbientOcclusionFace();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_111082_.m_122032_();

      for(Direction direction : f_173405_) {
         p_111086_.setSeed(p_111087_);
         List<BakedQuad> list = p_111080_.getQuads(p_111081_, direction, p_111086_, modelData);
         if (!list.isEmpty()) {
            blockpos$mutableblockpos.m_122159_(p_111082_, direction);
            if (!p_111085_ || Block.m_152444_(p_111081_, p_111079_, p_111082_, direction, blockpos$mutableblockpos)) {
               this.m_111012_(p_111079_, p_111081_, p_111082_, p_111083_, p_111084_, list, afloat, bitset, modelblockrenderer$ambientocclusionface, p_111088_);
               flag = true;
            }
         }
      }

      p_111086_.setSeed(p_111087_);
      List<BakedQuad> list1 = p_111080_.getQuads(p_111081_, (Direction)null, p_111086_, modelData);
      if (!list1.isEmpty()) {
         this.m_111012_(p_111079_, p_111081_, p_111082_, p_111083_, p_111084_, list1, afloat, bitset, modelblockrenderer$ambientocclusionface, p_111088_);
         flag = true;
      }

      return flag;
   }

   @Deprecated //Forge: Model data argument
   public boolean m_111090_(BlockAndTintGetter p_111091_, BakedModel p_111092_, BlockState p_111093_, BlockPos p_111094_, PoseStack p_111095_, VertexConsumer p_111096_, boolean p_111097_, Random p_111098_, long p_111099_, int p_111100_) {
       return tesselateWithoutAO(p_111091_, p_111092_, p_111093_, p_111094_, p_111095_, p_111096_, p_111097_, p_111098_, p_111099_, p_111100_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public boolean tesselateWithoutAO(BlockAndTintGetter p_111091_, BakedModel p_111092_, BlockState p_111093_, BlockPos p_111094_, PoseStack p_111095_, VertexConsumer p_111096_, boolean p_111097_, Random p_111098_, long p_111099_, int p_111100_, net.minecraftforge.client.model.data.IModelData modelData) {
      boolean flag = false;
      BitSet bitset = new BitSet(3);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_111094_.m_122032_();

      for(Direction direction : f_173405_) {
         p_111098_.setSeed(p_111099_);
         List<BakedQuad> list = p_111092_.getQuads(p_111093_, direction, p_111098_, modelData);
         if (!list.isEmpty()) {
            blockpos$mutableblockpos.m_122159_(p_111094_, direction);
            if (!p_111097_ || Block.m_152444_(p_111093_, p_111091_, p_111094_, direction, blockpos$mutableblockpos)) {
               int i = LevelRenderer.m_109537_(p_111091_, p_111093_, blockpos$mutableblockpos);
               this.m_111001_(p_111091_, p_111093_, p_111094_, i, p_111100_, false, p_111095_, p_111096_, list, bitset);
               flag = true;
            }
         }
      }

      p_111098_.setSeed(p_111099_);
      List<BakedQuad> list1 = p_111092_.getQuads(p_111093_, (Direction)null, p_111098_, modelData);
      if (!list1.isEmpty()) {
         this.m_111001_(p_111091_, p_111093_, p_111094_, -1, p_111100_, true, p_111095_, p_111096_, list1, bitset);
         flag = true;
      }

      return flag;
   }

   private void m_111012_(BlockAndTintGetter p_111013_, BlockState p_111014_, BlockPos p_111015_, PoseStack p_111016_, VertexConsumer p_111017_, List<BakedQuad> p_111018_, float[] p_111019_, BitSet p_111020_, ModelBlockRenderer.AmbientOcclusionFace p_111021_, int p_111022_) {
      for(BakedQuad bakedquad : p_111018_) {
         this.m_111039_(p_111013_, p_111014_, p_111015_, bakedquad.m_111303_(), bakedquad.m_111306_(), p_111019_, p_111020_);
         p_111021_.m_111167_(p_111013_, p_111014_, p_111015_, bakedquad.m_111306_(), p_111019_, p_111020_, bakedquad.m_111307_());
         this.m_111023_(p_111013_, p_111014_, p_111015_, p_111017_, p_111016_.m_85850_(), bakedquad, p_111021_.f_111149_[0], p_111021_.f_111149_[1], p_111021_.f_111149_[2], p_111021_.f_111149_[3], p_111021_.f_111150_[0], p_111021_.f_111150_[1], p_111021_.f_111150_[2], p_111021_.f_111150_[3], p_111022_);
      }

   }

   private void m_111023_(BlockAndTintGetter p_111024_, BlockState p_111025_, BlockPos p_111026_, VertexConsumer p_111027_, PoseStack.Pose p_111028_, BakedQuad p_111029_, float p_111030_, float p_111031_, float p_111032_, float p_111033_, int p_111034_, int p_111035_, int p_111036_, int p_111037_, int p_111038_) {
      float f;
      float f1;
      float f2;
      if (p_111029_.m_111304_()) {
         int i = this.f_110995_.m_92577_(p_111025_, p_111024_, p_111026_, p_111029_.m_111305_());
         f = (float)(i >> 16 & 255) / 255.0F;
         f1 = (float)(i >> 8 & 255) / 255.0F;
         f2 = (float)(i & 255) / 255.0F;
      } else {
         f = 1.0F;
         f1 = 1.0F;
         f2 = 1.0F;
      }

      p_111027_.m_85995_(p_111028_, p_111029_, new float[]{p_111030_, p_111031_, p_111032_, p_111033_}, f, f1, f2, new int[]{p_111034_, p_111035_, p_111036_, p_111037_}, p_111038_, true);
   }

   private void m_111039_(BlockAndTintGetter p_111040_, BlockState p_111041_, BlockPos p_111042_, int[] p_111043_, Direction p_111044_, @Nullable float[] p_111045_, BitSet p_111046_) {
      float f = 32.0F;
      float f1 = 32.0F;
      float f2 = 32.0F;
      float f3 = -32.0F;
      float f4 = -32.0F;
      float f5 = -32.0F;

      for(int i = 0; i < 4; ++i) {
         float f6 = Float.intBitsToFloat(p_111043_[i * 8]);
         float f7 = Float.intBitsToFloat(p_111043_[i * 8 + 1]);
         float f8 = Float.intBitsToFloat(p_111043_[i * 8 + 2]);
         f = Math.min(f, f6);
         f1 = Math.min(f1, f7);
         f2 = Math.min(f2, f8);
         f3 = Math.max(f3, f6);
         f4 = Math.max(f4, f7);
         f5 = Math.max(f5, f8);
      }

      if (p_111045_ != null) {
         p_111045_[Direction.WEST.m_122411_()] = f;
         p_111045_[Direction.EAST.m_122411_()] = f3;
         p_111045_[Direction.DOWN.m_122411_()] = f1;
         p_111045_[Direction.UP.m_122411_()] = f4;
         p_111045_[Direction.NORTH.m_122411_()] = f2;
         p_111045_[Direction.SOUTH.m_122411_()] = f5;
         int j = f_173405_.length;
         p_111045_[Direction.WEST.m_122411_() + j] = 1.0F - f;
         p_111045_[Direction.EAST.m_122411_() + j] = 1.0F - f3;
         p_111045_[Direction.DOWN.m_122411_() + j] = 1.0F - f1;
         p_111045_[Direction.UP.m_122411_() + j] = 1.0F - f4;
         p_111045_[Direction.NORTH.m_122411_() + j] = 1.0F - f2;
         p_111045_[Direction.SOUTH.m_122411_() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch(p_111044_) {
      case DOWN:
         p_111046_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_111046_.set(0, f1 == f4 && (f1 < 1.0E-4F || p_111041_.m_60838_(p_111040_, p_111042_)));
         break;
      case UP:
         p_111046_.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
         p_111046_.set(0, f1 == f4 && (f4 > 0.9999F || p_111041_.m_60838_(p_111040_, p_111042_)));
         break;
      case NORTH:
         p_111046_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_111046_.set(0, f2 == f5 && (f2 < 1.0E-4F || p_111041_.m_60838_(p_111040_, p_111042_)));
         break;
      case SOUTH:
         p_111046_.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
         p_111046_.set(0, f2 == f5 && (f5 > 0.9999F || p_111041_.m_60838_(p_111040_, p_111042_)));
         break;
      case WEST:
         p_111046_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_111046_.set(0, f == f3 && (f < 1.0E-4F || p_111041_.m_60838_(p_111040_, p_111042_)));
         break;
      case EAST:
         p_111046_.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
         p_111046_.set(0, f == f3 && (f3 > 0.9999F || p_111041_.m_60838_(p_111040_, p_111042_)));
      }

   }

   private void m_111001_(BlockAndTintGetter p_111002_, BlockState p_111003_, BlockPos p_111004_, int p_111005_, int p_111006_, boolean p_111007_, PoseStack p_111008_, VertexConsumer p_111009_, List<BakedQuad> p_111010_, BitSet p_111011_) {
      for(BakedQuad bakedquad : p_111010_) {
         if (p_111007_) {
            this.m_111039_(p_111002_, p_111003_, p_111004_, bakedquad.m_111303_(), bakedquad.m_111306_(), (float[])null, p_111011_);
            BlockPos blockpos = p_111011_.get(0) ? p_111004_.m_142300_(bakedquad.m_111306_()) : p_111004_;
            p_111005_ = LevelRenderer.m_109537_(p_111002_, p_111003_, blockpos);
         }

         float f = p_111002_.m_7717_(bakedquad.m_111306_(), bakedquad.m_111307_());
         this.m_111023_(p_111002_, p_111003_, p_111004_, p_111009_, p_111008_.m_85850_(), bakedquad, f, f, f, f, p_111005_, p_111005_, p_111005_, p_111005_, p_111006_);
      }

   }

   @Deprecated //Forge: Model data argument
   public void m_111067_(PoseStack.Pose p_111068_, VertexConsumer p_111069_, @Nullable BlockState p_111070_, BakedModel p_111071_, float p_111072_, float p_111073_, float p_111074_, int p_111075_, int p_111076_) {
      renderModel(p_111068_, p_111069_, p_111070_, p_111071_, p_111072_, p_111073_, p_111074_, p_111075_, p_111076_, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }
   public void renderModel(PoseStack.Pose p_111068_, VertexConsumer p_111069_, @Nullable BlockState p_111070_, BakedModel p_111071_, float p_111072_, float p_111073_, float p_111074_, int p_111075_, int p_111076_, net.minecraftforge.client.model.data.IModelData modelData) {
      Random random = new Random();
      long i = 42L;

      for(Direction direction : f_173405_) {
         random.setSeed(42L);
         m_111058_(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.getQuads(p_111070_, direction, random, modelData), p_111075_, p_111076_);
      }

      random.setSeed(42L);
      m_111058_(p_111068_, p_111069_, p_111072_, p_111073_, p_111074_, p_111071_.getQuads(p_111070_, (Direction)null, random, modelData), p_111075_, p_111076_);
   }

   private static void m_111058_(PoseStack.Pose p_111059_, VertexConsumer p_111060_, float p_111061_, float p_111062_, float p_111063_, List<BakedQuad> p_111064_, int p_111065_, int p_111066_) {
      for(BakedQuad bakedquad : p_111064_) {
         float f;
         float f1;
         float f2;
         if (bakedquad.m_111304_()) {
            f = Mth.m_14036_(p_111061_, 0.0F, 1.0F);
            f1 = Mth.m_14036_(p_111062_, 0.0F, 1.0F);
            f2 = Mth.m_14036_(p_111063_, 0.0F, 1.0F);
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         p_111060_.m_85987_(p_111059_, bakedquad, f, f1, f2, p_111065_, p_111066_);
      }

   }

   public static void m_111000_() {
      f_110996_.get().m_111220_();
   }

   public static void m_111077_() {
      f_110996_.get().m_111225_();
   }

   @OnlyIn(Dist.CLIENT)
   protected static enum AdjacencyInfo {
      DOWN(new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}, 0.5F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.SOUTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.SOUTH}),
      UP(new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}, 1.0F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.SOUTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.SOUTH}),
      NORTH(new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST}, 0.8F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_WEST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_EAST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST}),
      SOUTH(new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP}, 0.8F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.WEST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.WEST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.EAST}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.EAST}),
      WEST(new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH}, 0.6F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.SOUTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.SOUTH}),
      EAST(new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH}, 0.6F, true, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.SOUTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.NORTH}, new ModelBlockRenderer.SizeInfo[]{ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.SOUTH});

      final Direction[] f_111110_;
      final boolean f_111111_;
      final ModelBlockRenderer.SizeInfo[] f_111112_;
      final ModelBlockRenderer.SizeInfo[] f_111113_;
      final ModelBlockRenderer.SizeInfo[] f_111114_;
      final ModelBlockRenderer.SizeInfo[] f_111115_;
      private static final ModelBlockRenderer.AdjacencyInfo[] f_111116_ = Util.m_137469_(new ModelBlockRenderer.AdjacencyInfo[6], (p_111134_) -> {
         p_111134_[Direction.DOWN.m_122411_()] = DOWN;
         p_111134_[Direction.UP.m_122411_()] = UP;
         p_111134_[Direction.NORTH.m_122411_()] = NORTH;
         p_111134_[Direction.SOUTH.m_122411_()] = SOUTH;
         p_111134_[Direction.WEST.m_122411_()] = WEST;
         p_111134_[Direction.EAST.m_122411_()] = EAST;
      });

      private AdjacencyInfo(Direction[] p_111122_, float p_111123_, boolean p_111124_, ModelBlockRenderer.SizeInfo[] p_111125_, ModelBlockRenderer.SizeInfo[] p_111126_, ModelBlockRenderer.SizeInfo[] p_111127_, ModelBlockRenderer.SizeInfo[] p_111128_) {
         this.f_111110_ = p_111122_;
         this.f_111111_ = p_111124_;
         this.f_111112_ = p_111125_;
         this.f_111113_ = p_111126_;
         this.f_111114_ = p_111127_;
         this.f_111115_ = p_111128_;
      }

      public static ModelBlockRenderer.AdjacencyInfo m_111131_(Direction p_111132_) {
         return f_111116_[p_111132_.m_122411_()];
      }
   }

   @OnlyIn(Dist.CLIENT)
   class AmbientOcclusionFace {
      final float[] f_111149_ = new float[4];
      final int[] f_111150_ = new int[4];

      public AmbientOcclusionFace() {
      }

      public void m_111167_(BlockAndTintGetter p_111168_, BlockState p_111169_, BlockPos p_111170_, Direction p_111171_, float[] p_111172_, BitSet p_111173_, boolean p_111174_) {
         BlockPos blockpos = p_111173_.get(0) ? p_111170_.m_142300_(p_111171_) : p_111170_;
         ModelBlockRenderer.AdjacencyInfo modelblockrenderer$adjacencyinfo = ModelBlockRenderer.AdjacencyInfo.m_111131_(p_111171_);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         ModelBlockRenderer.Cache modelblockrenderer$cache = ModelBlockRenderer.f_110996_.get();
         blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0]);
         BlockState blockstate = p_111168_.m_8055_(blockpos$mutableblockpos);
         int i = modelblockrenderer$cache.m_111221_(blockstate, p_111168_, blockpos$mutableblockpos);
         float f = modelblockrenderer$cache.m_111226_(blockstate, p_111168_, blockpos$mutableblockpos);
         blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1]);
         BlockState blockstate1 = p_111168_.m_8055_(blockpos$mutableblockpos);
         int j = modelblockrenderer$cache.m_111221_(blockstate1, p_111168_, blockpos$mutableblockpos);
         float f1 = modelblockrenderer$cache.m_111226_(blockstate1, p_111168_, blockpos$mutableblockpos);
         blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[2]);
         BlockState blockstate2 = p_111168_.m_8055_(blockpos$mutableblockpos);
         int k = modelblockrenderer$cache.m_111221_(blockstate2, p_111168_, blockpos$mutableblockpos);
         float f2 = modelblockrenderer$cache.m_111226_(blockstate2, p_111168_, blockpos$mutableblockpos);
         blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[3]);
         BlockState blockstate3 = p_111168_.m_8055_(blockpos$mutableblockpos);
         int l = modelblockrenderer$cache.m_111221_(blockstate3, p_111168_, blockpos$mutableblockpos);
         float f3 = modelblockrenderer$cache.m_111226_(blockstate3, p_111168_, blockpos$mutableblockpos);
         BlockState blockstate4 = p_111168_.m_8055_(blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0]).m_122173_(p_111171_));
         boolean flag = !blockstate4.m_60831_(p_111168_, blockpos$mutableblockpos) || blockstate4.m_60739_(p_111168_, blockpos$mutableblockpos) == 0;
         BlockState blockstate5 = p_111168_.m_8055_(blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1]).m_122173_(p_111171_));
         boolean flag1 = !blockstate5.m_60831_(p_111168_, blockpos$mutableblockpos) || blockstate5.m_60739_(p_111168_, blockpos$mutableblockpos) == 0;
         BlockState blockstate6 = p_111168_.m_8055_(blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[2]).m_122173_(p_111171_));
         boolean flag2 = !blockstate6.m_60831_(p_111168_, blockpos$mutableblockpos) || blockstate6.m_60739_(p_111168_, blockpos$mutableblockpos) == 0;
         BlockState blockstate7 = p_111168_.m_8055_(blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[3]).m_122173_(p_111171_));
         boolean flag3 = !blockstate7.m_60831_(p_111168_, blockpos$mutableblockpos) || blockstate7.m_60739_(p_111168_, blockpos$mutableblockpos) == 0;
         float f4;
         int i1;
         if (!flag2 && !flag) {
            f4 = f;
            i1 = i;
         } else {
            blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0]).m_122173_(modelblockrenderer$adjacencyinfo.f_111110_[2]);
            BlockState blockstate8 = p_111168_.m_8055_(blockpos$mutableblockpos);
            f4 = modelblockrenderer$cache.m_111226_(blockstate8, p_111168_, blockpos$mutableblockpos);
            i1 = modelblockrenderer$cache.m_111221_(blockstate8, p_111168_, blockpos$mutableblockpos);
         }

         float f5;
         int j1;
         if (!flag3 && !flag) {
            f5 = f;
            j1 = i;
         } else {
            blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[0]).m_122173_(modelblockrenderer$adjacencyinfo.f_111110_[3]);
            BlockState blockstate10 = p_111168_.m_8055_(blockpos$mutableblockpos);
            f5 = modelblockrenderer$cache.m_111226_(blockstate10, p_111168_, blockpos$mutableblockpos);
            j1 = modelblockrenderer$cache.m_111221_(blockstate10, p_111168_, blockpos$mutableblockpos);
         }

         float f6;
         int k1;
         if (!flag2 && !flag1) {
            f6 = f;
            k1 = i;
         } else {
            blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1]).m_122173_(modelblockrenderer$adjacencyinfo.f_111110_[2]);
            BlockState blockstate11 = p_111168_.m_8055_(blockpos$mutableblockpos);
            f6 = modelblockrenderer$cache.m_111226_(blockstate11, p_111168_, blockpos$mutableblockpos);
            k1 = modelblockrenderer$cache.m_111221_(blockstate11, p_111168_, blockpos$mutableblockpos);
         }

         float f7;
         int l1;
         if (!flag3 && !flag1) {
            f7 = f;
            l1 = i;
         } else {
            blockpos$mutableblockpos.m_122159_(blockpos, modelblockrenderer$adjacencyinfo.f_111110_[1]).m_122173_(modelblockrenderer$adjacencyinfo.f_111110_[3]);
            BlockState blockstate12 = p_111168_.m_8055_(blockpos$mutableblockpos);
            f7 = modelblockrenderer$cache.m_111226_(blockstate12, p_111168_, blockpos$mutableblockpos);
            l1 = modelblockrenderer$cache.m_111221_(blockstate12, p_111168_, blockpos$mutableblockpos);
         }

         int i3 = modelblockrenderer$cache.m_111221_(p_111169_, p_111168_, p_111170_);
         blockpos$mutableblockpos.m_122159_(p_111170_, p_111171_);
         BlockState blockstate9 = p_111168_.m_8055_(blockpos$mutableblockpos);
         if (p_111173_.get(0) || !blockstate9.m_60804_(p_111168_, blockpos$mutableblockpos)) {
            i3 = modelblockrenderer$cache.m_111221_(blockstate9, p_111168_, blockpos$mutableblockpos);
         }

         float f8 = p_111173_.get(0) ? modelblockrenderer$cache.m_111226_(p_111168_.m_8055_(blockpos), p_111168_, blockpos) : modelblockrenderer$cache.m_111226_(p_111168_.m_8055_(p_111170_), p_111168_, p_111170_);
         ModelBlockRenderer.AmbientVertexRemap modelblockrenderer$ambientvertexremap = ModelBlockRenderer.AmbientVertexRemap.m_111201_(p_111171_);
         if (p_111173_.get(1) && modelblockrenderer$adjacencyinfo.f_111111_) {
            float f29 = (f3 + f + f5 + f8) * 0.25F;
            float f31 = (f2 + f + f4 + f8) * 0.25F;
            float f32 = (f2 + f1 + f6 + f8) * 0.25F;
            float f33 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 = p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[0].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[1].f_111258_];
            float f14 = p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[2].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[3].f_111258_];
            float f15 = p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[4].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[5].f_111258_];
            float f16 = p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[6].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111112_[7].f_111258_];
            float f17 = p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[0].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[1].f_111258_];
            float f18 = p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[2].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[3].f_111258_];
            float f19 = p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[4].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[5].f_111258_];
            float f20 = p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[6].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111113_[7].f_111258_];
            float f21 = p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[0].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[1].f_111258_];
            float f22 = p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[2].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[3].f_111258_];
            float f23 = p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[4].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[5].f_111258_];
            float f24 = p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[6].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111114_[7].f_111258_];
            float f25 = p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[0].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[1].f_111258_];
            float f26 = p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[2].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[3].f_111258_];
            float f27 = p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[4].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[5].f_111258_];
            float f28 = p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[6].f_111258_] * p_111172_[modelblockrenderer$adjacencyinfo.f_111115_[7].f_111258_];
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111185_] = f29 * f13 + f31 * f14 + f32 * f15 + f33 * f16;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111186_] = f29 * f17 + f31 * f18 + f32 * f19 + f33 * f20;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111187_] = f29 * f21 + f31 * f22 + f32 * f23 + f33 * f24;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111188_] = f29 * f25 + f31 * f26 + f32 * f27 + f33 * f28;
            int i2 = this.m_111153_(l, i, j1, i3);
            int j2 = this.m_111153_(k, i, i1, i3);
            int k2 = this.m_111153_(k, j, k1, i3);
            int l2 = this.m_111153_(l, j, l1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111185_] = this.m_111158_(i2, j2, k2, l2, f13, f14, f15, f16);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111186_] = this.m_111158_(i2, j2, k2, l2, f17, f18, f19, f20);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111187_] = this.m_111158_(i2, j2, k2, l2, f21, f22, f23, f24);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111188_] = this.m_111158_(i2, j2, k2, l2, f25, f26, f27, f28);
         } else {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111185_] = this.m_111153_(l, i, j1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111186_] = this.m_111153_(k, i, i1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111187_] = this.m_111153_(k, j, k1, i3);
            this.f_111150_[modelblockrenderer$ambientvertexremap.f_111188_] = this.m_111153_(l, j, l1, i3);
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111185_] = f9;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111186_] = f10;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111187_] = f11;
            this.f_111149_[modelblockrenderer$ambientvertexremap.f_111188_] = f12;
         }

         float f30 = p_111168_.m_7717_(p_111171_, p_111174_);

         for(int j3 = 0; j3 < this.f_111149_.length; ++j3) {
            this.f_111149_[j3] *= f30;
         }

      }

      private int m_111153_(int p_111154_, int p_111155_, int p_111156_, int p_111157_) {
         if (p_111154_ == 0) {
            p_111154_ = p_111157_;
         }

         if (p_111155_ == 0) {
            p_111155_ = p_111157_;
         }

         if (p_111156_ == 0) {
            p_111156_ = p_111157_;
         }

         return p_111154_ + p_111155_ + p_111156_ + p_111157_ >> 2 & 16711935;
      }

      private int m_111158_(int p_111159_, int p_111160_, int p_111161_, int p_111162_, float p_111163_, float p_111164_, float p_111165_, float p_111166_) {
         int i = (int)((float)(p_111159_ >> 16 & 255) * p_111163_ + (float)(p_111160_ >> 16 & 255) * p_111164_ + (float)(p_111161_ >> 16 & 255) * p_111165_ + (float)(p_111162_ >> 16 & 255) * p_111166_) & 255;
         int j = (int)((float)(p_111159_ & 255) * p_111163_ + (float)(p_111160_ & 255) * p_111164_ + (float)(p_111161_ & 255) * p_111165_ + (float)(p_111162_ & 255) * p_111166_) & 255;
         return i << 16 | j;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum AmbientVertexRemap {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      final int f_111185_;
      final int f_111186_;
      final int f_111187_;
      final int f_111188_;
      private static final ModelBlockRenderer.AmbientVertexRemap[] f_111189_ = Util.m_137469_(new ModelBlockRenderer.AmbientVertexRemap[6], (p_111204_) -> {
         p_111204_[Direction.DOWN.m_122411_()] = DOWN;
         p_111204_[Direction.UP.m_122411_()] = UP;
         p_111204_[Direction.NORTH.m_122411_()] = NORTH;
         p_111204_[Direction.SOUTH.m_122411_()] = SOUTH;
         p_111204_[Direction.WEST.m_122411_()] = WEST;
         p_111204_[Direction.EAST.m_122411_()] = EAST;
      });

      private AmbientVertexRemap(int p_111195_, int p_111196_, int p_111197_, int p_111198_) {
         this.f_111185_ = p_111195_;
         this.f_111186_ = p_111196_;
         this.f_111187_ = p_111197_;
         this.f_111188_ = p_111198_;
      }

      public static ModelBlockRenderer.AmbientVertexRemap m_111201_(Direction p_111202_) {
         return f_111189_[p_111202_.m_122411_()];
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Cache {
      private boolean f_111214_;
      private final Long2IntLinkedOpenHashMap f_111215_ = Util.m_137537_(() -> {
         Long2IntLinkedOpenHashMap long2intlinkedopenhashmap = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_111238_) {
            }
         };
         long2intlinkedopenhashmap.defaultReturnValue(Integer.MAX_VALUE);
         return long2intlinkedopenhashmap;
      });
      private final Long2FloatLinkedOpenHashMap f_111216_ = Util.m_137537_(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int p_111245_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      });

      private Cache() {
      }

      public void m_111220_() {
         this.f_111214_ = true;
      }

      public void m_111225_() {
         this.f_111214_ = false;
         this.f_111215_.clear();
         this.f_111216_.clear();
      }

      public int m_111221_(BlockState p_111222_, BlockAndTintGetter p_111223_, BlockPos p_111224_) {
         long i = p_111224_.m_121878_();
         if (this.f_111214_) {
            int j = this.f_111215_.get(i);
            if (j != Integer.MAX_VALUE) {
               return j;
            }
         }

         int k = LevelRenderer.m_109537_(p_111223_, p_111222_, p_111224_);
         if (this.f_111214_) {
            if (this.f_111215_.size() == 100) {
               this.f_111215_.removeFirstInt();
            }

            this.f_111215_.put(i, k);
         }

         return k;
      }

      public float m_111226_(BlockState p_111227_, BlockAndTintGetter p_111228_, BlockPos p_111229_) {
         long i = p_111229_.m_121878_();
         if (this.f_111214_) {
            float f = this.f_111216_.get(i);
            if (!Float.isNaN(f)) {
               return f;
            }
         }

         float f1 = p_111227_.m_60792_(p_111228_, p_111229_);
         if (this.f_111214_) {
            if (this.f_111216_.size() == 100) {
               this.f_111216_.removeFirstFloat();
            }

            this.f_111216_.put(i, f1);
         }

         return f1;
      }
   }

   @OnlyIn(Dist.CLIENT)
   protected static enum SizeInfo {
      DOWN(Direction.DOWN, false),
      UP(Direction.UP, false),
      NORTH(Direction.NORTH, false),
      SOUTH(Direction.SOUTH, false),
      WEST(Direction.WEST, false),
      EAST(Direction.EAST, false),
      FLIP_DOWN(Direction.DOWN, true),
      FLIP_UP(Direction.UP, true),
      FLIP_NORTH(Direction.NORTH, true),
      FLIP_SOUTH(Direction.SOUTH, true),
      FLIP_WEST(Direction.WEST, true),
      FLIP_EAST(Direction.EAST, true);

      final int f_111258_;

      private SizeInfo(Direction p_111264_, boolean p_111265_) {
         this.f_111258_ = p_111264_.m_122411_() + (p_111265_ ? ModelBlockRenderer.f_173405_.length : 0);
      }
   }
}
