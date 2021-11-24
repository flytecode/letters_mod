package net.minecraft.world.level.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WorldBorder {
   public static final double f_156092_ = 5.9999968E7D;
   private final List<BorderChangeListener> f_61905_ = Lists.newArrayList();
   private double f_61906_ = 0.2D;
   private double f_61908_ = 5.0D;
   private int f_61909_ = 15;
   private int f_61910_ = 5;
   private double f_61911_;
   private double f_61912_;
   int f_61913_ = 29999984;
   private WorldBorder.BorderExtent f_61914_ = new WorldBorder.StaticBorderExtent(5.9999968E7D);
   public static final WorldBorder.Settings f_61907_ = new WorldBorder.Settings(0.0D, 0.0D, 0.2D, 5.0D, 5, 15, 5.9999968E7D, 0L, 0.0D);

   public boolean m_61937_(BlockPos p_61938_) {
      return (double)(p_61938_.m_123341_() + 1) > this.m_61955_() && (double)p_61938_.m_123341_() < this.m_61957_() && (double)(p_61938_.m_123343_() + 1) > this.m_61956_() && (double)p_61938_.m_123343_() < this.m_61958_();
   }

   public boolean m_61927_(ChunkPos p_61928_) {
      return (double)p_61928_.m_45608_() > this.m_61955_() && (double)p_61928_.m_45604_() < this.m_61957_() && (double)p_61928_.m_45609_() > this.m_61956_() && (double)p_61928_.m_45605_() < this.m_61958_();
   }

   public boolean m_156093_(double p_156094_, double p_156095_) {
      return p_156094_ > this.m_61955_() && p_156094_ < this.m_61957_() && p_156095_ > this.m_61956_() && p_156095_ < this.m_61958_();
   }

   public boolean m_61935_(AABB p_61936_) {
      return p_61936_.f_82291_ > this.m_61955_() && p_61936_.f_82288_ < this.m_61957_() && p_61936_.f_82293_ > this.m_61956_() && p_61936_.f_82290_ < this.m_61958_();
   }

   public double m_61925_(Entity p_61926_) {
      return this.m_61941_(p_61926_.m_20185_(), p_61926_.m_20189_());
   }

   public VoxelShape m_61946_() {
      return this.f_61914_.m_6186_();
   }

   public double m_61941_(double p_61942_, double p_61943_) {
      double d0 = p_61943_ - this.m_61956_();
      double d1 = this.m_61958_() - p_61943_;
      double d2 = p_61942_ - this.m_61955_();
      double d3 = this.m_61957_() - p_61942_;
      double d4 = Math.min(d2, d3);
      d4 = Math.min(d4, d0);
      return Math.min(d4, d1);
   }

   public BorderStatus m_61954_() {
      return this.f_61914_.m_5570_();
   }

   public double m_61955_() {
      return this.f_61914_.m_6613_();
   }

   public double m_61956_() {
      return this.f_61914_.m_6606_();
   }

   public double m_61957_() {
      return this.f_61914_.m_6608_();
   }

   public double m_61958_() {
      return this.f_61914_.m_6619_();
   }

   public double m_6347_() {
      return this.f_61911_;
   }

   public double m_6345_() {
      return this.f_61912_;
   }

   public void m_61949_(double p_61950_, double p_61951_) {
      this.f_61911_ = p_61950_;
      this.f_61912_ = p_61951_;
      this.f_61914_.m_6622_();

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_7721_(this, p_61950_, p_61951_);
      }

   }

   public double m_61959_() {
      return this.f_61914_.m_6618_();
   }

   public long m_61960_() {
      return this.f_61914_.m_6738_();
   }

   public double m_61961_() {
      return this.f_61914_.m_6605_();
   }

   public void m_61917_(double p_61918_) {
      this.f_61914_ = new WorldBorder.StaticBorderExtent(p_61918_);

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_6312_(this, p_61918_);
      }

   }

   public void m_61919_(double p_61920_, double p_61921_, long p_61922_) {
      this.f_61914_ = (WorldBorder.BorderExtent)(p_61920_ == p_61921_ ? new WorldBorder.StaticBorderExtent(p_61921_) : new WorldBorder.MovingBorderExtent(p_61920_, p_61921_, p_61922_));

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_6689_(this, p_61920_, p_61921_, p_61922_);
      }

   }

   protected List<BorderChangeListener> m_61962_() {
      return Lists.newArrayList(this.f_61905_);
   }

   public void m_61929_(BorderChangeListener p_61930_) {
      this.f_61905_.add(p_61930_);
   }

   public void m_156096_(BorderChangeListener p_156097_) {
      this.f_61905_.remove(p_156097_);
   }

   public void m_61923_(int p_61924_) {
      this.f_61913_ = p_61924_;
      this.f_61914_.m_6623_();
   }

   public int m_61963_() {
      return this.f_61913_;
   }

   public double m_61964_() {
      return this.f_61908_;
   }

   public void m_61939_(double p_61940_) {
      this.f_61908_ = p_61940_;

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_6313_(this, p_61940_);
      }

   }

   public double m_61965_() {
      return this.f_61906_;
   }

   public void m_61947_(double p_61948_) {
      this.f_61906_ = p_61948_;

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_6315_(this, p_61948_);
      }

   }

   public double m_61966_() {
      return this.f_61914_.m_6611_();
   }

   public int m_61967_() {
      return this.f_61909_;
   }

   public void m_61944_(int p_61945_) {
      this.f_61909_ = p_61945_;

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_5904_(this, p_61945_);
      }

   }

   public int m_61968_() {
      return this.f_61910_;
   }

   public void m_61952_(int p_61953_) {
      this.f_61910_ = p_61953_;

      for(BorderChangeListener borderchangelistener : this.m_61962_()) {
         borderchangelistener.m_5903_(this, p_61953_);
      }

   }

   public void m_61969_() {
      this.f_61914_ = this.f_61914_.m_5794_();
   }

   public WorldBorder.Settings m_61970_() {
      return new WorldBorder.Settings(this);
   }

   public void m_61931_(WorldBorder.Settings p_61932_) {
      this.m_61949_(p_61932_.m_62036_(), p_61932_.m_62042_());
      this.m_61947_(p_61932_.m_62043_());
      this.m_61939_(p_61932_.m_62044_());
      this.m_61952_(p_61932_.m_62045_());
      this.m_61944_(p_61932_.m_62046_());
      if (p_61932_.m_62048_() > 0L) {
         this.m_61919_(p_61932_.m_62047_(), p_61932_.m_62049_(), p_61932_.m_62048_());
      } else {
         this.m_61917_(p_61932_.m_62047_());
      }

   }

   interface BorderExtent {
      double m_6613_();

      double m_6608_();

      double m_6606_();

      double m_6619_();

      double m_6618_();

      double m_6611_();

      long m_6738_();

      double m_6605_();

      BorderStatus m_5570_();

      void m_6623_();

      void m_6622_();

      WorldBorder.BorderExtent m_5794_();

      VoxelShape m_6186_();
   }

   class MovingBorderExtent implements WorldBorder.BorderExtent {
      private final double f_61972_;
      private final double f_61973_;
      private final long f_61974_;
      private final long f_61975_;
      private final double f_61976_;

      MovingBorderExtent(double p_61979_, double p_61980_, long p_61981_) {
         this.f_61972_ = p_61979_;
         this.f_61973_ = p_61980_;
         this.f_61976_ = (double)p_61981_;
         this.f_61975_ = Util.m_137550_();
         this.f_61974_ = this.f_61975_ + p_61981_;
      }

      public double m_6613_() {
         return Mth.m_14008_(WorldBorder.this.m_6347_() - this.m_6618_() / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
      }

      public double m_6606_() {
         return Mth.m_14008_(WorldBorder.this.m_6345_() - this.m_6618_() / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
      }

      public double m_6608_() {
         return Mth.m_14008_(WorldBorder.this.m_6347_() + this.m_6618_() / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
      }

      public double m_6619_() {
         return Mth.m_14008_(WorldBorder.this.m_6345_() + this.m_6618_() / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
      }

      public double m_6618_() {
         double d0 = (double)(Util.m_137550_() - this.f_61975_) / this.f_61976_;
         return d0 < 1.0D ? Mth.m_14139_(d0, this.f_61972_, this.f_61973_) : this.f_61973_;
      }

      public double m_6611_() {
         return Math.abs(this.f_61972_ - this.f_61973_) / (double)(this.f_61974_ - this.f_61975_);
      }

      public long m_6738_() {
         return this.f_61974_ - Util.m_137550_();
      }

      public double m_6605_() {
         return this.f_61973_;
      }

      public BorderStatus m_5570_() {
         return this.f_61973_ < this.f_61972_ ? BorderStatus.SHRINKING : BorderStatus.GROWING;
      }

      public void m_6622_() {
      }

      public void m_6623_() {
      }

      public WorldBorder.BorderExtent m_5794_() {
         return (WorldBorder.BorderExtent)(this.m_6738_() <= 0L ? WorldBorder.this.new StaticBorderExtent(this.f_61973_) : this);
      }

      public VoxelShape m_6186_() {
         return Shapes.m_83113_(Shapes.f_83036_, Shapes.m_83048_(Math.floor(this.m_6613_()), Double.NEGATIVE_INFINITY, Math.floor(this.m_6606_()), Math.ceil(this.m_6608_()), Double.POSITIVE_INFINITY, Math.ceil(this.m_6619_())), BooleanOp.f_82685_);
      }
   }

   public static class Settings {
      private final double f_62001_;
      private final double f_62002_;
      private final double f_62003_;
      private final double f_62004_;
      private final int f_62005_;
      private final int f_62006_;
      private final double f_62007_;
      private final long f_62008_;
      private final double f_62009_;

      Settings(double p_62011_, double p_62012_, double p_62013_, double p_62014_, int p_62015_, int p_62016_, double p_62017_, long p_62018_, double p_62019_) {
         this.f_62001_ = p_62011_;
         this.f_62002_ = p_62012_;
         this.f_62003_ = p_62013_;
         this.f_62004_ = p_62014_;
         this.f_62005_ = p_62015_;
         this.f_62006_ = p_62016_;
         this.f_62007_ = p_62017_;
         this.f_62008_ = p_62018_;
         this.f_62009_ = p_62019_;
      }

      Settings(WorldBorder p_62032_) {
         this.f_62001_ = p_62032_.m_6347_();
         this.f_62002_ = p_62032_.m_6345_();
         this.f_62003_ = p_62032_.m_61965_();
         this.f_62004_ = p_62032_.m_61964_();
         this.f_62005_ = p_62032_.m_61968_();
         this.f_62006_ = p_62032_.m_61967_();
         this.f_62007_ = p_62032_.m_61959_();
         this.f_62008_ = p_62032_.m_61960_();
         this.f_62009_ = p_62032_.m_61961_();
      }

      public double m_62036_() {
         return this.f_62001_;
      }

      public double m_62042_() {
         return this.f_62002_;
      }

      public double m_62043_() {
         return this.f_62003_;
      }

      public double m_62044_() {
         return this.f_62004_;
      }

      public int m_62045_() {
         return this.f_62005_;
      }

      public int m_62046_() {
         return this.f_62006_;
      }

      public double m_62047_() {
         return this.f_62007_;
      }

      public long m_62048_() {
         return this.f_62008_;
      }

      public double m_62049_() {
         return this.f_62009_;
      }

      public static WorldBorder.Settings m_62037_(DynamicLike<?> p_62038_, WorldBorder.Settings p_62039_) {
         double d0 = p_62038_.get("BorderCenterX").asDouble(p_62039_.f_62001_);
         double d1 = p_62038_.get("BorderCenterZ").asDouble(p_62039_.f_62002_);
         double d2 = p_62038_.get("BorderSize").asDouble(p_62039_.f_62007_);
         long i = p_62038_.get("BorderSizeLerpTime").asLong(p_62039_.f_62008_);
         double d3 = p_62038_.get("BorderSizeLerpTarget").asDouble(p_62039_.f_62009_);
         double d4 = p_62038_.get("BorderSafeZone").asDouble(p_62039_.f_62004_);
         double d5 = p_62038_.get("BorderDamagePerBlock").asDouble(p_62039_.f_62003_);
         int j = p_62038_.get("BorderWarningBlocks").asInt(p_62039_.f_62005_);
         int k = p_62038_.get("BorderWarningTime").asInt(p_62039_.f_62006_);
         return new WorldBorder.Settings(d0, d1, d5, d4, j, k, d2, i, d3);
      }

      public void m_62040_(CompoundTag p_62041_) {
         p_62041_.m_128347_("BorderCenterX", this.f_62001_);
         p_62041_.m_128347_("BorderCenterZ", this.f_62002_);
         p_62041_.m_128347_("BorderSize", this.f_62007_);
         p_62041_.m_128356_("BorderSizeLerpTime", this.f_62008_);
         p_62041_.m_128347_("BorderSafeZone", this.f_62004_);
         p_62041_.m_128347_("BorderDamagePerBlock", this.f_62003_);
         p_62041_.m_128347_("BorderSizeLerpTarget", this.f_62009_);
         p_62041_.m_128347_("BorderWarningBlocks", (double)this.f_62005_);
         p_62041_.m_128347_("BorderWarningTime", (double)this.f_62006_);
      }
   }

   class StaticBorderExtent implements WorldBorder.BorderExtent {
      private final double f_62051_;
      private double f_62052_;
      private double f_62053_;
      private double f_62054_;
      private double f_62055_;
      private VoxelShape f_62056_;

      public StaticBorderExtent(double p_62059_) {
         this.f_62051_ = p_62059_;
         this.m_62073_();
      }

      public double m_6613_() {
         return this.f_62052_;
      }

      public double m_6608_() {
         return this.f_62054_;
      }

      public double m_6606_() {
         return this.f_62053_;
      }

      public double m_6619_() {
         return this.f_62055_;
      }

      public double m_6618_() {
         return this.f_62051_;
      }

      public BorderStatus m_5570_() {
         return BorderStatus.STATIONARY;
      }

      public double m_6611_() {
         return 0.0D;
      }

      public long m_6738_() {
         return 0L;
      }

      public double m_6605_() {
         return this.f_62051_;
      }

      private void m_62073_() {
         this.f_62052_ = Mth.m_14008_(WorldBorder.this.m_6347_() - this.f_62051_ / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
         this.f_62053_ = Mth.m_14008_(WorldBorder.this.m_6345_() - this.f_62051_ / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
         this.f_62054_ = Mth.m_14008_(WorldBorder.this.m_6347_() + this.f_62051_ / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
         this.f_62055_ = Mth.m_14008_(WorldBorder.this.m_6345_() + this.f_62051_ / 2.0D, (double)(-WorldBorder.this.f_61913_), (double)WorldBorder.this.f_61913_);
         this.f_62056_ = Shapes.m_83113_(Shapes.f_83036_, Shapes.m_83048_(Math.floor(this.m_6613_()), Double.NEGATIVE_INFINITY, Math.floor(this.m_6606_()), Math.ceil(this.m_6608_()), Double.POSITIVE_INFINITY, Math.ceil(this.m_6619_())), BooleanOp.f_82685_);
      }

      public void m_6623_() {
         this.m_62073_();
      }

      public void m_6622_() {
         this.m_62073_();
      }

      public WorldBorder.BorderExtent m_5794_() {
         return this;
      }

      public VoxelShape m_6186_() {
         return this.f_62056_;
      }
   }
}