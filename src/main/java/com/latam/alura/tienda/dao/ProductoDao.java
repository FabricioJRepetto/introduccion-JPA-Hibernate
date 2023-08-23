package com.latam.alura.tienda.dao;

import com.latam.alura.tienda.modelo.Producto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public List<Producto> consultarPorParametro(String nombre, BigDecimal precio, LocalDate fecha) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1 ");

        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append("AND p.nombre=:nombre ");
        }
        if (precio != null && !precio.equals(new BigDecimal(0))) {
            jpql.append("AND p.precio=:precio ");
        }
        if (fecha != null) {
            jpql.append("AND p.fechaDeRegistro=:fecha");
        }

        TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);

        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", nombre);
        }
        if (precio != null && !precio.equals(new BigDecimal(0))) {
            query.setParameter("precio", precio);
        }
        if (fecha != null) {
            query.setParameter("fecha", fecha);
        }

        return query.getResultList();
    }

    public List<Producto> consultarConCriteria(String nombre, BigDecimal precio, LocalDate fecha) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Producto> query = builder.createQuery(Producto.class);
        Root<Producto> from = query.from(Producto.class);

        Predicate filtro = builder.and();

        if (nombre != null && !nombre.trim().isEmpty()) {
            filtro = builder.and(filtro, builder.equal(from.get("nombre"), nombre));
        }
        if (precio != null && !precio.equals(new BigDecimal(0))) {
            filtro = builder.and(filtro, builder.equal(from.get("precio"), precio));
        }
        if (fecha != null) {
            filtro = builder.and(filtro, builder.equal(from.get("fechaDeRegistro"), fecha));
        }

        query = query.where(filtro);
        return em.createQuery(query).getResultList();
    }

}
