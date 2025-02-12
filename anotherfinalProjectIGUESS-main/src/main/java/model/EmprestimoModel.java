package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="emprestimo")
public class EmprestimoModel {
    @Id
    @GeneratedValue
    private long idEmprestimo;
    @ManyToOne
    private UsuarioModel usuario;
    @ManyToOne
    private LivroModel livro;
    @Temporal(TemporalType.DATE)
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucaoPrevista;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;

    public long getIdEmprestimo() {
        return idEmprestimo;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setUsuario(UsuarioModel usuario) {

    }

    public void setLivro(LivroModel livro) {
    }
}
