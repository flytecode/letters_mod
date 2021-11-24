package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundCommandsPacket implements Packet<ClientGamePacketListener> {
   private static final byte f_178797_ = 3;
   private static final byte f_178798_ = 4;
   private static final byte f_178799_ = 8;
   private static final byte f_178800_ = 16;
   private static final byte f_178801_ = 0;
   private static final byte f_178802_ = 1;
   private static final byte f_178803_ = 2;
   private final RootCommandNode<SharedSuggestionProvider> f_131858_;

   public ClientboundCommandsPacket(RootCommandNode<SharedSuggestionProvider> p_131861_) {
      this.f_131858_ = p_131861_;
   }

   public ClientboundCommandsPacket(FriendlyByteBuf p_178805_) {
      List<ClientboundCommandsPacket.Entry> list = p_178805_.m_178366_(ClientboundCommandsPacket::m_131887_);
      m_178812_(list);
      int i = p_178805_.m_130242_();
      this.f_131858_ = (RootCommandNode)(list.get(i)).f_131893_;
   }

   public void m_5779_(FriendlyByteBuf p_131886_) {
      Object2IntMap<CommandNode<SharedSuggestionProvider>> object2intmap = m_131862_(this.f_131858_);
      List<CommandNode<SharedSuggestionProvider>> list = m_178806_(object2intmap);
      p_131886_.m_178352_(list, (p_178810_, p_178811_) -> {
         m_131871_(p_178810_, p_178811_, object2intmap);
      });
      p_131886_.m_130130_(object2intmap.get(this.f_131858_));
   }

   private static void m_178812_(List<ClientboundCommandsPacket.Entry> p_178813_) {
      List<ClientboundCommandsPacket.Entry> list = Lists.newArrayList(p_178813_);

      while(!list.isEmpty()) {
         boolean flag = list.removeIf((p_178816_) -> {
            return p_178816_.m_178817_(p_178813_);
         });
         if (!flag) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }

   }

   private static Object2IntMap<CommandNode<SharedSuggestionProvider>> m_131862_(RootCommandNode<SharedSuggestionProvider> p_131863_) {
      Object2IntMap<CommandNode<SharedSuggestionProvider>> object2intmap = new Object2IntOpenHashMap<>();
      Queue<CommandNode<SharedSuggestionProvider>> queue = Queues.newArrayDeque();
      queue.add(p_131863_);

      CommandNode<SharedSuggestionProvider> commandnode;
      while((commandnode = queue.poll()) != null) {
         if (!object2intmap.containsKey(commandnode)) {
            int i = object2intmap.size();
            object2intmap.put(commandnode, i);
            queue.addAll(commandnode.getChildren());
            if (commandnode.getRedirect() != null) {
               queue.add(commandnode.getRedirect());
            }
         }
      }

      return object2intmap;
   }

   private static List<CommandNode<SharedSuggestionProvider>> m_178806_(Object2IntMap<CommandNode<SharedSuggestionProvider>> p_178807_) {
      ObjectArrayList<CommandNode<SharedSuggestionProvider>> objectarraylist = new ObjectArrayList<>(p_178807_.size());
      objectarraylist.size(p_178807_.size());

      for(Object2IntMap.Entry<CommandNode<SharedSuggestionProvider>> entry : Object2IntMaps.fastIterable(p_178807_)) {
         objectarraylist.set(entry.getIntValue(), entry.getKey());
      }

      return objectarraylist;
   }

   private static ClientboundCommandsPacket.Entry m_131887_(FriendlyByteBuf p_131888_) {
      byte b0 = p_131888_.readByte();
      int[] aint = p_131888_.m_130100_();
      int i = (b0 & 8) != 0 ? p_131888_.m_130242_() : 0;
      ArgumentBuilder<SharedSuggestionProvider, ?> argumentbuilder = m_131868_(p_131888_, b0);
      return new ClientboundCommandsPacket.Entry(argumentbuilder, b0, i, aint);
   }

   @Nullable
   private static ArgumentBuilder<SharedSuggestionProvider, ?> m_131868_(FriendlyByteBuf p_131869_, byte p_131870_) {
      int i = p_131870_ & 3;
      if (i == 2) {
         String s = p_131869_.m_130277_();
         ArgumentType<?> argumenttype = ArgumentTypes.m_121609_(p_131869_);
         if (argumenttype == null) {
            return null;
         } else {
            RequiredArgumentBuilder<SharedSuggestionProvider, ?> requiredargumentbuilder = RequiredArgumentBuilder.argument(s, argumenttype);
            if ((p_131870_ & 16) != 0) {
               requiredargumentbuilder.suggests(SuggestionProviders.m_121656_(p_131869_.m_130281_()));
            }

            return requiredargumentbuilder;
         }
      } else {
         return i == 1 ? LiteralArgumentBuilder.literal(p_131869_.m_130277_()) : null;
      }
   }

   private static void m_131871_(FriendlyByteBuf p_131872_, CommandNode<SharedSuggestionProvider> p_131873_, Map<CommandNode<SharedSuggestionProvider>, Integer> p_131874_) {
      byte b0 = 0;
      if (p_131873_.getRedirect() != null) {
         b0 = (byte)(b0 | 8);
      }

      if (p_131873_.getCommand() != null) {
         b0 = (byte)(b0 | 4);
      }

      if (p_131873_ instanceof RootCommandNode) {
         b0 = (byte)(b0 | 0);
      } else if (p_131873_ instanceof ArgumentCommandNode) {
         b0 = (byte)(b0 | 2);
         if (((ArgumentCommandNode)p_131873_).getCustomSuggestions() != null) {
            b0 = (byte)(b0 | 16);
         }
      } else {
         if (!(p_131873_ instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + p_131873_);
         }

         b0 = (byte)(b0 | 1);
      }

      p_131872_.writeByte(b0);
      p_131872_.m_130130_(p_131873_.getChildren().size());

      for(CommandNode<SharedSuggestionProvider> commandnode : p_131873_.getChildren()) {
         p_131872_.m_130130_(p_131874_.get(commandnode));
      }

      if (p_131873_.getRedirect() != null) {
         p_131872_.m_130130_(p_131874_.get(p_131873_.getRedirect()));
      }

      if (p_131873_ instanceof ArgumentCommandNode) {
         ArgumentCommandNode<SharedSuggestionProvider, ?> argumentcommandnode = (ArgumentCommandNode)p_131873_;
         p_131872_.m_130070_(argumentcommandnode.getName());
         ArgumentTypes.m_121611_(p_131872_, argumentcommandnode.getType());
         if (argumentcommandnode.getCustomSuggestions() != null) {
            p_131872_.m_130085_(SuggestionProviders.m_121654_(argumentcommandnode.getCustomSuggestions()));
         }
      } else if (p_131873_ instanceof LiteralCommandNode) {
         p_131872_.m_130070_(((LiteralCommandNode)p_131873_).getLiteral());
      }

   }

   public void m_5797_(ClientGamePacketListener p_131878_) {
      p_131878_.m_7443_(this);
   }

   public RootCommandNode<SharedSuggestionProvider> m_131884_() {
      return this.f_131858_;
   }

   static class Entry {
      @Nullable
      private final ArgumentBuilder<SharedSuggestionProvider, ?> f_131889_;
      private final byte f_131890_;
      private final int f_131891_;
      private final int[] f_131892_;
      @Nullable
      CommandNode<SharedSuggestionProvider> f_131893_;

      Entry(@Nullable ArgumentBuilder<SharedSuggestionProvider, ?> p_131895_, byte p_131896_, int p_131897_, int[] p_131898_) {
         this.f_131889_ = p_131895_;
         this.f_131890_ = p_131896_;
         this.f_131891_ = p_131897_;
         this.f_131892_ = p_131898_;
      }

      public boolean m_178817_(List<ClientboundCommandsPacket.Entry> p_178818_) {
         if (this.f_131893_ == null) {
            if (this.f_131889_ == null) {
               this.f_131893_ = new RootCommandNode<>();
            } else {
               if ((this.f_131890_ & 8) != 0) {
                  if ((p_178818_.get(this.f_131891_)).f_131893_ == null) {
                     return false;
                  }

                  this.f_131889_.redirect((p_178818_.get(this.f_131891_)).f_131893_);
               }

               if ((this.f_131890_ & 4) != 0) {
                  this.f_131889_.executes((p_131906_) -> {
                     return 0;
                  });
               }

               this.f_131893_ = this.f_131889_.build();
            }
         }

         for(int i : this.f_131892_) {
            if ((p_178818_.get(i)).f_131893_ == null) {
               return false;
            }
         }

         for(int j : this.f_131892_) {
            CommandNode<SharedSuggestionProvider> commandnode = (p_178818_.get(j)).f_131893_;
            if (!(commandnode instanceof RootCommandNode)) {
               this.f_131893_.addChild(commandnode);
            }
         }

         return true;
      }
   }
}