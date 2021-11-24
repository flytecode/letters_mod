package net.minecraft.server.rcon.thread;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.PktUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconClient extends GenericThread {
   private static final Logger f_11579_ = LogManager.getLogger();
   private static final int f_144029_ = 3;
   private static final int f_144030_ = 2;
   private static final int f_144031_ = 0;
   private static final int f_144032_ = 2;
   private static final int f_144033_ = -1;
   private boolean f_11580_;
   private final Socket f_11581_;
   private final byte[] f_11582_ = new byte[1460];
   private final String f_11583_;
   private final ServerInterface f_11584_;

   RconClient(ServerInterface p_11587_, String p_11588_, Socket p_11589_) {
      super("RCON Client " + p_11589_.getInetAddress());
      this.f_11584_ = p_11587_;
      this.f_11581_ = p_11589_;

      try {
         this.f_11581_.setSoTimeout(0);
      } catch (Exception exception) {
         this.f_11515_ = false;
      }

      this.f_11583_ = p_11588_;
   }

   public void run() {
	   {
         try {
         while(true) {
            if (!this.f_11515_) {
               return;
            }

            BufferedInputStream bufferedinputstream = new BufferedInputStream(this.f_11581_.getInputStream());
            int i = bufferedinputstream.read(this.f_11582_, 0, 1460);
            if (10 <= i) {
               int j = 0;
               int k = PktUtils.m_11492_(this.f_11582_, 0, i);
               if (k != i - 4) {
                  return;
               }

               j = j + 4;
               int l = PktUtils.m_11492_(this.f_11582_, j, i);
               j = j + 4;
               int i1 = PktUtils.m_11485_(this.f_11582_, j);
               j = j + 4;
               switch(i1) {
               case 2:
                  if (this.f_11580_) {
                     String s1 = PktUtils.m_11488_(this.f_11582_, j, i);

                     try {
                        this.m_11594_(l, this.f_11584_.m_7261_(s1));
                     } catch (Exception exception) {
                        this.m_11594_(l, "Error executing: " + s1 + " (" + exception.getMessage() + ")");
                     }
                     continue;
                  }

                  this.m_11598_();
                  continue;
               case 3:
                  String s = PktUtils.m_11488_(this.f_11582_, j, i);
                  int j1 = j + s.length();
                  if (!s.isEmpty() && s.equals(this.f_11583_)) {
                     this.f_11580_ = true;
                     this.m_11590_(l, 2, "");
                     continue;
                  }

                  this.f_11580_ = false;
                  this.m_11598_();
                  continue;
               default:
                  this.m_11594_(l, String.format("Unknown request %s", Integer.toHexString(i1)));
                  continue;
               }
            }
            return;
         }
         } catch (IOException ioexception) {
            return;
         } catch (Exception exception1) {
            f_11579_.error("Exception whilst parsing RCON input", (Throwable)exception1);
            return;
         } finally {
            this.m_11599_();
            f_11579_.info("Thread {} shutting down", (Object)this.f_11516_);
            this.f_11515_ = false;
         }
      }
   }

   private void m_11590_(int p_11591_, int p_11592_, String p_11593_) throws IOException {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
      DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
      byte[] abyte = p_11593_.getBytes(StandardCharsets.UTF_8);
      dataoutputstream.writeInt(Integer.reverseBytes(abyte.length + 10));
      dataoutputstream.writeInt(Integer.reverseBytes(p_11591_));
      dataoutputstream.writeInt(Integer.reverseBytes(p_11592_));
      dataoutputstream.write(abyte);
      dataoutputstream.write(0);
      dataoutputstream.write(0);
      this.f_11581_.getOutputStream().write(bytearrayoutputstream.toByteArray());
   }

   private void m_11598_() throws IOException {
      this.m_11590_(-1, 2, "");
   }

   private void m_11594_(int p_11595_, String p_11596_) throws IOException {
      byte[] whole = p_11596_.getBytes(StandardCharsets.UTF_8);
      int i = whole.length;
      int start = 0;
      do {
         int j = 4096 <= i ? 4096 : i;
         this.m_11590_(p_11595_, 0, new String(java.util.Arrays.copyOfRange(whole, start, j+start), StandardCharsets.UTF_8));
         i -= j;
         start += j;
      } while(0 != i);

   }

   public void m_7530_() {
      this.f_11515_ = false;
      this.m_11599_();
      super.m_7530_();
   }

   private void m_11599_() {
      try {
         this.f_11581_.close();
      } catch (IOException ioexception) {
         f_11579_.warn("Failed to close socket", (Throwable)ioexception);
      }

   }
}
