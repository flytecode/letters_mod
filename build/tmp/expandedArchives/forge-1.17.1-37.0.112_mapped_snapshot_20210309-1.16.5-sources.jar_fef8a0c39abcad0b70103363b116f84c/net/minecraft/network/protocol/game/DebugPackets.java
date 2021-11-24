package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.pathfinder.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugPackets {
   private static final Logger f_133672_ = LogManager.getLogger();

   public static void m_133682_(ServerLevel p_133683_, BlockPos p_133684_, String p_133685_, int p_133686_, int p_133687_) {
      FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(Unpooled.buffer());
      friendlybytebuf.m_130064_(p_133684_);
      friendlybytebuf.writeInt(p_133686_);
      friendlybytebuf.m_130070_(p_133685_);
      friendlybytebuf.writeInt(p_133687_);
      m_133691_(p_133683_, friendlybytebuf, ClientboundCustomPayloadPacket.f_132026_);
   }

   public static void m_133674_(ServerLevel p_133675_) {
      FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(Unpooled.buffer());
      m_133691_(p_133675_, friendlybytebuf, ClientboundCustomPayloadPacket.f_132027_);
   }

   public static void m_133676_(ServerLevel p_133677_, ChunkPos p_133678_) {
   }

   public static void m_133679_(ServerLevel p_133680_, BlockPos p_133681_) {
      m_133722_(p_133680_, p_133681_);
   }

   public static void m_133716_(ServerLevel p_133717_, BlockPos p_133718_) {
      m_133722_(p_133717_, p_133718_);
   }

   public static void m_133719_(ServerLevel p_133720_, BlockPos p_133721_) {
      m_133722_(p_133720_, p_133721_);
   }

   private static void m_133722_(ServerLevel p_133723_, BlockPos p_133724_) {
   }

   public static void m_133703_(Level p_133704_, Mob p_133705_, @Nullable Path p_133706_, float p_133707_) {
   }

   public static void m_133708_(Level p_133709_, BlockPos p_133710_) {
   }

   public static void m_133711_(WorldGenLevel p_133712_, StructureStart<?> p_133713_) {
   }

   public static void m_133699_(Level p_133700_, Mob p_133701_, GoalSelector p_133702_) {
      if (p_133700_ instanceof ServerLevel) {
         ;
      }
   }

   public static void m_133688_(ServerLevel p_133689_, Collection<Raid> p_133690_) {
   }

   public static void m_133695_(LivingEntity p_133696_) {
   }

   public static void m_133697_(Bee p_133698_) {
   }

   public static void m_179503_(Level p_179504_, GameEvent p_179505_, BlockPos p_179506_) {
   }

   public static void m_179507_(Level p_179508_, GameEventListener p_179509_) {
   }

   public static void m_179510_(Level p_179511_, BlockPos p_179512_, BlockState p_179513_, BeehiveBlockEntity p_179514_) {
   }

   private static void m_179498_(LivingEntity p_179499_, FriendlyByteBuf p_179500_) {
      Brain<?> brain = p_179499_.m_6274_();
      long i = p_179499_.f_19853_.m_46467_();
      if (p_179499_ instanceof InventoryCarrier) {
         Container container = ((InventoryCarrier)p_179499_).m_141944_();
         p_179500_.m_130070_(container.m_7983_() ? "" : container.toString());
      } else {
         p_179500_.m_130070_("");
      }

      if (brain.m_21874_(MemoryModuleType.f_26377_)) {
         p_179500_.writeBoolean(true);
         Path path = brain.m_21952_(MemoryModuleType.f_26377_).get();
         path.m_164704_(p_179500_);
      } else {
         p_179500_.writeBoolean(false);
      }

      if (p_179499_ instanceof Villager) {
         Villager villager = (Villager)p_179499_;
         boolean flag = villager.m_35392_(i);
         p_179500_.writeBoolean(flag);
      } else {
         p_179500_.writeBoolean(false);
      }

      p_179500_.m_178352_(brain.m_147340_(), (p_179531_, p_179532_) -> {
         p_179531_.m_130070_(p_179532_.m_37998_());
      });
      Set<String> set = brain.m_21956_().stream().map(Behavior::toString).collect(Collectors.toSet());
      p_179500_.m_178352_(set, FriendlyByteBuf::m_130070_);
      p_179500_.m_178352_(m_179495_(p_179499_, i), (p_179534_, p_179535_) -> {
         String s = StringUtil.m_144998_(p_179535_, 255, true);
         p_179534_.m_130070_(s);
      });
      if (p_179499_ instanceof Villager) {
         Set<BlockPos> set1 = Stream.of(MemoryModuleType.f_26360_, MemoryModuleType.f_26359_, MemoryModuleType.f_26362_).map(brain::m_21952_).flatMap(Util::m_137519_).map(GlobalPos::m_122646_).collect(Collectors.toSet());
         p_179500_.m_178352_(set1, FriendlyByteBuf::m_130064_);
      } else {
         p_179500_.m_130130_(0);
      }

      if (p_179499_ instanceof Villager) {
         Set<BlockPos> set2 = Stream.of(MemoryModuleType.f_26361_).map(brain::m_21952_).flatMap(Util::m_137519_).map(GlobalPos::m_122646_).collect(Collectors.toSet());
         p_179500_.m_178352_(set2, FriendlyByteBuf::m_130064_);
      } else {
         p_179500_.m_130130_(0);
      }

      if (p_179499_ instanceof Villager) {
         Map<UUID, Object2IntMap<GossipType>> map = ((Villager)p_179499_).m_35517_().m_148159_();
         List<String> list = Lists.newArrayList();
         map.forEach((p_179522_, p_179523_) -> {
            String s = DebugEntityNameGenerator.m_133668_(p_179522_);
            p_179523_.forEach((p_179518_, p_179519_) -> {
               list.add(s + ": " + p_179518_ + ": " + p_179519_);
            });
         });
         p_179500_.m_178352_(list, FriendlyByteBuf::m_130070_);
      } else {
         p_179500_.m_130130_(0);
      }

   }

   private static List<String> m_179495_(LivingEntity p_179496_, long p_179497_) {
      Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> map = p_179496_.m_6274_().m_147339_();
      List<String> list = Lists.newArrayList();

      for(Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : map.entrySet()) {
         MemoryModuleType<?> memorymoduletype = entry.getKey();
         Optional<? extends ExpirableValue<?>> optional = entry.getValue();
         String s;
         if (optional.isPresent()) {
            ExpirableValue<?> expirablevalue = optional.get();
            Object object = expirablevalue.m_26319_();
            if (memorymoduletype == MemoryModuleType.f_26325_) {
               long i = p_179497_ - (Long)object;
               s = i + " ticks ago";
            } else if (expirablevalue.m_26321_()) {
               s = m_179492_((ServerLevel)p_179496_.f_19853_, object) + " (ttl: " + expirablevalue.m_148191_() + ")";
            } else {
               s = m_179492_((ServerLevel)p_179496_.f_19853_, object);
            }
         } else {
            s = "-";
         }

         list.add(Registry.f_122871_.m_7981_(memorymoduletype).m_135815_() + ": " + s);
      }

      list.sort(String::compareTo);
      return list;
   }

   private static String m_179492_(ServerLevel p_179493_, @Nullable Object p_179494_) {
      if (p_179494_ == null) {
         return "-";
      } else if (p_179494_ instanceof UUID) {
         return m_179492_(p_179493_, p_179493_.m_8791_((UUID)p_179494_));
      } else if (p_179494_ instanceof LivingEntity) {
         Entity entity1 = (Entity)p_179494_;
         return DebugEntityNameGenerator.m_179486_(entity1);
      } else if (p_179494_ instanceof Nameable) {
         return ((Nameable)p_179494_).m_7755_().getString();
      } else if (p_179494_ instanceof WalkTarget) {
         return m_179492_(p_179493_, ((WalkTarget)p_179494_).m_26420_());
      } else if (p_179494_ instanceof EntityTracker) {
         return m_179492_(p_179493_, ((EntityTracker)p_179494_).m_147481_());
      } else if (p_179494_ instanceof GlobalPos) {
         return m_179492_(p_179493_, ((GlobalPos)p_179494_).m_122646_());
      } else if (p_179494_ instanceof BlockPosTracker) {
         return m_179492_(p_179493_, ((BlockPosTracker)p_179494_).m_6675_());
      } else if (p_179494_ instanceof EntityDamageSource) {
         Entity entity = ((EntityDamageSource)p_179494_).m_7639_();
         return entity == null ? p_179494_.toString() : m_179492_(p_179493_, entity);
      } else if (!(p_179494_ instanceof Collection)) {
         return p_179494_.toString();
      } else {
         List<String> list = Lists.newArrayList();

         for(Object object : (Iterable)p_179494_) {
            list.add(m_179492_(p_179493_, object));
         }

         return list.toString();
      }
   }

   private static void m_133691_(ServerLevel p_133692_, FriendlyByteBuf p_133693_, ResourceLocation p_133694_) {
      Packet<?> packet = new ClientboundCustomPayloadPacket(p_133694_, p_133693_);

      for(Player player : p_133692_.m_6018_().m_6907_()) {
         ((ServerPlayer)player).f_8906_.m_141995_(packet);
      }

   }
}