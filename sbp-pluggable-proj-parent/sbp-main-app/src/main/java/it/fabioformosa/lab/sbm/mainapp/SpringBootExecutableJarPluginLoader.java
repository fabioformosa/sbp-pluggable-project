package it.fabioformosa.lab.sbm.mainapp;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.jar.JarEntry;

import org.pf4j.JarPluginLoader;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;
import org.springframework.boot.loader.jar.JarFile;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringBootExecutableJarPluginLoader extends JarPluginLoader implements PluginLoader {

    public SpringBootExecutableJarPluginLoader(PluginManager pluginManager) {
        super(pluginManager);
    }

    @SneakyThrows
    @Override
    public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor) {
        log.info("SpringBootExecutableJarPluginLoader - loading plugin {}", pluginPath);
        PluginClassLoader pluginClassLoader = new PluginClassLoader(pluginManager, pluginDescriptor,
                getClass().getClassLoader());
        JarFile executableSpringJarFile = new JarFile(pluginPath.toFile());

        log.info("SpringBootExecutableJarPluginLoader - loading executable spring jar {}", executableSpringJarFile.getName());

        Iterator<JarEntry> iterator = executableSpringJarFile.entries().asIterator();
        while(iterator.hasNext()) {
            JarEntry nestedJarEntry = iterator.next();
            log.info("SpringBootExecutableJarPluginLoader - found nested jar {}", nestedJarEntry.getName());
            if(!(nestedJarEntry.getName().endsWith("/") || nestedJarEntry.getName().endsWith(".jar")))
                continue;
            JarFile nestedJarFile = executableSpringJarFile.getNestedJarFile(nestedJarEntry);
            pluginClassLoader.addURL(nestedJarFile.getUrl());
        }

        return pluginClassLoader;
    }

}
