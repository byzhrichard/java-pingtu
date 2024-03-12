package com.byzh_pingtu.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    JMenuItem changeGirlItem = new JMenuItem("美女");
    JMenuItem changeAnimalItem = new JMenuItem("动物");
    JMenuItem changeSportsItem = new JMenuItem("运动");
    JMenuItem repalyItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭");

    JMenuItem accountItem = new JMenuItem("公众号");
    //创建二维数组
    int[][] data =new int[4][4];
    int[][] win = new int[][]{
        {1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0},
    };
    int step = 0;
    //获取0的坐标
    int x,y;
    String path = "image\\animal\\animal3\\";
    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据
        initData();
        //初始化图片
        initImage();
        //可视化
        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        //打乱
        for (int i = 0; i < tempArr.length; i++) {
            int index = rd_int(0,tempArr.length-1);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //交给二维数组
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0){
                x = i/4;
                y = i%4;
            }
            data[i/4][i%4] = tempArr[i];
        }
    }

    private void initImage() {
        //删除:除去原有图片,使上一次状态的图片消失
        this.getContentPane().removeAll();

        if (victory()){
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }

        //步数
        JLabel stepCount = new JLabel("步数:"+step);
        stepCount.setBounds(50,50,100,20);
        this.getContentPane().add(stepCount);

        //拼图
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];
                //嵌套创建对象,带参构造放入管理容器(因为没有0这个图片,所以0为空图片)
                JLabel pingtu = new JLabel(new ImageIcon(path + num + ".jpg"));
                //指定位置
                pingtu.setBounds(105 *j + 83,105 *i + 134,105,105);
                //加边框
                pingtu.setBorder(new BevelBorder(BevelBorder.RAISED));
                //添加到界面中
                this.getContentPane().add(pingtu);
            }
        }
        //背景
        JLabel background = new JLabel(new ImageIcon("image\\background.png"));
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);
        //刷新:除去旧的且载入新的
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //JMenuBar
        JMenuBar jmBar = new JMenuBar();
        //JMenu
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeJMenu = new JMenu("更换图片");
        //菜单添加
        changeJMenu.add(changeAnimalItem);
        changeJMenu.add(changeGirlItem);
        changeJMenu.add(changeSportsItem);

        functionJMenu.add(changeJMenu);
        functionJMenu.add(repalyItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        jmBar.add(functionJMenu);
        jmBar.add(aboutJMenu);
        //给条目绑定事件
        changeAnimalItem.addActionListener(this);
        changeSportsItem.addActionListener(this);
        changeGirlItem.addActionListener(this);

        repalyItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        //设置菜单
        this.setJMenuBar(jmBar);
    }

    private void initJFrame() {
        //宽 高
        this.setSize(603,680);
        //标题
        this.setTitle("byzh拼图单机版v1.0");
        //前端显示
        this.setAlwaysOnTop(true);
        //界面居中
        this.setLocationRelativeTo(null);
        //一键退出
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消组件的居中放置
        this.setLayout(null);
        //添加键盘监听事件
        this.addKeyListener(this);
    }
    //判断data是否与win相同
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    //获取随机数
    public int rd_int(int min,int max){
        Random r = new Random();
        return r.nextInt(min,max+1);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == 49){
            //清屏
            this.getContentPane().removeAll();
            //答案
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //背景
            JLabel background = new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40,40,508,560);
            this.getContentPane().add(background);
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()){
            return;
        }
        //左37 上38 右39 下40
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        switch (keyCode){
            case 37,65->{
                if (y==3){
                    return;
                }
                System.out.println("left");
                data[x][y] = data[x][y+1];
                data[x][y+1] = 0;
                y++;
                step++;
                initImage();
            }
            case 38,87->{
                if (x==3){
                    return;
                }
                System.out.println("up");
                data[x][y] = data[x+1][y];
                data[x+1][y] = 0;
                x++;
                step++;
                initImage();
            }
            case 39,68->{
                if (y==0){
                    return;
                }
                System.out.println("right");
                data[x][y] = data[x][y-1];
                data[x][y-1] = 0;
                y--;
                step++;
                initImage();
            }
            case 40,83->{
                if (x==0){
                    return;
                }
                System.out.println("down");
                data[x][y] = data[x-1][y];
                data[x-1][y] = 0;
                x--;
                step++;
                initImage();
            }
            case 49->{
                //恢复原有图片
                initImage();
            }
            case 48->{
                //作弊
                //注意:不能令data=win,因为是地址值赋值,所以若在作弊后再按上下左右就会修改win里面的值
                data = new int[][]{
                        {1,2,3,4},
                        {5,6,7,8},
                        {9,10,11,12},
                        {13,14,15,0},
                };
                initImage();
                x=3;
                y=3;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == repalyItem){
            System.out.println("重新开始");
            //打乱
            initData();
            //计步器
            step = 0;
            //加载图片
            initImage();

        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.out.println("关闭");
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("公众号");

            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("image\\about.png"));
            jLabel.setBounds(0,0,258,258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            //弹窗不关闭就无法操作下面的界面
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }else if (obj == changeAnimalItem){
            String newpath = "image\\animal\\animal" + rd_int(1,8) + "\\";
            while (path.equals(newpath)){
                newpath = "image\\animal\\animal" + rd_int(1,8) + "\\";
            }
            path = newpath;
            initData();
            initImage();
        }else if (obj == changeGirlItem){
            String newpath = "image\\girl\\girl" + rd_int(1,13) + "\\";
            while (path.equals(newpath)){
                newpath = "image\\girl\\girl" + rd_int(1,13) + "\\";
            }
            path = newpath;
            initData();
            initImage();
        }else if (obj == changeSportsItem){
            String newpath = "image\\sport\\sport" + rd_int(1,10) + "\\";
            while (path.equals(newpath)){
                newpath = "image\\sport\\sport" + rd_int(1,10) + "\\";
            }
            path = newpath;
            initData();
            initImage();
        }
    }
}
