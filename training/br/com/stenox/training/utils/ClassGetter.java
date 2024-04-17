// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils;

import java.util.Enumeration;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.lang.reflect.Method;
import java.io.File;
import java.util.Iterator;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import org.bukkit.plugin.Plugin;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

public class ClassGetter
{
    public static ArrayList<Class<?>> getClassesForPackage(final JavaPlugin plugin, final String pkgname) {
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        final CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            final URL resource = src.getLocation();
            resource.getPath();
            processJarfile(resource, pkgname, classes);
        }
        return classes;
    }
    
    public static ArrayList<Class<?>> getClassesForPackage(final Plugin plugin, final String pkgname) {
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        final CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            final URL resource = src.getLocation();
            resource.getPath();
            processJarfile(resource, pkgname, classes);
        }
        return classes;
    }
    
    public static ArrayList<Class<?>> getClassesForPackageSimple(final JavaPlugin plugin, final String pkgname) {
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        final CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            final URL resource = src.getLocation();
            resource.getPath();
            processJarfile(resource, pkgname, classes);
        }
        final ArrayList<String> names = new ArrayList<String>();
        final ArrayList<Class<?>> classi = new ArrayList<Class<?>>();
        for (final Class<?> classy : classes) {
            names.add(classy.getSimpleName());
            classi.add(classy);
        }
        classes.clear();
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        for (final String s : names) {
            for (final Class<?> classy2 : classi) {
                if (classy2.getSimpleName().equals(s)) {
                    classes.add(classy2);
                    break;
                }
            }
        }
        return classes;
    }
    
    private static Class<?> loadClass(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
        catch (NoClassDefFoundError e2) {
            return null;
        }
    }
    
    public static List<Class<?>> getClassesForPackageByPlugin(final Object plugin, final String pkgname) {
        try {
            final Method method = plugin.getClass().getMethod("getFile", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            final File file = (File)method.invoke(plugin, new Object[0]);
            return getClassesForPackageByFile(file, pkgname);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Class<?>>();
        }
    }
    
    public static List<Class<?>> getClassesForPackageByFile(final File file, final String pkgname) {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            final String relPath = pkgname.replace('.', '/');
            try (final JarFile jarFile = new JarFile(file)) {
                final Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    final String entryName = entry.getName();
                    if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > relPath.length() + "/".length()) {
                        String className = entryName.replace('/', '.').replace('\\', '.');
                        if (className.endsWith(".class")) {
                            className = className.substring(0, className.length() - 6);
                        }
                        final Class<?> c = loadClass(className);
                        if (c == null) {
                            continue;
                        }
                        classes.add(c);
                    }
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + file.getAbsolutePath() + "'", e);
        }
        return classes;
    }
    
    private static void processJarfile(final URL resource, final String pkgname, final ArrayList<Class<?>> classes) {
        final String relPath = pkgname.replace('.', '/');
        final String resPath = resource.getPath().replace("%20", " ");
        final String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);
        }
        catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File " + jarPath + "", e);
        }
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            final String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > relPath.length() + "/".length()) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }
}
