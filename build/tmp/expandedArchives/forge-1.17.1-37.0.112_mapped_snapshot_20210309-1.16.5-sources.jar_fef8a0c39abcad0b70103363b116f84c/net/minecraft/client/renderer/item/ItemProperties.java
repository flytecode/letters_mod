package net.minecraft.client.renderer.item;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemProperties {
   private static final Map<ResourceLocation, ItemPropertyFunction> f_117820_ = Maps.newHashMap();
   private static final String f_174568_ = "CustomModelData";
   private static final ResourceLocation f_117821_ = new ResourceLocation("damaged");
   private static final ResourceLocation f_117822_ = new ResourceLocation("damage");
   private static final ClampedItemPropertyFunction f_117823_ = (p_174660_, p_174661_, p_174662_, p_174663_) -> {
      return p_174660_.m_41768_() ? 1.0F : 0.0F;
   };
   private static final ClampedItemPropertyFunction f_117824_ = (p_174655_, p_174656_, p_174657_, p_174658_) -> {
      return Mth.m_14036_((float)p_174655_.m_41773_() / (float)p_174655_.m_41776_(), 0.0F, 1.0F);
   };
   private static final Map<Item, Map<ResourceLocation, ItemPropertyFunction>> f_117825_ = Maps.newHashMap();

   public static ItemPropertyFunction registerGeneric(ResourceLocation p_174582_, ItemPropertyFunction p_174583_) {
      f_117820_.put(p_174582_, p_174583_);
      return p_174583_;
   }

   private static void m_174579_(ItemPropertyFunction p_174580_) {
      f_117820_.put(new ResourceLocation("custom_model_data"), p_174580_);
   }

   public static void register(Item p_174571_, ResourceLocation p_174572_, ItemPropertyFunction p_174573_) {
      f_117825_.computeIfAbsent(p_174571_, (p_117828_) -> {
         return Maps.newHashMap();
      }).put(p_174572_, p_174573_);
   }

   @Nullable
   public static ItemPropertyFunction m_117829_(Item p_117830_, ResourceLocation p_117831_) {
      if (p_117830_.m_41462_() > 0) {
         if (f_117822_.equals(p_117831_)) {
            return f_117824_;
         }

         if (f_117821_.equals(p_117831_)) {
            return f_117823_;
         }
      }

      ItemPropertyFunction itempropertyfunction = f_117820_.get(p_117831_);
      if (itempropertyfunction != null) {
         return itempropertyfunction;
      } else {
         Map<ResourceLocation, ItemPropertyFunction> map = f_117825_.get(p_117830_);
         return map == null ? null : map.get(p_117831_);
      }
   }

   static {
      registerGeneric(new ResourceLocation("lefthanded"), (p_174650_, p_174651_, p_174652_, p_174653_) -> {
         return p_174652_ != null && p_174652_.m_5737_() != HumanoidArm.RIGHT ? 1.0F : 0.0F;
      });
      registerGeneric(new ResourceLocation("cooldown"), (p_174645_, p_174646_, p_174647_, p_174648_) -> {
         return p_174647_ instanceof Player ? ((Player)p_174647_).m_36335_().m_41521_(p_174645_.m_41720_(), 0.0F) : 0.0F;
      });
      m_174579_((p_174640_, p_174641_, p_174642_, p_174643_) -> {
         return p_174640_.m_41782_() ? (float)p_174640_.m_41783_().m_128451_("CustomModelData") : 0.0F;
      });
      register(Items.f_42411_, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
         if (p_174637_ == null) {
            return 0.0F;
         } else {
            return p_174637_.m_21211_() != p_174635_ ? 0.0F : (float)(p_174635_.m_41779_() - p_174637_.m_21212_()) / 20.0F;
         }
      });
      register(Items.f_42411_, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
         return p_174632_ != null && p_174632_.m_6117_() && p_174632_.m_21211_() == p_174630_ ? 1.0F : 0.0F;
      });
      register(Items.f_151058_, new ResourceLocation("filled"), (p_174625_, p_174626_, p_174627_, p_174628_) -> {
         return BundleItem.m_150766_(p_174625_);
      });
      register(Items.f_42524_, new ResourceLocation("time"), new ClampedItemPropertyFunction() {
         private double f_117899_;
         private double f_117900_;
         private long f_117901_;

         public float m_142187_(ItemStack p_174665_, @Nullable ClientLevel p_174666_, @Nullable LivingEntity p_174667_, int p_174668_) {
            Entity entity = (Entity)(p_174667_ != null ? p_174667_ : p_174665_.m_41609_());
            if (entity == null) {
               return 0.0F;
            } else {
               if (p_174666_ == null && entity.f_19853_ instanceof ClientLevel) {
                  p_174666_ = (ClientLevel)entity.f_19853_;
               }

               if (p_174666_ == null) {
                  return 0.0F;
               } else {
                  double d0;
                  if (p_174666_.m_6042_().m_63956_()) {
                     d0 = (double)p_174666_.m_46942_(1.0F);
                  } else {
                     d0 = Math.random();
                  }

                  d0 = this.m_117903_(p_174666_, d0);
                  return (float)d0;
               }
            }
         }

         private double m_117903_(Level p_117904_, double p_117905_) {
            if (p_117904_.m_46467_() != this.f_117901_) {
               this.f_117901_ = p_117904_.m_46467_();
               double d0 = p_117905_ - this.f_117899_;
               d0 = Mth.m_14109_(d0 + 0.5D, 1.0D) - 0.5D;
               this.f_117900_ += d0 * 0.1D;
               this.f_117900_ *= 0.9D;
               this.f_117899_ = Mth.m_14109_(this.f_117899_ + this.f_117900_, 1.0D);
            }

            return this.f_117899_;
         }
      });
      register(Items.f_42522_, new ResourceLocation("angle"), new ClampedItemPropertyFunction() {
         private final ItemProperties.CompassWobble f_117910_ = new ItemProperties.CompassWobble();
         private final ItemProperties.CompassWobble f_117911_ = new ItemProperties.CompassWobble();

         public float m_142187_(ItemStack p_174672_, @Nullable ClientLevel p_174673_, @Nullable LivingEntity p_174674_, int p_174675_) {
            Entity entity = (Entity)(p_174674_ != null ? p_174674_ : p_174672_.m_41609_());
            if (entity == null) {
               return 0.0F;
            } else {
               if (p_174673_ == null && entity.f_19853_ instanceof ClientLevel) {
                  p_174673_ = (ClientLevel)entity.f_19853_;
               }

               BlockPos blockpos = CompassItem.m_40736_(p_174672_) ? this.m_117915_(p_174673_, p_174672_.m_41784_()) : this.m_117921_(p_174673_);
               long i = p_174673_.m_46467_();
               if (blockpos != null && !(entity.m_20182_().m_82531_((double)blockpos.m_123341_() + 0.5D, entity.m_20182_().m_7098_(), (double)blockpos.m_123343_() + 0.5D) < (double)1.0E-5F)) {
                  boolean flag = p_174674_ instanceof Player && ((Player)p_174674_).m_7578_();
                  double d1 = 0.0D;
                  if (flag) {
                     d1 = (double)p_174674_.m_146908_();
                  } else if (entity instanceof ItemFrame) {
                     d1 = this.m_117913_((ItemFrame)entity);
                  } else if (entity instanceof ItemEntity) {
                     d1 = (double)(180.0F - ((ItemEntity)entity).m_32008_(0.5F) / ((float)Math.PI * 2F) * 360.0F);
                  } else if (p_174674_ != null) {
                     d1 = (double)p_174674_.f_20883_;
                  }

                  d1 = Mth.m_14109_(d1 / 360.0D, 1.0D);
                  double d2 = this.m_117918_(Vec3.m_82512_(blockpos), entity) / (double)((float)Math.PI * 2F);
                  double d3;
                  if (flag) {
                     if (this.f_117910_.m_117933_(i)) {
                        this.f_117910_.m_117935_(i, 0.5D - (d1 - 0.25D));
                     }

                     d3 = d2 + this.f_117910_.f_117927_;
                  } else {
                     d3 = 0.5D - (d1 - 0.25D - d2);
                  }

                  return Mth.m_14091_((float)d3, 1.0F);
               } else {
                  if (this.f_117911_.m_117933_(i)) {
                     this.f_117911_.m_117935_(i, Math.random());
                  }

                  double d0 = this.f_117911_.f_117927_ + (double)((float)this.m_174669_(p_174675_) / 2.14748365E9F);
                  return Mth.m_14091_((float)d0, 1.0F);
               }
            }
         }

         private int m_174669_(int p_174670_) {
            return p_174670_ * 1327217883;
         }

         @Nullable
         private BlockPos m_117921_(ClientLevel p_117922_) {
            return p_117922_.m_6042_().m_63956_() ? p_117922_.m_104822_() : null;
         }

         @Nullable
         private BlockPos m_117915_(Level p_117916_, CompoundTag p_117917_) {
            boolean flag = p_117917_.m_128441_("LodestonePos");
            boolean flag1 = p_117917_.m_128441_("LodestoneDimension");
            if (flag && flag1) {
               Optional<ResourceKey<Level>> optional = CompassItem.m_40727_(p_117917_);
               if (optional.isPresent() && p_117916_.m_46472_() == optional.get()) {
                  return NbtUtils.m_129239_(p_117917_.m_128469_("LodestonePos"));
               }
            }

            return null;
         }

         private double m_117913_(ItemFrame p_117914_) {
            Direction direction = p_117914_.m_6350_();
            int i = direction.m_122434_().m_122478_() ? 90 * direction.m_122421_().m_122540_() : 0;
            return (double)Mth.m_14098_(180 + direction.m_122416_() * 90 + p_117914_.m_31823_() * 45 + i);
         }

         private double m_117918_(Vec3 p_117919_, Entity p_117920_) {
            return Math.atan2(p_117919_.m_7094_() - p_117920_.m_20189_(), p_117919_.m_7096_() - p_117920_.m_20185_());
         }
      });
      register(Items.f_42717_, new ResourceLocation("pull"), (p_174620_, p_174621_, p_174622_, p_174623_) -> {
         if (p_174622_ == null) {
            return 0.0F;
         } else {
            return CrossbowItem.m_40932_(p_174620_) ? 0.0F : (float)(p_174620_.m_41779_() - p_174622_.m_21212_()) / (float)CrossbowItem.m_40939_(p_174620_);
         }
      });
      register(Items.f_42717_, new ResourceLocation("pulling"), (p_174615_, p_174616_, p_174617_, p_174618_) -> {
         return p_174617_ != null && p_174617_.m_6117_() && p_174617_.m_21211_() == p_174615_ && !CrossbowItem.m_40932_(p_174615_) ? 1.0F : 0.0F;
      });
      register(Items.f_42717_, new ResourceLocation("charged"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
         return p_174612_ != null && CrossbowItem.m_40932_(p_174610_) ? 1.0F : 0.0F;
      });
      register(Items.f_42717_, new ResourceLocation("firework"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
         return p_174607_ != null && CrossbowItem.m_40932_(p_174605_) && CrossbowItem.m_40871_(p_174605_, Items.f_42688_) ? 1.0F : 0.0F;
      });
      register(Items.f_42741_, new ResourceLocation("broken"), (p_174600_, p_174601_, p_174602_, p_174603_) -> {
         return ElytraItem.m_41140_(p_174600_) ? 0.0F : 1.0F;
      });
      register(Items.f_42523_, new ResourceLocation("cast"), (p_174595_, p_174596_, p_174597_, p_174598_) -> {
         if (p_174597_ == null) {
            return 0.0F;
         } else {
            boolean flag = p_174597_.m_21205_() == p_174595_;
            boolean flag1 = p_174597_.m_21206_() == p_174595_;
            if (p_174597_.m_21205_().m_41720_() instanceof FishingRodItem) {
               flag1 = false;
            }

            return (flag || flag1) && p_174597_ instanceof Player && ((Player)p_174597_).f_36083_ != null ? 1.0F : 0.0F;
         }
      });
      register(Items.f_42740_, new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
         return p_174592_ != null && p_174592_.m_6117_() && p_174592_.m_21211_() == p_174590_ ? 1.0F : 0.0F;
      });
      register(Items.f_42713_, new ResourceLocation("throwing"), (p_174585_, p_174586_, p_174587_, p_174588_) -> {
         return p_174587_ != null && p_174587_.m_6117_() && p_174587_.m_21211_() == p_174585_ ? 1.0F : 0.0F;
      });
      register(Items.f_151033_, new ResourceLocation("level"), (p_174575_, p_174576_, p_174577_, p_174578_) -> {
         CompoundTag compoundtag = p_174575_.m_41737_("BlockStateTag");

         try {
            if (compoundtag != null) {
               Tag tag = compoundtag.m_128423_(LightBlock.f_153657_.m_61708_());
               if (tag != null) {
                  return (float)Integer.parseInt(tag.m_7916_()) / 16.0F;
               }
            }
         } catch (NumberFormatException numberformatexception) {
         }

         return 1.0F;
      });
   }

   @OnlyIn(Dist.CLIENT)
   static class CompassWobble {
      double f_117927_;
      private double f_117928_;
      private long f_117929_;

      boolean m_117933_(long p_117934_) {
         return this.f_117929_ != p_117934_;
      }

      void m_117935_(long p_117936_, double p_117937_) {
         this.f_117929_ = p_117936_;
         double d0 = p_117937_ - this.f_117927_;
         d0 = Mth.m_14109_(d0 + 0.5D, 1.0D) - 0.5D;
         this.f_117928_ += d0 * 0.1D;
         this.f_117928_ *= 0.8D;
         this.f_117927_ = Mth.m_14109_(this.f_117927_ + this.f_117928_, 1.0D);
      }
   }
}
