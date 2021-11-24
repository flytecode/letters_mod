package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;

public abstract class NodeEvaluator {
   protected PathNavigationRegion f_77312_;
   protected Mob f_77313_;
   protected final Int2ObjectMap<Node> f_77314_ = new Int2ObjectOpenHashMap<>();
   protected int f_77315_;
   protected int f_77316_;
   protected int f_77317_;
   protected boolean f_77318_;
   protected boolean f_77319_;
   protected boolean f_77320_;

   public void m_6028_(PathNavigationRegion p_77347_, Mob p_77348_) {
      this.f_77312_ = p_77347_;
      this.f_77313_ = p_77348_;
      this.f_77314_.clear();
      this.f_77315_ = Mth.m_14143_(p_77348_.m_20205_() + 1.0F);
      this.f_77316_ = Mth.m_14143_(p_77348_.m_20206_() + 1.0F);
      this.f_77317_ = Mth.m_14143_(p_77348_.m_20205_() + 1.0F);
   }

   public void m_6802_() {
      this.f_77312_ = null;
      this.f_77313_ = null;
   }

   protected Node m_77349_(BlockPos p_77350_) {
      return this.m_5676_(p_77350_.m_123341_(), p_77350_.m_123342_(), p_77350_.m_123343_());
   }

   protected Node m_5676_(int p_77325_, int p_77326_, int p_77327_) {
      return this.f_77314_.computeIfAbsent(Node.m_77295_(p_77325_, p_77326_, p_77327_), (p_77332_) -> {
         return new Node(p_77325_, p_77326_, p_77327_);
      });
   }

   public abstract Node m_7171_();

   public abstract Target m_7568_(double p_77322_, double p_77323_, double p_77324_);

   public abstract int m_6065_(Node[] p_77353_, Node p_77354_);

   public abstract BlockPathTypes m_7209_(BlockGetter p_77337_, int p_77338_, int p_77339_, int p_77340_, Mob p_77341_, int p_77342_, int p_77343_, int p_77344_, boolean p_77345_, boolean p_77346_);

   public abstract BlockPathTypes m_8086_(BlockGetter p_77333_, int p_77334_, int p_77335_, int p_77336_);

   public void m_77351_(boolean p_77352_) {
      this.f_77318_ = p_77352_;
   }

   public void m_77355_(boolean p_77356_) {
      this.f_77319_ = p_77356_;
   }

   public void m_77358_(boolean p_77359_) {
      this.f_77320_ = p_77359_;
   }

   public boolean m_77357_() {
      return this.f_77318_;
   }

   public boolean m_77360_() {
      return this.f_77319_;
   }

   public boolean m_77361_() {
      return this.f_77320_;
   }
}