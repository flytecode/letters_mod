package net.minecraft.server.packs.repository;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;

public class FolderRepositorySource implements RepositorySource {
   private static final FileFilter f_10381_ = (p_10398_) -> {
      boolean flag = p_10398_.isFile() && p_10398_.getName().endsWith(".zip");
      boolean flag1 = p_10398_.isDirectory() && (new File(p_10398_, "pack.mcmeta")).isFile();
      return flag || flag1;
   };
   private final File f_10382_;
   private final PackSource f_10383_;

   public FolderRepositorySource(File p_10386_, PackSource p_10387_) {
      this.f_10382_ = p_10386_;
      this.f_10383_ = p_10387_;
   }

   public void m_7686_(Consumer<Pack> p_10391_, Pack.PackConstructor p_10392_) {
      if (!this.f_10382_.isDirectory()) {
         this.f_10382_.mkdirs();
      }

      File[] afile = this.f_10382_.listFiles(f_10381_);
      if (afile != null) {
         for(File file1 : afile) {
            String s = "file/" + file1.getName();
            Pack pack = Pack.m_10430_(s, false, this.m_10388_(file1), p_10392_, Pack.Position.TOP, this.f_10383_);
            if (pack != null) {
               p_10391_.accept(pack);
            }
         }

      }
   }

   private Supplier<PackResources> m_10388_(File p_10389_) {
      return p_10389_.isDirectory() ? () -> {
         return new FolderPackResources(p_10389_);
      } : () -> {
         return new FilePackResources(p_10389_);
      };
   }
}