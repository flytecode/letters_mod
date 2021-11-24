package net.minecraft.client.gui.screens.achievement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StatsScreen extends Screen implements StatsUpdateListener {
   private static final Component f_96897_ = new TranslatableComponent("multiplayer.downloadingStats");
   protected final Screen f_96896_;
   private StatsScreen.GeneralStatisticsList f_96898_;
   StatsScreen.ItemStatisticsList f_96899_;
   private StatsScreen.MobsStatisticsList f_96900_;
   final StatsCounter f_96901_;
   @Nullable
   private ObjectSelectionList<?> f_96902_;
   private boolean f_96903_ = true;
   private static final int f_169489_ = 128;
   private static final int f_169490_ = 18;
   private static final int f_169491_ = 20;
   private static final int f_169492_ = 1;
   private static final int f_169493_ = 1;
   private static final int f_169494_ = 2;
   private static final int f_169495_ = 2;
   private static final int f_169484_ = 40;
   private static final int f_169485_ = 5;
   private static final int f_169486_ = 0;
   private static final int f_169487_ = -1;
   private static final int f_169488_ = 1;

   public StatsScreen(Screen p_96906_, StatsCounter p_96907_) {
      super(new TranslatableComponent("gui.stats"));
      this.f_96896_ = p_96906_;
      this.f_96901_ = p_96907_;
   }

   protected void m_7856_() {
      this.f_96903_ = true;
      this.f_96541_.m_91403_().m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
   }

   public void m_96972_() {
      this.f_96898_ = new StatsScreen.GeneralStatisticsList(this.f_96541_);
      this.f_96899_ = new StatsScreen.ItemStatisticsList(this.f_96541_);
      this.f_96900_ = new StatsScreen.MobsStatisticsList(this.f_96541_);
   }

   public void m_96975_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 120, this.f_96544_ - 52, 80, 20, new TranslatableComponent("stat.generalButton"), (p_96963_) -> {
         this.m_96924_(this.f_96898_);
      }));
      Button button = this.m_142416_(new Button(this.f_96543_ / 2 - 40, this.f_96544_ - 52, 80, 20, new TranslatableComponent("stat.itemsButton"), (p_96959_) -> {
         this.m_96924_(this.f_96899_);
      }));
      Button button1 = this.m_142416_(new Button(this.f_96543_ / 2 + 40, this.f_96544_ - 52, 80, 20, new TranslatableComponent("stat.mobsButton"), (p_96949_) -> {
         this.m_96924_(this.f_96900_);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 28, 200, 20, CommonComponents.f_130655_, (p_96923_) -> {
         this.f_96541_.m_91152_(this.f_96896_);
      }));
      if (this.f_96899_.m_6702_().isEmpty()) {
         button.f_93623_ = false;
      }

      if (this.f_96900_.m_6702_().isEmpty()) {
         button1.f_93623_ = false;
      }

   }

   public void m_6305_(PoseStack p_96913_, int p_96914_, int p_96915_, float p_96916_) {
      if (this.f_96903_) {
         this.m_7333_(p_96913_);
         m_93215_(p_96913_, this.f_96547_, f_96897_, this.f_96543_ / 2, this.f_96544_ / 2, 16777215);
         m_93208_(p_96913_, this.f_96547_, f_97124_[(int)(Util.m_137550_() / 150L % (long)f_97124_.length)], this.f_96543_ / 2, this.f_96544_ / 2 + 9 * 2, 16777215);
      } else {
         this.m_96983_().m_6305_(p_96913_, p_96914_, p_96915_, p_96916_);
         m_93215_(p_96913_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
         super.m_6305_(p_96913_, p_96914_, p_96915_, p_96916_);
      }

   }

   public void m_7819_() {
      if (this.f_96903_) {
         this.m_96972_();
         this.m_96975_();
         this.m_96924_(this.f_96898_);
         this.f_96903_ = false;
      }

   }

   public boolean m_7043_() {
      return !this.f_96903_;
   }

   @Nullable
   public ObjectSelectionList<?> m_96983_() {
      return this.f_96902_;
   }

   public void m_96924_(@Nullable ObjectSelectionList<?> p_96925_) {
      if (this.f_96902_ != null) {
         this.m_169411_(this.f_96902_);
      }

      if (p_96925_ != null) {
         this.m_7787_(p_96925_);
         this.f_96902_ = p_96925_;
      }

   }

   static String m_96946_(Stat<ResourceLocation> p_96947_) {
      return "stat." + p_96947_.m_12867_().toString().replace(':', '.');
   }

   int m_96908_(int p_96909_) {
      return 115 + 40 * p_96909_;
   }

   void m_96917_(PoseStack p_96918_, int p_96919_, int p_96920_, Item p_96921_) {
      this.m_96952_(p_96918_, p_96919_ + 1, p_96920_ + 1, 0, 0);
      this.f_96542_.m_115123_(p_96921_.m_7968_(), p_96919_ + 2, p_96920_ + 2);
   }

   void m_96952_(PoseStack p_96953_, int p_96954_, int p_96955_, int p_96956_, int p_96957_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_93097_);
      m_93143_(p_96953_, p_96954_, p_96955_, this.m_93252_(), (float)p_96956_, (float)p_96957_, 18, 18, 128, 128);
   }

   @OnlyIn(Dist.CLIENT)
   class GeneralStatisticsList extends ObjectSelectionList<StatsScreen.GeneralStatisticsList.Entry> {
      public GeneralStatisticsList(Minecraft p_96995_) {
         super(p_96995_, StatsScreen.this.f_96543_, StatsScreen.this.f_96544_, 32, StatsScreen.this.f_96544_ - 64, 10);
         ObjectArrayList<Stat<ResourceLocation>> objectarraylist = new ObjectArrayList<>(Stats.f_12988_.iterator());
         objectarraylist.sort(Comparator.comparing((p_96997_) -> {
            return I18n.m_118938_(StatsScreen.m_96946_(p_96997_));
         }));

         for(Stat<ResourceLocation> stat : objectarraylist) {
            this.m_7085_(new StatsScreen.GeneralStatisticsList.Entry(stat));
         }

      }

      protected void m_7733_(PoseStack p_96999_) {
         StatsScreen.this.m_7333_(p_96999_);
      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ObjectSelectionList.Entry<StatsScreen.GeneralStatisticsList.Entry> {
         private final Stat<ResourceLocation> f_97001_;
         private final Component f_97002_;

         Entry(Stat<ResourceLocation> p_97005_) {
            this.f_97001_ = p_97005_;
            this.f_97002_ = new TranslatableComponent(StatsScreen.m_96946_(p_97005_));
         }

         private String m_169513_() {
            return this.f_97001_.m_12860_(StatsScreen.this.f_96901_.m_13015_(this.f_97001_));
         }

         public void m_6311_(PoseStack p_97011_, int p_97012_, int p_97013_, int p_97014_, int p_97015_, int p_97016_, int p_97017_, int p_97018_, boolean p_97019_, float p_97020_) {
            GuiComponent.m_93243_(p_97011_, StatsScreen.this.f_96547_, this.f_97002_, p_97014_ + 2, p_97013_ + 1, p_97012_ % 2 == 0 ? 16777215 : 9474192);
            String s = this.m_169513_();
            GuiComponent.m_93236_(p_97011_, StatsScreen.this.f_96547_, s, p_97014_ + 2 + 213 - StatsScreen.this.f_96547_.m_92895_(s), p_97013_ + 1, p_97012_ % 2 == 0 ? 16777215 : 9474192);
         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", (new TextComponent("")).m_7220_(this.f_97002_).m_130946_(" ").m_130946_(this.m_169513_()));
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class ItemStatisticsList extends ObjectSelectionList<StatsScreen.ItemStatisticsList.ItemRow> {
      protected final List<StatType<Block>> f_97021_;
      protected final List<StatType<Item>> f_97022_;
      private final int[] f_97029_ = new int[]{3, 4, 1, 2, 5, 6};
      protected int f_97023_ = -1;
      protected final Comparator<StatsScreen.ItemStatisticsList.ItemRow> f_97025_ = new StatsScreen.ItemStatisticsList.ItemRowComparator();
      @Nullable
      protected StatType<?> f_97026_;
      protected int f_97027_;

      public ItemStatisticsList(Minecraft p_97032_) {
         super(p_97032_, StatsScreen.this.f_96543_, StatsScreen.this.f_96544_, 32, StatsScreen.this.f_96544_ - 64, 20);
         this.f_97021_ = Lists.newArrayList();
         this.f_97021_.add(Stats.f_12949_);
         this.f_97022_ = Lists.newArrayList(Stats.f_12983_, Stats.f_12981_, Stats.f_12982_, Stats.f_12984_, Stats.f_12985_);
         this.m_93473_(true, 20);
         Set<Item> set = Sets.newIdentityHashSet();

         for(Item item : Registry.f_122827_) {
            boolean flag = false;

            for(StatType<Item> stattype : this.f_97022_) {
               if (stattype.m_12897_(item) && StatsScreen.this.f_96901_.m_13015_(stattype.m_12902_(item)) > 0) {
                  flag = true;
               }
            }

            if (flag) {
               set.add(item);
            }
         }

         for(Block block : Registry.f_122824_) {
            boolean flag1 = false;

            for(StatType<Block> stattype1 : this.f_97021_) {
               if (stattype1.m_12897_(block) && StatsScreen.this.f_96901_.m_13015_(stattype1.m_12902_(block)) > 0) {
                  flag1 = true;
               }
            }

            if (flag1) {
               set.add(block.m_5456_());
            }
         }

         set.remove(Items.f_41852_);

         for(Item item1 : set) {
            this.m_7085_(new StatsScreen.ItemStatisticsList.ItemRow(item1));
         }

      }

      protected void m_7154_(PoseStack p_97049_, int p_97050_, int p_97051_, Tesselator p_97052_) {
         if (!this.f_93386_.f_91067_.m_91560_()) {
            this.f_97023_ = -1;
         }

         for(int i = 0; i < this.f_97029_.length; ++i) {
            StatsScreen.this.m_96952_(p_97049_, p_97050_ + StatsScreen.this.m_96908_(i) - 18, p_97051_ + 1, 0, this.f_97023_ == i ? 0 : 18);
         }

         if (this.f_97026_ != null) {
            int k = StatsScreen.this.m_96908_(this.m_97058_(this.f_97026_)) - 36;
            int j = this.f_97027_ == 1 ? 2 : 1;
            StatsScreen.this.m_96952_(p_97049_, p_97050_ + k, p_97051_ + 1, 18 * j, 0);
         }

         for(int l = 0; l < this.f_97029_.length; ++l) {
            int i1 = this.f_97023_ == l ? 1 : 0;
            StatsScreen.this.m_96952_(p_97049_, p_97050_ + StatsScreen.this.m_96908_(l) - 18 + i1, p_97051_ + 1 + i1, 18 * this.f_97029_[l], 18);
         }

      }

      public int m_5759_() {
         return 375;
      }

      protected int m_5756_() {
         return this.f_93388_ / 2 + 140;
      }

      protected void m_7733_(PoseStack p_97043_) {
         StatsScreen.this.m_7333_(p_97043_);
      }

      protected void m_6205_(int p_97036_, int p_97037_) {
         this.f_97023_ = -1;

         for(int i = 0; i < this.f_97029_.length; ++i) {
            int j = p_97036_ - StatsScreen.this.m_96908_(i);
            if (j >= -36 && j <= 0) {
               this.f_97023_ = i;
               break;
            }
         }

         if (this.f_97023_ >= 0) {
            this.m_97038_(this.m_97033_(this.f_97023_));
            this.f_93386_.m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0F));
         }

      }

      private StatType<?> m_97033_(int p_97034_) {
         return p_97034_ < this.f_97021_.size() ? this.f_97021_.get(p_97034_) : this.f_97022_.get(p_97034_ - this.f_97021_.size());
      }

      private int m_97058_(StatType<?> p_97059_) {
         int i = this.f_97021_.indexOf(p_97059_);
         if (i >= 0) {
            return i;
         } else {
            int j = this.f_97022_.indexOf(p_97059_);
            return j >= 0 ? j + this.f_97021_.size() : -1;
         }
      }

      protected void m_7415_(PoseStack p_97045_, int p_97046_, int p_97047_) {
         if (p_97047_ >= this.f_93390_ && p_97047_ <= this.f_93391_) {
            StatsScreen.ItemStatisticsList.ItemRow statsscreen$itemstatisticslist$itemrow = this.m_168795_();
            int i = (this.f_93388_ - this.m_5759_()) / 2;
            if (statsscreen$itemstatisticslist$itemrow != null) {
               if (p_97046_ < i + 40 || p_97046_ > i + 40 + 20) {
                  return;
               }

               Item item = statsscreen$itemstatisticslist$itemrow.m_169519_();
               this.m_97053_(p_97045_, this.m_97040_(item), p_97046_, p_97047_);
            } else {
               Component component = null;
               int j = p_97046_ - i;

               for(int k = 0; k < this.f_97029_.length; ++k) {
                  int l = StatsScreen.this.m_96908_(k);
                  if (j >= l - 18 && j <= l) {
                     component = this.m_97033_(k).m_12905_();
                     break;
                  }
               }

               this.m_97053_(p_97045_, component, p_97046_, p_97047_);
            }

         }
      }

      protected void m_97053_(PoseStack p_97054_, @Nullable Component p_97055_, int p_97056_, int p_97057_) {
         if (p_97055_ != null) {
            int i = p_97056_ + 12;
            int j = p_97057_ - 12;
            int k = StatsScreen.this.f_96547_.m_92852_(p_97055_);
            this.m_93179_(p_97054_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
            p_97054_.m_85836_();
            p_97054_.m_85837_(0.0D, 0.0D, 400.0D);
            StatsScreen.this.f_96547_.m_92763_(p_97054_, p_97055_, (float)i, (float)j, -1);
            p_97054_.m_85849_();
         }
      }

      protected Component m_97040_(Item p_97041_) {
         return p_97041_.m_41466_();
      }

      protected void m_97038_(StatType<?> p_97039_) {
         if (p_97039_ != this.f_97026_) {
            this.f_97026_ = p_97039_;
            this.f_97027_ = -1;
         } else if (this.f_97027_ == -1) {
            this.f_97027_ = 1;
         } else {
            this.f_97026_ = null;
            this.f_97027_ = 0;
         }

         this.m_6702_().sort(this.f_97025_);
      }

      @OnlyIn(Dist.CLIENT)
      class ItemRow extends ObjectSelectionList.Entry<StatsScreen.ItemStatisticsList.ItemRow> {
         private final Item f_169514_;

         ItemRow(Item p_169517_) {
            this.f_169514_ = p_169517_;
         }

         public Item m_169519_() {
            return this.f_169514_;
         }

         public void m_6311_(PoseStack p_97081_, int p_97082_, int p_97083_, int p_97084_, int p_97085_, int p_97086_, int p_97087_, int p_97088_, boolean p_97089_, float p_97090_) {
            StatsScreen.this.m_96917_(p_97081_, p_97084_ + 40, p_97083_, this.f_169514_);

            for(int i = 0; i < StatsScreen.this.f_96899_.f_97021_.size(); ++i) {
               Stat<Block> stat;
               if (this.f_169514_ instanceof BlockItem) {
                  stat = StatsScreen.this.f_96899_.f_97021_.get(i).m_12902_(((BlockItem)this.f_169514_).m_40614_());
               } else {
                  stat = null;
               }

               this.m_97091_(p_97081_, stat, p_97084_ + StatsScreen.this.m_96908_(i), p_97083_, p_97082_ % 2 == 0);
            }

            for(int j = 0; j < StatsScreen.this.f_96899_.f_97022_.size(); ++j) {
               this.m_97091_(p_97081_, StatsScreen.this.f_96899_.f_97022_.get(j).m_12902_(this.f_169514_), p_97084_ + StatsScreen.this.m_96908_(j + StatsScreen.this.f_96899_.f_97021_.size()), p_97083_, p_97082_ % 2 == 0);
            }

         }

         protected void m_97091_(PoseStack p_97092_, @Nullable Stat<?> p_97093_, int p_97094_, int p_97095_, boolean p_97096_) {
            String s = p_97093_ == null ? "-" : p_97093_.m_12860_(StatsScreen.this.f_96901_.m_13015_(p_97093_));
            GuiComponent.m_93236_(p_97092_, StatsScreen.this.f_96547_, s, p_97094_ - StatsScreen.this.f_96547_.m_92895_(s), p_97095_ + 5, p_97096_ ? 16777215 : 9474192);
         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", this.f_169514_.m_41466_());
         }
      }

      @OnlyIn(Dist.CLIENT)
      class ItemRowComparator implements Comparator<StatsScreen.ItemStatisticsList.ItemRow> {
         public int compare(StatsScreen.ItemStatisticsList.ItemRow p_169524_, StatsScreen.ItemStatisticsList.ItemRow p_169525_) {
            Item item = p_169524_.m_169519_();
            Item item1 = p_169525_.m_169519_();
            int i;
            int j;
            if (ItemStatisticsList.this.f_97026_ == null) {
               i = 0;
               j = 0;
            } else if (ItemStatisticsList.this.f_97021_.contains(ItemStatisticsList.this.f_97026_)) {
               StatType<Block> stattype = (StatType<Block>) ItemStatisticsList.this.f_97026_;
               i = item instanceof BlockItem ? StatsScreen.this.f_96901_.m_13017_(stattype, ((BlockItem)item).m_40614_()) : -1;
               j = item1 instanceof BlockItem ? StatsScreen.this.f_96901_.m_13017_(stattype, ((BlockItem)item1).m_40614_()) : -1;
            } else {
               StatType<Item> stattype1 = (StatType<Item>) ItemStatisticsList.this.f_97026_;
               i = StatsScreen.this.f_96901_.m_13017_(stattype1, item);
               j = StatsScreen.this.f_96901_.m_13017_(stattype1, item1);
            }

            return i == j ? ItemStatisticsList.this.f_97027_ * Integer.compare(Item.m_41393_(item), Item.m_41393_(item1)) : ItemStatisticsList.this.f_97027_ * Integer.compare(i, j);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class MobsStatisticsList extends ObjectSelectionList<StatsScreen.MobsStatisticsList.MobRow> {
      public MobsStatisticsList(Minecraft p_97100_) {
         super(p_97100_, StatsScreen.this.f_96543_, StatsScreen.this.f_96544_, 32, StatsScreen.this.f_96544_ - 64, 9 * 4);

         for(EntityType<?> entitytype : Registry.f_122826_) {
            if (StatsScreen.this.f_96901_.m_13015_(Stats.f_12986_.m_12902_(entitytype)) > 0 || StatsScreen.this.f_96901_.m_13015_(Stats.f_12987_.m_12902_(entitytype)) > 0) {
               this.m_7085_(new StatsScreen.MobsStatisticsList.MobRow(entitytype));
            }
         }

      }

      protected void m_7733_(PoseStack p_97102_) {
         StatsScreen.this.m_7333_(p_97102_);
      }

      @OnlyIn(Dist.CLIENT)
      class MobRow extends ObjectSelectionList.Entry<StatsScreen.MobsStatisticsList.MobRow> {
         private final Component f_97105_;
         private final Component f_97106_;
         private final boolean f_97107_;
         private final Component f_97108_;
         private final boolean f_97109_;

         public MobRow(EntityType<?> p_97112_) {
            this.f_97105_ = p_97112_.m_20676_();
            int i = StatsScreen.this.f_96901_.m_13015_(Stats.f_12986_.m_12902_(p_97112_));
            if (i == 0) {
               this.f_97106_ = new TranslatableComponent("stat_type.minecraft.killed.none", this.f_97105_);
               this.f_97107_ = false;
            } else {
               this.f_97106_ = new TranslatableComponent("stat_type.minecraft.killed", i, this.f_97105_);
               this.f_97107_ = true;
            }

            int j = StatsScreen.this.f_96901_.m_13015_(Stats.f_12987_.m_12902_(p_97112_));
            if (j == 0) {
               this.f_97108_ = new TranslatableComponent("stat_type.minecraft.killed_by.none", this.f_97105_);
               this.f_97109_ = false;
            } else {
               this.f_97108_ = new TranslatableComponent("stat_type.minecraft.killed_by", this.f_97105_, j);
               this.f_97109_ = true;
            }

         }

         public void m_6311_(PoseStack p_97114_, int p_97115_, int p_97116_, int p_97117_, int p_97118_, int p_97119_, int p_97120_, int p_97121_, boolean p_97122_, float p_97123_) {
            GuiComponent.m_93243_(p_97114_, StatsScreen.this.f_96547_, this.f_97105_, p_97117_ + 2, p_97116_ + 1, 16777215);
            GuiComponent.m_93243_(p_97114_, StatsScreen.this.f_96547_, this.f_97106_, p_97117_ + 2 + 10, p_97116_ + 1 + 9, this.f_97107_ ? 9474192 : 6316128);
            GuiComponent.m_93243_(p_97114_, StatsScreen.this.f_96547_, this.f_97108_, p_97117_ + 2 + 10, p_97116_ + 1 + 9 * 2, this.f_97109_ ? 9474192 : 6316128);
         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", CommonComponents.m_178398_(this.f_97106_, this.f_97108_));
         }
      }
   }
}