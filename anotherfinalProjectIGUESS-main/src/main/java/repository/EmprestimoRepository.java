package repository;
import model.EmprestimoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
public class EmprestimoRepository {

    private static EmprestimoRepository instance;
    private EntityManager entityManager;

    public EmprestimoRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static EmprestimoRepository getInstance() {
        if (instance == null) {
            instance = new EmprestimoRepository();
        }
        return instance;
    }

    public void salvar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar empréstimo: " + e.getMessage());
        }
    }

    public EmprestimoModel buscarPorId(Long id) {
        return entityManager.find(EmprestimoModel.class, id);
    }

    public void atualizar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(emprestimo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }

    public void remover(Long id) {
        try {
            EmprestimoModel emprestimo = buscarPorId(id);
            if (emprestimo != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(emprestimo);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Erro ao remover empréstimo: " + e.getMessage());
        }
    }

    public List<EmprestimoModel> listarTodos() {
        return entityManager.createQuery("FROM EmprestimoModel", EmprestimoModel.class).getResultList();
    }
}
