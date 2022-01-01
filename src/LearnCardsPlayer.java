import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LearnCardsPlayer {
    private ArrayList<LearnCard> cardList;
    private JTextArea display;
    private JFrame frame;
    private JButton nextCardButton;
    private boolean isAnsweredDisplayed;
    private int actualCardIndex;
    private LearnCard actualCard;

    public static void main(String[] args) {
        LearnCardsPlayer app = new LearnCardsPlayer();
        app.show();
    }
    public void show(){
        frame = new JFrame("LearnCards v1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        display = new JTextArea(10, 20);
        display.setFont(bigFont);
        display.setLineWrap(true);
        display.setEditable(false);

        JScrollPane fScroller = new JScrollPane(display);
        fScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        nextCardButton = new JButton("Show Question");
        nextCardButton.addActionListener(new NextCardListener());
        mainPanel.add(fScroller);
        mainPanel.add(nextCardButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemLoad = new JMenuItem("Load Card-set");
        menuItemLoad.addActionListener(new MenuLoadListener());
        menuFile.add(menuItemLoad);
        menuBar.add(menuFile);

        frame.setJMenuBar(menuBar);
        frame.add(BorderLayout.CENTER, mainPanel);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            if(isAnsweredDisplayed){
                display.setText(actualCard.getAnswer());
                nextCardButton.setText("Next Card");
                isAnsweredDisplayed = false;
            }else{
                if(actualCardIndex < cardList.size()){
                    nextCardDisplay();
                }else{
                    display.setText("That was the last card!");
                    nextCardButton.setEnabled(false);
                }
            }
        }
    }
    public class MenuLoadListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser openFile = new JFileChooser();
            openFile.showSaveDialog(frame);
            fileLoad(openFile.getSelectedFile());
        }
    }
    private void fileLoad(File file){
        cardList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String row;
            while ((row = reader.readLine()) != null){
                makeCard(row);
            }
        }catch (Exception ex){
            System.out.println("Can't read card file.");
            ex.printStackTrace();
        }
    }
    private void makeCard(String parsingRow){
        String[] questionAnswer = parsingRow.split("/");
        LearnCard card = new LearnCard(questionAnswer[0], questionAnswer[1]);
        cardList.add(card);
        System.out.println("Made a card.");
    }
    private void nextCardDisplay(){
        actualCard = cardList.get(actualCardIndex);
        ++actualCardIndex;
        display.setText((actualCard.getQuestion()));
        nextCardButton.setText("Show the answer");
        isAnsweredDisplayed = true;
    }

}
