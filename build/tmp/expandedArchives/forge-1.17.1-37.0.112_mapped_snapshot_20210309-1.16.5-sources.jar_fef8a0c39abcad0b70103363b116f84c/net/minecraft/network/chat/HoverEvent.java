package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoverEvent {
   static final Logger f_130813_ = LogManager.getLogger();
   private final HoverEvent.Action<?> f_130814_;
   private final Object f_130815_;

   public <T> HoverEvent(HoverEvent.Action<T> p_130818_, T p_130819_) {
      this.f_130814_ = p_130818_;
      this.f_130815_ = p_130819_;
   }

   public HoverEvent.Action<?> m_130820_() {
      return this.f_130814_;
   }

   @Nullable
   public <T> T m_130823_(HoverEvent.Action<T> p_130824_) {
      return (T)(this.f_130814_ == p_130824_ ? p_130824_.m_130864_(this.f_130815_) : null);
   }

   public boolean equals(Object p_130828_) {
      if (this == p_130828_) {
         return true;
      } else if (p_130828_ != null && this.getClass() == p_130828_.getClass()) {
         HoverEvent hoverevent = (HoverEvent)p_130828_;
         return this.f_130814_ == hoverevent.f_130814_ && Objects.equals(this.f_130815_, hoverevent.f_130815_);
      } else {
         return false;
      }
   }

   public String toString() {
      return "HoverEvent{action=" + this.f_130814_ + ", value='" + this.f_130815_ + "'}";
   }

   public int hashCode() {
      int i = this.f_130814_.hashCode();
      return 31 * i + (this.f_130815_ != null ? this.f_130815_.hashCode() : 0);
   }

   @Nullable
   public static HoverEvent m_130821_(JsonObject p_130822_) {
      String s = GsonHelper.m_13851_(p_130822_, "action", (String)null);
      if (s == null) {
         return null;
      } else {
         HoverEvent.Action<?> action = HoverEvent.Action.m_130852_(s);
         if (action == null) {
            return null;
         } else {
            JsonElement jsonelement = p_130822_.get("contents");
            if (jsonelement != null) {
               return action.m_130848_(jsonelement);
            } else {
               Component component = Component.Serializer.m_130691_(p_130822_.get("value"));
               return component != null ? action.m_130854_(component) : null;
            }
         }
      }
   }

   public JsonObject m_130825_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("action", this.f_130814_.m_130861_());
      jsonobject.add("contents", this.f_130814_.m_130850_(this.f_130815_));
      return jsonobject;
   }

   public static class Action<T> {
      public static final HoverEvent.Action<Component> f_130831_ = new HoverEvent.Action<>("show_text", true, Component.Serializer::m_130691_, Component.Serializer::m_130716_, Function.identity());
      public static final HoverEvent.Action<HoverEvent.ItemStackInfo> f_130832_ = new HoverEvent.Action<>("show_item", true, HoverEvent.ItemStackInfo::m_130906_, HoverEvent.ItemStackInfo::m_130905_, HoverEvent.ItemStackInfo::m_130908_);
      public static final HoverEvent.Action<HoverEvent.EntityTooltipInfo> f_130833_ = new HoverEvent.Action<>("show_entity", true, HoverEvent.EntityTooltipInfo::m_130880_, HoverEvent.EntityTooltipInfo::m_130879_, HoverEvent.EntityTooltipInfo::m_130882_);
      private static final Map<String, HoverEvent.Action<?>> f_130834_ = Stream.of(f_130831_, f_130832_, f_130833_).collect(ImmutableMap.toImmutableMap(HoverEvent.Action::m_130861_, (p_178444_) -> {
         return p_178444_;
      }));
      private final String f_130835_;
      private final boolean f_130836_;
      private final Function<JsonElement, T> f_130837_;
      private final Function<T, JsonElement> f_130838_;
      private final Function<Component, T> f_130839_;

      public Action(String p_130842_, boolean p_130843_, Function<JsonElement, T> p_130844_, Function<T, JsonElement> p_130845_, Function<Component, T> p_130846_) {
         this.f_130835_ = p_130842_;
         this.f_130836_ = p_130843_;
         this.f_130837_ = p_130844_;
         this.f_130838_ = p_130845_;
         this.f_130839_ = p_130846_;
      }

      public boolean m_130847_() {
         return this.f_130836_;
      }

      public String m_130861_() {
         return this.f_130835_;
      }

      @Nullable
      public static HoverEvent.Action<?> m_130852_(String p_130853_) {
         return f_130834_.get(p_130853_);
      }

      T m_130864_(Object p_130865_) {
         return (T)p_130865_;
      }

      @Nullable
      public HoverEvent m_130848_(JsonElement p_130849_) {
         T t = this.f_130837_.apply(p_130849_);
         return t == null ? null : new HoverEvent(this, t);
      }

      @Nullable
      public HoverEvent m_130854_(Component p_130855_) {
         T t = this.f_130839_.apply(p_130855_);
         return t == null ? null : new HoverEvent(this, t);
      }

      public JsonElement m_130850_(Object p_130851_) {
         return this.f_130838_.apply(this.m_130864_(p_130851_));
      }

      public String toString() {
         return "<action " + this.f_130835_ + ">";
      }
   }

   public static class EntityTooltipInfo {
      public final EntityType<?> f_130871_;
      public final UUID f_130872_;
      @Nullable
      public final Component f_130873_;
      @Nullable
      private List<Component> f_130874_;

      public EntityTooltipInfo(EntityType<?> p_130876_, UUID p_130877_, @Nullable Component p_130878_) {
         this.f_130871_ = p_130876_;
         this.f_130872_ = p_130877_;
         this.f_130873_ = p_130878_;
      }

      @Nullable
      public static HoverEvent.EntityTooltipInfo m_130880_(JsonElement p_130881_) {
         if (!p_130881_.isJsonObject()) {
            return null;
         } else {
            JsonObject jsonobject = p_130881_.getAsJsonObject();
            EntityType<?> entitytype = Registry.f_122826_.m_7745_(new ResourceLocation(GsonHelper.m_13906_(jsonobject, "type")));
            UUID uuid = UUID.fromString(GsonHelper.m_13906_(jsonobject, "id"));
            Component component = Component.Serializer.m_130691_(jsonobject.get("name"));
            return new HoverEvent.EntityTooltipInfo(entitytype, uuid, component);
         }
      }

      @Nullable
      public static HoverEvent.EntityTooltipInfo m_130882_(Component p_130883_) {
         try {
            CompoundTag compoundtag = TagParser.m_129359_(p_130883_.getString());
            Component component = Component.Serializer.m_130701_(compoundtag.m_128461_("name"));
            EntityType<?> entitytype = Registry.f_122826_.m_7745_(new ResourceLocation(compoundtag.m_128461_("type")));
            UUID uuid = UUID.fromString(compoundtag.m_128461_("id"));
            return new HoverEvent.EntityTooltipInfo(entitytype, uuid, component);
         } catch (CommandSyntaxException | JsonSyntaxException jsonsyntaxexception) {
            return null;
         }
      }

      public JsonElement m_130879_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("type", Registry.f_122826_.m_7981_(this.f_130871_).toString());
         jsonobject.addProperty("id", this.f_130872_.toString());
         if (this.f_130873_ != null) {
            jsonobject.add("name", Component.Serializer.m_130716_(this.f_130873_));
         }

         return jsonobject;
      }

      public List<Component> m_130884_() {
         if (this.f_130874_ == null) {
            this.f_130874_ = Lists.newArrayList();
            if (this.f_130873_ != null) {
               this.f_130874_.add(this.f_130873_);
            }

            this.f_130874_.add(new TranslatableComponent("gui.entity_tooltip.type", this.f_130871_.m_20676_()));
            this.f_130874_.add(new TextComponent(this.f_130872_.toString()));
         }

         return this.f_130874_;
      }

      public boolean equals(Object p_130886_) {
         if (this == p_130886_) {
            return true;
         } else if (p_130886_ != null && this.getClass() == p_130886_.getClass()) {
            HoverEvent.EntityTooltipInfo hoverevent$entitytooltipinfo = (HoverEvent.EntityTooltipInfo)p_130886_;
            return this.f_130871_.equals(hoverevent$entitytooltipinfo.f_130871_) && this.f_130872_.equals(hoverevent$entitytooltipinfo.f_130872_) && Objects.equals(this.f_130873_, hoverevent$entitytooltipinfo.f_130873_);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.f_130871_.hashCode();
         i = 31 * i + this.f_130872_.hashCode();
         return 31 * i + (this.f_130873_ != null ? this.f_130873_.hashCode() : 0);
      }
   }

   public static class ItemStackInfo {
      private final Item f_130888_;
      private final int f_130889_;
      @Nullable
      private final CompoundTag f_130890_;
      @Nullable
      private ItemStack f_130891_;

      ItemStackInfo(Item p_130893_, int p_130894_, @Nullable CompoundTag p_130895_) {
         this.f_130888_ = p_130893_;
         this.f_130889_ = p_130894_;
         this.f_130890_ = p_130895_;
      }

      public ItemStackInfo(ItemStack p_130897_) {
         this(p_130897_.m_41720_(), p_130897_.m_41613_(), p_130897_.m_41783_() != null ? p_130897_.m_41783_().m_6426_() : null);
      }

      public boolean equals(Object p_130911_) {
         if (this == p_130911_) {
            return true;
         } else if (p_130911_ != null && this.getClass() == p_130911_.getClass()) {
            HoverEvent.ItemStackInfo hoverevent$itemstackinfo = (HoverEvent.ItemStackInfo)p_130911_;
            return this.f_130889_ == hoverevent$itemstackinfo.f_130889_ && this.f_130888_.equals(hoverevent$itemstackinfo.f_130888_) && Objects.equals(this.f_130890_, hoverevent$itemstackinfo.f_130890_);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.f_130888_.hashCode();
         i = 31 * i + this.f_130889_;
         return 31 * i + (this.f_130890_ != null ? this.f_130890_.hashCode() : 0);
      }

      public ItemStack m_130898_() {
         if (this.f_130891_ == null) {
            this.f_130891_ = new ItemStack(this.f_130888_, this.f_130889_);
            if (this.f_130890_ != null) {
               this.f_130891_.m_41751_(this.f_130890_);
            }
         }

         return this.f_130891_;
      }

      private static HoverEvent.ItemStackInfo m_130906_(JsonElement p_130907_) {
         if (p_130907_.isJsonPrimitive()) {
            return new HoverEvent.ItemStackInfo(Registry.f_122827_.m_7745_(new ResourceLocation(p_130907_.getAsString())), 1, (CompoundTag)null);
         } else {
            JsonObject jsonobject = GsonHelper.m_13918_(p_130907_, "item");
            Item item = Registry.f_122827_.m_7745_(new ResourceLocation(GsonHelper.m_13906_(jsonobject, "id")));
            int i = GsonHelper.m_13824_(jsonobject, "count", 1);
            if (jsonobject.has("tag")) {
               String s = GsonHelper.m_13906_(jsonobject, "tag");

               try {
                  CompoundTag compoundtag = TagParser.m_129359_(s);
                  return new HoverEvent.ItemStackInfo(item, i, compoundtag);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  HoverEvent.f_130813_.warn("Failed to parse tag: {}", s, commandsyntaxexception);
               }
            }

            return new HoverEvent.ItemStackInfo(item, i, (CompoundTag)null);
         }
      }

      @Nullable
      private static HoverEvent.ItemStackInfo m_130908_(Component p_130909_) {
         try {
            CompoundTag compoundtag = TagParser.m_129359_(p_130909_.getString());
            return new HoverEvent.ItemStackInfo(ItemStack.m_41712_(compoundtag));
         } catch (CommandSyntaxException commandsyntaxexception) {
            HoverEvent.f_130813_.warn("Failed to parse item tag: {}", p_130909_, commandsyntaxexception);
            return null;
         }
      }

      private JsonElement m_130905_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", Registry.f_122827_.m_7981_(this.f_130888_).toString());
         if (this.f_130889_ != 1) {
            jsonobject.addProperty("count", this.f_130889_);
         }

         if (this.f_130890_ != null) {
            jsonobject.addProperty("tag", this.f_130890_.toString());
         }

         return jsonobject;
      }
   }
}