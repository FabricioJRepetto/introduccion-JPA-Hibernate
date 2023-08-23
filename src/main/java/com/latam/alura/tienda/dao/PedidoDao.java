package com.latam.alura.tienda.dao;

import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public void actualizar(Pedido pedido) {
        this.em.merge(pedido);
    }

    public void remover(Pedido pedido) {
        pedido = this.em.merge(pedido);
        this.em.remove(pedido);
    }

    public Pedido consultaPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> consultaTodo() {
        String jpql = "SELECT P FROM Pedido AS P";
        return em.createQuery(jpql, Pedido.class).getResultList();
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(P.valorTotal) FROM Pedido P";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    // Estrategia #1: Object[] - poco específico
    public List<Object[]> relatorioDeVentas() {
        String jpql = "SELECT producto.nombre, " +
                "SUM(item.cantidad), " +
                "MAX(pedido.fecha) " +
                "FROM Pedido pedido " +
                "JOIN pedido.items item " +
                "JOIN item.producto producto " +
                "GROUP BY producto.nombre " +
                "ORDER BY item.cantidad DESC";
        return em.createQuery(jpql, Object[].class).getResultList();
    }

    // Estrategia #2: VO - Tipado 👍👍
    public List<RelatorioDeVenta> relatorioDeVentasVO() {
        String jpql = "SELECT new com.latam.alura.tienda.vo.RelatorioDeVenta(producto.nombre, " +
                "SUM(item.cantidad), " +
                "MAX(pedido.fecha)) " +
                "FROM Pedido pedido " +
                "JOIN pedido.items item " +
                "JOIN item.producto producto " +
                "GROUP BY producto.nombre " +
                "ORDER BY item.cantidad DESC";

        return em.createQuery(jpql, RelatorioDeVenta.class).getResultList();
    }

    // Consultas planeadas para LAZY fetch
    public Pedido consultaPedidoConCliente(Long id) {
        String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id=:id";
        return em.createQuery(jpql, Pedido.class).setParameter("id", id).getSingleResult();
    }

}
