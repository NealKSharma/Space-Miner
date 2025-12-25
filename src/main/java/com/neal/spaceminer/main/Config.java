package com.neal.spaceminer.main;

import java.io.*;
import java.nio.file.Path;

public class Config {

    GamePanel gamePanel;

    public Config(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void saveConfig(){
        Path saveData = Path.of("gameConfig.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveData.toFile()))){
            // FULLSCREEN
            if(gamePanel.fullScreen){
                bw.write("On");
            } else {
                bw.write("Off");
            }
            bw.newLine();

            // MINIMAP
            if(gamePanel.map.miniMapOn){
                bw.write("On");
            } else {
                bw.write("Off");
            }
            bw.newLine();

            // SOUND VOLUME
            bw.write(String.valueOf(gamePanel.ui.volume));
            bw.newLine();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try (BufferedReader br = new BufferedReader(new FileReader("gameConfig.txt"))){
            // FULLSCREEN
            String s = br.readLine();
            if(s.equals("On")){
                gamePanel.fullScreen = true;
            } else if(s.equals("Off")){
                gamePanel.fullScreen = false;
            }


            // MINIMAP
            s = br.readLine();
            if(s.equals("On")){
                gamePanel.map.miniMapOn = true;
            } else if(s.equals("Off")){
                gamePanel.map.miniMapOn = false;
            }

            // SOUND
            s = br.readLine();
            gamePanel.ui.volume = Integer.parseInt(s);

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}