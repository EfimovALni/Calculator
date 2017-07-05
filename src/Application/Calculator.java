package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by alex on 5.7.17.
 */
public class Calculator extends JFrame{
    public static double widthUserScreen =  0.0;
    public static double heightUserScreen = 0.0;

    public final int appWidth = 330;
    public final int appHeight = 485;

    private JTextArea history;
    private JTextArea display;
    private JScrollPane jScrollPaneHistory;

    private JButton backspace;      // ←
    private JButton clearDisplay;
    private JButton clearMemory;
    private JButton reserv1;
    private JButton square;

    private JButton seven;
    private JButton eight;
    private JButton nine;
    private JButton division;       // ÷
    private JButton percent;        // %


    private JButton four;
    private JButton five;
    private JButton six;
    private JButton multiplication;  // × •
    private JButton pi;              // π


    public JButton one;
    private JButton two;
    private JButton three;
    private JButton subtraction;    // −
    private JButton sqrt;           // √

    private JButton zero;
    private JButton znakPlusMinus;   // ±
    private JButton dot;
    private JButton addition;
    private JButton equals;

    private char operationChar;


    public static void main(String[] args) {

        new Calculator();

    }


    public Calculator() {
        super("CASIO v.0.0.1");

        Dimension screenSizeUser = Toolkit.getDefaultToolkit().getScreenSize(); // For calculate user screen

        widthUserScreen = screenSizeUser.width;
        heightUserScreen = screenSizeUser.height;

        buttons();
        displays();
        mainForm(this);

    }

    private void buttons() {
        backspace = new JButton("←");
        backspace.setBounds(5, 210, 60,50);
        backspace.setFont(new Font("Arial", Font.BOLD, 15));
        backspace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(display.getText().length() > 1) {
                    display.setText(display.getText().substring(0,display.getText().length() - 1));
                    if (display.getText().equals("-"))
                        display.setText("0");
                } else if (display.getText().length() == 1) {
                    display.setText("0");
                }
            }
        });

        add(backspace);

        clearMemory = new JButton("CM");
        clearMemory.setBounds(70, 210, 60, 50);
        clearMemory.setFont(new Font("Arial", Font.PLAIN, 15));
        clearMemory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                display.setText("0");
                history.setText("");
            }
        });
        add(clearMemory);

        clearDisplay = new JButton("C");
        clearDisplay.setBounds(135, 210, 60, 50);
        clearDisplay.setFont(new Font("Arial", Font.PLAIN, 15));
        clearDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                display.setText("0");
            }
        });
        add(clearDisplay);

        reserv1 = new JButton("res1");
        reserv1.setBounds(200, 210, 60, 50);
        reserv1.setFont(new Font("Arial", Font.PLAIN, 10));
        add(reserv1);

        square = new JButton("Х²");
        square.setBounds(265, 210, 60, 50);
        square.setFont(new Font("Arial", Font.PLAIN, 15));
        square.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double tempD = Double.parseDouble(display.getText());
                String tempResS = String.valueOf((tempD * tempD));

