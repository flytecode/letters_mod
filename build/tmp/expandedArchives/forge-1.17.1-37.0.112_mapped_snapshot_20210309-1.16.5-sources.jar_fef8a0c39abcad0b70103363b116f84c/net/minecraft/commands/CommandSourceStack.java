package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandSourceStack implements SharedSuggestionProvider {
   public static final SimpleCommandExceptionType f_81286_ = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.player"));
   public static final SimpleCommandExceptionType f_81287_ = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.entity"));
   private final CommandSource f_81288_;
   private final Vec3 f_81289_;
   private final ServerLevel f_81290_;
   private final int f_81291_;
   private final String f_81292_;
   private final Component f_81293_;
   private final MinecraftServer f_81294_;
   private final boolean f_81295_;
   @Nullable
   private final Entity f_81296_;
   private final ResultConsumer<CommandSourceStack> f_81297_;
   private final EntityAnchorArgument.Anchor f_81298_;
   private final Vec2 f_81299_;

   public CommandSourceStack(CommandSource p_81302_, Vec3 p_81303_, Vec2 p_81304_, ServerLevel p_81305_, int p_81306_, String p_81307_, Component p_81308_, MinecraftServer p_81309_, @Nullable Entity p_81310_) {
      this(p_81302_, p_81303_, p_81304_, p_81305_, p_81306_, p_81307_, p_81308_, p_81309_, p_81310_, false, (p_81361_, p_81362_, p_81363_) -> {
      }, EntityAnchorArgument.Anchor.FEET);
   }

   protected CommandSourceStack(CommandSource p_81312_, Vec3 p_81313_, Vec2 p_81314_, ServerLevel p_81315_, int p_81316_, String p_81317_, Component p_81318_, MinecraftServer p_81319_, @Nullable Entity p_81320_, boolean p_81321_, ResultConsumer<CommandSourceStack> p_81322_, EntityAnchorArgument.Anchor p_81323_) {
      this.f_81288_ = p_81312_;
      this.f_81289_ = p_81313_;
      this.f_81290_ = p_81315_;
      this.f_81295_ = p_81321_;
      this.f_81296_ = p_81320_;
      this.f_81291_ = p_81316_;
      this.f_81292_ = p_81317_;
      this.f_81293_ = p_81318_;
      this.f_81294_ = p_81319_;
      this.f_81297_ = p_81322_;
      this.f_81298_ = p_81323_;
      this.f_81299_ = p_81314_;
   }

   public CommandSourceStack m_165484_(CommandSource p_165485_) {
      return this.f_81288_ == p_165485_ ? this : new CommandSourceStack(p_165485_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81329_(Entity p_81330_) {
      return this.f_81296_ == p_81330_ ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, p_81330_.m_7755_().getString(), p_81330_.m_5446_(), this.f_81294_, p_81330_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81348_(Vec3 p_81349_) {
      return this.f_81289_.equals(p_81349_) ? this : new CommandSourceStack(this.f_81288_, p_81349_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81346_(Vec2 p_81347_) {
      return this.f_81299_.m_82476_(p_81347_) ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, p_81347_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81334_(ResultConsumer<CommandSourceStack> p_81335_) {
      return this.f_81297_.equals(p_81335_) ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, p_81335_, this.f_81298_);
   }

   public CommandSourceStack m_81336_(ResultConsumer<CommandSourceStack> p_81337_, BinaryOperator<ResultConsumer<CommandSourceStack>> p_81338_) {
      ResultConsumer<CommandSourceStack> resultconsumer = p_81338_.apply(this.f_81297_, p_81337_);
      return this.m_81334_(resultconsumer);
   }

   public CommandSourceStack m_81324_() {
      return !this.f_81295_ && !this.f_81288_.m_142559_() ? new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, true, this.f_81297_, this.f_81298_) : this;
   }

   public CommandSourceStack m_81325_(int p_81326_) {
      return p_81326_ == this.f_81291_ ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, p_81326_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81358_(int p_81359_) {
      return p_81359_ <= this.f_81291_ ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, p_81359_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
   }

   public CommandSourceStack m_81350_(EntityAnchorArgument.Anchor p_81351_) {
      return p_81351_ == this.f_81298_ ? this : new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, p_81351_);
   }

   public CommandSourceStack m_81327_(ServerLevel p_81328_) {
      if (p_81328_ == this.f_81290_) {
         return this;
      } else {
         double d0 = DimensionType.m_63908_(this.f_81290_.m_6042_(), p_81328_.m_6042_());
         Vec3 vec3 = new Vec3(this.f_81289_.f_82479_ * d0, this.f_81289_.f_82480_, this.f_81289_.f_82481_ * d0);
         return new CommandSourceStack(this.f_81288_, vec3, this.f_81299_, p_81328_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_);
      }
   }

   public CommandSourceStack m_81331_(Entity p_81332_, EntityAnchorArgument.Anchor p_81333_) {
      return this.m_81364_(p_81333_.m_90377_(p_81332_));
   }

   public CommandSourceStack m_81364_(Vec3 p_81365_) {
      Vec3 vec3 = this.f_81298_.m_90379_(this);
      double d0 = p_81365_.f_82479_ - vec3.f_82479_;
      double d1 = p_81365_.f_82480_ - vec3.f_82480_;
      double d2 = p_81365_.f_82481_ - vec3.f_82481_;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = Mth.m_14177_((float)(-(Mth.m_14136_(d1, d3) * (double)(180F / (float)Math.PI))));
      float f1 = Mth.m_14177_((float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F);
      return this.m_81346_(new Vec2(f, f1));
   }

   public Component m_81357_() {
      return this.f_81293_;
   }

   public String m_81368_() {
      return this.f_81292_;
   }

   public boolean m_6761_(int p_81370_) {
      return this.f_81291_ >= p_81370_;
   }

   public Vec3 m_81371_() {
      return this.f_81289_;
   }

   public ServerLevel m_81372_() {
      return this.f_81290_;
   }

   @Nullable
   public Entity m_81373_() {
      return this.f_81296_;
   }

   public Entity m_81374_() throws CommandSyntaxException {
      if (this.f_81296_ == null) {
         throw f_81287_.create();
      } else {
         return this.f_81296_;
      }
   }

   public ServerPlayer m_81375_() throws CommandSyntaxException {
      if (!(this.f_81296_ instanceof ServerPlayer)) {
         throw f_81286_.create();
      } else {
         return (ServerPlayer)this.f_81296_;
      }
   }

   public Vec2 m_81376_() {
      return this.f_81299_;
   }

   public MinecraftServer m_81377_() {
      return this.f_81294_;
   }

   public EntityAnchorArgument.Anchor m_81378_() {
      return this.f_81298_;
   }

   public void m_81354_(Component p_81355_, boolean p_81356_) {
      if (this.f_81288_.m_6999_() && !this.f_81295_) {
         this.f_81288_.m_6352_(p_81355_, Util.f_137441_);
      }

      if (p_81356_ && this.f_81288_.m_6102_() && !this.f_81295_) {
         this.m_81366_(p_81355_);
      }

   }

   private void m_81366_(Component p_81367_) {
      Component component = (new TranslatableComponent("chat.type.admin", this.m_81357_(), p_81367_)).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
      if (this.f_81294_.m_129900_().m_46207_(GameRules.f_46144_)) {
         for(ServerPlayer serverplayer : this.f_81294_.m_6846_().m_11314_()) {
            if (serverplayer != this.f_81288_ && this.f_81294_.m_6846_().m_11303_(serverplayer.m_36316_())) {
               serverplayer.m_6352_(component, Util.f_137441_);
            }
         }
      }

      if (this.f_81288_ != this.f_81294_ && this.f_81294_.m_129900_().m_46207_(GameRules.f_46141_)) {
         this.f_81294_.m_6352_(component, Util.f_137441_);
      }

   }

   public void m_81352_(Component p_81353_) {
      if (this.f_81288_.m_7028_() && !this.f_81295_) {
         this.f_81288_.m_6352_((new TextComponent("")).m_7220_(p_81353_).m_130940_(ChatFormatting.RED), Util.f_137441_);
      }

   }

   public void m_81342_(CommandContext<CommandSourceStack> p_81343_, boolean p_81344_, int p_81345_) {
      if (this.f_81297_ != null) {
         this.f_81297_.onCommandComplete(p_81343_, p_81344_, p_81345_);
      }

   }

   public Collection<String> m_5982_() {
      return Lists.newArrayList(this.f_81294_.m_7641_());
   }

   public Collection<String> m_5983_() {
      return this.f_81294_.m_129896_().m_83488_();
   }

   public Collection<ResourceLocation> m_5984_() {
      return Registry.f_122821_.m_6566_();
   }

   public Stream<ResourceLocation> m_6860_() {
      return this.f_81294_.m_129894_().m_44073_();
   }

   public CompletableFuture<Suggestions> m_5497_(CommandContext<SharedSuggestionProvider> p_81340_, SuggestionsBuilder p_81341_) {
      return null;
   }

   public Set<ResourceKey<Level>> m_6553_() {
      return this.f_81294_.m_129784_();
   }

   public RegistryAccess m_5894_() {
      return this.f_81294_.m_129911_();
   }
}