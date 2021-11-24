package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
   private final Advancement f_138298_;
   private final DisplayInfo f_138299_;
   private final AdvancementRewards f_138300_;
   private final ResourceLocation f_138301_;
   private final Map<String, Criterion> f_138302_;
   private final String[][] f_138303_;
   private final Set<Advancement> f_138304_ = Sets.newLinkedHashSet();
   private final Component f_138305_;

   public Advancement(ResourceLocation p_138307_, @Nullable Advancement p_138308_, @Nullable DisplayInfo p_138309_, AdvancementRewards p_138310_, Map<String, Criterion> p_138311_, String[][] p_138312_) {
      this.f_138301_ = p_138307_;
      this.f_138299_ = p_138309_;
      this.f_138302_ = ImmutableMap.copyOf(p_138311_);
      this.f_138298_ = p_138308_;
      this.f_138300_ = p_138310_;
      this.f_138303_ = p_138312_;
      if (p_138308_ != null) {
         p_138308_.m_138317_(this);
      }

      if (p_138309_ == null) {
         this.f_138305_ = new TextComponent(p_138307_.toString());
      } else {
         Component component = p_138309_.m_14977_();
         ChatFormatting chatformatting = p_138309_.m_14992_().m_15552_();
         Component component1 = ComponentUtils.m_130750_(component.m_6881_(), Style.f_131099_.m_131140_(chatformatting)).m_130946_("\n").m_7220_(p_138309_.m_14985_());
         Component component2 = component.m_6881_().m_130938_((p_138316_) -> {
            return p_138316_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, component1));
         });
         this.f_138305_ = ComponentUtils.m_130748_(component2).m_130940_(chatformatting);
      }

   }

   public Advancement.Builder m_138313_() {
      return new Advancement.Builder(this.f_138298_ == null ? null : this.f_138298_.m_138327_(), this.f_138299_, this.f_138300_, this.f_138302_, this.f_138303_);
   }

   @Nullable
   public Advancement m_138319_() {
      return this.f_138298_;
   }

   @Nullable
   public DisplayInfo m_138320_() {
      return this.f_138299_;
   }

   public AdvancementRewards m_138321_() {
      return this.f_138300_;
   }

   public String toString() {
      return "SimpleAdvancement{id=" + this.m_138327_() + ", parent=" + (this.f_138298_ == null ? "null" : this.f_138298_.m_138327_()) + ", display=" + this.f_138299_ + ", rewards=" + this.f_138300_ + ", criteria=" + this.f_138302_ + ", requirements=" + Arrays.deepToString(this.f_138303_) + "}";
   }

   public Iterable<Advancement> m_138322_() {
      return this.f_138304_;
   }

   public Map<String, Criterion> m_138325_() {
      return this.f_138302_;
   }

   public int m_138326_() {
      return this.f_138303_.length;
   }

   public void m_138317_(Advancement p_138318_) {
      this.f_138304_.add(p_138318_);
   }

   public ResourceLocation m_138327_() {
      return this.f_138301_;
   }

   public boolean equals(Object p_138324_) {
      if (this == p_138324_) {
         return true;
      } else if (!(p_138324_ instanceof Advancement)) {
         return false;
      } else {
         Advancement advancement = (Advancement)p_138324_;
         return this.f_138301_.equals(advancement.f_138301_);
      }
   }

   public int hashCode() {
      return this.f_138301_.hashCode();
   }

   public String[][] m_138329_() {
      return this.f_138303_;
   }

   public Component m_138330_() {
      return this.f_138305_;
   }

   public static class Builder implements net.minecraftforge.common.extensions.IForgeAdvancementBuilder {
      private ResourceLocation f_138332_;
      private Advancement f_138333_;
      private DisplayInfo f_138334_;
      private AdvancementRewards f_138335_ = AdvancementRewards.f_9978_;
      private Map<String, Criterion> f_138336_ = Maps.newLinkedHashMap();
      private String[][] f_138337_;
      private RequirementsStrategy f_138338_ = RequirementsStrategy.f_15978_;

      Builder(@Nullable ResourceLocation p_138341_, @Nullable DisplayInfo p_138342_, AdvancementRewards p_138343_, Map<String, Criterion> p_138344_, String[][] p_138345_) {
         this.f_138332_ = p_138341_;
         this.f_138334_ = p_138342_;
         this.f_138335_ = p_138343_;
         this.f_138336_ = p_138344_;
         this.f_138337_ = p_138345_;
      }

      private Builder() {
      }

      public static Advancement.Builder m_138353_() {
         return new Advancement.Builder();
      }

      public Advancement.Builder m_138398_(Advancement p_138399_) {
         this.f_138333_ = p_138399_;
         return this;
      }

      public Advancement.Builder m_138396_(ResourceLocation p_138397_) {
         this.f_138332_ = p_138397_;
         return this;
      }

      public Advancement.Builder m_138362_(ItemStack p_138363_, Component p_138364_, Component p_138365_, @Nullable ResourceLocation p_138366_, FrameType p_138367_, boolean p_138368_, boolean p_138369_, boolean p_138370_) {
         return this.m_138358_(new DisplayInfo(p_138363_, p_138364_, p_138365_, p_138366_, p_138367_, p_138368_, p_138369_, p_138370_));
      }

      public Advancement.Builder m_138371_(ItemLike p_138372_, Component p_138373_, Component p_138374_, @Nullable ResourceLocation p_138375_, FrameType p_138376_, boolean p_138377_, boolean p_138378_, boolean p_138379_) {
         return this.m_138358_(new DisplayInfo(new ItemStack(p_138372_.m_5456_()), p_138373_, p_138374_, p_138375_, p_138376_, p_138377_, p_138378_, p_138379_));
      }

      public Advancement.Builder m_138358_(DisplayInfo p_138359_) {
         this.f_138334_ = p_138359_;
         return this;
      }

      public Advancement.Builder m_138354_(AdvancementRewards.Builder p_138355_) {
         return this.m_138356_(p_138355_.m_10004_());
      }

      public Advancement.Builder m_138356_(AdvancementRewards p_138357_) {
         this.f_138335_ = p_138357_;
         return this;
      }

      public Advancement.Builder m_138386_(String p_138387_, CriterionTriggerInstance p_138388_) {
         return this.m_138383_(p_138387_, new Criterion(p_138388_));
      }

      public Advancement.Builder m_138383_(String p_138384_, Criterion p_138385_) {
         if (this.f_138336_.containsKey(p_138384_)) {
            throw new IllegalArgumentException("Duplicate criterion " + p_138384_);
         } else {
            this.f_138336_.put(p_138384_, p_138385_);
            return this;
         }
      }

      public Advancement.Builder m_138360_(RequirementsStrategy p_138361_) {
         this.f_138338_ = p_138361_;
         return this;
      }

      public Advancement.Builder m_143951_(String[][] p_143952_) {
         this.f_138337_ = p_143952_;
         return this;
      }

      public boolean m_138392_(Function<ResourceLocation, Advancement> p_138393_) {
         if (this.f_138332_ == null) {
            return true;
         } else {
            if (this.f_138333_ == null) {
               this.f_138333_ = p_138393_.apply(this.f_138332_);
            }

            return this.f_138333_ != null;
         }
      }

      public Advancement m_138403_(ResourceLocation p_138404_) {
         if (!this.m_138392_((p_138407_) -> {
            return null;
         })) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.f_138337_ == null) {
               this.f_138337_ = this.f_138338_.m_15985_(this.f_138336_.keySet());
            }

            return new Advancement(p_138404_, this.f_138333_, this.f_138334_, this.f_138335_, this.f_138336_, this.f_138337_);
         }
      }

      public Advancement m_138389_(Consumer<Advancement> p_138390_, String p_138391_) {
         Advancement advancement = this.m_138403_(new ResourceLocation(p_138391_));
         p_138390_.accept(advancement);
         return advancement;
      }

      public JsonObject m_138400_() {
         if (this.f_138337_ == null) {
            this.f_138337_ = this.f_138338_.m_15985_(this.f_138336_.keySet());
         }

         JsonObject jsonobject = new JsonObject();
         if (this.f_138333_ != null) {
            jsonobject.addProperty("parent", this.f_138333_.m_138327_().toString());
         } else if (this.f_138332_ != null) {
            jsonobject.addProperty("parent", this.f_138332_.toString());
         }

         if (this.f_138334_ != null) {
            jsonobject.add("display", this.f_138334_.m_14998_());
         }

         jsonobject.add("rewards", this.f_138335_.m_9997_());
         JsonObject jsonobject1 = new JsonObject();

         for(Entry<String, Criterion> entry : this.f_138336_.entrySet()) {
            jsonobject1.add(entry.getKey(), entry.getValue().m_11425_());
         }

         jsonobject.add("criteria", jsonobject1);
         JsonArray jsonarray1 = new JsonArray();

         for(String[] astring : this.f_138337_) {
            JsonArray jsonarray = new JsonArray();

            for(String s : astring) {
               jsonarray.add(s);
            }

            jsonarray1.add(jsonarray);
         }

         jsonobject.add("requirements", jsonarray1);
         return jsonobject;
      }

      public void m_138394_(FriendlyByteBuf p_138395_) {
         if (this.f_138332_ == null) {
            p_138395_.writeBoolean(false);
         } else {
            p_138395_.writeBoolean(true);
            p_138395_.m_130085_(this.f_138332_);
         }

         if (this.f_138334_ == null) {
            p_138395_.writeBoolean(false);
         } else {
            p_138395_.writeBoolean(true);
            this.f_138334_.m_14983_(p_138395_);
         }

         Criterion.m_11420_(this.f_138336_, p_138395_);
         p_138395_.m_130130_(this.f_138337_.length);

         for(String[] astring : this.f_138337_) {
            p_138395_.m_130130_(astring.length);

            for(String s : astring) {
               p_138395_.m_130070_(s);
            }
         }

      }

      public String toString() {
         return "Task Advancement{parentId=" + this.f_138332_ + ", display=" + this.f_138334_ + ", rewards=" + this.f_138335_ + ", criteria=" + this.f_138336_ + ", requirements=" + Arrays.deepToString(this.f_138337_) + "}";
      }

      public static Advancement.Builder m_138380_(JsonObject p_138381_, DeserializationContext p_138382_) {
         if ((p_138381_ = net.minecraftforge.common.crafting.ConditionalAdvancement.processConditional(p_138381_)) == null) return null;
         ResourceLocation resourcelocation = p_138381_.has("parent") ? new ResourceLocation(GsonHelper.m_13906_(p_138381_, "parent")) : null;
         DisplayInfo displayinfo = p_138381_.has("display") ? DisplayInfo.m_14981_(GsonHelper.m_13930_(p_138381_, "display")) : null;
         AdvancementRewards advancementrewards = p_138381_.has("rewards") ? AdvancementRewards.m_9991_(GsonHelper.m_13930_(p_138381_, "rewards")) : AdvancementRewards.f_9978_;
         Map<String, Criterion> map = Criterion.m_11426_(GsonHelper.m_13930_(p_138381_, "criteria"), p_138382_);
         if (map.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray jsonarray = GsonHelper.m_13832_(p_138381_, "requirements", new JsonArray());
            String[][] astring = new String[jsonarray.size()][];

            for(int i = 0; i < jsonarray.size(); ++i) {
               JsonArray jsonarray1 = GsonHelper.m_13924_(jsonarray.get(i), "requirements[" + i + "]");
               astring[i] = new String[jsonarray1.size()];

               for(int j = 0; j < jsonarray1.size(); ++j) {
                  astring[i][j] = GsonHelper.m_13805_(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
               }
            }

            if (astring.length == 0) {
               astring = new String[map.size()][];
               int k = 0;

               for(String s2 : map.keySet()) {
                  astring[k++] = new String[]{s2};
               }
            }

            for(String[] astring1 : astring) {
               if (astring1.length == 0 && map.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for(String s : astring1) {
                  if (!map.containsKey(s)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + s + "'");
                  }
               }
            }

            for(String s1 : map.keySet()) {
               boolean flag = false;

               for(String[] astring2 : astring) {
                  if (ArrayUtils.contains(astring2, s1)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  throw new JsonSyntaxException("Criterion '" + s1 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
               }
            }

            return new Advancement.Builder(resourcelocation, displayinfo, advancementrewards, map, astring);
         }
      }

      public static Advancement.Builder m_138401_(FriendlyByteBuf p_138402_) {
         ResourceLocation resourcelocation = p_138402_.readBoolean() ? p_138402_.m_130281_() : null;
         DisplayInfo displayinfo = p_138402_.readBoolean() ? DisplayInfo.m_14988_(p_138402_) : null;
         Map<String, Criterion> map = Criterion.m_11431_(p_138402_);
         String[][] astring = new String[p_138402_.m_130242_()][];

         for(int i = 0; i < astring.length; ++i) {
            astring[i] = new String[p_138402_.m_130242_()];

            for(int j = 0; j < astring[i].length; ++j) {
               astring[i][j] = p_138402_.m_130277_();
            }
         }

         return new Advancement.Builder(resourcelocation, displayinfo, AdvancementRewards.f_9978_, map, astring);
      }

      public Map<String, Criterion> m_138405_() {
         return this.f_138336_;
      }
   }
}
