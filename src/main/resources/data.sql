INSERT INTO quad_tree_node (id, left_top_lat, left_top_lon, right_bot_lat, right_bot_lon, load_factor)
VALUES
('1', 90, -180, -90, 180, 4),
('2', 90, -180, -60, -30, 3),
('3', 90, -30, 30, 180, 3),
('4', 30, -30, -60, 180, 0),
('5', -60, -180, -90, 180, 0);

INSERT INTO quad_tree_node_inheritance (id_parent, id_child)
VALUES
('1', '2'),
('1', '3'),
('1', '4'),
('1', '5');

INSERT INTO quad_tree_node_adjacency (id_1, id_2)
VALUES
('2', '3'),
('2', '4'),
('2', '5'),
('3', '4'),
('3', '5'),
('4', '5');
