package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.GameRules;

public class ServerFunctionManager {
   private static final Component f_179958_ = new TranslatableComponent("commands.debug.function.noRecursion");
   private static final ResourceLocation f_136099_ = new ResourceLocation("tick");
   private static final ResourceLocation f_136100_ = new ResourceLocation("load");
   final MinecraftServer f_136101_;
   @Nullable
   private ServerFunctionManager.ExecutionContext f_179959_;
   private List<CommandFunction> f_136105_ = ImmutableList.of();
   private boolean f_136106_;
   private ServerFunctionLibrary f_136107_;

   public ServerFunctionManager(MinecraftServer p_136110_, ServerFunctionLibrary p_136111_) {
      this.f_136101_ = p_136110_;
      this.f_136107_ = p_136111_;
      this.m_136125_(p_136111_);
   }

   public int m_136122_() {
      return this.f_136101_.m_129900_().m_46215_(GameRules.f_46152_);
   }

   public CommandDispatcher<CommandSourceStack> m_136127_() {
      return this.f_136101_.m_129892_().m_82094_();
   }

   public void m_136128_() {
      this.m_136115_(this.f_136105_, f_136099_);
      if (this.f_136106_) {
         this.f_136106_ = false;
         Collection<CommandFunction> collection = this.f_136107_.m_136096_().m_7689_(f_136100_).m_6497_();
         this.m_136115_(collection, f_136100_);
      }

   }

   private void m_136115_(Collection<CommandFunction> p_136116_, ResourceLocation p_136117_) {
      this.f_136101_.m_129905_().m_6521_(p_136117_::toString);

      for(CommandFunction commandfunction : p_136116_) {
         this.m_136112_(commandfunction, this.m_136129_());
      }

      this.f_136101_.m_129905_().m_7238_();
   }

   public int m_136112_(CommandFunction p_136113_, CommandSourceStack p_136114_) {
      return this.m_179960_(p_136113_, p_136114_, (ServerFunctionManager.TraceCallbacks)null);
   }

   public int m_179960_(CommandFunction p_179961_, CommandSourceStack p_179962_, @Nullable ServerFunctionManager.TraceCallbacks p_179963_) {
      if (this.f_179959_ != null) {
         if (p_179963_ != null) {
            this.f_179959_.m_179975_(f_179958_.getString());
            return 0;
         } else {
            this.f_179959_.m_179972_(p_179961_, p_179962_);
            return 0;
         }
      } else {
         int i;
         try {
            this.f_179959_ = new ServerFunctionManager.ExecutionContext(p_179963_);
            i = this.f_179959_.m_179977_(p_179961_, p_179962_);
         } finally {
            this.f_179959_ = null;
         }

         return i;
      }
   }

   public void m_136120_(ServerFunctionLibrary p_136121_) {
      this.f_136107_ = p_136121_;
      this.m_136125_(p_136121_);
   }

   private void m_136125_(ServerFunctionLibrary p_136126_) {
      this.f_136105_ = ImmutableList.copyOf(p_136126_.m_136096_().m_7689_(f_136099_).m_6497_());
      this.f_136106_ = true;
   }

   public CommandSourceStack m_136129_() {
      return this.f_136101_.m_129893_().m_81325_(2).m_81324_();
   }

   public Optional<CommandFunction> m_136118_(ResourceLocation p_136119_) {
      return this.f_136107_.m_136089_(p_136119_);
   }

   public Tag<CommandFunction> m_136123_(ResourceLocation p_136124_) {
      return this.f_136107_.m_136097_(p_136124_);
   }

   public Iterable<ResourceLocation> m_136130_() {
      return this.f_136107_.m_136055_().keySet();
   }

   public Iterable<ResourceLocation> m_136131_() {
      return this.f_136107_.m_136096_().m_13406_();
   }

