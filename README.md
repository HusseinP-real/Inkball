# Inkball Game / 弹球游戏

A physics-based puzzle game built with Processing framework where players guide colored balls into matching holes by drawing lines on the screen.

一款基于物理引擎的益智游戏，使用 Processing 框架开发。玩家通过在屏幕上绘制线条来引导彩色球进入匹配的洞中。

## Table of Contents / 目录

- [Features / 特性](#features--特性)
- [System Requirements / 系统要求](#system-requirements--系统要求)
- [Installation / 安装](#installation--安装)
- [How to Run / 运行方法](#how-to-run--运行方法)
- [Gameplay / 游戏玩法](#gameplay--游戏玩法)
- [Controls / 控制说明](#controls--控制说明)
- [Game Mechanics / 游戏机制](#game-mechanics--游戏机制)
- [Level System / 关卡系统](#level-system--关卡系统)
- [Configuration / 配置说明](#configuration--配置说明)
- [Project Structure / 项目结构](#project-structure--项目结构)

## Features / 特性

- **Three Challenging Levels** / 三个挑战关卡: Progressively difficult levels with unique layouts
- **Physics-Based Gameplay** / 基于物理的游戏玩法: Realistic ball movement and collision detection
- **Interactive Drawing** / 交互式绘制: Draw lines with mouse to guide balls
- **Color Matching System** / 颜色匹配系统: Match ball colors with corresponding holes
- **Score System** / 计分系统: Earn points by successfully guiding balls into holes
- **Time Limit** / 时间限制: Complete levels within the time limit
- **Accelerator Tiles** / 加速器方块: Special tiles that change ball direction
- **Wall Collisions** / 墙壁碰撞: Colored walls that can change ball colors

## System Requirements / 系统要求

- **Java 8 or higher** / Java 8 或更高版本
- **Gradle 5.6.3+** (included via wrapper)
- **Operating System** / 操作系统: Windows, macOS, or Linux

## Installation / 安装

### Prerequisites / 前置要求

1. **Clone the repository** / 克隆仓库:
   ```bash
   git clone https://github.com/HusseinP-real/Inkball.git
   cd Inkball
   ```

2. **Ensure Java is installed** / 确保已安装 Java:
   ```bash
   java -version
   ```
   Should display Java 8 or higher / 应显示 Java 8 或更高版本

### Build the Project / 构建项目

The project uses Gradle wrapper, so you don't need to install Gradle separately.

项目使用 Gradle wrapper，因此无需单独安装 Gradle。

**On macOS/Linux** / 在 macOS/Linux 上:
```bash
./gradlew build
```

**On Windows** / 在 Windows 上:
```bash
gradlew.bat build
```

## How to Run / 运行方法

### Run from Command Line / 从命令行运行

**On macOS/Linux** / 在 macOS/Linux 上:
```bash
./gradlew run
```

**On Windows** / 在 Windows 上:
```bash
gradlew.bat run
```

### Run the JAR File / 运行 JAR 文件

After building, you can run the generated JAR file:

构建后，您可以运行生成的 JAR 文件：

```bash
java -jar build/libs/INFO1113\ inkball_hpen0911-1.0.jar
```

## Gameplay / 游戏玩法

### Objective / 游戏目标

Guide all colored balls into their matching colored holes before time runs out. Each level has a specific time limit and set of balls to guide.

在时间耗尽之前，将所有彩色球引导到匹配颜色的洞中。每个关卡都有特定的时间限制和需要引导的球。

### Main Menu / 主菜单

When you start the game, you'll see the main menu with three options:

启动游戏时，您会看到主菜单，包含三个选项：

1. **Start Game** / 开始游戏: Start from Level 1 / 从第 1 关开始
2. **Select Level** / 选择关卡: Choose a specific level to play / 选择特定关卡进行游戏
3. **Exit Game** / 退出游戏: Close the application / 关闭应用程序

**Navigation** / 导航:
- Use **UP/DOWN arrow keys** / 使用上下箭头键 to navigate menu options
- Press **ENTER** to select / 按 ENTER 选择
- Click with **mouse** to select options / 使用鼠标点击选择选项

### Level Select Screen / 关卡选择界面

- Use **LEFT/RIGHT arrow keys** / 使用左右箭头键 to browse levels
- Press **ENTER** to start selected level / 按 ENTER 开始选中的关卡
- Press **ESC** to return to main menu / 按 ESC 返回主菜单
- Click on level buttons with **mouse** to start / 使用鼠标点击关卡按钮开始

## Controls / 控制说明

### In-Game Controls / 游戏内控制

| Key / 按键 | Action / 操作 |
|-----------|--------------|
| **Mouse Drag** / 鼠标拖拽 | Draw lines to guide balls / 绘制线条引导球 |
| **Right Mouse Button** / 右键 | Clear all drawn lines / 清除所有绘制的线条 |
| **SPACE** | Pause/Resume game / 暂停/继续游戏 |
| **R** | Restart current level / 重新开始当前关卡 |
| **M** | Return to main menu / 返回主菜单 |

### Menu Controls / 菜单控制

| Key / 按键 | Action / 操作 |
|-----------|--------------|
| **UP/DOWN** | Navigate menu / 导航菜单 |
| **LEFT/RIGHT** | Navigate level selection / 导航关卡选择 |
| **ENTER** | Confirm selection / 确认选择 |
| **ESC** | Go back / 返回 |

## Game Mechanics / 游戏机制

### Ball Movement / 球的移动

- Balls spawn from **spawn points** (marked as 'S' in level files) / 球从生成点（在关卡文件中标记为 'S'）生成
- Balls move with physics-based velocity / 球以基于物理的速度移动
- Balls bounce off walls and player-drawn lines / 球会从墙壁和玩家绘制的线条反弹

### Drawing Lines / 绘制线条

- **Left-click and drag** / 左键点击并拖拽 to draw lines
- Lines act as barriers that balls will bounce off / 线条作为障碍物，球会从中反弹
- **Right-click** / 右键点击 to clear all drawn lines
- Lines disappear after a ball collides with them / 球与线条碰撞后，线条会消失

### Color Matching / 颜色匹配

- Each ball has a color: **grey**, **orange**, **blue**, **green**, or **yellow** / 每个球都有颜色：灰色、橙色、蓝色、绿色或黄色
- Balls must enter holes of the **same color** / 球必须进入相同颜色的洞
- Entering a **wrong colored hole** reduces score / 进入错误颜色的洞会减少分数

### Wall Interactions / 墙壁交互

- **Grey walls** (X): Regular walls that bounce balls / 普通墙壁，球会反弹
- **Colored walls** (0-4): Change ball color when touched / 彩色墙壁：触碰时改变球的颜色
  - Wall color 0 = Grey / 灰色
  - Wall color 1 = Orange / 橙色
  - Wall color 2 = Blue / 蓝色
  - Wall color 3 = Green / 绿色
  - Wall color 4 = Yellow / 黄色

### Accelerator Tiles / 加速器方块

Special tiles that change ball direction when touched:

特殊方块，触碰时会改变球的方向：

- **U** (UP): Accelerates ball upward / 向上加速
- **D** (DOWN): Accelerates ball downward / 向下加速
- **L** (LEFT): Accelerates ball left / 向左加速
- **R** (RIGHT): Accelerates ball right / 向右加速

### Scoring System / 计分系统

- **Correct hole capture** / 正确捕获: Gain points based on ball color / 根据球的颜色获得分数
  - Grey: 70 points
  - Orange: 50 points
  - Blue: 50 points
  - Green: 50 points
  - Yellow: 100 points
- **Wrong hole** / 错误洞: Lose points / 失去分数
  - Orange/Blue/Green: -25 points
  - Yellow: -100 points
  - Grey: No penalty / 无惩罚
- **Time bonus** / 时间奖励: Remaining time converted to score when level completes / 关卡完成时剩余时间转换为分数
- **Level modifiers** / 关卡修正器: Each level has score multipliers / 每个关卡都有分数倍数

## Level System / 关卡系统

### Level 1 / 第 1 关
- **Time Limit** / 时间限制: 120 seconds / 120 秒
- **Spawn Interval** / 生成间隔: 10 seconds / 10 秒
- **Balls** / 球数: 6 balls (blue, orange, grey, blue, green, yellow)
- **Features** / 特性: Introduction to basic mechanics / 基础机制介绍

### Level 2 / 第 2 关
- **Time Limit** / 时间限制: 180 seconds / 180 秒
- **Spawn Interval** / 生成间隔: 6 seconds / 6 秒
- **Balls** / 球数: 8 balls (green, grey, grey, blue, yellow, orange, blue, grey)
- **Features** / 特性: Increased difficulty with more balls / 难度增加，球数更多

### Level 3 / 第 3 关
- **Time Limit** / 时间限制: 180 seconds / 180 秒
- **Spawn Interval** / 生成间隔: 5 seconds / 5 秒
- **Balls** / 球数: 8 grey balls / 8 个灰色球
- **Features** / 特性: Final challenge with rapid spawning / 最终挑战，快速生成

### Level Completion / 关卡完成

- Complete a level by getting all balls into matching holes / 通过将所有球放入匹配的洞来完成关卡
- Automatically proceed to next level / 自动进入下一关
- Final level completion shows total score / 最终关卡完成时显示总分

## Configuration / 配置说明

The game configuration is stored in `config.json`. You can modify level settings here:

游戏配置存储在 `config.json` 中。您可以在此修改关卡设置：

### Level Configuration / 关卡配置

```json
{
  "layout": "level1.txt",           // Level layout file / 关卡布局文件
  "time": 120,                       // Time limit in seconds / 时间限制（秒）
  "spawn_interval": 10,              // Ball spawn interval in seconds / 球生成间隔（秒）
  "balls": ["blue", "orange", ...]   // List of ball colors / 球颜色列表
}
```

### Score Configuration / 分数配置

```json
{
  "score_increase_from_hole_capture": {
    "grey": 70,
    "orange": 50,
    "blue": 50,
    "green": 50,
    "yellow": 100
  },
  "score_decrease_from_wrong_hole": {
    "grey": 0,
    "orange": 25,
    "blue": 25,
    "green": 25,
    "yellow": 100
  }
}
```

### Level Layout Files / 关卡布局文件

Level layouts are defined in text files (e.g., `level1.txt`) using the following symbols:

关卡布局在文本文件中定义（例如 `level1.txt`），使用以下符号：

| Symbol / 符号 | Meaning / 含义 |
|--------------|---------------|
| **X** | Grey wall / 灰色墙壁 |
| **0-4** | Colored wall (0=grey, 1=orange, 2=blue, 3=green, 4=yellow) / 彩色墙壁 |
| **H0-H4** | Hole with color (H0=grey, H1=orange, etc.) / 带颜色的洞 |
| **B0-B4** | Starting ball with color / 带颜色的起始球 |
| **S** | Spawn point for balls / 球生成点 |
| **U/D/L/R** | Accelerator tile (Up/Down/Left/Right) / 加速器方块 |
| **Space** | Empty tile / 空方块 |

## Project Structure / 项目结构

```
Inkball/
├── src/
│   ├── main/
│   │   ├── java/inkball/
│   │   │   ├── App.java              # Main game class / 主游戏类
│   │   │   ├── GameState.java        # Game state enum / 游戏状态枚举
│   │   │   ├── MainMenuScreen.java   # Main menu UI / 主菜单界面
│   │   │   ├── LevelSelectScreen.java # Level selection UI / 关卡选择界面
│   │   │   ├── Balls.java            # Ball physics and rendering / 球物理和渲染
│   │   │   ├── Walls.java            # Wall rendering / 墙壁渲染
│   │   │   ├── Holes.java            # Hole rendering / 洞渲染
│   │   │   ├── Tiles.java            # Tile rendering / 方块渲染
│   │   │   ├── WaitingBalls.java     # Waiting ball queue / 等待球队列
│   │   │   └── alivePoint.java       # Spawn point / 生成点
│   │   └── resources/inkball/        # Game assets (images) / 游戏资源（图片）
│   └── test/                         # Test files / 测试文件
├── level1.txt                        # Level 1 layout / 第 1 关布局
├── level2.txt                        # Level 2 layout / 第 2 关布局
├── level3.txt                        # Level 3 layout / 第 3 关布局
├── config.json                       # Game configuration / 游戏配置
├── build.gradle                      # Gradle build configuration / Gradle 构建配置
└── README.md                         # This file / 本文件
```

## Tips & Strategies / 技巧与策略

1. **Plan your lines** / 规划你的线条: Think ahead before drawing / 绘制前先思考
2. **Use walls strategically** / 策略性使用墙壁: Colored walls can change ball colors / 彩色墙壁可以改变球的颜色
3. **Clear lines when needed** / 需要时清除线条: Right-click to remove obstacles / 右键点击移除障碍
4. **Watch the timer** / 注意计时器: Manage your time efficiently / 高效管理时间
5. **Use accelerators** / 使用加速器: Position balls to hit accelerator tiles / 将球定位到加速器方块
6. **Color matching priority** / 颜色匹配优先级: Focus on high-value balls (yellow) first / 优先关注高价值球（黄色）

## Troubleshooting / 故障排除

### Game won't start / 游戏无法启动

- Check Java version: `java -version` (should be 8+) / 检查 Java 版本
- Rebuild project: `./gradlew clean build` / 重新构建项目

### Images not loading / 图片无法加载

- Ensure `src/main/resources/inkball/` contains all image files / 确保 `src/main/resources/inkball/` 包含所有图片文件
- Check file paths in code match actual file names / 检查代码中的文件路径是否与实际文件名匹配

### Level files not found / 找不到关卡文件

- Ensure `level1.txt`, `level2.txt`, `level3.txt` are in project root / 确保 `level1.txt`、`level2.txt`、`level3.txt` 在项目根目录
- Check `config.json` references correct file names / 检查 `config.json` 是否引用正确的文件名

## Development / 开发

### Running Tests / 运行测试

```bash
./gradlew test
```

### Building Distribution / 构建分发版本

```bash
./gradlew build
./gradlew distZip  # Creates distribution ZIP / 创建分发 ZIP
```


## Author / 作者

HusseinP-real

## Repository / 仓库

https://github.com/HusseinP-real/Inkball.git

---

**Enjoy the game! / 祝游戏愉快！**

