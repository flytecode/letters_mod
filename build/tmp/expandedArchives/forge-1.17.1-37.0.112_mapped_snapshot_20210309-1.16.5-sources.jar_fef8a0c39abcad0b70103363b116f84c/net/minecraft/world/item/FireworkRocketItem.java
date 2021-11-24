package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireworkRocketItem extends Item {
   public static final String f_150831_ = "Fireworks";
   public static final String f_150832_ = "Explosion";
   public static final String f_150833_ = "Explosions";
   public static final String f_150834_ = "Flight";
   public static final String f_150835_ = "Type";
   public static final String f_150836_ = "Trail";
   public static final String f_150837_ = "Flicker";
   public static final String f_150838_ = "Colors";
   public static final String f_150839_ = "FadeColors";
   public static final double f_150840_ = 0.15D;

   public FireworkRocketItem(Item.Properties p_41209_) {
      super(p_41209_);
   }

   public InteractionResult m_6225_(UseOnContext p_41216_) {
      Level level = p_41216_.m_43725_();
      if (!level.f_46443_) {
         ItemStack itemstack = p_41216_.m_43722_();
         Vec3 vec3 = p_41216_.m_43720_();
         Direction direction = p_41216_.m_43719_();
         FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, p_41216_.m_43723_(), vec3.f_82479_ + (double)direction.m_122429_() * 0.15D, vec3.f_82480_ + (double)direction.m_122430_() * 0.15D, vec3.f_82481_ + (double)direction.m_122431_() * 0.15D, itemstack);
         level.m_7967_(fireworkrocketentity);
         itemstack.m_41774_(1);
      }

      return InteractionResult.m_19078_(level.f_46443_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41218_, Player p_41219_, InteractionHand p_41220_) {
      if (p_41219_.m_21255_()) {
         ItemStack itemstack = p_41219_.m_21120_(p_41220_);
         if (!p_41218_.f_46443_) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(p_41218_, itemstack, p_41219_);
            p_41218_.m_7967_(fireworkrocketentity);
            if (!p_41219_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            p_41219_.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         return InteractionResultHolder.m_19092_(p_41219_.m_21120_(p_41220_), p_41218_.m_5776_());
      } else {
         return InteractionResultHolder.m_19098_(p_41219_.m_21120_(p_41220_));
      }
   }

   public void m_7373_(ItemStack p_41211_, @Nullable Level p_41212_, List<Component> p_41213_, TooltipFlag p_41214_) {
      CompoundTag compoundtag = p_41211_.m_41737_("Fireworks");
      if (compoundtag != null) {
         if (compoundtag.m_128425_("Flight", 99)) {
            p_41213_.add((new TranslatableComponent("item.minecraft.firework_rocket.flight")).m_130946_(" ").m_130946_(String.valueOf((int)compoundtag.m_128445_("Flight"))).m_130940_(ChatFormatting.GRAY));
         }

         ListTag listtag = compoundtag.m_128437_("Explosions", 10);
         if (!listtag.isEmpty()) {
            for(int i = 0; i < listtag.size(); ++i) {
               CompoundTag compoundtag1 = listtag.m_128728_(i);
               List<Component> list = Lists.newArrayList();
               FireworkStarItem.m_41256_(compoundtag1, list);
               if (!list.isEmpty()) {
                  for(int j = 1; j < list.size(); ++j) {
                     list.set(j, (new TextComponent("  ")).m_7220_(list.get(j)).m_130940_(ChatFormatting.GRAY));
                  }

                  p_41213_.addAll(list);
               }
            }
         }

      }
   }

   public ItemStack m_7968_() {
      ItemStack itemstack = new ItemStack(this);
      itemstack.m_41784_().m_128344_("Flight", (byte)1);
      return itemstack;
   }

   public static enum Shape {
      SMALL_BALL(0, "small_ball"),
      LARGE_BALL(1, "large_ball"),
      STAR(2, "star"),
      CREEPER(3, "creeper"),
      BURST(4, "burst");

      private static final FireworkRocketItem.Shape[] f_41226_ = Arrays.stream(values()).sorted(Comparator.comparingInt((p_41240_) -> {
         return p_41240_.f_41227_;
      })).toArray((p_41243_) -> {
         return new FireworkRocketItem.Shape[p_41243_];
      });
      private final int f_41227_;
      private final String f_41228_;

      private Shape(int p_41234_, String p_41235_) {
         this.f_41227_ = p_41234_;
         this.f_41228_ = p_41235_;
      }

      public int m_41236_() {
         return this.f_41227_;
      }

      public String m_41241_() {
         return this.f_41228_;
      }

      public static FireworkRocketItem.Shape m_41237_(int p_41238_) {
         return p_41238_ >= 0 && p_41238_ < f_41226_.length ? f_41226_[p_41238_] : SMALL_BALL;
      }
   }
}