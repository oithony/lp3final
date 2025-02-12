package view;

import model.LivroModel;
import repository.LivroRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaBuscaLivro extends JFrame{
    private JTextField textFieldBuscar;
    private JButton buscarButton;
    private JButton removerButton;
    private JButton voltarButton;
    private JScrollPane scrollPaneLivro;
    private JTable tableBuscaLivro;
    private JPanel painelPrincipal;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudHibernatePU");
    private EntityManager entityManager = emf.createEntityManager();
    private LivroRepository livroRepository = new LivroRepository();
    public Styles styles = new Styles();

    public TelaBuscaLivro(){
        this.setTitle("Tela de Busca de Livro: ");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(painelPrincipal);
        this.setLocationRelativeTo(null);
        styles.StyleTextField(textFieldBuscar);
        styles.StyleButton(buscarButton);
        styles.StyleButton(removerButton);
        styles.StyleButton(voltarButton);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroLivro().setVisible(true);
                setVisible(false);

            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });
        this.setVisible(true);
    }

    private void removerLivro() {


            String buscaTexto = textFieldBuscar.getText();
            if (buscaTexto != null && !buscaTexto.isEmpty()) {
                try {
                    long idLivro = Long.parseLong(buscaTexto);

                    entityManager.getTransaction().begin();

                    LivroModel livro = entityManager.find(LivroModel.class, idLivro);

                    if (livro != null) {

                        entityManager.remove(livro);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
                        atualizarTabela();
                    } else {
                        JOptionPane.showMessageDialog(this, "Livro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    entityManager.getTransaction().rollback();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Digite o ID do livro para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

    }

    public void atualizarTabela(){
        List<LivroModel> livros = buscar();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "ISBN", "Quantidade Exemplares", "Data de Publicação"}, 0);

        for (LivroModel livro : livros) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataPublicacaoStr = (livro.getDataPublicacao() != null) ? sdf.format(livro.getDataPublicacao()) : "Não disponível";
            model.addRow(new Object[]{
                    livro.getIdLivro(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.getQuantidadeExemplares(),
                    dataPublicacaoStr
            });
        }

        tableBuscaLivro.setModel(model);
    }

    public List<LivroModel> buscar() {
        String buscaTexto = textFieldBuscar.getText();
        if (buscaTexto == null || buscaTexto.isEmpty()) {
            return entityManager.createQuery("from LivroModel", LivroModel.class).getResultList();
        } else {
            try {
                // Buscando por título ou ISBN (tratando como número)
                if (buscaTexto.matches("[0-9]+")) { // Se o texto for um número, busca por ISBN
                    int isbn = Integer.parseInt(buscaTexto);
                    return entityManager.createQuery("from LivroModel l where l.isbn = :isbn", LivroModel.class)
                            .setParameter("isbn", isbn)
                            .getResultList();
                } else { // Caso contrário, busca por título
                    return entityManager.createQuery("from LivroModel l where l.titulo like :buscaTexto", LivroModel.class)
                            .setParameter("buscaTexto", "%" + buscaTexto + "%")
                            .getResultList();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na busca: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }
    }
}

