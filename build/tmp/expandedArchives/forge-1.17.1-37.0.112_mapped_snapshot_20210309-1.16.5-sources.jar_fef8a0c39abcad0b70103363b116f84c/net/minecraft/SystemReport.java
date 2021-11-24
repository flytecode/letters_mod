package net.minecraft;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

public class SystemReport {
   public static final long f_143506_ = 1048576L;
   private static final long f_143507_ = 1000000000L;
   private static final Logger f_143508_ = LogManager.getLogger();
   private static final String f_143509_ = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
   private static final String f_143510_ = System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
   private static final String f_143511_ = System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
   private final Map<String, String> f_143512_ = Maps.newLinkedHashMap();

   public SystemReport() {
      this.m_143519_("Minecraft Version", SharedConstants.m_136187_().getName());
      this.m_143519_("Minecraft Version ID", SharedConstants.m_136187_().getId());
      this.m_143519_("Operating System", f_143509_);
      this.m_143519_("Java Version", f_143510_);
      this.m_143519_("Java VM Version", f_143511_);
      this.m_143522_("Memory", () -> {
         Runtime runtime = Runtime.getRuntime();
         long i = runtime.maxMemory();
         long j = runtime.totalMemory();
         long k = runtime.freeMemory();
         long l = i / 1048576L;
         long i1 = j / 1048576L;
         long j1 = k / 1048576L;
         return k + " bytes (" + j1 + " MiB) / " + j + " bytes (" + i1 + " MiB) up to " + i + " bytes (" + l + " MiB)";
      });
      this.m_143522_("CPUs", () -> {
         return String.valueOf(Runtime.getRuntime().availableProcessors());
      });
      this.m_143516_("hardware", () -> {
         this.m_143535_(new SystemInfo());
      });
      this.m_143522_("JVM Flags", () -> {
         List<String> list = Util.m_137582_().collect(Collectors.toList());
         return String.format("%d total; %s", list.size(), String.join(" ", list));
      });
   }

   public void m_143519_(String p_143520_, String p_143521_) {
      this.f_143512_.put(p_143520_, p_143521_);
   }

   public void m_143522_(String p_143523_, Supplier<String> p_143524_) {
      try {
         this.m_143519_(p_143523_, p_143524_.get());
      } catch (Exception exception) {
         f_143508_.warn("Failed to get system info for {}", p_143523_, exception);
         this.m_143519_(p_143523_, "ERR");
      }

   }

   private void m_143535_(SystemInfo p_143536_) {
      HardwareAbstractionLayer hardwareabstractionlayer = p_143536_.getHardware();
      this.m_143516_("processor", () -> {
         this.m_143539_(hardwareabstractionlayer.getProcessor());
      });
      this.m_143516_("graphics", () -> {
         this.m_143552_(hardwareabstractionlayer.getGraphicsCards());
      });
      this.m_143516_("memory", () -> {
         this.m_143541_(hardwareabstractionlayer.getMemory());
      });
   }

   private void m_143516_(String p_143517_, Runnable p_143518_) {
      try {
         p_143518_.run();
      } catch (Throwable throwable) {
         f_143508_.warn("Failed retrieving info for group {}", p_143517_, throwable);
      }

   }

   private void m_143531_(List<PhysicalMemory> p_143532_) {
      int i = 0;

      for(PhysicalMemory physicalmemory : p_143532_) {
         String s = String.format("Memory slot #%d ", i++);
         this.m_143522_(s + "capacity (MB)", () -> {
            return String.format("%.2f", (float)physicalmemory.getCapacity() / 1048576.0F);
         });
         this.m_143522_(s + "clockSpeed (GHz)", () -> {
            return String.format("%.2f", (float)physicalmemory.getClockSpeed() / 1.0E9F);
         });
         this.m_143522_(s + "type", physicalmemory::getMemoryType);
      }

   }

   private void m_143549_(VirtualMemory p_143550_) {
      this.m_143522_("Virtual memory max (MB)", () -> {
         return String.format("%.2f", (float)p_143550_.getVirtualMax() / 1048576.0F);
      });
      this.m_143522_("Virtual memory used (MB)", () -> {
         return String.format("%.2f", (float)p_143550_.getVirtualInUse() / 1048576.0F);
      });
      this.m_143522_("Swap memory total (MB)", () -> {
         return String.format("%.2f", (float)p_143550_.getSwapTotal() / 1048576.0F);
      });
      this.m_143522_("Swap memory used (MB)", () -> {
         return String.format("%.2f", (float)p_143550_.getSwapUsed() / 1048576.0F);
      });
   }

   private void m_143541_(GlobalMemory p_143542_) {
      this.m_143516_("physical memory", () -> {
         this.m_143531_(p_143542_.getPhysicalMemory());
      });
      this.m_143516_("virtual memory", () -> {
         this.m_143549_(p_143542_.getVirtualMemory());
      });
   }

   private void m_143552_(List<GraphicsCard> p_143553_) {
      int i = 0;

      for(GraphicsCard graphicscard : p_143553_) {
         String s = String.format("Graphics card #%d ", i++);
         this.m_143522_(s + "name", graphicscard::getName);
         this.m_143522_(s + "vendor", graphicscard::getVendor);
         this.m_143522_(s + "VRAM (MB)", () -> {
            return String.format("%.2f", (float)graphicscard.getVRam() / 1048576.0F);
         });
         this.m_143522_(s + "deviceId", graphicscard::getDeviceId);
         this.m_143522_(s + "versionInfo", graphicscard::getVersionInfo);
      }

   }

   private void m_143539_(CentralProcessor p_143540_) {
      ProcessorIdentifier processoridentifier = p_143540_.getProcessorIdentifier();
      this.m_143522_("Processor Vendor", processoridentifier::getVendor);
      this.m_143522_("Processor Name", processoridentifier::getName);
      this.m_143522_("Identifier", processoridentifier::getIdentifier);
      this.m_143522_("Microarchitecture", processoridentifier::getMicroarchitecture);
      this.m_143522_("Frequency (GHz)", () -> {
         return String.format("%.2f", (float)processoridentifier.getVendorFreq() / 1.0E9F);
      });
      this.m_143522_("Number of physical packages", () -> {
         return String.valueOf(p_143540_.getPhysicalPackageCount());
      });
      this.m_143522_("Number of physical CPUs", () -> {
         return String.valueOf(p_143540_.getPhysicalProcessorCount());
      });
      this.m_143522_("Number of logical CPUs", () -> {
         return String.valueOf(p_143540_.getLogicalProcessorCount());
      });
   }

   public void m_143525_(StringBuilder p_143526_) {
      p_143526_.append("-- ").append("System Details").append(" --\n");
      p_143526_.append("Details:");
      this.f_143512_.forEach((p_143529_, p_143530_) -> {
         p_143526_.append("\n\t");
         p_143526_.append(p_143529_);
         p_143526_.append(": ");
         p_143526_.append(p_143530_);
      });
   }

   public String m_143515_() {
      return this.f_143512_.entrySet().stream().map((p_143534_) -> {
         return (String)p_143534_.getKey() + ": " + (String)p_143534_.getValue();
      }).collect(Collectors.joining(System.lineSeparator()));
   }
}