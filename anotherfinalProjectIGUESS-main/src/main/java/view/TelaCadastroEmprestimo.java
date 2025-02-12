package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.EmprestimoModel;
import model.LivroModel;
import model.UsuarioModel;
import repository.EmprestimoRepository;
import repository.LivroRepository;
import repository.UsuarioRepository;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaCadastroEmprestimo extends JFrame {
    private JPanel painelEmprestimo;
    private JTextField idUsuarioField;
    private JLabel GERENCIAMENTODEEMPRESTIMOSDELIVROS;
    private JLabel IDDOUSUARIO;
    private JLabel IDDOLIVRO;
    private JTextField idLivroField;
    private JLabel DATADOEMPRESTIMO;
    private JTextField dataEmprestimoField;
    private JTextField dataDevolucaoField;
    private JButton voltarButton;
    private JButton emprestarButton;
    private JButton devolverButton;
    private JTable tableUsuarios;
    private JTable tableLivros;
    private JScrollPane scrollPaneUsuario;
    private JScrollPane scrollPaneLivro;
    private JButton buttonListarIds;
    public Styles styles = new Styles();

    public TelaCadastroEmprestimo() {
        this.setTitle("Gerenciamento de Empréstimos");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(painelEmprestimo);
        this.setLocationRelativeTo(null);

        styles.StyleButton(emprestarButton);
        styles.StyleButton(devolverButton);
        styles.StyleButton(voltarButton);
        styles.StyleButton(buttonListarIds);
        styles.StyleTextField(idLivroField);
        styles.StyleTextField(dataEmprestimoField);
        styles.StyleTextField(dataDevolucaoField);
        styles.StyleTextField(idUsuarioField);

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UsuarioRepository usuarioRepository = new UsuarioRepository();
                    LivroRepository livroRepository = new LivroRepository();
                    EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

                    Long idUsuario = Long.parseLong(idUsuarioField.getText());
                    Long idLivro = Long.parseLong(idLivroField.getText());

                    UsuarioModel usuario = usuarioRepository.buscarPorId(idUsuario);
                    LivroModel livro = livroRepository.buscarPorId(idLivro);

                    if (usuario == null || livro == null) {
                        JOptionPane.showMessageDialog(null, "Usuário ou Livro não encontrados!");
                        return;
                    }

                    if (livro.getQuantidadeExemplares() <= 0) {
                        JOptionPane.showMessageDialog(null, "Livro indisponível!");
                        return;
                    }

                    EmprestimoModel emprestimo = new EmprestimoModel();
                    emprestimo.setUsuario(usuario);
                    emprestimo.setLivro(livro);
                    emprestimo.setDataEmprestimo(new Date());

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        sdf.setLenient(false);
                        Date dataDevolucao = sdf.parse(dataDevolucaoField.getText());
                        emprestimo.setDataDevolucaoPrevista(dataDevolucao);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Formato de data inválido! Use dd/MM/yyyy");
                        return;
                    }

                    emprestimoRepository.salvar(emprestimo);
                    livro.setQuantidadeExemplares(livro.getQuantidadeExemplares() - 1);
                    livroRepository.atualizar(livro);

                    JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao realizar empréstimo: " + ex.getMessage());
                }
            }
        });


        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Principal();
            }
        });


        buttonListarIds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarIds();
            }
        });
    }


    private void mostrarIds() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        LivroRepository livroRepository = new LivroRepository();

        List<UsuarioModel> usuarios = usuarioRepository.buscarTodos();
        List<LivroModel> livros = livroRepository.buscarTodos();


        DefaultTableModel modelUsuarios = new DefaultTableModel(new Object[]{"ID Usuário", "Nome"}, 0);
        for (UsuarioModel usuario : usuarios) {
            modelUsuarios.addRow(new Object[]{usuario.getIdUsuario(), usuario.getNome()});
        }
        tableUsuarios.setModel(modelUsuarios);


        DefaultTableModel modelLivros = new DefaultTableModel(new Object[]{"ID Livro", "Título"}, 0);
        for (LivroModel livro : livros) {
            modelLivros.addRow(new Object[]{livro.getIdLivro(), livro.getTitulo()});
        }
        tableLivros.setModel(modelLivros);
    }
}
