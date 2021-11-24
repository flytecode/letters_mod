package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class NbtComponent extends BaseComponent implements ContextAwareComponent {
   private static final Logger f_130953_ = LogManager.getLogger();
   protected final boolean f_130950_;
   protected final Optional<Component> f_178447_;
   protected final String f_130951_;
   @Nullable
   protected final NbtPathArgument.NbtPath f_130952_;

   @Nullable
   private static NbtPathArgument.NbtPath m_130977_(String p_130978_) {
      try {
         return (new NbtPathArgument()).parse(new StringReader(p_130978_));
      } catch (CommandSyntaxException commandsyntaxexception) {
         return null;
      }
   }

   public NbtComponent(String p_178454_, boolean p_178455_, Optional<Component> p_178456_) {
      this(p_178454_, m_130977_(p_178454_), p_178455_, p_178456_);
   }

   protected NbtComponent(String p_178449_, @Nullable NbtPathArgument.NbtPath p_178450_, boolean p_178451_, Optional<Component> p_178452_) {
      this.f_130951_ = p_178449_;
      this.f_130952_ = p_178450_;
      this.f_130950_ = p_178451_;
      this.f_178447_ = p_178452_;
   }

   protected abstract Stream<CompoundTag> m_7353_(CommandSourceStack p_130962_) throws CommandSyntaxException;

   public String m_130979_() {
      return this.f_130951_;
   }

   public boolean m_130980_() {
      return this.f_130950_;
   }

   public MutableComponent m_5638_(@Nullable CommandSourceStack p_130964_, @Nullable Entity p_130965_, int p_130966_) throws CommandSyntaxException {
      if (p_130964_ != null && this.f_130952_ != null) {
         Stream<String> stream = this.m_7353_(p_130964_).flatMap((p_130973_) -> {
            try {
               return this.f_130952_.m_99638_(p_130973_).stream();
            } catch (CommandSyntaxException commandsyntaxexception) {
               return Stream.empty();
            }
         }).map(Tag::m_7916_);
         if (this.f_130950_) {
            Component component = DataFixUtils.orElse(ComponentUtils.m_178424_(p_130964_, this.f_178447_, p_130965_, p_130966_), ComponentUtils.f_178421_);
            return stream.flatMap((p_130971_) -> {
               try {
                  MutableComponent mutablecomponent = Component.Serializer.m_130701_(p_130971_);
                  return Stream.of(ComponentUtils.m_130731_(p_130964_, mutablecomponent, p_130965_, p_130966_));
               } catch (Exception exception) {
                  f_130953_.warn("Failed to parse component: {}", p_130971_, exception);
                  return Stream.of();
               }
            }).reduce((p_178464_, p_178465_) -> {
               return p_178464_.m_7220_(component).m_7220_(p_178465_);
            }).orElseGet(() -> {
               return new TextComponent("");
            });
         } else {
            return ComponentUtils.m_178424_(p_130964_, this.f_178447_, p_130965_, p_130966_).map((p_178461_) -> {
               return stream.map((p_178471_) -> {
                  return (net.minecraft.network.chat.MutableComponent)new TextComponent(p_178471_);
               }).reduce((p_178468_, p_178469_) -> {
                  return p_178468_.m_7220_(p_178461_).m_7220_(p_178469_);
               }).orElseGet(() -> {
                  return new TextComponent("");
               });
            }).orElseGet(() -> {
               return new TextComponent(stream.collect(Collectors.joining(", ")));
            });
         }
      } else {
         return new TextComponent("");
      }
   }

   public static class BlockNbtComponent extends NbtComponent {
      private final String f_130981_;
      @Nullable
      private final Coordinates f_130982_;

      public BlockNbtComponent(String p_178482_, boolean p_178483_, String p_178484_, Optional<Component> p_178485_) {
         super(p_178482_, p_178483_, p_178485_);
         this.f_130981_ = p_178484_;
         this.f_130982_ = this.m_130996_(this.f_130981_);
      }

      @Nullable
      private Coordinates m_130996_(String p_130997_) {
         try {
            return BlockPosArgument.m_118239_().parse(new StringReader(p_130997_));
         } catch (CommandSyntaxException commandsyntaxexception) {
            return null;
         }
      }

      private BlockNbtComponent(String p_178475_, @Nullable NbtPathArgument.NbtPath p_178476_, boolean p_178477_, String p_178478_, @Nullable Coordinates p_178479_, Optional<Component> p_178480_) {
         super(p_178475_, p_178476_, p_178477_, p_178480_);
         this.f_130981_ = p_178478_;
         this.f_130982_ = p_178479_;
      }

      @Nullable
      public String m_131001_() {
         return this.f_130981_;
      }

      public NbtComponent.BlockNbtComponent m_6879_() {
         return new NbtComponent.BlockNbtComponent(this.f_130951_, this.f_130952_, this.f_130950_, this.f_130981_, this.f_130982_, this.f_178447_);
      }

      protected Stream<CompoundTag> m_7353_(CommandSourceStack p_130994_) {
         if (this.f_130982_ != null) {
            ServerLevel serverlevel = p_130994_.m_81372_();
            BlockPos blockpos = this.f_130982_.m_119568_(p_130994_);
            if (serverlevel.m_46749_(blockpos)) {
               BlockEntity blockentity = serverlevel.m_7702_(blockpos);
               if (blockentity != null) {
                  return Stream.of(blockentity.m_6945_(new CompoundTag()));
               }
            }
         }

         return Stream.empty();
      }

      public boolean equals(Object p_130999_) {
         if (this == p_130999_) {
            return true;
         } else if (!(p_130999_ instanceof NbtComponent.BlockNbtComponent)) {
            return false;
         } else {
            NbtComponent.BlockNbtComponent nbtcomponent$blocknbtcomponent = (NbtComponent.BlockNbtComponent)p_130999_;
            return Objects.equals(this.f_130981_, nbtcomponent$blocknbtcomponent.f_130981_) && Objects.equals(this.f_130951_, nbtcomponent$blocknbtcomponent.f_130951_) && super.equals(p_130999_);
         }
      }

      public String toString() {
         return "BlockPosArgument{pos='" + this.f_130981_ + "'path='" + this.f_130951_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
      }
   }

   public static class EntityNbtComponent extends NbtComponent {
      private final String f_131004_;
      @Nullable
      private final EntitySelector f_131005_;

      public EntityNbtComponent(String p_178494_, boolean p_178495_, String p_178496_, Optional<Component> p_178497_) {
         super(p_178494_, p_178495_, p_178497_);
         this.f_131004_ = p_178496_;
         this.f_131005_ = m_131019_(p_178496_);
      }

      @Nullable
      private static EntitySelector m_131019_(String p_131020_) {
         try {
            EntitySelectorParser entityselectorparser = new EntitySelectorParser(new StringReader(p_131020_));
            return entityselectorparser.m_121377_();
         } catch (CommandSyntaxException commandsyntaxexception) {
            return null;
         }
      }

      private EntityNbtComponent(String p_178487_, @Nullable NbtPathArgument.NbtPath p_178488_, boolean p_178489_, String p_178490_, @Nullable EntitySelector p_178491_, Optional<Component> p_178492_) {
         super(p_178487_, p_178488_, p_178489_, p_178492_);
         this.f_131004_ = p_178490_;
         this.f_131005_ = p_178491_;
      }

      public String m_131024_() {
         return this.f_131004_;
      }

      public NbtComponent.EntityNbtComponent m_6879_() {
         return new NbtComponent.EntityNbtComponent(this.f_130951_, this.f_130952_, this.f_130950_, this.f_131004_, this.f_131005_, this.f_178447_);
      }

      protected Stream<CompoundTag> m_7353_(CommandSourceStack p_131017_) throws CommandSyntaxException {
         if (this.f_131005_ != null) {
            List<? extends Entity> list = this.f_131005_.m_121160_(p_131017_);
            return list.stream().map(NbtPredicate::m_57485_);
         } else {
            return Stream.empty();
         }
      }

      public boolean equals(Object p_131022_) {
         if (this == p_131022_) {
            return true;
         } else if (!(p_131022_ instanceof NbtComponent.EntityNbtComponent)) {
            return false;
         } else {
            NbtComponent.EntityNbtComponent nbtcomponent$entitynbtcomponent = (NbtComponent.EntityNbtComponent)p_131022_;
            return Objects.equals(this.f_131004_, nbtcomponent$entitynbtcomponent.f_131004_) && Objects.equals(this.f_130951_, nbtcomponent$entitynbtcomponent.f_130951_) && super.equals(p_131022_);
         }
      }

      public String toString() {
         return "EntityNbtComponent{selector='" + this.f_131004_ + "'path='" + this.f_130951_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
      }
   }

   public static class StorageNbtComponent extends NbtComponent {
      private final ResourceLocation f_131027_;

      public StorageNbtComponent(String p_178505_, boolean p_178506_, ResourceLocation p_178507_, Optional<Component> p_178508_) {
         super(p_178505_, p_178506_, p_178508_);
         this.f_131027_ = p_178507_;
      }

      public StorageNbtComponent(String p_178499_, @Nullable NbtPathArgument.NbtPath p_178500_, boolean p_178501_, ResourceLocation p_178502_, Optional<Component> p_178503_) {
         super(p_178499_, p_178500_, p_178501_, p_178503_);
         this.f_131027_ = p_178502_;
      }

      public ResourceLocation m_131043_() {
         return this.f_131027_;
      }

      public NbtComponent.StorageNbtComponent m_6879_() {
         return new NbtComponent.StorageNbtComponent(this.f_130951_, this.f_130952_, this.f_130950_, this.f_131027_, this.f_178447_);
      }

      protected Stream<CompoundTag> m_7353_(CommandSourceStack p_131038_) {
         CompoundTag compoundtag = p_131038_.m_81377_().m_129897_().m_78044_(this.f_131027_);
         return Stream.of(compoundtag);
      }

      public boolean equals(Object p_131041_) {
         if (this == p_131041_) {
            return true;
         } else if (!(p_131041_ instanceof NbtComponent.StorageNbtComponent)) {
            return false;
         } else {
            NbtComponent.StorageNbtComponent nbtcomponent$storagenbtcomponent = (NbtComponent.StorageNbtComponent)p_131041_;
            return Objects.equals(this.f_131027_, nbtcomponent$storagenbtcomponent.f_131027_) && Objects.equals(this.f_130951_, nbtcomponent$storagenbtcomponent.f_130951_) && super.equals(p_131041_);
         }
      }

      public String toString() {
         return "StorageNbtComponent{id='" + this.f_131027_ + "'path='" + this.f_130951_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
      }
   }
}