import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homework_1 {

    // 宣告為 static 靜態變數，讓 main 方法內部的按鈕監聽器可以修改與紀錄
    static int rollCount = 0;
    static int totalSum = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("骰子模擬器");
        frame.setSize(400, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- 補上規格缺少的上方標籤 ---
        JLabel statsLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        frame.add(statsLabel, BorderLayout.NORTH);

        JLabel diceLabel = new JLabel("?", SwingConstants.CENTER);
        diceLabel.setFont(new Font("Dialog", Font.BOLD, 60));
        frame.add(diceLabel, BorderLayout.CENTER);

        JButton rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("Dialog", Font.PLAIN, 24));
        frame.add(rollButton, BorderLayout.SOUTH);

        // --- 核心邏輯：加入按鈕點擊事件 ---
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 隨機產生 1~6 的數字
                int diceValue = (int) (Math.random() * 6) + 1;
                
                // 更新中央標籤的數字
                diceLabel.setText(String.valueOf(diceValue));

                // 2. 依照點數改變顏色
                if (diceValue == 6) {
                    diceLabel.setForeground(Color.GREEN); // 點數 6 變綠色
                } else if (diceValue == 1) {
                    diceLabel.setForeground(Color.RED);   // 點數 1 變紅色
                } else {
                    diceLabel.setForeground(Color.BLACK); // 其餘黑色
                }

                // 3. 計算統計數據並更新上方標籤
                rollCount++;             // 增加擲骰次數
                totalSum += diceValue;   // 累加總和
                double average = (double) totalSum / rollCount; // 計算平均值 (強制轉型 double 以保留小數)
                
                // 格式化字串 (%.2f 代表小數點後兩位)
                String statsText = String.format("已擲 %d 次，總和 %d，平均 %.2f", rollCount, totalSum, average);
                statsLabel.setText(statsText);
            }
        });

        frame.setVisible(true);
    }
}