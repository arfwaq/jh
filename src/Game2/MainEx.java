package Game2;

import javax.swing.*;
import java.awt.*;

public class MainEx extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainEx() {
        initializeUI();
    }

    private void initializeUI() {
        // 한글 폰트 설정
        UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("맑은 고딕", Font.PLAIN, 12));

        // 게임 설명 및 난이도 선택
        String message = """
                난이도를 선택하세요: 
                기본 규칙 : 5개의 목숨이 있으며 장애물에 닿을때마다 -1로 카운트합니다.
                좌측 상단에 목숨이 표시 되어 있고 0이 되면 게임이 자동으로 종료됩니다.
                각 난이도 별로 장애물이 생성되는 속도와 플레이어의 크기가 다릅니다
                플레이어는 기본적으로 일정 시간을 살아 남았을때  목숨이 추가되며
                각 난이도 별로 목숨이 추가되는 시간이 다릅니다.
                
                빨간색 아이템 : 목숨+1 , 초록색 아이템 5개 획득시 목숨+1
                500점을 획득 할 때마다 최대 목숨이 +1 늘어납니다 (최대 10)
             
                
                쉬움: 30초마다 목숨이 하나씩 증가합니다.  
                보통: 45초마다 목숨이 하나씩 증가합니다.  
                어려움: 60초마다 목숨이 하나씩 증가합니다.  
                """;

        String[] options = {"쉬움", "보통", "어려움"};
        String difficulty = (String) JOptionPane.showInputDialog(
                this,
                message,
                "난이도 설정",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (difficulty != null) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 600, 600);
            DodgingGame gamePanel = new DodgingGame(difficulty, this);
            setContentPane(gamePanel);  // 새로운 DodgingGame 패널로 교체
            revalidate();  // UI 갱신
        }
    }

    // 게임을 초기화하는 메서드
    public void resetGame() {
        getContentPane().removeAll();  // 기존 패널 제거
        initializeUI();                // UI 재초기화하여 난이도 선택 화면 표시
        revalidate();                  // UI 갱신
        repaint();
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainEx frame = new MainEx();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
