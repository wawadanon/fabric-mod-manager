package me.flashyreese.fabricmm;

import com.vdurmont.semver4j.Semver;
import me.flashyreese.common.util.JarUtil;
import me.flashyreese.fabricmm.ui.FabricModManagerUI;
import org.json.JSONObject;
import org.kamranzafar.jddl.DirectDownloader;

import javax.swing.*;
import java.awt.*;

public class Application {

    private static Semver VERSION;
    public static final DirectDownloader DIRECT_DOWNLOADER = new DirectDownloader();

    public static void main(String[] args) throws Exception {
        VERSION = new Semver(new JSONObject(JarUtil.readTextFile("fabric.mod.manager.json")).getString("version"), Semver.SemverType.STRICT);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            FabricModManagerUI fabricModManagerUI = null;
            try {
                fabricModManagerUI = new FabricModManagerUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fabricModManagerUI.setVisible(true);
        });
        Thread downloaderThread = new Thread(DIRECT_DOWNLOADER);
        downloaderThread.start();
        downloaderThread.join();
    }

    public static Semver getVersion(){
        return VERSION;
    }
}
