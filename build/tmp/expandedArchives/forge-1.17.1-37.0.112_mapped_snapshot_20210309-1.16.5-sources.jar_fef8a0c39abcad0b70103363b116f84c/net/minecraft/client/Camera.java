package net.minecraft.client;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Camera {
   private boolean f_90549_;
   private BlockGetter f_90550_;
   private Entity f_90551_;
   private Vec3 f_90552_ = Vec3.f_82478_;
   private final BlockPos.MutableBlockPos f_90553_ = new BlockPos.MutableBlockPos();
   private final Vector3f f_90554_ = new Vector3f(0.0F, 0.0F, 1.0F);
   private final Vector3f f_90555_ = new Vector3f(0.0F, 1.0F, 0.0F);
   private final Vector3f f_90556_ = new Vector3f(1.0F, 0.0F, 0.0F);
   private float f_90557_;
   private float f_90558_;
   private final Quaternion f_90559_ = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private boolean f_90560_;
   private float f_90562_;
   private float f_90563_;
   public static final float f_167683_ = 0.083333336F;

   public void m_90575_(BlockGetter p_90576_, Entity p_90577_, boolean p_90578_, boolean p_90579_, float p_90580_) {
      this.f_90549_ = true;
      this.f_90550_ = p_90576_;
      this.f_90551_ = p_90577_;
      this.f_90560_ = p_90578_;
      this.m_90572_(p_90577_.m_5675_(p_90580_), p_90577_.m_5686_(p_90580_));
      this.m_90584_(Mth.m_14139_((double)p_90580_, p_90577_.f_19854_, p_90577_.m_20185_()), Mth.m_14139_((double)p_90580_, p_90577_.f_19855_, p_90577_.m_20186_()) + (double)Mth.m_14179_(p_90580_, this.f_90563_, this.f_90562_), Mth.m_14139_((double)p_90580_, p_90577_.f_19856_, p_90577_.m_20189_()));
      if (p_90578_) {
         if (p_90579_) {
            this.m_90572_(this.f_90558_ + 180.0F, -this.f_90557_);
         }

         this.m_90568_(-this.m_90566_(4.0D), 0.0D, 0.0D);
      } else if (p_90577_ instanceof LivingEntity && ((LivingEntity)p_90577_).m_5803_()) {
         Direction direction = ((LivingEntity)p_90577_).m_21259_();
         this.m_90572_(direction != null ? direction.m_122435_() - 180.0F : 0.0F, 0.0F);
         this.m_90568_(0.0D, 0.3D, 0.0D);
      }

   }

   public void m_90565_() {
      if (this.f_90551_ != null) {
         this.f_90563_ = this.f_90562_;
         this.f_90562_ += (this.f_90551_.m_20192_() - this.f_90562_) * 0.5F;
      }

   }

   private double m_90566_(double p_90567_) {
      for(int i = 0; i < 8; ++i) {
         float f = (float)((i & 1) * 2 - 1);
         float f1 = (float)((i >> 1 & 1) * 2 - 1);
         float f2 = (float)((i >> 2 & 1) * 2 - 1);
         f = f * 0.1F;
         f1 = f1 * 0.1F;
         f2 = f2 * 0.1F;
         Vec3 vec3 = this.f_90552_.m_82520_((double)f, (double)f1, (double)f2);
         Vec3 vec31 = new Vec3(this.f_90552_.f_82479_ - (double)this.f_90554_.m_122239_() * p_90567_ + (double)f + (double)f2, this.f_90552_.f_82480_ - (double)this.f_90554_.m_122260_() * p_90567_ + (double)f1, this.f_90552_.f_82481_ - (double)this.f_90554_.m_122269_() * p_90567_ + (double)f2);
         HitResult hitresult = this.f_90550_.m_45547_(new ClipContext(vec3, vec31, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, this.f_90551_));
         if (hitresult.m_6662_() != HitResult.Type.MISS) {
            double d0 = hitresult.m_82450_().m_82554_(this.f_90552_);
            if (d0 < p_90567_) {
               p_90567_ = d0;
            }
         }
      }

      return p_90567_;
   }

   protected void m_90568_(double p_90569_, double p_90570_, double p_90571_) {
      double d0 = (double)this.f_90554_.m_122239_() * p_90569_ + (double)this.f_90555_.m_122239_() * p_90570_ + (double)this.f_90556_.m_122239_() * p_90571_;
      double d1 = (double)this.f_90554_.m_122260_() * p_90569_ + (double)this.f_90555_.m_122260_() * p_90570_ + (double)this.f_90556_.m_122260_() * p_90571_;
      double d2 = (double)this.f_90554_.m_122269_() * p_90569_ + (double)this.f_90555_.m_122269_() * p_90570_ + (double)this.f_90556_.m_122269_() * p_90571_;
      this.m_90581_(new Vec3(this.f_90552_.f_82479_ + d0, this.f_90552_.f_82480_ + d1, this.f_90552_.f_82481_ + d2));
   }

   protected void m_90572_(float p_90573_, float p_90574_) {
      this.f_90557_ = p_90574_;
      this.f_90558_ = p_90573_;
      this.f_90559_.m_80143_(0.0F, 0.0F, 0.0F, 1.0F);
      this.f_90559_.m_80148_(Vector3f.f_122225_.m_122240_(-p_90573_));
      this.f_90559_.m_80148_(Vector3f.f_122223_.m_122240_(p_90574_));
      this.f_90554_.m_122245_(0.0F, 0.0F, 1.0F);
      this.f_90554_.m_122251_(this.f_90559_);
      this.f_90555_.m_122245_(0.0F, 1.0F, 0.0F);
      this.f_90555_.m_122251_(this.f_90559_);
      this.f_90556_.m_122245_(1.0F, 0.0F, 0.0F);
      this.f_90556_.m_122251_(this.f_90559_);
   }

   protected void m_90584_(double p_90585_, double p_90586_, double p_90587_) {
      this.m_90581_(new Vec3(p_90585_, p_90586_, p_90587_));
   }

   protected void m_90581_(Vec3 p_90582_) {
      this.f_90552_ = p_90582_;
      this.f_90553_.m_122169_(p_90582_.f_82479_, p_90582_.f_82480_, p_90582_.f_82481_);
   }

   public Vec3 m_90583_() {
      return this.f_90552_;
   }

   public BlockPos m_90588_() {
      return this.f_90553_;
   }

   public float m_90589_() {
      return this.f_90557_;
   }

   public float m_90590_() {
      return this.f_90558_;
   }

   public Quaternion m_90591_() {
      return this.f_90559_;
   }

   public Entity m_90592_() {
      return this.f_90551_;
   }

   public boolean m_90593_() {
      return this.f_90549_;
   }

   public boolean m_90594_() {
      return this.f_90560_;
   }

   public Camera.NearPlane m_167684_() {
      Minecraft minecraft = Minecraft.m_91087_();
      double d0 = (double)minecraft.m_91268_().m_85441_() / (double)minecraft.m_91268_().m_85442_();
      double d1 = Math.tan(minecraft.f_91066_.f_92068_ * (double)((float)Math.PI / 180F) / 2.0D) * (double)0.05F;
      double d2 = d1 * d0;
      Vec3 vec3 = (new Vec3(this.f_90554_)).m_82490_((double)0.05F);
      Vec3 vec31 = (new Vec3(this.f_90556_)).m_82490_(d2);
      Vec3 vec32 = (new Vec3(this.f_90555_)).m_82490_(d1);
      return new Camera.NearPlane(vec3, vec31, vec32);
   }

   public FogType m_167685_() {
      if (!this.f_90549_) {
         return FogType.NONE;
      } else {
         FluidState fluidstate = this.f_90550_.m_6425_(this.f_90553_);
         if (fluidstate.m_76153_(FluidTags.f_13131_) && this.f_90552_.f_82480_ < (double)((float)this.f_90553_.m_123342_() + fluidstate.m_76155_(this.f_90550_, this.f_90553_))) {
            return FogType.WATER;
         } else {
            Camera.NearPlane camera$nearplane = this.m_167684_();

            for(Vec3 vec3 : Arrays.asList(camera$nearplane.f_167687_, camera$nearplane.m_167694_(), camera$nearplane.m_167698_(), camera$nearplane.m_167699_(), camera$nearplane.m_167700_())) {
               Vec3 vec31 = this.f_90552_.m_82549_(vec3);
               BlockPos blockpos = new BlockPos(vec31);
               FluidState fluidstate1 = this.f_90550_.m_6425_(blockpos);
               if (fluidstate1.m_76153_(FluidTags.f_13132_)) {
                  if (vec31.f_82480_ <= (double)(fluidstate1.m_76155_(this.f_90550_, blockpos) + (float)blockpos.m_123342_())) {
                     return FogType.LAVA;
                  }
               } else {
                  BlockState blockstate = this.f_90550_.m_8055_(blockpos);
                  if (blockstate.m_60713_(Blocks.f_152499_)) {
                     return FogType.POWDER_SNOW;
                  }
               }
            }

            return FogType.NONE;
         }
      }
   }

   public final Vector3f m_90596_() {
      return this.f_90554_;
   }

   public final Vector3f m_90597_() {
      return this.f_90555_;
   }

   public final Vector3f m_167686_() {
      return this.f_90556_;
   }

   public void m_90598_() {
      this.f_90550_ = null;
      this.f_90551_ = null;
      this.f_90549_ = false;
   }

   public void setAnglesInternal(float yaw, float pitch) {
      this.f_90558_ = yaw;
      this.f_90557_ = pitch;
   }

   public net.minecraft.world.level.block.state.BlockState getBlockAtCamera() {
      if (!this.f_90549_)
         return net.minecraft.world.level.block.Blocks.f_50016_.m_49966_();
      else
         return this.f_90550_.m_8055_(this.f_90553_).getStateAtViewpoint(this.f_90550_, this.f_90553_, this.f_90552_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class NearPlane {
      final Vec3 f_167687_;
      private final Vec3 f_167688_;
      private final Vec3 f_167689_;

      NearPlane(Vec3 p_167691_, Vec3 p_167692_, Vec3 p_167693_) {
         this.f_167687_ = p_167691_;
         this.f_167688_ = p_167692_;
         this.f_167689_ = p_167693_;
      }

      public Vec3 m_167694_() {
         return this.f_167687_.m_82549_(this.f_167689_).m_82549_(this.f_167688_);
      }

      public Vec3 m_167698_() {
         return this.f_167687_.m_82549_(this.f_167689_).m_82546_(this.f_167688_);
      }

      public Vec3 m_167699_() {
         return this.f_167687_.m_82546_(this.f_167689_).m_82549_(this.f_167688_);
      }

      public Vec3 m_167700_() {
         return this.f_167687_.m_82546_(this.f_167689_).m_82546_(this.f_167688_);
      }

      public Vec3 m_167695_(float p_167696_, float p_167697_) {
         return this.f_167687_.m_82549_(this.f_167689_.m_82490_((double)p_167697_)).m_82546_(this.f_167688_.m_82490_((double)p_167696_));
      }
   }
}
