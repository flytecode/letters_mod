package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RealmsScreen extends Screen {
   protected static final int f_175058_ = 17;
   protected static final int f_175059_ = 20;
   protected static final int f_175060_ = 7;
   protected static final long f_175061_ = 5368709120L;
   public static final int f_175062_ = 16777215;
   public static final int f_175063_ = 10526880;
   protected static final int f_175064_ = 5000268;
   protected static final int f_175065_ = 7105644;
   protected static final int f_175066_ = 8388479;
   protected static final int f_175067_ = 6077788;
   protected static final int f_175068_ = 16711680;
   protected static final int f_175069_ = 15553363;
   protected static final int f_175070_ = -1073741824;
   protected static final int f_175040_ = 13413468;
   protected static final int f_175041_ = -256;
   protected static final int f_175042_ = 3368635;
   protected static final int f_175043_ = 7107012;
   protected static final int f_175044_ = 8226750;
   protected static final int f_175045_ = 16777120;
   protected static final String f_175046_ = "https://www.minecraft.net/realms/adventure-maps-in-1-9";
   protected static final int f_175047_ = 8;
   protected static final int f_175048_ = 8;
   protected static final int f_175049_ = 8;
   protected static final int f_175050_ = 8;
   protected static final int f_175051_ = 40;
   protected static final int f_175052_ = 8;
   protected static final int f_175053_ = 8;
   protected static final int f_175054_ = 8;
   protected static final int f_175055_ = 64;
   protected static final int f_175056_ = 64;
   private final List<RealmsLabel> f_175057_ = Lists.newArrayList();

   public RealmsScreen(Component p_175072_) {
      super(p_175072_);
   }

   protected static int m_120774_(int p_120775_) {
      return 40 + p_120775_ * 13;
   }

   protected RealmsLabel m_175073_(RealmsLabel p_175074_) {
      this.f_175057_.add(p_175074_);
      return this.m_169394_(p_175074_);
   }

   public Component m_175075_() {
      return CommonComponents.m_178391_(this.f_175057_.stream().map(RealmsLabel::m_175034_).collect(Collectors.toList()));
   }
}