//                display.setText());
                if (tempResS.endsWith(".0")) {
                    display.setText(tempResS.replace(".0", ""));
                    return;
                } else {
                    display.setText(tempResS);
                }
            }
        });
        add(square);


        seven = new JButton("7");
        seven.setBounds(5, 265, 60, 50);
        seven.setFont(new Font("Arial", Font.PLAIN, 30));
        seven.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("7");
                return;
                }
                display.append("7");
            }
        });
        add(seven);

        eight = new JButton("8");
        eight.setBounds(70, 265, 60, 50);
        eight.setFont(new Font("Arial", Font.PLAIN, 30));
        eight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("8");
                    return;
                }
                display.append("8");
            }
        });
        add(eight);

        nine = new JButton("9");
        nine.setBounds(135, 265, 60, 50);
        nine.setFont(new Font("Arial", Font.PLAIN, 30));
        nine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("9");
                    return;
                }
                display.append("9");
            }
        });
        add(nine);

        division = new JButton("÷");
        division.setBounds(200, 265, 60,50);
        division.setFont(new Font("Arial", Font.PLAIN, 30));
        division.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(division);

        percent = new JButton("%");
        percent.setBounds(265, 265, 60, 50);
        percent.setFont(new Font("Arial", Font.PLAIN, 20));
        percent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double tempNumb1 = Double.parseDouble(display.getText());
                double tempNumb2 = 10.0;   // Сделать что бы второе число с дисплея читалось

                String resS = String.valueOf(tempNumb1 * 0.01 * tempNumb2);    // Calculate 10%

                if (resS.endsWith(".0")) {
                    display.setText(resS.replace(".0", ""));
                } else {
                    display.setText(resS);
                }
            }
        });
        add(percent);


        four = new JButton("4");
        four.setBounds(5, 320, 60, 50);
        four.setFont(new Font("Arial", Font.PLAIN, 30));
        four.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("4");
                    return;
                }
                display.append("4");
            }
        });
        add(four);

        five = new JButton("5");
        five.setBounds(70, 320, 60, 50);
        five.setFont(new Font("Arial", Font.PLAIN, 30));
        five.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")){
                    display.setText("5");
                    return;
                }
                display.append("5");
            }
        });
        add(five);

        six = new JButton("6");
        six.setBounds(135, 320, 60, 50);
        six.setFont(new Font("Arial", Font.PLAIN, 30));
        six.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("6");
                    return;
                }
                display.append("6");
            }
        });
        add(six);

        multiplication = new JButton("×");
        multiplication.setBounds(200, 320, 60, 50);
        multiplication.setFont(new Font("Arial", Font.PLAIN, 30));
        multiplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(multiplication);

        pi = new JButton("π");
        pi.setBounds(265, 320, 60, 50);
        pi.setFont(new Font("Arial", Font.PLAIN,20));
        pi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double pi = new BigDecimal(Math.PI).setScale(8, RoundingMode.UP).doubleValue();
                display.setText(String.valueOf(pi));
            }
        });
        add(pi);


        one = new JButton("1");
        one.setBounds(5, 375, 60, 50);
        one.setFont(new Font("Arial", Font.PLAIN, 30));
        one.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("1");
                    return;
                }
                display.append("1");
            }
        });
        add(one);

        two = new JButton("2");
        two.setBounds(70, 375, 60 ,50);
        two.setFont(new Font("Arial", Font.PLAIN, 30));
        two.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("2");
                    return;
                }
                display.append("2");
            }
        });
        add(two);

        three = new JButton("3");
        three.setBounds(135, 375, 60, 50);
        three.setFont(new Font("Arial", Font.PLAIN, 30));
        three.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("3");
                    return;
                }
                display.append("3");
            }
        });
        add(three);

        subtraction = new JButton("−");
        subtraction.setBounds(200, 375, 60, 50);
        subtraction.setFont(new Font("Arial", Font.PLAIN, 30));
        subtraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(subtraction);

        sqrt = new JButton("√");
        sqrt.setBounds(265, 375, 60, 50);
        sqrt.setFont(new Font("Arial", Font.PLAIN, 20));
        sqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double tempD = Double.parseDouble(display.getText());

                if (tempD < 0) {
                    display.setText("0");
                    return;
                } else {
                    String tempStr = String.valueOf(Math.sqrt(tempD));

                    if (tempStr.endsWith(".0")) {
                        display.setText(tempStr.replace(".0", ""));
                    } else {
                        display.setText(tempStr);
                    }
                }
            }
        });
        add(sqrt);


        zero = new JButton("0");
        zero.setBounds(5, 430, 60, 50);
        zero.setFont(new Font("Arial", Font.PLAIN, 30));
        zero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (display.getText().length() > 9)
                    return;
                if (display.getText().equalsIgnoreCase("0")) {
                    display.setText("0");
                    return;
                }
                display.append("0");
            }
        });
        add(zero);

        znakPlusMinus = new JButton("±");
        znakPlusMinus.setBounds(70, 430, 60, 50);
        znakPlusMinus.setFont(new Font("Arial", Font.PLAIN, 30));
        znakPlusMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String tempZnakPlusMinusS = display.getText();
                Double tempZnakPlusMinusD = (Double.parseDouble(tempZnakPlusMinusS) * -1);

                if (tempZnakPlusMinusD == 0)
                    return;
                if (tempZnakPlusMinusD.toString().endsWith(".0"))
                {
                    display.setText(tempZnakPlusMinusD.toString().replace(".0",""));
                } else {
                    display.setText(tempZnakPlusMinusD.toString());
                }
            }
        });
        add(znakPlusMinus);

        dot = new JButton(".");
        dot.setBounds(135, 430, 60, 50);
        dot.setFont(new Font("Arial", Font.PLAIN, 30));
        dot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!display.getText().contains("."))
                    display.append(".");
                    else
                        return;
            }
        });
        add(dot);

        addition = new JButton("+");
        addition.setBounds(200, 430, 60 ,50);
        addition.setFont(new Font("Arial", Font.PLAIN, 30));
        addition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(addition);

        equals = new JButton("=");
        equals.setBounds(265, 430, 60, 50);
        equals.setFont(new Font("Arial", Font.PLAIN, 40));
        equals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(equals);
    }

    private void displays() {

        history = new JTextArea();
        jScrollPaneHistory = new JScrollPane(history);
        jScrollPaneHistory.setBounds(5, 5, appWidth - 10, 150);
        history.setFont(new Font("Arial", Font.PLAIN, 20));
//        history.setEditable(false);
        add(jScrollPaneHistory);

        display = new JTextArea("0");
        display.setBounds(5, 160, appWidth - 10,40);
        display.setFont(new Font("Arial",Font.PLAIN, 40));
        display.setBackground(Color.lightGray);
//        display.setEditable(false);
        add(display);
    }

    private void mainForm(Calculator app) {
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(appWidth, appHeight);
        app.setLocation(((int) widthUserScreen / 2) - (appWidth / 2),
                ((int) heightUserScreen / 2) - (appHeight / 2)); // Calculate center position on screen user
        app.setLayout(null);
        app.setResizable(false);
        app.setVisible(true);
    }
}
