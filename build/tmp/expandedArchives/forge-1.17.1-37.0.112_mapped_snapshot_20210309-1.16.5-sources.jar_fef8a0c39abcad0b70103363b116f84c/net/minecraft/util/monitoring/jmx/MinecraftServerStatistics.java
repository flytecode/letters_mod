package net.minecraft.util.monitoring.jmx;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MinecraftServerStatistics implements DynamicMBean {
   private static final Logger f_18314_ = LogManager.getLogger();
   private final MinecraftServer f_18315_;
   private final MBeanInfo f_18316_;
   private final Map<String, MinecraftServerStatistics.AttributeDescription> f_18317_ = Stream.of(new MinecraftServerStatistics.AttributeDescription("tickTimes", this::m_18330_, "Historical tick times (ms)", long[].class), new MinecraftServerStatistics.AttributeDescription("averageTickTime", this::m_18321_, "Current average tick time (ms)", Long.TYPE)).collect(Collectors.toMap((p_18332_) -> {
      return p_18332_.f_18346_;
   }, Function.identity()));

   private MinecraftServerStatistics(MinecraftServer p_18320_) {
      this.f_18315_ = p_18320_;
      MBeanAttributeInfo[] ambeanattributeinfo = this.f_18317_.values().stream().map(MinecraftServerStatistics.AttributeDescription::m_18361_).toArray((p_145923_) -> {
         return new MBeanAttributeInfo[p_145923_];
      });
      this.f_18316_ = new MBeanInfo(MinecraftServerStatistics.class.getSimpleName(), "metrics for dedicated server", ambeanattributeinfo, (MBeanConstructorInfo[])null, (MBeanOperationInfo[])null, new MBeanNotificationInfo[0]);
   }

   public static void m_18328_(MinecraftServer p_18329_) {
      try {
         ManagementFactory.getPlatformMBeanServer().registerMBean(new MinecraftServerStatistics(p_18329_), new ObjectName("net.minecraft.server:type=Server"));
      } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException malformedobjectnameexception) {
         f_18314_.warn("Failed to initialise server as JMX bean", (Throwable)malformedobjectnameexception);
      }

   }

   private float m_18321_() {
      return this.f_18315_.m_129903_();
   }

   private long[] m_18330_() {
      return this.f_18315_.f_129748_;
   }

   @Nullable
   public Object getAttribute(String p_18334_) {
      MinecraftServerStatistics.AttributeDescription minecraftserverstatistics$attributedescription = this.f_18317_.get(p_18334_);
      return minecraftserverstatistics$attributedescription == null ? null : minecraftserverstatistics$attributedescription.f_18347_.get();
   }

   public void setAttribute(Attribute p_18343_) {
   }

   public AttributeList getAttributes(String[] p_18336_) {
      List<Attribute> list = Arrays.stream(p_18336_).map(this.f_18317_::get).filter(Objects::nonNull).map((p_145925_) -> {
         return new Attribute(p_145925_.f_18346_, p_145925_.f_18347_.get());
      }).collect(Collectors.toList());
      return new AttributeList(list);
   }

   public AttributeList setAttributes(AttributeList p_18345_) {
      return new AttributeList();
   }

   @Nullable
   public Object invoke(String p_18339_, Object[] p_18340_, String[] p_18341_) {
      return null;
   }

   public MBeanInfo getMBeanInfo() {
      return this.f_18316_;
   }

   static final class AttributeDescription {
      final String f_18346_;
      final Supplier<Object> f_18347_;
      private final String f_18348_;
      private final Class<?> f_18349_;

      AttributeDescription(String p_18351_, Supplier<Object> p_18352_, String p_18353_, Class<?> p_18354_) {
         this.f_18346_ = p_18351_;
         this.f_18347_ = p_18352_;
         this.f_18348_ = p_18353_;
         this.f_18349_ = p_18354_;
      }

      private MBeanAttributeInfo m_18361_() {
         return new MBeanAttributeInfo(this.f_18346_, this.f_18349_.getSimpleName(), this.f_18348_, true, false, false);
      }
   }
}