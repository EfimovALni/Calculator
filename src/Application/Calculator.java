package Application;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by alex on 5.7.17.
 */
public class Calculator extends JFrame{

    public static double widthUserScreen =  0.0;
    public static double heightUserScreen = 0.0;

    private final int appWidth = 330;
    private final int appHeight = 485;

    public boolean pressedOrUnpressedDigit; // For defined pressed or unpressed digit, include '±', '.', 'π', 'Х²', '√'
    public boolean znakFlag;        // Show was or wasn't delete 'sign of number' (i.e. '-3.4') from TextArea
    public boolean plusFlag;        // Indication of "flag" or "unflag" button '+'
    public boolean minusFlag;       // Indication of "flag" or "unflag" button '-'
    public boolean supplyFlag;       // Indication of "flag" or "unflag" button '*'
    public boolean equalsFlag;      // Show was or wasn't pressed button '='  Показывает была ли нажата кнопка "=".
    public boolean equalsPressFlag; /* Показывает было ли нажато '='
                                       Это для того что бы когда мы нажимает более одного раза действие 2+3+5=10
                                       А потом нажали на ClearDisplay, и история чиста!
                                       Сброс этого флага означает, что на кнопки "+" или (другой операции) программа
                                       пойдет по обычномуусловию, а не как 2+5+4+3+3+..+3 = ответ! */

    private Double result = 0.0;
    private Double tempPlus = 0.0;

    public char operationChar;      // Show which 'sign of operation' now is choose

    private Double numberOne;       // The first input number
    private Double numberTwo;       // The second input number
    private Double tempResult;

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


    private JButton one;
    private JButton two;
    private JButton three;
    private JButton subtraction;    // −
    private JButton sqrt;           // √

    private JButton zero;
    private JButton znakPlusMinus;   // ±
    private JButton dot;
    private JButton addition;
    private JButton equals;

    DecimalFormat decimalFormat = new DecimalFormat();
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();


    public char getOperationChar() {
        return operationChar;
    }

    public void setOperationChar(char operationChar) {
        this.operationChar = operationChar;
    }


    public Double getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(Double numberOne) {
        this.numberOne = numberOne;
    }

