package inkball;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * 关卡选择界面
 */
public class LevelSelectScreen {
    private App app;
    private JSONArray levels;
    private int selectedLevel = 0;
    private int totalLevels;
    
    public LevelSelectScreen(App app) {
        this.app = app;
        loadLevels();
    }
    
    private void loadLevels() {
        JSONObject config = app.loadJSONObject(app.configPath);
        levels = config.getJSONArray("levels");
        totalLevels = levels.size();
    }
    
    public void draw() {
        app.background(50, 50, 80);
        
        // 绘制标题
        app.fill(255);
        app.textAlign(PApplet.CENTER);
        app.textSize(32);
        app.text("选择关卡", App.WIDTH / 2, 100);
        
        // 绘制关卡按钮
        int buttonWidth = 120;
        int buttonHeight = 80;
        int spacing = 20;
        int startX = (App.WIDTH - (totalLevels * buttonWidth + (totalLevels - 1) * spacing)) / 2;
        int startY = 200;
        
        for (int i = 0; i < totalLevels; i++) {
            int x = startX + i * (buttonWidth + spacing);
            int y = startY;
            
            // 按钮背景
            if (i == selectedLevel) {
                app.fill(100, 150, 255); // 选中状态
            } else {
                app.fill(80, 80, 120);   // 普通状态
            }
            app.stroke(255);
            app.strokeWeight(2);
            app.rect(x, y, buttonWidth, buttonHeight, 10);
            
            // 关卡编号
            app.fill(255);
            app.textAlign(PApplet.CENTER);
            app.textSize(24);
            app.text("关卡 " + (i + 1), x + buttonWidth / 2, y + buttonHeight / 2 - 10);
            
            // 关卡信息
            JSONObject level = levels.getJSONObject(i);
            app.textSize(12);
            app.text("时间: " + level.getInt("time") + "s", x + buttonWidth / 2, y + buttonHeight / 2 + 10);
            app.text("球数: " + level.getJSONArray("balls").size(), x + buttonWidth / 2, y + buttonHeight / 2 + 25);
        }
        
        // 绘制说明文字
        app.fill(200);
        app.textAlign(PApplet.CENTER);
        app.textSize(16);
        app.text("使用左右箭头键选择关卡", App.WIDTH / 2, 350);
        app.text("按 ENTER 开始游戏", App.WIDTH / 2, 370);
        app.text("按 ESC 返回主菜单", App.WIDTH / 2, 390);
        
        // 绘制选中关卡的详细信息
        if (selectedLevel >= 0 && selectedLevel < totalLevels) {
            JSONObject selectedLevelData = levels.getJSONObject(selectedLevel);
            
            app.fill(255, 255, 255, 200);
            app.rect(50, 420, App.WIDTH - 100, 120, 10);
            
            app.fill(0);
            app.textAlign(PApplet.LEFT);
            app.textSize(14);
            app.text("关卡 " + (selectedLevel + 1) + " 详情:", 70, 445);
            app.text("游戏时间: " + selectedLevelData.getInt("time") + " 秒", 70, 465);
            app.text("球的生成间隔: " + selectedLevelData.getInt("spawn_interval") + " 秒", 70, 485);
            
            // 显示球的颜色
            JSONArray balls = selectedLevelData.getJSONArray("balls");
            String ballsText = "球的颜色: ";
            for (int i = 0; i < balls.size(); i++) {
                ballsText += balls.getString(i);
                if (i < balls.size() - 1) ballsText += ", ";
            }
            app.text(ballsText, 70, 505);
            
            app.text("得分加成: " + selectedLevelData.getFloat("score_increase_from_hole_capture_modifier"), 70, 525);
        }
    }
    
    public void keyPressed(int keyCode) {
        if (keyCode == PApplet.LEFT) {
            selectedLevel = (selectedLevel - 1 + totalLevels) % totalLevels;
        } else if (keyCode == PApplet.RIGHT) {
            selectedLevel = (selectedLevel + 1) % totalLevels;
        } else if (keyCode == PApplet.ENTER) {
            // 开始选中的关卡
            app.startLevel(selectedLevel);
        } else if (keyCode == PApplet.ESC) {
            // 返回主菜单
            app.setGameState(GameState.MENU);
        }
    }
    
    public void mousePressed(int mouseX, int mouseY) {
        // 检查是否点击了关卡按钮
        int buttonWidth = 120;
        int buttonHeight = 80;
        int spacing = 20;
        int startX = (App.WIDTH - (totalLevels * buttonWidth + (totalLevels - 1) * spacing)) / 2;
        int startY = 200;
        
        for (int i = 0; i < totalLevels; i++) {
            int x = startX + i * (buttonWidth + spacing);
            int y = startY;
            
            if (mouseX >= x && mouseX <= x + buttonWidth && 
                mouseY >= y && mouseY <= y + buttonHeight) {
                selectedLevel = i;
                // 双击开始游戏
                app.startLevel(selectedLevel);
                break;
            }
        }
    }
    
    public int getSelectedLevel() {
        return selectedLevel;
    }
} 