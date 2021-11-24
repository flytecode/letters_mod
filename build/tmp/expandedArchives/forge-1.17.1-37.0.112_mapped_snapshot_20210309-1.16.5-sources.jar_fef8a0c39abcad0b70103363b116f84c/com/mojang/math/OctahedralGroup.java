package com.mojang.math;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.util.StringRepresentable;

public enum OctahedralGroup implements StringRepresentable {
   IDENTITY("identity", SymmetricGroup3.P123, false, false, false),
   ROT_180_FACE_XY("rot_180_face_xy", SymmetricGroup3.P123, true, true, false),
   ROT_180_FACE_XZ("rot_180_face_xz", SymmetricGroup3.P123, true, false, true),
   ROT_180_FACE_YZ("rot_180_face_yz", SymmetricGroup3.P123, false, true, true),
   ROT_120_NNN("rot_120_nnn", SymmetricGroup3.P231, false, false, false),
   ROT_120_NNP("rot_120_nnp", SymmetricGroup3.P312, true, false, true),
   ROT_120_NPN("rot_120_npn", SymmetricGroup3.P312, false, true, true),
   ROT_120_NPP("rot_120_npp", SymmetricGroup3.P231, true, false, true),
   ROT_120_PNN("rot_120_pnn", SymmetricGroup3.P312, true, true, false),
   ROT_120_PNP("rot_120_pnp", SymmetricGroup3.P231, true, true, false),
   ROT_120_PPN("rot_120_ppn", SymmetricGroup3.P231, false, true, true),
   ROT_120_PPP("rot_120_ppp", SymmetricGroup3.P312, false, false, false),
   ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", SymmetricGroup3.P213, true, true, true),
   ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", SymmetricGroup3.P213, false, false, true),
   ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", SymmetricGroup3.P321, true, true, true),
   ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", SymmetricGroup3.P321, false, true, false),
   ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", SymmetricGroup3.P132, true, true, true),
   ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", SymmetricGroup3.P132, true, false, false),
   ROT_90_X_NEG("rot_90_x_neg", SymmetricGroup3.P132, false, false, true),
   ROT_90_X_POS("rot_90_x_pos", SymmetricGroup3.P132, false, true, false),
   ROT_90_Y_NEG("rot_90_y_neg", SymmetricGroup3.P321, true, false, false),
   ROT_90_Y_POS("rot_90_y_pos", SymmetricGroup3.P321, false, false, true),
   ROT_90_Z_NEG("rot_90_z_neg", SymmetricGroup3.P213, false, true, false),
   ROT_90_Z_POS("rot_90_z_pos", SymmetricGroup3.P213, true, false, false),
   INVERSION("inversion", SymmetricGroup3.P123, true, true, true),
   INVERT_X("invert_x", SymmetricGroup3.P123, true, false, false),
   INVERT_Y("invert_y", SymmetricGroup3.P123, false, true, false),
   INVERT_Z("invert_z", SymmetricGroup3.P123, false, false, true),
   ROT_60_REF_NNN("rot_60_ref_nnn", SymmetricGroup3.P312, true, true, true),
   ROT_60_REF_NNP("rot_60_ref_nnp", SymmetricGroup3.P231, true, false, false),
   ROT_60_REF_NPN("rot_60_ref_npn", SymmetricGroup3.P231, false, false, true),
   ROT_60_REF_NPP("rot_60_ref_npp", SymmetricGroup3.P312, false, false, true),
   ROT_60_REF_PNN("rot_60_ref_pnn", SymmetricGroup3.P231, false, true, false),
   ROT_60_REF_PNP("rot_60_ref_pnp", SymmetricGroup3.P312, true, false, false),
   ROT_60_REF_PPN("rot_60_ref_ppn", SymmetricGroup3.P312, false, true, false),
   ROT_60_REF_PPP("rot_60_ref_ppp", SymmetricGroup3.P231, true, true, true),
   SWAP_XY("swap_xy", SymmetricGroup3.P213, false, false, false),
   SWAP_YZ("swap_yz", SymmetricGroup3.P132, false, false, false),
   SWAP_XZ("swap_xz", SymmetricGroup3.P321, false, false, false),
   SWAP_NEG_XY("swap_neg_xy", SymmetricGroup3.P213, true, true, false),
   SWAP_NEG_YZ("swap_neg_yz", SymmetricGroup3.P132, false, true, true),
   SWAP_NEG_XZ("swap_neg_xz", SymmetricGroup3.P321, true, false, true),
   ROT_90_REF_X_NEG("rot_90_ref_x_neg", SymmetricGroup3.P132, true, false, true),
   ROT_90_REF_X_POS("rot_90_ref_x_pos", SymmetricGroup3.P132, true, true, false),
   ROT_90_REF_Y_NEG("rot_90_ref_y_neg", SymmetricGroup3.P321, true, true, false),
   ROT_90_REF_Y_POS("rot_90_ref_y_pos", SymmetricGroup3.P321, false, true, true),
   ROT_90_REF_Z_NEG("rot_90_ref_z_neg", SymmetricGroup3.P213, false, true, true),
   ROT_90_REF_Z_POS("rot_90_ref_z_pos", SymmetricGroup3.P213, true, false, true);

