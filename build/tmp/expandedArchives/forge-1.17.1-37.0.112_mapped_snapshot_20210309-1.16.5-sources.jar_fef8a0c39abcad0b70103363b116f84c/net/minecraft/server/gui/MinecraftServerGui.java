package net.minecraft.server.gui;

import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftServerGui extends JComponent {
   private static final Font f_139899_ = new Font("Monospaced", 0, 12);
   private static final Logger f_139900_ = LogManager.getLogger();
   private static final String f_142884_ = "Minecraft server";
   private static final String f_142885_ = "Minecraft server - shutting down!";
   private final DedicatedServer f_139901_;
   private Thread f_139902_;
   private final Collection<Runnable> f_139903_ = Lists.newArrayList();
   final AtomicBoolean f_139904_ = new AtomicBoolean();

   public static MinecraftServerGui m_139921_(final DedicatedServer p_139922_) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception exception) {
      }

      final JFrame jframe = new JFrame("Minecraft server");
      final MinecraftServerGui minecraftservergui = new MinecraftServerGui(p_139922_);
      jframe.setDefaultCloseOperation(2);
      jframe.add(minecraftservergui);
      jframe.pack();
      jframe.setLocationRelativeTo((Component)null);
      jframe.setVisible(true);
      jframe.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent p_139944_) {
            if (!minecraftservergui.f_139904_.getAndSet(true)) {
               jframe.setTitle("Minecraft server - shutting down!");
               p_139922_.m_7570_(true);
               minecraftservergui.m_139935_();
            }

         }
      });
      minecraftservergui.m_139909_(jframe::dispose);
      minecraftservergui.m_139908_();
      return minecraftservergui;
   }

   private MinecraftServerGui(DedicatedServer p_139907_) {
      this.f_139901_ = p_139907_;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.m_139934_(), "Center");
         this.add(this.m_139932_(), "West");
      } catch (Exception exception) {
         f_139900_.error("Couldn't build server GUI", (Throwable)exception);
      }

   }

   public void m_139909_(Runnable p_139910_) {
      this.f_139903_.add(p_139910_);
   }

   private JComponent m_139932_() {
      JPanel jpanel = new JPanel(new BorderLayout());
      StatsComponent statscomponent = new StatsComponent(this.f_139901_);
      this.f_139903_.add(statscomponent::m_139964_);
      jpanel.add(statscomponent, "North");
      jpanel.add(this.m_139933_(), "Center");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return jpanel;
   }

   private JComponent m_139933_() {
      JList<?> jlist = new PlayerListComponent(this.f_139901_);
      JScrollPane jscrollpane = new JScrollPane(jlist, 22, 30);
      jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return jscrollpane;
   }

   private JComponent m_139934_() {
      JPanel jpanel = new JPanel(new BorderLayout());
      JTextArea jtextarea = new JTextArea();
      JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);
      jtextarea.setEditable(false);
      jtextarea.setFont(f_139899_);
      JTextField jtextfield = new JTextField();
      jtextfield.addActionListener((p_139920_) -> {
         String s = jtextfield.getText().trim();
         if (!s.isEmpty()) {
            this.f_139901_.m_139645_(s, this.f_139901_.m_129893_());
         }

         jtextfield.setText("");
      });
      jtextarea.addFocusListener(new FocusAdapter() {
         public void focusGained(FocusEvent p_139949_) {
         }
      });
      jpanel.add(jscrollpane, "Center");
      jpanel.add(jtextfield, "South");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.f_139902_ = new Thread(() -> {
         String s;
         while((s = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.m_139914_(jtextarea, jscrollpane, s);
         }

      });
      this.f_139902_.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_139900_));
      this.f_139902_.setDaemon(true);
      return jpanel;
   }

   private java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
   public void m_139908_() {
      this.f_139902_.start();
      latch.countDown();
   }

   public void m_139925_() {
      if (!this.f_139904_.getAndSet(true)) {
         this.m_139935_();
      }

   }

   void m_139935_() {
      this.f_139903_.forEach(Runnable::run);
   }

   public void m_139914_(JTextArea p_139915_, JScrollPane p_139916_, String p_139917_) {
      try {
         latch.await();
      } catch (InterruptedException e){} //Prevent logging until after constructor has ended.
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> {
            this.m_139914_(p_139915_, p_139916_, p_139917_);
         });
      } else {
         Document document = p_139915_.getDocument();
         JScrollBar jscrollbar = p_139916_.getVerticalScrollBar();
         boolean flag = false;
         if (p_139916_.getViewport().getView() == p_139915_) {
            flag = (double)jscrollbar.getValue() + jscrollbar.getSize().getHeight() + (double)(f_139899_.getSize() * 4) > (double)jscrollbar.getMaximum();
         }

         try {
            document.insertString(document.getLength(), p_139917_, (AttributeSet)null);
         } catch (BadLocationException badlocationexception) {
         }

         if (flag) {
            jscrollbar.setValue(Integer.MAX_VALUE);
         }

      }
   }
}
