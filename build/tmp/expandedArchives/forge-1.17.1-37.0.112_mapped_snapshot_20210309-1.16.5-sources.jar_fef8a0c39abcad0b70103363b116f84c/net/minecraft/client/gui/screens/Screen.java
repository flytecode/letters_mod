package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.ScreenNarrationCollector;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public abstract class Screen extends AbstractContainerEventHandler implements Widget {
   private static final Logger f_96536_ = LogManager.getLogger();
   private static final Set<String> f_96537_ = Sets.newHashSet("http", "https");
   private static final int f_169366_ = 2;
   private static final Component f_169367_ = new TranslatableComponent("narrator.screen.usage");
   protected final Component f_96539_;
   private final List<GuiEventListener> f_96540_ = Lists.newArrayList();
   private final List<NarratableEntry> f_169368_ = Lists.newArrayList();
   @Nullable
   protected Minecraft f_96541_;
   protected ItemRenderer f_96542_;
   public int f_96543_;
   public int f_96544_;
   public final List<Widget> f_169369_ = Lists.newArrayList();
   public boolean f_96546_;
   protected Font f_96547_;
   private URI f_96538_;
   private static final long f_169370_ = TimeUnit.SECONDS.toMillis(2L);
   private static final long f_169371_ = f_169370_;
   private static final long f_169372_ = 750L;
   private static final long f_169373_ = 200L;
   private static final long f_169374_ = 200L;
   private final ScreenNarrationCollector f_169375_ = new ScreenNarrationCollector();
   private long f_169376_ = Long.MIN_VALUE;
   private long f_169377_ = Long.MAX_VALUE;
   @Nullable
   private NarratableEntry f_169365_;

   protected Screen(Component p_96550_) {
      this.f_96539_ = p_96550_;
   }

   public Component m_96636_() {
      return this.f_96539_;
   }

   public Component m_142562_() {
      return this.m_96636_();
   }

   public void m_6305_(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
      for(Widget widget : this.f_169369_) {
         widget.m_6305_(p_96562_, p_96563_, p_96564_, p_96565_);
      }

   }

   public boolean m_7933_(int p_96552_, int p_96553_, int p_96554_) {
      if (p_96552_ == 256 && this.m_6913_()) {
         this.m_7379_();
         return true;
      } else if (p_96552_ == 258) {
         boolean flag = !m_96638_();
         if (!this.m_5755_(flag)) {
            this.m_5755_(flag);
         }

         return false;
      } else {
         return super.m_7933_(p_96552_, p_96553_, p_96554_);
      }
   }

   public boolean m_6913_() {
      return true;
   }

   public void m_7379_() {
      this.f_96541_.popGuiLayer();
   }

   protected <T extends GuiEventListener & Widget & NarratableEntry> T m_142416_(T p_169406_) {
      this.f_169369_.add(p_169406_);
      return this.m_7787_(p_169406_);
   }

   protected <T extends Widget> T m_169394_(T p_169395_) {
      this.f_169369_.add(p_169395_);
      return p_169395_;
   }

   protected <T extends GuiEventListener & NarratableEntry> T m_7787_(T p_96625_) {
      this.f_96540_.add(p_96625_);
      this.f_169368_.add(p_96625_);
      return p_96625_;
   }

   protected void m_169411_(GuiEventListener p_169412_) {
      if (p_169412_ instanceof Widget) {
         this.f_169369_.remove((Widget)p_169412_);
      }

      if (p_169412_ instanceof NarratableEntry) {
         this.f_169368_.remove((NarratableEntry)p_169412_);
      }

      this.f_96540_.remove(p_169412_);
   }

   protected void m_169413_() {
      this.f_169369_.clear();
      this.f_96540_.clear();
      this.f_169368_.clear();
   }

   private Font tooltipFont = null;
   private ItemStack tooltipStack = ItemStack.f_41583_;
   protected void m_6057_(PoseStack p_96566_, ItemStack p_96567_, int p_96568_, int p_96569_) {
      tooltipStack = p_96567_;
      this.m_169388_(p_96566_, this.m_96555_(p_96567_), p_96567_.m_150921_(), p_96568_, p_96569_);
      tooltipStack = ItemStack.f_41583_;
   }

   public void renderTooltip(PoseStack poseStack, List<Component> textComponents, Optional<TooltipComponent> tooltipComponent, int x, int y, ItemStack stack) {
      this.renderTooltip(poseStack, textComponents, tooltipComponent, x, y, null, stack);
   }
   public void renderTooltip(PoseStack poseStack, List<Component> textComponents, Optional<TooltipComponent> tooltipComponent, int x, int y, @Nullable Font font) {
      this.renderTooltip(poseStack, textComponents, tooltipComponent, x, y, font, ItemStack.f_41583_);
   }
   public void renderTooltip(PoseStack poseStack, List<Component> textComponents, Optional<TooltipComponent> tooltipComponent, int x, int y, @Nullable Font font, ItemStack stack) {
      this.tooltipFont = font;
      this.tooltipStack = stack;
      this.m_169388_(poseStack, textComponents, tooltipComponent, x, y);
      this.tooltipFont = null;
      this.tooltipStack = ItemStack.f_41583_;
   }
   public void m_169388_(PoseStack p_169389_, List<Component> p_169390_, Optional<TooltipComponent> p_169391_, int p_169392_, int p_169393_) {
      List<ClientTooltipComponent> list = net.minecraftforge.client.ForgeHooksClient.gatherTooltipComponents(this.tooltipStack, p_169390_, p_169391_, p_169392_, f_96543_, f_96544_, this.tooltipFont, this.f_96547_);
      this.m_169383_(p_169389_, list, p_169392_, p_169393_);
   }

   public List<Component> m_96555_(ItemStack p_96556_) {
      return p_96556_.m_41651_(this.f_96541_.f_91074_, this.f_96541_.f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
   }

   public void m_96602_(PoseStack p_96603_, Component p_96604_, int p_96605_, int p_96606_) {
      this.m_96617_(p_96603_, Arrays.asList(p_96604_.m_7532_()), p_96605_, p_96606_);
   }

   public void m_96597_(PoseStack p_96598_, List<Component> p_96599_, int p_96600_, int p_96601_) {
      List<ClientTooltipComponent> components = net.minecraftforge.client.ForgeHooksClient.gatherTooltipComponents(this.tooltipStack, p_96599_, p_96600_, f_96543_, f_96544_, this.tooltipFont, this.f_96547_);
      this.m_169383_(p_96598_, components, p_96600_, p_96601_);
   }
   public void renderComponentTooltip(PoseStack poseStack, List<? extends net.minecraft.network.chat.FormattedText> tooltips, int mouseX, int mouseY, ItemStack stack) {
      this.renderComponentTooltip(poseStack, tooltips, mouseX, mouseY, null, stack);
   }
   public void renderComponentTooltip(PoseStack poseStack, List<? extends net.minecraft.network.chat.FormattedText> tooltips, int mouseX, int mouseY, @Nullable Font font) {
      this.renderComponentTooltip(poseStack, tooltips, mouseX, mouseY, font, ItemStack.f_41583_);
   }
   @Deprecated(forRemoval = true, since = "1.17.1") public void renderComponentToolTip(PoseStack poseStack, List<? extends net.minecraft.network.chat.FormattedText> tooltips, int mouseX, int mouseY, @Nullable Font font) {
      renderComponentTooltip(poseStack, tooltips, mouseX, mouseY, font);
   }
   public void renderComponentTooltip(PoseStack poseStack, List<? extends net.minecraft.network.chat.FormattedText> tooltips, int mouseX, int mouseY, @Nullable Font font, ItemStack stack) {
      this.tooltipFont = font;
      this.tooltipStack = stack;
      List<ClientTooltipComponent> components = net.minecraftforge.client.ForgeHooksClient.gatherTooltipComponents(stack, tooltips, mouseX, f_96543_, f_96544_, this.tooltipFont, this.f_96547_);
      this.m_169383_(poseStack, components, mouseX, mouseY);
      this.tooltipFont = null;
      this.tooltipStack = ItemStack.f_41583_;
   }

   public void m_96617_(PoseStack p_96618_, List<? extends FormattedCharSequence> p_96619_, int p_96620_, int p_96621_) {
      this.m_169383_(p_96618_, p_96619_.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), p_96620_, p_96621_);
   }
   @Deprecated(forRemoval = true, since = "1.17.1") public void renderToolTip(PoseStack p_96618_, List<? extends FormattedCharSequence> p_96619_, int p_96620_, int p_96621_, Font font) {
      renderTooltip(p_96618_, p_96619_, p_96620_, p_96621_, font);
   }
   public void renderTooltip(PoseStack poseStack, List<? extends FormattedCharSequence> lines, int x, int y, Font font) {
      this.tooltipFont = font;
      this.m_96617_(poseStack, lines, x, y);
      this.tooltipFont = null;
   }

   private void m_169383_(PoseStack p_169384_, List<ClientTooltipComponent> p_169385_, int p_169386_, int p_169387_) {
      if (!p_169385_.isEmpty()) {
         net.minecraftforge.client.event.RenderTooltipEvent.Pre preEvent = net.minecraftforge.client.ForgeHooksClient.preTooltipEvent(this.tooltipStack, p_169384_, p_169386_, p_169387_, f_96543_, f_96544_, p_169385_, this.tooltipFont, this.f_96547_);
         if (preEvent.isCanceled()) return;
         int i = 0;
         int j = p_169385_.size() == 1 ? -2 : 0;

         for(ClientTooltipComponent clienttooltipcomponent : p_169385_) {
            int k = clienttooltipcomponent.m_142069_(preEvent.getFontRenderer());
            if (k > i) {
               i = k;
            }

            j += clienttooltipcomponent.m_142103_();
         }

         int j2 = preEvent.getX() + 12;
         int k2 = preEvent.getY() - 12;
         if (j2 + i > this.f_96543_) {
            j2 -= 28 + i;
         }

         if (k2 + j + 6 > this.f_96544_) {
            k2 = this.f_96544_ - j - 6;
         }

         p_169384_.m_85836_();
         int l = -267386864;
         int i1 = 1347420415;
         int j1 = 1344798847;
         int k1 = 400;
         float f = this.f_96542_.f_115093_;
         this.f_96542_.f_115093_ = 400.0F;
         Tesselator tesselator = Tesselator.m_85913_();
         BufferBuilder bufferbuilder = tesselator.m_85915_();
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
         Matrix4f matrix4f = p_169384_.m_85850_().m_85861_();
         net.minecraftforge.client.event.RenderTooltipEvent.Color colorEvent = net.minecraftforge.client.ForgeHooksClient.colorTooltipEvent(this.tooltipStack, p_169384_, j2, k2, preEvent.getFontRenderer(), p_169385_);
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 4, j2 + i + 3, k2 - 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundStart());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 + j + 3, j2 + i + 3, k2 + j + 4, 400, colorEvent.getBackgroundEnd(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 4, k2 - 3, j2 - 3, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 + i + 3, k2 - 3, j2 + i + 4, k2 + j + 3, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
         m_93123_(matrix4f, bufferbuilder, j2 + i + 2, k2 - 3 + 1, j2 + i + 3, k2 + j + 3 - 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderEnd());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 - 3 + 1, 400, colorEvent.getBorderStart(), colorEvent.getBorderStart());
         m_93123_(matrix4f, bufferbuilder, j2 - 3, k2 + j + 2, j2 + i + 3, k2 + j + 3, 400, colorEvent.getBorderEnd(), colorEvent.getBorderEnd());
         RenderSystem.m_69482_();
         RenderSystem.m_69472_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         bufferbuilder.m_85721_();
         BufferUploader.m_85761_(bufferbuilder);
         RenderSystem.m_69461_();
         RenderSystem.m_69493_();
         MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
         p_169384_.m_85837_(0.0D, 0.0D, 400.0D);
         int l1 = k2;

         for(int i2 = 0; i2 < p_169385_.size(); ++i2) {
            ClientTooltipComponent clienttooltipcomponent1 = p_169385_.get(i2);
            clienttooltipcomponent1.m_142440_(preEvent.getFontRenderer(), j2, l1, matrix4f, multibuffersource$buffersource);
            l1 += clienttooltipcomponent1.m_142103_() + (i2 == 0 ? 2 : 0);
         }

         multibuffersource$buffersource.m_109911_();
         p_169384_.m_85849_();
         l1 = k2;

         for(int l2 = 0; l2 < p_169385_.size(); ++l2) {
            ClientTooltipComponent clienttooltipcomponent2 = p_169385_.get(l2);
            clienttooltipcomponent2.m_142162_(preEvent.getFontRenderer(), j2, l1, p_169384_, this.f_96542_, 400, this.f_96541_.m_91097_());
            l1 += clienttooltipcomponent2.m_142103_() + (l2 == 0 ? 2 : 0);
         }

         this.f_96542_.f_115093_ = f;
      }
   }

   protected void m_96570_(PoseStack p_96571_, @Nullable Style p_96572_, int p_96573_, int p_96574_) {
      if (p_96572_ != null && p_96572_.m_131186_() != null) {
         HoverEvent hoverevent = p_96572_.m_131186_();
         HoverEvent.ItemStackInfo hoverevent$itemstackinfo = hoverevent.m_130823_(HoverEvent.Action.f_130832_);
         if (hoverevent$itemstackinfo != null) {
            this.m_6057_(p_96571_, hoverevent$itemstackinfo.m_130898_(), p_96573_, p_96574_);
         } else {
            HoverEvent.EntityTooltipInfo hoverevent$entitytooltipinfo = hoverevent.m_130823_(HoverEvent.Action.f_130833_);
            if (hoverevent$entitytooltipinfo != null) {
               if (this.f_96541_.f_91066_.f_92125_) {
                  this.m_96597_(p_96571_, hoverevent$entitytooltipinfo.m_130884_(), p_96573_, p_96574_);
               }
            } else {
               Component component = hoverevent.m_130823_(HoverEvent.Action.f_130831_);
               if (component != null) {
                  this.m_96617_(p_96571_, this.f_96541_.f_91062_.m_92923_(component, Math.max(this.f_96543_ / 2, 200)), p_96573_, p_96574_);
               }
            }
         }

      }
   }

   protected void m_6697_(String p_96587_, boolean p_96588_) {
   }

   public boolean m_5561_(@Nullable Style p_96592_) {
      if (p_96592_ == null) {
         return false;
      } else {
         ClickEvent clickevent = p_96592_.m_131182_();
         if (m_96638_()) {
            if (p_96592_.m_131189_() != null) {
               this.m_6697_(p_96592_.m_131189_(), false);
            }
         } else if (clickevent != null) {
            if (clickevent.m_130622_() == ClickEvent.Action.OPEN_URL) {
               if (!this.f_96541_.f_91066_.f_92039_) {
                  return false;
               }

               try {
                  URI uri = new URI(clickevent.m_130623_());
                  String s = uri.getScheme();
                  if (s == null) {
                     throw new URISyntaxException(clickevent.m_130623_(), "Missing protocol");
                  }

                  if (!f_96537_.contains(s.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(clickevent.m_130623_(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                  }

                  if (this.f_96541_.f_91066_.f_92040_) {
                     this.f_96538_ = uri;
                     this.f_96541_.m_91152_(new ConfirmLinkScreen(this::m_96622_, clickevent.m_130623_(), false));
                  } else {
                     this.m_96589_(uri);
                  }
               } catch (URISyntaxException urisyntaxexception) {
                  f_96536_.error("Can't open url for {}", clickevent, urisyntaxexception);
               }
            } else if (clickevent.m_130622_() == ClickEvent.Action.OPEN_FILE) {
               URI uri1 = (new File(clickevent.m_130623_())).toURI();
               this.m_96589_(uri1);
            } else if (clickevent.m_130622_() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.m_6697_(clickevent.m_130623_(), true);
            } else if (clickevent.m_130622_() == ClickEvent.Action.RUN_COMMAND) {
               this.m_96612_(clickevent.m_130623_(), false);
            } else if (clickevent.m_130622_() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
               this.f_96541_.f_91068_.m_90911_(clickevent.m_130623_());
            } else {
               f_96536_.error("Don't know how to handle {}", (Object)clickevent);
            }

            return true;
         }

         return false;
      }
   }

   public void m_96615_(String p_96616_) {
      this.m_96612_(p_96616_, true);
   }

   public void m_96612_(String p_96613_, boolean p_96614_) {
      p_96613_ = net.minecraftforge.event.ForgeEventFactory.onClientSendMessage(p_96613_);
      if (p_96613_.isEmpty()) return;
      if (p_96614_) {
         this.f_96541_.f_91065_.m_93076_().m_93783_(p_96613_);
      }
      //if (net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.player, msg) != 0) return; //Forge: TODO Client command re-write

      this.f_96541_.f_91074_.m_108739_(p_96613_);
   }

   public final void m_6575_(Minecraft p_96607_, int p_96608_, int p_96609_) {
      this.f_96541_ = p_96607_;
      this.f_96542_ = p_96607_.m_91291_();
      this.f_96547_ = p_96607_.f_91062_;
      this.f_96543_ = p_96608_;
      this.f_96544_ = p_96609_;
      java.util.function.Consumer<GuiEventListener> add = (b) -> {
         if (b instanceof Widget w)
            this.f_169369_.add(w);
         if (b instanceof NarratableEntry ne)
            this.f_169368_.add(ne);
         f_96540_.add(b);
      };
      if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Pre(this, this.f_96540_, add, this::m_169411_))) {
      this.m_169413_();
      this.m_7522_((GuiEventListener)null);
      this.m_7856_();
      this.m_169407_(false);
      this.m_169378_(f_169370_);
      }
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post(this, this.f_96540_, add, this::m_169411_));
   }

   public List<? extends GuiEventListener> m_6702_() {
      return this.f_96540_;
   }

   protected void m_7856_() {
   }

   public void m_96624_() {
   }

   public void m_7861_() {
   }

   public void m_7333_(PoseStack p_96557_) {
      this.m_96558_(p_96557_, 0);
   }

   public void m_96558_(PoseStack p_96559_, int p_96560_) {
      if (this.f_96541_.f_91073_ != null) {
         this.m_93179_(p_96559_, 0, 0, this.f_96543_, this.f_96544_, -1072689136, -804253680);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, p_96559_));
      } else {
         this.m_96626_(p_96560_);
      }

   }

   public void m_96626_(int p_96627_) {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157456_(0, f_93096_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_96544_, 0.0D).m_7421_(0.0F, (float)this.f_96544_ / 32.0F + (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_96543_, (double)this.f_96544_, 0.0D).m_7421_((float)this.f_96543_ / 32.0F, (float)this.f_96544_ / 32.0F + (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_96543_, 0.0D, 0.0D).m_7421_((float)this.f_96543_ / 32.0F, (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_7421_(0.0F, (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
      tesselator.m_85914_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, new PoseStack()));
   }

   public boolean m_7043_() {
      return true;
   }

   private void m_96622_(boolean p_96623_) {
      if (p_96623_) {
         this.m_96589_(this.f_96538_);
      }

      this.f_96538_ = null;
      this.f_96541_.m_91152_(this);
   }

   private void m_96589_(URI p_96590_) {
      Util.m_137581_().m_137648_(p_96590_);
   }

   public static boolean m_96637_() {
      if (Minecraft.f_91002_) {
         return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 343) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 347);
      } else {
         return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 341) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 345);
      }
   }

   public static boolean m_96638_() {
      return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344);
   }

   public static boolean m_96639_() {
      return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 342) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 346);
   }

   public static boolean m_96628_(int p_96629_) {
      return p_96629_ == 88 && m_96637_() && !m_96638_() && !m_96639_();
   }

   public static boolean m_96630_(int p_96631_) {
      return p_96631_ == 86 && m_96637_() && !m_96638_() && !m_96639_();
   }

   public static boolean m_96632_(int p_96633_) {
      return p_96633_ == 67 && m_96637_() && !m_96638_() && !m_96639_();
   }

   public static boolean m_96634_(int p_96635_) {
      return p_96635_ == 65 && m_96637_() && !m_96638_() && !m_96639_();
   }

   public void m_6574_(Minecraft p_96575_, int p_96576_, int p_96577_) {
      this.m_6575_(p_96575_, p_96576_, p_96577_);
   }

   public static void m_96579_(Runnable p_96580_, String p_96581_, String p_96582_) {
      try {
         p_96580_.run();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, p_96581_);
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Affected screen");
         crashreportcategory.m_128165_("Screen name", () -> {
            return p_96582_;
         });
         throw new ReportedException(crashreport);
      }
   }

   protected boolean m_96583_(String p_96584_, char p_96585_, int p_96586_) {
      int i = p_96584_.indexOf(58);
      int j = p_96584_.indexOf(47);
      if (p_96585_ == ':') {
         return (j == -1 || p_96586_ <= j) && i == -1;
      } else if (p_96585_ == '/') {
         return p_96586_ > i;
      } else {
         return p_96585_ == '_' || p_96585_ == '-' || p_96585_ >= 'a' && p_96585_ <= 'z' || p_96585_ >= '0' && p_96585_ <= '9' || p_96585_ == '.';
      }
   }

   public boolean m_5953_(double p_96595_, double p_96596_) {
      return true;
   }

   public void m_7400_(List<Path> p_96591_) {
   }

   public Minecraft getMinecraft() {
      return this.f_96541_;
   }

   private void m_169380_(long p_169381_, boolean p_169382_) {
      this.f_169377_ = Util.m_137550_() + p_169381_;
      if (p_169382_) {
         this.f_169376_ = Long.MIN_VALUE;
      }

   }

   private void m_169378_(long p_169379_) {
      this.f_169376_ = Util.m_137550_() + p_169379_;
   }

   public void m_169414_() {
      this.m_169380_(750L, false);
   }

   public void m_169415_() {
      this.m_169380_(200L, true);
   }

   public void m_169416_() {
      this.m_169380_(200L, true);
   }

   private boolean m_169419_() {
      return NarratorChatListener.f_93311_.m_93316_();
   }

   public void m_169417_() {
      if (this.m_169419_()) {
         long i = Util.m_137550_();
         if (i > this.f_169377_ && i > this.f_169376_) {
            this.m_169409_(true);
            this.f_169377_ = Long.MAX_VALUE;
         }
      }

   }

   protected void m_169407_(boolean p_169408_) {
      if (this.m_169419_()) {
         this.m_169409_(p_169408_);
      }

   }

   private void m_169409_(boolean p_169410_) {
      this.f_169375_.m_169186_(this::m_142228_);
      String s = this.f_169375_.m_169188_(!p_169410_);
      if (!s.isEmpty()) {
         NarratorChatListener.f_93311_.m_93319_(s);
      }

   }

   protected void m_142228_(NarrationElementOutput p_169396_) {
      p_169396_.m_169146_(NarratedElementType.TITLE, this.m_142562_());
      p_169396_.m_169146_(NarratedElementType.USAGE, f_169367_);
      this.m_142227_(p_169396_);
   }

   protected void m_142227_(NarrationElementOutput p_169403_) {
      ImmutableList<NarratableEntry> immutablelist = this.f_169368_.stream().filter(NarratableEntry::m_142518_).collect(ImmutableList.toImmutableList());
      Screen.NarratableSearchResult screen$narratablesearchresult = m_169400_(immutablelist, this.f_169365_);
      if (screen$narratablesearchresult != null) {
         if (screen$narratablesearchresult.f_169422_.m_169123_()) {
            this.f_169365_ = screen$narratablesearchresult.f_169420_;
         }

         if (immutablelist.size() > 1) {
            p_169403_.m_169146_(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.screen", screen$narratablesearchresult.f_169421_ + 1, immutablelist.size()));
            if (screen$narratablesearchresult.f_169422_ == NarratableEntry.NarrationPriority.FOCUSED) {
               p_169403_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
            }
         }

         screen$narratablesearchresult.f_169420_.m_142291_(p_169403_.m_142047_());
      }

   }

   @Nullable
   public static Screen.NarratableSearchResult m_169400_(List<? extends NarratableEntry> p_169401_, @Nullable NarratableEntry p_169402_) {
      Screen.NarratableSearchResult screen$narratablesearchresult = null;
      Screen.NarratableSearchResult screen$narratablesearchresult1 = null;
      int i = 0;

      for(int j = p_169401_.size(); i < j; ++i) {
         NarratableEntry narratableentry = p_169401_.get(i);
         NarratableEntry.NarrationPriority narratableentry$narrationpriority = narratableentry.m_142684_();
         if (narratableentry$narrationpriority.m_169123_()) {
            if (narratableentry != p_169402_) {
               return new Screen.NarratableSearchResult(narratableentry, i, narratableentry$narrationpriority);
            }

            screen$narratablesearchresult1 = new Screen.NarratableSearchResult(narratableentry, i, narratableentry$narrationpriority);
         } else if (narratableentry$narrationpriority.compareTo(screen$narratablesearchresult != null ? screen$narratablesearchresult.f_169422_ : NarratableEntry.NarrationPriority.NONE) > 0) {
            screen$narratablesearchresult = new Screen.NarratableSearchResult(narratableentry, i, narratableentry$narrationpriority);
         }
      }

      return screen$narratablesearchresult != null ? screen$narratablesearchresult : screen$narratablesearchresult1;
   }

   public void m_169418_() {
      this.m_169380_(f_169371_, false);
   }

   @OnlyIn(Dist.CLIENT)
   public static class NarratableSearchResult {
      public final NarratableEntry f_169420_;
      public final int f_169421_;
      public final NarratableEntry.NarrationPriority f_169422_;

      public NarratableSearchResult(NarratableEntry p_169424_, int p_169425_, NarratableEntry.NarrationPriority p_169426_) {
         this.f_169420_ = p_169424_;
         this.f_169421_ = p_169425_;
         this.f_169422_ = p_169426_;
      }
   }
}