   class ExecutionContext {
      private int f_179965_;
      @Nullable
      private final ServerFunctionManager.TraceCallbacks f_179966_;
      private final Deque<ServerFunctionManager.QueuedCommand> f_179967_ = Queues.newArrayDeque();
      private final List<ServerFunctionManager.QueuedCommand> f_179968_ = Lists.newArrayList();

      ExecutionContext(ServerFunctionManager.TraceCallbacks p_179971_) {
         this.f_179966_ = p_179971_;
      }

      void m_179972_(CommandFunction p_179973_, CommandSourceStack p_179974_) {
         int i = ServerFunctionManager.this.m_136122_();
         if (this.f_179967_.size() + this.f_179968_.size() < i) {
            this.f_179968_.add(new ServerFunctionManager.QueuedCommand(p_179974_, this.f_179965_, new CommandFunction.FunctionEntry(p_179973_)));
         }

      }

      int m_179977_(CommandFunction p_179978_, CommandSourceStack p_179979_) {
         int i = ServerFunctionManager.this.m_136122_();
         int j = 0;
         CommandFunction.Entry[] acommandfunction$entry = p_179978_.m_77989_();

         for(int k = acommandfunction$entry.length - 1; k >= 0; --k) {
            this.f_179967_.push(new ServerFunctionManager.QueuedCommand(p_179979_, 0, acommandfunction$entry[k]));
         }

         while(!this.f_179967_.isEmpty()) {
            try {
               ServerFunctionManager.QueuedCommand serverfunctionmanager$queuedcommand = this.f_179967_.removeFirst();
               ServerFunctionManager.this.f_136101_.m_129905_().m_6521_(serverfunctionmanager$queuedcommand::toString);
               this.f_179965_ = serverfunctionmanager$queuedcommand.f_179980_;
               serverfunctionmanager$queuedcommand.m_179985_(ServerFunctionManager.this, this.f_179967_, i, this.f_179966_);
               if (!this.f_179968_.isEmpty()) {
                  Lists.reverse(this.f_179968_).forEach(this.f_179967_::addFirst);
                  this.f_179968_.clear();
               }
            } finally {
               ServerFunctionManager.this.f_136101_.m_129905_().m_7238_();
            }

            ++j;
            if (j >= i) {
               return j;
            }
         }

         return j;
      }

      public void m_179975_(String p_179976_) {
         if (this.f_179966_ != null) {
            this.f_179966_.m_142255_(this.f_179965_, p_179976_);
         }

      }
   }

   public static class QueuedCommand {
      private final CommandSourceStack f_136133_;
      final int f_179980_;
      private final CommandFunction.Entry f_136134_;

      public QueuedCommand(CommandSourceStack p_179982_, int p_179983_, CommandFunction.Entry p_179984_) {
         this.f_136133_ = p_179982_;
         this.f_179980_ = p_179983_;
         this.f_136134_ = p_179984_;
      }

      public void m_179985_(ServerFunctionManager p_179986_, Deque<ServerFunctionManager.QueuedCommand> p_179987_, int p_179988_, @Nullable ServerFunctionManager.TraceCallbacks p_179989_) {
         try {
            this.f_136134_.m_142134_(p_179986_, this.f_136133_, p_179987_, p_179988_, this.f_179980_, p_179989_);
         } catch (CommandSyntaxException commandsyntaxexception) {
            if (p_179989_ != null) {
               p_179989_.m_142255_(this.f_179980_, commandsyntaxexception.getRawMessage().getString());
            }
         } catch (Exception exception) {
            if (p_179989_ != null) {
               p_179989_.m_142255_(this.f_179980_, exception.getMessage());
            }
         }

      }

      public String toString() {
         return this.f_136134_.toString();
      }
   }

   public interface TraceCallbacks {
      void m_142256_(int p_179990_, String p_179991_);

      void m_142279_(int p_179992_, String p_179993_, int p_179994_);

      void m_142255_(int p_179998_, String p_179999_);

      void m_142147_(int p_179995_, ResourceLocation p_179996_, int p_179997_);
   }
}