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

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.util.ArrayList;

// Project:      asm-refresher
// Author:       jordan

public class Refresher {

    static final File clientFile = new File(RefresherUtils.currentClient);
    static ArrayList<ClassNode> classNodes = new ArrayList<ClassNode>();
    static boolean checkUpdate = true;


    public static void main(String[] args) {
        if (RefresherUtils.clientCheck(RefresherUtils.currentClient) == false) {
            System.out.println("Downloading client from URL....");
            RefresherUtils.downloadClient(RefresherUtils.clientLink, RefresherUtils.currentClient);
        }
        try {
            System.out.println("Current checksum: " + RefresherUtils.getChecksum(RefresherUtils.currentClient));
            System.out.println("Updated checksum: " + RefresherUtils.getChecksum(RefresherUtils.freshClient));
            System.out.println("Loading class files as class nodes..");

            classNodes = AsmUtils.loadClasses(clientFile);
            for(ClassNode classNode : classNodes) {
                System.out.println("Class name: " + classNode.name);
                for (Object mNodeObject : classNode.methods.toArray()) {
                    MethodNode methodNode = (MethodNode) mNodeObject;
                    System.out.println(" > Method Name: " + methodNode.name);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
