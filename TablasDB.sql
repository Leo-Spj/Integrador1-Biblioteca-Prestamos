DROP DATABASE IF EXISTS `biblioteca-prestamos`;
CREATE DATABASE `biblioteca-prestamos`;

USE `biblioteca-prestamos`;

CREATE TABLE Rol (
    rol_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni INT UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE,
    contraseña VARCHAR(255),
    rol_id INT,
    estado BOOLEAN NOT NULL DEFAULT TRUE,

    FOREIGN KEY (rol_id) REFERENCES Rol(rol_id)
);

CREATE TABLE Autor (
    autor_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Libro (
    libro_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    autor_id INT NOT NULL,
    link_imagen VARCHAR(255),
    descripcion TEXT,
    stock INT NOT NULL,

    FOREIGN KEY (autor_id) REFERENCES Autor(autor_id)
);

CREATE TABLE Prestamo (
    prestamo_id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    libro_id INT,
    fecha_prestamo DATE NOT NULL,
    fecha_limite DATE NOT NULL,
    fecha_devolucion DATE,
    devuelto BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (usuario_id) REFERENCES Usuario(usuario_id),
    FOREIGN KEY (libro_id) REFERENCES Libro(libro_id)
);

-- Crear índices
CREATE INDEX idx_usuario_dni ON Usuario(dni);
CREATE INDEX idx_libro_titulo ON Libro(titulo);
CREATE INDEX idx_prestamo_usuario_id ON Prestamo(usuario_id);
CREATE INDEX idx_prestamo_libro_id ON Prestamo(libro_id);

-- Insertar registros en Rol
INSERT INTO Rol (nombre) VALUES
('Bibliotecario'),
('Usuario');

-- Insertar registros en Usuario
INSERT INTO Usuario (nombres, apellidos, dni, correo, contraseña, rol_id) VALUES
('Bibliotecario', 'Bibliotecario', 12345678, 'biblioteca@utp.edu.pe', 'biblioteca123', 1);

INSERT INTO Usuario (nombres, apellidos, dni, rol_id) VALUES
('Usuario2', 'Usuario', 87654321, 2),
('Usuario3', 'Usuario2', 87654322, 2),
('Usuario4', 'Usuario3', 87654323, 2);

-- Insertar registros en Autor
INSERT INTO Autor (nombre) VALUES
('Gabriel García Márquez'),
('Isabel Allende'),
('Jorge Luis Borges'),
('Juan Rulfo'),
('Julio Cortázar'),
('Laura Esquivel'),
('Mario Vargas Llosa'),
('Octavio Paz'),
('Pablo Neruda');


DELIMITER //
CREATE TRIGGER tr_set_default_image
BEFORE INSERT ON Libro
FOR EACH ROW
BEGIN
IF NEW.link_imagen IS NULL THEN
SET NEW.link_imagen = CONCAT('https://placehold.co/100x150/orange/white?text=', REPLACE(NEW.titulo, ' ', '%5Cn'));
END IF;
END;
//
DELIMITER ;


-- Insertar registros en Libro
INSERT INTO Libro (isbn, titulo, autor_id,link_imagen ,descripcion, stock) VALUES
('3888580894', 'Cien años de soledad', 1, 'https://i.postimg.cc/0289XLbZ/1-Cien-a-os-de-soledad.jpg', 'Novela icónica que sigue la historia de la familia Buendía en el pueblo ficticio de Macondo.', 2),
('5215707454', 'El amor en los tiempos del cólera', 1, 'https://i.postimg.cc/vZTbbf7V/2-el-amor-en-tiempos-de-c-lera.jpg', 'Historia de amor entre Fermina Daza y Florentino Ariza que abarca varias décadas.', 3),
('2997016528', 'Crónica de una muerte anunciada', 1, 'https://i.postimg.cc/5NnbhnzJ/3-Cr-nica-de-una-muerte-anunciada.jpg', 'Historia sobre la muerte de Santiago Nasar, narrada como una crónica periodística.', 3),
('1948210141', 'El coronel no tiene quien le escriba', 1, 'https://i.postimg.cc/wjrHvMFb/4-El-coronel-no-tiene-quien-le-escriba.jpg', 'Relato sobre un coronel retirado que espera una pensión que nunca llega.', 3),
('5396283330', 'Relato de un náufrago', 1, 'https://i.postimg.cc/XJSbfPRf/5-Relato-de-un-n-ufrago.jpg', 'Historia basada en hechos reales sobre un marinero que sobrevive diez días a la deriva en el Caribe.', 3),
('4282237437', 'El otoño del patriarca', 1, 'https://i.postimg.cc/6QGKGKm6/6-El-oto-o-del-patriarca.jpg', 'Novela que describe la decadencia de un dictador caribeño.', 3),
('5219989508', 'Memoria de mis putas tristes', 1, 'https://i.postimg.cc/pTftqZvg/7-Memoria-de-mis-putas-tristes.jpg', 'Historia de un hombre de 90 años que decide regalarse una noche de amor con una joven virgen.', 1),
('4123306029', 'Noticia de un secuestro', 1, 'https://i.postimg.cc/GtbnNyh9/8-Noticia-de-un-secuestro.jpg', 'Crónica periodística sobre una serie de secuestros en Colombia llevados a cabo por el cartel de Medellín.', 1),
('8434688259', 'Doce cuentos peregrinos', 1, 'https://i.postimg.cc/hvMRqtfs/9-Doce-cuentos-peregrinos.jpg', 'Conjunto de relatos que tienen en común a personajes latinoamericanos viviendo en Europa.', 3),
('7001079571', 'La hojarasca', 1, 'https://i.postimg.cc/c44NBwbb/10-La-hojarasca.jpg', 'Primera novela del autor, que introduce el pueblo de Macondo y sus habitantes.', 2),
('8843607213', 'El general en su laberinto', 1, 'https://i.postimg.cc/L4VpPNNP/11-El-general-en-su-laberinto.jpg', 'Novela histórica que narra los últimos días de Simón Bolívar.', 2),
('8798519701', 'Vivir para contarla', 1, 'https://i.postimg.cc/ncRfRM8c/12-Vivir-para-contarla.jpg', 'Autobiografía del autor, que abarca su infancia y juventud.', 1),
('7134435711', 'La casa de los espíritus', 2, 'https://i.postimg.cc/4xYkrZZv/13-La-casa-de-los-esp-ritus.jpg', 'Saga familiar que mezcla realismo mágico con la historia política de Chile.', 2),
('8455392077', 'Eva Luna', 2, 'https://i.postimg.cc/vZCFyjHJ/14-Eva-Luna.jpg', 'Novela que sigue la vida de Eva Luna, una joven huérfana con un talento innato para contar historias.', 1),
('7185986487', 'De amor y de sombra', 2, 'https://i.postimg.cc/YSgcC3vh/15-De-amor-y-de-sombra.jpg', 'Historia de amor ambientada en un país latinoamericano bajo una dictadura militar.', 1),
('8767162147', 'Paula', 2, 'https://i.postimg.cc/xCSwJzmg/16-Paula.jpg', 'Libro de memorias escrito tras la enfermedad y muerte de la hija de la autora.', 3),
('2647566867', 'Hija de la fortuna', 2, 'https://i.postimg.cc/mDN0QgN4/17-Hija-de-la-fortuna.jpg', 'Historia de una joven chilena que viaja a California durante la fiebre del oro en busca de su amor perdido.', 3),
('6325164774', 'El amante japonés', 2, 'https://i.postimg.cc/NFsZbvpH/18-El-amante-japon-s.jpg', 'Historia de amor entre una joven polaca y un jardinero japonés en el San Francisco de la Segunda Guerra Mundial.', 2),
('6084252216', 'La suma de los días', 2, 'https://i.postimg.cc/kGTzpYph/19-La-suma-de-los-d-as.jpg', 'Continuación de las memorias de Allende, centrada en su vida familiar en California.', 2),
('4988450015', 'Inés del alma mía', 2, 'https://i.postimg.cc/3rftzGw6/20-Ines-del-alma-m-a.jpg', 'Novela histórica sobre Inés Suárez, una conquistadora española en el siglo XVI.', 2),
('2860112969', 'Ficciones', 3, 'https://i.postimg.cc/NfWpjwmk/21-Ficciones.jpg', 'Colección de cuentos que exploran temas como la metafísica, la literatura y la identidad.', 3),
('9472143119', 'El Aleph', 3, 'https://i.postimg.cc/L8Lvq0VK/22-El-Aleph.jpg', 'Conjunto de relatos que incluyen elementos de la filosofía, la teología y la literatura.', 3),
('5305404287', 'El libro de arena', 3, 'https://i.postimg.cc/yYJLKNgN/23-El-libro-de-arena.jpg', 'Colección de cuentos que abordan temas como el infinito y la identidad.', 2),
('4605999020', 'Historia universal de la infamia', 3, 'https://i.postimg.cc/fbsCYjH3/24-Historia-universal-de-la-infamia.jpg', 'Serie de biografías ficticias de criminales y aventureros.', 3),
('5430430360', 'El informe de Brodie', 3, 'https://i.postimg.cc/SsNGvQbk/25-El-informe-de-Brodie.jpg', 'Colección de cuentos que exploran lo fantástico y lo metafísico.', 1),
('4692161890', 'La memoria de Shakespeare', 3, 'https://i.postimg.cc/VNFRgrTK/26-La-memoria-de-Shakespeare.jpg', 'Colección de cuentos publicados póstumamente que abordan temas recurrentes del autor.', 3),
('3527112981', 'Seis problemas para don Isidro Parodi', 3, 'https://i.postimg.cc/SsTGF0m8/27-Seis-problemas-para-don-Isidro-Parodi.jpg', 'Colección de relatos policiales escritos en colaboración con Adolfo Bioy Casares bajo el seudónimo de H. Bustos Domecq.', 3),
('8862721725', 'Manual de zoología fantástica', 3, 'https://i.postimg.cc/kG2v7j5H/28-Manual-de-zoolog-a-fant-stica.jpg', 'Compendio de seres mitológicos y fantásticos de diversas culturas.', 2),
('3754841856', 'Pedro Páramo', 4, 'https://i.postimg.cc/Rh2Qhc6Z/29-Pedro-P-ramo.jpg', 'Novela sobre Juan Preciado, quien viaja al pueblo de Comala en busca de su padre, Pedro Páramo.', 2),
('5405199911', 'Rayuela', 5, 'https://i.postimg.cc/DfNcxKj3/30-Rayuela.jpg', 'Novela que se puede leer de varias maneras, explorando la vida de un grupo de intelectuales en París y Buenos Aires.', 1),
('7423166542', 'Bestiario', 5, 'https://i.postimg.cc/Hs79KPRc/31-Bestiario.jpg', 'Primera colección de cuentos publicada por Cortázar, donde lo fantástico irrumpe en lo cotidiano.', 2),
('1087162506', 'Final del juego', 5, 'https://i.postimg.cc/wj8QdQYW/32-Final-del-juego.jpg', 'Conjunto de relatos que incluyen elementos de lo fantástico y lo absurdo.', 2),
('7895373356', 'Los premios', 5, 'https://i.postimg.cc/xd9GXjCM/33-Los-premios.jpg', 'Novela que sigue a un grupo de personas que ganan un viaje en barco y sus experiencias a bordo.', 3),
('1552680208', 'Las armas secretas', 5, 'https://i.postimg.cc/sgY9fGV3/34-Las-armas-secretas.jpg', 'Colección de cuentos que incluye algunos de los relatos más conocidos del autor.', 1),
('5581758914', 'La vuelta al día en ochenta mundos', 5, 'https://i.postimg.cc/8CwRM8J3/35-La-vuelta-al-d-a-en-ochenta-mundos.jpg', 'Colección de ensayos, cuentos y reflexiones sobre diversos temas, con ilustraciones y fotografías.', 2),
('5640784008', '62 Modelo para armar', 5, 'https://i.postimg.cc/1ttpKWDz/36-62-Modelo-para-armar.jpg', 'Novela experimental que sigue a un grupo de personajes en diversas ciudades de Europa.', 2),
('7604486737', 'Un tal Lucas', 5, 'https://i.postimg.cc/PxR1VmMQ/37-Un-tal-Lucas.jpg', 'Conjunto de relatos, poemas y reflexiones protagonizados', 3),
('2259208617', 'Como agua para chocolate', 6, 'https://i.postimg.cc/Vv0nxdGQ/38-Como-agua-para-chocolate.jpg', 'Novela que combina recetas de cocina con una historia de amor en tiempos de la Revolución Mexicana.', 1),
('6143579964', 'La ley del amor', 6, 'https://i.postimg.cc/pT4q5N3v/39-La-ley-del-amor.jpg', 'Novela que mezcla elementos de ciencia ficción y romance en un futuro distópico.', 2),
('1974320067', 'Tan veloz como el deseo', 6, 'https://i.postimg.cc/9091G8j7/40-Tan-veloz-como-el-deseo.jpg', 'Novela sobre un hombre con el don de interpretar los deseos de las personas a través del oído.', 3),
('2113103506', 'Malinche', 6, 'https://i.postimg.cc/nc83HJvY/41-Malinche.jpg', 'Novela histórica sobre la vida de Malinche, la intérprete y amante de Hernán Cortés.', 3),
('8725928128', 'La ciudad y los perros', 7, 'https://i.postimg.cc/Kc39Szg8/42-La-ciudad-y-los-perros.jpg', 'Novela sobre la vida en una academia militar en Lima, Perú, y las tensiones sociales y personales de los cadetes.', 1),
('6447523443', 'La fiesta del chivo', 7, 'https://i.postimg.cc/2842yKGp/43-La-fiesta-del-chivo.jpg', 'Historia basada en la dictadura de Rafael Leónidas Trujillo en República Dominicana.', 1),
('1203345490', 'Conversación en La Catedral', 7, 'https://i.postimg.cc/T300q5BC/44-Conversaci-n-en-La-Catedral.jpg', 'Novela que explora la corrupción y la decadencia moral en Perú durante la dictadura de Manuel A. Odría.', 2),
('7323111686', 'La casa verde', 7, 'https://i.postimg.cc/YCM8WzDv/45-La-casa-verde.jpg', 'Novela que entrelaza varias historias en la selva peruana y en un burdel.', 3),
('6197490784', 'Pantaleón y las visitadoras', 7, 'https://i.postimg.cc/VNHF2Qft/46-Pantale-n-y-las-visitadoras.jpg', 'Novela satírica sobre un oficial del ejército peruano encargado de organizar un servicio de prostitutas para las tropas en la Amazonía.', 1),
('1148782808', 'Los cachorros', 7, 'https://i.postimg.cc/rppJhFkc/47-Los-cachorros.jpg', 'Novela corta que sigue la vida de un grupo de amigos en Lima desde su infancia hasta la adultez.', 2),
('2102104421', 'Travesuras de la niña mala', 7, 'https://i.postimg.cc/ZRBLS7nM/48-Travesuras-de-la-ni-a-mala.jpg', 'Historia de amor entre Ricardo Somocurcio y una mujer que aparece y desaparece a lo largo de su vida.', 2),
('2810822794', 'El sueño del celta', 7, 'https://i.postimg.cc/Jnf54NB4/49-El-sue-o-del-celta.jpg', 'Novela basada en la vida de Roger Casement, un diplomático británico que denunció los abusos coloniales en el Congo y Perú.', 1),
('3337921053', 'El laberinto de la soledad', 8, 'https://i.postimg.cc/Hnb4JrQ0/50-El-laberinto-de-la-soledad.jpg', 'Ensayo sobre la identidad mexicana y el carácter del pueblo mexicano.', 2),
('4639190320', 'Sor Juana Inés de la Cruz o Las trampas de la fe', 8, 'https://i.postimg.cc/1RFK7jLg/51-Sor-Juana-In-s-de-la-Cruz-o-Las-trampas-de-la-fe.jpg', 'Biografía de la poeta y monja mexicana Sor Juana Inés de la Cruz.', 1),
('1380981131', 'El arco y la lira', 8, 'https://i.postimg.cc/28f7sxD0/52-El-arco-y-la-lira.jpg', 'Ensayo sobre la poesía y su relación con la vida y la cultura.', 3),
('1935045932', 'Piedra de sol', 8, 'https://i.postimg.cc/fTjfD0WD/53-Piedra-de-sol.jpg', 'Extenso poema circular que refleja las preocupaciones filosóficas y estéticas del autor.', 2),
('8484165258', 'Veinte poemas de amor y una canción desesperada', 9, 'https://i.postimg.cc/XYjcJ7XF/54-Veinte-poemas-de-amor-y-una-cancion-desesperada.jpg', 'Colección de poemas que exploran el amor y la pérdida.', 3),
('2803601269', 'Canto general', 9, 'https://i.postimg.cc/ht287W7Y/55-Canto-general.jpg', 'Extensa obra poética que traza la historia de América Latina desde la prehistoria hasta el siglo XX.', 2),
('5036265268', 'Residencia en la tierra', 9, 'https://i.postimg.cc/MGX77P7p/56-Residencia-en-la-tierra.jpg', 'Serie de poemas que exploran la alienación y el absurdo de la existencia humana.', 1),
('6626438257', 'Memorial de Isla Negra', 9, 'https://i.postimg.cc/yN5FvmjX/57-Memorial-de-Isla-Negra.jpg', 'Poemario autobiográfico que recorre la vida del poeta y su relación con Chile.', 1);

INSERT INTO prestamo (usuario_id, libro_id, fecha_prestamo, fecha_limite, fecha_devolucion, devuelto) VALUES
(2, 7, '2021-09-01', '2021-09-15', '2021-09-15', TRUE),
(3, 48, '2021-09-01', '2021-09-15', '2021-09-15', TRUE),
(4, 39, '2021-09-01', '2021-09-15', '2021-09-15', TRUE);

-- Stored Procedures

-- Procedimiento para realizar un préstamo comprobando que el "estado" del usuario sea TRUE
DELIMITER //
CREATE PROCEDURE sp_realizar_prestamo(IN p_usuario_dni INT, IN p_libro_id INT, IN p_dias INT)
BEGIN
DECLARE v_estado BOOLEAN;
DECLARE v_stock INT;

SELECT estado INTO v_estado
FROM Usuario
WHERE dni = p_usuario_dni;

SELECT stock INTO v_stock
FROM Libro
WHERE libro_id = p_libro_id;

IF v_estado = TRUE AND v_stock > 0 THEN
INSERT INTO Prestamo (usuario_id, libro_id, fecha_prestamo, fecha_limite)
VALUES ((SELECT usuario_id FROM Usuario WHERE dni = p_usuario_dni), p_libro_id, CURRENT_DATE, CURRENT_DATE + INTERVAL p_dias DAY);
ELSEIF v_stock < 1 THEN
SELECT 'No hay stock disponible para este libro' AS mensaje;
ELSE
SELECT 'El usuario no puede realizar préstamos' AS mensaje;
END IF;
END //
DELIMITER ;

-- Procedimiento para devolver un libro
DELIMITER //
CREATE PROCEDURE sp_devolver_libro(IN p_prestamo_id INT)
BEGIN
UPDATE Prestamo
SET fecha_devolucion = CURRENT_DATE,
devuelto = TRUE
WHERE prestamo_id = p_prestamo_id;
END //
DELIMITER ;


-- Triggers

-- Trigger para actualizar el stock de un libro cuando se realiza un préstamo
DELIMITER //
CREATE TRIGGER tr_actualizar_stock_prestamo
AFTER INSERT ON Prestamo
FOR EACH ROW
BEGIN
UPDATE Libro
SET stock = stock - 1
WHERE libro_id = NEW.libro_id;
END //
DELIMITER ;

-- Trigger para actualizar el stock de un libro cuando se devuelve un libro
DELIMITER //
CREATE TRIGGER tr_actualizar_stock_devolucion
AFTER UPDATE ON Prestamo
FOR EACH ROW
BEGIN
IF NEW.devuelto = TRUE THEN
UPDATE Libro
SET stock = stock + 1
WHERE libro_id = NEW.libro_id;
END IF;
END //
DELIMITER ;


-- Eventos

-- Evento para verificar si hay libros que se han pasado de la fecha límite de devolución
-- y marcar a los usuarios "estado" como FALSE
DELIMITER //
CREATE EVENT e_verificar_prestamos_vencidos
ON SCHEDULE EVERY 1 DAY
STARTS (CURRENT_TIMESTAMP + INTERVAL 11 HOUR)
DO
BEGIN
UPDATE Usuario
SET estado = FALSE
WHERE usuario_id IN (
SELECT usuario_id
FROM Prestamo
WHERE fecha_limite < CURRENT_DATE
AND devuelto = FALSE
);
END //
DELIMITER ;
