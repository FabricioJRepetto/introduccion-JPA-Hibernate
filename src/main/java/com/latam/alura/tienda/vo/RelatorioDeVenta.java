package com.latam.alura.tienda.vo;

import java.time.LocalDate;

public class RelatorioDeVenta {
    private String nombreDelProducto;
    private Long cantidadDeProducto;
    private LocalDate fechaUltimaVenta;

    public RelatorioDeVenta(String nombre, Long cantidad, LocalDate fecha) {
        this.nombreDelProducto = nombre;
        this.cantidadDeProducto = cantidad;
        this.fechaUltimaVenta = fecha;
    }

    public String getNombreDelProducto() {
        return nombreDelProducto;
    }

    public void setNombreDelProducto(String nombreDelProducto) {
        this.nombreDelProducto = nombreDelProducto;
    }

    public Long getCantidadDeProducto() {
        return cantidadDeProducto;
    }

    public void setCantidadDeProducto(Long cantidadDeProducto) {
        this.cantidadDeProducto = cantidadDeProducto;
    }

    public LocalDate getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(LocalDate fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    @Override
    public String toString() {
        return "Producto: '" + nombreDelProducto + '\'' +
                ", Cantidad: " + cantidadDeProducto +
                ", Ultima venta: " + fechaUltimaVenta;
    }
}
