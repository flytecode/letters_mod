package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JUnitLikeTestReporter implements TestReporter {
   private final Document f_177659_;
   private final Element f_177660_;
   private final Stopwatch f_177661_;
   private final File f_177662_;

   public JUnitLikeTestReporter(File p_177664_) throws ParserConfigurationException {
      this.f_177662_ = p_177664_;
      this.f_177659_ = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      this.f_177660_ = this.f_177659_.createElement("testsuite");
      Element element = this.f_177659_.createElement("testsuite");
      element.appendChild(this.f_177660_);
      this.f_177659_.appendChild(element);
      this.f_177660_.setAttribute("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
      this.f_177661_ = Stopwatch.createStarted();
   }

   private Element m_177670_(GameTestInfo p_177671_, String p_177672_) {
      Element element = this.f_177659_.createElement("testcase");
      element.setAttribute("name", p_177672_);
      element.setAttribute("classname", p_177671_.m_127645_());
      element.setAttribute("time", String.valueOf((double)p_177671_.m_177485_() / 1000.0D));
      this.f_177660_.appendChild(element);
      return element;
   }

   public void m_8014_(GameTestInfo p_177669_) {
      String s = p_177669_.m_127633_();
      String s1 = p_177669_.m_127642_().getMessage();
      Element element;
      if (p_177669_.m_127643_()) {
         element = this.f_177659_.createElement("failure");
         element.setAttribute("message", s1);
      } else {
         element = this.f_177659_.createElement("skipped");
         element.setAttribute("message", s1);
      }

      Element element1 = this.m_177670_(p_177669_, s);
      element1.appendChild(element);
   }

   public void m_142335_(GameTestInfo p_177674_) {
      String s = p_177674_.m_127633_();
      this.m_177670_(p_177674_, s);
   }

   public void m_142411_() {
      this.f_177661_.stop();
      this.f_177660_.setAttribute("time", String.valueOf((double)this.f_177661_.elapsed(TimeUnit.MILLISECONDS) / 1000.0D));

      try {
         this.m_177666_(this.f_177662_);
      } catch (TransformerException transformerexception) {
         throw new Error("Couldn't save test report", transformerexception);
      }
   }

   public void m_177666_(File p_177667_) throws TransformerException {
      TransformerFactory transformerfactory = TransformerFactory.newInstance();
      Transformer transformer = transformerfactory.newTransformer();
      DOMSource domsource = new DOMSource(this.f_177659_);
      StreamResult streamresult = new StreamResult(p_177667_);
      transformer.transform(domsource, streamresult);
   }
}