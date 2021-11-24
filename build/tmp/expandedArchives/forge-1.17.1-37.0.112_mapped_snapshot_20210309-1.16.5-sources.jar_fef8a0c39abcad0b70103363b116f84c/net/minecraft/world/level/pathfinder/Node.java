package net.minecraft.world.level.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class Node {
   public final int f_77271_;
   public final int f_77272_;
   public final int f_77273_;
   private final int f_77283_;
   public int f_77274_ = -1;
   public float f_77275_;
   public float f_77276_;
   public float f_77277_;
   public Node f_77278_;
   public boolean f_77279_;
   public float f_77280_;
   public float f_77281_;
   public BlockPathTypes f_77282_ = BlockPathTypes.BLOCKED;

   public Node(int p_77285_, int p_77286_, int p_77287_) {
      this.f_77271_ = p_77285_;
      this.f_77272_ = p_77286_;
      this.f_77273_ = p_77287_;
      this.f_77283_ = m_77295_(p_77285_, p_77286_, p_77287_);
   }

   public Node m_77289_(int p_77290_, int p_77291_, int p_77292_) {
      Node node = new Node(p_77290_, p_77291_, p_77292_);
      node.f_77274_ = this.f_77274_;
      node.f_77275_ = this.f_77275_;
      node.f_77276_ = this.f_77276_;
      node.f_77277_ = this.f_77277_;
      node.f_77278_ = this.f_77278_;
      node.f_77279_ = this.f_77279_;
      node.f_77280_ = this.f_77280_;
      node.f_77281_ = this.f_77281_;
      node.f_77282_ = this.f_77282_;
      return node;
   }

   public static int m_77295_(int p_77296_, int p_77297_, int p_77298_) {
      return p_77297_ & 255 | (p_77296_ & 32767) << 8 | (p_77298_ & 32767) << 24 | (p_77296_ < 0 ? Integer.MIN_VALUE : 0) | (p_77298_ < 0 ? '\u8000' : 0);
   }

   public float m_77293_(Node p_77294_) {
      float f = (float)(p_77294_.f_77271_ - this.f_77271_);
      float f1 = (float)(p_77294_.f_77272_ - this.f_77272_);
      float f2 = (float)(p_77294_.f_77273_ - this.f_77273_);
      return Mth.m_14116_(f * f + f1 * f1 + f2 * f2);
   }

   public float m_164697_(BlockPos p_164698_) {
      float f = (float)(p_164698_.m_123341_() - this.f_77271_);
      float f1 = (float)(p_164698_.m_123342_() - this.f_77272_);
      float f2 = (float)(p_164698_.m_123343_() - this.f_77273_);
      return Mth.m_14116_(f * f + f1 * f1 + f2 * f2);
   }

   public float m_77299_(Node p_77300_) {
      float f = (float)(p_77300_.f_77271_ - this.f_77271_);
      float f1 = (float)(p_77300_.f_77272_ - this.f_77272_);
      float f2 = (float)(p_77300_.f_77273_ - this.f_77273_);
      return f * f + f1 * f1 + f2 * f2;
   }

   public float m_164702_(BlockPos p_164703_) {
      float f = (float)(p_164703_.m_123341_() - this.f_77271_);
      float f1 = (float)(p_164703_.m_123342_() - this.f_77272_);
      float f2 = (float)(p_164703_.m_123343_() - this.f_77273_);
      return f * f + f1 * f1 + f2 * f2;
   }

   public float m_77304_(Node p_77305_) {
      float f = (float)Math.abs(p_77305_.f_77271_ - this.f_77271_);
      float f1 = (float)Math.abs(p_77305_.f_77272_ - this.f_77272_);
      float f2 = (float)Math.abs(p_77305_.f_77273_ - this.f_77273_);
      return f + f1 + f2;
   }

   public float m_77306_(BlockPos p_77307_) {
      float f = (float)Math.abs(p_77307_.m_123341_() - this.f_77271_);
      float f1 = (float)Math.abs(p_77307_.m_123342_() - this.f_77272_);
      float f2 = (float)Math.abs(p_77307_.m_123343_() - this.f_77273_);
      return f + f1 + f2;
   }

   public BlockPos m_77288_() {
      return new BlockPos(this.f_77271_, this.f_77272_, this.f_77273_);
   }

   public Vec3 m_164701_() {
      return new Vec3((double)this.f_77271_, (double)this.f_77272_, (double)this.f_77273_);
   }

   public boolean equals(Object p_77309_) {
      if (!(p_77309_ instanceof Node)) {
         return false;
      } else {
         Node node = (Node)p_77309_;
         return this.f_77283_ == node.f_77283_ && this.f_77271_ == node.f_77271_ && this.f_77272_ == node.f_77272_ && this.f_77273_ == node.f_77273_;
      }
   }

   public int hashCode() {
      return this.f_77283_;
   }

   public boolean m_77303_() {
      return this.f_77274_ >= 0;
   }

   public String toString() {
      return "Node{x=" + this.f_77271_ + ", y=" + this.f_77272_ + ", z=" + this.f_77273_ + "}";
   }

   public void m_164699_(FriendlyByteBuf p_164700_) {
      p_164700_.writeInt(this.f_77271_);
      p_164700_.writeInt(this.f_77272_);
      p_164700_.writeInt(this.f_77273_);
      p_164700_.writeFloat(this.f_77280_);
      p_164700_.writeFloat(this.f_77281_);
      p_164700_.writeBoolean(this.f_77279_);
      p_164700_.writeInt(this.f_77282_.ordinal());
      p_164700_.writeFloat(this.f_77277_);
   }

   public static Node m_77301_(FriendlyByteBuf p_77302_) {
      Node node = new Node(p_77302_.readInt(), p_77302_.readInt(), p_77302_.readInt());
      node.f_77280_ = p_77302_.readFloat();
      node.f_77281_ = p_77302_.readFloat();
      node.f_77279_ = p_77302_.readBoolean();
      node.f_77282_ = BlockPathTypes.values()[p_77302_.readInt()];
      node.f_77277_ = p_77302_.readFloat();
      return node;
   }
}