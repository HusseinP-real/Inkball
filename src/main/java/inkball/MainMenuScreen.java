package inkball;

import processing.core.PApplet;

/**
 * 主菜单界面
 */
public class MainMenuScreen {
    private App app;
    private int selectedOption = 0;
    private String[] menuOptions = {"开始游戏", "选择关卡", "退出游戏"};
    
    public MainMenuScreen(App app) {
        this.app = app;
    }
    
    public void draw() {
        app.background(30, 30, 50);
        
        // 绘制游戏标题
        app.fill(255, 255, 100);
        app.textAlign(PApplet.CENTER);
        app.textSize(48);
        app.text("INKBALL", App.WIDTH / 2, 150);
        
        // 绘制副标题
        app.fill(200);
        app.textSize(16);
        app.text("弹球游戏", App.WIDTH / 2, 180);
        
        // 绘制菜单选项
        int startY = 250;
        int spacing = 50;
        
        for (int i = 0; i < menuOptions.length; i++) {
            int y = startY + i * spacing;
            
            // 选中状态的背景
            if (i == selectedOption) {
                app.fill(100, 150, 255, 100);
                app.rect(App.WIDTH / 2 - 100, y - 25, 200, 40, 10);
            }
            
            // 菜单文字
            if (i == selectedOption) {
                app.fill(255, 255, 100);
            } else {
                app.fill(255);
            }
            app.textAlign(PApplet.CENTER);
            app.textSize(24);
            app.text(menuOptions[i], App.WIDTH / 2, y);
        }
        
        // 绘制操作说明
        app.fill(150);
        app.textSize(14);
        app.text("使用上下箭头键选择，按 ENTER 确认", App.WIDTH / 2, App.HEIGHT - 50);
    }
    
    public void keyPressed(int keyCode) {
        if (keyCode == PApplet.UP) {
            selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
        } else if (keyCode == PApplet.DOWN) {
            selectedOption = (selectedOption + 1) % menuOptions.length;
        } else if (keyCode == PApplet.ENTER) {
            handleMenuSelection();
        }
    }
    
    public void mousePressed(int mouseX, int mouseY) {
        // 检查是否点击了菜单选项
        int startY = 250;
        int spacing = 50;
        
        for (int i = 0; i < menuOptions.length; i++) {
            int y = startY + i * spacing;
            
            if (mouseX >= App.WIDTH / 2 - 100 && mouseX <= App.WIDTH / 2 + 100 &&
                mouseY >= y - 25 && mouseY <= y + 15) {
                selectedOption = i;
                handleMenuSelection();
                break;
            }
        }
    }
    
    private void handleMenuSelection() {
        switch (selectedOption) {
            case 0: // 开始游戏
                app.setGameState(GameState.PLAYING);
                app.startLevel(0);
                break;
            case 1: // 选择关卡
                app.setGameState(GameState.LEVEL_SELECT);
                break;
            case 2: // 退出游戏
                app.exit();
                break;
        }
    }
} 