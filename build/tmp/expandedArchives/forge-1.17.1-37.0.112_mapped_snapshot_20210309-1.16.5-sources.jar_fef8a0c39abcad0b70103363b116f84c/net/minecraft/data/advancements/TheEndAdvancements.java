package net.minecraft.data.advancements;

import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LevitationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class TheEndAdvancements implements Consumer<Consumer<Advancement>> {
   public void accept(Consumer<Advancement> p_124030_) {
      Advancement advancement = Advancement.Builder.m_138353_().m_138371_(Blocks.f_50259_, new TranslatableComponent("advancements.end.root.title"), new TranslatableComponent("advancements.end.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/end.png"), FrameType.TASK, false, false, false).m_138386_("entered_end", ChangeDimensionTrigger.TriggerInstance.m_19782_(Level.f_46430_)).m_138389_(p_124030_, "end/root");
      Advancement advancement1 = Advancement.Builder.m_138353_().m_138398_(advancement).m_138371_(Blocks.f_50320_, new TranslatableComponent("advancements.end.kill_dragon.title"), new TranslatableComponent("advancements.end.kill_dragon.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("killed_dragon", KilledTrigger.TriggerInstance.m_48134_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20565_))).m_138389_(p_124030_, "end/kill_dragon");
      Advancement advancement2 = Advancement.Builder.m_138353_().m_138398_(advancement1).m_138371_(Items.f_42584_, new TranslatableComponent("advancements.end.enter_end_gateway.title"), new TranslatableComponent("advancements.end.enter_end_gateway.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("entered_end_gateway", EnterBlockTrigger.TriggerInstance.m_31297_(Blocks.f_50446_)).m_138389_(p_124030_, "end/enter_end_gateway");
      Advancement.Builder.m_138353_().m_138398_(advancement1).m_138371_(Items.f_42729_, new TranslatableComponent("advancements.end.respawn_dragon.title"), new TranslatableComponent("advancements.end.respawn_dragon.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("summoned_dragon", SummonedEntityTrigger.TriggerInstance.m_68275_(EntityPredicate.Builder.m_36633_().m_36636_(EntityType.f_20565_))).m_138389_(p_124030_, "end/respawn_dragon");
      Advancement advancement3 = Advancement.Builder.m_138353_().m_138398_(advancement2).m_138371_(Blocks.f_50492_, new TranslatableComponent("advancements.end.find_end_city.title"), new TranslatableComponent("advancements.end.find_end_city.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).m_138386_("in_city", LocationTrigger.TriggerInstance.m_53670_(LocationPredicate.m_52627_(StructureFeature.f_67026_))).m_138389_(p_124030_, "end/find_end_city");
      Advancement.Builder.m_138353_().m_138398_(advancement1).m_138371_(Items.f_42735_, new TranslatableComponent("advancements.end.dragon_breath.title"), new TranslatableComponent("advancements.end.dragon_breath.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("dragon_breath", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42735_)).m_138389_(p_124030_, "end/dragon_breath");
      Advancement.Builder.m_138353_().m_138398_(advancement3).m_138371_(Items.f_42748_, new TranslatableComponent("advancements.end.levitate.title"), new TranslatableComponent("advancements.end.levitate.description"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, false).m_138354_(AdvancementRewards.Builder.m_10005_(50)).m_138386_("levitated", LevitationTrigger.TriggerInstance.m_49144_(DistancePredicate.m_148838_(MinMaxBounds.Doubles.m_154804_(50.0D)))).m_138389_(p_124030_, "end/levitate");
      Advancement.Builder.m_138353_().m_138398_(advancement3).m_138371_(Items.f_42741_, new TranslatableComponent("advancements.end.elytra.title"), new TranslatableComponent("advancements.end.elytra.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("elytra", InventoryChangeTrigger.TriggerInstance.m_43199_(Items.f_42741_)).m_138389_(p_124030_, "end/elytra");
      Advancement.Builder.m_138353_().m_138398_(advancement1).m_138371_(Blocks.f_50260_, new TranslatableComponent("advancements.end.dragon_egg.title"), new TranslatableComponent("advancements.end.dragon_egg.description"), (ResourceLocation)null, FrameType.GOAL, true, true, false).m_138386_("dragon_egg", InventoryChangeTrigger.TriggerInstance.m_43199_(Blocks.f_50260_)).m_138389_(p_124030_, "end/dragon_egg");
   }
}