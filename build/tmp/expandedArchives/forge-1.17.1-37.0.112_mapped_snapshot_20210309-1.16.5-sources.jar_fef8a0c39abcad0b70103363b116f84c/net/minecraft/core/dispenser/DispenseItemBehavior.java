package net.minecraft.core.dispenser;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface DispenseItemBehavior {
   Logger f_181892_ = LogManager.getLogger();
   DispenseItemBehavior f_123393_ = (p_123400_, p_123401_) -> {
      return p_123401_;
   };

   ItemStack m_6115_(BlockSource p_123403_, ItemStack p_123404_);

   static void m_123402_() {
      DispenserBlock.m_52672_(Items.f_42412_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
            Arrow arrow = new Arrow(p_123407_, p_123408_.m_7096_(), p_123408_.m_7098_(), p_123408_.m_7094_());
            arrow.f_36705_ = AbstractArrow.Pickup.ALLOWED;
            return arrow;
         }
      });
      DispenserBlock.m_52672_(Items.f_42738_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123420_, Position p_123421_, ItemStack p_123422_) {
            Arrow arrow = new Arrow(p_123420_, p_123421_.m_7096_(), p_123421_.m_7098_(), p_123421_.m_7094_());
            arrow.m_36878_(p_123422_);
            arrow.f_36705_ = AbstractArrow.Pickup.ALLOWED;
            return arrow;
         }
      });
      DispenserBlock.m_52672_(Items.f_42737_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123456_, Position p_123457_, ItemStack p_123458_) {
            AbstractArrow abstractarrow = new SpectralArrow(p_123456_, p_123457_.m_7096_(), p_123457_.m_7098_(), p_123457_.m_7094_());
            abstractarrow.f_36705_ = AbstractArrow.Pickup.ALLOWED;
            return abstractarrow;
         }
      });
      DispenserBlock.m_52672_(Items.f_42521_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
            return Util.m_137469_(new ThrownEgg(p_123468_, p_123469_.m_7096_(), p_123469_.m_7098_(), p_123469_.m_7094_()), (p_123466_) -> {
               p_123466_.m_37446_(p_123470_);
            });
         }
      });
      DispenserBlock.m_52672_(Items.f_42452_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123476_, Position p_123477_, ItemStack p_123478_) {
            return Util.m_137469_(new Snowball(p_123476_, p_123477_.m_7096_(), p_123477_.m_7098_(), p_123477_.m_7094_()), (p_123474_) -> {
               p_123474_.m_37446_(p_123478_);
            });
         }
      });
      DispenserBlock.m_52672_(Items.f_42612_, new AbstractProjectileDispenseBehavior() {
         protected Projectile m_6895_(Level p_123485_, Position p_123486_, ItemStack p_123487_) {
            return Util.m_137469_(new ThrownExperienceBottle(p_123485_, p_123486_.m_7096_(), p_123486_.m_7098_(), p_123486_.m_7094_()), (p_123483_) -> {
               p_123483_.m_37446_(p_123487_);
            });
         }

         protected float m_7101_() {
            return super.m_7101_() * 0.5F;
         }

         protected float m_7104_() {
            return super.m_7104_() * 1.25F;
         }
      });
      DispenserBlock.m_52672_(Items.f_42736_, new DispenseItemBehavior() {
         public ItemStack m_6115_(BlockSource p_123491_, ItemStack p_123492_) {
            return (new AbstractProjectileDispenseBehavior() {
               protected Projectile m_6895_(Level p_123501_, Position p_123502_, ItemStack p_123503_) {
                  return Util.m_137469_(new ThrownPotion(p_123501_, p_123502_.m_7096_(), p_123502_.m_7098_(), p_123502_.m_7094_()), (p_123499_) -> {
                     p_123499_.m_37446_(p_123503_);
                  });
               }

               protected float m_7101_() {
                  return super.m_7101_() * 0.5F;
               }

               protected float m_7104_() {
                  return super.m_7104_() * 1.25F;
               }
            }).m_6115_(p_123491_, p_123492_);
         }
      });
      DispenserBlock.m_52672_(Items.f_42739_, new DispenseItemBehavior() {
         public ItemStack m_6115_(BlockSource p_123507_, ItemStack p_123508_) {
            return (new AbstractProjectileDispenseBehavior() {
               protected Projectile m_6895_(Level p_123517_, Position p_123518_, ItemStack p_123519_) {
                  return Util.m_137469_(new ThrownPotion(p_123517_, p_123518_.m_7096_(), p_123518_.m_7098_(), p_123518_.m_7094_()), (p_123515_) -> {
                     p_123515_.m_37446_(p_123519_);
                  });
               }

               protected float m_7101_() {
                  return super.m_7101_() * 0.5F;
               }

               protected float m_7104_() {
                  return super.m_7104_() * 1.25F;
               }
            }).m_6115_(p_123507_, p_123508_);
         }
      });
      DefaultDispenseItemBehavior defaultdispenseitembehavior = new DefaultDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123523_, ItemStack p_123524_) {
            Direction direction = p_123523_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            EntityType<?> entitytype = ((SpawnEggItem)p_123524_.m_41720_()).m_43228_(p_123524_.m_41783_());

            try {
               entitytype.m_20592_(p_123523_.m_7727_(), p_123524_, (Player)null, p_123523_.m_7961_().m_142300_(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
            } catch (Exception exception) {
               f_181892_.error("Error while dispensing spawn egg from dispenser at {}", p_123523_.m_7961_(), exception);
               return ItemStack.f_41583_;
            }

            p_123524_.m_41774_(1);
            p_123523_.m_7727_().m_151555_(GameEvent.f_157810_, p_123523_.m_7961_());
            return p_123524_;
         }
      };

      for(SpawnEggItem spawneggitem : SpawnEggItem.m_43233_()) {
         DispenserBlock.m_52672_(spawneggitem, defaultdispenseitembehavior);
      }

      DispenserBlock.m_52672_(Items.f_42650_, new DefaultDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123461_, ItemStack p_123462_) {
            Direction direction = p_123461_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos blockpos = p_123461_.m_7961_().m_142300_(direction);
            Level level = p_123461_.m_7727_();
            ArmorStand armorstand = new ArmorStand(level, (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5D);
            EntityType.m_20620_(level, (Player)null, armorstand, p_123462_.m_41783_());
            armorstand.m_146922_(direction.m_122435_());
            level.m_7967_(armorstand);
            p_123462_.m_41774_(1);
            return p_123462_;
         }
      });
      DispenserBlock.m_52672_(Items.f_42450_, new OptionalDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123529_, ItemStack p_123530_) {
            BlockPos blockpos = p_123529_.m_7961_().m_142300_(p_123529_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            List<LivingEntity> list = p_123529_.m_7727_().m_6443_(LivingEntity.class, new AABB(blockpos), (p_123527_) -> {
               if (!(p_123527_ instanceof Saddleable)) {
                  return false;
               } else {
                  Saddleable saddleable = (Saddleable)p_123527_;
                  return !saddleable.m_6254_() && saddleable.m_6741_();
               }
            });
            if (!list.isEmpty()) {
               ((Saddleable)list.get(0)).m_5853_(SoundSource.BLOCKS);
               p_123530_.m_41774_(1);
               this.m_123573_(true);
               return p_123530_;
            } else {
               return super.m_7498_(p_123529_, p_123530_);
            }
         }
      });
      DefaultDispenseItemBehavior defaultdispenseitembehavior1 = new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123535_, ItemStack p_123536_) {
            BlockPos blockpos = p_123535_.m_7961_().m_142300_(p_123535_.m_6414_().m_61143_(DispenserBlock.f_52659_));

            for(AbstractHorse abstracthorse : p_123535_.m_7727_().m_6443_(AbstractHorse.class, new AABB(blockpos), (p_123533_) -> {
               return p_123533_.m_6084_() && p_123533_.m_7482_();
            })) {
               if (abstracthorse.m_6010_(p_123536_) && !abstracthorse.m_7481_() && abstracthorse.m_30614_()) {
                  abstracthorse.m_141942_(401).m_142104_(p_123536_.m_41620_(1));
                  this.m_123573_(true);
                  return p_123536_;
               }
            }

            return super.m_7498_(p_123535_, p_123536_);
         }
      };
      DispenserBlock.m_52672_(Items.f_42654_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42651_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42652_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42653_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42130_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42131_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42139_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42141_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42142_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42198_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42137_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42143_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42133_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42138_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42135_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42132_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42136_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42140_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42197_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42134_, defaultdispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42009_, new OptionalDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123541_, ItemStack p_123542_) {
            BlockPos blockpos = p_123541_.m_7961_().m_142300_(p_123541_.m_6414_().m_61143_(DispenserBlock.f_52659_));

            for(AbstractChestedHorse abstractchestedhorse : p_123541_.m_7727_().m_6443_(AbstractChestedHorse.class, new AABB(blockpos), (p_123539_) -> {
               return p_123539_.m_6084_() && !p_123539_.m_30502_();
            })) {
               if (abstractchestedhorse.m_30614_() && abstractchestedhorse.m_141942_(499).m_142104_(p_123542_)) {
                  p_123542_.m_41774_(1);
                  this.m_123573_(true);
                  return p_123542_;
               }
            }

            return super.m_7498_(p_123541_, p_123542_);
         }
      });
      DispenserBlock.m_52672_(Items.f_42688_, new DefaultDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123547_, ItemStack p_123548_) {
            Direction direction = p_123547_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(p_123547_.m_7727_(), p_123548_, p_123547_.m_7096_(), p_123547_.m_7098_(), p_123547_.m_7096_(), true);
            DispenseItemBehavior.m_123395_(p_123547_, fireworkrocketentity, direction);
            fireworkrocketentity.m_6686_((double)direction.m_122429_(), (double)direction.m_122430_(), (double)direction.m_122431_(), 0.5F, 1.0F);
            p_123547_.m_7727_().m_7967_(fireworkrocketentity);
            p_123548_.m_41774_(1);
            return p_123548_;
         }

         protected void m_6823_(BlockSource p_123545_) {
            p_123545_.m_7727_().m_46796_(1004, p_123545_.m_7961_(), 0);
         }
      });
      DispenserBlock.m_52672_(Items.f_42613_, new DefaultDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123556_, ItemStack p_123557_) {
            Direction direction = p_123556_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            Position position = DispenserBlock.m_52720_(p_123556_);
            double d0 = position.m_7096_() + (double)((float)direction.m_122429_() * 0.3F);
            double d1 = position.m_7098_() + (double)((float)direction.m_122430_() * 0.3F);
            double d2 = position.m_7094_() + (double)((float)direction.m_122431_() * 0.3F);
            Level level = p_123556_.m_7727_();
            Random random = level.f_46441_;
            double d3 = random.nextGaussian() * 0.05D + (double)direction.m_122429_();
            double d4 = random.nextGaussian() * 0.05D + (double)direction.m_122430_();
            double d5 = random.nextGaussian() * 0.05D + (double)direction.m_122431_();
            SmallFireball smallfireball = new SmallFireball(level, d0, d1, d2, d3, d4, d5);
            level.m_7967_(Util.m_137469_(smallfireball, (p_123552_) -> {
               p_123552_.m_37010_(p_123557_);
            }));
            p_123557_.m_41774_(1);
            return p_123557_;
         }

         protected void m_6823_(BlockSource p_123554_) {
            p_123554_.m_7727_().m_46796_(1018, p_123554_.m_7961_(), 0);
         }
      });
      DispenserBlock.m_52672_(Items.f_42453_, new BoatDispenseItemBehavior(Boat.Type.OAK));
      DispenserBlock.m_52672_(Items.f_42742_, new BoatDispenseItemBehavior(Boat.Type.SPRUCE));
      DispenserBlock.m_52672_(Items.f_42743_, new BoatDispenseItemBehavior(Boat.Type.BIRCH));
      DispenserBlock.m_52672_(Items.f_42744_, new BoatDispenseItemBehavior(Boat.Type.JUNGLE));
      DispenserBlock.m_52672_(Items.f_42746_, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK));
      DispenserBlock.m_52672_(Items.f_42745_, new BoatDispenseItemBehavior(Boat.Type.ACACIA));
      DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior f_123558_ = new DefaultDispenseItemBehavior();

         public ItemStack m_7498_(BlockSource p_123561_, ItemStack p_123562_) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)p_123562_.m_41720_();
            BlockPos blockpos = p_123561_.m_7961_().m_142300_(p_123561_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            Level level = p_123561_.m_7727_();
            if (dispensiblecontaineritem.m_142073_((Player)null, level, blockpos, (BlockHitResult)null)) {
               dispensiblecontaineritem.m_142131_((Player)null, level, p_123562_, blockpos);
               return new ItemStack(Items.f_42446_);
            } else {
               return this.f_123558_.m_6115_(p_123561_, p_123562_);
            }
         }
      };
      DispenserBlock.m_52672_(Items.f_42448_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42447_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_151055_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42457_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42458_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42456_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42459_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_151057_, dispenseitembehavior1);
      DispenserBlock.m_52672_(Items.f_42446_, new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior f_123563_ = new DefaultDispenseItemBehavior();

         public ItemStack m_7498_(BlockSource p_123566_, ItemStack p_123567_) {
            LevelAccessor levelaccessor = p_123566_.m_7727_();
            BlockPos blockpos = p_123566_.m_7961_().m_142300_(p_123566_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            BlockState blockstate = levelaccessor.m_8055_(blockpos);
            Block block = blockstate.m_60734_();
            if (block instanceof BucketPickup) {
               ItemStack itemstack = ((BucketPickup)block).m_142598_(levelaccessor, blockpos, blockstate);
               if (itemstack.m_41619_()) {
                  return super.m_7498_(p_123566_, p_123567_);
               } else {
                  levelaccessor.m_142346_((Entity)null, GameEvent.f_157816_, blockpos);
                  Item item = itemstack.m_41720_();
                  p_123567_.m_41774_(1);
                  if (p_123567_.m_41619_()) {
                     return new ItemStack(item);
                  } else {
                     if (p_123566_.<DispenserBlockEntity>m_8118_().m_59237_(new ItemStack(item)) < 0) {
                        this.f_123563_.m_6115_(p_123566_, new ItemStack(item));
                     }

                     return p_123567_;
                  }
               }
            } else {
               return super.m_7498_(p_123566_, p_123567_);
            }
         }
      });
      DispenserBlock.m_52672_(Items.f_42409_, new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123412_, ItemStack p_123413_) {
            Level level = p_123412_.m_7727_();
            this.m_123573_(true);
            Direction direction = p_123412_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos blockpos = p_123412_.m_7961_().m_142300_(direction);
            BlockState blockstate = level.m_8055_(blockpos);
            if (BaseFireBlock.m_49255_(level, blockpos, direction)) {
               level.m_46597_(blockpos, BaseFireBlock.m_49245_(level, blockpos));
               level.m_142346_((Entity)null, GameEvent.f_157797_, blockpos);
            } else if (!CampfireBlock.m_51321_(blockstate) && !CandleBlock.m_152845_(blockstate) && !CandleCakeBlock.m_152910_(blockstate)) {
               if (blockstate.isFlammable(level, blockpos, p_123412_.m_6414_().m_61143_(DispenserBlock.f_52659_).m_122424_())) {
                  blockstate.catchFire(level, blockpos, p_123412_.m_6414_().m_61143_(DispenserBlock.f_52659_).m_122424_(), null);
                  if (blockstate.m_60734_() instanceof TntBlock)
                     level.m_7471_(blockpos, false);
               } else {
                  this.m_123573_(false);
               }
            } else {
               level.m_46597_(blockpos, blockstate.m_61124_(BlockStateProperties.f_61443_, Boolean.valueOf(true)));
               level.m_142346_((Entity)null, GameEvent.f_157792_, blockpos);
            }

            if (this.m_123570_() && p_123413_.m_41629_(1, level.f_46441_, (ServerPlayer)null)) {
               p_123413_.m_41764_(0);
            }

            return p_123413_;
         }
      });
      DispenserBlock.m_52672_(Items.f_42499_, new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123416_, ItemStack p_123417_) {
            this.m_123573_(true);
            Level level = p_123416_.m_7727_();
            BlockPos blockpos = p_123416_.m_7961_().m_142300_(p_123416_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            if (!BoneMealItem.m_40627_(p_123417_, level, blockpos) && !BoneMealItem.m_40631_(p_123417_, level, blockpos, (Direction)null)) {
               this.m_123573_(false);
            } else if (!level.f_46443_) {
               level.m_46796_(1505, blockpos, 0);
            }

            return p_123417_;
         }
      });
      DispenserBlock.m_52672_(Blocks.f_50077_, new DefaultDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123425_, ItemStack p_123426_) {
            Level level = p_123425_.m_7727_();
            BlockPos blockpos = p_123425_.m_7961_().m_142300_(p_123425_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            PrimedTnt primedtnt = new PrimedTnt(level, (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_(), (double)blockpos.m_123343_() + 0.5D, (LivingEntity)null);
            level.m_7967_(primedtnt);
            level.m_6263_((Player)null, primedtnt.m_20185_(), primedtnt.m_20186_(), primedtnt.m_20189_(), SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.m_142346_((Entity)null, GameEvent.f_157810_, blockpos);
            p_123426_.m_41774_(1);
            return p_123426_;
         }
      });
      DispenseItemBehavior dispenseitembehavior = new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123429_, ItemStack p_123430_) {
            this.m_123573_(ArmorItem.m_40398_(p_123429_, p_123430_));
            return p_123430_;
         }
      };
      DispenserBlock.m_52672_(Items.f_42682_, dispenseitembehavior);
      DispenserBlock.m_52672_(Items.f_42681_, dispenseitembehavior);
      DispenserBlock.m_52672_(Items.f_42683_, dispenseitembehavior);
      DispenserBlock.m_52672_(Items.f_42678_, dispenseitembehavior);
      DispenserBlock.m_52672_(Items.f_42680_, dispenseitembehavior);
      DispenserBlock.m_52672_(Items.f_42679_, new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123433_, ItemStack p_123434_) {
            Level level = p_123433_.m_7727_();
            Direction direction = p_123433_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos blockpos = p_123433_.m_7961_().m_142300_(direction);
            if (level.m_46859_(blockpos) && WitherSkullBlock.m_58267_(level, blockpos, p_123434_)) {
               level.m_7731_(blockpos, Blocks.f_50312_.m_49966_().m_61124_(SkullBlock.f_56314_, Integer.valueOf(direction.m_122434_() == Direction.Axis.Y ? 0 : direction.m_122424_().m_122416_() * 4)), 3);
               level.m_142346_((Entity)null, GameEvent.f_157797_, blockpos);
               BlockEntity blockentity = level.m_7702_(blockpos);
               if (blockentity instanceof SkullBlockEntity) {
                  WitherSkullBlock.m_58255_(level, blockpos, (SkullBlockEntity)blockentity);
               }

               p_123434_.m_41774_(1);
               this.m_123573_(true);
            } else {
               this.m_123573_(ArmorItem.m_40398_(p_123433_, p_123434_));
            }

            return p_123434_;
         }
      });
      DispenserBlock.m_52672_(Blocks.f_50143_, new OptionalDispenseItemBehavior() {
         protected ItemStack m_7498_(BlockSource p_123437_, ItemStack p_123438_) {
            Level level = p_123437_.m_7727_();
            BlockPos blockpos = p_123437_.m_7961_().m_142300_(p_123437_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            CarvedPumpkinBlock carvedpumpkinblock = (CarvedPumpkinBlock)Blocks.f_50143_;
            if (level.m_46859_(blockpos) && carvedpumpkinblock.m_51381_(level, blockpos)) {
               if (!level.f_46443_) {
                  level.m_7731_(blockpos, carvedpumpkinblock.m_49966_(), 3);
                  level.m_142346_((Entity)null, GameEvent.f_157797_, blockpos);
               }

               p_123438_.m_41774_(1);
               this.m_123573_(true);
            } else {
               this.m_123573_(ArmorItem.m_40398_(p_123437_, p_123438_));
            }

            return p_123438_;
         }
      });
      DispenserBlock.m_52672_(Blocks.f_50456_.m_5456_(), new ShulkerBoxDispenseBehavior());

      for(DyeColor dyecolor : DyeColor.values()) {
         DispenserBlock.m_52672_(ShulkerBoxBlock.m_56190_(dyecolor).m_5456_(), new ShulkerBoxDispenseBehavior());
      }

      DispenserBlock.m_52672_(Items.f_42590_.m_5456_(), new OptionalDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior f_123439_ = new DefaultDispenseItemBehavior();

         private ItemStack m_123446_(BlockSource p_123447_, ItemStack p_123448_, ItemStack p_123449_) {
            p_123448_.m_41774_(1);
            if (p_123448_.m_41619_()) {
               p_123447_.m_7727_().m_142346_((Entity)null, GameEvent.f_157816_, p_123447_.m_7961_());
               return p_123449_.m_41777_();
            } else {
               if (p_123447_.<DispenserBlockEntity>m_8118_().m_59237_(p_123449_.m_41777_()) < 0) {
                  this.f_123439_.m_6115_(p_123447_, p_123449_.m_41777_());
               }

               return p_123448_;
            }
         }

         public ItemStack m_7498_(BlockSource p_123444_, ItemStack p_123445_) {
            this.m_123573_(false);
            ServerLevel serverlevel = p_123444_.m_7727_();
            BlockPos blockpos = p_123444_.m_7961_().m_142300_(p_123444_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            BlockState blockstate = serverlevel.m_8055_(blockpos);
            if (blockstate.m_60622_(BlockTags.f_13072_, (p_123442_) -> {
               return p_123442_.m_61138_(BeehiveBlock.f_49564_);
            }) && blockstate.m_61143_(BeehiveBlock.f_49564_) >= 5) {
               ((BeehiveBlock)blockstate.m_60734_()).m_49594_(serverlevel, blockstate, blockpos, (Player)null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
               this.m_123573_(true);
               return this.m_123446_(p_123444_, p_123445_, new ItemStack(Items.f_42787_));
            } else if (serverlevel.m_6425_(blockpos).m_76153_(FluidTags.f_13131_)) {
               this.m_123573_(true);
               return this.m_123446_(p_123444_, p_123445_, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43599_));
            } else {
               return super.m_7498_(p_123444_, p_123445_);
            }
         }
      });
      DispenserBlock.m_52672_(Items.f_42054_, new OptionalDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_123452_, ItemStack p_123453_) {
            Direction direction = p_123452_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos blockpos = p_123452_.m_7961_().m_142300_(direction);
            Level level = p_123452_.m_7727_();
            BlockState blockstate = level.m_8055_(blockpos);
            this.m_123573_(true);
            if (blockstate.m_60713_(Blocks.f_50724_)) {
               if (blockstate.m_61143_(RespawnAnchorBlock.f_55833_) != 4) {
                  RespawnAnchorBlock.m_55855_(level, blockpos, blockstate);
                  p_123453_.m_41774_(1);
               } else {
                  this.m_123573_(false);
               }

               return p_123453_;
            } else {
               return super.m_7498_(p_123452_, p_123453_);
            }
         }
      });
      DispenserBlock.m_52672_(Items.f_42574_.m_5456_(), new ShearsDispenseItemBehavior());
      DispenserBlock.m_52672_(Items.f_42784_, new OptionalDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource p_175747_, ItemStack p_175748_) {
            BlockPos blockpos = p_175747_.m_7961_().m_142300_(p_175747_.m_6414_().m_61143_(DispenserBlock.f_52659_));
            Level level = p_175747_.m_7727_();
            BlockState blockstate = level.m_8055_(blockpos);
            Optional<BlockState> optional = HoneycombItem.m_150878_(blockstate);
            if (optional.isPresent()) {
               level.m_46597_(blockpos, optional.get());
               level.m_46796_(3003, blockpos, 0);
               p_175748_.m_41774_(1);
               this.m_123573_(true);
               return p_175748_;
            } else {
               return super.m_7498_(p_175747_, p_175748_);
            }
         }
      });
   }

   static void m_123395_(BlockSource p_123396_, Entity p_123397_, Direction p_123398_) {
      p_123397_.m_6034_(p_123396_.m_7096_() + (double)p_123398_.m_122429_() * (0.5000099999997474D - (double)p_123397_.m_20205_() / 2.0D), p_123396_.m_7098_() + (double)p_123398_.m_122430_() * (0.5000099999997474D - (double)p_123397_.m_20206_() / 2.0D) - (double)p_123397_.m_20206_() / 2.0D, p_123396_.m_7094_() + (double)p_123398_.m_122431_() * (0.5000099999997474D - (double)p_123397_.m_20205_() / 2.0D));
   }
}
