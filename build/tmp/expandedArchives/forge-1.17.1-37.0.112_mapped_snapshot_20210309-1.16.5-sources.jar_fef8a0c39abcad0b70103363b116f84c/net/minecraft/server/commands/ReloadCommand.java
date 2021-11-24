package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReloadCommand {
   private static final Logger f_138220_ = LogManager.getLogger();

   public static void m_138235_(Collection<String> p_138236_, CommandSourceStack p_138237_) {
      p_138237_.m_81377_().m_129861_(p_138236_).exceptionally((p_138234_) -> {
         f_138220_.warn("Failed to execute reload", p_138234_);
         p_138237_.m_81352_(new TranslatableComponent("commands.reload.failure"));
         return null;
      });
   }

   private static Collection<String> m_138222_(PackRepository p_138223_, WorldData p_138224_, Collection<String> p_138225_) {
      p_138223_.m_10506_();
      Collection<String> collection = Lists.newArrayList(p_138225_);
      Collection<String> collection1 = p_138224_.m_7513_().m_45855_();

      for(String s : p_138223_.m_10514_()) {
         if (!collection1.contains(s) && !collection.contains(s)) {
            collection.add(s);
         }
      }

      return collection;
   }

   public static void m_138226_(CommandDispatcher<CommandSourceStack> p_138227_) {
      p_138227_.register(Commands.m_82127_("reload").requires((p_138231_) -> {
         return p_138231_.m_6761_(2);
      }).executes((p_138229_) -> {
         CommandSourceStack commandsourcestack = p_138229_.getSource();
         MinecraftServer minecraftserver = commandsourcestack.m_81377_();
         PackRepository packrepository = minecraftserver.m_129891_();
         WorldData worlddata = minecraftserver.m_129910_();
         Collection<String> collection = packrepository.m_10523_();
         Collection<String> collection1 = m_138222_(packrepository, worlddata, collection);
         commandsourcestack.m_81354_(new TranslatableComponent("commands.reload.success"), true);
         m_138235_(collection1, commandsourcestack);
         return 0;
      }));
   }
}