package me.flashyreese.fabricmm.ui.components;

import com.vdurmont.semver4j.Semver;
import me.flashyreese.fabricmm.Application;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

public class FabricModManagerMenuBar extends JMenuBar {

    private JMenu helpMenu;
    private JMenuItem checkForUpdates;

    public FabricModManagerMenuBar(){
        initComponents();
        setupComponents();
        loadComponents();
    }

    private void initComponents(){
        helpMenu = new JMenu();
        checkForUpdates = new JMenuItem();
    }

    private void setupComponents(){
        helpMenu.setText("Help");
        checkForUpdates.setText("Check for Updates...");
        checkForUpdates.addActionListener(e -> {
            try{
                URL url = new URL("https://api.github.com/repos/FlashyReese/fabric-mod-manager/releases");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String json = in.lines().collect(Collectors.joining());
                in.close();
                JSONArray jsonArray = new JSONArray(json);
                if(!jsonArray.isEmpty()){
                    Semver latest = new Semver(jsonArray.getJSONObject(0).getString("tag_name"), Semver.SemverType.STRICT);
                    if(Application.getVersion().isLowerThan(latest)){
                        Desktop.getDesktop().browse(new URI("https://github.com/FlashyReese/fabric-mod-manager/releases"));
                    }else{
                        JOptionPane.showMessageDialog(null, "Up to date!");
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void loadComponents(){
        helpMenu.add(checkForUpdates);
        this.add(helpMenu);
    }
}