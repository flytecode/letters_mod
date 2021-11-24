package net.minecraft.world.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> implements net.minecraftforge.common.extensions.IForgeItemStack {
   public static final Codec<ItemStack> f_41582_ = RecordCodecBuilder.create((p_41697_) -> {
      return p_41697_.group(Registry.f_122827_.fieldOf("id").forGetter((p_150946_) -> {
         return p_150946_.f_41589_;
      }), Codec.INT.fieldOf("Count").forGetter((p_150941_) -> {
         return p_150941_.f_41587_;
      }), CompoundTag.f_128325_.optionalFieldOf("tag").forGetter((p_150939_) -> {
         return Optional.ofNullable(p_150939_.f_41590_);
      })).apply(p_41697_, ItemStack::new);
   });
   private final net.minecraftforge.registries.IRegistryDelegate<Item> delegate;
   private CompoundTag capNBT;

   private static final Logger f_41585_ = LogManager.getLogger();
   public static final ItemStack f_41583_ = new ItemStack((Item)null);
   public static final DecimalFormat f_41584_ = Util.m_137469_(new DecimalFormat("#.##"), (p_41704_) -> {
      p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   });
   public static final String f_150906_ = "Enchantments";
   public static final String f_150909_ = "display";
   public static final String f_150910_ = "Name";
   public static final String f_150911_ = "Lore";
   public static final String f_150912_ = "Damage";
   public static final String f_150913_ = "color";
   private static final String f_150914_ = "Unbreakable";
   private static final String f_150915_ = "RepairCost";
   private static final String f_150916_ = "CanDestroy";
   private static final String f_150917_ = "CanPlaceOn";
   private static final String f_150918_ = "HideFlags";
   private static final int f_150919_ = 0;
   private static final Style f_41586_ = Style.f_131099_.m_131140_(ChatFormatting.DARK_PURPLE).m_131155_(true);
   private int f_41587_;
   private int f_41588_;
   @Deprecated
   private final Item f_41589_;
   private CompoundTag f_41590_;
   private boolean f_41591_;
   private Entity f_41592_;
   private BlockInWorld f_41593_;
   private boolean f_41594_;
   private BlockInWorld f_41595_;
   private boolean f_41596_;

   public Optional<TooltipComponent> m_150921_() {
      return this.m_41720_().m_142422_(this);
   }

   public ItemStack(ItemLike p_41599_) {
      this(p_41599_, 1);
   }

   private ItemStack(ItemLike p_41604_, int p_41605_, Optional<CompoundTag> p_41606_) {
      this(p_41604_, p_41605_);
      p_41606_.ifPresent(this::m_41751_);
   }

   public ItemStack(ItemLike p_41601_, int p_41602_) { this(p_41601_, p_41602_, (CompoundTag) null); }
   public ItemStack(ItemLike p_41604_, int p_41605_, @Nullable CompoundTag p_41606_) {
      super(ItemStack.class, true);
      this.capNBT = p_41606_;
      this.f_41589_ = p_41604_ == null ? null : p_41604_.m_5456_();
      this.delegate = p_41604_ == null ? null : p_41604_.m_5456_().delegate;
      this.f_41587_ = p_41605_;
      this.forgeInit();
      if (this.f_41589_ != null && this.f_41589_.isDamageable(this)) {
         this.m_41721_(this.m_41773_());
      }

      this.m_41617_();
   }

   private void m_41617_() {
      this.f_41591_ = false;
      this.f_41591_ = this.m_41619_();
   }

   private ItemStack(CompoundTag p_41608_) {
      super(ItemStack.class, true);
      this.capNBT = p_41608_.m_128441_("ForgeCaps") ? p_41608_.m_128469_("ForgeCaps") : null;
      Item rawItem =
      this.f_41589_ = Registry.f_122827_.m_7745_(new ResourceLocation(p_41608_.m_128461_("id")));
      this.delegate = rawItem.delegate;
      this.f_41587_ = p_41608_.m_128445_("Count");
      if (p_41608_.m_128425_("tag", 10)) {
         this.f_41590_ = p_41608_.m_128469_("tag");
         this.m_41720_().m_142312_(this.f_41590_);
      }
      this.forgeInit();

      if (this.m_41720_().isDamageable(this)) {
         this.m_41721_(this.m_41773_());
      }

      this.m_41617_();
   }

   public static ItemStack m_41712_(CompoundTag p_41713_) {
      try {
         return new ItemStack(p_41713_);
      } catch (RuntimeException runtimeexception) {
         f_41585_.debug("Tried to load invalid item: {}", p_41713_, runtimeexception);
         return f_41583_;
      }
   }

   public boolean m_41619_() {
      if (this == f_41583_) {
         return true;
      } else if (this.m_41720_() != null && !this.m_150930_(Items.f_41852_)) {
         return this.f_41587_ <= 0;
      } else {
         return true;
      }
   }

   public ItemStack m_41620_(int p_41621_) {
      int i = Math.min(p_41621_, this.f_41587_);
      ItemStack itemstack = this.m_41777_();
      itemstack.m_41764_(i);
      this.m_41774_(i);
      return itemstack;
   }

   public Item m_41720_() {
      return this.f_41591_ || this.delegate == null ? Items.f_41852_ : this.delegate.get();
   }

   public boolean m_150922_(Tag<Item> p_150923_) {
      return p_150923_.m_8110_(this.m_41720_());
   }

   public boolean m_150930_(Item p_150931_) {
      return this.m_41720_() == p_150931_;
   }

   public InteractionResult m_41661_(UseOnContext p_41662_) {
      if (!p_41662_.m_43725_().f_46443_) return net.minecraftforge.common.ForgeHooks.onPlaceItemIntoWorld(p_41662_);
      return onItemUse(p_41662_, (c) -> m_41720_().m_6225_(p_41662_));
   }

   public InteractionResult onItemUseFirst(UseOnContext p_41662_) {
      return onItemUse(p_41662_, (c) -> m_41720_().onItemUseFirst(this, p_41662_));
   }

   private InteractionResult onItemUse(UseOnContext p_41662_, java.util.function.Function<UseOnContext, InteractionResult> callback) {
      Player player = p_41662_.m_43723_();
      BlockPos blockpos = p_41662_.m_8083_();
      BlockInWorld blockinworld = new BlockInWorld(p_41662_.m_43725_(), blockpos, false);
      if (player != null && !player.m_150110_().f_35938_ && !this.m_41723_(p_41662_.m_43725_().m_5999_(), blockinworld)) {
         return InteractionResult.PASS;
      } else {
         Item item = this.m_41720_();
         InteractionResult interactionresult = callback.apply(p_41662_);
         if (player != null && interactionresult.m_146666_()) {
            player.m_36246_(Stats.f_12982_.m_12902_(item));
         }

         return interactionresult;
      }
   }

   public float m_41691_(BlockState p_41692_) {
      return this.m_41720_().m_8102_(this, p_41692_);
   }

   public InteractionResultHolder<ItemStack> m_41682_(Level p_41683_, Player p_41684_, InteractionHand p_41685_) {
      return this.m_41720_().m_7203_(p_41683_, p_41684_, p_41685_);
   }

   public ItemStack m_41671_(Level p_41672_, LivingEntity p_41673_) {
      return this.m_41720_().m_5922_(this, p_41672_, p_41673_);
   }

   public CompoundTag m_41739_(CompoundTag p_41740_) {
      ResourceLocation resourcelocation = Registry.f_122827_.m_7981_(this.m_41720_());
      p_41740_.m_128359_("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
      p_41740_.m_128344_("Count", (byte)this.f_41587_);
      if (this.f_41590_ != null) {
         p_41740_.m_128365_("tag", this.f_41590_.m_6426_());
      }

      CompoundTag cnbt = this.serializeCaps();
      if (cnbt != null && !cnbt.m_128456_()) {
         p_41740_.m_128365_("ForgeCaps", cnbt);
      }
      return p_41740_;
   }

   public int m_41741_() {
      return this.m_41720_().getItemStackLimit(this);
   }

   public boolean m_41753_() {
      return this.m_41741_() > 1 && (!this.m_41763_() || !this.m_41768_());
   }

   public boolean m_41763_() {
      if (!this.f_41591_ && this.m_41720_().isDamageable(this)) {
         CompoundTag compoundtag = this.m_41783_();
         return compoundtag == null || !compoundtag.m_128471_("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean m_41768_() {
      return this.m_41763_() && m_41720_().isDamaged(this);
   }

   public int m_41773_() {
      return this.m_41720_().getDamage(this);
   }

   public void m_41721_(int p_41722_) {
      this.m_41720_().setDamage(this, p_41722_);
   }

   public int m_41776_() {
      return this.m_41720_().getMaxDamage(this);
   }

   public boolean m_41629_(int p_41630_, Random p_41631_, @Nullable ServerPlayer p_41632_) {
      if (!this.m_41763_()) {
         return false;
      } else {
         if (p_41630_ > 0) {
            int i = EnchantmentHelper.m_44843_(Enchantments.f_44986_, this);
            int j = 0;

            for(int k = 0; i > 0 && k < p_41630_; ++k) {
               if (DigDurabilityEnchantment.m_44655_(this, i, p_41631_)) {
                  ++j;
               }
            }

            p_41630_ -= j;
            if (p_41630_ <= 0) {
               return false;
            }
         }

         if (p_41632_ != null && p_41630_ != 0) {
            CriteriaTriggers.f_10586_.m_43669_(p_41632_, this, this.m_41773_() + p_41630_);
         }

         int l = this.m_41773_() + p_41630_;
         this.m_41721_(l);
         return l >= this.m_41776_();
      }
   }

   public <T extends LivingEntity> void m_41622_(int p_41623_, T p_41624_, Consumer<T> p_41625_) {
      if (!p_41624_.f_19853_.f_46443_ && (!(p_41624_ instanceof Player) || !((Player)p_41624_).m_150110_().f_35937_)) {
         if (this.m_41763_()) {
            p_41623_ = this.m_41720_().damageItem(this, p_41623_, p_41624_, p_41625_);
            if (this.m_41629_(p_41623_, p_41624_.m_21187_(), p_41624_ instanceof ServerPlayer ? (ServerPlayer)p_41624_ : null)) {
               p_41625_.accept(p_41624_);
               Item item = this.m_41720_();
               this.m_41774_(1);
               if (p_41624_ instanceof Player) {
                  ((Player)p_41624_).m_36246_(Stats.f_12983_.m_12902_(item));
               }

               this.m_41721_(0);
            }

         }
      }
   }

   public boolean m_150947_() {
      return this.f_41589_.m_142522_(this);
   }

   public int m_150948_() {
      return this.f_41589_.m_142158_(this);
   }

   public int m_150949_() {
      return this.f_41589_.m_142159_(this);
   }

   public boolean m_150926_(Slot p_150927_, ClickAction p_150928_, Player p_150929_) {
      return this.m_41720_().m_142207_(this, p_150927_, p_150928_, p_150929_);
   }

   public boolean m_150932_(ItemStack p_150933_, Slot p_150934_, ClickAction p_150935_, Player p_150936_, SlotAccess p_150937_) {
      return this.m_41720_().m_142305_(this, p_150933_, p_150934_, p_150935_, p_150936_, p_150937_);
   }

   public void m_41640_(LivingEntity p_41641_, Player p_41642_) {
      Item item = this.m_41720_();
      if (item.m_7579_(this, p_41641_, p_41642_)) {
         p_41642_.m_36246_(Stats.f_12982_.m_12902_(item));
      }

   }

   public void m_41686_(Level p_41687_, BlockState p_41688_, BlockPos p_41689_, Player p_41690_) {
      Item item = this.m_41720_();
      if (item.m_6813_(this, p_41687_, p_41688_, p_41689_, p_41690_)) {
         p_41690_.m_36246_(Stats.f_12982_.m_12902_(item));
      }

   }

   public boolean m_41735_(BlockState p_41736_) {
      return this.m_41720_().isCorrectToolForDrops(this, p_41736_);
   }

   public InteractionResult m_41647_(Player p_41648_, LivingEntity p_41649_, InteractionHand p_41650_) {
      return this.m_41720_().m_6880_(this, p_41648_, p_41649_, p_41650_);
   }

   public ItemStack m_41777_() {
      if (this.m_41619_()) {
         return f_41583_;
      } else {
         ItemStack itemstack = new ItemStack(this.m_41720_(), this.f_41587_, this.serializeCaps());
         itemstack.m_41754_(this.m_41612_());
         if (this.f_41590_ != null) {
            itemstack.f_41590_ = this.f_41590_.m_6426_();
         }

         return itemstack;
      }
   }

   public static boolean m_41658_(ItemStack p_41659_, ItemStack p_41660_) {
      if (p_41659_.m_41619_() && p_41660_.m_41619_()) {
         return true;
      } else if (!p_41659_.m_41619_() && !p_41660_.m_41619_()) {
         if (p_41659_.f_41590_ == null && p_41660_.f_41590_ != null) {
            return false;
         } else {
            return (p_41659_.f_41590_ == null || p_41659_.f_41590_.equals(p_41660_.f_41590_)) && p_41659_.areCapsCompatible(p_41660_);
         }
      } else {
         return false;
      }
   }

   public static boolean m_41728_(ItemStack p_41729_, ItemStack p_41730_) {
      if (p_41729_.m_41619_() && p_41730_.m_41619_()) {
         return true;
      } else {
         return !p_41729_.m_41619_() && !p_41730_.m_41619_() ? p_41729_.m_41744_(p_41730_) : false;
      }
   }

   private boolean m_41744_(ItemStack p_41745_) {
      if (this.f_41587_ != p_41745_.f_41587_) {
         return false;
      } else if (!this.m_150930_(p_41745_.m_41720_())) {
         return false;
      } else if (this.f_41590_ == null && p_41745_.f_41590_ != null) {
         return false;
      } else {
         return (this.f_41590_ == null || this.f_41590_.equals(p_41745_.f_41590_)) && this.areCapsCompatible(p_41745_);
      }
   }

   public static boolean m_41746_(ItemStack p_41747_, ItemStack p_41748_) {
      if (p_41747_ == p_41748_) {
         return true;
      } else {
         return !p_41747_.m_41619_() && !p_41748_.m_41619_() ? p_41747_.m_41656_(p_41748_) : false;
      }
   }

   public static boolean m_41758_(ItemStack p_41759_, ItemStack p_41760_) {
      if (p_41759_ == p_41760_) {
         return true;
      } else {
         return !p_41759_.m_41619_() && !p_41760_.m_41619_() ? p_41759_.m_41726_(p_41760_) : false;
      }
   }

   public boolean m_41656_(ItemStack p_41657_) {
      return !p_41657_.m_41619_() && this.m_150930_(p_41657_.m_41720_());
   }

   public boolean m_41726_(ItemStack p_41727_) {
      if (!this.m_41763_()) {
         return this.m_41656_(p_41727_);
      } else {
         return !p_41727_.m_41619_() && this.m_150930_(p_41727_.m_41720_());
      }
   }

   public static boolean m_150942_(ItemStack p_150943_, ItemStack p_150944_) {
      return p_150943_.m_150930_(p_150944_.m_41720_()) && m_41658_(p_150943_, p_150944_);
   }

   public String m_41778_() {
      return this.m_41720_().m_5671_(this);
   }

   public String toString() {
      return this.f_41587_ + " " + this.m_41720_();
   }

   public void m_41666_(Level p_41667_, Entity p_41668_, int p_41669_, boolean p_41670_) {
      if (this.f_41588_ > 0) {
         --this.f_41588_;
      }

      if (this.m_41720_() != null) {
         this.m_41720_().m_6883_(this, p_41667_, p_41668_, p_41669_, p_41670_);
      }

   }

   public void m_41678_(Level p_41679_, Player p_41680_, int p_41681_) {
      p_41680_.m_6278_(Stats.f_12981_.m_12902_(this.m_41720_()), p_41681_);
      this.m_41720_().m_7836_(this, p_41679_, p_41680_);
   }

   public int m_41779_() {
      return this.m_41720_().m_8105_(this);
   }

   public UseAnim m_41780_() {
      return this.m_41720_().m_6164_(this);
   }

   public void m_41674_(Level p_41675_, LivingEntity p_41676_, int p_41677_) {
      this.m_41720_().m_5551_(this, p_41675_, p_41676_, p_41677_);
   }

   public boolean m_41781_() {
      return this.m_41720_().m_41463_(this);
   }

   public boolean m_41782_() {
      return !this.f_41591_ && this.f_41590_ != null && !this.f_41590_.m_128456_();
   }

   @Nullable
   public CompoundTag m_41783_() {
      return this.f_41590_;
   }

   public CompoundTag m_41784_() {
      if (this.f_41590_ == null) {
         this.m_41751_(new CompoundTag());
      }

      return this.f_41590_;
   }

   public CompoundTag m_41698_(String p_41699_) {
      if (this.f_41590_ != null && this.f_41590_.m_128425_(p_41699_, 10)) {
         return this.f_41590_.m_128469_(p_41699_);
      } else {
         CompoundTag compoundtag = new CompoundTag();
         this.m_41700_(p_41699_, compoundtag);
         return compoundtag;
      }
   }

   @Nullable
   public CompoundTag m_41737_(String p_41738_) {
      return this.f_41590_ != null && this.f_41590_.m_128425_(p_41738_, 10) ? this.f_41590_.m_128469_(p_41738_) : null;
   }

   public void m_41749_(String p_41750_) {
      if (this.f_41590_ != null && this.f_41590_.m_128441_(p_41750_)) {
         this.f_41590_.m_128473_(p_41750_);
         if (this.f_41590_.m_128456_()) {
            this.f_41590_ = null;
         }
      }

   }

   public ListTag m_41785_() {
      return this.f_41590_ != null ? this.f_41590_.m_128437_("Enchantments", 10) : new ListTag();
   }

   public void m_41751_(@Nullable CompoundTag p_41752_) {
      this.f_41590_ = p_41752_;
      if (this.m_41720_().isDamageable(this)) {
         this.m_41721_(this.m_41773_());
      }

      if (p_41752_ != null) {
         this.m_41720_().m_142312_(p_41752_);
      }

   }

   public Component m_41786_() {
      CompoundTag compoundtag = this.m_41737_("display");
      if (compoundtag != null && compoundtag.m_128425_("Name", 8)) {
         try {
            Component component = Component.Serializer.m_130701_(compoundtag.m_128461_("Name"));
            if (component != null) {
               return component;
            }

            compoundtag.m_128473_("Name");
         } catch (JsonParseException jsonparseexception) {
            compoundtag.m_128473_("Name");
         }
      }

      return this.m_41720_().m_7626_(this);
   }

   public ItemStack m_41714_(@Nullable Component p_41715_) {
      CompoundTag compoundtag = this.m_41698_("display");
      if (p_41715_ != null) {
         compoundtag.m_128359_("Name", Component.Serializer.m_130703_(p_41715_));
      } else {
         compoundtag.m_128473_("Name");
      }

      return this;
   }

   public void m_41787_() {
      CompoundTag compoundtag = this.m_41737_("display");
      if (compoundtag != null) {
         compoundtag.m_128473_("Name");
         if (compoundtag.m_128456_()) {
            this.m_41749_("display");
         }
      }

      if (this.f_41590_ != null && this.f_41590_.m_128456_()) {
         this.f_41590_ = null;
      }

   }

   public boolean m_41788_() {
      CompoundTag compoundtag = this.m_41737_("display");
      return compoundtag != null && compoundtag.m_128425_("Name", 8);
   }

   public List<Component> m_41651_(@Nullable Player p_41652_, TooltipFlag p_41653_) {
      List<Component> list = Lists.newArrayList();
      MutableComponent mutablecomponent = (new TextComponent("")).m_7220_(this.m_41786_()).m_130940_(this.m_41791_().f_43022_);
      if (this.m_41788_()) {
         mutablecomponent.m_130940_(ChatFormatting.ITALIC);
      }

      list.add(mutablecomponent);
      if (!p_41653_.m_7050_() && !this.m_41788_() && this.m_150930_(Items.f_42573_)) {
         Integer integer = MapItem.m_151131_(this);
         if (integer != null) {
            list.add((new TextComponent("#" + integer)).m_130940_(ChatFormatting.GRAY));
         }
      }

      int j = this.m_41618_();
      if (m_41626_(j, ItemStack.TooltipPart.ADDITIONAL)) {
         this.m_41720_().m_7373_(this, p_41652_ == null ? null : p_41652_.f_19853_, list, p_41653_);
      }

      if (this.m_41782_()) {
         if (m_41626_(j, ItemStack.TooltipPart.ENCHANTMENTS)) {
            m_41709_(list, this.m_41785_());
         }

         if (this.f_41590_.m_128425_("display", 10)) {
            CompoundTag compoundtag = this.f_41590_.m_128469_("display");
            if (m_41626_(j, ItemStack.TooltipPart.DYE) && compoundtag.m_128425_("color", 99)) {
               if (p_41653_.m_7050_()) {
                  list.add((new TranslatableComponent("item.color", String.format("#%06X", compoundtag.m_128451_("color")))).m_130940_(ChatFormatting.GRAY));
               } else {
                  list.add((new TranslatableComponent("item.dyed")).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
               }
            }

            if (compoundtag.m_128435_("Lore") == 9) {
               ListTag listtag = compoundtag.m_128437_("Lore", 8);

               for(int i = 0; i < listtag.size(); ++i) {
                  String s = listtag.m_128778_(i);

                  try {
                     MutableComponent mutablecomponent1 = Component.Serializer.m_130701_(s);
                     if (mutablecomponent1 != null) {
                        list.add(ComponentUtils.m_130750_(mutablecomponent1, f_41586_));
                     }
                  } catch (JsonParseException jsonparseexception) {
                     compoundtag.m_128473_("Lore");
                  }
               }
            }
         }
      }

      if (m_41626_(j, ItemStack.TooltipPart.MODIFIERS)) {
         for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            Multimap<Attribute, AttributeModifier> multimap = this.m_41638_(equipmentslot);
            if (!multimap.isEmpty()) {
               list.add(TextComponent.f_131282_);
               list.add((new TranslatableComponent("item.modifiers." + equipmentslot.m_20751_())).m_130940_(ChatFormatting.GRAY));

               for(Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  double d0 = attributemodifier.m_22218_();
                  boolean flag = false;
                  if (p_41652_ != null) {
                     if (attributemodifier.m_22209_() == Item.f_41374_) {
                        d0 = d0 + p_41652_.m_21172_(Attributes.f_22281_);
                        d0 = d0 + (double)EnchantmentHelper.m_44833_(this, MobType.f_21640_);
                        flag = true;
                     } else if (attributemodifier.m_22209_() == Item.f_41375_) {
                        d0 += p_41652_.m_21172_(Attributes.f_22283_);
                        flag = true;
                     }
                  }

                  double d1;
                  if (attributemodifier.m_22217_() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.m_22217_() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                     if (entry.getKey().equals(Attributes.f_22278_)) {
                        d1 = d0 * 10.0D;
                     } else {
                        d1 = d0;
                     }
                  } else {
                     d1 = d0 * 100.0D;
                  }

                  if (flag) {
                     list.add((new TextComponent(" ")).m_7220_(new TranslatableComponent("attribute.modifier.equals." + attributemodifier.m_22217_().m_22235_(), f_41584_.format(d1), new TranslatableComponent(entry.getKey().m_22087_()))).m_130940_(ChatFormatting.DARK_GREEN));
                  } else if (d0 > 0.0D) {
                     list.add((new TranslatableComponent("attribute.modifier.plus." + attributemodifier.m_22217_().m_22235_(), f_41584_.format(d1), new TranslatableComponent(entry.getKey().m_22087_()))).m_130940_(ChatFormatting.BLUE));
                  } else if (d0 < 0.0D) {
                     d1 = d1 * -1.0D;
                     list.add((new TranslatableComponent("attribute.modifier.take." + attributemodifier.m_22217_().m_22235_(), f_41584_.format(d1), new TranslatableComponent(entry.getKey().m_22087_()))).m_130940_(ChatFormatting.RED));
                  }
               }
            }
         }
      }

      if (this.m_41782_()) {
         if (m_41626_(j, ItemStack.TooltipPart.UNBREAKABLE) && this.f_41590_.m_128471_("Unbreakable")) {
            list.add((new TranslatableComponent("item.unbreakable")).m_130940_(ChatFormatting.BLUE));
         }

         if (m_41626_(j, ItemStack.TooltipPart.CAN_DESTROY) && this.f_41590_.m_128425_("CanDestroy", 9)) {
            ListTag listtag1 = this.f_41590_.m_128437_("CanDestroy", 8);
            if (!listtag1.isEmpty()) {
               list.add(TextComponent.f_131282_);
               list.add((new TranslatableComponent("item.canBreak")).m_130940_(ChatFormatting.GRAY));

               for(int k = 0; k < listtag1.size(); ++k) {
                  list.addAll(m_41761_(listtag1.m_128778_(k)));
               }
            }
         }

         if (m_41626_(j, ItemStack.TooltipPart.CAN_PLACE) && this.f_41590_.m_128425_("CanPlaceOn", 9)) {
            ListTag listtag2 = this.f_41590_.m_128437_("CanPlaceOn", 8);
            if (!listtag2.isEmpty()) {
               list.add(TextComponent.f_131282_);
               list.add((new TranslatableComponent("item.canPlace")).m_130940_(ChatFormatting.GRAY));

               for(int l = 0; l < listtag2.size(); ++l) {
                  list.addAll(m_41761_(listtag2.m_128778_(l)));
               }
            }
         }
      }

      if (p_41653_.m_7050_()) {
         if (this.m_41768_()) {
            list.add(new TranslatableComponent("item.durability", this.m_41776_() - this.m_41773_(), this.m_41776_()));
         }

         list.add((new TextComponent(Registry.f_122827_.m_7981_(this.m_41720_()).toString())).m_130940_(ChatFormatting.DARK_GRAY));
         if (this.m_41782_()) {
            list.add((new TranslatableComponent("item.nbt_tags", this.f_41590_.m_128431_().size())).m_130940_(ChatFormatting.DARK_GRAY));
         }
      }

      net.minecraftforge.event.ForgeEventFactory.onItemTooltip(this, p_41652_, list, p_41653_);
      return list;
   }

   private static boolean m_41626_(int p_41627_, ItemStack.TooltipPart p_41628_) {
      return (p_41627_ & p_41628_.m_41809_()) == 0;
   }

   private int m_41618_() {
      return this.m_41782_() && this.f_41590_.m_128425_("HideFlags", 99) ? this.f_41590_.m_128451_("HideFlags") : 0;
   }

   public void m_41654_(ItemStack.TooltipPart p_41655_) {
      CompoundTag compoundtag = this.m_41784_();
      compoundtag.m_128405_("HideFlags", compoundtag.m_128451_("HideFlags") | p_41655_.m_41809_());
   }

   public static void m_41709_(List<Component> p_41710_, ListTag p_41711_) {
      for(int i = 0; i < p_41711_.size(); ++i) {
         CompoundTag compoundtag = p_41711_.m_128728_(i);
         Registry.f_122825_.m_6612_(EnchantmentHelper.m_182446_(compoundtag)).ifPresent((p_41708_) -> {
            p_41710_.add(p_41708_.m_44700_(EnchantmentHelper.m_182438_(compoundtag)));
         });
      }

   }

   private static Collection<Component> m_41761_(String p_41762_) {
      try {
         BlockStateParser blockstateparser = (new BlockStateParser(new StringReader(p_41762_), true)).m_116806_(true);
         BlockState blockstate = blockstateparser.m_116808_();
         ResourceLocation resourcelocation = blockstateparser.m_116822_();
         boolean flag = blockstate != null;
         boolean flag1 = resourcelocation != null;
         if (flag || flag1) {
            if (flag) {
               return Lists.newArrayList(blockstate.m_60734_().m_49954_().m_130940_(ChatFormatting.DARK_GRAY));
            }

            Tag<Block> tag = BlockTags.m_13115_().m_13404_(resourcelocation);
            if (tag != null) {
               Collection<Block> collection = tag.m_6497_();
               if (!collection.isEmpty()) {
                  return collection.stream().map(Block::m_49954_).map((p_41717_) -> {
                     return p_41717_.m_130940_(ChatFormatting.DARK_GRAY);
                  }).collect(Collectors.toList());
               }
            }
         }
      } catch (CommandSyntaxException commandsyntaxexception) {
      }

      return Lists.newArrayList((new TextComponent("missingno")).m_130940_(ChatFormatting.DARK_GRAY));
   }

   public boolean m_41790_() {
      return this.m_41720_().m_5812_(this);
   }

   public Rarity m_41791_() {
      return this.m_41720_().m_41460_(this);
   }

   public boolean m_41792_() {
      if (!this.m_41720_().m_8120_(this)) {
         return false;
      } else {
         return !this.m_41793_();
      }
   }

   public void m_41663_(Enchantment p_41664_, int p_41665_) {
      this.m_41784_();
      if (!this.f_41590_.m_128425_("Enchantments", 9)) {
         this.f_41590_.m_128365_("Enchantments", new ListTag());
      }

      ListTag listtag = this.f_41590_.m_128437_("Enchantments", 10);
      listtag.add(EnchantmentHelper.m_182443_(EnchantmentHelper.m_182432_(p_41664_), (byte)p_41665_));
   }

   public boolean m_41793_() {
      if (this.f_41590_ != null && this.f_41590_.m_128425_("Enchantments", 9)) {
         return !this.f_41590_.m_128437_("Enchantments", 10).isEmpty();
      } else {
         return false;
      }
   }

   public void m_41700_(String p_41701_, net.minecraft.nbt.Tag p_41702_) {
      this.m_41784_().m_128365_(p_41701_, p_41702_);
   }

   public boolean m_41794_() {
      return this.f_41592_ instanceof ItemFrame;
   }

   public void m_41636_(@Nullable Entity p_41637_) {
      this.f_41592_ = p_41637_;
   }

   @Nullable
   public ItemFrame m_41795_() {
      return this.f_41592_ instanceof ItemFrame ? (ItemFrame)this.m_41609_() : null;
   }

   @Nullable
   public Entity m_41609_() {
      return !this.f_41591_ ? this.f_41592_ : null;
   }

   public int m_41610_() {
      return this.m_41782_() && this.f_41590_.m_128425_("RepairCost", 3) ? this.f_41590_.m_128451_("RepairCost") : 0;
   }

   public void m_41742_(int p_41743_) {
      this.m_41784_().m_128405_("RepairCost", p_41743_);
   }

   public Multimap<Attribute, AttributeModifier> m_41638_(EquipmentSlot p_41639_) {
      Multimap<Attribute, AttributeModifier> multimap;
      if (this.m_41782_() && this.f_41590_.m_128425_("AttributeModifiers", 9)) {
         multimap = HashMultimap.create();
         ListTag listtag = this.f_41590_.m_128437_("AttributeModifiers", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            if (!compoundtag.m_128425_("Slot", 8) || compoundtag.m_128461_("Slot").equals(p_41639_.m_20751_())) {
               Optional<Attribute> optional = Registry.f_122866_.m_6612_(ResourceLocation.m_135820_(compoundtag.m_128461_("AttributeName")));
               if (optional.isPresent()) {
                  AttributeModifier attributemodifier = AttributeModifier.m_22212_(compoundtag);
                  if (attributemodifier != null && attributemodifier.m_22209_().getLeastSignificantBits() != 0L && attributemodifier.m_22209_().getMostSignificantBits() != 0L) {
                     multimap.put(optional.get(), attributemodifier);
                  }
               }
            }
         }
      } else {
         multimap = this.m_41720_().getAttributeModifiers(p_41639_, this);
      }

      multimap = net.minecraftforge.common.ForgeHooks.getAttributeModifiers(this, p_41639_, multimap);
      return multimap;
   }

   public void m_41643_(Attribute p_41644_, AttributeModifier p_41645_, @Nullable EquipmentSlot p_41646_) {
      this.m_41784_();
      if (!this.f_41590_.m_128425_("AttributeModifiers", 9)) {
         this.f_41590_.m_128365_("AttributeModifiers", new ListTag());
      }

      ListTag listtag = this.f_41590_.m_128437_("AttributeModifiers", 10);
      CompoundTag compoundtag = p_41645_.m_22219_();
      compoundtag.m_128359_("AttributeName", Registry.f_122866_.m_7981_(p_41644_).toString());
      if (p_41646_ != null) {
         compoundtag.m_128359_("Slot", p_41646_.m_20751_());
      }

      listtag.add(compoundtag);
   }

   public Component m_41611_() {
      MutableComponent mutablecomponent = (new TextComponent("")).m_7220_(this.m_41786_());
      if (this.m_41788_()) {
         mutablecomponent.m_130940_(ChatFormatting.ITALIC);
      }

      MutableComponent mutablecomponent1 = ComponentUtils.m_130748_(mutablecomponent);
      if (!this.f_41591_) {
         mutablecomponent1.m_130940_(this.m_41791_().f_43022_).m_130938_((p_41719_) -> {
            return p_41719_.m_131144_(new HoverEvent(HoverEvent.Action.f_130832_, new HoverEvent.ItemStackInfo(this)));
         });
      }

      return mutablecomponent1;
   }

   private static boolean m_41693_(BlockInWorld p_41694_, @Nullable BlockInWorld p_41695_) {
      if (p_41695_ != null && p_41694_.m_61168_() == p_41695_.m_61168_()) {
         if (p_41694_.m_61174_() == null && p_41695_.m_61174_() == null) {
            return true;
         } else {
            return p_41694_.m_61174_() != null && p_41695_.m_61174_() != null ? Objects.equals(p_41694_.m_61174_().m_6945_(new CompoundTag()), p_41695_.m_61174_().m_6945_(new CompoundTag())) : false;
         }
      } else {
         return false;
      }
   }

   public boolean m_41633_(TagContainer p_41634_, BlockInWorld p_41635_) {
      if (m_41693_(p_41635_, this.f_41593_)) {
         return this.f_41594_;
      } else {
         this.f_41593_ = p_41635_;
         if (this.m_41782_() && this.f_41590_.m_128425_("CanDestroy", 9)) {
            ListTag listtag = this.f_41590_.m_128437_("CanDestroy", 8);

            for(int i = 0; i < listtag.size(); ++i) {
               String s = listtag.m_128778_(i);

               try {
                  Predicate<BlockInWorld> predicate = BlockPredicateArgument.m_115570_().parse(new StringReader(s)).m_115602_(p_41634_);
                  if (predicate.test(p_41635_)) {
                     this.f_41594_ = true;
                     return true;
                  }
               } catch (CommandSyntaxException commandsyntaxexception) {
               }
            }
         }

         this.f_41594_ = false;
         return false;
      }
   }

   public boolean m_41723_(TagContainer p_41724_, BlockInWorld p_41725_) {
      if (m_41693_(p_41725_, this.f_41595_)) {
         return this.f_41596_;
      } else {
         this.f_41595_ = p_41725_;
         if (this.m_41782_() && this.f_41590_.m_128425_("CanPlaceOn", 9)) {
            ListTag listtag = this.f_41590_.m_128437_("CanPlaceOn", 8);

            for(int i = 0; i < listtag.size(); ++i) {
               String s = listtag.m_128778_(i);

               try {
                  Predicate<BlockInWorld> predicate = BlockPredicateArgument.m_115570_().parse(new StringReader(s)).m_115602_(p_41724_);
                  if (predicate.test(p_41725_)) {
                     this.f_41596_ = true;
                     return true;
                  }
               } catch (CommandSyntaxException commandsyntaxexception) {
               }
            }
         }

         this.f_41596_ = false;
         return false;
      }
   }

   public int m_41612_() {
      return this.f_41588_;
   }

   public void m_41754_(int p_41755_) {
      this.f_41588_ = p_41755_;
   }

   public int m_41613_() {
      return this.f_41591_ ? 0 : this.f_41587_;
   }

   public void m_41764_(int p_41765_) {
      this.f_41587_ = p_41765_;
      this.m_41617_();
   }

   public void m_41769_(int p_41770_) {
      this.m_41764_(this.f_41587_ + p_41770_);
   }

   public void m_41774_(int p_41775_) {
      this.m_41769_(-p_41775_);
   }

   public void m_41731_(Level p_41732_, LivingEntity p_41733_, int p_41734_) {
      this.m_41720_().m_5929_(p_41732_, p_41733_, this, p_41734_);
   }

   public void m_150924_(ItemEntity p_150925_) {
      this.m_41720_().m_142023_(p_150925_);
   }

   public boolean m_41614_() {
      return this.m_41720_().m_41472_();
   }

   // FORGE START
   public void deserializeNBT(CompoundTag nbt) {
      final ItemStack itemStack = ItemStack.m_41712_(nbt);
      this.m_41751_(itemStack.m_41783_());
      if (itemStack.capNBT != null) deserializeCaps(itemStack.capNBT);
   }

   /**
    * Set up forge's ItemStack additions.
    */
   private void forgeInit() {
      if (this.delegate != null) {
         this.gatherCapabilities(() -> f_41589_.initCapabilities(this, this.capNBT));
         if (this.capNBT != null) deserializeCaps(this.capNBT);
      }
   }

   public SoundEvent m_41615_() {
      return this.m_41720_().m_6023_();
   }

   public SoundEvent m_41616_() {
      return this.m_41720_().m_6061_();
   }

   @Nullable
   public SoundEvent m_150920_() {
      return this.m_41720_().m_142602_();
   }

   public static enum TooltipPart {
      ENCHANTMENTS,
      MODIFIERS,
      UNBREAKABLE,
      CAN_DESTROY,
      CAN_PLACE,
      ADDITIONAL,
      DYE;

      private final int f_41803_ = 1 << this.ordinal();

      public int m_41809_() {
         return this.f_41803_;
      }
   }
}
