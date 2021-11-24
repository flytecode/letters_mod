package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DisplayInfo {
   private final Component f_14958_;
   private final Component f_14959_;
   private final ItemStack f_14960_;
   private final ResourceLocation f_14961_;
   private final FrameType f_14962_;
   private final boolean f_14963_;
   private final boolean f_14964_;
   private final boolean f_14965_;
   private float f_14966_;
   private float f_14967_;

   public DisplayInfo(ItemStack p_14969_, Component p_14970_, Component p_14971_, @Nullable ResourceLocation p_14972_, FrameType p_14973_, boolean p_14974_, boolean p_14975_, boolean p_14976_) {
      this.f_14958_ = p_14970_;
      this.f_14959_ = p_14971_;
      this.f_14960_ = p_14969_;
      this.f_14961_ = p_14972_;
      this.f_14962_ = p_14973_;
      this.f_14963_ = p_14974_;
      this.f_14964_ = p_14975_;
      this.f_14965_ = p_14976_;
   }

   public void m_14978_(float p_14979_, float p_14980_) {
      this.f_14966_ = p_14979_;
      this.f_14967_ = p_14980_;
   }

   public Component m_14977_() {
      return this.f_14958_;
   }

   public Component m_14985_() {
      return this.f_14959_;
   }

   public ItemStack m_14990_() {
      return this.f_14960_;
   }

   @Nullable
   public ResourceLocation m_14991_() {
      return this.f_14961_;
   }

   public FrameType m_14992_() {
      return this.f_14962_;
   }

   public float m_14993_() {
      return this.f_14966_;
   }

   public float m_14994_() {
      return this.f_14967_;
   }

   public boolean m_14995_() {
      return this.f_14963_;
   }

   public boolean m_14996_() {
      return this.f_14964_;
   }

   public boolean m_14997_() {
      return this.f_14965_;
   }

   public static DisplayInfo m_14981_(JsonObject p_14982_) {
      Component component = Component.Serializer.m_130691_(p_14982_.get("title"));
      Component component1 = Component.Serializer.m_130691_(p_14982_.get("description"));
      if (component != null && component1 != null) {
         ItemStack itemstack = m_14986_(GsonHelper.m_13930_(p_14982_, "icon"));
         ResourceLocation resourcelocation = p_14982_.has("background") ? new ResourceLocation(GsonHelper.m_13906_(p_14982_, "background")) : null;
         FrameType frametype = p_14982_.has("frame") ? FrameType.m_15549_(GsonHelper.m_13906_(p_14982_, "frame")) : FrameType.TASK;
         boolean flag = GsonHelper.m_13855_(p_14982_, "show_toast", true);
         boolean flag1 = GsonHelper.m_13855_(p_14982_, "announce_to_chat", true);
         boolean flag2 = GsonHelper.m_13855_(p_14982_, "hidden", false);
         return new DisplayInfo(itemstack, component, component1, resourcelocation, frametype, flag, flag1, flag2);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack m_14986_(JsonObject p_14987_) {
      if (!p_14987_.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item item = GsonHelper.m_13909_(p_14987_, "item");
         if (p_14987_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack itemstack = new ItemStack(item);
            if (p_14987_.has("nbt")) {
               try {
                  CompoundTag compoundtag = TagParser.m_129359_(GsonHelper.m_13805_(p_14987_.get("nbt"), "nbt"));
                  itemstack.m_41751_(compoundtag);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + commandsyntaxexception.getMessage());
               }
            }

            return itemstack;
         }
      }
   }

   public void m_14983_(FriendlyByteBuf p_14984_) {
      p_14984_.m_130083_(this.f_14958_);
      p_14984_.m_130083_(this.f_14959_);
      p_14984_.m_130055_(this.f_14960_);
      p_14984_.m_130068_(this.f_14962_);
      int i = 0;
      if (this.f_14961_ != null) {
         i |= 1;
      }

      if (this.f_14963_) {
         i |= 2;
      }

      if (this.f_14965_) {
         i |= 4;
      }

      p_14984_.writeInt(i);
      if (this.f_14961_ != null) {
         p_14984_.m_130085_(this.f_14961_);
      }

      p_14984_.writeFloat(this.f_14966_);
      p_14984_.writeFloat(this.f_14967_);
   }

   public static DisplayInfo m_14988_(FriendlyByteBuf p_14989_) {
      Component component = p_14989_.m_130238_();
      Component component1 = p_14989_.m_130238_();
      ItemStack itemstack = p_14989_.m_130267_();
      FrameType frametype = p_14989_.m_130066_(FrameType.class);
      int i = p_14989_.readInt();
      ResourceLocation resourcelocation = (i & 1) != 0 ? p_14989_.m_130281_() : null;
      boolean flag = (i & 2) != 0;
      boolean flag1 = (i & 4) != 0;
      DisplayInfo displayinfo = new DisplayInfo(itemstack, component, component1, resourcelocation, frametype, flag, false, flag1);
      displayinfo.m_14978_(p_14989_.readFloat(), p_14989_.readFloat());
      return displayinfo;
   }

   public JsonElement m_14998_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("icon", this.m_14999_());
      jsonobject.add("title", Component.Serializer.m_130716_(this.f_14958_));
      jsonobject.add("description", Component.Serializer.m_130716_(this.f_14959_));
      jsonobject.addProperty("frame", this.f_14962_.m_15548_());
      jsonobject.addProperty("show_toast", this.f_14963_);
      jsonobject.addProperty("announce_to_chat", this.f_14964_);
      jsonobject.addProperty("hidden", this.f_14965_);
      if (this.f_14961_ != null) {
         jsonobject.addProperty("background", this.f_14961_.toString());
      }

      return jsonobject;
   }

   private JsonObject m_14999_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("item", Registry.f_122827_.m_7981_(this.f_14960_.m_41720_()).toString());
      if (this.f_14960_.m_41782_()) {
         jsonobject.addProperty("nbt", this.f_14960_.m_41783_().toString());
      }

      return jsonobject;
   }
}