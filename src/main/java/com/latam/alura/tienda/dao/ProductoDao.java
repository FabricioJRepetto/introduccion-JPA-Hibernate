package com.latam.alura.tienda.dao;

import com.latam.alura.tienda.modelo.Producto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProductoDao {

    private EntityManager em;

    public ProductoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Producto producto) {
        this.em.persist(producto);
    }

    public void actualizar(Producto producto) {
        this.em.merge(producto);
    }

    public void remover(Producto producto) {
        producto = this.em.merge(producto);
        this.em.remove(producto);
    }

    public Producto consultaPorId(Long id) {
        return em.find(Producto.class, id);
    }

    public List<Producto> consultaTodo() {
        String jpql = "SELECT P FROM Producto AS P";
        return em.createQuery(jpql, Producto.class).getResultList();
    }

    public List<Producto> consultaPorNombre(String nombre) {
        String jpql = "SELECT P FROM Producto AS P WHERE P.nombre=:nombre";
        return em.createQuery(jpql, Producto.class).setParameter("nombre", nombre).getResultList();
    }

    public List<Producto> consultaPorNombreDeCategoria(String categoria) {
        String jpql = "SELECT P FROM Producto AS P WHERE P.categoria.nombre=:categoria";
        return em.createQuery(jpql, Producto.class).setParameter("categoria", categoria).getResultList();
    }

    public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
        // Utilzia NamedQuery, las cuales se definen en la misma instancia.
        // Util a la hora de centralizar todas las consultas en un solo lugar, en vez de teneras esparcidas por el proyecto.
        return em.createNamedQuery("Producto.precioPorNombre", BigDecimal.class).setParameter("nombre", nombre).getSingleResult();
    }

}
