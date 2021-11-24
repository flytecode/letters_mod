package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseSpawner {
   private static final Logger f_45441_ = LogManager.getLogger();
   private static final int f_151303_ = 1;
   private static WeightedRandomList<SpawnData> f_151304_ = WeightedRandomList.m_146332_();
   private int f_45442_ = 20;
   private WeightedRandomList<SpawnData> f_45443_ = f_151304_;
   private SpawnData f_45444_ = new SpawnData();
   private double f_45445_;
   private double f_45446_;
   private int f_45447_ = 200;
   private int f_45448_ = 800;
   private int f_45449_ = 4;
   @Nullable
   private Entity f_45450_;
   private int f_45451_ = 6;
   private int f_45452_ = 16;
   private int f_45453_ = 4;
   private final Random f_151305_ = new Random();

   @Nullable
   private ResourceLocation m_151332_(@Nullable Level p_151333_, BlockPos p_151334_) {
      String s = this.f_45444_.m_47265_().m_128461_("id");

      try {
         return StringUtil.m_14408_(s) ? null : new ResourceLocation(s);
      } catch (ResourceLocationException resourcelocationexception) {
         f_45441_.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", s, p_151333_ != null ? p_151333_.m_46472_().m_135782_() : "<null>", p_151334_.m_123341_(), p_151334_.m_123342_(), p_151334_.m_123343_());
         return null;
      }
   }

   public void m_45462_(EntityType<?> p_45463_) {
      this.f_45444_.m_47265_().m_128359_("id", Registry.f_122826_.m_7981_(p_45463_).toString());
   }

   private boolean m_151343_(Level p_151344_, BlockPos p_151345_) {
      return p_151344_.m_45914_((double)p_151345_.m_123341_() + 0.5D, (double)p_151345_.m_123342_() + 0.5D, (double)p_151345_.m_123343_() + 0.5D, (double)this.f_45452_);
   }

   public void m_151319_(Level p_151320_, BlockPos p_151321_) {
      if (!this.m_151343_(p_151320_, p_151321_)) {
         this.f_45446_ = this.f_45445_;
      } else {
         double d0 = (double)p_151321_.m_123341_() + p_151320_.f_46441_.nextDouble();
         double d1 = (double)p_151321_.m_123342_() + p_151320_.f_46441_.nextDouble();
         double d2 = (double)p_151321_.m_123343_() + p_151320_.f_46441_.nextDouble();
         p_151320_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         p_151320_.m_7106_(ParticleTypes.f_123744_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         if (this.f_45442_ > 0) {
            --this.f_45442_;
         }

         this.f_45446_ = this.f_45445_;
         this.f_45445_ = (this.f_45445_ + (double)(1000.0F / ((float)this.f_45442_ + 200.0F))) % 360.0D;
      }

   }

   public void m_151311_(ServerLevel p_151312_, BlockPos p_151313_) {
      if (this.m_151343_(p_151312_, p_151313_)) {
         if (this.f_45442_ == -1) {
            this.m_151350_(p_151312_, p_151313_);
         }

         if (this.f_45442_ > 0) {
            --this.f_45442_;
         } else {
            boolean flag = false;

            for(int i = 0; i < this.f_45449_; ++i) {
               CompoundTag compoundtag = this.f_45444_.m_47265_();
               Optional<EntityType<?>> optional = EntityType.m_20637_(compoundtag);
               if (!optional.isPresent()) {
                  this.m_151350_(p_151312_, p_151313_);
                  return;
               }

               ListTag listtag = compoundtag.m_128437_("Pos", 6);
               int j = listtag.size();
               double d0 = j >= 1 ? listtag.m_128772_(0) : (double)p_151313_.m_123341_() + (p_151312_.f_46441_.nextDouble() - p_151312_.f_46441_.nextDouble()) * (double)this.f_45453_ + 0.5D;
               double d1 = j >= 2 ? listtag.m_128772_(1) : (double)(p_151313_.m_123342_() + p_151312_.f_46441_.nextInt(3) - 1);
               double d2 = j >= 3 ? listtag.m_128772_(2) : (double)p_151313_.m_123343_() + (p_151312_.f_46441_.nextDouble() - p_151312_.f_46441_.nextDouble()) * (double)this.f_45453_ + 0.5D;
               if (p_151312_.m_45772_(optional.get().m_20585_(d0, d1, d2)) && SpawnPlacements.m_21759_(optional.get(), p_151312_, MobSpawnType.SPAWNER, new BlockPos(d0, d1, d2), p_151312_.m_5822_())) {
                  Entity entity = EntityType.m_20645_(compoundtag, p_151312_, (p_151310_) -> {
                     p_151310_.m_7678_(d0, d1, d2, p_151310_.m_146908_(), p_151310_.m_146909_());
                     return p_151310_;
                  });
                  if (entity == null) {
                     this.m_151350_(p_151312_, p_151313_);
                     return;
                  }

                  int k = p_151312_.m_45976_(entity.getClass(), (new AABB((double)p_151313_.m_123341_(), (double)p_151313_.m_123342_(), (double)p_151313_.m_123343_(), (double)(p_151313_.m_123341_() + 1), (double)(p_151313_.m_123342_() + 1), (double)(p_151313_.m_123343_() + 1))).m_82400_((double)this.f_45453_)).size();
                  if (k >= this.f_45451_) {
                     this.m_151350_(p_151312_, p_151313_);
                     return;
                  }

                  entity.m_7678_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), p_151312_.f_46441_.nextFloat() * 360.0F, 0.0F);
                  if (entity instanceof Mob) {
                     Mob mob = (Mob)entity;
                     if (!net.minecraftforge.event.ForgeEventFactory.canEntitySpawnSpawner(mob, p_151312_, (float)entity.m_20185_(), (float)entity.m_20186_(), (float)entity.m_20189_(), this)) {
                        continue;
                     }

                     if (this.f_45444_.m_47265_().m_128440_() == 1 && this.f_45444_.m_47265_().m_128425_("id", 8)) {
                        if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mob, p_151312_, (float)entity.m_20185_(), (float)entity.m_20186_(), (float)entity.m_20189_(), this, MobSpawnType.SPAWNER))
                        ((Mob)entity).m_6518_(p_151312_, p_151312_.m_6436_(entity.m_142538_()), MobSpawnType.SPAWNER, (SpawnGroupData)null, (CompoundTag)null);
                     }
                  }

                  if (!p_151312_.m_8860_(entity)) {
                     this.m_151350_(p_151312_, p_151313_);
                     return;
                  }

                  p_151312_.m_46796_(2004, p_151313_, 0);
                  if (entity instanceof Mob) {
                     ((Mob)entity).m_21373_();
                  }

                  flag = true;
               }
            }

            if (flag) {
               this.m_151350_(p_151312_, p_151313_);
            }

         }
      }
   }

   private void m_151350_(Level p_151351_, BlockPos p_151352_) {
      if (this.f_45448_ <= this.f_45447_) {
         this.f_45442_ = this.f_45447_;
      } else {
         this.f_45442_ = this.f_45447_ + this.f_151305_.nextInt(this.f_45448_ - this.f_45447_);
      }

      this.f_45443_.m_146335_(this.f_151305_).ifPresent((p_151349_) -> {
         this.m_142667_(p_151351_, p_151352_, p_151349_);
      });
      this.m_142523_(p_151351_, p_151352_, 1);
   }

   public void m_151328_(@Nullable Level p_151329_, BlockPos p_151330_, CompoundTag p_151331_) {
      this.f_45442_ = p_151331_.m_128448_("Delay");
      List<SpawnData> list = Lists.newArrayList();
      if (p_151331_.m_128425_("SpawnPotentials", 9)) {
         ListTag listtag = p_151331_.m_128437_("SpawnPotentials", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            list.add(new SpawnData(listtag.m_128728_(i)));
         }
      }

      this.f_45443_ = WeightedRandomList.m_146328_(list);
      if (p_151331_.m_128425_("SpawnData", 10)) {
         this.m_142667_(p_151329_, p_151330_, new SpawnData(1, p_151331_.m_128469_("SpawnData")));
      } else if (!list.isEmpty()) {
         this.f_45443_.m_146335_(this.f_151305_).ifPresent((p_151338_) -> {
            this.m_142667_(p_151329_, p_151330_, p_151338_);
         });
      }

      if (p_151331_.m_128425_("MinSpawnDelay", 99)) {
         this.f_45447_ = p_151331_.m_128448_("MinSpawnDelay");
         this.f_45448_ = p_151331_.m_128448_("MaxSpawnDelay");
         this.f_45449_ = p_151331_.m_128448_("SpawnCount");
      }

      if (p_151331_.m_128425_("MaxNearbyEntities", 99)) {
         this.f_45451_ = p_151331_.m_128448_("MaxNearbyEntities");
         this.f_45452_ = p_151331_.m_128448_("RequiredPlayerRange");
      }

      if (p_151331_.m_128425_("SpawnRange", 99)) {
         this.f_45453_ = p_151331_.m_128448_("SpawnRange");
      }

      this.f_45450_ = null;
   }

   public CompoundTag m_151339_(@Nullable Level p_151340_, BlockPos p_151341_, CompoundTag p_151342_) {
      ResourceLocation resourcelocation = this.m_151332_(p_151340_, p_151341_);
      if (resourcelocation == null) {
         return p_151342_;
      } else {
         p_151342_.m_128376_("Delay", (short)this.f_45442_);
         p_151342_.m_128376_("MinSpawnDelay", (short)this.f_45447_);
         p_151342_.m_128376_("MaxSpawnDelay", (short)this.f_45448_);
         p_151342_.m_128376_("SpawnCount", (short)this.f_45449_);
         p_151342_.m_128376_("MaxNearbyEntities", (short)this.f_45451_);
         p_151342_.m_128376_("RequiredPlayerRange", (short)this.f_45452_);
         p_151342_.m_128376_("SpawnRange", (short)this.f_45453_);
         p_151342_.m_128365_("SpawnData", this.f_45444_.m_47265_().m_6426_());
         ListTag listtag = new ListTag();
         if (this.f_45443_.m_146337_()) {
            listtag.add(this.f_45444_.m_47264_());
         } else {
            for(SpawnData spawndata : this.f_45443_.m_146338_()) {
               listtag.add(spawndata.m_47264_());
            }
         }

         p_151342_.m_128365_("SpawnPotentials", listtag);
         return p_151342_;
      }
   }

   @Nullable
   public Entity m_151314_(Level p_151315_) {
      if (this.f_45450_ == null) {
         this.f_45450_ = EntityType.m_20645_(this.f_45444_.m_47265_(), p_151315_, Function.identity());
         if (this.f_45444_.m_47265_().m_128440_() == 1 && this.f_45444_.m_47265_().m_128425_("id", 8) && this.f_45450_ instanceof Mob) {
         }
      }

      return this.f_45450_;
   }

   public boolean m_151316_(Level p_151317_, int p_151318_) {
      if (p_151318_ == 1) {
         if (p_151317_.f_46443_) {
            this.f_45442_ = this.f_45447_;
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_142667_(@Nullable Level p_151325_, BlockPos p_151326_, SpawnData p_151327_) {
      this.f_45444_ = p_151327_;
   }

   public abstract void m_142523_(Level p_151322_, BlockPos p_151323_, int p_151324_);

   public double m_45473_() {
      return this.f_45445_;
   }

   public double m_45474_() {
      return this.f_45446_;
   }

   @Nullable
   public Entity getSpawnerEntity() {
      return null;
   }

   @Nullable
   public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return null; }
}
