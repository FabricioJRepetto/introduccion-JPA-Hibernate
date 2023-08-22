package com.latam.alura.tienda.dao;

import com.latam.alura.tienda.modelo.Cliente;
import com.latam.alura.tienda.modelo.Pedido;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Cliente cliente) {
        this.em.persist(cliente);
    }

    public void actualizar(Cliente cliente) {
        this.em.merge(cliente);
    }

    public void remover(Cliente cliente) {
        cliente = this.em.merge(cliente);
        this.em.remove(cliente);
    }

    public Cliente consultaPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> consultaTodo() {
        String jpql = "SELECT P FROM Cliente AS P";
        return em.createQuery(jpql, Cliente.class).getResultList();
    }

//    public List<Pedido> consultaPorNombre(String nombre) {
//        String jpql = "SELECT P FROM Pedido AS P WHERE P.nombre=:nombre";
//        return em.createQuery(jpql, Pedido.class).setParameter("nombre", nombre).getResultList();
//    }

//    public List<Pedido> consultaPorNombreDeCategoria(String categoria) {
//        String jpql = "SELECT P FROM Pedido AS P WHERE P.categoria.nombre=:categoria";
//        return em.createQuery(jpql, Pedido.class).setParameter("categoria", categoria).getResultList();
//    }

//    public BigDecimal consultarPrecioPorNombreDeProducto(String pedido) {
//        String jpql = "SELECT P.precio FROM Pedido AS P WHERE P.nombre=:producto";
//        return em.createQuery(jpql, BigDecimal.class).setParameter("producto", pedido).getSingleResult();
//    }

}
