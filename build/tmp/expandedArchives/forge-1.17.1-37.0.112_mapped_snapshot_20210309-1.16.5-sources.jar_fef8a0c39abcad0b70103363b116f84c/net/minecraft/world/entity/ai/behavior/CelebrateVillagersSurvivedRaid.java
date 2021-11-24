package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CelebrateVillagersSurvivedRaid extends Behavior<Villager> {
   @Nullable
   private Raid f_22682_;

   public CelebrateVillagersSurvivedRaid(int p_22684_, int p_22685_) {
      super(ImmutableMap.of(), p_22684_, p_22685_);
   }

   protected boolean m_6114_(ServerLevel p_22690_, Villager p_22691_) {
      BlockPos blockpos = p_22691_.m_142538_();
      this.f_22682_ = p_22690_.m_8832_(blockpos);
      return this.f_22682_ != null && this.f_22682_.m_37767_() && MoveToSkySeeingSpot.m_23558_(p_22690_, p_22691_, blockpos);
   }

   protected boolean m_6737_(ServerLevel p_22693_, Villager p_22694_, long p_22695_) {
      return this.f_22682_ != null && !this.f_22682_.m_37762_();
   }

   protected void m_6732_(ServerLevel p_22704_, Villager p_22705_, long p_22706_) {
      this.f_22682_ = null;
      p_22705_.m_6274_().m_21862_(p_22704_.m_46468_(), p_22704_.m_46467_());
   }

   protected void m_6725_(ServerLevel p_22712_, Villager p_22713_, long p_22714_) {
      Random random = p_22713_.m_21187_();
      if (random.nextInt(100) == 0) {
         p_22713_.m_35310_();
      }

      if (random.nextInt(200) == 0 && MoveToSkySeeingSpot.m_23558_(p_22712_, p_22713_, p_22713_.m_142538_())) {
         DyeColor dyecolor = Util.m_137545_(DyeColor.values(), random);
         int i = random.nextInt(3);
         ItemStack itemstack = this.m_22696_(dyecolor, i);
         FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(p_22713_.f_19853_, p_22713_, p_22713_.m_20185_(), p_22713_.m_20188_(), p_22713_.m_20189_(), itemstack);
         p_22713_.f_19853_.m_7967_(fireworkrocketentity);
      }

   }

   private ItemStack m_22696_(DyeColor p_22697_, int p_22698_) {
      ItemStack itemstack = new ItemStack(Items.f_42688_, 1);
      ItemStack itemstack1 = new ItemStack(Items.f_42689_);
      CompoundTag compoundtag = itemstack1.m_41698_("Explosion");
      List<Integer> list = Lists.newArrayList();
      list.add(p_22697_.m_41070_());
      compoundtag.m_128408_("Colors", list);
      compoundtag.m_128344_("Type", (byte)FireworkRocketItem.Shape.BURST.m_41236_());
      CompoundTag compoundtag1 = itemstack.m_41698_("Fireworks");
      ListTag listtag = new ListTag();
      CompoundTag compoundtag2 = itemstack1.m_41737_("Explosion");
      if (compoundtag2 != null) {
         listtag.add(compoundtag2);
      }

      compoundtag1.m_128344_("Flight", (byte)p_22698_);
      if (!listtag.isEmpty()) {
         compoundtag1.m_128365_("Explosions", listtag);
      }

      return itemstack;
   }
}