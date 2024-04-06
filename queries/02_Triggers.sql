-- Trigger para asignar el precio del producto correspondiente al id_producto que se está insertando en la tabla tbl_detalle_facturas a la columna precio_unitario en la nueva fila a insertar.
DELIMITER //

CREATE TRIGGER set_precio_unitario
BEFORE INSERT ON tbl_detalle_facturas
FOR EACH ROW
BEGIN
    DECLARE producto_precio DECIMAL(10,2);

    -- Obtener el precio del producto correspondiente al id_producto que se está insertando
    SELECT precio_unitario INTO producto_precio FROM tbl_productos WHERE id_producto = NEW.id_producto;

    -- Asignar el precio del producto a la columna precio_unitario en la nueva fila a insertar
    SET NEW.precio_unitario = producto_precio;
END;
//

DELIMITER ;

-- Trigger para calcular el total de la fila a insertar en la tabla tbl_detalle_facturas.
DELIMITER //

CREATE TRIGGER set_total
BEFORE INSERT ON tbl_detalle_facturas
FOR EACH ROW
BEGIN
    -- Calcular el total de la fila a insertar
    SET NEW.total = NEW.cantidad * NEW.precio_unitario;
END;
//

DELIMITER ;

-- Trigger para actualizar el subtotal y total de la factura correspondiente al id_factura que se está insertando en la tabla tbl_detalle_facturas
DELIMITER //

CREATE TRIGGER update_factura_totals
AFTER INSERT ON tbl_detalle_facturas
FOR EACH ROW
BEGIN
    -- Actualizar el subtotal de la factura
    UPDATE tbl_facturas
    SET subtotal = (
        SELECT SUM(total)
        FROM tbl_detalle_facturas
        WHERE id_factura = NEW.id_factura
    )
    WHERE id_factura = NEW.id_factura;

    -- Actualizar el total de la factura (subtotal + impuesto)
    UPDATE tbl_facturas
    SET total = subtotal + (subtotal * impuesto / 100)
    WHERE id_factura = NEW.id_factura;
END;
//

DELIMITER ;

-- Trigger para eliminar los detalles de factura asociados a la factura que se está eliminando en la tabla tbl_facturas
DELIMITER //

CREATE TRIGGER delete_factura_details
BEFORE DELETE ON tbl_facturas
FOR EACH ROW
BEGIN
    -- Eliminar los detalles de factura asociados a la factura que se está eliminando
    DELETE FROM tbl_detalle_facturas WHERE id_factura = OLD.id_factura;
END;
//

DELIMITER ;
