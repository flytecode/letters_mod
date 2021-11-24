package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LiquidBlockRenderer {
   private static final float f_173402_ = 0.8888889F;
   private final TextureAtlasSprite[] f_110940_ = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] f_110941_ = new TextureAtlasSprite[2];
   private TextureAtlasSprite f_110942_;

   protected void m_110944_() {
      this.f_110940_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49991_.m_49966_()).m_6160_();
      this.f_110940_[1] = ModelBakery.f_119221_.m_119204_();
      this.f_110941_[0] = Minecraft.m_91087_().m_91304_().m_119430_().m_110893_(Blocks.f_49990_.m_49966_()).m_6160_();
      this.f_110941_[1] = ModelBakery.f_119222_.m_119204_();
      this.f_110942_ = ModelBakery.f_119223_.m_119204_();
   }

   private static boolean m_110973_(BlockGetter p_110974_, BlockPos p_110975_, Direction p_110976_, FluidState p_110977_) {
      BlockPos blockpos = p_110975_.m_142300_(p_110976_);
      FluidState fluidstate = p_110974_.m_6425_(blockpos);
      return fluidstate.m_76152_().m_6212_(p_110977_.m_76152_());
   }

   private static boolean m_110978_(BlockGetter p_110979_, Direction p_110980_, float p_110981_, BlockPos p_110982_, BlockState p_110983_) {
      if (p_110983_.m_60815_()) {
         VoxelShape voxelshape = Shapes.m_83048_(0.0D, 0.0D, 0.0D, 1.0D, (double)p_110981_, 1.0D);
         VoxelShape voxelshape1 = p_110983_.m_60768_(p_110979_, p_110982_);
         return Shapes.m_83117_(voxelshape, voxelshape1, p_110980_);
      } else {
         return false;
      }
   }

   private static boolean m_110968_(BlockGetter p_110969_, BlockPos p_110970_, Direction p_110971_, float p_110972_) {
      BlockPos blockpos = p_110970_.m_142300_(p_110971_);
      BlockState blockstate = p_110969_.m_8055_(blockpos);
      return m_110978_(p_110969_, p_110971_, p_110972_, blockpos, blockstate);
   }

   private static boolean m_110959_(BlockGetter p_110960_, BlockPos p_110961_, BlockState p_110962_, Direction p_110963_) {
      return m_110978_(p_110960_, p_110963_.m_122424_(), 1.0F, p_110961_, p_110962_);
   }

   public static boolean m_110948_(BlockAndTintGetter p_110949_, BlockPos p_110950_, FluidState p_110951_, BlockState p_110952_, Direction p_110953_) {
      return !m_110959_(p_110949_, p_110950_, p_110952_, p_110953_) && !m_110973_(p_110949_, p_110950_, p_110953_, p_110951_);
   }

   public boolean m_110954_(BlockAndTintGetter p_110955_, BlockPos p_110956_, VertexConsumer p_110957_, FluidState p_110958_) {
      boolean flag = p_110958_.m_76153_(FluidTags.f_13132_);
      TextureAtlasSprite[] atextureatlassprite = net.minecraftforge.client.ForgeHooksClient.getFluidSprites(p_110955_, p_110956_, p_110958_);
      BlockState blockstate = p_110955_.m_8055_(p_110956_);
      int i = p_110958_.m_76152_().getAttributes().getColor(p_110955_, p_110956_);
      float alpha = (float)(i >> 24 & 255) / 255.0F;
      float f = (float)(i >> 16 & 255) / 255.0F;
      float f1 = (float)(i >> 8 & 255) / 255.0F;
      float f2 = (float)(i & 255) / 255.0F;
      boolean flag1 = !m_110973_(p_110955_, p_110956_, Direction.UP, p_110958_);
      boolean flag2 = m_110948_(p_110955_, p_110956_, p_110958_, blockstate, Direction.DOWN) && !m_110968_(p_110955_, p_110956_, Direction.DOWN, 0.8888889F);
      boolean flag3 = m_110948_(p_110955_, p_110956_, p_110958_, blockstate, Direction.NORTH);
      boolean flag4 = m_110948_(p_110955_, p_110956_, p_110958_, blockstate, Direction.SOUTH);
      boolean flag5 = m_110948_(p_110955_, p_110956_, p_110958_, blockstate, Direction.WEST);
      boolean flag6 = m_110948_(p_110955_, p_110956_, p_110958_, blockstate, Direction.EAST);
      if (!flag1 && !flag2 && !flag6 && !flag5 && !flag3 && !flag4) {
         return false;
      } else {
         boolean flag7 = false;
         float f3 = p_110955_.m_7717_(Direction.DOWN, true);
         float f4 = p_110955_.m_7717_(Direction.UP, true);
         float f5 = p_110955_.m_7717_(Direction.NORTH, true);
         float f6 = p_110955_.m_7717_(Direction.WEST, true);
         float f7 = this.m_110964_(p_110955_, p_110956_, p_110958_.m_76152_());
         float f8 = this.m_110964_(p_110955_, p_110956_.m_142128_(), p_110958_.m_76152_());
         float f9 = this.m_110964_(p_110955_, p_110956_.m_142126_().m_142128_(), p_110958_.m_76152_());
         float f10 = this.m_110964_(p_110955_, p_110956_.m_142126_(), p_110958_.m_76152_());
         double d0 = (double)(p_110956_.m_123341_() & 15);
         double d1 = (double)(p_110956_.m_123342_() & 15);
         double d2 = (double)(p_110956_.m_123343_() & 15);
         float f11 = 0.001F;
         float f12 = flag2 ? 0.001F : 0.0F;
         if (flag1 && !m_110968_(p_110955_, p_110956_, Direction.UP, Math.min(Math.min(f7, f8), Math.min(f9, f10)))) {
            flag7 = true;
            f7 -= 0.001F;
            f8 -= 0.001F;
            f9 -= 0.001F;
            f10 -= 0.001F;
            Vec3 vec3 = p_110958_.m_76179_(p_110955_, p_110956_);
            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            float f18;
            float f19;
            float f20;
            if (vec3.f_82479_ == 0.0D && vec3.f_82481_ == 0.0D) {
               TextureAtlasSprite textureatlassprite1 = atextureatlassprite[0];
               f13 = textureatlassprite1.m_118367_(0.0D);
               f17 = textureatlassprite1.m_118393_(0.0D);
               f14 = f13;
               f18 = textureatlassprite1.m_118393_(16.0D);
               f15 = textureatlassprite1.m_118367_(16.0D);
               f19 = f18;
               f16 = f15;
               f20 = f17;
            } else {
               TextureAtlasSprite textureatlassprite = atextureatlassprite[1];
               float f21 = (float)Mth.m_14136_(vec3.f_82481_, vec3.f_82479_) - ((float)Math.PI / 2F);
               float f22 = Mth.m_14031_(f21) * 0.25F;
               float f23 = Mth.m_14089_(f21) * 0.25F;
               float f24 = 8.0F;
               f13 = textureatlassprite.m_118367_((double)(8.0F + (-f23 - f22) * 16.0F));
               f17 = textureatlassprite.m_118393_((double)(8.0F + (-f23 + f22) * 16.0F));
               f14 = textureatlassprite.m_118367_((double)(8.0F + (-f23 + f22) * 16.0F));
               f18 = textureatlassprite.m_118393_((double)(8.0F + (f23 + f22) * 16.0F));
               f15 = textureatlassprite.m_118367_((double)(8.0F + (f23 + f22) * 16.0F));
               f19 = textureatlassprite.m_118393_((double)(8.0F + (f23 - f22) * 16.0F));
               f16 = textureatlassprite.m_118367_((double)(8.0F + (f23 - f22) * 16.0F));
               f20 = textureatlassprite.m_118393_((double)(8.0F + (-f23 - f22) * 16.0F));
            }

            float f44 = (f13 + f14 + f15 + f16) / 4.0F;
            float f45 = (f17 + f18 + f19 + f20) / 4.0F;
            float f46 = (float)atextureatlassprite[0].m_118405_() / (atextureatlassprite[0].m_118410_() - atextureatlassprite[0].m_118409_());
            float f47 = (float)atextureatlassprite[0].m_118408_() / (atextureatlassprite[0].m_118412_() - atextureatlassprite[0].m_118411_());
            float f48 = 4.0F / Math.max(f47, f46);
            f13 = Mth.m_14179_(f48, f13, f44);
            f14 = Mth.m_14179_(f48, f14, f44);
            f15 = Mth.m_14179_(f48, f15, f44);
            f16 = Mth.m_14179_(f48, f16, f44);
            f17 = Mth.m_14179_(f48, f17, f45);
            f18 = Mth.m_14179_(f48, f18, f45);
            f19 = Mth.m_14179_(f48, f19, f45);
            f20 = Mth.m_14179_(f48, f20, f45);
            int j = this.m_110945_(p_110955_, p_110956_);
            float f25 = f4 * f;
            float f26 = f4 * f1;
            float f27 = f4 * f2;
            this.vertex(p_110957_, d0 + 0.0D, d1 + (double)f7, d2 + 0.0D, f25, f26, f27, alpha, f13, f17, j);
            this.vertex(p_110957_, d0 + 0.0D, d1 + (double)f8, d2 + 1.0D, f25, f26, f27, alpha, f14, f18, j);
            this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f9, d2 + 1.0D, f25, f26, f27, alpha, f15, f19, j);
            this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f10, d2 + 0.0D, f25, f26, f27, alpha, f16, f20, j);
            if (p_110958_.m_76171_(p_110955_, p_110956_.m_7494_())) {
               this.vertex(p_110957_, d0 + 0.0D, d1 + (double)f7, d2 + 0.0D, f25, f26, f27, alpha, f13, f17, j);
               this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f10, d2 + 0.0D, f25, f26, f27, alpha, f16, f20, j);
               this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f9, d2 + 1.0D, f25, f26, f27, alpha, f15, f19, j);
               this.vertex(p_110957_, d0 + 0.0D, d1 + (double)f8, d2 + 1.0D, f25, f26, f27, alpha, f14, f18, j);
            }
         }

         if (flag2) {
            float f35 = atextureatlassprite[0].m_118409_();
            float f36 = atextureatlassprite[0].m_118410_();
            float f37 = atextureatlassprite[0].m_118411_();
            float f39 = atextureatlassprite[0].m_118412_();
            int i1 = this.m_110945_(p_110955_, p_110956_.m_7495_());
            float f41 = f3 * f;
            float f42 = f3 * f1;
            float f43 = f3 * f2;
            this.vertex(p_110957_, d0, d1 + (double)f12, d2 + 1.0D, f41, f42, f43, alpha, f35, f39, i1);
            this.vertex(p_110957_, d0, d1 + (double)f12, d2, f41, f42, f43, alpha, f35, f37, i1);
            this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f12, d2, f41, f42, f43, alpha, f36, f37, i1);
            this.vertex(p_110957_, d0 + 1.0D, d1 + (double)f12, d2 + 1.0D, f41, f42, f43, alpha, f36, f39, i1);
            flag7 = true;
         }

         int k = this.m_110945_(p_110955_, p_110956_);

         for(int l = 0; l < 4; ++l) {
            float f38;
            float f40;
            double d3;
            double d4;
            double d5;
            double d6;
            Direction direction;
            boolean flag8;
            if (l == 0) {
               f38 = f7;
               f40 = f10;
               d3 = d0;
               d5 = d0 + 1.0D;
               d4 = d2 + (double)0.001F;
               d6 = d2 + (double)0.001F;
               direction = Direction.NORTH;
               flag8 = flag3;
            } else if (l == 1) {
               f38 = f9;
               f40 = f8;
               d3 = d0 + 1.0D;
               d5 = d0;
               d4 = d2 + 1.0D - (double)0.001F;
               d6 = d2 + 1.0D - (double)0.001F;
               direction = Direction.SOUTH;
               flag8 = flag4;
            } else if (l == 2) {
               f38 = f8;
               f40 = f7;
               d3 = d0 + (double)0.001F;
               d5 = d0 + (double)0.001F;
               d4 = d2 + 1.0D;
               d6 = d2;
               direction = Direction.WEST;
               flag8 = flag5;
            } else {
               f38 = f10;
               f40 = f9;
               d3 = d0 + 1.0D - (double)0.001F;
               d5 = d0 + 1.0D - (double)0.001F;
               d4 = d2;
               d6 = d2 + 1.0D;
               direction = Direction.EAST;
               flag8 = flag6;
            }

            if (flag8 && !m_110968_(p_110955_, p_110956_, direction, Math.max(f38, f40))) {
               flag7 = true;
               BlockPos blockpos = p_110956_.m_142300_(direction);
               TextureAtlasSprite textureatlassprite2 = atextureatlassprite[1];
               if (atextureatlassprite[2] != null) {
                  if (p_110955_.m_8055_(blockpos).shouldDisplayFluidOverlay(p_110955_, blockpos, p_110958_)) {
                     textureatlassprite2 = atextureatlassprite[2];
                  }
               }

               float f49 = textureatlassprite2.m_118367_(0.0D);
               float f50 = textureatlassprite2.m_118367_(8.0D);
               float f28 = textureatlassprite2.m_118393_((double)((1.0F - f38) * 16.0F * 0.5F));
               float f29 = textureatlassprite2.m_118393_((double)((1.0F - f40) * 16.0F * 0.5F));
               float f30 = textureatlassprite2.m_118393_(8.0D);
               float f31 = l < 2 ? f5 : f6;
               float f32 = f4 * f31 * f;
               float f33 = f4 * f31 * f1;
               float f34 = f4 * f31 * f2;
               this.vertex(p_110957_, d3, d1 + (double)f38, d4, f32, f33, f34, alpha, f49, f28, k);
               this.vertex(p_110957_, d5, d1 + (double)f40, d6, f32, f33, f34, alpha, f50, f29, k);
               this.vertex(p_110957_, d5, d1 + (double)f12, d6, f32, f33, f34, alpha, f50, f30, k);
               this.vertex(p_110957_, d3, d1 + (double)f12, d4, f32, f33, f34, alpha, f49, f30, k);
               if (textureatlassprite2 != this.f_110942_) {
                  this.vertex(p_110957_, d3, d1 + (double)f12, d4, f32, f33, f34, alpha, f49, f30, k);
                  this.vertex(p_110957_, d5, d1 + (double)f12, d6, f32, f33, f34, alpha, f50, f30, k);
                  this.vertex(p_110957_, d5, d1 + (double)f40, d6, f32, f33, f34, alpha, f50, f29, k);
                  this.vertex(p_110957_, d3, d1 + (double)f38, d4, f32, f33, f34, alpha, f49, f28, k);
               }
            }
         }

         return flag7;
      }
   }

   private void vertex(VertexConsumer p_110985_, double p_110986_, double p_110987_, double p_110988_, float p_110989_, float p_110990_, float p_110991_, float alpha, float p_110992_, float p_110993_, int p_110994_) {
       p_110985_.m_5483_(p_110986_, p_110987_, p_110988_).m_85950_(p_110989_, p_110990_, p_110991_, alpha).m_7421_(p_110992_, p_110993_).m_85969_(p_110994_).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
   }

   private int m_110945_(BlockAndTintGetter p_110946_, BlockPos p_110947_) {
      int i = LevelRenderer.m_109541_(p_110946_, p_110947_);
      int j = LevelRenderer.m_109541_(p_110946_, p_110947_.m_7494_());
      int k = i & 255;
      int l = j & 255;
      int i1 = i >> 16 & 255;
      int j1 = j >> 16 & 255;
      return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
   }

   private float m_110964_(BlockGetter p_110965_, BlockPos p_110966_, Fluid p_110967_) {
      int i = 0;
      float f = 0.0F;

      for(int j = 0; j < 4; ++j) {
         BlockPos blockpos = p_110966_.m_142082_(-(j & 1), 0, -(j >> 1 & 1));
         if (p_110965_.m_6425_(blockpos.m_7494_()).m_76152_().m_6212_(p_110967_)) {
            return 1.0F;
         }

         FluidState fluidstate = p_110965_.m_6425_(blockpos);
         if (fluidstate.m_76152_().m_6212_(p_110967_)) {
            float f1 = fluidstate.m_76155_(p_110965_, blockpos);
            if (f1 >= 0.8F) {
               f += f1 * 10.0F;
               i += 10;
            } else {
               f += f1;
               ++i;
            }
         } else if (!p_110965_.m_8055_(blockpos).m_60767_().m_76333_()) {
            ++i;
         }
      }

      return f / (float)i;
   }
}
