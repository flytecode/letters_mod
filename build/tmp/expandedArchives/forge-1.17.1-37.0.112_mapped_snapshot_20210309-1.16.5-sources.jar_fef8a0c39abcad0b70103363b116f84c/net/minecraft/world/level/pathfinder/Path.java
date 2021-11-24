package net.minecraft.world.level.pathfinder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Path {
   private final List<Node> f_77362_;
   private Node[] f_77363_ = new Node[0];
   private Node[] f_77364_ = new Node[0];
   private Set<Target> f_77365_;
   private int f_77366_;
   private final BlockPos f_77367_;
   private final float f_77368_;
   private final boolean f_77369_;

   public Path(List<Node> p_77371_, BlockPos p_77372_, boolean p_77373_) {
      this.f_77362_ = p_77371_;
      this.f_77367_ = p_77372_;
      this.f_77368_ = p_77371_.isEmpty() ? Float.MAX_VALUE : this.f_77362_.get(this.f_77362_.size() - 1).m_77306_(this.f_77367_);
      this.f_77369_ = p_77373_;
   }

   public void m_77374_() {
      ++this.f_77366_;
   }

   public boolean m_77387_() {
      return this.f_77366_ <= 0;
   }

   public boolean m_77392_() {
      return this.f_77366_ >= this.f_77362_.size();
   }

   @Nullable
   public Node m_77395_() {
      return !this.f_77362_.isEmpty() ? this.f_77362_.get(this.f_77362_.size() - 1) : null;
   }

   public Node m_77375_(int p_77376_) {
      return this.f_77362_.get(p_77376_);
   }

   public void m_77388_(int p_77389_) {
      if (this.f_77362_.size() > p_77389_) {
         this.f_77362_.subList(p_77389_, this.f_77362_.size()).clear();
      }

   }

   public void m_77377_(int p_77378_, Node p_77379_) {
      this.f_77362_.set(p_77378_, p_77379_);
   }

   public int m_77398_() {
      return this.f_77362_.size();
   }

   public int m_77399_() {
      return this.f_77366_;
   }

   public void m_77393_(int p_77394_) {
      this.f_77366_ = p_77394_;
   }

   public Vec3 m_77382_(Entity p_77383_, int p_77384_) {
      Node node = this.f_77362_.get(p_77384_);
      double d0 = (double)node.f_77271_ + (double)((int)(p_77383_.m_20205_() + 1.0F)) * 0.5D;
      double d1 = (double)node.f_77272_;
      double d2 = (double)node.f_77273_ + (double)((int)(p_77383_.m_20205_() + 1.0F)) * 0.5D;
      return new Vec3(d0, d1, d2);
   }

   public BlockPos m_77396_(int p_77397_) {
      return this.f_77362_.get(p_77397_).m_77288_();
   }

   public Vec3 m_77380_(Entity p_77381_) {
      return this.m_77382_(p_77381_, this.f_77366_);
   }

   public BlockPos m_77400_() {
      return this.f_77362_.get(this.f_77366_).m_77288_();
   }

   public Node m_77401_() {
      return this.f_77362_.get(this.f_77366_);
   }

   @Nullable
   public Node m_77402_() {
      return this.f_77366_ > 0 ? this.f_77362_.get(this.f_77366_ - 1) : null;
   }

   public boolean m_77385_(@Nullable Path p_77386_) {
      if (p_77386_ == null) {
         return false;
      } else if (p_77386_.f_77362_.size() != this.f_77362_.size()) {
         return false;
      } else {
         for(int i = 0; i < this.f_77362_.size(); ++i) {
            Node node = this.f_77362_.get(i);
            Node node1 = p_77386_.f_77362_.get(i);
            if (node.f_77271_ != node1.f_77271_ || node.f_77272_ != node1.f_77272_ || node.f_77273_ != node1.f_77273_) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean m_77403_() {
      return this.f_77369_;
   }

   @VisibleForDebug
   void m_164709_(Node[] p_164710_, Node[] p_164711_, Set<Target> p_164712_) {
      this.f_77363_ = p_164710_;
      this.f_77364_ = p_164711_;
      this.f_77365_ = p_164712_;
   }

   @VisibleForDebug
   public Node[] m_77404_() {
      return this.f_77363_;
   }

   @VisibleForDebug
   public Node[] m_77405_() {
      return this.f_77364_;
   }

   public void m_164704_(FriendlyByteBuf p_164705_) {
      if (this.f_77365_ != null && !this.f_77365_.isEmpty()) {
         p_164705_.writeBoolean(this.f_77369_);
         p_164705_.writeInt(this.f_77366_);
         p_164705_.writeInt(this.f_77365_.size());
         this.f_77365_.forEach((p_164708_) -> {
            p_164708_.m_164699_(p_164705_);
         });
         p_164705_.writeInt(this.f_77367_.m_123341_());
         p_164705_.writeInt(this.f_77367_.m_123342_());
         p_164705_.writeInt(this.f_77367_.m_123343_());
         p_164705_.writeInt(this.f_77362_.size());

         for(Node node : this.f_77362_) {
            node.m_164699_(p_164705_);
         }

         p_164705_.writeInt(this.f_77363_.length);

         for(Node node1 : this.f_77363_) {
            node1.m_164699_(p_164705_);
         }

         p_164705_.writeInt(this.f_77364_.length);

         for(Node node2 : this.f_77364_) {
            node2.m_164699_(p_164705_);
         }

      }
   }

   public static Path m_77390_(FriendlyByteBuf p_77391_) {
      boolean flag = p_77391_.readBoolean();
      int i = p_77391_.readInt();
      int j = p_77391_.readInt();
      Set<Target> set = Sets.newHashSet();

      for(int k = 0; k < j; ++k) {
         set.add(Target.m_77506_(p_77391_));
      }

      BlockPos blockpos = new BlockPos(p_77391_.readInt(), p_77391_.readInt(), p_77391_.readInt());
      List<Node> list = Lists.newArrayList();
      int l = p_77391_.readInt();

      for(int i1 = 0; i1 < l; ++i1) {
         list.add(Node.m_77301_(p_77391_));
      }

      Node[] anode = new Node[p_77391_.readInt()];

      for(int j1 = 0; j1 < anode.length; ++j1) {
         anode[j1] = Node.m_77301_(p_77391_);
      }

      Node[] anode1 = new Node[p_77391_.readInt()];

      for(int k1 = 0; k1 < anode1.length; ++k1) {
         anode1[k1] = Node.m_77301_(p_77391_);
      }

      Path path = new Path(list, blockpos, flag);
      path.f_77363_ = anode;
      path.f_77364_ = anode1;
      path.f_77365_ = set;
      path.f_77366_ = i;
      return path;
   }

   public String toString() {
      return "Path(length=" + this.f_77362_.size() + ")";
   }

   public BlockPos m_77406_() {
      return this.f_77367_;
   }

   public float m_77407_() {
      return this.f_77368_;
   }
}