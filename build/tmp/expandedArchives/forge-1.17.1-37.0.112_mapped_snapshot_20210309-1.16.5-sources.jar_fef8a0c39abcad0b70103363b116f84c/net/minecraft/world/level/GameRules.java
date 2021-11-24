package net.minecraft.world.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRules {
   public static final int f_151487_ = 3;
   static final Logger f_46128_ = LogManager.getLogger();
   private static final Map<GameRules.Key<?>, GameRules.Type<?>> f_46129_ = Maps.newTreeMap(Comparator.comparing((p_46218_) -> {
      return p_46218_.f_46323_;
   }));
   public static final GameRules.Key<GameRules.BooleanValue> f_46131_ = m_46189_("doFireTick", GameRules.Category.UPDATES, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46132_ = m_46189_("mobGriefing", GameRules.Category.MOBS, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46133_ = m_46189_("keepInventory", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(false));
   public static final GameRules.Key<GameRules.BooleanValue> f_46134_ = m_46189_("doMobSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46135_ = m_46189_("doMobLoot", GameRules.Category.DROPS, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46136_ = m_46189_("doTileDrops", GameRules.Category.DROPS, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46137_ = m_46189_("doEntityDrops", GameRules.Category.DROPS, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46138_ = m_46189_("commandBlockOutput", GameRules.Category.CHAT, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46139_ = m_46189_("naturalRegeneration", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46140_ = m_46189_("doDaylightCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46141_ = m_46189_("logAdminCommands", GameRules.Category.CHAT, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46142_ = m_46189_("showDeathMessages", GameRules.Category.CHAT, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.IntegerValue> f_46143_ = m_46189_("randomTickSpeed", GameRules.Category.UPDATES, GameRules.IntegerValue.m_46312_(3));
   public static final GameRules.Key<GameRules.BooleanValue> f_46144_ = m_46189_("sendCommandFeedback", GameRules.Category.CHAT, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46145_ = m_46189_("reducedDebugInfo", GameRules.Category.MISC, GameRules.BooleanValue.m_46252_(false, (p_46212_, p_46213_) -> {
      byte b0 = (byte)(p_46213_.m_46223_() ? 22 : 23);

      for(ServerPlayer serverplayer : p_46212_.m_6846_().m_11314_()) {
         serverplayer.f_8906_.m_141995_(new ClientboundEntityEventPacket(serverplayer, b0));
      }

   }));
   public static final GameRules.Key<GameRules.BooleanValue> f_46146_ = m_46189_("spectatorsGenerateChunks", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.IntegerValue> f_46147_ = m_46189_("spawnRadius", GameRules.Category.PLAYER, GameRules.IntegerValue.m_46312_(10));
   public static final GameRules.Key<GameRules.BooleanValue> f_46148_ = m_46189_("disableElytraMovementCheck", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(false));
   public static final GameRules.Key<GameRules.IntegerValue> f_46149_ = m_46189_("maxEntityCramming", GameRules.Category.MOBS, GameRules.IntegerValue.m_46312_(24));
   public static final GameRules.Key<GameRules.BooleanValue> f_46150_ = m_46189_("doWeatherCycle", GameRules.Category.UPDATES, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46151_ = m_46189_("doLimitedCrafting", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(false));
   public static final GameRules.Key<GameRules.IntegerValue> f_46152_ = m_46189_("maxCommandChainLength", GameRules.Category.MISC, GameRules.IntegerValue.m_46312_(65536));
   public static final GameRules.Key<GameRules.BooleanValue> f_46153_ = m_46189_("announceAdvancements", GameRules.Category.CHAT, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46154_ = m_46189_("disableRaids", GameRules.Category.MOBS, GameRules.BooleanValue.m_46250_(false));
   public static final GameRules.Key<GameRules.BooleanValue> f_46155_ = m_46189_("doInsomnia", GameRules.Category.SPAWNING, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46156_ = m_46189_("doImmediateRespawn", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46252_(false, (p_46200_, p_46201_) -> {
      for(ServerPlayer serverplayer : p_46200_.m_6846_().m_11314_()) {
         serverplayer.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132164_, p_46201_.m_46223_() ? 1.0F : 0.0F));
      }

   }));
   public static final GameRules.Key<GameRules.BooleanValue> f_46121_ = m_46189_("drowningDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46122_ = m_46189_("fallDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46123_ = m_46189_("fireDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_151485_ = m_46189_("freezeDamage", GameRules.Category.PLAYER, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46124_ = m_46189_("doPatrolSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46125_ = m_46189_("doTraderSpawning", GameRules.Category.SPAWNING, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46126_ = m_46189_("forgiveDeadPlayers", GameRules.Category.MOBS, GameRules.BooleanValue.m_46250_(true));
   public static final GameRules.Key<GameRules.BooleanValue> f_46127_ = m_46189_("universalAnger", GameRules.Category.MOBS, GameRules.BooleanValue.m_46250_(false));
   public static final GameRules.Key<GameRules.IntegerValue> f_151486_ = m_46189_("playersSleepingPercentage", GameRules.Category.PLAYER, GameRules.IntegerValue.m_46312_(100));
   private final Map<GameRules.Key<?>, GameRules.Value<?>> f_46130_;

   public static <T extends GameRules.Value<T>> GameRules.Key<T> m_46189_(String p_46190_, GameRules.Category p_46191_, GameRules.Type<T> p_46192_) {
      GameRules.Key<T> key = new GameRules.Key<>(p_46190_, p_46191_);
      GameRules.Type<?> type = f_46129_.put(key, p_46192_);
      if (type != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + p_46190_);
      } else {
         return key;
      }
   }

   public GameRules(DynamicLike<?> p_46160_) {
      this();
      this.m_46183_(p_46160_);
   }

   public GameRules() {
      this.f_46130_ = f_46129_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_46210_) -> {
         return p_46210_.getValue().m_46352_();
      }));
   }

   private GameRules(Map<GameRules.Key<?>, GameRules.Value<?>> p_46162_) {
      this.f_46130_ = p_46162_;
   }

   public <T extends GameRules.Value<T>> T m_46170_(GameRules.Key<T> p_46171_) {
      return (T)(this.f_46130_.get(p_46171_));
   }

   public CompoundTag m_46163_() {
      CompoundTag compoundtag = new CompoundTag();
      this.f_46130_.forEach((p_46197_, p_46198_) -> {
         compoundtag.m_128359_(p_46197_.f_46323_, p_46198_.m_5831_());
      });
      return compoundtag;
   }

   private void m_46183_(DynamicLike<?> p_46184_) {
      this.f_46130_.forEach((p_46187_, p_46188_) -> {
         p_46184_.get(p_46187_.f_46323_).asString().result().ifPresent(p_46188_::m_7377_);
      });
   }

   public GameRules m_46202_() {
      return new GameRules(this.f_46130_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_46194_) -> {
         return p_46194_.getValue().m_5590_();
      })));
   }

   public static void m_46164_(GameRules.GameRuleTypeVisitor p_46165_) {
      f_46129_.forEach((p_46205_, p_46206_) -> {
         m_46166_(p_46165_, p_46205_, p_46206_);
      });
   }

   private static <T extends GameRules.Value<T>> void m_46166_(GameRules.GameRuleTypeVisitor p_46167_, GameRules.Key<?> p_46168_, GameRules.Type<?> p_46169_) {
      p_46167_.m_6889_((GameRules.Key<T>)p_46168_, (GameRules.Type<T>)p_46169_);
      ((GameRules.Type<T>)p_46169_).m_46353_(p_46167_, (GameRules.Key<T>)p_46168_);
   }

   public void m_46176_(GameRules p_46177_, @Nullable MinecraftServer p_46178_) {
      p_46177_.f_46130_.keySet().forEach((p_46182_) -> {
         this.m_46172_(p_46182_, p_46177_, p_46178_);
      });
   }

   private <T extends GameRules.Value<T>> void m_46172_(GameRules.Key<T> p_46173_, GameRules p_46174_, @Nullable MinecraftServer p_46175_) {
      T t = p_46174_.m_46170_(p_46173_);
      this.<T>m_46170_(p_46173_).m_5614_(t, p_46175_);
   }

   public boolean m_46207_(GameRules.Key<GameRules.BooleanValue> p_46208_) {
      return this.m_46170_(p_46208_).m_46223_();
   }

   public int m_46215_(GameRules.Key<GameRules.IntegerValue> p_46216_) {
      return this.m_46170_(p_46216_).m_46288_();
   }

   public static class BooleanValue extends GameRules.Value<GameRules.BooleanValue> {
      private boolean f_46219_;

      static GameRules.Type<GameRules.BooleanValue> m_46252_(boolean p_46253_, BiConsumer<MinecraftServer, GameRules.BooleanValue> p_46254_) {
         return new GameRules.Type<>(BoolArgumentType::bool, (p_46242_) -> {
            return new GameRules.BooleanValue(p_46242_, p_46253_);
         }, p_46254_, GameRules.GameRuleTypeVisitor::m_6891_);
      }

      static GameRules.Type<GameRules.BooleanValue> m_46250_(boolean p_46251_) {
         return m_46252_(p_46251_, (p_46236_, p_46237_) -> {
         });
      }

      public BooleanValue(GameRules.Type<GameRules.BooleanValue> p_46221_, boolean p_46222_) {
         super(p_46221_);
         this.f_46219_ = p_46222_;
      }

      protected void m_5528_(CommandContext<CommandSourceStack> p_46231_, String p_46232_) {
         this.f_46219_ = BoolArgumentType.getBool(p_46231_, p_46232_);
      }

      public boolean m_46223_() {
         return this.f_46219_;
      }

      public void m_46246_(boolean p_46247_, @Nullable MinecraftServer p_46248_) {
         this.f_46219_ = p_46247_;
         this.m_46368_(p_46248_);
      }

      public String m_5831_() {
         return Boolean.toString(this.f_46219_);
      }

      protected void m_7377_(String p_46234_) {
         this.f_46219_ = Boolean.parseBoolean(p_46234_);
      }

      public int m_6855_() {
         return this.f_46219_ ? 1 : 0;
      }

      protected GameRules.BooleanValue m_5589_() {
         return this;
      }

      protected GameRules.BooleanValue m_5590_() {
         return new GameRules.BooleanValue(this.f_46360_, this.f_46219_);
      }

      public void m_5614_(GameRules.BooleanValue p_46225_, @Nullable MinecraftServer p_46226_) {
         this.f_46219_ = p_46225_.f_46219_;
         this.m_46368_(p_46226_);
      }
   }

   public static enum Category {
      PLAYER("gamerule.category.player"),
      MOBS("gamerule.category.mobs"),
      SPAWNING("gamerule.category.spawning"),
      DROPS("gamerule.category.drops"),
      UPDATES("gamerule.category.updates"),
      CHAT("gamerule.category.chat"),
      MISC("gamerule.category.misc");

      private final String f_46267_;

      private Category(String p_46273_) {
         this.f_46267_ = p_46273_;
      }

      public String m_46274_() {
         return this.f_46267_;
      }
   }

   public interface GameRuleTypeVisitor {
      default <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_46278_, GameRules.Type<T> p_46279_) {
      }

      default void m_6891_(GameRules.Key<GameRules.BooleanValue> p_46280_, GameRules.Type<GameRules.BooleanValue> p_46281_) {
      }

      default void m_6894_(GameRules.Key<GameRules.IntegerValue> p_46282_, GameRules.Type<GameRules.IntegerValue> p_46283_) {
      }
   }

   public static class IntegerValue extends GameRules.Value<GameRules.IntegerValue> {
      private int f_46284_;

      private static GameRules.Type<GameRules.IntegerValue> m_46294_(int p_46295_, BiConsumer<MinecraftServer, GameRules.IntegerValue> p_46296_) {
         return new GameRules.Type<>(IntegerArgumentType::integer, (p_46293_) -> {
            return new GameRules.IntegerValue(p_46293_, p_46295_);
         }, p_46296_, GameRules.GameRuleTypeVisitor::m_6894_);
      }

      static GameRules.Type<GameRules.IntegerValue> m_46312_(int p_46313_) {
         return m_46294_(p_46313_, (p_46309_, p_46310_) -> {
         });
      }

      public IntegerValue(GameRules.Type<GameRules.IntegerValue> p_46286_, int p_46287_) {
         super(p_46286_);
         this.f_46284_ = p_46287_;
      }

      protected void m_5528_(CommandContext<CommandSourceStack> p_46304_, String p_46305_) {
         this.f_46284_ = IntegerArgumentType.getInteger(p_46304_, p_46305_);
      }

      public int m_46288_() {
         return this.f_46284_;
      }

      public void m_151489_(int p_151490_, @Nullable MinecraftServer p_151491_) {
         this.f_46284_ = p_151490_;
         this.m_46368_(p_151491_);
      }

      public String m_5831_() {
         return Integer.toString(this.f_46284_);
      }

      protected void m_7377_(String p_46307_) {
         this.f_46284_ = m_46317_(p_46307_);
      }

      public boolean m_46314_(String p_46315_) {
         try {
            this.f_46284_ = Integer.parseInt(p_46315_);
            return true;
         } catch (NumberFormatException numberformatexception) {
            return false;
         }
      }

      private static int m_46317_(String p_46318_) {
         if (!p_46318_.isEmpty()) {
            try {
               return Integer.parseInt(p_46318_);
            } catch (NumberFormatException numberformatexception) {
               GameRules.f_46128_.warn("Failed to parse integer {}", (Object)p_46318_);
            }
         }

         return 0;
      }

      public int m_6855_() {
         return this.f_46284_;
      }

      protected GameRules.IntegerValue m_5589_() {
         return this;
      }

      protected GameRules.IntegerValue m_5590_() {
         return new GameRules.IntegerValue(this.f_46360_, this.f_46284_);
      }

      public void m_5614_(GameRules.IntegerValue p_46298_, @Nullable MinecraftServer p_46299_) {
         this.f_46284_ = p_46298_.f_46284_;
         this.m_46368_(p_46299_);
      }
   }

   public static final class Key<T extends GameRules.Value<T>> {
      final String f_46323_;
      private final GameRules.Category f_46324_;

      public Key(String p_46326_, GameRules.Category p_46327_) {
         this.f_46323_ = p_46326_;
         this.f_46324_ = p_46327_;
      }

      public String toString() {
         return this.f_46323_;
      }

      public boolean equals(Object p_46334_) {
         if (this == p_46334_) {
            return true;
         } else {
            return p_46334_ instanceof GameRules.Key && ((GameRules.Key)p_46334_).f_46323_.equals(this.f_46323_);
         }
      }

      public int hashCode() {
         return this.f_46323_.hashCode();
      }

      public String m_46328_() {
         return this.f_46323_;
      }

      public String m_46331_() {
         return "gamerule." + this.f_46323_;
      }

      public GameRules.Category m_46332_() {
         return this.f_46324_;
      }
   }

   public static class Type<T extends GameRules.Value<T>> {
      private final Supplier<ArgumentType<?>> f_46337_;
      private final Function<GameRules.Type<T>, T> f_46338_;
      final BiConsumer<MinecraftServer, T> f_46339_;
      private final GameRules.VisitorCaller<T> f_46340_;

      Type(Supplier<ArgumentType<?>> p_46342_, Function<GameRules.Type<T>, T> p_46343_, BiConsumer<MinecraftServer, T> p_46344_, GameRules.VisitorCaller<T> p_46345_) {
         this.f_46337_ = p_46342_;
         this.f_46338_ = p_46343_;
         this.f_46339_ = p_46344_;
         this.f_46340_ = p_46345_;
      }

      public RequiredArgumentBuilder<CommandSourceStack, ?> m_46358_(String p_46359_) {
         return Commands.m_82129_(p_46359_, this.f_46337_.get());
      }

      public T m_46352_() {
         return this.f_46338_.apply(this);
      }

      public void m_46353_(GameRules.GameRuleTypeVisitor p_46354_, GameRules.Key<T> p_46355_) {
         this.f_46340_.m_46374_(p_46354_, p_46355_, this);
      }
   }

   public abstract static class Value<T extends GameRules.Value<T>> {
      protected final GameRules.Type<T> f_46360_;

      public Value(GameRules.Type<T> p_46362_) {
         this.f_46360_ = p_46362_;
      }

      protected abstract void m_5528_(CommandContext<CommandSourceStack> p_46365_, String p_46366_);

      public void m_46370_(CommandContext<CommandSourceStack> p_46371_, String p_46372_) {
         this.m_5528_(p_46371_, p_46372_);
         this.m_46368_(p_46371_.getSource().m_81377_());
      }

      protected void m_46368_(@Nullable MinecraftServer p_46369_) {
         if (p_46369_ != null) {
            this.f_46360_.f_46339_.accept(p_46369_, this.m_5589_());
         }

      }

      protected abstract void m_7377_(String p_46367_);

      public abstract String m_5831_();

      public String toString() {
         return this.m_5831_();
      }

      public abstract int m_6855_();

      protected abstract T m_5589_();

      protected abstract T m_5590_();

      public abstract void m_5614_(T p_46363_, @Nullable MinecraftServer p_46364_);
   }

   interface VisitorCaller<T extends GameRules.Value<T>> {
      void m_46374_(GameRules.GameRuleTypeVisitor p_46375_, GameRules.Key<T> p_46376_, GameRules.Type<T> p_46377_);
   }
}