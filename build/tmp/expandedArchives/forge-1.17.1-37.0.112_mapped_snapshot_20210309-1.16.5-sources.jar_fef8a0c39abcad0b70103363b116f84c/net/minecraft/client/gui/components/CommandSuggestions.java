package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommandSuggestions {
   private static final Pattern f_93847_ = Pattern.compile("(\\s+)");
   private static final Style f_93848_ = Style.f_131099_.m_131140_(ChatFormatting.RED);
   private static final Style f_93849_ = Style.f_131099_.m_131140_(ChatFormatting.GRAY);
   private static final List<Style> f_93850_ = Stream.of(ChatFormatting.AQUA, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.LIGHT_PURPLE, ChatFormatting.GOLD).map(Style.f_131099_::m_131140_).collect(ImmutableList.toImmutableList());
   final Minecraft f_93851_;
   final Screen f_93852_;
   final EditBox f_93853_;
   final Font f_93854_;
   private final boolean f_93855_;
   private final boolean f_93856_;
   final int f_93857_;
   final int f_93858_;
   final boolean f_93859_;
   final int f_93860_;
   private final List<FormattedCharSequence> f_93861_ = Lists.newArrayList();
   private int f_93862_;
   private int f_93863_;
   @Nullable
   private ParseResults<SharedSuggestionProvider> f_93864_;
   @Nullable
   private CompletableFuture<Suggestions> f_93865_;
   @Nullable
   CommandSuggestions.SuggestionsList f_93866_;
   private boolean f_93867_;
   boolean f_93868_;

   public CommandSuggestions(Minecraft p_93871_, Screen p_93872_, EditBox p_93873_, Font p_93874_, boolean p_93875_, boolean p_93876_, int p_93877_, int p_93878_, boolean p_93879_, int p_93880_) {
      this.f_93851_ = p_93871_;
      this.f_93852_ = p_93872_;
      this.f_93853_ = p_93873_;
      this.f_93854_ = p_93874_;
      this.f_93855_ = p_93875_;
      this.f_93856_ = p_93876_;
      this.f_93857_ = p_93877_;
      this.f_93858_ = p_93878_;
      this.f_93859_ = p_93879_;
      this.f_93860_ = p_93880_;
      p_93873_.m_94149_(this::m_93914_);
   }

   public void m_93922_(boolean p_93923_) {
      this.f_93867_ = p_93923_;
      if (!p_93923_) {
         this.f_93866_ = null;
      }

   }

   public boolean m_93888_(int p_93889_, int p_93890_, int p_93891_) {
      if (this.f_93866_ != null && this.f_93866_.m_93988_(p_93889_, p_93890_, p_93891_)) {
         return true;
      } else if (this.f_93852_.m_7222_() == this.f_93853_ && p_93889_ == 258) {
         this.m_93930_(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_93882_(double p_93883_) {
      return this.f_93866_ != null && this.f_93866_.m_93971_(Mth.m_14008_(p_93883_, -1.0D, 1.0D));
   }

   public boolean m_93884_(double p_93885_, double p_93886_, int p_93887_) {
      return this.f_93866_ != null && this.f_93866_.m_93975_((int)p_93885_, (int)p_93886_, p_93887_);
   }

   public void m_93930_(boolean p_93931_) {
      if (this.f_93865_ != null && this.f_93865_.isDone()) {
         Suggestions suggestions = this.f_93865_.join();
         if (!suggestions.isEmpty()) {
            int i = 0;

            for(Suggestion suggestion : suggestions.getList()) {
               i = Math.max(i, this.f_93854_.m_92895_(suggestion.getText()));
            }

            int j = Mth.m_14045_(this.f_93853_.m_94211_(suggestions.getRange().getStart()), 0, this.f_93853_.m_94211_(0) + this.f_93853_.m_94210_() - i);
            int k = this.f_93859_ ? this.f_93852_.f_96544_ - 12 : 72;
            this.f_93866_ = new CommandSuggestions.SuggestionsList(j, k, i, this.m_93898_(suggestions), p_93931_);
         }
      }

   }

   private List<Suggestion> m_93898_(Suggestions p_93899_) {
      String s = this.f_93853_.m_94155_().substring(0, this.f_93853_.m_94207_());
      int i = m_93912_(s);
      String s1 = s.substring(i).toLowerCase(Locale.ROOT);
      List<Suggestion> list = Lists.newArrayList();
      List<Suggestion> list1 = Lists.newArrayList();

      for(Suggestion suggestion : p_93899_.getList()) {
         if (!suggestion.getText().startsWith(s1) && !suggestion.getText().startsWith("minecraft:" + s1)) {
            list1.add(suggestion);
         } else {
            list.add(suggestion);
         }
      }

      list.addAll(list1);
      return list;
   }

   public void m_93881_() {
      String s = this.f_93853_.m_94155_();
      if (this.f_93864_ != null && !this.f_93864_.getReader().getString().equals(s)) {
         this.f_93864_ = null;
      }

      if (!this.f_93868_) {
         this.f_93853_.m_94167_((String)null);
         this.f_93866_ = null;
      }

      this.f_93861_.clear();
      StringReader stringreader = new StringReader(s);
      boolean flag = stringreader.canRead() && stringreader.peek() == '/';
      if (flag) {
         stringreader.skip();
      }

      boolean flag1 = this.f_93855_ || flag;
      int i = this.f_93853_.m_94207_();
      if (flag1) {
         CommandDispatcher<SharedSuggestionProvider> commanddispatcher = this.f_93851_.f_91074_.f_108617_.m_105146_();
         if (this.f_93864_ == null) {
            this.f_93864_ = commanddispatcher.parse(stringreader, this.f_93851_.f_91074_.f_108617_.m_105137_());
         }

         int j = this.f_93856_ ? stringreader.getCursor() : 1;
         if (i >= j && (this.f_93866_ == null || !this.f_93868_)) {
            this.f_93865_ = commanddispatcher.getCompletionSuggestions(this.f_93864_, i);
            this.f_93865_.thenRun(() -> {
               if (this.f_93865_.isDone()) {
                  this.m_93932_();
               }
            });
         }
      } else {
         String s1 = s.substring(0, i);
         int k = m_93912_(s1);
         Collection<String> collection = this.f_93851_.f_91074_.f_108617_.m_105137_().m_5982_();
         this.f_93865_ = SharedSuggestionProvider.m_82970_(collection, new SuggestionsBuilder(s1, k));
      }

   }

   private static int m_93912_(String p_93913_) {
      if (Strings.isNullOrEmpty(p_93913_)) {
         return 0;
      } else {
         int i = 0;

         for(Matcher matcher = f_93847_.matcher(p_93913_); matcher.find(); i = matcher.end()) {
         }

         return i;
      }
   }

   private static FormattedCharSequence m_93896_(CommandSyntaxException p_93897_) {
      Component component = ComponentUtils.m_130729_(p_93897_.getRawMessage());
      String s = p_93897_.getContext();
      return s == null ? component.m_7532_() : (new TranslatableComponent("command.context.parse_error", component, p_93897_.getCursor(), s)).m_7532_();
   }

   private void m_93932_() {
      if (this.f_93853_.m_94207_() == this.f_93853_.m_94155_().length()) {
         if (this.f_93865_.join().isEmpty() && !this.f_93864_.getExceptions().isEmpty()) {
            int i = 0;

            for(Entry<CommandNode<SharedSuggestionProvider>, CommandSyntaxException> entry : this.f_93864_.getExceptions().entrySet()) {
               CommandSyntaxException commandsyntaxexception = entry.getValue();
               if (commandsyntaxexception.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                  ++i;
               } else {
                  this.f_93861_.add(m_93896_(commandsyntaxexception));
               }
            }

            if (i > 0) {
               this.f_93861_.add(m_93896_(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
            }
         } else if (this.f_93864_.getReader().canRead()) {
            this.f_93861_.add(m_93896_(Commands.m_82097_(this.f_93864_)));
         }
      }

      this.f_93862_ = 0;
      this.f_93863_ = this.f_93852_.f_96543_;
      if (this.f_93861_.isEmpty()) {
         this.m_93920_(ChatFormatting.GRAY);
      }

      this.f_93866_ = null;
      if (this.f_93867_ && this.f_93851_.f_91066_.f_92037_) {
         this.m_93930_(false);
      }

   }

   private void m_93920_(ChatFormatting p_93921_) {
      CommandContextBuilder<SharedSuggestionProvider> commandcontextbuilder = this.f_93864_.getContext();
      SuggestionContext<SharedSuggestionProvider> suggestioncontext = commandcontextbuilder.findSuggestionContext(this.f_93853_.m_94207_());
      Map<CommandNode<SharedSuggestionProvider>, String> map = this.f_93851_.f_91074_.f_108617_.m_105146_().getSmartUsage(suggestioncontext.parent, this.f_93851_.f_91074_.f_108617_.m_105137_());
      List<FormattedCharSequence> list = Lists.newArrayList();
      int i = 0;
      Style style = Style.f_131099_.m_131140_(p_93921_);

      for(Entry<CommandNode<SharedSuggestionProvider>, String> entry : map.entrySet()) {
         if (!(entry.getKey() instanceof LiteralCommandNode)) {
            list.add(FormattedCharSequence.m_13714_(entry.getValue(), style));
            i = Math.max(i, this.f_93854_.m_92895_(entry.getValue()));
         }
      }

      if (!list.isEmpty()) {
         this.f_93861_.addAll(list);
         this.f_93862_ = Mth.m_14045_(this.f_93853_.m_94211_(suggestioncontext.startPos), 0, this.f_93853_.m_94211_(0) + this.f_93853_.m_94210_() - i);
         this.f_93863_ = i;
      }

   }

   private FormattedCharSequence m_93914_(String p_93915_, int p_93916_) {
      return this.f_93864_ != null ? m_93892_(this.f_93864_, p_93915_, p_93916_) : FormattedCharSequence.m_13714_(p_93915_, Style.f_131099_);
   }

   @Nullable
   static String m_93927_(String p_93928_, String p_93929_) {
      return p_93929_.startsWith(p_93928_) ? p_93929_.substring(p_93928_.length()) : null;
   }

   private static FormattedCharSequence m_93892_(ParseResults<SharedSuggestionProvider> p_93893_, String p_93894_, int p_93895_) {
      List<FormattedCharSequence> list = Lists.newArrayList();
      int i = 0;
      int j = -1;
      CommandContextBuilder<SharedSuggestionProvider> commandcontextbuilder = p_93893_.getContext().getLastChild();

      for(ParsedArgument<SharedSuggestionProvider, ?> parsedargument : commandcontextbuilder.getArguments().values()) {
         ++j;
         if (j >= f_93850_.size()) {
            j = 0;
         }

         int k = Math.max(parsedargument.getRange().getStart() - p_93895_, 0);
         if (k >= p_93894_.length()) {
            break;
         }

         int l = Math.min(parsedargument.getRange().getEnd() - p_93895_, p_93894_.length());
         if (l > 0) {
            list.add(FormattedCharSequence.m_13714_(p_93894_.substring(i, k), f_93849_));
            list.add(FormattedCharSequence.m_13714_(p_93894_.substring(k, l), f_93850_.get(j)));
            i = l;
         }
      }

      if (p_93893_.getReader().canRead()) {
         int i1 = Math.max(p_93893_.getReader().getCursor() - p_93895_, 0);
         if (i1 < p_93894_.length()) {
            int j1 = Math.min(i1 + p_93893_.getReader().getRemainingLength(), p_93894_.length());
            list.add(FormattedCharSequence.m_13714_(p_93894_.substring(i, i1), f_93849_));
            list.add(FormattedCharSequence.m_13714_(p_93894_.substring(i1, j1), f_93848_));
            i = j1;
         }
      }

      list.add(FormattedCharSequence.m_13714_(p_93894_.substring(i), f_93849_));
      return FormattedCharSequence.m_13722_(list);
   }

   public void m_93900_(PoseStack p_93901_, int p_93902_, int p_93903_) {
      if (this.f_93866_ != null) {
         this.f_93866_.m_93979_(p_93901_, p_93902_, p_93903_);
      } else {
         int i = 0;

         for(FormattedCharSequence formattedcharsequence : this.f_93861_) {
            int j = this.f_93859_ ? this.f_93852_.f_96544_ - 14 - 13 - 12 * i : 72 + 12 * i;
            GuiComponent.m_93172_(p_93901_, this.f_93862_ - 1, j, this.f_93862_ + this.f_93863_ + 1, j + 12, this.f_93860_);
            this.f_93854_.m_92744_(p_93901_, formattedcharsequence, (float)this.f_93862_, (float)(j + 2), -1);
            ++i;
         }
      }

   }

   public String m_93924_() {
      return this.f_93866_ != null ? "\n" + this.f_93866_.m_168847_() : "";
   }

   @OnlyIn(Dist.CLIENT)
   public class SuggestionsList {
      private final Rect2i f_93947_;
      private final String f_93948_;
      private final List<Suggestion> f_93949_;
      private int f_93950_;
      private int f_93951_;
      private Vec2 f_93952_ = Vec2.f_82462_;
      private boolean f_93953_;
      private int f_93954_;

      SuggestionsList(int p_93957_, int p_93958_, int p_93959_, List<Suggestion> p_93960_, boolean p_93961_) {
         int i = p_93957_ - 1;
         int j = CommandSuggestions.this.f_93859_ ? p_93958_ - 3 - Math.min(p_93960_.size(), CommandSuggestions.this.f_93858_) * 12 : p_93958_;
         this.f_93947_ = new Rect2i(i, j, p_93959_ + 1, Math.min(p_93960_.size(), CommandSuggestions.this.f_93858_) * 12);
         this.f_93948_ = CommandSuggestions.this.f_93853_.m_94155_();
         this.f_93954_ = p_93961_ ? -1 : 0;
         this.f_93949_ = p_93960_;
         this.m_93986_(0);
      }

      public void m_93979_(PoseStack p_93980_, int p_93981_, int p_93982_) {
         int i = Math.min(this.f_93949_.size(), CommandSuggestions.this.f_93858_);
         int j = -5592406;
         boolean flag = this.f_93950_ > 0;
         boolean flag1 = this.f_93949_.size() > this.f_93950_ + i;
         boolean flag2 = flag || flag1;
         boolean flag3 = this.f_93952_.f_82470_ != (float)p_93981_ || this.f_93952_.f_82471_ != (float)p_93982_;
         if (flag3) {
            this.f_93952_ = new Vec2((float)p_93981_, (float)p_93982_);
         }

         if (flag2) {
            GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() - 1, this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_(), CommandSuggestions.this.f_93860_);
            GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() + this.f_93947_.m_110091_(), this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_() + this.f_93947_.m_110091_() + 1, CommandSuggestions.this.f_93860_);
            if (flag) {
               for(int k = 0; k < this.f_93947_.m_110090_(); ++k) {
                  if (k % 2 == 0) {
                     GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_() + k, this.f_93947_.m_110086_() - 1, this.f_93947_.m_110085_() + k + 1, this.f_93947_.m_110086_(), -1);
                  }
               }
            }

            if (flag1) {
               for(int i1 = 0; i1 < this.f_93947_.m_110090_(); ++i1) {
                  if (i1 % 2 == 0) {
                     GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_() + i1, this.f_93947_.m_110086_() + this.f_93947_.m_110091_(), this.f_93947_.m_110085_() + i1 + 1, this.f_93947_.m_110086_() + this.f_93947_.m_110091_() + 1, -1);
                  }
               }
            }
         }

         boolean flag4 = false;

         for(int l = 0; l < i; ++l) {
            Suggestion suggestion = this.f_93949_.get(l + this.f_93950_);
            GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() + 12 * l, this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_() + 12 * l + 12, CommandSuggestions.this.f_93860_);
            if (p_93981_ > this.f_93947_.m_110085_() && p_93981_ < this.f_93947_.m_110085_() + this.f_93947_.m_110090_() && p_93982_ > this.f_93947_.m_110086_() + 12 * l && p_93982_ < this.f_93947_.m_110086_() + 12 * l + 12) {
               if (flag3) {
                  this.m_93986_(l + this.f_93950_);
               }

               flag4 = true;
            }

            CommandSuggestions.this.f_93854_.m_92750_(p_93980_, suggestion.getText(), (float)(this.f_93947_.m_110085_() + 1), (float)(this.f_93947_.m_110086_() + 2 + 12 * l), l + this.f_93950_ == this.f_93951_ ? -256 : -5592406);
         }

         if (flag4) {
            Message message = this.f_93949_.get(this.f_93951_).getTooltip();
            if (message != null) {
               CommandSuggestions.this.f_93852_.m_96602_(p_93980_, ComponentUtils.m_130729_(message), p_93981_, p_93982_);
            }
         }

      }

      public boolean m_93975_(int p_93976_, int p_93977_, int p_93978_) {
         if (!this.f_93947_.m_110087_(p_93976_, p_93977_)) {
            return false;
         } else {
            int i = (p_93977_ - this.f_93947_.m_110086_()) / 12 + this.f_93950_;
            if (i >= 0 && i < this.f_93949_.size()) {
               this.m_93986_(i);
               this.m_93970_();
            }

            return true;
         }
      }

      public boolean m_93971_(double p_93972_) {
         int i = (int)(CommandSuggestions.this.f_93851_.f_91067_.m_91589_() * (double)CommandSuggestions.this.f_93851_.m_91268_().m_85445_() / (double)CommandSuggestions.this.f_93851_.m_91268_().m_85443_());
         int j = (int)(CommandSuggestions.this.f_93851_.f_91067_.m_91594_() * (double)CommandSuggestions.this.f_93851_.m_91268_().m_85446_() / (double)CommandSuggestions.this.f_93851_.m_91268_().m_85444_());
         if (this.f_93947_.m_110087_(i, j)) {
            this.f_93950_ = Mth.m_14045_((int)((double)this.f_93950_ - p_93972_), 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean m_93988_(int p_93989_, int p_93990_, int p_93991_) {
         if (p_93989_ == 265) {
            this.m_93973_(-1);
            this.f_93953_ = false;
            return true;
         } else if (p_93989_ == 264) {
            this.m_93973_(1);
            this.f_93953_ = false;
            return true;
         } else if (p_93989_ == 258) {
            if (this.f_93953_) {
               this.m_93973_(Screen.m_96638_() ? -1 : 1);
            }

            this.m_93970_();
            return true;
         } else if (p_93989_ == 256) {
            this.m_93985_();
            return true;
         } else {
            return false;
         }
      }

      public void m_93973_(int p_93974_) {
         this.m_93986_(this.f_93951_ + p_93974_);
         int i = this.f_93950_;
         int j = this.f_93950_ + CommandSuggestions.this.f_93858_ - 1;
         if (this.f_93951_ < i) {
            this.f_93950_ = Mth.m_14045_(this.f_93951_, 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
         } else if (this.f_93951_ > j) {
            this.f_93950_ = Mth.m_14045_(this.f_93951_ + CommandSuggestions.this.f_93857_ - CommandSuggestions.this.f_93858_, 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
         }

      }

      public void m_93986_(int p_93987_) {
         this.f_93951_ = p_93987_;
         if (this.f_93951_ < 0) {
            this.f_93951_ += this.f_93949_.size();
         }

         if (this.f_93951_ >= this.f_93949_.size()) {
            this.f_93951_ -= this.f_93949_.size();
         }

         Suggestion suggestion = this.f_93949_.get(this.f_93951_);
         CommandSuggestions.this.f_93853_.m_94167_(CommandSuggestions.m_93927_(CommandSuggestions.this.f_93853_.m_94155_(), suggestion.apply(this.f_93948_)));
         if (this.f_93954_ != this.f_93951_) {
            NarratorChatListener.f_93311_.m_168785_(this.m_168847_());
         }

      }

      public void m_93970_() {
         Suggestion suggestion = this.f_93949_.get(this.f_93951_);
         CommandSuggestions.this.f_93868_ = true;
         CommandSuggestions.this.f_93853_.m_94144_(suggestion.apply(this.f_93948_));
         int i = suggestion.getRange().getStart() + suggestion.getText().length();
         CommandSuggestions.this.f_93853_.m_94196_(i);
         CommandSuggestions.this.f_93853_.m_94208_(i);
         this.m_93986_(this.f_93951_);
         CommandSuggestions.this.f_93868_ = false;
         this.f_93953_ = true;
      }

      Component m_168847_() {
         this.f_93954_ = this.f_93951_;
         Suggestion suggestion = this.f_93949_.get(this.f_93951_);
         Message message = suggestion.getTooltip();
         return message != null ? new TranslatableComponent("narration.suggestion.tooltip", this.f_93951_ + 1, this.f_93949_.size(), suggestion.getText(), message) : new TranslatableComponent("narration.suggestion", this.f_93951_ + 1, this.f_93949_.size(), suggestion.getText());
      }

      public void m_93985_() {
         CommandSuggestions.this.f_93866_ = null;
      }
   }
}