   private final Matrix3f f_56473_;
   private final String f_56474_;
   @Nullable
   private Map<Direction, Direction> f_56475_;
   private final boolean f_56476_;
   private final boolean f_56478_;
   private final boolean f_56479_;
   private final SymmetricGroup3 f_56480_;
   private static final OctahedralGroup[][] f_56481_ = Util.m_137469_(new OctahedralGroup[values().length][values().length], (p_56533_) -> {
      Map<Pair<SymmetricGroup3, BooleanList>, OctahedralGroup> map = Arrays.stream(values()).collect(Collectors.toMap((p_174952_) -> {
         return Pair.of(p_174952_.f_56480_, p_174952_.m_56534_());
      }, (p_174950_) -> {
         return p_174950_;
      }));

      for(OctahedralGroup octahedralgroup : values()) {
         for(OctahedralGroup octahedralgroup1 : values()) {
            BooleanList booleanlist = octahedralgroup.m_56534_();
            BooleanList booleanlist1 = octahedralgroup1.m_56534_();
            SymmetricGroup3 symmetricgroup3 = octahedralgroup1.f_56480_.m_109182_(octahedralgroup.f_56480_);
            BooleanArrayList booleanarraylist = new BooleanArrayList(3);

            for(int i = 0; i < 3; ++i) {
               booleanarraylist.add(booleanlist.getBoolean(i) ^ booleanlist1.getBoolean(octahedralgroup.f_56480_.m_109180_(i)));
            }

            p_56533_[octahedralgroup.ordinal()][octahedralgroup1.ordinal()] = map.get(Pair.of(symmetricgroup3, booleanarraylist));
         }
      }

   });
   private static final OctahedralGroup[] f_56482_ = Arrays.stream(values()).map((p_56536_) -> {
      return Arrays.stream(values()).filter((p_174947_) -> {
         return p_56536_.m_56521_(p_174947_) == IDENTITY;
      }).findAny().get();
   }).toArray((p_56520_) -> {
      return new OctahedralGroup[p_56520_];
   });

   private OctahedralGroup(String p_56513_, SymmetricGroup3 p_56514_, boolean p_56515_, boolean p_56516_, boolean p_56517_) {
      this.f_56474_ = p_56513_;
      this.f_56476_ = p_56515_;
      this.f_56478_ = p_56516_;
      this.f_56479_ = p_56517_;
      this.f_56480_ = p_56514_;
      this.f_56473_ = new Matrix3f();
      this.f_56473_.f_8134_ = p_56515_ ? -1.0F : 1.0F;
      this.f_56473_.f_8138_ = p_56516_ ? -1.0F : 1.0F;
      this.f_56473_.f_8142_ = p_56517_ ? -1.0F : 1.0F;
      this.f_56473_.m_8178_(p_56514_.m_109179_());
   }

   private BooleanList m_56534_() {
      return new BooleanArrayList(new boolean[]{this.f_56476_, this.f_56478_, this.f_56479_});
   }

   public OctahedralGroup m_56521_(OctahedralGroup p_56522_) {
      return f_56481_[this.ordinal()][p_56522_.ordinal()];
   }

   public OctahedralGroup m_174944_() {
      return f_56482_[this.ordinal()];
   }

   public Matrix3f m_174948_() {
      return this.f_56473_.m_8183_();
   }

   public String toString() {
      return this.f_56474_;
   }

   public String m_7912_() {
      return this.f_56474_;
   }

   public Direction m_56528_(Direction p_56529_) {
      if (this.f_56475_ == null) {
         this.f_56475_ = Maps.newEnumMap(Direction.class);

         for(Direction direction : Direction.values()) {
            Direction.Axis direction$axis = direction.m_122434_();
            Direction.AxisDirection direction$axisdirection = direction.m_122421_();
            Direction.Axis direction$axis1 = Direction.Axis.values()[this.f_56480_.m_109180_(direction$axis.ordinal())];
            Direction.AxisDirection direction$axisdirection1 = this.m_56526_(direction$axis1) ? direction$axisdirection.m_122541_() : direction$axisdirection;
            Direction direction1 = Direction.m_122387_(direction$axis1, direction$axisdirection1);
            this.f_56475_.put(direction, direction1);
         }
      }

      return this.f_56475_.get(p_56529_);
   }

   public boolean m_56526_(Direction.Axis p_56527_) {
      switch(p_56527_) {
      case X:
         return this.f_56476_;
      case Y:
         return this.f_56478_;
      case Z:
      default:
         return this.f_56479_;
      }
   }

   public FrontAndTop m_56530_(FrontAndTop p_56531_) {
      return FrontAndTop.m_122622_(this.m_56528_(p_56531_.m_122625_()), this.m_56528_(p_56531_.m_122629_()));
   }
}