package net.minecraft.data.models.model;

import javax.annotation.Nullable;

public final class TextureSlot {
   public static final TextureSlot f_125867_ = m_125898_("all");
   public static final TextureSlot f_125868_ = m_125900_("texture", f_125867_);
   public static final TextureSlot f_125869_ = m_125900_("particle", f_125868_);
   public static final TextureSlot f_125870_ = m_125900_("end", f_125867_);
   public static final TextureSlot f_125871_ = m_125900_("bottom", f_125870_);
   public static final TextureSlot f_125872_ = m_125900_("top", f_125870_);
   public static final TextureSlot f_125873_ = m_125900_("front", f_125867_);
   public static final TextureSlot f_125874_ = m_125900_("back", f_125867_);
   public static final TextureSlot f_125875_ = m_125900_("side", f_125867_);
   public static final TextureSlot f_125876_ = m_125900_("north", f_125875_);
   public static final TextureSlot f_125877_ = m_125900_("south", f_125875_);
   public static final TextureSlot f_125878_ = m_125900_("east", f_125875_);
   public static final TextureSlot f_125879_ = m_125900_("west", f_125875_);
   public static final TextureSlot f_125880_ = m_125898_("up");
   public static final TextureSlot f_125881_ = m_125898_("down");
   public static final TextureSlot f_125882_ = m_125898_("cross");
   public static final TextureSlot f_125883_ = m_125898_("plant");
   public static final TextureSlot f_125884_ = m_125900_("wall", f_125867_);
   public static final TextureSlot f_125885_ = m_125898_("rail");
   public static final TextureSlot f_125886_ = m_125898_("wool");
   public static final TextureSlot f_125887_ = m_125898_("pattern");
   public static final TextureSlot f_125888_ = m_125898_("pane");
   public static final TextureSlot f_125889_ = m_125898_("edge");
   public static final TextureSlot f_125890_ = m_125898_("fan");
   public static final TextureSlot f_125891_ = m_125898_("stem");
   public static final TextureSlot f_125892_ = m_125898_("upperstem");
   public static final TextureSlot f_125856_ = m_125898_("crop");
   public static final TextureSlot f_125857_ = m_125898_("dirt");
   public static final TextureSlot f_125858_ = m_125898_("fire");
   public static final TextureSlot f_125859_ = m_125898_("lantern");
   public static final TextureSlot f_125860_ = m_125898_("platform");
   public static final TextureSlot f_125861_ = m_125898_("unsticky");
   public static final TextureSlot f_125862_ = m_125898_("torch");
   public static final TextureSlot f_125863_ = m_125898_("layer0");
   public static final TextureSlot f_125864_ = m_125898_("lit_log");
   public static final TextureSlot f_176490_ = m_125898_("candle");
   public static final TextureSlot f_176491_ = m_125898_("inside");
   public static final TextureSlot f_176492_ = m_125898_("content");
   private final String f_125865_;
   @Nullable
   private final TextureSlot f_125866_;

   private static TextureSlot m_125898_(String p_125899_) {
      return new TextureSlot(p_125899_, (TextureSlot)null);
   }

   private static TextureSlot m_125900_(String p_125901_, TextureSlot p_125902_) {
      return new TextureSlot(p_125901_, p_125902_);
   }

   private TextureSlot(String p_125895_, @Nullable TextureSlot p_125896_) {
      this.f_125865_ = p_125895_;
      this.f_125866_ = p_125896_;
   }

   public String m_125897_() {
      return this.f_125865_;
   }

   @Nullable
   public TextureSlot m_125903_() {
      return this.f_125866_;
   }

   public String toString() {
      return "#" + this.f_125865_;
   }
}