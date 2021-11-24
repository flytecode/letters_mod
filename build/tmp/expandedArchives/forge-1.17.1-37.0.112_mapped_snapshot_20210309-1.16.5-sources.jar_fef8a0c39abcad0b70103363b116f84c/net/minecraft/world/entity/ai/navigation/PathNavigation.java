package net.minecraft.world.entity.ai.navigation;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public abstract class PathNavigation {
   private static final int f_148217_ = 20;
   protected final Mob f_26494_;
   protected final Level f_26495_;
   @Nullable
   protected Path f_26496_;
   protected double f_26497_;
   protected int f_26498_;
   protected int f_26499_;
   protected Vec3 f_26500_ = Vec3.f_82478_;
   protected Vec3i f_26501_ = Vec3i.f_123288_;
   protected long f_26502_;
   protected long f_26503_;
   protected double f_26504_;
   protected float f_26505_ = 0.5F;
   protected boolean f_26506_;
   protected long f_26507_;
   protected NodeEvaluator f_26508_;
   private BlockPos f_26509_;
   private int f_26510_;
   private float f_26511_ = 1.0F;
   private final PathFinder f_26512_;
   private boolean f_26513_;

   public PathNavigation(Mob p_26515_, Level p_26516_) {
      this.f_26494_ = p_26515_;
      this.f_26495_ = p_26516_;
      int i = Mth.m_14107_(p_26515_.m_21133_(Attributes.f_22277_) * 16.0D);
      this.f_26512_ = this.m_5532_(i);
   }

   public void m_26566_() {
      this.f_26511_ = 1.0F;
   }

   public void m_26529_(float p_26530_) {
      this.f_26511_ = p_26530_;
   }

   public BlockPos m_26567_() {
      return this.f_26509_;
   }

   protected abstract PathFinder m_5532_(int p_26531_);

   public void m_26517_(double p_26518_) {
      this.f_26497_ = p_26518_;
   }

   public boolean m_26568_() {
      return this.f_26506_;
   }

   public void m_26569_() {
      if (this.f_26495_.m_46467_() - this.f_26507_ > 20L) {
         if (this.f_26509_ != null) {
            this.f_26496_ = null;
            this.f_26496_ = this.m_7864_(this.f_26509_, this.f_26510_);
            this.f_26507_ = this.f_26495_.m_46467_();
            this.f_26506_ = false;
         }
      } else {
         this.f_26506_ = true;
      }

   }

   @Nullable
   public final Path m_26524_(double p_26525_, double p_26526_, double p_26527_, int p_26528_) {
      return this.m_7864_(new BlockPos(p_26525_, p_26526_, p_26527_), p_26528_);
   }

   @Nullable
   public Path m_26556_(Stream<BlockPos> p_26557_, int p_26558_) {
      return this.m_26551_(p_26557_.collect(Collectors.toSet()), 8, false, p_26558_);
   }

   @Nullable
   public Path m_26548_(Set<BlockPos> p_26549_, int p_26550_) {
      return this.m_26551_(p_26549_, 8, false, p_26550_);
   }

   @Nullable
   public Path m_7864_(BlockPos p_26546_, int p_26547_) {
      return this.m_26551_(ImmutableSet.of(p_26546_), 8, false, p_26547_);
   }

   @Nullable
   public Path m_148218_(BlockPos p_148219_, int p_148220_, int p_148221_) {
      return this.m_148222_(ImmutableSet.of(p_148219_), 8, false, p_148220_, (float)p_148221_);
   }

   @Nullable
   public Path m_6570_(Entity p_26534_, int p_26535_) {
      return this.m_26551_(ImmutableSet.of(p_26534_.m_142538_()), 16, true, p_26535_);
   }

   @Nullable
   protected Path m_26551_(Set<BlockPos> p_26552_, int p_26553_, boolean p_26554_, int p_26555_) {
      return this.m_148222_(p_26552_, p_26553_, p_26554_, p_26555_, (float)this.f_26494_.m_21133_(Attributes.f_22277_));
   }

   @Nullable
   protected Path m_148222_(Set<BlockPos> p_148223_, int p_148224_, boolean p_148225_, int p_148226_, float p_148227_) {
      if (p_148223_.isEmpty()) {
         return null;
      } else if (this.f_26494_.m_20186_() < (double)this.f_26495_.m_141937_()) {
         return null;
      } else if (!this.m_7632_()) {
         return null;
      } else if (this.f_26496_ != null && !this.f_26496_.m_77392_() && p_148223_.contains(this.f_26509_)) {
         return this.f_26496_;
      } else {
         this.f_26495_.m_46473_().m_6180_("pathfind");
         BlockPos blockpos = p_148225_ ? this.f_26494_.m_142538_().m_7494_() : this.f_26494_.m_142538_();
         int i = (int)(p_148227_ + (float)p_148224_);
         PathNavigationRegion pathnavigationregion = new PathNavigationRegion(this.f_26495_, blockpos.m_142082_(-i, -i, -i), blockpos.m_142082_(i, i, i));
         Path path = this.f_26512_.m_77427_(pathnavigationregion, this.f_26494_, p_148223_, p_148227_, p_148226_, this.f_26511_);
         this.f_26495_.m_46473_().m_7238_();
         if (path != null && path.m_77406_() != null) {
            this.f_26509_ = path.m_77406_();
            this.f_26510_ = p_148226_;
            this.m_26565_();
         }

         return path;
      }
   }

   public boolean m_26519_(double p_26520_, double p_26521_, double p_26522_, double p_26523_) {
      return this.m_26536_(this.m_26524_(p_26520_, p_26521_, p_26522_, 1), p_26523_);
   }

   public boolean m_5624_(Entity p_26532_, double p_26533_) {
      Path path = this.m_6570_(p_26532_, 1);
      return path != null && this.m_26536_(path, p_26533_);
   }

   public boolean m_26536_(@Nullable Path p_26537_, double p_26538_) {
      if (p_26537_ == null) {
         this.f_26496_ = null;
         return false;
      } else {
         if (!p_26537_.m_77385_(this.f_26496_)) {
            this.f_26496_ = p_26537_;
         }

         if (this.m_26571_()) {
            return false;
         } else {
            this.m_6804_();
            if (this.f_26496_.m_77398_() <= 0) {
               return false;
            } else {
               this.f_26497_ = p_26538_;
               Vec3 vec3 = this.m_7475_();
               this.f_26499_ = this.f_26498_;
               this.f_26500_ = vec3;
               return true;
            }
         }
      }
   }

   @Nullable
   public Path m_26570_() {
      return this.f_26496_;
   }

   public void m_7638_() {
      ++this.f_26498_;
      if (this.f_26506_) {
         this.m_26569_();
      }

      if (!this.m_26571_()) {
         if (this.m_7632_()) {
            this.m_7636_();
         } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            Vec3 vec3 = this.m_7475_();
            Vec3 vec31 = this.f_26496_.m_77380_(this.f_26494_);
            if (vec3.f_82480_ > vec31.f_82480_ && !this.f_26494_.m_20096_() && Mth.m_14107_(vec3.f_82479_) == Mth.m_14107_(vec31.f_82479_) && Mth.m_14107_(vec3.f_82481_) == Mth.m_14107_(vec31.f_82481_)) {
               this.f_26496_.m_77374_();
            }
         }

         DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
         if (!this.m_26571_()) {
            Vec3 vec32 = this.f_26496_.m_77380_(this.f_26494_);
            BlockPos blockpos = new BlockPos(vec32);
            this.f_26494_.m_21566_().m_6849_(vec32.f_82479_, this.f_26495_.m_8055_(blockpos.m_7495_()).m_60795_() ? vec32.f_82480_ : WalkNodeEvaluator.m_77611_(this.f_26495_, blockpos), vec32.f_82481_, this.f_26497_);
         }
      }
   }

   protected void m_7636_() {
      Vec3 vec3 = this.m_7475_();
      this.f_26505_ = this.f_26494_.m_20205_() > 0.75F ? this.f_26494_.m_20205_() / 2.0F : 0.75F - this.f_26494_.m_20205_() / 2.0F;
      Vec3i vec3i = this.f_26496_.m_77400_();
      double d0 = Math.abs(this.f_26494_.m_20185_() - ((double)vec3i.m_123341_() + (this.f_26494_.m_20205_() + 1) / 2D)); //Forge: Fix MC-94054
      double d1 = Math.abs(this.f_26494_.m_20186_() - (double)vec3i.m_123342_());
      double d2 = Math.abs(this.f_26494_.m_20189_() - ((double)vec3i.m_123343_() + (this.f_26494_.m_20205_() + 1) / 2D)); //Forge: Fix MC-94054
      boolean flag = d0 <= (double)this.f_26505_ && d2 <= (double)this.f_26505_ && d1 < 1.0D; //Forge: Fix MC-94054
      if (flag || this.f_26494_.m_21481_(this.f_26496_.m_77401_().f_77282_) && this.m_26559_(vec3)) {
         this.f_26496_.m_77374_();
      }

      this.m_6481_(vec3);
   }

   private boolean m_26559_(Vec3 p_26560_) {
      if (this.f_26496_.m_77399_() + 1 >= this.f_26496_.m_77398_()) {
         return false;
      } else {
         Vec3 vec3 = Vec3.m_82539_(this.f_26496_.m_77400_());
         if (!p_26560_.m_82509_(vec3, 2.0D)) {
            return false;
         } else {
            Vec3 vec31 = Vec3.m_82539_(this.f_26496_.m_77396_(this.f_26496_.m_77399_() + 1));
            Vec3 vec32 = vec31.m_82546_(vec3);
            Vec3 vec33 = p_26560_.m_82546_(vec3);
            return vec32.m_82526_(vec33) > 0.0D;
         }
      }
   }

   protected void m_6481_(Vec3 p_26539_) {
      if (this.f_26498_ - this.f_26499_ > 100) {
         if (p_26539_.m_82557_(this.f_26500_) < 2.25D) {
            this.f_26513_ = true;
            this.m_26573_();
         } else {
            this.f_26513_ = false;
         }

         this.f_26499_ = this.f_26498_;
         this.f_26500_ = p_26539_;
      }

      if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
         Vec3i vec3i = this.f_26496_.m_77400_();
         if (vec3i.equals(this.f_26501_)) {
            this.f_26502_ += Util.m_137550_() - this.f_26503_;
         } else {
            this.f_26501_ = vec3i;
            double d0 = p_26539_.m_82554_(Vec3.m_82539_(this.f_26501_));
            this.f_26504_ = this.f_26494_.m_6113_() > 0.0F ? d0 / (double)this.f_26494_.m_6113_() * 1000.0D : 0.0D;
         }

         if (this.f_26504_ > 0.0D && (double)this.f_26502_ > this.f_26504_ * 3.0D) {
            this.m_26564_();
         }

         this.f_26503_ = Util.m_137550_();
      }

   }

   private void m_26564_() {
      this.m_26565_();
      this.m_26573_();
   }

   private void m_26565_() {
      this.f_26501_ = Vec3i.f_123288_;
      this.f_26502_ = 0L;
      this.f_26504_ = 0.0D;
      this.f_26513_ = false;
   }

   public boolean m_26571_() {
      return this.f_26496_ == null || this.f_26496_.m_77392_();
   }

   public boolean m_26572_() {
      return !this.m_26571_();
   }

   public void m_26573_() {
      this.f_26496_ = null;
   }

   protected abstract Vec3 m_7475_();

   protected abstract boolean m_7632_();

   protected boolean m_26574_() {
      return this.f_26494_.m_20072_() || this.f_26494_.m_20077_();
   }

   protected void m_6804_() {
      if (this.f_26496_ != null) {
         for(int i = 0; i < this.f_26496_.m_77398_(); ++i) {
            Node node = this.f_26496_.m_77375_(i);
            Node node1 = i + 1 < this.f_26496_.m_77398_() ? this.f_26496_.m_77375_(i + 1) : null;
            BlockState blockstate = this.f_26495_.m_8055_(new BlockPos(node.f_77271_, node.f_77272_, node.f_77273_));
            if (blockstate.m_60620_(BlockTags.f_144269_)) {
               this.f_26496_.m_77377_(i, node.m_77289_(node.f_77271_, node.f_77272_ + 1, node.f_77273_));
               if (node1 != null && node.f_77272_ >= node1.f_77272_) {
                  this.f_26496_.m_77377_(i + 1, node.m_77289_(node1.f_77271_, node.f_77272_ + 1, node1.f_77273_));
               }
            }
         }

      }
   }

   protected abstract boolean m_6454_(Vec3 p_26540_, Vec3 p_26541_, int p_26542_, int p_26543_, int p_26544_);

   public boolean m_6342_(BlockPos p_26545_) {
      BlockPos blockpos = p_26545_.m_7495_();
      return this.f_26495_.m_8055_(blockpos).m_60804_(this.f_26495_, blockpos);
   }

   public NodeEvaluator m_26575_() {
      return this.f_26508_;
   }

   public void m_7008_(boolean p_26563_) {
      this.f_26508_.m_77358_(p_26563_);
   }

   public boolean m_26576_() {
      return this.f_26508_.m_77361_();
   }

   public void m_26561_(BlockPos p_26562_) {
      if (this.f_26496_ != null && !this.f_26496_.m_77392_() && this.f_26496_.m_77398_() != 0) {
         Node node = this.f_26496_.m_77395_();
         Vec3 vec3 = new Vec3(((double)node.f_77271_ + this.f_26494_.m_20185_()) / 2.0D, ((double)node.f_77272_ + this.f_26494_.m_20186_()) / 2.0D, ((double)node.f_77273_ + this.f_26494_.m_20189_()) / 2.0D);
         if (p_26562_.m_123306_(vec3, (double)(this.f_26496_.m_77398_() - this.f_26496_.m_77399_()))) {
            this.m_26569_();
         }

      }
   }

   public float m_148228_() {
      return this.f_26505_;
   }

   public boolean m_26577_() {
      return this.f_26513_;
   }
}
