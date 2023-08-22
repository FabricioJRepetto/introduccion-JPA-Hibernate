package com.latam.alura.tienda.prueba;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utiles.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class RegistroDeProducto {
    public static void main(String[] args) {
        Categoria celulares = new Categoria("CELULARES");
        Producto celular = new Producto("Motorola",
                "Telefono usado",
                new BigDecimal(1000),
                celulares);

        EntityManager em = JPAUtils.getEntityManager();

        ProductoDao productoDao = new ProductoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.guardar(celulares);
        productoDao.guardar(celular);

        /* luego de un "persist"
            la entidad pasa a estar "managed"
            lo que permite realizarle actualizaciones */
        celular.setNombre("Xiaomi");

//        em.getTransaction().commit(); // actualizacion definitiva
        em.flush(); // actualizacion con posibilidad de rollback
//        em.close(); // solo cierra el EM, para realizar cambios en instancias hay que volver a instanciarlo
        em.clear(); // envia las instancias a "detach" para ahorrar memoria

        // Trae una instancia "detached" devuelta al estado "managed"
        celular = em.merge(celular);
        /* luego de un "close" o "clear"
            la entidad pasa a estar "detached"
            y los cambios no se tienen en cuenta */
        celular.setNombre("Motorola");

        em.flush();
        em.clear();

        celular = em.merge(celular);
        em.remove(celular);

        em.flush();
//        em.getTransaction().rollback(); // Rollback
    }
}
