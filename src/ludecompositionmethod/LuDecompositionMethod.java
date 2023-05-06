package ludecompositionmethod;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.border.LineBorder;

public class LuDecompositionMethod extends JFrame {

    JTextField[][] field = new JTextField[10][10];
    double[][] matrix = new double[10][10];
    double[][] l = new double[10][10];
    double[][] u = new double[10][10];
    double[][] a = new double[10][10];
    double[] x = new double[10];
    static int n;
    JButton compute;
    JTextField dp = new JTextField(7);

    ;
    void display() {

        JFrame frame = new JFrame("Solution for LU Dicomposition");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(Integer.parseInt(dp.getText()));
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 55, 15));
        JLabel l1 = new JLabel("L Dicomposition");
        JLabel l2 = new JLabel("U Dicomposition");
        l1.setFont(new Font("Serif", 3, 20));
        l2.setFont(new Font("Serif", 3, 20));

        JPanel p2 = new JPanel(new GridLayout(n, (n)));
        p2.setBorder(new LineBorder(Color.GRAY, 1));
        JPanel p3 = new JPanel(new GridLayout(n, (n)));
        p3.setBorder(new LineBorder(Color.GRAY, 1));
        p1.setBorder(new LineBorder(Color.GRAY, 1));
        p2.setBackground(Color.WHITE);
        p3.setBackground(Color.WHITE);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                p2.add(new JLabel("" + df.format(l[i][j])));
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                p3.add(new JLabel("" + df.format(u[i][j])));
            }
        }
        JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JPanel p4 = new JPanel(new GridLayout(1, 2));

        if (!("".equals(String.format(field[n - 1][n].getText())))) {
            pp1.add(new JLabel("the Solution is"));
            for (int j = 0; j < n; j++) {

                pp1.add(new JLabel(" X " + (j + 1) + "  = " + df.format(x[j]) + " , "));
            }
        }

        p4.add(p2);
        p4.add(p3);
        p1.add(l1);
        p1.add(l2);

        frame.add(p1, BorderLayout.NORTH);
        frame.add(p4, BorderLayout.CENTER);
        frame.add(pp1, BorderLayout.SOUTH);
    }

    LuDecompositionMethod() {
        super("LU Decomposition Calculater");

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JLabel l1 = new JLabel("Enter the matrix Rowwise");
        l1.setFont(new Font("Serif", 3, 20));
        p1.add(l1);

        JPanel p2 = new JPanel(new GridLayout(n, (n + 1)));
        p2.setBorder(new LineBorder(Color.GRAY, 1));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = new JTextField(10);
                field[i][j].setText("");
            }
        }
        for (int i = 0; i < n; i++) {
            p2.add(new JLabel("R" + (i + 1)));
            for (int j = 0; j < (n + 1); j++) {
                field[i][j] = new JTextField(10);
                p2.add(field[i][j]);
            }
        }
        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        p3.add(new JLabel("D.Places"));
        p3.add(dp);
        compute = new JButton("Compute");
        compute.setFont(new Font("Serif", 3, 20));
        p3.add(compute);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.SOUTH);

        compute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if ("".equals(String.format(field[n - 1][n].getText()))) {

                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < (n); j++) {

                            matrix[i][j] = Double.parseDouble(field[i][j].getText());

                        }
                    }
                } else {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < (n + 1); j++) {

                            matrix[i][j] = Double.parseDouble(field[i][j].getText());
                            a[i][j] = matrix[i][j];
                        }
                    }
                }
                lu();
                elimination();
                display();
            }
        });
    }

    void lu() {
        int i = 0, j = 0, k = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (j < i) {
                    l[j][i] = 0;
                } else {
                    l[j][i] = matrix[j][i];
                    for (k = 0; k < i; k++) {
                        l[j][i] = l[j][i] - l[j][k] * u[k][i];
                    }
                }
            }
            for (j = 0; j < n; j++) {
                if (j < i) {
                    u[i][j] = 0;
                } else if (j == i) {
                    u[i][j] = 1;
                } else {
                    u[i][j] = matrix[i][j] / l[i][i];
                    for (k = 0; k < i; k++) {
                        u[i][j] = u[i][j] - ((l[i][k] * u[k][j]) / l[i][i]);
                    }
                }
            }
        }
    }

    void elimination() {
        int i, j, k;
        for (i = 0; i < n; i++) //Pivotisation
        {
            for (k = i + 1; k < n; k++) {
                if (Math.abs(a[i][i]) < Math.abs(a[k][i])) {
                    for (j = 0; j <= n; j++) {
                        double temp = a[i][j];
                        a[i][j] = a[k][j];
                        a[k][j] = temp;
                    }
                }
            }
        }

        for (i = 0; i < n - 1; i++) //loop to perform the gauss elimination
        {
            for (k = i + 1; k < n; k++) {
                double t = a[k][i] / a[i][i];
                for (j = 0; j <= n; j++) {
                    a[k][j] = a[k][j] - t * a[i][j];    //make the elements below the pivot elements equal to zero or elimnate the variables
                }
            }
        }

        for (i = n - 1; i >= 0; i--) //back-substitution
        {                        //x is an array whose values correspond to the values of x,y,z..
            x[i] = a[i][n];                //make the variable to be calculated equal to the rhs of the last equation
            for (j = i + 1; j < n; j++) {
                if (j != i) //then subtract all the lhs values except the coefficient of the variable whose value                                   is being calculated
                {
                    x[i] = x[i] - a[i][j] * x[j];
                }
            }
            x[i] = x[i] / a[i][i];            //now finally divide the rhs by the coefficient of the variable to be calculated
        }

    }

    public static void main(String[] args) {

        String sdimention = JOptionPane.showInputDialog("Enter the number of equation");
        n = Integer.parseInt(sdimention);

        JFrame frame = new LuDecompositionMethod();

        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
