package Game2;

import javax.swing.*;
import java.awt.*;

public class MainEx extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainEx() {
        initializeUI();
    }

    private void initializeUI() {
        // �ѱ� ��Ʈ ����
        UIManager.put("OptionPane.messageFont", new Font("���� ���", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("���� ���", Font.PLAIN, 12));

        // ���� ���� �� ���̵� ����
        String message = """
                ���̵��� �����ϼ���: 
                �⺻ ��Ģ : 5���� ����� ������ ��ֹ��� ���������� -1�� ī��Ʈ�մϴ�.
                ���� ��ܿ� ����� ǥ�� �Ǿ� �ְ� 0�� �Ǹ� ������ �ڵ����� ����˴ϴ�.
                �� ���̵� ���� ��ֹ��� �����Ǵ� �ӵ��� �÷��̾��� ũ�Ⱑ �ٸ��ϴ�
                �÷��̾�� �⺻������ ���� �ð��� ��� ��������  ����� �߰��Ǹ�
                �� ���̵� ���� ����� �߰��Ǵ� �ð��� �ٸ��ϴ�.
                
                ������ ������ : ���+1 , �ʷϻ� ������ 5�� ȹ��� ���+1
                500���� ȹ�� �� ������ �ִ� ����� +1 �þ�ϴ� (�ִ� 10)
             
                
                ����: 30�ʸ��� ����� �ϳ��� �����մϴ�.  
                ����: 45�ʸ��� ����� �ϳ��� �����մϴ�.  
                �����: 60�ʸ��� ����� �ϳ��� �����մϴ�.  
                """;

        String[] options = {"����", "����", "�����"};
        String difficulty = (String) JOptionPane.showInputDialog(
                this,
                message,
                "���̵� ����",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (difficulty != null) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 600, 600);
            DodgingGame gamePanel = new DodgingGame(difficulty, this);
            setContentPane(gamePanel);  // ���ο� DodgingGame �гη� ��ü
            revalidate();  // UI ����
        }
    }

    // ������ �ʱ�ȭ�ϴ� �޼���
    public void resetGame() {
        getContentPane().removeAll();  // ���� �г� ����
        initializeUI();                // UI ���ʱ�ȭ�Ͽ� ���̵� ���� ȭ�� ǥ��
        revalidate();                  // UI ����
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
