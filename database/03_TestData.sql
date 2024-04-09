-- proveedores --
INSERT INTO tbl_proveedores (nombre, direccion, telefono, correo, contrasena, estado)
VALUES 
('Proveedor1', 'Calle 123, Ciudad A', '123456789', 'proveedor1@example.com', 'contrasena1', 'activo'),
('Proveedor2', 'Avenida XYZ, Ciudad B', '987654321', 'proveedor2@example.com', 'contrasena2', 'activo'),
('Proveedor3', 'Carrera 456, Ciudad C', '456123789', 'proveedor3@example.com', 'contrasena3', 'activo');

-- clientes --
INSERT INTO tbl_clientes (nombre, identificacion, telefono, correo, id_proveedor)
VALUES 
('Cliente1', 'ID001', '111222333', 'cliente1@example.com', 1),
('Cliente2', 'ID002', '444555666', 'cliente2@example.com', 2),
('Cliente3', 'ID003', '777888999', 'cliente3@example.com', 3);

-- productos --
INSERT INTO tbl_productos (nombre, descripcion, precio_unitario, id_proveedor)
VALUES 
('Producto1', 'Descripción del Producto 1', 10.50, 1),
('Producto2', 'Descripción del Producto 2', 20.75, 2),
('Producto3', 'Descripción del Producto 3', 15.00, 3);

-- facturas --
INSERT INTO tbl_facturas (fecha_emision, id_proveedor, id_cliente, subtotal, impuesto, total)
VALUES 
('2024-03-30 10:00:00', 1, 1, 0.00, 13.00, 0.00),
('2024-03-30 11:00:00', 2, 2, 0.00, 13.00, 0.00),
('2024-03-30 12:00:00', 3, 3, 0.00, 13.00, 0.00);

-- detalle_facturas --
INSERT INTO tbl_detalle_facturas (id_factura, id_producto, cantidad, precio_unitario, total)
VALUES 
(1, 1, 2, 0, 0),
(2, 2, 1, 0, 0),
(3, 3, 3, 0, 0);

-- admins --
INSERT INTO tbl_admins (nombre, contrasena)
VALUES 
('admin', 'admin'),
('root', 'root');
