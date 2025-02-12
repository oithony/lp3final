package view;

import model.LivroModel;
import repository.LivroRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaCadastroLivro extends JFrame {
    private JPanel painelPrincipal;
    private JLabel GERENCIAMENTODELIVROS;
    private JLabel TEMA;
    private JLabel AUTOR;
    private JLabel ISBN;
    private JLabel TITULODOLIVRO;
    private JLabel DATADEPUBLICAÇÃO;
    private JLabel QUANTIDADE;
    private JTextField tituloField;
    private JTextField temaField;
    private JTextField autorField;
    private JTextField isbnField;
    private JTextField dataPublicacaoField;
    private JTextField quantidadeField;
    private JButton salvarButton;
    private JButton buscarButton;
    private JButton voltarButton;
    public Styles styles = new Styles();


    public TelaCadastroLivro(){
        this.setTitle("Gerenciamento de Livros");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(painelPrincipal);
        this.setLocationRelativeTo(null);
        styles.StyleButton(salvarButton);
        styles.StyleButton(buscarButton);
        styles.StyleButton(voltarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivroModel livro = new LivroModel();
                livro.setTitulo(tituloField.getText());
                livro.setTema(temaField.getText());
                livro.setAutor(autorField.getText());
                livro.setIsbn(Integer.parseInt(isbnField.getText()));
                livro.setQuantidadeExemplares(Integer.parseInt(quantidadeField.getText()));


                try {
                    String dataString = dataPublicacaoField.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataPublicacao = sdf.parse(dataString);
                    livro.setDataPublicacao(dataPublicacao);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida! Use o formato dd/MM/yyyy");
                    return;
                }


                LivroRepository.getInstance().salvar(livro);
                JOptionPane.showMessageDialog(null, "Livro salvo com sucesso!");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaBuscaLivro();
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
