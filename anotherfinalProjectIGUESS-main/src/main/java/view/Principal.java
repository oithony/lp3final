package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    private JButton livrosButton;
    private JButton usuariosButton;
    private JButton emprestimosButton;
    private JPanel painelPrincipal;
    private JButton sairButton;

    public Styles styles = new Styles();

    public Principal(){
        this.setTitle("Sistema Bibliotecário - IFMS");
        this.setSize(640,480);
        this.setContentPane(painelPrincipal);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        styles.StyleButton(livrosButton);
        styles.StyleButton(usuariosButton);
        styles.StyleButton(emprestimosButton);
        styles.StyleButton(sairButton);
        this.setVisible(true);
        livrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroLivro().setVisible(true);
                dispose();
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadastroUsuario telaCadastroUsuario = new TelaCadastroUsuario();
                telaCadastroUsuario.setVisible(true);
                dispose();
            }
        });

        emprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroEmprestimo().setVisible(true);
                dispose();
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
