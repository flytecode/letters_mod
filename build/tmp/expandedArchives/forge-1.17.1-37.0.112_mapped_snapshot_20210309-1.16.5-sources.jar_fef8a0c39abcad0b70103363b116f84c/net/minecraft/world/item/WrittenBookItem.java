package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WrittenBookItem extends Item {
   public static final int f_151235_ = 16;
   public static final int f_151236_ = 32;
   public static final int f_151237_ = 1024;
   public static final int f_151238_ = 32767;
   public static final int f_151239_ = 100;
   public static final int f_151240_ = 2;
   public static final String f_151241_ = "title";
   public static final String f_151242_ = "filtered_title";
   public static final String f_151243_ = "author";
   public static final String f_151244_ = "pages";
   public static final String f_151245_ = "filtered_pages";
   public static final String f_151246_ = "generation";
   public static final String f_151247_ = "resolved";

   public WrittenBookItem(Item.Properties p_43455_) {
      super(p_43455_);
   }

   public static boolean m_43471_(@Nullable CompoundTag p_43472_) {
      if (!WritableBookItem.m_43452_(p_43472_)) {
         return false;
      } else if (!p_43472_.m_128425_("title", 8)) {
         return false;
      } else {
         String s = p_43472_.m_128461_("title");
         return s.length() > 32 ? false : p_43472_.m_128425_("author", 8);
      }
   }

   public static int m_43473_(ItemStack p_43474_) {
      return p_43474_.m_41783_().m_128451_("generation");
   }

   public static int m_43477_(ItemStack p_43478_) {
      CompoundTag compoundtag = p_43478_.m_41783_();
      return compoundtag != null ? compoundtag.m_128437_("pages", 8).size() : 0;
   }

   public Component m_7626_(ItemStack p_43480_) {
      CompoundTag compoundtag = p_43480_.m_41783_();
      if (compoundtag != null) {
         String s = compoundtag.m_128461_("title");
         if (!StringUtil.m_14408_(s)) {
            return new TextComponent(s);
         }
      }

      return super.m_7626_(p_43480_);
   }

   public void m_7373_(ItemStack p_43457_, @Nullable Level p_43458_, List<Component> p_43459_, TooltipFlag p_43460_) {
      if (p_43457_.m_41782_()) {
         CompoundTag compoundtag = p_43457_.m_41783_();
         String s = compoundtag.m_128461_("author");
         if (!StringUtil.m_14408_(s)) {
            p_43459_.add((new TranslatableComponent("book.byAuthor", s)).m_130940_(ChatFormatting.GRAY));
         }

         p_43459_.add((new TranslatableComponent("book.generation." + compoundtag.m_128451_("generation"))).m_130940_(ChatFormatting.GRAY));
      }

   }

   public InteractionResult m_6225_(UseOnContext p_43466_) {
      Level level = p_43466_.m_43725_();
      BlockPos blockpos = p_43466_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (blockstate.m_60713_(Blocks.f_50624_)) {
         return LecternBlock.m_153566_(p_43466_.m_43723_(), level, blockpos, blockstate, p_43466_.m_43722_()) ? InteractionResult.m_19078_(level.f_46443_) : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43468_, Player p_43469_, InteractionHand p_43470_) {
      ItemStack itemstack = p_43469_.m_21120_(p_43470_);
      p_43469_.m_6986_(itemstack, p_43470_);
      p_43469_.m_36246_(Stats.f_12982_.m_12902_(this));
      return InteractionResultHolder.m_19092_(itemstack, p_43468_.m_5776_());
   }

   public static boolean m_43461_(ItemStack p_43462_, @Nullable CommandSourceStack p_43463_, @Nullable Player p_43464_) {
      CompoundTag compoundtag = p_43462_.m_41783_();
      if (compoundtag != null && !compoundtag.m_128471_("resolved")) {
         compoundtag.m_128379_("resolved", true);
         if (!m_43471_(compoundtag)) {
            return false;
         } else {
            ListTag listtag = compoundtag.m_128437_("pages", 8);

            for(int i = 0; i < listtag.size(); ++i) {
               listtag.set(i, (Tag)StringTag.m_129297_(m_151248_(p_43463_, p_43464_, listtag.m_128778_(i))));
            }

            if (compoundtag.m_128425_("filtered_pages", 10)) {
               CompoundTag compoundtag1 = compoundtag.m_128469_("filtered_pages");

               for(String s : compoundtag1.m_128431_()) {
                  compoundtag1.m_128359_(s, m_151248_(p_43463_, p_43464_, compoundtag1.m_128461_(s)));
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static String m_151248_(@Nullable CommandSourceStack p_151249_, @Nullable Player p_151250_, String p_151251_) {
      Component component;
      try {
         component = Component.Serializer.m_130714_(p_151251_);
         component = ComponentUtils.m_130731_(p_151249_, component, p_151250_, 0);
      } catch (Exception exception) {
         component = new TextComponent(p_151251_);
      }

      return Component.Serializer.m_130703_(component);
   }

   public boolean m_5812_(ItemStack p_43476_) {
      return true;
   }
}