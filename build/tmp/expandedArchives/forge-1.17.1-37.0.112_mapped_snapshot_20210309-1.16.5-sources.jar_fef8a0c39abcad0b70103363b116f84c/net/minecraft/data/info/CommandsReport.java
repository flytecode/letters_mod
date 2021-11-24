package net.minecraft.data.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;

public class CommandsReport implements DataProvider {
   private static final Gson f_124041_ = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator f_124042_;

   public CommandsReport(DataGenerator p_124045_) {
      this.f_124042_ = p_124045_;
   }

   public void m_6865_(HashCache p_124048_) throws IOException {
      Path path = this.f_124042_.m_123916_().resolve("reports/commands.json");
      CommandDispatcher<CommandSourceStack> commanddispatcher = (new Commands(Commands.CommandSelection.ALL)).m_82094_();
      DataProvider.m_123920_(f_124041_, p_124048_, ArgumentTypes.m_121590_(commanddispatcher, commanddispatcher.getRoot()), path);
   }

   public String m_6055_() {
      return "Command Syntax";
   }
}