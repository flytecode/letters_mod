package net.minecraft.world.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowledgeBookItem extends Item {
   private static final String f_151103_ = "Recipes";
   private static final Logger f_42819_ = LogManager.getLogger();

   public KnowledgeBookItem(Item.Properties p_42822_) {
      super(p_42822_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_42824_, Player p_42825_, InteractionHand p_42826_) {
      ItemStack itemstack = p_42825_.m_21120_(p_42826_);
      CompoundTag compoundtag = itemstack.m_41783_();
      if (!p_42825_.m_150110_().f_35937_) {
         p_42825_.m_21008_(p_42826_, ItemStack.f_41583_);
      }

      if (compoundtag != null && compoundtag.m_128425_("Recipes", 9)) {
         if (!p_42824_.f_46443_) {
            ListTag listtag = compoundtag.m_128437_("Recipes", 8);
            List<Recipe<?>> list = Lists.newArrayList();
            RecipeManager recipemanager = p_42824_.m_142572_().m_129894_();

            for(int i = 0; i < listtag.size(); ++i) {
               String s = listtag.m_128778_(i);
               Optional<? extends Recipe<?>> optional = recipemanager.m_44043_(new ResourceLocation(s));
               if (!optional.isPresent()) {
                  f_42819_.error("Invalid recipe: {}", (Object)s);
                  return InteractionResultHolder.m_19100_(itemstack);
               }

               list.add(optional.get());
            }

            p_42825_.m_7281_(list);
            p_42825_.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         return InteractionResultHolder.m_19092_(itemstack, p_42824_.m_5776_());
      } else {
         f_42819_.error("Tag not valid: {}", (Object)compoundtag);
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }
}