package net.minecraft.gametest.framework;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GameTestHelper {
   private final GameTestInfo f_127595_;
   private boolean f_177099_;

   public GameTestHelper(GameTestInfo p_127597_) {
      this.f_127595_ = p_127597_;
   }

   public ServerLevel m_177100_() {
      return this.f_127595_.m_127637_();
   }

   public BlockState m_177232_(BlockPos p_177233_) {
      return this.m_177100_().m_8055_(this.m_177449_(p_177233_));
   }

   @Nullable
   public BlockEntity m_177347_(BlockPos p_177348_) {
      return this.m_177100_().m_7702_(this.m_177449_(p_177348_));
   }

   public void m_177301_() {
      AABB aabb = this.m_177448_();
      List<Entity> list = this.m_177100_().m_6443_(Entity.class, aabb.m_82400_(1.0D), (p_177131_) -> {
         return !(p_177131_ instanceof Player);
      });
      list.forEach(Entity::m_6074_);
   }

   public ItemEntity m_177189_(Item p_177190_, float p_177191_, float p_177192_, float p_177193_) {
      ServerLevel serverlevel = this.m_177100_();
      Vec3 vec3 = this.m_177227_(new Vec3((double)p_177191_, (double)p_177192_, (double)p_177193_));
      ItemEntity itementity = new ItemEntity(serverlevel, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, new ItemStack(p_177190_, 1));
      itementity.m_20334_(0.0D, 0.0D, 0.0D);
      serverlevel.m_7967_(itementity);
      return itementity;
   }

   public <E extends Entity> E m_177176_(EntityType<E> p_177177_, BlockPos p_177178_) {
      return this.m_177173_(p_177177_, Vec3.m_82539_(p_177178_));
   }

   public <E extends Entity> E m_177173_(EntityType<E> p_177174_, Vec3 p_177175_) {
      ServerLevel serverlevel = this.m_177100_();
      E e = p_177174_.m_20615_(serverlevel);
      if (e instanceof Mob) {
         ((Mob)e).m_21530_();
      }

      Vec3 vec3 = this.m_177227_(p_177175_);
      e.m_7678_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, e.m_146908_(), e.m_146909_());
      serverlevel.m_7967_(e);
      return e;
   }

   public <E extends Entity> E m_177168_(EntityType<E> p_177169_, int p_177170_, int p_177171_, int p_177172_) {
      return this.m_177176_(p_177169_, new BlockPos(p_177170_, p_177171_, p_177172_));
   }

   public <E extends Entity> E m_177163_(EntityType<E> p_177164_, float p_177165_, float p_177166_, float p_177167_) {
      return this.m_177173_(p_177164_, new Vec3((double)p_177165_, (double)p_177166_, (double)p_177167_));
   }

   public <E extends Mob> E m_177329_(EntityType<E> p_177330_, BlockPos p_177331_) {
      E e = this.m_177176_(p_177330_, p_177331_);
      e.m_147272_();
      return e;
   }

   public <E extends Mob> E m_177321_(EntityType<E> p_177322_, int p_177323_, int p_177324_, int p_177325_) {
      return this.m_177329_(p_177322_, new BlockPos(p_177323_, p_177324_, p_177325_));
   }

   public <E extends Mob> E m_177326_(EntityType<E> p_177327_, Vec3 p_177328_) {
      E e = this.m_177173_(p_177327_, p_177328_);
      e.m_147272_();
      return e;
   }

   public <E extends Mob> E m_177316_(EntityType<E> p_177317_, float p_177318_, float p_177319_, float p_177320_) {
      return this.m_177326_(p_177317_, new Vec3((double)p_177318_, (double)p_177319_, (double)p_177320_));
   }

   public GameTestSequence m_177185_(Mob p_177186_, BlockPos p_177187_, float p_177188_) {
      return this.m_177425_().m_177546_(2, () -> {
         Path path = p_177186_.m_21573_().m_7864_(this.m_177449_(p_177187_), 0);
         p_177186_.m_21573_().m_26536_(path, (double)p_177188_);
      });
   }

   public void m_177103_(int p_177104_, int p_177105_, int p_177106_) {
      this.m_177385_(new BlockPos(p_177104_, p_177105_, p_177106_));
   }

   public void m_177385_(BlockPos p_177386_) {
      this.m_177357_(p_177386_, (p_177212_) -> {
         return p_177212_.m_60620_(BlockTags.f_13093_);
      }, () -> {
         return "Expected button";
      });
      BlockPos blockpos = this.m_177449_(p_177386_);
      BlockState blockstate = this.m_177100_().m_8055_(blockpos);
      ButtonBlock buttonblock = (ButtonBlock)blockstate.m_60734_();
      buttonblock.m_51116_(blockstate, this.m_177100_(), blockpos);
   }

   public void m_177408_(BlockPos p_177409_) {
      BlockPos blockpos = this.m_177449_(p_177409_);
      BlockState blockstate = this.m_177100_().m_8055_(blockpos);
      blockstate.m_60664_(this.m_177100_(), this.m_177368_(), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.m_82512_(blockpos), Direction.NORTH, blockpos, true));
   }

   public LivingEntity m_177183_(LivingEntity p_177184_) {
      p_177184_.m_20301_(0);
      p_177184_.m_21153_(0.25F);
      return p_177184_;
   }

   public Player m_177368_() {
      return new Player(this.m_177100_(), BlockPos.f_121853_, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player")) {
         public boolean m_5833_() {
            return false;
         }

         public boolean m_7500_() {
            return true;
         }
      };
   }

   public void m_177302_(int p_177303_, int p_177304_, int p_177305_) {
      this.m_177421_(new BlockPos(p_177303_, p_177304_, p_177305_));
   }

   public void m_177421_(BlockPos p_177422_) {
      this.m_177208_(Blocks.f_50164_, p_177422_);
      BlockPos blockpos = this.m_177449_(p_177422_);
      BlockState blockstate = this.m_177100_().m_8055_(blockpos);
      LeverBlock leverblock = (LeverBlock)blockstate.m_60734_();
      leverblock.m_54676_(blockstate, this.m_177100_(), blockpos);
   }

   public void m_177234_(BlockPos p_177235_, long p_177236_) {
      this.m_177245_(p_177235_, Blocks.f_50330_);
      this.m_177306_(p_177236_, () -> {
         this.m_177245_(p_177235_, Blocks.f_50016_);
      });
   }

   public void m_177434_(BlockPos p_177435_) {
      this.m_177100_().m_46953_(this.m_177449_(p_177435_), false, (Entity)null);
   }

   public void m_177107_(int p_177108_, int p_177109_, int p_177110_, Block p_177111_) {
      this.m_177245_(new BlockPos(p_177108_, p_177109_, p_177110_), p_177111_);
   }

   public void m_177112_(int p_177113_, int p_177114_, int p_177115_, BlockState p_177116_) {
      this.m_177252_(new BlockPos(p_177113_, p_177114_, p_177115_), p_177116_);
   }

   public void m_177245_(BlockPos p_177246_, Block p_177247_) {
      this.m_177252_(p_177246_, p_177247_.m_49966_());
   }

   public void m_177252_(BlockPos p_177253_, BlockState p_177254_) {
      this.m_177100_().m_7731_(this.m_177449_(p_177253_), p_177254_, 3);
   }

   public void m_177396_() {
      this.m_177101_(13000);
   }

   public void m_177101_(int p_177102_) {
      this.m_177100_().m_8615_((long)p_177102_);
   }

   public void m_177203_(Block p_177204_, int p_177205_, int p_177206_, int p_177207_) {
      this.m_177208_(p_177204_, new BlockPos(p_177205_, p_177206_, p_177207_));
   }

   public void m_177208_(Block p_177209_, BlockPos p_177210_) {
      BlockState blockstate = this.m_177232_(p_177210_);
      this.m_177271_(p_177210_, (p_177216_) -> {
         return blockstate.m_60713_(p_177209_);
      }, "Expected " + p_177209_.m_49954_().getString() + ", got " + blockstate.m_60734_().m_49954_().getString());
   }

   public void m_177336_(Block p_177337_, int p_177338_, int p_177339_, int p_177340_) {
      this.m_177341_(p_177337_, new BlockPos(p_177338_, p_177339_, p_177340_));
   }

   public void m_177341_(Block p_177342_, BlockPos p_177343_) {
      this.m_177271_(p_177343_, (p_177251_) -> {
         return !this.m_177232_(p_177343_).m_60713_(p_177342_);
      }, "Did not expect " + p_177342_.m_49954_().getString());
   }

   public void m_177377_(Block p_177378_, int p_177379_, int p_177380_, int p_177381_) {
      this.m_177382_(p_177378_, new BlockPos(p_177379_, p_177380_, p_177381_));
   }

   public void m_177382_(Block p_177383_, BlockPos p_177384_) {
      this.m_177361_(() -> {
         this.m_177208_(p_177383_, p_177384_);
      });
   }

   public void m_177271_(BlockPos p_177272_, Predicate<Block> p_177273_, String p_177274_) {
      this.m_177275_(p_177272_, p_177273_, () -> {
         return p_177274_;
      });
   }

   public void m_177275_(BlockPos p_177276_, Predicate<Block> p_177277_, Supplier<String> p_177278_) {
      this.m_177357_(p_177276_, (p_177296_) -> {
         return p_177277_.test(p_177296_.m_60734_());
      }, p_177278_);
   }

   public <T extends Comparable<T>> void m_177255_(BlockPos p_177256_, Property<T> p_177257_, T p_177258_) {
      this.m_177357_(p_177256_, (p_177223_) -> {
         return p_177223_.m_61138_(p_177257_) && p_177223_.<T>m_61143_(p_177257_).equals(p_177258_);
      }, () -> {
         return "Expected property " + p_177257_.m_61708_() + " to be " + p_177258_;
      });
   }

   public <T extends Comparable<T>> void m_177259_(BlockPos p_177260_, Property<T> p_177261_, Predicate<T> p_177262_, String p_177263_) {
      this.m_177357_(p_177260_, (p_177300_) -> {
         return p_177262_.test(p_177300_.m_61143_(p_177261_));
      }, () -> {
         return p_177263_;
      });
   }

   public void m_177357_(BlockPos p_177358_, Predicate<BlockState> p_177359_, Supplier<String> p_177360_) {
      BlockState blockstate = this.m_177232_(p_177358_);
      if (!p_177359_.test(blockstate)) {
         throw new GameTestAssertPosException(p_177360_.get(), this.m_177449_(p_177358_), p_177358_, this.f_127595_.m_177488_());
      }
   }

   public void m_177156_(EntityType<?> p_177157_) {
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177157_, this.m_177448_(), Entity::m_6084_);
      if (list.isEmpty()) {
         throw new GameTestAssertException("Expected " + p_177157_.m_147048_() + " to exist");
      }
   }

   public void m_177369_(EntityType<?> p_177370_, int p_177371_, int p_177372_, int p_177373_) {
      this.m_177374_(p_177370_, new BlockPos(p_177371_, p_177372_, p_177373_));
   }

   public void m_177374_(EntityType<?> p_177375_, BlockPos p_177376_) {
      BlockPos blockpos = this.m_177449_(p_177376_);
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177375_, new AABB(blockpos), Entity::m_6084_);
      if (list.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + p_177375_.m_147048_(), blockpos, p_177376_, this.f_127595_.m_177488_());
      }
   }

   public void m_177179_(EntityType<?> p_177180_, BlockPos p_177181_, double p_177182_) {
      BlockPos blockpos = this.m_177449_(p_177181_);
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177180_, (new AABB(blockpos)).m_82400_(p_177182_), Entity::m_6084_);
      if (list.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + p_177180_.m_147048_(), blockpos, p_177181_, this.f_127595_.m_177488_());
      }
   }

   public void m_177132_(Entity p_177133_, int p_177134_, int p_177135_, int p_177136_) {
      this.m_177140_(p_177133_, new BlockPos(p_177134_, p_177135_, p_177136_));
   }

   public void m_177140_(Entity p_177141_, BlockPos p_177142_) {
      BlockPos blockpos = this.m_177449_(p_177142_);
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177141_.m_6095_(), new AABB(blockpos), Entity::m_6084_);
      list.stream().filter((p_177139_) -> {
         return p_177139_ == p_177141_;
      }).findFirst().orElseThrow(() -> {
         return new GameTestAssertPosException("Expected " + p_177141_.m_6095_().m_147048_(), blockpos, p_177142_, this.f_127595_.m_177488_());
      });
   }

   public void m_177198_(Item p_177199_, BlockPos p_177200_, double p_177201_, int p_177202_) {
      BlockPos blockpos = this.m_177449_(p_177200_);
      List<ItemEntity> list = this.m_177100_().m_142425_(EntityType.f_20461_, (new AABB(blockpos)).m_82400_(p_177201_), Entity::m_6084_);
      int i = 0;

      for(Entity entity : list) {
         ItemEntity itementity = (ItemEntity)entity;
         if (itementity.m_32055_().m_41720_().equals(p_177199_)) {
            i += itementity.m_32055_().m_41613_();
         }
      }

      if (i != p_177202_) {
         throw new GameTestAssertPosException("Expected " + p_177202_ + " " + p_177199_.m_41466_().getString() + " items to exist (found " + i + ")", blockpos, p_177200_, this.f_127595_.m_177488_());
      }
   }

   public void m_177194_(Item p_177195_, BlockPos p_177196_, double p_177197_) {
      BlockPos blockpos = this.m_177449_(p_177196_);

      for(Entity entity : this.m_177100_().m_142425_(EntityType.f_20461_, (new AABB(blockpos)).m_82400_(p_177197_), Entity::m_6084_)) {
         ItemEntity itementity = (ItemEntity)entity;
         if (itementity.m_32055_().m_41720_().equals(p_177195_)) {
            return;
         }
      }

      throw new GameTestAssertPosException("Expected " + p_177195_.m_41466_().getString() + " item", blockpos, p_177196_, this.f_127595_.m_177488_());
   }

   public void m_177309_(EntityType<?> p_177310_) {
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177310_, this.m_177448_(), Entity::m_6084_);
      if (!list.isEmpty()) {
         throw new GameTestAssertException("Did not expect " + p_177310_.m_147048_() + " to exist");
      }
   }

   public void m_177397_(EntityType<?> p_177398_, int p_177399_, int p_177400_, int p_177401_) {
      this.m_177402_(p_177398_, new BlockPos(p_177399_, p_177400_, p_177401_));
   }

   public void m_177402_(EntityType<?> p_177403_, BlockPos p_177404_) {
      BlockPos blockpos = this.m_177449_(p_177404_);
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177403_, new AABB(blockpos), Entity::m_6084_);
      if (!list.isEmpty()) {
         throw new GameTestAssertPosException("Did not expect " + p_177403_.m_147048_(), blockpos, p_177404_, this.f_127595_.m_177488_());
      }
   }

   public void m_177158_(EntityType<?> p_177159_, double p_177160_, double p_177161_, double p_177162_) {
      Vec3 vec3 = new Vec3(p_177160_, p_177161_, p_177162_);
      Vec3 vec31 = this.m_177227_(vec3);
      Predicate<? super Entity> predicate = (p_177346_) -> {
         return p_177346_.m_142469_().m_82335_(vec31, vec31);
      };
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177159_, this.m_177448_(), predicate);
      if (list.isEmpty()) {
         throw new GameTestAssertException("Expected " + p_177159_.m_147048_() + " to touch " + vec31 + " (relative " + vec3 + ")");
      }
   }

   public void m_177311_(EntityType<?> p_177312_, double p_177313_, double p_177314_, double p_177315_) {
      Vec3 vec3 = new Vec3(p_177313_, p_177314_, p_177315_);
      Vec3 vec31 = this.m_177227_(vec3);
      Predicate<? super Entity> predicate = (p_177231_) -> {
         return !p_177231_.m_142469_().m_82335_(vec31, vec31);
      };
      List<? extends Entity> list = this.m_177100_().m_142425_(p_177312_, this.m_177448_(), predicate);
      if (list.isEmpty()) {
         throw new GameTestAssertException("Did not expect " + p_177312_.m_147048_() + " to touch " + vec31 + " (relative " + vec3 + ")");
      }
   }

   public <E extends Entity, T> void m_177237_(BlockPos p_177238_, EntityType<E> p_177239_, Function<? super E, T> p_177240_, @Nullable T p_177241_) {
      BlockPos blockpos = this.m_177449_(p_177238_);
      List<E> list = this.m_177100_().m_142425_(p_177239_, new AABB(blockpos), Entity::m_6084_);
      if (list.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + p_177239_.m_147048_(), blockpos, p_177238_, this.f_127595_.m_177488_());
      } else {
         for(E e : list) {
            T t = p_177240_.apply(e);
            if (t == null) {
               if (p_177241_ != null) {
                  throw new GameTestAssertException("Expected entity data to be: " + p_177241_ + ", but was: " + t);
               }
            } else if (!t.equals(p_177241_)) {
               throw new GameTestAssertException("Expected entity data to be: " + p_177241_ + ", but was: " + t);
            }
         }

      }
   }

   public void m_177440_(BlockPos p_177441_) {
      BlockPos blockpos = this.m_177449_(p_177441_);
      BlockEntity blockentity = this.m_177100_().m_7702_(blockpos);
      if (blockentity instanceof BaseContainerBlockEntity && !((BaseContainerBlockEntity)blockentity).m_7983_()) {
         throw new GameTestAssertException("Container should be empty");
      }
   }

   public void m_177242_(BlockPos p_177243_, Item p_177244_) {
      BlockPos blockpos = this.m_177449_(p_177243_);
      BlockEntity blockentity = this.m_177100_().m_7702_(blockpos);
      if (blockentity instanceof BaseContainerBlockEntity && ((BaseContainerBlockEntity)blockentity).m_18947_(p_177244_) != 1) {
         throw new GameTestAssertException("Container should contain: " + p_177244_);
      }
   }

   public void m_177224_(BoundingBox p_177225_, BlockPos p_177226_) {
      BlockPos.m_121919_(p_177225_).forEach((p_177267_) -> {
         BlockPos blockpos = p_177226_.m_142082_(p_177267_.m_123341_() - p_177225_.m_162395_(), p_177267_.m_123342_() - p_177225_.m_162396_(), p_177267_.m_123343_() - p_177225_.m_162398_());
         this.m_177268_(p_177267_, blockpos);
      });
   }

   public void m_177268_(BlockPos p_177269_, BlockPos p_177270_) {
      BlockState blockstate = this.m_177232_(p_177269_);
      BlockState blockstate1 = this.m_177232_(p_177270_);
      if (blockstate != blockstate1) {
         this.m_177289_("Incorrect state. Expected " + blockstate1 + ", got " + blockstate, p_177269_);
      }

   }

   public void m_177123_(long p_177124_, BlockPos p_177125_, Item p_177126_) {
      this.m_177127_(p_177124_, () -> {
         this.m_177242_(p_177125_, p_177126_);
      });
   }

   public void m_177120_(long p_177121_, BlockPos p_177122_) {
      this.m_177127_(p_177121_, () -> {
         this.m_177440_(p_177122_);
      });
   }

   public <E extends Entity, T> void m_177349_(BlockPos p_177350_, EntityType<E> p_177351_, Function<E, T> p_177352_, T p_177353_) {
      this.m_177361_(() -> {
         this.m_177237_(p_177350_, p_177351_, p_177352_, p_177353_);
      });
   }

   public <E extends Entity> void m_177152_(E p_177153_, Predicate<E> p_177154_, String p_177155_) {
      if (!p_177154_.test(p_177153_)) {
         throw new GameTestAssertException("Entity " + p_177153_ + " failed " + p_177155_ + " test");
      }
   }

   public <E extends Entity, T> void m_177147_(E p_177148_, Function<E, T> p_177149_, String p_177150_, T p_177151_) {
      T t = p_177149_.apply(p_177148_);
      if (!t.equals(p_177151_)) {
         throw new GameTestAssertException("Entity " + p_177148_ + " value " + p_177150_ + "=" + t + " is not equal to expected " + p_177151_);
      }
   }

   public void m_177413_(EntityType<?> p_177414_, int p_177415_, int p_177416_, int p_177417_) {
      this.m_177418_(p_177414_, new BlockPos(p_177415_, p_177416_, p_177417_));
   }

   public void m_177418_(EntityType<?> p_177419_, BlockPos p_177420_) {
      this.m_177361_(() -> {
         this.m_177374_(p_177419_, p_177420_);
      });
   }

   public void m_177426_(EntityType<?> p_177427_, int p_177428_, int p_177429_, int p_177430_) {
      this.m_177431_(p_177427_, new BlockPos(p_177428_, p_177429_, p_177430_));
   }

   public void m_177431_(EntityType<?> p_177432_, BlockPos p_177433_) {
      this.m_177361_(() -> {
         this.m_177402_(p_177432_, p_177433_);
      });
   }

   public void m_177412_() {
      this.f_127595_.m_177486_();
   }

   private void m_177442_() {
      if (this.f_177099_) {
         throw new IllegalStateException("This test already has final clause");
      } else {
         this.f_177099_ = true;
      }
   }

   public void m_177279_(Runnable p_177280_) {
      this.m_177442_();
      this.f_127595_.m_177489_().m_177549_(0L, p_177280_).m_177543_();
   }

   public void m_177361_(Runnable p_177362_) {
      this.m_177442_();
      this.f_127595_.m_177489_().m_177552_(p_177362_).m_177543_();
   }

   public void m_177117_(int p_177118_, Runnable p_177119_) {
      this.m_177442_();
      this.f_127595_.m_177489_().m_177549_((long)p_177118_, p_177119_).m_177543_();
   }

   public void m_177127_(long p_177128_, Runnable p_177129_) {
      this.f_127595_.m_177472_(p_177128_, p_177129_);
   }

   public void m_177306_(long p_177307_, Runnable p_177308_) {
      this.m_177127_(this.f_127595_.m_177488_() + p_177307_, p_177308_);
   }

   public void m_177446_(BlockPos p_177447_) {
      BlockPos blockpos = this.m_177449_(p_177447_);
      ServerLevel serverlevel = this.m_177100_();
      serverlevel.m_8055_(blockpos).m_60735_(serverlevel, blockpos, serverlevel.f_46441_);
   }

   public void m_177289_(String p_177290_, BlockPos p_177291_) {
      throw new GameTestAssertPosException(p_177290_, this.m_177449_(p_177291_), p_177291_, this.m_177436_());
   }

   public void m_177286_(String p_177287_, Entity p_177288_) {
      throw new GameTestAssertPosException(p_177287_, p_177288_.m_142538_(), this.m_177452_(p_177288_.m_142538_()), this.m_177436_());
   }

   public void m_177284_(String p_177285_) {
      throw new GameTestAssertException(p_177285_);
   }

   public void m_177392_(Runnable p_177393_) {
      this.f_127595_.m_177489_().m_177552_(p_177393_).m_177554_(() -> {
         return new GameTestAssertException("Fail conditions met");
      });
   }

   public void m_177410_(Runnable p_177411_) {
      LongStream.range(this.f_127595_.m_177488_(), (long)this.f_127595_.m_177490_()).forEach((p_177365_) -> {
         this.f_127595_.m_177472_(p_177365_, p_177411_::run);
      });
   }

   public GameTestSequence m_177425_() {
      return this.f_127595_.m_177489_();
   }

   public BlockPos m_177449_(BlockPos p_177450_) {
      BlockPos blockpos = this.f_127595_.m_127636_();
      BlockPos blockpos1 = blockpos.m_141952_(p_177450_);
      return StructureTemplate.m_74593_(blockpos1, Mirror.NONE, this.f_127595_.m_127646_(), blockpos);
   }

   public BlockPos m_177452_(BlockPos p_177453_) {
      BlockPos blockpos = this.f_127595_.m_127636_();
      Rotation rotation = this.f_127595_.m_127646_().m_55952_(Rotation.CLOCKWISE_180);
      BlockPos blockpos1 = StructureTemplate.m_74593_(p_177453_, Mirror.NONE, rotation, blockpos);
      return blockpos1.m_141950_(blockpos);
   }

   public Vec3 m_177227_(Vec3 p_177228_) {
      Vec3 vec3 = Vec3.m_82528_(this.f_127595_.m_127636_());
      return StructureTemplate.m_74578_(vec3.m_82549_(p_177228_), Mirror.NONE, this.f_127595_.m_127646_(), this.f_127595_.m_127636_());
   }

   public long m_177436_() {
      return this.f_127595_.m_177488_();
   }

   private AABB m_177448_() {
      return this.f_127595_.m_177484_();
   }

   private AABB m_177451_() {
      AABB aabb = this.f_127595_.m_177484_();
      return aabb.m_82338_(BlockPos.f_121853_.m_141950_(this.m_177449_(BlockPos.f_121853_)));
   }

   public void m_177292_(Consumer<BlockPos> p_177293_) {
      AABB aabb = this.m_177451_();
      BlockPos.MutableBlockPos.m_121921_(aabb.m_82386_(0.0D, 1.0D, 0.0D)).forEach(p_177293_);
   }

   public void m_177423_(Runnable p_177424_) {
      LongStream.range(this.f_127595_.m_177488_(), (long)this.f_127595_.m_177490_()).forEach((p_177283_) -> {
         this.f_127595_.m_177472_(p_177283_, p_177424_::run);
      });
   }
}