/*
 *** DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *** 	Version 2, 2 2018
 ***
 *** 	Copyright (C) 2018 Jordan Makris <jnotrighteous@tuta.io>
 ***
 *** 	Everyone is permitted to copy and distribute verbatim or modified
 *** 	copies of this license document, and changing it is allowed as long
 *** 	as the name is changed.
 ***
 *** 	DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *** 	TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *** 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package club.notrighteous.asmrefresher;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//Project:      asm-refresher
//Author:       jordan

public final class AsmUtils {

	/*
	 * Big thank you to the methods author...
	 * @author Konloch
	 * https:://github.com/Konloch/bytecode-viewer
	 */
	
	
    public static ArrayList<ClassNode> loadClasses(final File jarFile) throws IOException {
        ArrayList<ClassNode> classes = new ArrayList<ClassNode>();
        ZipInputStream jis = new ZipInputStream(new FileInputStream(jarFile));
        ZipEntry entry;
        while ((entry = jis.getNextEntry()) != null) {
            try {
                final String name = entry.getName();
                if (name.endsWith(".class")) {
                    byte[] bytes = getBytes(jis);
                    String cafebabe = String.format("%02X%02X%02X%02X", bytes[0], bytes[1], bytes[2], bytes[3]);
                    if(cafebabe.toLowerCase().equals("cafebabe")) {
                        try {
                            final ClassNode cn = getNode(bytes);
                            classes.add(cn);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(jarFile+">"+name+": Header does not start with CAFEBABE, ignoring.");
                    }
                }

            } catch(Exception e) {

            } finally {
                jis.closeEntry();
            }
        }
        jis.close();
        return classes;
    }

    public static byte[] getBytes(final InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int a = 0;
        while ((a = is.read(buffer)) != -1) {
            baos.write(buffer, 0, a);
        }
        baos.close();
        buffer = null;
        return baos.toByteArray();
    }

    public static ClassNode getNode(final byte[] bytez) {
        ClassReader cr = new ClassReader(bytez);
        ClassNode cn = new ClassNode();
        try {
            cr.accept(cn, ClassReader.EXPAND_FRAMES);
        } catch (Exception e) {
            try {
                cr.accept(cn, ClassReader.SKIP_FRAMES);
            } catch(Exception e2) {
                e2.printStackTrace(); //just skip it
            }
        }
        cr = null;
        return cn;
    }

    private AsmUtils() {
    }
}
