import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LearnCardsAuthor {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<LearnCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        LearnCardsAuthor app = new LearnCardsAuthor();
        app.show();
    }
    public void show(){
        frame = new JFrame("LearnCards (Author-mode)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);
        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);
        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextCardButton = new JButton("Next Card");

        cardList = new ArrayList<>();

        JLabel qLabel = new JLabel("The question is:");
        JLabel aLabel = new JLabel("The answer is:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextCardButton);
        nextCardButton.addActionListener(new NextCardListener());
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem menuItemSave = new JMenuItem("Save");
        menuItemNew.addActionListener(new MenuNewListener());
        menuItemSave.addActionListener(new MenuSaveListener());


        menuFile.add(menuItemNew);
        menuFile.add(menuItemSave);
        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);
        frame.add(BorderLayout.CENTER, mainPanel);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            LearnCard card = new LearnCard(question.getText(), answer.getText());
            cardList.add(card);
            cardClear();
        }
    }
    public class MenuNewListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            cardList.clear();
            cardClear();
        }
    }
    public class MenuSaveListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            LearnCard card = new LearnCard(question.getText(), answer.getText());
            cardList.add(card);

            JFileChooser fileChoose = new JFileChooser();
            fileChoose.showSaveDialog(frame);
            fileSave(fileChoose.getSelectedFile());
        }
    }
    private void cardClear(){
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
    public void fileSave(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(LearnCard card:cardList){
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't write the card-list!");
            e.printStackTrace();}
    }
}
