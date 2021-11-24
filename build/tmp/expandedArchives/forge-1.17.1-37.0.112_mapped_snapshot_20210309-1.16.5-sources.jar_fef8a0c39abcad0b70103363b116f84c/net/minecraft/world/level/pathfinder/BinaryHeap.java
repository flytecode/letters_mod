package net.minecraft.world.level.pathfinder;

public class BinaryHeap {
   private Node[] f_77078_ = new Node[128];
   private int f_77079_;

   public Node m_77084_(Node p_77085_) {
      if (p_77085_.f_77274_ >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.f_77079_ == this.f_77078_.length) {
            Node[] anode = new Node[this.f_77079_ << 1];
            System.arraycopy(this.f_77078_, 0, anode, 0, this.f_77079_);
            this.f_77078_ = anode;
         }

         this.f_77078_[this.f_77079_] = p_77085_;
         p_77085_.f_77274_ = this.f_77079_;
         this.m_77082_(this.f_77079_++);
         return p_77085_;
      }
   }

   public void m_77081_() {
      this.f_77079_ = 0;
   }

   public Node m_164680_() {
      return this.f_77078_[0];
   }

   public Node m_77091_() {
      Node node = this.f_77078_[0];
      this.f_77078_[0] = this.f_77078_[--this.f_77079_];
      this.f_77078_[this.f_77079_] = null;
      if (this.f_77079_ > 0) {
         this.m_77089_(0);
      }

      node.f_77274_ = -1;
      return node;
   }

   public void m_164681_(Node p_164682_) {
      this.f_77078_[p_164682_.f_77274_] = this.f_77078_[--this.f_77079_];
      this.f_77078_[this.f_77079_] = null;
      if (this.f_77079_ > p_164682_.f_77274_) {
         if (this.f_77078_[p_164682_.f_77274_].f_77277_ < p_164682_.f_77277_) {
            this.m_77082_(p_164682_.f_77274_);
         } else {
            this.m_77089_(p_164682_.f_77274_);
         }
      }

      p_164682_.f_77274_ = -1;
   }

   public void m_77086_(Node p_77087_, float p_77088_) {
      float f = p_77087_.f_77277_;
      p_77087_.f_77277_ = p_77088_;
      if (p_77088_ < f) {
         this.m_77082_(p_77087_.f_77274_);
      } else {
         this.m_77089_(p_77087_.f_77274_);
      }

   }

   public int m_164683_() {
      return this.f_77079_;
   }

   private void m_77082_(int p_77083_) {
      Node node = this.f_77078_[p_77083_];

      int i;
      for(float f = node.f_77277_; p_77083_ > 0; p_77083_ = i) {
         i = p_77083_ - 1 >> 1;
         Node node1 = this.f_77078_[i];
         if (!(f < node1.f_77277_)) {
            break;
         }

         this.f_77078_[p_77083_] = node1;
         node1.f_77274_ = p_77083_;
      }

      this.f_77078_[p_77083_] = node;
      node.f_77274_ = p_77083_;
   }

   private void m_77089_(int p_77090_) {
      Node node = this.f_77078_[p_77090_];
      float f = node.f_77277_;

      while(true) {
         int i = 1 + (p_77090_ << 1);
         int j = i + 1;
         if (i >= this.f_77079_) {
            break;
         }

         Node node1 = this.f_77078_[i];
         float f1 = node1.f_77277_;
         Node node2;
         float f2;
         if (j >= this.f_77079_) {
            node2 = null;
            f2 = Float.POSITIVE_INFINITY;
         } else {
            node2 = this.f_77078_[j];
            f2 = node2.f_77277_;
         }

         if (f1 < f2) {
            if (!(f1 < f)) {
               break;
            }

            this.f_77078_[p_77090_] = node1;
            node1.f_77274_ = p_77090_;
            p_77090_ = i;
         } else {
            if (!(f2 < f)) {
               break;
            }

            this.f_77078_[p_77090_] = node2;
            node2.f_77274_ = p_77090_;
            p_77090_ = j;
         }
      }

      this.f_77078_[p_77090_] = node;
      node.f_77274_ = p_77090_;
   }

   public boolean m_77092_() {
      return this.f_77079_ == 0;
   }

   public Node[] m_164684_() {
      Node[] anode = new Node[this.m_164683_()];
      System.arraycopy(this.f_77078_, 0, anode, 0, this.m_164683_());
      return anode;
   }
}