    public Double getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(Double numberTwo) {
        this.numberTwo = numberTwo;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getTempPlus() {
        return tempPlus;
    }

    public void setTempPlus(Double tempPlus) {
        this.tempPlus = tempPlus;
    }

    public Double getTempResult() {
        return tempResult;
    }

    public void setTempResult(Double tempResult) {
        this.tempResult = tempResult;
    }

    public static double getWidthUserScreen() {
        return widthUserScreen;
    }

    public static void setWidthUserScreen(double widthUserScreen) {
        Calculator.widthUserScreen = widthUserScreen;
    }


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
                setNumberOne(0.0);
                setNumberTwo(0.0);
                setTempResult(0.0);
                equalsPressFlag = false;    // Это для того что бы когда мы нажимает более одного раза действие 2+3+5=10
                // А потом нажали на ClearDisplay, и история чиста!
                // Сброс этого флага означает, что на кнопки "+" или (другой операции) программапойдет по обычномуусловию, а не как
                // 2+5+4+3+3+..+3 = ответ!
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
        reserv1.setEnabled(false);
        add(reserv1);

        square = new JButton("Х²");
        square.setBounds(265, 210, 60, 50);
        square.setFont(new Font("Arial", Font.PLAIN, 15));
        square.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pressedOrUnpressedDigit = true;
                double tempD = Double.parseDouble(display.getText());
                String tempResS = String.valueOf((tempD * tempD));

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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
        percent.setEnabled(false);
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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                /*setOperationChar('*');
                znakFlag = true;
                plusFlag = true;

                try {
                    if (pressedOrUnpressedDigit) {
                        // This condition to trim extra zero's, i.e. '10.00' --> '10'
                        equalsFlag = false;

                        if (!equalsPressFlag) {
                            setNumberOne(Double.parseDouble(display.getText()));
                            toTrimZero(getNumberOne());
                            history.append("\n" + String.valueOf(getOperationChar()) + "\n");                            equalsPressFlag = true;

                        } else if (equalsPressFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            display.setText("0");
                            setTempResult(getNumberOne() * getNumberTwo());
                            System.out.println("Double press '*': " + getResult());

                            history.append(" ");    // For gap between operation sign & sign of digit (i.e. + -34.2)
                            toTrimZero(getNumberTwo());

                            history.append("\n = ");
                            toTrimZero(getTempResult());
                            history.append("\n");

                            setNumberOne(getTempResult());
                            history.append(String.valueOf(getOperationChar()) + "\n");
                        }
                    } else if (equalsFlag) {
                        // выполнение этого условия позволяет прибавлять к ответу что то еще, что набрано на цифровой клаве
                        history.append(getOperationChar() + "\n");
                        setNumberOne(Double.parseDouble(display.getText()));
                        display.setText("0");

                        if (znakFlag) {
                            // Это услови удаляет, введеную операцию и меняет её на '+'
                            if (!history.getText().endsWith("*")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                        equalsFlag = false;
                    } else {
                        if (znakFlag) {
                            if (!history.getText().endsWith("*")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                System.out.println("test:\t" + operationChar);
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                    }
                    pressedOrUnpressedDigit = false;

                } catch (Exception e) {
                    System.err.println("Exception, because wasn't pressed some digits.");
                }*/


                /*
                Пока непонятно вводить ли этот Десимал или "реплэйсом" обойтись
                Тот громозкийй, да и этот надо куда то в функцию выносить..
                Короче надо подумать! */

//                format = new Format(Math.PI);

               /* decimalFormatSymbols.setDecimalSeparator('.');
                decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
                decimalFormat.setDecimalSeparatorAlwaysShown(false);
                setNumberOne(Double.parseDouble(display.getText()));
                history.append(decimalFormat.format(getNumberOne()));*/
            }
        });
        add(multiplication);

        pi = new JButton("π");
        pi.setBounds(265, 320, 60, 50);
        pi.setFont(new Font("Arial", Font.PLAIN,20));
        pi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pressedOrUnpressedDigit = true;
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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;     // Say us about "The digit was pressed" include ".", "±"
//                equalsPressFlag = false;

                if (display.getText().length() > 9) {
                    return;
                }
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
               /* setOperationChar('-');
                znakFlag = true;
                minusFlag = true;

                try {
                    if (pressedOrUnpressedDigit) {

                        equalsFlag = false;

                        if (!equalsPressFlag) {
                            setNumberOne(Double.parseDouble(display.getText()));
                            toTrimZero(getNumberOne());
                            history.append("\n" + String.valueOf(getOperationChar()) + "\n");                            equalsPressFlag = true;

                        } else if (equalsPressFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            display.setText("0");
                            setTempResult(getNumberOne() - getNumberTwo());
                            System.out.println("Double press '-': " + getResult());

                            history.append(" ");    // For gap between operation sign & sign of digit (i.e. + -34.2)
                            toTrimZero(getNumberTwo());

                            history.append("\n = ");
                            toTrimZero(getTempResult());
                            history.append("\n");

                            setNumberOne(getTempResult());
                            history.append(String.valueOf(getOperationChar()) + "\n");
                        }
                    } else if (equalsFlag) {
                        // выполнение этого условия позволяет прибавлять к ответу что то еще, что набрано на цифровой клаве
                        history.append(getOperationChar() + "\n");
                        setNumberOne(Double.parseDouble(display.getText()));
                        display.setText("0");

                        if (znakFlag) {
                            // Это услови удаляет, введеную операцию и меняет её на '+'
                            if (!history.getText().endsWith("-")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                        equalsFlag = false;
                    } else {
                        if (znakFlag) {
                            if (!history.getText().endsWith("-")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                System.out.println("test:\t" + operationChar);
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                    }
                    pressedOrUnpressedDigit = false;
                } catch (Exception e) {
                    System.err.println("Exception, because wasn't pressed some digits.");
                }*/
            }
                /*setOperationChar('-');
                znakFlag = true;
                plusFlag = true;

                if (pressedOrUnpressedDigit) {
                    // Это условие обрезает просто нули, напр: '10.00', выведет '10'
                    equalsFlag = false;

                    if (!equalsPressFlag) {
                        setNumberOne(Double.parseDouble(display.getText()));
                        if (getNumberOne().toString().endsWith(".0")) {
                            history.append(getNumberOne().toString().replace(".0", "")
                                    + "\n" + getOperationChar() + "\n");
                            display.setText("0");
                        } else {
                            history.append(getNumberOne().toString() + "\n" + getOperationChar() + "\n");
                            display.setText("0");
                        }
                        equalsPressFlag = true;

                    } else if (equalsPressFlag) {
                        setNumberTwo(Double.parseDouble(display.getText()));
                        display.setText("0");
                        System.out.println("Double press '-': " + getNumberTwo());

                        setTempResult(getNumberOne() - getNumberTwo());
                        history.append(*//*String.valueOf(getOperationChar()) *//* + getNumberTwo() + "\n = " + String.valueOf(getTempResult()) + "\n");

                        setNumberOne(getTempResult());

                        history.append(String.valueOf(getOperationChar()));
                    }

                } else if (equalsFlag) {
                    // выполнение этого условия позволяет прибавлять к ответу что то еще, что набрано на цифровой клаве
                    history.append(getOperationChar() + "\n");
                    setNumberOne(Double.parseDouble(display.getText()));
                    display.setText("0");

                    if (znakFlag) {
                        // Это условие удаляет, введеную операцию и меняет её на '+'
                        if (!history.getText().endsWith("-")) {
                            for (int i = 0; i < 2; i++) {
                                history.setText(history.getText().substring(0, history.getText().length() - 1));
                            }
                            history.append(String.valueOf(getOperationChar()) + "\n");
                        }
                    }
                    znakFlag = false;
                    equalsFlag = false;
                } else {
                    if (znakFlag) {
                        if (!history.getText().endsWith("-")) {
                            for (int i = 0; i < 2; i++) {
                                history.setText(history.getText().substring(0, history.getText().length() - 1));
                            }
                            System.out.println("test:\t" + operationChar);
                            history.append(String.valueOf(getOperationChar()) + "\n");
                        }
                    }
                    znakFlag = false;
                }
                pressedOrUnpressedDigit = false;
            }*/
        });
        add(subtraction);

        sqrt = new JButton("√");
        sqrt.setBounds(265, 375, 60, 50);
        sqrt.setFont(new Font("Arial", Font.PLAIN, 20));
        sqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pressedOrUnpressedDigit = true;
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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                pressedOrUnpressedDigit = true;

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
                setOperationChar('+');
                znakFlag = true;
                plusFlag = true;

                try {
                    if (pressedOrUnpressedDigit) {
                        // This condition to trim extra zero's, i.e. '10.00' --> '10'
                        equalsFlag = false;

                        if (!equalsPressFlag) {
                            setNumberOne(Double.parseDouble(display.getText()));
                            toTrimZero(getNumberOne());
                            history.append("\n" + String.valueOf(getOperationChar()) + "\n");
                            equalsPressFlag = true;

                        } else if (equalsPressFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            display.setText("0");
                            setTempResult(getNumberOne() + getNumberTwo());
                            System.out.println("Double press '+': " + getResult());

                            history.append(" ");    // For gap between operation sign & sign of digit (i.e. + -34.2)
                            toTrimZero(getNumberTwo());

                            history.append("\n = ");
                            toTrimZero(getTempResult());
                            history.append("\n");

                            setNumberOne(getTempResult());
                            history.append(String.valueOf(getOperationChar()) + "\n");
                        }
                    } else if (equalsFlag) {
                        // выполнение этого условия позволяет прибавлять к ответу что то еще, что набрано на цифровой клаве
                        history.append(getOperationChar() + "\n");
                        setNumberOne(Double.parseDouble(display.getText()));
                        display.setText("0");

                        if (znakFlag) {
                            // Это услови удаляет, введеную операцию и меняет её на '+'
                            if (!history.getText().endsWith("+")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                        equalsFlag = false;
                    } else {
                        if (znakFlag) {
                            if (!history.getText().endsWith("+")) {
                                for (int i = 0; i < 2; i++) {
                                    history.setText(history.getText().substring(0, history.getText().length() - 1));
                                }
                                System.out.println("test:\t" + operationChar);
                                history.append(String.valueOf(getOperationChar()) + "\n");
                            }
                        }
                        znakFlag = false;
                    }
                    pressedOrUnpressedDigit = false;

                } catch (Exception e) {
                    System.err.println("Exception, because wasn't pressed some digits.");
                }
            }
        });
        add(addition);

        equals = new JButton("=");
        equals.setBounds(265, 430, 60, 50);
        equals.setFont(new Font("Arial", Font.PLAIN, 40));
        equals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                equalsFlag = true;
                equalsPressFlag = true;

                switch (getOperationChar()) {
                    case '+' :
                        if (plusFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            setResult(getNumberOne() + getNumberTwo());
// This line for DecimalFormat//                           setResult(format(getResult()));  // Don't delete
                            String resultS = String.valueOf(getResult());
                            toTrimZero(getNumberTwo());

                            history.append("\n = " + resultS + "\n");
                            display.setText(resultS);

                            setTempPlus(getResult());
                            plusFlag = false;
                        } else {
                            history.append(String.valueOf(getOperationChar()) + "\n" + getNumberTwo().toString() + "\n");
                            setResult(tempPlus + getNumberTwo());
                            display.setText(getResult().toString());
                            history.append(" = " + getResult().toString() + "\n");
                            tempPlus = getResult();
                        }
                        break;

                    case '-' :
                        if (minusFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            setResult(getNumberOne() - getNumberTwo());
                            String resultS = String.valueOf(getResult());
                            toTrimZero(getNumberTwo());

                            history.append("\n = " + resultS + "\n");
                            display.setText(resultS);

                            setTempPlus(getResult());
                            minusFlag = false;
                        } else {
                            history.append(String.valueOf(getOperationChar()) + "\n" + getNumberTwo().toString() + "\n");
                            setResult(tempPlus - getNumberTwo());
                            display.setText(getResult().toString());
                            history.append(" = " + getResult().toString() + "\n");
                            tempPlus = getResult();
                        }
                        /*if (plusFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            setResult(getNumberOne() - getNumberTwo());
                            String resultS = String.valueOf(getResult());

                            history.append(getNumberTwo().toString() + "\n");
                            display.setText("0");

                            history.append(" = " + resultS + "\n");
                            display.setText(resultS);

                            setTempPlus(getResult());
                            plusFlag = false;

                        } else {
                            history.append(String.valueOf(getOperationChar()) + "\n" + getNumberTwo().toString() + "\n");
                            setResult(tempPlus + getNumberTwo());
                            display.setText(getResult().toString());
                            history.append(" = " + getResult().toString() + "\n");
                            tempPlus = getResult();
                        }*/
                        break;
                    case '*' :
                        /*if (supplyFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            setResult(getNumberOne() * getNumberTwo());
                            String resultS = String.valueOf(getResult());
                            toTrimZero(getNumberTwo());

                            history.append("\n = " + resultS + "\n");
                            display.setText(resultS);

                            setTempPlus(getResult());
                            supplyFlag = false;
                        } else {
                            history.append(String.valueOf(getOperationChar()) + "\n" + getNumberTwo().toString() + "\n");
                            setResult(tempPlus * getNumberTwo());
                            display.setText(getResult().toString());
                            history.append(" = " + getResult().toString() + "\n");
                            tempPlus = getResult();
                        }*/
                        break;
                    case '/' :
                        /*if (plusFlag) {
                            setNumberTwo(Double.parseDouble(display.getText()));
                            setResult(getNumberOne() / getNumberTwo());
                            String resultS = String.valueOf(getResult());
                            toTrimZero(getNumberTwo());

                            history.append("\n = " + resultS + "\n");
                            display.setText(resultS);

                            setTempPlus(getResult());
                            plusFlag = false;
                        } else {
                            history.append(String.valueOf(getOperationChar()) + "\n" + getNumberTwo().toString() + "\n");
                            setResult(tempPlus / getNumberTwo());
                            display.setText(getResult().toString());
                            history.append(" = " + getResult().toString() + "\n");
                            tempPlus = getResult();
                        }*/
                        break;
                    case '%' :
                        break;
                }

                if (display.getText().endsWith(".0") || history.getText().endsWith(".0")) {
                    display.setText(display.getText().replace(".0", ""));
                    history.setText(history.getText().replace(".0", ""));
                }
                pressedOrUnpressedDigit =  false;
                equalsPressFlag = false;
            }
        });
        add(equals);
    }

    /* // Don't delete

    ERROR 999 + 1, пишет ошибку вывода '1 000'
    private double format (Double resultDecimalFormat) {
        // resultDecimalFormat - result in decimal format
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        setResult(Double.parseDouble(decimalFormat.format(resultDecimalFormat)));

        return getResult();
    }*/

    private Double toTrimZero(Double meaning) {

        if (meaning.toString().endsWith(".0")) {
            history.append(meaning.toString().replace(".0", ""));
            display.setText("0");
        } else {
            history.append(meaning.toString());
            display.setText("0");
        }
        return meaning;
    }

    private void displays() {
        history = new JTextArea();
        jScrollPaneHistory = new JScrollPane(history);
        jScrollPaneHistory.setBounds(5, 5, appWidth - 10, 150);
        history.setFont(new Font("Arial", Font.PLAIN, 15));
        history.setEditable(false);
        add(jScrollPaneHistory);

        display = new JTextArea("0");
        display.setBounds(5, 160, appWidth - 10,40);
        display.setFont(new Font("Arial",Font.PLAIN, 40));
        display.setBackground(Color.lightGray);
        display.setEditable(false);
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
