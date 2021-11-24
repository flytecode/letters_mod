package net.minecraft.client.renderer;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum FaceInfo {
   DOWN(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_)),
   UP(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_)),
   NORTH(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_)),
   SOUTH(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_)),
   WEST(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108996_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_)),
   EAST(new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108991_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108995_, FaceInfo.Constants.f_108994_), new FaceInfo.VertexInfo(FaceInfo.Constants.f_108993_, FaceInfo.Constants.f_108992_, FaceInfo.Constants.f_108994_));

   private static final FaceInfo[] f_108974_ = Util.m_137469_(new FaceInfo[6], (p_108987_) -> {
      p_108987_[FaceInfo.Constants.f_108995_] = DOWN;
      p_108987_[FaceInfo.Constants.f_108992_] = UP;
      p_108987_[FaceInfo.Constants.f_108994_] = NORTH;
      p_108987_[FaceInfo.Constants.f_108991_] = SOUTH;
      p_108987_[FaceInfo.Constants.f_108996_] = WEST;
      p_108987_[FaceInfo.Constants.f_108993_] = EAST;
   });
   private final FaceInfo.VertexInfo[] f_108975_;

   public static FaceInfo m_108984_(Direction p_108985_) {
      return f_108974_[p_108985_.m_122411_()];
   }

   private FaceInfo(FaceInfo.VertexInfo... p_108981_) {
      this.f_108975_ = p_108981_;
   }

   public FaceInfo.VertexInfo m_108982_(int p_108983_) {
      return this.f_108975_[p_108983_];
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Constants {
      public static final int f_108991_ = Direction.SOUTH.m_122411_();
      public static final int f_108992_ = Direction.UP.m_122411_();
      public static final int f_108993_ = Direction.EAST.m_122411_();
      public static final int f_108994_ = Direction.NORTH.m_122411_();
      public static final int f_108995_ = Direction.DOWN.m_122411_();
      public static final int f_108996_ = Direction.WEST.m_122411_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class VertexInfo {
      public final int f_108998_;
      public final int f_108999_;
      public final int f_109000_;

      VertexInfo(int p_109002_, int p_109003_, int p_109004_) {
         this.f_108998_ = p_109002_;
         this.f_108999_ = p_109003_;
         this.f_109000_ = p_109004_;
      }
   }
}