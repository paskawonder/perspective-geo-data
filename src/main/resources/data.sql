INSERT INTO quad_tree_node (id, left_top_lat, left_top_lon, right_bot_lat, right_bot_lon)
VALUES
('1', 90, -180, -60, 180),
('2', 90, -180, -60, -30),
('3', 90, -30, 30, 180),
('4', 30, -30, -60, 180),
('5', -60, -180, -90, 180);

INSERT INTO quad_tree_node_inheritance (id_parent, id_child)
VALUES
('1', '2'),
('1', '3'),
('1', '4'),
('1', '5');

INSERT INTO quad_tree_node_adjacency (id_1, id_2)
VALUES
('2', '3'),
('3', '2'),
('2', '4'),
('4', '2'),
('3', '4'),
('4', '3'),
('2', '5'),
('5', '2'),
('3', '5'),
('5', '3'),
('5', '4'),
('4', '5');
