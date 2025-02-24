import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class TaxCalculator extends JFrame {
    private JTextField incomeField;
    private JRadioButton oldRegimeRadio, newRegimeRadio;
    private JTextArea resultArea;

    public TaxCalculator() {
        setTitle("Income Tax Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Annual Income:"));
        incomeField = new JTextField();
        inputPanel.add(incomeField);
        
        // Radio Buttons Panel
        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        oldRegimeRadio = new JRadioButton("Old Regime", true);
        newRegimeRadio = new JRadioButton("New Regime");
        ButtonGroup regimeGroup = new ButtonGroup();
        regimeGroup.add(oldRegimeRadio);
        regimeGroup.add(newRegimeRadio);
        radioPanel.add(oldRegimeRadio);
        radioPanel.add(newRegimeRadio);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton calculateButton = new JButton("Calculate Tax");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        // Result Area
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(resultArea), BorderLayout.EAST);

        // Event Listeners
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTax();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeField.setText("");
                resultArea.setText("");
                oldRegimeRadio.setSelected(true);
            }
        });
    }

   private void calculateTax() {
    try {
        double income = Double.parseDouble(incomeField.getText());
        double tax = 0;

        // South Africa uses a single progressive tax system (no old/new regimes)
        tax = calculateSouthAfricanTax(income);

        // Format currency for South Africa (ZAR)
        NumberFormat zarFormat = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));
        
        resultArea.setText(
            "Taxable Income: " + zarFormat.format(income) + "\n" +
            "Tax Payable: " + zarFormat.format(tax)
        );
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter valid ZAR amount!", 
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
// Updated with South Africa's 2023/2024 tax brackets
private double calculateSouthAfricanTax(double income) {
    double tax = 0;
    
    // Tax brackets (2023/2024 tax year)
    if (income > 1_817_000) {
        tax += (income - 1_817_000) * 0.45;
        income = 1_817_000;
    }
    if (income > 857_900) {
        tax += (income - 857_900) * 0.41;
        income = 857_900;
    }
    if (income > 673_000) {
        tax += (income - 673_000) * 0.39;
        income = 673_000;
    }
    if (income > 512_800) {
        tax += (income - 512_800) * 0.36;
        income = 512_800;
    }
    if (income > 370_500) {
        tax += (income - 370_500) * 0.31;
        income = 370_500;
    }
    if (income > 237_100) {
        tax += (income - 237_100) * 0.26;
        income = 237_100;
    }
    tax += income * 0.18;  // Lowest bracket
    
    // Subtract primary rebate (R17,235 for 2023/2024)
    tax = Math.max(tax - 17_235, 0);
    
    return tax;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaxCalculator().setVisible(true);
            }
        });
    }
}