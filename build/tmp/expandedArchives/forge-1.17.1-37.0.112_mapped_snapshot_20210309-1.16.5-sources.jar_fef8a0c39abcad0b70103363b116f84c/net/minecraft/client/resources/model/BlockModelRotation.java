package net.minecraft.client.resources.model;

import com.mojang.math.OctahedralGroup;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum BlockModelRotation implements ModelState {
   X0_Y0(0, 0),
   X0_Y90(0, 90),
   X0_Y180(0, 180),
   X0_Y270(0, 270),
   X90_Y0(90, 0),
   X90_Y90(90, 90),
   X90_Y180(90, 180),
   X90_Y270(90, 270),
   X180_Y0(180, 0),
   X180_Y90(180, 90),
   X180_Y180(180, 180),
   X180_Y270(180, 270),
   X270_Y0(270, 0),
   X270_Y90(270, 90),
   X270_Y180(270, 180),
   X270_Y270(270, 270);

   private static final int f_174872_ = 360;
   private static final Map<Integer, BlockModelRotation> f_119142_ = Arrays.stream(values()).collect(Collectors.toMap((p_119163_) -> {
      return p_119163_.f_119145_;
   }, (p_119157_) -> {
      return p_119157_;
   }));
   private final Transformation f_119143_;
   private final OctahedralGroup f_119144_;
   private final int f_119145_;

   private static int m_119159_(int p_119160_, int p_119161_) {
      return p_119160_ * 360 + p_119161_;
   }

   private BlockModelRotation(int p_119151_, int p_119152_) {
      this.f_119145_ = m_119159_(p_119151_, p_119152_);
      Quaternion quaternion = Vector3f.f_122225_.m_122240_((float)(-p_119152_));
      quaternion.m_80148_(Vector3f.f_122223_.m_122240_((float)(-p_119151_)));
      OctahedralGroup octahedralgroup = OctahedralGroup.IDENTITY;

      for(int i = 0; i < p_119152_; i += 90) {
         octahedralgroup = octahedralgroup.m_56521_(OctahedralGroup.ROT_90_Y_NEG);
      }

      for(int j = 0; j < p_119151_; j += 90) {
         octahedralgroup = octahedralgroup.m_56521_(OctahedralGroup.ROT_90_X_NEG);
      }

      this.f_119143_ = new Transformation((Vector3f)null, quaternion, (Vector3f)null, (Quaternion)null);
      this.f_119144_ = octahedralgroup;
   }

   public Transformation m_6189_() {
      return this.f_119143_;
   }

   public static BlockModelRotation m_119153_(int p_119154_, int p_119155_) {
      return f_119142_.get(m_119159_(Mth.m_14100_(p_119154_, 360), Mth.m_14100_(p_119155_, 360)));
   }

   public OctahedralGroup m_174873_() {
      return this.f_119144_;
   }
}