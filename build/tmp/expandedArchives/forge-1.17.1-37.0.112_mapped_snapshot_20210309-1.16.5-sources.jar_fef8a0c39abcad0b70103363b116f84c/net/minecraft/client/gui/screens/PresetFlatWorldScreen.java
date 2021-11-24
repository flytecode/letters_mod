package net.minecraft.client.gui.screens;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class PresetFlatWorldScreen extends Screen {
   private static final Logger f_96368_ = LogManager.getLogger();
   private static final int f_169346_ = 128;
   private static final int f_169347_ = 18;
   private static final int f_169348_ = 20;
   private static final int f_169349_ = 1;
   private static final int f_169350_ = 1;
   private static final int f_169351_ = 2;
   private static final int f_169352_ = 2;
   static final List<PresetFlatWorldScreen.PresetInfo> f_96369_ = Lists.newArrayList();
   private static final ResourceKey<Biome> f_169353_ = Biomes.f_48202_;
   final CreateFlatWorldScreen f_96370_;
   private Component f_96371_;
   private Component f_96372_;
   private PresetFlatWorldScreen.PresetsList f_96373_;
   private Button f_96374_;
   EditBox f_96375_;
   FlatLevelGeneratorSettings f_96376_;

   public PresetFlatWorldScreen(CreateFlatWorldScreen p_96379_) {
      super(new TranslatableComponent("createWorld.customize.presets.title"));
      this.f_96370_ = p_96379_;
   }

   @Nullable
   private static FlatLayerInfo m_96413_(String p_96414_, int p_96415_) {
      String[] astring = p_96414_.split("\\*", 2);
      int i;
      if (astring.length == 2) {
         try {
            i = Math.max(Integer.parseInt(astring[0]), 0);
         } catch (NumberFormatException numberformatexception) {
            f_96368_.error("Error while parsing flat world string => {}", (Object)numberformatexception.getMessage());
            return null;
         }
      } else {
         i = 1;
      }

      int j = Math.min(p_96415_ + i, DimensionType.f_156651_);
      int k = j - p_96415_;
      String s = astring[astring.length - 1];

      Block block;
      try {
         block = Registry.f_122824_.m_6612_(new ResourceLocation(s)).orElse((Block)null);
      } catch (Exception exception) {
         f_96368_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
         return null;
      }

      if (block == null) {
         f_96368_.error("Error while parsing flat world string => Unknown block, {}", (Object)s);
         return null;
      } else {
         return new FlatLayerInfo(k, block);
      }
   }

   private static List<FlatLayerInfo> m_96445_(String p_96446_) {
      List<FlatLayerInfo> list = Lists.newArrayList();
      String[] astring = p_96446_.split(",");
      int i = 0;

      for(String s : astring) {
         FlatLayerInfo flatlayerinfo = m_96413_(s, i);
         if (flatlayerinfo == null) {
            return Collections.emptyList();
         }

         list.add(flatlayerinfo);
         i += flatlayerinfo.m_70337_();
      }

      return list;
   }

   public static FlatLevelGeneratorSettings m_96406_(Registry<Biome> p_96407_, String p_96408_, FlatLevelGeneratorSettings p_96409_) {
      Iterator<String> iterator = Splitter.on(';').split(p_96408_).iterator();
      if (!iterator.hasNext()) {
         return FlatLevelGeneratorSettings.m_70376_(p_96407_);
      } else {
         List<FlatLayerInfo> list = m_96445_(iterator.next());
         if (list.isEmpty()) {
            return FlatLevelGeneratorSettings.m_70376_(p_96407_);
         } else {
            FlatLevelGeneratorSettings flatlevelgeneratorsettings = p_96409_.m_70380_(list, p_96409_.m_70395_());
            ResourceKey<Biome> resourcekey = f_169353_;
            if (iterator.hasNext()) {
               try {
                  ResourceLocation resourcelocation = new ResourceLocation(iterator.next());
                  resourcekey = ResourceKey.m_135785_(Registry.f_122885_, resourcelocation);
                  p_96407_.m_123009_(resourcekey).orElseThrow(() -> {
                     return new IllegalArgumentException("Invalid Biome: " + resourcelocation);
                  });
               } catch (Exception exception) {
                  f_96368_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
                  resourcekey = f_169353_;
               }
            }

            ResourceKey<Biome> resourcekey1 = resourcekey;
            flatlevelgeneratorsettings.m_70383_(() -> {
               return p_96407_.m_123013_(resourcekey1);
            });
            return flatlevelgeneratorsettings;
         }
      }
   }

   static String m_96439_(Registry<Biome> p_96440_, FlatLevelGeneratorSettings p_96441_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = 0; i < p_96441_.m_70401_().size(); ++i) {
         if (i > 0) {
            stringbuilder.append(",");
         }

         stringbuilder.append(p_96441_.m_70401_().get(i));
      }

      stringbuilder.append(";");
      stringbuilder.append((Object)p_96440_.m_7981_(p_96441_.m_70400_()));
      return stringbuilder.toString();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_96371_ = new TranslatableComponent("createWorld.customize.presets.share");
      this.f_96372_ = new TranslatableComponent("createWorld.customize.presets.list");
      this.f_96375_ = new EditBox(this.f_96547_, 50, 40, this.f_96543_ - 100, 20, this.f_96371_);
      this.f_96375_.m_94199_(1230);
      Registry<Biome> registry = this.f_96370_.f_95814_.f_100847_.m_101456_().m_175515_(Registry.f_122885_);
      this.f_96375_.m_94144_(m_96439_(registry, this.f_96370_.m_95846_()));
      this.f_96376_ = this.f_96370_.m_95846_();
      this.m_7787_(this.f_96375_);
      this.f_96373_ = new PresetFlatWorldScreen.PresetsList();
      this.m_7787_(this.f_96373_);
      this.f_96374_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, new TranslatableComponent("createWorld.customize.presets.select"), (p_96405_) -> {
         FlatLevelGeneratorSettings flatlevelgeneratorsettings = m_96406_(registry, this.f_96375_.m_94155_(), this.f_96376_);
         this.f_96370_.m_95825_(flatlevelgeneratorsettings);
         this.f_96541_.m_91152_(this.f_96370_);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, (p_96394_) -> {
         this.f_96541_.m_91152_(this.f_96370_);
      }));
      this.m_96449_(this.f_96373_.m_93511_() != null);
   }

   public boolean m_6050_(double p_96381_, double p_96382_, double p_96383_) {
      return this.f_96373_.m_6050_(p_96381_, p_96382_, p_96383_);
   }

   public void m_6574_(Minecraft p_96390_, int p_96391_, int p_96392_) {
      String s = this.f_96375_.m_94155_();
      this.m_6575_(p_96390_, p_96391_, p_96392_);
      this.f_96375_.m_94144_(s);
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_96370_);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public void m_6305_(PoseStack p_96385_, int p_96386_, int p_96387_, float p_96388_) {
      this.m_7333_(p_96385_);
      this.f_96373_.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
      p_96385_.m_85836_();
      p_96385_.m_85837_(0.0D, 0.0D, 400.0D);
      m_93215_(p_96385_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      m_93243_(p_96385_, this.f_96547_, this.f_96371_, 50, 30, 10526880);
      m_93243_(p_96385_, this.f_96547_, this.f_96372_, 50, 70, 10526880);
      p_96385_.m_85849_();
      this.f_96375_.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
      super.m_6305_(p_96385_, p_96386_, p_96387_, p_96388_);
   }

   public void m_96624_() {
      this.f_96375_.m_94120_();
      super.m_96624_();
   }

   public void m_96449_(boolean p_96450_) {
      this.f_96374_.f_93623_ = p_96450_ || this.f_96375_.m_94155_().length() > 1;
   }

   private static void m_96424_(Component p_96425_, ItemLike p_96426_, ResourceKey<Biome> p_96427_, List<StructureFeature<?>> p_96428_, boolean p_96429_, boolean p_96430_, boolean p_96431_, FlatLayerInfo... p_96432_) {
      f_96369_.add(new PresetFlatWorldScreen.PresetInfo(p_96426_.m_5456_(), p_96425_, (p_96423_) -> {
         Map<StructureFeature<?>, StructureFeatureConfiguration> map = Maps.newHashMap();

         for(StructureFeature<?> structurefeature : p_96428_) {
            map.put(structurefeature, StructureSettings.f_64580_.get(structurefeature));
         }

         StructureSettings structuresettings = new StructureSettings(p_96429_ ? Optional.of(StructureSettings.f_64581_) : Optional.empty(), map);
         FlatLevelGeneratorSettings flatlevelgeneratorsettings = new FlatLevelGeneratorSettings(structuresettings, p_96423_);
         if (p_96430_) {
            flatlevelgeneratorsettings.m_70369_();
         }

         if (p_96431_) {
            flatlevelgeneratorsettings.m_70385_();
         }

         for(int i = p_96432_.length - 1; i >= 0; --i) {
            flatlevelgeneratorsettings.m_70401_().add(p_96432_[i]);
         }

         flatlevelgeneratorsettings.m_70383_(() -> {
            return p_96423_.m_123013_(p_96427_);
         });
         flatlevelgeneratorsettings.m_70403_();
         return flatlevelgeneratorsettings.m_70370_(structuresettings);
      }));
   }

   static {
      m_96424_(new TranslatableComponent("createWorld.customize.preset.classic_flat"), Blocks.f_50440_, Biomes.f_48202_, Arrays.asList(StructureFeature.f_67028_), false, false, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(2, Blocks.f_50493_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.tunnelers_dream"), Blocks.f_50069_, Biomes.f_48204_, Arrays.asList(StructureFeature.f_67014_), true, true, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(5, Blocks.f_50493_), new FlatLayerInfo(230, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.water_world"), Items.f_42447_, Biomes.f_48225_, Arrays.asList(StructureFeature.f_67024_, StructureFeature.f_67020_, StructureFeature.f_67023_), false, false, false, new FlatLayerInfo(90, Blocks.f_49990_), new FlatLayerInfo(5, Blocks.f_49992_), new FlatLayerInfo(5, Blocks.f_50493_), new FlatLayerInfo(5, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.overworld"), Blocks.f_50034_, Biomes.f_48202_, Arrays.asList(StructureFeature.f_67028_, StructureFeature.f_67014_, StructureFeature.f_67013_, StructureFeature.f_67019_), true, true, true, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(59, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.snowy_kingdom"), Blocks.f_50125_, Biomes.f_48213_, Arrays.asList(StructureFeature.f_67028_, StructureFeature.f_67018_), false, false, false, new FlatLayerInfo(1, Blocks.f_50125_), new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(59, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.bottomless_pit"), Items.f_42402_, Biomes.f_48202_, Arrays.asList(StructureFeature.f_67028_), false, false, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(2, Blocks.f_50652_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.desert"), Blocks.f_49992_, Biomes.f_48203_, Arrays.asList(StructureFeature.f_67028_, StructureFeature.f_67017_, StructureFeature.f_67014_), true, true, false, new FlatLayerInfo(8, Blocks.f_49992_), new FlatLayerInfo(52, Blocks.f_50062_), new FlatLayerInfo(3, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.redstone_ready"), Items.f_42451_, Biomes.f_48203_, Collections.emptyList(), false, false, false, new FlatLayerInfo(52, Blocks.f_50062_), new FlatLayerInfo(3, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
      m_96424_(new TranslatableComponent("createWorld.customize.preset.the_void"), Blocks.f_50375_, Biomes.f_48173_, Collections.emptyList(), false, true, false, new FlatLayerInfo(1, Blocks.f_50016_));
   }

   @OnlyIn(Dist.CLIENT)
   static class PresetInfo {
      public final Item f_96454_;
      public final Component f_96455_;
      public final Function<Registry<Biome>, FlatLevelGeneratorSettings> f_96456_;

      public PresetInfo(Item p_96458_, Component p_96459_, Function<Registry<Biome>, FlatLevelGeneratorSettings> p_96460_) {
         this.f_96454_ = p_96458_;
         this.f_96455_ = p_96459_;
         this.f_96456_ = p_96460_;
      }

      public Component m_96461_() {
         return this.f_96455_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   class PresetsList extends ObjectSelectionList<PresetFlatWorldScreen.PresetsList.Entry> {
      public PresetsList() {
         super(PresetFlatWorldScreen.this.f_96541_, PresetFlatWorldScreen.this.f_96543_, PresetFlatWorldScreen.this.f_96544_, 80, PresetFlatWorldScreen.this.f_96544_ - 37, 24);

         for(PresetFlatWorldScreen.PresetInfo presetflatworldscreen$presetinfo : PresetFlatWorldScreen.f_96369_) {
            this.m_7085_(new PresetFlatWorldScreen.PresetsList.Entry(presetflatworldscreen$presetinfo));
         }

      }

      public void m_6987_(@Nullable PresetFlatWorldScreen.PresetsList.Entry p_96472_) {
         super.m_6987_(p_96472_);
         PresetFlatWorldScreen.this.m_96449_(p_96472_ != null);
      }

      protected boolean m_5694_() {
         return PresetFlatWorldScreen.this.m_7222_() == this;
      }

      public boolean m_7933_(int p_96466_, int p_96467_, int p_96468_) {
         if (super.m_7933_(p_96466_, p_96467_, p_96468_)) {
            return true;
         } else {
            if ((p_96466_ == 257 || p_96466_ == 335) && this.m_93511_() != null) {
               this.m_93511_().m_96479_();
            }

            return false;
         }
      }

      @OnlyIn(Dist.CLIENT)
      public class Entry extends ObjectSelectionList.Entry<PresetFlatWorldScreen.PresetsList.Entry> {
         private final PresetFlatWorldScreen.PresetInfo f_169357_;

         public Entry(PresetFlatWorldScreen.PresetInfo p_169360_) {
            this.f_169357_ = p_169360_;
         }

         public void m_6311_(PoseStack p_96489_, int p_96490_, int p_96491_, int p_96492_, int p_96493_, int p_96494_, int p_96495_, int p_96496_, boolean p_96497_, float p_96498_) {
            this.m_96499_(p_96489_, p_96492_, p_96491_, this.f_169357_.f_96454_);
            PresetFlatWorldScreen.this.f_96547_.m_92889_(p_96489_, this.f_169357_.f_96455_, (float)(p_96492_ + 18 + 5), (float)(p_96491_ + 6), 16777215);
         }

         public boolean m_6375_(double p_96481_, double p_96482_, int p_96483_) {
            if (p_96483_ == 0) {
               this.m_96479_();
            }

            return false;
         }

         void m_96479_() {
            PresetsList.this.m_6987_(this);
            Registry<Biome> registry = PresetFlatWorldScreen.this.f_96370_.f_95814_.f_100847_.m_101456_().m_175515_(Registry.f_122885_);
            PresetFlatWorldScreen.this.f_96376_ = this.f_169357_.f_96456_.apply(registry);
            PresetFlatWorldScreen.this.f_96375_.m_94144_(PresetFlatWorldScreen.m_96439_(registry, PresetFlatWorldScreen.this.f_96376_));
            PresetFlatWorldScreen.this.f_96375_.m_94198_();
         }

         private void m_96499_(PoseStack p_96500_, int p_96501_, int p_96502_, Item p_96503_) {
            this.m_96484_(p_96500_, p_96501_ + 1, p_96502_ + 1);
            PresetFlatWorldScreen.this.f_96542_.m_115123_(new ItemStack(p_96503_), p_96501_ + 2, p_96502_ + 2);
         }

         private void m_96484_(PoseStack p_96485_, int p_96486_, int p_96487_) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.m_157456_(0, GuiComponent.f_93097_);
            GuiComponent.m_93143_(p_96485_, p_96486_, p_96487_, PresetFlatWorldScreen.this.m_93252_(), 0.0F, 0.0F, 18, 18, 128, 128);
         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", this.f_169357_.m_96461_());
         }
      }
   }
}