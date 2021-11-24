package net.minecraft.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyMapping implements Comparable<KeyMapping>, net.minecraftforge.client.extensions.IForgeKeyMapping {
   private static final Map<String, KeyMapping> f_90809_ = Maps.newHashMap();
   private static final net.minecraftforge.client.settings.KeyBindingMap f_90810_ = new net.minecraftforge.client.settings.KeyBindingMap();
   private static final Set<String> f_90811_ = Sets.newHashSet();
   public static final String f_167805_ = "key.categories.movement";
   public static final String f_167806_ = "key.categories.misc";
   public static final String f_167807_ = "key.categories.multiplayer";
   public static final String f_167808_ = "key.categories.gameplay";
   public static final String f_167809_ = "key.categories.inventory";
   public static final String f_167810_ = "key.categories.ui";
   public static final String f_167811_ = "key.categories.creative";
   private static final Map<String, Integer> f_90812_ = Util.m_137469_(Maps.newHashMap(), (p_90845_) -> {
      p_90845_.put("key.categories.movement", 1);
      p_90845_.put("key.categories.gameplay", 2);
      p_90845_.put("key.categories.inventory", 3);
      p_90845_.put("key.categories.creative", 4);
      p_90845_.put("key.categories.multiplayer", 5);
      p_90845_.put("key.categories.ui", 6);
      p_90845_.put("key.categories.misc", 7);
   });
   private final String f_90813_;
   private final InputConstants.Key f_90814_;
   private final String f_90815_;
   private InputConstants.Key f_90816_;
   boolean f_90817_;
   private int f_90818_;

   public static void m_90835_(InputConstants.Key p_90836_) {
      KeyMapping keymapping = f_90810_.lookupActive(p_90836_);
      if (keymapping != null) {
         ++keymapping.f_90818_;
      }

   }

   public static void m_90837_(InputConstants.Key p_90838_, boolean p_90839_) {
      for (KeyMapping keymapping : f_90810_.lookupAll(p_90838_))
      if (keymapping != null) {
         keymapping.m_7249_(p_90839_);
      }

   }

   public static void m_90829_() {
      for(KeyMapping keymapping : f_90809_.values()) {
         if (keymapping.f_90816_.m_84868_() == InputConstants.Type.KEYSYM && keymapping.f_90816_.m_84873_() != InputConstants.f_84822_.m_84873_()) {
            keymapping.m_7249_(InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), keymapping.f_90816_.m_84873_()));
         }
      }

   }

   public static void m_90847_() {
      for(KeyMapping keymapping : f_90809_.values()) {
         keymapping.m_90866_();
      }

   }

   public static void m_90854_() {
      f_90810_.clearMap();

      for(KeyMapping keymapping : f_90809_.values()) {
         f_90810_.addKey(keymapping.f_90816_, keymapping);
      }

   }

   public KeyMapping(String p_90821_, int p_90822_, String p_90823_) {
      this(p_90821_, InputConstants.Type.KEYSYM, p_90822_, p_90823_);
   }

   public KeyMapping(String p_90825_, InputConstants.Type p_90826_, int p_90827_, String p_90828_) {
      this.f_90813_ = p_90825_;
      this.f_90816_ = p_90826_.m_84895_(p_90827_);
      this.f_90814_ = this.f_90816_;
      this.f_90815_ = p_90828_;
      f_90809_.put(p_90825_, this);
      f_90810_.addKey(this.f_90816_, this);
      f_90811_.add(p_90828_);
   }

   public boolean m_90857_() {
      return this.f_90817_ && isConflictContextAndModifierActive();
   }

   public String m_90858_() {
      return this.f_90815_;
   }

   public boolean m_90859_() {
      if (this.f_90818_ == 0) {
         return false;
      } else {
         --this.f_90818_;
         return true;
      }
   }

   private void m_90866_() {
      this.f_90818_ = 0;
      this.m_7249_(false);
   }

   public String m_90860_() {
      return this.f_90813_;
   }

   public InputConstants.Key m_90861_() {
      return this.f_90814_;
   }

   public void m_90848_(InputConstants.Key p_90849_) {
      this.f_90816_ = p_90849_;
   }

   public int compareTo(KeyMapping p_90841_) {
      if (this.f_90815_.equals(p_90841_.f_90815_)) return I18n.m_118938_(this.f_90813_).compareTo(I18n.m_118938_(p_90841_.f_90813_));
      Integer tCat = f_90812_.get(this.f_90815_);
      Integer oCat = f_90812_.get(p_90841_.f_90815_);
      if (tCat == null && oCat != null) return 1;
      if (tCat != null && oCat == null) return -1;
      if (tCat == null && oCat == null) return I18n.m_118938_(this.f_90815_).compareTo(I18n.m_118938_(p_90841_.f_90815_));
      return  tCat.compareTo(oCat);
   }

   public static Supplier<Component> m_90842_(String p_90843_) {
      KeyMapping keymapping = f_90809_.get(p_90843_);
      return keymapping == null ? () -> {
         return new TranslatableComponent(p_90843_);
      } : keymapping::m_90863_;
   }

   public boolean m_90850_(KeyMapping p_90851_) {
      if (getKeyConflictContext().conflicts(p_90851_.getKeyConflictContext()) || p_90851_.getKeyConflictContext().conflicts(getKeyConflictContext())) {
         net.minecraftforge.client.settings.KeyModifier keyModifier = getKeyModifier();
         net.minecraftforge.client.settings.KeyModifier otherKeyModifier = p_90851_.getKeyModifier();
         if (keyModifier.matches(p_90851_.getKey()) || otherKeyModifier.matches(getKey())) {
            return true;
         } else if (getKey().equals(p_90851_.getKey())) {
            // IN_GAME key contexts have a conflict when at least one modifier is NONE.
            // For example: If you hold shift to crouch, you can still press E to open your inventory. This means that a Shift+E hotkey is in conflict with E.
            // GUI and other key contexts do not have this limitation.
            return keyModifier == otherKeyModifier ||
               (getKeyConflictContext().conflicts(net.minecraftforge.client.settings.KeyConflictContext.IN_GAME) &&
               (keyModifier == net.minecraftforge.client.settings.KeyModifier.NONE || otherKeyModifier == net.minecraftforge.client.settings.KeyModifier.NONE));
         }
      }
      return this.f_90816_.equals(p_90851_.f_90816_);
   }

   public boolean m_90862_() {
      return this.f_90816_.equals(InputConstants.f_84822_);
   }

   public boolean m_90832_(int p_90833_, int p_90834_) {
      if (p_90833_ == InputConstants.f_84822_.m_84873_()) {
         return this.f_90816_.m_84868_() == InputConstants.Type.SCANCODE && this.f_90816_.m_84873_() == p_90834_;
      } else {
         return this.f_90816_.m_84868_() == InputConstants.Type.KEYSYM && this.f_90816_.m_84873_() == p_90833_;
      }
   }

   public boolean m_90830_(int p_90831_) {
      return this.f_90816_.m_84868_() == InputConstants.Type.MOUSE && this.f_90816_.m_84873_() == p_90831_;
   }

   public Component m_90863_() {
      return getKeyModifier().getCombinedName(f_90816_, () -> {
      return this.f_90816_.m_84875_();
      });
   }

   public boolean m_90864_() {
      return this.f_90816_.equals(this.f_90814_) && getKeyModifier() == getKeyModifierDefault();
   }

   public String m_90865_() {
      return this.f_90816_.m_84874_();
   }

   public void m_7249_(boolean p_90846_) {
      this.f_90817_ = p_90846_;
   }

   /****************** Forge Start *****************************/
   private net.minecraftforge.client.settings.KeyModifier keyModifierDefault = net.minecraftforge.client.settings.KeyModifier.NONE;
   private net.minecraftforge.client.settings.KeyModifier keyModifier = net.minecraftforge.client.settings.KeyModifier.NONE;
   private net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext = net.minecraftforge.client.settings.KeyConflictContext.UNIVERSAL;

   /**
    * Convenience constructor for creating KeyBindings with keyConflictContext set.
    */
   public KeyMapping(String description, net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext, final InputConstants.Type inputType, final int keyCode, String category) {
       this(description, keyConflictContext, inputType.m_84895_(keyCode), category);
   }

   /**
    * Convenience constructor for creating KeyBindings with keyConflictContext set.
    */
   public KeyMapping(String description, net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext, InputConstants.Key keyCode, String category) {
       this(description, keyConflictContext, net.minecraftforge.client.settings.KeyModifier.NONE, keyCode, category);
   }

   /**
    * Convenience constructor for creating KeyBindings with keyConflictContext and keyModifier set.
    */
   public KeyMapping(String description, net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext, net.minecraftforge.client.settings.KeyModifier keyModifier, final InputConstants.Type inputType, final int keyCode, String category) {
       this(description, keyConflictContext, keyModifier, inputType.m_84895_(keyCode), category);
   }

   /**
    * Convenience constructor for creating KeyBindings with keyConflictContext and keyModifier set.
    */
   public KeyMapping(String description, net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext, net.minecraftforge.client.settings.KeyModifier keyModifier, InputConstants.Key keyCode, String category) {
      this.f_90813_ = description;
      this.f_90816_ = keyCode;
      this.f_90814_ = keyCode;
      this.f_90815_ = category;
      this.keyConflictContext = keyConflictContext;
      this.keyModifier = keyModifier;
      this.keyModifierDefault = keyModifier;
      if (this.keyModifier.matches(keyCode))
         this.keyModifier = net.minecraftforge.client.settings.KeyModifier.NONE;
      f_90809_.put(description, this);
      f_90810_.addKey(keyCode, this);
      f_90811_.add(category);
   }

   @Override
   public InputConstants.Key getKey() {
       return this.f_90816_;
   }

   @Override
   public void setKeyConflictContext(net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext) {
       this.keyConflictContext = keyConflictContext;
   }

   @Override
   public net.minecraftforge.client.settings.IKeyConflictContext getKeyConflictContext() {
       return keyConflictContext;
   }

   @Override
   public net.minecraftforge.client.settings.KeyModifier getKeyModifierDefault() {
       return keyModifierDefault;
   }

   @Override
   public net.minecraftforge.client.settings.KeyModifier getKeyModifier() {
       return keyModifier;
   }

   @Override
   public void setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier keyModifier, InputConstants.Key keyCode) {
       this.f_90816_ = keyCode;
       if (keyModifier.matches(keyCode))
           keyModifier = net.minecraftforge.client.settings.KeyModifier.NONE;
       f_90810_.removeKey(this);
       this.keyModifier = keyModifier;
       f_90810_.addKey(keyCode, this);
   }
   /****************** Forge End *****************************/
}
