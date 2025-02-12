
package view;
import model.UsuarioModel;
import repository.UsuarioRepository;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroUsuario extends JFrame {
    private JPanel painelCadastro;
    private JLabel NomeCompleto;
    private JTextField nomeField;
    private JLabel Sexo;
    private JTextField sexoField;
    private JLabel Celular;
    private JTextField celularField;
    private JLabel Email;
    private JTextField emailField;
    private JButton salvarButton;
    private JButton buscarButton;
    private JButton voltarButton;
    private JPanel painelUsuario;
    public Styles styles = new Styles();

    public TelaCadastroUsuario() {
        this.setTitle("Tela de Cadastro");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(painelUsuario);
        this.setLocationRelativeTo(null);
        styles.StyleButton(salvarButton);
        styles.StyleButton(buscarButton);
        styles.StyleButton(voltarButton);
        styles.StyleTextField(nomeField);
        styles.StyleTextField(sexoField);
        styles.StyleTextField(celularField);
        styles.StyleTextField(emailField);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setNome(nomeField.getText());
                usuario.setSexo(sexoField.getText());
                usuario.setCelular(celularField.getText());
                usuario.setEmail(emailField.getText());

                UsuarioRepository.getInstance().salvar(usuario);
                JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaBuscaUsuario();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Principal();
            }
        });
    }
}
